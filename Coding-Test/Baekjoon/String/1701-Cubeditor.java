import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		new Main().solution();
	}

	private void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		int maxLen = 0;

		for (int i = 0; i < s.length(); i++) {
			String sub = s.substring(i);
			maxLen = Math.max(maxLen, getLPS(sub));
		}

		System.out.println(maxLen);
	}

	private int getLPS(String s) {
		int n = s.length();
		int[] lps = new int[n];
		int j = 0;  // 접두사 포인터

		for (int i = 1; i < n; i++) {
			while (j > 0 && s.charAt(i) != s.charAt(j)) {
				j = lps[j - 1]; // 이전 접두사 길이로 이동
			}
			if (s.charAt(i) == s.charAt(j)) {
				lps[i] = ++j; // 접두사 길이 증가
			}
		}

		int maxLPS = 0;
		for (int len : lps) {
			maxLPS = Math.max(maxLPS, len);
		}
		return maxLPS;
	}
}
