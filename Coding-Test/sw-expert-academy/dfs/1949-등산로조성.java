import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws IOException {
		new Solution().solution();
	}
	
	int N, K;
	int[][] board;
	boolean[][] checked;
	int maxNum;
	List<int[]> start;
	int maxArea;
	
	int[] dr = {1, -1, 0, 0};
	int[] dc = {0, 0, 1, -1};
	
	public void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 1; t <= T; t ++) {
			st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			board = new int[N][N];
			start = new LinkedList<>();
			maxNum = Integer.MIN_VALUE;
			maxArea = Integer.MIN_VALUE;
			
			for(int i = 0; i < N; i ++) {	// 이차원 배열에 값 넣고 그 중 최고 높이 기록
				st = new StringTokenizer(br.readLine());
				
				for(int y = 0; y < N; y++) {
					int num = Integer.parseInt(st.nextToken());
					board[i][y] = num;
					
					maxNum = Math.max(maxNum, num);
				}
			}
			
			for(int i = 0; i < N; i ++) {	// 최고 높이인 값들을 시작지점으로 사용하기 위해 리스트에 넣어두기
				for(int y = 0; y < N; y++) {
					if(board[i][y] == maxNum) {
						start.add(new int[] { i, y });
					}
				}
			}
			
			for (int i = 0; i < N; i++) {
			    for (int y = 0; y < N; y++) {
			        for (int d = 0; d < K; d++) {	// 모든 칸을 1~k 까지 빼보면서 시작지점 DFS 탐색 
			            board[i][y] --;
			            for (int[] stArr : start) {
			                checked = new boolean[N][N];
			                dfs(stArr[0], stArr[1], 1);
			            }
			        }
			        board[i][y] += K;
			    }
			}
		
			sb.append("#")
				.append(t)
				.append(" ")
				.append(maxArea)
				.append("\n");
		}
		
		System.out.println(sb);
		br.close();
	}
	
	private void dfs(int row, int col, int cnt) {
		checked[row][col] = true;
		maxArea = Math.max(maxArea, cnt);	// 최고 깊이 갱신
		
		for(int i = 0; i < dr.length; i++) {
			int mRow = row + dr[i];
			int mCol = col + dc[i];
			
			if(mRow >= N || mCol >= N || mRow < 0 || mCol < 0 || checked[mRow][mCol]) {
				continue;
			}
			
			if(board[row][col] > board[mRow][mCol]) {	// 다음 칸이 낮을 때만 재귀 탐색
				dfs(mRow, mCol, cnt + 1);
			}
		}
		
		checked[row][col] = false;	// 이후 다른 탐색에서는 영향을 끼치지 않게 하기 위한 백트래킹
	}
}
