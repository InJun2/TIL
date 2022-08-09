# Bean LifeCycle ( 빈 생명주기 )

### 빈 생명주기 콜백 시작
- 데이터베이스 커넥션 풀이나, 네트워크 소켓처럼 애플리케이션 시작 시점에 필요한 연결을 미리 해두고, 애플리케이션 종료 시점에 연결을 모두 종료하는 작업을 진행하려면 객체의 초기화와 종료 작업이 필요함
- 스프링 빈은 간단하게 객체생성 -> 의존관계 주입 라이프 사이클을 가짐
- 스프링 빈은 객체를 생성하고, 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가 완료됨. 그렇기 때문에 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야 함
> 스프링은 의존 관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공함. 또 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 주기 때문에 안전하게 종료 작업을 진행할 수 있음

<br>

#### 예제 코드

```java
// 실행 테스트
public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig{

        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }

// 연결 과정 단순화 -> 현재 url이 먼저 연결되지 않은 상태
public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

}
```
<br>

### 스프링 빈의 이벤트 라이프 사이클
1. 스프링 컨테이너 생성
2. 스프링 빈 생성
3. 의존관계 주입
4. 초기화 콜백
5. 사용
6. 소멸 전 콜백
7. 스프링 종료

> #### 객체와 생성과 초기화를 분리
>- 생성자는 필수 정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가짐. 반면에 초기화는 이렇게 생성된 값들을 활용해서 외부 커넥션을 연결하는 등 무거운 동작을 수행
>- 생성자 안에서 무거운 초기화 작업을 함께 하는 것 보다는 객체를 생성하는 부분과 초기화 하는 부분을 명확하게 나누는 것이 유지보수 관점에서 좋음. 물론 초기화 작업이 내부 값들만 약간 변경하는 정도로 단순한 경우에는 생성자에서 한번에 다 처리하는게 더 나을 수 있음

<br>

### 스프링의 빈 생명주기 콜백 지원
#### 1. 인터페이스(InitializingBean, DisposableBean)
- 이 인터페이스는 스프링 전용 인터페이스, 해당 코드가 스프링 전용 인터페이스에 의존
- 초기화, 소멸 메서드의 이름을 변경할 수 없음
- 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없음
- 현재는 더나은 방법이 있어서 거의 사용하지 않음
```java
public class NetworkClient implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
```
#### 2. 설정 정보에 초기화 메서드, 종료 메서드 지정
- 메서드 이름을 자유롭게 줄 수 있음
- 스프링 빈이 스프링 코드에 의존하지 않음
- 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있음

> 종료 메서드 추론
>- @Bean 의 destroyMethod 속성에는 특별한 기능이 있음
>- 라이븓러리 대부분 close, shutdown 이라는 이름의 종료 메서드를 사용
>- @Bean의 destroyMethod는 기본값이 '(inferred)' (추론)으로 등록되어 있음
>- 이 추론 기능은 close, shutdown 라는 이름의 메서드를 자동으로 호출해줌
>- 따라서 직접 스프링 빈으로 등록하면 종료 메서드는 따로 적어주지 않아도 잘 동작하고 만약 추론 기능을 사용하기 싫으면 destroyMethod="" 으로 빈 공백을 지정

```java
    @Configuration
    static class LifeCycleConfig {

        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }

    public class NetworkClient{

        public void init() {
            System.out.println("NetworkClient.init");
            connect();
            call("초기화 연결 메시지");
        }

        public void close() {
            System.out.println("NetworkClient.close");
            disconnect();
        }
```

#### 3. @PostConstruct, @PreDestroy 어노테이션 지원
- 최신 스프링에서 가장 권장하는 방법 -> @PostConstruct, @PreDestroy 기술을 사용하는 것이 좋음
- 어노테이션 하나만 붙이면 되므로 매우 편리
- 패키지를 잘보면 javax.annotation.PostConstruct 로 스프링에 종속적인 기술이 아니라 자바 표준
- 컴포넌트 스캔과 잘 어울림
- 유일한 단점은 외부 라이브러리에는 적용하지 못함. 외부 라이브러리를 초기화, 종료해야하면 @Bean의 기능을 사용하면됨 ( 위의 2번 )

```java
    public class NetworkClient{

        @PostConstruct
        public void init() {
            System.out.println("NetworkClient.init");
            connect();
            call("초기화 연결 메시지");
        }

        @PreDestroy
        public void close() {
            System.out.println("NetworkClient.close");
            disconnect();
        }
```