import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));


    static ArrayList<Integer>[] graph;
    static boolean[] visited;
    static int[] answer;
    static int M, N, R;
    static int order;

    public static void main (String[] args) throws IOException, NumberFormatException {
        StringTokenizer tokenizer = new StringTokenizer(read());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        R = Integer.parseInt(tokenizer.nextToken());

        graph = new ArrayList[N +1];
        for(int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        visited = new boolean[N +1];
        answer = new int[N +1];
        order = 1;

        for(int i=0; i< M; i++) {
            tokenizer = new StringTokenizer(read());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());
            graph[x].add(y);
            graph[y].add(x);
        }

        // 오름차순 정렬
        for(int i=1; i<=N; i++) {
            Collections.sort(graph[i]);
        }

        dfs(R);

        for(int i = 1; i <= N; i++) {
            sb.append(answer[i] + "\n");
        }

        write(sb.toString());

        close();
    }

    public static void dfs(int idx) {
        visited[idx] = true;
        answer[idx] = order;
        order++;

        for(int i=0; i< graph[idx].size(); i++) {
            int next = graph[idx].get(i);

            if(!visited[next]){
                dfs(next);
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
