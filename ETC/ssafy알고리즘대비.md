# SSAFY A형 알고리즘 대비

## 1. PRIM 알고리즘
- 하나의 정점에서 연결된 간선들 중에 하나씩 선택하면서 MST를 만들어 가는 방식
1. 임의 정점을 하나 선택해서 시작
2. 선택한 정점과 인접하는 정점들 중의 최소 비용이 간선이 존재하는 정점을 선택
3. 모든 정점이 선택될 때 까지 2 과정을 반복
- 우선순위큐를 활용한 Prim 알고리즘

```java
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PrimTest {
	public static void main(String[] args) {
		new PrimTest().solution();
	}
	
	public void solution() {
		Scanner sc = new Scanner(System.in);
		
		int V = sc.nextInt();
		int[][] adjMatrix = new int[V][V];
		
		boolean[] visited = new boolean[V];
		int[] minEdge = new int[V];
		
		for(int i = 0; i < V; i++) {
			for(int j = 0; j < V; j++) {
				adjMatrix[i][j] = sc.nextInt();
			}
		}
		
		Arrays.fill(minEdge, Integer.MAX_VALUE);
		minEdge[0] = 0;
		
		int cost = 0;
		int count = 0;

		// 우선순위 큐를 사용하여 가중치가 작은 순으로 정점을 선택
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
		pq.offer(new int[] {0, 0}); // {정점, 가중치}

		while (!pq.isEmpty()) {
			int[] minEdgeInfo = pq.poll();
			int minVertex = minEdgeInfo[0];
			int minWeight = minEdgeInfo[1];
			
			// 이미 방문한 정점이면 스킵
			if (visited[minVertex]) continue;

			visited[minVertex] = true;
			cost += minWeight;
			count++;

			// 현재 정점과 인접한 정점들에 대해 최소 간선 업데이트
			for (int j = 0; j < V; j++) {
				if (!visited[j] && adjMatrix[minVertex][j] > 0 && minEdge[j] > adjMatrix[minVertex][j]) {
					minEdge[j] = adjMatrix[minVertex][j];
					pq.offer(new int[] {j, minEdge[j]});
				}
			}
		}
		
		System.out.println(count == V ? cost : -1);
		sc.close();
	}
}

```

<br>

## 2. 순열/중복 순열
- 중복 순열에서 for 문이 0번 인덱스부터 시작하면 중복 순열

```java
public class Permutation {
    static int[] arr = {1,2,3,4};
    static int R = 3;
    static boolean[] visited = new boolean[4];

    public static void main(String[] args) {
        int[] perm = new int[R];

//        permute(perm, 0);
        permute2(perm, 0);
    }

    private static void permute(int[] result, int cnt) {
        if(cnt == R) {
            for(int a : result) {
                System.out.print(a + " ");
            }
            System.out.println();
            return;
        }

        for(int i = 0; i < arr.length; i++) {
            if(visited[i]) {
               continue;
            }
            result[cnt] = arr[i];
            visited[i] = true;
            permute(result, cnt + 1);
            visited[i] = false;
        }
    }

    private static void permute2(int[] result, int cnt) {
        if(cnt == R) {
            for(int a : result) {
                System.out.print(a + " ");
            }
            System.out.println();
            return;
        }

        for(int i = 0; i < arr.length; i++) {
            result[cnt] = arr[i];
            permute2(result, cnt + 1);
        }
    }
}

```

<br>

## 3. 조합/중복조합
- 해당 중복순열 for 문이 start 부터 시작하면 조합

```java
public class Combination {
    static int[] arr = {1,2,3,4};
    static int R = 3;
    static boolean[] visited = new boolean[4];

    public static void main(String[] args) {
        int[] perm = new int[R];

//        combination(perm, 0, 0);
        combination2(perm, 0, 0);
    }

    private static void combination(int[] result, int cnt, int start) {
        if(cnt == R) {
            for(int a : result) {
                System.out.print(a + " ");
            }
            System.out.println();
            return;
        }

        for(int i = start; i < arr.length; i++) {
            result[cnt] = arr[i];
            combination(result, cnt + 1, i + 1);
        }
    }

    private static void combination2(int[] result, int cnt, int start) {
        if(cnt == R) {
            for(int a : result) {
                System.out.print(a + " ");
            }
            System.out.println();
            return;
        }

        for(int i = start; i < arr.length; i++) {
            result[cnt] = arr[i];
            combination2(result, cnt + 1, i);
        }
    }
}

```

<br>

## 4. 부분집합
- 부분 집합은 모든 경우 확인이므로 현재 인덱스 true인 경우, false인 경우 각각 하고 이후 재귀

