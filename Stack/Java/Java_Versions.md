# Java Version

### Java Version 별 사용 시나리오
- 최신 Java 버전은 6개월마다 바뀌기 때문에 수많은 버전이 출시됨에 따라 다음과 같은 사용 시나리오 존재
- 기업의 기존 프로젝트에서는 Java 8을 사용해야 하는 경우가 많음
- 일부 레거시 프로젝트는 Java 1.5 또는 1.6에서 중단되기도 함
- 최신 IDE, 프레임워크 및 빌드 도구를 사용하고 그린 필드 프로젝트를 시작하는 경우 Java 11(LTS) 또는 최신 Java 17 LTS를 망설임 없이 사용할 수 있음
- 안드로이드 개발의 특별한 분야가 있는데, 자바 버전은 기본적으로 자바 7에 고정되어 있고, 특정한 자바8 가능들을 사용할 수 있음. 또는 코틀린 프로그래밍 언어를 사용하는 것으로 전환
- Java는 하위 호환성이 매우 높아 특정 Java 버전만을 학습할 필요는 없음 -> 반대로 java17 기능을 의존한다면 java8에서는 사용할 수 없음

<br>

### Java 8
- 대규모 릴리즈로 해당 기능들 사용이 가능해졌음
- Lambda
- Stream
- Interface default method
- Optional
- new Date and Time API

<br>

### Java 9
- 모듈 시스템 등장(jigsaw)
- Collection (List, Set, Map)을 쉽게 구성할 수 있는 몇 가지 추가 기능
    ```java
        List<Stirng> list = List.of(\*"one"\*, \*"two"\*, \*"three"\*);
    ```
- Stream 에서 takeWhile, dropWhile, iterate 메서드의 형태로 몇 가지 추가 기능
    ```java
        Stream<String> stream = Stream.iterate(*""*, s -> s + *"s"*)
                                    .takeWhile(s -> s.length() < 10);
    ```
- Optional 에서 ifPresentOrElse 추가 기능
    ```java
        user.ifPresentOrElse(this::displayAccount, this::displayLogin);
    ```
- 인터페이스에서 private method 사용 가능
    ```java
        public interface MyInterface {

            private static void myPrivateMethod(){
                System.out.println(*"Yay, I am private!"*);
            }
        }
    ```
- 기타 언어 기능. try-with-resource 문 또는 다이아몬드 연산자(<>) 확장, HTTP 클라이언트와 같은 몇 가지 다른 개선 사항 존재

<br>

### Java 10
- 가비지 컬렉션 등과 같은 Java 10에 몇가지 변경 사항이 존재
- var 키워드를 사용하여 지역 변수 타입 추론을 허용. 메서드 내부의 변수에만 적용 가능
    ```java
        var myName = "Marco";
    ```
- 병렬 처리 가비지 컬렉션 도입으로 인한 성능 향상
- JVM 힙 영역을 시스템 메모리가 아닌 다른 종류의 메모리에도 할당 가능

<br>

### Java 11
- Java 11은 개발자 관점에서 볼 때 약간 작은 릴리스
- Oracle JDK와 OpenJDK 통합
- Oracle JDK가 구동형 유료 모델로 전환
- 서드파티 JDK로의 이전 필요
- Lambda 지역변수 사용법 변경
- 기타 추가
- String & Files 에는 몇가지 새로운 메서드 추가
    ```java
        *"Marco"*.isBlank();
        *"Mar\nco"*.lines();
        *"Marco  "*.strip();

        Path path = Files.writeString(Files.createTempFile(*"helloworld"*, *".txt"*), *"Hi, my name is!"*);
        String s = Files.readString(path);
    ```
- Run Source Files. Java 10부터 소스 파일을 먼저 컴파일하지 않고도 실행할 수 있음. 스크립틍을 향한 한 걸음
- 람다 매개변수에 대한 지역 변수 유형 추론. 람다 표현식에 var 사용 가능

<br>

### Java 12
- 몇 가지 새로운 기능과 정리가 포함되어 있지만 언급할 만한 내용은 유니코드 11지원과 새로운 스위치 표현식의 preview

<br>

