# 24. Swap Nodes in Pairs
- 연결된 목록이 주어지면 인접한 두 노드를 모두 교체하고 해당 헤드를 반환하되 목록의 노드 값을 수정하지 않고 문제를 해결해야 함 ( 노드 자체만 변경이 가능 )
    - https://leetcode.com/problems/swap-nodes-in-pairs/description/

<br>

### 풀이 방법
- 짝수번째와 홀수번째를 바꾸는 문제같은데 temp를 써서 현재 연결노드와 다음 연결노드의 노드를 바꾸면 되는게 아닌지?
    - 생각해보니 그냥 바꾼다고 해도 뒤뒤의 노드들부터는 같아야하므로 뒤의 부분의 노드들을 또 따로 temp로 보관하였음
    - 그런데 풀이한 방법은 사실상 새로만든 ListNode가 됬음..
- 이후 책의 풀이를 참조하였음

<br>

### 첫번째 코드
```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode current = dummy;

        while (current.next != null && current.next.next != null) {
            current.next = changeNode(current.next);
            current = current.next.next;
        }

        return dummy.next;
    }

    // public ListNode changeNode(ListNode head) {
    //     ListNode temp = head;
    //     ListNode lastTemp = head.next.next;
    //     head = head.next;
    //     head.next = temp;
    //     head.next.next = lastTemp;

    //     return head;
    // }

    public ListNode changeNode(ListNode first, ListNode second) {
        first.next = second.next;
        second.next = first;

        return second;
    }
}
```

- runtime : 0ms
- memory : 41.41mb

<br>

### 값만 교환하는 책의 풀이 코드
```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode node = head;
        
        while(node != null && node.next != null) {
            int tmp;
            tmp = node.val;
            node.val = node.next.val;
            node.next.val = tmp;
            node = node.next.next;
        }

        return head;
    }
}
```

- runtime : 0ms
- memory : 41.60mb

<br>

### 반복 구조로 스왑하는 책의 풀이 코드
- 연결 리스트 자체를 바꾸는 일은 복잡한데 그 뒤의 연결되는 리스트도 변경해야하기 때문
```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode node = new ListNode(0);
        ListNode root = node;

        node.next = head;
        while(node.next != null && node.next.next != null) {
            ListNode a = node.next;
            ListNode b = node.next.next;
            a.next = b.next;
            node.next = b;
            node.next.next = a;
            node = node.next.next;
        }

        return root.next;
    }
}
```

- runtime : 0ms
- memory : 41.23mb

<br>

### 재귀 구조로 스왑
```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        if(head != null && head.next != null) {
            ListNode p = head.next;
            head.next = swapPairs(head.next.next);
            p.next = head;
            return p;
        }

        return head;
    }
}
```

- runtime : 0ms
- memory : 40.85mb