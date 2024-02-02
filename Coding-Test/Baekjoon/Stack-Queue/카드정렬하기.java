import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N;

    public static void main(String[] args) throws IOException, NumberFormatException {
        N = Integer.parseInt(read());
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            queue.offer(Integer.parseInt(read()));
        }

        int result = 0;

        while(queue.size() > 1) {
            int counts = queue.poll() + queue.poll();
            result += counts;
            queue.offer(counts);
        }

        write(String.valueOf(result));

        close();
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
