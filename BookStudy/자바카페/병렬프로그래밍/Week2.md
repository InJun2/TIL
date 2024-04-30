# 2 Week (4 ~ 5 챕터)

## 4. 객체 구성
### 1. 스레드 안전한 클래스 설계
- public static 변수에 저장해도 스레드 동기화 프로그램을 작성할 수 있으나 구조적인 캡슐화 없이 만들어낸 결과물은 여러 스레드에서 사용해도 안전한지 확인하기 어렵고 이후 변경에 있어 동기화 문제를 지속적으로 신경써야함
- 객체가 가지고 있는 여러 가지 정보를 해당 객체 내부에 숨겨두면 전체 프로그램을 뒤져볼필요 없이 객체 단위로 스레드 안전성이 확보되어 있는지 확인할 수 있음

```
스레드 안전한 클래스를 설계할 때 3가지 고려요소
- 객체의 상태를 보관하는 변수가 어떤 것인가?
- 객체의 상태를 보관하는 변수가 가질 수 있는 값이 어떤 종류, 어떤 범위에 해당하는가?
- 객체 내부의 값을 동시에 사용하고자 할 때, 그 과정을 관리할 수 있는 정책
```

<br>

- 객체의 상태는 항상 객체 내부의 변수를 기반으로 함
- 객체 내부의 다른 객체를 가르키는 객체가 있을 경우 해당 객체도 상태 범위에 포함시켜야 함
- 객체 내부의 여러 변수가 갖고 있는 현재 상태를 사용하고자 할 때 값이 계속해서 변하는 상황에서도 값을 안전하게 사용할 수 있도록 조절하는 방법을 동기화 정책이라고 함
    - 동기화 정책에는 객체의 불변성, 스레드 한정, 락 등을 어떻게 적절하게 활용해 스레드 안전성을 확보하며 어떻게 활용하는지 명시
    - 클래스를 유지보수하기 좋게 관리하려면 해당 객체에 대한 동기화 정책을 항상 문서로 작성해두어야 함

- [자바 동기화 synchronzied 정리](https://github.com/InJun2/TIL/blob/main/Stack/Java/Synchronized.md)

<br>

#### 동기화 요구사항 정리
- 객체와 변수를 놓고 봤을 때 객체와 변수가 가질 수 있는 가능한 값의 범위를 생각할 수 있는데 이런 값의 범위를 상태 범위(state space)라고 함
- 클래스 내부의 상태나 상태 변화에 있어 여러가지 제약 조건이 있을 수 있는데 이런 제약 조건에 따라 또 다른 동기화 기법이나 캡슐화 방법을 사용해야 할 수도 있음
- 특정한 연산을 실행했을 때 올바르지 않은 상태 값을 가질 가능성이 있다면 해당 연산은 단일 연산으로 구현해야함
    - 서로 연관된 값은 단일 연산으로 한번에 읽거나 변경해야 함
    - 만약 두개의 변수가 있고 하나의 값을 변경하고 락을 해제하고 락을 확보하고 다른 값을 변경한다면 올바르지 않은 상태가 될 수 있음
- 상태 범위에 두 개 이상의 변수가 연결되어 동시에 관여하고 있다면 이런 변수를 사용하는 모든 부분에서 락을 사용해 동기화를 맞춰야 함
- 객체가 가질 수 있는 값의 범위와 변동 폭을 정확하게 인식하고 스레드 안전성을 완벽하게 확보해야함

<br>

#### 상태 의존 연산
- 상태를 기반으로 선행조건(precondition)을 갖기도 하는데 예를 들면 비어있는 큐에서는 값을 뽑아낼 수 없음
- 이처럼 현재 조건에 따라 동작 여부가 결정되는 연산을 상태 의존(state-dependent) 연산이라고 함

<br>

#### 상태 소유권
- 객체의 상태를 정의하고자 할 때, 객체가 실제로 소유하는 데이터만 기준으로 잡아야함
- 소유권이라는 개념은 자바 언어 자체에 내장되어 있지는 않지만 클래스를 설계할 때 충분히 고려할 수 있는 요소
- 대부분의 경우 소유권과 캡슐화 정책은 함께 고려하는 경우가 많음. 캡슐화 정책은 내부에 객체와 함께 상태 정보를 숨기기 때문에 객체의 상태에 대한 소유권이 있음
- 특정 변수에 대한 소유권을 가지고 있기 때문에 락 구조가 어떻게 동작하는지도 소유권을 가짐
- 컬렉션 클래스에서는 소유권 분리의 형태를 사용하는 경우도 많음
    - 소유권 분리는 컬렉션 클래스를 놓고 볼때 컬렉션 내부의 구조에 대한 소유권은 컬렉션 클래스가 갖고, 컬렉션에 추가되어 있는 객체에 대한 소유권은 컬렉션을 호출해 사용하는 클라이언트 프로그램이 갖는 구조
    - 서블릿 프레임워크에서 볼 수 있는 ServletContext 클래스가 대표적인 예로 ServletContext는 Map과 비슷한 구조로 만들어져 있고 객체를 불러다 쓰는 프로그램은 setAttribute 메서드와 getAttribute 메서드를 통하여 추가되어 있는 객체를 사용

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ServletContext 객체 가져오기
        ServletContext servletContext = getServletContext();

        // ServletContext에서 객체 가져오기
        String message = (String) servletContext.getAttribute("message");

        // 클라이언트에게 메시지 보내기
        response.setContentType("text/html");
        response.getWriter().println("<h1>" + message + "</h1>");

        // 객체 자체의 소유권은 ServletContext가 가지고 있지만 해당 객체 내부의 message에 있어 소유권은 메시지를 조작할 수 있는 클라이언트가 가지고 있음
    }
}
```

<br>

### 2. 인스턴스 한정
- 객체를 적절하게 캡슐화하는 것으로도 스레드 안전성을 확보할 수 있는데 '인스턴스 한정' 기법을 사용하는 셈
- 특정 객체가 다른 객체 내부에 완벽하게 숨겨져 있다면 훨씬 간편하게 스레드 안전성을 분석해볼 수 있음
    - 데이터 객체 내부에 숨겨두면 해당 내용은 해당 객체의 메서드에서만 사용할 수 있어 항상 지정된 형태의 락이 적용되는지 쉽게 확인할 수 있음
- 인스턴스 한정 기법을 사용하면 클래스 내부의 여러가지 데이터를 여러 개의 락을 사용해 따로 동기화 시킬 수 있음
- 자바 플랫폼 라이브러리에는 이런 클래스를 멀티스레드 환경에서 안전하게 사용할 수 있도록 Collections.synchronizedList 같은 팩토리 메소드가 만들어져 있음
    - 대부분 데코레이터 패턴을 활용하여 래퍼 클래스는 호출하는 연동 역할만 하면서 동시에 모든 메서드가 동기화 되어 있음
- 한정되어야 할 객체를 공개하면 객체가 특정 코드 범위에 한정됬어야 하는데 해당 코드 범위를 넘어 유출된 경우는 버그이며 반복 객체나 내부 클래스 인스턴스를 사용하면서 공개한다면 한정됬어야 할 객체를 간접적으로 외부에 유출시킬 가능성이 있음 

<br>

```java
@ThreadSafe
public class PersonSet {
    @GuardedBy("this")
    private final Set<Person> mySet = new HashSet<Person>();

