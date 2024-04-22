# Timer Class

### Timer 
- 자바 스레드처럼 일종의 스레드 클래스로 실제로 타이머의 기능을 수행하는 클래스
- 등록된 작업 실행을 스레드를 하나씩 생성하여 실행
- 특정 작업이 오래 실행되면 등록된 다른 TimerTask 작업이 지연되어 예정된 시간에 실행되지 않을 수 있음
- TimerTask가 도중에 예상치 못한 예외를 던져버리는 경우 예측하지 못한 상태로 넘어가고 Timer 스레드가 멈춰버릴 수 있음
    - 해당 상황 발생시 새로운 스레드를 생성해주지도 않음
    - 해당 Timer에 등록되어 있던 모든 작업이 취소된 상황이라 간주해야하며 실행되지 않고 새로운 작업을 등록할 수도 없음
- Timer 대신 ScheduledThreadPoolExecutor를 사용하라고 함
    - 지연 작업과 주기적 작업마다 여러 개의 스레드를 할당하여 실행 예정 시간을 벗어나지 않음
    - 오류에 대해 훨씬 안정적으로 처리가 가능
    - 자바 5버전 부터 ScheduledThreadPoolExecutor를 사용하는 것이 좋다고 하니 대부분의 상황에 Timer는 쓰지 않는 것이 좋아보임
- 특별한 스케줄링 방법을 지원하는 스케줄링 서비스를 구현해야 한다면 BlockingQueue를 구현하면서 ScheduledThreadPoolExecutor와 비슷한 기능을 제공하는 DelayQueue를 사용하는 것을 추천
    - DelayQueue는 큐 내부에 여러 개의 Delayed 객체로 작업을 관리하며 각각의 Delayed 객체는 저마다의 시각을 가지고 있음
    - Delayed 내부의 시각이 만료된 객체만 take() 메서드로 가져갈 수 있음
    - DelayQueue에서 뽑아내는 객체는 객체마다 지정되어 있던 시각 순서로 정렬되어 뽑아짐

<br>


### TimerTask
- Timer 클래스가 수행되어야할 내용을 작성하는 클래스

<br>

### 이전 프로젝트에서 사용한 방법

```java
private void replyLetterTimeTask(Long letterId) {
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Letter letter = letterRepository.findById(letterId).get();
            if (LetterState.RECEIVE_WAITING.equals(letter.getState())) {
                letter.updateLetterState(LetterState.EXPIRATION);
                saveLetter(letter);
                log.info("letter Id : " + letter.getId() + " , 해당 답변은 만료되었습니다");
            }
        }
    };
    timer.schedule(timerTask, 1000 * 60 * 30);
}
```

<br>

### Timer -> ScheduledThreadPoolExecutor

```java
public class ReplyLetterScheduler {
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    public void replyLetterTimeTask(Long letterId) {
        long delayMillis = TimeUnit.MINUTES.toMillis(30);
        executor.schedule(() -> executeTask(letterId), delayMillis, TimeUnit.MILLISECONDS);
    }

    private void executeTask(Long letterId) {
        Letter letter = letterRepository.findById(letterId).get();
        if (LetterState.RECEIVE_WAITING.equals(letter.getState())) {
            letter.updateLetterState(LetterState.EXPIRATION);
            saveLetter(letter);
            log.info("Letter Id : " + letter.getId() + " , 해당 답변은 만료되었습니다");
        }
    }
}
```

<br>

### 해당 정리 이후 리펙토링
- 정리 이후 다시 생각해보니 하나하나 스레드가 시간을 대기하는 것보다 유사한 방법으로 해당 방법이 더 좋을 것 같음
    - 내부적으로 큐를 사용하고 요청이 들어오면 해당 서비스에서 편지의 상태를 변경하고 편지의 Entity와 현재 시간을 저장
    - 이후 스케줄링을 통해 1분마다 해당 큐에서 30분 이후인 Entity가 나오기 전까지 편지를 만료로 바꿔도 될 것 같음

<div style="text-align: right">24-04-22</div>

-------

## Reference
- https://codechacha.com/ko/java-timer/