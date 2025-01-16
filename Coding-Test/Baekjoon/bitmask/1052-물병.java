import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N, M;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int result = -1;

        String binary = Integer.toBinaryString(N);

        for(int i = 0; ; i++) {
            if(Integer.bitCount(N) <= M) {
                result = i;
                break;
            }

            N += 1;
        }

        System.out.println(result);
    }
}
