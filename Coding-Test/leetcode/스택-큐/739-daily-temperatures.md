# 739. 739. Daily Temperatures
- 정수 배열이 temperatures 일일 기온을 나타내는 경우 더 따뜻한 기온을 얻기 위해 그 날 이후 기다려야 하는 일수를 배열 answer로 반환
    - 가능한 미래의 날이 없다면 유지할 것 (answer[i] = 0)
    - 1 <= temperatures.length <= 10^5
    - https://leetcode.com/problems/daily-temperatures/description/

<br>

### 풀이 방법
- 첫 번째로 생각난 방법은 for문을 통해 해당 인덱스 이후부터 더 작은 값의 인덱스까지 비교
    - 최악의 경우 n + ( n - 1 ) + ( n - 2 ) .. + 1 이므로 n(n - 1) -> O(n^2) 해당 요구사항의 온도 배열의 개수가 매우 많으므로 진행하지 않음
- 그렇다면 스택에 값을 넣어두고 해당 인덱스보다 높은 수가 나올때 까지 꺼내고 측정해야 함
    - 그런데 그 뒤의 값들도 이전 조회했던 값과 유사할 확률이 높아 최적화할 수 있을 것 같은데 방안이 떠오르지 않아 책을 참조

<br>

### 풀이 코드

```java
import java.util.Deque;
import java.util.ArrayDeque;

class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        // 결과 처리를 위한 스택 선언
        Deque<Integer> stack = new ArrayDeque<>();
        // 결과를 담을 정수형 배열
        int[] result = new int[temperatures.length];

        for(int i = 0; i < temperatures.length; i++) {
            // 현재 온도가 스택에 있는 온도보다 높다면 꺼내서 결과를 업데이트
            while(!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int last = stack.pop();
                result[last] = i - last;
            }

            stack.push(i);
        }

        return result;
    }
}
```

- runtime : 22ms
- memory : 56.66mb

<br>

### 다른 사람 풀이
- 스택을 사용하지 않고 기존 배열을 비교하는 방법이 더 빠르게 측정되어 있길래 작성해봄
- 하지만 이전 방식이 한 번의 순회로 모든 온도에 대한 더 따뜻한 온도까지의 일수를 계산할 수 있기 때문에 성능이 더 좋을 수 좋다고 함
    - 스택을 사용하자..

```java
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];
        int hottest = 0;

        for(int i = n - 1; i >= 0; i--){
            int currtemp = temperatures[i];
            if(currtemp >= hottest){
                hottest = currtemp;
                continue;
            }

            int days = 1;
            while(temperatures[i+days] <= currtemp){
                days += res[i+days];
            }
            res[i] = days;
        }
        return res;
    }
}
```

- runtime : 6ms