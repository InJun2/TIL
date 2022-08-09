### IP(Internet Protocol)
- 지정한 IP 주소에 데이터를 전달
- 패킷이라는 통신 단위로 데이터 전달
- IP 프로토콜은 한계 존재
    - 비연결성 : 패킷을 받을 대상이 없거나 서비스 불능 상태여도 패킷 전송
    - 비신뢰성 : 중간에 패킷이 사라지거나 패킷이 순서대로 오지 않을 경우 존재 
    - 프로그램 구분 : 같은 IP를 사용하는 서버에서 통신하는 애플리케이션이 둘 이상일 경우

<br>

### TCP (Transmission Control Protocol)
- 연결지향
- 데이터 전달 보증 및 순서 보장. 신뢰할 수 있는 프로토콜 -> 3way handshake를 이용

### UDP (User Datagram Protocol)
- 데이터 전달 보증 X, 순서 보장 X
- TCP 와 같이 신뢰성이 있는 프로토콜은 아니지만 단순하고 빠름. IP와 거의 유사하나 Port와 체크섬 정도만 사용

<br>

### DNS (Domain Name System)
- 전화번호부
- 도메인 명을 IP 주소로 변환

<br>

### URI, URL
#### URI (Uniform Resource Identifier)
- 리소스 식별하는 통일된 방식
- 자원, URI로 식별할 수 있는 모든 것 (제한 없음)
- 다른 항목과 구분하는데 필요한 정보
#### URL (Uniform Resource Locator)
- 리소스가 있는 위치를 지정
- 위치는 변할 수 있지만 이름은 변하지 않음