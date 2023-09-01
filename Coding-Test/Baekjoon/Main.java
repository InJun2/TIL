import java.io.*;
import java.util.Arrays;

public class Main { // 백준 입출력을 위한 기본 폼
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main (String[] args) throws IOException, NumberFormatException {
        String input = read();

        String output = Solution.solution(getIntegerTokenizer(input));

        write(output);

        close();
    }


    private static String read() throws IOException {
        return br.readLine();
    }

    private static void write(String output) throws IOException {
        wr.write(output);
    }

    private static void close() throws IOException {
        br.close();
        wr.close();
    }

    private static String[] getStringTokenizer(String str) {
        return str.split(" ");
    }

    private static int[] getIntegerTokenizer(String str) {
        return Arrays.stream(str.split( " "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static String returnString(String[] val) {
        for(String str : val) {
            sb.append(str);
            sb.append("\n");
        }

        return sb.toString();
    }
}
