import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws IOException {
		new Solution().solution();
	}

	int M;
	int result;
	char[] chars;
	Set<String> visitedSet;
	
	public void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 1; t <= T; t ++) {
			st = new StringTokenizer(br.readLine());
			
			String str = st.nextToken();
			M = Integer.parseInt(st.nextToken());
			chars = str.toCharArray();
			result = Integer.MIN_VALUE;
			visitedSet = new HashSet<>();
			
			combination(0);
		
			sb.append("#")
				.append(t)
				.append(" ")
				.append(result)
				.append("\n");
		}
		
		System.out.println(sb);
		br.close();
	}
	
	private void combination(int cnt) {
		String currentState = new String(chars);
        if(visitedSet.contains(currentState)) {
            return;
        }
        
        visitedSet.add(currentState);
		
		if(cnt == M) {
			result = Math.max(result, Integer.parseInt(new String(chars)));
			return;
		}
		
		for(int i = 0; i < chars.length; i++) {
			for(int y = i + 1; y < chars.length; y++) {
				swap(i, y);
				combination(cnt + 1);
				swap(i, y);					
			}
		}
		
		if ((M - cnt) % 2 == 1) {   // 남는 교환횟수가 홀수일 때를 처리
            swap(chars.length - 1, chars.length - 2);
            result = Math.max(result, Integer.parseInt(new String(chars)));
            swap(chars.length - 1, chars.length - 2); // 원상태 복구
        }
	}
	
	private void swap(int i , int y) {
		char temp = chars[i];
		chars[i] = chars[y];
		chars[y] = temp;
	}
}
