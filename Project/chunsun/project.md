# Chunsun 프로젝트

## 1. 프로젝트 소개
- '개발자 과외는 천선'의 줄임말로 개발자와 학생을 매칭해주는 코딩 과외 플랫폼입니다.
- SSAFY 12기의 팀프로젝트로, 하나의 EC2 서버에서 마이크로서비스 아키텍처(MSA)를 도입하여 서비스 간 결합도를 낮추고 확장성을 고려한 설계를 진행하였습니다.
- 저는 카카오 소셜 로그인과 JWT 인증/인가를 통한 회원 인증을 처리하며, SSE(Server-Sent-Events)를 활용해 과외 요청 및 결제 성공 여부를 실시간으로 전달하는 알림 기능을 구현했습니다.


### 1-1. 서비스 주요 기능

📑 회원 기능 (담당)
- 카카오 소셜 로그인 및 회원가입
- JWT 인증/인가 처리

📑 과외 기능
- 강사-학생 매칭 서비스
- 과외 신청 및 수락

📑 결제 기능
- 포트원을 통한 결제 시스템
- 쿠폰 발급 및 사용

📑 1:1 채팅방
- WebSocket을 활용한 실시간 채팅

📑 알림 기능 (담당)
- 과외 요청 및 성사, 결제 성공 실시간 SSE 알림

<br>

### 1-2. 서비스 아키텍처

![Service Architecture](./img/architecture.png)

- CI/CD 자동화: GitLab, Jenkins, Docker, AWS EC2를 활용한 CI/CD 배포 자동화를 구축하였습니다.
- MSA 기반 서비스 설계: SSAFY 교육기관에서 제공하는 메모리 16GB의 EC2 환경에서 Docker-Compose를 이용해 MSA (Microservices Architecture) 구조를 설계하였습니다. 이를 통해 서비스 간 결합도를 낮추고 확장성을 고려한 아키텍처를 구현하였습니다.
- Spring Cloud 기반 서비스 디스커버리: Spring Cloud, Eureka, Gateway, Config Discovery 등을 활용하여 서비스 간 통신을 효율적으로 관리하고, 동적인 서비스 등록 및 탐색이 가능하도록 설계하였습니다.

<br>

![local docker container](./img/docker-container.png)

- 로컬 환경에서의 도커 연결 동작 확인
- 설정, 유레카, 게이트웨이, 디스커버리, 인증, 유저, 채팅, 과외, 쿠폰, 결제, 알림, 랭킹, FE 서버 컨테이너 빌드
    - MongoDB, MySQL, Redis, Kafka 서버 빌드

<br>

### 1-3. 사용한 기술 스택

#### ✅ BE
- Java, SpringBoot, JPA, Jdbc, QueryDsl, Spring Cloud, Spring Security, Oauth2, WebSocket, SSE, Kafka

#### ✅ FE
- React, HTML/CSS/JavaScript, Zustand

#### ✅ DB
- MySQL, MongoDB, Redis

#### ✅ Infra
- Docker, AWS EC2, AWS S3, Jenkins, Nginx


<br>

## 2. 프로젝트 구현 사항

### 2-1. 하나의 EC2에서 MSA 아키텍처 구조 설계

- SSAFY 내의 하나의 EC2 서버에서 MSA 아키텍처를 구성하기 위해 여러개의 docker 컨테이너를 생성하였습니다.
- 컨테이너 생성을 통해 여러 서버를 구축하고 이를 통해 서버 가용성 및 추가 서버 생성 및 제거에 대한 확장성을 향상하였습니다.
- eureka, discovery, gateway, config 서버를 통해 다른 마이크로 서비스에 접근할 수 있도록 구현하였습니다. 각각의 기능은 다음과 같습니다.

<br>

#### eureka server
- <code>spring-cloud-starter-netflix-eureka-server</code> 의존성을 추가한 서버로 모든 마이크로 서비스가 자신을 등록하는 중앙 저장소
- 서비스 등록 및 검색 기능을 제공하여 Eureka 대시보드를 통해 등록된 서비스의 상태를 모니터링 할 수 있으며 동적 서비스 등록 및 검색이 용이하여 이후 서버 생성 및 제거 확장성 향상

