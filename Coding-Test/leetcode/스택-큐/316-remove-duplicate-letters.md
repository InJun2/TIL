# 316. Remove Duplicate Letters
- String 문자열이 주어지면 모든 문자가 한 번만 나타나도록 중복문자를 제거하여 결과 문자열 반환
    - 다만 사전순으로 가장 작은 것을 가능한 모든 결과중에서 제거
    - https://leetcode.com/problems/remove-duplicate-letters/description/

<br>

### 풀이 방법
- 해당 조건에 나오는 사전식 순서가 이해가 되지 않아 책을 참조하여 문제 풀이를 이어서 진행
    - 사전식 순서 : 중복을 제거하였을 때 사전순으로 가장 앞에 나올수 있는 문자를 하나씩 등록
    - d b a c d c b c 가 주어졌을 때 우선 앞에서부터 중복 제거를 했을 때 가장 앞에 나올 수 있는 문자 : a
    - a c d c b c 가 남았는데 a 뒤에 중복을 제거할 수 있는 문자는 c, 사전순으로는 b가 먼저지만 b는 중복이 없기 때문에 이후 문자 : c
    - c d c b c 에서 현재 c는 등록되었으므로 제거하면 남은 문자는 중복이 없으므로 다음 들어가는 문자는 d, b
    - 결과는 a c d b
- 우선 생각나는 방식은 전체를 Map에 한번 넣어 중복이 있는지 없는지 확인. 이후 중복이 없는 문자열이 나오기까지 혹은 마지막 중복이 있는 문자가 나오기 까지 Set에 값을 넣고 그 중 가장 낮은 알파벳을 결과 문자열에 등록 후 중복이 없는 문자열 등록
    - 전체 중복의 개수를 기록하는 duplicateMap
    - 현재 문자열을 순회하면서 만난 중복의 개수를 확인하는 checkMap
    - 문자열을 모두 Queue에 넣어두고 해당 큐를 순회하며 중복이 없는 문자열이 나오기까지 혹은 마지막 중복이 있는 문자가 나오기까지 순회
- 해당 Set을 초기화하고 반복

<br>

### 첫번째 코드
- 돌아가지 않음

```java
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

class Solution {
    Map<Character, Integer> duplicateMap = new HashMap<>();
    Map<Character, Integer> checkMap = new HashMap<>();

    public String removeDuplicateLetters(String s) {
        Queue<Character> queue = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        int min = Integer.MAX_VALUE;

        for (char ch : s.toCharArray()) {
            duplicateMap.put(ch, duplicateMap.getOrDefault(ch, 0) + 1);
            queue.add(ch);
        }

        while (!queue.isEmpty()) {
            char ch = queue.poll();
            if (min > ch) {
                min = ch;
            }
            checkMap.put(ch, checkMap.getOrDefault(ch, 0) + 1);
            if (duplicateMap.get(ch) == 1 || checkMap.get(ch) == duplicateMap.get(ch)) {
                builder.append((char)min);
                min = Integer.MAX_VALUE;
            }
        }

        return builder.toString();
    }
}
```

<br>

### GPT 수정 코드
```java
import java.util.*;

class Solution {
    public String removeDuplicateLetters(String s) {
        Map<Character, Integer> countMap = new HashMap<>();
        Set<Character> seen = new HashSet<>();
        Stack<Character> stack = new Stack<>();

        // 각 문자의 빈도를 계산합니다.
        for (char ch : s.toCharArray()) {
            countMap.put(ch, countMap.getOrDefault(ch, 0) + 1);
        }

        // 문자열을 하나씩 처리합니다.
        for (char ch : s.toCharArray()) {
            // 이미 스택에 있는 문자는 건너뜁니다.
            if (seen.contains(ch)) {
                countMap.put(ch, countMap.get(ch) - 1);
                continue;
            }

            // 현재 문자가 스택의 최상단 문자보다 작고, 스택의 최상단 문자가 나중에 다시 등장한다면 스택에서 제거합니다.
            while (!stack.isEmpty() && ch < stack.peek() && countMap.get(stack.peek()) > 0) {
                seen.remove(stack.pop());
            }

            // 스택에 현재 문자를 추가하고, seen 집합에 추가합니다.
            stack.push(ch);
            seen.add(ch);
            countMap.put(ch, countMap.get(ch) - 1);
        }

        // 스택에 있는 문자들을 연결하여 결과를 반환합니다.
        StringBuilder builder = new StringBuilder();
        for (char ch : stack) {
            builder.append(ch);
        }

        return builder.toString();
    }
}
/* Stack을 사용해야 하는 이유
- 문제의 요구사항은 모든 문자를 한 번씩만 포함한 가장 작은 사전 순 문자열을 반환해야 함
- 문자열의 상대적 순서를 유지해야 함
- 스택의 최상단 문자와 현재 문자를 비교하면서, 스택에 있는 문자가 더 크고 앞으로 다시 등장할 수 있다면 그 문자를 스택에서 제거하여 이를 통해 작은 문자가 앞으로 오도록 함 (스택의 최상단만 비교하면 됨)
- 이미 스택에 있는 문자는 다시 추가하지 않기 위해 seen 집합을 사용하여 추적하고 countMap을 사용하여 각 문자가 문자열에서 앞으로 몇 번 더 등장할 수 있는지 추적
*/
```

- runtime : 5ms
- memory : 42.64mb

<br>

### 풀이 첫번째 코드
```java
import java.util.*;

class Solution {
    public Set<Character> toSortedSet(String s) {
        // 문자열을 문자 단위 집합으로 저장할 변수 선언
        Set<Character> set = new TreeSet<>(new Comparator<Character>() {
            // 비교 메서드 정의
            @Override
            public int compare(Character o1, Character o2) {
                if (o1 == o2) { // 동일한 문자면 무시
                    return 0;
                } else if (o1 > o2) {   // 새로운 문자 o1이 기본 문자 o2보다 크면 뒤에 위치
                    return 1;
                } else {    // 작으면 앞에 위치
                    return -1;
                }
            }
        });

        // 문자열을 문자 단위로 집합에 저장, 정렬된 상태로 저장됨
        for(int i = 0; i < s.length(); i++) {
            set.add(s.charAt(i));
        }

        return set;
    }

    public String removeDuplicateLetters(String s) {
        // 정렬된 문자열 집합 순회
        for(char c : toSortedSet(s)) {
            // 해당 문자가 포함된 위치로부터 접미사로 지정
            String suffix = s.substring(s.indexOf(c));
            
            // 전체 집합과 접미사 집합이 일치하면 해당 문자 정답에 추가하고 재귀 호출 진행
            if (toSortedSet(s).equals(toSortedSet(suffix))) {
                return c + removeDuplicateLetters(suffix.replace(String.valueOf(c), ""));
            }
        }

        return "";
    }
}
```

- runtime : 63ms ~ 65ms
- memory : 44.58mb