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

```