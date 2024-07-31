# JPA (Java Persistence API)

### JPA?
- 자바 진영에서 ORM(Object-Relational Mapping) 기술표준으로 사용되는 인터페이스 모음
- 실제적으로 구현된 것이 아니라 구현된 클래스와 매핑을 해주는 프레임워크
    - 대표적인 JPA 구현 오픈소스로는 Hibernate 존재

<br>

### ORM(Obejct-Relational Mapping)
- 어플리케이션 Class와 RDB(Relational Database) 테이블을 매핑하는 것
- 기술적으로는 어플리케이션의 객체를 RDB 테이블에 자동으로 영속화 해주는 것

#### 장점
- SQL문이 아닌 Method를 통해 DB를 조작하여 개발자는 객체 지향의 로직 구현에 집중 가능
    - 비즈니스 로직을 구성하는데만 집중할 수 있음
- Query와 같은 선언문, 할당 같은 부수적인 코드가 줄고 각종 객체에 대해 코드를 별도로 작성해 코드의 가독성을 높임
- 매핑하는 정보가 Class로 명시되었기 때문에 ERD를 보는 의존도를 낮추고 유지보수 및 리팩토링에 유리
- ORM을 사용하면 다른 데이터베이스 사용으로 변경해도 DB 만 변경하고 기존 사용 쿼리를 수정할 필요가 없음

<br>

#### 단점
- 프로젝트의 규모가 크고 복잡하여 설계가 잘못되기 쉬움
    - 속도 저하 및 일관성을 무너뜨리는 문제점
    - 복잡하고 무거운 쿼리에 있어 속도를 위해 직접적인 쿼리 튜닝을 해야하는 경우 SQL문을 사용해야 함
    - 러닝커브 존재

<br>

### JPA 사용 장점
- JPA는 자바 표준 API로 특정 프레임워크에 종속되지 않으며 표준을 따르는 다른 구현체로 쉽게 전환 가능
- ORM 을 통한 객체 지향 프로그래밍과 관계형 데이터 베이스 간의 불일치 해결
    - 객체를 데이터베이스 테이블에 매핑하고 SQL을 직접 작성하지 않아도 데이터베이스 연산 수행 가능
- 어노테이션을 사용하여 매핑 정보를 제공하여 java 기반 설정이 가능
- JPA는 ORM 장점과 동일하게 특정 데이터베이스에 종속되지 않으며 데이터베이스를 변경하더라도 JPA 설정만 변경하면 기존 코드를 수정할 필요없이 새로운 데이터베이스 사용이 용이
- 엔티티매니저를 통해 1차 캐시, 2차 캐시를 사용하여 반복되는 데이터베이스 접근을 줄임. 이는 부하를 줄이고 성능을 향상시킴
- 일관된 트랜잭션 처리를 제공. JTA(Java Transaction API)와의 통합을 통해 분산 트랜잭션도 처리 가능
- 비즈니스 로직과 데이터 접근 로직이 분리되어 코드의 가독성과 유지보수성을 높일 수 있음

<br>

