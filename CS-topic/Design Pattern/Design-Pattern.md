# Design Pattern
## 디자인 패턴
- 실제 개발 현장에서 비즈니스 요구 사항을 프로그래밍으로 처리하면서 만들어진 다양한 해결책 중에서 많은 사람들이 인정한 베스트 프랙티스
    - 특정 문맥에서 공통적으로 발생하는 문제에 대해 재사용 가능한 해결책
- 해당 디자인 패턴의 종류는 책 '스프링 입문을 위한 자바 객체 지향의 원리와 이해' 책의 '스프링이 사랑한 디자인 패턴' 챕터의 내용을 통해 전달하고자 함
     - 해당 책에서는 스프링은 OOP 프레임워크로 객체 지향의 특성과 설계 원칙을 적용한 프레임워크로 스프링에서 주로 사용되는 패턴을 정리해 두었음
- Gof 디자인 패턴은 크게 다음과 같이 분류됨

### 1) 생성 패턴(Creational Pattern) 
- 추상 팩토리(Abstract Factory): 서로 연관, 의존하는 객체들을 그룹으로 생성해 추상적으로 표현
- 빌더(Builder): 객체의 생성 과정과 표현 방법 분리 → 동일한 객체 생성에도 서로 다른 결과
- 팩토리 메소드(Factory Method): 객체를 생성하기 위한 인터페이스를 정의하여, 어떤 클래스가 인스턴스화 될 것인지는 서브클래스가 결정하도록 하는 것(Virtual-Constructor 패턴)
- 프로토타입(Prototype): 원본 객체를 복제하는 방법
- 싱글톤(Singleton): 하나의 객체를 여러 프로세스가 동시에 참조할 수 없음

<br>

### 2) 구조 패턴(Structural Pattern) 
- 어댑터(Adapter): 호환성이 없는 클래스 인터페이스를 이용할 수 있도록 변환해주는 패턴
- 브리지(Bridge): 구현부에서 추상층을 분리하여, 독립적으로 확장 및 다양성을 가지는 패턴
- 컴포지트(Composite): 여러 객체를 가진 복합, 단일 객체를 구분 없이 다룰 때 사용하는 패턴
- 데코레이터(Decorator): 상속을 사용하지 않고도 객체의 기능을 동적으로 확장해주는 패턴
- 퍼싸드(Façade): 서브 클래스들의 기능을 간편하게 사용할 수 있도록 하는 패턴
- 플라이웨이트(Flyweight): 공유해서 사용함으로써 메모리를 절약하는 패턴
- 프록시(Proxy): 접근이 어려운 객체를 연결해주는 인터페이스 역할을 수행하는 패턴

<br>

### 3) 행위 패턴(Behavioral Pattern)  
- 책임 연쇄(Chain of Responsibility): 한 객체가 처리하지 못하면 다음 객체로 넘어가는 패턴
- 커맨드(Command): 요청에 사용되는 각종 명령어들을 추상, 구체 클래스로 분리하여 단순화함
- 인터프리터(Interpreter): 언어에 문법 표현을 정의하는 패턴
- 반복자(Iterator): 동일한 인터페이스를 사용하도록 하는 패턴
- 중재자(Mediator): 서로의 존재를 모르는 상태에서도 협력할 수 있게 하는 패턴
- 메멘토(Memento): 요청에 따라 객체를 해당 시점의 상태로 돌릴 수 있는 기능을 제공하는 패턴
- 옵서버(Observer): 관찰대상의 변화를 탐지하는 패턴
- 상태(State): 객체의 상태에 따라 동일한 동작을 다르게 처리해야 할 때 사용하는 패턴
- 전략(Strategy): 클라이언트에 영향을 받지 않는 독립적인 알고리즘을 선택하는 패턴
- 템플릿 메소드(Template Method): 유사한 서브 클래스를 묶어 공통된 내용을 상위 클래스에 정의하는 패턴
- 방문자(Visitor): 필요할 때마다 해당 클래스에 방문해서 처리하는 패턴

