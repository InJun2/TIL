// via를 사용한 재귀에 대한 방법이 떠올르지 않아 GPT 참조
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int count = 0;  // 이동 횟수 카운트
    StringBuilder sb = new StringBuilder();

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());  // 원판의 개수 입력

        // 하노이 탑 재귀 호출
        hanoi(N, 1, 3, 2);  // 1번 기둥에서 3번 기둥으로 N개의 원판을 옮김

        System.out.println(count);  // 총 이동 횟수 출력
        System.out.println(sb);     // 이동 과정 출력
    }

    // 하노이 탑을 재귀적으로 해결하는 함수
    private void hanoi(int n, int from, int to, int via) {
        if (n == 1) {
            // 기저 조건: 원판이 1개일 경우 바로 옮김
            sb.append(from).append(" ").append(to).append("\n");
            count++;
            return;
        }

        // 1. N-1개의 원판을 from에서 via로 옮김
        hanoi(n - 1, from, via, to);

        // 2. 가장 큰 원판을 from에서 to로 옮김
        sb.append(from).append(" ").append(to).append("\n");
        count++;

        // 3. N-1개의 원판을 via에서 to로 옮김
        hanoi(n - 1, via, to, from);
    }
}
