# DI

### 프레임워크(Framework)란?
- 시작하기전에 애플리케이션 프레임워크란 소프트웨어 개발자가 응용 소프트웨어의 표준 구조를 구현하기 위해 사용하는 것
- 프로그래밍에서 특정 운영 체제를 위한 응용 프로그램 표준 구조를 구현하는 클래스와 라이브러리의 모임으로 프레임워크라고 부름
- 즉, 프레임워크란 재사용할 수 있는 수많은 코드를 통합한 것
    - 기본적인 구조와 기능을 직접 구현할 필요 없이 프레임워크가 제공하는 기능을 활용해 개발 시간을 단축하고 생산성을 높일 수 있도록 도움
    - 이렇게 표준화된 구조를 통해 유지보수가 용이해지고, 팀 간 협업 시 코드의 일관성을 유지할 수 있음

<br>

### ApplicationContext?
- 스프링 컨테이너의 구현체 중 하나인 ApplicationContext 는 가장 널리 사용되는 컨테이너로 스프링에서 빈(Bean)을 관리하는 스프링의 핵심 IoC 컨테이너
    - BeanFactory : 가장 단순한 형태의 IoC 컨테이너로 지연 초기화를 통해 빈을 필요할 때 생성하지만 현재는 거의 사용되지 않고 주로 ApplicationContext 기반 인터페이스로 남아 있음
    - ApplicationContext : BeanFactory를 확장한 인터페이스로 즉시 초기화를 지원하며 더 많은 기능(트랜잭션, AOP, 이벤트 처리 등)을 제공하여 웹 애플리케이션을 포함한 대부분의 스프링 애플리케이션에서 사용됨
- 

```java
// ApplicationConext 의 구현체

// XML 설정 파일을 클래스 경로에서 로드해서 객체를 관리하는 방법
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");


// Java 설정 파일에서 빈을 등록하고 주입하는 방법
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

/*
WebApplicationContext : 웹 애플리케이션에서 사용되는 컨테이너로, 서블릿 컨텍스트와 통합됨

*/

```

<br>

### DI(Dipendency Injection, 의존성 주입)
- 