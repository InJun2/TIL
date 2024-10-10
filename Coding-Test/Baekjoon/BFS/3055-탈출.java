import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static final String END = "KAKTUS";

    int R, C;
    char[][] board;
    int[][] distWater, distHedgehog;
    Queue<int[]> waterQueue = new ArrayDeque<>();
    Queue<int[]> hedgehogQueue = new ArrayDeque<>();

    int[] mRow = {1, -1, 0, 0};
    int[] mCol = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        board = new char[R][C];
        distWater = new int[R][C]; // 물이 도달하는 시간을 기록
        distHedgehog = new int[R][C]; // 고슴도치가 도달하는 시간을 기록

        int startR = -1, startC = -1;

        // 물과 고슴도치 위치 초기화
        for (int i = 0; i < R; i++) {
            String str = br.readLine();
            for (int j = 0; j < C; j++) {
                board[i][j] = str.charAt(j);

                if (board[i][j] == 'S') {
                    startR = i;
                    startC = j;
                    distHedgehog[i][j] = 1; // 시작점은 1로 설정 (0은 방문하지 않은 상태)
                    hedgehogQueue.offer(new int[]{i, j});
                } else if (board[i][j] == '*') {
                    distWater[i][j] = 1; // 물 시작점도 1로 설정
                    waterQueue.offer(new int[]{i, j});
                }
            }
        }

        // BFS로 물을 먼저 확산시킴
        bfsWater();

        // BFS로 고슴도치를 이동시킴
        int result = bfsHedgehog();

        if (result == -1) {
            System.out.println(END);
        } else {
            System.out.println(result);
        }
    }

    private void bfsWater() {
        while (!waterQueue.isEmpty()) {
            int[] current = waterQueue.poll();
            int row = current[0];
            int col = current[1];

            for (int i = 0; i < 4; i++) {
                int newRow = row + mRow[i];
                int newCol = col + mCol[i];

                if (newRow < 0 || newCol < 0 || newRow >= R || newCol >= C) {
                    continue;
                }

                if (distWater[newRow][newCol] != 0 || board[newRow][newCol] == 'X' || board[newRow][newCol] == 'D') {
                    continue;
                }

                distWater[newRow][newCol] = distWater[row][col] + 1;
                waterQueue.offer(new int[]{newRow, newCol});
            }
        }
    }

    private int bfsHedgehog() {
        while (!hedgehogQueue.isEmpty()) {
            int[] current = hedgehogQueue.poll();
            int row = current[0];
            int col = current[1];

            for (int i = 0; i < 4; i++) {
                int newRow = row + mRow[i];
                int newCol = col + mCol[i];

                if (newRow < 0 || newCol < 0 || newRow >= R || newCol >= C) {
                    continue;
                }

                if (distHedgehog[newRow][newCol] != 0 || board[newRow][newCol] == 'X') {
                    continue;
                }

                // 목적지 'D'에 도달하면, 시간을 반환
                if (board[newRow][newCol] == 'D') {
                    return distHedgehog[row][col];
                }

                // 물이 아직 확산되지 않았거나, 고슴도치가 물보다 먼저 도착할 수 있는 경우만 이동
                if (distWater[newRow][newCol] == 0 || distHedgehog[row][col] + 1 < distWater[newRow][newCol]) {
                    distHedgehog[newRow][newCol] = distHedgehog[row][col] + 1;
                    hedgehogQueue.offer(new int[]{newRow, newCol});
                }
            }
        }

        return -1; // 도달하지 못하면 -1 반환
    }
}
