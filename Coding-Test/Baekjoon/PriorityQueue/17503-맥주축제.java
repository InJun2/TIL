import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N, M, K;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        if (N > K) {
            System.out.println(-1);
            return;
        }

        Beer[] beers = new Beer[K];
        long totalPreference = 0;

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            long p = Long.parseLong(st.nextToken());
            long l = Long.parseLong(st.nextToken());
            beers[i] = new Beer(p, l);
            totalPreference += p;
        }

        if (totalPreference < M) {
            System.out.println(-1);
            return;
        }

        Arrays.sort(beers, (a, b) -> Long.compare(a.level, b.level));

        PriorityQueue<Long> pq = new PriorityQueue<>();
        long sum = 0;
        long result = Long.MAX_VALUE;

        for (Beer b : beers) {
            pq.offer(b.preference);
            sum += b.preference;
            if (pq.size() > N) {
                sum -= pq.poll();
            }
            if (pq.size() == N && sum >= M) {
                result = Math.min(result, b.level);
            }
        }

        System.out.println(result == Long.MAX_VALUE ? -1 : result);
    }

    static class Beer {
        long preference;
        long level;

        public Beer(long preference, long level) {
            this.preference = preference;
            this.level = level;
        }
    }
}
