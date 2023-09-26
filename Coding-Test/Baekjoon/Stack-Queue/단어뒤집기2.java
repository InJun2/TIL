import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main (String[] args) throws IOException, NumberFormatException {
        String str = read();

        for(int i = 0; i<str.length(); i++) {
            if(str.charAt(i) == '<') {
                stackAllOut();
                sb.append('<');
                flag = true;
                continue;
            }
            if(str.charAt(i) == '>') {
                sb.append('>');
                flag = false;
                continue;
            }
            if(flag) {
                sb.append(str.charAt(i));
                continue;
            }
            if(str.charAt(i) == ' ') {
                stackAllOut();
                sb.append(' ');
                continue;
            }
            stack.push(str.charAt(i));
        }

        stackAllOut();
        System.out.println(sb);

        close();
    }

    private static void stackAllOut() {
        while(!stack.isEmpty()) {
            sb.append(stack.pop());
        }
    }

    static Stack<Character> stack = new Stack<>();

    static boolean flag = false;

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

    public static String[] getTokenizer(String str) {
        return str.split(" ");
    }

    public static int[] getIntegerTokenizer(String str) {
        return Arrays.stream(str.split( " "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static String returnString(String[] val) {
        for(String str : val) {
            sb.append(str);
            sb.append(" ");
        }

        return sb.toString();
    }
}
