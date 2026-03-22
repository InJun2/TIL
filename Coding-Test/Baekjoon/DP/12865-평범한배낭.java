import java.io.*;
import java.util.*;

public class Main {
    static int N, K;
    static int[] W, V;
    static Integer[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        W = new int[N];
        V = new int[N];
        dp = new Integer[N][K + 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            W[i] = Integer.parseInt(st.nextToken());
            V[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(dfs(0, K));
    }

    static int dfs(int index, int weightLeft) {
        if (index == N) {
            return 0;
        }

        if (dp[index][weightLeft] != null) {
            return dp[index][weightLeft];
        }

        int skip = dfs(index + 1, weightLeft);
        
        int take = 0;
        if (W[index] <= weightLeft) {
            take = V[index] + dfs(index + 1, weightLeft - W[index]);
        }

        dp[index][weightLeft] = Math.max(take, skip);
        return dp[index][weightLeft];
    }
}