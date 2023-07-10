# Spring Scheduler

### Spring Scheduler 란?
- 일정한 시간간격 또는 일정한 시각에 특정 로직을 돌리기 위해서 사용하는 라이브러리로 Spring boot starter에 의존하여 spring boot를 사용한다면 따로 의존성을 추가해주지 않아도 됨
- 1개의 Thread pool을 사용하여 schedule이 끝나야 다음 schedule 실행이 가능
- 이러한 다른 Spring에서 사용하는 Scheduler로는 Spring Quartz 존재. Quartz는 좀 더 Scheduling의 세밀한 제어가 필요할 때, 그리고 만들어야하는 Scheduling 서비스 노드가 멀티이기 때문에 클러스터링이 필요할 때 여러분이 만들고자 하는 서비스
- 진행하는 프로젝트에서는 Spring Scheduler를 주로 사용하였음

<br>

### @Scheduler 규칙
- Method는 void 타입이어야 함
- Method는 매개변수 사용 불가

<br>

### 사용예시
- 의존성 주입은 따로 필요하지 않고 @SpringBootApplication 어노테이션을 사용하여 실행하거나 @Configuration 어노테이션을 사용하여 설정 클래스임을 명시하고 해당 클래스에서 @EnableScheduling 어노테이션을 추가하여 설정. 이후 설정이 많아지면 구분이 어렵기 때문에 주로 후자의 방법을 사용하였음
```java
@SpringBootApplication
@EnableScheduling
public class JavaProjectServiceApplication {
    ...
}

@Configuration
@EnableScheduling
public class Scheduler {
}
```
- 이후 Scheduler를 사용할 메소드에 @Scheduled 어노테이션 및 실행 기준 추가
    - 사용방법으로는 fixedDelay, fixedRate, initialDelay + fixedDelay, cron, zone 등 존재
    - cron은 왼쪽부터 초, 분, 시, 일, 월, 년도를 의미. 모든 시간에서 실행하려면 *을 사용
```java
    @Scheduled(fixedDelay = 1000) // scheduler 끝나는 시간 기준으로 1000 간격으로 실행
    public void scheduleFixedDelayTask() {
        System.out.println(
        "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedRate = 1000) // scheduler 시작하는 시간 기준으로 1000 간격으로 실행
    public void scheduleFixedRateTask() {
        System.out.println(
        "Fixed rate task - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 5000) // initialDelay 값 이후 처음 실행되고, fixedDelay 값에 따라 계속 실행
    public void scheduleFixedRateTask() {
        System.out.println(
        "Fixed rate task - " + System.currentTimeMillis() / 1000 + "&& Fixed rate task - " + System.currentTimeMillis() / 5000);
    }

    @Scheduled(cron = "0 15 10 15 * ?") // cron에 따라 실행. 매월 15일 10시 15분 0초에 실행됨을 의미
    public void scheduleTaskUsingCronExpression() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println(
        "schedule tasks using cron jobs - " + now);
    }

```

<br>

<div style="text-align: right">23-07-10</div>

-------

## Reference
- https://data-make.tistory.com/699