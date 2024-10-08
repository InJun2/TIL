# CHAPTER12 분산 시스템

## 1. 네트워크와 인터넷
- 통신이란 쉽게 말해 의사소통하는 것을 뜻함
    - 모스 부호: 전기를 이용한 최초의 통신 수단, 미리 정해놓은 짧은 음과 긴 음을 조합하여 메시지를 만들어 전송
    - 전화: 통신하려는 대상을 선으로 연결, 음성을 전달
    - 무전기: 최초의 무선통신 기기로, 양쪽으로 통신할 수 있으나 어느 시점에는 한쪽으로만 통신이 가능

<br>

### 네트워크 환경의 발전
- 네트워크는 유무선통신 기술을 이용하여 여러 기기를 하나로 연결한 것
- 일상생활에서 가장 많이 사용하는 네트워크는 전화망이다. 전화망은 기술의 발달과 함께 무선 전화망으로 발전했다. 무선 전화망은 1세대부터 5세대까지 다음과 같은 과정을 거쳐 진화하였음
    - 1세대 무선 전화망: 초기의 무선 전화망은 아날로그 신호만 전송
    - 2세대 무선 전화망: 디지털 신호를 전송한다. 아날로그 신호보다 효율성이 좋음
    - 3세대 무선 전화망: 기존의 전화 기능에 데이터 통신 기능이 추가
    - 4세대 무선 전화망: 데이터 통신에 대해 데이터 전송 속도를 높임
    - 5세대 무선 전화망: 초고속 무선 통신이 가능

<br>

### 네트워크 구성 방식
- 네트워크는 구성 방식에 따라 강결합 시스템과 약결합 시스템으로 나눌 수 있음
- 강결합 시스템
    - 네트워크로 연결된 모든 컴퓨터의 프로세서가 하나의 메모리를 공유하는 방식
    - 모든 컴퓨터는 메모리를 공유하면서 같은 운영체제를 사용하는데, 강결합 시스템은 약결합 시스템에 비해 속도가 빠름
- 약결합 시스템
    - 둘 이상의 독립된 시스템을 연결한 것으로, 오늘날의 네트워크는 이 방식으로 이루어져 있다.
    - 약결합 시스템은 통신 오버헤드가 있기 때문에 강결합 시스템보다 느리지만 강결합 시스템에서는 하나의 시스템에 문제가 생기면 다른 시스템에도 영향을 미치지만 약결합 시스템에서는 컴퓨터들이 서로 독립적으로 작동하기 때문에 하나의 시스템에 장애가 발생해도 다른 시스템에 영향을 미치지 않음

<br>

### 프로토콜
- 다른 기기 간에 통신을 하기 위해 정한 규약으로 우리말로는 '통신규약'
- 하드웨어가 서로 연결되어있어도 네트워크를 구성하려면 소프트웨어적으로 프로토콜을 마련해야함
    - HTTP (Hypertext Transfer Protocol)
    - FTP (File Transfer Protocol)
    - TCP/IP (Transmission Control Protocol/Internet Protocol)
    - SMTP (Simple Mail Transfer Protocol)
    - DHCP (Dynamic Host Configuration Protocol)
    - UDP (User Datagram Protocol)

<br>

### 인터넷
- 기존 네트워크는 가까운 거리에 연결된 네트워크라는 의미로 LAN(Local Area Network) 이라고 지칭
    - LAN (Local Area Network) : 가까운 거리에 연결된 네트워크
    - WAN (Wide Area Network) : 국가 전체를 연결하거나 국가 간에 연결되어 있는 네트워크
- LAN을 구성하는 방법은 많은데 이러한 LAN의 구조를 토폴로지(topology)라고 하며 현재까지 사용되는 것은 모양에 따라 스타형, 링형, 버스형으로 나뉨
    - 스타형 : 중간에 네트워크를 관장하는 시스템을 두고 방사형으로 기기를 연결
    - 링형: 모든 기기를 원형으로 연결
    - 버스형: 중앙의 버스에 독립적으로 기기를 붙여 네트워크를 구성
- 링형의 경우 데이터 전송 프로토콜로 IBM이 개발한 토큰링(token ring)방식을 사용하며 이런 토큰링을 사용하는 LAN을 FDDI(Fiber Distributed Data Interface)라고 이름 붙임
- 버스형에서는 데이터 전송을 위한 프로토콜로 CSMA/CD(Carrier Sense Multiple Access/Collision Detect) 방식을 사용
    - 제록스는 버스형에 CSMA/CD 프로토콜을 사용하는 LAN을 이더넷(ethernet)이라고 이름 붙임

