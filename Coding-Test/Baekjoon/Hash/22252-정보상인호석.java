import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        Map<String, PriorityQueue<Integer>> map = new HashMap<>();
        N = Integer.parseInt(st.nextToken());
        long result = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int cases = Integer.parseInt(st.nextToken());
            String name = st.nextToken();
            int num = Integer.parseInt(st.nextToken());

            map.putIfAbsent(name, new PriorityQueue<>(Collections.reverseOrder()));
            PriorityQueue<Integer> pq = map.get(name);

            if(cases == 1) {
                for(int j = 0; j < num; j++) {
                    int value = Integer.parseInt(st.nextToken());
                    pq.offer(value);
                }
            } else if(cases == 2) {
                for(int j = 0; j < num; j++) {
                    if(pq.isEmpty()) {
                        break;
                    }

                    result += pq.poll();
                }
            }
            map.put(name, pq);
        }

        System.out.println(result);
    }
}
