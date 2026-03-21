import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		new Main().solution();
	}

	int N;
	int[][] map;

	int[] dx = {1, -1, 0, 0};
	int[] dy = {0, 0, 1, -1};
	int count = 2;
	int moveMin = Integer.MAX_VALUE;

	void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];

		for(int i = 0; i < N; i ++) {
			String[] str = br.readLine().split(" ");
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(str[j]);
			}
		}

		for(int i = 0; i < N; i++) {
			for(int j =0; j < N; j++) {
				checkMap(i, j);
			}
		}

		for(int i = 0; i < N; i++) {
			for(int j =0; j < N; j++) {
				buildMap(i, j);
			}
		}

		System.out.println(moveMin);
	}

	void checkMap(int y, int x) {
		if(map[y][x] != 1) {
			return;
		}

		Queue<int[]> que = new LinkedList<>();
		que.offer(new int[] {y, x});
		map[y][x] = count;

		while(!que.isEmpty()) {
			int[] po = que.poll();

			for(int i = 0; i < dx.length; i++) {
				int mx = po[1] + dx[i];
				int my = po[0] + dy[i];

				if(mx < 0 || my < 0 || mx > N - 1 || my > N - 1) {
					continue;
				}

				if(map[my][mx] == 0) {
					continue;
				}

				if(map[my][mx] == 1) {
					map[my][mx] = count;
					que.offer(new int[] {my, mx});
				}
			}
		}

		count++;
	}

	void buildMap(int y, int x) {
		if(map[y][x] == 0) {
			return;
		}

		int num = map[y][x];
		Queue<int[]> que = new LinkedList<>();
		que.offer(new int[] {y, x, 0});

		boolean[][] visited = new boolean[N][N];
		visited[y][x] = true;

		while(!que.isEmpty()) {
			int[] po = que.poll();
			int cy = po[0];
			int cx = po[1];
			int dist = po[2];

			if (dist >= moveMin) {
				continue;
			}

			for(int i = 0; i < dx.length; i++) {
				int mx = cx + dx[i];
				int my = cy + dy[i];

				if(mx < 0 || my < 0 || mx > N - 1 || my > N - 1) {
					continue;
				}

				if(!visited[my][mx]) {
					visited[my][mx] = true;

					if(map[my][mx] == 0) {
						que.offer(new int[] {my, mx, dist + 1});
					}
					else if(map[my][mx] != num) {
						moveMin = Math.min(moveMin, dist);
						return;
					}
				}
			}
		}
	}
}