    public synchronzied void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronzied boolean containsPerson(Person p) {
        return mySet.contains(p);
    }
    
    // Person 클래스는 HashSet 클래스에 보관되어 있고 HashSet 자체는 스레드 안전한 객체가 아님
    // 하지만 private로 지정되어 있어 직접적으로 외부에 유출되지 않아 해당 변수를 사용하는 방법은 addPerson과 containsPerson 뿐임
    // 해당 메서드들에 동기화를 적용하여 스레드 안전성을 확보했음
}

```

<br>

#### 자바 모니터 패턴
- 인스턴스 한정 기법에 대한 원리와 내용은 결국 자바 모니터 패턴에 해당함
- 변경 가능한 데이터를 모두 객체 내부에 숨긴 다음 객체의 암묵적 락으로 데이터에 대한 동시 접근을 막는 것을 모니터 패턴이라고 함
- 만약 락이 외부에 공개되어 있다면 다른 객체도 해당하는 락을 활용해 동기화 작업에 함께 참여할 수 있음. 올바르지 않게 참여하면 큰 문제가 됨
- 외부에서 변경 가능한 데이터를 요청할 경우 그에 대한 복사본을 넘겨주는 방법을 사용하면 스레드 안정성을 부분적이나마 확보할 수 있음
    - 만약 차량 위치 정보를 가져오는 get메서드와 차량 위치 정보를 업데잍하는 set메서드를 구현했을 경우 외부에 차량 위치 정보를 복사본으로 주면 값이 변경이 가능하지만 실제 차량 위치가 변경되었을 때 외부에서 가져간 위치 정보는 바뀌지 않음
    - 위의 경우처럼 시시각각으로 상태를 확인한다면 알고싶은 시점마다 매번 복사본을 만들어야 하므로 성능에 문제가 있을 수 있음

<br>

### 3. 스레드 안전성 위임
- 클래스를 구현할 때 새로 만들거나 이미 만들어져있지만 스레드 안전성이 없는 객체를 조합해 만들 경우 스레드 안전성을 확보하고자 한다면 자바모니터 패턴을 유용하게 사용할 수 있음
- 조합하고자 하는 클래스가 이미 스레드 안전성을 확보하고 있고 해당 클래스로 스레드 안전성을 확보하려면 동기화 과정을 거쳐야할 수도 있고 아닐 수도 있음
- A 클래스는 스레드 안전히며 B 클래스는 A클래스를 사용하여 다른 제한 조건이 없을 경우 B클래스 또한 스레드 안전하며 해당 경우 스레드 안전성 문제를 A 클래스에게 '위임(delegate)'한다고 함

<br>

```java
// 111p 예제
@Immutable
public class Point {    // 불변인 클래스로 외부에 공개할 수 있어 객체 인스턴스를 복사해줄 필요가 없음
    public final int x, y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y; 
    }
}

@ThreadSafe
public class DelegatingVehicleTracker {
    private final ConcurrentMap<String, Point> locations;   // 모든 동기화 작업은 ConcurrentMap에서 담당
    private final Map<String, Point> unmodifiableMap;   // Map에 들어있는 모든 값은 불변

    public DelegatingVehicleTracker(Map<String, Point> points) {
        locations = new ConcurrentHashMap<String, Point>(points);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null) 
            throw new IllegalArgumentException("invalid vehicle name: " + id);
    }
}
// ConcurrentHashMap은 자바에서 제공하는 인터페이스로 내부적으로 동기화 매커니즘을 사용해 스레드 안전함
// Collections.unmodifiableMap를 사용하여 원본 객체를 래핑하여 맵을 변경할 수 없는 불변 객체로 생성
```

<br>

#### 독립 상태 변수
- 스레드 안전한 내부 변수가 두개 이상이라도 해당 변수끼리 독립적이라면 변수가 서로의 상태값에 연관성이 없어 스레드 안전성을 위임할 수 있음
- 내부 변수들이 스레드 안전성을 확보하고 변수끼리 서로 연동하는 상태가 없으므로 아래 클래스는 스레드 안전성이라는 책임을 두 변수에 완전히 위임할 수 있음

```java
// keyListeners와 mouseListeners는 서로 연관이 없음
public class VisualComponent {
    private final List<KeyListener> keyListeners 
        = new CopyOnWriteArrayList<KeyListener>();
    // CopyOnWriteArrayList는 내부 데이터에 대한 변경이 필요할 때마다 본사본을 만들어 변경하는 방식으로 작동하여 스레드 안전한 구현체

