import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws IOException {
		new Solution().solution();
	}

	List<List<Integer>> list = new ArrayList<>();
	boolean[] visited;
	int maxNode;

	public void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		for(int t = 1; t<= 10; t++) {
			list.clear();
			for(int i = 0; i <= 100; i++) {
				list.add(new ArrayList<>());
			}
			
			st = new StringTokenizer(br.readLine());
			int row = Integer.parseInt(st.nextToken());
			int startNode = Integer.parseInt(st.nextToken());
			visited = new boolean[101];
			
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < row / 2; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				
				list.get(from).add(to);
			}
			
			bfs(startNode);
			
			sb.append("#")
				.append(t)
				.append(" ")
				.append(maxNode)
				.append("\n");
		}
		
		System.out.println(sb);
	}
	
	private void bfs(int start) {
		Queue<int[]> queue = new ArrayDeque<>();	// 큐생성
		visited[start] = true;	// 현재 위치 방문
		int maxDepth = 0;
		maxNode = Integer.MIN_VALUE;	// 최대 깊이 시 최고 크기의 노드
		
		queue.add(new int[] {start, 0});
		
		while(!queue.isEmpty()) {
			int[] node = queue.poll();
			
			if(maxDepth < node[1]) {
				maxDepth = node[1];
				maxNode = node[0];
			} else if (maxDepth == node[1]) {
				maxNode = Math.max(maxNode, node[0]);
			}
			
			for(int nextNode : list.get(node[0])) {
				if(!visited[nextNode]) {
					visited[nextNode] = true;
					queue.add(new int[] {nextNode, node[1] + 1});		
				}
			}
		}
	}
}
