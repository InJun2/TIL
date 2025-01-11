import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N;
    int[] array;
    long[][] dp;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        array = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }

        dp = new long[N - 1][21];
        dp[0][array[0]] = 1;

        for (int i = 1; i < N - 1; i++) {
            for (int j = 0; j <= 20; j++) {
                if (dp[i - 1][j] > 0) {
                    if (j + array[i] <= 20) {
                        dp[i][j + array[i]] += dp[i - 1][j];
                    }
                    if (j - array[i] >= 0) {
                        dp[i][j - array[i]] += dp[i - 1][j];
                    }
                }
            }
        }

        System.out.println(dp[N - 2][array[N - 1]]);
    }
}
