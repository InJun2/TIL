import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    int[] dr = {0, 0, 1, -1};
    int[] dc = {1, -1, 0, 0};
    int N, M, result;
    int[][] board;
    boolean[][] visited;
    Queue<int[]> queue;

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int t = 1; t < T + 1; t++) {
            result = 0;
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            board = new int[N][M];
            visited = new boolean[N][M];
            queue = new ArrayDeque<>();

            for(int i = 0; i < N; i++) {
                String str = br.readLine();

                for(int j = 0; j < M; j++) {
                    if(str.charAt(j) == 'W') {
                        queue.offer(new int[]{i, j, 1});
                        visited[i][j] = true;
                    }
                }
            }

            bfs();

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < M; j++) {
                    result += board[i][j];
                }
            }

            sb.append("#").append(t).append(" ").append(result).append("\n");
        }

        System.out.println(sb.toString());
    }

    private void bfs() {
        while(!queue.isEmpty()) {
            int[] pos = queue.poll();
            int row = pos[0];
            int col = pos[1];
            int cnt = pos[2];

            for(int i = 0; i < dr.length; i++) {
                int mr = row + dr[i];
                int mc = col + dc[i];

                if(mr < 0 || mr >= N || mc < 0 || mc >= M || visited[mr][mc]) {
                    continue;
                }

                visited[mr][mc] = true;
                board[mr][mc] = cnt;
                queue.offer(new int[]{mr, mc, cnt + 1});
            }
        }
    }
}