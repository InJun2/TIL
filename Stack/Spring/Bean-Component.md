# Bean vs Component 

### 사용용도
>- Spring에서 객체를 제어하기 위해서는 객체들이 Bean으로 등록되어 있어야하며 @Bean과 @Component는 어떤 객체를 Bean으로 등록하고 싶을 때 사용되는 어노테이션임
>- Bean은 Spring IoC(Inversion of Control) 컨테이너가 관리하는 객체를 의미함
>- Spring Bean은 애플리케이션의 구성 요소를 나타내며, Spring 컨테이너에 의해 생성, 관리, 소멸됨
>
>
> \* Annotatiton ( 어노테이션 ) 
>>- 사전적인 의미로는 주석. 자바에서 어노테이션은 주석처럼 쓰이며 특정한 기능을 수행하도록 하는 기술. 프로그램에게 추가적인 정보를 제공해주는 메타데이터임. 코드에 메타데이터를 작성하여 직관적인 코딩이 가능하고 이에따른 생산성이 증대됨

<br>

### @Bean
>- 메서드 위에 선언 가능하고, 개발자가 컨트롤이 불가능한 외부 라이브러리들을 Bean으로 등록하기 위한 어노테이션. 
>- 인스턴스를 생성하는 메소드를 만든 후 그 메소드에 @Bean을 선언해 Bean으로 등록

<br>

### @Component 
>- 클래스 위에 선언 가능하고, 개발자가 직접 작성한/컨트롤이 가능한 Class를 Bean으로 등록하기 위한 어노테이션.
>- Class타입, Interface타입, Enum 타입 등에 선언 가능

<br>

|Bean|Component|
|---|---|
|메소드에 사용|클래스에 사용|
|개발자가 컨트롤이 불가능한 외부 라이브러리에 사용|개발자가 직접 컨트롤이 가능한 내부 클래스에 사용|

<br>

### 스프링 빈의 특징
- 생명 주기 관리
    - Spring 컨테이너는 Bean의 생성, 초기화, 소멸을 관리함. 개발자는 이 생명 주기를 커스터마이즈하여 초기화 로직이나 정리 로직을 정의할 수 있음
- 의존성 주입(DI)
    - Spring은 DI를 통해 Bean 간의 의존성을 설정함. 이를 통해 결합도를 낮추고 유연한 코드 작성이 가능
- 스코프
    - Bean의 스코프는 싱글톤(Singleton), 프로토타입(Prototype), 요청(Request), 세션(Session) 등 여러 가지로 설정할 수 있음. 각 스코프는 Bean이 어떻게 생성되고 사용되는지를 결정

<br>

### Spring의 Bean 생성 주기
1. Bean 정의 (Definition)
    - XML설정 파일 혹은 어노테이션을 통해 스프링 컨테이너가 Bean의 설정 정보를 읽어 들임
```java
@Configuration
public class AppConfig {
    
    @Bean
    public MyBean myBean() {
        return new MyBean();
    }
}
```
2. Bean 인스턴스화 (Instantiation)
- 스프링 컨테이너는 Bean의 인스턴스를 생성. 이는 Bean 클래스의 생성자를 호출하여 객체를 만드는 과정
3. 의존성 주입 (Dependency Injection)
- 생성된 Bean에 필요한 의존성을 주입. 의존성 주입 방식은 생성자 주입, 세터 주입, 필드 주입이 존재
```java
@Service
public class MyService {
    private final MyRepository myRepository;

    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }
}
```
4. 초기화 (Initialization)
- 의존성 주입이 완료된 후, Bean의 초기화 메서드가 호출. 초기화 메서드는 다음 중 하나로 정의할 수 있음
    - @PostConstruct 어노테이션이 붙은 메서드
    - InitializingBean 인터페이스의 afterPropertiesSet() 메서드
    - XML 설정에서 지정한 init-method
```java
@Component
public class MyBean {

    @PostConstruct
    public void init() {
        // 초기화 로직
    }
}
```
5. 사용 (Usage)
- 초기화가 완료된 Bean은 애플리케이션의 다른 부분에서 사용될 준비가 됨
- 스프링 컨테이너는 애플리케이션 실행 동안 Bean을 관리하고, 필요에 따라 의존성을 주입하여 Bean을 사용할 수 있도록 함
6. 소멸 (Destruction)
- Bean의 생명 주기가 끝나면, 소멸 단계가 시작됨
- 애플리케이션 컨텍스트가 종료되거나, Bean이 더 이상 필요하지 않을 때 발생하며 소멸 메서드는 다음 중 하나로 정의할 수 있음
    - @PreDestroy 어노테이션이 붙은 메서드
    - DisposableBean 인터페이스의 destroy() 메서드
    - XML 설정에서 지정한 destroy-method

```java
@Component
public class MyBean {

    @PreDestroy
    public void destroy() {
        // 소멸 로직
    }
}
```

<br>

### 프로토타입 빈이란?
- 스프링 빈이란 스프링 컨테이너에서 관리하는 자바 객체로 싱글톤 스코프로 생성되어 스프링 컨테이너와 생명주기를 같이 함
- 프로토 타입 빈이란  Spring 프레임워크에서 제공하는 여러 빈 스코프 중 하나로, 요청 시마다 새로운 인스턴스를 생성하는 빈을 의미
- 클라이언트에서 프로토타입 스코프의 스프링 빈을 스프링 컨테이너에 요청하면 스프링 컨테이너는 프로토타입 빈을 생성하고 의존 관계 주입하고 생성한 프로토타입 빈을 클라이언트에 반환
- 여기서 프로토타입은 스프링 빈과 다르게 빈 생성, 의존관계 주입, 초기화 까지만 진행하여 반환
    - 해당 프로토타입 빈은 이후에 관리하지 않기에 모두 클라이언트에서 자체적으로 관리해야함
    - 기존 빈과 다르게 스프링 컨테이너와 생명주기를 같이 하지 않음
    - 싱글톤 스프링 빈은 매번 동일한 인스턴스를 반환하지만 프로토타입 빈은 스프링컨테이너에 요청할 때마다 새로운 빈을 생성하고 의존 관계 주입 및 초기화 후 반환함
- 웹 관련 스코프
    - request : 웹 요청이 들어오고 나갈때까지 유지되는 스코프
    - session : 웹 세션이 생성되고 종료될 때까지 유지되는 스코프
    - application : 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프

```java
@Bean
@Scope("prototype")
public MyBean myPrototypeBean() {
    return new MyBean();
}

public class Client {
    @Autowired
    private ApplicationContext context;

    public void usePrototypeBean() {
        MyBean bean1 = context.getBean(MyBean.class);
        MyBean bean2 = context.getBean(MyBean.class);

        // bean1과 bean2는 서로 다른 인스턴스입니다.
        System.out.println(bean1 == bean2); // false
    }
}
```

<div style="text-align: right">22-06-20</div>

-------

## Reference
- https://youngjinmo.github.io/2021/06/bean-component/
