# LIS (Longest Increasing Subsequence, 최장 증가 부분 수열)

### 최장 증가 부분 수열
- 어떤 수열이 왼쪽에서 오른쪽으로 나열되어 있으면, 그 배열 순서를 유지하면서 크기가 점진적으로 커지는 가장 긴 부분 수열을 추출하는 문제
- 수열의 모든 부분 집합을 구하여 그 부분 집합이 증가 수열인지 판별하고 증가 수열 중 가장 길이가 긴 값을 구함
    - 부분 수열의 각 수는 서로 연속할 필요는 없음
- 최장 증가 부분 순열 탐색에서 접근 방법은 완전 탐색에서 DP, 이분 탐색을 이용한 방법 등이 있음
    - 완전 탐색은 비효율적이므로 다이나믹 프로그래밍(DP)를 통해 구현 가능 (O(2^n) 시간 복잡도)
    - 추가적으로 이분 탐색을 이용한 방법으로 구현 가능 (O(NlogN) 시간 복잡도)

<br>

### DP 접근 방법
- 입력 : 숫자 배열 a1, a2, a3 ... an
- LIS(i) : a1, a2 .... ai 에서 최장 부분 수열의 길이
- Case1 : LIS(i)가 ai를 부분 수열의 마지막으로 포함하지 않는다면 LIS(i) = LIS(i - 1)
- Case2 : LIS(i)가 ai를 부분 수열의 마지막으로 포함한다면 LIS(i) = ?
    - 증가 수열의 관계인 aj < ai 인 aj를 찾아야함
    - j 값을 알 수 없으므로 모두 검색해야 함
    - 그 중 최대값을 찾아 1 증가시켜 LIS(i)에 저장한다
    - LIS(i) 중에서 최대값을 찾는다 (i : 1 ~ n)

<br>

### DP 풀이
- 수열의 한 원소에 대해, 그 원소에서 끝나는 최장 증가 수열에서 최장 증가 수열의 k를 제외한 모든 원소들은 반드시 k보다 작아야 할 것
- 가장 마지막 번째 값보다 크다면 배열 다음번째에 저장
- 만약 마지막번째 숫자보다 작다면 해당 저장되었던 dp 중 특정 인덱스보다 큰 마지막 번호 숫자에 저장

<br>

#### 풀이 예시
- 예시 배열 수열으로는 (1, 5, 4, 2, 3, 8)
- 1에서 끝나는 LIS 길이 : 1 (1)
- 5에서 끝나는 LIS 길이 : 2 (1, 5)
- 4에서 끝나는 LIS 길이 : 2 (1, 4)
- 2에서 끝나는 LIS 길이 : 2 (1, 2)
- 3에서 끝나는 LIS 길이 : 3 (1, 2, 3)
- 8에서 끝나는 LIS 길이 : 4 (1, 2, 3, 8)
- 즉 최장 증가 수열의 길이는 4

<br>

### 이분 탐색 풀이
- DP를 이용한 방법은 완전 탐색에 비하면 시간 복잡도 면에서 효율적이지만 여전히 O(N^2)으로 동작
- 이분 탐색 방식은 위와 동일하게 동작하되 DP 배열에서 탐색하고 추가하는 과정을 이분 탐색으로 접근하여 업데이트 하는 방식
- DP 배열은 LIS를 유지하면서 길이만을 추적하며 현재까지 만들어진 LIS은 오름차순으로 정렬되어 있으며 해당 LIS 배열에서 마지막 값을 관리하는 용도로 사용됨

<br>

### SWEA 3307. 최장 증가 부분 순열 풀이
#### 1. DP 만을 이용한 풀이

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    int N;
    int[] array;
    int[] dp;
    int last;

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            N = Integer.parseInt(br.readLine());
            array = new int[N];
            dp = new int[N];
            last = 0;
            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                array[i] = Integer.parseInt(st.nextToken());
            }

            dp[0] = array[0];
            for(int i = 1; i < N; i++) {
                if(array[i] > dp[last]) {
                    dp[++last] = array[i];
                } else {
                    for(int j = 0; j <= last; j++) {
                        if(array[i] <= dp[j]) {
                            dp[j] = array[i];
                            break;
                        }
                    }
                }
            }

            sb.append("#")
                    .append(t + 1)
                    .append(" ")
                    .append(last + 1)
                    .append("\n");
        }

        System.out.println(sb);
    }
}
```
<br>

#### 2. 이분 탐색을 추가한 DP

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Solution {
    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    int N;
    int[] array;
    int[] dp;
    int last;

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            N = Integer.parseInt(br.readLine());
            array = new int[N];
            dp = new int[N];
            last = 0;
            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                array[i] = Integer.parseInt(st.nextToken());
            }

            dp[0] = array[0];
            last = 0;

            for (int i = 1; i < N; i++) {
                if (array[i] > dp[last]) {
                    // 새로운 값이 LIS의 마지막 값보다 크면 LIS 확장
                    dp[++last] = array[i];
                } else {
                    // 이분 탐색을 통해 현재 array[i]가 들어갈 위치를 찾음
                    int pos = Arrays.binarySearch(dp, 0, last + 1, array[i]);
                    if (pos < 0) {
                        pos = -(pos + 1);  // 삽입 위치를 찾음
                    }
                    dp[pos] = array[i];  // 적절한 위치에 값을 업데이트
                }
            }

            sb.append("#")
              .append(t + 1)
              .append(" ")
              .append(last + 1)
              .append("\n");
        }

        System.out.println(sb);
    }
}

```

<br>

#### Arrays.binarySearch() 메서드
- 자바에서 제공하는 이분 탐색 메서드
- 이 함수는 배열에서 원하는 값이 위치한 인덱스를 반환하거나, 그 값이 배열에 없을 경우 그 값이 삽입되어야 할 위치를 음수로 반환
    - 반환값이 음수라면 삽입 되어야 할 위치를 찾아야하므로 삽입돌 인덱스의 반전된 값을 구해야 함
    - 만약 -3을 반환했다면 이는 2번 인덱스에 값이 들어가야함을 의미하여 -(pos + 1) 과 같이 양수 인덱스로 변환해주어야함


<br>

## Referecne
- https://4legs-study.tistory.com/106