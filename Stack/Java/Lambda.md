#  Lambda

### Lambda?
- 람다식은 익명함수(anonymous function)로 구동되고 자바 8에 도입되었고 함수형 인터페이스 객체를 표현함. 람다는 로직이자 하나의 값
- 람다식은 추상 메소드를 구현하는데 이름이 없기 때문에 익명함수와 비슷 -> 람다식은 익명함수(클래스, 객체)를 사용하는 것보다 더 적은 코드로 동일한 내용을 구현 가능.
    - 즉, 람다식은 지정된 하나의 추상 메서드의 구현부를 구현하는 방법이다
    - 람다식이 할당되는 인터페이스를 Lambda 식의 타겟 타입이라 하고 타겟 타입은 abstract 메서드가 반드시 하나만 존재해야 함 (dafault, static 은 무관)
    - 또한 모든 인터페이스는 Object 클래스의 메서드를 암묵적으로 포함하기 때문에, Object 클래스의 메서드를 추상 메서드의 개수에 포함시키지 않음
    - @FunctionalInterface : 컴파일러가 하나의 abstract method 만 있음을 체크 -> 안정적인 프로그래밍을 위한 옵션으로 그렇지 않으면 오류 발생
    - abstract method가 2개 이상 존재하는 경우는 여전히 익명의 inner class 사용
- 람다는 일급함수로 매개변수, 리턴값, 자료구조의 일부가 될 수 있음
- 람다는 함수형 인터페이스의 구현체며 람다가 있어야할 곳이 함수형 인터페이스. 함수형 인터페이스는 제네릭 <T\>를 타입 파라미터로 가짐 -> 함수형 인터페이스로 인해 자바에서 의미하는 기본형의 데이터 많이 아니라 행위(로직)도 값으로 취급할 수 있게 됨
- 람다식은 함수형 프로그래밍이기 때문에 게으른 평가(Lazy Evaluation) 진행하여 실행 즉시 값을 평가하는 것이 아니라 필요한 시점에 평가함.
- 람다식은 병렬처리, 이벤트 처리 등 함수적 프로그래밍에서 유용하게 쓰임 -> 구현을 하기 위한 정의가 필요하지 않음
- 람다식을 쓰는 이유는 기호 및 약속된 표현을 쓰기 때문에 코드가 간결해지고, 필터링 및 매핑 시 집계결과를 쉽게 가져올 수 있음
- 람다식을 실행하면 익명구현객체가 생성됨 -> 람다식은 함수적 인터페이스에서 사용함
```
순수함수
- 동일한 입력에 대해 항상 동일한 출력을 반환하는 함수
- 외부의 상태를 변경하거나 영향을 받지 않는 함수

일반함수
- 일반적으로 사용하는 함수로 일반적인 형태를 띰
- 함수 선언식

익명함수
- 함수 리터럴 방식으로 만들어진 이름없는 함수 -> 바인딩 할 필요없이 사용. 이름이 필요하지 않음
- 함수를 재사용하지 않을 경우 익명함수를 만들어서 사용

일급함수
- 함수를 다른 변수와 동일하게 다루는 언어는 일급 함수를 가짐
- 함수를 다른 함수에 인수로 제공하거나 함수가 함수를 반환할 수 있고 변수에도 할당할 수 있음
```

<br>

#### Lambda Expression의 장단점
- 장점
    - 코드를 간결하게 만들 수 있음
    - 식에 개발자의 의도가 명확히 드러나 가독성이 높아짐
    - 함수를 만드는 과정없이 한번에 처리할 수 있어 생산성이 높아짐
    - 병렬프로그래밍이 용이
- 단점
    - 람다를 사용하면서 만든 무명함수는 재사용이 불가능
    - 디버깅이 어려움
    - 람다를 남발하면 비슷한 함수가 중복 생성되어 코드가 지저분해질 수 있음
    - 재귀로 만들경우 부적합


