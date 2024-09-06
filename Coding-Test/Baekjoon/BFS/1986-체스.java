import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    int N, M;
    boolean[][] visited;  // 공격받는 칸 여부
    boolean[][] board;    // 말이 있는 위치 (장애물 역할)

    // 기사 이동 경로 (L자 이동)
    int[] kr = {1, 1, -1, -1, 2, 2, -2, -2};
    int[] kc = {2, -2, 2, -2, 1, -1, 1, -1};

    // 퀸의 8방향 이동 (상하좌우 및 대각선)
    int[] qr = {1, -1, 0, 0, 1, 1, -1, -1};  // 행 방향
    int[] qc = {0, 0, 1, -1, 1, -1, 1, -1};  // 열 방향

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 체스판 크기 입력
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        visited = new boolean[N][M];
        board = new boolean[N][M];

        // 1. 퀸의 개수와 좌표 입력
        st = new StringTokenizer(br.readLine());
        int numQueens = Integer.parseInt(st.nextToken());
        int[][] queens = new int[numQueens][2];
        for (int i = 0; i < numQueens; i++) {
            queens[i][0] = Integer.parseInt(st.nextToken()) - 1;
            queens[i][1] = Integer.parseInt(st.nextToken()) - 1;
            board[queens[i][0]][queens[i][1]] = true;  // 퀸이 있는 위치 기록
            visited[queens[i][0]][queens[i][1]] = true;  // 퀸이 있는 칸은 공격받음
        }

        // 2. 기사의 개수와 좌표 입력
        st = new StringTokenizer(br.readLine());
        int numKnights = Integer.parseInt(st.nextToken());
        for (int i = 0; i < numKnights; i++) {
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            board[row][col] = true;  // 기사가 있는 위치 기록
            visited[row][col] = true;  // 기사가 있는 칸은 공격받음
            moveKnight(row, col);  // 기사의 이동 처리
        }

        // 3. 폰의 개수와 좌표 입력
        st = new StringTokenizer(br.readLine());
        int numPawns = Integer.parseInt(st.nextToken());
        for (int i = 0; i < numPawns; i++) {
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            board[row][col] = true;  // 폰이 있는 위치 기록
            visited[row][col] = true;  // 폰이 있는 칸은 공격받음
        }

        // 4. 퀸의 이동 경로 처리
        for (int i = 0; i < numQueens; i++) {
            moveQueen(queens[i][0], queens[i][1]);
        }

        // 5. 안전한 칸 세기
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visited[i][j] && !board[i][j]) {  // 공격받지 않은 칸이면서 말이 없는 칸
                    result++;
                }
            }
        }

        // 6. 결과 출력
        System.out.println(result);
    }

    // 퀸의 이동 경로 처리 (8방향 상하좌우 + 대각선)
    private void moveQueen(int row, int col) {
        for (int i = 0; i < 8; i++) {
            int nr = row;
            int nc = col;
            while (true) {
                nr += qr[i];
                nc += qc[i];
                if (checkBounds(nr, nc) || board[nr][nc]) {  // 범위 벗어나거나 말이 있으면 멈춤
                    break;
                }
                visited[nr][nc] = true;  // 퀸이 공격할 수 있는 칸 표시
            }
        }
    }

    // 기사의 이동 경로 처리 (L자 이동)
    private void moveKnight(int row, int col) {
        for (int i = 0; i < kr.length; i++) {
            int nr = row + kr[i];
            int nc = col + kc[i];
            if (checkBounds(nr, nc)) {  // 범위 벗어나면 패스
                continue;
            }
            visited[nr][nc] = true;  // 기사가 공격할 수 있는 칸 표시
        }
    }

    // 범위 체크
    private boolean checkBounds(int row, int col) {
        return row < 0 || col < 0 || row >= N || col >= M;
    }
}
