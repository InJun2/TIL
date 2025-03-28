# 방줘 프로젝트 ( 25.03.10 ~ 25.04.10 )

## 1. 프로젝트 소개 (현재 작성 중)
- '방줘' 프로젝트는 월세를 저렴하게 구하고 싶은 청년들을 타겟으로 한 서비스로 공인중계사 없이 월세 거래를 진행할 수 있도록 도와주는 서비스입니다.
- 등기부 등본 정보를 통해 임대 건물에 대한 정보를 조회하고 위험도 분석 및 임대인과 임차인의 거래가 가능하도록 해당 서비스를 통해 계약서를 작성 및 작성 보조 기능을 제공합니다.
- BE 4명, FE 2명으로 구성되어 진행하였으며 저는 매물 기능 및 결제 기능을 담당하였습니다.


<br>

### 1-1. 담당 서비스 도메인

📑 매물 기능
- 매물 등록 및 조회
- 집주인 인증
- 포트원 본인 인증
- 등기부 위험도 분석

📑 결제 기능
- 포트원 금액 결제
- 결제 내역 조회

<br><br>

### 1-2. 서비스 아키텍처



<br>


<br><br>


### 1-3. 내가 사용한 기술 스택

#### ✅ BE
- Java, SpringBoot, JPA, Swagger

#### ✅ DB
- MySQL, Redis

<br><br>


## 2. 협업 진행 방식

<br>

### 2-1. Jira 협업 툴 사용


- Jira 스프린트 진행

<br>

### 2-2. Figma 와이어프레임

![wireframe](./img/figma.png)

- figma를 사용한 와이어프레임 작성

<br>

### 2-3. Notion 명세 및 문서화

![Notion1](./img/notion.png)

<br>

![요구사항 명세서](./img/요구사항명세서.png)

<br>

![API 명세서](./img/API명세서.png)

<br>

![postman](./img/postman.png)

- 요구사항 명세서, API 명세서 노션으로 작성
- API 테스트는 PostMan 사용

<br>

### 2-4. ERD 설계

![erd](./img/erd.png)

- ERD-Cloud 를 사용한 ERD 설계

<br>

### 2-5. GitLab MergeRequest


<br><br>

## 3. 프로젝트 구현 사항

<br>

## 4. 프로젝트 진행 중 이슈 사항

### 4-1. 공통 모듈 분리

#### 1. 매물 조회 중 페이지네이션 로직 분리
- 기존 모든 리스트 정보를 조회 후 해당 리스트를 페이지네이션으로 변환했던 과정을 페이지네이션 객체를 미리 생성 후 JPA 파라미터로 추가하는 로직으로 변경
- 기존 로직은 모든 리스트 조회하고 페이지네이션 객체를 생성하는 것은 N개의 엔티티들을 모두 조회
- 현재 로직은 요청되는 페이지에 해당되는 엔티티만을 조회

```java
// 응답 반환을 위한 페이지네이션 공통 모듈 분리
@Getter
@JsonPropertyOrder({"totalItems", "totalPages", "currentPage", "size", "currentPageItemCount", "offset", "items"})
public class PageResponse<T> {
	private static final int DEFAULT_SIZE = 15;

	private final int totalItems;
	private final int totalPages;
	private final int currentPage;
	private final int size;
	private final int currentPageItemCount;
	private final List<T> items;


	public PageResponse(int totalItems, Integer currentPage, Integer size, List<T> items) {
		int cp = (currentPage == null || currentPage <= 0) ? 1 : currentPage;
		int s = (size == null || size <= 0) ? DEFAULT_SIZE : size;
		this.totalItems = totalItems;
		this.currentPage = cp;
		this.size = s;
		this.currentPageItemCount = (items == null) ? 0 : items.size();
		this.totalPages = (totalItems == 0) ? 0 : (int)Math.ceil((double)totalItems / s);
		this.items = items;
	}

	public PageResponse(int totalItems, Integer currentPage, List<T> items) {
		this(totalItems, currentPage, DEFAULT_SIZE, items);
	}

	public int getOffset() {
		return (currentPage - 1) * size + 1;
	}
}

// Page 객체를 조회하기 위한, Pageable 객체 생성 클래스
public class PaginationRequest {
	private static final int DEFAULT_SIZE = 15;

	public static Pageable toPageable(Integer page, Integer size) {
		int currentPage = (page == null || page < 1) ? 1 : page;
		int pageSize = (size == null || size < 1) ? DEFAULT_SIZE : size;

		return PageRequest.of(currentPage - 1, pageSize);
	}

	public static Pageable toPageable(Integer page) {
		int currentPage = (page == null || page < 1) ? 1 : page;
		
		return PageRequest.of(currentPage - 1, DEFAULT_SIZE);
	}
}

// 조회를 위한 매물 레포지토리로 Pageable 객체를 사용해 Page에 해당하는 엔티티만 페이지 리스트로 반환
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	Optional<Room> findByRoomIdAndDeletedAtIsNull(Long roomId);

	Page<Room> findAllByMemberId(Long memberId, Pageable pageable);

	List<Room> findByRoomIdIn(List<Long> roomIds);

	Page<Room> findAll(Specification<Room> spec, Pageable pageable);
}
```

