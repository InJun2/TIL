import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws IOException {
		new Solution().solution();
	}
	
	class Security {
		int K;
		int price;
		
		public Security(int K) {
			this.K = K - 1;
			this.price = K * K + ((K - 1) * (K - 1));
		}
		
		public void check() {
			for(int i = 0; i < N; i++) {
				for(int y = 0; y < N; y++) {
					int homeCnt = getSecurityHome(i, y);
					if(price - (homeCnt * M) <= 0) {
						maxHome = Math.max(maxHome, homeCnt);
					}
				}
			}
		}
		
		private int getSecurityHome(int row, int col) {
			int homeCnt = 0;
			for(int[] location : list) {
				if(K >= Math.abs(location[0] - row) + Math.abs(location[1] - col)) {
					homeCnt++;
				}
			}
			
			return homeCnt;
		}
	}

	int N,M;
	LinkedList<int[]> list;
	int maxHome;
	
	public void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 1; t <= T; t ++) {
			st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			list = new LinkedList<>();
			maxHome = Integer.MIN_VALUE;
			
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				
				for(int y = 0; y < N; y++) {
					int area = Integer.parseInt(st.nextToken());
					if(area == 1) {
						list.add(new int[] {i, y});
					}
				}
			}
			
			for(int k = 1; k <= N + 1; k++) {
				new Security(k).check();
			}
		
			sb.append("#")
				.append(t)
				.append(" ")
				.append(maxHome)
				.append("\n");
		}
		
		System.out.println(sb);
		br.close();
	}
}
