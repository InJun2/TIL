# 21. Merge Two Sorted Lists
- 두 개의 정렬된 연결 목록의 헤드가 제공되는데 두 목록을 하나의 정렬된 목록으로 병합하고 병합된 목록의 헤드를 반환
    - https://leetcode.com/problems/merge-two-sorted-lists/description/

<br>

### 풀이 방법
- 이미 두개가 정렬되어 있으므로 맨 앞의 노드의 값을 비교하고 해당 노드의 값을 추가하고 추가한 노드를 다음 노드를 가리키도록 변경을 반복

<br>

### 첫번째 코드
- 기존 연결리스트를 생성하고 각각의 노드를 조회해서 추가하는 방식의 코드를 사용
- 연결리스트를 사용해본 적이 적은데 잘못된 구문과 최적화를 GPT로 수정

```java
/**
- 더미 노드 (dummy): 연결 리스트의 시작을 추적하기 위해 사용됩니다. 더미 노드는 실제 리스트의 첫 번째 노드 앞에 위치하며, 결과 리스트의 헤드를 쉽게 반환할 수 있게 합니다.
- 현재 포인터 (current): 결과 리스트를 빌드할 때 현재 위치를 추적하는 포인터입니다. 더미 노드는 리스트의 시작을 추적하기 위해 고정되고, current 포인터는 리스트를 따라 이동하면서 새로운 노드를 추가합니다.
 */
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(); // 반환을 위한 더미 노드 생성
        ListNode current = dummy; // 현재 위치를 추적할 포인터

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                dummy.next = list1; // 현재 노드의 다음을 list1으로 설정
                list1 = list1.next; // list1 포인터를 다음 노드로 이동
            } else {
                dummy.next = list2; // 현재 노드의 다음을 list2로 설정
                list2 = list2.next; // list2 포인터를 다음 노드로 이동
            }
            dummy = dummy.next; // 결과 리스트의 포인터를 이동
        }

        // list1과 list2 중 남아있는 노드가 있다면 결과 리스트에 추가
        if (list1 != null) {
            dummy.next = list1;
        } else {
            dummy.next = list2;
        }

        return dummy.next;
}
```

- runtime : 0ms
- memory : 42.55mb

<br>

### 풀이 코드
- 책의 풀이코드는 재귀로 구현

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 두 노드 중 한쪽이 널이면 널이 아닌 노드를 리턴
        if(list1 == null) {
            return list2;
        }

        if(list2 == null) {
            return list1;
        }

        // l2가 더 크면 l1에 재귀 호출 결과를 엮고 l1을 리턴
        if(list1.val < list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            // l1이 더 크거나 같으면 l2에 재귀 호출 결과를 엮고 l2를 리턴
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }
}
```

- runtime : 0ms
- memory : 42.21mb