# HTTP Header

### HTTP Header
- HTTP 전송에 필요한 모든 부가 정보
- 표준 헤더가 너무 많음
- 필요시 임의의 헤더 추가 가능
- 메시지 바디의 내용, 메시지 바디의 크기, 압축, 인증, 요청클라이언트, 서버정보, 캐시 관리 정보.. 등의 부가 정보

<br>

### Header 분류 - RFC2616(과거)
- General : 메시지 전체에 적용되는 정보
- Request : 요청 정보
- Response : 응답 정보
- Entity : 엔티티 바디 정보

#### HTTP BODY
- 메시지 본문(message body)은 엔티티 본문(entity body)을 전달하는데 사용
- 엔티티 본문은 요청이나 응답에서 전달할 실제 데이터
- 엔티티 헤더는 엔티티 본문의 데이터를 해석할 수 있는 정보 제공
    - 데이터 유형(html, json), 데이터 길이, 압축 정보 등등

<br>

### Header 분류 - RFC7230~7235
- 엔티티(Entity) -> 표현(Representation)
- Representation = representation Metadata + Representation Data
- 표현 = 표현 메타데이터 + 표현 데이터

#### HTTP Body
- 메시지 본문(message body)를 통해 표현 데이터 전달
- 메시지 본문 = 페이로드(payload)
- 표현은 요청이나 응답에서 전달할 실제 데이터
- 표현 헤더는 표현 데이터를 해석할 수 있는 정보 제공
    - 데이터 유형(html,json), 데이터 길이, 압축 정보 등등

<br>

### 표현
- Content-Type : 표현 데이터의 형식
    - 미디어 타입, 문자 인코딩
- Content-Encoding : 표현 데이터의 압축 형식
    - 표현 데이터를 압축하기 위해 사용
    - 데이터를 전달하는 곳에서 압축 후 인코딩 헤더 추가
    - 데이터를 읽는 쪽에서 인코딩 헤더의 정보로 압축 해제
- Content-Language : 표현 데이터의 자연 언어
    - 표현 데이터의 자연 언어를 표현
- Content-Length : 표현 데이터의 길이
    - 바이트 단위, Transfer-Encoding을 사용하면 Content-Length를 사용하면 안됨
- 표현 헤더는 전송, 응답 둘다 사용

<br>

### 협상 (Negotiation, 콘텐츠 네고시에이션)
- 클라이언트가 선호하는 표현 요청
- Accept : 클라이언트가 선호하는 미디어 타입 전달
- Accept-Charset : 클라이언트가 선호하는 문자 인코딩
- Accept-Encoding : 클라이언트가 선호하는 압축 인코딩
- Accpet-Language : 클아이언트가 선호하는 자연 언어
- 협상 헤더는 요청시에만 사용

#### 협상과 우선순위
- Quality Values(q) 사용
- 0~1 클수록 높은 우선 순위
    - Accept-Language : ko-KR;q=0.9,en-US;q=0.8,en;
- 생략하면 1
- 구체적인 것이 우선
    - Accept: text/*;q=0.3, text/html;q=0.7, text/html;level=1, text/html;level2;q=0.4, */\*;q=0.5

    |Media Type|Quality|
    |:---:|:---:|
    |text/html;level=1|1|
    |text/html|0.7|
    |text/plain|0.3|
    |image/jpeg|0.5|
    |text/html;level=2|0.4|
    |text/html;level=3|0.7|

<br>

### 전송 방식
- #### 단순 전송
    - Content-Length 한번에 요청하고 한번에 응답 받음
- #### 압축 전송
    - Content-Encoding 압축 형식을 작성해서 압축 전송
- #### 분할 전송
    - Transfer-Encoding 만들어질 때마다 클라이언트에게 분할해서 전송
- #### 범위 전송
    - Range, Content-Range 특정 범위를 지정해서 전송

<br>

### HTTP Header 용어
- #### Host
    - 요청한 호스트 정보 (도메인)
    - 요청에서 사용하며 필수
    - 하나의 서버가 여러 도메인을 처리해야 할 때
    - 하나의 IP 주소에 여러 도메인이 적용되어 있을 때
- #### Location
    - 페이지 리다이렉션
    - 웹 브라우저는 3xx 응답의 결과에 Location 헤더가 있으면 Location 위치로 자동 이동 (리다이렉트)
    - 201 : Location 값은 요청에 의해 생성된 리소스 URI
