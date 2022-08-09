### HTTP vs HTTPS

### HTTP
- Hyper Text Transfer Protocol으로 서버/클라이언트 모델을 따라 데이터를 주고 받기 위한 프로토콜
- 인터넷에서 하이퍼텍스트를 교환하기 위한 통신 규약으로, 주로 80번 포트를 사용하여 HTTP 서버가 80번 포트에서 요청을 기다리고 있으며 클라이언트는 80번 포트로 요청을 보냄
- HTTP는 애플리케이션 레벨의 프로토콜로 TCP/IP 위에서 작동. HTTP는 상태를 가지고 있지 않은 Stateless 프로토콜이며 Mthod, Path, Version, Headers, Body 등으로 구성됨
- HTTP는 암호화가 되지 않은 평문 데이터를 전송하는 프토콜이었기 때문에 HTTP로 비밀번호나 주민번호 등을 주고 받으면 제3자가 정보를 조회할 수 있었음. 이러한 문제를 해결하기 위해 HTTPS 등장
- 상세 정리 내용 링크
    - [IP-TCP-UDP-HTTP](./IP-TCP-UDP-HTTP.md)
    - [Inflrean-HTTP](../../Stack/Inflearn/%EA%B9%80%EC%98%81%ED%95%9C%EA%B0%95%EC%82%AC/HTTP%EC%9B%B9%EA%B8%B0%EB%B3%B8%EC%A7%80%EC%8B%9D/HTTP.md)
    - [Inflrean-HTTP Header](../../Stack/Inflearn/%EA%B9%80%EC%98%81%ED%95%9C%EA%B0%95%EC%82%AC/HTTP%EC%9B%B9%EA%B8%B0%EB%B3%B8%EC%A7%80%EC%8B%9D/HTTP_Header.md)
    - [Inflrean-HTTP Method](../../Stack/Inflearn/%EA%B9%80%EC%98%81%ED%95%9C%EA%B0%95%EC%82%AC/HTTP%EC%9B%B9%EA%B8%B0%EB%B3%B8%EC%A7%80%EC%8B%9D/HTTP_Method.md)

<br>

### HTTPS
- Hyper Text Transsfer Protocol Secure 으로 HyperText Transfer Protocol over Secure Socket Layer, HTTP over TLS, HTTP over SSL, HTTP Secure 등으로 불리는 HTTPS는 HTTP에 암호화가 추가된 프로토콜
- HTTPS는 HTTP와 다르게 443번 포트를 사용하며 네트워크 상에서 중간에 제3자가 정보를 볼 수 없도록 암호화를 지원
- 소켓 통신에서 일반 텍스트를 이용하는 대신 SSL이나 TLS 프로토콜을 통해 세션 데이터를 암호화함
- Google의 경우 2014년에 HTTPS를 순위 신호로 사용하여 먼저 검색됨
- 암호화/복호화가 진행해 HTTP보다 속도가 조금 느리지만 현재는 차이를 거의 느끼지 못할 정도임
- 인증서를 발급하고 유지하기 위한 추가 비용이 발생함
- HTTPS는 대칭키 암호화 방식과 비대칭키 암호화 방식을 모두 사용하고 있음 -> 빠른 연산 속도와 안정성을 모두 얻음
    - 대칭키 암호화 : 클라이언트와 서버가 동일한 키를 사용해 암호화/복호화 진행. 키가 노출되면 매우 위험하지만 연산속도가 빠름
    - 비대칭키 암호화 : 1개의 쌍으로 구성된 공개키와 개인키를 암호화/복호화 하는데 사용. 키가 노출되어도 비교적 안전하지만 연산 속도가 느림

<br>

#### HTTPS의 동작 과정
- HTTPS 연결과정(Hand-Shacking)에서는 먼저 서버와 클라이언트 간에 세션키를 교환
    - 처음 연결을 성립하여 안전하게 세션키를 공유하는 과정에서 비대칭키가 사용
    - 세션키는 주고 받는 데이터를 암호화하기 위해 사용되는 대칭키이며 데이터 간의 교환에는 빠른 연산속도가 필요하므로 세션키는 대칭키로 만들어짐
    - 연결 이후에 데이터를 교환하는 과정에서 빠른 연산속도를 위해 대칭키가 사용되는 것

<br>

1. 클라이언트(브라우저)가 서버로 최초 연결을 시도
2. 서버는 공개키(인증서)를 브라우저에게 넘겨줌
3. 브라우저는 인증서의 유효성을 검사하고 세션키를 발급함
4. 브라우저는 세션키를 보관하며 추가로 서버의 공개키로 세션키를 암호화하여 서버로 전송
5. 서버는 개인키로 암호화된 세션키를 복호화하여 세션키를 얻음
6. 클라이언트와 서버는 동일한 세션키를 공유하므로 데이터 전달할 때 세션키로 암호화/복호화를 진행함

<br>

#### HTTPS 발급 과정
- 위에서 살펴봐야 하는 부분은 서버가 비대칭키를 발급받는 과정 
- 서버는 클라이언트와 세션키를 공유하기 위한 공개키를 생성해야 하는데 일반적으로 인증된 기관(Certificate Authority)에 공개키를 전송하여 인증서를 발급 받음

<br>

1. A기업은 HTTP 기반의 애플리케이션에 HTTPS를 적용하기 위해 공개키/개인키를 발급함
2. CA 기업에게 돈을 지불하고, 공개키를 저장하는 인증서의 발급을 요청함
3. CA 기업은 CA 기업의 이름, 서버의 공개키, 서버의 정보 등을 기반으로 인증서를 생성하고, CA 기업의 개인키로 복호화하여 A 기업에게 이를 제공
4. A기업은 클라이언트에게 암호화된 인증서를 제공
5. 브라우저는 CA 기업의 공개키를 미리 다운받아 갖고 있어 암호화된 인증서를 복호화함
6. 암호화된 인증서를 복호화하여 얻은 A 기업의 공개키로 세션키를 공유

<br>

<div style="text-align: right">22-08-09</div>

-------

## Reference
- https://mangkyu.tistory.com/98
- https://hyeran-story.tistory.com/159