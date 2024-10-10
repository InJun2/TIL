import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws NumberFormatException, IOException {
		new Solution().solution();
	}

	int N, L;
	int board[][];

	class Runway {
		int[] heights;
		int curr;

		public Runway(int[] heights) {
			this.heights = heights;
		}

		public boolean check() {
			curr = heights[0];
			int length = 0;

			for (int i = 0; i < heights.length; i++) {
				if (heights[i] == curr) {
					length++;
					continue;
				}

				if (heights[i] - curr == 1) { // 이거 올라갈때
					if(length >= L) {
						curr = heights[i];
						length = 1;
						continue;
					}
					
					return false;
				}

				if (curr - heights[i] == 1) { // 내려갈 때
					if(i + L > heights.length) {
						return false;
					}
					
					for (int j = 0; j < L; j++) {
				        if (heights[i + j] != curr - 1) {
				            return false;  // 경사로의 높이가 적합하지 않음
				        }
				    }
					
				    i += L - 1;  // 경사로 설치한 만큼 index를 증가시킴
				    curr = heights[i];
				    length = 0;
				    continue;
				}
				
				return false;
			}

			return true;
		}
	}

	public void solution() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			int result = 0;
			st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());

			board = new int[N][N];

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());

				for (int y = 0; y < N; y++) {
					board[i][y] = Integer.parseInt(st.nextToken());
				}
			}

			for (int i = 0; i < N; i++) {
				Runway runway = new Runway(board[i]);
				if (runway.check()) {
					result++;
				}
				
				int[] arr = new int[N];

				for (int y = 0; y < N; y++) {
					arr[y] = board[y][i];
				}

				Runway runway2 = new Runway(arr);
				if (runway2.check()) {
					result++;
				}
			}
			
			sb.append("#").append(t).append(" ").append(result).append("\n");
		}

		System.out.print(sb);
	}
}
