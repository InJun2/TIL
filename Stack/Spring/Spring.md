# Spring

### Spring?
- 스프링은 엔터프라이즈급 애플리케이션 개발에 필요로 하는 경량형 프레임 워크.
- 프로젝트의 규모가 커질수록 스트럿츠 보다는 스프링 프로젝트가 많이 활용이 되고 있는 추세. J2EE에서 제공하는 대부분의 기능을 지원하기 때문에 JAVA개발에 있어서 대표적인 프레임워크로 자리잡고 있으며 JDBC를 비롯하여 iBatis, 하이버네이트, JPA등 DB처리를 위해 널리 사용되는 라이브러리와 연동을 지원하고 있음. 또한 전자정부 표준프레임워크의 기반이 되는 기술이기 때문에 스프링 프레임워크의 활용도는 더욱 높아지고 있음.
- 기존 EJB(Enterprise Java Bean)는 비즈니스 객체들을 관리하는 컨테이너를 만들어서 필요할 때마다 컨테이너로부터 객체를 받는 식으로 관리하여 엔터프라이즈급 어플리케이션 개발을 단순화하기 위해 개발한 스펙이 존재하였음 -> 해당 EJB는 의존성을 해결하려 했지만 무겁고 복잡하고 비즈니스 로직에 특정 기술이 종속되는 것이 큰 문제점 존재하였음 -> 해당 접근 개념은 좋았으나 자체적 문제가 존재하였고 이러한 개념을 바탕으로 POJO를 이용한, J2EE framework인 스프링이 등장
- 애플리케이션 프레임워크로 보통 프레임워크는 애플리케이션의 특정 계층에서 동작하는 한가지 기술 분야에 집중하는데 스프링은 이와 다르게 애플리케이션 개발의 전 과정을 빠르고 편리하며 효율적으로 진행하는데 일차적인 목표를 두는 프레임 워크
- Spring의 톰캣을 포함한 다수의 웹 서버는 멀티 스레드 방식을 따름 -> 클라이언트 요청이 있을 때마다 Thread을 생성하여 요청을 처리
- 오픈소스로 구성되어 있음

>#### 주요 기술 설명 
>- 경량 컨테이너 : 스프링은 객체를 담고있는 컨테이너로써 자바 객체의 생성과 소멸과 같은 라이프사이클을 관리하고,언제든 필요한 객체를 가져다 사용할 수 있도록 해줌 // 라이프사이클은 시스템의 프로비저닝에서부터 운영 및 사용종료까지를 의미
>- DI ( Dependency Injection : 의존성 주입 ) : 별도의 설정 파일을 통해 객체들간의 의존 관계들을 설정할 수 있음, 객체들 간의 느슨한 결합을 유지하고 직접 의존하고 있는 객체를 굳이 생성하거나 검색할 필요성이 없어짐 ( 프로그래밍에서 구성요소 간의 의존 관계가 소스 코드 내부가 아닌 외부의 설정파일을 통해 정의되는 방식, 즉 유연한 확장이 가능함 ), 객체지향 원칙 중 개방 폐쇠 원칙(OCP)
>- AOP ( Aspect Oriented Programming : 측면 지향 프로그래밍 ) : 문제를 바라보는 관점을 기준으로 프로그래밍하는 기법, 문제를 해결하기 위한 핵심 관심 사항과 전체에 작용되는 공통 관심사항을 기준으로 프로그래밍 함으로써 공통 모듈을 여러 코드에 쉽게 적용 할 수 있도록 함. 스프링은 자체적으로 프록시 기반의 AOP를 지원. 적용 기법으로는 다이나믹 프록시를 같이 이용하거나 AspectJ 오픈소스 AOP 툴 사용이 존재
>- POJO ( Plain Old Java Object ) : 일반적인 자바 객체, 객체지향적이고 특정 기술과 규약, 환경에 종속적이지 않음. 
>- IoC ( Inversion of Control : 제어의 반전 ) : 스프링의 핵심. 자바의 객체 생성 및 의존관계에 있어 제어권은 개발자에게 있었으나 서블릿과 EJB가 등장하면서 기존의 제어권이 서블릿 컨테이너 및 EJB 컨테이너에게 넘어가게 됨, 스프링 프레임워크에서도 객체에 대한 생성과 생명주기를 관리할 수 있는 기능을 제공하고 있는데 이런 이유로 스프링 컨테이너 라고 부름 
    - 스프링 컨테이너의 구현체 중 하나인 ApplicationContext 는 가장 널리 사용되는 컨테이너로 스프링에서 빈(Bean)을 관리하는 스프링의 핵심 IoC 컨테이너
    - BeanFactory : 가장 단순한 형태의 IoC 컨테이너로 지연 초기화를 통해 빈을 필요할 때 생성하지만 현재는 거의 사용되지 않고 주로 ApplicationContext 기반 인터페이스로 남아 있음
    - ApplicationContext : BeanFactory를 확장한 인터페이스로 즉시 초기화를 지원하며 더 많은 기능(트랜잭션, AOP, 이벤트 처리 등)을 제공하여 웹 애플리케이션을 포함한 대부분의 스프링 애플리케이션에서 사용됨

