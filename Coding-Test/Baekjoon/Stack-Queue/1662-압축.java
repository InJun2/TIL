import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    String str;
    int idx = 0;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        str = br.readLine();

        System.out.println(parse());
    }

    private long parse() {
        Stack<Long> stack = new Stack<>();
        long length = 0;
        int k = 0;

        while (idx < str.length()) {
            char c = str.charAt(idx);

            if (c == ')') {
                idx++;
                while (!stack.isEmpty()) {
                    length += stack.pop();
                }
                return length;

            } else if (c == '(') {
                idx ++;
                length --;
                long innerLen = parse();
                stack.push(k * innerLen);
            } else {
                stack.push(1L);
                k = c - '0';
                idx++;
            }
        }

        while (!stack.isEmpty()) {
            length += stack.pop();
        }

        return length;
    }
}
