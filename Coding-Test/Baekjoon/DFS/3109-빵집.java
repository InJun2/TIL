import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author	황인준
 * @since 	2024. 8. 22.
 * @link	https://www.acmicpc.net/problem/3109
 * @performance	배열의 column이 왼쪽부터 오른쪽까지 한칸씩 이동하되 높이는 대각선 위, 아래, 높이는 그대로 등으로 이동하여 
 * 				도달하는 경우를 최대한 높이는 문제로 기존 건물이 있는 'x'는 파이프를 설치하지 못하므로 boolean[][] 에
 * 				true를 통해 장애물 위치를 지정하고 이후 해당 파이프를 설치할 때도 해당 boolean 배열에 true를 지정.
 * 				해당 column이 가장 오른쪽 배열위치에 도달했다면 true 반환하여 메서드 스택 반환 시작 및 true인 경우마다 result ++ 후 답 반환
 * 				추가적으로 //check[moveRow][moveCol] = false; 해당 부분을 계속 넣어야 한다고 생각했는데 해당 부분이 있으면
 * 				만약 아래 행에서 해당 경로를 지났던 경로와 똑같이 들어오게 된다면 이미 실패할 경로를 다시 탐색하므로 시간 초과 발생..
 * @category #완전탐색 #DFS
 * @note	
*/
public class BJ_G2_3109_빵집 {

	public static void main(String[] args) throws IOException {
		new BJ_G2_3109_빵집().solution();
	}
	
	int N, M;
	boolean[][] check;
	int result = 0;
	
	int[] dRow = {-1, 0, 1};
	
	
	private void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		check = new boolean[N][M];
		
		for(int i = 0; i < N; i++) {
			String line = br.readLine();
			
			for(int j = 0; j < M; j++) {
				char ch = line.charAt(j);
				if(ch == 'x') {
					check[i][j] = true;
				}
			}
		}

		for(int i = 0; i < N; i ++) {
			if(move(i, 0)) {
				result ++;
			}
		}
		
		System.out.println(result);
	}

	private boolean move(int row, int col) {
	    if (col == M - 1) {
	        return true;
	    }

	    for(int i = 0; i < dRow.length; i++) {
	    	int moveRow = row + dRow[i];
	    	int moveCol = col + 1 ;
	    	
	    	if (moveRow >= 0 && moveRow < N && !check[moveRow][moveCol]) {
                check[moveRow][moveCol] = true;
                
                if (move(moveRow, moveCol)) {
                    return true;
                }
                
//                check[moveRow][moveCol] = false;	// 이 녀석을 남겨두면 나중에 아래 행에서 해당 위치에 도달하면 똑같은 실패를 반복하므로 true로 막아두어야 함
            }
    	}
	    
	    return false;
	}
}
