import java.io.*;
import java.util.*;

public class Main {

    int N, M, start;
    List<List<Integer>> list = new ArrayList<>();
    boolean[] checked;
    int[] result;

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(st.nextToken());

        checked = new boolean[N + 1];
        result = new int[N + 1];
        for(int i = 0; i < N + 1; i++) {
            list.add(new ArrayList<>());
        }

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            list.get(a).add(b);
            list.get(b).add(a);
        }

        dfs(start, 0);

        for(int i = 1; i < checked.length; i++) {
            if(!checked[i]) {
                sb.append(-1);
            } else {
                sb.append(result[i]);
            }

            sb.append("\n");
        }

        System.out.println(sb);
    }

    private void dfs(int from, int cnt) {
        checked[from] = true;
        result[from] = cnt;

        Collections.sort(list.get(from));

        for(int to : list.get(from)) {
            if(!checked[to]) {
                dfs(to, cnt + 1);
            }
        }
    }
}
