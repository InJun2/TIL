# Reflection

### 리플렉션(Reflection)
- 구체적인 클래스 타입을 알지 못해도 그 클래스의 메소드, 타입, 변수들에 접근할 수 있도록 해주는 자바 API
- JVM은 클래스 정보를 클래스 로더를 통해 읽어와서 해당 정보를 JVM 메모리에 저장한다. 그렇게 저장된 클래스에 대한 정보가 마치 거울에 투영된 모습과 닮아있어, 리플렉션이라는 이름을 가지게 되었음
- 동적으로 객체를 생성하고 그 메서드를 호출하는 방법
    - 자바에서 클래스는 JVM의 Method Area에 로드되며 리플렉션은 런타임 시에 해당 정보를 가져옴
    - Method Area : 자바 프로그램이 실행되는 동안 클래스와 관련된 메타데이터를 저장하는 메모리 영역
    - 즉, 리플렉션은 런타임시 해당 메타데이터를 가져오는 것이며, 클래스가 메모리에 로드된 이후 사용 가능함
- 자바의 리플렉션은 클래스, 인터페이스, 메소드들을 찾을 수 있고, 객체를 생성하거나 변수를 변경하거나 메소드를 호출할 수 있음
    - 리플렉션 덕분에 우리가 스프링에서 @Component , @Bean 과 같은 어노테이션을 프레임워크의 기능을 사용하기 위해 사용할 수 있음

<br>

### 사용 경우
- 코드를 작성할 시점에는 어떤 타입의 클래스를 사용할지 모르지만 런타임 시점에 지금 실행되고 있는 클래스를 가져와서 실행해야 하는 경우. 여기서 중요한 부분은 런타임 시점
- 변수의 값을 조건에 다르게 사용해야하는 경우라던가 어플리케이션이 실행되고 나서 생성되는 클래스의 경우 리플렉션을 사용
- 프레임워크나 IDE에서 이런 동적인 바인딩을 이용한 기능을 제공함. 아래가 리플렉션을 이용한 대표적인 예.
    - intellij의 자동완성 기능
    - 스프링의 어노테이션 (컴포넌트 스캔, DI, AOP). 스프링에서는 런타임 시에 개발자가 등록한 빈을 애플리케이션을 가져와 사용할 수 있게함.
    - ORM인 하이버네이트
    - jackson 라이브러리 Serialization/Deserialization

<br>

### 주로 가져올수 있는 정보
- Class
- Constructor
- Method
- Field

<br>

### 리플렉션의 특징
- 확장성 특징
    - 애플리케이션은 정규화된 이름을 사용하여 확장성 객체의 인스턴스를 생성하여 외부 사용자 정의 클래스를 사용할 수 있음
- 클래스 브라우저 및 시각적 개발 환경 제공
    - 클래스 브라우저는 클래스의 Method, Property, Constructor를 열거할 수 있어야 함
    - 시각적 개발 환경은 개발자가 올바른 코드를 작성하는데 도움이 되도록 Reflection에서 사용할 수 있는 형식 정보를 사용하는 것이 편리
- 디버거 및 테스트 도구
    - 디버거는 개인 Property, Method, Constructor를 검사할 수 있어야 함
    - 테스트 장치는 Reflection을 사용하여 클래스에 정의된 발견 가능한 세트 API를 체계적으로 호출하여 테스트에서 높은 수준의 코드 커버리지를 보장

<br>

### 리플렉션 사용시 주의사항
- Performance의 오버헤드 
    - Reflection에는 동적으로 해석되는 유형이 포함되므로, 특정 JVM 최적화를 수행할 수 없음
    - 따라서 Reflection 작업이 비 Reflection 작업보다 성능이 떨어지며, 성능에 민감한 애플리케이션에서 자주 호출되는 코드엔 사용하지 않아야 함
