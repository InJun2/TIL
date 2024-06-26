# 8 Week ( 12 챕터 ~ 13 챕터 )

## 12. 병렬 프로그래밍 테스트
- concurrent 패키지 테스트 실패시 테스트 케이스를 모아 tearDown 메소드에서 모든 오류 상황을 표시
    - 이 기반 클래스를 사용시 모든 테스트를 진행할 때 항상 특정 테스트에서 생성된 스레드는 해당 테스트가 종료되기 직전에 모두 종료돼야 함
    - JSR166TestCase.class
- 메모리의 상황을 볼 수 있는 힙 조사(heap inspection)용 도구
- CPU 프로세서의 개수, 운영체제, 프로세서 아키텍처 등을 다양하게 변경하면서 테스트한다고 하는데 실제에서도 그렇게 진행하는지
    - Thread.yield 메서드란?
- 배리어란?

<br>

# 12. 병렬 프로그램 테스트
- 병렬 프로그램을 테스트한 결과는 안전성과 활동성의 문제로 귀결됨
- 활동성은 진행중인 상태와 진행이 멈춘 상태를 테스트 하는 경우가 많고, 이때 더 이상 실행되지 않는 것처럼 보이는 경우가 생기면 실행 속도가 느린건지 실행도중 멈추는 오류가 발생한건지 확인해야 함
- 활동성 문제를 테스트하는 것은 성능 문제를 테스트 하는 것과 밀접한 관련이 있음
- 성능은 아래와 같이 수치화해 측정할 수 있음
	 - 처리량(throughput) : 병렬로 실행되는 여러 개의 작업이 각자가 할일을 끝내는 속도
	 - 응답성(responsiveness) : 요청이 들어온 이후 작업을 마치고 결과를 줄 때까지의 시간. 지연시간(latency) 이라고도 함
	 - 확장성(scalability) : 자원을 더 많이 확보할 때마다 그에 따라 처리할 수 있는 작업량이 늘어나는 정도

 </br>

## 정확성 테스트
~~~java
@ThreadSafe
public class BoundedBuffer<E> {
    private final Semaphore availableItems, availableSpaces;
 
    @GuardedBy("this") private final E[] items; 
    @GuardedBy("this") private int putPosition = 0, takePosition = 0;
    
    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity); 
        items = (E[]) new Object[capacity];
    }
    
    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }
    
    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }
    
    public void put(E x) throws InterruptedException {  
        availableSpaces.acquire();  
        doInsert(x);
        availableItems.release(); 
    }
    
    public E take() throws InterruptedException { 
        availableItems.acquire(); 
        E item = doExtract();
        availableSpaces.release(); 
        return item;
    } 
    
    private synchronized void doInsert(E x) {
        int i = putPosition;
        items[i] = x;
        putPosition = (++i == items.length) ? 0 : i;
    }
    
    private synchronized E doExtract() {
        int i = takePosition;
        E x = items[i];
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : i;
        return x;
    }
}
~~~
- Semaphore를 사용해 크기를 제한하고 제한된 크기를 초과한 경우에 대기상태에 들어가도록 함
- put(), take()는 개수가 지정된 세마포어를 사용해 동기화를 맞추고 있음
- avaliableItems 세마포어는 현재 버퍼 내부에서 뽑아 낼 수 있는 항목의 개수를 담고 있음
- take()는 avaliableItems 세마포어에서 가져갈 항목이 있는지에 대한 확인을 받아야 함
	- 버퍼에 항목이 하나 이상 들어 있었다면 즉시 확인에 성공하고, 버퍼가 비어있다면 대기 상태에 들어감


### 12.1.1 가장 기본적인 단위 테스트
- 가장 기본적인 단위 테스트는 BoundedBuffer 인스턴스를 하나 생성하고, 다양한 메소드를 호출한 뒤 최종 상태와 변수 값 등을 확인해보는 방법
~~~java
public class BoundedBufferTest extends TestCase {
   void testIsEmptyWhenConstructed() {
       BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
       assertTrue(bb.isEmpty());
       assertFalse(bb.isFull());
   }
   
   void testIsFullAfterPuts() throws InterruptedException {
       BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
       for(int i = 0; i < 10; i ++) {
           bb.put(i);
       }
       assertTrue(bb.isFull());
       assertFalse(bb.isEmpty());
   }
}
~~~
- 용량이 N인 BoundedBuffer 클래스에 N개의 항목을 추가하고, 버퍼 클래스 스스로가 용량이 가득찼다고 표현하는 코드를 테스트하는 케이스


### 12.1.2 블로킹 메소드 테스트
- 따로 실행되고 있는 스레드에서 성공과 실패 여부를 파악하는 경우에, 파악된 성공 또는 실패 여부를 다시 원래 테스트 케이스의 메소드에 알려줄 수 있는 방법이 마련돼 있어야 테스트 결과를 단위 테스트 프레임웍에서 제대로 리포팅할 수 있음
- 대기 상태에 들어가는 메소드를 테스트할 때는 복잡한 상황이 존재함
	- 테스트 시, 어떤 방법으로건 대기 상태를 풀어서 대기 상태에 들어갔었음을 확인해야 함
	- 가장 확실한 방법은 인터럽트를 거는 방법
- 인터럽트를 활용해 테스트하려면 대기 상태에 들어갈 대상 메소드가 인터럽트에 적절하게 대응하도록(인터럽트 걸리는 즉시 리턴되거나 InterruptedException을 던지는) 만들어져 있어야함
~~~java
void testTakeBlocksWhenEmpty() {
    final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
    Thread taker = new Thread() {
        @Override
        public void run() {
            try {
                int unused = bb.take();
                fail();  
            } catch (InterruptedException success) { }
        }
    };
    
    try {
        taker.start();
        Thread.sleep(LOCKUP_DETECT_TIMEOUT);
        taker.isInterrupted();
        taker.join(LOCKUP_DETECT_TIMEOUT);
        assertFalse(taker.isAlive());
    } catch (Exception unexcepted) {
        fail();
    }
}
~~~
- taker 스레드를 실행하고 적당량 이상 대기 후, taker에 인터럽트를 걺
- 만약 taker 스레드가 정사엊ㄱ으로 대기 상태에 들어가 있었다면 인터럽트가 걸렸을 때 InterruptedException을 띄울 것이고, catch 구문에서는 예외가 발생한 상황이 정상이라고 판단 후 스레드를 종료시킴
- taker 스레드를 실행시켰던 테스트 프로그램은 taker 스레드가 종료될 때까지 join 메소드로 기다리고, Thread.isAlive 메소드를 사용해 join 메소가 정상적으로 종료됐는지를 확인
	- taker 스레드가 정상적으로 인터럽트에 응답했다면 join 메소드가 즉시 종료됨
- join 메소드를 사용해 정상적으로 종료되는지를 확인하는 작업은 Thread 클래스를 직접 상속받아 사용하는 편이 나은 몇 안되는 방법 가운데 하나임
- Thread.getState 메소드를 사용하여 대기상태에 들어갔는지 확인하는 방법은 믿을만 하지 못함
	- JVM 구현 방법에 따라 스핀 대기(spin waiting) 기법을 활용할 수도 있으므로, 특정 스레드가 대기 상태라 해도 WAITING 또는 TIMED_WAITING 상태에 놓여있다고 볼 수 없기 때문
- 병렬성을 제어하는 용도로 Thread,getState 메소드를 사용하지 말아야 함


### 12.1.3 안전성 테스트
- 병렬 실행 환경에서는 올바른 테스트 프로그램을 작성하는 일이 테스트 대상 클래스 자체를 구현하는 일보다 훨씬 어려운 경우도 존재함
- 안전성을 테스트하는 프로그램을 효과적으로 작성하려면 뭔가 문제가 발생했을 때 잘못 사용되는 속성을 '높은 확률로' 찾아내는 작업을 해야 함고 동시에 오류를 확인하는 코드가 테스트 대상의 병렬성을 인위적으로 제한해서는 안된다는 점을 고려해야 함
- 테스트 하는 대상 속성의 값을 확인할 때 추가적인 동기화 작업을 하지 않아도 된다면 가장 좋은 상태라 볼 수 있음
- 프로듀서-컨슈머 디자인 패턴을 사용해 동작하는 클래스에 가장 적합한 방법은
	- 큐&버퍼에 추가된 항목을 모두 그대로 뽑아 낼 수 있는지를 확인하고, 그외에는 아무일도 하지 않는지를 확인
