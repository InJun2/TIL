import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author	황인준
 * @since 	2024. 8. 27.
 * @link	https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5LwsHaD1MDFAXc
 * @performance	기존 접근을 지뢰들의 위치를 찾고 해당 둘레들을 Set에 넣고 해당 주위를 확인했는데 그렇게 진행할 필요가 없었음.. 풀다가 여러 자료구조를
 * 				사용하다가 구현이 어려워져 금강이의 코드를 참고하였음. 해당 코드를 바탕으로 우선 N이 최대 300이므로 이중 포문을 사용하며 한번 모두 순회하고
 * 				각각 주변에 지뢰가 없는지 확인. 없다면 해당 부분을 bfs 시작 지점으로 지정. bfs는 주변을 모두 전파하여 visited를 방문. 해당 반복을 통해
 * 				여러개를 전파할 수 있는 블록들을 전파하고 이후 남아있는 지뢰 주변 블록들을 하나하나 result++ 진행 후 반환
 * @category #그래프탐색 #BFS
 * @note	
*/
public class Solution {

    final int[] moveRow = {-1, -1, -1, 0, 0, 1, 1, 1};
    final int[] moveCol = {-1, 0, 1, -1, 1, -1, 0, 1};
    int N;
    char[][] board;
    boolean[][] visited;
    int result;
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        new Solution().solution();
    }
    
    private void solution() throws NumberFormatException, IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            board = new char[N][N];
            visited = new boolean[N][N];
            result = 0;

            // 보드 입력 받기
            for(int i = 0; i < N; i ++) {
            	board[i] = br.readLine().toCharArray();
            }
            
            // 0인 칸을 찾아 클릭
            for(int i = 0; i < N; i ++) {
            	for(int j = 0; j < N; j++) {
            		if(aroundCheck(i, j) && board[i][j] != '*' && !visited[i][j]) {	// 해당 조건을 잘못 넣어 문제가 발생했었음
            			bfs(i, j);
            			result ++;
            		}
            	}
            }
            
            // 남은 칸 클릭
            for(int i = 0; i < N; i ++) {
            	for(int y = 0; y < N; y ++) {
            		if(!visited[i][y] && board[i][y] != '*') {
            			visited[i][y] = true;
            			result ++;
            		}
            	}
            }
            
            sb.append("#").append(t).append(" ").append(result).append("\n");
        }

        System.out.println(sb);
    }

    private boolean isValid(int row, int col) {
    	if(row >= N || col >= N || row < 0 || col < 0) {
    		return false;
    	}
    	
    	return true;
    }
    
    private boolean aroundCheck(int row, int col) {
    	for(int i = 0; i < moveRow.length; i++) {
    		int mRow = row + moveRow[i];
    		int mCol = col + moveCol[i];
    		
    		if(!isValid(mRow, mCol)) {
    			continue;
    		}
    		
    		if(board[mRow][mCol] == '*') {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    private void bfs(int row, int col) {
    	Queue<int[]> queue = new ArrayDeque<>();
    	queue.offer(new int[] {row, col});
    	visited[row][col] = true;
    	
    	while(!queue.isEmpty()) {
    		int[] location = queue.poll();
    		
    		for(int i = 0; i < moveRow.length; i++) {
        		int mRow = location[0] + moveRow[i];
        		int mCol = location[1] + moveCol[i];
        		
        		if(!isValid(mRow, mCol) || visited[mRow][mCol]) {
        			continue;
        		}
        		
        		visited[mRow][mCol] = true;
        		if(aroundCheck(mRow, mCol)) {
        			queue.offer(new int[] {mRow, mCol});
        		}
        	}
    	}
    }
    
    
}