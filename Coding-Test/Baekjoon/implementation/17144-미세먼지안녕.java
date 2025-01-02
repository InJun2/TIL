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
    int c;  // 아랫부분 공기 청정기

    int[] dRow = {1, -1, 0, 0};
    int[] dCol = {0, 0, 1, -1};

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());

        board = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int number = Integer.parseInt(st.nextToken());
                board[i][j] = number;

                if (number == -1) {
                    c = i;
                }
            }
        }

        for (int i = 0; i < T; i++) {
            board = swap();
            clear();
        }

        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                result += board[i][j];
            }
        }

        System.out.println(result + 2);
    }

    private int[][] swap() {
        int[][] tempBoard = new int[N][M];

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                if (board[x][y] == -1) {
                    tempBoard[x][y] = -1;
                    continue;
                }

                if (board[x][y] > 0) {
                    int amount = board[x][y] / 5;
                    int cnt = 0;

                    for (int d = 0; d < 4; d++) {
                        int nx = x + dRow[d];
                        int ny = y + dCol[d];

                        if (nx >= 0 && nx < N && ny >= 0 && ny < M && board[nx][ny] != -1) {
                            tempBoard[nx][ny] += amount;
                            cnt++;
                        }
                    }

                    tempBoard[x][y] += board[x][y] - (amount * cnt);
                }
            }
        }

        return tempBoard;
    }



    private void printBoard(int[][] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void clear() {
        int top = c - 1;

        for (int x = top - 1; x > 0; x--) {
            board[x][0] = board[x - 1][0];
        }

        for (int y = 0; y < M - 1; y++) {
            board[0][y] = board[0][y + 1];
        }

        for (int x = 0; x < top; x++) {
            board[x][M - 1] = board[x + 1][M - 1];
        }

        for (int y = M - 1; y > 1; y--) {
            board[top][y] = board[top][y - 1];
        }

        board[top][1] = 0;

        int bottom = c;

        for (int x = bottom + 1; x < N - 1; x++) {
            board[x][0] = board[x + 1][0];
        }

        for (int y = 0; y < M - 1; y++) {
            board[N - 1][y] = board[N - 1][y + 1];
        }

        for (int x = N - 1; x > bottom; x--) {
            board[x][M - 1] = board[x - 1][M - 1];
        }

        for (int y = M - 1; y > 1; y--) {
            board[bottom][y] = board[bottom][y - 1];
        }

        board[bottom][1] = 0;
    }
}


// 처음에 map 이나 queue로 하나씩 넣어서 해당 부분만 진행하려 했으나 스왑이 기존 값에 대해 한번에 진행해야 하므로 복제 배열을 사용
// 그 와중에 테이블 값을 이동하는게 어려워서 제대로 진행하지 못하고 시간을 엄청 잡아먹게 되었음 -> 이후 테이블 값 변경은 블로그 참조
// 메모리 32164kb, 시간 320ms