# Glass-Bottle 프로젝트 진행 중 이슈 사항

## Glass Bottle Project
- [Glass Bottle Project](https://github.com/selab-hs/glass-bottle)는 SE스터디원 3명과 해당 프로젝트에 대해 피드백을 진행해주는 지인1명으로 진행한 프로젝트로 사용자가 특정 mbti, 혹은 무작위로 불특정 10명 가량 익명 편지를 보내고 해당 편지에 대해 익명의 답변자로부터 답변받을 수 있도록 API를 구현
- 주요 기능은 스프링시큐리티를 사용하지 않고 로컬 로그인을 통한 jwt 토큰으로 유저 정보를 관리하는 유저 도메인을 맡은 [HwangHarim](https://github.com/HwangHarim)님과 해당 유저 정보를 통하여 주요 기능인 편지 작성/답변을 진행하는 편지 도메인을 맡은 [sonrose](https://github.com/sonrose)님과 진행
- 해당 프로젝트에서 저는 주로 편지 도메인의 약간의 기능과 모니터링, DevOps 부분을 담당하였음
- 해당 프로젝트는 Git Flow 방식을 통하여 main, staging, feature, fix, docs 브랜치를 사용하여 코드 구현 사항에 맞는 브랜치를 사용하여 적용 진행
- 해당 프로젝트에서 사용한 라이브러리는 다음과 같음
    - spring-boot-starter-web
    - spring-boot-starter-webflux
    - spring-boot-starter-data-jpa
    - spring-boot-starter-actuator
    - spring-boot-starter-test
    - spring-boot-configuration-processor
    - lombok
    - redis
    - mysql
    - swagger 3.0
    - jwt
    - slack appender
    - zxing
    - jackson
    - fixture-monkey

<br>

## 구현사항
- 에러 로그 발생시 Slack으로 메시지 전송
- 매일 8시 마다 전일 회원가입 유저, 작성한 편지 Slack 메시지 전송
- 1시간 마다 서버 상태를 Slack으로 메시지 전송
- 5분마다 편지 엔티티의 생성시간에 따른 상태 변경
- 편지 답변시 편지의 유효시간을 30분으로 지정
- 편지 공유 기능 구현
- GitAction을 사용하여 AWS S3에 저장하고 AWS CodeDeploy를 통한 CI/CD 자동화

<br>

### * Slack 메시지 전송
- Slack으로 메시지를 전송하는 기능들은 모두 Slack의 Incomming Webhook App을 통하여 메시지 전송하였음
- 해당 App에서 제공하는 WebHook URI를 통하여 메시지를 전송할 수 있음
- 해당 WebHook URI는 Incomming Webhook App을 추가한 슬랙 채널의 민감한 데이터이므로 Git SubModule을 사용하여 private한 하위 저장소를 만들고 해당 저장소에 application.yml에 해당 값을 추가해주었음
- 해당 yml에 넣은 값은 Java 코드에서 @Value나 @ConfigurationProperties를 사용하여 값을 가져올 수 있음
    - [사용예시](https://github.com/InJun2/TIL/blob/main/Stack/Spring/Value-ConfigurationProperties.md)
```yml
logging:
    slack:
        uri:
            slack-error: ~~~
            slack-glass-bottle: ~~~
            ...
```

### 에러 로그 발생시 Slack으로 메시지 전송
- Slack Appender 라이브러리를 사용하여 자동으로 에러 레벨의 로그 발생 시 Slack으로 메시지 전달
- Logback을 이용하여 자동으로 모든 에러 로그가 중앙 집권화되어 에러를 한곳에서 확인하고 관리할 수 있음
    - Logback이란 오픈소스 로깅 프레임워크로 SLF4J의 구현체이자 스프링부트에 기본으로 내장되어 있는 로깅 라이브러리로 xml 방식과 @Configuration을 통한 java 코드 방식이 존재
    - 해당 프로젝트는 xml 방식을 사용하여 로깅 설정 진행
- 해당 xml 설정을 적용하기 위해 SubModule의 application.yml에 logging.config: classpath:{xml디렉토리/xml 파일명} 처럼 설정 파일의 디렉토리 위치 추가
- 프로젝트에서 사용하는 예외처리는 모두 BusinessException을 상속받기때문에 ExceptionHandler을 통하여 해당 에러를 캐치하면 Error Level Log 출력
    - Error Level Log를 출력하여 위에서한 설정을 통하여 자동으로 Slack으로 메시지 전송
- [사용 예시](https://github.com/InJun2/TIL/blob/main/Stack/Spring/SlackAppender.md)

<br>

### 매일 8시 마다 전일 회원가입 유저, 작성한 편지 Slack 메시지 전송
- [Spring Scheduler](https://github.com/InJun2/TIL/blob/main/Stack/Spring/Spring-Scheduler.md)를 사용하여 다른 의존성을 추가하지 않고 @EnableScheduling 어노테이션을 추가하여 설정
    - 해당 프로젝트에서는 설정을 위해 Scheduler class를 생성하여 @Configuration과 @EnableScheduling 어노테이션만을 추가하여 사용하였음
- Spring Scheduler의 cron 표현식을 이용하여 특정 시간에 메소드 실행
```java
@Scheduled(cron = "0 0 8 * * *")    // 매일 8시간 마다 실행
    private void schedulerYesterdayLetter() {
        var response = restTemplateService.postRequestToSlack(slackProperties.slackGlassBottle(), "Write Letters", letterService.getYesterdayLetters());
        log.info(String.valueOf(response));
    }

@Scheduled(cron = "0 0 8 * * *")    // 매일 8시간 마다 실행
    private void schedulerYesterdayJoinMember() {
        var response = restTemplateService.postRequestToSlack(slackProperties.slackJoinMember(), "Join Users", memberService.getYesterdayJoinUsers());
        log.info(String.valueOf(response));
    }
```
- slackProperties 통하여 application.yml의 Slack WebHook URI를 가져왔으며, [RestTemplate](https://github.com/InJun2/TIL/blob/main/Stack/Spring/Spring-RestTemplate.md)를 사용하는 메서드들을 묶어 Service를 생성하였음
- RestTemplate는 buildRestTemplate를 @Bean으로 등록하여 설정하고 싱글톤으로 사용하였음
```java
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    ...

    // 기존 WebMvcConfigurer를 사용하는 WebConfig에 메서드 추가하였음
    @Bean
    public RestTemplate buildRestTemplate(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 연결시간초과, ms
        factory.setReadTimeout(5000); // 읽기시간초과, ms
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100) // 최대 오픈되는 커넥션 수
                .setMaxConnPerRoute(5) // IP,포트 1쌍에 대해 수행 할 연결 수
                .build();
        factory.setHttpClient(httpClient);

        return new RestTemplate(factory);
    }
}
```
```java
@Service
@RequiredArgsConstructor
public class RestTemplateService {
    private final RestTemplate restTemplate;

    public String postRequestToSlack(String uri, String sender, Object data) {
        SlackPostRequest request = new SlackPostRequest(sender, data, ":love_letter:");

        return sendPost(uri, request);
    }

    public String getToUri(String uri) {
        return restTemplate.getForObject(uri, String.class);
    }

    private String sendPost(String uri, SlackPostRequest request) {
        return String.valueOf(
                restTemplate.exchange(uri, HttpMethod.POST, request.toEntity(), String.class)
        );
    }
}
```
- SlackPostRequest 객체는 Slack으로 Post 요청을 보내기 위한 데이터를 담아 전송하기 위해 생성하였음
    - 현재 Slack으로 Post 전송 요청을 하는 메서드가 3개이다 보니 중복 코드를 줄이기 위해 객체로 생성하여 실행하였음
- 해당 객체의 정보는 다음과 같음
    - Slack Post Request를 JSON 타입으로 request를 생성하기 위해 Map을 통해 key, value로 값을 넣어주었고 이를 전송하기 위함
    - HttpHeaders를 통해 JSON Type임을 명시
```java
public class SlackPostRequest {
    private final HttpHeaders headers;
    private final Map<String, Object> requestVo;

    public SlackPostRequest(String sender, Object data, String icon) {
        this.headers = createHeader();
        this.requestVo = createRequestVo(sender, data, icon);
    }

    private HttpHeaders createHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }

    private Map<String, Object> createRequestVo(String sender, Object data, String icon) {
        Map<String, Object> request = new HashMap<>();
        request.put("username", sender);
        request.put("text", data);
        request.put("icon_emoji", icon);

        return request;
    }

    public HttpEntity<Map<String, Object>> toEntity(){
        return new HttpEntity<>(requestVo, headers);
    }
}
```
- 마지막으로 Slack으로 보낼 데이터의 letterService.getYesterdayLetters()와 memberService.getYesterdayJoinUsers()는 구현 방식이 유사하여 getYesterdayLetter의 경우만 표시하였음
    - 마찬가지로 두 메서드에서 사용하는 LocalDateTime이 전일 8시1초부터 금일 8시까지로 같으므로 중복코드를 없애기 위해 해당 시간의 LocalDateTime을 LocalDateTimeUtil 클래스를 생성하여 사용하였음
```java
@Transactional(readOnly = true)
public String getYesterdayLetters() {
    var letters = letterRepository
            .findAllByCreatedAtBetween(
                    LocalDateTimeUtil
                            .getYesterdayEightClock()
                    , LocalDateTimeUtil
                            .getTodayEightClock());
    return lettersToString(letters);
}

private String lettersToString(List<Letter> letters) {
    if (letters == null || letters.isEmpty()) {
        return "전날 작성된 편지는 존재하지 않습니다.";
    }

    StringBuilder message = new StringBuilder();
    for (Letter letter : letters) {
        message.append("[ CreateTime : ").append(letter.getCreatedAt()).append(", letterId : ").append(letter.getId()).append(", letterState : ").append(letter.getState()).append(" ] \n");
    }

    return message.toString();
}
```
```java
    // LocalDateTimeUtil Class는 다음과 같음
    public class LocalDateTimeUtil {
    public static LocalDateTime getNow(){
        return LocalDateTime.now();
    }

    public static LocalDateTime getYesterdayEightClock(){
        return LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(8,0,1));
    }

    public static LocalDateTime getTodayEightClock(){
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(8,0,0));
    }
}
```

<br>

### 1시간 마다 서버 상태를 Slack으로 메시지 전송
- [Spring Actuator](https://github.com/InJun2/TIL/blob/main/Stack/Spring/Actuator.md)을 사용하여 서버 상태를 Spring Scheduler을 사용하여 1시간마다 서버의 상태를 Slack으로 모니터링
- 사용방법은 위와 동일하게 SpringScheduler를 사용한 메서드에서 RestTemplate를 통하여 get 요청을 통해 actucator health 정보를 받아와 Slack으로 Post 요청 전송
```java
@Scheduled(cron = "0 0 */1 * * *")
private void schedulerServerStateCheck() {
    var health = restTemplateService.getToUri("http://localhost:8080/actuator/health");
    var response =restTemplateService.postRequestToSlack(slackProperties.slackServerHealth(), "Server Health", health);
    log.info(String.valueOf(response));
}

// RestTemplateService에서의 actuator data 문자열으로 받아오기위한 getToUri
public String getToUri(String uri) {
        return new RestTemplate().getForObject(uri, String.class);
    }
```

<br>


### 5분마다 편지 엔티티의 생성시간에 따른 상태 변경
- 해당 작업은 5분마다 편지들을 모두 조회하고 만약 하루가 지났다면 '만료' 상태로 변경
- 현재 모든 JPA Entity는 BaseEntity를 상속받아 생성시간과 변경시간이 같이 저장되기 때문에 생성시간의 LocalDateTime을 비교하여 실행
- 사용한 코드는 다음과 같음
```java
// BaseEntity Class
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modifiedAt;
}
```
```java
// LetterSchedulerService Class
@Scheduled(cron = "0 */5 * * * *")
private void schedulerYesterdayLetter() {
    validateExpirationLetterState();
}

public void validateExpirationLetterState(){
    letterService.findLetterState(LetterState.ACTIVE)
            .stream()
            .filter(letter -> LocalDateTimeUtil.getNow().isAfter(letter.getCreatedAt().plusDays(1)))
            .forEach(letter -> {
                letter.updateLetterState(LetterState.EXPIRATION);
                letterService.saveLetter(letter);
                log.info("해당 편지 만료 : " + letter.getId());
            });
}

// LetterService Class
@Transactional(readOnly = true)
public List<Letter> findLetterState(LetterState state) {
    return letterRepository.findAllByState(state);
}
```

<br>

### 편지 답변시 편지의 유효시간을 30분으로 지정
- 만약 편지 답변시 작성 시작시에는 발송편지의 상태가 '활성화'였으나 작성 이후 '만료'가 되었을 경우. 즉, 답변을 시작했으나 받은 편지가 작성 중 상태가 '만료'로 변했을 경우 문제 발생
- 이를 해결하기 위해 답변 작성을 시작시 요청을 통해 '답변대기중' 상태로 변경하고 해당상태에서 작성 유효시간을 30분으로 지정하기 위한 구현
- 유효시간 30분을 지정하기 위해 java Timer Class를 사용 
- [처리 방법](https://github.com/InJun2/TIL/blob/main/Project/glass-bottle/Glass-Bottle-Reply-Letter-Check.md)은 해당 방법을 통해 정리하였음

<br>

### 편지 공유 기능 구현
- 편지 발송자가 발송한 편지의 정보와 답변받은 편지 하나의 정보를 담은 QR Code를 받아 공유하기 위한 기능 구현
- Controller에서 로그인한 유저정보(인증 토큰), 발송편지 id, 답변 편지 id 요청을 받아 실행
```java
@GetMapping("/sharing/{id}/{receiveId}")
public ResponseEntity<String> sharingLetter(@AuthMember UserInfo userInfo, @PathVariable Map<String, String> pathId) {
    Long id = Long.valueOf(pathId.get("id"));
    Long receiveId = Long.valueOf(pathId.get("receiveId"));
    var dataQR = letterService.sharingLetter(userInfo, id, receiveId);
    return ResponseDto.ok(dataQR);
}
```
- 로그인한 유저 정보가 발송 편지 id의 유저인지 확인 및 해당 편지의 발송편지인지 확인
```java
// LetterService Class
public String sharingLetter(UserInfo userInfo, Long id, Long receiveId){
        var senderLetter = findLetterById(id);
        validateLetterSharingRequest(userInfo, id);

        SharingResponse response = new SharingResponse(senderLetter, findLetterById(receiveId));
        String data = MapperUtil.writeValueAsString(response);

        return convertQRString(data);
    }

// 발송 유저 id, 답변 유저 id, 편지 id를 가지고 있는 LetterInvoce Entity에서 로그인한 유저 id와 해당 발송 편지id가 있는 LetterInvoce 가 있는지 조회하여 유효성검사 진행
@Transactional(readOnly = true)
    public void validateLetterSharingRequest(UserInfo userInfo, Long letterId) {
        letterInvoiceRepository.findBySenderUserIdAndLetterId(userInfo.getId(), letterId)
                .orElseThrow(InvalidSharingLetterRequestException::new);
    }
```
- 해당 편지들의 데이터를 담은 SharingResponse 객체 생성
```java
public class SharingResponse {
    String senderMbti;
    String senderTitle;
    String senderContent;
    String receiverMbti;
    String receiverTitle;
    String receiverContent;

    public SharingResponse(WriteLetterResponse senderLetter, WriteLetterResponse receiverLetter) {
        this.senderMbti = String.valueOf(senderLetter.getSenderMbtiId());
        this.senderTitle = senderLetter.getTitle();
        this.senderContent = senderLetter.getContent();
        this.receiverMbti = String.valueOf(receiverLetter.getReceiverMbtiId());
        this.receiverTitle = receiverLetter.getTitle();
        this.receiverContent = receiverLetter.getContent();
    }
}
```
- 해당 객체를 ObjectMapper를 사용하는 메서드를 모아둔 Util Class의 writeValueAsString()를 호출하여 해당 객체를 String 값으로 변경
    - 해당 코드는 피드백을 진행해주는 지인이 구현해둔 코드임
```java
// MapperUtil Class
private static ObjectMapper mapper = new ObjectMapper();

public static ObjectMapper mapper() {
        var deserializationFeature = DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
        var serializationFeature = SerializationFeature.FAIL_ON_EMPTY_BEANS;

        mapper
                .setSerializationInclusion(NON_NULL);

        mapper
                .configure(deserializationFeature, false)
                .configure(serializationFeature, false);

        mapper
                .registerModule(new JavaTimeModule())
                .disable(WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }

public static String writeValueAsString(Object object) {
        try {
            return mapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("[ERROR] Exception -> {}", e.getMessage());
            throw new RuntimeException();
        }
    }

...
```
- zxing 라이브러리를 통하여 해당 String Data QRcode로 변경하여 응답
```java
public String convertQRString(String data) {
        try{
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return out.toString();
        } catch (WriterException e) {
            throw new QRConversionFailedException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
```
- 현재 API는 FrontEnd가 없기 때문에 편의상 해당 ByteArrayOutputStream를 String으로 변환해서 반환하고 있으나 QR 코드로 반환하는 경우 toString() 메서드를 제거하고 해당 방식을 통해 반환해야함
    ```java
    return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(out.toByteArray());
    ```

<br>

### GitAction을 사용하여 AWS S3에 저장하고 AWS CodeDeploy를 통한 CI/CD 자동화
- 프로젝트를 진행하면서 EC2 인스턴스에 CI/CD 자동화를 구현하기 위해 Git Action, AWS S3, AWS CodeDeploy를 사용하기로 하였음
- Git Action을 통해 staging 브랜치와 main 브랜치에 PR 혹은 push 진행 시 자동으로 S3에 저장하고 CodeDeploy를 실행하도록 하였음
- Git Action을 사용하면서 우리 프로젝트는 Submodule을 사용했기 떄문에 checkout시 submodule을 같이 checkout 하도록 설정
- 또한 Git Action 실행에서 민감한 데이터인 AWS ACESS KEY가 존재하기 때문에 Repository Secret을 사용하여 이러한 데이터를 안전하게 사용하였음
- [Git Action](https://github.com/InJun2/TIL/blob/main/Stack/Git/Git-Action.md)의 사용방법은 해당 링크에 정리해두었음
- 해당 Git Action에서 배포전 사전작업을 모두 진행한 후 해당 과정을 통해 gradle build 하고 압축된 jar 파일을 S3 버킷에 저장
- [AWS S3](https://github.com/InJun2/TIL/blob/main/Stack/AWS/S3.md)는 AWS에서 지원하는 인터넷용 스토리지로 사용방법은 해당 링크에 정리해두었음
- 이후 마찬가지로 Git Action을 통해 S3에 저장하였다면 AWS CodeDeploy를 통해 코드를 대신 실행시켜주도록 진행하였음
- 해당 CodeDeploy를 사용하기 위해 미리 EC2, S3, CodeDeploy 접근 권한을 가진 사용자를 만들고 ec2 내에 CodeDeploy Agent를 설치하고 AWS CodeDeploy에서 애플리케이션과 배포 그룹/배포 등을 만들었음
- 이후 CodeDeploy를 실행하기 위해 설정파일인 glass-bottle 바로 하위에 appspec.yml을 만들고, 실행하기 위한 파일인 deploy.sh 파일을 생성하였음
- 또한 서버 배포 과정 중 관리의 효율성을 위해 ec2-user 하위 디렉토리인 action을 만들고 해당 디렉토리에 S3에서 가져온 jar 파일과 실행되는 로그를 저장할 deploy.log 파일과 에러 로그를 저장할 deploy_err.log 파일을 통해 로그를 기록하였음

<br>

## 프로젝트 중 에러 Issue

### M1 Netty Error
- Netty를 쓰는 환경에서는 M1 Apple chip 부터 발생한다고 함
- 프로젝트 실행에는 문제가 없지만 실행시 에러 메시지가 출력되어 알아보니 native library를 로드하지 못해 발생한 오류로 netty-resolver-dns-native-macos라이브러리 문제 라고함
- [해결 방법](https://github.com/InJun2/TIL/blob/main/Stack/Error/M1-Netty-Error.md)

<br>

### Swagger 3.0 버전 문제 발생
- Swagger 3.0 은 프로젝트를 진행하면서 두번 문제가 발생하였음
- 첫번째 문제는 Spring 2.6.X 버전 이상에서 swagger 3.0을 사용할 경우 문제가 발생
- 해당 문제는 mvc 설정을 하던 중에 this.condition 부분에 NullPointerException을 발생했고, 해당 문제는 스프링 부트 2.6.0 버전부터 적용된 변경 사항이 springfox 의 기존 작동에 문제를 일으키고 있다고 함
- [해결 방법](https://github.com/InJun2/TIL/blob/main/Stack/Error/Spring2.6-Swagger3.0-Runtime-Error.md)
- 두번째 문제는 Spring Actuator을 추가하면서 발생하였음
- mvc 설정을 하던 중에 this.condition 부분에 NullPointerException을 발생했고, 해당 문제는 이전 Spring 버전과 Swagger 3.0 에서도 발생했던 문제와 동일하였음
- 이번 문제의 경우 Swagger는 모든 endpoint에 대해서 documentation을 해주는 역할인데 Actuator 또한 마찬가지로 몇몇 endpoint(refresh, beans, health)등을 직접 생성하는 역할이다 보니 의존성이 충돌나는 것이라고 함
- [해결 방법](https://github.com/InJun2/TIL/blob/main/Stack/Error/Swagger3.0-SpringActuator-Error.md)

<br>

### EC2 내 프로젝트 gradle build 시 무한로딩
- 자동 CI/CD 구축 이전으로 직접 EC2에서 git clone으로 서버 파일을 clone하고 gradle build 진행하였으나 Compile Java 에서 한번에 시간만 계속 늘어나고 동작하지 않았음
- 해당 문제의 원인은 EC2에 있었는데 현재 프로젝트는 프리티어로 진행하고 있는데 1GB의 메모리, 30GB의 스토리지가 최대인데 램 부족 현상으로 인해 서버가 자주 다운되는 현상이 일어 날수도 있다고함
- [해결 방법](https://github.com/InJun2/TIL/blob/main/Stack/Error/EC2_Memory_Shortage.md)

<br>

### 프로젝트 Hikari Pool Thread Starvation 발생
- 자동 CI/CD 를 통한 프로그램이 실행되다가 어느 정도 시간이 지난 이후 ec2 서버에서 지속적으로 종료되는 현상 발생
- Spring actuator health 정보도 down되어 있었으며 EC2 Instance의 상태검사도 1/2로 상태검사 실패로 적혀있었음
- 해당 에러는 주로 커넥션풀이나 쓰레드가 부족해서 혹은 메모리가 부족해서 발생한다고 함
- [해결 방법](https://github.com/InJun2/TIL/blob/main/Stack/Error/HikariPool-ThreadStarvation.md)

<br>

### CodeDeploy 실행시 Git SubModule Update 안됨
- 민감한 데이터를 관리하기 위해 resource/application.yml 파일을 git submoule을 통해 관리하고 있는데 해당 submodule 최신화가 진행되지 않음
- submodule도 간단한 PR을 통해 main으로 통합하는데 ec2 에서 서브모듈을 받아올 때 main 브랜치를 받아오지 못했음
- CodeDeploy 실행을 위한 shell 파일에서 원격 submodule 브랜치를 모두 업데이트 하고 main 브랜치를 pull 하도록 명령어 추가
```shell
git submodule update --remote --recursive
git add .
git commit -m "update submodules"
git pull origin main
```

<br>

### 개선 가능 사항
- LogBack 라이브러리 xml 파일이 아닌 java 설정으로 변경
- 웹 요청을 현재 사용중인 RestTemplate를 사용하고 있는데 많은 요청이 필요할 경우 비동기 처리가 되도록 AsyncRestTemplate로 변경 혹은 WebClient를 사용하여 구현으로 변경 (그러나 해당 프로젝트의 경우 24시간마다 한번 혹은 1시간마다 스케줄링을 통한 RestTemplate를 사용하기 때문에 현재는 변경할 필요가 없음)
- 현재 RestTemplate의 경우 메소드마다 새로운 RestTemplate 객체를 생성하고 있는데 성능 개선을 위해 싱글톤 패턴을 사용하여 생성으로 변경 (@Bean을 이용한 RestTemplate 설정 추가 및 싱글톤으로 적용 완료)
- 5분마다 스케줄링을 통해 '활성화'상태인 편지들을 모두 조회하여 하루가 지났는지 조회하는데 해당 기능에서 성능 개선 방법이 있는지
- Timer Class의 경우 많이 사용될 경우 이게 쓰레드를 많이 잡아먹거나 부하가 걸리지는 않을지? ()
    - 
- AWS S3에 저장하고 CodeDeploy를 통해 CI/CD 자동화 방법이 아닌 DockerHub에 저장하고 Docker를 통해 배포 변경