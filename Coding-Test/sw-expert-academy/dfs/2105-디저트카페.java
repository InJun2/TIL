import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) throws NumberFormatException, IOException {
        new Solution().solution();
    }

    int N;
    int[][] board;
    boolean[][] visited;
    int maxDesserts;

    int[] mRow = {1, 1, -1, -1}; // 오른쪽 아래, 왼쪽 아래, 왼쪽 위, 오른쪽 위
    int[] mCol = {1, -1, -1, 1};

    public void solution() throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            board = new int[N][N];
            visited = new boolean[N][N];
            maxDesserts = -1;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int y = 0; y < N; y++) {
                    board[i][y] = Integer.parseInt(st.nextToken());
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    dfs(i, j, i, j, 0, 1, new HashSet<>());
                }
            }

            sb.append("#")
              .append(t)
              .append(" ")
              .append(maxDesserts)
              .append("\n");
        }

        System.out.println(sb);
    }

    private void dfs(int startX, int startY, int x, int y, int direction, int count, Set<Integer> desserts) {
        desserts.add(board[x][y]);
        visited[x][y] = true;

        for (int i = direction; i < 4; i++) {
            int newRow = x + mRow[i];
            int newCol = y + mCol[i];

            if (newRow == startX && newCol == startY && count > 2) {
                maxDesserts = Math.max(maxDesserts, count);
                visited[x][y] = false;
                desserts.remove(board[x][y]);
                return;
            }

            if (isValid(newRow, newCol) && !visited[newRow][newCol] && !desserts.contains(board[newRow][newCol])) {
                dfs(startX, startY, newRow, newCol, i, count + 1, desserts);
            }
        }

        visited[x][y] = false;
        desserts.remove(board[x][y]);
    }

    private boolean isValid(int row, int col) {
        return row < N && col < N && row >= 0 && col >= 0;
    }
}
