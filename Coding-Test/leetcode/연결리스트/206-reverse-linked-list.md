# 206. Reverse Linked List
- 단일 연결 목록이 주어지면 반전하여 반전된 목록을 반환
    - https://leetcode.com/problems/reverse-linked-list/description/

<br>

### 풀이 방법
- 다음 노드가 null일 때까지 순회하되 값을 기록해두고 새로운 연결 리스트를 생성하여 기록된 값을 거꾸로 넣기
    - 하나씩 값을 기록하는 방법은 기존 자료구조를 제외하고는 잘 모르겠음..
- 이해가 되지 않아 책의 풀이를 보고 진행

<br>

### 풀이 코드
```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while(current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;
    }
}
```

- runtime : 0ms
- memory : 42.72mb

```
ex) 1 -> 2 -> 3 -> 4 -> 5

초기상태
- prev = null
- current = 1

첫 번째 반복
- next = 2
- current.next = null
- prev = 1
- current = 2
- 리스트 상태: 1 -> null, 2 -> 3 -> 4 -> 5

두 번째 반복
- next = 3
- current.next = 1
- prev = 2
- current = 3
- 리스트 상태: 2 -> 1 -> null, 3 -> 4 -> 5

반복 종료 후
- prev = 5
- current = null
- 리스트 상태 : 5 -> 4 -> 3 -> 2 -> 1 -> null
```