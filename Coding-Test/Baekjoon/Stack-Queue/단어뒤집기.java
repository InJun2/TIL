import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main (String[] args) throws IOException, NumberFormatException {
        Stack<Character> stack = new Stack<>();
        int cnt = Integer.parseInt(read());

        for (int i=0; i < cnt; i++) {
            String[] str = read().split(" ");

            for(int y=0; y <str.length; y++) {
                for(char ch : str[y].toCharArray()) {
                    stack.push(ch);
                }

                while(!stack.isEmpty()) {
                    sb.append(stack.pop());
                }

                sb.append(" ");
            }
                sb.append("\n");
        }

        write(sb.toString());
        close();
    }

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
