# Call by Value, Call by Reference

### Call by Value ( 값에 의한 호출 )
- 함수가 호출될 때 메모리 공간안에서는 임시의 공간 생성. 함수가 종료되면 해당 공간은 사라지게 됨
- 함수 호출 시 전달되는 변수의 값을 복사하여 함수의 인자로 전달
- 복사된 인자는 함수 안에서 지역적으로 사용하는 변수로 사용됨. 함수 내부에서 인자 값이 바뀌어도 외부 값은 바뀌지 않음
- 원래 값에 영향을 받지 않아 안전한 장점이 있는 반면 메모리 양이 늘어나는 단점 존재
- JAVA에서 사용되는 호출 방식
- JAVA의 경우 함수에 전달되는 인자의 데이터 타입에 따라서 ( 기본 자료형 / 참조 자료형 ) 함수 호출 방식이 달라짐
        - 기본 자료형 : call by value로 동작 ( int, short, long, float, double, boolean )
        - 참조 자료형 : call by reference로 동작 ( Array, Class Instance )

<br>

### Call by Rerference ( 참조에 의한 호출 )
- 함수가 호출될 때 메모리 공간 안에서는 함수를 위한 별도의 임시 공간이 생성 ( 함수 종료시 사라짐 )
- call by reference 참조에 의한 호출방식은 함수 호출 시 인자로 전달되는 변수의 레퍼런스를 전달 ( 해당 변수를 가르킴, 값을 복사하는 것이 아닌 해당 위치를 참조 )
- 복사하지 않고 직접 참조를 하므로 빠른 장점이 있는 반면 직접 참조를 하기 때문에 원래 값이 영향을 받는 단점 존재
- 함수 안에서 인자의 값이 변경되면 함수 호출 시에 있던 변수들도 값이 바뀜

<br>

### 자바는 Call by Reference도 가능하다?
>- 일단 Call by Value와 Call by Reference은 자바의 개념이 아니라 프로그래밍 언어들의 매개변수 호출 방식. 
>- 우리가 자바를 사용하다보면 메소드를 이용하여 객체를 전달하고 본래 클래스의 변수에 영향을 끼치는데 이건 Call by Reference 가 아니라고 함. 
>- 자바 기본적으로 모두 Call by Value 호출 방식을 사용하여 값을 넘겨받은 메소드에서 값을 복사하여 새로운 지역 변수에 저장.
>- 그런데 우리가 객체를 다룰 때는 실제로 참조라고 하는 객체 핸들을 다루게 되는데 이 객체 핸들도 값으로 전달된다고 한다.
>- 즉 자바는 객체를 메소드로 넘길 때 객체를 참조하는 지역변수의 실제주소를 넘기는 것이 아니라 그 지역변수가 가르키고 있는 힙 영역의 객체를 가르키는 새로운 지역변수를
생성하여 그 것을 통하여 같은 객체를 가르키도록 하는 방식이라고 한다.
>- 그렇기 때문에 자바는 Call by Value 로 모든 메소드의 호출을 수행이 가능하다.

<br>

#### 예시 코드 


```java
public class Test {
	
	public static void main(String[] args) {
		NumberClass num1 = new NumberClass(10);
		NumberClass num2 = new NumberClass(20);
		
		swap(num1, num2);
	}
	
	// 자리 바꾸기 메소드
	static void swap(NumberClass num1, NumberClass num2) {
		int temp;
		temp = num1.num;
		num1.num = num2.num;
		num2.num = temp;
	}
}

class NumberClass {
	int num;
	NumberClass (int num) {
		this.num = num;
	}
}
```

<br>

### 예시코드의 방식

![Call by Value(Object Transfer)](./img/call%20by%20value-objectTransfer.png)


<br>

<div style="text-align: right">22-07-04</div>

-------

## Reference
- https://velog.io/@ahnick/Java-Call-by-Value-Call-by-Reference
- https://kimmayer.tistory.com/entry/Java%EC%9D%98-Call-by-value-Call-by-reference
- https://hyoje420.tistory.com/6