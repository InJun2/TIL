import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    char[] list1;
    char[] list2;

    int len1, len2;
    int[][] dp;

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        list1 = br.readLine().toCharArray();
        list2 = br.readLine().toCharArray();

        len1 = list1.length;
        len2 = list2.length;

        dp = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                char tmp1 = list1[i - 1];
                char tmp2 = list2[j - 1];

                if(tmp1 == tmp2){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        int start1 = len1;
        int start2 = len2;

        while (start1 > 0 && start2 > 0) {
            if(list1[start1 - 1] == list2[start2 - 1]){
                sb.append(list1[start1 - 1]);
                start1 -= 1;
                start2 -= 1;
            } else if(dp[start1][start2] == dp[start1 -1][start2]){
                start1 -= 1;
            } else {
                start2 -= 1;
            }
        }

        System.out.println(sb.reverse());
    }
}
