# Web Server vs Web Application Server?

<br>

## Web Server (WS)
- 클라이언트로부터 HTTP 요청을 받아 정적인 웹 페이지나 파일을 제공함
  - 주로 정적인 콘텐츠를 제공하여 클라이언트와 통신하므로 멱등성 존재
- 대표적인 웹 서버로는 Apache HTTP Server, Nginx, Microsoft IIS 등이 존재
- 가상 호스팅(Virtual Hosting)을 통해 하나의 IP 주소에서 여러 개의 도메인을 처리할 수 있습니다.

<br>

## Web Application Server (WAS)
- 웹 애플리케이션 서버는 웹 서버의 기능을 확장하여 동적인 콘텐츠를 생성하고 실행하기 위한 환경 제공
  - 동적인 데이터를 제공하여 다양한 언어와 기술을 지원함
  - 웹 서버의 기능을 확장하여 서블릿, JSP, EJB 등의 코드를 실행하고 관리
- 대표적인 AWS로는 Apache Tomcat, Red Hat JBoss, IBM WebSphere, Oracle WebLogic 등이 존재

<br>

### 구조
- WS는 정적인 콘텐츠를 사용자에게 제공하고 WAS는 동적인 콘텐츠를 사용자에게 제공
- WS는 일반적 하나의 ip를 가지고 WAS는 하나의 포트에서 동작함
- PORT에서 동작하는 WAS 안의 각각의 웹 애플리케이션은 하나하나 독립적인 실행환경을 가지며 컨테이너(Container)라고 부름
- 컨테이너 안의 애플리케이션을 식별하는 데 사용되는 경로(path)나 이름을 Api를 컨텍스트(Context)라고 부름
  - 톰캣 안에 설치되는 API => 컨텍스트

<br>

### 추가
- 주로 사용하는 스프링의 경우 내장 톰캣이 존재하며 IDE에서 바로 WAS를 만드는 환경 제공이 가능
