# Checked Exception vs Unchecked Exception

![Java Exception](./img/exception.png)

### Java Exception
- 자바의 예외는 크게 3가지로 나눌 수 있음
    - 에러 (Error), 체크 예외 (Checked Exception), 언체크 예외 (Unchecked Exception)
- 자바의 에러, 예외는 Throwable 클래스에서 나누어짐
- Unchecked Exception은 자바 실행 시(Runtime)

<br>

### Error
- Error는 합리적인 응용 프로그램이 잡아내려고 해서는 안 되는 심각한 문제를 나타내는 Throwable의 하위 클래스
- 시스템에 비정상적인 상황이 발생하였을 경우에 발생
- 메모리 부족(OutofMemoryError)이나 스택오버플로우(StackOverflowError)같이 복구할 수 없는 것을 의미
- 이는 시스템 레벨에서 발생하기 때문에 개발자가 예측하기 쉽지 않고 처리할 수 있는 방법도 없음 -> 오류에 대한 처리를 신경쓰지 않아도 됨

<br>

### Exception
- Exception(예외)는 프로그램 실행 중에 개발자가 구현한 로직에서 예기치 않은 상황이 발생했을 때
    - 예외는 개발자가 직접 처리할 수 있기 때문에 예외 상황을 미리 예측하여 핸들링 할 수 있음
- 실행 중에 정상적인 프로그램의 흐름을 어긋나는 경우로 개발자가 처리할 수 있는 예외
- 예외는 Checked Exception 과 Unchecked Exception으로 나눌 수 있음
- Unchecked Exception은 RuntimeException 클래스와 해당 클래스의 하위 클래스이고 그 외의 클래스가 Checked Exception 임

<br>

### Checked Exception
- 체크 예외의 특징은 반드시 에러 처리를 해야하는 특징(try-chach or throw)을 가지고 있어 해주지 않는 다면 컴파일 단계에서 오류가 발생
    - 명시적인 예외처리를 강제하기 때문에 Checked Exception 이라고 함
    - try-catch로 예외를 잡거나, throw로 호출한 메소드에게 예외를 던져야 함
    - 컴파일 단계에서 컴파일러가 예외처리를 확인하는 Exception 클래스
- 체크 예외는 RuntimeException의 하위 클래스가 아니면서 Exception 클래스의 하위 클래스들
    - 체크 예외의 예시는 존재하지 않는 파일의 이름을 입력(FileNotFoundException)
    - 타입이 맞지 않는 등의 잘못된 입력 (IOException)
    - 실수로 클래스의 이름을 잘못 적음(ClassNotFoundException) 등이 있음
- Rollback이 되지 않고 트랜잭션이 commit까지 완료됨
    - Spring Framework에서 CheckedException은 Transaction 처리시에 Exception이 발생해도 Rollback을 하지 않는다
    ```
    Rollback이 되지 않는 이유
    - 기본적으로 Checked Exception은 복구가 가능하다는 매커니즘을 가지고 있음
    - 예를들어 이미지 파일을 찾아서 전송해주는 함수에서 이미지를 찾지 못했을 경우 기본 이미지를 전송한다는 복구 전략을 가질 수 있음
    ```

<br>

### Unchecked Exception
- 체크 예외와는 달리 에러처리를 강제하지 않고 실행 중에(runtime) 발생할 수 있는 예외를 의미 
- RuntimeException와 해당 클래스의 하위 클래스들을 의미
    - RuntimeException은 프로그램 실행 중에 발생하여 의도적으로 개발자가 설정한 조건을 위배했을 때 발생
    - 명시적으로 예외 처리를 하지 않아도 되는 Exception
    - 배열의 범위를 벗어나거나(ArrayIndexOutOfBoundsException)
    - 값이 null 참조변수를 참조(NullPointerException)
    - 존재하지 않는 파일의 이름을 입력(FileNotFoundException)..
- 컴파일러가 예외처리를 확인하지 않는 RuntimeException 클래스
- 배열의 범위를 벗어난(ArrayIndexOutOfBoundsException), 값이 null이 참조변수를 참조(NullPointerException) 등이 있음
- Rollback이 가능
    - Spring Framework에서 UncheckedException은 Transaction 처리시에 Exception이 발생한 경우 Rollback을 수행

