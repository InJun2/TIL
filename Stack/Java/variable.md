# Variable
- 변수(Variable)는 프로그래밍에서 데이터를 저장하고 참조할 수 있는 이름이 붙은 메모리 공간
- 변수는 프로그램 내에서 데이터를 임시로 저장하거나 처리할 때 사용되며, 각 변수는 자료형(Data Type)에 따라 특정한 유형의 데이터를 저장할 수 있음

<br>

### 변수의 특징
- 이름
    - 변수는 이름을 가지고 있어야 하며 이 이름을 통해 저장된 데이터를 참조할 수 있음
- 자료형
    - 변수는 특정한 자료형을 가져야 함
    - 자료형은 변수가 어떤 종류의 데이터를 저장할 수 있는지 결정하며 정수, 실수, 문자, 논리값 등 다양한 데이터 유형이 있음
- 메모리 위치
    - 변수는 프로그램 실행 중 데이터를 저장하기 위해 메모리 공간을 할당 받음
    - 이 메모리 공간은 변수의 자료형과 크기에 따라 달라질 수 있음
- 생성과 소멸
    - 변수는 선언되고 초기화된 후 사용됨
    - 일반적으로 변수는 선언 시점에 메모리 공간이 할당되고 변수의 범위를 벗어나면 해당 변수는 소멸됨

<br>

### Instance Variable
- 선언 위치 : 클래스 {} 블록 영역에 선언
- 변수 생성 : 객체가 만들어 질 때 heap에 객체 별로 생성됨
- 변수 접근 : 객체 생성 후(메모리에 올린 후) 객체 이름(소속)으로 접근
    - 객체를 만들 때마다 객체 별로 생성
    - 객체마다 고유한 상태(변수 값)유지
- 소멸 시점 : Garbage Collector에 의해 객체가 없어질 때
    - 프로그래머가 명시적으로 소멸시킬 수 없음

```java
public class Person {
    // Instance variable
    String scientificName;

    public static void main(String[] args) {
        // Creating an object of Person
        Person p = new Person();
        
        // Accessing instance variable using object reference
        p.scientificName = "Homo sapiens";
        
        // Instance variable maintains state unique to each object
        System.out.println("Scientific name of person: " + p.scientificName);
    }
}
```

<br>

### Class Variable
- 선언 위치 : 클래스 {} 블록 영역에 선언되며 static 키워드를 붙임
- 변수 생성 : 클래스 로더에 의해 클래스가 로딩될 때 heap에 클래스 별로 생성
    - 개별 객체의 생성과 무관하며 모든 객체가 공유하게 됨 (공유변수라고도 함)
- 변수 초기화 : 타입 별로 default 초기화
- 변수 접근 : 객체 생성과 무관하게 클래스 이름으로 접근
    - 객체를 생성하고 객체 이름으로 접근도 가능하나 static에 부합한 표현은 아님
- 소멸 시점 : 클래스가 언로드 될 때 Garbage Collector 발생

```java
public class Person {
    // Class variable
    static int count;

    public static void main(String[] args) {
        // Accessing class variable using class name
        Person.count = 10;
        
        // Class variable is shared among all instances
        System.out.println("Number of persons: " + Person.count);
    }
}
```

<br>

### Local Variable & Parameter Variable
- 지역 변수는 메서드나 블록 내에 선언되어 그 범위 내에서만 사용할 수 있는 변수
- 매개 변수는 메서드나 생성자 등에 전달되는 값을 저장하기 위해 사용되는 변수
- 선언 위치 : 클래스 영역의 {} 블록 이외에 모든 중갈호 안에 선언되는 변수들
- 변수 생성 : 선언된 라인이 실행될 때
    - 생성 메모리 영역 : stack의 메서드 프레임 내부
- 변수 초기화 : 사용하기 전 명시적 초기화 필요
- 변수 접근 : 외부에서는 접근이 불가능하므로 객체이름 불필요
    - 내부에서는 이름에 바로 접근
- 소멸 시점 : 선언된 영역 {} 블록을 벗어날 때

```java
public class Calculation {
    public void multiply(int x, int y) {
        // Local variable within method multiply
        int result = x * y;
        System.out.println("Multiplication result: " + result);
    }

    public static void main(String[] args) {
        Calculation calc = new Calculation();
        int a = 5;
        int b = 3;
        
        // Calling method with local variables a and b as parameters
        calc.multiply(a, b);
    }
}
```