import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws IOException {
		new Solution().solution();
	}
	
	class Edge {
		int to;
		int weight;
		
		public Edge(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}
	
	List<List<Edge>> list;
	boolean[] visited;
	int V;
	long minValue;

	public void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 1; t<= T; t++) {
			st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			
			list = new ArrayList<>();
			
			for(int i = 0; i < V + 1; i ++) {
				list.add(new ArrayList<>());
			}
			visited = new boolean[V + 1];
			
			for(int i = 0; i < E; i ++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				int weight = Integer.parseInt(st.nextToken());
				
				list.get(from).add(new Edge(to, weight));	
				list.get(to).add(new Edge(from, weight));
			}
			
			prims();
			
			sb.append("#")
				.append(t)
				.append(" ")
				.append(minValue)
				.append("\n");
		}
		
		System.out.println(sb);
	}
	
	private void prims() {
		minValue = 0;
		Queue<Edge> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.weight, o2.weight));
		queue.offer(new Edge(1, 0));	// 임의의 1번 노드 삽입
		
		while(!queue.isEmpty()) {
			Edge edge = queue.poll();	// 최소 가중치 to
			
			if(visited[edge.to]) {
				continue;
			}
			
			visited[edge.to] = true;
			minValue += edge.weight;
			
			for(Edge nextEdge : list.get(edge.to)) {	// 이후 to 에서 최소 노드 탐색
				if(!visited[nextEdge.to]) {	
					queue.offer(nextEdge);
				}
			}
		}
	}
}
