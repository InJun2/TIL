import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main (String[] args) throws IOException, NumberFormatException {
        int inputNumber = Integer.parseInt(read());


        for(int i=0; i<inputNumber; i++) {
            String str = read();

            if(str.startsWith("push")) {
                String[] pushNum = str.split(" ");
                stack.push(Integer.parseInt(pushNum[1]));
                continue;
            }

            switch (str) {
                case "size" :
                    sb.append(stack.size());
                    sb.append("\n");
                    break;
                case "pop" :
                    if(stack.isEmpty()) {
                        sb.append("-1");
                        sb.append("\n");
                        break;
                    }
                    sb.append(stack.pop());
                    sb.append("\n");
                    break;
                case "empty" :
                    if(stack.isEmpty()) {
                        sb.append("1");
                        sb.append("\n");
                        break;
                    }
                    sb.append("0");
                    sb.append("\n");
                    break;
                case "top" :
                    if(stack.isEmpty()) {
                        sb.append("-1");
                        sb.append("\n");
                        break;
                    }
                    sb.append(stack.peek());
                    sb.append("\n");
                    break;
            }
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