    private final List<MouseListener> mouseListeners
        = new CopyOnWriteArrayList<MouseListener>();

    public void addKeyListener(KeyListener listener) { 
        keyListeners.add(listener);
    }
    public void addMouseListener(MouseListener listener) { 
        mouseListeners.add(listener);
    }
    public void removeKeyListener(KeyListener listener) { 
        keyListeners.remove(listener);
    }
    public void removeMouseListener(MouseListener listener) { 
        mouseListeners.remove(listener);
    } 
}
```

<br>

#### 독립 상태 변수 위임할 때의 문제점
- 대부분의 클래스가 위처럼 간단하게 구성되어 있지않으며 거의 모두가 내부의 상태 변수 간의 의존성을 가지고 있음
- 스레드 안전한 두 상태 변수를 쓰더라도 의존성이 있다면 스레드 안전하지 못함
- 또한 두 개 이상의 클래스를 한번에 처리하는 복합 연산 메서드가 없는 상태라면 스레드 안전성을 내부 변수에 모두 위임할 수 있음

```java
// 스레드 세이프하지 않은 예시 코드
public class NumberRange {
    // 의존성 조건 : lower <= upper
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public void setLower(int i) {
        if (i > upper.get())
            throw new IllegalArgumentException(
                    "can't set lower to " + i + " > upper");
        lower.set(i); 
    }

    public void setUpper(int i) {
        if (i < lower.get())
            throw new IllegalArgumentException(
                    "can't set upper to " + i + " < lower");
        upper.set(i); 
    }

    public boolean isInRange(int i) {
        return (i >= lower.get() && i <= upper.get());
    } 
}

// volatile 변수에 대한 스레드 안전성 규칙과 굉장히 유사
// atomicInteger로 변수 두개다 스레드 안전하지만 만약 (0,10)인 상태에서 thread A가 setLower(5)를 호출하고, thread B가 setUpper를 4로 호출한다면, lower=5, upper=4가 될 수 있음
// 스레드 간의 경쟁 조건이 발생하므로 이 두 연산을 단일 연산으로 처리해야 함
```

<br>

#### 스레드 위임한 내부 상태 변수 외부 공개
- 내부 상태 변수로부터 스레드 안전성을 위임받은 클래스의 내부 상태 변수값을 외부에 공개하고자 한다면 해결책은 상태 변수가 어떤 의존성을 가지고 있는지에 따라 다름
- 상태 변수가 스레드 안전하고, 내부에서 상태 변수 값에 의존성이 없으며, 상태 변수에 대한 어떤 연산을 하더라도 잘못된 상태에 이를 가능성이 없다면 외부에 공개해도 안전

<br>

```java
@ThreadSafe
public class SafePoint {
    @GuardedBy("this") private int x, y;

    private SafePoint(int[] a) { this(a[0], a[1]); }
    
    public SafePoint(SafePoint p) { this(p.get()); }
    
    public SafePoint(int x, int y) {
        this.x = x;
        this.y = y; 
    }

    public synchronized int[] get() {
        return new int[] { x, y };
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y; 
    }
}

// getX와 getY가 따로 구성되어 있다면 x를 가져오고나서 y값을 가져오기 전에 값이 바뀌어 스테일 데이터를 읽을 수도 있음
// set, get은 동기화되어 있어 스레드 안전함
```

<br>

```java

@ThreadSafe
public class PublishingVehicleTracker {
    private final Map<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

    public PublishingVehicleTracker(Map<String, SafePoint> locations) {
        this.locations
            = new ConcurrentHashMap<String, SafePoint>(locations);
        this.unmodifiableMap
            = Collections.unmodifiableMap(this.locations);
    }

    public Map<String, SafePoint> getLocations() {
        return unmodifiableMap;
    }

    public SafePoint getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (!locations.containsKey(id))
            throw new IllegalArgumentException(
                  "invalid vehicle name: " + id);
        locations.get(id).set(x, y);
    }
}
// 이전 예시에서 값을 변경 못했던 Point대신 SafePoint 사용
// 외부에서 호출하는 프로그램은 차량을 추가하거나 삭제할 수 없으나 Map 클래스를 가져가서 내부의 SafePoint 값을 수정하여 차량의 위치를 변경할 수 있음
// 하지만 이와같이 Map이 동적으로 연동된다는 특성은 이후 제약 사항을 추가할 때 스레드 안전성을 해칠 수 있음
```

<br>

### 4. 스레드 안전하게 구현된 클래스에 기능 추가
- 자바 기본 클래스 라이브러리에는 여러가지 스레드 안전한 유용한 기반 클래스가 많음
- 만들어져있는 클래스를 사용하는 것은 재사용하면 개발에 필요한 시간과 자원을 절약하고, 오류 발생 가능성 감소, 유지보수 비용 절감 등 으로 대부분 적절한 방법이 됨
- 기존 라이브러리를 사용하여 기능 추가 방법 2가지
    1. 기존 클래스에 직접 단일 연산 메서드를 추가
    2. 기존 클래스에 상속받아서 함수를 추가

```
예시) 스레드 안전한 List 클래스에서 특정 항목이 목록에 없다면 추가하는 기능을 단일 연산으로 구현해야 한다고 생각해본다면