<br><br>

#### 2. S3 File Upload 모듈 분리

```java
// 매물 저장 기존 코드
@Service
public class RoomImageService {
	private final ImageRepository imageRepository;
	private final RoomRepository roomRepository;

	private final S3Client s3Client;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	@Value("${aws.region}")
	private String awsRegion;

	public void uploadAndSaveImage(Room room, MultipartFile file) {
		try {
			String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.bucket(bucketName)
				.key(fileName)
				.build();

			s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

			String imageUrl = "https://" + bucketName + ".s3." + awsRegion + ".amazonaws.com/" + fileName;

			Room managedRoom = roomRepository.getReferenceById(room.getRoomId());

			Image image = Image.builder()
				.room(managedRoom)
				.imageUrl(imageUrl)
				.build();

			imageRepository.save(image);
		} catch (IOException e) {
			throw new BusinessException(RoomErrorCode.FAIL_IMAGE_UPLOAD);
		}
	}
}
```

#### 문제 사항
- 해당 코드를 잘 사용하고 있다가 이후 다른 도메인에서도 이미지 저장하는 로직이 필요
- S3에 대한 정보를 애초에 매물 ImageService 이 관리하고 있는 구조가 잘못된 설계라고 생각이 들게되었음
    - 이후 확장될 수 있을 만한 기능에 대해서 설계에서 분리하는 습관을 들이면 좋을 것 같음..
- 이후 다른 도메인에서도 사용할 수 있도록 어댑터 패턴을 이용한 아래 방법으로 분리

```java
// 이후 기능이 추가될수 있어 추상화
public interface FileUploaderPort {
	String upload(MultipartFile file, String directory, String fileName);
}

// S3 관련 데이터를 해당 어댑터과 관리
@Service
@RequiredArgsConstructor
public class S3FileUploaderAdapter implements FileUploaderPort {
	private final S3Client s3Client;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	@Value("${aws.region}")
	private String awsRegion;

	@Override
    public String upload(MultipartFile file, String directory, String fileName) {
		try {
			String key = directory + fileName;
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.bucket(bucketName)
				.key(key)
				.build();

			s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

			return "https://" + bucketName + ".s3." + awsRegion + ".amazonaws.com/" + key;
		} catch (IOException e) {
			throw new BusinessException(GlobalErrorCodes.FAIL_IMAGE_UPLOAD);
		}
	}
}

// 이후 S3 관련 정보는 FileUploaderPort 인터페이스가 관리
@Service
@RequiredArgsConstructor
public class RoomImageService {
    private final static String IMAGE_PATH = "rooms/"
	private final FileUploaderPort fileUploader;
	private final RoomImageRepository imageRepository;
	private final RoomRepository roomRepository;

	public void uploadAndSaveImage(Room room, MultipartFile file) {
		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		String imageUrl = fileUploader.upload(file, IMAGE_PATH, fileName);
		Room managedRoom = roomRepository.getReferenceById(room.getRoomId());
		Image image = Image.builder().room(managedRoom).imageUrl(imageUrl).build();
		imageRepository.save(image);
	}
}
```

<br><br>

### 4-2. 단방향 연관관계 JPQL 코드 수정 고민
- 단건 조회에서 매물(Room)에 관련된 객체를 받아오고 각각의 Repository에서 조회하고 있는데 이후 GPT를 통해 JPQL을 이용한 아래 방법이 되는지 확인해봤는데 역시 안됨
    - 현재 Room에는 양방향 연관관계가 되어있지 않음
    - 객체와 SQL문이 섞여 사용하다보니 복잡하기도 함
