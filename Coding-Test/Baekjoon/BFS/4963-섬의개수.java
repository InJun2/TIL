import java.io.*;
import java.util.*;

public class Main {

    int N, M;
    boolean[][] checked;
    int result;

    int[] dx = {1, 1, 1, 0, 0, -1, -1, -1};
    int[] dy = {1, -1, 0, 1, -1, 1, -1, 0};

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        while(true) {
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            checked = new boolean[N][M];
            result = 0;

            if(N == 0 && M == 0) {
                break;
            }

            for(int i = 0; i < N; i ++) {
                st = new StringTokenizer(br.readLine());

                for(int j = 0; j < M; j ++) {
                    int area = Integer.parseInt(st.nextToken());

                    if(area == 0) {
                        checked[i][j] = true;
                    }
                }
            }

            for(int i = 0; i < N; i ++) {
                for(int j = 0; j < M; j ++) {
                    if(!checked[i][j]) {
                        bfs(i, j);
                        result++;
                    }
                }
            }

            sb.append(result)
                    .append("\n");
        }

        System.out.println(sb);
    }

    private void bfs(int x, int y) {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{x, y});
        checked[x][y] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i = 0; i < dx.length; i++) {
                int mRow = cur[0] + dx[i];
                int mCol = cur[1] + dy[i];

                if(mRow < 0 || mCol < 0 || mRow >= N || mCol >= M || checked[mRow][mCol]) {
                    continue;
                }

                checked[mRow][mCol] = true;
                q.add(new int[]{mRow, mCol});
            }
        }
    }
}