- 동기화된 List 인스턴스는 대부분의 기능을 갖고 있으나 특정 항목이 들어있는지 확인하는 contains 메소드와 항목을 추가하는 add 메소드가 따로 분리되어 있음
- 하지만 단일 연산으로는 처리할 수 없기 때문에 기능을 따로 추가해야 함
- 추가하고자 하는 항목이 목록에 있는지 확인하고 목록에 이미 들어 있다고 확인되면 추가하지 않으면 됨
- 클래스가 스레드 안전성을 확보해야 한다는 요구사항이 있으므로 바로 목록에 들어있지 않은 경우에만 추가하는 단일 연산 조건이 있음
- 동시에 같은 객체를 추가하려고 할때 두번 추가될 가능성이 있음
- 그러므로 단일 연산 하나를 기존 클래스에 추가하고자 한다면 해당하는 단일 연산 메소드를 기존 클래스에 직접 추가하는 방법이 가장 안전
- 기능을 추가하는 또 다른 방법은 기존 클래스를 상속 받는 방법인데 이 방법은 기존 클래스를 외부에서 상속받아 사용할 수 있도록 설계했을 때나 사용할 수 있음
- 또한 클래스 상속 방법은 동기화를 맞춰야하는 대상이 두 개 이상의 클래스에 걸쳐 분산되어 문제가 생길 위험이 높음
- 상위 클래스가 내부적으로 동기화 기법을 약간 수정하면 하위 클래스는 본의 아니게 동기화가 깨질 수 있음
```

<br>

#### 클라이언트 사이드 동기화
- synchronizedList 메서드를 사용해 동기화한 ArrayList에는 위의 두가지 기능 추가 방법을 사용하지 못함
    - 동기화된 ArrayList를 받아간 외부 프로그램은 List 객체가 동기화 되었는지 알 수 없기 때문
- 이를 위한 클래스를 상속받지 않고 원하는 기능을 추가할 수 있는 세번째 방법은 도우미 클래스를 따로 구현해서 추가 기능을 구현하는 방법
- 클라이언트 사이드는 라이브러리를 가져다 쓰는 코드로 이해하면 되고 라이브러리를 사용하여(client-side) 도우미 클래스를 구현

<br>

```java
// 스레드 안전하지 않은 예시 코드
@NotThreadSafe
public class ListHelper<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());
    ...
    public synchronized boolean putIfAbsent(E x) { 
        boolean absent = !list.contains(x);
        if (absent)
            list.add(x); 
        return absent;
    }
}

// helper class를 만들었을 때, Collections.synchronizedList()는 list 변수에 락이 걸려있기 때문에 putIfAbsent() 함수는 제대로 동작하지 않음
// helper class를 만들 때 올바로 구현하려면 어디에 락이 걸려있는지 알고, 같은 락을 꼭 사용해줘야 함
/*
    지금 해당 코드에서 다른 스레드가 List의 다른 메서드를 통해 값을 변경할 수 있다는 건지, 또한 해당 메서드는 왜 잘못건 락인지 잘 모르겠음
    개인적으로 이해한 내용은 메서드 단에서의 synchronized는 해당 메서드를 포함한 class에 락을 거는 것이고
    아래의 메서드 내부에 블록으로 synchronized 동기화를 하면 해당 객체에 대하 락을 거는 것이기 때문에 락을 잘못 걸었다는 것인지??
    synchronizedList의 내부 코드
*/
```

<br>

```java
@ThreadSafe
public class ListHelper<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());
    ...
    public boolean putIfAbsent(E x) {
        synchronized (list)  {
            boolean absent = !list.contains(x); 
            if (absent) 
                list.add(x); 
            return absent;
        }
    }
}

// 위의 방법을 해결한 코드로 클라이언트 측 락이나 외부 락을 사용하여 List가 사용하는 것과 동일한 락을 사용해야 함
// 같은 수준의 락을 사용해야 한다 -> 동기화 하고자 하는 객체에 대해서 락을 사용해야한다??
```

<br>

#### 클래스 재구성
- 기존 클래스에 새로운 단일 연산을 추가하고자할 때 더 안전하게 사용할 수 있는 방법은 클래스 재구성(composition)
- 상위 클래스를 상속받아 기능을 불러와 사용하고 클래스를 자체적인 락으로 사용하여 기능을 추가하므로 내부 List 클래스가 스레드 안전한지 신경 쓸 필요가 없고 내부적으로 동기화 정책을 뒤바꾼다해도 상관 없음
- 동기화 기법을 한번 더 사용하게 되어 성능으로는 더 부정적일 수 있지만 클라이언트 측 락보다 훨씬 안전
- 해당 클래스가 외부로 공개되지 않는 한 스레드 안전성을 확보할 수 있음

<br>

```java
@ThreadSafe
public class ImprovedList<T> implements List<T> {
    private final List<T> list;
    public ImprovedList(List<T> list) { this.list = list; }

    public synchronized boolean putIfAbsent(T x) { 
        boolean contains = list.contains(x);
        if (contains)
            list.add(x); 
        return !contains;
    }
    public synchronized void clear() { 
        list.clear(); 
    }
}

