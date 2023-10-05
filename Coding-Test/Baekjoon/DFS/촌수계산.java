import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    static boolean[][] graph;
    static boolean[] visited;
    static int N, start, end, answer;

    public static void main (String[] args) throws IOException, NumberFormatException {
        N = Integer.parseInt(read());

        StringTokenizer tokenizer = new StringTokenizer(read());
        start = Integer.parseInt(tokenizer.nextToken());
        end = Integer.parseInt(tokenizer.nextToken());

        graph = new boolean[N + 1][N + 1];
        visited = new boolean[N + 1];
        answer = -1;

        int M = Integer.parseInt(read());
        for(int i=0; i<M; i++) {
            tokenizer = new StringTokenizer(read());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());
            graph[x][y] = graph[y][x] = true;
        }

        dfs(start, 0);

        write(String.valueOf(answer));

        close();
    }

    public static void dfs(int idx, int cnt) {
        visited[idx] = true;
        if(idx == end) {
            answer = cnt;
            return;
        }

        for(int i = 1; i<= N ; i++) {
            if(!visited[i] && graph[idx][i]) {
                dfs(i, cnt + 1);
            }
        }
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
