# 238. Product of Array Except Self
- 입력 받은 매열에서 ouput[i]가 자신을 제외한 나머지 모든 엘리먼트의 곱셈 결과가 되도록 출력
    - 접두사 또는 접미사의 곱은 32비트 정수에 맞는 것이 보장
    - O(n) 나누기 연산을 사용하지 않고 시간 내에 실행되는 알고리즘 작성
    - https://leetcode.com/problems/product-of-array-except-self/description/

<br>

### 풀이 방법
- 처음 생각난 방법은 자료구조 안에 값을 넣었다가 각각 요소을 제거했다가 모든 값을 곱하고 다시 해당 요소를 넣는 방법
    - 아무생각 없이 구현했는데 구현하고 나서보니 많은 비용이 발생하는 쓸모 없는 코드가 되었음
- 이중 포문으로 간단하게 구현해보았으나 시간 초과 재 발생
- 책을 참조하여 왼쪽 오른쪽으로 곱셈으로 변경

<br>

### 첫 번째 코드
- 불필요한 부분이 너무 많아 시간 초과 발생
    - indexOf, remove에 많은 비용 발생
- 

```java
import java.util.List;
import java.util.LinkedList;

class Solution {
    public int[] productExceptSelf(int[] nums) {
        List<Integer> list = new LinkedList<>();
        int[] result = new int[nums.length];

        for(int num : nums) {
            list.add(num);
        }

        for(int i = 0; i < nums.length; i++) {
            int index = list.indexOf(nums[i]);
            list.remove(index);
            result[i] = 1;

            for (int num : list) {
                result[i] *= num;
            }
            
            list.add(nums[i]);
        }

        return result;
    }
}
```

<br>

### 두 번째 코드

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];

        for(int i = 0; i < nums.length; i++) {
            result[i] = 1;

            for(int y = 0; y < nums.length; y++) {
                if(i == y) {
                    continue;
                }

                result[i] *= nums[y];
            }
        }

        return result;
    }
}
```

<br>

### 풀이 코드

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int mult = 1;

        for(int i = 0; i < n; i++) {    // 1 3 5 7
            result[i] = mult;   // 1 1 3 15
            mult *= nums[i];    // 1 3 15 (105)
        }
        mult = 1;

        for(int j = n - 1; j >= 0; j--) {   // 7 5 3 1
            result[j] *= mult;  // (15 * 1) (3 * 7) (1 * 35) (1 * 105)
            mult *= nums[j];    // 7 (5 * 7) (35 * 3) (105 * 1)
        }

        return result;
    }
}
```

- runtime : 1 ~ 2ms
- memory : 55.15mb