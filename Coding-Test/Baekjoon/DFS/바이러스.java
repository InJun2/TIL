import java.io.*;
import java.util.Arrays;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main (String[] args) throws IOException, NumberFormatException {

        node = Integer.parseInt(read());
        line = Integer.parseInt(read());

        arr = new int[node+1][node+1];
        check = new boolean[node+1];

        for(int i=0; i < line; i ++) {
            int computer[] = getIntegerTokenizer(br.readLine());
            arr[computer[0]][computer[1]] = arr[computer[1]][computer[0]] = 1;
        }

        dfs(1);
        write(String.valueOf(count-1));

        close();
    }

    static void dfs(int start) {
        check[start] = true;
        count ++;

        for(int i = 0; i<= node; i++) {
            if(arr[start][i] == 1 && !check[i]) {
                dfs(i);
            }
        }
    }

    static int node;
    static int line;

    static int[][] arr;

    static boolean[] check;

    static int count = 0;


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
            sb.append("\n");
        }

        return sb.toString();
    }
}
