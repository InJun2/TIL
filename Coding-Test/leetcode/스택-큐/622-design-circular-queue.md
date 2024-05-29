# 622. Design Circular Queue
- 순환 대기 구현 디자인하기. 순환 큐는 FIFO원리에 따라 연산을 수행하고 마지막 위치가 다시 첫 번째 위치로 연결되어 원을 이루는 선형 데이터 구조
    - 마지막 위치가 시작 위치와 연결되는 원형 구조를 띄어 "Ring Buffer"라고도 함
    - 기존 큐는 꽉차면 더 이상 엘리먼트를 추가할 수 없으나 원형 큐는 앞쪽에 공간이 남아 있다면 앞쪽으로 추가할 수 있도록 재활용이 가능하여 제한된 공간을 재활용이 가능하다고 함
    - 1 <= k <= 1000, 0 <= value <= 1000
    - https://leetcode.com/problems/design-circular-queue/description/

<br>

### 풀이 방법
- 원형 큐를 디자인하는 것은 방법이 떠오르지 않는 것 같음. 책을 참조하여 작성하고 이후 이해를 꾀하였음

<br>

### 풀이 코드
```java
class MyCircularQueue {
    int[] q;
    int front = 0, rear = -1, len = 0;

    public MyCircularQueue(int k) {
        // k 크기의 원형 큐로 사용할 배열 선언
        this.q = new int[k];
    }
    
    public boolean enQueue(int value) {
        // 꽉 차 있지 않다면 삽입 진행
        if(!this.isFull()) {
            // rear 포인터 한 칸 앞으로 이동, 최대 크기를 초과하면 나머지 위치로 이동
            this.rear = (this.rear + 1) % this.q.length;

            //rear 위치에 값 삽입
            this.q[rear] = value;

            // 현재 큐의 크기 계산
            this.len++;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean deQueue() {
        // 텅 비어 있지 않다면 삭제 진행
        if (!this.isEmpty()) {
            // front 포인터 한 칸 앞으로 이동, 최대 크기를 초과하면 나머지 위치로 이동
            this.front = (this.front + 1) % this.q.length;

            // 현재 큐의 크기 계산
            this.len--;
            return true;
        } else {
            return false;
        }
    }
    
    public int Front() {
        // 맨 앞의 값을 가져온다
        return (this.isEmpty() ? -1 : this.q[this.front]);
    }
    
    public int Rear() {
        // 맨 뒤의 값을 가져온다
        return (this.isEmpty() ? -1 : this.q[this.rear]);
    }
    
    public boolean isEmpty() {
        // 현재 큐의 크기가 0이면 비어있음
        return this.len == 0;
    }
    
    public boolean isFull() {
        // 현재 큐의 크기가 전체 큐의 크기와 일치하면 꽉 차있음
        return this.len == this.q.length;
    }
}
```

- runtime : 4ms
- memory : 44.72mb