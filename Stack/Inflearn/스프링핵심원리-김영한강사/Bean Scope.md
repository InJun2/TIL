# Bean Scope

### 빈 스코프란?
- 빈이 스프링 컨테이너에서 존재할 수 있는 범위를 의미

### 스코프 지원
#### 싱글톤
- 기본 스코프, 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프
#### 프로토 타입
- 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는 매우 짧은 범위의 스코프
#### 웹 관련 스코프
    - request : 웹 요청이 들어오고 나갈때 까지 유지되는 스코프
    - session : 웹 세션이 생성되고 종료될 때 까지 유지되는 스코프
    - application : 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프

<br>

### 싱글톤 스코프
- 싱글톤 스코프의 빈을 조회하면 스프링 컨테이너는 항상 같은 인스턴스의 스프링 빈을 반환
- 싱글톤 빈은 스프링 컨테이너 생성 시점에 초기화 메서드가 실행
- 싱글톤 빈은 스프링 컨테이너가 관리하기 때문에 스프링 컨테이너가 종료될 때 빈의 종료 메서드 실행

```java
@Test
    void singletonBeanFInd(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        assertThat(singletonBean1).isSameAs(singletonBean2);

        ac.close();
    }

    @Scope("singleton")
    static class SingletonBean{
        @PostConstruct
        public void init(){
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("SingletonBean.destroy");
        }
    }
```

<br>

### 프로토타입 스코프
- 프로토타입 스코프는 스프링 컨테이너에 조회하면 스프링 컨테이너는 항상 새로운 인스턴스를 생성해서 반환
- 프로토타입 스코프 빈은 스프링 컨테이너에서 빈을 조회할 때 생성되고 초기화 메서드도 실행됨
- 프로토타입 빈을 2번 조회하면 완전히 다른 스프링 빈이 생성되고 초기화도 두번 실행되었음
- 프로토타입 빈은 스프링 컨테이너가 생성과 의존 관계 주입 그리고 초기화 까지만 관여하고 더 이상 관리하지 않음. 따라서 프로토타입 빈은 스프링 컨테이너가 종료될 때 @PreDestroy 같은 종료메서드가 실행되지 않음
- 핵심은 스프링 컨테이너는 프로토타입 빈을 생성하고 의존관계주입, 초기화 까지만 처리함

```java
@Test
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("프로토 타입 빈 : " + prototypeBean1);
        System.out.println("프로토 타입 빈2 : " + prototypeBean2);

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        // prototypeBean1.destroy(); 프로토타입빈은 이후 메서드를 실행하지 않기 때문에 사용 시 직접 사용해주어야함
        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean{
        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
```

<br>

### 프로토타입 스코프 - 싱글톤 빈과 함께 사용시 문제점
- 스프링 컨테이너에 프로토타입 스코프의 빈을 요청하면 항상 새로운 객체 인스턴스를 생성해서 반환. 하지만 싱글톤 빈과 함께 사용할 때는 의도한 대로 잘 동작하지 않으므로 주의해야 함
- 스프링은 일반저긍로 싱클톤 빈을 사용하므로 싱글톤 빈이 프로토타입 빈을 사용하게 됨. 그런데 싱글톤 빈은 생성 시점에만 의존관계 주입을 받기 때문에 프로토타입 빈이 새로 생성되기는 하지만 싱글톤 빈과 함께 유지되는 것이 문제
- 여러 빈에서 같은 프로토타입 빈을 주입받으면 주입 받는 시점에 각각 새로운 프로토타입 빈이 생성됨. 물론 사용할 때 마다 새로 생성되는 것은 아니라고 함

<br>

### 프로토타입 스코프 - 싱글톤 빈과 함께 사용시 Provider로 문제 해결
- 의존관계를 외부에서 주입(DI) 받는게 아니라 직접 필요한 의존관계를 찾는 것을 Dependency Lookup(DL) 의존관계 조회(탐색)이라고 함
- 그런데 이렇게 스프링 애플리케이션 컨텍스트 전체를 주입받게 되면, 스프링 컨테이너에 종속적인 코드가 되고, 단위 테스트도 어려워 짐
- 지금 필요한 기능은 지정한 프로토타입 빈을 컨테이너에서 대신 DL 정도의 기능만을 제공하는 무언가가 필요함 -> 스프링에는 ObjectFactory, ObjectProvider을 제공

