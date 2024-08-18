# Transaction Isolation Level

### Transaction Isolation Level (트랜잭션 격리 수준)
- 동시에 여러 트랜잭션이 처리될 때, 특정 트랜잭션이 다른 트랜잭션에서 변경하거나 조회하는 데이터를 볼 수 있도록 허용할지 말지 결정하는 것
- 여러 트랜잭션이 동시에 실행될 때 발생할 수 있는 데이터 일관성 문제를 해결하는 역할을 함

<br>

![read uncommitted](./img/read-uncommitted.png)

#### 1. read uncommitted
- 커밋 전의 트랜잭션 데이터 변경 내용을 다른 트랜잭션이 읽을 수 있도록 허용
- 트랜잭션 A가 아직 커밋되지 않은 상태에서 트랜잭션 B가 DB를 조회하면, 변경된 트랜잭션 A의 값을 볼 수 있게됨
- 트랜잭션 A가 일을 다 수행하지 않고 commit 되기 전이므로 롤백될 수도 있는데 롤백 전 무효가 된 데이터를 읽을 수 있는 Dirty Read 발생

<br>

![read committed](./img/read-commited.png)

#### 2. read committed
- read하는 시간을 기준으로 그 전에 commit된 데이터를 읽음
    - 트랜잭션 시작 이후 데이터를 읽을 시점에 commit 되어 있는 데이터를 읽음
    - 커밋된 데이터를 읽으므로 Dirty Read를 방지
- 다른 트랜잭션이 커밋한 변경 사항을 즉시 반영하여 읽을 수 있음
- 동일한 트랜잭션에서 동일한 데이터를 여러번 읽을 때 처음 읽을 때와 이후 읽은 데이터가 다른 Non-repeatable Read(반복 불가능한 읽기)가 발생할 수 있음
    - 다른 트랜잭션에서 작업을 처리하여 해당 데이터가 중간에 커밋되었을 경우

<br>

![repeatable read](./img/repeatable-read.png)

<br>

![repeatable read3](./img/repeatable-read2.png)

#### 3. repeatable read
- 트랜잭션 시작 시간을 기준으로 그 전에 commit된 데이터를 읽음
    - 읽기를 진행하기 전 트랜잭션의 시작 시간이 기준
    - 트랜잭션 벙위 내에서 조회한 내용이 항상 동일함을 보장
- 트랜잭션이 시작된 이후, 다른 트랜잭션이 커밋한 변경 사항을 반영하지 않음
    - 처음 읽은 데이터를 트랜잭션이 끝날 때 까지 동일하게 읽을 수 있음
- 트랜잭션 내에서 동일한 데이터를 여러 번 읽더라도 같은 결과를 보장하여 Non-repeatable Read(반복 불가능한 읽기)를 방지
- 트랜잭션 동안 새로운 행이 삽입되거나 삭제되는 Phantom Read는 여전히 발생할 수 있음
    - 팬텀 리드는 read uncommitted, read committed, repeatable read 모두 발생할 수 있음

<br>

#### 4. serializable
- 한 트랜잭션에서 사용하는 데이터는 다른 트랜잭션에서 접근할 수 없음
- 트랜잭션의 ACID 성질이 엄격하게 지켜지나 성능은 가장 떨어지는 방법
- 단순한 SELECT 문만으로도 트랜잭션이 커밋될 때까지 모든 데이터에 잠금(Lock)이 설정되어 다른 트랜잭션에서 해당 데이터 접근을 제한

<br>

### Reference
- https://unequaled-peach-7e5.notion.site/DB-8987ed9cf61b4492971483ec2bcdae78