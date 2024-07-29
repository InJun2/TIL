// 첫번 째 구현 --> 회전에 맞춰 이동하고 남아있는 부분을 2로 하고 head를 통해 이동하는데 꼬리를 처리할 수 없었음

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.StringTokenizer;

// public class Main {
//     static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//     static int[][] board;

//     public static void main(String[] args) throws IOException {
//         int[] dx = {1, -1, 0, 0};
//         int[] dy = {0, 0, 1, -1};
//         String[] dir = {"E", "S", "W", "N"};    // 동, 남, 서, 북
//         int current = 0;
//         int time = 0;

//         Integer N = Integer.parseInt(br.readLine());
//         StringTokenizer st;
//         board = new int[N][N];
//         Map<Integer, String> map = new HashMap<>();
//         int[] head =  new int[]{0, 0};

//         Integer K = Integer.parseInt(br.readLine());
//         for(int i = 0; i < K; i++) {
//             st = new StringTokenizer(br.readLine());
//             int a = Integer.parseInt(st.nextToken());
//             int b = Integer.parseInt(st.nextToken());
//             board[a-1][b-1] = 1;
//         }

//         Integer L = Integer.parseInt(br.readLine());
//         for(int i = 0; i < L; i ++) {
//             st = new StringTokenizer(br.readLine());
//             int a = Integer.parseInt(st.nextToken());
//             String b = st.nextToken();
//             map.put(a, b);
//         }

//         while(true) {
//             time++;
//             if(map.containsKey(time)) {
//                 switch (map.get(time)) {
//                     case "D" -> {
//                         current = (current + 4 + 1) % 4;
//                     }
//                     case "L" -> {
//                         current = (current + 4 - 1) % 4;
//                     }
//                 }
//             }

//             switch (dir[current]) {
//                 case "E" -> {
//                     head[0] = head[0] + dx[0];
//                     head[1] = head[1] + dy[0];
//                 }
//                 case "S" -> {
//                     head[0] = head[0] + dx[3];
//                     head[1] = head[1] + dy[3];
//                 }
//                 case "W" -> {
//                     head[0] = head[0] + dx[1];
//                     head[1] = head[1] + dy[1];
//                 }
//                 case "N" -> {
//                     head[0] = head[0] + dx[2];
//                     head[1] = head[1] + dy[2];
//                 }
//             }

//             if(head[0] < 0 || head[1] < 0 || head[0] >= N || head[1] >= N) {
//                 System.out.println(time);
//                 break;
//             }

//             if(board[head[0]][head[1]] == 2) {
//                 System.out.println(time);
//                 break;
//             }

//             if(board[head[0]][head[1]] == 1) {
//                 board[head[0]][head[1]] = 2;
//             }
//         }

//     }
// }


// 이후 풀이 키워드에 대해 GPT를 참조하여 해당 코드에서 Queue 를 사용하여 다시 풀이
// Queue로 구현하면 peek을 통해 머리가 갈 위치를 파악하고 사과가 없어 꼬리를 빼야할 때 poll을 하면 되므로
// Queue 구현에서 자꾸 동서남북 좌표가 잘못되고 Test3 을 통과 못해서 해답 참조

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] board;

    public static void main(String[] args) throws IOException {
        int[] dx = {0, 1, 0, -1}; // 동, 남, 서, 북
        int[] dy = {1, 0, -1, 0};
        int current = 0;
        int time = 0;

        Integer N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        board = new int[N][N];
        Map<Integer, String> map = new HashMap<>();
        Deque<int[]> snake = new LinkedList<>();
        snake.add(new int[]{0, 0});
        board[0][0] = 2;

        Integer K = Integer.parseInt(br.readLine());
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            board[a - 1][b - 1] = 1;
        }

        Integer L = Integer.parseInt(br.readLine());
        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            String b = st.nextToken();
            map.put(a, b);
        }

        while (true) {
            time++;

            int[] head = snake.peekFirst();
            int nx = head[0] + dx[current];
            int ny = head[1] + dy[current];

            if (nx < 0 || ny < 0 || nx >= N || ny >= N || board[nx][ny] == 2) {
                System.out.println(time);
                break;
            }

            if (board[nx][ny] != 1) {
                int[] tail = snake.pollLast();
                board[tail[0]][tail[1]] = 0;
            }

            snake.addFirst(new int[]{nx, ny});
            board[nx][ny] = 2;

            if (map.containsKey(time)) {
                switch (map.get(time)) {
                    case "D":   // 백준은 자바 11버전으로 향상된 switch 문이 안되서 삽질함..
                        current = (current + 4 + 1) % 4;
                        break;
                    case "L":
                        current = (current + 4 - 1) % 4;
                        break;
                }
            }
        }
    }
}