<br>

### 스프링 삼각형 (핵심 개념)
>- POJO (Plain Old Java Object) : 스프링에서는 특별한 규약이나 프레임워크에 의존적이지 않은 순수한 자바 객체를 사용. 개발자가 환경에 종속되지 않는 코드를 작성할 수 있도록 도와줌. 스프링을 사용하면 자바 객체를 특별한 클래스로 상속하거나 인터페이스를 구현할 필요가 없음

>- DI/IoC (Dependency Injection / Inversion of Control) : 객체가 직접 다른 객체를 생성하는 대신 외부 설정을 통해 필요한 의존성을 주입받는 것을 의미. 제어의 반전(IoC) 는 객체의 생성과 생명주기를 개발자가 아닌 스프링 컨테이너가 관리하도록 하는 개념으로 IoC 개념 안에 DI가 있는 것. 이러한 개념을 통해 객체간의 느슨합 결합(loose coupling)을 유지하며 코드의 확장성과 유연성이 높아짐

>- PSA (Portable Service Abstraction, 휴대용 서비스 추상화) : PSA는 특정 기술에 종속되지 않는 일관된 방식의 서비스 추상화를 의미 (인터페이스와 구현체를 분리하여 코드 변경을 최소화). 스프링은 다양한 기술 스택(JDBC, 트랜잭션 등)과의 연동 시 환경에 구애받지 않고 같은 방식으로 코드를 작성할 수 있게함. 개발자는 이를 통해 일관된 프로그래밍 모델로 개발할 수 있고 이는 애플리케이션의 유지보수성과 확장성을 크게 높임

```java
// PSA 개념 숙지 미흡으로 해당 예시만 작성
/*
    PSA의 핵심은 인터페이스와 구현체를 분리하여 다양한 환경에 적응할 수 있도록 하는 것
*/

// DataSource 인터페이스를 사용하되 다양한 커넥션 풀 구현체를 쉽게 교체할 수 있음
@Bean
public DataSource dataSource() {
    return new HikariDataSource();  // 구현체를 변경해도 코드에는 영향이 없음.
}

```

<br>

### 스프링 삼각형과 AOP 정리

| **구분**             | **설명**                                                                 | **역할**                                       | **예시**                                  |
|----------------------|--------------------------------------------------------------------------|------------------------------------------------|------------------------------------------|
| **POJO**             | 특별한 상속이나 구현 없이 순수한 자바 객체 사용                          | 환경에 종속되지 않는 객체지향 코드 작성 지원     | `@Component`, `@Service`로 등록된 객체 자체 |
| **DI / IoC**         | 객체 생성과 생명주기 관리를 스프링 컨테이너가 담당하고, 필요한 객체 주입   | 느슨한 결합 유지 및 의존성 관리                 | 메서드, 필드, 생성자 주입을 통한 객체 의존성 주입              |
| **PSA**              | 특정 기술에 종속되지 않는 추상화된 프로그래밍 모델 제공                   | 코드 변경 없이 다양한 기술 스택 지원            | `JdbcTemplate` 등 인터페이스와 구현체 분리      |
| **AOP**              | 공통 관심사를 핵심 로직과 분리하여 적용                                   | 로깅, 트랜잭션 등의 부가 기능을 코드 중복 없이 적용 | `@Transactional`로 트랜잭션 관리          |

<br>

### 스프링 구조

![Spring Structure](./img/spring_structure.png)

#### 1. Spring Core
Spring Core는 Spring Container을 의미. core라는 말 그대로 Container는 Spring Framework의 핵심이며 그중 핵심은 Bean Factory Container. 그 이유는 바로 Bean Factory는 IOC패턴을 적용하여 객체 구성 부터 의존성 처리까지 모든 일을 처리하는 역할을 함 
 
#### 2. Spring Context
Spring context는 Spring Framework의 context 정보들을 제공하는 설정 파일. Spring Context에는 JNDI, EJB, Validation, Scheduiling, Internaliztaion 등 엔터프라이즈 서비스들을 포함
 
#### 3. Spring AOP
Spring AOP module은 Spring Framework에서 관점지향 프로그래밍을 할 수 있고 AOP를 적용 할수 있게 도와주는 Module
 
