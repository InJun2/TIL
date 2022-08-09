# Singleton Container

### 웹 애플리케이션과 싱글톤
- 스프링은 태생이 기업용 온라인 서비스 기술을 지원하기 위해 탄생
- 대부분의 스프링 애플리케이션은 웹 애플리케이션.  ( 웹이 아닌 애플리케이션 개발도 가능 )
- 웹 애플리케이션은 보통 여러 고객이 동시에 요청을 함
- 고객 트래픽이 초당 100이 나온다면 초당 100개의 객체가 생성되고 소멸됨 -> 메모리 낭비가 심함
- 위의 경우를 해결하기 위해 해당 객체가 딱 1개만 생성되고 공유하도록 설계하면 되도록 정의한 패턴이 싱글톤 패턴

### 싱글톤 패턴
- 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴
- 그래서 객체 인스턴스를 2개 이상 생성하지 못하도록 막아야 함
- private 생성자를 사용해서 외부에서 임의로 new 키워드를 사용하지 못하도록 막음
- 호출할 때 마다 같은 인스턴스를 반환하는 것을 확인 가능
- 싱글톤 패턴을 구현하는 방법은 여러가지가 있고 아래 예시코드는 그중 객체를 미리 생성해두는 가장 단순한 방법
```java
public class SingletonService{
        private static final SingletonService instance = new SingletonService();

        public static SingletonService getInstance(){
                return instance;
        }
}       

// 싱글톤 객체 호출 클래스
....
SingletonService singletonService1 = SingletonService.getInstance();
SingletonService singletonService2 = SingletonService.getInstance();

asseertThat(singletonService1).isSameAs(singletonService2);

singletonService1.logic();
....
```
### 테스트
- assertThat().isSameAs = 객체 인스턴스가 같은지 확인
- assertThat().isEqualsAs = 값이 같은지 확인

<br>

### 싱글톤 패턴 문제점
- 싱글톤 패턴을 구현하는 코드 자체가 많이 들어감
- 의존관계상 클라이언트가 구체 클래스에 의존 -> DIP 위반
- 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높음
- 테스트하기 어려움
- 내부 속성을 변경하거나 초기화 하기 어려움
- private 생성자로 자식 클래스를 만들기 어려움
- 결론적으로 유연성이 떨어짐
- 안티 패턴으로 불리기도 함

<br>

### 싱글톤 컨테이너
- 스프링 컨테이너는 싱글톤 패턴의 문제를 해결하면서, 객체 인스턴스를 싱글톤으로 관리
- 이전 공부했던 스프링 빈이 바로 싱글톤으로 관리되는 빈
- 스프링 컨테이너는 싱글턴 패턴을 적용하지 않아도 객체 인스턴스를 싱글톤으로 관리함 ( 컨테이너는 객체를 하나만 생성해서 관리 )
- 스프링 컨테이너는 싱글톤 컨테이너 역할을 하고 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라고 함
- 스프링 컨테이너의 이런 기능 덕분에 싱글톤 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지 가능
- 스프링 컨테이너 덕분에 고객의 요청이 올 때마다 생성하는 것이 아니라 이미 만들어진 객체를 공유해서 효율적으로 재사용 가능 ( 90% 정도는 이렇게 사용 -> 스프링의 기본 빈 등록 방식은 싱글톤 이지만 요청할 때마다 새로운 객체를 생성해서 반환하는 기능도 제공 )


### 싱글톤 방식의 주의점
- 싱글톤 패턴이든 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤 방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(stateful)하게 설계하면 안됨
- 스프링 빈의 필드에 공유 값을 설정하면 정말 큰 장애가 발생할 수 있음
- 무상태(stateless)로 설계하여야 함
>- 특정 클라이언트에 의존적인 필드가 있으면 안됨
>- 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안됨
>- 가급적 읽기만 가능해야 함
>- 필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야 함
>- 공유 필드는 항상 조심해야 함


#### <code>값 변경이 가능하게 할 경우</code>

``` java 
public class StatefulService {

    private int price;

    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

}

class StatefulServiceTest {
    
    @Test
    void statefulServiceSingletion(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext((TestConfig.class));
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // A 사용자 10000원 주문
        statefulService1.order("userA", 10000);
        
        // B 사용자 20000원 주문
        statefulService2.order("userB", 20000);

        // 사용자 A 주문 금액 조회 -> 조회 시 A 사용자가 20000원으로 변경되어 있음
        int price = statefulService1.getPrice();

        System.out.println("price = " + price);
    }
    
    static class TestConfig{
        
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
        
    }
```

#### 해결
``` java 
public class StatefulService {

    private int price;

        // 전역변수 값을 변경하는 것이 아니라 지역 변수로 반환
    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;
    }

}

class StatefulServiceTest {
    
    @Test
    void statefulServiceSingletion(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext((TestConfig.class));
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // A 사용자 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);
        
        // B 사용자 20000원 주문
        int userAPrice = statefulService2.order("userB", 20000);

        System.out.println("price = " + userAPrice);
    }
    
    static class TestConfig{
        
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
        
    }
```

<br>

### @Configuration과 싱글톤
```java
@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }

}
```
- memberService 빈을 만드느 코드를 보면 memberRepository()를 호출
- orderService 빈을 만드는 코드도 동일하게 memberRepository()를 호출
- 결과적으로 각각 다른 2개의 MemoryMemberRepository가 생성되어 싱글톤이 꺠지는 것 처럼 보임
- 하지만 AppConfig 에서 memberRepository를 하나 만들고 이를 주입하면 모두 같은 객체를 전달 받음

<br>

### @Configuration과 바이트 코드 조작의 마법
```java
    @Test
    void configurationDepp(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
    }
```

- 해당 코드를 실행하면 bean = class helllo.core.AppConfig\$\$EnhancerBySpringCGLIB\$\$a823831d 라는 주소값이 나온다
- 순수한 클래스면 class hello.core.AppConfig가 출력이 되어야 하는데 xxxCGLIB가 붙으면서 복잡해 짐. 이것은 내가 만든 클래스가 아니라 CGLIB라는 바이트코드 조작 라이브러리를 이용하여 AppConfig클래스를 상속받은 임의의 다른 클래스를 만들고 그 다른 클래스를 스프링 빈으로 등록한 것임
- 이 임의의 다른 클래스가 바로 싱글톤이 보장되도록 해줌. 바이트 코드를 조작사여 작성되어 있음
```java
@Bean
public MemberRepository memberRepository(){
        if(memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있으면){
                return 스프링 컨테이너 찾아서 반환;
        } else {        // 스프링 컨테이너에 없으면
                 기존 로직을 호출하여 MemoryMemberRepository를 생성하고 스프링 컨테이너에 등록
                 return 반환
        }
}
```
- 위의 코드는 정말 단순하게 작성되어 있는데 실제로 CGLIB의 내부 기술을 사용하는 것은 매우 복잡하다고 함
- @Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어져서 싱글톤이 보장되는 것
- AppConifg@CGLIB는 AppConfig의 자식 타입이므로 AppConfig 타입으로 조회가 가능

> @Configuration을 작성하지 않는다면?
>- @Configuration이 바이트코드를 조작하는 CGLIB 기술을 사용해 싱글톤을 보장하고 있었기 때문에 CGLIB 기술 없이 순수한 AppConfig로 스프링 빈에 등록이 되었음 -> memberRepository()를 호출하여 각각 다른 객체 생성 ( 싱글톤 보장 되지 않음 )
>- 크게 고민할 것 없이 스프링 설정 정보는 항상 @Configuration을 사용

---
- 2022-07-10