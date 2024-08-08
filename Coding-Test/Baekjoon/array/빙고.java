import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] board = new int[5][5];
    static boolean[][] visited = new boolean[5][5];
    static Map<Integer, Integer[]> boardMap = new HashMap<>();
    static int count = 0;

    public static void main(String[] args) throws IOException {
        StringTokenizer st;

        for (int i = 0; i < 5; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < 5; j++) {
                int number = Integer.parseInt(st.nextToken());
                board[i][j] = number;
                boardMap.put(number, new Integer[]{i, j});
            }
        }

        for (int i = 0; i < 5; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < 5; j++) {
                count++;
                int number = Integer.parseInt(st.nextToken());
                Integer[] check = boardMap.get(number);
                visited[check[0]][check[1]] = true;

                if(checkBingo()) {
                    System.out.println(count);
                    return;
                }
            }
        }
    }

    private static boolean checkBingo() {
        if (count < 12) {
            return false;
        }

        int bingo = 0;

        for (int i = 0; i < 5; i++) {
            if (visited[i][0] && visited[i][1] && visited[i][2] && visited[i][3] && visited[i][4]) {
                bingo++;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (visited[0][i] && visited[1][i] && visited[2][i] && visited[3][i] && visited[4][i]) {
                bingo++;
            }
        }

        if (visited[0][0] && visited[1][1] && visited[2][2] && visited[3][3] && visited[4][4]) {
            bingo++;
        }

        if (visited[0][4] && visited[1][3] && visited[2][2] && visited[3][1] && visited[4][0]) {
            bingo++;
        }

        return bingo >= 3 ? true : false;
    }
}