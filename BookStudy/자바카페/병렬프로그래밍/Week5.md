# 5 Week ( 8 챕터 ~ 9 챕터 )

## 8. 스레드풀 활용

### 궁금한 부분
- Executor 프레임워크는 정의 부분과 실행 부분을 서로 분리 시켜줌
- 단일 스레드로 동작하는 Executor ?
- ThreadPoolExecutor는 beforeExecute, afterExecute, terminated와 같은 훅을 제공한다는데 여기서 훅이란?
    - ThreadPoolExecutor 라이브러리 확인하기
- 쓰레드풀에 지정된 설정의 스레드 저장하기
    - 해당 기능으로 책에서 스레드 이름을 지정한 것으로 이해하였음
- SynchronousQueue는 따지고 보면 큐가 아니며 단지 스레드 간에 작업을 넘겨주는 기능이라고 하는데 어떻게 동작하는지
- 최근도 스레드풀을 생성할 때 Executor를 쓰는지 혹은 다른 라이브러리가 있는지?

<br>

### Executor 정의와 실행 분리
- Executor 프레임워크는 Java에서 스레드 기반 비동기 실행을 추상화하고 간소화하기 위해 사용되는 프레임워크
- 정의 부분 : 작업을 정의하고 스레드 풀을 구성하는 단계
    - ExecutorService의 구현체를 생성하고, 적절한 스레드 풀 구성 및 옵션을 설정
- 실행 부분 : 작업을 제출하고 실행하는 단계
    - 정의된 ExecutorService를 사용하여 작업을 제출하고, ExecutorService가 제공하는 메서드를 사용하여 작업을 실행하고 대기

<br>

### 단일 스레드로 동작하는 Executor
- Executors.newSingleThreadExecutor()로 생성
- 한 번에 하나의 작업을 처리하므로 순차적으로 실행
- 단일 스레드 이므로 별도의 스레드 관리가 필요하지 않음
- 작업을 그저 큐에 넣는다기보다 스레드의 생성, 관리, 실행을 Executor에 의해 관리됨

<br>

### ThreadPoolExecutor는 beforeExecute, afterExecute, terminated와 같은 훅을 제공한다는데 여기서 훅이란?
- 훅(hook) 이란 소프트웨어에서 어떤 이벤트가 발생했을때 실행되는 사용자 정의 코드
    - 리액트에서 훅을 사용하는 이유라고 생각됨
- 해당 코드는 아래 ThreadPoolExecutor 예시에 작성되어 있음

<br>

### ThreadPoolExecutor
- ThreadPoolExecutor는 Executors 클래스의 newCachedThreadPool, newFixedThreadPool, newScheduledThreadPool 과 같은 팩토리 메소드에서 생성해주는 Executor의 기본적인 내용이 구현되어 있는 클래스
    - 팩토리 메소드를 사용해 만들어진 스레드 풀의 기본 실행 정책이 요구 사항에 맞지 않으면 ThreadPoolExecutor 클래스의 생성 메서드를 직접 호출해 스레드풀 생성

```java
public class Executors {
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }

    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }

    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>(),
                                      threadFactory);
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize);
    }

    public static ScheduledExecutorService newScheduledThreadPool(
            int corePoolSize, ThreadFactory threadFactory) {
        return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
    }
}

public class ThreadPoolExecutor extends AbstractExecutorService {
    // 이후 실행할 작업을 실행 대기열에 추가하고 이후 실행
    public void execute(Runnable command) {
        ...
    }

    // 이전에 제출된 작업이 실행되지만 새 작업은 허용되지 않는 순차적 종료를 시작. 이미 종료된 경우 호출은 추가 효과가 없음
    public void shutdown() {
        ...
    }

    // 활발하게 실행 중인 모든 작업을 중지하려고 시도하고, 대기 중인 작업의 처리를 중지하고, 실행 대기 중인 작업 목록을 반환함
    // 이러한 작업은 이 메서드에서 반환되면 작업 대기열에서 제거됨. 이 메서드는 실행 중인 작업이 종료될 때까지 기다리지 않음
    public List<Runnable> shutdownNow() {
        ...
    }

    // ThreadPoolExecutor가 종료되었는지 확인
    public boolean isShutdown() {
        return runStateAtLeast(ctl.get(), SHUTDOWN);
    }

    // 이 실행기가 shutdown 또는 shutdownNow 후에 종료하는 중이지만 완전히 종료되지 않은 경우 true를 반환
    public boolean isTerminating() {
        int c = ctl.get();
        return runStateAtLeast(c, SHUTDOWN) && runStateLessThan(c, TERMINATED);
    }

    // ThreadPoolExecutor가 종료될 때까지 대기
    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        ...
            }

    // 작업 큐를 반환. 작업 대기열에 대한 액세스는 주로 디버깅 및 모니터링을 위한 것
    public BlockingQueue<Runnable> getQueue() {
        return workQueue;
    }

    // 작업을 활발하게 실행 중인 대략적인 스레드 수를 반환
    public int getActiveCount() {
        ...
    }

    // 실행이 완료된 대략적인 총 작업 수를 반환
    // 작업 및 스레드의 상태는 계산 중에 동적으로 변경될 수 있으므로 반환된 값은 근사치일 뿐이며 연속 호출을 통해 감소하지 않음
    public long getCompletedTaskCount() {
        ...
    }

    // 실행이 예약된 대략적인 총 작업 수를 반환
    // 작업 및 스레드의 상태는 계산 중에 동적으로 변경될 수 있으므로 반환된 값은 근사치일 뿐임
    public long getTaskCount() {
        ...
    }

    /*
        지정된 스레드에서 지정된 Runnable을 실행하기 전에 호출되는 메서드
        이 메서드는 작업 r 실행할 스레드 t 에 의해 호출되며 ThreadLocal을 다시 초기화하거나 로깅을 수행하는 데 사용될 수 있음
    */
    protected void beforeExecute(Thread t, Runnable r) {}

    /*
        지정된 Runnable의 실행이 완료되면 호출되는 메서드
        이 메서드는 작업을 실행한 스레드에 의해 호출. null이 아닌 경우 Throwable은 실행이 갑자기 종료되도록 하는 포착되지 않은 RuntimeException 또는 Error
    */
    protected void afterExecute(Runnable r, Throwable t) { }


    // Executor가 종료되었을 때 호출되는 메서드
    protected void terminated() { }

    ...
}
```

<br>

###  쓰레드풀에 지정된 설정의 스레드 사용하기?
- 책에서 ThreadPool에 ThreadFactory를 사용하여 스레드를 생성하고 사용하였음
- 지난 발표에서 해당 스레드 설정은 AsyncConfigurerSupport 상속을 통해 사용하였음

