# Java Annotation

### Java Annotation?
- 코드에 메타데이터를 제공하는 일종의 주석
- 컴파일러, JVM, 프레임워크 등이 보는 주석으로 소스코드에 메타 데이터를 삽입하는 형태
    - 코드에 대한 정보 추가 -> 소스 코드의 구조 변경, 환경 설정 정보 추가 등의 작업 진행

<br>

### 표준 어노테이션 (Standard Annotations)
- 자바 표준 라이브러리에 포함된 어노테이션
- @Deprecated
    - 컴파일러에게 해당 메서드가 deprecated 되었다고 알려줌 (오래된 메서드)
- @Override
    - 컴파일러에게 해당 메서드는 override한 메서드 임을 알려줌
    - @Override 가 선언된 경우 반드시 super class에 선언되어 있는 메서드여야 함
- @SuppressWarnings
    - 컴파일러에게 사소한 warning의 경우 신경 쓰지 말라고 알려줌 (경고 억제)

```java
@Override   // 재정의
public String toString() {
    return "Example";
}

@Deprecated // 오래된 메서드
public void oldMethod() {}

@SuppressWarnings("unchecked")  // 경고 억제
public void uncheckedOperation() {}
```

<br>

### 메타 어노테이션 (Meta Annotations)
- 다른 어노테이션을 정의할 때 사용되는 어노테이션
    - 클래스 레벨에서 클래스 대신 @interface으로 정의
- @Retention
    - 어노테이션의 유지 정책을 정의
- @Target
    - 어노테이션이 적용될 수 있는 요소를 정의
- @Inherited
    - 어노테이션이 서브클래스에 상속될 수 있음을 정의
- @Documented
    - 어노테이션이 Javadoc에 포함되도록 정의

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAnnotation {
    String value();
}

@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR})
public @interface MyAnnotation2 {
    String value();
}
```

<br>

### 사용자 정의 어노테이션 (Custom Annotations)
- 메타 어노테이션을 사용하여 직접 정의한 어노테이션
    - 해당 어노테이션은 실제로 코드에 적용되어 특정 동작을 정의하거나 메타데이터를 제공할 수 있음

```java
@MyAnnotation("Example value")
public void myMethod() {
    // 메서드 내용
}
```

<br>

### 어노테이션 유지 정책
- RetentionPolicy.SOURCE : 소스 코드에서만 유지되고 컴파일 시 제거됨
    - 컴파일러가 주로 사용하며 컴파일 결과물인 클래스 파일 안에는 포함되지 않음
    - 주로 컴파일러, IDE, 소스 분석 도구에서 사용
- RetentionPolicy.CLASS : 바이트 코드에 포함되지만, 런타임에는 사용할 수 없음
    - 컴파일 시 클래스 안에 포함되지만 리플렉션 시 정보를 얻을 수 없음 (생략 시 기본)
    - 주로 SOURCE의 사용처, ByteCode Level의 분석 도구에서 사용
- RetentionPolicy.RUNTIME : 런타임에 리플렉션을 통해 접근할 수 있음
    - 컴파일 시 포함되고 리플렉션 시 정보를 얻을 수 있음
    - 주로 CLASS의 사용처, 응용 어플리케이션, 프레임워크 및 라이브러리에서 사용

<br>

### 어노테이션의 적용 대상
- ElementType.TYPE : 클래스, 인터페이스, 열거형에 적용
- ElementType.FIELD : 필드에 적용
- ElementType.METHOD : 메서드에 적용
- ElementType.PARAMETER : 메서드 매개변수에 적용
- ElementType.CONSTRUCTOR : 생성자에 적용
- ElementType.LOCAL_VARIABLE : 지역 변수에 적용
- ElementType.ANNOTATION_TYPE: 애노테이션 타입에 적용
- ElementType.PACKAGE: 패키지에 적용

<br>

### 메타 어노테이션 설정을 위한 어노테이션
- @Document : JavaDoc을 만들 때 이 어노테이션이 문서에 표시되어야 함
- @Inherited : 어노테이션이 하위 클래스에 상속됨
- @Repeatable : 해당 어노테이션이 반복해서 적용될 수 있는지 표시

<br>

### 속성
- 추상 메서드처럼 쓰고 일반 속성 처럼 '키=값'으로 사용
    - 메서드 이름 -> 속성 명, 리턴 타입 -> 속성의 타입
- 설정하는 속성이 value 하나인 경우 속성(value) 생락 가능
- 배열은 {}를 쓰는데 길이가 1인 경우 {} 생략 가능
- 속성은 default 값을 가질 수 있으며 이 경우 속성을 설정 생략 가능

```java
// 키-값 형태로 배열 할당
@SuppressWarnings(value = {"unused", "rawtypes"})

