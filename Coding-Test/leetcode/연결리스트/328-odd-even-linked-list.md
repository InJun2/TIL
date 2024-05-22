# 328. Odd Even Linked List
- 단일 연결 리스트가 주어지는데 head를 제외하고 이후 짝수 인덱스의 노드와 홀수 인덱스의 노드 중 홀수 인덱스의 노드부터 나열하고 이후 짝수 인덱스의 노드를 나열한 연결 리스트 반환
    - https://leetcode.com/problems/odd-even-linked-list/description/

<br>

### 풀이 방법
- 생각나는 방법은 연결 리스트를 두개 만들어서 홀수번째 짝수번째의 노드들을 넣어두고 head 이후 짝수 연결리스트 연결, 이후 홀수 연결리스트 연결

<br>

### 첫번째 코드
- 구현을 잘못한 코드
    - 안돌아가져서 해당 코드를 GPT로 수정한 코드가 책의 코드와 같아 그대로 사용

```java
class Solution {
    public ListNode oddEvenList(ListNode head) {
        ListNode result = head;
        ListNode odd = new ListNode();
        ListNode even = new ListNode();

        while(head.next != null) {
            even.next = head.next;
            if(head.next.next != null) {
                odd.next = head.next.next;
            }

            head = head.next.next;
        }

        odd.next = even;
        result.next = odd;

        return result;
    }
}
```

<br>

```java
class Solution {
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }
        
        ListNode odd = head; // 홀수 노드의 시작점
        ListNode even = head.next; // 짝수 노드의 시작점
        ListNode evenHead = even; // 나중에 연결할 짝수 노드의 시작점 저장

        while (even != null && even.next != null) {
            odd.next = even.next; // 홀수 노드의 다음을 짝수 다음으로 설정
            odd = odd.next; // 다음 홀수 노드로 이동
            even.next = odd.next; // 짝수 노드의 다음을 홀수 다음으로 설정
            even = even.next; // 다음 짝수 노드로 이동
        }

        odd.next = evenHead; // 홀수 노드의 끝을 짝수 노드의 시작으로 연결

        return head;
    }
}
```

- runtime : 0ms
- memory : 44.32mb

```
ex) 1 -> 2 -> 3 -> 4 -> 5

초기상태
- odd : 1 -> 2 -> 3 -> 4 -> 5
- even : 2 -> 3 -> 4 -> 5
- evenHead : 2 -> 3 -> 4 -> 5

while문 1회차
- odd : 1 -> 3 -> 4 -> 5 (even.next : 3 -> 4 -> 5)
- odd (3 -> 4 -> 5)
- even : 2 -> 4 -> 5 (odd.next : 4 -> 5)
- even (4 -> 5)

.. 반복
```