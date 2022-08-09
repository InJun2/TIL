# Spring MVC

### Spinrg MVC?
- Spring에서 제공하는 웹 모듈로 Model, View, Controller 세가지 구성 요소를 사용
- MVC를 사용해 사용자의 다양한 HTTP Request를 처리하고 단순한 텍스트 형식의 응답부터 REST 형식의 응답은 물론 View를 표시하는 html을 return하는 응답까지 다양한 응답을 할 수 있도록하는 프레임워크
- Spring MVC는 다양한 요청을 처리하고 응답하기 위해 주요 구성요소들을 만들어놓고 구성요소들을 확장할 수 있게 만들어 놓음

<br>

### MVC?
>- #### Model
>   - 데이터와 비즈니스 로직을 처리/관리
>   - 애플리케이션이 포함해야할 데이터가 무엇인지 정의
>   - 일반적으로 POJO로 구성
>   - DAO, DTO, Service 등
>- #### View
>   - 비즈니스 로직의 처리 결과를 통해 사용자 인터페이스가 표현되는 구간
>   - 애플리케이션의 데이터를 보여주는 방식을 정의 -> Rest API로 구현되면 json 응답으로 구성
>   - JSP, Thymeleaf, Grooby 등의 Template Engine, json data
>- #### Controller
>   - View와 Model 사이의 인터페이스 역할
>   - 애플리케이션 사용자 입력에 대한 응답으로 Model 및 View를 업데이트 하는 로직을 포함
>   - Model/View에 대한 사용자 입력 정보를 수신하여 결과를 Model에 담아 View에 전달
>   - Model Object와 Model을 화면에 출력할 View Name을 반환 -> REST Controller의 경우 해당 Model Obejct를 반환

<br>

### Spring MVC의 구조
- #### DispatcherServlet (Front Controller)
    - HTTP Request를 요청이오면 제일 먼저 받아온 후 해당 요청을 처리할 컨트롤러를 HandlerMapping에 검색요청 후 정해진 컨트롤러로 지정 (Super Controller 역할)
    - 모든 연결을 담당
    - 앞쪽에서 처리하는 컨트롤러를 두는 패턴을 사용하는 Front Controller 패턴
- #### HandlerMapping
    - 클라이언트의 요청 경로를 이용해서 이를 처리할 컨트롤러 빈 객체를 DispatcherSerlvet에 전달
- #### HandlerAdapter
    - 컨트롤러의 알맞은 메소드를 호출하여 요청을 처리
    - Controller - Service - DAO - DB를 거쳐 결과를 ModelAndView 객체로 변환 후 DispatcherServlet에 리턴
- #### ModelAndView
    - 컨트롤러가 리턴한 뷰 이름을 담고 있음
    - 처리한 결과로 ViewResolver에서 View를 정하는데 사용
- #### ViewResolver
    - DispatcherServlet에서 ModelAndView를 통해 응답 결과를 보여줄 뷰를 찾거나 생성해서 리턴
    - 이후 DispatcherServlet이 ViewResolver가 리턴한 View 객체에 응답 결과 생성을 요청

<br>

###  [Spring Process 정리 링크](./Spring-Process.md)

<br>

<div style="text-align: right">22-07-21</div>

-------

## Reference
- https://velog.io/@solchan/Spring-Spring-MVC란-무엇인가
- https://u0hun.tistory.com/10?category=1015356