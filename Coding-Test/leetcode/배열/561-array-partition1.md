# 561. Array Partition 1
- n개의 페어를 구성하고 n개의 페어들의 작은값인 min(a, b)의 합으로 만들 수 있는 가장 큰 수를 출력
    - https://leetcode.com/problems/array-partition/description/

<br>

### 풀이 방법
- 정렬하고 낮은 수끼리 묶고 높은 수끼리 묶으면 되는게 아닌지..?
    - 낮은 수끼리 묶는다면 첫번째 수만 더하면 됨
    - java의 경우 다른 사람들의 풀이를 보면 3ms 까지 나오지만 sort 메서드를 따로 구현했하였음
        - sort는 많은 비용이 듬

<br>

### 구현 코드

```java
import java.util.Arrays;

class Solution {
    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int result = 0;

        for(int i = 0; i < nums.length / 2; i++) {
            result += nums[i * 2];
        }

        return result;
    }
}
```

- runtime : 12ms
- memory : 46.47mb

<br>

### 다른 사람 풀이

```java
class Solution {
    public int arrayPairSum(int[] nums) {
        // return heleprSort(nums);
        return helper(nums);
    }

    //Tc: O(nlogn)
    //Sc: O(1)
    private  int heleprSort(int[] nums) {
        int max = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i = i + 2) {
            max = max + Math.min(nums[i], nums[i + 1]);
        }
        return max;
    }

    public int helper(int[] nums) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            max = Math.max(num, max);
            min = Math.min(num, min);
        }
        short[] count = new short[max - min + 1];
        for (int num : nums) {
            count[num - min]++;
        }
        int result = 0;
        int rem = 1;
        for (int i = 0; i < count.length; i++) {
            short c = count[i];
            if (c != 0) {
                int cr = c + rem;
                rem = cr & 1;
                result += (i + min) * (cr >>> 1);
            }
        }
        return result;
    }
}
```

- runtime : 3ms