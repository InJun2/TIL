import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N;
    static ArrayList<ArrayList<Integer>> graph;
    static int[] colors;
    static int RED = 1;

    public static void main(String[] args) throws IOException, NumberFormatException {
        int K = Integer.parseInt(read());

        for (int test = 0; test < K; test++) {
            StringTokenizer tokenizer = new StringTokenizer(read());
            int V = Integer.parseInt(tokenizer.nextToken());
            int E = Integer.parseInt(tokenizer.nextToken());
            graph = new ArrayList<>();
            colors = new int[V + 1];

            for (int y = 0; y <= V; y++) {
                graph.add(new ArrayList<>());
            }

            // 그래프 연결
            for (int edge = 0; edge < E; edge++) {
                tokenizer = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(tokenizer.nextToken());
                int to = Integer.parseInt(tokenizer.nextToken());

                graph.get(from).add(to);
                graph.get(to).add(from);
            }

            // 1. 색칠 되지 않은 모든 정점에 대해서
            boolean rst = false;
            for (int y = 1; y <= V; y++) {
                if (colors[y] == 0) {
                    rst = isBipartiteGraph(y, RED);
                }
                if (!rst) break;
            }
            if (rst) System.out.println("YES");
            else System.out.println("NO");
        }

        close();
    }

    private static boolean isBipartiteGraph(int start, int color) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        // 2. 시작 정점 임의의 색상으로 색칠
        colors[start] = color;

        while(!queue.isEmpty()) {
            int cur = queue.poll();
            for(int next : graph.get(cur)) {
                // 4. 인접 정점 색이 동일하면 이분 그래프가 아님
                if(colors[cur] == colors[next]) return false;

                // 3. 인접 정점 색칠 안된 경우 현재 정점 반대 색깔로 색칠
                // 색상 배열을 통해 방문 여부 확인 가능
                if(colors[next] == 0) {
                    colors[next] = colors[cur] * -1;
                    queue.add(next);
                }
            }
        }
        return true;
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

// 해당 문제는 풀이하지 못해 완전히 블로그를 참조하였음 (https://velog.io/@zayson/백준-Java-1707번-이분-그래프)