```java
// 명령형 프로그래밍
public int sumNumbers(List<Integer> list){
    int sum = 0;
    for(int i=0; i<list.size(); i++){
        if(list.get(i)>0){
            sum += list.get(i);
        }
    }
    return result;
}

// 선언형 프로그래밍 -> 함수형 프로그래밍을 하기 위해 Stream, 람다, 함수형 인터페이스를 지원
public int sumNumbers(List<Integer> list){
    return list.stream()
            .filter(()->num>0)
            .sum()
}
```
<br>

### 람다 - 함수 인터페이스
- Java는 기본적으로 객체지향 언어이기 때문에 순수함수와 일반 함수를 다르게 취급하고 있는데 Java에서 이를 구분하기 위해 함수형 인터페이스 등장
- 

```java
// 함수 인터페이스를 상속받아 실행
public static void main(String[] args) {
    Comparator<Integer> comp = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2); // o1 은 Integer 이고, Integer 은 Comparable 을 구현하고,
        }				 // Comparable 에 compareTo 메서드가 있다.
    };

    System.out.println(comp.compare(2, 1));
}

// 위를 람다식으로 변환
public static void main(String[] args) {
Comparator<Integer> comp = (o1, o2) -> (o1.compareTo(o2));

System.out.println(comp.compare(2, 1));
}
```

<br>

### Lambda 작성
- 기본 람다식은 @FunctionalInterface에 대한 구현체 작성법은 다음과 같음
    - (type variable_name[...] -> {실행문;});
- 매개변수의 유무와 리턴값의 유무로 작성 가능
    - 매개변수 부분은 실행문 블록에서 사용하기 위한 값 제공 (일반적인 변수 선언)
    - 매개변수의 타입은 런타임 시 대입되는 값에 따라 자동 인식되므로 일반적으로 생략함
    - 매개변수 및 실행문이 하나일 때는 변수 및 실행문의 괄호, ; 생략가능
- 기본 형태는 다음과 같음
```
A a = (매개값) -> {구현코드};

표현방법
파라미터가 없으면 () 필수
() -> expression

파라미터가 하나일 경우 && 리턴타입이 한줄일 경우 {} 생략 가능
(parameter) -> expression

파라미터가 하나일 경우 () 제거 가능
parameter -> expression

파라미터가 2개일 경우 () 필수
(parameter1, parameter2) -> expression

파라미터가 2개이고, 리턴하기 위한 코드가 한줄이 아닐 경우 {}를 사용
(parameter1, parameter2) -> { code block }

예시
int result = doSomething((n1, n2) -> {
    int res1 = n1 * 10;
    int res2 = n2 * 10;
    return res1 + res2;
});
```

- 매개변수가 없고, 리턴값이 없는 람다식
```java
@FunctionalInterface
public interface JavaCoding {
    void nowCoding();
}

 public static void main(String[] args) {
        //객체 선언
        JavaCoding jc;
        // {} 실행코드가 1줄인경우 {} 생략가능
        jc = () -> System.out.println("Rollin Rollin Rollin Rollin");
        jc.nowCoding();
 }
```
- 매개변수가 있고, 리턴값이 없는 람다식
```java
@FunctionalInterface
public interface JavaCoding {
    void nowCoding(String str);
}

public static void main(String[] args) {
    //객체 선언
    JavaCoding jc;
    String str;

    //람다식 바디{} 생략, 매개변수가 1개인 경우 () 생략할 수 있음
    jc = (a) -> System.out.println(a+ "Rollin Rollin");
    str= "Rollin Rollin ";
    jc.nowCoding(str);
}
```
- 매개변수가 없고, 리턴값이 있는 람다식
```java
@FunctionalInterface
public interface JavaCoding {
    String nowCoding();
}

 public static void main(String[] args) {
    //객체 선언
    JavaCoding jc;
    String str1 = "Rollin Rollin ";
    String str2 = "Rollin Rollin";

    jc = () -> {
        return str1;
    };
    System.out.println(jc.nowCoding());

    jc = () -> { return str2; };
    System.out.println(jc.nowCoding());
 }
```
- 매개변수가 있고, 리턴값이 있는 람다식
```java
@FunctionalInterface
public interface JavaCoding {
    String nowCoding(String s);
}

 public static void main(String[] args) {
    //객체 선언
    JavaCoding jc;
    str = "Rollin Rollin";

    jc = s -> s+ str;
    System.out.println(jc.nowCoding("Rollin Rollin"));
}

- 인터페이스 정의 및 사용 방식
@FunctionalInterface
interface MyFunctional {
    double numTo(int num);
}

private static void useFunction(MyFunctional function, int num) {
    System.out.println(function.numTo(num));
}

public static void main(String[] args) {
    useFunction(num -> Math.pow(num, 2), 20);
}
```

