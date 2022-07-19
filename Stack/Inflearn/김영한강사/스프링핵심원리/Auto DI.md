# Automatic Dependencies Injection ( 의존성 자동 주입 )

### 의존 관계 주입
#### 1. 생성자 주입
#### 2. 수정자 주입 (Setter주입)
#### 3. 필드 주입
#### 4. 일반 메서드 주입

#### 기초 지식
- 의존 관계 자동 주입은 스프링 컨테이너가 관리하는 스프링 빈이어야 동작하는데 스프링 빈이 아닌 Member 같은 클래스에서 @Autowired 코드를 적용해도 아무 기능도 동작하지 않음

#### 1. 생성자 주입
- 이름 그대로 생성자를 통해서 의존 관계를 주입하는 방법
- 생성자 호출 시점에 단 1번만 호출되는 것이 보장
- 불변, 필수 의존관계에 사용
- 생성자가 딱 1개만 있으면 @Autowired가 없어도 가능 ( 스프링 빈 )
- 최근에는 스프링을 포함한 DI 프레임 워크 대부분이 생성자 주입을 권장
- 생성자 주입을 가장 많이 사용하는 이유는 여러가지가 있지만 프레임워크에 의존하지 않고 순수한 자바 언어의 특징을 잘 살리는 방법이기도 함
- 기본으로 생성자 주입을 사용하고 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 됨
- 항상 생성자 주입을 선택하고 가끔 옵션이 필요하면 수정자 주입을 선택하는 것이 좋음
>불변
>- 대부분의 의존 관계 주입은 한번 일어나면 애플리케이션 종료시점까지 의존 관계를 변경할 일이 없음. 오히려 대부분의 의존 관계는 애플리케이션 종료 전까지 변하면 안된다 (불변)
>- 수정자 주입을 사용하면 메서드를 public으로 열어두어야 함
>- 누군가 실수로 변경할 수도 있고, 변경하면 안되는 메서드를 열어두는 것은 좋은 설계 방법이 아님
>
>누락
>- 프레임워크 없이 순수한 자바 코드를 단위 테스트 하는 경우에 수정자 의존관계인 경우 @Autowired가 프레임워크 안에서 동작할 때는 의존관계가 없으면 오류가 발생
``` java
@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

<br>

#### 2. 수정자 주입 ( Setter 주입 )
- setter라 불리는 필드의 값을 변경하는 수정자 메서드를 통해서 의존관계를 주입하는 방법
- 선택, 변경 가능성이 있는 의존관계에서 사용
- 자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용하는 방법
- @Autowired 의 기본 동작은 주입할 대상이 없으면 오류 발생. 주입할 대상이 없어도 동작하게 하려면 @Autowired(required = false)로 지정

``` java
@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy){
        this.discountPolicy = discountPolicy;
    }
}
```

<br>

#### 3. 필드 주입
- 이름 그대로 필드에 바로 주입하는 방법
- 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 있음
- DI 프레임 워크가 없으면 아무것도 할 수 없음
- 사용하지 않는 것이 좋음
- 필드 주입을 사용해도 가능 한 곳
    - 애플리케이션의 실제코드와 관계 없는 테스트 코드
    - 스프링 설정을 목적으로 하는 @Configuration 같은 곳에서만 특별한 용도로 사용

```java
@Component
public class OrderServiceImpl implements OrderService{
    
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiscountPolicy discountPolicy;

}

```

<br>

#### 4. 일반 메서드 주입
- 일반 메서드를 통해서 주입 받을 수 있음
- 한번에 여러 필드를 주입 받을 수 있음
- 보통 생성자 주입, 수정자 주입을 사용하기 때문에 일반적으로 잘 사용하지 않음


```java
@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```


### 옵션 처리
- 주입할 스프링 빈이 없어도 동작해야 할 때가 있음
- 그런데 @Autowired만 사용하면 required 옵션의 기본값이 true로 되어 있어서 자동 주입 대상이 없으면 오류가 발생

#### 자동 주입 대상을 옵션으로 처리하는 방법
- @Autowired(required = false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
- org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null이 입력됨
- Optional<> : 자동 주입할 대상이 없으면 Optional.empty가 입력됨

```java
    @Autowired(required = false)    // 호출 안됨
    public void setNoBean1(Member noBean1){
        System.out.println("noBean1 = " + noBean1);
    }

    @Autowired                      // null 호출
    public void setNoBean2(@Nullable Member noBean2){
        System.out.println("noBean2 = " + noBean2);
    }

    @Autowired                      // Optional.empty 호출
    public  void setNoBean3(Optional<Member> noBean3){
        System.out.println("noBean3 = " + noBean3);
    }
