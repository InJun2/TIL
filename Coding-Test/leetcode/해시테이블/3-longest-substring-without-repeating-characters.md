# 3. Longest Substring Without Repeating Characters
- 문자열 s가 주어졌을 때 해당 문자열에서 반복되지 않는 가장 긴 문자열의 길이를 반환
    - https://leetcode.com/problems/longest-substring-without-repeating-characters/description/

<br>

### 풀이 방법
- 문자열의 내부 포인터를 이동해 나가면서 중복되지 않은 문자열의 개수를 기록해나가는 방법이 좋을 것 같음
- 해당 구현 방법이 잘 떠오르지 않아 책의 풀이를 참조하였음

<br>

### 풀이 코드

```java
import java.util.HashMap;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> used = new HashMap<>();

        int maxLength = 0;
        int left = 0;
        int right = 0;

        for (char c : s.toCharArray()) {
            if (used.containsKey(c) && left <= used.get(c)) {
                left = used.get(c) + 1;
            } else {
                maxLength = Math.max(maxLength, right - left + 1);
            }

            used.put(c, right);
            right ++;
        }

        return maxLength;
    }
}
```

- runtime : 5ms
- memory : 44.72mb