- #### Allow
    - 허용 가능한 HTTP 메서드
    - 405 에서 응답을 포함해야함, 많이 구현하지는 않음
    - Allow: GET, HEAD, PUT
- #### Retry-After
    - 유저 에이전트가 다음요청을 하기까지 기다려야 하는 시간
    - 503 : 서비스가 언제까지 불능인지 알려줄 수 있음
    - Retry-After : 날짜표기 혹은 초단위 표기
- #### From
    - 유저 에이전트의 이메일 정보
    - 일반적으로 잘 사용하지 않음
- #### Referer
    - 현재 요청된 페이지의 이전 웹 페이지 주소
    - A -> B로 이동하는 경우 B를 요청할 때 Referer:A 를 포함해서 요청
    - Referer를 사용해서 유입 경로 분석 가능
    - 요청에서 사용
- #### User-Agent
    - 클라이언트의 애플리케이션 정보 ( 웹 브라우저 정보 등등)
    - 통계 정보
    - 어떤 종류의 브라우저에서 장애가 발생하는지 파악 가능
    - 요청에서 사용
- #### Server
    - 요청을 처리하는 ORIGIN 서버의 소프트웨어 정보
    - server: nginx
    - 응답에서 사용
- #### Date
    - 메시지가 발생한 날짜와 시간
    - 응답에서 사용

<br>

### 인증 헤더
- #### Authorization
    - 클라이언트 인증 정보를 서버에 전달
- #### WWW-Authenticate
    - 리소스 접근시 필요한 인증 방법 정의
    - 401 응답과 함께 사용

<br>

### 쿠키
- 사용처
    - 사용자 로그인 세션 관리
    - 광고 정보 트레킹
- 쿠키 정보는 항상 서버에 전송됨
    - 네트워크 트래픽 추가 유발
    - 최소한의 정보만 사용(세션id, 인증토큰)
    - 서버에 전송하지 않고, 웹 브라우저 내부에 데이터를 저장하고 싶으면 웹 스토리지(localStorage, sessionStorage)참고
    - 보안에 민감한 데이터는 저장하면 안됨
- #### Set-Cookie 
    - 서버에서 클라이언트로 쿠키 전달 (응답)
- #### Cookie
    - 클라이언트가 서버에서 받은 쿠키를 저장하고, HTTP 요청시 서버로 전달

#### 쿠키를 사용하는 이유
- HTTP는 무상태(Stateless) 프로토콜
- 클라이언트와 서버가 요청과 응답을 주고 받으면 연결이 끊어짐
- 클라이언트가 다시 요청하면 서버는 이전 요청을 기억하지 못함
- 클라이언트와 서버는 서로 상태를 유지하지 않음
- 웹 브라우저 쿠키 저장소를 이용하여 로그인정보같은 필요한 정보를 가지고 있음
- 모든 요청에 쿠키저장소에 있는 쿠키 정보를 자동으로 포함함

<br>

### 쿠키 정보
- set-cookie: sessionId=abcde1234; expires=Sat, 26-Dec-2020 00:00:00; path=/; domain=.google.com;Secure

<br>

- #### Expires
    - 만료일이 되면 쿠키 삭제
- #### Set-Cookie: max-age=3600
    - 0이나 음수를 지정하면 쿠키 삭제
- 세션 쿠키 : 만료 날짜를 생략하면 브라우저 종료시 까지만 유지
- 영속 쿠키 : 만료 날짜를 입력하면 해당 날짜까지 유지
- #### Domain
    - 명시 : 명시한 문서 기준 도메인 + 서브 도메인 포함
    - 생략 : 현재 문서 기준 도메인만 적용
- #### Path
    - 이 경로를 포함한 하위 경로 페이지만 쿠키 접근
    - 일반적으로 path=/ 루트로 지정
- #### 보안(Secure, HttpOnly, SameSite)
- Secure
    - 쿠키는 http, https를 구분하지 않고 전송
    - Secure를 적용하면 https인 경우에만 전송
- HttpOnly
    - XSS 공격 방지
    - 자바스크립트에서 접근 불가(document.cookie)
    - HTTP 전송에만 사용
- SameSite
    - XSRF 공격 방지
    - 요청 도메인과 쿠키에 설정된 도메인이 같은 경우만 쿠키 전송