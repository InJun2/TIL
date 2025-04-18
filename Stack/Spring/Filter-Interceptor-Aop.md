# Filter vs Interceptor vs AOP

### 공통처리

- 웹 애플리케이션을 개발하는 도중 공통적으로 처리해야하는 업무들이 많이 존재
- 공통로직을 모든 페이지에 작성해야하면 중복코드가 많아지고 프로적트 단위가 커질수록 서버에 부하를 줌 -> 공통 부분은 따로 관리하는 것이 좋음
- 공통처리를 위한 Filter, Interceptor, AOP
  - Filter(필터) : 핸들러 동작의 전 후 과정에 부가로직 처리, 웹 컨테이너에서 관리
  - Interceptor(인터셉터) : 필터와 비슷, 스프링 컨테이너에서 관리
  - AOP(관점 지향 프로그래밍) : 메서드 동작의 전 후 과정에 부가로직 처리

<br>

![Filter](./img/Filter.png)

### Filter

- Filter는 <code>J2EE</code> 표준 스펙으로 디스패처 서블릿에 요청이 전달되기 전/후에 URL 패턴에 맞는 모든 요청에 대해 부가작업을 처리할 수 있는 기능을 제공
- 스프링 컨테이너가 아닌 톰캣과 같은 웹 어플리케이션 서버(WAS)에서 관리가 되는 것이고 디스패처 서블릿 전/후에 처리하는 것임
- 지정된 자원 앞단에서 요청내용을 변경하거나 여러가지 체크 수행, 자원의 처리가 끝난 후 응답내용에 대해 인코딩 등 변경 처리
- Spring에서 빈등록이 가능
- javax.servlet의 Filter 인터페이스를 구현해야함

```
J2EE
- 자바 기술로 기업환경의 어플리케이션을 만드는데 필요한 스펙들을 모아둔 스펙 집합
- 언어는 JAVA 이고 플랫폼은 자유로움. JVM 이 플랫폼에 상관없이 동일한 자바 소스 코드 실행 가능
- J2EE는 매우 방대한 범위를 다루는 스펙 집합으로 서블릿, JSP, EJB, JDBC 등의 구성요소로 이루어져 있음
```

<br>

#### Filter Method

- init 메소드
  - 필터 객체를 초기화하고 서비스에 추가하기 위한 메소드
  - 웹 컨테이너가 1회 init 메소드를 호출하여 필터 객체를 초기화하면 이후의 요청들은 doFilter를 통해 처리됨
- doFilter 메소드
  - URL-Pattern에 맞는 모든 HTTP 요청이 디스패처 서블릿으로 전달되기 전에 웹 컨테이너에 의해 실행되는 메소드
  - doFilter의 파라미터로는 FilterChain이 있는데, FilterChain의 doFilter 를 통해 다음 대상으로 요청을 전달하게 됨.
  - chain.doFilter() 전/후에 우리가 필요한 처리 과정을 넣어줌으로써 원하는 처리를 진행할 수 있음
- destroy 메소드
  - 필터 객체를 서비스에서 제거하고 사용하는 자원을 반환하기 위한 메소드
  - 웹 컨테이너에 의해 1번 호출되며 이후에는 이제 doFilter에 의해 처리되지 않음

```
자바 기반의 웹 애플리케이션 프로그래밍 기술에서는 서블릿을 이용하여 웹 요청과 응답의 흐름을 메서드 호출으로 다룰 수 있게 해줌

서블릿 생명주기 메서드
초기화 : init()
- 서블릿 요청 시 맨 처음 한 번만 호출된다.
- 서블릿 생성 시 초기화 작업을 주로 수행한다.

작업 수행 : doGet(), doPost()
- 서블릿 요청 시 매번 호출된다.
- 실제로 클라이언트가 요청하는 작업을 수행한다.

종료 : destroy()
- 서블릿이 기능을 수행하고 메모리에서 소멸될 때 호출된다.
- 서블릿의 마무리 작업을 주로 수행한다.

서블릿 컨테이너
- 구현되어 있는 servlet 클래스의 규칙에 맞게 서블릿을 담고 관리해주는 컨테이너
- 클라이언트에서 요청을 하면 컨테이너는 HttpServletRequest, HttpServletResponse 두 객체를 생성하여 post, get여부에 따라 동적인 페이지를 생성하여 응답을 보냄
```

