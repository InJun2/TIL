# 325. Reverse String
- 문자열 뒤집기로 해당 char[] 배열을 뒤집는 함수 구현
    - https://leetcode.com/problems/reverse-string/description/
- 그러나 이후 확인하였을때 in-place O(1) 이므로 해당 배열안에서 구현하여야함

<br>

### 해결 방안
- 처음 풀이 방법은 우선 Stack에 쌓아두고 해당값을 새로운 char[] 배열에 값에 넣어두고 변경
    - 이후 해당 배열은 뒤집어져있는 상태이므로 기존 배열 값을 하나하나 뒤집어둔 배열 값으로 변경
- 그런데 문제 요구사항에 O(1) 공간 복잡도를 요구하므로 기존 s 배열 내부에서 노드 변경으로 구현 변경
    - 책을 참고하니 요구사항을 확인하여 책 구현 방법으로 구현

<br>

### 첫번째 풀이

```java
import java.util.Stack;

class Solution {
    public void reverseString(char[] s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s) {
            stack.push(c);
        }

        char[] reverseChar = new char[s.length];
        int index = 0;
        while (!stack.isEmpty()) {
            reverseChar[index++] = stack.pop();
        }

        for (int i = 0; i < s.length; i++) {
            s[i] = reverseChar[i];
        }
    }
}
```

- runtime : 5ms
- memory : 51.07mb

<br>

### 두번째 풀이

```java
class Solution {
    public void reverseString(char[] s) {
        int start = 0;
        int end = s.length - 1;

        while (start < end) {
            char temp = s[start];
            s[start] = s[end];
            s[end] = temp;

            start++;
            end--;
        }
    }
}
```

- runtime : 0ms
- memory : 48.97mb