```java
ThreadFactory threadFactory = new ThreadFactoryBuilder()
    .setNameFormat("MyThread-%d")
    .build();
ExecutorService executor = Executors.newFixedThreadPool(10, threadFactory);
```

```java
// 기본 상속은 AsyncConfigurer, 커스텀하고 싶다면 AsyncConfigurerSupport
// AsyncConfigurer는 비동기 작업을 위한 Executor 설정을 제공하는 메서드를 정의
// AsyncConfigurerSupport 클래스는 AsyncConfigurer를 구현한 클래스로, 비동기 처리를 위한 설정을 지원하는 커스텀 도우미 클래스
@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {    
    @Override
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("MyAsyncThread-");
        executor.initialize();
        return executor;
    }
}
```

<br>

- 해당 정리들은 맨 아래 블로그를 완전히 참조하였음

<br>

### 작업과 실행 정책 간의 보이지 않는 연결 관계
- Executor 프레임웍이 나름대로 실행정책을 정하거나 변경하는데 있어 유연성을 가지고 있지만, 특정 형태의 실행정책에서는 실행할 수 없는 작업이 존재함
- 일정한 조건을 갖춘 실행 정책이 필요한 작업에는 아래와 같은 것들이 있음
    - 의존성이 있는 작업
        - 다른 작업에 의존성을 갖는 작업을 스레드 풀에 올려 실행하려는 경우에는 실행 정책에 보이지 않는 조건을 거는 셈
        - 스레드 풀이 동작하는 동안 활동성 문제(liveness problem)가 발생하지 않도록 하려면 실행 정책에 대한 이와 같은 보이지 않는 조건을 면밀하게 조사하고 관리해야 함
    - 스레드 한정 기법을 사용하는 작업
        - 단일 스레드로 동작하는 스레드 풀은 여러 스레드가 동작하는 경우보다 병렬 프로그램 입장에서 안전하게 동작함
        - 작업에서 사용하는 객체를 스레드 수준에 맞춰 한정할 수 있으므로, 같은 스레드에 한정되어 있는 객체라면 해당 객체가 스레드 안전성을 갖추고 있지 않다해도 얼마든지 마음대로 사용할 수 있음
        - 해당 작업을 실행하려면 Executor 프레임웍이 단일 스레드로 동작해야 한다는 조건이 생기기 때문에 작업과 실행정책 간의 보이지 않는 연결고리가 걸려있는 상황
        - 단일 스레드를 사용하는 풀 대신 여러 개의 스레드를 사용하는 풀로 변경하면, 스레드 안전성을 쉽게 잃을 수 있음
    - 응답 시간이 민감한 작업
        - 단일 스레드로 동작하는 Executor에 오랫동안 실행될 작업을 등록하거나, 서너개의 스레드로 동작하는 풀에 실행 시간이 긴 작업을 몇개만 등록하더라도 해당 Executor를 중심으로 움직이는 화면 관련 부분은 응답 성능이 크게 떨어짐
    - ThreadLocal을 사용하는 작업
        - ThreadLocal을 사용하면 각 스레드에서 같은 이름의 값을 각자의 버전으로 유지할 수 있음
        - Executor는 상황이 되는대로 기존 스레드를 최대한 재사용함
        - 기본적으로 포함된 Executor는 처리해야할 작업의 수가 적을 땐 쉬는 스레드를 제거하기도 하고, 작업량이 많을 땐 새로운 스레드를 생성하여 사용하기도 함
        - 스레드 풀에 속한 스레드에서 ThreadLocal을 사용할 떄에는 현재 실행 중인 작업이 끝나면 더 이상 사용하지 않을 값만 보관해야함
        - ThreadLocal을 편법으로 활용해 작업 간에 값을 전달하는 용도로 사용해서는 안됨
- 스레드 풀은 동일하고 서로 독립적인 다수의 작업을 실행할 떄 가장 효과적임
- 실행시간이 오래 걸리는 작업과 금방 끝나는 작업을 섞여서 실행하도록 하면 풀의 크기가 굉장히 크지 않는 한 작업 실행을 방해하는 것과 비슷한 상황이 발생함
- 크기가 제한되어 있는 스레드 풀에 다른 작업에 의존성을 갖고 있는 작업을 등록하면 데드락이 발생할 가능성이 높음
- 다른 작업에 의존성이 있는 작업을 실행해야할 때는 스레드 풀의 크기를 충분히 크게 잡아서 작업이 큐에서 대기하거나 등록되지 못하는 상황이 없도록 해야함
- 스레드 한정 기법을 사용하는 작업은 반드시 순차적으로 실행돼야 함

<br>

### 스레드 부족 데드락
- 스레드 풀에서 다른 작업에 의존성을 갖고 있는 작업을 실행시킨다면 데드락에 걸릴 가능성이 높음
- 단일 스레드로 동작하는 Executor에서 다른 작업을 큐에 등록하고 해당 작업이 실행된 결과를 가져다 사용하는 작업을 실행하면, 데드락이 제대로 걸림
- 스레드 풀의 크기가 크더라도 실행되는 모든 스레드가 큐에 쌓여 아직 실행되지 않은 작업의 결과를 바등려고 대기 중이라면 이와 동일한 상황이 발생할 수 있음
    - 스레드 부족 데드락(thread starvation deadlock)이라 함
- 스레드 부족 데드락은 특정 자원을 확보하고자 계속해서 대기하거나 풀 내부의 다른 작업이 실행돼야 알 수 있는 조건이 만족하기를 기다리는 것처럼 끝없이 계속 대기할 가능성이 있는 기능을 사용하는 작업이 풀에 등록된 경우에는 언제든지 발생할 수 있음

<br>

```java
public class ThreadDeadlock {
    ExecutorService exec = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {
        public String call() throws Exception {
            Future<String> header, footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            // 데드락 발생
            return header.get() + page + footer.get();
        }
    }
}
```

<br>

- Executor에서 스레드를 하나만 쓰도록 구현하다면 ThreadDeadlock 클래스는 항상 데드락에 걸림
- 배리어(barrier)를 사용해 서로의 동작을 조율하는 작업 역시 풀의 크리가 충분히 크지 않다면 스레드 부족 데드락이 발생할 수 있음
- 완전히 독립적이지 않은 작업을 Executor에 등록할 때는 항상 스레드 부족 데드락이 발생할 수 있다느 사실을 염두에 둬야 하며, 작업을 구현한 코드나 Executor를 설정하는 설정 파일 등에 항상 스레드 풀의 크기나 설정에 대한 내용을 설명해야 함
- 스레드 풀의 크기는 직접적으로 지정하는 것 이외에도 스레드 풀에서 필요로 하는 자원이 제한되어 원하는 크기보다 작은 수준에서 동작하는 경우도 있음

