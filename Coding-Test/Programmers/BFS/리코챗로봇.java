import java.util.PriorityQueue;
import java.util.Comparator;

class Solution {    // 블로그 참조, 다시 풀어볼 예정
    int[] dx = {1, -1, 0, 0};
    int[] dy = {0, 0, 1, -1};
    
    public int solution(String[] board) {
        int INF = Integer.MAX_VALUE;
		int[][] dist = new int[board.length][board[0].length()];
		int[] start = new int[2];
		int[] target = new int[2];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length(); j++) {
				if (board[i].charAt(j) == 'R') {
					start = new int[] {i, j};
				}

				if (board[i].charAt(j) == 'G') {
					target = new int[] {i, j};
				}

				dist[i][j] = INF;
			}
		}

		bfs(board, dist, start);

		if (dist[target[0]][target[1]] == INF) {
			return -1;
		}

		return dist[target[0]][target[1]];
    }
    
    public void bfs(String[] board, int[][] dist, int[] start) {
		PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> dist[o[0]][o[1]]));
		queue.add(start);
		dist[start[0]][start[1]] = 0;

		while (!queue.isEmpty()) {
			int[] nowPosition = queue.poll();
			int row = nowPosition[0];
			int col = nowPosition[1];

			for (int i = 0; i < 4; i++) {
				int nextRow = row;
				int nextCol = col;
				int move = 0;

				while (true) {
					int newRow = nextRow + dx[i];
					int newCol = nextCol + dy[i];

					if (newRow < 0 || newRow >= board.length || newCol < 0 ||
                        newCol >= board[0].length() || board[newRow].charAt(newCol) == 'D') {
						break;
					}

					nextRow = newRow;
					nextCol = newCol;
					move = 1;
				}

				if (dist[row][col] + move < dist[nextRow][nextCol]) {
					dist[nextRow][nextCol] = dist[row][col] + move;
					queue.add(new int[] {nextRow, nextCol});
				}
			}
		}
	}
}