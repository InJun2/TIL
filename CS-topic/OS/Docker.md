# Docker

### Docker
- 컨테이너 기술을 기반으로 한 일종의 가상화 플랫폼
- 가상화란 물리적 자원인 하드웨어를 효율적으로 활용하기 위해서 하드웨어 공간 위에 가상의 머신을 만드는 기술이고 컨테이너란 컨테이너가 실행되고 있는 호스트 OS의 기능을 그대로 사용하면서 프로세스를 격리해 독립된 환경을 만드는 기술
- 독립된 환경을 만들어서 하드웨어를 효율적으로 활용하는 기술
    - 컨테이너를 잘 다룰 수 있도록 도와주는 도구
    - 도커를 이용하여 쉽게 이미지를 실행시켜 컨테이너로 만들거나, 생성된 컨테이너를 관리하거나 컨테이너를 다시 이미지로 만드는 작업을 쉽게 할 수 있음
    - 개발 과정에서 다른 라이브러리와 충돌하는 것을 방지하기 위해 격리된 환경이 필요할 때, 완성된 서비스를 배포할 때, 혹은 배포 중인 서비스를 받아서 실행해볼 때도 유용

<br>

### 가상화와 컨테이너
#### 가상화
- 가상화는 하나의 하드웨어를 여러 개의 가상 머신으로 분할해 효율적으로 사용할 수 있는 기술이고 분할된 가상 머신들은 각각 독립적인 환경으로 구동되는데 이때 베이스가 되는 기존의 환경을 Host OS, 그리고 가상 머신으로 분할된 각각의 환경을 Guest OS라고 부름
- 가상 머신을 생성하기 위해서는 하이퍼바이저 또는 가상 머신 모니터라고 불리는 소프트웨어를 이용
- 하이퍼바이저는 호스트 하드웨어에 설치되어 호스트와 게스트를 나누는 역할을 하고, 각각의 게스트는 하이퍼바이저에 의해 관리되며 시스템 자원을 할당받게 됨
- 이때 하이퍼바이저에 의해 생성된 게스트는 호스트나 다른 게스트와 상호 간섭하지 않고 완전히 분리된 환경에서 구동됨
- 하이퍼바이저를 활용하면 마치 하드웨어가 여러 개인 것처럼 하나의 서버를 여러 명이 나눠쓸 수도 있고, 컴퓨터 한 대에서 서로 다른 OS를 동시에 사용할 수도 있음
- 가상 머신으로 동작을 시키려면 반드시 하이퍼바이저를 거쳐야하기 때문에 속도 저하가 필연적이고 또 가상 머신은 해당 환경을 구동하는데 필요한 파일을 모두 포함하고 있기 때문에 가상 머신을 배포할 때 만들어지는 이미지의 크기가 매우 커진다는 한계점이 있음

<br>

#### 컨테이너
- 컨테이너는 가상의 OS를 만드는 것이 아니라 베이스 환경의 OS를 공유하면서 필요한 프로세스만 격리하는 방식으로 커널을 공유하기 때문에 호스트 OS의 기능을 모두 사용할 수 있음
    - 우리가 사용할 OS를 가상화하지 않고 단순히 프로세스를 격리하여 호스트 입장에서는 단순히 프로세스가 실행되는 것으로 보이고 필요만 만큼만 CPU나 메모리가 사용되어 훨씬 빠르고 효율적
    - 그렇기 때문에 컨테이너 위에서는 호스트 OS와 다른 OS를 구동할 수 없음
- 대신 격리시킬 애플리케이션과 거기에 필요한 파일이나 특정 라이브러리 등 종속 항목만 포함하기 때문에 배포를 위해 생성되는 이미지의 용량이 작아지는 장점이 있음
- 운영체제가 아닌 프로세스이며 하이퍼바이저를 거칠 필요가 없어 실행 속도가 빠름

<br>

#### 이미지
- 가상 머신이나 컨테이너 또는 프로그램을 실행하는 데 필요한 파일과 라이브러리, 설정 등을 가지고 있는 파일
- 이미지는 레이어라는 계층 구조로 이루어져 있는데 변경 사항이 생기면 새로운 레이어를 추가해서 기록
- 이미지 전체를 새로 받지 않고 해당 레이어만 받는 것으로 이미지를 업데이트할 수 있는 장점이 있음
- 이미지를 실행하면 프로세스, 즉 컨테이너가 됨

<br>

### 도커를 사용하는 이유
- 컨테이너 기반 오픈소스 가상화 플랫폼으로 다양한 이유로 계속 바뀌는 서버환경과 개발 환경 문제를 해결하기 위해 등장
- 도커 허브에 올라온 이미지와 docker-compose.yml의 설정으로 원하는 프로그램을 편안하게 설치/제거가 가능
- 하나의 서버에 포트만 변경하여 동일한 프로그램을 실행하기도 쉬워서 환경변수나 경로등의 충돌을 신경쓰지 않아도됨
- 서로 다른 프로그램이더라도 컨테이너로 규격화되어있어 컨테이너로 만들 수 있고 어떤 환경에서도 돌아감
- 가상머신과 비슷하게 생각할 수 있으나 가상머신보다 빠르고 쉽고 효율적임. 도커는 컴퓨터 자원을 그대로 사용하고 가상머신은 독립된 메모리를 할당받음

<br>

### 도커의 특징
- 확장성과 이식성
    - 도커가 설치되어 있다면 어디서든 컨테이너를 실행할 수 있음
    - 오픈 소스이기에 특정 회사나 서비스에 종속적이지 않음
    - 쉽게 개발서버를 만들 수 있고 테스트 서버 생성도 가능
- 표준성
    - 도커를 사용하지 않는 경우, 각각의 언어로 만든 서비스들의 배포 방식은 모두 다름
    - 도커는 컨테이너라는 표준으로 서버를 배포하므로 모든 서비스들의 배포 과정이 동일해짐
- 이미지
    - 컨테이너를 실행하기 위한 압축파일과 같은 개념
    - 이미지에서 컨테이너를 생성하기 떄문에 반드시 이미지를 만드는 과정이 필요
    - Dockerfile을 이용하여 이미지를 만들고 처음부터 재현 가능
    - 빌드 서버에서 이미지를 만들면 해당 이미지를 이미지 저장소(허브)에 저장하고 운영서버에서 이미지를 불러와 사용
- 설정관리
    - 도커에서 설정은 보통 아래와 같이 환경변수로 제어
    - MYSQL_PASS=password와 같이 컨테이너를 띄울 때 환경변수를 같이 지정
    - 하나의 이미지가 환경변수에 따라 동적으로 설정파일을 생성하도록 만들어져야함
- 자원관리
    - 컨테이너는 삭제 후 새로 만들면 모든 데이터가 초기화됨 (제거가 쉬움)
    - 그러므로 저장이 필요하다면, 업로드 파일을 외부 스토리지와 링크하여 사용하거나 S3같은 별도의 저장소가 필요
    - 세션이나 캐시를 memcached나 redis와 같은 외부로 분리

<br>

<div style="text-align: right">22-10-10</div>

-------

## Reference
- https://velog.io/@markany/도커에-대한-어떤-것-1.-도커란-무엇인가
- https://wooody92.github.io/docker/Docker-도커란-무엇인가/