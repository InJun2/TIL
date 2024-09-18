import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int n;
    int[][] board;
    int blueResult = 0, whiteResult = 0;

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        board = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        recursion(0, n - 1, 0, n - 1);  // 시작

        System.out.println(whiteResult);
        System.out.println(blueResult);
    }

    private void recursion(int startRow, int endRow, int startCol, int endCol) {
        // 색이 모두 같은지 확인
        if (checkColor(startRow, endRow, startCol, endCol)) {
            if (board[startRow][startCol] == 1) {
                blueResult++;
            } else {
                whiteResult++;
            }
            return;
        }

        // 색이 같지 않으면 영역을 4분할하여 재귀 호출
        int middleRow = (startRow + endRow) / 2;
        int middleCol = (startCol + endCol) / 2;

        recursion(startRow, middleRow, startCol, middleCol);             // 1사분면
        recursion(middleRow + 1, endRow, startCol, middleCol);           // 3사분면
        recursion(startRow, middleRow, middleCol + 1, endCol);           // 2사분면
        recursion(middleRow + 1, endRow, middleCol + 1, endCol);         // 4사분면
    }

    // 해당 영역이 모두 같은 색인지 확인하는 함수
    private boolean checkColor(int startRow, int endRow, int startCol, int endCol) {
        int color = board[startRow][startCol];
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (board[i][j] != color) {
                    return false;  // 다른 색이 발견되면 false 반환
                }
            }
        }
        return true;  // 모두 같은 색이면 true 반환
    }
}
