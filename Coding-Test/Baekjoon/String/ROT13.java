import java.io.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException, NumberFormatException {
        String s = read();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isLetter(c)) {
                sb.append(c);
                continue;
            }

            int n = (int)c + 13;

            if (Character.isUpperCase(c) && n > 90) {
                n -= 26;
            }

            if (Character.isLowerCase(c) && n > 122) {
                n -= 26;
            }

            sb.append((char)n);
        }

        write(String.valueOf(sb));

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
