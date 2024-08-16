import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    
    static int N, M;
    static int[] save;
    static StringBuilder sb = new StringBuilder();
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        save = new int[M];
        
        permutate(0, 0);
        
        System.out.println(sb);
    }
    
    private static void permutate(int start, int cnt) {
        if(cnt == M) {
            for(int num : save) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
            return;
        }
        
        for(int i = start; i &lt; N; i++) {
            save[cnt] = i + 1;
            permutate(i + 1, cnt + 1);
        }
    }
}