<br>

### 오래 실행하는 작업
- 오래 실행되는 작업이 있다면 스레드 풀은 전체적인 작업 실행 과정에 어려움을 겪게 되며 금방 끝나는 작업이 실행되는 속도에도 영향을 미침
- 제한 없이 대기하는 기능 대신 일정 시간 동안만 대기하는 메소드를 사용할 수 있다면, 오래 실행되는 작업이 주는 악영향을 줄일 수 있는 하나의 방법으로 볼 수 있음
- 자바 라이브러리에서 제공하는 대부분의 블로킹 메소드는 시간이 제한되지 않는 것과 시간이 제한된 것이 함께 만들어져 있음
    - Thread.join(), BlockingQueue.put(), CountDownLatch.await(), Selector.select() ...
- 스레드 풀을 사용하는 도중에 모든 스레드에서 실행 중인 작업이 대기 상태에 빠지는 경우가 자주 발생한다면, 스레드 풀의 크기가 작다는 것으로 이해할 수도 있음

<br>

### 스레드풀 크기 조절
- 스레드 풀의 가장 이상적인 크기는 스레드 풀에서 실행할 작업의 종류와 스레드 풀을 활용할 애플리케이션의 특성에 따라 결정됨
- 스레드 풀의 크기는 설정 파일이나 Runtime.availableProcess 등의 메소드 결과 값에 따라 동적으로 지정되어야 함
- 스레드 풀의 크기가 너무 크게 설정되어 있다면, 스레드는 CPU나 메모리 등의 자원을 더 확보하기 위해 경쟁하게 됨, 이는 자원 부족으로 이어짐
- 스레드 풀의 크기가 너무 작다면, 작업량은 계속해서 쌓이는데 CPU나 메모리는 남아돌며 작업 처리속도가 떨어질 수 있음
- 스레드 풀의 크기를 적절하게 산정하려면 현재 컴퓨터 환경이 어느정도인지 확인해야 하고, 확보하고 있는 자원의 양도 알아야 하며, 해야할 작업이 어떻게 동작하는지도 정확히 알아야 함
- CPU를 많이 사용하는 작업의 경우 N개의 CPU를 탑재하고 있는 하드웨어에서 스레드 풀을 사용할 때는 스레드의 개수를 N+1개로 맞추면 최적의 성능을 발휘한다고 알려져 있음
- I/O 작업이 많거나 다른 브로킹 작업을 해야하는 경우라면 어느 순간에는 모든 스레드가 대기 상태에 들어가 전체 진행이 멈출수 있기 때문에 스레드 풀의 크기를 훨씬 크게 잡아야할 필요가 있음
- 스레드 풀의 크기를 정하려면 처리해야 할 작업이 시작해서 끝날 때까지 실제 작업하는 시간 대비 시간의 비율을 구해봐야함

<br>

```
Ncpu = CPU 개수
Ucpu = 목표로 하는 CPU 활용도. 0보다 크거나 같고 1보다 작거나 같음
W/C = 작업 시간 대비 대기 시간의 비율

CPU가 원하는 활용도를 유지할 수 있는 스레드 풀의 크기는  
Nthreads = Ncpu * Ucput * ( 1 + W/C )
```

<br>

- CPU의 개수는 Runtime 클래스의 availableProcessors 메소드로 확인 가능
    - int N_CPU = Runtime.getRuntime().availableProcessors();
- CPU가 아닌 다른 자원을 대상으로 하는 스레드 풀의 크기를 정하는 일은 CPU 보다 쉬움
    - 메모리, 파일 핸들, 소켓 핸들, 데이터베이스 연결 등
- 각 작업에서 실제로 필요한 자원의 양을 모두 더한 값을 자원의 전체 개수로 나워주면 됨
    - 스레드 풀의 최대 크기에 해당됨
- 각 작업 하나가 데이터베이스 연결 하나를 사용한다고 가정하면 스레드 풀의 실제 크기는 데이터베이스 연결 풀의 크기로 제한되는 셈
- 데이터베이스 연결 풀을 특정 스레드 풀에서만 사용한다고 하면, 데이터베이스 연결 풀에 확보된 연결 가운데 실제로 스레드 풀의 크기에 해당되는 연결만 사용될 것

<br>

### ThreadPoolExecutor 설정
- ThreadPoolExecutor는 Executors 클래스에 들어있는 팩토리 메소드로 생성해주는 Executor에 대한 기본적인 내용이 구현되어 있는 클래스
    - newCachedThreadPool, newFixedThreadPool, newScheduledThreadPool 등
- ThreadPoolExecutor 클래스는 유연하면서도 안정적이고 여러가지 설정을 통해 입맛에 맞게 바꿔 사용할 수 있도록 되어있음
- 팩토리 메소드를 사용해 만들어진 스레드 풀의 기본 실행 정책이 요구사항에 잘 맞지 않는다면 ThreadPoolExecutor의 생성자를 호출해 스레드 풀을 생성할 수 있음
    - 생성자의 파라미터 값을 통해 스레드 풀 설정을 조절할 수 있음

<br>

### 스레드 생성과 제거
- 풀의 코어(core) 크기나 최대(maximum) 크기, 스레드 유지(keep-alive) 시간 등의 값을 통해 스레드가 생성되고 제거되는 과정을 조절할 수 있음
- 코어 크기는 스레드 풀을 사용할 때 원하는 스레드 개수라고 볼 수 있음
- 스레드 풀 클래스는 실행할 작업이 없다 하더라도 스레드의 개수를 최대한 코어 크기에 맞추도록 되어있음
    - 최초 ThreadPoolExecutor를 생성한 이후에도 prestartAllCoreThreads 메소드를 호출하지 않는 한 코어 크기만큼의 스레드가 미리 만들어지지는 않음
    - 작업이 실행되면서 코어 크기까지의 스레드가 차례로 생성됨
- 큐에 작업이 가득 차지 앟는 이상 스레드의 수가 코어 크기를 넘지 않음
- 풀의 최대 크기는 동시에 얼마나 많은 개수의 스레드가 동작할 수 있는지를 제한하는 최대 값임
- 코어 크기와 스레드 유지 시간을 적절하게 조절하면 작업없이 쉬고 있는 스레드가 차지하고 있는 자원을 프로그램의 다른 부분에서 활용하게 반납하도록 할 수 있음
- newFixedThreadPool
    - 메소드 결과로 생성할 스레드 풀의 코어 크기와 최대 크기를 메소드에 지정한 값으로 동일하게 지정됨
    - 시간 제한은 무제한으로 설정되는 것과 같음
