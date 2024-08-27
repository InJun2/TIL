import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author	황인준
 * @since 	2024. 8. 27.
 * @link	https://www.acmicpc.net/problem/2636
 * @performance	우선 맨 처음 치즈의 값을 1로 두고 이후 가장 가장자리부터 0이면 방문 true로 변경하며 반복
 * 				이후 가장자리에서 1을 만난다면 해당 부분을 0으로 바꾸고 방문 true로 변경. 이후 해당 반복  
 * @category #그래프탐색 #BFS
 * @note	
*/
public class Main {
	public static void main(String[] args) throws IOException {
		new Main().solution();
	}
	
	int[] rowDir = {1, -1, 0, 0};
	int[] colDir = {0, 0, 1, -1};
	
	int maxRow, maxCol;
	int[][] board;
	
	private void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		maxRow = Integer.parseInt(st.nextToken());
		maxCol = Integer.parseInt(st.nextToken());
		board = new int[maxRow][maxCol];
		int cnt = 0;
		int realResult = 0;
		
		for(int i = 0; i < maxRow; i ++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j = 0; j < maxCol; j ++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 1) {
					realResult ++;
				}
			}
		}
		
		
		while(true) {
			cnt ++;
			int result = bfs();
						
			if(result == 0) {
				cnt --;
				break;
			}
			
			realResult = result;
		}
		
		System.out.println(cnt);
		System.out.println(realResult);
	}
	
	private int bfs() {
		Queue<int[]> queue = new ArrayDeque<>();
		boolean[][] visited = new boolean[maxRow][maxCol];
		Set<int[]> list = new HashSet<>();
		
		visited[0][0] = true;
		queue.offer(new int[] {0, 0});
		
		while(!queue.isEmpty()) {
			int[] location = queue.poll();
			
			for(int i = 0; i < rowDir.length; i++) {
				int mRow = location[0] + rowDir[i];
				int mCol = location[1] + colDir[i];
				
				if(!isValid(mRow, mCol)) {
					continue;
				}
				
				if(visited[mRow][mCol]) {
					continue;
				}
				
				if(board[mRow][mCol] == 0) {
					visited[mRow][mCol] = true;
					queue.offer(new int[] {mRow, mCol});
					continue;
				}
				
				if(!visited[mRow][mCol] && board[mRow][mCol] == 1) {
					visited[mRow][mCol] = true;
					list.add(new int[] {mRow, mCol});
				}
			}
		}
		
		for(int[] location : list) {
			board[location[0]][location[1]] = 0;
		}
		
		return list.size();
	}
	
	private boolean isValid(int row, int col) {
		return row < maxRow && col < maxCol && row >= 0 && col >= 0;
	}
}