### Entity Manager
- [Entity Manager 정리](https://github.com/InJun2/TIL/blob/main/Stack/Spring/JPA_Entity_Manager.md)
- [JPA Dirty Checking 정리](https://github.com/InJun2/TIL/blob/main/Stack/Spring/JPA_Dirty_Checking.md)

<br>

## 질문

### 1. 영속성은 어떤 기능을 하는지? 이게 정말 성능 향상에 큰 도움이 되는지
- 영속성(Persistence)은 데이터베이스와 어플리케이션 객체 간의 상태를 관리하는 개념
- 영속성 컨텍스트(Persistence Context)는 엔티티 객체의 생명주기를 관리하고 엔티티 객체는 이 컨텍스트 내에서 데이터베이스와 동기화 됨
- 영속성 컨텍스트는 1차 캐시를 제공하고, 1차 캐시는 1차 캐시는 동일한 트랜잭션 내에서 엔티티 매니저가 관리하는 엔티티 객체를 메모리에 저장함
    - 이를 통해 같은 엔티티를 여러 번 조회할 때 데이터베이스에 반복적으로 접근하지 않아도 됨
```java
EntityManager em = entityManagerFactory.createEntityManager();
em.getTransaction().begin();

Person person = em.find(Person.class, 1L);  // 첫 번째 조회, DB 접근
Person samePerson = em.find(Person.class, 1L);  // 두 번째 조회, 1차 캐시 사용

em.getTransaction().commit();
em.close();
```

- 영속성 컨텍스트는 트랜잭션이 끝날 때 까지 엔티티를 추적함. 변경 감지 (Dirty Checking)를 통해 트랜잭션이 종료된 경우 변경된 부분만 데이터베이스에 반영함
- 영속성 컨텍스트의 객체는 지연 로딩 (Lazy Loading)을 통해 엔티티 객체의 연관된 데이터는 실제로 필요할 때 로드됨. 이는 초기 데이터베이스 조회 시 불필요한 데이터를 가져오지 않도록 하여 성능을 최적화함
- JPA는 데이터베이스에 대한 쓰기 작업을 트랜잭션 종료 시점까지 지연하는 쓰기 지연 (Write-behind)를 통해 여러 쓰기 작업을 하나의 배치로 처리하여 성능을 향상시킴
- JPA 구현체(Hibernate)는 2차 캐시를 지원하여 2차 캐시는 영속성 컨텍스트를 벗어난 데이터도 캐싱하여 데이터베이스 부하를 줄임

<br>

### 2. N + 1 문제란?
- JPA의 N + 1 문제는 단일 쿼리로 다수의 엔티티를 조회한 후 각 엔티티의 연관된 엔티티를 개별 쿼리로 조회하는 과정에서 발생하는 성능 문제
- JPA에서 흔히 발생하는 성능 이슈 중 하나로 하나의 쿼리를 통해 특정 엔티티 리스트를 조회한 후, 각 엔티티의 연관된 엔티티를 조회할 때 추가적인 쿼리가 발생하여 총 N+1 개의 쿼리가 실행되는 문제를 의미

```java
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
}

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}

// 모든 주문을 조회하고 각 주문에 연관된 고객을 출력한다고 가정할 때
/*
    select o from order o 모든 주문을 조회하는 하나의 쿼리 실행시
    각 주문에 대해 order.getCustomer().getName() 을 호출하여 고객 정보를 조회하는 N개의 쿼리가 실행되어 총 N+1 개의 쿼리가 실행됨
*/
List<Order> orders = em.createQuery("SELECT o FROM Order o", Order.class).getResultList();

for (Order order : orders) {
    System.out.println(order.getCustomer().getName());
}
```
- 이를 해결하기 위한 방법은 다음과 같음
1. 즉시 로딩 (Eager Loading) 사용
    - 연관된 엔티티를 즉시 로딩하도록 설정하여 단일 쿼리로 모든 데이터를 가져옴
    - 'fetch = FetchType.EAGER'로 설정하면 Order를 조회할 때 Customer도 함께 조회함
    - 하지만 즉시 로딩을 남용하면 불필요한 데이터까지 로딩하게 되어 다른 성능 문제가 발생할 수 있어 따라서 신중하게 사용이 필요
```java
@ManyToOne(fetch = FetchType.EAGER)
private Customer customer;
```
2. Fetch Join 사용
    - JPQL에서 'FETCH JOIN'을 사용하여 필요한 연관 데이터를 함께 조회함
    - JOIN FETCH 구문을 사용하면 Order와 Customer를 한 번에 가져오는 단일 쿼리를 생성함
    - 하지만 남용하면 연관된 너무 많은 양의 데이터를 한번에 가져올 수 있고, 복잡한 연관 관계가 있는 경우 페치 조인으로 인해 JPQL 쿼리가 복잡해질 수 있음
```java
List<Order> orders = em.createQuery("SELECT o FROM Order o JOIN FETCH o.customer", Order.class).getResultList();
```
3. 배치 페치 (Batch Fetching) 사용
    - 배치 페치는 여러 개의 연관된 엔티티를 한 번에 로드하는 기법
    - 여러 연관된 엔티티를 한 번에 로드하여 데이터베이스 접근을 줄이는 기법으로 기본적으로 JPA는 연관된 엔티티를 하나씩 로드하지만, 배치 페치를 사용하면 한 번에 여러 엔티티를 로드
    - @BatchSize(size = 10) 애노테이션을 사용하면 한 번에 최대 10개의 연관된 Customer 엔티티를 로드하여 각 Order 엔티티마다 개별 쿼리를 실행하지 않고 묶음으로 데이터를 가져옴
        - 예를 들어, Order 엔티티 20개를 조회하면, 고객 데이터를 조회하기 위해 두 번의 배치 쿼리가 실행됨
    - Hibernate와 같은 JPA 구현체에서 제공하는 배치 페치를 사용
```java
@BatchSize(size = 10)
@ManyToOne(fetch = FetchType.LAZY)
private Customer customer;
```
4. Entity Graph 사용
    - JPA 2.1부터 도입된 엔티티 그래프를 사용하여 동적으로 페치 전략을 정의할 수 있음
    - setHint 메서드를 통해 엔티티 그래프를 적용하여 필요할 때만 연관된 엔티티를 로드할 수 있음
```java
EntityGraph<Order> graph = em.createEntityGraph(Order.class);
graph.addAttributeNodes("customer");

List<Order> orders = em.createQuery("SELECT o FROM Order o", Order.class)
                       .setHint("javax.persistence.fetchgraph", graph)
                       .getResultList();
```