<br>

### 람다 실행 블록에서의 변수 참조
- 람다 식 내부에서의 this는 외부 클래스의 instance
- 외부 클래스의 member 변수 : 접근제한자의 제약 없이 사용 가능
- 외부 클래스의 local 변수 : final 키워드가 추가된 것으로 동작 -> read only

```java
@FunctionalInterface
interface MyFunctionalInterface {
    void useNumber(int num);
}

public class LambdaVariableTest {
    private int instanceMember = 10;

    public void useFIMethod(MyFunctionalInterface fi) {
        fi.useNumber(1);
    }

    public void lambdaMethodTest() {
        int localVar = 20;

        useFIMethod(msg -> {
            // this는 외부 클래스의 instance
            System.out.printf("this : %s\n", this.getClass().getName());
            System.out.printf("멤버 변수 참조 : %d\n", this.instanceMember ++);
            System.out.printf("로컬 변수참조 : %d\n", localVar);    // 읽기 전용
            System.out.printf(msg++);
            // 해당 경우 람다식을 통해 람다식 외부 변수 값을 오염 시킬 수 있어 final의 변수에만 접근이 가능. (람다식 내부에서는 effectively final(실질적으로 final)인 변수만 접근할 수 있음)
        })
    }
}
```

<br>

#### 메소드 레퍼런스 ::
- 람다식을 더 간단하게 표현하는 방법으로 다른 함수 하나만을 실행하는 경우 :: 연산자를 이용해 기존 메서드 참조
- ClassName::MethodName 형식으로 사용. 메소드를 호출하지만 괄호는 써주지 않고 생략
- <소유자>::<파라미터를 사용하는 소유자의 메서드> 형식으로 사용함
- 파라미터의 인스턴스 메서드 참조
    - 객체::인스턴스 메서드
```java
List<String> names = new ArrayList<>();

// names.forEach(item -> names.add(item));
names.forEach(names::add);
```
- 정적 메서드 참조
    - 클래스::정적 메서드
```java
Integer nums = new Integer[]{1,2,3,4,5};

// Array.sort(nums, (num1, num2) -> Integer.compare(num1, num2)); // static method
Array.sort(nums, Integer::compare);
```
- 생성자 참조
    - 클래스명::new
```java
// Supplier<StringBuilder> s1 = new StringBuilder();
Supplier<StringBuilder> s1 = StringBuilder::new;
```

- 메소드 레퍼런스는 사용 패턴에 따라 분류
    - Static 메소드 레퍼런스
    - Instance 메소드 레퍼런스
    - Constructor 메소드 레퍼런스

<br>


<div style="text-align: right">22-08-13</div>

-------

## Reference
- https://makecodework.tistory.com/entry/Java-람다식Lambda-익히기
- https://codechacha.com/ko/java-lambda-expression/
- https://alkhwa-113.tistory.com/entry/람다식feat-익명-구현-클래스-vs-람다식
- https://codechacha.com/ko/java8-method-reference/