#### discovery server
- <code>spring-cloud-starter-netflix-eureka-server</code> 라이브러리를 통해 각 마이크로서비스가 Eureka에 등록된 다른 서비스 정보를 조회하여 호출할 수 있도록 지원
- 각 마이크로 서비스는 <code>spring-cloud-starter-netflix-eureka-client</code> 의존성을 내장해 부팅 시 eureka 서버에 자신을 등록하고, 필요한 경우 등록된 서비스 목록을 조회
- 각 마이크로 서비스는 spring cloud 사용 연계를 위해 openfeign client를 사용하여 server to server 통신 진행 

#### gateway server
- <code>spring-cloud-starter-gateway</code> 라이브러리를 통한 API Gateway 역할을 수행
- 외부 클라이언트의 요청을 받아 내부의 여러 마이크로 서비스로 라우팅하는 단일 진입점 역할 수행
- 내부 서비스의 위치 정보는 eureka 서버(디스커버리)를 통해 동적으로 확인
- 유저 별 인증/인가 처리 처리 후 엔드포인트 서버로 로그인 정보 파싱하여 전송

#### config server
- <code>spring-cloud-config-server</code> 라이브러리를 통해 외부 설정을 중앙 설정을 통한 일괄 관리
- 각 마이크로서비스에서 <code>spring-cloud-starter-bus-amqp</code> 의존성을 추가하여 Config 서버에서 발생하는 설정 변경 이벤트를 RabbitMQ 브로커를 통해 전체 서비스에 전파하는 역할을 담당
    - 즉, Config 서버는 설정 변경 시(/actuator/bus-refresh) 이벤트를 생성하여 RabbitMQ 를 통해 전체 마이크로 서비스로 전파되고 설정 변경

<br>

### 2-2. 인증 서버 구현 및 게이트웨이 서버 인가 설정

![auth-login](./img/authserver-login.png)

![snowflake](./img/snowflake.png)

####  카카오 소셜 로그인 및 회원가입

- 브라우저에서 카카오 소셜 로그인 정보 동의 및 로그인을 통한 인가코드 발급 및 천선 서버로 로그인 요청
- 서버에서 인가코드를 이용해 카카오 인증서버에 요청하여 엑세스 토큰 발급
- 발급한 엑세스 토큰을 통해 카카오 서버로 회원가입에 필요한 정보 획득
    - 카카오ID, 이름, 닉네임, 이메일
- 획득한 정보를 기반으로 회원 여부 확인 및 처리
    - 서버 DB에 해당 카카오 ID가 있다면 로그인 진행
    - 없다면 회원가입이 필요하므로 브라우저로 사용자 정보 전송
    - 단일 요청에서 서로 다른 DTO를 반환해야 하므로, 서비스 인터페이스의 반환 타입을 인터페이스로 추상화
- 로그인 성공 시 천선 서버에서 AccessToken 및 Refresh Token 생성
    - Controller(표현 계층)에서 Util 클래스를 통해 AccessToken는 Header에 저장하고, RefreshToken은 Cookie에 HttpOnly 설정하여 저장
    - RefreshToken 로그인 정보 유효 확인을 위한 카카오 사용자 정보 객체를 Redis에 저장
- RefreshToken의 고유 식별자 생성
    - subject 값에 Snowflake 알고리즘 적용하여 중복되지 않는 ID 생성
    - Snowflake를 선택한 이유는 이후 인증 확장 시 고유한 식별이 가능하며, UUID(128비트)보다 64비트인 Snowflake가 Redis 탐색 성능에 유리할 것으로 판단
    - 그러나, 생성된 RefreshToken은 UUID와 비슷한 비트 크기를 가지지만, UUID에 비해 예측 가능성이 높아 보안적으로 부적절한 선택일 가능성이 있어 잘못된 판단이라고 생각..

<br>

![gateway1](./img/gateway1.png)

![gateway2](./img/gateway2.png)

![gateway filter](./img/gateway-filter.png)