// ImprovedList 클래스는 그 자체를 락으로 사용하여 그 안에 포함되어 있는 List와는 다른 수준에서 락을 활용하고 있음
// 해당 클래스를 락으로 사용해 동기화하기 때문에 내부의 List 클래스가 스레드 안전한지 아닌지는 중요하지 않고 신경쓸 필요가 없음
```

<br>

### 5. 동기화 정책 문서화
- 동기화 정책에 대한 내용을 문서화 하는 일은 가장 강력한 방법 중 하나
- 구현한 클래스가 어느 수준까지 스레드 안전성을 보장하는지에 대해 충분히 문서화를 해두어야 함
- 동기화 기법이나 정책을 잘 정리 해두 면 유지보수 팀이 원활하게 관리할 수 있음
- @GuardedBy 등의 어노테이션을 통해 동기화를 맞출 때 사용하는 아주 작은 기법이라도 적어 두는 것이 좋음
- 대부분의 클래스는 참조할 만한 정보를 하나도 제공하지 않아 스레드 안전성을 어디까지 보장하고 있는지, 어떤 요구사항을 갖춰야하는지 언급되지 않음
- 해당 책에서는 java.text.SimpleDateFormat 클래스가 스레드 동기화 되어있지 않은 예시로 들었음
- 사용하는 클래스의 표준을 확인해보고 표준에 정보가 제대로 없다면 기능에 대해 설계자 입장에서 생각하고 '논리적인 추측'을 진행하는 것이 좋음
- 동기화 정책을 모르지만 여러 스레드에서 동시에 일어난다면 사용하는 객체에 대해 스레드 안전성을 확보해두는 것이 좋음

<br>

## 5. 구성 단위
- 4장에서는 스레드 안전한 클래스를 사용해 스레드 안전성 위임 기법이 상당히 효과적이고 쓸 만한 방법
- 5장에서는 자바 패키지에 라이브러리는 병렬 프로그램을 작성할 때 필요한 여러 가지 동기화 도구가 마련되어 있음

<br>

### 1. 동기화된 컬렉션 클래스
- 동기화되어 있는 컬렉션의 대표주자는 Vector와 Hashtable
- JDK 1.2 버전부터는 Collections.synchronizedXXX 클래스는 모두 public 함수 내부에 캡슐화해서 내부 값을 한 스레드만 접근하게 동기화 되어 있음

<br>

#### 동기화된 컬렉션 클래스의 문제점
- 동기화된 컬렉션 클래스는 스레드 안전성을 확보하고 있으나 여러 개의 연산을 묶어 단일 연산처럼 활용해야 할 필요성이 항상 발생
    - 반복(iteration), 이동(navigation) 등의 기능 혹은 '없는 경우만 추가하는' 등의 연산이 대표적
- 동기화된 컬렉션 클래스는 대부분 클라이언트 측 락을 사용할 수 있도록 만들어져있기 때문에 컬렉션 클래스가 사용하는 락을 함께 사용한다면 추가하는 기능을 컬렉션 클래스에 있는 다른 메서드와 같은 수준으로 동기화할 수 있음


<br>

```java
// 스레드 안전하지 않은 코드 예시
public static Object getLast(Vector list) {
    int lastIndex = list.size() - 1;
    return list.get(lastIndex);
}
public static void deleteLast(Vector list) {
    int lastIndex = list.size() - 1;
    list.remove(lastIndex);
}

// Vector 클래스의 메소드만을 사용하여 여러 스레드가 호출해도 데이터는 꺠지지 않지만 문제가 생길 수 있음
// Vector 10개의 값이 있는 상태에서 스레드 B가 getLast를 호출하고 스레드 A는 동시에 deleteLast 메소드를 호출한다면 섞여서 동작할 수 있음
// lastIndex가 달라져서 ArrayIndexOutOfBoundsException이 발생할 수 있음
// Vector 입장에서는 스레드 안전성에 문제는 없지만 getLast 메서드를 호출하는 입장에서 가져가고자 했던 마지막 값이 있음에도 가져가지 못한 올바르지 않은 상황 발생
```

<br>

```java
// 클라이언트 측 락을 통해 동기화한 코드
public static Object getLast(Vector list) {
    synchronized (list) {
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    } 
}
public static void deleteLast(Vector list) {
    synchronized (list) {
        int lastIndex = list.size() - 1;
        list.remove(lastIndex);
    }
}
```

<br>

```java
// 예외 상황을 발생시키지 않게 하기 위해 클라이언트 측 락을 통해 반복문을 동기화 한 경우
synchronized (vector) {
    for (int i = 0; i < vector.size(); i++)
        doSomething(vector.get(i));
}

