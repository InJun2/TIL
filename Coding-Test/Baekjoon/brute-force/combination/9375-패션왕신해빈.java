import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        Map<String, Integer> map;
        
        int T = Integer.parseInt(br.readLine());
        
        String key;
        
        for(int i = 0; i < T; i ++) {
        	int result = 1;
        	map = new HashMap<>();
        	int K = Integer.parseInt(br.readLine());
        	
        	for(int j = 0; j < K; j ++) {
        		st = new StringTokenizer(br.readLine());
        		st.nextToken();
        		key = st.nextToken(); 
        		
        		map.put(key, map.getOrDefault(key, 0) + 1);
        	}
        	
        	for(int count : map.values()) {
        		result *= (count + 1);
        	}

            result -= 1;
        	
        	sb.append(result).append("\n");
        }
        
        System.out.println(sb);
    }
}