<br>

### Checked Exception vs UnChecked Exception 선택
- 임의의 예외 클래스를 만들어 예외 처리를 하는 경우가 많은데 이 때 try-catch로 묶어줄 필요가 있을 경우에만 Exception 클래스를 확장
- 일반적으로 실행시 예외를 처리할 수 있는 경우에는 RuntimeException 클래스를 확장해 Unchecked Exception을 사용하는 것이 좋음

<br>

![Exception Handling](./img/exception_handling.png)

### 예외 처리 방법
#### 1. 예외 복구
- 재시도를 통해 예외를 복구. 예외 상황을 파악하고 문제를 해결하여 정상 상태로 돌려 놓음
- 예외 복구의 핵심은 예외가 발생하더라도 애플리케이션은 정상적인 흐름으로 진행
- 네트워크 환경이 좋지 않을경우 서버에 접속이 안되는 상황의 시스템에 적용하는 것이 효과적
- 예외가 발생하면 예외를 잡아서 일정 시간만큼 대기하고 다시 재시도를 반복하고 최대 재시도 횟수를 넘기면 예외를 발생
- 재시도를 통해 정상적인 흐름을 타게하거나 예외가 발생하면 이를 미리 예측하여 다른 흐름으로 유도시키도록 구현이 바람직

#### 2. 예외처리 회피
- 예외가 발생하면 throws를 통해 호출한쪽으로 예외를 던지고 그 처리를 회피하는 것.
- 그러나 무책임하게 던지는 것은 위험. 호출한 쪽에서 다시 예외를 받아 처리하도록 하거나 해당 메소드에서 이 예외를 던지는 것이 최선의 방법이라는 확신이 있을 때만 사용해야함

#### 3. 예외 전환
- 예외를 잡아서 지정한 예외로 던지는 것.
- 호출한 쪽에서 예외를 받아서 처리할 때 좀 더 명확하게 인지할 수 있도록 돕기 위한 방법
- 예시로 Checked Exception 중 복구가 불가능한 예외가 잡혔다면 이를 Unchecked Exception으로 전환해서 다른 계층에서 일일이 예외를 선언할 필요가 없도록 할 수 있음

<br>

### 자바 예외 처리 성능 영향 및 부하를 줄이는 방법
- 예외 객체를 생성할 때는 스택 트레이스를 수집하는 등 상당한 비용이 발생
    - 스택 트레이스를 수집하는 과정은 CPU와 메모리를 소모
- 예외가 발생하면 정상적인 코드 흐름을 벗어나기 때문에 CPU의 분기 예측을 실패하고 캐시 미스를 유발할 수 있음

#### 부하를 줄이는 방법
- 예외는 예외적인 상황을 처리하기 위해 사용해야 하며 일반적인 제어 흐름을 위해 사용하지 않도록 하기
    - 입력 값을 검증하여 사전에 잘못된 값을 처리하면 예외 발생을 줄일 수 있음
- 예외를 잡을 때는 최대한 구체적은 예외를 잡도록 하기. 특히 'Exception'이나 'Throwable'을 잡는 것은 피해야 함
- 성능이 민감한 코드에서는 예외를 피하기, 루프 안에서 예외가 발생하지 않도록 입력 값을 사전에 검증
- 예외가 발생할 때마다 로깅을 수행하면 성능에 영향을 끼칠 수 있음. 로깅 레벨을 조정하거나, 중요하지 않은 예외에 대해 로깅을 피하기
- 불필요한 캐치 블록을 줄이고 필요한 때만 예외를 잡기. 여러 단계에서 예외를 처리하기 보다 최종 단계에서 예외를 처리하는 것이 좋음

<br>

<div style="text-align: right">22-07-11</div>
<div style="text-align: right">+ 내용추가 : 22-09-18</div>

-------

## Reference
- https://devlog-wjdrbs96.tistory.com/351
- https://www.nextree.co.kr/p3239/
- https://steady-coding.tistory.com/583