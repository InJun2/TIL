# Spring vs EJB

### EJB (Enterprise Java Bean)
- 자바 객체를 재사용 가능하도록, 컴포넌트화 시킬수 있는 코딩 방식을 정의한 것을 의미 -> 분산 애플리케이션을 지원하는 컴포넌트 기반의 객체
- 엔터프라이즈급 어플리케이션 개발을 단순화하기 위해 발표한 스펙
- 주로 JSP는 화면처리, EJB는 업무 로직 처리를 진행
- 대량의 트랜잭션을 안정적으로 처리하게 해주고, 분산 트랜잭션을 지원하는 등 장점들도 분명히 존재하나 단점들이 부각됨
- 개발을 하다보면 많은 객체를 만들게 되는데, 이러한 비즈니스 객체들을 관리하는 컨테이너를 만들어서 필요할 때마다 컨테이너로부터 객체를 받는 식으로 관리하기 위해 탄생
- 취지는 좋았으나 서비스가 구현해야하는 실제 비즈니스 로직보다 EJB 컨테이너를 사용하기 위한 상투적인 코드가 많은 불편함 존재
- 설정이 매우 복잡하여 프로젝트 자체가 특정 기술에 종속, 기술 침투(Invasive)되는 문제가 있었음 (의존성을 해결하려 했지만 무겁고 복잡하고 비즈니스 로직에 특정 기술이 종속되는 것이 큰 문제점 존재)

<br>

#### EJB의 장점
>1. 정형화된 비즈니스 계층 제공
>2. 선언적인 트랜잭션 관리 제공
>3. 다양한 클라이언트에 대한 지원 가능
>4. 분산기능 제공
>5. 비즈니스 객체를 여러 서버에 분산시키는 것이 가능

<br>

#### EJB의 단점
>1. 단위테스트가 어려움
>2. 복잡하고 불필요한 메서드를 구현해야 함
>3. 특정 기술에 종속적인 코드
>4. 개발과 배포가 불편함
>5. 속도가 느림
>6. EJB 컨테이너에 종속적으로 이식성이 떨어짐

<br>

### EJB의 종류
1. 세션 빈 (Session Bean) 
    - DB 연동이 필요 없음
2. 엔티티 빈 (Entity Bean)
    - 데이터베이스의 데이터를 관리하는 객체
    - Insert(삽입), Update(수정), Delete(삭제), Select(조회)
    - DB 관련 쿼리는 자동으로 만들어지고, 개발자는 고급 업무 처리에 집중할 수 있음
    - DB가 수정되면 코드 수정 없이 다시 배포 (설정 문서 만들어서 복사)
3. 메시지 구동 빈 (Message-driven Bean)
    - JMS로 빈을 날려줌

<br>

### EJB 컨테이너가 제공하는 것
- 트랜잭션 관리
- 인증과 접근 제어
- EJB 인스턴스 풀링
- 세션관리
- 지속성 메커니즘
- 데이터베이스 커넥션 풀링

<br>

### Spring
- 위의 상황에서 생긴 스프링 프레임워크. 프레임워크는 위의 단점들을 모두 극복함
- EJB의 단점을 극복하여 POJO를 기반으로 좋은 객체 지향 애플리케이션 개발을 위한 도구임
- 스프링의 주요 기술에는 경량컨테이너, DI, AOP, POJO, IoC 존재 -> 해당 내용의 설명은 아래 Spring 정리 내용 링크에 작성

#### 스프링의 장점
> 1. 단순화된 단위 테스팅
>       - EJB는 컨테이너 외부에서 실행하는 것이 어려웠음
>       - 스프링 프레임 워크는 의존성 주입(DI)를 통해 단위 테스트를 위해 전체 어플리케이션을 배포할 필요가 없어짐 -> 생산성 향상, 빠른 결함 발견(비용감소), 지속적인 통합(CI)시 자동화된 단위 테스트로 향후 결함을 예방
> 2. 복잡한 코드의 감소
>       - 여러 외부 라이브러리를 쉽게 사용이 가능하고 스프링의 특징 중 하나인 관점 지향 프로그래밍(AOP)을 통해 공통 로직을 하나로 작성이 가능함
> 3. 아키텍처의 유연성
>       - 스프링 프레임워크는 모듈 방식으로 스프링 코어 모듈위에 독립적인 모듈을 올려 완성함
>       - 애플리케이션의 모듈간의 결합을 줄이고 단위 테스트할수 있게 만드는 것에 중점을 두면서 사용자가 선택한 프레임워크와의 통합을 제공
>       - 원하는 기능 구현을 위해 프레임워크를 자유롭게 선택 가능

#### 스프링에 관한 이전 정리 내용
- [Spring 정리 내용](./Spring.md)
- [Spring 의 특징 IOC, DI, AOP](./IOC-DI-AOP.md)
- [Spring Container 정리 내용](./Spring_Container.md)

<br>

<div style="text-align: right">22-07-19</div>

-------

## Reference
- https://m.blog.naver.com/qhdqhdekd261/221690113143
- https://velog.io/@outstandingboy/Spring-왜-스프링-프레임워크를-사용할까-Spring-vs-EJB-JavaEE
- https://www.javatpoint.com/what-is-ejb
- https://rainbow97.tistory.com/entry/JAVA-EJBEnterprise-JavaBeans