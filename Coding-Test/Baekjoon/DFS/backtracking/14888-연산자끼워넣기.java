import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		new Main().solution();
	}

	int N;
	int min = Integer.MAX_VALUE;
	int max = Integer.MIN_VALUE;
	int num[], oper[];

	void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());
		num = new int[N];
		oper = new int[4];
		StringTokenizer st = new StringTokenizer(br.readLine());

		for(int i = 0; i < N; i++) {
			num[i] = Integer.parseInt(st.nextToken());
 		}

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < 4; i++) {
			oper[i] = Integer.parseInt(st.nextToken());
		}

		dfs(1, num[0]);

		System.out.println(max);
		System.out.println(min);
	}

	void dfs(int index, int curr) {
		if(index == N) {
			max = Math.max(curr, max);
			min = Math.min(curr, min);
			return;
		}

		for (int i = 0; i < 4; i++) {
			if (oper[i] > 0) {

				oper[i]--;

				int result = curr;
				if(i == 0) {
					result += num[index];
				} else if( i == 1) {
					result -= num[index];
				} else if(i == 2) {
					result *= num[index];
				} else {
					result /= num[index];
				}
				dfs(index + 1, result);

				oper[i]++;
			}
		}
	}
}
