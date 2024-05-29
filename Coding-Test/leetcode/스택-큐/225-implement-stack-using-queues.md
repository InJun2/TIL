# 225. Implement Stack using Queues
- 두개의 큐을 사용하여 LIFO 대기열 구현하기
    - void push(int x) : 요소 x를 대기열 맨 위쪽으로 푸시
    - int pop() : 대기열의 위에 있는 요소를 제거하고 반환
    - int top() : 대기열의 맨 위에 있는 요소를 반환
    - boolean empty() : 대기열이 비어 있으면 ture 반환, 그렇지 않으면 false 반환
    - https://leetcode.com/problems/implement-queue-using-stacks/description/

<br>

### 풀이 방법
- 우선 큐에 모두 담아두고 큐의 크기 - 1 만큼 값을 빼서 다시 담기를 반복하면 마지막 노드의 값을 반환이 가능

<br>

### 첫 번째 코드
```java
import java.util.Queue;
import java.util.LinkedList;

class MyStack {
    Queue<Integer> queue;

    public MyStack() {
        queue = new LinkedList<>();
    }
    
    public void push(int x) {
        queue.offer(x);
    }
    
    public int pop() {
        for(int i = 0; i < queue.size() - 1; i++) {
            queue.offer(queue.poll());
        }

        return queue.poll();
    }
    
    public int top() {
        for(int i = 0; i < queue.size() - 1; i++) {
            queue.offer(queue.poll());
        }

        int top = queue.poll();
        queue.offer(top);

        return top;
    }
    
    public boolean empty() {
        return queue.isEmpty();
    }
}
```

<br>

### 풀이 코드
- 매번 맨 뒤의 값을 n번 조회하기보다는 push 할 때 맨 뒤의 값을 두는 방법을 선택

```java
import java.util.Queue;
import java.util.LinkedList;

class MyStack {
    Queue<Integer> queue;

    public MyStack() {
        queue = new LinkedList<>();
    }
    
    public void push(int x) {
        queue.add(x);

        // 맨 앞에 두는 상태로 전체 재정렬
        for(int i = 1; i < queue.size(); i++) {
            queue.add(queue.poll());
        }
    }
    
    public int pop() {
        return queue.poll();
    }
    
    public int top() {
        return queue.peek();
    }
    
    public boolean empty() {
        return queue.isEmpty();
    }
}
```