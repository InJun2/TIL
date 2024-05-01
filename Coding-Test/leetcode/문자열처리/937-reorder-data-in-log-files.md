# Reorder Data in Log Files
- logs 문자열 배열이 주어지고 해당 로그는 모든 단어가 영문 소문자로 구성되어 있는 Letter-logs 와 모든 단어가 숫자로 구성된 digit-logs가 존재
- 다음과 같이 로그를 다시 정렬
    - 문자 로그는 모든 숫자 로그 앞에 존재
    - 편지 기록은 내용에 따라 사전순으로 정렬. 내용이 동일하면 식별자를 기준으로 사전순 정렬
    - 숫자 로그는 상대적 순서를 유지
    - https://leetcode.com/problems/reorder-data-in-log-files/description/

<br>

### 해결방안
- 해당 로그가 정렬되는 순서가 이해가 안되서 책을 참조
- 로그의 문자로그와 숫자로그를 처음 공백으로 2개로 분리 후 각각 리스트로 저장
- Collections.sort()에 Comparator을 이용하여 정렬 조건을 설정하여 정렬
    - 람다로 간단히 대체
- 

<br>

### 코드

```java
import java.util.List;
import java.util.ArrayList;

class Solution {
    public String[] reorderLogFiles(String[] logs) {
        List<String> letterList = new ArrayList<>();
        List<String> digitList = new ArrayList<>();

        for (String log : logs) {
            if (Character.isDigit(log.split(" ")[1].charAt(0))) {
                digitList.add(log);
            } else {
                letterList.add(log);
            }
        }

        letterList.sort((s1, s2) -> {
            // 식별자와 식별자 외 나머지 부분을 이렇게 두 부분으로 나눔
            String[] s1x = s1.split(" ", 2);
            String[] s2x = s2.split(" ", 2);

            // 문자 로그 사전순 비교
            // -1이라면 s1x[1]이 우선, 0이라면 같은 문자열, 1이라면 s2x[1]이 우선
            int compared = s1x[1].compareTo(s2x[1]);

            // 문자가 동일한 경우 식별자 비교
            if (compared == 0) {
                return s1x[0].compareTo(s2x[0]);
            } else {
                // 비교 대상의 순서가 동일한 경우 0, 순서가 앞인 경우 1, 순서가 뒤인 경우 -1
                return compared;
            }
        });

        letterList.addAll(digitList);

        return letterList.toArray(new String[0]);
    }
}
```

- runtime : 4ms
- memory : 44.14mb