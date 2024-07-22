# Enum

### Enum 이란?
- 열거형 데이터 타입
- 데이터가 몇 가지 한정된 값(주로 상수들)만을 갖는 형태로 구성되는 경우
- java.lang.Enum을 내부적으로 상속 받은 형태의 특별한 클래스
    - class 키워드 대신 enum 사용
    - 마찬가지로 최상위 상속 객체는 Object
- Enum 상수 나열 이후 선언 끝에 ; 추가 필요
- 일반 클래스 처럼 Enum에도 멤버 변수, 메서드 추가 가능

<br>

### 필요성
- 값 비교시 단순히 값 뿐만 아니라 타입에 대해서도 체크가 가능하여 논리적 오류 방지
- 주로 비교 연산에서 사용하여 ==, equlas를 통해 enum 상수 값이 같은지 비교

<br>

### 생성자를 통한 enum 상수 초기화
- Enum의 멤버 변수를 생성자를 통해 초기화
- 생성자는 언제나 private 접근 제한자를 가지며 외부에서 접근 불가
- 내부에서 enum 상수 선언 시 값 할당

```java
public enum Grade {
	SALES(1),   // enum 상수에 값 1 할당
	PART_TIME_JOB(2),
	NORMAL(3),
	SPRING(1);
	
	private int value;  // 멤버 변수
	
	Grade(int value) {  // 생성자 초기화
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
```

<br>

### java.lang.Enum 주요 메서드
- public final String name()
    - enum 상수의 이름을 문자열 리턴
- public final int ordinal()
    - enum 상수의 순서로 0부터 시작함
- public final int compareTo()
    - enum 상수의 ordinal 차이 리턴
- public static T[] values()
    - Enum 타입에 선언된 enum 상수를 배열로 리턴
- public static T valueOf(String name)
    - 문자열에 매핑 된 enum 상수 객체 리턴

```java
enum Grade {
    SALES, ...

    // SALES -> name : SALES, ordinal : 0
}
```