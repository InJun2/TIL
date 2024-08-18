# Lock

### 병행 제어 ( Concurrency Controll )
- Concurrency Controll 이란 데이터 베이스 시스템 또는 다중 사용자 환경에서 여러 트랜잭션이 동시에 실행될 때의 데이터의 일관성 유지 및 무결성 보장을 위한 매커니즘
- 여러 사용자가 동일한 데이터에 접근하고 수정하는 상황에서 데이터가 정확하고 일관성있게 유지될 수 있도록 함
- 해당 병행 제어를 하기 위해 트랜잭션을 통한 DB 락, 라이브러리 락을 사용함

<br>

### Lock

#### write-lock (exclusive lock)
- 배타적 락으로 불리며 read/write 할 때 사용되어 다른 트랜잭션이 같은 데이터를 read/write 하는 것을 허용하지 않음
    - 읽기, 쓰기 모두 허용하지 않음
- 이 락이 걸리면 읽기와 쓰기 모두 허용되지 않음

#### read-lock (shared lock)
- 공유 락으로 불리며 read 할 때 사용되며 다른 트랜잭션이 같은 데이터를 read 하는 것을 허용
    - 읽기는 허용, 쓰기는 차단
- 이 락이 걸린 상태에서 다른 트랜잭션도 동일한 데이터를 읽을 수 있음
- 그러나 쓰기 작업을 허용되지 않음

#### transaction isolation level
- 트랜잭션 격리 수준에 따라 락의 사용이 결정됨
    - [트랜잭션 격리 수준 정리 링크](https://github.com/InJun2/TIL/blob/main/CS-topic/DB/transaction-isolation-level.md)
- Repeatable Read 이상의 격리 수준에서는 읽기 작업 시 Read-Lock을 사용하여, 트랜잭션이 데이터를 여러 번 읽더라도 일관된 데이터를 읽을 수 있도록 함
- Read Committed 격리 수준에서는 쓰기 작업 시 Write-Lock을 사용하여, 다른 트랜잭션이 데이터를 읽거나 수정하지 못하게 함
- 락의 범위는 데이터베이스 엔진에 따라 다름
    - 일부 데이터베이스 시스템은 행 단위로 락을 걸 수 있는 반면, 다른 시스템은 페이지 또는 테이블 단위로 락을 걸 수도 있음

<br>

### MVCC (Multiversion concurrency control)
- MVCC는 commit 된 데이터만 읽으므로 다른 트랜잭션이 값을 변경하던 중 커밋을 하지 않았다면 다른 트랜잭션은 변경 전 값을 읽음
- 데이터를 읽을 때 특정 시점을 기준으로 가장 최근의 commit된 데이터를 읽음
    - 데이터의 이전 버전을 유지하여 트랜잭션이 요청한 시점의 데이터를 정확하게 반환가능. 데이터의 일관성 유지
    - consistent read (일관된 읽기) : 트랜잭션이 시작된 이후에 다른 트랜잭션이 데이터를 변경하고 커밋하더라도, 현재 트랜잭션은 이를 보지 않고 트랜잭션이 시작될 때의 데이터를 계속 읽음
- 데이터 변화(write) 이력을 관리
- read와 write는 서로를 block 하지 않음
    - 읽기 작업은 데이터의 기존 버전을 참조하고, 쓰기 작업은 새로운 버전을 생성하므로, 트랜잭션 간의 락 경쟁이 줄어듬
- MySQL InnoDB와 postgreSQL 에서 사용됨
- 모든 트랜잭션이 완료될 때까지 여러 버전을 유지해야하므로 추가적인 스토리지가 필요할 수 있고 데이터베이스가 오래된 데이터 버전을 정리하는 Garbage Collection 메커니즘 필요

<br>

<!-- ### 개인적인 궁금증

#### 1. MongoDB vs Redis
- 

<br>

#### 2. MySQL vs Postgres


<br> -->

### Reference
- https://www.youtube.com/watch?v=0PScmeO3Fig
