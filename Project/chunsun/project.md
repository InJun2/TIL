# Chunsun 프로젝트

## 1. 프로젝트 소개
- '개발자 과외는 천선'의 줄임말로 개발자와 학생을 매칭해주는 코딩 과외 플랫폼입니다.
- SSAFY 12기의 팀프로젝트로, 하나의 EC2 서버에서 마이크로서비스 아키텍처(MSA)를 도입하여 서비스 간 결합도를 낮추고 확장성을 고려한 설계를 진행하였습니다.
- 저는 카카오 소셜 로그인과 JWT 인증/인가를 통한 회원 인증을 처리하며, SSE(Server-Sent-Events)를 활용해 과외 요청 및 결제 성공 여부를 실시간으로 전달하는 알림 기능을 구현했습니다.


### 1-1. 서비스 주요 기능

📑 회원 기능 (담당)
- 카카오 소셜 로그인 및 회원가입
- JWT 인증/인가 처리

📑 과외 기능
- 강사-학생 매칭 서비스
- 과외 신청 및 수락락

📑 결제 기능
- 포트원을 통한 결제 시스템
- 쿠폰 발급 및 사용

📑 1:1 채팅방
- WebSocket을 활용한 실시간 채팅

📑 알림 기능
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

## 2. 프로젝트 진행 사항

### 2-1. 프로젝트 적용 기술

- Short URL를 `ID` 20000 부터 시작하여 `base62` 인코딩을 통한 Short URL 을 반환하였습니다.
- `Redis` TTL 사용하여 클라이언트별 1분간 10회 요청 제한을 진행하였습니다.
    - `Redis UUID` Key 사용
    - `AOP` Annotation을 사용한 요청 제한
    - 10회라는 적은 횟수 제한으로 `스핀락`으로 구현
- `Swagger 3.0`을 사용하여 문서화를 진행하였습니다.

### 2-2. 프로젝트 도전 사항

1. 빠른 작업 속도를 위해 Shortcode 생성과 캐시 저장 과정을 멀티스레드를 이용해서 기능을 최적화하였습니다.
    - origin url 을 중복 검사 없이 DB에 저장하여 저장된 Index를 Base62(숫자와 영문자)로 인코딩하여 Shortcode를 생성
    - 생성하면서 멀티스레드를 적용하여 새로운 스레드가 Redis에 Key(Shortcode) - Value(origin url) 형식으로 저장, 일정 시간 동안 유지

<br>

2. 중복되는 검사를 빠르게 처리하기 위해 캐시의 원리를 이용하여 프로젝트에 적용하여 중복 검색은 시간복잡도 O(1)로 성능 최적화하였습니다.
- 1차로 Redis에서 요청 받은 Shortcode를 key 값으로 검색
    캐시히트 : 검색된 value(origin url)을 리턴
    캐시미스 : DB에 조회하여 origin url을 리턴

<br>

3. System Action Log 기록하는 기능 구현을 위해, HttpServeletRequest에 있는 Header 데이터를 기록하여 접근기록을 보며 데이터를 확인 후 저장하였습니다.
    - HttpSelveletReqeust 에서 원하는 Header 값을 필터링하는 HeaderUtil 구현하여 저장

<br>

4. 동적 쿼리를 적용하여 복잡한 쿼리 조건을 충족하였습니다.
    - 회원가입을 진행하면서, HttpServeletRequest의 Header의 "client-id"(uuid)로 DB에 조회함. 이후 동일한 uuid가 저장된 Shortcode를 조회하여 생성된 MemberId를 Update함
    - uuid가 사용자마다 다르기 때문에 QueryDsl을 사용한 동적쿼리를 사용하여 기능 구현
    - uuid가 생성한 Shortcode가 대량이 존재하여 전체 조회가 발생하면 서버의 부화가 발생할 수 있으므로 1000개씩 청크를 나누어 조회 후 memberId 업데이트 진행

<br>

5. 단축 URL 6개월 마다 삭제에서, Batch 작업을 적용하여 일정 기능을 STEP 별로 기능 자동화를 적용하였습니다.
    - 작업을 batch를 사용, 6개월이 지난 DATA를 매일 자정 12시에 Scheduler를 구현
    - 삭제 해야하는 data가 대용량으로 존재할시, 서버의 부하로 리퀘스트에 지장을 줄 수 있기 때문에, 1000건씩 청크를 나누어 삭제 진행

---

## 3. 화면 구성

<br>

### 3-1. 천선 메인 페이지

![main page](./img/main.png)

- 메인 페이지

<br>

### 3-2. 카카오 로그인

![sign-uo](./img/login.png)

- KAKAO 소셜 로그인/회원가입

<br>

### 3-3. 과외 매칭 서비스

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

### 3-4. 과외 비용 결제

![payment](./img/payment.png)

- 쿠폰 적용 및 총 과외 비용 결제

<br>

## 4. 협업 진행행

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