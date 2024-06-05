# 641. Design Circular Deque
- 순환 이중 데크 구현 설계
    - MyCircularDeque(int k)
    - boolean insertFront()
    - boolean insertLast()
    - boolean deleteFront()
    - boolean deleteLast()
    - int getFront()
    - int getRear()
    - boolean isEmpty()
    - boolean isFull()
    - https://leetcode.com/problems/design-circular-deque/description/

<br>

### 풀이 방법
- 기존 원형 큐 처럼 배열으로 구현
- 기존 원형 큐의 특이사항 처럼 앞부분과 뒷부분을 가르킬 인덱스 번호를 지정하고 추가되거나 삭제될 때 % 연산을 진행하여 순환 예정
    - 구현 진행 중 추가/삭제 후 순환과정에서 구현실수로 GPT 참조
- 책의 풀이는 기존 데크처럼 연결리스트를 사용한 구현
    - 연결 리스트는 애초에 빈 공간이라는 개념이 존재하기 때문에 원형은 아무런 의미가 없음

<br>

### 첫번째 코드
```java
class MyCircularDeque {
    private int[] deque;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public MyCircularDeque(int k) {
        capacity = k;
        deque = new int[k];
        front = 0;
        rear = k - 1;
        size = 0;
    }
    
    public boolean insertFront(int value) {
        if (isFull()) {
            return false;
        }

        front = (front - 1 + capacity) % capacity;
        deque[front] = value;
        size++;

        return true;
    }
    
    public boolean insertLast(int value) {
        if (isFull()) {
            return false;
        }

        rear = (rear + 1) % capacity;
        deque[rear] = value;
        size++;

        return true;
    }
    
    public boolean deleteFront() {
        if (isEmpty()) {
            return false;
        }

        front = (front + 1) % capacity;
        size--;

        return true;
    }
    
    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        }

        rear = (rear - 1 + capacity) % capacity;
        size--;

        return true;
    }
    
    public int getFront() {
        if (isEmpty()) {
            return -1;
        }

        return deque[front];
    }
    
    public int getRear() {
        if (isEmpty()) {
            return -1;
        }

        return deque[rear];
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public boolean isFull() {
        return size == capacity;
    }
}
```

- runtime : 4ms
- memory : 44.98mb

<br>

### 풀이 코드

```java
class MyCircularDeque {
    static class DoublyLinkedList {
        DoublyLinkedList left;
        DoublyLinkedList right;
        int val;

        public DoublyLinkedList(int val) {
            this.val = val;
        }
    }

    int len;
    int k;
    DoublyLinkedList head;
    DoublyLinkedList tail;

    public MyCircularDeque(int k) {
        head = new DoublyLinkedList(0);
        tail = new DoublyLinkedList(0);

        head.right = tail;
        tail.left = head;
        this.k = k;
        this.len = 0;
    }
    
    public boolean insertFront(int value) {
        if (isFull()) {
            return false;
        }

        DoublyLinkedList node = new DoublyLinkedList(value);
        node.right = head.right;
        node.left = head;
        head.right.left = node;
        head.right = node;
        len++;

        return true;
    }
    
    public boolean insertLast(int value) {
        if (isFull()) {
            return false;
        }

        DoublyLinkedList node = new DoublyLinkedList(value);
        node.left = tail.left;
        node.right = tail;
        tail.left.right = node;
        tail.left = node;
        len++;

        return true;
    }
    
    public boolean deleteFront() {
        if (isEmpty()) {
            return false;
        }

        head.right.right.left = head;
        head.right = head.right.right;
        len--;

        return true;
    }
    
    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        }

        tail.left.left.right = tail;
        tail.left = tail.left.left;
        len--;

        return true;
    }
    
    public int getFront() {
        if (isEmpty()) {
            return -1;
        }

        return head.right.val;
    }
    
    public int getRear() {
        if (isEmpty()) {
            return -1;
        }

        return tail.left.val;
    }
    
    public boolean isEmpty() {
        return len == 0;
    }
    
    public boolean isFull() {
        return len == k;
    }
}
```

- runtime : 4ms
- memory : 44.52mb