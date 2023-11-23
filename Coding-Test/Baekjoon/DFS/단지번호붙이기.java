import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N;
    static boolean[][] arr;
    static boolean[][] visited;
    static int dx[] = {-1, 1, 0, 0};    // 상하 좌우 x,y 확인용 변수
    static int dy[] = {0, 0, -1, 1};
    static ArrayList<Integer> answer;
    static int count = 0;

    public static void main(String[] args) throws IOException, NumberFormatException {
        N = Integer.parseInt(read());
        arr = new boolean[N][N];
        visited = new boolean[N][N];
        answer = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String str = read();

            for (int y = 0; y < str.length(); y++) {
                char check = str.charAt(y);

                if(check == '1') {
                    arr[i][y] = true;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int y = 0; y < N; y++) {
                if (!visited[i][y] && arr[i][y]) {
                    dfs(i, y);
                    answer.add(count);
                    count = 0;
                }
            }
        }

        Collections.sort(answer);
        sb.append(answer.size() + "\n");
        for(int counts : answer) {
            sb.append(counts + "\n");
        }
        write(sb.toString());

        close();
    }

    public static void dfs(int x, int y) {
        count++;
        visited[x][y] = true;
        for(int i=0; i < dx.length; i++) {
            int nowX = x + dx[i];
            int nowY = y + dy[i];

            if (nowX >= 0 && nowY >= 0 && nowX < N && nowY < N) {
                if (!visited[nowX][nowY] && arr[nowX][nowY]) {
                    dfs(nowX, nowY);
                }
            }
        }
    }


    public static String read() throws IOException {
        return br.readLine();
    }

    public static void write(String output) throws IOException {
        wr.write(output);
    }

    public static void close() throws IOException {
        br.close();
        wr.close();
    }
}