- newCachedThreadPool
    - 스레드 풀의 최대 크기를 Inter.MAX_VALUE 값으로 지정하고 코어 크기를 0으로, 스레드 유지 시간을 1분으로 지정함
    - 스레드 풀은 끝없이 크기가 늘어날 수 있으며, 사용량이 줄어들면 스레드 개수가 적당히 줄어드는 효과가 있음

<br>

### 큐에 쌓인 작업 관리
- 크기가 제한된 스레드 풀에서는 동시에 실행될 수 있는 스레드의 개수가 제한되어 있음
- 작업을 처리할 수 있는 능력보다 많은 양의 요청이 들어오면 처리하지 못한 요청이 큐에 계속 쌓임
- 스레드 풀을 사용하는 경우에는 Executor 클래스에서 관리하는 큐에 Runnable로 정의된 작업이 계속해서 쌓을 뿐, 스레드 풀 없이 스레드가 계속해서 생성됐을 때 각 스레드가 CPU를 확보하기 위해 대기하는 것과 다를바 없는 상황이 발생함
- ThreadPoolExecutor를 생성할 때 작업을 쌓아둘 큐로 BlockingQueue를 지정할 수 있음
- 스레드 풀에서 작업을 쌓아둘 큐에 적용할 수 있는 전략에는 세가지가 존재
    - 큐에 크기 제한을 두지 않는 방법
    - 큐의 크기를 제한하는 방법
    - 작업을 스레드에게 직접 넘겨주는 방법
- newFixedThreadPool 메소드와 newSingleThreadExecutor 메소드에서 생성하는 풀은 기본 설정으로 크기가 제한되지 않은 LinkedBlokingQueue를 사용함
    - 모든 스레드가 busy한 상태이고, 계속 작업이 추가되면 큐에 작업이 쌓임
- 자원 관리 측면에서 ArrayBlockingQueue 또는 크기가 제한된 LinkedBlockingQueue나 PriorityBlockingQueue와 같이 큐의 크기를 제한시켜 사용하는 방법이 안정적임
    - 자원 사용량을 한정시킬 수 있지만, 큐가 가득 찼을 때 새로운 작업을 등록하려는 상황을 어떻게 처리해야 하는지 문제가 발생함
- 스레드의 개수는 줄이고 큐의 크기를 늘리면 메모리와 CPU 사용량은 줄이면서 컨텍스트 스위칭 횟수를 줄일 수 있지만, 전체적인 성능에는 제한이 생길 수 있음
- 스레드의 개수가 많거나 제한이 거의 없는 상태인 경우에는 작업을 큐에 쌓는 절차를 생략할 수 있을 텐데, 이럴때는 SynchronousQueue를 사용해 프로듀서에서 생성한 작업을 컨슈머 스레드에게 직접 전달할 수 있음
- SynchronousQueue에 작업을 추가하려면 컨슈머 스레드가 대기하고 있어야 함
- 대기 중인 스레드가 없는 상태에서 스레드의 개수가 최대 크기보다 작다면 ThreadPoolExecutor는 새로운 스레드를 생성해 동작시킴
- 쉬고 있는 스레드에게 처리할 작업을 직접 넘겨주는 방법이 있다면 훨씬 효율적일 수 있음
- 크기가 고정된 풀보다는 newCachedThreadPool 팩토리 메소드가 생성해주는 Executor가 나은 선택일 수 있음
- 크기가 고정된 풀은 자원 관리 측면에서 동시에 실행되는 스레드 수를 제한해야하는 경우에 현명한 선택임
    - 네트워크로 클라이언트 요청을 받아 처리하는 애플리케이션과 같은 경우, 크기가 고정되어 있지 않다면 요청이 많아져 부하가 걸릴 떄 문제가 커짐
- 스레드 풀에서 실행할 작업이 서로 독립적인 경우에만 스레드의 개수나 작업 큐의 크기를 제한할 수 있음
- 다른 작업에 의존성을 갖는 작업을 실행해야할 때 스레드나 큐의 크기가 제한되어 있다면 스레드 부족 데드락에 걸릴 가능성이 높음
    - 해당 경우는 newCachedThreadPool 메소드에서 생성하는 것과 같이 크기가 제한되지 않은 풀을 사용해야 함

<br>

### 집중 대응 정책
- 크기가 제한된 큐에 작업이 가득차면 집중 대응 정책(saturation policy)이 동작함
- ThreadPoolExecutor의 집중 대응 정책은 setRejectedExecutionHandler 메소드를 사용해 원하는 정책으로 변경할 수 있음
- RejectedExecutionHandler에는 AbortPolicy, CallerRunsPolicy, DiscardPolicy, DiscardOldestPolicy 등이 존재
- 중단(abort) 정책
    - 기본적으로 사용하는 정책, execute 메소드에서 RejectedExecutionException을 던짐
    - execute 메소드를 호출하는 스레드는 RejectedExecutionException을 잡아서 작업을 더이상 추가할 수 없는 상황에 직접 대응해야 함
- 호출자 실행(caller runs) 정책
    - 작업을 제거해 버리거나 예외를 던지지 않으면서 큐의 크기를 초과하는 작업을 프로듀서에게 거꾸로 넘겨 작업 추가 속도를 늦출 수 있도록 속도 조절 방법으로 사용됨
    - 즉, 새로 등록하려한 작업을 스레드 풀의 작업 스레드로 실행하지 않고, execute 메소드를 호출해 작업을 등록하려한 스레드에서 실행시킴
- 제거(discard) 정책
    - 큐에 작업을 더이상 쌓을 수 없다면 추가하려한 정책을 아무 반응없이 제거함
- 오래된 항목 제거(discard oldest) 정책
    - 큐에 쌓은 항목 중 가장 오래되어 다음번에 실행될 예정이던 작업을 제거하고, 추가하고자 한 작업을 큐에 다시 추가함
- 스레드 풀에 적용할 집중 대응 정책을 선택하거나 실행 정책의 다른 여러가지 설정을 변경하는 일은 모두 Executor를 생성할 때 지정할 수 있음

