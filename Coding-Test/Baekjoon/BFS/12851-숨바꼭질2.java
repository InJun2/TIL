import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		new Main().solution();
	}

	static int MAX = 100000;

	void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] time = new int[MAX + 1];
		Arrays.fill(time, Integer.MAX_VALUE);
		int count = 0;

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[] {N, 0});

		while(!queue.isEmpty()) {
			int[] now = queue.poll();

			int po = now[0];
			int cnt = now[1];

			if(cnt > time[M]) {
				continue;
			}

			if(po == M) {
				if(cnt < time[M]) {
					time[M] = cnt;
					count = 1;
				} else if(cnt == time[M]) {
					count += 1;
				}
			}

			if(po + 1 <= MAX && cnt + 1 <= time[po + 1]) {
				time[po + 1] = cnt + 1;
				queue.offer(new int[] {po + 1, cnt + 1});
			}

			if(po - 1 >= 0 && cnt + 1 <= time[po - 1]) {
				time[po - 1] = cnt + 1;
				queue.offer(new int[] {po - 1, cnt + 1});
			}

			if(po != 0 && po * 2 <= MAX && cnt + 1 <= time[po * 2]) {
				time[po * 2] = cnt + 1;
				queue.offer(new int[] {po * 2, cnt + 1});
			}
		}

		System.out.println(time[M]);
		System.out.println(count);
	}
}
