import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] checked;
    static int N;
    static int[] nx = {0, 1, 0, -1};
    static int[] ny = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());
        Integer key = Integer.parseInt(br.readLine());
        int count = N * N;
        int dire = 0;

        int x = 0;
        int y = 0;
        checked = new int[N][N];

        while(count > 0) {
            checked[y][x] = count--;

            if (check(x + nx[dire], y + ny[dire])) {
                x += nx[dire];
                y += ny[dire];
            } else {
                dire = (dire + 1) % 4;
                if (check(x + nx[dire], y + ny[dire])) {
                    x += nx[dire];
                    y += ny[dire];
                }
            }
        }


        int resultX = 0;
        int resultY = 0;

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                sb.append(checked[i][j]).append(" ");

                if(checked[i][j] == key) {
                    resultX = j + 1;
                    resultY = i + 1;
                }
            }
            sb.append("\n");
        }

        sb.append(resultY).append(" ").append(resultX);
        System.out.println(sb);
    }

    private static boolean check(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N && checked[y][x] == 0;
    }
}