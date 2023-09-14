import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main (String[] args) throws IOException, NumberFormatException {
        int cnt = Integer.parseInt(read());

        for(int i = 0 ; i < cnt ; i++) {
            int readNum = Integer.parseInt(br.readLine());

            for( ; temp <= readNum ; temp++) {
                stack.push(temp);
                sb.append("+").append("\n");
            }

            if(stack.peek() == readNum) {
                stack.pop();
                sb.append("-").append("\n");
            }else {
                err = true;
            }
        }

        if(err) {
            write("NO");
        }
        else {
            write(sb.toString());
        }
        
        close();
    }

    static int temp = 1;
    static boolean err = false;

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