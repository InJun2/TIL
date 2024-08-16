import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author	황인준
 * @since 	2024. 8. 16.
 * @link	https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWT-lPB6dHUDFAVT
 * @performance	마지막 인덱스까지 순회하고 해당 순회했을 때의 칼로리가 특정 칼로리를 넘지 않는 부분집합 들 중 가장 점수가 높은 경우의 점수를 출력
 * @category #완전탐색 #부분집합
 * @note
*/
public class Solution {
    private int N;
    private int L;
    private int[][] cal;
    private int maxScore;
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        new Solution().solution();		
    }
    
    public void solution() throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        
        for(int t = 0; t &lt; T; t ++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());
            cal = new int[N][2];
            maxScore = Integer.MIN_VALUE;
            
            for(int i = 0; i &lt; N; i++) {
                st = new StringTokenizer(br.readLine());
                int score = Integer.parseInt(st.nextToken());
                int calory = Integer.parseInt(st.nextToken());
                
                cal[i][0] = score;
                cal[i][1] = calory;
            }
            
            subset(0, 0, 0);
            
            sb.append("#")
                .append(t + 1)
                .append(" ")
                .append(maxScore)
                .append("\n");
        }
        System.out.println(sb);
        br.close();
    }
    
    private void subset(int cnt, int totalCalory, int totalScore) {
        // 기저 조건
        if (cnt == N) {
            // 총 칼로리가 제한 이하일 때만 점수 갱신
            if (totalCalory &lt;= L) {
                maxScore = Math.max(maxScore, totalScore);
            }
            return;
        }
        
        // 재귀 조건
        subset(cnt + 1, totalCalory + cal[cnt][1], totalScore + cal[cnt][0]);
        
        subset(cnt + 1, totalCalory, totalScore);
    }
}