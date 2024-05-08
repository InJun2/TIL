# 42. Trapping Rain Water
- 고도 높이 배열이 주어지면 비가 높이 1만큼 왔다고 했을때 얼마나 많은 물이 쌓일 수 있는지 계산하기
    - https://leetcode.com/problems/trapping-rain-water/description/

<br>

### 풀이 방법
- 해당 문제는 이전 올바른 괄호 만들기 문제처럼 스택으로 풀 수 있지 않을까 생각하고 진행
- 어떤 원리로 값을 보관할지 고민해야 하는데 우선 어떤 경우 물이 쌓인것을 확인할지 생각해 봤을 때 다음과 같음
    - 이후 값이 크다면 현재 이전까지 물이 쌓여있을 것이고, 이후 값이 작다면 해당 인덱스부터 물이 쌓이기 시작할 것
    - 이 두가지 중 스택에 모두 넣어두고 현재 높이보다 크거나 같기 전까지 값이 나올때까지 확인하고 해당 사이의 비의 양을 확인하는 방법으로 진행
- 직접 풀어보는 방식은 풀이하지 못하고 답지를 참고

<br>

### 첫번째 코드
- 진행하려했으나 최대 높이가 된 후 값을 구하지 못하겠음..

```java
import java.util.Stack;

class Solution {
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int maxHeight = height[0];
        int index = 0;

        for(int heightStack : height) {
            stack.push(heightStack);
        }

        maxHeight = stack.pop();

        while(!stack.isEmpty()) {
            int next = stack.peek();

            if(next > maxHeight) {
                
            }
        }
    }
}
```

<br>

### 해설 코드
- 재미있는 사실은 Stack은 동기화된 Vector로 구현되어 있어 싱글 스레드에서는 ArrayDeque 구현이 더 효율적이라고 함
    - Stack<Integer> stack = new Stack<>(); runtime : 6ms
    - Deque<Integer> stack = new ArrayDeque<>(); runtime : 3ms 

```java
import java.util.Deque;
import java.util.ArrayDeque;

class Solution {
    public int trap(int[] height) {
        Deque<Integer> stack = new ArrayDeque<>();
        int volume = 0;

        for(int i = 0; i < height.length; i++) {
            // 변곡점을 만나는 경우
            while(!stack.isEmpty() && height[i] > height[stack.peek()]) {
                // 스택에서 꺼낸다
                Integer top = stack.pop();

                if (stack.isEmpty()) {
                    break;
                }

                // 스택의 마지막 위치까지를 거리로 계산
                int distance = i - stack.peek() - 1;

                // 현재 높이 또는 스택의 마지막 위치 높이 중 낮은 값에 방금 꺼낸 높이의 차이를 물 높이로 지정
                int waters = Math.min(height[i], height[stack.peek()]) - height[top];

                // 물이 쌓이는 양은 거리와 물 높이의 곱
                volume += distance * waters;
            }

            // 진행하면서 현재 위치를 스택에 삽입
            stack.push(i);
        }

        return volume;
    }
}
```

- runtime : 3ms
- memory : 45.67mb

<br>

### 두 번째 해설코드
- 투 포인트 방식이 가장 빠름

```java
class Solution {
    public int trap(int[] height) {
        int water = 0;

        int l = 0;
        int r = height.length-1;

        int maxL = height[l];
        int maxR = height[r];

        while (l < r){
            if(maxL < maxR){
                l+=1;
                maxL = Math.max(maxL, height[l]);
                water += maxL - height[l];
            }
            else {
                r-= 1;
                maxR = Math.max(maxR, height[r]);
                water += maxR - height[r];
            }
        }

        return water;
    }
}
```

- runtime : 0ms
- memory : 46.26mb