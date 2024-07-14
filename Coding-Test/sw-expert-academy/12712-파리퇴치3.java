import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    static int[][] box;
    static int n;

    private static int[] dx = {1, -1, 0, 0};
    private static int[] dy = {0, 0, 1, -1};
    private static int[] dx2 = {-1, 1, -1, 1};
    private static int[] dy2 = {1, 1, -1, -1};

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int result;

        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            box = new int[n][n];

            for (int i = 0; i < n; i++) {
                StringTokenizer st1 = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    box[i][j] = Integer.parseInt(st1.nextToken());
                }
            }

            result = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    result = Math.max(result, getMaxFlies(i, j, m));
                }
            }

            System.out.printf("#%d %d\n", test_case, result);
        }
    }

    private static int getMaxFlies(int x, int y, int m) {
        int plusSum = box[x][y];
        int xSum = box[x][y];

        for (int i = 1; i < m; i++) {
            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d] * i;
                int ny = y + dy[d] * i;

                if (isValid(nx, ny)) {
                    plusSum += box[nx][ny];
                }
            }
        }

        for (int i = 1; i < m; i++) {
            for (int d = 0; d < 4; d++) {
                int nx = x + dx2[d] * i;
                int ny = y + dy2[d] * i;

                if (isValid(nx, ny)) {
                    xSum += box[nx][ny];
                }
            }
        }

        return Math.max(plusSum, xSum);
    }

    private static boolean isValid(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }
}