<br>

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    N_THREADS, N_THREADS, 
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(CAPACITY));
executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
```

- 스레드 개수와 작업 큐의 크기가 제한된 스레드 풀을 만들면서 호출자 실행 정책을 지정하는 예
- 작업 큐가 가득 찼을 때 execute 메소드가 그저 대기하도록 하는 집중 대응 정책은 따로 만들어진 것이 없음

<br>

```java
@ThreadSafe
public class BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec, int bound) {
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command)
            throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }
}
```

- semaphore를 사용하면 작업 추가 속도를 적절한 범위 내에서 제한할 수 있음
- 큐의 크기에 제한을 두지 않아야하고, 스레드 풀의 스래드 개수와 큐에서 대기하도록 허용하고자 하는 최대 작업 개수를 더한 값을 세마포어의 크기로 지정해야 함

<br>

### 스레드 팩토리
- 스레드 풀에서 새로운 스레드를 생성해야 할 시점이 되면, 새로운 스레드는 항상 스레드 팩토리를 통해 생성함
```java
public interface ThreadFactory {
	Thread newThread(Runnable r);
}
```

<br>

- 기본값으로 설정된 스레드 팩토리에서는 데몬이 아니면서 아무런 설정도 변경하지 않은 새로운 스레드를 생성하도록 되어 있음
- ThreadFactory 클래스에는 newThread라는 메소드 하나만 정의되어 있음
    - 스레드 풀에서 새로운 스레드를 생성할 때에는 해당 메소드를 호출함
- 스레드 팩토리를 직접 작성해 사용해야 하는 경우
    - 스레드 풀에서 사용하는 스레드에 UncaughtExceptionHandler를 직접 지정하고자 할 경우
    - Thread 클래스를 상속받은 또 다른 스레드를 생성해 사용하고자 하는 경우
    - 새로 생성한 스레드의 실행 우선 순위를 조절하고자 하는 경우
    - 데몬 상태를 직접 지정하는 경우
    - 스레드마다 이름을 지정해 오류가 발생했을 때 덤프 파일이나 로그 파일에서 스레드 이름이 표시되도록 하는 경우

<br>

```java
public class MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    public Thread newThread(Runnable runnable) {
        return new MyAppThread(runnable, poolName);
    }
}
```

- MyAppThread를 생성할 때 생성자에 스레드 풀 이름을 넘겨 덤프 파일이나 로그 파일에서 특정 스레드가 어떤 스레드 풀에 속해 동작하는지 확인할 수 있도록 함
    - 디버깅할 때 요긴

<br>

```java
public class MyAppThread extends Thread {
    public static final String DEFAULT_NAME = "MyAppThread";
    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger log = Logger.getAnonymousLogger();

    public MyAppThread(Runnable r) {
        this(r, DEFAULT_NAME);
    }

    public MyAppThread(Runnable runnable, String name) {
        super(runnable, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t,
                                          Throwable e) {
                log.log(Level.SEVERE,
                        "UNCAUGHT in thread " + t.getName(), e);
            }
        });
    }

    public void run() {
        // debug 플래그를 복사해 계속해서 동일한 값을 갖도록 한다
        boolean debug = debugLifecycle;
        if (debug) log.log(Level.FINE, "Created " + getName());
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) log.log(Level.FINE, "Exiting " + getName());
        }
    }

    public static int getThreadsCreated() {
        return created.get();
    }

    public static int getThreadsAlive() {
        return alive.get();
    }

    public static boolean getDebug() {
        return debugLifecycle;
    }

    public static void setDebug(boolean b) {
        debugLifecycle = b;
    }
}
```

- 스레드 이름을 지정하는 것을 포함해, 작업 실행중 오류 발생시 Logger 클래스를 통해 로그로 남겨주는 UncaughtExceptionHandler를 직접 지정함
- 스레드가 몇개나 생성되고 제거됐는지에 대한 간단한 통계값도 보관함
- 스레드가 생성되고 종료될 때 디버깅용 메시지도 출력하도록 구현
- 애플리케이션에서 보안 정책(security policy)를 사용해 각 부분마다 권한을 따로 지정하고 있다면, Executors에 포함되어 있는 privilegedThreadFactory 팩토리 메소드를 사용해 스레드 팩토리를 만들어 사용할 수 있음
- privilegedThreadFactory에서 만들어 낸 스레드 팩토리는 privilegedThreadFactory 메소드를 호출한 스레드와 동일한 권한, 동일한 AccessControlContext, 동일한 contextClassLoader 결과를 갖는 스레드를 생성함

<br>

### ThreadPoolExecutor 생성 이후 설정 변경
```java
ExecutorService exec = Executors.newCachedThreadPool();
if(exec instanceof ThreadPoolExecutor)
	( (ThreadPoolExecutor) exec ).setCorePoolSize(10);
else
	throw new AssertionError("Oops, bad assumption");
```

- 스레드 풀을 ThreadPoolExecutor로 형변환해 여러가지 set 메소드를 사용 가능
- Executors에는 unconfigurableExecutorService 메소드가 있는데, 현재 만들어져 있는 ExecutorService를 넘겨 받은 다음 ExecutorService의 메소드만을 외부에 노출하고 나머지는 가리도록 래핑하여 설정을 변경하지 못하도록 할 수 있음
- 스레드 풀을 사용하지 않는 newSingleThreadExecutor 메소드는 ThreadPoolExecutor 인스턴스를 만들어주는 대신 단일 스레드라는 기능에 맞춰 래핑한 ExecutorService를 생성함
- 설정을 변경할 가능성이 있는 외부 코드에서도 직접 구현한 Executor 클래스의 설정을 변경하지 못하도록 하려면 앞서 소개했던 unconfigurableExecutorService 메소드를 사용하는 것이 나음

<br>

### ThreadPoolExecutor 상속
- 하위 클래스가 오버라이드해 사용할 수 있도록 beforeExecute, afterExecute, terminated와 같은 여러가지 훅(hook)을 제공함
- beforeExecute/afterExecute 메소드는 작업을 실행할 스레드의 내부에서 호출하도록 되어 있으며, 로깅 또는 작업 실행 시점이 언제인지 기록해두거나 실행 상태를 모니터링하는 등의 작업에 적당함
- afterExecute 메소드는 run 메소드의 정상 종료 또는 Exception을 던지고 종료되는 상황에도 항상 호출됨
    - 만약 Exception 보다 심각한 오류인 Error의 경우 afterExecute 메서드는 실행되지 않음
- beforeExecute 메소드에서 RuntimeException이 발생하면 작업이 실행되지 않기 때문에 afterExecute 메소드 역시 실행되지 않음
- 스레드 풀이 종료 절차를 마무리한 이후, 모든 작업과 모든 스레드가 종료되고나면 terminated 훅 메소드를 호출함
- terminated 메소드에서는 Executor가 동작하는 과정에서 사용했던 자원을 반납하는 등의 일을 처리하거나 여러가지 알람, 로그 출력, 통계 값 확보 등의 작업을 진행하기에 적당한 메소드임

<br>

### 스레드풀에 통계 확인 기능 추가
```java
public class TimingThreadPool extends ThreadPoolExecutor {
    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private final Logger log = Logger.getLogger("TimingThreadPool");
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.fine(String.format("Thread %s: start %s", t, r));
        startTime.set(System.nanoTime());
    }

    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            log.fine(String.format("Thread %s: end %s, time=%dns",
                    t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }
    }

    protected void terminated() {
        try {
            log.info(String.format("Terminated: avg time=%dns",
                    totalTime.get() / numTasks.get()));
        } finally {
            super.terminated();
        }
    }
}
```

- 작업 실행시간 측정을 위해 beforeExecute 메소드에서 시간을 기록함
- afterExecute 메소드에서는 시작할 떄 기록해뒀던 시간을 참조해 작업이 실행된 시간을 알아냄
- 훅 메소드 역시 작업을 실행했던 바로 그 스레드에서 호출하기 때문에 beforeExecute 에서 측정한 값을 ThreadLocal에 보관해두면 afterExecute 에서 안전하게 찾아낼 수 있음

<br>

### 재귀 함수 병렬화
- 반복문 내부에서 복잡한 연산을 수행하거나 블로킹 I/O 메소드를 호출하는 등의 작업을 진행하는 부분이 있고, 각 반복 작업이 이전 회차와 독립적이라면 병렬화 할 수 있는 좋은 대상으로 생각할 수 있음
- 반복문의 각 차수에 해당하는 작업이 서로 독립적이라 한다면, Executor를 활용하여 반복문을 병렬 프로그램으로 쉽게 변경할 수 있음

```java
void processSequentially(List<Element> elements) {
	for(Element e : elements)
		process(e);
}

