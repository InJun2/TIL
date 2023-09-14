import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main (String[] args) throws IOException, NumberFormatException {
        int cnt = Integer.parseInt(read());

        for(int i = 0; i < cnt; i++ ) {
            for(char ch : read().toCharArray()) {
                if(ch == '(') {
                    stack.push(1);
                }

                if(ch == ')') {
                    if(stack.isEmpty()) {
                        stack.push(1);
                        break;
                    } else{
                        stack.pop();
                    }
                }
            }

            if(!stack.isEmpty()) {
                sb.append("NO\n");
            }else {
                sb.append("YES\n");
            }
            stack.clear();
        }

        write(sb.toString());
        close();
    }

    static Stack<Integer> stack = new Stack<>();


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
