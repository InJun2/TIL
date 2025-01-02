// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.util.*;

// public class Main {
//     public static void main(String[] args) throws IOException {
//         new Main().solution();
//     }

//     int N;
//     char[][] board;
//     boolean[][][] visited;

//     int endRow = 0;
//     int endCol = 0;

//     int[] dRow = {1, -1, 0, 0};
//     int[] dCol = {0, 0, 1, -1};

//     private void solution() throws IOException {
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//         N = Integer.parseInt(br.readLine());
//         board = new char[N][N];
//         visited = new boolean[N][N][2];

//         int targetRow = 0, targetCol = 0;

//         for (int i = 0; i < N; i++) {
//             String str = br.readLine();
//             for (int j = 0; j < N; j++) {
//                 board[i][j] = str.charAt(j);

//                 if (str.charAt(j) == 'B') {
//                     targetRow = i;
//                     targetCol = j;
//                 } else if (str.charAt(j) == 'E') {
//                     endRow = i;
//                     endCol = j;
//                 }
//             }
//         }

//         int[] position = getPosition(targetRow, targetCol, 'B');
//         int result = bfs(position);
//         System.out.println(result);
//     }

//     private int bfs(int[] pos) {
//         int[] goal = getPosition(endRow, endCol, 'E');
//         Queue<int[]> queue = new ArrayDeque<>();
//         queue.add(new int[]{pos[0], pos[1], pos[2], 0});
//         visited[pos[0]][pos[1]][pos[2]] = true;

//         while (!queue.isEmpty()) {
//             int[] cur = queue.poll();
//             int row = cur[0], col = cur[1];
//             int dir = cur[2], num = cur[3];

//             if (row == goal[0] && col == goal[1] && dir == goal[2]) {
//                 return num;
//             }

//             // 이동
//             for (int i = 0; i < 4; i++) {
//                 int mRow = row + dRow[i];
//                 int mCol = col + dCol[i];

//                 if (isMove(mRow, mCol, dir) && !visited[mRow][mCol][dir]) {
//                     visited[mRow][mCol][dir] = true;
//                     queue.add(new int[]{mRow, mCol, dir, num + 1});
//                 }
//             }

//             // 회전
//             if (isTurn(row, col)) {
//                 int nDir = Math.abs(dir - 1);
//                 if (!visited[row][col][nDir]) {
//                     visited[row][col][nDir] = true;
//                     queue.add(new int[]{row, col, nDir, num + 1});
//                 }
//             }
//         }

//         return 0;
//     }

//     private boolean isMove(int row, int col, int dir) {
//         if (row < 0 || row >= N || col < 0 || col >= N) {
//             return false;
//         }

//         if (dir == 0) { // 가로
//             return col - 1 >= 0 && col + 1 < N
//                     && board[row][col - 1] != '1'
//                     && board[row][col] != '1'
//                     && board[row][col + 1] != '1';
//         } else { // 세로
//             return row - 1 >= 0 && row + 1 < N
//                     && board[row - 1][col] != '1'
//                     && board[row][col] != '1'
//                     && board[row + 1][col] != '1';
//         }
//     }

//     private int[] getPosition(int row, int col, char ch) {
//         // 가로 방향 확인
//         if (col - 1 >= 0 && col + 1 < N
//                 && board[row][col - 1] == ch && board[row][col] == ch && board[row][col + 1] == ch) {
//             return new int[]{row, col, 0}; // 중심: 중간의 col
//         }
//         // 세로 방향 확인
//         if (row - 1 >= 0 && row + 1 < N
//                 && board[row - 1][col] == ch && board[row][col] == ch && board[row + 1][col] == ch) {
//             return new int[]{row, col, 1}; // 중심: 중간의 row
//         }
//         return new int[]{row, col, 0}; // 기본값: 가로로 처리
//     }

//     private boolean isTurn(int row, int col) {
//         for (int i = -1; i <= 1; i++) {
//             for (int j = -1; j <= 1; j++) {
//                 int nRow = row + i;
//                 int nCol = col + j;

//                 if (nRow < 0 || nRow >= N || nCol < 0 || nCol >= N || board[nRow][nCol] == '1') {
//                     return false;
//                 }
//             }
//         }

//         return true;
//     }
// }
// 기존 코드는 중간 지점만 확인하고 이를 이동가능한지, 회전 가능한지 확인 후 큐에 넣는 방식인데 가로, 세로 및 현재 위치에 대해 재대로 값을 가져오지 못함
// 이후 GPT를 참고하여 코드 변경

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N;
    char[][] board;
    boolean[][][] visited;

    Point[] startB, endE;

    int[] dRow = {-1, 1, 0, 0};
    int[] dCol = {0, 0, -1, 1};

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        board = new char[N][N];
        visited = new boolean[2][N][N];

        startB = new Point[3];
        endE = new Point[3];

        int sIdx = 0, eIdx = 0;

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < N; j++) {
                board[i][j] = str.charAt(j);

                if (board[i][j] == 'B') {
                    startB[sIdx++] = new Point(i, j);
                } else if (board[i][j] == 'E') {
                    endE[eIdx++] = new Point(i, j);
                }
            }
        }

        int result = bfs();
        System.out.println(result);
    }

    private int bfs() {
        Queue<Log> queue = new LinkedList<>();

        int dir = (startB[0].y + 1 == startB[1].y) ? 0 : 1; // 0: 가로, 1: 세로
        queue.add(new Log(startB[1].x, startB[1].y, dir, 0));
        visited[dir][startB[1].x][startB[1].y] = true;

        while (!queue.isEmpty()) {
            Log cur = queue.poll();

            int x = cur.x;
            int y = cur.y;
            int curDir = cur.dir;
            int dist = cur.dist;

            if (x == endE[1].x && y == endE[1].y) {
                if (curDir == 0 && board[x][y - 1] == 'E' && board[x][y + 1] == 'E') {
                    return dist;
                } else if (curDir == 1 && board[x - 1][y] == 'E' && board[x + 1][y] == 'E') {
                    return dist;
                }
            }

            for (int d = 0; d < 4; d++) {
                int nx = x + dRow[d];
                int ny = y + dCol[d];

                if (canMove(nx, ny, curDir) && !visited[curDir][nx][ny]) {
                    visited[curDir][nx][ny] = true;
                    queue.add(new Log(nx, ny, curDir, dist + 1));
                }
            }

            if (canRotate(x, y) && !visited[1 - curDir][x][y]) {
                visited[1 - curDir][x][y] = true;
                queue.add(new Log(x, y, 1 - curDir, dist + 1));
            }
        }

        return 0;
    }

    private boolean canMove(int x, int y, int dir) {
        if (x < 0 || x >= N || y < 0 || y >= N) {
            return false;
        }

        if (dir == 0) { // 가로
            return y - 1 >= 0 && y + 1 < N
                    && board[x][y - 1] != '1' && board[x][y] != '1' && board[x][y + 1] != '1';
        } else { // 세로
            return x - 1 >= 0 && x + 1 < N
                    && board[x - 1][y] != '1' && board[x][y] != '1' && board[x + 1][y] != '1';
        }
    }

    private boolean canRotate(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i < 0 || i >= N || j < 0 || j >= N || board[i][j] == '1') {
                    return false;
                }
            }
        }
        return true;
    }

    static class Log {
        int x, y, dir, dist;

        Log(int x, int y, int dir, int dist) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.dist = dist;
        }
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

// 메모리 14584kb, 시간 112ms