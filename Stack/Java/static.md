# static

### static
- static은 정적이라는 의미를 가지며 java 관점에서는 '클래스의' 또는 '공통적인' 이라는 의미를 가짐
    - static은 클래스 레벨의 멤버를 정의할 때 사용하는 키워드
- 클래스의 인스턴스와 무관하게 클래스 자체에 속하는 멤버를 정의하는 데 사용
- JVM은 Java 프로그램을 실행하기 위해 클래스를 로드하는데 클래스가 로드될 때 해당 클래스의 코드, 상수, static 변수 등은 모두 메모리에 로딩됨
    - static은 컴파일러에 의해 .java에서 .class 파일로 로드될 시 우선적으로 Method 영역 메모리에 할당됨
    - static 변수나 메서드는 클래스가 로드될 때 한 번만 메모리에 할당되어 모든 인스턴스가 동일한 데이터를 공유하게 되어 메모리 사용이 효율적
    - 여러 인스턴스 간에 공통으로 사용하는 데이터나 메서드의 경우, static을 사용하면 데이터 중복을 줄일 수 있음
- static은 변수, 메서드, 중첩 클래스에 적용이 가능

<br>

### static valiable
- static 변수는 클래스의 모든 인스턴스가 공유하는 데이터로 static 변수는 객체가 생성되기 전에도 접근할 수 있으며 모든 객체가 동일한 값을 참조함
    - 클래스 전체에서 공통적으로 사용하는 데이터나 상수를 저장하는 데 사용됨
    - static 변수는 class 레벨에서 사용하는 변수로 class 변수라고 함 
- 메모리에 로딩될 때 static 변수는 메서드 영역에 할당됨
    - 메서드 영역은 클래스와 관련된 정보(클래스 정의, 상수 풀, 필드, 메서드, 정적 변수 등)를 저장하는 공간으로 JVM이 시작될 때 할당되며, 프로그램 실행 동안 유지됨
- static 변수는 이 과정에서 클래스가 처음 로드될 때 메모리에 할당되며 프로그램의 실행 동안 변하지 않는 데이터를 저장하는 용도로 사용됨
    - 이러한 특성에 있어 지나친 static 변수 사용은 메모리 사용에 있어 필요 이상으로 긴 생명주기를 가져 메모리 낭비를 초래할 수 있음

<br>

### static method
- static 메서드는 클래스 레벨에서 정의되며 인스턴스화 없이 호출할 수 있음
- 메서드 내부에서 인스턴스 변수나 인스턴스 메서드에 접근할 수 없음
    - static 메서드는 class 변수만 접근할 수 있음
    - static 메서드는 super나 this를 사용할 수 없음
- 유틸리티 메서드나 특정 클래스와 관련된 공통된 동작을 정의할 때 사용됨

<br>

### static nested class
- static 키워드는 클래스 자체에 직접 붙일 수 없으나 클래스의 내부 중첩 클래스에는 적용이 가능
- 정적 중첩 클래스(static nested class)는 바깥 클래스의 인스턴스와는 독립적으로 존재할 수 있으며, 바깥 클래스의 static 멤버에만 접근할 수 있음
    - 즉 바깥 클래스의 인스턴스를 생성하지 않고도 정적 내부 클래스를 인스턴스화 할 수 있음
- 바깥 클래스의 static 멤버(변수 및 메서드)에만 접근할 수 있고, 바깥 클래스의 인스턴스 멤버에 직접 접근할 수는 없음
    - Method 영역에 올라가 있는 것만 접근이 가능하므로
- 바깥 클래스의 인스턴스와 연관되지 않으므로, 메모리 효율성이 향상될 수 있음
- 바깥 클래스의 private 및 protected 멤버에 접근할 수 있음. 따라서, 바깥 클래스와 관련된 구체적인 구현을 숨기면서도 내부적으로 접근할 수 있는 장점이 있음

```java
public class OuterClass {
    private static String outerStaticVariable = "Outer Static Variable";

    // 정적 내부 클래스
    public static class StaticNestedClass {
        public void display() {
            // 바깥 클래스의 static 변수에 접근
            System.out.println(outerStaticVariable);
        }
    }
}

// 다른 클래스에서 정적 내부 클래스만 인스턴스화 (OuterClass 생성 없이 정적 내부 클래스만 생성)
public class Main {
    public static void main(String[] args) {
        OuterClass.StaticNestedClass nestedClass = new OuterClass.StaticNestedClass();
        nestedClass.display();  // 출력: Outer Static Variable
    }
}
```

