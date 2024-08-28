import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws IOException {
		new Main().solution();
	}
	
	int N, M;
	List<List<Integer>> list = new ArrayList<>();
	boolean[] visited;
	boolean result;
	
	private void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		visited = new boolean[N];
		
		for(int i = 0; i < N; i++) {
			list.add(new ArrayList<>());
		}
		
		// 여러 사람이 있고 그 중 5명이 연속으로 있는 경우가 있어야 한다 <- depth 4
		for(int t = 0; t < M; t ++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			
			list.get(from).add(to);
			list.get(to).add(from);
		}
		
		for(int i = 0; i < list.size(); i++) {
			dfs(i, 0);
			if(result) {
				break;
			}
		}
		
		System.out.println(result ? 1 : 0);
	}

	private void dfs(int startNode, int cnt) {
		if(cnt == 4 || result == true) {
			result = true;
			return;
		}
		
		visited[startNode] = true;
		List<Integer> fromList = list.get(startNode);
		for(int from : fromList) {
			if(!visited[from]) {
				dfs(from, cnt + 1);
			}
		}

		visited[startNode] = false;
	}
}
