# 234. Palindrome Linked List
- 단일 연결 리스트가 주어지고 해당 리스트가 팰린드롬(회문)인지 확인
    - https://leetcode.com/problems/palindrome-linked-list/description/

<br>

### 풀이 방법
- 기존 방법대로 스택으로 풀면 해당 문제를 해결할 수는 있을것 같으나 후속조치에 성립이 안됨
- 연결리스트로만 푸는 방법은 내부 노드들을 조회하고 기록해서 해당 값이 회문인지 확인 하는 방법밖에 생각이 나지 않음
- 우선 풀이가 가능한지 스택으로 풀이 후 책의 내용을 보고 최적화
- 이후 책의 러너 기법을 사용하여 풀이

<br>

### 구현 코드
```java
class Solution {
    public boolean isPalindrome(ListNode head) {
        Stack<Integer> stack = new Stack<>();

        ListNode node = head;
        while(node != null) {
            stack.add(node.val);
            node = node.next;
        }

        while (head != null) {
            if(head.val != stack.pop()) {
                return false;
            }
            head = head.next;
        }

        return true;
    }
}
```

- runtime : 14ms
- memory : 58.50mb

<br>

### 풀이 코드
- 러너 기법을 사용하여 빠른 러너, 느린러너를 출발 시키고 빠른러너가 끝에 다다르면 느린 러너는 중간에 도착할 것이므로 이 경로를 역순으로하여 연결리스트를 만들어나가고 이후 일치하는지 확인하는 방법

```java
class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        // 빠른 러너가 끝까지 갈 때까지 느린 러너와 함께 진행
        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }   

        // 홀수 개일 때 느린 러너가 한 칸 더 앞으로 가도록 처리
        if(fast != null) {
            slow = slow.next;
        }

        // 중간에 도달한 느린 러너를 기준으로하여 역순 연결 리스트 생성
        ListNode rev = null;
        while(slow != null) {
            // 느린 러너로 역순 연결 리스트 rev를 만들어나감
            ListNode next = slow.next;
            slow.next = rev;
            rev = slow;
            slow = next;
        }

        // rev 연결 리스트를 끝까지 이동하며 비교
        while(rev != null) {
            //  역순 연결 리스트 rev와 기존 연결 리스트 head를 차례대로 비교
            if(rev.val != head.val) {
                return false;
            }

            rev = rev.next;
            head = head.next;
        }

        return true;
    }
}
```

- runtime : 3~4ms
- memory : 58~59, 69mb

<br>

```java
ListNode fast = head;
ListNode slow = head;

while(fast != null && fast.next != null) {
    fast = fast.next.next;
    slow = slow.next;
}

// 에서 fast.next = fast.next.next; 하지 않는 이유
// 리스트를 조작하는 것이 아니고 기존 헤드의 포인터 위치만을 변경하여 조회하기 위함
```