#### ObjectFactroy, ObjectProvider
- 실행해보면 prototypeBeanProvider.getObject()를 통해 항상 새로운 프로토타입 빈이 생성되는 것을 확인할 수 있음
- ObjectProvider의 getObject()를 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환 ( DL )
- 스프링이 제공하는 기능을 사용하지만 기능이 단순하므로 단위테스트를 만들거나 mock 코드를 만들기는 훨씬 쉬워 짐
- ObjectProvider는 지금 딱 필요한 DL 정도의 기능만 제공
> - ObjectFactory : 기능이 단순, 별도의 라이브러리 필요없음, 스프링에 의존
> - ObejctProvider : ObjectFactory 상속, 옵션, 스트림 처리등 편의 기능이 많고 별도의 라이브러리 필요 없음. 스프링에 의존

<br>

#### JSR-330 Provider
- javax.inject.Provider 라는 JSR-330 표준을 사용하는 방법
- 이 방법을 사용하려면 javax.inject:javax.inject:1 라이브러리를 gradle에 추가해야 함 ( 별도의 라이브러리 필요 )
- 실행해보면 provider.get()을 통해서 항상 새로운 프로토타입 빈이 생성되는 것을 확인 할 수 있음
- provider의 get()을 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환 (DL)
- 자바 표준이고, 기능이 단순하므로 단위테스트를 만들거나 mock 코드를 만들기는 훨씬 쉬워짐 ( 자바 표준이므로 스프링이 아닌 다른 컨테이너에서도 사용 가능 )
- Provider는 지금 딱 필요한 DL 정도의 기능만 제공
> ObjectProvider는 DL을 위한 편의 기능을 많이 제공해주고 스프링 외의 별도의 의존 관계 추가가 필요 없기 때문에 편리. 만약 코드를 스프링이 아닌 다른 컨테이너에서도 사용할 수 있어야 한다면 JSR-330 Provider를 사용해야 함 (해당 빈도수는 매우 적음)

<br>

### 웹스코프란
- 웹 스코프는 웹 환경에서만 동작
- 웹 스코프는 프로토타입과 다르게 스프링이 해당 스코프의 종료시점까지 관리. 따라서 종료 메서드가 호출됨

#### 웹 스코프의 종류
- request : HTTP 요청 하나가 들어오고 나갈 때 까지 유지되는 스코프, 각각의 HTTP 요청마다 별도의 빈 인스턴스가 생성되고 관리됨
- session : HTTP Session과 동일한 생명주기를 가지는 스코프
- application : 서블릿 컨텍스트(ServletContext)와 동일한 생명주기를 가지는 스코프
- websocket : 웹 소켓과 동일한 생명주기를 가지는 스코프

### 웹 환경
- gradle에 implementation 'org.springframework.boot:spring-boot-starter-web' 추가
- 스프링 부트는 웹 라이브러리가 없으면 AnnotationConfigApplicationContext를 기반으로 어플리케이션을 구동하고 웹 라이브러리가 추가되면 웹 관련 추가 설정과 환경이 필요하므로 AnnotationConfigServletWebServerApplicationContext를 기반으로 어플리케이션 구동함

#### request 스코프
- 동시에 여러 HTTP 요청이 오면 정확히 어떤 요청이 남긴 로그인지 구분하기 어려운데 해당 상황에 사용하는 것이 request 스코프
- 기대하는 공통 포맷 : \[UUID\]\[requestURL\]\{message\}
- UUID를 이용하여 HTTP 요청 구분
- requestURL 정보도 추가로 넣어서 어떤 URL을 요청해서 남은 로그인지 확인

