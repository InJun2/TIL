import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N, M;
    int[][] board;
    boolean[][] visited;
    int max = 0;

    int[] dRow = {1, -1, 0, 0};
    int[] dCol = {0, 0, 1, -1};

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                visited[i][j] = true;
                dfs(i, j, 0, board[i][j]);
                visited[i][j] = false;
            }
        }

        System.out.println(max);
    }

    private void dfs(int row, int col, int depth, int sum) {
        if(depth == 3) {
            max = Math.max(max, sum);
            return;
        }

        for(int i = 0; i < dRow.length; i++) {
            int mRow = row + dRow[i];
            int mCol = col + dCol[i];

            if(mRow < 0 || mRow >= N || mCol < 0 || mCol >= M || visited[mRow][mCol]) {
                continue;
            }

            if(depth == 1) {
                visited[mRow][mCol] = true;
                dfs(row, col, depth + 1, sum + board[mRow][mCol]);
                visited[mRow][mCol] = false;
            }

            visited[mRow][mCol] = true;
            dfs(mRow, mCol, depth + 1, sum + board[mRow][mCol]);
            visited[mRow][mCol] = false;
        }
    }
}

// 문제만 봤을 때는 이걸 어떻게 풀어야하는지 이해하지 못함
// 문제 풀이 방법을 블로그를 참조
// 어차피 해당 블록을 회전하고 대칭하면 4개로 만들어지는 테트리스의 모든 경우를 조회하면 됨
// 이를 해결하기 위해 한 지점씩 모두 depth가 4가 되도록 테트리스를 dfs 조회하고 확인
// 여기서 문제가 'ㅗ, ㅜ, ㅏ, ㅓ' 등 의 모음은 백트래킹으로 현재 visited 를 반영하면서 다음으로 넘어갈 수 없어 따로 처리가 필요
// 이를 통해 depth 가 1인 경우 추가적으로 방문처리를 하면서 dfs 탐색 진행
// 메모리 34456kb, 시간 676ms