void processInParallel(Executor exec, List<Element> elements) {
	for(final Element e: elements)
		exec.execute(new Runnable() {
			public void run() { process(e); }
		});
}
```

- processInParallel을 호출하면 지정된 Executor에 실행할 작업을 모두 등록하기만 하고 리턴되기 때문에 processSequentially 메소드보다 훨씬 빨리 실행됨
- 한 묶음의 작업을 한꺼번에 등록하고 그 작업들이 모두 종료될 때까지 대기하고자 한다면 ExecutorService.invokeAll 메소드를 사용하는 것을 권장함
- 반복문 내부의 작업을 개별적인 작업으로 구분해 실행하느라 추가되는 약간의 부하가 부담되지 않을 만큼 적지 않은 시간이 걸리는 작업이라야 효과를 볼 수 있음

<br>

```java
public<T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> results) {
	for(Node<T> n: nodes) {
		results.add(n.compute());
		sequentialRecursive(n.getChildren(), results);
	}
}

public<T> void parallelRecursive(
                    final Executor exec, 
                    List<Node<T>> nodes, 
                    final Collection<T> results) {
	for(final Node<T> n : nodes) {
		exec.execute(new Runnable() {
			public void run() {
				results.add(n.compute());
			}
		});

		parallelRecursive(exec, n.getChildren(), results);
	}
}
```

- sequentialRecursive 메소드는 트리 구조를 대상으로 깊이 우선 탐색을 실행하면서 각 노드에서 연산 작업을 처리하고 연산 결과를 컬렉션에 담음
- parallelRecursive 메소드 역시 깊이 우선 탐색이지만, 노드를 방문시 결과를 계산하는 것이 아니라, 노드별 값을 계산하는 작업을 생성해 Executor에 등록시킴

<br>

```java
public<T> Collection<T> getParallelResults(List<Node<T>> nodes) throws InterruptedException {
	ExecutorService exec = Executor.newCachedThreadPool();
	Queue<T> resultQueue = new ConcurrentLinkedQueue<T>();
	parallelRecursive(exec, nodes, resultQueue);
	exec.shutdown();
	exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
	return resultQueue;
}
```

- parallelRecursive 메소드를 호출하는 스레드는 parallelRecursive 메소드에서 사용할 전용 Executor를 하나 생성해 parallelRecursive 메소드를 호출한 다음, Executor의 shutdown 메소드와 awaitTermination 메소드를 차례로 호출해 모든 연산 작업이 마무리되기를 기다릴 수 있음

<br>

### 퍼즐 프레임워크 예제
- 먼저 '퍼즐'이라는 대상을 초기 위치, 목표 위치, 정상적인 이동 규칙 등의 세가지로 추상화하고, 세가지 개념을 묶어 퍼즐이라고 정의
- 이동 규칙은 두가지 부분으로 나뉨
    - 현재 위치에서 규정에 맞춰 움질일 수 있는 방향에 몇가지 안이 있는지를 모두 찾아내는 부분
    - 특정 위치로 이동시키고 난 결과를 계산하는 부분

<br>

```java
public interface Puzzle(P, M) {
	P initialPosition();
	boolean isGoal(P position);
	Set<M> legalMoves(P position);
	P move(P position, M move);
}
```

```java
@Immutable
public class Node<P, M> {
    final P pos;
    final M move;
    final Node<P, M> prev;

    Node(P pos, M move, Node<P, M> prev) { ... }
    
    List<M> asMoveList() {
        List<M> solution = new LinkedList<M>();
        for(Node<P, M> n = this; n.move != null; n = n.prev) {
            solution.add(0, n.move);
        }
        return solution;
    }
}
```

- 이동 과정을 거쳐 도착한 특정 위치를 표현하며, 해당 위치로 오게했던 이동 규칙과 바로 직전 위치를 가리키는 Node에 대한 참조를 갖고 있음
- Node 클래스가 갖고있는 이전 Node에 대한 참조를 계속해서 따라가면 처음 시작한 이후 어떤 과정을 거쳐 현재 위치까지 오게 됐는지를 알 수 있음

<br>

```java
public class ConcurrentPuzzleSolver<P, M> {
    private final Puzzle<P, M> puzzle;
    private final ExecutorService exec;
    private final ConcurrentHashMap<P, Boolean> seen; 
    final ValueLatch<Node<P, M>> solution = new ValueLatch<Node<P, M>>();

    ...
    
    public List<M> solve() throws InterruptedException {
        try {
            P p = puzzle.initailPosition();
            exec.execute(newTask(p, null, null));
            // 최종 결과를 찾을 때까지 대기
            Node<P, M> solnNode = solution.getValue();
            return (solnNode == null) ? null : solnNode.asMoveList();
        } finally {
            exec.shutdown();
        }
    }
    
    protected Runnable newTask(P p, M m, Node<P, M> n) {
        return new SolverTask(p, m, n);
    }
    
