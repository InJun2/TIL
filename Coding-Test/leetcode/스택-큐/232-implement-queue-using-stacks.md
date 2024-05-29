# 232. Implement Queue using Stacks
- 두개의 스택을 사용하여 FIFO 대기열 구현하기
    - void push(int x) : 요소 x를 대기열 뒤쪽으로 푸시
    - int pop() : 대기열의 앞부분에서 요소를 제거하고 반환
    - int peek() : 대기열의 맨 앞에 있는 요소를 반환
    - boolean empty() : 대기열이 비어 있으면 ture 반환, 그렇지 않으면 false 반환
    - https://leetcode.com/problems/implement-queue-using-stacks/description/

<br>

### 풀이 방법
- 스택은 후입선출이기 때문에 첫번째 스택에 순서대로 넣고 해당 스택에서 두번째 스택에 넣은 후 빼면 됨
    - 이후 책을 보고 peek을 사용하여 pop 사용으로 최적화

<br>

### 첫 번째 코드
```java
import java.util.Deque;
import java.util.ArrayDeque;

class MyQueue {
    Deque<Integer> firstStack;
    Deque<Integer> secondStack;

    public MyQueue() {
        firstStack = new ArrayDeque<>();
        secondStack = new ArrayDeque<>();
    }
    
    public void push(int x) {
        firstStack.push(x);
    }
    
    public int pop() {
        peek();
        return secondStack.pop();
    }
    
    public int peek() {
        if (secondStack.isEmpty()) {
            while (!firstStack.isEmpty()) {
                secondStack.push(firstStack.pop());
            }
        }
        return secondStack.peek();
    }
    
    public boolean empty() {
        return firstStack.isEmpty() && secondStack.isEmpty();
    }
}
```

- runtime: 0ms
- memory : 41.47mb