```

<br>

#### \* final 키워드
- 생성자 주입을 제외하고는 필드에 final 키워드를 사용할 수 없음
- 생성자 주입을 사용하면 필드에 final 키워드 사용 가능. 그래서 생성자에서 혹시라도 값이 설정되지 않는 오류를 컴파일 시점에 막아줌
- 필수 필드가 누락되어 final 키워드를 사용한 변수에 값이 누락되면 자바는 컴파일 시점에 다음 오류를 발생 시킴 -> 컴파일 오류는 세상에서 가장 빠르고 좋은 오류

<br>

### 롬복과 최신 트렌드
- 개발을 해보면 대부분이 불변이고 생성자에 모두 final 키워드, 생성자 생성, 주입 받은 값을 대입하는 코드 등의 불편함을 해소
- 롬복이 어노테이션 프로세서라는 기능을 이용해서 컴파일 시점에 생성자 코드를 자동으로 생성해줌
- 최근에는 생성자를 딱 1개 두고 @Autowired를 생략하고 @RequiredArgsConstructor 를 함께 사용하여 기능은 다 제공하고 코드는 깔끔해져서 이런 방식을 많이 사용함

### Intellij 롬복사용 방법
- window : File -> Settings -> Plugins -> Lombok 체크,
File -> Settings -> Annotation Processors -> Enable annotation processing 체크 Aplly

#### Getter, Setter, toString
- private 필드 변수의 getter 혹은 setter 자동 생성
```java
@Getter
@Setter
@ToString
public class HelloLombok {

    private String name;
    private int age;
    ...
```

#### RequiredArgsConstructor
- final이 붙은 필드를 모아서 생성자를 자동으로 만들어줌 ( 전체 필드로 생성자 생성 )
```java
@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    ...
```

<br>

### 조회 빈이 2개 이상 일 때
- 예시 코드에서 DisCountPolicy 의 구현체는 FixDisCountPolicy, RateDisCountPolicy 두 개가 있음 -> 두개를 @Component로 등록해놨다면 NoUniqueBeanDefinitionException 오류 발생
- 이때 하위 타입으로 지정할 수도 있지만 하위 타입으로 지정하는 것은 DIP를 위배하고 유연성이 떨어짐, 또한 완전히 똑같은 타입의 스프링 빈이 2개 있을 때 해결이 되지 않음
- 스프링 빈을 수동 등록해서 문제를 해결해도 되지만 외존 관계 자동 주입에서 해결하는 여러 방법이 있음
- 해결방법은 @Autowired 필드명, @Quilifier, @Primary

<br>

### @Autowired 필드명, @Quilifier, @Primary
#### 1. @Autowired 필드명 매칭
- @Autowired는 타입 매칭을 시도하고, 이때 여러 빈이 있다면 필드 명, 파라미터 명으로 빈 이름을 추가 매칭함
- @Autowired 필드명 매칭은 먼저 타입 매칭을 시도하고 그 결과에 여러 빈이 있을 때 추가로 동작하는 기능
```java
// 기존코드
@Autowired
private DiscountPolicy discountPolicy

// 필드 명을 빈 이름으로 변경
@Autowired
private DiscountPolicy rateDiscountPolicy

```

#### 2. Qualifier 끼리 매칭
- @Qualifier 추가 구분자를 붙여주는 방법. 주입 시 추가적인 방법을 제공하는 것이지 빈 이름을 변경하는 것이 아님
- @Qualifier 는 @Qualifier 를 찾는 용도로만 사용하는게 명확하고 좋음
- @Qualifier 매칭 -> 빈 이름 매칭 -> 못 찾았을 경우 NoSuchBeanDefinitionException 예외 발생
```java
// 설정
@Component
@Qualifier("mainDiscountPolicy")
public class RateDiscountPolicy implements DiscountPolicy{
    private int discountPercent = 10;

// 호출
@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy")DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
```

#### 3. Primary 사용
- @Primary는 우선순위를 정하는 방법
- @Autowired 시에 여러 빈 매칭되면 @Primary가 우선권을 가짐

```java
@Component
@Primary    // 우선권을 가져 해당 클래스로 주입
public class RateDiscountPolicy implements DiscountPolicy{
    private int discountPercent = 10;
```

<br>

#### @Primary와 @Qualifier 중에 어떤 것을 사용할지
- @Quilifier의 단점은 주입 받을 때 모든 코드에 @Quilifier를 붙여주어야 함
- 메인 데이터베이스의 커넥션을 획득하는 스프링 빈은 @Primary를 적용해서 조회하는 곳에서 @Qualifier 지정 없이 편리하게 조회하고, 서브 데이터베이스 커넥션 빈을 획득 할 때는 @Qualifier를 지정해서 명시적으로 획득하는 방식으로 사용하면 코드를 깔끔하게 유지 가능
- @Primary는 기본값 처럼 동작하는 것이고, @Qualifier는 매우 상세하게 동작하기 때문에 스프링은 자동보다는 수동이, 넓은 범위의 선택권보다 좁은 범위의 선택권이 우선 순위가 높기 때문에 @Primary보다 @Qualifier가 우선권이 높음

<br>

### 어노테이션 직접 만들기
- @Qualifier("mainDiscountPolicy") 이렇게 문자를 적으면 컴파일 시 타입 체크가 안됨
- 해당 아래 예시 코드처럼 중간에 어노테이션을 만들어 해당 어노테이션을 주입 부분과 호출 부분에 사용하면 된다
- 어노테이션을 모아서 사용하는 기능은 스프링이 지원해주는 기능이고, @Qualifier 뿐만 아니라 다른 어노테이션들도 함께 조합해서 사용 가능
```java
// 어노테이션 생성
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")    // Qualifier 명 지정
public @interface MainDiscountPolicy {
}

// 주입 클래스 지정
@Component
@MainDiscountPolicy     // 어노테이션 지정
public class RateDiscountPolicy implements DiscountPolicy{
    private int discountPercent = 10;

// 호출
@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
```

<br>

### 조회한 빈이 모두 필요할 때 List, Map
- 의도적으로 정말 해당 타입의 스프링 빈이 다 필요한 경우도 있음
- 할인 서비스를 제공하는데 클라이언트가 할인의 종류를 선택할 수 선택하게 하는 방법은 스프링을 사용하여 쉽게 전략 패턴을 매우 간단하게 구현 가능

```java
static class DiscountService {