    class SolverTask extends Node<P, M> implements Runnable {
    	...
=
        public void run() {
            if(solution.isSet() || seen.putIfAbsent(pos, true) != null) {
                return; // 최종 결과를 구했거나 해당 위치를 이미 탐색했던 경우
            }
            
            if(puzzle.isGoal(pos)) { 
                solution.setValue(this);
            } else {
                for(M m : puzzle.legalMoves(pos)) { 
                    exec.execute(newTask(puzzle.move(pos, m), m, this));  
                }
            }
        }
    }
}
```

- 순차적인 퍼즐 해결 클래스에서 병렬화 할 수 있는 다음 이동할 위치를 계산하고 목표 조건에 이르렀는지 계산하는 부분을 병렬로 실행 시킨 것
- ConcurrentPuzzleSolver 클래스는 Node 클래스를 상속받고 Runnable 인터페이스를 구현한 SolverTask라는 내부 클래스를 사용함
- ConcurrentPuzzleSolver 클래스에서는 Set 대신 ConcurrentHashMap을 사용
    - 컬렉션 내부 정보에 대해 스레드 안전성 호가보
    - putIfAbsent와 같은 단일 연산 메소드를 사용해 여러 스레드에서 같은 이름으로 값을 저장하려 할 때 발생할 수 있는 경쟁 상황을 예방함
- ConcurrentPuzzleSolver 클래스는 검색 상태를 호출 스택에 보관하는 대신 스레드 풀 내부의 작업 큐를 사용함
- 하지만, 목표한 결과에 도달했을 떄 더 이상 탐색하지 않고 프로그램을 종료시키려면 동작중인 스레드 가운데 아직 목표한 지점에 도달하지 못한 스레드가 있는지 알아낼 방법이 필요
    - 여러 스레드에서 찾아낸 첫번째 해결 방법을 결과로 채택한다고 하면, 아직 어느 스레드에서도 결과를 찾지 못했는지를 알 수 있어야함
    - 최종 결과와 관련된 이런 조건을 처리하기에 적절한 방법은 래치(latch)

<br>

```java
@ThreadSafe
public class ValueLatch<T> {
    @GuardedBy("this") private T value = null;
    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet() {
        return (done.getCount() == 0);
    }

    public synchronized void setValue(T newValue) {
        if(!isSet()) {
            value = newValue;
            done.countDown();
        }
    }

