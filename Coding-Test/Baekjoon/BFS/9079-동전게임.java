import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    final static int LEN = 3;
    Set<Integer> visited;
    int result = 000000000;

    int[] change = {
            0b111000000, 0b000111000, 0b000000111,
            0b100100100, 0b010010010, 0b001001001,
            0b100010001, 0b001010100
    };

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        for (int t = 0; t < N; t++) {
            StringBuilder inputChar = new StringBuilder();
            visited = new HashSet<>();

            for (int i = 0; i < LEN; i++) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < LEN; j++) {
                    char ch = st.nextToken().charAt(0);
                    if(ch == 'H') {
                        inputChar.append('0');
                        continue;
                    }

                    inputChar.append('1');
                }
            }

            result = Integer.parseInt(inputChar.toString(), 2);
            int cnt = bfs(result);
            sb.append(cnt).append("\n");
        }

        System.out.println(sb);
    }

    private int bfs(int number) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{number, 0});
        visited.add(number);

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            int state = curr[0];
            int cnt = curr[1];

            if(state == 0 || state == 0b111111111) {
                return cnt;
            }

            for(int ch : change) {
                int nextState = state ^ ch;
                if(!visited.contains(nextState)) {
                    visited.add(nextState);
                    queue.add(new int[]{nextState, cnt + 1});
                }
            }
        }

        return -1;
    }
}

// 해당 문제는 총 9개이고 이에 대해 한줄마다 XOR 연산을 해주면 된다 생각했는데 이진법으로 표현하고 사용하는데 경험이 없어 해당 부분 GPT 참조
// 또한 최소 연산 횟수를 기록해야 하므로 bfs로 반복하고 SET을 사용하여 중복 확인 후 queue 에 삽입
// 메모리 : 14124kb, 시간 104ms