// 동기화는 이뤘지만 이 코드가 동작하는 동안 vector object 내부 값을 읽고 처리하는 모든 액션을 처리하는 스레드가 대기 상태에 들어가 병렬 프로그래밍의 장점을 잃음
```

<br>

#### Iterator 와 ConcurrentModificationException
- Collection 클래스에 들어있는 값을 차례로 반복시켜 읽어내는 가장 표준적인 방법은 Iterator 지만 반복문이 실행되는 동안 컬렉션 클래스 내부 값 변경 작업을 시도할 때의 문제를 막지 못함
- 즉, 동기화된 클래스에서 만든 Iterator도 다른 스레드가 같은 시점에 컬렉션 내부 값을 변경하는 작업을 처리하지는 못하게 만들어져 있고 대신 즉시 멈춤(fail-fast)의 형태로 반응하게 되어 있음
    - 즉시 멈춤이란 반복문을 실행하는 도중에 클래스 내부의 값이 변경되는 상황이 포착되면 ConcurrentModificationException(동시 발생 예외) 예외를 발생시키고 멈추는 처리 방법
- 컬렉션 클래스는 내부에 값 변경 횟수를 카운팅하는 변수가 있고, 반복문이 실행되는 동안 변경 횟수 값이 바뀌면 hasNext()나 next()에서 ConcurrentModificationException을 발생시킴
- for-each 구문은 compile시 Iterator 를 사용한 구문으로 변경되어 마찬가지로 hasNext나 next 메서드를 매번 호출하는 방식으로 변경됨
    - 따라서 반복문을 사용할 때는 ConcurrentModificationException이 발생하지 않도록 미연에 방지하는 방법은 반복문 전체를 적절한 락으로 동기화 시키는 방법 밖에 없음
- 하지만 반복문 코드 전체를 동기화하는 것은 컬렉션이 많은 내부 값이 있다면 작업 시간이 많이 소모되는 중 내부의 값을 사용하려는 스레드가 오랜 시간을 대기 상태에서 기다려야 하고 데드락이 발생할 수 있음
- 소모상태(starvation)나 데드락의 위험이 있는 상태에서 컬렉션 클래스를 오랜 시간 동안 락으로 막아두면 전체 애플리케이션의 확장성을 해칠 수 있음
- 반복문을 실행하는 동안 컬랙션에 락을 건 효과를 얻으려면, clone 메소드로 복사본을 만들어서 반복문을 사용하는 방법을 이용할 수 있음
- 하지만 clone도 효과적인지는 테스트가 필요함. 엄청나게 많은 양을 복사하는 것도 시간이 오래 걸리거나, 메모리를 많이 사용할 수도 있기 때문

<br>

#### 숨겨진 Iterator
- 락을 걸어 동기화하면 Iterator를 사용할 때 ConcurrentModificationException이 발생하지 않도록 제어할 수 있으나 컬렉션을 공유해 사용하는 모든 부분에서 동기화를 맞춰야 한다는 점을 잊으면 안됨
- 아래 HiddenIterator 클래스처럼 iterator가 내부적으로 없어도 사용하는 경우도 있어 ConcurrentModificationException이 발생할 수 있음
- 여기서 HashSet 보다는 동기화된 클래스를 사용하는 것(ConcurrentHashSet, Collections.synchronizedSet)이 Iterator와 관련한 이런 문제가 일어나지 않을 것

<br>

```java
public class HiddenIterator {
    @GuardedBy("this")
    private final Set<Integer> set = new HashSet<Integer>();

    public synchronized void add(Integer i) {
        set.add(i); 
    } 
    public synchronized void remove(Integer i) { 
        set.remove(i); 
    }
    public void addTenThings() {
        Random r = new Random();
        for (int i = 0; i < 10; i++)
            add(r.nextInt());
        System.out.println("DEBUG: added ten elements to " + set);
        // 문자열 두 개를 +연산으로 연결하는 과정에서 컬렉션 클래스의 toString()을 호출하는데 해당 소스코드는 컬렉션의 iterator를 호출해 문자열을 만듬
        // 이는 add() 자체는 동기화 되었지만, println()을 할 때는 동기화를 하는데 실패한 것
    } 
}
```

> toString() 뿐만 아니라 hashCode(), equals()도 내부적으로 iterator를 사용
> 마찬가지로 containsAll(), removeAll(), retainAll(), 컬랙션 클래스는 파라미터로 받는 생성자들도 모두 iterator를 사용하므로, ConcurrentModificationException이 발생할 수 있음

<br>

### 2. 병렬 컬렉션
- 자바 5.0 부터 컬렉션 동기화 측면에서 많은 발전이 있어 동기화된 컬렉션의 내부 변수에 접근하는 통로를 일련화해서 스레드 안전성을 확보했으나 여러 스레드에서 접근할 때 하나의 스레드만 작업할 수 있어 효율성이 떨어짐
- 하지만 병렬 컬렉션(Concurrent Collections)는 여러 스레드에서 동시에 사용할 수 있도록 설계되어 있음
1. Hash 기능과 병렬성을 확보한 ConcurrentHashMap
2. List 클래스 하위로, 객체 목록을 반복하며 열람하는 연산의 성능을 최우선으로 구현한 CopyOnWriteArrayList
3. ConcurrentMap 인터페이스에는 put-if-absent, replace, conditional-remove 연산이 추가
    - putIfAbsent() : 없는 경우 새로 추가
    - replace() : 대치 연산
    - conditionalRemove() : 조건부 제거
4. Queue, BlockingQueue 두 가지 형태의 컬렉션 인터페이스 추가
    - ConcurrentLinkedQueue : FIFO 큐
    - PriorityQueue : 우선 순위에 따라 쌓여 있는 항목이 추출되는 순서가 바뀜
    - Queue 인터페이스에서 정의 되어 있는 연산은 동기화를 맞추느라 대기 상태에서 기다리는 부분이 없음
    - List를 대체해서도 구현할 수 있지만, List의 특징이자 동시 사용성에 걸림돌이라고 할 만한 기능이 있어 Queue의 관점에서 필요한 기능만을 가지도록 Queue에 모아두고 사용하게됨
    - BlockingQueue : 항목을 추가하거나 뽑아낼 때, 상황에 따라 Thread를 대기할 수 있게 한다 Producer-Consumer 패턴에 굉장히 편리하게 사용할 수 있다고 함
5. 자바 6에는 ConcurrentSkipListMap과 ConcurrentSkipListSet를 통해 각각 SortedMap과 SortedSet 클래스의 병렬성을 높임

<br>

#### ConcurrentHashMap
- 동기화된 컬렉션 클래스는 각 연산을 수행하는 시간동안 항상 락을 확보하고 있어야 하지만 특정 연산은 생각보다 많은 양의 일을 할 수도 있음
- ConcurrentHashMap은 HashMap과 같이 해시를 기반으로 하는 Map이지만 내부적으로 락 스트라이핑(lock striping) 동기화 방법을 사용
- 락 스트라이핑 동기화 방식으로 여러 스레드에서 공유하는 상태에 잘 대응이 가능
    - 값을 읽어 가는 연산은 많은 수의 스레드라도 얼마든지 동시에 처리할 수 있음
    - 읽기/쓰기 연산도 동시에 처리 가능
    - 쓰기 연산은 제한된 개수 만큼 동시에 수행 가능
- ConcurrentHashMap에서 만들어낸 Iterator는 즉시 멈춤 대신 미약한 일관성(weakly consistent) 전략을 사용
    - 반복문과 동시에 컬렉션의 내용을 변경해도 Iterator를 만들었던 시점의 상황대로 반복을 계속할 수 있고 Iterator를 만든 시점 이후에 변경된 내용을 반영해 동작할 수 있음(이부분은 반드시 보장되지는 않음)
- 해당 전략으로 ConcurrentHashMap이 만드는 Iterator는 ConcurrentModificationException을 발생시키지 않음
    - 즉, ConcurrentHashMap의 항목을 대상으로 반복문을 실행하는 경우 따로 락을 걸어 동기화할 필요가 없음
- 대신 병렬성 문제로 size(), isEmpty() 메서드의 의미가 약해지는 단점도 있음
    - 메서드의 결과값이 리턴하는 시점에 실제 객체의 수가 바뀌었을 수 있어 추정 값이 됨
- 또한 Map에서는 지원하지만 ConcurrentHashMap에서는 지원하지 않는 기능도 존재하는데 맵을 독점적으로 사용할 수 있도록 막아버리는 기능
    - HashTable이나 synchronizedMap 메서드를 사용하면 Map에 대한 락을 잡아 다른 스레드에서 사용하지 못하도록 막을 수 있으나 ConcurrentHashMap가 훨씬 많은 이점이 존재
    - 맵을 독점적으로 사용하고자 하는 경우에 ConcurrentHashMap 사용에 충분히 신경을 기울일 것

<br>

#### Map 기반의 또 다른 단일 연산
- ConcurrentHashMap는 클라이언트 측 락 기법을 활용할 수 없음
- ConcurrentHashMap은 ConcurrentMap를 구현하여 putIfAbsent(), removeIfEqual(), replaceIfEqual() 같이 자주 사용하는 몇가지 연산이 이미 구현되어 있음
- 해당 연산을 사용하지 않고 새로운 단일 연산을 만든다면 ConcurrentMap을 사용하는 것이 좋을 것

<br>

```java
public interface ConcurrentMap<K,V> extends Map<K,V> {
    // key라는 키가 없는 경우에만 value 추가
    V putIfAbsent(K key, V value);