#### 게이트웨이 서버 인가 설정

- Spring Security를 사용한 게이트웨이 서버 인증/인가 필터 적용
    - Spring Cloud Gateway에서 Spring Security를 연동하고 WebFilterChain을 통해 인증/인가를 제어
- JWT 처리에 있어 요청 엔드포인트 서버로 디코딩 로그인 사용자 정보 전송을 위해 <code>@Order</code> 어노테이션을 통한 우선 순위 설정
    - <code>@Order(1)</code>에 인증이 필요 없는 경로를 설정, <code>@Order(2)</code>에 인증이 필요한 경로를 설정한 부분이 이에 해당
    - 인증이 필요하지 않은 경로는 필터 적용하지 않고 통과
- 특정 경로 요청에 대한 사용자 인가 권한 확인을 위한 필터 구현
    - <code>JwtAuthenticationFilter</code>를 통해 JWT를 파싱하고, 사용자 정보를 추출
    - <code>ReactiveSecurityContextHolder</code>에 등록하여 Spring Security 컨텍스트에서 활용하여 권한 확인
    - 필터에서 로그인 사용자 ID를 헤더에 등록하여 엔드포인트 서버에서 편리하게 사용자 정보를 참조
- 역할(권한) 분리를 통한 접근 제어
    - 하나의 경로에 여러 권한이 접근 가능하므로 권한(Role)을 객체로 생성하고 권한 조합을 배열로 묶어서 중복을 줄이고 가독성을 높임

<br>

![kafka producer yml](./img/kafka-producer-yml.png)

![kafka consumer yml](./img/kafka-consumer-yml.png)

![sse emitoer](./img/sse-service.png)

![kafka consumer](./img/notification-kafka-consumer.png)

#### 알림 서버 구현

- Kafka를 통한 비동기 알림 처리
    - alarm 토픽에 메시지를 발행하여 알림 요청을 비동기로 분산 처리
    - 여러 마이크로서비스에서 비동기 방식으로 알림 요청을 처리하여 알림 서버 확장성 향상
    - Spring Kafka 수동 커밋 설정으로, 정상 처리 후에만 오프셋을 커밋하여 메시지 유실을 방지
- 알림 메시지 수신 및 저장
    - <code>@KafkaListener</code>로 알림 메시지(NotificationDto.RequestDto)를 수신
    - 알림 타입 유효 검증 후 MongoDB 등 저장소에 알림 이력 저장
- SSE를 통한 실시간 알림 전송
    - Server-Sent Events(SSE) 방식으로 실시간 푸시 알림 구현
    - 사용자별로 <code>SseEmitter</code>를 관리하여, 새 알림 발생 시 해당 사용자의 Emitter에 이벤트 전송
    - Emitter는 일정 시간 후 만료되어 메모리 누수 방지


<br>

## 3. 프로젝트 진행 중 이슈 사항

![Refresh Token Refresh](./img/change-role-jwt.png)

### 1. 유저 권한 변경이후 RefreshToken 재발급 시 문제 발생
- 로그인 성공 시 AccessToken과 RefreshToken을 생성
- 로그인 이후, RefreshToken을 Redis에 저장 ( key: RefreshToken, value: 사용자 일부 정보 객체 )
    - 재로그인 시 Redis에서 RefreshToken을 조회하여 AccessToken을 재발급함으로써 DB I/O를 줄이는 방식 사용
    - 화이트리스트/블랙리스트는 구현하지 않았지만, 기존 로그인 여부를 확인하여 AccessToken 재발급
- 그러나 AccessToken과 Redis value 모두 Role 정보가 포함되어 있으므로, 사용자의 권한이 변경되면 기존 JWT 정보가 더 이상 유효하지 않아 재발급 필요하다 생각하여 재발급
- 권한이 변경될 때 AccessToken과 RefreshToken을 재발급하는경우 무한 RefreshToken 발급이 가능해지는 문제 발생
- 이후 RefreshToken을 새로 생성할 필요는 없고 Redis Value에 Role만 새로 저장하고 AccessToken을 새로 생성하는 방식으로 변경

