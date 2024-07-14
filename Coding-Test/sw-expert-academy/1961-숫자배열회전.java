import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    static int n;

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb;
        int[][] box;

        int T;
        T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            n = Integer.parseInt(br.readLine());
            box = new int[n][n];

            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    box[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int[][] arr90 = rotation(box);
            int[][] arr180 = rotation(arr90);
            int[][] arr270 = rotation(arr180);

            System.out.println("#" + test_case);
            for (int i = 0; i < n; i++) {
                sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(arr90[i][j]);
                }

                sb.append(" ");

                for (int j = 0; j < n; j++) {
                    sb.append(arr180[i][j]);
                }

                sb.append(" ");

                for (int j = 0; j < n; j++) {
                    sb.append(arr270[i][j]);
                }

                System.out.println(sb);
            }
        }
    }

    public static int[][] rotation(int[][] arr) {
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[j][n - 1- i] = arr[i][j];
            }
        }

        return result;
    }
}