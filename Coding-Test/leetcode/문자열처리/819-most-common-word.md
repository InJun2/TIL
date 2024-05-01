# 819. Most Common Word
- 금지된 단어와 문자열이 주어지고 금지되지 않은 가장 빈번한 문자열을 반환
    - https://leetcode.com/problems/most-common-word/description/

<be>

### 해결 방안
- 우선 단어별 횟수를 저장해야하므로 <String, Integer> 맵을 사용하여 구현 예정
- 우선 주어진 문자열을 소문자로 변경하고 공백을 기준으로 분리하고 해당 단어들을 저장
    - 그런데 금지된 문자열은 배열로 주어지고 해당 문자열은 중복이 되어도 횟수를 추가하지 않는 것은 동일하므로 Set으로 중복 제거
    - 책을 확인하니 문자열에서 특수문자를 제거해야 하므로 전처리 추가
- 해당 단어들을 Map에 저장하려할 때 금지된 단어를 확인하기 위해 금지 문자 검사 후 금지 되지 않았다면 Map에 추가
    - 책을 확인하여 Map의 Value를 저장하는 방법으로 getOrDefault(key, 0) +1로 변경
- 이후 최대 밸류의 key를 가져오는 방법도 책을 확인하고 작성

<br>

### 코드

```java
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;

class Solution {
    public String mostCommonWord(String paragraph, String[] banned) {
        Map<String, Integer> countWords = new HashMap<>();
        Set<String> banWords = new HashSet<>();

        for(String ban : banned) {
            banWords.add(ban);
        }

        String[] words = paragraph.replaceAll("\\W+", " ").toLowerCase().split(" ");
        for(String word : words) {
            if(!banWords.contains(word)) {
                countWords.put(word, countWords.getOrDefault(word, 0) + 1);
            }
        }

        return Collections.max(countWords.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
```

- runtime : 11 ms
- memory : 42.5mb

<br>

### 스터디 중 나온 이야기
- ban의 문자열이 많다면 매번 비교하고 비교한 값을 사용하는 것은 성능상으로 좋지 않을 것 같음
    - ban 문자열이 많다면 ban에 대해서 결과에 대해서 비교를 마지막에 하는 것이 좋을 것