```java
public interface Filter {

    public default void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException;

    public default void destroy() {}
}

@Component
public class MethodFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(MethodFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Start Method checking");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("Request Method: {}", req.getMethod());

        chain.doFilter(request, response);

        logger.info("Return Method: {}", req.getMethod());
    }

    @Override
    public void destroy() {
        logger.info("End Method checking");
        Filter.super.destroy();
    }
}

@Configuration
public class FilterConfiguration implements WebMvcConfigurer {
    @Bean
    public FilterRegistrationBean<MethodFilter> methodFilterRegistrationBean() {
        FilterRegistrationBean<MethodFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MethodFilter());
        registrationBean.addUrlPatterns("/stations");

        return registrationBean;
    }
}
```

<br>

### DelegatingFilterProxy

- DelegatingFilterProxy는 스프링의 빈을 Filter로 사용하기 위한 Proxy Filter로 Spring 1.2에서 추가되었음
- Filter는 Servlet Container 에서 동작하므로 Spring에서 직접 제어하기는 어려움이 있음. 이를 해결하기 위해 스프링에서 Proxy Filter를 만들어 기존의 Filter Chain에 투입
  - DelegatingFilterProxy는 사용자 요청을 받아 실제 필터 역할을 하는 스프링 빈에게 요청을 위임하는 역할을 함
- 이를 통해 Spring 기술을 Filter 차원에서도 사용할 수 있게 함
- Spring Boot 에서는 자동 환경 설정으로 DelegatingFilterProxy를 자동으로 거치도록 처리됨
  - 필터를 컴포넌트로 등록할 때 @Componenet는 urlPattern을 지정할 수 없어 @WebFilter를 사용함
- @WebFilter로 등록된 필터 클래스를 스캔하고 빈으로 등록해주기 위해서 @ServletComponentScan을 이용
  - 이 애너테이션은 @WebFilter 뿐 아니라 @WebServlet, @WebListener 타입의 서블릿 컴포넌트를 스캔하여 스프링 빈으로 등록함

```java
@WebFilter(urlPatterns = "/showMessage")
public class SimpleFilter implements Filter{

}

@SpringBootApplication
@ServletComponentScan(basePackageClasses = SimpleFilter.class)
public class MvcFilterApplication{

}
```

<br>

![Interceptor](./img/Interceptor.png)

### Interceptor

- Interceptor는 J2EE 표준 스펙인 필터와 달리 Spring이 제공하는 기술로 디스패처 서블릿이 컨트롤러를 호출하기 전과 후에 요청과 응답을 참조하거나 가공할 수 있는 기능
- 웹 컨테이너에서 동작하는 필터와 달리 인터셉터는 스프링 컨텍스트에서 동작을 함
- 디스패처 서블릿은 핸들러 매핑을 통해 적절한 컨트롤러를 찾도록 요청하는데 그 결과로 실행체인(HandlerExecutionChain)을 돌려줌. 해당 체인은 1개이상의 인터셉터가 등록되어 있다면 순차적으로 인터셉터들을 거쳐 컨트롤러가 실행되도록 하고, 인터셉터가 없다면 바로 컨트롤러 실행
- 디스패처 서블릿이 컨트롤러 호출 전, 후로 끼어들기 때문에 스프링 컨텍스트 내부에서 controller에 관한 요청과 응답에 대해 처리 -> 스프링의 모든 빈 객체에 접근 가능
- 그림과는 다르게 실제로는 Interceptor가 컨트롤러로 요청을 위임하는 것은 아님
- org.springframework.web.servlet의 HandlerInterceptor 인터페이스를 구현해야함

<br>

#### Interceptor Method

- preHandle 메소드
  - 컨트롤러가 호출되기 전에 실행. 컨트롤러 이전에 처리해야 하는 전처리 작업이나 정보를 가공하거나 추가하는 경우 사용
  - preHandle의 반환타입은 boolean으로 반환값이 true이면 다음 단계로 진행되고 false면 작업을 중단하여 이후의 작업은 진행되지 않음
- postHandle 메소드
  - 컨트롤러가 호출된 후에 실행. 컨트롤러 이후에 처리해야하는 후처리 작업이 있을 때 사용
  - 이 메소드에는 컨트롤러가 반환하는 ModelAndView 타입의 정보가 제공되는데 최근에는 RestAPI 기반의 컨트롤러를 만들면서 자주 사용되지는 않음
- afterCompletion 메소드
  - 모든 뷰에서 최종 결과를 생성하는 일을 포함해 모든 작업이 완료된 후에 실행 -> view 렌더링 이후
  - 요청 처리 중에 사용한 리소스를 반환할 때 사용하기 적합

