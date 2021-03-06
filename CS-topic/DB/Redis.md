# Redis

### Redis
- 빠른 오픈 소스 <code>인 메모리</code> 키 값 데이터 구조 스토어
- Key-Value 구도의 비정형 데이터를 저장하고 관리하기 위한 오픈 소스 기반의 비관계형 데이터 베이스 관리 시스템(DBMS)
- 여러 서버에서 같은 데이터를 공유할 때 주로 사용
- 보통 데이터 베이스는 하드 디스크나 SSD에 저장하는데 Redis는 메모리(RAM)에 저장해서 디스크 스캐닝이 필요없어 매우 빠른 장점이 존재
- 캐싱도 가능하여 실시간 채팅에 적합하며 세션 공유를 위해 세션 클러스트링에도 활용
- 다양한 자료 구조를 지원
- 싱글 스레드 방식으로 인해 연산을 원자적으로 수행이 가능 -> 싱글 스레드기 때문에 시간 복잡도 고려
- In-memory 특성상 메모리 파편화, 가상 메모리등의 이해가 필요
- 읽기 성능 증대를 위한 서버측 리플리케이션 지원, 쓰기 성능 증대를 위한 클아이언트 측 샤딩 지원
- RAM의 휘발성을 막기위해 snapshot, AOF(Append Only File)의 백업 과정 존재

<br>

![In Memory DataBase](./img/in_memory.svg)

```
인 메모리 데이터베이스
- 컴퓨터에 주 메모리에 모든 조직 또는 개인의 데이터를 저장
- 인메모리 데이터베이스에 대한 데이터 분석은 보조 기억 장치를 사용하는 기존 데이터베이스에 비해 빠름
- 사물 인터넷(IoT)의 출현과 클라우드 기반 솔루션 성장에 따라 실시간으로 데이터 처리해야할 필요했기 때문에 실시간 데이터를 처리하기 위한 고성능 데이터베이스 솔루션
```

<br>

#### Redis 특징 재정리
- 영속성을 지원하는 인 메모리 데이터 저장소 ( RAM의 휘발성을 막기위해 snapshot, AOF 사용 )
    - snapshot : 특정 지점을 설정하고 디스크에 백업, 순간적으로 메모리에 있는 내용 전체를 디스크에 옮겨 담는 방식
    - AOF(Append Only File) : 모든 write/update 연산 자체를 모두 log 파일로 기록하고, 서버가 셧다운되면 재실행해서 다시 만들어 놓는 것
- 다양한 자료구조 지원
- 싱글 스레드 방식으로 인해 연산을 원자적으로 수행을 가능
- 읽기 성능 증대를 위한 서버 측 리플리케이션 지원
    - 리플리케이션(Replication) : 서버측 복제로 레디스가 실행중인 서버가 충돌하는 경우 장애 조치 처리와 함께 더 높은 읽기 성능을 지원하기 위함
- 쓰기 성능 증대를 위한 클라이언트 측 샤딩(Sharding) 지원
    - 샤딩(Sharding) : 같은 테이블의 스키마를 가진 데이터를 다수의 데이터베이스에 분산하여 저장하는 방법
- 다양한 서비스에서 사용되며 검증된 기술

<br>

#### Memory 관리
- 메모리 파편화
    - 메모리를 할당 받고 해제하는 과정에서 공간이 비어지게 되는데 공간을 많이 차지하는 프로세스가 들어오면 중간중간 비어있는 메모리 공간 때문에 할당가능한 부분이 줄어듬 -> 실제 사용하는 메모리보다 많이 사용하고 있다고 판단하고 프로세스가 죽는 경우가 생김 . Redis를 사용할 시 이런 메모리 공간을 여유롭게 사용해야함
- 가상메모리 Swap
    - 실제로 프로세스를 메모리에 올릴때 일부만 올려서 메모리에서 사용하는데 그렇기 때문에 디스크와 메모리 간에 주고받는 시간 발생. 싱글 쓰레드에서는 속도가 중요하기 때문에 해당 Swap 지식이 필요함
- Replication - Fork
    - 리플리케이션을 이용하여 데이터가 유실되는 것을 방지하기 위해 서버를 복제. 복사가 일어날 때 메모리가 가득차있다면 복제가 일어나지 않고 서버가 죽는 현상 발생할 수 도있음. Redis는 메모리를 여유롭게 사용해야함

<br>

![Redis Value](./img/redis_value.png)

#### Redis Value
- 다양한 구조체 지원 -> Key가 될 수 있는 데이터 구조체가 다양
    - String (text, binary data) - 512MB 까지 저장이 가능
    - set (String 집합)
    - sorted set (set을 정렬해둔 상태)
    - Hash
    - List (양방향 연결 리스트)

<br>

#### Redis Single Thread
- 싱글 스레드 방식이어서 연산을 하나씩 처리 -> 빠르게 처리해야 하므로 시간 복잡도에 신경써야함
- 레디스는 싱글 스레드를 사용하여 연산을 원자적으로 처리하여 Race Condition(경쟁상태: 둘 이상의 입력 또는 조작의 타이밍이나 순서 등이 결과 값에 영향을 줄 수 있는 상태)이 거의 발생하지 않음
- 서버하나에 여러 개의 서버를 띄우는 것이 가능 (Master-Slave 형식으로 구성하여 데이터 분실 위험을 없앰)

<br>

<div style="text-align: right">22-07-20</div>

-------

## Reference
- https://steady-coding.tistory.com/586
- https://github.com/gyoogle/tech-interview-for-developer/blob/master/Computer%20Science/Database/Redis.md
- https://www.tibco.com/ko/reference-center/what-is-an-in-memory-database
- https://liebe97.tistory.com/37
- [유튜브 : 디디의 Redis 10분 테코톡](https://www.youtube.com/watch?v=Gimv7hroM8A)