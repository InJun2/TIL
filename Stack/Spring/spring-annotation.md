# Spring Annotation

### Spring Annotation
- Annotation 이란 기본적으로 기능이 없는 메타 데이터를 명시, 제공하는 주석이지만 자바나 스프링에서는 코드에 추가적인 정보를 첨부하여 그 정보를 기반으로 다양한 작업을 수행함
    - [Java Annotation](https://github.com/InJun2/TIL/blob/main/Stack/Java/annotation.md)
- Spring에서의 어노테이션은 프로그램 코드의 일부가 아닌 프로그램에 관한 데이터를 제공하고, 코드에 정보를 추가하는 정형화된 방법
    - 컴파일러에게 코드 작성 문법 에러를 체크하도록 정보를 제공
    - 소프트웨어 개발 툴이 빌드나 배치시 코드를 자동으로 생성할 수 있도록 정보를 제공
    - 실행시(런타임시) 특정 기능을 실행하도록 정보를 제공
- Spring에서는 리플렉션과 프록시 기법을 통해 애노테이션을 기반으로 다양한 기능을 제공
    - Spring의 @Component, @Service, @Autowired와 같은 애노테이션은 런타임 시 리플렉션을 통해 클래스와 메서드 정보를 읽고, 빈 등록, 의존성 주입 등의 기능을 자동으로 수행

<br>

### Annotation 동작 순서
1. 어노테이션을 정의
2. 클래스에 어노테이션을 배치
3. 코드가 실행되는 중에 Reflection을 이용하여 추가 정보를 획득하여 동작

<br>

### Annotation 구성 요소
- @Retention
    - 해당 어노테이션의 동작 범위를 지정. 파라미터로 RetentionPolicy를 받음
- @Documented
    - javaDoc 으로 Annotation에 대한 설명을 분리
- @Target
    - 어느 부분에 적용해야할지 정의

<br>

## Ssafy Wizards CS Study

### 1. Annotation이 Spring에서 많은 기능을 하는 이유
- 리플렉션(Reflection)
    - 런타임에 클래스, 메서드, 필드 정보를 읽고 조작할 수 있는 기능을 제공. 이를 통해 Spring은 애노테이션을 기반으로 설정된 메타데이터를 읽어 동적으로 빈을 생성하고 의존성을 주입할 수 있음
- 프록시(Proxy)
    - 프록시는 메서드 호출을 가로채어 추가적인 기능(예: 트랜잭션, 보안, 로깅)을 제공할 수 있음
    - Spring은 프록시를 사용하여 애노테이션 기반 기능을 구현
- 애노테이션은 Java의 표준 기능으로, Spring 외의 다른 라이브러리나 프레임워크와도 쉽게 통합할 수 있음
- 애노테이션을 사용하면 선언적 프로그래밍이 가능해짐. 개발자가 비즈니스 로직에 집중할 수 있도록 도와주며, 보일러플레이트 코드(반복적인 코드)를 줄여줌
- 애노테이션은 코드와 설정을 분리에 도움이 됨. @Component나 @Service 애노테이션을 사용하여 클래스의 역할을 선언함으로써, XML 설정 파일을 최소화하고 코드의 모듈성을 향상
- 해당 기능들을 통해 Spring에서 다양한 기능을 유연하게 확장하고 적용할 수 있음

<br>

### 2. Lombok의 @Data를 잘 사용하지 않는 이유
- @Data 애노테이션은 간편하게 게터, 세터, toString, equals, hashCode 메서드를 생성해줌
- 하지만 어떤 메서드가 자동 생성되는지 명확하게 알기어려워 가독성을 떨어뜨릴수 있고 자동 생성된 코드로 인해 디버깅이 어려울 수 있음
- 모든 메서드를 자동으로 생성하여 불필요한 메서드 생성 가능
    - 특히 모든 변수에 무분별한 Setter 접근은 데이터 일관성을 해칠 수 있음
- 특정 프레임워크나 라이브러리와의 호환성 문제가 발생할 수 있음

<br>

### Reference
- https://velog.io/@rara_kim/Spring-어노테이션Annotation
- https://velog.io/@anak_2/Java-annotations-이란-설명-활용
- https://developer-youn.tistory.com/122