<br>

### 인터넷 등장과 발전
- 기존 LAN은 규격이 제각각이고 토폴로지나 프로토콜간 호환성이 없었고 이를 해결하기 위해 서로 호환되지 않는 LAN을 묶어 하나의 네트워크로 만들기 위한 ARPA 연구를 진행하여 아르파넷을 개발
- 아르파넷은 여러 종류의 LAN을 선으로 연결해 하나의 네트워크로 만들고난 후 LAN 사이에 데이터 전송을 위한 프로토콜을 설계했는데 이것이 IP
    - 당시 IP는 목적지까지 데이터를 보내는 일은 훌륭히 수행했으나 데이터가 원래대로 전달되지 않거나 없어지고는 했음. 이를 보완하여 개발된 것이 TCP/IP
    - 이후 아르파넷은 전 세계적으로 확장되어 오늘날의 인터넷이 되었음. 
- 이후 마크 앤드리슨과 에릭비나가 모자이크(Mosaic)를 개발하여 텍스트 위주 정보 전달 방식에서 벗어나 화면에 그래픽을 사용하며 링크를 걸어 다른 화면과 연결하는 기능을 제공하는 하이퍼텍스트 기능도 제공했음
- 모자이크를 개발한 앤드리슨은 이후 넷스카이프를 세우고 마이크로소프트가 인터넷 익스플로러를 개발하고 윈도우에 포함하여 무료로 배포하며 웹 브라우저가 급속도로 보급되었고 웹 브라우저를 이용한 서비스는 월드 와이드 웹(World Wide Web)이라 함

<br>

## 2. 분산시스템
- 개인용 컴퓨터의 보급으로 값이 싸고 크기가 작은 컴퓨터를 네트워크로 묶어 대형 컴퓨터 같은 능력을 가진 시스템
- 분산 시스템은 중앙 처리 시스템과 반대되는 개념으로 네트워크 상에 분리되어 있는 컴퓨터가 작업을 처리하고 그 내용이나 결과를 결합함. 약결합의 분산시스템의 장점은 다음과 같음
    - 네트워크로 연결된 기기가 여러 자원을 공유할 수 있다.
    - 작업 분배를 통해 여러 기기가 작업을 나누어 처리할 수 있다.
    - 데이터나 처리를 분산함으로써 연산 속도를 향상할 수 있다.
    - 장애가 발생해도 시스템을 복구할 수 있다.
- 분산 시스템에서 고려사항은 각 기기의 독립성이 보장되어야하고 사용자는 시스템을 하나의 기기로 인식할 수 있어야함
- 분산 시스템 운영체제는 네트워크 운영체제와 분산 운영체제가 있음
    - 네트워크 운영체제 : 각 컴퓨터가 독자적인 운영체제를 가진 채 사용자 프로그램을 통해 분산 시스템이 구현된 것으로, 낮은 수준의 분산 시스템 운영체제라고 볼 수 있다.
    - 분산 운영체제 : 시스템 내에 하나의 운영체제가 존재하고, 전체 네트워크를 통틀어서 단일 운영체제로 운영된다. 사용자가 시스템 내 기기의 종류를 알 필요가 없고 전체 시스템을 일관성 있게 설정할 수 있다. 네트워크의 이해, 유지, 수정이 용이하다.

<br>

### 클라이언트/서버 시스템
- 완전한 분산 시스템은 각 컴퓨터가 독립적으로 구성되어 시스템을 구성하는데 문제가 많고 하나의 컴퓨터가 작업을 처리하지 못하면 작업을 옮기고 전체 작업 결과를 하나로 합쳐야하는데 어려움
- 이에 따라 클라이언트/서버 시스템은 작업을 요청하는 클라이언트와 요청받은 작업을 처리하는 서버의 이중 구조로 되어있음
- 웹 시스템 동작 과정은 다음과 같음
    1. 클라이언트가 웹 브라우저에 서버 주소를 입력
    2. 웹 브라우저는 HTTP를 이용하여 서버에 있는 HTML을 요청
    3. 서버는 클라이언트의 요청을 처리한 후 결과를 클라이언트에 전달
    4. 결과가 클라이언트에 도착하면 웹 브라우저가 화면에 출력