#### 4. Spring DAO
DAO란 Data Access Object의 약자로 Database Data에 접근하는 객체. Spring JDBC DAO는 추상 레이어를 지원함으로써 코딩이나 예외처리 하는 부분을 간편화 시켜 일관된 방법으로 코드를 짤 수 있게 도와줌
 
#### 5. Spring ORM
ORM이란 Object relational mapping의 약자로 간단하게 객체와의 관계 설정. Spring에서는 Ibatis, Hibernate, JDO 등 인기있는 객체 관계형 도구(OR도구)를 사용 할 수 있도록 지원
 
#### 6. Spring Web
Spirng에서 Web context module은 Application module에 내장되어 있고 Web기반의 응용프로그램에 대한 Context를 제공하여 일반적인 Web Application 개발에 필요한 기본적인 기능을 지원. 그로인해 Jakarta Structs 와의 통합을 지원
 
#### 7. Spring MVC
Spring에서는 MVC에서는 Model2 구조로 Apllication을 만들 수 있도록 지원. MVC (Model-View-Controller) 프레임 워크는 웹 응용 프로그램을 작성하기위한 완전한 기능을 갖춘 MVC를 구현. MVC 프레임 워크는 전략 인터페이스를 통해 고급 구성 가능하며 JSP, Velocity, Tiles, iText 및 POI를 포함한 수많은 뷰 기술을 지원함.

>#### 인프런 강의 정리 링크 ( 김영한 강사님의 스프링 핵심원리 - 기본편 )
>1. [스프링 컨테이너](../Inflearn/스프링핵심원리-김영한강사/SpringContainer-Bean.md)
>2. [자동 의존성 주입](../Inflearn/스프링핵심원리-김영한강사/Auto%20DI.md)
>3. [싱글톤 컨테이너](../Inflearn/스프링핵심원리-김영한강사/Singleton%20Container.md)

<br>

![Spring MVC](./img/spring_mvc.png)

### Spring MVC 동작 순서
#### Request -> DispatcherServlet -> HandlerMapping -> Controller -> Service -> DAO -> DB -> DAO -> Service -> Controller -> DispatcherServlet -> ViewResolver -> View -> Response

>1. DispatcherServlet : 애플리케이션으로 들어오는 모든 Request를 받는 부분. Request를 실제로 처리할 Controller에게 전달하고 그 결과값을 받아서 View에 전달하여 적절한 응답을 생성할 수 있도록 흐름을 제어 ( web.xml에 설정되어 있는대로 요청을 받아옴 )
>2. HandlerMapping  : Request URL에 따라 각각 어떤 Controller가 실제로 처리할 것인지 찾아주는 역할
>3. Controller : Request를 직접 처리한 후 그 결과를 다시 DispatcherServelt에 돌려주는 역할 ( MVC 패턴은 Controller로 들어와서 Service, Dao를 거쳐 비즈니스 로직을 처리한 후 Controller에서 결과를 리턴 한다 )
>4. ModelAndView : Controller가 처리한 결과와 그 결과를 보여줄 View에 관한 정보를 담고 있는 객체
>5. ViewResolver : View 관련 정보를 갖고 실제 View를 찾아주는 역할 ( servlet-context.xml에 존재 )
>6. View : Controller가 처리한 결과값을 보여줄 View를 생성 ( 클라이언트에게 보내줌 )

<br>

#### Spring project 에서 사용되는 용어 혹은 파일
- src : 최근 개발 트렌드에서는 main과 test로 디렉토리 안에 존재하고 main에는 java와 resource가 존재 ( java 파일에는 패키지, 클래스 존재 resource 파일에는 정적 파일 존재 )
- build.gradle : project에서 사용되는 설정 파일
- POM ( Project Object Model ) : Maven Project의 설정 부분을 기록해 놓은 XML 형식의 파일
- project : 루트 태그, 문서의 단일한 이름을 특정하기 위해 적는 namespace
- parent : 하위프로젝트가 상위 프로젝트의 pom.xml을 상속받기 위해 사용되는 태그, 태그안의 그룹아이디, 아티팩트 아이디, 버전은 모두 상속받음
- groupId : 자바에서 패키지 이름으로 회사나 단체의 유니크한 이름을 명시. 제작자나 회사, 혹은 단체를 식별하기 위해 작성
- artifactId : 공식문서에서는 프로젝트를 부를 때 아티팩트 아이디로 부른다고 한다. 프로젝트의 이름과 같은 의미
- packaing : 프로젝트가 완성되고 소스코드를 파일로 패키징하여 배포할 때의 방법을 명시해 놓은 것, jar (Java Archive)와 war(Web Application Archive)의 두 가지 방법으로 패키징해서 배포할 수 있다. ( jar는 자바 환경 위에서 동작할 수 있는 어플리케이션, war는 웹 어플리케이션 전용의 패키징이고 웹 서버가 내장되어 있어서 웹 사이트 배포에 특화됨 )
- name : 프로그램의 이름. 일반적으로 아티팩트 아이디를 그대로 사용
- properties : pom.xml에서 사용하는 속성들에 대한 명시. 
- dependencies : 프로젝트가 참조하는 외부라이브러리들이 해당 태그로 감싸져서 명시되어 있음. 
- scope : 라이브러리가 사용되는 범위를 지정한 것. 언제 사용되는지를 명시해 놓은 것
- gradle : gradle과 관련하여 gradle을 사용하는 폴더 ( or maven )
- plugin : 빌들에서 사용할 플러그인을 명시해 놓은 것. 간단히 설명하면 추가기능으로 기본 소프트웨어를 지원해서 특수한 기능을 확장할 수 있도록 설계된 프로그램
- .idea : 프로젝트에서 사용하는 설정 파일

