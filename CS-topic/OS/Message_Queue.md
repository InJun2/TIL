# Message Queue

### 메시지 큐 (Messsage Queue)
- 메시지 큐란 프로세스 간에 데이터를 교환할 때 사용 되는 통신 방법 중의 하나
- 메시지 큐는 메시지 지향 미들웨어(MOM)를 구현한 시스템으로 프로그램(프로세스) 간의 데이터를 교환할 때 사용하는 기술
- 메시지 큐를 사용하면 다음과 같은 장점을 가질 수 있음. 이러한 장점으로 메시지 큐는 대용량 데이터를 처리하기 위한 배치 작업이나 채팅서비스, 비동기 데이터를 처리할 때 사용됨
    - 비동기 (Asynchronous) : 데이터를 수신자에게 바로 보내지 않고 큐에 넣고 관리하기 때문에 나중에 처리 가능
    - 비동조 (Decoupling) : 애플리케이션과 분리할 수 있기 때문에 확장이 용이해짐
    - 탄력성 (Resilience) : 일부가 실패하더라도 전체에 영향을 주지 않음
    - 과잉 (Redundancy) : 실패할 경우 재실행 가능
    - 보증 (Guarantees) : 작업이 처리된 걸 확인할 수 있음
    - 확장성 (Scalable) : N:1:M 구조로 다수의 프로세스들이 큐에 메시지를 보낼 수 있음


<br>

### 메시지 큐 주로 사용
- 메시지 큐는 소비자가 실제로 메시지를 어느 시점에 가져가서 처리하는 지는 보장하지 않음
- 언젠가는 큐에 넣어둔 메시지가 소비되어 처리될 것이라고 믿는 것
- 이러한 비동기적 특성 때문에 메시지 큐는 실패하면 치명적인 핵심 작업보다는 어플리케이션의 부가적인 기능에 사용하는 것이 적합
- 1만개의 요청이 발생했을 때 모든 요청을 서버로 보내는 것이 아니라 메시지 큐에 보내고, 서버측에서는 처리하는데 이상없을 정도의 요청들만 메시지 큐에서 가져와서 처리해준다면 서버의 부담믈 줄일 수 있음
    - 다른 곳의 API로부터 데이터 송수신 가능
    - 다양한 애플리케이션에서 비동기 통신 가능
    - 이메일 발송 및 문서 업로드 가능
    - 많은 양의 프로세스들을 처리 가능

<br>

### 메시지 지향 미들웨어(Message Oriented Middleware)
- 응용 소프트웨어 간의 데이터(비동기 메시지) 통신을 위한 소프트웨어
- 메시지 지향 미들웨어는 메시지를 전달하는 과정에서 보관하거나 라우팅 및 변환할 수 있다는 장점을 가짐
    - 보관 : 메시지의 백업을 유지함으로써 지속성을 제공, 덕분에 송수신 측은 동시에 네트워크 연결을 유지할 필요없음
    - 라우팅 : 미들웨어 계층 자신이 직접 메시지 라우팅이 가능하기 때문에 하나의 메시지를 여러 수신자에게 배포가 가능해짐(멀티캐스트)
    - 변환 : 송수신 측의 요구에 따라 메시지를 변환할 수 있음

<br>

### 메시지 지향 미들웨어의 단점
- 아키텍처에 외부 구성 요소인 메시지 전송 에이전트가 필요, 일반적으로 새로운 요소를 추가할 경우 시스템 성능이 저하되고, 신뢰성이 떨어짐
- 시스템이 복잡해지기 때문에 관리가 어렵고 비용이 발생
- 애플리케이션 간의 통신은 본질적으로 동기지만, 메시지 기반 통신은 본질적으로 비동기이기 때문에 메커니즘 불일치가 발생
- 이를 위해 요청을 그룹화하여 하나의 의사 동기 트랜잭션으로 응답하는 기능을 가짐
- 표준이라고 부를 규격이 존재하지 않기 때문에 호환이 안될 수도 있음

<br>

