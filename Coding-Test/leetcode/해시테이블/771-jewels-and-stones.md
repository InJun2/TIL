# 771. Jewels and Stones
- 돌에는 보석이 몇개나 있는지 파악후 반환하기
    - 대소문자는 구분한다
    - https://leetcode.com/problems/jewels-and-stones/description/

<br>

### 풀이 방법
- 보석을 HashMap 혹은 HashSet을 통해 보관하고 돌을 순회할 때 해당 보석이 있는지 확인하여 반환하기

<br>

### 풀이 코드

```java
import java.util.Set;
import java.util.HashSet;

class Solution {
    public int numJewelsInStones(String jewels, String stones) {
        int count = 0;
        Set<Character> freqs = new HashSet<>();

        for (char j : jewels.toCharArray()) {
            freqs.add(j);
        }

        for (char s : stones.toCharArray()) {
            if (freqs.contains(s)) {
                count ++;
            }
        }

        return count;
    }
}
```

- runtime : 1ms
- memory : 41.64mb