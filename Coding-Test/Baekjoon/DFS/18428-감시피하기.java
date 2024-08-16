package d0816;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author	황인준
 * @since 	2024. 8. 16.
 * @link	https://www.acmicpc.net/problem/18428
 * @performance	'T'인 기준으로 상하좌우를 확인하므로 해당 값을 읽을 때 'T' 라면 해당 위치 저장,
 * 				우선 X에 O를 위치를 두는 것을 완전탐색으로 여기저기 넣어보고 아니면 백트래킹을 통해 기존 상태로 복귀,
 * 				이후 장애물 'O'를 3개 다 두었다면 'T'를 기준으로 상하좌우 확인이 필요,
 * 				이후 T에서 상하좌우로 while문을 통해 끝까지 가고 만약 'S'와 부딪힌다면 false 리턴
 * 				true 가 나올 때 까지 조회하고 이후 나오지 않았다면 false, 해당 boolean 값을 통해 출력
 * 				
 * @category #완전탐색 #백트래킹
 * @note
*/
public class Main {
	private final int MAX_OBJECT = 3;
	private String[][] board;
	private int N;
	private List<int[]> teachers = new ArrayList<>();
	private boolean result;
	
	private int[] dx = {1, -1, 0, 0};
	private int[] dy = {0, 0, 1, -1};

	public static void main(String[] args) throws NumberFormatException, IOException {
		new Main().solution();		
	}
	
	public void solution() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		board = new String[N][N];
		
		for(int i = 0; i < N; i ++) {
			st = new StringTokenizer(br.readLine());
			
			for(int y = 0 ; y < N; y++) {
				String str = st.nextToken();
				
				if(str.equals("T")) {
					teachers.add(new int[] {i, y});
				}
				board[i][y] = str;
			}
		}
		
		dfs(0);
		
		if (result) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
	}
	
	private void dfs(int cnt) {
		// 기저 조건
		// 3일때만 확인하는 것이 좋은 이유는 3이전에 확인하면 불필요한 연산이 많이 들어 갈 것 같음
		if(cnt == MAX_OBJECT) {
			if(check()) {
                result = true;
            }
			
			return;
		}
		
		// 재귀 조건
		
		for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j].equals("X")) { // 빈 공간일 경우만 장애물 설치
                    board[i][j] = "O";
                    dfs(cnt + 1);
                    board[i][j] = "X"; // 백트래킹: 장애물 제거
                    if (result) {
                    	return; // 이미 답을 찾았으면 더 이상 탐색할 필요 없음
                    }
                }
            }
        }
	}
	
	private boolean check() {
		for(int[] teacher : teachers) {
			int row = teacher[0];
			int col = teacher[1];
			
			for(int i = 0; i < dx.length; i++) {
				int mx = col;
				int my = row;

				while(true) {
					mx += dx[i];
					my += dy[i];
					
					if(mx >= N || my >= N || mx < 0 || my < 0) {
						break;
					}
					
					if(board[my][mx].equals("O")) {
						break;
					}
					
					if(board[my][mx].equals("S")) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