- 서버는 계속 멈추지 않고 클라이언트의 요청을 처리하는데 이와 같이 멈추지 않고 계속 작동하는 프로그램을 데몬(demon)이라고 함
    - 웹 데몬으로 많이 사용되는 소프트웨어는 IIS, 아파치 톰캣 등

<br>

### CGI(Common Gateway Interface)와 가상머신
- 초기 HTML 화면은 고정된 페이지로 구성되어 동적인 데이터를 표시하는 것이 안됬으나 동적인 데이터를 HTML에 삽입하려면 프로세스에 질문하고 결과값을 HTML 형태로 웹 데몬에 전달하는 프로세스가 필요했는데 이를위해 개발된 것이 CGI
- 초기의 CGI는 C, 펄, 오크 같은 프로그래밍언어를 사용해 간단한 작업만이 가능하였으나 웹 시스템이 발전하면서 많은 작업을 웹에서 하게 되었음
    - 그에따라 데이터베이스와 웹 서버의 연결 작업에 CGI를 사용하기 시작
    - 대표적인 예로 ASP, PHP, JSP가 있음
- 클라이언트/서버 시스템이 발전함에 따라 미들웨어 개념도 등장
    - 미들웨어 : 양쪽을 연결하여 데이터를 주고받을 수 있도록 중간에서 매개 역할을 하는 소프트웨어
    - 미들웨어를 사용하는 클라이언트/서버를 3-tire 클라이언트/서버, 사용하지 않는 클라이언트/서버를 2-tire 클라이언트/서버라고 함
- 운영체제와 관련된 미들웨어로는 가상머신이 있으며 객체지향언어인 자바는 다른 운영체제에서도 코드 수정없이 사용할 수 있도록하는 자바 가상 머신(JVM)이 존재

<br>

### P2P 시스템
- 클라이언트/서버의 서버 과부하로 인해 P2P 시스템이 설계됨
- P2P는 크게 비구조적 P2P와 구조적 P2P로 구성
    - 비구조적 P2P : 전체 노드에 관한 정보는 서버가 가지고 있고 실제 데이터 전송을 일대일로 연결된 말단 노드를 통해 이루어지는 구조. 데이터를 보내는 프로그램이 중단되면 받는쪽은 데이터를 내려받지 못함
    - 구조적 P2P : 전체 네트워크에 대한 정보를 모든 노드에 저장하여 관리하거나 하나의 노드에 집중 저장하여 관리. 특정 파일의 소유자 정보를 여러 노드가 공유함으로써 시스템의 한 노드가 사라져도 데이터 공유가 지속적으로 이루어짐. 주요 프로그램으로는 토렌트가 있음
- 토렌트 시스템에서 원본 파일을 가진 컴퓨터를 시드(seed)라고 부르며 최초의 시드로부터 파일이 전송되고 여러 사람들이 같은 파일을 가지게 됨으로써 시드가 늘어남
    - 시드를 중심으로 데이터를 여러 노드가 주고받는 방식을 구조적 P2P라고 하며 그리드 컴퓨팅 환경에서는 그리드 딜리버리라고 함

<br>

### 클라우드 컴퓨팅
- 그리드 컴퓨팅은 이기종의 컴퓨터를 묶어 대용량 컴퓨터 풀을 구성하고 이를 원격지로 연결하여 대용량 연산을 수행하는 컴퓨팅 환경
- 그리드 컴퓨팅을 구성하려면 CPU관리, 저장소 관리, 보안 조항, 데이터 이동, 모니터링 등에 대한 표준 규약이 필요
    - 그리드는 계산 그리드, 데이터 그리드, 엑세스 그리드로 나뉨
    - 기존 저장장치와 서버를 그리드로 묶어 사용하려면 미들웨어가 필요한데 책은 글로버스를 사용한 컴퓨팅 구조를 보여줌

<br>

