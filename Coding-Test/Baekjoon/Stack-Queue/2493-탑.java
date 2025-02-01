// import java.io.*;
// import java.util.*;

// public class Main {
//     public static void main(String[] args) throws IOException {
//         new Main().solution();
//     }

//     Stack<int[]> stack = new Stack<>();
//     Stack<Integer> result = new Stack<>();
//     int N;

//     public void solution() throws IOException {
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//         StringTokenizer st;
//         StringBuilder sb = new StringBuilder();

//         N = Integer.parseInt(br.readLine());
//         st = new StringTokenizer(br.readLine());

//         for(int i = 0; i < N; i++) {
//             int height = Integer.parseInt(st.nextToken());
//             stack.push(new int[]{height, i + 1});
//         }

//         swapStack(stack);

//         while (!result.isEmpty()) {
//             sb.insert(0, result.pop() + " ");
//         }

//         System.out.println(sb.toString().trim());
//     }

//     private void swapStack(Stack<int[]> stack) {
//         if (stack.isEmpty()) {
//             return;
//         }

//         int[] curr = stack.pop();

//         swapStack(stack);

//         while (!stack.isEmpty() && stack.peek()[0] <= curr[0]) {
//             stack.pop();
//         }

//         if (!stack.isEmpty()) {
//             result.push(stack.peek()[1]);
//         } else {
//             result.push(0);
//         }

//         stack.push(curr);
//     }
// }
//
// 처음 접근한 방법은 스택을 다른 스택에 넣으면서 다른 스택에 존재하는 스택은 해당 높이보다 작은 탑들 이므로 해당 큐로 재귀
// 그러나 재귀를 마치고 남은 스택을 복원, 그런데 시간 초과 발생
//
//

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    Stack<int[]> stack = new Stack<>();

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] answer = new int[N];

        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < N; i++){
            int height = Integer.parseInt(st.nextToken());

            while(!stack.isEmpty() && stack.peek()[0] <= height) {  // 애초에 해당 높이보다 작은 stack들은 필요없음. 이후 탑들은 해당 탑을 먼저 참조하기 때문
                stack.pop();
            }

            if(stack.isEmpty()){
                answer[i] = 0;
            }
            else {
                answer[i] = stack.peek()[1] + 1;
            }

            stack.push(new int[]{height, i});
        }

        for(int i : answer){
            sb.append(i).append(" ");
        }

        System.out.println(sb.toString().trim());
    }
}

