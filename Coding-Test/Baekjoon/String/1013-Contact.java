import java.io.*;
import java.util.regex.*;

public class Main {
	public static void main(String[] args) throws IOException {
		new Main().solution();
	}

	private void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int n = Integer.parseInt(br.readLine());
		Pattern pattern = Pattern.compile("^(100+1+|01)+$");

		for (int i = 0; i < n; i++) {
			String s = br.readLine();
			Matcher matcher = pattern.matcher(s);

			if (matcher.matches()) {
				sb.append("YES").append("\n");
			} else {
				sb.append("NO").append("\n");
			}
		}
		System.out.print(sb);
	}
}


