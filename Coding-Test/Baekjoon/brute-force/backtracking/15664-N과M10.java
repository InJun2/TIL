import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N, M;
    int[] array;
    StringBuilder sb = new StringBuilder();
    Set<String> set = new HashSet<>();

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        array = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(array);

        dfs(0, 0, new int[M]);
        System.out.println(sb);
    }

    private void dfs(int idx, int pos, int[] arr) {
        if(pos == M) {
            StringBuilder sb2 = new StringBuilder();
            for (int j : arr) {
                sb2.append(j).append(" ");
            }

            if(set.contains(sb2.toString())) {
                return;
            }

            set.add(sb2.toString());
            sb.append(sb2).append("\n");
            return;
        }

        if(idx >= N) {
            return;
        }

        arr[pos] = array[idx];
        dfs(idx + 1, pos + 1, arr);

        dfs(idx + 1, pos, arr);
    }
}

// 메모리 14280kb, 시간 104ms