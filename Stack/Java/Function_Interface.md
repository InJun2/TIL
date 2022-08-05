# 함수형 인터페이스 ( Function\<T,R\> )

### 함수형 인터페이스
- 추상 메서드가 오직 하나인 인터페이스
- 추상 메서드가 하나라는 뜻은 default method 또는 static method는 여러개 존재해도 상관 없음
- @FunctionalInterface 어노테이션을 사용. 이 어노테이션은 해당 인터페이스가 함수형 인터페이스 조건에 맞는지 검사
- 여기서 \<T,R\>은 제네릭 메소드. 제네릭 메소드는 매개타입과 리턴타입으로 타입파라미터를 갖는 메소드. 데이터형식에 의존하지 않고 하나의 값이 여러 다른 데이터 타입을 가질 수 있게한 방법 (외부에서 사용자가 타입 지정)


### 1. Supplier\<T\> interface
- Supplier\<T\> 인터페이스는 함수형 인터페이스
- 람다식으로는 () -> T 로 표현. 아무것도 받지 않고 특정 객체를 리턴
- Supplier\<T\>는 특이하게 매개변수를 받지 않고 단순히 무엇인가를 반환하는 추상메서드 T get(); 존재
- Supplier\<T\>는 get()을 통해 게으른 연산(Lazy Evaluation)이 가능. 불필요한 연산을 피하기위해 연산을 지연시키는 것을 말함
- Supplier\<T\>의 내부 코드는 다음과 같음
```java
@FunctionalInterface
public interface Supplier<T>{
    /**
        * Gets a result.
        *
        * @return a result
    */
    T get();    // 추상 메서드 존재. 단순히 무엇인가를 반환 함
}
```

<br>

#### Supplier\<T\> 간단히 사용
- Supplier\<T\>는 매개변수를 받지 않고 get을 이용해 값을 리턴해줌
```java
import java.util.function.Supplier;

public class SupplierExample {

	public static void main(String[] args) {
		Supplier<String> helloSupplier = () -> "Hello ";
		
		System.out.println(helloSupplier.get() + "World");
	}
}
```

<br>

### 2. Predicate
### 3. Consumer
### 4. Function\<T,R\>
### 5. Comparator
### 6. Runnable
### 7. Callable

<br>

<div style="text-align: right">22-08-05</div>

-------

## Reference
- https://m.blog.naver.com/zzang9ha/222087025042
- https://bcp0109.tistory.com/313