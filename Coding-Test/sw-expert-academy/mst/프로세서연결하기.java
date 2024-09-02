// 기존 코드
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.StringReader;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.StringTokenizer;

// public class SW_프로세서연결하기 {

// 	String input = 
// 			"7\r\n" + 
// 			"0 0 1 0 0 0 0\r\n" + 
// 			"0 0 1 0 0 0 0\r\n" + 
// 			"0 0 0 0 0 1 0\r\n" + 
// 			"0 0 0 0 0 0 0\r\n" + 
// 			"1 1 0 1 0 0 0\r\n" + 
// 			"0 1 0 0 0 0 0\r\n" + 
// 			"0 0 0 0 0 0 0";
	
// 	public static void main(String[] args) throws NumberFormatException, IOException {
// 		// TODO Auto-generated method stub
// 		new SW_프로세서연결하기().solution();
// 	}
	
// 	class Processor {
// 		int row, col;
// 		int distance;
// 		boolean check;
		
// 		public Processor(int row, int col) {
// 			this.row = row;
// 			this.col = col;
// 			this.distance = 0;
// 			this.check = false;
// 		}
// 	}
	
// 	int N;
// 	int[][] board;
// 	List<Processor> list;
// 	int maxProcessor;
// 	int minResult;
	
// 	int[] dx = {1, -1, 0, 0};	// 우, 왼, 하, 상
// 	int[] dy = {0, 0, 1, -1};
	
// 	public void solution() throws NumberFormatException, IOException {
// 		BufferedReader br = new BufferedReader(new StringReader(input));
// 		StringBuilder sb;
// 		StringTokenizer st;
		
// 		N = Integer.parseInt(br.readLine());
// 		board = new int[N][N];
// 		list = new ArrayList<>();
// 		minResult = Integer.MAX_VALUE;
		
// 		for(int i = 0; i < N; i ++) {
// 			st = new StringTokenizer(br.readLine());
			
// 			for(int j = 0; j < N; j ++) {
// 				int num = Integer.parseInt(st.nextToken());
// 				board[i][j] = num;
				
// 				if(num == 1 && i != 0 && i != N - 1 && j != 0 && j != N -1) {
// 					list.add(new Processor(i, j));
// 				}
// 			}
// 		}
		
// 		move(0);
		
// 		System.out.println(minResult);
// 	}
	
// 	private void move(int cnt) {
// 		if(cnt == list.size()) {
// 			int checkProcessor = 0;
// 			int result = 0;
// 			for(Processor pro : list) {
// 				if(pro.check) {
// 					checkProcessor ++;
// 				}
				
// 				result += pro.distance;
// 			}
			
			
// 			if(maxProcessor <= checkProcessor) {
// 				minResult = Math.min(minResult, result);
// 				maxProcessor = Math.max(maxProcessor, checkProcessor);
// 			}
// 			return;
// 		}
		
		
// 		// 이미 이동한 거리에 -1로 변경하고 이후 재귀호출 이후 백트래킹
		
// 		Processor processor = list.get(cnt);
		
// 		for(int dir = 0; dir < 4; dir ++) {
// 			int dist = 0;
			
// 			int[][] copy = new int[N][N];
// 			for (int i = 0; i < board.length; i++) {
// 			    copy[i] = board[i].clone();
// 			}
			
// 			while(true) {
// 				if(processor.row == 0 || processor.col == 0 || processor.row == N - 1 || processor.col == N - 1) {  // 성공했을 때
// 					processor.distance = Math.max(processor.distance, dist);
// 					processor.check = true; // 성공했으니 true
// 					break;
// 				}
				
// 				int moveRow = processor.row + dx[dir];
// 				int moveCol = processor.col + dy[dir];
				
// 				if(board[moveRow][moveCol] == -1) { // 앞에 -1이 존재한다면 더 가야하는데 실패했고 백트래킹을 진행해야함
// 					for (int i = 0; i < board.length; i++) {
// 					    board[i] = copy[i];
// 					}
// 					break;
// 				}
				
