# 121. Best Time to Buy and Sell Stock
- 날짜에 특정 주식 가격을 나타내는 배열이 제공되는데 특정 일자에 주식을 사고 미래에 팔아서 가장 큰 수익을 얻을 때의 최대 이익을 반환
    - 이익을 얻을 수 없으면 0 반환
    - https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/

<br>

### 풀이 방법
- 하나하나 최대값을 확인해보는 브루트 포스로 우선 구현
    - 타임 아웃 발생
- 그러면 뒤에서 부터 가장 높았던 가격을 배열에 넣어두고 다시 순회하면서 해당 배열의 값을 통해 최대값 확인
- 이후 책을 참조하니 뒤에서 진행할 필요도 없고 배열에 넣을 필요도 없었음
    - 최소 값을 변수로 지정해두고 현재 인덱스의 값과 계속 Math.min() 진행
    - 이후 반복문 진행하면서 현재 인덱스의 값에서 minValue 를 뺀 최대값을 반환

<br>

### 첫 번째 코드

```java
class Solution {
    public int maxProfit(int[] prices) {
        int max = 0;

        for(int i = 0; i < prices.length; i ++) {
            for(int j = i + 1; j < prices.length; j++) {
                max = Math.max(max, prices[j] - prices[i]);
            }
        }

        return max;
    }
}
```

<br>

### 두 번째 코드

```java
class Solution {
    public int maxProfit(int[] prices) {
        int[] maxArray = new int[prices.length];
        int max = Integer.MIN_VALUE;

        for(int i = prices.length - 1; i >= 0; i --) {
            max = Math.max(prices[i], max);
            maxArray[i] = max;
        }

        max = 0;
        for(int i = 0; i < prices.length; i++) {
            max = Math.max(max, maxArray[i] - prices[i]);
        }

        return max;
    }
}
```

- runtime : 1 ~ 2ms
- memory : 56.59mb

<br>

### 풀이 코드

```java
class Solution {
    public int maxProfit(int[] prices) {
        int maxProfit = 0, minPrice = prices[0];
        for(int price : prices) {
            minPrice = Math.min(minPrice, price);
            maxProfit = Math.max(maxProfit, price - minPrice);
        }

        return maxProfit;
    }
}
```

- runtime : 1 ~ 2ms
- memory : 61.68mb