# Checked Exception vs Unchecked Exception

![Java Exception](./img/exception.png)

### Java Exception
- 자바의 Exception(예외)는 크게 3가지로 나눌 수 있음
- 체크 예외 (Checked Exception), 에러 (Error), 언체크 예외 (Unchecked Exception)
- 자바의 예외는 Throwable 클래스로 나뉘어짐

<br>

### Error
- 시스템에 비정상적인 상황이 발생하였을 경우에 발생
- 메모리 부족(OutofMemoryError)이나 스택오버플로우(StackOverflowError)같이 복구할 수 없는 것을 의미
- 이는 시스템 레벨에서 발생하기 때문에 개발자가 예측하기 쉽지 않고 처리할 수 있는 방법도 없음 -> 오류에 대한 처리를 신경쓰지 않아도 됨

<br>

### Exception?
- Exception(예외)는 프로그램 실행 중에 개발자의 실수로 예기치 않은 상황이 발생했을 때
- 배열의 범위를 벗어나거나(ArrayIndexOutOfBoundsException), 값이 null 참조변수를 참조(NullPointerException), 존재하지 않는 파일의 이름을 입력(FileNotFoundException) 등등이 있음.
- 예외는 Checked Exception 과 Unchecked Exception으로 나눌 수 있음
- Unchecked Exception은 RuntimeException 클래스의 하위 클래스이고 그 외의 클래스가 Checked Exception 임

<br>

### Checked Exception
- 체크 예외의 특징은 반드시 에러 처리를 해야하는 특징(try-chach or throw)을 가지고 있음. 
- 체크 예외는 RuntimeException의 하위 클래스가 아니면서 Exception 클래스의 하위 클래스들
- 컴파일 단계에서 컴파일러가 예외처리를 확인하는 Exception 클래스
- 체크 예외의 예시는 존재하지 않는 파일의 이름을 입력(FileNotFoundException), 실수로 클래스의 이름을 잘못 적음(ClassNotFoundException) 등이 있음
- Rollback이 되지 않고 트랜잭션이 commit까지 완료됨

<br>

### Unchecked Exception
- 체크 예외와는 달리 에러처리를 강제하지 않고 실행 중에(runtime) 발생할 수 있는 예외를 의미
- RuntimeException의 하위 클래스들을 의미
- 컴파일러가 예외처리를 확인하지 않는 RuntimeException 클래스
- 배열의 범위를 벗어난(ArrayIndexOutOfBoundsException), 값이 null이 참조변수를 참조(NullPointerException) 등이 있음
- Rollback이 가능

<br>

### Checked Exception vs UnChecked Exception 선택
- 임의의 예외 클래스를 만들어 예외 처리를 하는 경우가 많은데 이 때 try-catch로 묶어줄 필요가 있을 경우에만 Exception 클래스를 확장
- 일반적으로 실행시 예외를 처리할 수 있는 경우에는 RuntimeException 클래스를 확장해 Unchecked Exception을 사용하는 것이 좋음

<br>

![Exception Handling](./img/exception_handling.png)

### 예외 처리 방법
#### 1. 예외 복구
- 재시도를 통해 예외를 복구
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


<div style="text-align: right">22-07-11</div>

-------

## Reference
- https://devlog-wjdrbs96.tistory.com/351
- https://www.nextree.co.kr/p3239/