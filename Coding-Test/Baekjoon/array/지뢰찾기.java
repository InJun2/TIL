import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[][] board;
    static int[] nx = {1, 1, 0, -1, -1, -1, 0, 1};
    static int[] ny = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());
        board = new int[N][N];

        for (int y = 0; y < N; y++) {
            String str = br.readLine();
            for (int x = 0; x < N; x++) {
                char ch = str.charAt(x);
                if (ch != '.') {
                    board[y][x] = -1; // '*'을 -1로 표시
                    int count = Character.getNumericValue(ch);
                    aroundCount(x, y, count);
                }
            }
        }

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                if (board[y][x] == -1) {
                    sb.append('*');
                } else if (board[y][x] >= 10) {
                    sb.append('M');
                } else {
                    sb.append(board[y][x]);
                }
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    private static void aroundCount(int x, int y, int count) {
        for (int i = 0; i < nx.length; i++) {
            int dx = x + nx[i];
            int dy = y + ny[i];

            if (dx >= 0 && dx < N && dy >= 0 && dy < N && board[dy][dx] != -1) {
                board[dy][dx] += count;
            }
        }
    }
}