### 클라우드 컴퓨팅 환경의 이해
- SaaS (Software as a Service) : 소프트웨어형 서비스. 소프트웨어는 중앙에 호스팅, 사용자는 웹,앱 등 클라이언트로 접속하여 소프트웨어를 서비스 형태로 이용하는 서비스
- PaaS (Platform as a Service) : 플랫폼형 서비스. 필요한 개발 요소를 빌려쓸 수 있고 애플리케이션 개발, 실행 플랫폼
    - PaaS-TA : 국내 IT 서비스 경쟁력 강화를 목표로 개발되었으며 인프라 제어 및 관리환경, 실행환경, 개발환경, 서비스환경, 운영환경으로 구성되어있는 개방형 클라우드 컴퓨팅 플랫폼
- IaaS (Infrastructure as a Service) : 인프라형 서비스. 서버, 스토리지 같은 시스템 자원을 클라우드로 제공하는 서비스

<br>

![IaaS vs PaaS vs SaaS](./img/iaas-paas-saas.jpeg)

<br>

---

### 연습문제
#### 1. 이더넷이란?

<details>
<summary>정답</summary>

- LAN(근거리 통신망) 구축을 위해 장치를 연결하기 위해 개발된 유선 네트워크 통신망 기술
    - 컴퓨터 네트워크에서 장치들을 유선으로 연결한다고 생각
- 이더넷은 LAN(Local Area Network)를 위해 개발된 근거리 유선 네트워크 통신망 기술이며 이더넷 프로토콜은 네트워크에서 데이터를 전송하는 방법을 규정하는 규약으로 IEEE 802.3에 표준으로 정의되어 있음
- 이더넷은 CSMA/CD(반송파 감지 다중 접속/충돌 탐지) 프로토콜을 기반으로 구축되어 여러 대의 장치가 동시에 데이터 전송을 시도하는 경우 데이터의 충돌을 방지한다고 함
    - 호스트가 채널의 상태를 감지하여 충돌을 회피하는 네트워킹 방식으로 누군가 이미 채널을 사용하고 있다면 Carrier가 감지되고 감지되지 않으면 아무도 사용하지 않는 것으로 판단하여 통신을 시도
    - 동시에 다른 호스트도 데이터를 전송하는 경우 다중 접근이라고 하며 해당 경우 데이터 전송이 제대로 이루어지지 않아 호스트는 데이터를 전송한 후 충돌이 일어나지 않았는지 체크하게 되는데 이를 Collison Detection이라 부름
    - MAC(Media Acess Control Address)를 통해 이더넷 인터페이스를 특정
- 네트워크를 만드는 방식에는 이더넷 말고도 과거에는 토큰링, FDDI, ATM 등의 방식들이 있었으나 현재는 이더넷이 전 세계적으로 가장 많이 사용되는 네트워크 방식

</details>

<br>

#### 2. 분산 시스템의 고가용성에서 상시 대기, 상호 인계, 컨커런트 액세스가 있는데 컨커런트 엑세스에서 한 시스템에서 장애가 발생해도 고가용성을 보장하는 이유

<details>
<summary>정답</summary>

- 여러 시스템이 동시에 업무를 나누어 병렬처리를 진행하는데 시스템 전체가 가동 상태로 업무를 수행하기 때문에 한 시스템에 장애가 발생하더라도 다른 시스템으로 동작하고 있음
- 클러스터링이나 로드 밸런싱과 같은 기술을 사용하여 작업이 중단된 시스템을 자동으로 감지하고, 해당 작업을 다른 시스템으로 이동시키는 것이 가능
- 이 구조에서는 두 클러스터가 동일한 업무를 수행하기 위해 L4 스위치를 이용하여 작업분배를 진행한다고 함

```
클러스트링
- 여러 컴퓨터 시스템이 함께 작동하여 단일 시스템처럼 동작하도록 구성된 컴퓨팅 리소스의 그룹을 의미

고가용성 클러스트링
- 시스템이 장애가 발생했을 때 중단 없이 작동할 수 있도록 설계
- 여러 대의 서버가 동일한 애플리케이션 또는 서비스를 실행하고, 하나의 서버에 장애가 발생하면 다른 서버가 자동으로 작업을 이어받아 서비스의 지속성을 보장

부하 분산 클러스트링
- 부하를 분산시켜 성능을 향상시키는 데 사용. 여러 대의 서버가 동일한 애플리케이션 또는 서비스를 실행하고, 클라이언트 요청이 도착하면 부하 분산 장치가 이 요청을 여러 서버로 분산시켜 처리
- 이를 통해 각 서버의 부하를 균형있게 분배하여 전체 시스템의 성능을 향상
```


</details>

<br>