- 1:1 관계인 Address나 Likes 같이 단일 값은 처리하기 쉽지만, Options, MaintenanceInclude, Image와 같은 1:N 컬렉션을 포함하면 데이터 중복 문제가 발생할 수 있다고 함
- 현재 단건조회 이며 추가적인 조회쿼리에 대해서는 각각의 엔티티 별도의 Repository 가 나뉘어 있으니 각각 단건 조회하는 것이 좋다고 생각되었음. 이전 방법을 그대로 이용

```java
// 기존 코드
@Transactional(readOnly = true)
public SearchRoomResponseDto searchRoom(Long roomId, Long memberId) {
    var room = findRoom(roomId);
    var address = addressService.findByRoom(room);
    var options = optionService.findByRoom(room);
    var maintenanceIncludes = maintenanceIncludeService.findByRoom(room);
    var images = imageService.findByRoom(room);
    var isLiked = likeRepository.findByRoomAndMemberId(room, memberId)
        .map(Likes::getFlag)
        .orElse(false);

    return RoomConverter.convert(room, isLiked, address, options, maintenanceIncludes, images);
}

// 이후 고민 코드
@Query("""
    SELECT new com.bangjwo.room.application.dto.response.SearchRoomResponseDto(
            r, a, o, m, i)
    FROM Room r
    LEFT JOIN Address a ON a.room = r
    LEFT JOIN Options o ON o.room = r
    LEFT JOIN MaintenanceInclude m ON m.room = r
    LEFT JOIN Image i ON i.room = r
    WHERE r.roomId = :roomId
    """)
Optional<SearchRoomResponseDto> findRoomDetailsByRoomId(Long roomId);

```

<br><br>

### 4-3. 매물 조회 시 N + 1 문제 발생
- 현재 Page로 생성한 ROOM 엔티티 리스트를 먼저 조회하고 각 ROOM 엔티티 마다 LIKES, IMAGE 엔티티가 N번(Room 리스트 size 만큼) 씩 추가 조회 쿼리가 실행됨
    - 현재 페이지네이션을 통해 총 page size * 2 번 만큼 반복되고 있음 ( 1 + 2N )
- 현재 구성은 ROOM 엔티티를 조회 이후 LIKES, IMAGE 엔티티 내부에 ROOM이 있고, 해당 ROOM으로 LazyLoading 조회를 통해 1 + 2N 조회되는 문제가 발생함

<br>

```sql
Hibernate: 
    /* <criteria> */ select
        r1_0.room_id,
        r1_0.available_from,
        r1_0.bathroom_cnt,
        r1_0.building_type,
        ...
    from
        room r1_0 
    where
        r1_0.member_id=? 
    limit   /* Pageable 객체만큼만 조회 */
        ?
```

- 변경된 sql 페이지네이션 조회 결과

<br><br>

```sql
Hibernate: 
    /* <criteria> */ select
        r1_0.room_id,
        r1_0.available_from,
        r1_0.bathroom_cnt,
        ...
    from
        room r1_0 
    where
        r1_0.monthly_rent<=? 
        and (
            r1_0.exclusive_area between ? and ? 
            or r1_0.exclusive_area between ? and ? 
            or r1_0.exclusive_area between ? and ?
        ) 
        and r1_0.room_id in ((select
            a1_0.room_id 
        from
            address a1_0 
        where
            a1_0.lat between ? and ? 
            and a1_0.lng between ? and ?)) 
    limit
        ?, ?
Hibernate: 
    /* <criteria> */ select
        l1_0.like_id,
        l1_0.flag,
        ...
    from
        likes l1_0 
    where
        l1_0.room_id=? 
        and l1_0.member_id=?
Hibernate: 
    /* <criteria> */ select
        i1_0.image_id,
        i1_0.created_at,
        ...
    from
        image i1_0 
    where
        i1_0.room_id=? 
    order by
        i1_0.room_id desc 
    limit
        ?
Hibernate: 
    /* <criteria> */ select
        l1_0.like_id,
        l1_0.flag,
        ...
    from
        likes l1_0 
    where
        l1_0.room_id=? 
        and l1_0.member_id=?
Hibernate: 
    /* <criteria> */ select
        i1_0.image_id,
        i1_0.created_at,
        ...
    from
        image i1_0 
    where
        i1_0.room_id=? 
    order by
        i1_0.room_id desc 
    limit
        ?
Hibernate: 
    /* <criteria> */ select
        l1_0.like_id,
        l1_0.flag,
    ......  /*  page 만큼 반복 */
```

