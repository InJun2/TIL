import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * @author	황인준
 * @since 	2024. 8. 19.
 * @link	https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWIeUtVakTMDFAVH
 * @performance	해당 문제를 이해를 잘못해서 차이가 최솟값이 나오는 2가지 요리만을 선정하는 문제로 착각하여 풀이 진행 후 다시 진행.
 * 				그러고도 이해를 하지 못해서 풀이를 참조하였는데 해당 문제는 요리를 절반씩 나누고 해당 절반씩 조합이 이루어 졌을 때 해당 시너지 값을 각자의 조합에서 더하고
 * 				해당 조합의 시너지 값을 빼는 문제였음. 
 * @category #완전탐색 #조합 #백트래킹
 * @note
*/
public class SW_4012_요리사 {
	
	public static void main(String[] agrs) throws NumberFormatException, IOException {
		new SW_4012_요리사().solution();
	}
	
	int N;
	int[][] board;
	int minValue;
	ArrayList<Integer> values;
	
	private void solution() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 0; t < T; t++) {
			N = Integer.parseInt(br.readLine());
			board = new int[N][N];
			values = new ArrayList<>();
			minValue = Integer.MAX_VALUE;
			
			for(int i = 0; i < N; i ++) {
				st = new StringTokenizer(br.readLine());
				
				for(int y = 0; y < N; y ++) {
					board[i][y] = Integer.parseInt(st.nextToken());
				}
			}
			
			combination(0, new boolean[N]);
			
			sb.append("#")
				.append(t + 1)
				.append(" ")
				.append(minValue)
				.append("\n");
			
		}
		
		System.out.println(sb);
	}
	
	private void combination(int cnt, boolean[] checked) {
		if(cnt == N) {
			int trueCnt = 0;
			
			for(int i = 0; i < checked.length; i++) {
				if(checked[i]) {
					trueCnt ++;
				}
			}
			
			if(trueCnt == N /2) {
				carculateValue(checked);
			}
			
			return;
		}
		
		checked[cnt] = true;
		combination(cnt + 1, checked);
		
		checked[cnt] = false; 
		combination(cnt + 1, checked);
	}
	
	private void carculateValue(boolean[] checked) {
		List<Integer> aList = new ArrayList<>();
		List<Integer> bList = new ArrayList<>();
		
		for(int i = 0; i < N; i++) {
			if(checked[i]) {
				aList.add(i);
			} else {
				bList.add(i);
			}
		}
		
		 int sumA = 0, sumB = 0;

	        // A 그룹 시너지 합 계산
	        for (int i = 0; i < aList.size(); i++) {
	            for (int j = i + 1; j < aList.size(); j++) {
	                sumA += board[aList.get(i)][aList.get(j)] + board[aList.get(j)][aList.get(i)];
	            }
	        }

	        // B 그룹 시너지 합 계산
	        for (int i = 0; i < bList.size(); i++) {
	            for (int j = i + 1; j < bList.size(); j++) {
	                sumB += board[bList.get(i)][bList.get(j)] + board[bList.get(j)][bList.get(i)];
	            }
	        }

	        // 두 그룹 간의 차이 계산 및 최솟값 갱신
	        int difference = Math.abs(sumA - sumB);
	        minValue = Math.min(minValue, difference);
	}
}
