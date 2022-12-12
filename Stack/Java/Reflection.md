# Reflection

### 리플렉션(Reflection)
- 구체적인 클래스 타입을 알지 못해도 그 클래스의 메소드, 타입, 변수들에 접근할 수 있도록 해주는 자바 API
- 동적으로 객체를 생성하고 그 메서드를 호출하는 방법
- 자바의 리플렉션은 클래스, 인터페이스, 메소드들을 찾을 수 있고, 객체를 생성하거나 변수를 변경하거나 메소드를 호출할 수 있음

<br>

### 사용 경우
- 코드를 작성할 시점에는 어떤 타입의 클래스를 사용할지 모르지만 런타임 시점에 지금 실행되고 있는 클래스를 가져와서 실행해야 하는 경우. 여기서 중요한 부분은 런타임 시점
- 변수의 값을 조건에 다르게 사용해야하는 경우라던가 어플리케이션이 실행되고 나서 생성되는 클래스의 경우 리플렉션을 사용
- 프레임워크나 IDE에서 이런 동적인 바인딩을 이용한 기능을 제공함. 아래가 리플렉션을 이용한 대표적인 예.
    - intellij의 자동완성 기능
    - 스프링의 어노테이션. 스프링에서는 런타임 시에 개발자가 등록한 빈을 애플리케이션을 가져와 사용할 수 있게함.
    - ORM인 하이버네이트
    - jackson 라이브러리

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
- 변수 doubleArray는 double형 배열의 클래스를 로드한 것과 같고, 변수 stringArray는 2차원 문자열 배열의 클래스를 로드한 것과 같음

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

<br>

<div style="text-align: right">22-12-12</div>

-------

## Reference
- https://velog.io/@yeon/Reflection이란
- https://medium.com/msolo021015/자바-reflection이란-ee71caf7eec5
- https://docs.oracle.com/javase/tutorial/reflect/