- 보안 제한 사항
    - Reflection에는 시큐리티 매니저의 실행 시에 존재하지 않는 실행 시 액세스 권한이 필요
    - 이것은 Applet과 같이 제한된 보안 컨텍스트에서 실행되어야 하는 코드에 대한 중요한 고려 사항임
        - Applet : 플러그인의 하나로 전용 위젯 엔진이나 더 큰 프로그램 범위 내에서 실행되는 특정한 작업을 수행하는 조그마한 응용 프로그램으로 독립적으로 사용되지 않으며 프로그램이 제공하는 컨테이너 안에서 실행되어야 하며 플러그인을 통해 작성되어야 함
- 캡슐화를 저해할 수 있음 (내부 노출)
    - Reflection은 private한 Property및 Method에 액세스하는 것과 같이 비 Reflection 코드에서 작동하지 않는 코드를 수행할 수 있음
    - 즉, Reflection을 사용하면 예기치 않은 부작용이 발생하여 코드 기능이 저하되고 이식성이 손상될 수 있음
    - 또한 추상화를 깨뜨려 플랫폼 업그레이드 시 동작이 변경될 수 있음

<br>

### 예제코드
- Object.getClass()를 통해 클래스의 정보를 로드함
- .class 문법을 사용함

```java
// 임의의 클래스를 가져오는 방법
Class c = "foo".getClass();
System.out.println(c);      //class java.lang.String

// Array는 객체이므로 Array 인스턴스에서 클래스 정보를 로드할 수 있습니다.
byte[] b = new byte[1024];
Class c1 = b.getClass();
System.out.println(c1);     //class [B

Set<String> s = new HashSet<>();
Class c2 = s.getClass();
System.out.println(c2);     //class java.util.HashSet
```

<br>

- boolean형 같은 경우 원시 유형이기 때문에 아래의 예시의 경우 컴파일 에러가 발생함

```java
//.class 문법
boolean bl;
Class c3 = bl.getClass();   //컴파일 에러 발생
Class c4 = boolean.class;
```

<br>

- PrintStream 및 다차원 배열 또한 .class 구문이 사용가능

```java
Class c5 = java.io.PrintStream.class;
System.out.println(c5);     //class java.io.PrintStream
Class c6 = int[][].class;
System.out.println(c6);     //class [[I
```

<br>

- Class.forNmae() 문법을 사용
- getClass()와 다른 점은 Object의 메서드로써 해당클래스가 객체를 생성했을 때만 사용가능하지만 forName()은 객체를 생성하기 전에 직접 클래스를 얻을 수 있음
- 클래스가 없다면 예외가 발생하기 때문에 사용하기 위해 ClassNotFoundException 예외를 추가해주어야 함

```java
//아래와 같이 패키지 명으로 클래스를 로드할 수 있습니다.
Class c7 = Class.forName("ko.maeng.reflection.ReflectionApplication");
Class doubleArray = Class.forName("[D");    //class [D
Class stringArray = Class.forName("[[Ljava.lang.String;");  //class [[Ljava.lang.String;
```

<br>

- TYPE Field를 통한 원시형 클래스 반환

```java
Class c8 = Double.TYPE;     //double
Class c9 = Void.TYPE;       //void
```

<br>

- Method를 활용한 클래스 반환 방법
    - class.getSuperClass() : 슈퍼 클래스를 반환합니다.
    - class.getClass() : 상속된 클래스를 포함하여 모든 공용 클래스, 인터페이스 및 열거형을 반환
    - class.getDeclaredClass() : 명시적으로 선언된 모든 클래스 및 인터페이스, 열거형을 반환
    - class.getDeclaringClass() : 클래스에 구성된 클래스(명시적으로 선언된)를 반환
    - class.getEnclosingClass() : 클래스의 즉시 동봉된 클래스를 반환


- 스프링 리플렉션 사용 예시
```java
public static Field findField(Class<?> clazz, String name, Class<?> type) {
	// Assert.notNull(clazz, "Class must not be null");
	// Assert.isTrue(name != null || type != null, "Either name or type of the field must be specified");
	
    Class<?> searchType = clazz;
	while (!Object.class.equals(searchType) && searchType != null) {
		Field[] fields = searchType.getDeclaredFields();
		for (Field field : fields) {
			if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
				return field;
			}
		}
		searchType = searchType.getSuperclass();
	}
	return null;
}
```

