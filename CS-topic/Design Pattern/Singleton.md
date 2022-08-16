# Singleton Pattern

### Sington
- 객체의 인스턴스를 오직 1개만 생성하고 생성된 객체를 호출하는 패턴
    - 프로그램 내에서 하나의 객체만 존재해야 함
    - 프로그램 내에서 여러 부분에서 해당 객체를 공유하여 사용해야 함
- 최초 한번만 메모리를 할당하고 해당 메모리에 인스턴스를 만들어 사용하는 디자인 패턴
    - GoF 디자인패턴의 생성패턴
    - 하나의 고정된 메모리 영역을 사용하여 추후 해당 객체에 접근할 때 메모리 낭비를 방지
    - 미리 생성해둔 객체활용해 속도 이점 존재
    - 싱글톤 인스턴스가 전역으로 사용되는 인스턴스기 때문에 다른 클래스의 인스턴스들이 접근하여 사용 가능해 데이터 공유가 쉬움

```java
// 늦은 초기화 -> 아래 문제점 존재
public class Singleton {

    private static Singleton instance;
	
    private Singleton () {} //생성자를 private로
	
    public static Singleton getInstance() {
        if (instance == null){
            instance = new Singleton();
        }    
        
        return instance;
    }
}

// 이른 초기화
public class Singleton{
    private static Singleton instance = new Singleton();

    private Singleton(){
        // 외부에서 생성이 불가능하도록 private
    }

    public static Singleton getInstance(){
        return instance;
    }
    // 해당 객체를 리턴받아 두개의 객체를 생성하고 주소값을 확인해보면 같은 주소값을 출력함
}
```

<br>

### Multi-Thread 싱글톤 문제
- 여러개의 인스턴스 생성
    - 멀티스레드 환경에서 인스턴스가 없을 때 동시에 생성하게 만들어 둔다면 각각 새로운 인스턴스 생성됨 -> 위의 늦은 초기화만 해당됨
- 변수 값의 일관성 실패
    - 여러개의 스레드에서 다른 전역변수의 값을 동시에 변경한다면 일관되지 않은 값이 생길 수 있음

<br>

#### Multi-Thread 싱글톤 해결방안 
- 정적 변수에서 인스턴스 생성
    - 위의 예시코드 처럼 static 변수로 singleton 인스턴스를 생성하는 방법으로 해결 가능
    - 초기에 인스턴스를 생성하면 멀티스레드 환경에서도 다른 객체들은 getInstance 를 통해 하나의 인스턴스를 공유할 수 있음
- synchronzied 사용
    - synchronzied 를 사용하여 동시성 문제를 해결하는 방법
    - 늦은 초기화에서 synchronzied를 사용하여 이후 호출될 때는 인스턴스가 이미 생성되어 있기 때문에 해당 synchronzied 블록에 접근 하지 않음 
    - 그러나 Thread-safe를 보장하기 위해 성능 저하가 발생함
```java
public class Singleton {

    private static Singleton instance;
	
    private Singleton () {} //생성자를 private로

    public static Singleton getInstance() {
        if (instance == null) {
            //synchroized를 활용하여 여러 인스턴스를 생성하는 것을 방지
            synchronized (Singleton.class) {
                if (instance == null)
                    instance = new Singleton();
                }
            }
        return instance;
    }
}
```

<div style="text-align: right">22-08-15</div>

-------

## Reference
- https://tecoble.techcourse.co.kr/post/2020-11-07-singleton/
- https://velog.io/@seongwon97/싱글톤Singleton-패턴이란
- https://coding-factory.tistory.com/709