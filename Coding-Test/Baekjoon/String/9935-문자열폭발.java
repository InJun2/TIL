// import java.io.*;
// import java.util.*;

// public class Main {

//     public static void main(String[] args) throws IOException {
//         new Main().solution();
//     }

//     String FAIL = "FRULA";

//     private void solution() throws IOException {
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

//         String str = br.readLine();
//         String split = br.readLine();

//         while(str.contains(split)) {
//             str = str.replaceAll(split, "");
//         }

//         System.out.println(str == "" ? FAIL : str);
//     }
// }
// 메모리 초과

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		new Main().solution();
	}

	String FAIL = "FRULA";

	private void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		String str = br.readLine();
		String splitStr = br.readLine();

		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < str.length(); i++) {
			stack.push(str.charAt(i));

			if (stack.size() >= splitStr.length()) {
				boolean flag = true;

				for (int j = 0; j < splitStr.length(); j++) {
					if (splitStr.charAt(j) != stack.get(stack.size() - splitStr.length() + j)) {
						flag = false;
						break;
					}
				}
				
				if (flag) {
					for (int k = 0; k < splitStr.length(); k++) {
						stack.pop();
					}
				}
			}
		}

		if (stack.isEmpty()) {
			sb.append(FAIL);
		} else {
			for (char c : stack) {
				sb.append(c);
			}
		}

		System.out.println(sb);
	}
}
