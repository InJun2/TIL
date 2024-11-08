import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    boolean check[][][];
    int[] target;
    int[] dr = {0, 0, 1, -1};
    int[] dc = {1, -1, 0, 0};
    int result;


    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int t = 1; t < T + 1; t++) {
            check  = new boolean[201][201][2];
            result = 0;
            st = new StringTokenizer(br.readLine());
            int startRow = Integer.parseInt(st.nextToken()) + 100;
            int startCol = Integer.parseInt(st.nextToken()) + 100;
            int endRow = Integer.parseInt(st.nextToken()) + 100;
            int endCol = Integer.parseInt(st.nextToken()) + 100;

            target = new int[]{endRow, endCol};

            bfs(startRow, startCol);

            sb.append("#").append(t).append(" ").append(result).append("\n");
        }

        System.out.println(sb.toString());
    }

    private void bfs(int row, int col) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{row, col, 0, 0});
        queue.add(new int[]{row, col, 0, 1});
        check[row][col][0] = true;
        check[row][col][1] = true;

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();

            if(curr[0] == target[0] && curr[1] == target[1]) {
                result = curr[2];
                return;
            }

            if(curr[3] == 0) {
                for(int i = 0; i < dr.length / 2; i++) {
                    int nr = curr[0] + dr[i];
                    int nc = curr[1] + dc[i];

                    if(nr < 0 || nc < 0 || nr >= 201 || nc >= 201 || check[nr][nc][0]) {
                        continue;
                    }

                    check[nr][nc][0] = true;
                    queue.add(new int[]{nr, nc, curr[2] + 1, 1});
                }
            } else {
                for(int i = dr.length / 2 ; i < dr.length; i++) {
                    int nr = curr[0] + dr[i];
                    int nc = curr[1] + dc[i];

                    if(nr < 0 || nc < 0 || nr >= 201 || nc >= 201 || check[nr][nc][1]) {
                        continue;
                    }

                    check[nr][nc][1] = true;
                    queue.add(new int[]{nr, nc, curr[2] + 1, 0});
                }
            }
        }
    }
}