<br>

### 현재 문제가 되는 코드

```java
@Entity
@Table(name = "LIKES") // ...
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likeId;

    // 현재 단방향으로 구성
	@ManyToOne(fetch = FetchType.LAZY)  // 연관관계가 Lazy로 되어있어 실제 사용 시점에 각각 별도의 조회 발생
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;
    // ...
}


@Entity
@Table(name = "IMAGE") // ...
public class Image extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imageId;

    // 현재 단방향으로 구성
	@ManyToOne(fetch = FetchType.LAZY)  // 연관관계가 Lazy로 되어있어 실제 사용 시점에 각각 별도의 조회 발생
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;
    // ...
}

// RoomService searchRooms() 메서드
Pageable pageable = PaginationRequest.toPageable(page);
Page<Room> roomPage = roomRepository.findAll(spec, pageable);   // Page에 일치하는 Room 리스트 조회

List<RoomSummaryResponse> roomSummaryList = roomPage.getContent().stream()
    .map(room -> {
        boolean liked = likeRepository.findByRoomIdAndMemberId(room.getRoomId(), memberId)  // Room 하나마다 Like 조회
            .map(Likes::getFlag)
            .orElse(false);
        String imageUrl = imageService.findMainImageByRoom(room).getImageUrl(); // Room 하나마다 Image 조회
        return RoomConverter.convertToRoomSummary(room, liked, imageUrl);
    })
    .collect(Collectors.toList());
```

### 문제 해결 방식
- 문제를 해결할 수 있는 첫번째 방식은 Room 을 1번 조회한 후 해당 엔티티들의 RoomId를 추출하여 다른 리스트로 모으고 해당 RoomId 리스트를 In 절으로 배치 조회
- 두번째 방식은 Fetch Join 으로 변경하여 Room과 연관된 Likes, Images 엔티티를 한 번에 조회
    - 물론, Fetch Join을 하기위해서는 Room에 연관 관계를 추가하여 사용하여야함
    - 하지만 페이징과 Fetch Join을 함께 사용시 결과 행이 중복되어 페이징 계산에 문제가 발생할 수 있어 DISTINCT 사용 필요

<br>

### 잘못된 설계로 발생한 문제
- 현재 Room을 생성할 때 Address, Image, Options, Likes 등의 객체를 가져오는데 해당 객체들은 모두 RoomId가 내부에 있기에 해당 테이블안에 위치해두었음
- 이후 Address, Image, Options, Likes 등의 객체를 가져올때 Room을 한번에 가져오는 것은 좋은 생각이었다고 생각
- 그러나 Room을 조회하고 이후 Room에 관련된 객체들을 조회하려니 N + 1 문제가 발생한 것
- fetch 조인을 한다고 해결하려 했지만 다음과 같은 문제가 발생
    - 현재 단방향으로 되어있어 Room에는 연관 엔티티가 없어 양방향으로 바꾸지 않으면 Fetch 조인이 불가능. 양방향으로 된다고 할 때 순환 참조가 발생할 수 있음
    - 또한 Spring Data JPA 공식 문서에 따르면, 페치 조인 + 페이징은 권장하지 않는다고 함

<br>

### 연관 관계 정리
- Lazy 연관 관계는 불필요한 엔티티에 대해 직접 조회하기 전까지는 쿼리를 날리지 않아 해당 엔티티를 안쓰는 항목이 있으면 좋음. 하지만 이후 쓰게 된다면 그 시점에 조회하기 때문에 추가적인 쿼리가 나가게 됨
- JPQL에서 JOIN FETCH 구문을 통해 명시적으로 필요한 엔티티를 JOIN으로 한꺼번에 조회함. Lazy로 설정된 관계라도, 필요한 시점에만 동적으로 fetch join 가능
- [해당 블로그](https://velog.io/@xogml951/JPA-N1-문제-해결-총정리)가 정리가 잘되어있어 참조

<br>

### 배치 방식으로 해결 이유
- Page 객체로 선처리 했던 값을 사용하다보니 Fetch 조인은 중복 문제 발생 가능 + 양방향으로 변경 필요하므로, 첫번째 해결 방법인 배치 방법으로 문제 해결
- 네이티브 쿼리를 사용하지 않은 이유는 하나의 레포지토리에서 다른 엔티티에 대한 의존성이 생길 수 있어, 도메인 책임 분리를 유지하기 위해 JPQL과 배치 조회 방식으로 해결 결정
    - 네이티브 쿼리를 사용시 반환 타입이 Entity가 아니라면 네이티브 쿼리는 영속성 컨텍스트에서 관리되지 않음
    - ex) Room Repository에서 Likes 엔티티를 반환해도 영속성 컨텍스트에 관리되지만 반환 타입이 맞지 않다면 관리되지 않음