// 배열이지만 값이 하나인 경우는 중괄호 생략 가능
@SuppressWarnings(value = "unused")

// 할당하려는 속성이 value 하나인 경우 키 생략 가능
@SuppressWarnings({"unused", "rawtypes"})

```

<br>

### 동작 원리
- 애노테이션 프로세서(Annotation Processor)는 컴파일러가 소스 코드를 컴파일할 때 애노테이션을 처리하고, 필요한 경우 추가적인 소스 코드나 메타데이터를 생성하는 역할을 함
1. 애노테이션 프로세서 수집: 컴파일러는 소스 코드에 있는 애노테이션과 애노테이션 프로세서를 수집
2. 컴파일 라운드(Round) 수행
    - Round 1: 애노테이션 프로세서가 소스 코드를 분석하여 애노테이션을 처리 (Oracle은 Processing의 반복 횟수를 Round로 명시)
    - 이 과정에서 소스 코드, 메타데이터, 또는 보일러플레이트 코드를 생성할 수 있음
    - 반복 검사: 컴파일러는 새로운 소스 파일이 생성되었는지 확인. 새로 생성된 소스 파일에 애노테이션이 포함되어 있을 수 있기 때문에, 이를 다시 처리해야 함
    - 반복 수행: 새로 생성된 소스 파일이 있으면, 컴파일러는 다시 애노테이션 프로세서를 호출하여 다음 라운드를 수행
3. 종료 조건
    - 새로운 소스 파일이 더 이상 생성되지 않으면, 컴파일 과정이 종료

<br>

### 반복 이유
- 중첩된 애노테이션 처리
    - 첫 번째 라운드에서 생성된 새로운 소스 파일이나 보일러플레이트 코드에 또 다른 애노테이션이 있을 수 있음. 이를 처리하기 위해 추가적인 라운드가 필요
- 종속성 처리
    - 하나의 애노테이션 프로세서가 생성한 코드를 다른 애노테이션 프로세서가 처리해야 할 경우가 있음. 이 경우 각 라운드마다 필요한 모든 코드를 처리하여 최종적으로 모든 애노테이션이 올바르게 처리함

<br>

### 사용 예시 

```java
@Documented
@Retention(RUNTIME) // 유지 정책은 런 타임시 
@Target(FIELD)  // 적용 대상은 필드
public @interface ValidCheck {
	Type value();
	
	enum Type {     // 가능한 타입은 NAME, EMAIL, PASS
		NAME,
		EMAIL,
		PASS,
	}
}

public class User {
	@ValidCheck(Type.NAME)
	private String name;
	
	@ValidCheck(Type.EMAIL)
	private String email;
	
	@ValidCheck(Type.PASS)
	private String pass;

	public User(String name, String email, String pass) {
		...
	}
}

public class ValidChecker {
    public static void errors(Object obj) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();  // 주어진 객체의 모든 필드를 검사  
		for(Field f : fields) {
			f.setAccessible(true);	// private field에 접근 설정
			if(f.isAnnotationPresent(ValidCheck.class)) {   // 해당 애노테이션이 적용되어 있는지 확인, 없다면 null 반환
				ValidCheck annotation = f.getAnnotation(ValidCheck.class);  // 어노테이션 클래스 가져오기
				ValidCheck.Type type = annotation.value();  // 어노테이션의 속성 값을 가져옴
				Object fieldValue = f.get(obj);	//  리플렉션을 사용하여 객체의 필드 값을 읽어오는 메서드
            ...
            }
        ...
        }
    ...
    }
}
```

<br>

### Reference
- https://developer-youn.tistory.com/122