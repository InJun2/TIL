import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		new Main().solution();
	}

	void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(br.readLine());

		for(int i = 0; i < N; i++) {
			int M = Integer.parseInt(br.readLine());
			List<String> strings = new ArrayList<>();
			boolean check = true;

			for(int j = 0; j < M; j++) {
				strings.add(br.readLine());
			}

			Collections.sort(strings);

			for(int j = 0; j < M - 1; j ++) {
				if(strings.get(j + 1).startsWith(strings.get(j))) {
					check = false;
					break;
				}
			}

			if(check) {
				sb.append("YES\n");
			} else {
				sb.append("NO\n");
			}
		}

		System.out.println(sb.toString());
	}
}