<br>

### static 사용시 주의 사항
- 클래스 로더가 .class파일을 탐색 중 static 키워드가 존재하면 객체가 생성되지 않아도 항상 메모리를 할당해야 하는 멤버로 보고 Method Area(Static Area)에 메모리를 할당하므로 인스턴스보다 먼저 생성됨
- static 변수는 글로벌 변수에 가까우므로 인스턴스 변수보다 테스트가 까다로움
- static 변수는 객체지향 프로그램의 원칙인 각 객체의 데이터들이 캡슐화 되어야 한다는 원칙에 어긋남
- 또한 오버라이딩을 할 수 없어 코드의 재사용성이 떨어질 뿐만 아니라 프로그램이 종료되기 전까지 항상 메모리에 상주하여 자주 사용하지 않는 메서드가 누적되면 GC에 수거되지 못해 오히려 메모리 낭비 발생

```java
public class Counter {
    public int count = 0;
    Counter() {
        this.count++;
    }
    public static int getCount() {
        return count; // 인스턴스보다 먼저 메모리를 할당받으므로 에러 발생
    }
}
```

<br>

### class에 붙는 static과 멤버에 붙는 static의 의미 차이
- 클래스의 멤버(필드, 메서드)에 붙는 static키워드는 정적으로 메모리를 할당하므로 객체 생성 없이도 사용 가능하다는 의미
- 클래스에 붙는 static은 외부 클래스의 객체 생성없이 내부 클래스의 객체를 생성할 수 있다는 의미

<br>

### 생성자에도 static을 사용할 수 있을지?
- static은 클래스의 객체가 아닌 클래스에 속하게 되는데 클래스는 이미 정적으로 존재
    - 'static'이라는 키워드는 클래스 자체에 속하는 것을 의미하며 클래스에 모든 인스턴스에 공유됨
    - 반면 생성자는 클래스의 인스턴스를 생성하기 위한 메서드
    - 생성자는 클래스의 객체가 생성될 때 호출하므로 성립이 되지 않음
- 정적 생성자를 선언하면 하위 클래스에선 생성자에 접근하거나 호출할 수 없음

<br>

## Ssafy Wizards CS Study

### static class와 static method
- static 을 사용하면 어떤 이점을 얻을 수 있나요? 어떤 제약이 걸릴까요?
- 컴파일 과정에서 static 이 어떻게 처리되는지 설명해 주세요.

<details>
<summary>정답</summary>

#### static 을 사용하면 어떤 이점을 얻을 수 있나요? 어떤 제약이 걸릴까요?
- static 변수나 메서드는 클래스가 로드될 때 한 번만 메모리에 할당되어 모든 인스턴스가 동일한 데이터를 공유하게 되어 메모리 사용이 효율적
    - static 메서드는 클래스 로딩 시 메모리에 적재되기 때문에 메서드 호출 시 성능이 향상될 수 있음
- 여러 인스턴스 간에 공통으로 사용하는 데이터나 메서드의 경우, static을 사용하면 데이터 중복을 줄일 수 있음
- static 메서드와 변수는 클래스 이름으로 직접 접근할 수 있으며, 인스턴스를 생성하지 않고도 사용할 수 있음
    - 인스턴스화가 필요 없는 유틸리티 메서드나 상수 등을 정의할 수 있음

<br>

#### 컴파일 과정에서 static 이 어떻게 처리되는지 설명해 주세요.
- 바이트 코드로 변경된 클래스 파일을 클래스 로더가 탐색 중 static 키워드는 객체가 생성되지 않았어도 항상 먼저 메모리에 할당해야 함
- 자바 메모리 구조에서의 Method 영역으로 로드 되고 해당 영역에 할당되어 상주함
- static 키워드가 붙은 멤버들은 객체(인스턴스)에 소속된 멤버가 아니라 클래스에 소속된 멤버이기 때문에 클래스 변수 혹은 클래스 메서드라고도 부름

</details>


<br>

-------

## Reference
- https://gymdev.tistory.com/73#google_vignette
- https://velog.io/@blwasc/Java-중첩-클래스
- https://honbabzone.com/java/java-static/