- 작성한 테스트 프로그램이 실제로 원하는 내용을 테스트하는지 확인하려면 사용하고 있는 체크섬 연산을 컴파일러가 예측할 수 없는 연산인지도 확인해야 함
- 똑똑한 컴파일러 때문에 발생하는 문제를 해결하기 위해선 테스트 데이터를 일련번호 대신 임의의 숫자를 생성해 사용
	- 단, 허술한 난수 발생기(random number generator)를 사용하면 테스트 결과가 잘못 나올 수 있음
- 허술한 난수 발생기(RNG)는 현재 시간과 클래스 간에 종속성이 있는 난수를 생성하는 경우가 있는데, 대부분의 난수 발생기가 스레드 안전성을 확보한 상태이며 추가적인 동기화 작업이 필요하기 때문
- 범용 난수 발생기를 사용하는 대신 간단한 난수 발생기를 사용하는 것도 좋은 방법
~~~java
static int xorShift(int y) {
    y ^= (y << 6);
    y ^= (y >>> 21);
    y ^= (y << 7);
    return y;
}
~~~
- 중간 품질의 난수 발생기
- 클래스 인스턴스의 hashCode값과 nanoTime을 사용해 xorShift 메소드를 사용하면 거의 예측이 불가능하며 실행할 때마다 새로운 난수를 생성함
~~~java
public class PutTakeTest {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
    private final CyclicBarrier barrier;
    private final BoundedBuffer<Integer> bb;
    private final int nTrials, nPairs;

    public static void main(String[] args) {
        new PutTakeTest(10, 10, 10000).test(); // 예제 인자 값
        pool.shutdown();
    }

    PutTakeTest(int capacity, int nPairs, int nTrials) {
        this.bb = new BoundedBuffer<Integer>(capacity);
        this.nPairs = nPairs;
        this.nTrials = nTrials;
        this.barrier = new CyclicBarrier(nPairs * 2 + 1);
    }

