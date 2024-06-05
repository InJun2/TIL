# 23. Merge k Sorted Lists
- k개로 연결된 목록의 배열이 주어지며 각 list 연결 목록은 오름차순으로 정렬되는데 모든 연결 목록을 하나의 연결 목록으로 병합하고 반환
    - https://leetcode.com/problems/merge-k-sorted-lists/description/

<br>

### 풀이 방법
- 정렬된 연결리스트 배열이 주어지므로 가장 앞부분만을 비교해서 새로운 연결리스트에 연결하여 반환
    - 그러나 반복문의 기준을 어떻게 진행해야하는지 감이 안잡힘, 방법에 대해 GPT를 사용
    - 값 노드를 생성하고 이전 연결리스트에 생성한 노드를 추가
- 책의 풀이는 우선순위 큐를 사용하여 구현

<br>

### 첫번째 코드
- 기존 방법을 진행하다 GPT 참조

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode();
        ListNode current = dummy;

        while (true) {
            int minIndex = -1;
            int minValue = Integer.MAX_VALUE;
            boolean allNull = true;

            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null) {
                    allNull = false;

                    if (lists[i].val < minValue) {
                        minValue = lists[i].val;
                        minIndex = i;
                    }
                }
            }

            if (allNull) {
                break;
            }

            // 최소 값의 노드를 연결리스트에 추가하고 다음 노드를 가리키게 변경
            current.next = lists[minIndex];
            current = current.next;

            // 연결리스트의 포인터를 다음노드로 이동
            lists[minIndex] = lists[minIndex].next;
        }

        return dummy.next;
    }
}
```

- runtime : 173ms
- memory : 44.62mb

<br>

### 풀이 코드
- 책의 풀이코드를 사용

```java
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        // 작은 순서대로 우선순위 큐 정렬
        PriorityQueue<ListNode> pq = new PriorityQueue<>((o1, o2) -> o1.val - o2.val);
        
        // 새로 연결리스트를 생성하고 이후 연결할 포인터 tail 생성 
        ListNode root = new ListNode();
        ListNode tail = root;

        // 맨 첫 노드들을 우선순위 큐에 삽입 (들어간 값은 정렬)
        for (ListNode node : lists) {
            if (node != null) {
                pq.add(node);
            }
        }
        
        // 우선순위큐가 비어있지 않을 때 까지 반복
        while (!pq.isEmpty()) {
            // 우선순위큐에서 하나 빼서 연결리스트에 연결 후 다음 노드를 가르킴
            tail.next = pq.poll();
            tail = tail.next;
            
            // 빼온 기존 노드의 다음값을 우선순위 큐에 삽입
            if (tail.next != null) {
                pq.add(tail.next);
            }
        }
        
        return root.next;
    }
}

/*

[
    1 -> 4 -> 5
    1 -> 3 -> 4
    2 -> 7
]

pq = [1, 1, 2]

---
tail -> pq[first]
tail = 1 -> 4 -> 5
pq = [1, 2, 4]

[
    4 -> 5
    1 -> 3 -> 4
    2 -> 7
]

---
tail -> pq[first]
tail = 1 -> 1 -> 3 -> 4
pq = [2, 3, 4]

[
    4 -> 5
    3 -> 4
    2 -> 7
]

이후 반복..

*/
```

- runtime : 4ms
- memory : 44.41mb