# 49. Group Anagrams
- String[] strs 배열이 주어지면 철자의 개수가 같은 그룹끼리 그룹화하기
    - 아나그램이란 모든 원래 문자를 재배열하여 형성된 단어나 구문으로 즉 구성된 철자의 개수가 같은 그룹끼리 묶으면 됨
    - https://leetcode.com/problems/group-anagrams/description/

<br>

### 해결방안
- 반환 값이 List<List<String>>이므로 같은 그룹을 List<String>으로 담고 그룹들을 List로 감싸서 반환 예정
- 같은 문자열을 구분하기 위해 CharArray로 변환 후 Arrays.sort() 메서드를 사용하여 문자열을 정렬하고 정렬한 문자열이 같은지 확인
    - sortedStr 부분을 책을 참고하여 new String(charArray) 에서 String.valueOf(charArray)로 변경
- 확인한 문자열을 Map에 정렬전 문자열을 key로 정렬전 문자열을 value로 구분
- 해당 Map을 key 별로 List에 저장
    - 책의 내용을 보고 반환을 map.values() 로 변경

<br>

### 코드

```java
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for(String str : strs) {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            String sortedStr = String.valueOf(charArray);

            if(!map.containsKey(sortedStr)) {
                map.put(sortedStr, new ArrayList<>());
            }

            map.get(sortedStr).add(str);
        }

            return new ArrayList<>(map.values());
    }
}
```

<br>

- runtime : 6ms
- memory : 47.55mb

<br>

### 스터디 중 나온 이야기
- 자바 HashMap에서는 같은 해시값 충돌 발생시 4개까지는 LinkedList로 저장이 되고 이후에는 트리로 저장이 된다고 함
    - [윤희님 해시 충돌 공유 내용](https://jennyuni.notion.site/Hash-95db59ea6d9049a585798f96b8d27889?pvs=4)