```java
public interface HandlerInterceptor {

    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        return true;
    }

    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        @Nullable ModelAndView modelAndView) throws Exception {
    }

    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        @Nullable Exception ex) throws Exception {
    }
}

@Component
public class MyInterceptor implements HandlerInterceptor {

    // 1. Controller 보내기 전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandler");
        System.out.println(handler);

        // false이면 controller로 요청 안한다
        return true;
    }

    // 2. Controller의 handler처리 후
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandler");
        System.out.println(handler);
        System.out.println(modelAndView);
    }

    // 3. View 처리 이후
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
        System.out.println(handler);
    }
}

@Configuration
@RequiredArgsConstructor
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    private final HandlerInterceptor handlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor)
                .addPathPatterns("/**");
    }
}
```

<br>

![Filter vs Interceptor](./img/Filter-Interceptor.png)

<br>

### Filter 와 Interceptor 용도

#### Filter

- 공통된 보안 및 인증/인가 관련 작업 -> 대표적으로 SpringSecurity
- 모든 요청에 대한 로깅 또는 감사
- 이미지/데이터 압축 및 문자열 인코딩
- Spring과 분리되어야 하는 기능 -> Spring과 무관하게 전역적으로 처리해야하는 작업들. 웹 애플리케이션에 전반적으로 사용되는 기능 구현 적당

#### Interceptor

- 세부적인 보안 및 인증/인가 공통 작업
- API 호출에 대한 로깅 또는 감사
- Controller로 넘겨주는 데이터 가공

<br>

### AOP (Aspect Oriented Programming, 관점 지향 프로그래밍)

- 객체 지향 프로그래밍 시 중복을 줄일 수 없는 부분을 줄이기 위해 관점에서 보고 처리. OOP를 보완하기 위한 개념
- 주로 로깅, 트랜잭션, 에러처리 등의 비즈니스단의 메서드에서 조금 더 세밀하게 조정하고 싶을 때 사용
- Filter와 Interceptor와는 달리 메소드 전후의 지점에 자유롭게 설정 가능 -> Interceptor나 Filter는 주소로 대상을 구분하지만 AOP는 주소, 파라미터, 어노테이션 등 다양한 방법으로 대상 지정 가능
- 프로그래머가 직면하는 가장 일반적인 문제를 해결하기 위한 Spring AOP 와 완전한 AOP 솔루션을 제공하는 AspectJ 라이브러리 존재.
- AOP의 Advice와 HandlerInterceptor의 가장 큰 차이는 파라미터의 차이 -> advice는 JoinPoint나 ProceedingJoinPoint 등을 활용해서 호출
- AOP 적용 방식은 여러가지가 있음. 그러나 Spring AOP는 런타임 시점에 적용하는 방식을 사용하는데 컴파일 시점과 클래스 로딩 시점에 적용하려면 별도의 컴파일러와 클래스로더 조작기를 써야 하는데, 이것을 정하고 사용 및 유지하는 과정이 매우 어렵고 복잡하기 때문
  - 컴파일 시점 적용
  - 클래스 로딩 시점 적용
  - 런타임 시점 적용

<br>

### AOP 주요 용어

- Aspect
  - 위에서 설명한 흩어진 관심사를 모듈화 한 것. 어드바이스 + 포인트컷을 모듈화한 애플리케이션의 횡단 기능
- JointPoint
  - Advice가 적용될 위치, 끼어들 수 있는 지점. 메서드 진입 지점, 생성자 호출 시점, 필드에서 값을 꺼내올 때 등 다양한 시점에 적용가능
  - 한 마디로 AOP를 적용할 수 있는 모든 지점 (스프링에서는 메서드 실행 지점으로 제한)
- Advice
  - 실질적으로 어떤 일을 해야할 지에 대한 것, 실질적인 부가기능을 담은 구현체로 조인포인트에서 실행되는 코드 즉 부가기능 그 자체
  - 에스팩트를 언제 핵심 코드에 적용할지 정의
- PointCut
  - JointPoint의 상세한 스펙을 정의한 것으로 조인포인트 중 어드바이스가 적용될 지점을 선별하는 기능.
  - 'A란 메서드의 진입 시점에 호출할 것'과 같이 더욱 구체적으로 Advice가 실행될 지점을 정할 수 있음
- Target
  - Aspect를 적용하는 곳 (클래스, 메서드 .. )으로 핵심 기능을 담은 모듈
  - 어드바이스를 받는 객체이고, 포인트컷으로 결정된다
- Advisor
  - 스프링 AOP에서만 쓰는 용어로, 하나의 어드바이스와 하나의 포인트컷으로 구성된 에스팩트를 특별하게 지칭

<br>

#### AOP annotation (Advice에 해당)

- @Before : 대상 메소드 수행 전
- @After : 대상 메소드 수행 후
- @After-returning : 대상 메소드의 정상적인 수행 후
- @After-throwing : 예외발생 후
- @Around : 대상 메소드의 수행 전, 후