    public T getValue() throws InterruptedException { 
        done.await(); 
        synchronized (this) {
            return value;
        }
    }
}
```

- CountDownLatch를 사용해 퍼즐 프로그램에서 필요로하는 래치 기능을 구현함
- 락을 적절히 활용하여 결과를 단 한번만 설정할 수 있도록 되어 있음
- 최종 결과를 가장 먼저 찾아낸 스레드는 Executor를 종료시켜 더 이상의 작업이 등록되지 않도록 막음
- 등록된 작업을 바로 제거하는 기능의 클래스를 RejectedExecutionHandler로 등록하면 RejectedExecutionException을 따로 처리할 필요가 없도록 할 수 있음
- 풀고자 하는 퍼즐에 해당이 없을 경우 제대로 대처하지 못함
- 가능한 모든 이동 방법을 모두 탐색하고 확인해봐도 원하는 결과를 얻지 못한 경우에는 solve 메소드가 getSolution 메소드를 호출한 부분에서 계속해서 대기하게 됨

<br>

```java
public class PuzzleSolver<P, M> extends ConcurrentPuzzleSolver<P, M> {
    public PuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec, ConcurrentHashMap<P, Boolean> seen) {
        super(puzzle, exec, seen);
    }
    
    private final AtomicInteger taskCount = new AtomicInteger(0);
    
    protected Runnable newTask(P p, M m, Node<P, M> n) {
        return new CountingSolverTask(p, m, n);
    }
    
    class CountingSolverTask extends SolverTask {
        CountingSolverTask(P pos, M move, Node<P, M> prev) {
            super(pos, move, prev);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } finally {
                if(taskCount.decrementAndGet() == 0) {
                    solution.setValue(null);
                }
            }
        }
    }
}
```

- 병렬 프로그램이 원하는 결과를 얻지 못했을 때 종료시키려면 작업을 실행하고 있는 스레드의 개수를 세고 있다가 더이상 아무 작업을 하지 않는 시점이 됐을 때 결과값으로 null을 설정하는 방법도 사용해볼만 함
- 퍼즐 프로그램에 몇가지 추가적인 종료 조건을 넣어두고 효과를 볼 수 있음
    - 시간 제한 기능
    - 일정 횟수까지만 이동할 수 있다는 등의 제약을 두는 것
    - 프로그램이 언제든지 작업을 중단할 수 있도록 준비해두고, 클라이언트 측에서 원하는 시점에 중단 요청을 보내도록 하는 것
- 시간 제한 기능은 ValueLatch 클래스의 getValue 메소드에 제한 시간을 넘겨주도록 바꿔볼 수 있음

<br>

## 9. GUI 애플리케이션

### 궁금한 부분
- 메인 이벤트 반복문과 이벤트 처리 스레드란?
- 최근 GUI는 어떻게 구성되어 있는지?

<br>

### GUI 단일 스레드 동작
- 모든 GUI 툴킷은 GUI 관련 작업이 모두 단일 스레드에서 일어나는 단일 스레드 서브시스템으로 구현
- 단일 스레드 서브시스템은 말 그대로, 하나의 스레드 위에서 동작하는 시스템
- 현실적으로 프로그램이 하나의 스레드만 사용하며 동작하는 건 말이 안되고, 작업 중 일부는 이벤트 스레드, 일부는 일반 애플리케이션 스레드에서 실행됨
    - 화면 표시 부분과 관련 기능은 모두 이벤트 스레드에서만 작업
    - 장기간 작업은 백그라운드 스레드를 실행하여 처리
- GUI를 그리는 안드로이드도 이러한 제약이 존재. 안드로이드에서 메인 스레드는 GUI에 접근하고 수정할 수 있는 유일한 스레드를 말하지만, 이 책에서 말하는 그런 역할을 하는 스레드는 이벤트 스레드임
- 터치 이벤트 등 OS → App 방향 수정, 컴포넌트 배경색 설정 등 OS 방향 수정으로 하나의 GUI 컴포넌트를 두고 양방향으로 움직이는 과정이 필연적임
    - 이 과정에서 멀티 스레드 사용 시 경쟁 조건과 데드락 등의 문제가 계속 발생
- 단일 스레드 GUI 프레임워크는 스레드 제한 기법으로 스레드 안전성을 보장함
- 이러한 이유로 대부분의 GUI 프레임워크가 이벤트 처리용 전담 스레드를 만들고, 전담 스레드는 큐에 쌓여 있는 이벤트를 가져와 애플리케이션에 준비돼 있는 이벤트 처리 메소드를 호출해 기능을 동작시키는 단일 스레드 이벤트 큐 모델을 정착

<br>

### 순차적 이벤트 처리
- 이러한 이벤트는 항상 순차적으로 실행됨
    - 마우스 클릭, 키보드 입력, 타임 아웃 -> 이벤트 -> 일종의 작업(task)
- 응답성 문제로 인해 이벤트 스레드에서 실행되는 작업은 작업을 빨리 끝내야함

<br>

### 스윙의 스레드 한정
- GUI 객체는 스레드 한정 기법을 사용해 스레드 안전성 확보
- 스윙 이벤트 스레드는 이벤트 큐에 쌓여 있는 작업을 순차적으로 처리하는 단일 스레드 Executor로 볼 수 있음
    - Executor 구현 이후 스윙이 나왔다면 Executor를 사용했을 것이라고 함
    - GuiExecutor 클래스는 작업을 SwingUtilities에 넘겨 실행하도록 하는 Excutor의 하위 클래스

<br>

### 짧게 실행되는 GUI 작업
- 프레임워크는 발생하는 이벤트를 애플리케이션 전달 (프레임워크 ➡️ 앱)
- 애플리케이션은 이벤트 대응 코드를 통해서 프레임워크에 포함된 내용 변경함 (앱 ➡️ 프레임워크)
- 짧게 실행되는 GUI 작업은 응답성 신경쓸 필요없이 바로 이벤트 스레드에서 실행 가능

<br>

### 장시간 실행되는 GUI 작업
- GUI를 잘 구성해둔 애플리케이션은 맞춤법 검사, 백그라운드 컴파일, 원격 서버에서 자료를 가져오는 기능 등 사용자가 기다리는 시간이 길어질 수 있는 작업을 사용하는 경우가 많음
- GUI 애플리케이션에선 시간이 오래 걸릴 작업을 기계적으로 대량 생성하는 일은 거의 없기 때문에 스레드가 무한정 늘어나는 가능성이 거의 없어 Executor를 직접 생성해두고 시간이 많이 걸리는 작업을 실행 시키면 됨
    - newCachedThreadPool로 생성한 Executor가 제격
- 이벤트 스레드에서 UI의 이벤트를 처리하는 이벤트 리스너를 호출하고 이벤트 리스너는 작업을 담당하는 Runnable 인스턴스를 생성해 스레드 풀에서 실행시킴
- 이후 작업이 완료되었다고 알리고 화면을 원상태로 복귀시키는 Runnable 작업을 이벤트 스레드에 등록

<br>

### 작업 중단
- 사용자가 취소하고 싶을 수 있음, 화면 라이프사이클에 의해 작업이 멈춰야 할 수 있음
    - Future 의 cancel() 메서드
- 중단 가능한 작업을 만들 수 있도록 설계된 API를 사용하는 것이 좋음
    - JDK - Future
    - Rx - Disposable
    - Coroutine - Job

<br>

### 진행 상태 및 완료 알림
- Future 인터페이스를 통해 장시간 실행되는 작업을 쉽게 구현했는데 마찬가지로 작업 완료 기능도 제공하는 API 사용하는 것이 좋음
    - FutureTask done 훅 메서드
    - Callable 작업이 완료되면 항상 done() 메서드가 호출됨

<br>

### 데이터 공유 모델
- 스윙의 화면 표시 객체는 이벤트 스레드에 제한되어 있음
- 일반적인 간단한 GUI 애플리케이션에는 변경 가능한 상태 변수가 모두 화면 표시 객체에 담겨 있고 이벤트 스레드를 제외하고는 프로그램이 실행된 메인 스레드만이 동작하는 경우가 많음
- 아래 스레드 안전 데이터 모델과 분할 데이터 모델에 대해 설명

<br>

### 스레드 안전 데이터 모델
- 특정 데이터를 두고 여러 스레드가 동시 동작하는 상황은 스레드 안전 데이터 모델을 사용해 쉽게 해결
- 사용하고자 하는 데이터 모델에서 동시 사용성을 높은 수준으로 지원한다면 응답속도 문제 없이 이벤트 스레드와 작업용 스레드에서 충분히 데이터 공유 가능
- 병렬 환경에서 원활하게 동작하고 오래된 버전의 데이터를 적절한 시점에 자동으로 제거하는 등 기능 갖추는 건 그다지 쉬운 일이 아님

<br>

### 분할 데이터 모델
- 화면 표시 부분(presentation domain), 애플리케이션 부분(application domain)의 데이터 모델을 구분해 사용하는 모델
- 화면 표시 모델은 공유 모델에 이벤트 리스너 등록으로 최신 내용 반영 가능
    - 스냅샷(객체)을 받아서 처리. 해당 경우 데이터 규모 작고, 변경 빈도가 높지 않고, 공유 모델과 화면 표시 모델 데이터 구조가 비슷해야함
    - 변경된 부분만 추려서 보내는 증분 업데이트(incremental update) 방법이 효율적
    - 변경 이벤트를 받고 직접 데이터 접근해 처리
- 애플리케이션 모델은 이벤트 스레드와 애플리케이션 스레드와 동시에 사용할 수 있기 때문에 스레드 안정성을 고려한 구조임

<br>

### 다른 형태의 단일 스레드 서브시스템
- 스레드 제한 기법은 GUI만의 전유물이 아님
- GUI에서 다룬 기법들이 다른 단일 스레드 서브시스템 활용할 때도 유용함
    - 해당 라이브러리를 독점 사용하는 전용 스레드나 단일 스레드 Executor 준비
    - 라이브러리에 접근하는 이벤트를 전용 스레드에 등록하는 프록시 활용

<br>

## 금주 나온 이야기
- VirtualThread 사용하기 위한 VirtualThreadTaskExecutor
    - jdk 21버전 부터 사용가능 하다고 함
    - 자바에서가 아닌 다른 곳에서 가상스레드를 그린 스레드라고도 부른다고함
- @Configuration에서 AsyncConfigure는 비동기 작업을 위한 Executor 설정을 제공
    - AsyncConfigurerSupport 클래스는 AsyncConfigurer를 구현한 클래스로, 비동기 처리를 위한 설정을 지원하는 커스텀 도우미 클래스
- Async(value=) 설정에 pool을 쓰지 않았다면?
    - TaskExecutor 관련 빈이 있는지 확인하고 없다면 SimpleAsyncTaskExecutor를 사용함

<br>

### Reference
- https://github.com/garlickim/study-note/blob/main/book/java-concurrency-in-practice/%5Bweek8%5D%20스레드%20풀%20활용.md
- https://greedy0110.tistory.com/154
    - 정리 시간 상 해당 블로그들을 완전히 참조하였음