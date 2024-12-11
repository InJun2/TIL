import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());

        int[][] board = new int[N][N];
        int[][] result = new int[N][N];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for(int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < N; i++) {
            int value = 0;

            for(int j = 0; j < N; j++) {
                if(i == 0) {
                    value += board[i][j];
                    result[i][j] = value;
                    continue;
                }

                value += board[i][j];
                result[i][j] = result[i - 1][j] + value;
            }
        }

        for(int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int startRow = Integer.parseInt(st.nextToken()) - 1;
            int startCol = Integer.parseInt(st.nextToken()) - 1;
            int endRow = Integer.parseInt(st.nextToken()) - 1;
            int endCol = Integer.parseInt(st.nextToken()) - 1;

            int res = result[endRow][endCol];

            if (startRow != 0) {
                res -= result[startRow - 1][endCol];
            }

            if (startCol != 0) {
                res -= result[endRow][startCol - 1];
            }

            if (startRow != 0 && startCol != 0) {
                res += result[startRow -1][startCol - 1];
            }

            sb.append(res).append("\n");
        }

        System.out.println(sb);
    }
}