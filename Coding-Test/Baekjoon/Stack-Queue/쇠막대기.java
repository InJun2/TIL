import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main (String[] args) throws IOException, NumberFormatException {
        String str = read();

        int answer = 0;

        for(int i=0; i<str.length(); i++) {
            if(str.charAt(i) == '(') {
                stack.push('(');
            }
            if(str.charAt(i) == ')') {
                stack.pop();
            }
            if(i != 0) {
                if(str.charAt(i -1) == '(' && str.charAt(i) == ')') {
                    answer += stack.size();
                    continue;
                }
                if(str.charAt(i - 1) == ')' && str.charAt(i) == ')') {
                    answer ++;
                }
            }
        }

        System.out.println(answer);

        close();
    }

    static Stack<Character> stack = new Stack<>();
    public static String read() throws IOException {
        return br.readLine();
    }

    public static void write(String output) throws IOException {
        wr.write(output);
    }

    public static void close() throws IOException {
        br.close();
        wr.close();
    }
}
