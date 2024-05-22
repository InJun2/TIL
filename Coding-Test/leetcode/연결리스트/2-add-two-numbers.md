# 2. Add Two Numbers
- 두 개의 음수가 아닌 정수를 나타내는 두개의 비어있지 않은 연결 목록이 제공됨. 해당 목록의 숫자는 역순으로 저장되며 각 노드에는 단일 숫자가 포함되어 있고 두 연결리스트의 숫자를 더하고 합계를 연결된 목록으로 반환
    - https://leetcode.com/problems/add-two-numbers/description/

<br>

### 풀이 방법
- 즉, 양의 정수가 반대로 뒤집혀 들어가 있으므로 다시 뒤집은 정수를 구하고 이를 더한 후 뒤에서부터 연결리스트에 기입
    - 구현을 못하겠음.. 책을 보고 첫번째 풀이 진행
- 이후 책의 두번째 풀이 코드로는 논리회로의 전가산기 방식이 존재했음

<br>

### 첫번째 풀이 코드

```java
import java.math.BigInteger;

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode reverseNode1 = reverseList(l1);
        ListNode reverseNode2 = reverseList(l2);

        BigInteger result = toBigInt(reverseNode1).add(toBigInt(reverseNode2));
        return toReversedLinkedList(result);
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;
    }

    public BigInteger toBigInt(ListNode node) {
        StringBuilder val = new StringBuilder();
        while (node != null) {
            val.append(node.val);
            node = node.next;
        }

        return new BigInteger(val.toString());
    }

    public ListNode toReversedLinkedList(BigInteger val) {
        ListNode prev = null;
        ListNode node = null;
        String value = val.toString();

        for (char c : value.toCharArray()) {
            node = new ListNode(Character.getNumericValue(c));
            node.next = prev;
            prev = node;
        }

        return prev;
    }
}
```

- runtime : 11ms
- memory : 45.3mb

<br>

### 전가산기 풀이
- 자료형을 일일이 변환하지 않아 실행속도가 더 빠르다고 함
- 해당 문제는 둘다 뒤집어져 있으므로 앞자리부터 더하고 결과 연결리스트에 담으면 끝나는 문제
    - 뒤집어 있기 때문에 하나씩 더해서 계산해도 맨뒷자리부터 연결리스트에 들어가기 때문에 문제가 없음
    - 더해서 10이 넘는 경우만 처리해주면 되는 문제로 더한 값, 자릿수 올림, 10으로 나눈 나머지를 연결리스트에 기입하며 선형적으로 구현
```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode node = new ListNode(0);

    ListNode root = node;

    int sum, carry = 0, remainder;

    while (l1 != null || l2 != null || carry != 0) {
        sum = 0;
        if (l1 != null) {
            sum += l1.val;
            l1 = l1.next;
        }

        if (l2 != null) {
            sum += l2.val;
            l2 = l2.next;
        }
        remainder = (sum + carry) % 10;

        carry = (sum + carry) / 10;
        node.next = new ListNode(remainder);

        node = node.next;
    }

    return root.next;
}
```