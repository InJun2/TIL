import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N, M;
    int[][] board;
    boolean[][][] visited;

    int[] mRow = {1, -1, 0, 0};
    int[] mCol = {0, 0, 1, -1};

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N][M];
        visited = new boolean[N][M][2];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                board[i][j] = line.charAt(j) - '0';
            }
        }

        int result = bfs();
        System.out.println(result);
    }

    private int bfs() {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, 0, 0, 1});   // row, col, break, cnt
        visited[0][0][0] = true;

        while(!queue.isEmpty()) {
            int[] array = queue.poll();
            int row = array[0], col = array[1];
            int isBreak = array[2];
            int cnt = array[3];

            if(row == N - 1 && col == M - 1) {
                return cnt;
            }

            for(int i = 0; i < mRow.length; i++) {
                int nRow = row + mRow[i];
                int nCol = col + mCol[i];

                if(nRow >= 0 && nRow < N && nCol >= 0 && nCol < M) {
                    if (board[nRow][nCol] == 1 && isBreak == 0 && !visited[nRow][nCol][1]) {
                        visited[nRow][nCol][1] = true;
                        queue.add(new int[]{nRow, nCol, 1, cnt + 1});
                    }

                    if(board[nRow][nCol] == 0 && !visited[nRow][nCol][isBreak]) {
                        visited[nRow][nCol][isBreak] = true;
                        queue.add(new int[]{nRow, nCol, isBreak ,cnt + 1});
                    }
                }
            }
        }

        return -1;
    }
}

// 처음에 boolean 안하고 cnt를 담고있는 count[N][M][2] 를 사용하고 값으로 Integer.MAX_VALUE 사용하였더니 메모리 초과 발생
// 메모리 109372kb, 시간 788ms