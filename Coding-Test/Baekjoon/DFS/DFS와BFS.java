import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main (String[] args) throws IOException, NumberFormatException {
        int number[] = getIntegerTokenizer(read());

        node= number[0];
        line = number[1];
        int start = number[2];
        link = new int[node+1][node+1];
        check = new boolean[node+1];

        for(int i = 0; i < line; i++) {
            int connection[] = getIntegerTokenizer(read());

            link[connection[0]][connection[1]] = link[connection[1]][connection[0]] = 1;
        }

        dfs(start);
        sb.append("\n");
        check = new boolean[node +1];

        bfs(start);
        write(String.valueOf(sb));

        close();
    }

    public static void dfs(int start) {
        check[start] = true;
        sb.append(start + " ");

        for(int i=0; i <=node; i++) {
            if(link[start][i] == 1 && !check[i]) {
                dfs(i);
            }
        }
    }

    public static void bfs(int start) {
        q.add(start);
        check[start] = true;

        while(!q.isEmpty()) {
            start = q.poll();
            sb.append(start + " ");
            for(int i =1; i<=node; i++) {
                if(link[start][i] == 1 && !check[i]) {
                    q.add(i);
                    check[i] = true;
                }
            }
        }
    }

    static int node, line;
    static int link[][];
    static boolean check[];
    static Queue<Integer> q = new LinkedList<>();

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

    public static int[] getIntegerTokenizer(String str) {
        return Arrays.stream(str.split( " "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

}
