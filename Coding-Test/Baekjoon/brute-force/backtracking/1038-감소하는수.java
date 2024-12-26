import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    List<Long> list = new ArrayList<>();
    int N;

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        if(N <= 10) {
            System.out.println(N);
            return;
        }
        if (N >= 1023) {
            System.out.println(-1);
            return;
        }

        for(int i = 0; i < 10; i++) {
            dfs(i);
        }

        Collections.sort(list);
        System.out.println(list.get(N));
    }

    private void dfs(long num) {
        list.add(num);
        long digit = num % 10;

        if(digit == 0) {
            return;
        }

        for(long i = digit -1; i >= 0; i--) {
            long newValue = num * 10 + i;
            dfs(newValue);
        }
    }
}

// 어떻게 값을 구해야할지 모르겠어서 블로그 참조..
// 10 이하의 한자리 수 일 경우 모두 감소하는 값이 없으므로 그대로 N이 값이됨
// long을 안해서 시행착오가 늘었음..
// 메모리 14464 kb, 시간 104ms