```java
public class Subset {
    static int[] arr = {1,2,3,4};
    static int R = 3;
    static boolean[] visited = new boolean[arr.length];

    public static void main(String[] args) {
        int[] perm = new int[R];

        subset(0);
    }

    private static void subset(int cnt) {
        if(cnt == arr.length) {
            for(int i = 0; i < visited.length; i++) {
                if(visited[i]) {
                    System.out.print(arr[i] + " ");
                }
            }
            System.out.println();
            return;
        }

        visited[cnt] = true;
        subset(cnt + 1);
        visited[cnt] = false;
        subset(cnt + 1);
    }
}

```

<br>

## 이후 A형 대비 알고리즘

### 1. 방향 전환

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    boolean check[][][];
    int[] target;
    int[] dr = {0, 0, 1, -1};
    int[] dc = {1, -1, 0, 0};
    int result;


    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int t = 1; t < T + 1; t++) {
            check  = new boolean[201][201][2];
            result = 0;
            st = new StringTokenizer(br.readLine());
            int startRow = Integer.parseInt(st.nextToken()) + 100;
            int startCol = Integer.parseInt(st.nextToken()) + 100;
            int endRow = Integer.parseInt(st.nextToken()) + 100;
            int endCol = Integer.parseInt(st.nextToken()) + 100;

            target = new int[]{endRow, endCol};

            bfs(startRow, startCol);

            sb.append("#").append(t).append(" ").append(result).append("\n");
        }

        System.out.println(sb.toString());
    }

    private void bfs(int row, int col) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{row, col, 0, 0});
        queue.add(new int[]{row, col, 0, 1});
        check[row][col][0] = true;
        check[row][col][1] = true;

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();

            if(curr[0] == target[0] && curr[1] == target[1]) {
                result = curr[2];
                return;
            }

            if(curr[3] == 0) {
                for(int i = 0; i < dr.length / 2; i++) {
                    int nr = curr[0] + dr[i];
                    int nc = curr[1] + dc[i];

                    if(nr < 0 || nc < 0 || nr >= 201 || nc >= 201 || check[nr][nc][0]) {
                        continue;
                    }

                    check[nr][nc][0] = true;
                    queue.add(new int[]{nr, nc, curr[2] + 1, 1});
                }
            } else {
                for(int i = dr.length / 2 ; i < dr.length; i++) {
                    int nr = curr[0] + dr[i];
                    int nc = curr[1] + dc[i];

                    if(nr < 0 || nc < 0 || nr >= 201 || nc >= 201 || check[nr][nc][1]) {
                        continue;
                    }

                    check[nr][nc][1] = true;
                    queue.add(new int[]{nr, nc, curr[2] + 1, 0});
                }
            }
        }
    }
}
```

<br>

### 2. 물놀이를 가자

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    int[] dr = {0, 0, 1, -1};
    int[] dc = {1, -1, 0, 0};
    int N, M, result;
    int[][] board;
    boolean[][] visited;
    Queue<int[]> queue;

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int t = 1; t < T + 1; t++) {
            result = 0;
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            board = new int[N][M];
            visited = new boolean[N][M];
            queue = new ArrayDeque<>();

            for(int i = 0; i < N; i++) {
                String str = br.readLine();

                for(int j = 0; j < M; j++) {
                    if(str.charAt(j) == 'W') {
                        queue.offer(new int[]{i, j, 1});
                        visited[i][j] = true;
                    }
                }
            }

            bfs();

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < M; j++) {
                    result += board[i][j];
                }
            }

            sb.append("#").append(t).append(" ").append(result).append("\n");
        }

        System.out.println(sb.toString());
    }

    private void bfs() {
        while(!queue.isEmpty()) {
            int[] pos = queue.poll();
            int row = pos[0];
            int col = pos[1];
            int cnt = pos[2];

            for(int i = 0; i < dr.length; i++) {
                int mr = row + dr[i];
                int mc = col + dc[i];

                if(mr < 0 || mr >= N || mc < 0 || mc >= M || visited[mr][mc]) {
                    continue;
                }

                visited[mr][mc] = true;
                board[mr][mc] = cnt;
                queue.offer(new int[]{mr, mc, cnt + 1});
            }
        }
    }
}
```

<br>

### 3. 혁진이의 프로그램 검증

