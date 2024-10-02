/*  첫번째 풀이
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

            // 가장 마지막 번째 값보다 크다면 배열 다음번째에 저장
            // 만약 마지막번째 숫자보다 작다면 해당 저장되었던 dp 중 특정 인덱스보다 큰 마지막 번호 숫자에 저장
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

*/

// GPT 참조 변경 풀이

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

        int T = Integer.parseInt(br.readLine());  // 테스트 케이스의 수

        for (int t = 0; t < T; t++) {
            N = Integer.parseInt(br.readLine());  // 수열의 길이
            array = new int[N];
            dp = new int[N];
            last = 0;
            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                array[i] = Integer.parseInt(st.nextToken());
            }

            dp[0] = array[0];  // LIS의 첫 번째 값 설정
            last = 0;  // 현재 LIS의 길이

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
              .append(last + 1)  // LIS의 길이 출력
              .append("\n");
        }

        System.out.println(sb);
    }
}