<br>

### 스프링 부트란?
- Spring framework 기반 프로젝트를 복잡한 설정 없이 쉽고 빠르게 만들어주는 라이브러리 -> 스프링을 더 쉽게 이용하기 위한 도구
- 스프링 부트는 스프링을 쉽게 사용할 수 있도록 필요한 여러가지 복잡한 설정을 대부분 미리 세팅 해놓았을 뿐만 아니라 WAS도 별도의 설절없이 바로 웹개발에 들어갈 수 있도록 만들어 둠

#### 스프링 부트의 장점
1. 라이브러리 관리 자동화 : 스프링 부트의 Starter 라이브러리를 등록해서 라이브러리 의존성을 간단히 관리
2. 라이브러리 버전 자동 관리 : 기존 스프링 라이브러리는 버전을 직접 입력해야 헀지만 스프링 부트 버전을 입력해 놓으면 spring 라이브러리 뿐만 아니라 third party 라이브러리들도 호환되는 버전으로 알아서 업데이트 및 관리해줌
3. 설정 자동화 :  @ComponentScan을 이용하여 Component, @Controller, @Repository, @Service 등 의 어노테이션이 붙어있는 객체들을 스캔해 자동으로 Bean에 등록해주고, @EnableAutoConfiguration 어노테이션을 선언해서 스프링에서 자주 사용했던 설정들을 알아서 등록 해줌
4. 내장 Tomcat : 스프링 부트는 WAS인 Tomcat을 내장하고 있다. @SpringBootApplication 어노테이션이 선언되어 있는 클래스의 mail()메소드를 실행하는 것만으로 서버를 구동시킬 수 있다. 내장 톰캣을 사용하기 위한 별도 설정없이 web starter 의존성만 추가해주면 됨
5. 독립적으로 실행 가능한 JAR : 웹 프로젝트는 war 파일로 패키징 해야하는데 스프링 부트는 내장 톰캣을 지원하기 때문에 jar로 패키징해서 웹 애플리케이션 실행이 가능 -> 자바 옵션만으로 더 간단하게 배포 가능

<br>

### 스프링 부트 스타터 프로젝트 종류
- spring-boot-starter-web-services : SOAP 웹 서비스 ( 다른 언어로 다른 플랫폼에서 빌드된 애플리케이션이 통신할 수 있도록 설계된 최초의 표준 프로토콜 )
- spring-boot-starter-web : Web, RESTful 응용프로그램
- spring-boot-starter-test : Unit testing, Integration Testing
- spring-boot-starter-jdbc : 기본적인 JDBC
- spring-boot-starter-hateoas : HATEOAS 기능을 서비스에 추가
- spring-boot-starter-security : 스프링 시큐리티를 이용한 인증 권한
- spring-boot-starter-data-jpa : Spring Data JPA with Hibernate
- spring-boot-starter-cache : 스프링 프레임워크에 캐싱 지원 가능
- spring-boot-starter-data-rest : Spring Data REST를 사용하여 간단한 REST 서비스 노출

#### 개발이나 유지보수에 도움을 주는 스타터
- spring-boot-starter-actuator: 여러분의 애플리케이션 추적하거나 모니터링하는 등의 기능을 사용할 수 있도록 함 
- spring-boot-starter-undertow, spring-boot-starter-jetty, spring-boot-starter-tomcat: 내장된 서블릿 컨테이너를 선택할 수 있도록 함
- spring-boot-starter-logging: logback 을 사용해서 로깅할 수 있도록 함
- spring-boot-starter-log4j2: Log4j2 를 사용해서 로깅할 수 있도록 함

<br>

<div style="text-align: right">22-07-14</div>

-------

## Reference
- https://hoon93.tistory.com/56
- https://intro0517.tistory.com/151
- https://m.blog.naver.com/dktmrorl/222117116193
- https://12bme.tistory.com/157
- https://bamdule.tistory.com/158
- https://server-engineer.tistory.com/739