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

<br>

### 4-1. 매물 조회 중 페이지네이션 로직 분리
- 기존 모든 리스트 정보를 조회 후 해당 리스트를 페이지네이션으로 변환했던 과정을 페이지네이션 객체를 미리 생성 후 JPA 파라미터로 추가하는 로직으로 변경
- 기존 로직은 모든 리스트 조회하고 페이지네이션 객체를 생성하는 것은 N개의 엔티티들을 모두 조회
- 현재 로직은 요청되는 페이지에 해당되는 엔티티만을 조회

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

<br>

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
    ...
```

### 4-2. 매물 조회 시 N + 1 문제 발생
- 현재 Page로 생성한 ROOM 엔티티 리스트를 먼저 조회하고 각 ROOM 엔티티 마다 LIKES, IMAGE 테이블이 N번 씩 추가 쿼리가 실행됨
    - 현재 페이지네이션을 통해 총 page size * 3 번 만큼 반복되고 있음 ( 3N )
- 현재 구성은 ROOM 엔티티를 조회 이후 LIKES, IMAGE 엔티티 내부에 ROOM이 있고, 해당 ROOM으로 LazyLoading 조회를 통해 N + 2N 조회되는 문제가 발생함

<br>

### 현재 문제가 되는 코드

```java
@Entity
@Table(name = "ADDRESS") // ...
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

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
Page<Room> roomPage = roomRepository.findAll(spec, pageable);   // Room N번 조회

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
- 문제를 해결할 수 있는 첫번째 방식은 Room 을 N번 조회한 후 해당 엔티티들의 RoomId를 추출하여 다른 리스트로 모으고 해당 RoomId 리스트를 In 절으로 배치 조회
- 두번째 방식은 Fetch Join 으로 변경하여 Room과 연관된 Likes, Images 엔티티를 한 번에 조회
    - 하지만 페이징과 Fetch Join을 함께 사용시 결과 행이 중복되어 페이징 계산에 문제가 발생할 수 있어 DISTINCT 사용 필요

<br>

## 5. 화면 구성

<br>

