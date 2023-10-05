import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    static ArrayList<Integer>[] lists;
    static boolean[] visited;
    static int[] answer;
    static int N;

    public static void main (String[] args) throws IOException, NumberFormatException {
        N = Integer.parseInt(read());

        lists = new ArrayList[N+1];
        for(int i=1; i<=N; i++) {
            lists[i] = new ArrayList<>();
        }
        visited = new boolean[N+1];
        answer = new int[N+1];

        for(int i =0; i< N-1; i++) {
            StringTokenizer str = new StringTokenizer(read());
            int x = Integer.parseInt(str.nextToken());
            int y = Integer.parseInt(str.nextToken());
            lists[x].add(y);
            lists[y].add(x);
        }

        dfs(1);

        for(int i=2; i<=N; i++) {
            sb.append(answer[i] + "\n");
        }

        write(sb.toString());

        close();
    }

    public static void dfs(int idx) {
        visited[idx] = true;

        for(int i=0; i<lists[idx].size(); i++) {
            int next = lists[idx].get(i);

            if(!visited[next]) {
                answer[next] = idx;
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
