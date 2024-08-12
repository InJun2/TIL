// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.util.LinkedList;
// import java.util.Queue;
// import java.util.StringTokenizer;

// class Solution {     // 첫번째 풀이 후 GPT 수정 코드 코드 실패
//     private static final int START = 180;  // 중앙에 가깝게 시작하도록 설정
//     static int[][] grid;
//     static int[] dx = {1, -1, 0, 0};
//     static int[] dy = {0, 0, 1, -1};

//     public static void main(String args[]) throws Exception {
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//         StringBuilder sb = new StringBuilder();
//         Queue<int[]> queue;

//         int T = Integer.parseInt(br.readLine());

//         for (int t = 0; t < T; t++) {
//             queue = new LinkedList<>();
//             StringTokenizer st = new StringTokenizer(br.readLine());
//             int N = Integer.parseInt(st.nextToken());
//             int M = Integer.parseInt(st.nextToken());
//             int K = Integer.parseInt(st.nextToken());

//             grid = new int[360][360];

//             for (int i = 0; i < N; i++) {
//                 st = new StringTokenizer(br.readLine());
//                 for (int j = 0; j < M; j++) {
//                     int life = Integer.parseInt(st.nextToken());
//                     if (life > 0) {
//                         int x = START + i;
//                         int y = START + j;
//                         grid[x][y] = life;
//                         queue.add(new int[]{x, y, life * 2, life + 1}); 
//                     }
//                 }
//             }

//             for (int time = 1; time <= K; time++) {
//                 int size = queue.size();
//                 for (int i = 0; i < size; i++) {
//                     if (queue.isEmpty()) break;  // 큐가 비어 있는지 확인

//                     int[] cell = queue.poll();
//                     if (cell == null) continue;  // 큐에서 null을 반환했는지 확인

//                     int x = cell[0];
//                     int y = cell[1];
//                     int deathTime = cell[2];
//                     int activeTime = cell[3];

//                     if (time == activeTime) {
//                         for (int d = 0; d < 4; d++) {
//                             int nx = x + dx[d];
//                             int ny = y + dy[d];
//                             if (nx >= 0 && ny >= 0 && nx < 360 && ny < 360 && grid[nx][ny] == 0) {  // 범위 체크 추가
//                                 grid[nx][ny] = grid[x][y];
//                                 queue.add(new int[]{nx, ny, time + grid[x][y] + 1, time + grid[x][y]});
//                             } else if (nx >= 0 && ny >= 0 && nx < 360 && ny < 360 && grid[nx][ny] < grid[x][y]) {
//                                 grid[nx][ny] = grid[x][y];
//                             }
//                         }
//                     }

//                     if (deathTime > time) {
//                         queue.add(cell);
//                     }
//                 }
//             }

//             int aliveCells = 0;
//             while (!queue.isEmpty()) {
//                 queue.poll();
//                 aliveCells++;
//             }

//             sb.append("#").append(t + 1).append(" ").append(aliveCells).append("\n");
//         }

//         System.out.println(sb);
//     }
// }

// 아래는 보충 수업 코드

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

    static class Cell {
        int r;
        int c;
        int originVital;
        int curVital;
    }

    // 사방탐색이므로..
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static boolean[][] visited;
    static PriorityQueue<int[]> pq; // 행,열, 본래생명력, 현재남은생명력.

    static int N, M, K, size_N, size_M, start;
    // N, M, K : 입력받은 것
    // size_N, size_M, start: 계산된 가상의 2차원배열.


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc=1; tc<=T; tc++) {
            // 각 테스트케이스의 초기화는 여기에서.
            N = sc.nextInt();
            M = sc.nextInt();
            K = sc.nextInt();

            size_N = N + K + 2; // 양 옆으로 K/2만큼 뻗어나갈 수 있다.
            // 1초후 비활성.
            // 2초후 분열..

            size_M = M + K + 2;
            start = K/2 + 1;

            visited = new boolean[size_N][size_M];
            pq = new PriorityQueue<int[]>((o1, o2)->Integer.compare(o2[2], o1[2]));

            PriorityQueue<Cell> pq2 = new PriorityQueue<Cell>(new Comparator<Cell>() {

                @Override
                public int compare(Cell o1, Cell o2) {
                    // TODO Auto-generated method stub
                    return o2.originVital - o1.originVital;
                }
            });
            // 배열의 세번째 원소의 크기 순으로(내림차순)
            // 현재 살아있는 줄기세포들을 생명력 순서대로 저장.


            // 초기상태 입력 받기.
            for(int r=0; r<N; r++) {
                for(int c=0; c<M; c++) {
                    int num = sc.nextInt();
                    if(num != 0) {
                        visited[r+start][c+start] = true;
                        pq.offer(new int[] {r+start, c+start, num, num});

                    }
                }
            }

            //pq에 대해 K시간 만큼 시물레이션 수행.
            simulation();
            // 결과 출력.

            System.out.println("#"+tc+" "+pq.size());



        }



    }
    private static void simulation() {
        Queue<int[]> q = new LinkedList<int[]>(); // 배양 후보들의 임시 저장소.


        // K 시간만큼 시물레이션 수행
        for( int k=0; k<K; k++) {
            // pq를 돌면서(pq에 있는 모든 원소를 한번씩 순회)
            while(!pq.isEmpty()) {
                int[] cell = pq.poll();
                cell[3]--; // 현재 생명력을 감소.
                // 현재 생명력이 음수 => 생명력 만큼 시간이 지나서
                // 활성화 상태가 되었음
                if(cell[3] < 0) {
                    // 상하좌우로 배양.
                    for(int d=0; d<4; d++) {
                        int nr = cell[0] + dr[d];
                        int nc = cell[1] + dc[d];
                        if(visited[nr][nc]) continue; //이미 선점된 위치라면, 다음 위치를 보러감
                        visited[nr][nc] = true;
                        q.offer(new int[] {nr, nc, cell[2], cell[2]}); // 해당 위치 추
                    }
                }

                if(cell[2]+cell[3] == 0) continue; // 만약 세포가 죽었다면 더이상 pq에 넣지 않는다.
                q.offer(cell);
            }

            while(!q.isEmpty()) pq.offer(q.poll());


            //, 만약, 배양을 할 수 있다면 배양을 한다.
            // 죽었다면 pq에서 빼내면된다.

            // 배양을 할 때, 모든 애들이 다 배양되는 것은 아님
            // => 임시 리스트를 만들어서 배양되는 후보를 먼저 저장해둔다.



        }

    }

}