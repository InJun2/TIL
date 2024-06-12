# 347. Top K Frequent Elements
- 정수 배열 nums 이 주어지고 그 중 가장 빈번한 요소 순서대로 k개 반환
    - https://leetcode.com/problems/top-k-frequent-elements/

<br>

### 풀이 방법
- Map을 사용해서 value를 기록해두고 entrySet으로 순회하여 가장 큰 value 반환하기
- 이후 우선순위 큐를 생성하여 Map의 value를 통해 역순으로 정렬하고 이후 해당 큐에 넣고 k개 만큼 빼는 것으로 수정

<br>

### 첫 번째 코드
- 중간에 EntrySet을 사용하는 법을 자세히 몰라 GPT를 참조

```java
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] result = new int[k];

        for(int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> pq = 
            new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            pq.offer(entry);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        for (int i = k - 1; i >= 0; i--) {
            result[i] = pq.poll().getKey();
        }

        return result;
    }
}
```

- runtime : 12ms
- memory : 45.35mb

<br>

### 책의 풀이
- 방법은 비슷

```java
import java.util.HashMap;
import java.util.PriorityQueue;

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] result = new int[k];

        for(int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);

        for (int elem : map.keySet()) {
            pq.add(new int[]{elem, map.get(elem)});
        }

        for (int i = 0; i < k; i++) {
            result[i] = pq.poll()[0];
        }

        return result;
    }
}
```

- runtime : 13ms
- memory : 45.38mb