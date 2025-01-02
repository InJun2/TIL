import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N, M;
    int minSix = Integer.MAX_VALUE, minOne = Integer.MAX_VALUE;
    int result = 0;

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for(int t = 0; t < M; t++) {
            st = new StringTokenizer(br.readLine());
            int six = Integer.parseInt(st.nextToken());
            int one = Integer.parseInt(st.nextToken());

            minSix = Math.min(minSix, six);
            minOne = Math.min(minOne, one);
        }

        if(minOne * 6 <= minSix) {
            result = minOne * N;
            System.out.println(result);
            System.exit(0);
        }

        int lastN = N;
        while(lastN > 6) {
            lastN -= 6;
            result += minSix;
        }

        if(lastN * minOne <= minSix) {
            result += minOne * lastN;
        } else {
            result += minSix;
        }

        System.out.println(result);
    }
}

// 생각나는대로
// 메모리 16200kb, 시간 136ms