### 메시지 플랫폼
- 메시지 플랫폼은 주로 메시지 브로커와 이벤트 브로커로 나누어지며 RabbitMQ, Redis, Kafka 같은 기술을 메시지 플랫폼이라고 함
- 생산자 : 데이터를 메시지 큐에 전달하는 입장
- 소비자 : 메시지 큐에서 데이터를 가져오는 입장
- 메시지 브로커 : 대규모 메시지 기반 미들웨어 아키텍쳐에서 사용되어 왔음. 메시지를 받아서 적절히 처리하면 짧은 시간내에 메시지가 삭제되는 특징이있어 데이터 손실의 위험이 있음
- 이벤트 브로커 : 이벤트 또는 메시지라고 불리는 정보를 하나만 보관하고 인덱스를 통해 개별 엑세스를 관리하고 업무상 필요한 시간동안 이벤트를 관리. 메시지 브로커와 다르게 이벤트가 삭제되지 않는다는 특징이 있으며 서비스에서 발생하는 이벤트를 DB에 저장하듯이 이벤트 브로커의 큐에 저장함
- 메시지 브로커는 이벤트 브로커의 역할을 할 수 없지만, 이벤트 브로커는 메시지 브로커의 역할을 할 수 있음
- 이벤트 저장으로 얻는 장점은 다음과 같음
    - 딱 한 번 일어난 이벤트 데이터를 브로커에 저장함으로써 모든 데이터 요소를 한 곳에서만 제어 또는 편집하도록 조직하는 관례(단일 진실 공급원)에 맞게 동작
    - 장애가 발생했을 때 장애가 일어난 지점부터 재처리할 수 있음
    - 많은 양의 실시간 스트림 데이터를 효과적으로 처리할 수 있음

<br>

### 메시지 큐 종류
#### ActiveMQ(JMS)
- MOM을 자바에서 지원하는 표준 API이다. JMS는 다른 자바 애플리케이션들끼리 통신이 가능하지만 다른 MOM의 통신은 불가능 (AMQP, SMTP 같은)
- ActiveMQ의 JMS 라이브러리를 사용한 자바 애플리케이션들끼리 통신이 가능
- 하지만 다른 자바 애플리케이션(Non ActiveMQ)의 JMS와는 통신할 수 없음

<br>

#### RabbitMQ
- RabbitMQ는 AMQP(Advanced Message Queuing Protocol)를 구현한 오픈소스 메시지 브로커
- AMQP는 MQ를 오픈 소스에 기반한 표준 프로토콜이다. 프로토콜만 맞다면 다른 AMQP를 사용한 애플리케이션끼리 통신이 가능
- 플러그인을 통해서 SMTP, STOMP 프로토콜과의 확장이 가능함
- AMQP는 메세지 전달을 아래 3가지 방식 중 하나를 보장
    - At-Most-Once: 각 메시지는 한번만 전달되거나 전달되지 않음
    - At-Least-Once: 각 메시지는 최소 한번 이상 전달됨을 보장
    - Exactly-Once: 각 메시지는 딱 한번만 전달됨
- AMQP는 메시징 제공자와 클라이언트의 동작에 대해 각기 다른 벤더들의 구현체가 상호 운용될 수 있는 정도로까지 권한을 줌
- 이는 SMTP, HTTP, FTP 등이 상호 운용이 가능한 시스템을 만든다는 점에서 동일
- 간단히 말하면 Exchange가 Producer로부터 메시지를 받고 Queue에 전달. Queue는 Consumer에게 메시지를 전달함

<br>

#### Apache Kafka
- Apache Kafka는 LinkedIn이 개발하고 Apache Software Foundation에 기부한 오픈 소스 스트림 프로세싱 소프트웨어 플랫폼
- 높은 처리량을 요구하는 실시간 데이터 피드 처리나 대기 시간이 짧은 플랫폼을 제공하는 것을 목표로 하며 TCP 기반 프로토콜을 사용
- 클러스터를 중심으로 Producer와 Consumer가 데이터를 Push하고 Pull하는 구조를 가짐
- 특징으로는 다음과 같은 특징을 가짐
    - Publisher / Subscriber 모델
    - 고가용성
    - 확장성
    - 디스크 순차 저장 및 처리
    - 분산 처리 (Partitioning)
