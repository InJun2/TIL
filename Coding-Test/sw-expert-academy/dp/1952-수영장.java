import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    int[] price;
    int[] month;
    int[] dp;
    
    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(br.readLine());
        
        for (int t = 1; t <= T; t++) {
            price = new int[4];
            month = new int[13];
            dp = new int[13];
            
            st = new StringTokenizer(br.readLine());            
            for(int i = 0; i < price.length; i ++) {
            	price[i] = Integer.parseInt(st.nextToken());
            }
             
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i < month.length; i++) {
            	month[i] = Integer.parseInt(st.nextToken());
            }
            
            for (int i = 1; i < dp.length; i++) {
				dp[i] = dp[i - 1] + Math.min(price[0] * month[i], price[1]);
                
                if (i >= 3) {
                    dp[i] = Math.min(dp[i], dp[i - 3] + price[2]);
                }
            }
            
            int minPrice = Math.min(dp[12], price[3]);

            sb.append("#")
            	.append(t)
            	.append(" ")
            	.append(minPrice)
            	.append("\n");
        }
        
        System.out.println(sb);
    }
}
