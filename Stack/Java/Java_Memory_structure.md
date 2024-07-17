# Java Memory Structure

![JVM Structure](./img/JVM.png)
### JVM ( Java Virtual Machine )
- 우선 자바의 메모리 구조는 JVM 안의 메모리 영역임 ( Runtime Data Area )
- 자바 프로그램은 프로그램과 운영체제 사이에 JVM(Java Virtual Machine)을 실행하여 어떤 운영체제에서도 동일한 결과를 갖게 한다
- 한단계 더 거치기 때문에 실행 속도 면에서 뒤쳐질수도 있음
- JVM의 구조는 위의 사진과 같음. JVM이 실행된 후 어떤 운영체제이든지 실행이 가능 
> JVM 구조
>- Source Code(.java) 파일을 Java Compiler를 통해서 Byte Code(.class)파일로 변환 (컴퓨터가 이해할 수 있는 코드로 변환)
>- Byte Code로 변환된 파일을 JVM의 Class Loader로 보냄
>- Class Loader는 Class 파일을 불러와서 메모리에 저장하는 역할
>- Execution Engine는 Class Loader에 저장된 Byte Code를 명령어 단위로 분류하여 하나씩 실행하게 하는 엔진
>- Garbage Collector는 사용하지 않거나 필요없는 객체들을 메모리에서 소멸시키는 역할
>- Runtime Data Area(Memory Area)는 JVM이 프로그램을 수행하기위해 운영체제로부터 할당받은 메모리 공간

<br>

![Java Memory Area](./img/java_memory_area.png)

### Memory Area 구조
#### 1. Method Area (MetaSpace)
- 모든 스레드가 공유하는 메모리 영역
- 클래스 로더에 의해 로드된 모든 클래스 정보를 저장
    - 클래스 명, 인터페이스 등 클래스 정보
    - 메서드 시그니처 정보
    - 필드 정보
    - 상수 풀
    - 정적 변수 (const)
    - 메서드의 JIT(Just-In-Time) 컴파일 코드 등
    - JVM이 시작될 때 할당되며, JVM이 종료될 떄 까지 유지
- JVM이 실행되면서 생기는 공간
- Class 정보, 전역변수, Static 변수 정보가 저장되는 공간
- Runtime Constant Pool 에는 말 그대로 '상수' 정보가 저장되는 공간
#### 2. Heap
- new 연산자로 생성된 객체, Array와 같은 동적으로 생성된 데이터가 저장되는 공간
- Heap에 저장된 데이터는 Garbage Collector가 처리하지 않는한 소멸되지 않음
- Reference Type의 데이터가 저장되는 공간
- 모든 스레드가 공유하는 메모리 영역
#### 3. Stack
- 지역 변수, 메소드의 매개변수와 같이 잠시 사용되고 필요가 없어지는 데이터가 저장되는 공간
- Last In First Out, 나중에 들어온 데이터가 먼저 나감
- 만약 지역변수이지만 Reference Type일 경우에는 Heap에 저장된 데이터 주소값을 Stack에 저장해서 사용하게 됨
- 스레드마다 하나씩 존재
#### 4. PC Register
- 스레드가 생성되면서 생기는 공간으로 스레드마다 개별 존재
- JVM이 현재 실행 중인 JVM 명령의 주소(현재 위치)를 저장하는 역할
    - 스레드가 어느 명령어를 처리하고 있는지 그 주소를 등록
- Context Switching 시 중요한 역할을 수행
#### 5. Native Method Stack
- Java가 아닌 다른 언어(C, C++)로 구성된 메소드를 실행이 필요할 때 사용되는 공간
    - JNI를 통해 호출된 메서드의 실행에 사용되는 메모리를 저장


<br>

### Stack-Heap ?

<br>

<p>
<img src="./img/java_stack1.png" width="200" height="400">
<img src="./img/java_stack2.png" width="200" height="400">
<img src="./img/java_stack3.png" width="200" height="400">
<img src="./img/java_stack4.png" width="200" height="400">
</p>

