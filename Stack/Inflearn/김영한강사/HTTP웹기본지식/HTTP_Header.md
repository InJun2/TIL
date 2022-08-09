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

<br>

### 캐시 기본 동작
- 서버에 요청을 보내면 응답으로 캐시 저장 시간을 받아옴 -> 해당 시간 동안 캐시에 저장되고 시간안에 다시 요청시 캐시에서 데이터를 가져옴 (서버경유하지않아도됨)
- 캐시 시간이 초과하면 서버를 통해 데이터를 다시 조회하고, 캐시를 갱신 -> 이때 다시 네트워크 다운로드가 발생
- 캐시 덕분에 캐시 가능 시간동안 네트워크를 사용하지 않아도 됨
- 비싼 네트워크 사용량을 줄일 수 있음
- 브라우저 로딩 속도가 매우 빠름
- 빠른 사용자 경험
#### 캐시 시간 초과
- 캐시 휴효 시간이 초과해서 서버에 다시 요청하면 다음 두 가지 상황이 나타남
    - 서버에서 기존 데이터를 변경
    - 서버에서 기존 데이터를 변경하지 않음

<br>

### 검증 헤더
#### 검증헤더
- 캐시 데이터와 서버 데이터가 같은지 검증하는 데이터
- Last-Modified, ETag
- Last-Modified는 마지막 수정된 날짜를 이용하여 검증, ETag는 캐시용 데이터에 임의의 고유한 버전 이름을 달아두고 같은지 검증
#### 조건부 요청 헤더
- 검증 헤더로 조건에 따른 분기
- If-Modified-Since, If-Unmodified-Since : Last-Modified 사용
- If-None-Match, If-Match : ETag 사용
- 조건이 만족하면 200 OK -> 데이터가 변경되어 모든 데이터 전송( body 포함 )
- 조건이 만족하지 않으면 304 Not Modified

#### 검증헤더 사용 시
- 캐시 만료후에도 서버에서 데이터를 변경하지 않음
- 생각해보면 데이터를 전송하는 대신에 저장해 두었던 캐시를 재사용 할 수 있음
- 단 클라이언트의 데이터와 서버의 데이터가 같다는 사실을 확일할 수 있는 방법 필요

<br>

### 1. 검증 헤더 추가 (Last-Modified)
1. 첫 번째 요청
    - 서버 요청을 보내면 위와 동일하게 캐시 저장 시간을 포함하고 거기에 Last-Modified도 포함하여 응답 데이터를 받아옴
    - Last-Modified를 이용하여 데이터가 최종 수정 시간 받아옴
2. 두 번째 요청
    - 서버로 요청을 보냈을 때 첫 번째 요청에서 받은 Last-Modified를 가지고 있는 if-modified-since를 요청에 담아 보내서 데이터 수정이 있었는지 확인
    - 수정이 되지 않았다면 304 Not Modified 라는 응답코드를 통해 변하지 않았음을 클라이언트에 알려주고 해당 응답에는 body가 존재하지 않는다 (수정된 것이 없기 때문에) -> 네트워크 부하 감소
    - 응답 결과를 재사용해서 브라우저 캐시에서 응답결과를 재사용하여 헤더 데이터 갱신하고 해당 캐시에서 데이터 가져옴

#### 검증헤더와 조건부 요청 중 Last Modified 요청
- 1초 미만 (0.x초) 단위로 캐시 조정이 불가능
- 날짜 기반의 로직 사용
- 데이터를 수정해서 날짜가 다르지만, 데이터를 수정해서 결과가 똑같은 경우
- 서버에서 별도의 캐시 로직을 관리하고 싶은 경우
<br>

### 2. 검증 헤더 추가 (ETag)
1. 첫 번째 요청
    - 서버 요청을 보내면 ETag를 응답받음.
    - ETag를 캐시에 저장
2. 두 번째 요청
    - If-None-Match 에 ETag명을 요청으로 포함하여 전송
    - ETag가 그대로면 304 응답코드를 보내 변함이 없고 body가 없이 응답. 이후 Last-Modified와 동일하게 동작

#### 검증헤더와 조건부 요청 중 ETag 요청
- 진짜 단순하게 ETag만 서버에 보내서 같으면 유지, 다르면 다시 받기
- 캐시 제어 로직을 서버에서 완전히 관리
- 클라이언트는 단순히 이 값을 서버에 제공 ( 클라이언트는 캐시 메커니즘을 모름 )

<br>

### 캐시 제어 헤더
- Cache-Control : 캐시 제어
    - max-age
    - 캐시 유효 시간, 초 단위
- Pragma : 캐시 제어 (하위호환)
    - no-cache
    - 데이터는 캐시해도 되지만, 항상 기본(origin) 서버에 검증하고 사용
    - HTTP 1.0 하위호환으로 지금은 거의 사용하지 않음
- Expires : 캐시 유효 기간(하위호환)
    - no-store
    - 데이터에 민감한 정보가 있으므로 저장하면 안됨 ( 메모리에서 사용하고 최대한 빨리 삭제 )
    - 캐시 만료일을 정확한 날짜로 지정
    - HTTP 1.0 부터 사용
    - 지금은 더 유연한 Cache-Control : max-age 를 권장

<br>

#### Cache-Control 캐시 지시어(directives) - 기타
- Cache-Control : public 
    - 응답이 public 캐시에 저장되어도 됨
- Cache-Control : private
    - 응답이 해당 사용자만을 위한 것, private 캐시에 저장해야 함 (기본값)
- Cache-Control : s-maxage
    - 프록시 캐시에만 적용되는 max-age
- Age : 60 (HTTP 헤어)
    - 오리진 서버에서 응답 후 프록시 캐시 내에 머문 시간 (1초)
#### 프록시 캐시
- Cloud-Front 방식으로 public 캐시로 처리
- 예를 들면 해외 기본 서버에서 받아오면 0.5초가 걸린다면, 자주 사용되는 요청을 우리나라에 프록시 캐시를 만들어 해당 해외 서버의 응답을 프록시 캐시에 넣어놓고 클라이언트가 요청시 해당 프록시 캐시에서 받아오는 방식

<br>

### 캐시 무효화
- Cache-Control : no-cache, no-store, must-revalidate
- Pragma : no-cache

#### Cache-Control 캐시 지시어 - 확실한 캐시 무효화
- Cache-Control : no-cache
    - 데이터는 캐시해도 되지만, 항상 기본 서버에 검증하고 사용
- Cache-Control : no-store
    - 데이터에 민감한 정보가 있으므로 저장하면 안됨
- Cache-Control : must-revalidate
    - 캐시 만료후 최초 조회시 기본 서버에 검증해야함
    - 기본 서버 접근 실패시 반드시 오류가 발생해야함 - 504(Gateway Timeout)
    - must-revalidate는 캐시 유효 시간이라면 캐시를 사용함
- Pragma : no-cache