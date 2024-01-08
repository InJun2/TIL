import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException, NumberFormatException {
        String s = read();
        List<String> list = new ArrayList<>();

        for(int i=0; i < s.length(); i++) {
            list.add(s.substring(i, s.length()));
        }

        Collections.sort(list);
        for(String str : list) {
            sb.append(str + "\n");
        }

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
