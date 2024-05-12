# 1. Two Sum
- 정수배열 nums와 정수가 주어지면 두 숫자의 합이 target이 되는 인덱스 반환
    - 무조건 해당 숫자가 되는 두 숫자가 존재함
    - https://leetcode.com/problems/two-sum/description/

<br>

### 풀이 방법
- 특정 수 두개를 더해서 target이 나올때 까지 확인해야하는데 이중 포문으로 구현속도가 어떤지 궁금해서 이중 포문으로 구현
    - 역시 많이 느림
- 이후 책을 보고 Map 최적화 진행
    - 처음 최적화는 Map을 사용한 방법으로 인덱스를 기록해야하기에 사용하지 못할 줄 알았으나 index 값을 key로 두고 index를 value로 Map 구현
    - target이 구해야하는 결과이므로 target-key의 값이 있는지 확인하면 됨. 하지만 같은 수를 더했을 때의 경우를 제외해야함
    - 이후 Map에서 찾는 i와 numsMap.get(target - nums[i])가 target이 되는 인덱스 두개가 됨
- 이후 책을 보고 투 포인터 방법 기재
    - 투 포인터 방법으로는 해당 문제를 풀 수 없지만 인덱스를 구하는 것이 아니라면 해당 방법으로 풀이 가능

<br>

### 첫번째 코드

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int index = 0; index < nums.length - 1; index++) {
            for (int y = index + 1; y < nums.length; y++) {
                if (nums[index] + nums[y] == target) {
                    return new int[]{index, y};
                }
            }
        }

        return null;
    }
}
```

- runtime : 45ms
- memory : 44.74mb

<br>

### 두번째 코드

```java
import java.util.Map;
import java.util.HashMap;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numsMap = new HashMap<>();

        for(int i = 0; i < nums.length; i ++) {
            numsMap.put(nums[i], i);
        }

        for(int i = 0; i < nums.length; i++) {
            if(numsMap.containsKey(target - nums[i]) && i != numsMap.get(target - nums[i])) {
                return new int[]{i, numsMap.get(target - nums[i])};
            }
        }

        return null;
    }
}
```

- runtime : 4ms
- memory : 44.52mb

<br>

### 인덱스를 구하는 것이 아니었다면 투포인트 코드
- 배열을 정렬해두고 투포인트 방법으로 코드 구현도 있었을 것
    - 그러나 해당 문제는 인덱스를 필요로 하므로 해당 방법으로 풀이 불가능

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while(left != right) {
            // 합이 target보다 작으면 왼쪽 포인터를 오른쪽으로 이동
            if (nums[left] + nums[right] < target) {
                left += 1;
            } else if (nums[left] + nums[right] > target) {
                // 합이 target보다 크면 오른쪽 포인터를 왼쪽으로 이동
                right -= 1;
            } else {
                return new int[]{left, right};
            }
        }

        return null;
    }
}
```

