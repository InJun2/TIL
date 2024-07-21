# final

### final
- 자바에서는 불변성을 확보할 수 있도록 final 키워드를 제공함
- final 키워드는 어떤 것을 제한하는 의미를 가지는 공통적인 성격을 가지고 있음
- final은 변수(variable), 메서드(method), 또는 클래스(class)에 사용될 수 있고 어느곳에서 사용되냐에 따라 다른 의미를 가짐
    - 클래스나 변수에 final을 붙이면 처음 정의된 상태가 변하지 않는 것을 의미

<br>

### 클래스(class)에서의 final
- final 키워드를 클래스에 붙이면 상속 불가능한 클래스가 됨
- 다른 클래스에서 상속하여 재정의를 할 수 없는 것
- 대표적인 클래스로 Integer와 같은 랩퍼(Wrapper) 클래스가 있음. 클래스 설계시 재 정의 여부를 생각해서 재정의 불가능하게 사용하고 싶다면 유지보수 측면에서 final을 사용하는 것이 좋음

```java
public final class FinalClass {
    // Class definition
}

// 다음 클래스는 컴파일 오류가 발생합니다.
// public class SubClass extends FinalClass {
// }
```

<br>

### 인자(arguments)에서의 final
- final 키워드를 인자에 붙이면 메소드 내에서 변경이 불가능함
- final int number를 인자로 선언하면 해당 number를 읽을 수는 있지만 number 값을 변경하려고 하면 컴파일에러가 발생

```java
public void method(final int number) {
    // number = 5; // 컴파일 오류 발생
    System.out.println(number);
}
```

<br>

### 메서드(method)에서의 final
- final 키워드를 메서드에 붙이면 override를 제한
    - 더 이상 재정의할 수 없음
- 클래스를 상속하게 되면 해당 클래스의 protected, public 접근 제한자를 가진 메서드를 상속해서 재구현을 할 수 있는데 상속 받은 클래스에서 해당 메서드를 수정해서 사용하지 못하도록 함

```java
public class SuperClass {
    public final void display() {
        System.out.println("This is a final method.");
    }
}

public class SubClass extends SuperClass {
    // 다음 메서드는 컴파일 오류가 발생합니다.
    // @Override
    // public void display() {
    //     System.out.println("Cannot override final method.");
    // }
}
```

<br>

### 변수(variable)에서의 final
- final 키워드를 변수에 붙이면 이 변수는 수정할 수 없다는 의미를 가짐
- 수정될 수 없기 때문에 초기화 값을 필수적이고 만약 객체안의 변수라면 생성자, static 블럭을 통한 초기화까지는 허용됨
    - 값이 할당되지 않은 멤버 변수의 경우 1회 초기화 가능
- 수정 할 수 없다는 범위는 그 변수의 값에 한정하여 다른 객체를 참조하거나 참조하는 객체의 내부의 값은 변경될 수 있음
    - 기본형 변수라면 값을 변경하지 못하지만 참조형 변수라면 가르키는 객체를 변경하지 못하는 것이지 객체 내부의 값은 변경이 가능함

```java
public class Example {
    public static void main(String[] args) {
        final int constantValue = 10;
        // constantValue = 20; // 컴파일 오류 발생
        System.out.println(constantValue);

        final List<String> list = new ArrayList<>();
        list.add("Hello"); // 가능
        // list = new ArrayList<>(); // 컴파일 오류 발생
    }
}
```

<br>

### 주의할 점
- final 변수는 초기화 이후 변경이 발생하지 않도록 만드는데 List에 final을 선언하면 List 변수의 변경은 불가능하나, List 내부에 있는 변수들은 변경이 가능하여 문자열을 계속 추가가 가능함
- Effective final은 Java8에서 추가된 기능으로 final이 붙지 않은 변수의 값이 변경되지 않는다면 그 변수를 Effectively final이라고 함. 컴파일러가 final로 취급함
    - 초기화 후에 값이 변하지 않으면 사실상 final로 취급함

```java
public void method() {
    int effectivelyFinal = 10;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println(effectivelyFinal);
        }
    };
}
```

<br>

## Ssafy Wizards CS Study

### final 키워드
- 그렇다면 컴파일 과정에서, final 키워드는 다르게 취급되나요?
- 추가 : 생성자에도 final을 사용할 수 있을까요?

<details>
<summary>정답</summary>

#### 그렇다면 컴파일 과정에서, final 키워드는 다르게 취급되나요?
- final 키워드는 컴파일러가 다양한 최적화를 수행할 수 있도록 도와줌
- final 변수
    - 상수 풀에 저장 : final 변수는 값이 변하지 않기 때문에, 컴파일러는 이 값을 상수 풀에 저장할 수 있음. 이를 통해 런타임에 값을 계속 참조할 필요 없이 컴파일 시점에 값이 직접 삽입됨
    - 인라인 최적화 : 컴파일러는 final 변수의 값을 코드 내에 인라인으로 삽입할 수 있음. 예를 들어, final 변수가 반복문 내에서 사용되면, 변수 참조 대신 실제 값이 삽입됨
- final 메서드
    - 가상 메서드 테이블에서 제거 : final 메서드는 오버라이딩될 수 없으므로, 컴파일러는 가상 메서드 테이블(vtable)에서 이 메서드를 제거할 수 있음. 이는 메서드 호출 시 간접 참조를 줄여주며, 성능 최적화에 기여함
    - 인라이닝 : final 메서드는 오버라이딩되지 않으므로, 컴파일러는 이 메서드를 호출하는 코드를 인라인으로 삽입할 수 있음. 이는 메서드 호출 오버헤드를 줄이고 성능을 향상함
- final 클래스
    - 상속과 다형성 최적화: final 클래스를 상속할 수 없으므로, 컴파일러는 이 클래스를 사용할 때 다형성(polymorphism)을 고려하지 않아도 됨
    - 고정된 객체 레이아웃: final 클래스의 객체 레이아웃은 고정되므로, 메모리 접근 시 더 빠른 참조가 가능

#### + 인라인이란?
- 함수 호출 대신 함수의 본체 코드를 호출 지접에 직접 삽입하는 것을 의미하며 주로 컴파일러 최적화 기법으로 사용됨

```java
public class InlineExample {    // 함수 호출 방식
    public static void main(String[] args) {
        int result = add(2, 3);  // 함수 호출
        System.out.println(result);
    }

    public static int add(int a, int b) {
        return a + b;
    }
}

public class InlineExample {    // 컴파일러가 인라인 최적화를 수행한 인라인 방식
    public static void main(String[] args) {
        int result = 2 + 3;  // 함수 본체 코드가 직접 삽입됨
        System.out.println(result);
    }
}
```

<br>

#### 생성자에도 final을 사용할 수 있을까요?
- 생성자는 특별한 메서드로 상속되지 않으며 오버라이딩이 되지 않아 수정될 가능성이 없어 수정을 제한할 의미가 없음
- 생성자는 본질적으로 수정할 수 없기 때문에 final 키워드가 필요 없음
    - final을 사용하면 컴파일 에러 발생

</details>


<br>

<div style="text-align: right">22-11-29</div>

-------

## Reference
- https://sabarada.tistory.com/148
- https://sudo-minz.tistory.com/135