import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 @author 황인준
 @since 2024. 8. 18.
 @link https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWxQ310aOlQDFAWL
 @performance   우리 N에 대해 모든 햄스터 수의 배치에 대해 중복 순열로 접근하고 해당 기록에 맞는 또한 오름차순인
                배치가 되도록 유효성 검사를 진행, 해당 재귀 기저조건에 대해 고민하다 결국 풀이 참조하였음
 @category #완전탐색 #중복순열 #백트래킹
 @note
 */
class Solution {
    private int N, X, M;   // 우리의 수, 우리 별 햄스터 최대 수, 기록의 수
    private int[][] records;
    private int[] cages, bestCages;
    private int maxHamsters;

    public static void main(String args[]) throws Exception {
        new Solution().solution();
    }

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());   // 우리의 수
            X = Integer.parseInt(st.nextToken());   // 각 우리의 햄스터 수 최대 값
            M = Integer.parseInt(st.nextToken());   // 기록의 수

            records = new int[M][3];
            cages = new int[N];
            bestCages = new int[N];
            maxHamsters = -1;

            for(int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                records[i][0] = Integer.parseInt(st.nextToken()) - 1;   // l번 부터
                records[i][1] = Integer.parseInt(st.nextToken()) - 1;   // r번 까지
                records[i][2] = Integer.parseInt(st.nextToken());   // s 마리
            }

            dfs(0);

            StringBuilder result = new StringBuilder();
            if(maxHamsters == -1) {
                result.append("-1");
            } else {
                for(int n : bestCages) {
                    result.append(n)
                            .append(" ");
                }
            }

            sb.append("#")
                    .append(t + 1)
                    .append(" ")
                    .append(result)
                    .append("\n");
        }

        System.out.println(sb);
        br.close();
    }

    private void dfs(int cnt) {
        if(cnt == N) {
            if (isValid()) {    // 기록의 수가 맞는지 확인
                int totals = getTotalHamsters();
                if (totals > maxHamsters ||
                        (totals == maxHamsters && sortCheck())) {
                    maxHamsters = totals;
                    bestCages = cages.clone();
                }
            }
            return;
        }

        for(int i = 0; i <= X; i++) {
            cages[cnt] = i;
            dfs(cnt + 1);
        }
    }

    private boolean isValid() {
        for (int[] record : records) {
            int sum = 0;
            for (int i = record[0]; i <= record[1]; i++) {
                sum += cages[i];
            }

            if (sum != record[2]) {
                return false;
            }
        }
        return true;
    }

    private int getTotalHamsters() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += cages[i];
        }

        return sum;
    }

    private boolean sortCheck() {
        for (int i = 0; i < N; i++) {
            if (cages[i] < bestCages[i]) {
                return true;
            } else if (cages[i] > bestCages[i]) {
                return false;
            }
        }
        return false;
    }
}