```java
import java.io.*;
import java.util.*;
 
public class Solution {
	static int[] di={-1,1,0,0};//상0,하1,좌2,우3
    static int[] dj={0,0,-1,1};
    static int R,C,mem;
    static char[][] map;
    static boolean[][][][] v;
    static String ans;
    static boolean end;    
    
    static void bfs(int i,int j,int dir){
    	ArrayDeque<int[]> q=new ArrayDeque<>();
    	q.offer(new int[]{i,j,dir,0});
    	while(!q.isEmpty()){
    		int[] ij=q.poll();
    		i=ij[0]; j=ij[1]; dir=ij[2]; int mem=ij[3];
    		
    		if(i<0 ) i=R-1; if(j<0)  j=C-1;
    		if(i>=R) i=0;   if(j>=C) j=0;
    		
    		if(v[i][j][dir][mem]) continue;
    		v[i][j][dir][mem]=true;
    		switch(map[i][j]){
    			case '<': dir=2; break;//좌2
    			case '>': dir=3; break;//우3
    			case '^': dir=0; break;//상0
    			case 'v': dir=1; break;//하1
    			case '_': dir=mem==0? 3:2; break;//우3:좌2
    			case '|': dir=mem==0? 1:0; break;//하1:상0
    			case '?': break;//밑에서 4방으로
    			case '.': break;
    			case '@': ans="YES"; return;//정답
    			case '+': mem=(mem==15? 0:mem+1); break;
    			case '-': mem=(mem==0? 15:mem-1); break;
    			default : mem=map[i][j]-'0'; break; // '0'~'9' -> int
    		}
    		if(map[i][j]=='?'){
    			for(int d=0; d<4; d++){
    				int ni=i+di[d];
    				int nj=j+dj[d];
    				q.offer(new int[]{ni,nj,d,mem});
    			}
    		}else{
    			int ni=i+di[dir];
    			int nj=j+dj[dir];
    			q.offer(new int[]{ni,nj,dir,mem});
    		}
    	}
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st=null;
        StringBuilder sb=new StringBuilder();
        int T=Integer.parseInt(br.readLine());
        for(int tc=1; tc<=T; tc++) {
        	st=new StringTokenizer(br.readLine()," ");
        	R=Integer.parseInt(st.nextToken());
        	C=Integer.parseInt(st.nextToken());
        	map=new char[R][C];
        	v=new boolean[R][C][4][16];
        	end=false;
        	for(int i=0; i<R; i++){
        		String s=br.readLine();
        		for(int j=0; j<C; j++){
        			map[i][j]=s.charAt(j);
        			if(map[i][j]=='@') end=true;
        		}
        	}
        	mem=0; ans="NO";
        	if(end) bfs(0,0,3);
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }    
}
```

<br>

### 4. 무합 로봇

```java
import java.io.*;
import java.util.*;
 
public class Solution {
	public static void main(String[] args) throws Exception{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb=new StringBuilder();
        int T=Integer.parseInt(br.readLine());
        for(int tc=1; tc<=T; tc++){
            int x=0,y=0,dir=0,max=0;
            String cmd=br.readLine();
            for(int d=0; d<4; d++){
                for(int c=0; c<cmd.length(); c++){
                	char C=cmd.charAt(c);
                          if(C=='L'){
                        dir--; if(dir<0) dir=3;
                    }else if(C=='R'){
                        dir++; if(dir>3) dir=0;
                    }else if(C=='S'){
                        switch(dir){//우0하1좌2상3
	                        case 0: x++; break;//우0
	                        case 1: y--; break;//하1
	                        case 2: x--; break;//좌2
	                        case 3: y++; break;//상3
                        } 
                        max=Math.max(max,x*x+y*y);
                    }
                }
            } 
            sb.append("#").append(tc).append(" ").append((x==0&&y==0)? max:"oo").append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}
```

<br>

### 5. 햄스터

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    private int N, X, M;
    private int[] cage;
    private int[][] record;
    private int[] maxCage;
    private boolean foundSolution;
    private int maxHamster;

    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            cage = new int[N + 1];
            foundSolution = false;
            maxCage = new int[N + 1];
            record = new int[M][3];
            maxHamster = -1;

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                record[i][0] = Integer.parseInt(st.nextToken());
                record[i][1] = Integer.parseInt(st.nextToken());
                record[i][2] = Integer.parseInt(st.nextToken());
            }

            // 백트래킹을 통해 배열을 채우기 시작
            backtrack(N, 0);

            sb.append("#").append(t).append(" ");
            if (foundSolution) {
                for (int i = 1; i <= N; i++) {
                    sb.append(maxCage[i]).append(" ");
                }
            } else {
                sb.append(-1);
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    // 백트래킹 메서드
    private void backtrack(int index, int curr) {
        // 마지막까지 채운 경우 모든 조건을 검사
        if (index == 0) {
            if (checkConditions() && curr > maxHamster) {
                foundSolution = true;
                maxHamster = curr;
                for(int i = 1; i <= N; i++) {
                    maxCage[i] = cage[i];
                }
            }
            return;
        }

        // 가능한 모든 값 할당 (0부터 X까지)
        for (int i = X; i > -1; i--) {
            cage[index] = i;
            backtrack(index - 1, curr + i);
        }
    }

    // 주어진 조건을 확인하는 메서드
    private boolean checkConditions() {
        for (int[] condition : record) {
            int start = condition[0];
            int end = condition[1];
            int requiredSum = condition[2];

            int sum = 0;
            for (int i = start; i <= end; i++) {
                sum += cage[i];
            }

            if (sum != requiredSum) {
                return false;
            }
        }

        return true;
    }
}

```