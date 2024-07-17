# Sealed Class

### Sealed Class?
- 봉인된 클래스로 특정 클래스에게만 상속이 가능한 클래스
- 상속 계층 내에서 엄격한 제어를 통해 명확한 역할과 직무 분류에 사용
    - 프로그램 구조를 명확하게 유지하고 의도치 않은 상속으로 인한 복잡성과 혼란 방지
- jdk 17 버전 이후 사용가능

<br>

### 사용방법
- Class(Interface 포함)에 sealed 키워드를 사용하고 상속을 허락하는 클래스를 permits 뒤에 나열
    - sealed가 선언된 클래스는 permits가 반드시 필요
- Sealed 된 클래스를 활용하기 위해서는 같은 모듈 혹은 같은 패키지 안에 존재 해야함
- 구현 클래스는 다음 키워드 중 하나 사용 필요
    - sealed : 여전히 봉인된 클래스로 추가적으로 permits로 하위 클래스 나열 필요
    - final : 더 이상 상속 받을 수 없는 클래스
    - non-sealed : 봉인이 해제된 클래스로 자유롭게 상속 가능

```java
// SealedStudyGroup을 상속 받을 수 있는 클래스는 Alog, Java, CS 만 가능
sealed class SealedStudyGroup permits Algo, Java, CS{
}

// 더이상 어떤 클래스도 Algo를 상속받지 못함
final class Algo extends SealedStudyGroup {
    ...
}

// Spring만 해당 클래스를 참조 가능 
sealed class Java extends SealedStudyGroup permits Spring {
	...
}

// sealed 적용 해제, 어떤 클래스도 해당 클래스를 상속할 수 있음
non-sealed class CS extends SealedStudyGroup {
	...
}

final class Spring extends Java{
    ...
}

class Network extends CS {
	...
}
```

<br>