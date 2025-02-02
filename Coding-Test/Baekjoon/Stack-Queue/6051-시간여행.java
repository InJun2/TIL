// import java.io.*;
// import java.util.*;

// public class Main {
//     public static void main(String[] args) throws IOException {
//         new Main().solution();
//     }

//     Stack<int[]> stack = new Stack<>();
//     int N;

//     public void solution() throws IOException {
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//         StringBuilder sb = new StringBuilder();
//         StringTokenizer st;

//         N = Integer.parseInt(br.readLine());

//         for (int i = 0; i < N; i++) {
//             st = new StringTokenizer(br.readLine());

//             char ch = st.nextToken().charAt(0);

//             switch (ch) {
//                 case 'a': {
//                     int a = Integer.parseInt(st.nextToken());

//                     stack.push(new int[]{i + 1, a});
//                     sb.append(stack.peek()[1]).append("\n");
//                     break;
//                 }
//                 case 't': {
//                     int a = Integer.parseInt(st.nextToken());

//                     while(a != stack.peek()[0]) {
//                         stack.pop();
//                     }

//                     sb.append(stack.pop()[1]).append("\n");
//                     break;
//                 }
//                 case 's': {
//                     if(stack.isEmpty()) {
//                         sb.append(-1).append("\n");
//                         break;
//                     }

//                     sb.append(stack.pop()[1]).append("\n");
//                 }
//             }

//             System.out.println(stack.peek()[1] + "  " + i);
//         }

//         System.out.print(sb);
//     }
// }
//
// 시간 이동의 경우 해당 스택의 상태를 불러오는데 실패
//
import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static Stack<Integer>[] saved;
    static Stack<Integer> current;

    public static void main(String[] args) throws IOException {
        new Main().solve();
    }

    void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        saved = new Stack[N + 1];
        current = new Stack<>();
        saved[0] = cloneStack(current);

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            char cmd = st.nextToken().charAt(0);

            if (cmd == 'a') {
                int x = Integer.parseInt(st.nextToken());
                current.push(x);
                sb.append(x).append("\n");
            } else if (cmd == 's') {
                if (current.isEmpty()) {
                    sb.append(-1).append("\n");
                } else {
                    current.pop();

                    if (current.isEmpty()) {
                        sb.append(-1).append("\n");
                    } else {
                        sb.append(current.peek()).append("\n");
                    }
                }
            } else if (cmd == 't') {
                int A = Integer.parseInt(st.nextToken());
                current = cloneStack(saved[A - 1]);
                if (current.isEmpty()) {
                    sb.append(-1).append("\n");
                } else {
                    sb.append(current.peek()).append("\n");
                }
            }

            saved[i] = cloneStack(current);
        }

        System.out.print(sb);
    }

    Stack<Integer> cloneStack(Stack<Integer> origin) {
        Stack<Integer> temp = new Stack<>();
        Stack<Integer> reverse = new Stack<>();
        while (!origin.isEmpty()) {
            reverse.push(origin.pop());
        }
        while (!reverse.isEmpty()) {
            int val = reverse.pop();
            origin.push(val);
            temp.push(val);
        }
        return temp;
    }
}

// 메모리 초과