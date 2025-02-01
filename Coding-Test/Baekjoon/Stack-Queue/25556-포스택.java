import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    Stack<Integer>[] stacks = new Stack[4];

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < 4; i++) {
            stacks[i] = new Stack<>();
        }

        int used = 0;

        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());

            int index = -1;
            int max = Integer.MIN_VALUE;

            for (int j = 0; j < used; j++) {
                if (!stacks[j].isEmpty()) {
                    int topVal = stacks[j].peek();
                    if (topVal < num && topVal > max) {
                        max = topVal;
                        index = j;
                    }
                }
            }

            if (index == -1) {
                if (used == 4) {
                    System.out.println("NO");
                    return;
                } else {
                    stacks[used].push(num);
                    used++;
                }
            } else {
                stacks[index].push(num);
            }
        }

        System.out.println("YES");
    }
}