    void test() {
        try {
            IntStream.range(0, nPairs)
                    .forEach(i -> {
                        pool.execute(new Producer());
                        pool.execute(new Consumer());
                    });
            barrier.await(); // 모든 스레드가 준비될 때까지 대기
            barrier.await(); // 모든 스레드의 작업이 끝날 때까지 대기
            assertEquals(putSum.get(), takeSum.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int) System.nanoTime());
                int sum = 0;
                barrier.await();
                for(int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for(int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
~~~
- 각 스레드마다 체크섬을 따로 운영하면 따로 동기화할 필요가 없으며, 경쟁이 발생하지 않아 테스트에만 집중 가능
- 스레드가 처리할 작업이 굉장히 짧은 시간이면 충분한 작업일 때, 이와 같은 스레드를 반복문 내부에서 차례로 생성해 실행시킨다면 최악의 경우에는 각 스레드가 순차적으로 실행될 가능성이 존재함
- CountDownLatch를 사용하여 모든 스레드가 준비될 때까지 대기하거나 모든 스레드가 완료될 때까지 대기하는 방법이 존재함
- CyclicBarrier를 사용해도 비슷한 효과를 낼 수 있음
	- 전체 작업 스레드의 개수에 1을 더한 크기로 초기화해두고, 작업 스레드와 테스트 프로그램이 시작하는 시점에 동시에 시작할 수 있도록 대기하고, 끝나는 시점도 한번에 끝내도록 대기하는 방법 
- 테스트 프로그램은 스레드가 교차 실행되는 경우의 수를 최대한 많이 확보할 수 있도록 CPU가 여러개 장착된 시스템에서 돌려보는 것이 좋음
- 절묘한 타이밍에 공유된 데이터를 사용하다 나타나는 오류를 찾으려면 CPU가 많이 있는 것보다 스레드를 더 많이 돌리는 것이 나음
- 스레드가 많아지면 실행 중인 스레드와 대기 상태에 들어간 스레드가 서로 교차하면서 스레드 간 상호작용이 발생하는 경우의 수가 많아지기 때문
- 미리 지정된 개수만큼 연산을 실행하고 테스트를 마치는 프로그램은 테스트가 종료되지 않고 계속 실행될 가능성이 존재하기 때문에 시간 제한을 두고 테스트를 중단하도록 해야함


### 12.1.4 자원 관리 테스트
- 메모리를 원하지 않음에도 불구하고 계속해서 잡고 있는 경우가 있는지 확인하려면 힙 조사(heap inspection) 도구를 사용해볼 만 함
~~~java
class Big {
        double[] data = new double[10000];
}

void teskLeadk() throws InterruptedException {
    BoundedBuffer<Big> bb = new BoundedBuffer<Big>(CAPACITY);
    int heapSize1 = /* 힙 스냅샷 */ ;
    for(int i = 0; i < CAPACITY; i++) {
        bb.put(new Big());
    }
    
    for(int i = 0; i < CAPACITY; i++) {
        bb.take();
    }
    
    int heapSize2 = /* 힙 스냅샷 */ ;
    
    assertTrue(Math.abs(heapSize1 - heapSize2) < THRESHOLD);
}
~~~
- 힙 조사 도구가 추가한 코드는 GC를 강제로 실행하고 힙 사용량과 기타 메모리 사용 현황을 불러오는 기능을 담당함 


### 12.1.5 콜백 사용
- 콜백 함수는 객체를 사용하는 동안 중요한 시점마다 그 내부의 값을 확인시켜주는 좋은 기회로 사용할 수 있음
- 스레드 풀이 제대로 동작하는지 테스트하려면 실행 정책에 맞게 여러 측면에서 절적한 수치를 뽑아낼 수 있는지를 테스트하면 됨
~~~java
class TestingThreadFactory implements ThreadFactory {
    public final AtomicInteger numCreated = new AtomicInteger(); 
    private final ThreadFactory factory = Executors.defaultThreadFactory();
    
    @Override
    public Thread newThread(Runnable r) {
        numCreated.incrementAndGet();
        return factory.newThread(r);
    }
}
~~~
- TestingThreadFactory 클래스는 생성된 스레드의 개수를 세는 기능을 갖고 있음
- 실제 테스트 케이스에서는 TestingThreadFactory가 알고 있는 스레드의 개수가 올바른지 확인해 볼 수 있음
- 기능이 추가된 Thread 객체를 생성하도록 코드를 변경하면 생성된 스레드가 언제 종료되는지를 추적할 수 있음
	- 테스트 케이스는 없어져야 할 스레드가 적절한 시점에 올바르게 사라지는지도 확인 가능
- 코어 풀 크기가 최대 풀 크기보다 작게 설정돼 있다면 실행할 대상이 늘어날 때마다 스레드의 개수가 함께 늘어나야 함
- 스레드 풀에 오래 실행될 작업을 많이 추가해두면, 스레드 개수가 올바르게 늘어나는지 등의 수치를 확인할 수 있음
~~~java
public void testPoolExpansion() throws InterruptedException  {
    int MAX_SIZE = 10;
    Example12_8.TestingThreadFactory threadFactory = new Example12_8.TestingThreadFactory();
    ExecutorService exec = Executors.newFixedThreadPool(MAX_SIZE, threadFactory);
    
    for(int i = 0; i < 10 * MAX_SIZE; i++) {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
    
    for(int i = 0; i < 20 && threadFactory.numCreated.get() < MAX_SIZE; i++) {
        Thread.sleep(100);
    }
    
    assertEquals(threadFactory.numCreated.get(), MAX_SIZE);
    exec.shutdownNow();
}
~~~


### 12.1.6 스레드 교차 실행량 확대
- CPU 프로세서의 개수, 운영체제, 프로세서 아키텍처 등을 다양하게 변경하면서 테스트해보면 특정 시스템에서만 발생하는 오류를 찾아낼 수 있음
- 스레드 교차 실행 정도를 크게 높이고, 테스트할 대상 공간을 크게 확대 시킬 수 있는 트릭은 공유된 자원을 사용하는 부분에서 Thread.yield 메소드를 호출해 컨텍스트 스위치가 많이 발생하도록 유도할 수 있음
~~~java
public synchronized void transferCredis(Account from, Account to, int amount) {
    from.setBalance(from.getBalance - amount);
    if(random.nextInt(1000) > THREADHOLD) {
        Thread.yield();
    }
    to.setBalance(to.getBalance + amount);
}
~~~
- 작업 도중 Thread.yield 메소드를 호출해주면 공유된 데이터를 사용할 때 적절한 동기화 방법을 사용하지 않은 경우 특정한 타이밍에 발생할 수 있는 버그가 노출되는 가능성을 높일 수 있음

</br>

## 12.2 성능 테스트
- 성능 테스트는 특정한 사용 환경 시나리오를 정해두고, 해당 시나리오를 통과하는데 얼마만큼의 시간이 걸리는지를 측정하고자 하는데 목적이 있음
- 성능 테스트의 두번째 목적은 성능과 관련된 스레드의 개수, 버퍼의 크기 등과 같은 각종 수치를 뽑아내고자 함

</br>

### 12.2.1 PutTakeTest에 시간 측정 부분 추가
- 단일 연산을 굉장히 많이 실행하여 전체 실행 시간을 구한 다음 실행했던 연산의 개수로 나누고, 단일 연산을 실행하는 데 걸린 평균 시간을 찾는 방법이 정확함
~~~java
public class BarrierTimer implements Runnable{
    private boolean started;
    private long startTime, endTime;
    
    @Override
    public synchronized void run() {
        long t = System.nanoTime();
        if(!started) {
            started = true;
            startTime = t;
        } else 
            endTime = t;
    }
    
    public synchronized void clear() {
        started = false;
    }
    
    public synchronized long getTime() {
        return endTime - startTime;
    }
}
~~~
- 배리어(barrier)가 적용되는 부분에서 시작 시간과 종료 시간을 측정할 수 있도록 기존 클래스를 확장할 수 있음
- 시간 측정 기능을 갖고 있는 배리어 액션을 사용하도록 하려면 CyclicBarrier를 초기화 하는 부분에 원하는 배리어 액션을 지정함
~~~java
this.timer = new BarrierTimer();
this.barrier = new CyclicBarrier(npairs * 2 + 1, timer);
~~~

</br>

~~~java
public void test() {
    try {
        timer.clear();
        for(int i = 0; i < nPairs; i++) {
            pool.execute(new Producer());
            pool.execute(new Consumer());
        }
        barrier.await();
        barrier.await();
        long nsPerItem = timer.getTime() / (nPairs * (long) nTrials);
        System.out.println("Throughput : " + nsPerItem + " ns/item");
        assertEquals(putSum.get(), takeSum.get());
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
~~~
- 베리어 기반의 타이머를 사용하도록 변경한 메소드

</br>

~~~java
public static void main(String[] args) throws Exception {
    int tpt = 100000; // 스레드별 실행 횟수
    for(int cap = 1; cap <= 1000; cap *= 10) {
        System.out.println("Capacity : " + cap);
        for(int pairs = 1;  pairs <= 128; pairs *= 2) {
            TimedPutTakeTest t = new TimedPutTakeTest(cap, pairs, tpt);
            System.out.print("Pairs: "+ pairs + "\t");
            t.test();
            System.out.print("\t");
            Thread.sleep(1000);
            t.test();
            System.out.println();
            Thread.sleep(1000);
        }
    }
    pool.shutdown();
}  
~~~
- 버퍼의 크기를 얼마로 제한해야 최고 성능을 알아내는지 알아보기 위해, 여러가지 인자에 다양한 값을 설정하면서 테스트 프로그램을 실행해봐야 함
- 위와 같은 테스트 실행 프로그램을 사용하면 편리함

- CPU가 4개 장착된 하드웨어서 버퍼의 크기를 1, 10, 100, 1000으로 변경하면서 실행한 결과
- 버퍼 크기가 1인 경우, 각 스레드가 대기 상태에 들어가고 나오면서 아주 적은 양의 작업밖에 할 수 없기 때문에 성능이 떨어지는 것은 당연
- 10을 넘는 크기를 지정하면 버퍼의 크기에 비해 성능이 향상되는 정도가 떨어지는 것을 볼 수 있음
- 스레드가 많이 실행되고 있더라도, 실제 작업을 하는 양은 많지 않고 스레드가 대기 상대에 들어갔다 나왔다하는 동기화를 맞추느라 CPU 용량의 대부분을 사용하기 때문
- 프로듀서-컨슈머 패턴으로 움직이는 애플리케이션을 생각한다면 항목을 생성하고 사용하는 과정에서 무시할 수 없을 만큼 상당한 양의 작업이 이뤄질 것
	- 스레드를 너무 많이 추가했다가는 그 여파를 눈으로 확인할 수 있게 됨..


### 12.2.2 다양한 알고리즘 비교
- BoundedBuffer 클래스의 속도가 떨어지는 가장 큰 이유는 put,take 연산 양쪽에서 모두 스레드 경쟁을 유발할 수 있는 연산을 사용하기 때문
	- 세마포어를 확보하거나 락을 확보하고 세마포어를 다시 해제하는 등의 연산
- 듀얼 하이퍼스레드 CPU가 장착된 하드웨어에서 버퍼 크기가 256인 클래스 3개를 비교 실행한 결과
- 연결 큐는 새로운 항목을 추가할 때마다 버퍼 항목을 메모리에 새로 할당 받아야하기 때문에 배열 기간의 큐보다 더 많은 일을 해야함
- 연결 리스트 기반의 큐는 put,take 연산에 대해 배열 기반의 큐보다 병렬 처리 환경에서 훨씬 안정적으로 동작함
	- 큐의 처음과 끝 부분에 서로 다른 스레드가 동시에 접근해 사용할 수 있기 때문
- 메모리 할당 작업은 일반적으로 스레드 내부에 한정돼 있기 떄문에 메모리를 할당한다 해도 스레드 간의 경쟁을 줄일 수 있는 알고리즘의 확장성이 더 높을 수 밖에 없음


### 12.2.3 응답성 측정
- 단일 작업 처리 시간을 측정할 때는 보통 측정 값의 분산(variance)을 중요한 수치로 생각함
	- 간혹 평균 처리 시간은 길지만 처리 시간의 분산이 작은 값을 유지하는 일이 더 중요할 수 있기 때문
- '예측성' 역시 중요한 성능 지표 가운데 하나임을 알아야 함
- 처리 시간에 대한 분산을 구해보면 "100밀리초 안에 작업을 끝내는 비율이 몇 % 정도인가?"와 같은 서비스 품질에 대한 수치를 결과로 제시할 수 있음
- 서비스 시간에 대한 분산을 시각적으로 표현할 수 있는 가장 효과적인 방법은 작업을 처리하는데 걸린 시간을 히스토그램으로 그려보는 방법
- 작업 처리 시간을 모두 더할 뿐 아니라 각 처리 시간을 목록으로 관리하고 있어야 함

- 버퍼의 크기를 1000으로 지정하고 256개의 병렬 작업이 각각 1000개의 항목을 버퍼에 넣음
	- 한쪽은 공정한 세마포어를 사용(회색)
	- 다른쪽은 불공정 세마포어를 사용(흰색)
- 불공정 세마포어를 사용한 결과는 최소 시간과 최대 시간의 차이가 80배가 넘음
- 최소 시간과 최대 시간의 차이를 줄이기 위해 동기화 코드에 공정성을 높이면 됨
- BoundedBuffer의 경우 세마포어를 생성할 때 공정한 모드로 초기화시켜 공정성을 높일 수 있음
- 동기화 부분에 공정성을 높이면 처리시간의 분산 값을 줄여주는 효과가 있지만, 처리 속도가 크게 떨어지는 역효과가 나타남

- 공정함의 문제가 평균 실행 시간을 크게 늦추거나 실행시간의 분산을 훨씬 낮게 바꿔주지 못한다는 사실이 나타남
- 스레드가 아주 빡빡한 동기화 요구사항 때문에 계속해서 대기 상태에 들어가는 상황이 아니라면 불공정한 세마포어를 사용해 처리 속도를 크게 높일 수 있고, 반대로 공정한 세마포어를 사용해 처리 시간의 분산을 낮출 수 있음
- 세마포어를 사용할 때에는 항상 어느 방법을 사용할 것인지 결정해야만 함

</br>

## 12.3 성능 측정의 함정 피하기
- 성능을 올바르게 나타내지 못하고 잘못된 수치를 뽑아내는 잘못된 코딩 방법으로 프로그램을 작성하지 않도록 주의해야 함

</br>

### 12.3.1 가비지 컬렉션
- GC가 언제 실행될 것인지는 미리 알고 있을 수가 없으며, 시간을 측정하는 테스트 프로그램이 동작하는 동안 GC가 진행될 가능성도 높음
- GC 때문에 테스트 결과가 올바르지 않게 나오는 경우를 막을 수 있는 두가지 방법
	- 테스트가 진행되는 동안 GC가 일어나지 않도록 하는 방법
	- 테스트가 진행되는 동안 GC가 여러번 실행된다는 사실을 명확히 하고, 테스트 결과에 객체 생성 부분이나 GC 부분을 적절하게 반영하도록 하는 방법
- 일반적으로는 후자의 방법이 많이 사용됨
	- 테스트 프로그램을 훨씬 긴 시간동안 실행할 수 있으며 실제 상황에서 나타나는 성능을 좀 더 가깝게 반영하기 때문
- BoundedBuffer 클래스를 대상으로 테스트 프로그램을 적당히 오랜 시간 동작시키면 일정 횟수 이상 GC가 동작할 것이며, 실제와 유사한 성능 결과를 얻을 수 있음


### 12.3.2 동적 컴파일
- 자바 언어와 같이 동적으로 컴파일하면서 실행되는 언어로 작성된 프로그램은 성능을 측정하는 테스트 프로그램을 작성하기 어려우며, 결과를 해석하기도 어려운 면이 있음
- 핫스팟 JVM이나 최근 사용되는 JVM은 바이트코드 인터프리트 방식과 동적 컴파일 방법을 혼용해 사용함
	- 클래스의 바이트코드를 처음 읽어들인 이후 인터프리터를 통해 바이트코드를 실행
	- 일정 시점이 지난 이후 메소드가 특정 횟수 이상 실행되면, 동적 컴파일러에 의해 해당 메소드를 기계어 코드로 컴파일 함
	- 컴파일이 완료되면 그 이후에는 인터프리트 대신 컴파일된 코드를 직접 실행함
- 실행 시간을 측정하는 테스트 프로그램은 대상 클래스의 코드가 모두 컴파일된 이후에 실행돼야 마땅함
	- 대부분의 애플리케이션은 실제 사용할 때 필요한 모든 메소드가 컴파일 된 상태에서 실행된다고 봐야하기 때문에, 인터프리트되는 코드의 실행 속도는 측정할 가치가 없기 때문

- 각각의 조건에서 동일한 횟수의 테스트 모듈을 실행하는 과정을 보여줌
- A는 컴파일하지 않고 계속해서 인터프리터로 실행
- B는 인터프리터로 실행하다가 중간에 컴파일해 실행
- B는 컴파일을 진행하고 실행
- 컴파일 작업이 언제 실행되는지가 전체 실행시간에 큰 영향을 미치고, 단일 연산에 소모되는 시간 역시 영향을 미침
- 컴파일된 코드와 컴파일되지 않은 코드 때문에 성능 측정치가 올바르지 않게 나타나는 상황을 예방하는 가장 간단한 방법은 테스트 프로그램을 긴 시간동안 실행시켜 컴파일될 부분은 모두 컴파일되고, 추가로 컴파일하거나 인터프리터로 실행되는 코드를 최소화하는 방법
- 핫스팟 JVM을 사용하는 경우라면 `-XX:+PrintCompilation` 옵션을 사용하여 동적 컴파일이 실행될 때 메시지를 출력할 수 있음
- 동일한 테스트 프로그램을 하나의 JVM에서 여러번 실행해보고, 그 중 적당한 테스트 결과를 골라낼 수 있음
	- 초기 실행 결과는 워밍업 과정으로 보고 제외


### 12.3.3 비현실적인 코드 경로 샘플링
- 런타임 컴파일러는 컴파일할 코드에 대한 최적화 정보를 얻기 위해 실행 과정에서 여러 가지 성능 값을 추출함
- 특정 애플리케이션에서 사용하는 시나리오 패턴만을 묘사해 테스트하는 것보다는 그와 유사한 다른 시나리오 패턴도 한데 묶어 테스트하는 일도 중요함
- 단일 스레드 프로그램의 성능을 테스트하고자 할 때도 단일 스레드 프로그램의 성능뿐만 아니라 멀티스레드 애플리케이션의 성능도 함꼐 테스트 하는 것이 좋음


### 12.3.4 비현실적인 경쟁 수준
- 병렬 애플리케이션은 두 종류의 작업을 번갈아가며 실행하는 구조로 동작
- 전체 작업을 두 종류의 작업으로 구분해 봤을 때, 각각 얼마만큼의 비율을 차지하는지에 따라 경쟁 수준이 달라지고 성능과 확장성의 측면에서 굉장히 다른 결과를 내놓게 됨
- 병렬 테스트 프로그램에서 실제 상황과 유사한 결과를 얻으려면 직접적으로 알고자 하는 부분, 즉 병렬 처리 작업을 조율하는 동기화 부분의 성능과 함께 스레드 내부에서 실행되는 작업의 형태도 실제 애플리케이션과 비슷한 특성을 띠고 있어야 함
- synchronizedMap 메소드로 생성한 락 동기화 기반의 Map을 놓고 봤을 때 락을 확보하려는 부분에서 스레드 간의 경쟁 빈도에 따라 성능 측정지에 영향을 미침


### 12.3.5 의미 없는 코드 제거
- 훌륭한 성능 측정 프로그램을 작성하려면 최적화 컴파일러가 의미 없는 코드를 제거하는 과정에 성능 측정상 필요한 부분까지 제거하지 않도록 약간의 편법을 써야 할 필요가 있음
- 프로그램 코드가 만들어내는 모든 결과 값을 프로그램 어디선가 사용하도록 해야함
	- 이로 인해, 추가적으로 동기화를 해야하거나 더 많은 자원을 소모하도록 하지는 않는 것이 좋음

</br>

## 12.4 보조적인 테스트 방법
- 프로그램이 조금만 복잡해진다면 아무리 테스트를 많이 한다고 해도 오류를 모두 잡아내는 일이 불가능함
- 테스팅의 목적은 '오류를 찾는 일'이 아니라 대상 프로그램이 처음 작성할 때 설계 했던 대로 동작한다는, '신뢰성을 높이는 작업'이라고 봐야함
- 병렬 프로그램은 오류가 발생할 가능성이 더 높기 때문에 훨씬 많이 테스트 해야함
- 테스팅 작업은 병렬 프로그램이 제대로 동작한다는 신뢰도를 높이는 과정에서 아주 중요한 역향을 담당하고 있지만, 사용할 수 있는 여러가지 QA 방법중에 하나로 인식해야 함
- QA 방법론에도 여러가지가 있는데, 각 방법을 적절하게 활용하면 오류의 유형에 따라 효율적으로 오류를 발견할 수 있음
- 코드리뷰나 정적분석과 같이 상호 보완적인 테스트 방법을 사용하면 신뢰도를 크게 높일 수 있음

</br>

### 12.4.1 코드 리뷰
- 코드 리뷰는 단위 테스트와 성능 테스트만큼 중요한 테스트 방법
- 코드를 직접 작성하지 않은 다른 사람에게 코드를 살펴보도록 하는 일도 게을리해서는 안됨
- 단순하게 문제점을 찾아내는 것 뿐만 아니라 코드 리뷰와 함께 소스코드 주석문에 대한 자세한 설명을 추가하는 일을 함께 하면서 추후 발생할 유지보수 비용을 낮출 수 있음


### 12.4.2 정적 분석 도구
- 정적 코드 분석 방법은 코드를 실행하지 않고 그 자체로 분석하며, 코드 감사(audit) 도구를 사용하면 클래스 파일 내부에 흔히 알려진 여러가지 버그 패턴 가운데 해당하는 부분이 있는지를 확인해줌
- FindBugs와 같은 정적 분석 도구에는 버그 패턴 감지기가 포함돼 있고, 단위 테스트나 성능 테스트, 코드리뷰 등의 과정에서 빼먹기 쉬운 일반적인 오류를 발견할 수 있음
- 정적 분석 도구를 실행시키면 경고할만한 부분을 목록으로 리포팅 해주며, 리포팅된 부분이 오류인지 아닌지는 반드시 사람이 직접 확인해야 함
- **일관적이지 않은 동기화**
	- 다수의 클래스는 클래스 내부의 변수를 자신의 암묵적인 락으로 동기화한다는 동기화 정책을 사용
	- 특정 변수가 동기화 블록 내부에서 사용되는 경우가 많으면서 일부는 동기화 블록 외부에서도 사용되는 경우가 있다면 해당 변수에 대해 동기화 정책이 정확하게 적용되지 않은 경우
	- 추후 @GaurdedBy와 같은 어노테이션이 표준화된 이후에는 감사 도구에서 변수와 락 간의 관계를 분석하는 등의 방법으로 동기화 정책을 추측하는 대신 어노테이션을 직업 분석해 정적 분석 결과의 품질 향상이 가능함
- **Thread.run 호출**
	- 일반적으로 Thread.start 메소드를 호출하는 대신 Thread.run을 호출하는 일은 잘못된 방법인 경우가 많음
- **해제되지 않은 락**
	- 명시적인 락은 해당 락을 확보한 블록이 실행을 마치고 빠져나갔다 해도 락이 자동으로 해제되지 않음
- **빈 synchronized 블록**
	- 자바 메모리 모델상에서는 비어있는 synchronized 블록이 의미가 있을 수 있겠지만, 실제로는 대부분 잘못 사용된 경우가 많음
- **더블 체크 락(double-checked lock)**
	- 늦은 초기화 방법에서 발생하는 동기화 부하를 줄이기 위한 방법
	- 동기화 능력이 부족하기 때문에 공유된 변경 가능한 값을 읽어가는 등의 경우가 발생할 가능성이 존재
- **생성 메소드에서 스레드 실행**
	- 생성 메소드에서 새로운 스레드를 실행시키도록 한다면, 해당 클래스를 상속받았을 때 문제가 생길 수 있음
	- this 변수를 스레드에게 노출시킬 수 있는 위험이 존재
- **알림 오류**
	- notify와 notifyAll 메소드는 항상 해당 조건과 관련된 상태가 변경됐을 때만 사용해야 함
	- synchronized 블록 내부에서 notify나 notifyAll 메소드를 호출하지만 상태를 변경하지 않은 상태라면 오류일 가능성이 높음
- **조건부 대기 오류**
	- 조건 큐에서 대기할 때는 필요한 락을 확보한 상태에서 상태 변수를 확인한 이후에 Object.wait나 Condition.await 메소드를 반복문으로 감싸는 구조로 구현해야 함
	- 락을 확보하지 않은 상태이거나, 반복문으로 감싸지 않은 상태이거나, 상태 변수를 제대로 확인하지 않은 상황에서 Object.wait나 Condition.await 메소드를 호출하도록 돼 있다면 오류일 가능성이 높음
- **Lock과 Condition의 오용**
	- synchronized 블록에 락인자를 넣을 때 Lock이라는 클래스 이름을 지정한다거나, 아니면 Condition.await 메소드를 호출하는 대신 Conditaion.wait 메소드를 호출하는 경우는 오타로 인해 동기화 구문을 잘못 작성하는 예임
- **락을 확보하고 대기 상태 진입**
	- 락을 확보한 상태에서 Thread.sleep을 호출하면 락을 필요로 하는 다른 스레드 역시 아무 일도 못하고 대기 상태에 들어가게 할 수 있으며 따라서 나중에 활동성에 심각한 영향을 줄 수 있음
	- 락을 두 개 확보한 상태에서 Object.wait 메소드를 호출하거나 Condition.await 메소드를 호출하는 경우 역시 비슷한 문제의 원인
- **스핀 반복문**
	- 아무런 일도 하지 않으면서 특정 변수의 값이 원하는 상태에 도달할 때까지 계속해서 반복하기만 하는 반복문을 사용하면 CPU 자원을 엄청나게 소모할 뿐만 아니라 해당 변수가 volatile이 아니라면 무한 반복에 빠질 수 있음
	- 원하는 형태로 상태 변수 값이 변경되기를 기다리는 경우에는 래치나 여러가지 조건부 대기 기능을 활용하는 편이 안전함


### 12.4.3 관점 지향 테스트 방법
- 관점 지향 프로그래밍(AOP) 기법이 병렬 프로그래밍 분야에 적용되는 사례는 굉장히 제한적임
	- AOP 도구가 아직은 동기화 관련 지점에서 포인트컷을 지원하고 있지 않기 때문
- AOP를 사용하여 상태 변수의 값이 동기화 정책에 잘 맞는지를 확인하는 등의 작업을 하도록 적용해 볼 수 있음
	- 스레드 안전성이 보장되지 않는 스윙 메소드를 호출하는 모든 부분에 관점을 적용해 항상 이벤트 스레드에서만 스윙 클래스의 메소드를 호출하는지 확인
- AOP의 특성상 코드를 따로 변경해야 할 필요가 없으니 이런 기법은 적용하기도 간편하고, 사소한 변수 공개 상황이나 스레드 한정 오류와 같은 부분을 찾아내기에 좋음


## 12.4.4 프로파일러와 모니터링 도구
- 대부분의 상용 프로파일링 도구에는 스레드의 동작 상황을 살펴볼 수 있는 모듈이 포함되어 있음
	- 테스트 대상 프로그램이 도대체 무슨 일을 하는지에 대한 내부적인 정보를 들여다보기에 좋은 방법
- 대부분 각 스레드의 실행 상태를 여러가지 색으로 구분해 시간이 지나감에 따라 어떻게 실행되는지를 표시하는 기능을 갖고 있음
- 결과 그래프를 보면 CPU를 얼마나 충분하게 활용하고 있으며, 만약 CPU을 충분하게 사용하지 못했다면 그 원인이 어디에 있는지도 대략 알 수 있음
- 자바에 내장된 JMX 에이전트를 사용하는 것도 제한적이나마 스레드의 상태를 모니터링 할 수 있는 방법
- ThreadInfo 클래스를 보면 스레드의 현재 상태 ID를 갖고 있고, 만약 스레드가 대기상태에 들어가 있다면 어떤 락을 놓고 대기중인지도 알 수 있음
- '스레드 경쟁 모니터링' 기능이 켜져 있다면 락이나 알림을 대상으로 몇번이나 대기 상태에 들어갔었는지를 ThreadInfo클래스에 저장하고 대기 상태에서 소모한 누적 값도 보관함

</br>

## 요약
- 병렬 프로그램이 올바르게 동작하는지 테스트하는 일은 굉장히 어려운 작업
	- 병렬 프로그램에서 발생하는 오류는 대부분 발생 확률이 아주 작은 타이밍 문제, 부하 문제, 아니면 기타 쉽게 발현되지 않는 여러 원인에 의해 발생하기 때문
- 숨어 있는 버그를 상용 서비스 이전에 발견할 수 있는 가장 좋은 방법은 바로 전통적인 테스트 방법과 코드 리뷰, 자동화된 분석 도구를 사용하는 방법
- 각 방법은 모두 다른 방법이 잘 찾아내지 못하는 오류를 찾아낼 수 있으니, 다양한 방법을 동원해 테스트해야 오류를 최대한 줄일 수 있음

<br>

# 13. # 명시적인 락
- ReentranLock은 암묵적인 락으로 할 수 없는 일도 처리할 수 있도록 고급 기능을 갖고 있음

## 13.1 Lock과 ReetrantLock
~~~java
public interface Lock {
	void lock();
	void lockInterruptibly() throws InterruptedException;
	boolean tryLock();
	boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException;
	void unlock();
	Condition newCondition();
}
~~~
- Lock 인터페이스는 여러가지 락 관련 기능에 대한 추상 메소드를 정의하고 있음
	- 조건 없는 락, 폴링 락, 타임아웃이 있는 락, 락 확보 대기 상태에 인터럽트를 걸 수 있는 방법이 포함되어 있음
	- 락을 확보하고 해제하는 모든 작업이 명시적임
- ReetrantLock 클래스 역시 Lock 인터페이스를 구현함
	- synchronized 구문과 동일한 메모리 가시성과 상호 배제 기능을 제공
- ReetrantLock는 synchronized 키워드와 동일하게 재진입이 가능하도록 허용함
- ReetrantLock는 Lock에 정의돼 있는 락 확보 방법을 모두 지원함
	- 락을 제대로 확보하기 어려운 시점에 synchronized 블록을 사용할 때보다 훨씬 능동적으로 대처할 수 있음
- 암묵적인 락만 사용해도 대부분의 경우에 별 문제 없이 사용할 수 있지만 기능적으로 제한되는 경우가 발생함
	- 락을 확보하고자 대기하고 있는 상태의 스레드에는 인터럽트를 거는 일이 불가능하고, 대기 상태에 들어가지 않으면서 락을 확보하는 방법 등이 필요한 상황이 있기 때문
- 암묵적인 락은 synchronized 블록이 끝나는 시점에 해제되어 이런 구조는 코딩하기에 간편하고 예외 처리 루틴과 잘 맞아 떨어지는 구조이긴 하지만 블록의 구조를 갖추지 않은 상황에서 락을 걸어야 하는 경우 적용이 불가능함
~~~java
Lock lock = new ReentrantLock();
// ...
lock.lock();
try {
    //객체 내부 값을 사용
    // 예외가 발생한 경우, 적절하게 내부값을 복원해야 할 수도 있음
} finally {
    lock.unlock();
}
~~~
- finally 블록에서 반드시 락을 해제해야 함
	- 예외 때문에 해당 객체가 불안정한 상태가 될 수 있다면 try-catch 구문이나 try-finally 구문을 추가로 지정해 안정적인 상태를 유지하도록 해야함
- 락을 해제하는 기능을 finally 구문에 넣어두지 않은 코드는 언제 터질지 모르는 시한폭탄과 같음
- ReetrantLock을 사용하면 해당하는 블록의 실행이 끝나고 통제권이 해당 블록을 떠나는 순간 락을 자동으로 해제하지 않기 때문에 굉장히 위험한 코드가 될 가능성이 높음

</br>

### 13.1.1 폴링과 시간 제한이 있는 락 확보 방법
- tryLock 메소드가 지원하는 폴링 락 확보 방법이나 시간 제한이 있는 락 확보 방법은 오류가 발생했을 때 무조건적으로 락을 확보하는 방법보다 오류를 잡아내기에 훨씬 깔끔한 방법
- 락을 확보할 때 시간 제한을 두거나 폴링하도록 하면 다른 방법, 즉 확률적으로 데드락을 회피할 수 있는 방법을 사용할 수 있음
- 락을 확보할 때 시간 제한을 두거나 폴링 방법(tryLock)을 사용하면 락을 확보하지 못하는 상황에도 통제권을 다시 얻을 수 있으며, 그러면 미리 확보하고 있던 락을 해제하는 등의 작업을 처리한 이후 다시 락을 재시도 할 수 있음
~~~java
public boolean transferMoney(Account fromAcct, Account toAcct, DollarAmount amount, long timeout, TimeUnit unit) throws InsufficientFundsException, InterruptedException {
    long fixedDelay = getFixedDelayComponentNanos(timeout, unit);
    long randMod = getRandomDelayModulusNanos(timeout, unit);
    long stopTime = System.nanoTime() + unit.toNanos(timeout);
    
    while (true) {
        if(fromAcct.lock.tryLock()) {
            try {
                if(toAcct.lock.tryLock()) {
                    try {
                        if(fromAcct.getBalance().compareTo(amount) < 0) {
                            throw new InsufficientFundsException();
                        } else {
                            fromAcct.debit(amount);
                            toAcct.credit(amount);
                            return true;
                        }
                    } finally {
                        toAcct.lock.unlock();
                    }
                }
            } finally {
                fromAcct.lock.unlock();
            }
        }
        
        if(System.nanoTime() >= stopTime) {
            return false;
        }
        NANOSECONDS.sleep(fixedDelay + rnd.nextLock() % randMod);
    }
}
~~~
- tryLock 메소드로 양쪽 락을 모두 확보하도록 돼 있지만 만약 양쪽 모두 확보할 수 없다면 잠시 대기했다가 재시도하도록 되어있음
- 대기하는 시간 간격은 라이브락이 발생할 확률을 최대한 줄일수 있도록 고정된 시간 또는 임의의 시간만큼 대기
- 만약 지정된 시간 이내에 락을 확보하지 못했다면 transferMoney 메소드는 오류가 발생했다는 정보를 리턴해주고 적절한 통제하에서 오류를 처리할 수 있음
~~~java
public boolean trySendOnSharedLine(String message, 
								   long timeout, 
								   TimeUnit unit) throws InterruptedException {
    long nanosToLock = unit.toNanos(timeout) - estimatedNanosToSend(message);
    if(!lock.tryLock(nanosToLock, TimeUnit.NANOSECONDS))
        return false;
    try {
        return sendOnSharedLine(message);
    } finally {
        lock.unlock();
    }
}
~~~
- 일정 시간 내에 작업을 처리하지 못하면 무리없이 적절한 방법으로 오류를 처리
	- tryLock 메소드에 타임아웃을 지정해 사용하면 시간이 제한된 작업 구조에 락을 함께 적용해 활용하기 좋음


### 13.1.2 인터럽트 걸 수 있는 락 확보 방법
- 일정 시간 안에 처리해야 하는 작업을 실행하고 있을 때 타임아웃을 걸 수 있는 락 확보 방법을 유용하게 사용할 수 있는 것처럼, 작업 도중 취소시킬 수 있어야 하는 작업인 경우에는 인터럽트를 걸 수 있는 락 확보 방법을 유용하게 사용할 수 있음
- lockInterruptibly 메소드를 사용하면 인터럽트는 그대로 처리할 수 있는 상태에서 락을 확보함
	- Lock 인터페이스에 lockInterruptibly 메소드를 포함하고 있기 때문에 인터럽트에 반응하지 않는 다른 종류의 블로킹 구조를 만들어야 할 필요가 없음
- 인터럽트에 대응할 수 있는 방법으로 락을 확보하는 코드의 구조는 일반적으로 락을 확보하는 모습보다 약간 복잡하지만 두 개의 try 구문을 사용해야 함
~~~java
public boolean sendOnSharedLine(String message) throws InterruptedException {
    lock.lockInterruptibly();
    try {
        return cancellableSendOnSharedLine(message);
    } finally {
        lock.unlock();
    }
}

private boolean cancellableSendOnSharedLine(String message) throws InterruptedException { ... }
~~~
- lockInterruptibly를 사용해 13.4에서 구현했던 sendOnSharedLine 메소드를 구현했으며 취소 가능한 작업으로 실행됨
- 타임아웃을 지정하는 tryLock 메소드 역시 인터럽트를 걸면 반응하도록 돼 잇으며, 인터럽트를 걸어 취소시킬수도 있어야 하면서 동시에 타임아웃을 지정할 수 있어야 한다면 tryLock을 사용하는 것만으로 충분함


### 13.1.3 블록을 벗어나는 구조의 락
- 암묵적인 락을 사용하는 경우에는 락을 확보하고 해제하는 부분이 완벽하게 블록의 구조에 맞춰져 있으며, 블록이 어떤 상태로 떠나는지에 관계없이 락은 항상 자신을 확보했던 블록이 끝나는 시점에 자동으로 해제됨
- 락을 적용하는 코드를 세분화할수록 애플리케이션의 확장성이 높아짐 (11장)
	- 락 스트라이핑 방법을 적용하면 해시기반의 컬렉션 클래스에서 여러 개의 해시 블록을 구성해 블록별로 다른 락을 사용함
	- 연결 리스트 역시 해시 컬렉션과 마찬가지로 락을 세분화 할 수 있음
		- 각각의 개별 노드마다 서로 다른 락을 적용

</br>

## 13.2 성능에 대한 고려 사항
- 여러가지 동기화 기법에 있어서 경쟁 성능은 확장성을 높이는데 가장 중요한 요소
- 락과 그에 관련된 스케쥴링을 관리하느라 컴퓨터의 자원을 많이 소모하면 할수록 실제 애플리케이션이 사용할 수 있는 자원은 줄어들 수 밖에 없음
잘 만들어진 동기화 기법일수록 시스템 호출을 더 적에 사용하고 컨텍스트 스위치 횟수를 줄이고 공유된 메모리 버스에 메모리 동기화 트래픽을 덜 사용하도록 하고, 시간을 많이 소모하는 작업을 줄여주며 연산 자원을 프로그램에서 우회시킬수 있음 

- 솔라리스 운영체제가 설치된 옵테론 프로세서 4개가 장착된 하드웨어에서 자바 5.0과 자바 6에서 각각 암묵적인 락과 ReentrantLock의 성능을 비교한 결과
- 특정 자바 버전에서 암묵적인 락에 비해 ReentrantLock의 성능이 얼마나 좋아졌는지 보여줌
- 자바 5.0에서는 암묵적인 락을 사용할 때 스레드 수가 1일때 보다 스레드 개수가 늘어나면 성능이 크게 떨어짐
	- ReentrantLock을 사용하면 성능이 떨어지는 정도가 훨씬 덜하며, 따라서 확장성이 더 낫다고 볼 수 있음
- 자바 6에서는 암묵적인 락을 사용했다 해도 스레드 간의 경쟁이 있는 상황에서 성능이 그다지 떨어지지 않고 ReentrantLock을 사용할 때와 별반 차이가 없음
- 성능과 확장성은 모두 CPU의 종류, 개수, 캐시의 크기, JVM의 여러가지 특성 등에 따라 굉장히 민감하게 바뀌기 때문에 성능과 확장성에 영향을 주는 여러가지 요인은 시간이 지나면서 계속해서 바뀌기 마련
- 성능 측정 결과는 움직이는 대상
	- 어제 X가 Y보다 빠르다는 결과를 산출했던 성능 테스트를 오늘 테스트해보면 다른 결과를 얻을 수 있음

</br>

## 13.3 공정성
- ReentrantLock 클래스는 두 종류의 공정성 설정을 지원
	- 불공정 락 방법 (default)
	- 공정한 방법
- 공정한 방법을 사용할 때는 요청한 순서를 지켜가면서 락을 확보
- 불공정한 방법을 사용하는 경우에는 순서 뛰어넘기가 일어나기도 하는데, 락을 확보하려고 대기하는 큐에 대기중인 스레드가 있다 하더라도 해제된 락이 있으면 대기자 목록을 뛰어 넘어 락을 확보할 수 있음
- 불공정한 ReentrantLock이 일부러 순서를 뛰어넘도록 하지는 않으며, 딱 맞는 타이밍에 락이 해제된다 해도 큐의 뒤쪽에 있어야 할 스레드가 순서를 뛰어넘지 못하게 제한하지 않을 뿐임
- 공정한 방법을 사용하면 확보하려는 락을 다른 스레드가 사용하고 있거나 동일한 락을 확보하려는 스레드가 큐에 대기하고 있으면 항상 큐의 맨 뒤에 쌓임 
- 불공정한 방법이라면 락이 당장 사용 중인 경우에만 큐의 대기자 목록에 들어감
- 락을 관리하는 입장에서 봤을때 공정하게만 처리하다 보면 스레드를 멈추고 다시 실행시키는 동안 성능에 큰 지장을 줄 수 있음
	- 실제로 통계적인 공정함 정도만으로도 충분히 괜찮은 결과를 얻을 수 있고, 그와 더불어 성능에도 훨씬 악영향이 적음
- 대부분의 경우 공정하게 순서를 관리해서 얻는 장점보다 불공정하게 처리해서 얻는 성능상의 이점이 큼
  
- HashMap을 놓고 공정한 락과 불공정한 락을 사용한 결과를 측정함
- 스레드 간의 경쟁이 심하게 나타나는 상황에서 락을 공정하게 관리하는 것보다 불공정하게 관리하는 방법의 성능이 훨씬 빠름
	- 대기 상태에 있던 스레드가 다시 실행 상태로 돌아가고 또한 실제로 실행되기까지는 상당한 시간이 걸리기 때문
- 예를 들어 스레드 A가 락을 확보하고 있는 상태에서 스레드 B가 락을 확보하고자 하는 경우
	- 락은 현재 누군가가 사용하고 있기 때문에 스레드 B는 일단 대기 상태에 들어감
	- 스레드 A가 락을 해제하면 스레드 B가 대기 상태가 풀리면서 다시 락을 확보하고자 요청함
	- 동시에 스레드 C가 끼어들면서 동일한 락을 확보하고자 한다면 스레드 B대신 스레드 C가 락을 미리 확보해버릴 확률이 꽤 높고, 스레드 B가 본격적으로 실행되기 전에 스레드 C는 이미 실행을 마치고 락을 다시 해제시키는 경우도 가능함
	- 이런 경우라면 모든 스레드가 원하는 성능을 충분히 발휘하면서 실행된 셈
	- 스레드 B는 사용할 수 있는 시점에 락을 확보할 수 있고, 스레드 C는 이보다 먼저 락을 사용할 수 있으니 처리량이 크게 늘어남
- 공정한 방법으로 락을 관리할 때는 락을 확보하고 사용하는 시간이 상대적으로 길거나 락 요청이 발생하는 시간 간격이 긴 경우에 유리
- 락 사용시간이 길거나 요청 간의 시간 간격이 길면 순서 뛰어넘기 방법으로 성능상의 이득을 얻을 수 있는 상태, 즉 락이 해제돼 있는 상태에서 다른 스레드가 해당 락을 확보하고자 대기 상태에서 깨어나고 있는 상태가 상대적으로 훨씬 덜 발생하기 때문
- 기본 ReentrantLock과 같이 암묵적인 락 역시 공정성에 대해 아무런 보장을 하지 않음
	- 통계적으로 공정하다는 사실을 놓고 보면 대부분의 락 구현 방법을 거의 모든 상황에 무리 없이 적용할 수 있음

</br>

## 13.4 synchronized 또는 ReetrantLock 선택
- ReetrantLock은 락을 확보할 때 타임아웃을 지정하거나 대기 상태에서 인터럽트에 잘 반응하고 공정성 여부를 지정할 수 있음
	- 블록의 구조를 갖추고 있지 않은 경우에도 락을 적용할 수 있는 유연함을 갖고 있음
- 암묵적인 락은 명시적인 락에 비해 상당한 장점이 있음
	- 코드에 나타나는 표현 방법도 훨씬 익숙하고 간결
	- 암묵적인 락과 명시적인 락을 섞어 쓴다고 하면 코드를 읽을 때 혼동될 뿐만 아니라 오류가 발생할 가능성도 높음
- ReetrantLock은 암묵적인 락에 비해 더 위험할 수도 있음
	- finally 블록에 unlock 메소드를 넣지 않는다면.. 시한폭탄을 심어두는 셈
- ReetrantLock은 synchronized 블록에서 제공하지 않는 특별한 기능이 꼭 필요할 때만 사용하는게 안전함
- ReetrantLock은 암묵적인 락만으로는 해결할 수 없는 복잡한 상황에서 사용하기 위한 고급 동기화 기능
- 고급 동기화 기법을 사용해야 하는 경우에만 ReetrantLock을 사용하도록 함
	- 락을 확보할 때 타임아웃을 지정해야 하는 경우
	- 폴링의 형태로 락을 확보하고자 하는 경우
	- 락을 확보하느라 대기 상태에 들어가 있을 때 인터럽트를 걸 수 있어야 하는 경우
	- 대기 상태 큐 처리 방법을 공정하게 해야 하는 경우
	- 코드가 단일 블록의 형태를 넘어서는 경우
- 암묵적인 락을 사용할 때는 항상 특정 스택 프레임에 락이 연관돼 있었지만, ReetrantLock은 블록을 벗어나는 범위에도 사용할 수 있기 때문에 특정 스택 프레임에 연결되지 않음
- 단순히 성능이 나아지기를 기대하면서 synchronized 대신 ReetrantLock을 사용하는 일은 그다지 좋은 선택이 아님

</br>

## 13.5 읽기-쓰기 락
- ReentrantLock은 표준적인 상호 배제(mutual exclusion) 락을 구현하고 있음
	- 한 시점에 단 하나의 스레드만이 락을 확보할 수 있음
- 상호 배제 규칙은 일반적으로 데이터의 완전성을 보장하는데 굉장히 엄격한 특징을 갖고 있음
	- 필요 이상으로 병렬프로그램의 장점을 제한함
- 상호 배제 규칙은 쓰기 연산과 쓰기 연산이 동시에 일어나거나 쓰기와 읽기 연산이 동시에 일어나는 경우를 제한할 뿐만 아니라 읽기와 읽기 연산이 동시에 일어나는 경우도 제한함
- 대부분의 경우 사용하는 데이터 구조는 읽기 작업이 많이 일어남
- 읽기 작업은 여러 개를 한꺼번에 처리할 수 있지만, 쓰기 작업은 혼자만 동작할 수 있는 구조의 동기화를 처리해주는 락이 바로 읽기-쓰기 락(read-write lock)
~~~java
public interface ReadWriteLock {
    Lock readLock();
    Lock writeLock();
}
~~~
- ReadWriteLock으로 동기화된 데이터를 읽어가려면 읽기 락을 확보해야 하고, 해당 데이터를 변경하고자 한다면 쓰기 락을 확보해야 함
- ReadWriteLock에서 구현하고 있는 동기화 정책은 여러 개의 읽기 작업은 동시에 처리할 수 있지만, 쓰기 작업은 한 번에 단 하나만 동작 할 수 있음
- 성능, 스케줄링 특성, 락 확보 방법의 특성, 공정성 문제, 기타 다른 락 관련 의미가 서로 다르게 반영되도록 새로운 클래스를 구현할 수 있게 되어 있음
- 특정 상황에서 병렬 프로그램의 성능을 크게 높일 수 있도록 최적화된 형태로 설계된 락
- 구현상의 복잡도가 약간 높기 때문에 최적화된 상황이 아닌 곳에 적용하면 상호 배제시키는 일반적인 락에 비해 성능이 약간 떨어짐
- 읽기 락과 쓰기 락 간의 상호작용을 잘 활용하면 여러가지 특성을 갖는 다양한 ReadWriteLock을 구현할 수 있음
- ReadWriteLock을 구현할 때 적용할 수 있는 특성에는 아래와 같은 것이 있음
	- **락 해제 방법**
		- 쓰기 작업에서 락을 해제했을 때 대기 큐에 읽기 작업뿐만 아니라 쓰기 작업도 대기중이었다고 하면 누구에게 락을 먼저 넘겨줄것인가의 문제, 읽기 작업을 먼저할 것인지, 쓰기 작업을 먼저할 것인지, 아니면 큐에 먼저 대기하고 있던 작업을 먼저 처리하도록 해도 좋음
	- **읽기 순서 뛰어넘기**
		- 읽기 작업에서 락을 사용하고 있고 대기 큐에 쓰기 작업이 대기하고 있다면 읽기 작업이 추가로 실행됐을 때 읽기 작업을 그냥 실행할 것인지? 아니면 대기 큐의 쓰기 작업 뒤에서 대기할지 정할 수 있음
	- **재진입 특성**
		- 읽기 쓰기 작업 모두 재진입이 가능한지?
	- **다운그레이드**
		- 특정 스레드에서 쓰기 락을 확보하고 있을 때, 쓰기 락을 해제하지 않고도 읽기 락을 확보할 수 있는지? 
		가능하다면 쓰기 작업을 하던 스레드가 읽기 락을 직접 확보하고 읽기 작업을 할 수 있음.
		즉 읽기 락을 확보하려는 사이에 다른 쓰기 작업이 실행되지 못하게 할 수 있음
	- **업그레이드**
		- 읽기 락을 확보하고 있는 상태에서 쓰기 락을 확보하고자 할 때 대기 큐에 들어 있는 다른 스레드보다 먼저 쓰기 락을 확보하게 할 것인지? 직접적인 업그레이드 연산을 제공하지 않는 한 자동으로 업그레이드가 일어나면 데드락의 위험이 높기 때문에 ReadWriteLock 을 구현하는 대부분의 경우에 업그레이드를 지원하지 않음
- ReentrantReadWriteLock 클래스를 사용하면 읽기 락과 쓰기 락 모두에게 재진입 락 기능을 제공함
- 공정하게 락을 설정하는 경우에는 대기 큐에서 대기한 시간이 가장 긴 스레드에게 우선권이 돌아감
	- 즉 읽기 락을 확보하고 있는 상태에서 다른 스레드가 쓰기 락을 요청하는 경우, 쓰기 락을 요청한 스레드가 쓰기 락을 확보하고 해제하기 전에는 다른 스레드에서 읽기 락을 가져가지 못함
- 쓰기 락을 확보한 상태에서 읽기 락을 사용하는 다운그레이드는 허용되며, 읽기 락을 확보한 상태에서 쓰기 락을 사용하는 업그레이드는 제한됨
- Reentrantlock과 동일하게 ReentrantReadWriteLock 역시 쓰기 락을 확보한 스레드가 명확하게 존재하며, 쓰기 락을 확보한 스레드만이 쓰기 락을 해제 할 수 있음
- 읽기-쓰기 락은 락을 확보하는 시간이 약간은 길면서 쓰기 락을 요청하는 경우가 적을 때에 병렬성을 크게 높여줌
~~~java
public class ReadWriteMap<K, V> {
    private final Map<K,V> map;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock r = lock.readLock();
    private final Lock w = lock.writeLock();
    
    public ReadWriteMap(Map<K, V> map) {
        this.map = map;
    }
    
    public V put(K key, V value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }
    // remove(), putAll(), clear() 메소드도 put()과 동일하게 구현
    
    public V get(Object key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }
    // 다른 읽기 메소드도 get()과 동일하게 구현
}
~~~
- ReadWriteMap 클래스는 ReentrantReadWriteLock을 사용해 Map에 대한 접근을 제한함
- ReadWriteLock을 사용하기 때문에 Map의 값을 읽어가는 작업은 얼마든지 한꺼번에 실행될 수 있지만 실제로 병렬로 사용할 수 있는 해시 기반의 Map클래스가 필요했던 것이라면 ReadWriteMap을 사용하는 것보다 ConcurrentHashMap만 사용해도 충분히 괜찮은 성능을 낼 수 있음
- 여러 스레드에서 동시에 사용해야 하지만 ConcurrentHashMap과 약간 다른 LinkedHashMap같은 기능이 필요하다면 ReadWriteLock을 사용해 필요한 만큼의 성능을 뽑아낼 수 있음
- ArrayList를 ReentrantLock으로 동기화시킨 클래스와 ReadWriteLock으로 동기화시킨 클래스의 실행 속도를 비교하고 있음

</br>

## 요약
- 명시적으로 Lock 클래스를 사용해 스레드를 동기화하면 암묵적인 락보다 더 많은 기능을 활용할 수 있음
	- 예를 들어 락을 확보할 수 없는 상황에 유연하게 대처하는 방법이나 대기 큐에서 기다리는 방법과 규칙도 원하는대로 정할 수 있음
- ReentrantLock에서만 제공되고 synchronized 구문은 제공하지 않는 동기화 관련 기능이 꼭 필요한 경우에만 ReentrantLock을 사용하도록 권장
- 읽기-쓰기 락을 사용하면 읽기 작업만 처리하는 다수의 스레드는 동기화된 값을 얼마든지 동시에 읽어갈 수 있음
- 읽기 작업이 대부분인 데이터 구조에 읽기-쓰기 락을 사용하면 확장성을 높여주는 훌륭한 도구가 됨

<br>

## Reference
- 정리 내용은 모두 해당 깃허브 링크를 참조하였음
    - https://github.com/garlickim/study-note/blob/main/book/java-concurrency-in-practice/%5Bweek12%5D%20%EB%B3%91%EB%A0%AC%20%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%A8%20%ED%85%8C%EC%8A%A4%ED%8A%B8.md
    - https://github.com/garlickim/study-note/blob/main/book/java-concurrency-in-practice/%5Bweek13%5D%20%EB%AA%85%EC%8B%9C%EC%A0%81%EC%9D%B8%20%EB%9D%BD.md