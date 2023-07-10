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
    - webflux
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
- 

<br>

### 1시간 마다 서버 상태를 Slack으로 메시지 전송

<br>



<br>

### 5분마다 편지 엔티티의 생성시간에 따른 상태 변경

<br>

### 편지 답변시 편지의 유효시간을 30분으로 지정

<br>

### 편지 공유 기능 구현

<br>

### GitAction을 사용하여 AWS S3에 저장하고 AWS CodeDeploy를 통한 CI/CD 자동화


<br>

### 프로젝트 중 에러 Issue

<br>

### 개선 가능 사항
- LogBack 라이브러리 xml 파일이 아닌 java 설정으로 변경
- 웹 요청 방법을 현재 사용중인 RestTemplate 비동기 처리가 되도록 변경 혹은 WebClient를 사용하여 구현으로 변경
- AWS S3에 저장하고 CodeDeploy를 통해 CI/CD 자동화 방법이 아닌 DockerHub에 저장하고 Docker를 통해 배포