```java
@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request){
        String requestURL = request.getRequestURI().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
```
- 로거가 잘 작동하는지 확인 테스트 컨트롤러
- 여기서 HttpRequest를 통해서 요청 URL을 받음
- 이렇게 받은 requestURL 값을 myLogger에 저장해두고 myLogger는 HTTP 요청당 각각 구분되므로 HTTP 요청때문에 값이 섞이는 걱정은 하지 않아도 됨
- 컨트롤러에서 controller test 라는 로그를 남김
- 하지만 현재는 스프링 애플리케이션을 실행시키면 오류 발생 -> 스프링 애플리케이션을 실행하는 시점에 싱글톤 빈은 생성해서 주입이 가능하지만, request 스코프 빈은 아직 생성되지 않았음. 해당 빈은 실제 고객의 요청이 와야 생성할 수 있음 

#### 위의 예시 코드 해결 방법 ObjectProvider
```java
@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request){
        String requestURL = request.getRequestURI().toString();
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
```
- ObjectProvider 덕분에 ObjectProvider.getObject() 를 호출하는 시점까지 request scope 빈의 생성을 지연할 수 있음
- ObjectProvider.getObject()를 호출하는 시점에는 HTTP 요청이 진행중이므로 request scope 빈의 생성이 정상 처리됨
- ObjectProvider.getObject()를 LogDemoController, LogDemoService 에서 각각 한번씩 따로 호출해도 같은 HTTP 호요청이면 같은 스프링 빈이 반환

<br>

#### Service 계층
- 서비스 계층은 비즈니스 로직이 존재
- 서비스 계층은 request scope를 사용하지 않고 파라미터로 모든 정보를 서비스 계층으로 넘긴다면 파라미터가 많아 지저분해지고 requestURL 같은 웹과 관련된 정보가 웹과 관련없는 서비스 계층까지 넘어가게 됨.
- 서비스 계층은 웹 기술에 종속하지 않고 가급적 순수하게 유지되는 것이 유지보수 관점에서 좋음

<br>

### 스코프와 프록시
- 위의 request 첫번째 예시코드를 프록시를 이용하여 해결하기
```java
@Component
@Scope(value="request", proxyMode = ScopedProxyMode.TARGET_CLASS)   // 해당 속성 추가
public class MyLogger {
    ...
}


@Controller     // 위의 예시코드와 유사
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request){
        String requestURL = request.getRequestURI().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
```
- proxyMode = ScopeProxyMode.TARGET_CLASS 를 추가하여 프록시 방식 사용
- 적용대상이 클래스면 TARGET_CLASS, 인터페이스면 TARGET_INTERFACES 사용
- 이렇게 하면 MyLogger 의 가짜 프록시 클래스를 만들어 두고 (바이트 코드를 조작하는 CGLIB가 생성/등록) HTTP request와 상관없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있음 (  MyLogger 이라는 클래스가 아닌 MyLogger.class를 CGLIB이 생성한 가짜 프록시 객체가 주입됨 )
- 가짜 프록시 객체는 내부에 진짜 myLogger 클래스 찾는 방법을 알고 있음
- 가짜 프록시 객체는 원본 클래스를 상속 받아서 만들어졌기 때문에 이 객체를 사용하는 클라이언트 입장에서는 사실 원본인지 아닌지도 모르게 동일하게 사용할 수 있음 (다형성)
- 가짜 프록시 객체는 request 객체와 관계가 없는 가짜이고 내부의 단순한 위임 로직만 있고 싱글톤처럼 동작함

<br>

### 정리
- 프록시 객체 덕분에 클라이언트는 마치 싱글톤 빈을 사용하듯이 편리하게 request scope를 사용할 수 있음 ( 하지만 싱글톤과 다르게 동작하므로 사용에 주의 )
- 사실 Provider를 사용하든, 프록시를 사용하든 핵심 아이디어는 진짜 객체 조회를 꼭 필요한 시점까지 지연처리 한다는 점
- 단지 어노테이션 설정 변경만으로 원본 객체를 프록시 객체로 대체할 수 있음 -> 다형성과 DI 컨테이너가 가진 큰 강점
- 꼭 웹 스코프가 아니어도 프록시는 사용 가능