<br>

![Cors Configuration](./img/cors-configuration.png)

### 2. 게이트웨이 서버에서 CORS 문제발생

- 로컬에서는 문제가 있지 않았으나 EC2 환경 배포 이후 게이트웨이 서버 요청 시 CORS 문제 발생
- 해당 문제는 Spring Cloud는 Netty 기반 WebFlux 환경이므로 CorsFilter 가 아닌 CorsWebFilter 설정을 통해 CORS 허용 설정이 필요했음
- 또한 CorsWebFilter 에서 요청에 대한 경로 패턴 및 메서드와 노출 커스텀 헤더 등록하여 CORS 설정 등록


<br>

![Composite index](./img/composite-index.png)

### 3. 알림 조회에 대해 복합인덱스 적용
- 유저별로 많은 알림이 존재할 수 있고 읽지 않은 알림 최신순 이후 읽은 알림 최신순으로 정렬하기 위한 복합 인덱스 적용
- 각 사용자별 별도의 인덱스 그룹을 최신순으로 조회하기 위한 MongoDB 복합인덱스 설정

<br>

![coupone send to all user](./img/send-all-sse-event.png)

### 4. 새로운 선착순 쿠폰 생성시 전체 유저 알림
- 새로운 쿠폰이 발급이 가능해졌을 경우 모든 인원에게 알림 전송
- OpenFeign을 활용하여 유저 서버에서 전체 사용자 정보를 조회한 후, 멀티 스레드 방식으로 알림 전송
- 하지만 해당 구조는 모든 알림이 성공적으로 전송되었음이 보장되지 않음
    - Kafka를 이용하여 전송 실패 시 재시도 가능하도록 보장하려 했으나,
운영 중인 컨테이너가 많아 새로운 서버 추가가 어려워 해당 방식은 반려됨
    - 다만, 알림 서비스 특성상 모든 사용자가 반드시 받아야 하는 서비스는 아니라고 판단하여 해당 방식 유지

<br>

## 4. 화면 구성

<br>

### 1. 천선 메인 페이지

![main page](./img/main.png)

- 메인 페이지

<br>

### 2. 카카오 로그인

![sign-uo](./img/login.png)

- KAKAO 소셜 로그인/회원가입

<br>

### 3. 과외 매칭 서비스

![student description create](./img/student-description.png)

- 유저 설명조회

<br>

![chatting](./img/chatting.png)

- 문의 신청을 통한 1:1 채팅방

<br>

![notification](./img/notification.png)

- 과외 및 쿠폰 관련 알림 조회

<br>

![notification detail](./img/coupon-notification.png)

- 알림 상세 확인하기

<br>

### 4. 과외 비용 결제

![payment](./img/payment.png)

- 쿠폰 적용 및 총 과외 비용 결제

<br>

## 5. 협업 진행 방식

<br>

### 4-1. Jira 협업 툴 사용

![JIRA1](./img/jira1.png)

![JIRA2](./img/jira2.png)

- Jira 스프린트 진행

<br>

### 4-2. Excalidraw 와이어프레임

![wireframe](./img/wireframe.png)

- Excalidraw를 사용한 와이어프레임 작성

<br>

### 4-3. Notion 명세 및 문서화

![Notion1](./img/notion.png)

- 프로젝트 설계 및 구현 문서화
- [문서화 링크](https://faithful-medicine-736.notion.site/1a31e69284f780f98c8ffde7b596d4b3?v=1a31e69284f781cc8300000ca3ef7180&pvs=4)

<br>

![Notion2](./img/notion-SRS.png)

- 노션 1차 요구사항 명세서 작성

<br>

### 4-4. API 명세서 작성

![API Document](./img/api-document.png)

- API 명세서 노션으로 작성
- API 테스트는 PostMan 사용

<br>

### 4-4. ERD 설계

![ERD Cloud](./img/erd-design.png)

- RDB ERD 설계

<br>

### 4-5. GitLab MergeRequest

![Gitlab Gitflow](./img/gitlab.png)

- Jira 이슈 번호를 사용한 Gitflow 방식 협업 진행