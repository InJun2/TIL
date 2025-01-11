import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N, M;
    List<Character> list = new ArrayList<>();
    Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
    StringBuilder sb = new StringBuilder();

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i ++) {
            list.add(st.nextToken().charAt(0));
        }

        Collections.sort(list);

        subset(0, 0, new char[N]);
        System.out.println(sb);
    }

    private void subset(int index, int depth, char[] chars) {
        if(depth == N) {
            if(validate(chars)) {
                sb.append(new String(chars)).append("\n");
            }

            return;
        }

        for(int i = index; i < M; i ++) {
            chars[depth] = list.get(i);
            subset(i + 1, depth + 1, chars);
        }
    }

    private boolean validate(char[] chars) {
        int vowelsNum = 0;

        for(char c : chars) {
            if(vowels.contains(c)) {
                vowelsNum++;
            }
        }

        if(vowelsNum == 0 || N - vowelsNum < 2) {
            return false;
        }

        return true;
    }
}

// 메모리 14964kb, 시간 108ms