- 변경된 코드와 발생 쿼리는 아래와 같음
    - 추가적으로 JPQL 에서 서브쿼리에서 ORDER BY, LIMIT 같은 문법을 지원하지 않아 MAX로 변경

```java
@Transactional(readOnly = true)
public RoomListResponseDto createRoomListResponseDto(
    List<Room> rooms, int totalItems, int page, int size, Long memberId) {
    List<Long> roomIds = rooms.stream()
        .map(Room::getRoomId)
        .toList();

    List<Likes> likes = likeService.getLikeRooms(rooms, memberId);
    Map<Long, Boolean> likeMap = likes.stream()
        .collect(Collectors.toMap(l -> l.getRoom().getRoomId(), Likes::getFlag, (a, b) -> a));

    List<Image> images = imageService.getMainImages(roomIds);
    Map<Long, String> imageMap = images.stream()
        .collect(Collectors.toMap(i -> i.getRoom().getRoomId(), Image::getImageUrl, (a, b) -> a));

    List<RoomSummaryResponse> roomSummaryList = rooms.stream()
        .map(room -> {
            boolean liked = likeMap.getOrDefault(room.getRoomId(), false);
            String imageUrl = imageMap.getOrDefault(room.getRoomId(), null);
            return RoomConverter.convertToRoomSummary(room, liked, imageUrl);
        })
        .toList();

    return new RoomListResponseDto(totalItems, page, size, roomSummaryList);
}

// Image JPQL
@Query("""
        SELECT i
        FROM Image i
        WHERE i.room.roomId IN :roomIds
        AND i.createdAt = (
            SELECT MAX(i2.createdAt)
            FROM Image i2
            WHERE i2.room.roomId = i.room.roomId
        )
    """)
List<Image> findMainImagesByRoomIds(@Param("roomIds") List<Long> roomIds);

// Likes JPQL
@Query("""
        SELECT l
        FROM Likes l
		WHERE l.room IN :rooms AND l.memberId = :memberId
    """)
List<Likes> findByRoomInAndMemberId(@Param("rooms") List<Room> rooms, @Param("memberId") Long memberId);
```

<br>

```java
Hibernate: 
    /* <criteria> */ select
        r1_0.room_id,
        r1_0.available_from,
        ...
    from
        room r1_0 
    where
        r1_0.monthly_rent<=? 
        and (
            r1_0.exclusive_area between ? and ? 
            or r1_0.exclusive_area between ? and ? 
            or r1_0.exclusive_area between ? and ?
        ) 
        and r1_0.room_id in ((select
            a1_0.room_id 
        from
            address a1_0 
        where
            a1_0.lat between ? and ? 
            and a1_0.lng between ? and ?)) 
    limit
        ?, ?
Hibernate: 
    /* SELECT
        l 
    FROM
        Likes l 
    WHERE
        l.room IN :rooms 
        AND l.memberId = :memberId  */ select
            l1_0.like_id,
            l1_0.member_id,
            l1_0.room_id
            ...
        from
            likes l1_0 
        where
            l1_0.room_id in (?, ?, ?) 
            and l1_0.member_id=?
Hibernate: 
    /*     SELECT
        i     
    FROM
        Image i     
    WHERE
        i.room.roomId IN :roomIds     
        AND i.createdAt = (
            SELECT
                MAX(i2.createdAt)         
            FROM
                Image i2         
            WHERE
                i2.room.roomId = i.room.roomId     
        )  */ select
            i1_0.image_id,
            i1_0.image_url,
            i1_0.room_id,
            ...
        from
            image i1_0 
        where
            i1_0.room_id in (?, ?, ?) 
            and i1_0.created_at=(
                select
                    max(i2_0.created_at) 
                from
                    image i2_0 
                where
                    i2_0.room_id=i1_0.room_id
            )
```

<br>


<br>

## 5. 화면 구성

<br>