<br>

## Ssafy Wizards CS Study

### Reflection
- 의미만 들어보면 리플렉션은 보안적인 문제가 있을 가능성이 있어보이는데, 실제로 그렇게 생각하시나요? 만약 그렇다면, 어떻게 방지할 수 있을까요?
- 리플렉션을 언제 활용할 수 있을까요?

<details>
<summary>정답</summary>

#### 의미만 들어보면 리플렉션은 보안적인 문제가 있을 가능성이 있어보이는데, 실제로 그렇게 생각하시나요? 만약 그렇다면, 어떻게 방지할 수 있을까요?
- 자바의 기본적인 접근 제어를 위회하여 비공식적인 필드나 메서드에 접근하거나 'private'로 설정된 멤버를 수정할 수 있음
- 리플렉션은 런타임 시 클래스 정보를 가져오므로 컴파일 타임에 검증되지 않은 타입에 접근하여 런타임 오류가 발생할 수 있고, 형식 안전성을 보장할 수 없음
- 리플렉션을 사용하기 위해 보안 관리자로부터 요구하는 권한이 있음. 이러한 적절한 권한 설정하지 않으면 보안 문제 발생

<br>

#### 방지 방법
- 보안 관리자를 사용하여 권한을 제한하기
    - SecurityManager를 사용하여 리플렉션 작업에 대한 권한을 제한하여 리플렉션이 비공식적인 필드나 메서드에 접근하는 것을 제어
    - reflectAccess와 accessDeclaredMembers 권한을 적절히 설정하여 불필요한 접근을 차단
     - reflectAccess: 리플렉션을 통해 비공식적인 필드나 메서드에 접근하기 위해 필요한 권한. 이 권한이 없으면 SecurityException이 발생
    - accessDeclaredMembers: private 및 protected 필드와 메서드에 접근하기 위해 필요. 이 권한이 없으면 이러한 멤버에 대한 접근이 제한됨
- 최소 권한 원칙 적용
    -  최소 권한 원칙을 적용하여 필요한 권한만 부여. 불필요한 권한을 부여하지 않도록 주의
- 코드 검토와 테스트
    - 리플렉션을 사용하는 코드는 신중하게 검토하고 충분히 테스트가 필요. 런타임 오류를 방지하기 위해 리플렉션을 사용할 때의 안전성을 확보하는 것이 중요
- 리플렉션 사용의 지양
    - 리플렉션의 사용을 가능한 한 줄이고, 필요하지 않은 경우에는 사용을 지양
- 안전한 리플렉션 사용
    - 리플렉션을 사용할 때는 예외 처리를 적절히 하고, 리플렉션을 통해 접근할 수 있는 메서드나 필드의 존재 여부를 먼저 확인하기

```java
grant {
    permission java.lang.reflect.ReflectPermission "suppressAccessChecks";
};
```

<br>

#### 리플렉션을 언제 활용할 수 있을까요?
- 프레임워크 및 라이브러리 : 자바 프레임워크와 라이브러리에서는 리플렉션을 사용하여 객체의 메타데이터를 분석하고, 런타임에 동적으로 객체를 생성하거나 메서드를 호출
- 테스트 : 테스트 프레임워크에서는 리플렉션을 사용하여 테스트할 메서드와 필드를 동적으로 식별하고 접근
- 직렬화 및 역직렬화 : 객체를 직렬화하거나 역직렬화할 때 리플렉션을 사용하여 객체의 구조를 동적으로 분석함
- 동적 프록시 생성 : 자바의 동적 프록시 생성 기능을 활용하여, 런타임에 인터페이스의 구현을 생성할 수 있음

</details>

<br>

<div style="text-align: right">추가 정리 : 23-11-30</div>

-------

## Reference
- https://velog.io/@yeon/Reflection이란
- https://medium.com/msolo021015/자바-reflection이란-ee71caf7eec5
- https://docs.oracle.com/javase/tutorial/reflect/
- https://hudi.blog/java-reflection/