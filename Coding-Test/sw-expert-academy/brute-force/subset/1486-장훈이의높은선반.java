import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author	황인준
 * @since 	2024. 8. 20.
 * @link	https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV2b7Yf6ABcBBASw
 * @performance	N이 20이므로 부분집합 완전 탐색은 가능하지 않을까 생각. 리스트를 통해 부분집합의 값을 넣고 해당 리스트의 크기만큼 직원의 높이를
 * 				더해서 값을 계산. 해당 계산한 값과 필요 높이를 빼서 가장 근처의 값 출력
 * @category #완전탐색 #부분집합
 * @note	
*/
public class SW_D4_1486_장훈이의높은선반 {
	
	public static void main(String[] agrs) throws NumberFormatException, IOException {
		new SW_D4_1486_장훈이의높은선반().solution();
	}
	
	int N;
	int B;
	int minResult;
	int[] height;
	
	private void solution() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 0; t < T; t++) {
			minResult = Integer.MAX_VALUE;
			st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			height = new int[N];
			
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < N; i++) {
				height[i] = Integer.parseInt(st.nextToken());
			}
			
			subnet(0, new ArrayList<Integer>());
			
			sb.append("#")
				.append(t + 1)
				.append(" ")
				.append(minResult)
				.append("\n");
			
		}
		
		System.out.println(sb);
	}
	
	private void subnet(int cnt, ArrayList<Integer> list) {
		if(cnt == N) {
			int sum = 0;
			for(int i = 0; i < list.size(); i++) {
				sum += list.get(i);
			}
			
			if(sum >= B) {
				minResult = Math.min(minResult, sum - B);				
			}
			
			return;
		}
		
		list.add(height[cnt]);
		subnet(cnt + 1, list);
		
		list.remove(list.size() - 1);
		subnet(cnt + 1, list);
	}
}