    private final Map<String, DiscountPolicy> policyMap;
    private final List<DiscountPolicy> policies;

    @Autowired
    public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
        this.policyMap = policyMap;
        this.policies = policies;
        System.out.println("policyMap = " + policyMap);
        System.out.println("policies = " + policies);
    }
    public int discount(Member member, int price, String discountCode) {
        DiscountPolicy discountPolicy = policyMap.get(discountCode);
        return discountPolicy.discount(member, price);
    }
}
```

#### 로직 분석
- DiscountService는 Map으로 모든 DiscountPolicy를 주입 받음. 이때 fixDiscountPolicy와 rateDiscountPolicy가 주입됨
- discount() 메서드는 discountCode로 fixDiscountPolicy가 넘어오면 map에서 fixDiscountPolicy 스프링 빈을 찾아서 실행
#### 주입분석
- Map<String, DiscountPolicy> : map의 키에 스프링 빈의 이름을 넣어주고, 그 값으로  DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아줌
- List<DiscountPolicy> : DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아줌, 만약 해당하는 타입의 스프링 빈이 없으면 빈 컬렉션이나 Map을 주입

<br>

### 자동, 수동의 올바른 실무 운영 기준
#### 편리한 자동 기능을 기본으로 사용
- 스프링이 나오고 시간이 갈수록 점점 자동을 선호하는 추세. 최근 스프링 부트는 컴포넌트 스캔을 기본으로 사용하고 스프링 부트의 다양한 스프링 빈들도 조건이 맞으면 자동으로 등록하도록 설계
- 설정 정보를 기반으로 애플리 케이션을 구성하는 부분과 실제 동작하는 부분을 명확하게 나누는 것은 이상적이지만 개발자 입장에서는 상단히 번거롭고 관리할 빈이 많아서 설정 정보가 커지면 해당 설정 정보를 관리하는 것이 부담이 됨
- 자동 빈 등록으로도 OCP, DIP를 지킬 수 있음
#### 수동 빈 등록은 언제 사용? -> 기술 지원 로직
- 업무 로직 빈 : 웹을 지원하는 컨트롤러, 핵심 비즈니스 로직이 있는 서비스, 데이터 계층의 로직을 처리하는 리포지토리등이 모두 업무 로직. 보통 비즈니스 요구사항을 개발할 때 추가되거나 변경
- 기술 지원 빈 : 기술적인 문제나 공통 관심사(AOP)를 처리할 때 주로 사용. 데이터베이스 연결이나 공통 로그 처리 처럼 업무 로직을 지원하기 위한 하부 기술이나 공통 기술들
- 업무 로직은 숫자도 많고 한번 개발해야 하면 컨트롤러, 서비스, 리포지토리 처럼 어느정도 유사한 패턴이 있음. 이런 경우 자동 기능을 적극 사용하는 것이 좋음. 보통 문제가 생겨도 어떤 곳 에서 문제가 발생했는지 명확하게 파악하기 쉬움
- 기술 지원 로직은 업무 로직과 비교해서 그 수가 매우 적고, 보통 애플리케이션 전반에 걸쳐서 광범위하게 영향을 미침. 그리고 업무 로직은 문제가 발생했을 떄 문제가 명확하지만 기술 지원 로직은 적용이 잘 되고 있는지 아닌지 조차 파악하기 어려운 경우가 많음. 그래서 이런 기술 지원 로직 들은 가급적 수동 빈 등록을 사용하여 명확하게 들어내는 것이 좋음
#### 비즈니스 로직 중에서 다형성을 적극 활용할 때
- 스프링 부터가 아니라 내가 직접 기술 지원 객체를 스프링 빈으로 등록한다면 수동으로 등롣해서 명확하게 들어내는 것이 좋음
- 다형성을 적극 활용하는 비즈니스 로직은 수동 등록을 고민해보는 것이 좋음