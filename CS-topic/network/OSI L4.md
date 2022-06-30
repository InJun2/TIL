# OSI L4

### L4 Swich?
- OSI 4계층에서 이용하는 스위치로 L4 라고 함
- 서버의 부하 분산([로드밸런싱 설명 링크](./Load%20Balancing.md))을 처리하는 장비  
- 외부에서 들어오는 모든 요청을 L4 스위치가 받아 서버들에게 적절히 나누어줌
- TCP, UDP, HTTP와 같은 Protocol들의 Header를 분석하여 그 정보를 바탕으로 부하 분산을 실시하고 거기에 더해 Source IP 혹은 Destination IP를 NAT(Network Address Translation)하여 보낼 수 있음
- 클라이언트와 서버가 '3-way handshake'를 거쳐 논리적 연결이 생성되었음을 나타내는 Connection을 생성하면 중간자 역할의 L4 스위치 역시 Connectino을 생성하여 리스트를 관리. ( 이 과정에서 3-way handshake 또한 L4 스위치를 통해 실시됨 )
- 논리적 연결을 통해 데이터를 주고받던 서버 혹은 클라이언트가 4-way handshake(연결종료과정)를 실시하여 Connection을 제거하면 4-way handshake의 중재자인 L4 스위치 또한 Connection을 삭제.
- L4 스위치의 Connection은 Connection time out 값을 가지는데  

### L4의 역할

> 공인 IP, 내부 IP? L4가 필요한 이유
    >- 본래는 클라이언트에서 서버의 공인 IP로 요청을 보내는데 여러개의 서버가 있을 경우 혹은 서버를 추가했을경우는 어려워짐. 
    >- 서버가 여러대 일경우 부하를 분산하는것이 로드밸런싱인데 이 행동을 L4에서 진행함. 
    >- 이제 클라이언트는 서버로 요청을 보내려면 어떤 요청이던지 모든 요청은 L4로 보내지고 L4는 로드밸런싱을 통해 알맞는 서버로 요청을 전달함. 여기서 클라이언트는 이에 알맞는 서버에 대한 IP는 알수없고 L4 스위치의 외부 망인 공인 IP로 요청을 보내는 것만이 가능. 그렇게 공인 IP로 요청이 들어오면 스위치는 서버 내부 망인 사설 IP를 가지고 있는데 L4가 알아서 사설 IP로 요청을 보낸다. 이렇게 L4 내에서 안밖으로 IP가 변환됨

## Reference

- https://aws-hyoh.tistory.com/entry/L4-Switch-쉽게-이해하기