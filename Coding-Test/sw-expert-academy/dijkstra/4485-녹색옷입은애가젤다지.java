// 첫번째 BFS 코드, 제약 사항 메모리가 아슬아슬하지만 되긴 했음

/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }
    
    int N;
    int[][] board;
    int[][] checked;
    
    int[] dr = {1, -1, 0, 0};
    int[] dc = {0, 0, 1, -1}; 
    
    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = 1;
        
        while(true) {
            N = Integer.parseInt(br.readLine());
            
            if(N == 0) {
                break;
            }
            
            board = new int[N][N];
            checked = new int[N][N];
            
            for(int i = 0; i < N; i++) {
                Arrays.fill(checked[i], Integer.MAX_VALUE);                
            }
            
            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                
                for(int y = 0; y < N; y++) {
                    board[i][y] = Integer.parseInt(st.nextToken());
                }
            }
            
            bfs();
            
            System.out.println("Problem " + t + ": " + checked[N -1][N -1]);
            t++;
        }
    }
    
    private void bfs() {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] {0, 0, board[0][0]});
        checked[0][0] = board[0][0];
        
        while(!queue.isEmpty()) {
            int[] lo = queue.poll();
            
            for(int i = 0; i < dr.length; i++) {
                int moveRow = lo[0] + dr[i];
                int moveCol = lo[1] + dc[i];
                
                if(!valid(moveRow, moveCol)) {
                    continue;
                }
                
                int newCost = lo[2] + board[moveRow][moveCol];
                if (newCost < checked[moveRow][moveCol]) {
                    checked[moveRow][moveCol] = newCost;
                    queue.offer(new int[] {moveRow, moveCol, newCost});
                }
            }
        }
    }
     
    private boolean valid(int row, int col) {
        return row >= 0 && col >= 0 && row < N && col < N;
    }
}

*/

// 이후 다익스트라 변경 코드

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }
    
    int N;
    int[][] board;
    int[][] checked;
    
    int[] dr = {1, -1, 0, 0};
    int[] dc = {0, 0, 1, -1}; 
    
    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int t = 1;
        
        while(true) {
            N = Integer.parseInt(br.readLine());
            
            if(N == 0) {
                break;
            }
            
            board = new int[N][N];
            checked = new int[N][N];
            
            for(int i = 0; i < N; i++) {
                Arrays.fill(checked[i], Integer.MAX_VALUE);                
            }
            
            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                
                for(int y = 0; y < N; y++) {
                    board[i][y] = Integer.parseInt(st.nextToken());
                }
            }
            
            dijkstra();
            
            sb.append("Problem ")
            	.append(t)
            	.append(": ")
            	.append(checked[N -1][N -1])
            	.append("\n");
            t++;
        }
        
        System.out.println(sb.toString());
    }
    
    private void dijkstra() {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));
        pq.offer(new int[] {0, 0, board[0][0]});
        checked[0][0] = board[0][0];
        
        while(!pq.isEmpty()) {
            int[] lo = pq.poll();
            int row = lo[0];
            int col = lo[1];
            int cost = lo[2];
            
            for(int i = 0; i < dr.length; i++) {
                int moveRow = row + dr[i];
                int moveCol = col + dc[i];
                
                if(!valid(moveRow, moveCol)) {
                    continue;
                }
                
                int newCost = cost + board[moveRow][moveCol];
                
                if (newCost < checked[moveRow][moveCol]) {
                    checked[moveRow][moveCol] = newCost;
                    pq.offer(new int[] {moveRow, moveCol, newCost});
                }
            }
        }
    }
     
    private boolean valid(int row, int col) {
        return row >= 0 && col >= 0 && row < N && col < N;
    }
}
