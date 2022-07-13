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
