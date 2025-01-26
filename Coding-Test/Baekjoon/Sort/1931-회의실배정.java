import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[][] meetings = new int[N][2];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            meetings[i][0] = Integer.parseInt(st.nextToken());
            meetings[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(meetings, (a, b) -> {
            if (a[1] == b[1]) { // 만약 끝나는 시간이 같으면 시작시간이 빠른 순으로 정렬
                return a[0] - b[0];
            }

            return a[1] - b[1];
        });

        int count = 0;
        int currentEndTime = 0;
        for (int i = 0; i < N; i++) {
            if (meetings[i][0] >= currentEndTime) {
                currentEndTime = meetings[i][1];
                count++;
            }
        }

        System.out.println(count);
    }
}
