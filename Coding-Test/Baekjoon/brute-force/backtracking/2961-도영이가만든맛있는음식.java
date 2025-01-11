import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N;
    int[][] board;
    List<Long> list = new ArrayList<>();

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        board = new int[N][2];

        for(int i = 0; i < N; i++) {
            // 부분집합 개수 정해서 최소, 즉 0개인 경우를 제외
            st = new StringTokenizer(br.readLine());
            board[i][0] = Integer.parseInt(st.nextToken());
            board[i][1] = Integer.parseInt(st.nextToken());
        }

        dfs(1, 0, 0, 0);
        Collections.sort(list);
        System.out.println(list.get(0));
    }

    private void dfs(int a, int b, int idx, int total) {
        if(idx == N) {
            if(total == 0) {
                return;
            }

            list.add((long) Math.abs(a - b));
            return;
        }

        for(int i = idx; i < N; i++) {
            if(total == 0) {
                dfs(board[i][0], board[i][1], i + 1, total + 1);
                continue;
            }

            dfs(board[i][0] * a, board[i][1] + b, i + 1, total + 1);

            dfs(a, b, i + 1, total);
        }
    }
}

// 메모리 17320kb, 시간 144ms