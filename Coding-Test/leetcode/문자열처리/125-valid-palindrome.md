# 125. Valid Palindrome
- 입력 s에 대해 뒤집어도(팔린드롬) 문자열이 같으면 true, 아니면 false 출력
    - https://leetcode.com/problems/valid-palindrome/description/

<br>

### 해결 방법
- 소문자 대문자 구분이 없다고하므로 일차적으로 문자을 소문자로 통일
- 생각난 방법은 우선 두번째 방법은 문자열이 아닌 특수문자만을 공백으로 변경
    - 특수문자를 하나하나 변경하려 했으나 replaceAll 정규 표현식을 사용하여 변경
    - 기존에는 소문자로 변경하고 s.replaceAll("[^a-z0-9]", ""); 처럼 변경하였으나 오히려 변경하고 조회하는 것이 두 번째 조회 횟수가 줄어 변경하였음
- 이후 방법은 문자열인 것만 match()로 찾아서 배열 같은 자료구조에 넣는다?
    - replace로 하는 것이 한번에 진행 할 수 있을 것 같아 변경
    - match보다 Character에 문자가 맞는지 확인하는 isLetterOrDigit() 메서드를 사용
- 기존 방법은 배열에 넣고 순서를 바꿨으나 StringBuilder reverse() 함수를 알게되어 사용으로 변경

<br>

### 첫번째 풀이

```java
class Solution {
    public boolean isPalindrome(String s) {
        s = s.replaceAll("[^A-Za-z0-9]", "");
        s = s.toLowerCase();

        StringBuilder reversed = new StringBuilder(s).reverse();

        return s.equals(reversed.toString());
    }
}
```

- runtime : 13ms
- memory : 44.82mb

<br>

### 두번째 풀이

```java
import java.util.Queue;
import java.util.LinkedList;

class Solution {
    public boolean isPalindrome(String s) {
        s = s.toLowerCase();

        Queue<Character> queue = new LinkedList<>();

        for (char c : s.toCharArray()) {
            if(Character.isLetterOrDigit(c)) {
                queue.add(c);
            }
        }

        StringBuilder builder = new StringBuilder();
        while (!queue.isEmpty()) {
            builder.append(queue.poll());
        }
        String originalString = builder.toString();
        String reverseString = builder.reverse().toString();

        return originalString.equals(reverseString);
    }
}
```

- runtime : 7ms
- memory : 46.48mb

<br>

### 스터디 중 이야기 나온 내용
- 두 개의 러너를 사용하여 맨 앞과 맨 뒤 인덱스를 러너로 두고 각자 한 칸씩 이동하며 값이 같은지 체크하는 방식도 있음