// 				processor.row = moveRow;
// 				processor.col = moveCol;
// 				board[moveRow][moveCol] = -1;
// 				dist ++;
// 			}
			
// 			move(cnt + 1);

// 			for (int i = 0; i < board.length; i++) {
// 			    board[i] = copy[i];
// 			}
// 		}
// 	}
// }


// GPT 참조 코드


package d0830;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SW_프로세서연결하기 {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		new SW_프로세서연결하기().solution();
	}
	
	class Processor {
		int row, col;
		int distance;
		boolean check;
		
		public Processor(int row, int col) {
			this.row = row;
			this.col = col;
			this.distance = 0;
			this.check = false;
		}
	}
	
	int N;
	int[][] board;
	List<Processor> list;
	int maxProcessor;
	int minResult;
	
	int[] dx = {1, -1, 0, 0};	// 우, 왼, 하, 상
	int[] dy = {0, 0, 1, -1};
	
	public void solution() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			board = new int[N][N];
			list = new ArrayList<>();
			maxProcessor = 0;
			minResult = Integer.MAX_VALUE;
			
			for(int i = 0; i < N; i ++) {
				st = new StringTokenizer(br.readLine());
				
				for(int j = 0; j < N; j ++) {
					int num = Integer.parseInt(st.nextToken());
					board[i][j] = num;
					
					if(num == 1 && i != 0 && i != N - 1 && j != 0 && j != N -1) {
						list.add(new Processor(i, j));
					}
				}
			}
			
			move(0);
			
			sb.append("#")
				.append(t)
				.append(" ")
				.append(minResult)
				.append("\n");
		}
		
		System.out.println(sb);
	}
	
	private void move(int cnt) {
		if(cnt == list.size()) {
			int checkProcessor = 0;
			int result = 0;
			for(Processor pro : list) {
				if(pro.check) {
					checkProcessor ++;
				}
				
				result += pro.distance;
			}
			
			
			if (maxProcessor < checkProcessor) {
	            maxProcessor = checkProcessor;
	            minResult = result;
	        } else if (maxProcessor == checkProcessor) {
	            minResult = Math.min(minResult, result);
	        }
			return;
		}
		
		
		// 이미 이동한 거리에 -1로 변경하고 이후 재귀호출 이후 백트래킹
		
		Processor processor = list.get(cnt);
		
		for (int dir = 0; dir < 4; dir++) {
	        int moveRow = processor.row;
	        int moveCol = processor.col;
	        int dist = 0;
	        boolean canPlace = true;

	        // 전선 설치를 시도하기 전 복사본 생성
	        int[][] copy = new int[N][N];
	        for (int i = 0; i < N; i++) {
	            copy[i] = board[i].clone();
	        }

	        // 탐색 및 설치 시도
	        while (true) {
	            moveRow += dx[dir];
	            moveCol += dy[dir];

	            // 경계에 도달한 경우
	            if (moveRow < 0 || moveCol < 0 || moveRow >= N || moveCol >= N) {
	                break;
	            }

	            // 이미 다른 전선이 설치된 경우
	            if (board[moveRow][moveCol] != 0) {
	                canPlace = false;
	                break;
	            }

	            dist++;
	            board[moveRow][moveCol] = -1;  // 전선 설치
	        }

	        if (canPlace && (moveRow < 0 || moveCol < 0 || moveRow >= N || moveCol >= N)) {
	            // 연결 성공한 경우
	            processor.check = true;
	            processor.distance = dist;

	            // 다음 Processor를 처리
	            move(cnt + 1);

	            // 백트래킹 - 전선 설치 복구
	            processor.check = false;
	            processor.distance = 0;

	            for (int i = 0; i < N; i++) {
	                board[i] = copy[i].clone();
	            }
	        } else {
	            // 전선 설치 실패, 복구
	            for (int i = 0; i < N; i++) {
	                board[i] = copy[i].clone();
	            }
	        }
	    }

	    // 현재 Processor를 연결하지 않고 다음으로 진행
	    move(cnt + 1);
	}
}
