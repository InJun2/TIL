import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main (String[] args) throws IOException, NumberFormatException {
        Queue<Integer> queue = new LinkedList<>();

        String[] str = read().split(" ");
        int people = Integer.parseInt(str[0]);
        int k = Integer.parseInt(str[1]);

        for(int i=1; i<people+1; i++) {
            queue.add(i);
        }

        sb.append("<");

        while(!(queue.size() ==1)) {
            for(int i=0; i<k-1; i++) {
                queue.add(queue.poll());
            }
            sb.append(queue.poll() + ", ");
        }

        sb.append(queue.poll() + ">");

        write(sb.toString());
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
