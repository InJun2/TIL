# 다형성

### 다형성 (Polymorphism) 이란?
- 서로 다른 클래스의 객체가 같은 메시지를 받았을 때 각자의 방식으로 동작하는 능력
- 객체지향의 핵심으로 상속과 함께 활용할 경우 코드를 간결하게 해주고 유연함을 갖추게 해줌
- 부모 클래스의 메소드를 자식 클래스가 오버라이딩(재정의)해서 자신의 역할에 맞게 활용하는 것이 다형성
- 구체적으로 현재 어떤 클래스 객체가 참조되는 지는 무관하게 프로그래밍 가능
- 예시로는 로그인의 경우 카카오 로그인, 네이버 로그인, 회원 로그인으로 생성해도 로그인 로직이 같은 경우

```java
// 로그인 인터페이스
public interface Login {
    void authenticate(String username, String password);
}

// 카카오 로그인 클래스
public class KakaoLogin implements Login {

    @Override
    public void authenticate(String username, String password) {
        // 카카오 로그인 로직
    }
}

// 네이버 로그인 클래스
public class NaverLogin implements Login {

    @Override
    public void authenticate(String username, String password) {
        // 네이버 로그인 로직
    }
}

// 기본 회원 로그인 클래스
public class BasicLogin implements Login {

    @Override
    public void authenticate(String username, String password) {
        // 기본 회원 로그인 로직
    }
}

public class Main {
    public static void main(String[] args) {
        // 다형성을 통해 각 로그인 방법을 인터페이스로 관리
        Login kakaoLogin = new KakaoLogin();
        Login naverLogin = new NaverLogin();
        Login basicLogin = new BasicLogin();

        // 각 로그인 방법으로 로그인을 수행
        login(kakaoLogin, "kakao_user", "kakao_password");
        login(naverLogin, "naver_user", "naver_password");
        login(basicLogin, "user", "password");
    }

    // 다형성을 이용한 로그인 메서드
    public static void login(Login loginMethod, String username, String password) {
        loginMethod.authenticate(username, password);
    }
}
```

<br>

### 다형성 Java 예시
```java
// Object > Person > SpiderMan
SpiderMan sman = new SpiderMan("피터", new Spider(), false);
SpiderMan sman2 = sman;
Person person = sman;
Object obj = sman;
```

<br>

### 다형성의 동적 바인딩, 정적 바인딩
#### 정적 바인딩 (Static Binding)
- 컴파일 단계에서 발생하는 바인드로 참조 변수의 타입에 따라 연결이 달라짐
- 참조 변수의 타입을 기반으로 메서드나 필드를 결정
- 컴파일러는 메서드 호출 시 참조 변수의 정확한 타입을 이용하여 해당 클래스에서 정의된 메서드나 필드를 바로 찾을 수 있음
    - 메서드 오버 로딩 시 어떤 메서드가 호출될 지 컴파일 시에 결정
- 이 과정에서 오버로딩된 메서드 중에서는 컴파일 시에 어떤 메서드가 호출될지 결정됨

<br>

#### 동적 바인딩 (Dynamic Binding)
- 런 타임에 발생하는 바인드로 객체의 실제 타입을 기반으로 메서드나 필드를 결정
- 메서드 호출 시 JVM은 객체의 실제 타입을 확인하고 그 타입에서 오버라이딩된 메서드를 호출함
    - 메서드 오버라이딩된 경우 런타임 시에 객체의 실제 타입에서 오버라이딩된 메서드가 호출
- 동적 바인딩은 다형성의 핵심 같은 개념으로 같은 메서드 호출이지만 객체의 실제 타입에 따라 다르게 실행될 수 있게함

<br>

### 정적 바인딩 vs 동적 바인딩?
- 정적 바인딩과 동적 바인딩을 사용하는 경우가 다르다기 보다는 발생 시점의 차이
- 컴파일 시간에 정적 바인딩이 발생하고 이후 런타임에서는 동적 바인딩이 발생하는 것

<br>

```java
class SuperClass {
    String x = "super";

    public void method() {
        System.out.println("super class method");
    }
}

class SubClass extends SuperClass {
    String x = "sub";

    @Override
    public void method() {
        System.out.println("sub class method");
    }
}

public class MemberBindingTest {

    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        System.out.println(subClass.x);
        subClass.method();
        // "sub", "super class method" 출력
        
        SuperClass superClass = subClass;
        System.out.println(superClass.x);
        superClass.method();
        // "super", "sub class method" 출력

        // 이렇게 출력되는 이유는 동적 바인드가 발생해서
    }
}

```

<br>

### Java 참조형 객체 형변환
```java
// 예시코드
class Animal {
    protected String species;

    public Animal(String species) {
        this.species = species;
    }

    public String displayInfo() {
        return species;
    }
}

class Dog extends Animal {
    private String name;

    public Dog(String species, String name) {
        super(species);
        this.name = name;
    }

    @Override
    public String displayInfo() {
        return name + " : " + species;
    }
}
```

#### 업 캐스팅 (Up Casting)
- 하위 클래스의 인스턴스를 상위 클래스 타입으로 변환하는 것
- 부모 클래스 타입의 변수로 자식 클래스 타입의 객체를 참조할 수 있는 것으로 묵시적으로 (자동으로) 이루어짐

```java
Animal animal = new Dog("개", "백구");
```

#### 다운 캐스팅 (Down Casting)
- 업캐스팅된 객체를 다시 원래의 하위 클래스 타입으로 변환하는 것
- 다운캐스팅은 명시적으로 형변환(casting) 연산자를 사용하여야함
-  업캐스팅된 객체를 다시 원래의 하위 클래스 타입으로 돌려놓는 과정이기 때문에, 다운캐스팅은 형변환 연산자를 통해 명시적으로 타입을 지정해주어야함
    - 다운캐스팅은 실제로 참조하고 있는 객체의 타입이 변환하려는 타입과 호환될 때에만 가능, 만약 호환되지 않는 경우 'ClassCastException' 예외가 발생
    - instanceof 연산자로 특정 클래스 타입인지 확인하고 사용을 권장

```java
Animal animal = new Dog("개", "백구");  //  업 캐스팅

if(animal instanceof Dog) {
    Dog dog = (Dog) animal;     // 다운 캐스팅
}
```

<br>

### 다운 캐스팅 객체 동작 과정
- 생성자로 Dog 객체를 생성하여 Heap 메모리에는 Dog 객체가 할당됨
- 하지만 Animal 변수에 담아 컴파일러는 Animal 타입으로 인식함
- 실행 시에는 실제 객체 타입을 고려하여 동적 바인딩이 이루어짐
    - 자식 클래스의 재정의 메서드를 사용

```java
Animal animal = new Dog("개", "백구");  //  업 캐스팅

animal.displayInfo();
// 다운 캐스팅을 하지 않더라도 업 캐스팅된 객체가 실제로 자식 클래스의 인스턴스인 경우이고 오버라이딩된 메서드를 출력한다면 부모 객체의 메서드가 아닌 자식 객체의 메서드가 실행됨
// "개 : 백구" 출력
```

<br>