    // key라는 키가 value 값을 갖고 있는 경우 제거
    boolean remove(K key, V value);

    // key라는 키가 oldValue 값을 갖고 있는 경우 newValue로 치환
    boolean replace(K key, V oldValue, V newValue);

    // key라는 키가 들어 있는 경우에만 newValue로 치환
    V replace(K key, V newValue);
}
```

<br>

#### CopyOnWriteArrayList
- synchronizedList 보다 병렬성을 높인 자료구조
- Iterator로 값을 사용할 때 List 전체에 락을 걸거나 List를 복제할 필요가 없음
- '변경할 때마다 복사'하는 컬렉션 클래스는 불변 객체 리스트를 외부에 공개하여 여러 스레드가 동시 작업하더라도 동기화 작업이 필요 없다는 개념을 바탕으로 스레드 안전성 확보
- 만약 CopyOnWriteArrayList에서 Iterator를 뽑아내 사용한다면 해당 과정을 통해 동작하여 동시 사용 문제가 없음
    1. iterator를 뽑아내는 시점의 컬렉션 데이터 기준으로 반복
    2. 반복하는 동안 컬렉션에 추가되거나 삭제되는 내용은 반복문과 상관 없는 복사본을 대상으로 반영
- CopyOnWriteArrayList는 add, remove 등 데이터가 변경될 때 락을 이용하여 새로운 데이터셋을 만든 후 데이터를 조작하고 setArray()로 원본 데이터를 교체하므로 Iterator로 읽을 때는 따로 락을 사용하지 않는 것
- 변경할 때마다 복사하여 성능의 측면에서 손해를 볼 수 있고 많은 양의 컬렉션이면 큰 손실이 있어 변경할 때 마다 복사하는 컬렉션은 변경 작업보다 읽어내는 일이 훨씬 빈번한 경우 효과적
    - 이런 조건은 이벤트 처리 시스템에서 이벤트 리스너를 관리하는 부분에서 유용하게 사용 가능

<br>

### 3. 블로킹 큐와 프로듀서-컨슈머 패턴
- 블로킹 큐(blocking queue)는 put(), take() 라는 핵심 메서드가 있고 offer(), pool() 메서드도 존재
    - put()은 큐에 요소를 추가할 때 사용. 큐가 가득차 있다면, put()은 공간이 생길 때까지 대기
    - take()는 큐에서 요소를 가져올 때 사용. 큐가 비어있다면, take()는 값이 들어올 때까지 대기
    - 만약 queue 크기가 제한이 없다면, put()이 대기하는 일은 없음
- 블로킹 큐는 프로듀서-컨슈머 패턴(Producer-Consumer Pattern)을 구현하기 좋음
- 프로듀서-컨슈머 패턴이란
    - 해야할 작업을 만들어내는 주체(Producer)와 작업을 처리하는 주체(Consumer)를 분리시키는 설계 방법
    - 이렇게 명확하게 나누면, 개발과정을 명확하고 단순화 시킬 수 있고 작업을 생성하는 부분과 처리하는 부분을 각각 부하 조절을 할 수 있다는 장점이 있음
    - 프로듀서는 작업을 새로 만들어 큐에 쌓아두고 컨슈머는 큐에 쌓여 있는 작업을 사용하는데 큐와 함께 스레드풀을 사용할 때 주로 Producer-Consumer 패턴을 사용
    - 프로듀서는 어떤 컨슈머가 몇 개나 동작하는지, 컨슈머는 프로듀서에 대해 알고있어야 할 필요가 없음
- 블로킹 큐에는 offer 메서드도 존재
    - offer() 메서드는 큐에 값을 넣을 때 대기하지 않고 바로 공간이 모자라 추가할 수 없다는 오류 출력
    - 해당 메서드를 잘 활용하면 프로듀서가 작업을 많이 만들어 과부하에 이르는 상태를 더 효과적으로 처리 가능
    - 예를 들어 부하 분배, 작업할 내용 직렬화하여 디스크 임시저장, 프로듀서 스레드 수 동적으로 감소 등
- 대다수의 경우 블로킹 큐만 사용해도 원하는 기능을 쉽게 구현할 수 있으나 프로그램이 블로킹 큐를 쉽게 적용할 수 없는 모양이라면 세마포어를 사용해 사용하기 적합한 데이터 구조를 만들어야 함
- 자바 클래스 라이브러리에는 블로킹 큐 인터페이스를 구현한 클래스가 존재
    - LinkedBlockingQueue, ArrayBlockingQueue : FIFO 구조로 병렬성 성능 향상
    - PriorityBlockingQueue : 우선순위 기준으로 동작. Comparator를 사용해 정렬 가능
    - SynchronousQueue : 큐에 항목이 쌓이지 않으며 큐 내부에 데이터를 저장하는 공간도 없으나 값을 추가하려는 스레드와 값을 읽어가려는 스레드를 관리. 컨슈머는 프로듀서를 기다릴 수 밖에 없으나 데이터 전달이 굉장히 빠르고 컨슈머에게 직접 데이터를 전달하므로 누구에게 전달했는지, 그 데이터가 처리되었는지 등등의 데이터도 알 수 있음

<br>

#### 데스크탑 검색 예제
- 데스크탑 검색 프로그램은 로컬 하드 디스크에 들어 있는 문서를 전부 읽어들이면서 나중에 검색하기 좋게 색인을 만들어두는 작업을 함
- 해당 프로그램에서 프로듀서 컨슈머 패턴은 파일을 크롤링하는 작업하고, 인덱싱하는 작업을 나눠두면 코드의 가독성이 올라감
    - 프로듀서가 네트워크나 IO에 시간을 많이 소모
    - 컨슈머는 CPU를 많이 사용
    - 이러한 기능을 하나의 스레드로 작동되게 개발했다면, 프로듀서가 동작할 때는 CPU가 일처리를 하지 않고, 컨슈머가 동작할 때는 네트워크나 IO가 일처리를 하지 않게 됨
    - 즉, 두개의 기능을 하나의 클래스로 구현할 때보다 재사용성과 성능 향상

```java
// FileCrawler 프로그램은 디스크에 들어있는 디렉토리 계층 구조를 따라가면서 검색 대상 파일이라고 판단되는 파일의 이름을 작업 큐에 모두 쌓아 넣는 프로듀서 역할을 담당
public class FileCrawler implements Runnable {
    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;
    ...
    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
        }
    }

    private void crawl(File root) throws InterruptedException { 
        File[] entries = root.listFiles(fileFilter);
        if (entries != null) {
            for (File entry : entries) 
                if (entry.isDirectory())
                    crawl(entry);
                else if (!alreadyIndexed(entry))
                    fileQueue.put(entry);
        } 
    }
}