### Java 13
- 기본적으로 유니코드 12.1 지원과 두 가지 새롭거나개선된 preview 기능
- 스위치 표현식(preview)
    ```java
        switch(status) {
        case SUBSCRIBER:
            *// code block*break;
        case FREE_TRIAL:
            *// code block*break;
        default:
            *// code block*}
        boolean result = switch (status) {
            case SUBSCRIBER -> true;
            case FREE_TRIAL -> false;
            default -> throw new IllegalArgumentException(*"something is murky!"*);
        };
    ```
- Multiline STrings (Preview)
    ```java
        String htmlBeforeJava13 = *"<html>\n"* +
                    *"    <body>\n"* +
                    *"        <p>Hello, world</p>\n"* +
                    *"    </body>\n"* +
                    *"</html>\n"*;

        String htmlWithJava13 = *"""
                    <html>
                        <body>
                            <p>Hello, world</p>
                        </body>
                    </html>
                    """*;
    ```

<br>

### Java 14
- 스위치 표현시 표준화
    ```java
    int numLetters = switch (day) {
        case MONDAY, FRIDAY, SUNDAY -> 6;
        case TUESDAY                -> 7;
        default      -> {
        String s = day.toString();
        int result = s.length();
        yield result;
        }
    };
    ```
- Instanceof 패턴 매칭 (preview)
    ```java
        // 이전에 변수 타입을 확인 후 캐스팅하여 사용
        if (obj instanceof String) {
            String s = (String) obj;
        }

        // 이제 캐스트를 효과적으로 삭제 가능
        if (obj instanceof String s) {
            System.out.println(s.contains(*"hello"*));
        }
    ```
- record (data object) 선언 기능 추가 (preview)
    ```java
    // 기존 코드
    final class Point {
        public final int x;
        public final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // record 사용 코드 -> 위와 기능적으로 동일
    record Point(int x, int y) {}
    ```
- 유용한 NullPointerExceptions 
    - 마지막으로 NullPointerExceptions는 정확히 어떤 변수가 null 인지 설명

<br>

### Java 15
- Text-Blocks / Multiline Strings
    - Java 13 실험 기능으로 도입된 여러 줄 문자열은 이제 프로덕션 준비 완료
    ```java
        String text = *"""
                Lorem ipsum dolor sit amet, consectetur adipiscing \
                elit, sed do eiusmod tempor incididunt ut labore \
                et dolore magna aliqua.\
                """*;
    ```
- Records & Pattern Matching(2차 preview, 상단 Java 14 참조)
- 스케일링 가능한 낮은 지연의 가비지 컬렉터 추가(ZGC)
- 레코드(2차 preview, Java 14 참조)
- Sealead Classes (Preview)
    - 상속 가능한 클래스를 지정할 수 있는 봉인 클래스가 제공됨
    - 상속 가능한 대상은 상위 클래스 또는 인터페이스 패키지 내에 속해 있어야 함
    ```java
        public abstract sealed class Shape
            permits Circle, Rectangle, Square {...}
    ```
- Nashorn JavaScript Engine 제거

<br>

### Java 16
- Pattern Maching for instanceof
- Unix-Domain Socket Channels
    - 유닉스 도메인 소켓에 연결 가능(MacOS 및 Windows(10+)에서도 지원됨)
    ```java
         socket.connect(UnixDomainSocketAddress.of(
        *"/var/run/postgresql/.s.PGSQL.5432"*));
    ```
- Foreign Linker API (Preview)
    - JNI(Java Native Interface)에 대한 계획된 교체로, 기본 라이브러리에 바인딩할 수 있다
- Recored & Pattern Matching

<br>

### Java 17
- Java 17은 Java 11 이후의 새로운 Java LTS(장기 지원) 릴리스
- Pattern Matching for switch (Preview)
    ```java
        public String test(Object obj) {

            return switch(obj) {

            case Integer i -> *"An integer"*;

            case String s -> *"A string"*;

            case Cat c -> *"A Cat"*;

            default -> *"I don't know what it is"*;

            };
        }
    ```
- Sealed Classes (Finalized)
    - Java 15에서 preview 제공되었던 기능 완료
- Foreign Function & Memory API (Incubator)
    - Java Native Interface(JNI)를 대체
    - 기본 함수를 호출하고 JVM 외부의 메모리에 액세스할 수 있음

<br>

<div style="text-align: right">22-09-06</div>

-------

## Reference
- https://velog.io/@ljo_0920/java-버전별-차이-특징