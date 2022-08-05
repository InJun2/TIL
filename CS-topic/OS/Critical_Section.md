# Critical Section

#### 공유자원 (Shared Resource)
- 여러 프로세스가 공동으로 이용하는 변수, 메모리, 파일 등을 말함
- 공유 자원은 공동으로 이용되기 때문에 누가 언제 데이터를 쓰느냐에 따라 그 결과가 달라질 수 있음
- 프로세스들의 공유 자원 접근 순서를 정하여 예상치 못한 문제가 발생하지 않도록 해야함

<br>

#### 경쟁 조건(Race Condition)
- 2개 이상의 프로세스가 공유 자원을 병렬적으로 읽거나 쓰는 상황
- 경쟁 조건이 발생하면 공유 자원 접근 순서에 따라 실행 결과가 달라질 수 있음

<br>

### 임계 구역(Critical Section)
- 공유 변수 영역이라고도 하며, 병렬프로그래밍에서 둘 이상의 프로세스가 동시에 접근해서는 안되는 공유자원 영역
- 공유 자원 접근 순서에 따라서 실행 결과가 달라지는 프로그램의 영역
- 임계구역에서는 프로세스들이 동시에 작업하면 안됨
- 전역 변수 뿐만 아니라 하드웨어 자원을 사용할 때도 적용되는 개념

<br>

#### 임계 구역 해결 조건
1. 상호 배제(Mutual Exclusion)
    - 한 프로세스가 임계구역에 들어가면 다른 프로세스는 임계구역에 들어갈 수 없음
2. 한정 대기(Bounded Waiting)
    - 어떤 프로세스도 무한대기하지 않아야함. 특정 프로세스가 임계구역에 진입하지 못하면 안됨
3. 진행의 융통성(Progress Flexibility)
    - 한 프로세스가 다른 프로세스의 진행을 방해해서는 안됨

<br>

#### 코드 구역
1. 입장 구역(Entry Section)
    - 각 프로세스가 자신의 임계구역에 진입하기 위해서 진입허가 요청을 구현하는 코드 영역
2. 임계 구역(Critical Section)
    - 입장 구역에서 진입허가가 나면 임계 구역에 진입
3. 퇴장 구역(Exit Section)
    - 임계 구역을 빠져나왔음을 알리는 코드 영역
4. 나머지 구역(Remainder Section)
    - 이 외에 다른 코드 부분들을 총칭하는 나머지 구역

<br>

### 임계 구역 해결 방법
#### 1. 피터슨 알고리즘 (Peterson)
- 변수 turn은 두 프로세스가 동시에 lock을 설정하여 임계구역에 못들어가는 상황에 대비하는 장치
- 두 프로세스가 동시에 lock을 설정해도 turn을 이용하여 다른 프로세스에게 양보
- 프로세스 수가 늘어나면 변수도 늘어나고 전체적인 알고리즘도 복잡해짐
- 바쁜 대기를 사용하여 자원을 낭비
```java
// 공유 변수
boolean lock1 = false;
boolean lock2 = false;
int turn = 1;

// 프로세스 1
lock1 = true;
turn = 2;
while(lock2 == true && turn == 2);
/*** 임계구역 ***/
lock1 = false;

// 프로세스 2
lock2 = true;
turn = 1;
while(lock1 == true && turn == 1);
/*** 임계구역 ***/
lock2 = false;
```

#### 2. 데커 알고리즘 (Dekker)
- 하드웨어 도움없이 임계구역 문제를 해결 가능
- 프로세스 수가 늘어나면 변수도 늘어나고 전체적인 알고리즘도 복잡해짐
- 바쁜 대기를 사용하여 자원을 낭비
```java
// 공유 변수
boolean lock1 = false;
boolean lock2 = false;
int turn = 1;

// 프로세스 1
lock1 = true;
while(lock2 == true) {
	if(turn == 2) {
		lock1 = false;
		while(turn == 2);
		lock1 = true;
	}
}
/*** 임계구역 ***/
turn = 2;
lock1 = false;

// 프로세스 2
lock2 = true;
while(lock1 == true) {
	if(turn == 1) {
		lock2 = false;
		while(turn == 1);
		lock2 = true;
	}
}
/*** 임계구역 ***/
turn = 1;
lock2 = false;
```
#### 3. 세마포어 알고리즘
- 앞의 알고리즘에 비해 간단하고 사용이 쉬우며 wake up 신호를 사용하여 바쁜 대기를 하지 않아도 됨
- P()와 V()의 내부 코드는 검사와 지정을 사용하여 분리 실행하지 않고 완전히 실행되게 해야함 -> 한정 대기 조건을 위해서
- 직접 변수를 조작하여 위험하고 사용자가 실수로 잘못된 사용으로 인해 임계 구역이 보호받지 못할 수 있음
```java
Semaphore(n); // 공유 가능한 자원의 수 = n;

// 잠금을 수행하는 코드 P()
P(); 
// if RS > 0 them RS = RS - 1
// else block()

/*** 임계구역 ***/

// 잠금 해제와 동기화를 같이 수행하는 코드 V()
V();
// RS = RS + 1
// wake_up()
```
#### 4. 모니터
- 공유 자원을 내부적으로 숨기고 공유 자원에 접근하기 위한 인터페이스만 제공하여 자원 보호, 프로세스간의 동기화
- 시스템 호출과 같은 개념
- 사용자 입장에서는 복잡한 코드를 실행하지 않아서 좋고, 시스템 입장에서는 임계구역 보호가 가능
- 임계 구역으로 지정된 변수나 자원에 접근하고자 하는 프로세스는 직접 P()나 V()를 사용하지 않고 모니터에 작업 요청

<br>

### 그외 정리 링크
- #### [교착상태](./DeadLock.md)
- #### [세마포어-뮤텍스](./Semaphore-Mutex.md)

<br>


<div style="text-align: right">22-08-05</div>

-------

## Reference
- https://hun-developer.tistory.com/36
- https://intrepidgeeks.com/tutorial/os-shared-resources-and-thresholds