- 카프카는 내구성이 뛰어난 메시지 저장소로, 고객들은 메시지가 한 번 배달되면 대기열에서 제거되는 전통적인 메시지 중개업자들과는 달리, 필요에 따라 이벤트 스트림을 재생 가능
- 대용량의 실시간 로그 처리에 특화되어 설계된 메시징 시스템으로써 기존 범용 메시징 시스템대비 TPS(Transaction per second)가 매우 우수하다. 단, 특화된 시스템이기 때문에 범용 메시징 시스템에서 제공하는 다양한 기능들은 제공되지 않음
- 분산 시스템을 기본으로 설계되었기 때문에 기존 메시징 시스템에 비해 분산 및 복제 구성을 손쉽게 할 수 있음
- AMQP 프로토콜이나 JMS API를 사용하지 않고 단순한 메시지 헤더를 지닌 TCP기반의 프로토콜을 사용하여 프로토콜에 의한 오버헤드를 감소시킴
- Producer가 Broker에게 다수의 메시지를 전송할 때 각 메시지를 개별적으로 전송해야하는 기존 메시징 시스템과는 달리, 다수의 메시지를 batch형태로 Broker에게 한 번에 전달할 수 있어 TCP/IP 라운드 트립 횟수를 줄일 수 있음
- 메시지를 기본적으로 메모리에 저장하는 기본 메시징 시스템과는 달리 메시지를 파일 시스템에 저장 (RabbitMQ는 ram or disk 선택가능)
- 파일 시스템에 메시지를 저장하기 때문에 별도의 설정을 하지 않아도 데이터의 영속성이 보장됨
- 기존 메시징 시스템에서는 처리되지 않고 남아있는 메시지의 수가 많을수록 시스템의 성능이 크게 감소하였으나, Kafka에서는 메시지를 파일 시스템에 저장하기 때문에 메시지를 많이 쌓아두어도 성능이 크게 감소하지 않음. 또한 많은 메시지를 쌓아둘 수 있기 때문에, 실시간 처리뿐만 아니라 주기적인 batch 작업에 사용할 데이터를 쌓아두는 용도로도 사용할 수 있음
- Consumer에 의해 처리된 메시지(ack)를 곧바로 삭제하는 기존 메시징 시스템과는 달리 처리된 메시지를 삭제하지 않고 파일 시스템에 그대로 두었다가 설정된 수명이 지나면 삭제함. 처리된 메시지를 일정 기간동안 삭제하지 않기 때문에 메시지 처리 도중에 문제가 발생하였거나 처리 로직이 변경되었을 경우 Consumer가 메시지를 처음부터 다시 처리(rewind)하도록 할 수 있음
- 기존의 메시징 시스템에서는 Broker가 Consumer에게 메시지를 Push해주는 방식인데 반해, Kafka는 Consumer가 Broker로부터 직접 메시지를 가지고 가는 pull(poliing)방식으로 동작함. 따라서 Consumer는 자신의 처리능력만큼의 메시지만 Broker로부터 가져오기 때문에 최적의 성능을 낼 수 있음
- 메시지를 Pull 방식으로 가져오므로, 메시지를 쌓아두었다가 주기적으로 처리하는 Batch Consumer의 구현이 가능함
- 큐의 기능은 JMS, AMQP 기반의 RabbitMQ등에 비해서는 많이 부족하지만 대용량 메시지를 지원할 수 있는 것이 가장 큰 특징이다. 특히 분산 환경에서 복사본을 다른 노드에 저장함으로써 노드 장애에 대한 장애 대응성을 가지고 있는 강점이 있음

<br>

#### Redis
- 레디스는 단순 키-값 구조의 인메모리 데이터베이스를 지원할 뿐 아니라 메시지 큐로써의 기능도 함께 지원하고 있음. 메시지 브로커로 메모리 기반의 딕셔너리 구조 데이터 관리 시스템
- 레디스 큐는 레디스의 자료구조 중 List를 이용하여 Queue를 구현한 것
- 처리속도가 빠르고 캐시의 역할도 가능하며 명시적으로 데이터 삭제가 가능함
- 메시지 큐라면 당연히 송신자와 수신자가 필요하므로 최신 버전의 Spring boot는 Redis 사용시 굳이 RedisTemplate를 설정할 필요가 없지만 메시지 큐 사용을 위해서는 어느정도의 설정은 필요

<div style="text-align: right">22-12-29</div>

-------

## Reference
- https://velog.io/@kimjaejung96/메세지-큐Message-Queue란-무엇인가
- https://sorjfkrh5078.tistory.com/291
- https://goyunji.tistory.com/125
- https://hyeo-noo.tistory.com/334