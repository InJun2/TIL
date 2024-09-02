// 이전 코드 <- 해당 코드는 방문처리가 안돼 런타임 에러 발생

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.StringTokenizer;

// public class Main {

//     public static void main(String[] args) throws IOException {
//         new Main().solution();
//     }
    
//     class Edge {
//     	public int target, weight;
    	
//     	public Edge(int target, int weight) {
//     		this.target = target;
//     		this.weight = weight;
//     	}
//     }
    
//     int N, M;
//     List<List<Edge>> list = new ArrayList<>();
//     int[] minPath;
    
//     private void solution() throws IOException {
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//         StringTokenizer st = new StringTokenizer(br.readLine());

//         N = Integer.parseInt(st.nextToken());	// 정점의 개수
//         M = Integer.parseInt(st.nextToken());	// 간선의 개수
//         int start = Integer.parseInt(br.readLine()); 	// 시작 정점
//         minPath = new int[N + 1];
//         Arrays.fill(minPath, Integer.MAX_VALUE);
        
//         for(int i = 0; i < N + 1; i++) {
//         	list.add(new ArrayList<>());
//         }
        
//         for(int i = 0; i < M; i++) {
//         	st = new StringTokenizer(br.readLine());
        	
//         	int from = Integer.parseInt(st.nextToken());
//         	int to = Integer.parseInt(st.nextToken());
//         	int path = Integer.parseInt(st.nextToken());
        	
//         	list.get(from).add(new Edge(to, path));
//         }
        
//         minPath[start] = 0;
//         repeat(list.get(start), 0);
        
//         for(int i = start; i < N + 1; i ++) {
//         	System.out.println(minPath[i] != Integer.MAX_VALUE ? minPath[i] : "INF");
//         }
//     }

// 	private void repeat(List<Edge> list2, int beforeWeight) {
// 		for(Edge edge : list2) {
// 			if(edge.weight < minPath[edge.target]) {
// 				minPath[edge.target] = Math.min(minPath[edge.target], edge.weight + beforeWeight);
// 				repeat(list.get(edge.target), minPath[edge.target]);				
// 			} else {
// 				return;
// 			}
// 		}
// 	}
// }


// 수정 코드

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }
    
    class Edge {
    	public int target, weight;
    	
    	public Edge(int target, int weight) {
    		this.target = target;
    		this.weight = weight;
    	}
    }
    
    int N, M;
    List<List<Edge>> list = new ArrayList<>();
    int[] minPath;
    
    private void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());	// 정점의 개수
        M = Integer.parseInt(st.nextToken());	// 간선의 개수
        int start = Integer.parseInt(br.readLine()); 	// 시작 정점
        minPath = new int[N + 1];
        Arrays.fill(minPath, Integer.MAX_VALUE);
        
        for(int i = 0; i < N + 1; i++) {
        	list.add(new ArrayList<>());
        }
        
        for(int i = 0; i < M; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	int from = Integer.parseInt(st.nextToken());
        	int to = Integer.parseInt(st.nextToken());
        	int path = Integer.parseInt(st.nextToken());
        	
        	list.get(from).add(new Edge(to, path));
        }
        
        repeat(start);
        
        for(int i = 1; i < N + 1; i ++) {
            System.out.println(minPath[i] != Integer.MAX_VALUE ? minPath[i] : "INF");
        }
    }

	private void repeat(int from) {
		Queue<Edge> queue = new PriorityQueue<>((e1, e2) -> Integer.compare(e1.weight, e2.weight));
		queue.offer(new Edge(from, 0));
		minPath[from] = 0;
		
		while(!queue.isEmpty()) {
			Edge curr = queue.poll();
			int target = curr.target;
			int weight = curr.weight;
			
			if(minPath[target] < weight) {
				continue;
			}
			
			for(Edge nextEdge : list.get(target)) {
				int dist = minPath[target] + nextEdge.weight;
				
				if(dist < minPath[nextEdge.target]) {
					minPath[nextEdge.target] = dist;
					queue.add(new Edge(nextEdge.target, dist));
				}
			}
		}
	}
}