#### Stack
- Heap 영역에 생성된 Object 타입의 데이터 참조값이 할당됨
- 원시타입의 데이터가 값과 함께 할당됨
- Thread마다 개별 존재하여 자신만의 Stack을 가짐
- 로컬 변수 , 중간연산 , 스택 프레임 이 저장됨.
- 지역변수들은 scope에 따른 visibility를 가짐

>```java
>public class Main {
>    public static void main(String[] args) {
>        int argument = 4;
>        argument = someOperation(argument);
>    }
>
>    private static int someOperation(int param){
>        int tmp = param * 3;
>        int result = tmp / 2;
>        return result;
>    }
>}
>```

<br>
<br>
<br>

<img src="./img/java_heap1.png" width="800" height="400">
<img src="./img/java_heap2.png" width="800" height="400">
<img src="./img/java_heap3.png" width="800" height="400">

#### Heap
- 모든 스레드가 공유하는 영역
- Heap 영역에는 주로 긴 생명주기를 가지는 데이터들이 저장됨
    - 동적으로 생성된 객체와 배열을 저장
    - 대부분의 오브젝트는 크기가 크고 서로 다른 코드블럭에서 공유되는 경유가 많음
- 어플리케이션의 모든 메모리 중 stack에 있는 데이터를 제외한 부분이라고 보면 된다고 함
- 모든 Obejct 객체의 실제 데이터는 Heap에 이에 대한 참조자(변수)은 Stack에 저장
- 몇개의 스레드가 존재하든 상관없이 단 하나의 heap 영역만 존재
- Heap 영역에 있는 오브젝트들을 가리키는 레퍼런스 변수가 stack에 올라가게 됨
- 메모리가 가비지 컬렉션에 의해 관리됨
- String Pool
    - Heap 메모리 중 특별한 메모리 영역으로, 문자열 리터럴을 재사용하여 메모리 효율성을 높이는 데 사용
    - intern() 메소드로 문자열을 String Pool에 등록할 수 있으나, 권장되지 않음(성능)
- 가비지 컬렉션의 대상.
    - Young Generation
    - Old Generation

<br>

### 메서드 호출 스택
- 각각의 메서드 호출 시 마다 메서드 동작을 위한 메모리 상자를 하나씩 할당
    - 상자 내부에 메서드를 위한 파라미터 변수 등 로컬 변수 구성
- A 메서드에서 새로운 메서드 B 호출 시 B 실행을 위한 메모리 상자를 쌓음
    - 언제나 맨 위에 있는 메모리 상자(B)만 활성화
    - 이때 A 메서드 동작이 끝나지 않고 잠시 정지된 상태
    - B가 리턴하게 되면 B를 위한 상자가 제거되며 메모리 반납
    - 다시 A가 최상위가 되어 다시 동작 재개

<br>

### static 변수
- JVM은 Java 프로그램을 실행하기 위해 클래스를 로드하는데 클래스가 로드될 때 해당 클래스의 코드, 상수, static 변수 등은 모두 메모리에 로딩됨
    - 메모리에 로딩될 때 static 변수는 메서드 영역에 할당됨
- static 변수는 이 과정에서 클래스가 처음 로드될 때 메모리에 할당되며 프로그램의 실행 동안 변하지 않는 데이터를 저장하는 용도로 사용됨
    - 이러한 특성에 있어 지나친 static 변수 사용은 메모리 사용에 있어 필요 이상으로 긴 생명주기를 가져 메모리 낭비를 초래할 수 있음
- static 변수는 클래스의 모든 인스턴스가 공휴나느 데이터로 static 변수는 객체가 생성되기 전에도 접근할 수 있으며 모든 객체가 동일한 값을 참조함

<br>

<div style="text-align: right">22-07-10</div>
<div style="text-align: right">+ 내용 추가 24-07-16</div>

-------

## Reference
- https://velog.io/@shin_stealer/자바의-메모리-구조
- https://steady-coding.tistory.com/305
- https://yaboong.github.io/java/2018/05/26/java-memory-management/