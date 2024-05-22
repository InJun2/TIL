# 92. Reverse Linked List II
- 단일 연결리스트와 left, rigth가 주어질 때 left <= right 목록의 노드를 반전하고 반환
    - https://leetcode.com/problems/reverse-linked-list-ii/description/

<br>

### 풀이 방법
- left만큼 next를 진행하고 while문 대신 for문으로 right-left 만큼 반복해서 해당 노드들을 지금까지 처럼 뒤집고 left만큼 next를 한 연결 리스트 뒤에 붙이기
    - 접근은 좋았으나 반전 구현이 부족하여 GPT로 보완
    - 해당 코드가 책의 코드와 동일

<br>

### 첫번째 코드
```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if(head == null) {
            return null;
        }

        ListNode result = new ListNode();
        result.next = head;
        ListNode prev = result; // 반전 시작 전 노드

        for(int i = 0 ; i < left - 1; i++) {
            prev = prev.next;
        }

        ListNode start = prev.next; // 반전의 시작점
        ListNode then = start.next; // 반전할 노드

        for(int i = 0; i < right - left; i++) {
            start.next = then.next;
            then.next = prev.next;
            prev.next = then;
            then = start.next;
        }

        return result.next;
    }
}
```

- runtime : 0ms
- memory : 41.48mb