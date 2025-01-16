import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N;
    int[][] board;
    boolean[] visited;
    int min = Integer.MAX_VALUE;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        visited = new boolean[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        combination(0, 0);

        System.out.println(min);
    }

    private void combination(int idx, int cnt) {
        if(cnt == N/2) {
            calcul();

            return;
        }

        for(int i = idx; i < N; i++) {
            if(!visited[i]) {
                visited[i] = true;
                combination(i + 1, cnt + 1);
                visited[i] = false;
            }
        }
    }

    private void calcul() {
        int sum1 = 0;
        int sum2 = 0;

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(visited[i] && visited[j]) {  // 이걸 맵으로 가져오기 가능할듯
                    sum1 += board[i][j];
                } else if(!visited[i] && !visited[j]) {
                    sum2 += board[i][j];
                }
            }
        }

        min = Math.min(min, Math.abs(sum1 - sum2));
    }
}