<br>

### 1. 어댑터 패턴 (Adapter Pattern)
- 어댑터를 번역하면 변환기로 서로 다른 두 인터페이스 사이에 통신이 가능하게 하는 것
    - 해외에서 한국의 전자제품을 사용하려면 110v '어댑터'를 가져가야 한다. 어댑터 처럼 핸드폰과 전원 콘센트 사이에서 둘을 연결해주는 역할 수행
- 호환되지 않는 인터페이스를 가진 객체들이 연결될 수 있도록 해주는 구조적 디자인 패턴
- 클라이언트가 사용하는 인터페이스를 따르지 않는 레거시 코드를 재사용할 수 있게함
- 코드 예시로는 RGB -> HDMI로 연결하는 코드를 예시로 하겠음
    - [참조 블로그](https://hirlawldo.tistory.com/168)

#### 장점
- 기존 코드를 변경하지 않고 원하는 인터페이스 구현체를 만들어 재사용 가능
- 기존 코드가 하던 일과 특정 인터페이스 구현체로 변환하는 작업을 각기 다른 클래스로 분리하여 관리 가능

#### 단점
- 다수의 새로운 인터페이스와 클래스를 도입해야 하므로 구조가 복잡해짐
- 때로는 서비스 클래스를 변경하는 것이 간단할 때도 있음

<br>

```java
// RGB
public class RGB {
    private byte[] images;
 
    public byte[] getImages() {
        return images;
    }
 
    public void setImages(byte[] images) {
        this.images = images;
    }
}

// HDMI
public class HDMI {
    private byte[] images;
 
    public HDMI(byte[] images) {
        this.images = images;
    }
 
    public byte[] getImages() {
        return images;
    }
 
    public void setImages(byte[] images) {
        this.images = images;
    }
}

// Adpater
public interface Adapter {
    HDMI convertRGBToHDMI(RGB rgb);
}

// Adapter를 구현한 HDMIConverter 클래스
public class HDMIConverter implements Adapter {
    @Override
    public HDMI convertRGBToHDMI(RGB rgb) {
        return new HDMI(rgb.getImages());
    }
}
```

<br>

### 2. 프록시 패턴 (Proxy Pattern)
- 프록시 패턴은 대리자, 대변인이라는 뜻을 가진 단어로 어떤 객체를 사용하고자 할때, 객체를 직접적으로 참조하는 것이 아닌 해당 객체를 대신하는 객체를 통해 대상 객체에 접근하는 방식을 사용하여 대신해서 그 역할을 수행하는 패턴
- 으로 서비스 객체가 들어갈 자리에 대리자 객체를 대신 투입해 클라이언트 쪽에서는 대리자 객체를 통해 메서드를 호출하고 반환값을 받는지 모르게 처리할 수 있음
- 프록시 패턴의 주요 포인트는 다음과 같음
    - 대리자는 실제 서비스와 같은 이름의 메서드를 구현. 이때 인터페이스를 사용
    - 대리자는 실제 서비스에 대한 참조 변수를 갖는다
    - 대리자는 실제 서비스의 같은 이름을 가진 메서드를 호출하고 그 값을 클라이언트에게 돌려준다
    - 대리자는 실제 서비스의 메서드 호출 전후에 별도의 로직을 수행할 수 있다
- 코드 예시로는 이미지를 프록시 이미지로 반환
    - [참조 블로그](https://velog.io/@newtownboy/디자인패턴-프록시패턴Proxy-Pattern)

#### 장점
- 사이즈가 큰 객체가 로딩되기 전에도 프록시를 통해 참조를 할 수 있음
- 기존 객체를 수정하지 않고 일련의 로직을 프록시 패턴을 통해 추가할 수 있음
- 실제 객체의 public, protected 메소드를 숨기고 인터페이스를 통해 노출시킬 수 있음
- 로컬에 있지 않고 떨어져있는 객체를 사용할 수 있음
- 원래 객체에 접근에 대해 사전처리를 할 수 있음

#### 단점
- 객체를 생성할 때 한 단계를 거치게 되므로, 빈번한 객체 생성이 필요한 경우 성능이 저하될 수 있음
- 프록시 내부에서 객체 생성을 위해 스레드가 생성, 동기화가 구현되어야 하는 경우 성능이 저하 될 수 있음
- 로직이 난해해져 가독성이 떨어질 수 있음

<br>

```java
// Image.java
public interface Image {
    public void displayImage();
}

// Real_Image.java
public class Real_Image implements Image {
	private String fileName;
    
    public Real_Image(String fileName) {
    	this.fileName = fileName;
    }
    
    private void loadFromDisk(String fileName) {
    	System.out.println("로딩: " + fileName);
    }
    
    @Override
    public void displayImage() {
        System.out.println("보여주기: " + fileName);
    }
}

// Proxy_Image.java
public class Proxy_Image implements Image {
    private String fileName;
    private Real_Image realImage;
    
    public Proxy_Image(String fileName) {
    	this.fileName = fileName;
    }
    
    @Override
    public void displayImage() {
    	if (realImage == null) {
        	realImage = new Real_Image(fileName);
        }
        realImage.displayImage();
    }
}

// Proxy_Pattern.javva
public class Proxy_Pattern {
    public static void main(String args[]) {
        Image image1 = new Proxy_Image("test1.jpg");
        Image image2 = new Proxy_Image("test2.jpg");
        
        image1.displayImage();
        image2.displayImage();
    }
}
```

<br>

### 3. 데코레이터 패턴 (Decoration Pattern)
- 데코레이터는 장식자로 원본에 장식을 더하는 패턴으로 프록시 패턴과 유사하지만 프록시 패턴은 원본 객체와 유사하게 그대로 전달하는 반면 데코레이터는 클라이언트가 받는 반환값에 장식을 추가한다
    - 프록시 패턴은 접근 제어가 목적
    - 데코레이터 패턴은 기능 추가가 목적
    - 자바의 입출력 스트림은 decorator pattern ( stream()은 컬렉션 요소를 처리하고 연산을 수행하기 위한 함수형 프로그래밍 스타일의 API로 다른 개념 )
- 프록시 패턴의 주요 포인트는 다음과 같음
    - 장식자는 실제 서비스와 같은 이름의 메서드를 구현하고 이때 인터페이스를 사용
    - 장식자는 실제 서비스에 대한 참조 변수를 갖는다 (합성)
    - 장식자는 실제 서비스의 같은 이름을 가진 메서드를 호출하고 그 값에 장식을 더해 클라이언트에게 돌려준다
    - 장식자는 실제 서비스의 메서드 호출 전후에 별도의 로직을 수행할 수 있다
- [참조 블로그](https://jake-seo-dev.tistory.com/406)

<br>

```java
// PizzaService
public interface pizzaService {
    String pizzaName();
}

// DefaultPizza
public class DefaultPizza implements PizzaService {
    @Override
    public String pizzaName() {
        return "피자";
    }
}

// CheeseDecorator
public abstract class PizzaDecorator implements PizzaService {
    PizzaService pizzaService;

    public PizzaDecorator(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Override
    public String pizzaName() {
        return pizzaService.pizzaName();
    }
}

// CheeseDecorator
public class CheeseDecorator extends PizzaDecorator{
    public CheeseDecorator(PizzaService pizzaService) {
        super(pizzaService);
    }

    @Override
    public String pizzaName() {
        return "치즈 " + super.pizzaName();
    }
}
```

<br>

### 4. 싱글톤 패턴 (Singleton Pattern)
- 싱글톤 패턴이란 인스턴스를 하나만 만들어 사용하기 위한 패턴. 커넥션 풀, 스레드 풀, 디바이스 설정 객체 등과 같은 경우 인스턴스를 여러 개 만들게 되면 불필요한 자원을 사용하게 되어 오직 인스턴스를 하나만 만들고 그것을 재사용하는 패턴
- 싱글톤 패턴을 적용할 경우 의미상 두 개의 객체가 존재할 수 없음. 이를 구현하기 위해 객체 생성을 위한 new에 제약을 걸어야 하고 만들어진 단일 객체를 반환할 수 있는 메서드가 필요
- 싱글톤 패턴의 특징은 다음과 같음
    - private 생성자를 가짐
    - 유일한 단일 객체를 참조할 정적 참조 변수를 가짐
    - 유일한 단일 객체를 반환할 수 있는 정적 메서드가 필요
    - 단일 객체는 쓰기 가능한 속성을 갖지 않는 것이 정석

#### 장점
- 최초 한번 객체를 생성하고 이후에도 사용하도록 정적으로 메모리 낭비 문제를 방지할 수 있음
- 이미 생성되어 있는 인스턴스를 활용하여 속도 측면에 이점 존재
- 전역으로 사용되는 인스턴스로 여러 클래스에서 데이터를 공유하며 사용할 수 있음
    - 동시성 문제는 유의하여 설계해야함

#### 단점
- 싱글톤 패턴의 객체를 미리 생성한 뒤에 필요한 경우 정적 메서드를 이용하기 때문에 클래스 사이에 의존성이 높아짐. 싱글톤의 인스턴스가 변경되면 해당 인스턴스를 참조하는 모든 클래스를 수정해야 함
- private로 상속이 어려움. 기본 생성자를 private로 만들기 때문에 상속을 통한 자식 클래스를 만들 수 없음
- 싱글톤 패턴은 자원을 공유하여 서로 독립적이여야 하는 단위 테스트를 진행하기 어려움. 독립적인 테스트가 진행이 되려면 전역에서 상태를 공유하고 있는 인스턴스의 상태를 매번 초기화해야 한다. 초기화해주지 않으면 전역에서 상태를 공유 중이기 때문에 테스트가 정상적으로 수행되지 못할 가능성이 존재
- 싱글톤 패턴은 다른 객체지향 원칙을 위배해 안티 패턴이라고도 불리기도 함

<br>

<br>

```java
public class Singeton {
    private static Singleton singleton; // 정적 참조 변수

    private Singleton() {}; // private 생성자

    // 객체 반환 정적 메서드
    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }

        return singleton;
    }
}
```

### 5. 템플릿 메서드 패턴 (Template Method Pattern)
- 어떤 작업을 처리하는 일부분을 서브 클래스로 캡슐화해 전체 일을 수행하는 구조는 바꾸지 않으면서 특정 단계에서 수행하는 내역을 바꾸는 패턴
    - 상위 클래스의 견본 메서드에서 하위 클래스가 오버라이딩한 메서드를 호출하는 패턴
- 전체적으로는 동일하면서 부분적으로는 다른 구문으로 구성된 메서드의 코드 중복을 최소화 할 때 유용
- 다른 관점에서 보면 동일한 기능을 상위 클래스에서 정의하면서 확장/변화가 필요한 부분만 서브 클래스에서 구현할 수 있도록 함
- 상위 클래스에서 공통 로직을 수행하는 템플릿 메서드와 하위 클래스에게 구현을 강제하는 추상 메서드 또는 선택적으로 오버라이딩 할 수 있는 훅(Hook) 메서드를 두어 사용함
- [참조 블로그](https://gmlwjd9405.github.io/2018/07/13/template-method-pattern.html)

#### 장점
- 중복된 코드를 없애고 SubClass 에서는 비즈니스 로직에만 집중할 수 있음
- 나중에 새로운 비즈니스 로직이 추가되어도 기존 코드를 수정하지 않아도 됨

#### 단점
- 추상 메서드가 많아지면 클래스 관리가 복잡해짐
- 자식 클래스는 실제로 부모 클래스를 사용하지 않는데 단순히 패턴 구현을 위한 상속 때문에 의존 관계를 가질수 있음
- 클래스간의 관계와 코드가 꼬여 버릴 수 있음

<br>

```java
public enum DoorStatus { CLOSED, OPENED }
public enum MotorStatus { MOVING, STOPPED }

public class Door {
  private DoorStatus doorStatus;

  public Door() { doorStatus = DoorStatus.CLOSED; }
  public DoorStatus getDoorStatus() { return doorStatus; }
  public void close() { doorStatus = DoorStatus.CLOSED; }
  public void open() { doorStatus = DoorStatus.OPENED; }
}

/* HyundaiMotor와 LGMotor의 공통적인 기능을 구현하는 클래스 */
public abstract class Motor {
    protected Door door;
    private MotorStatus motorStatus; // 공통 2. motorStatus 필드

    public Motor(Door door) { // 공통 1. Door 클래스와의 연관관계
        this.door = door;
        motorStatus = MotorStatus.STOPPED;
    }

    public void moveMotor() {
        MotorStatus motorStatus = getMotorStatus();
        // 이미 이동 중이면 아무 작업을 하지 않음
        if (motorStatus == MotorStatus.MOVING) return;

        DoorStatus doorStatus = door.getDoorStatus();
        // 만약 문이 열려 있으면 우선 문을 닫음
        if (doorStatus == DoorStatus.OPENED) door.close();
    }

    // 공통 3. etMotorStatus, setMotorStatus 메서드
    public MotorStatus getMotorStatus() { return MotorStatus; }
    protected void setMotorStatus(MotorStatus motorStatus) { this.motorStatus = motorStatus; }
}

/* Motor를 상속받아 HyundaiMotor 클래스를 구현 */
public class HyundaiMotor extends Motor{
    public HyundaiMotor(Door door) { super(door); }
    private void moveHyundaiMotor(Direction direction) {
        // Hyundai Motor를 구동시킴
    }

    public void move(Direction direction) {
        super.moveMotor();

        // Hyundai 모터를 주어진 방향으로 이동시킴
        moveHyundaiMotor(direction);
        // 모터 상태를 이동 중으로 변경함
        setMotorStatus(MotorStatus.MOVING);
    }
}

/* Motor를 상속받아 LGMotor 클래스를 구현 */
public class LGMotor extends Motor{
    public LGMotor(Door door) { super(door); }
    private void moveLGMotor(Direction direction) {
        // LG Motor를 구동시킴
    }
    public void move(Direction direction) {
            super.moveMotor();

            // LG 모터를 주어진 방향으로 이동시킴
            moveLGMotor(direction); // (이 부분을 제외하면 HyundaiMotor의 move 메서드와 동일)
            // 모터 상태를 이동 중으로 변경함
            setMotorStatus(MotorStatus.MOVING);
    }
}
```

<br>

### 6. 팩토리 메서드 패턴 (Factory Method Pattern)
- 공장을 의미하는 팩토리로 객체 생성 처리를 서브 클래스로 분리 해 처리하도록 캡슐화하는 패턴으로 특정한 구현체를 만들 수 있는 다양한 팩토리(Creator)를 통해 생성
    - 오버라이드된 메서드가 객체를 반환하는 패턴
    - 즉, 부모(상위) 클래스에 알려지지 않은 구체 클래스를 생성하는 패턴이며. 자식(하위) 클래스가 어떤 객체를 생성할지를 결정하도록 하는 패턴 (클래스의 인스턴스를 만드는 일을 서브클래스에게 맡기는 것)
- 상속을 통해 서브 클래스에서 팩토리 메소드를 오버라이딩하여 객체의 생성부를 구현
- 객체의 생성 코드를 별도의 클래스/메서드로 분리함으로써 객체 생성의 변화에 대비하는 데 유용
- [참조 블로그](https://readystory.tistory.com/117)
- [참조 블로그2](https://cjw-awdsd.tistory.com/54)

#### 장점
- 기존 코드(인스턴스를 만드는 과정)를 수정하지 않고 새로운 인스턴스를 다른 방법으로 생성하도록 확장
- 상속을 통해 사용하므로 코드가 간결해짐

#### 단점
- 관리해야할 클래스가 많아져 관리가 복잡해짐
- 클라이언트가 creator 클래스를 반드시 상속해 Product를 생성해야 함

<br>

```java
public abstract class Computer {
	
    public abstract String getRAM();
    public abstract String getHDD();
    public abstract String getCPU();
	
    @Override
    public String toString(){
        return "RAM= "+this.getRAM()+", HDD="+this.getHDD()+", CPU="+this.getCPU();
    }
}

public class PC extends Computer {
 
    private String ram;
    private String hdd;
    private String cpu;
	
    public PC(String ram, String hdd, String cpu){
        this.ram=ram;
        this.hdd=hdd;
        this.cpu=cpu;
    }
    @Override
    public String getRAM() {
        return this.ram;
    }
 
    @Override
    public String getHDD() {
    return this.hdd;
    }
 
    @Override
    public String getCPU() {
        return this.cpu;
    }
 
}

public class ComputerFactory {
 
    public static Computer getComputer(String type, String ram, String hdd, String cpu){
        if("PC".equalsIgnoreCase(type))
            return new PC(ram, hdd, cpu);
        else if("Server".equalsIgnoreCase(type))
            return new Server(ram, hdd, cpu);
		
        return null;
    }
}
```

<br>

### 7. 전략 패턴 (Strategy Pattern)
- 객체들이 할 수 있는 행위 각각에 대해 전략 클래스를 생성하고, 유사한 행위들을 캡슐화 하는 인터페이스를 정의하여, 객체의 행위를 동적으로 바꾸고 싶은 경우 직접 행위를 수정하지 않고 전략을 바꿔주기만 함으로써 행위를 유연하게 확장할 수 있는 패턴
    - 객체가 할 수 있는 행위들 각각을 전략으로 만들어 놓고, 동적으로 행위의 수정이 필요한 경우 전략을 바꾸는 것만으로 행위의 수정이 가능하도록 만든 패턴
    - '전략'이란 일종의 알고리즘이 될 수 도 있으며, 기능이나 동작이 될 수도 있는 특정한 목표를 수행하기 위한 행동 계획
- 전략 패턴의 3요소는 다음과 같음
    - 전략 메서드를 가진 전략 객체
    - 전략 객체를 사용하는 컨텍스트
    - 전략 객체를 생성해 컨텍스트에 주입하는 클라이언트
- [참조 블로그](https://bcp0109.tistory.com/370)

#### 장점
- 공통 로직이 부모 클래스에 있지 않고 Context 라는 별도의 클래스에 존재하기 때문에 구현체들에 대한 영향도가 적음
- Context 가 Strategy 라는 인터페이스를 의존하고 있기 때문에 구현체를 갈아끼우기 쉬움

#### 단점
- 로직이 늘어날 때마다 구현체 클래스가 늘어남
- Context 와 Strategy 를 한번 조립하면 전략을 변경하기 힘듬

<br>

```java
public interface Strategy {
    void call();
}

public class StrategyLogic1 implements Strategy {
    @Override
    public void call() {
        System.out.println("비즈니스 로직 1 실행");
    }
}

public class StrategyLogic2 implements Strategy {
    @Override
    public void call() {
        System.out.println("비즈니스 로직 2 실행");
    }
}

public class Context {

    private final Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 비즈니스 로직 시작
        strategy.call();
        // 비즈니스 로직 종료

        stopWatch.stop();
        System.out.println("실행 시간 = " + stopWatch.getTotalTimeMillis());
    }
}

public class AfterStrategyApp {

    public static void main(String[] args) {
        Strategy strategy1 = new StrategyLogic1();
        Context context1 = new Context(strategy1);
        context1.execute();

        Strategy strategy2 = new StrategyLogic1();
        Context context2 = new Context(strategy2);
        context2.execute();
    }
}
```

<br>

### 8. 상태 패턴 (State Pattern)
- 어떤 행위를 수행할 때 상태에 행위를 수행하도록 위임하고 시스템의 각 상태를 객체화하여 상태를 객체화 해 상태가 행동을 할 수 있도록 위임하는 패턴
- 상태 클래스를 인터페이스로 캡슐화하고, 각 클래스에서 수행하는 행위들을 메서드로 구현
    - 상태는 객체가 시스템에 존재하는 동안, 즉 객체의 라이프 타임동안 객체가 가질 수 있는 어떤 조건이나 상황
    - 객체가 어떤 상태에 있는 동안 어떤 액티비티 등을 수행하거나 특정 이벤트 발생을 기다리는 것
    - 시작 상태 : 객체가 시작하는 처음 상태
    - 상태 진입 : 객체의 한 상태에서 다른 상태로 이동하는 것
- [참조 블로그](https://inpa.tistory.com/entry/GOF-💠-상태State-패턴-제대로-배워보자)

#### 장점
- 상태(State)에 따른 동작을 개별 클래스로 옮겨서 관리 할 수 있음
- 상태(State)와 관련된 모든 동작을 각각의 상태 클래스에 분산시킴으로써, 코드 복잡도를 줄일수 있음
- 하나의 상태 객체만 사용하여 상태 변경을 하므로 일관성 없는 상태 주입을 방지

#### 단점
- 상태 별로 클래스를 생성하므로, 관리해야할 클래스 수 증가
- 상태 클래스 갯수가 많고 상태 규칙이 자주 변경된다면, Context의 상태 변경 코드가 복잡해지게 될 수 있다.
- 객체에 적용할 상태가 몇가지 밖에 없거나 거의 상태 변경이 이루어지지 않는 경우 패턴을 적용하는 것이 과도할 수 있음

<br>

```java
class Laptop {
    // 상태를 나타내는 상수
    public static final int OFF = 0;
    public static final int SAVING = 1;
    public static final int ON = 2;

    // 상태를 저장하는 변수
    private int powerState;

    Laptop() {
        this.powerState = Laptop.OFF; // 초기는 노트북이 꺼진 상태
    }

    // 상태 변경
    void changeState(int state) {
        this.powerState = state;
    }

    // 전원 버튼 클릭
    void powerButtonPush() {
        if (powerState == Laptop.OFF) {
            System.out.println("전원 on");
            changeState(Laptop.ON);
        } else if (powerState == Laptop.ON) {
            System.out.println("전원 off");
            changeState(Laptop.OFF);
        } else if (powerState == Laptop.SAVING) {
            System.out.println("전원 on");
            changeState(Laptop.ON);
        }
    }

    void setSavingState() {
        System.out.println("절전 모드");
        changeState(Laptop.SAVING);
    }

    void typebuttonPush() {
        if (powerState == Laptop.OFF) {
            throw new IllegalStateException("노트북이 OFF 인 상태");
        } else if (powerState == Laptop.ON) {
            System.out.println("키 입력");
        } else if (powerState == Laptop.SAVING) {
            throw new IllegalStateException("노트북이 절전 모드인 상태");
        }
    }

    void currentStatePrint() {
        if (powerState == Laptop.OFF) {
            System.out.println("노트북이 전원 ON 인 상태 입니다.");
        } else if (powerState == Laptop.ON) {
            System.out.println("노트북이 전원 ON 인 상태 입니다.");
        } else if (powerState == Laptop.SAVING) {
            System.out.println("노트북이 절전 모드 인 상태 입니다.");
        }
    }
}

class Client {
    public static void main(String args[]) {
        LaptopContext laptop = new LaptopContext();
        laptop.currentStatePrint();
        
        // 노트북 켜기 : OffState -> OnState
        laptop.powerButtonPush();
        laptop.currentStatePrint();
        laptop.typebuttonPush();
      
        // 노트북 절전하기 : OnState -> SavingState
        laptop.setSavingState();
        laptop.currentStatePrint();
    
        // 노트북 다시 켜기 : SavingState -> OnState
        laptop.powerButtonPush();
        laptop.currentStatePrint();

        // 노트북 끄기 : OnState -> OffState
        laptop.powerButtonPush();
        laptop.currentStatePrint();
    }
}
```

<div style="text-align: right">23-11-02</div>

-------