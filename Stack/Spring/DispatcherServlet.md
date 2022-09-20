# DispatcherServlet

### Servlet
- 자바를 이용하여 동적인 웹 페이지를 사용하기 위한 서버 측 프로그램
- 클라이언트가 서버에 요청을 보낼 때와 응답을 받을 때 필요한 HTTP 작업을 도와주는 역할
    - 개발자가 HTTP를 직접 파싱할 필요를 없게 만들어 줌
    - HttpServeltRequest, HttpServletResponse를 통해 HTTP 통신에서 필요한 여러가지 작업을 하게됨

<br>

### 서블릿 컨테이너와 서블릿이 호출되는 과정
1. 클라이언트로부터 요청
2. Servlet Request / Servlet Response 객체 생성
3. 설정 파일을 참고하여 매핑할 Servlet을 확인
4. 해당 서블릿 인스턴스 존재의 유무를 확인하여 없으면 생성(init())
5. Servlet Container에 스레드를 생성하고, res와 req 를 인자로 service 실행
6. 해당 결과를 웹 서버에게 네트워크를 통해 전달
-> 서블릿 컨테이너는 결국 서블릿의 생명주기를 관리하는 객체

<br>

### 스프링 이전의 배경
- 모든 서블릿을 URL 매핑을 위해 web.xml에 모두 등록해주어야 했음
- HTTPServlet을 사용하기 위해 직접 HttpServlet을 상속받아서 사용해야만 했음
- 공통 작업을 개발자가 직접 처리해주어야 했음

<br>

### DispatcherServlet
- Spring MVC 프로젝트의 핵심으로 Front Controller 역할을 수행하여 모든 요청을 받아 해당 요청들의 공통처리 작업을 처리하고 세부 컨트롤러로 위임
    - FrontController : 서블릿 컨테이너 제일 앞단에서 서버로 오는 모든 요청을 받아 처리하는 컨트롤러
- Controller, ViewResolver, HandlerMapping 과 같은 스프링 빈(Beans)을 구성
- DispatcherServlet은 HttpServlet을 상속받아 사용하고 있음
    - DispatcherServlet -> FrameworkServlet -> HttpServletBean -> HttpServlet 상속 구조
- DispatcherServlet만 정의하면, DispatcherServlet에서 모든 요청을 받아 처리할 수 있음
- 모든 요청에 대해 공통 로직 처리로 중복 코드량 감소
- 웹 요청 처리 관련 구현체들을 사용할 수 있고, 우리가 개발할 때 집중해야되는 요청처리 로직에만 신경을 쓸 수 있도록 도와줌
- 스프링 컨테이너, 스프링 IoC를 이용하여 개발을 진행할 수 있게 해줌

<br>

![Spring MVC Flow](./img/spring_process.png)
### Spring MVC의 동작방식
1. 클라이언트의 요청을 디스패처 서블릿이 받음
2. 요청 정보를 통해 요청을 위임할 컨트롤러를 찾음
3. 요청을 컨트롤러로 위임할 핸들러 어댑터를 찾아서 전달함
4. 핸들러 어댑터가 컨트롤러로 요청을 위임함
5. 비지니스 로직을 처리함
6. 컨트롤러가 반환값을 반환함
7. HandlerAdapter가 반환값을 처리함
8. 서버의 응답을 클라이언트로 반환함

<br>

### Spring MVC구조에서의 역할
1. Dispatcher Servlet
- Front Controller를 담당
- 애플리케이션으로 들어오는 모든 Request를 받는 부분. Request 를 실제로 처리할 Controller에게 전달하고 그 결과 값을 받아서 View에 전달하여 적절한 응답을 생성할 수 있도록 흐름을 제어
2. HandlerMapping
- Request URI에 따라 각각 어떤 Handler(Controller method)가 실제로 처리할 것인지 찾아주는 역할
3. HandlerAdaptor
- 결정된 Controller의 메소드 중 요청에 맞는 적합한 핸들러 매칭
4. Controller
- Request를 직접 처리한 후 그 결과를 다시 DispatcherServlet에 돌려주는 역할
5. ModelAndView
- Controller가 처리한 결과와 그 결과를 보여줄 View에 관한 정보를 담고 있는 객체
6. ViewResolver
- View 관련 정보를 갖고 실제 View를 찾아주는 역할
7. View
- Controller가 처리한 결과값을 보여줄 View를 생성
```
스프링 MVC는 웹 요청을 실제로 처리하는 객체를 핸들러(Handler)라고 표현하고 있음
- @Controller 적용 객체나 Controller 인터페이스를 구현한 객체 모두 스프링 MVC입장에서는 핸들러가됨
- 특정 요청 경로를 처리해주는 핸들러를 찾아주는 객체를 HandlerMapping이라 부름
```

<br>

<div style="text-align: right">22-09-18</div>

-------

## Reference
- https://velog.io/@seculoper235/2.-DispatcherServlet-이란
- [10분 테코톡-코기의 Servlet vs Spring](https://www.youtube.com/watch?v=calGCwG_B4Y)
- https://mangkyu.tistory.com/18