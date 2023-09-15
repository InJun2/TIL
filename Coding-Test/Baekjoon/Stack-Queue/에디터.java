import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main (String[] args) throws IOException, NumberFormatException {
        String str = read();
        int cnt = Integer.parseInt(read());

        Stack<Character> leftStack = new Stack<>();
        Stack<Character> rightStack = new Stack<>();

        for(char ch : str.toCharArray()) {
            leftStack.push(ch);
        }

        for(int i=0; i< cnt; i++) {
            String cmd = br.readLine();
            char ch = cmd.charAt(0);

            switch(ch) {
                case 'L':
                    if(!leftStack.isEmpty())
                        rightStack.push(leftStack.pop());

                    break;
                case 'D':
                    if(!rightStack.isEmpty())
                        leftStack.push(rightStack.pop());

                    break;
                case 'B':
                    if(!leftStack.isEmpty()) {
                        leftStack.pop();
                    }
                    break;
                case 'P':
                    char t = cmd.charAt(2);
                    leftStack.push(t);

                    break;
                default:
                    break;
            }
        }

        while(!leftStack.isEmpty())
            rightStack.push(leftStack.pop());

        while(!rightStack.isEmpty())
            sb.append(rightStack.pop());

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