// Indexer 프로그램은 작업 큐에 쌓여 있는 파일 이름을 뽑아내어 해당 파일의 내용을 색인하는 컨슈머 역할
public class Indexer implements Runnable {
    private final BlockingQueue<File> queue;
    public Indexer(BlockingQueue<File> queue) { 
        this.queue = queue;
    }
    public void run() {
        try {
            while (true) 
                indexFile(queue.take());
        } catch (InterruptedException e) { 
            Thread.currentThread().interrupt();
        }
    }
}
```

<br>

#### 직렬 스레드 한정
- 프로듀서-컨슈머 패턴과 블로킹 큐는 가변 객체를 사용할 때 객체의 소유권을 프로듀서에서 컨슈머로 넘기는 과정에서 직렬 스레드 한정(serial thread confienment) 기법을 사용
- 스레드에 한정된 객체는 특정 스레드 하나만이 소유권을 가질 수 있는데 객체를 안전한 방법으로 공개하면 소유권을 이전할 수 있음 (프로듀서 스레드 소유권 -> 컨슈머 스레드)
- 항상 소유권을 이전받는 스레드는 단 하나여야 하고 이러한 작업은 ConcurrentMap, AtomicReference로 처리 가능

<br>

#### 덱, 작업 가로채기
- 덱(Deque)은 앞,뒤 어느 쪽에도 객체를 삽입하거나 제거할 수 있는 큐
    - Deque를 상속받는 실제클래스는 ArrayDeque, LinkedBlockingDeque
- 덱은 작업 가로채기(Work Stealing Pattern)을 구현할 때 쓰임
- 프로듀서-컨슈머 패턴은 하나의 큐를 공유해서 사용하지만 작업 가로채기에서는 컨슈머가 본인의 덱을 가짐
- 만약 특정 컨슈머가 본인의 일을 다 쳐리했다면 다른 컨슈머의 마지막 덱에서 작업을 꺼내올 수 있도록 설계할 수 있음
- 앞이 아니라 뒤에서 작업을 가져오기 때문에 원래 컨슈머와 경쟁이 일어나지 않음
- 컨슈머가 프로듀서의 역할도 가지고 있는 경우 적용하기 좋음
- 컨슈머가 작업을 처리하다가 도중에 처리해야할 일이 생긴다면(예를 들어, 웹크로링 중에 다른 링크가 등장한다면) 자신의 덱에 새로운 작업을 추가하면 됨