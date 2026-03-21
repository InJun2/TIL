import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		new Main().solution();
	}

	int count = 0;

	// 2, 3, 1 -> 11
	void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());

		int size = (int) Math.pow(2, N);

		find(size, r, c);
		System.out.println(count);
	}

	void find(int size, int r, int c) {
		if(size == 1) {
			return;
		}

		int half = size /2;

		if(r < half && c < half) {
			find(half, r, c);
		}
		else if(r < half && c >= half) {
			count += half * half;
			find(half, r, c - half);
		}
		else if(r >= half && c < half) {
			count += 2 * half * half;
			find(half, r - half, c);
		}
		else if(r >= half && c >= half) {
			count += 3 * half * half;
			find(half, r - half, c - half);
		}
	}
}
