# Component Scan

### 컴포넌트 스캔과 의존관계 자동 주입 시작
- 지금까지 스프링 빈을 등록할 때는 자바 코드의 @Bean이나 XML의 <bean> 등을 통해서 설정 정보에 직접 등록할 스프링 빈을 나열했음
- 스프링은 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 컴포넌트 스캔이라는 기능을 제공
- 또, 의존관계도 자동으로 주입하는 @Autowired 라는 기능도 제공
>- 컴포넌트 스캔을 사용하면 @Configuration 이 붙은 설정 정보도 자동으로 등록됨
>- @ComponentScan 은 @Component 가 붙은 모든 클래스를 스프링 빈으로 등록
>- 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용
>   - 빈 이름 기본 전략 : MemberServiceImpl Class -> memberServiceImpl
>   - 빈 이름 직접 지정 : 만약 스프링 빈의 이름을 직접 지정하고 싶으면 @Component("memberService2)처럼 이름을 부여하면 됨
>- Autowired 의존관계 자동 주입 : @ComponentScan으로 @Component를 모두 찾아 Bean으로 등록하는 것은 좋으나 주입이 되지 않음. 생성자에 @Autowired를 지정하면 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입
>- 이때 기본 조회 전략은 같은 빈을 찾아서 주입해주고 생성자에 파라미터가 많아도 다 찾아서 자동으로 주입

<br>

### 탐색 위치와 기본 스캔 대상
- ComponentScan에 basePackages를 지정하여 해당 해당 패키지를 포함한 하위 패키지만 탐색이 가능
- 패키지가 아닌 클래스로 탐색 시 basePackageClasses 를 이용하여 지정한 클래스의 패키지를 탐색 시작 위로 지정 
- default 일 경우 @ComponentScan을 붙인 클래스부터 시작하여 하위 패키지를 모두 탐색
- 권장하는 방법은 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는것. 최근 스프링 부트도 이 방법을 기본으로 제공 ( 예제 코드에서는 com.hello 바로 밑에 설정 파일에 둠 )
- 스프링 부트를 사용하면 스프링 부트의 대표 시작 정보인 @SpringBootApplication 를 이 프로젝트 시작 루트 위치에 두는 것이 관례 ( 해당 설정안에 @ComponentScan이 들어 있음 )
```java
@ComponentScan(
    basePackages = "hello.core.member", 
    basePackageClasses = AutoAppConfig.class,
    ...
)
```

<br>

### 컴포넌트 스캔 기본 대상
- @Component : 컴포넌트 스캔에서 사용
- @Controller : 스프링 MVC 컨트롤러에서 사용, 스프링 MVC 컨트롤러로 인식
- @Service : 스프링 비즈니스 로직에서 사용, 특별한 처리를 하지는 않지만 개발자들이 핵심 비즈니스 로직이 여기에 있다는 것을 인식하게 해줌 ( 비즈니스 계층을 인식하는데 도움이 됨 )
- @Repository : 스프링 데이터 접근 계층에서 사용, 스프링 데이터 접근 계층으로 인식하고 데이터 계층의 예외를 스프링 예외로 변환해줌
- @Configuration : 스프링 설정 정보에서 사용, 스프링 설정 정보로 인식하고 스프링 빈이 싱글톤을 유지하도록 추가 처리를 함
>- 해당 인터페이스의 소스 코드를 보면 @Component를 포함하고 있음
>- 어노테이션에는 사실 상속관계라는 것은 없고 이렇게 어노테이션이 특정 어노테이션을 들고 있는 것을 인식할 수 있는 것은 자바 언어가 지원하는 기능이 아니고 스프링이 지원하는 기능

<br>

### 필터
- includeFiltesr에 MyIncludeComponent 어노테이션을 추가해서 BeanA가 스프링 빈에 등록
- excludeFilters에 MyExcludeComponent 어노테이션을 추가해서 BeanB는 스프링 빈에 등록되지 않음
> @Component 면 충분하기 때문에 includeFilters를 사용할 일은 거의 없음. excludeFilters는 여러 가지 이유로 간혹 사용할 때가 있지만 많지 않다고 함

```java
@ComponentScan(
            includeFilters = @ComponentScan.Filter(classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(classes = MyExcludeComponent.class)
    )
```

#### Filter Type 옵션
- ANNOTATION : 기본 값, 어노테이션을 인식해서 동작
- ASSIGNABLE_TYPE : 지정한 타입과 자식 타입을 인식해서 동작
- ASPECTJ : AspectJ 패턴 사용
- REGEX : 정규 표현식
- CUSTOM : TypeFilter 이라는 인터페이스를 구현해서 처리

<br>

### 중복 등록과 충돌
- 컴포넌트 스캔에서 같은 빈 이름을 등록하면 충돌이 나는데 다음 두가지 상황이 존재
>1. 자동 빈 등록 vs 자동 빈 등록
>2. 수동 빈 등록 vs 자동 빈 등록
- 1번의 경우에는 컴포넌트 스캔에 의해 자동으로 스프링 빈이 등록되는데 스프링이 ConflictingBeanDefinitionException 예외 발생
- 2번의 경우에는 수동 빈이 우선권을 가짐. 수동 빈이 자동 빈을 오버라이딩 해버림 -> 수동 빈 등록시 로그 출력
- 2번의 경우 개발자가 의도적으로 이런 결과를 기대했다면 수동이 우선권을 가지는 것이 좋으나 현실은 개발자가 의도적으로 설정하는 것 보다는 여러 설정들이 꼬여서 이런 결과가 만들어지는 경우가 대부분 -> 그러면 매우 잡기 어려운 버그가 만들어짐. 항상 잡기 힘든 버그는 애매한 버그
- 스프링 부트에서는 2번의 경우 스프링 부트 에러를 발생한다 -> 해당 경우 로그를 출력하고 설정을 콘솔에 출력해줌

---
- 2022-07-10