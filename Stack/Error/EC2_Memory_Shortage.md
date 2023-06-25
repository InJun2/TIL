# EC2 메모리 부족

### 문제 발생
- glass-bottle 프로젝트를 진행하던 중 ec2 인스턴스에서 스프링 부트 프로젝트를 실행하는 중 멈춤 현상이 발생. Compile Java 에서 20~60초 가량 시간만 증가하고 작동하지 않았음
- 해당 문제의 원인은 ec2에 있었음. 현재 프로젝트는 프리티어로 진행하고 있는데 1GB의 메모리, 30GB의 스토리지가 최대인데 램 부족 현상으로 인해 서버가 자주 다운되는 현상이 일어난다고 함
- 해당 방법 해결을 위해 리눅스에서 제공하는 RAM Swap 기능을 사용하였음

<br>

### RAM Swap
- 스왑 메모리란 실제 메모리 RAM은 가득 찼지만 더 많은 메모리가 필요할 때 디스크 공간을 이용하여 부족한 메모리를 대체할 수 있는 공간을 의미
- 실제 디스크 공간을 메모리처럼 사용하는 개념이기 때문에 가상 메모리라고 할 수 있음
- 그렇기 때문에 속도 측면에서는 메모리보다 현저히 떨어짐
- 리눅스 커널은 실제 메모리에 올라와 있는 메모리 블록들 중 당장 쓰이지 않는 것을 디스크에 저장하고, 이를 통해 사용 가능한 메모리 영역을 늘림

<br>

### Memory Swap 방법
- 우선 swapon -s 또는 free -h 명령어를 통해 Swap 메모리를 확인
    - total에서 buff/cache와 free를 뺀 메모리(used)는 사용 중 메모리를 의미
    - 공유 메모리(Shard)는 약 0~1MB를 사용하고 있는데, 공유 메모리란 하나의 프로세스에서 다른 프로세스의 메모리를 사용하고 싶을 때 사용하는 메모리
    - 현재 할당된 Swap 메모리는 0
- Swap Space 생성하기 위한 다음 명령어를 순서대로 입력
    - sudo fallocate -l 2G /swapfile
    - sudo chmod 600 /swapfile
    - sudo mkswap /swapfile
- RAM Swap
    - sudo swapon /swapfile
- 이후 아래 명령어를 통해 스왑 생성에 대한 확인
    - sudo swapon --show

<br>

### RAM Swap 자동 활성화
- EC2 인스턴스를 재부팅하게 되면 다시 초기화되기 때문에 재부팅하더라도 자동으로 RAM Swap이 활성화되도록 설정
- RAM Swap 자동 활성화는 다음과 nano 에디터를 통해 설정
- sudo nano /etc/fstab 명령어를 사용하여 nano 에디터를 열어 끝부분에 /swapfile swap swap defaults 0 0 
명령어를 추가

<br>

### 참조링크
- https://velog.io/@timointhebush/Java-Spring-프로젝트-배포-도전기1
- https://ittrue.tistory.com/297
- https://jw910911.tistory.com/122