<br>

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Timer {}

@Aspect
@Component
public class TimerAop { // 주로 사용되는 메서드 수행시간 출력을 위한 AOP 클래스

    @Pointcut("execution(* com.example.aop.controller..*.*(..))")
    private void cut() {}

    //사용자 지정 어노테이션이 붙은 메서드에도 적용!
    @Pointcut("@annotation(com.example.aop.annotation.Timer)")
    private void enableTimer() {}

    //메서드 전 후로 시간 측정 시작하고 멈추려면 Before, AfterReturning으로는 시간을 공유 할 수가 없음 Around 사용!
    @Around("cut() && enableTimer()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {

        //메서드 시작 전
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //메서드가 실행되는 지점
        Object result = joinPoint.proceed();

        //메서드 종료 후
        stopWatch.stop();

        System.out.println("총 걸린 시간: " + stopWatch.getTotalTimeSeconds());
    }
}
```

<br>

### Interceptor vs AOP

- 인터셉터 대신에 컨트롤러들에 적용할 부가기능을 어드바이스로 만들어 AOP 적용 가능
- Interceptor가 나은 경우
  - Spring의 컨트롤러는 타입과 실행 메소드가 모두 제각각이라 포인트 컷의 작성이 어려움
  - Spring의 컨트롤러는 파라미터나 리턴 값이 일정하지 않음

<br>

![Filter vs Interceptor vs AOP Flow](./img/commonLogic_flow.png)

<br>

![Interceptor Flow](./img/Interceptor_flow.png)

### Filter vs Interceptor vs AOP Flow

1. 서버를 실행시켜 서블릿이 올라오는 동안에 init이 실행되고 그후 doFilter가 실행됨
2. 컨트롤러에 들어가기 전 preHandler가 실행됨
3. 컨트롤러에서 나와 postHandler, after Completion, doFilter 순으로 진행됨
4. 서블릿 종료 시 destroy가 실행됨

<br>

### 차이점 정리

#### 1. 적용 시점 차이

    -  Filter -> Interceptor -> AOP -> Interceptor -> Filter

#### 2. 적용 방식 차이

    - Filter : web-context
    - Interceptor : servlet-context

#### 3. 실행 위치가 다름

    - Interceptor, Filter : Servlet 단위에서 실행
    - AOP : 메소드 앞에 Proxy 패턴의 형태로 실행

<br>

## Ssafy Wizards CS Study

### 1. @Aspect는 어떻게 동작하는지

1. Aspect 정의
   - @Aspect 애노테이션을 클래스에 사용하여 그 클래스가 하나의 Aspect(횡단 관심사)를 정의함
   - Aspect 클래스는 하나 이상의 어드바이스(Advice)를 가질 수 있음. 어드바이스는 실제로 횡단 관심사를 구현하는 메서드
2. Advice 정의
   - 어드바이스는 특정 시점에 실행되는 코드임 (포인트컷이 지정한 조인 포인트에서 실행될 실제 동작(코드))
   - @Before: 타겟 메서드가 호출되기 전에 실행됨
   - @After: 타겟 메서드가 완료된 후에 실행됨
   - @AfterReturning: 타겟 메서드가 성공적으로 완료된 후에 실행됨
   - @AfterThrowing: 타겟 메서드가 예외를 던진 후에 실행됨
   - @Around: 타겟 메서드 호출 전후에 실행되며, 가장 강력한 어드바이스
3. Pointcut 정의
   - 포인트컷(Pointcut)은 어드바이스가 적용될 타겟 메서드를 정의하는 표현식
   - 포인트 컷은 어드바이스가 적용될 위치로 대상 패키지, 클래스, 메서드를 지정
   - @Pointcut 애노테이션을 사용하여 포인트컷을 정의하고, 어드바이스에서 참조함

> - 이후 스프링 컨테이너가 시작될 때, AOP 설정을 읽고, 포인트컷과 어드바이스에 따라 프록시 객체를 생성
> - 프록시 객체는 타겟 객체의 메서드 호출을 가로채어, 정의된 어드바이스를 실행

```java
// Aspect 정의
@Aspect
public class LoggingAspect {
    @Before("execution(* com.example.service.*.*(..))") // Advice, Pointcut 정의
    public void logBeforeMethod() {
        System.out.println("Method is about to be called.");
    }
}
```

<br>

<div style="text-align: right">내용 추가 : 23-11-23</div>

---

## Reference

- https://mangkyu.tistory.com/173
- https://baek-kim-dev.site/61
- https://livenow14.tistory.com/60
- https://velog.io/@falling_star3/Tomcat-서블릿Servlet이란
- https://goodteacher.tistory.com/590
