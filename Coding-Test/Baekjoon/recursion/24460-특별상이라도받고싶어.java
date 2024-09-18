import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int n;
    int[][] board;

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

        int result = recursion(0, n - 1, 0, n - 1);

        System.out.println(result);
    }

    private int recursion(int startRow, int endRow, int startCol, int endCol) {
        if(startRow == endRow) {
            return board[startRow][startCol];
        }

        int middleRow = (startRow + endRow) / 2;
        int middleCol = (startCol + endCol) / 2;

        int a = recursion(startRow, middleRow, startCol, middleCol);
        int b = recursion(middleRow + 1, endRow, startCol, middleCol);
        int c = recursion(startRow, middleRow, middleCol + 1, endCol);
        int d = recursion(middleRow + 1, endRow, middleCol + 1, endCol);

        int[] array = new int[]{a, b, c, d};
        Arrays.sort(array);

        return array[1];
    }
}
