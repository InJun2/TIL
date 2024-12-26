import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    final static int START = 100;
    boolean[] failNum = new boolean[10];
    int N;
    int minCnt = 0;

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        int t = Integer.parseInt(br.readLine());

        if(t != 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < t; i++) {

                int number = Integer.parseInt(st.nextToken());
                failNum[number] = true;
            }
        }

        minCnt = Math.abs(N - START);

        for(int i = 0; i <= 1000000; i++) {
            int startCount = isPossible(i);
            if (startCount > 0) {
                int cnt = Math.abs(N - i) + startCount;
                minCnt = Math.min(minCnt, cnt);
            }
        }

        System.out.println(minCnt);
    }

    private int isPossible(int number) {
        String str = String.valueOf(number);
        for(char ch : str.toCharArray()) {
            if(failNum[ch - '0']) {
                return 0;
            }
        }

        return str.length();
    }
}

// 기존 가까운 값이 뭔지 구하고 여기서 +, - 하는 방법을 못찾았는데 GPT를 통해 가능한 시작번호를 모두 탐색해도 된다고 확인 후 구현
// t가 0인 경우 런타임 에러 발생하여 if 문으로 선처리
// 메모리 74660kb, 시간 252ms