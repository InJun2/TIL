import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    class Honey implements Comparable<Honey> {
        int row, col, honeyValue;

        public Honey(int row, int col) {
            this.row = row;
            this.col = col;
            this.honeyValue = 0;
        }

        public void getHoney() {
            int maxHoney = 0;
            // M길이 내의 모든 부분 집합을 탐색
            for (int subset = 0; subset < (1 << M); subset++) { // 부분 집합은 2^M 개
                int honeyCnt = 0;   // 부분집합 비트마다 반복해야 하므로 초기화가 필요
                int honeySum = 0;
                for (int i = 0; i < M; i++) {   // 꿀칸이 차지하는 비트 수만큼 반복 
                    if ((subset & (1 << i)) != 0) { // 해당 비트가 0이 아니라면 (1이라면) 해당 칸 값 구하기
                        honeyCnt += board[row][col + i];
                        honeySum += (int) Math.pow(board[row][col + i], 2);
                    }
                }
                if (honeyCnt <= C) {    // 만약 해당 경우가 최대 수납 꿀이 넘으면 해당 경우 패스
                    maxHoney = Math.max(maxHoney, honeySum);
                }
            }
            honeyValue = maxHoney;
            list.add(this);
        }

        @Override
        public int compareTo(Honey o) {
            return o.honeyValue - this.honeyValue;
        }
    }

    int N, M, C;
    int[][] board;
    List<Honey> list;
    int result;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int t = 1; t <= T; t ++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            list = new ArrayList<>();
            result = 0;

            board = new int[N][N];

            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                for(int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            for(int i = 0; i < N; i++) {
                for(int j = 0; j <= N - M; j++) {
                    new Honey(i, j).getHoney();
                }
            }

            Collections.sort(list);

            Honey firstHoney = list.get(0);
            for(int i = 1; i < list.size(); i++) {
                Honey secondHoney = list.get(i);

                if(secondHoney.row == firstHoney.row) {
                    if(Math.abs(firstHoney.col - secondHoney.col) < M) {
                        continue;
                    }
                }

                result = firstHoney.honeyValue + secondHoney.honeyValue;
                break;
            }

            sb.append("#")
                    .append(t)
                    .append(" ")
                    .append(result)
                    .append("\n");
        }

        System.out.println(sb);
    }
}
