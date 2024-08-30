# 최소 신장 트리 (MST, Minimum Spanning Tree)

### 그래프에서 최소 비용 문제
1. 모든 정점을 연결하는 간선들의 가중치의 합이 최소가 되는 트리
2. 두 정점 사이의 최소 비용의 경로 찾기
    - 최단경로에서 가중치가 없다면 BFS
    - 최단경로에서 가중치가 있다면 양의 가중치는 다익스트라, 음의 가중치까지 허용이라면 벨만포드

<br>

### 신장 트리
- n개의 정점으로 이루어진 무향(양방향) 그래프에서 n개의 정점과 n-1 개의 간선으로 이루어진 트리

<br>

### 최소 신장 트리 (Minimum Spanning Tree)
- 무향 가중치 그래프에서 신장 트리를 구성하는 간선들의 가중치의 합이 최소인 신장 트리

<br>

### KRUSKAL 알고리즘 
- 간선을 하나씩 선택해서 MST를 찾는 알고리즘
- 사이클이 발생하면 두 집합의 대표자가 다르므로 제외

1. 최초, 모든 간선을 가중치에 따라 **오름차순**으로 정렬
2. 가중치가 가장 낮은 간선부터 선택하면서 트리를 증가 시킴
    - 사이클이 존재하면 남아 있는 간선 중 그 다음으로 가중치가 낮은 간선 선택
3. n-1 개의 간선이 선택될 때 까지 2를 반복

### 예시 코드
```java
import java.util.Arrays;
import java.util.Scanner;

public class MST_KruskalTest {
	
	public static void main(String[] args) {
		new MST_KruskalTest().solution();
	}
	
	class Edge implements Comparable<Edge> {
		int start, end, weight;

		public Edge(int start, int end, int weight) {
			super();
			this.start = start;
			this.end = end;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.weight, o.weight);
		}
	}
	
	int V;
	int[] parents;
	
	public void solution() {
		Scanner sc = new Scanner(System.in);
		
		V = sc.nextInt();
		int E = sc.nextInt();
		
		Edge[] edges = new Edge[E];
		for(int i = 0; i < E; i++) {
			edges[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
		}
		
		Arrays.sort(edges);	// 간선의 가중치 기준 오름차순 정렬
		make();	// 모든 정점을 분리 집합으로.. (단위 서로소 집합<트리> 생성)
		
		int cnt = 0, cost = 0;
		for(Edge edge : edges) {
			if(union(edge.start, edge.end)) {
				cost += edge.weight;
				if(++cnt == V - 1) {
					break;	// 최소 신장 트리 완성
				}
			}
		}
		
		System.out.println(cost);
	}

	private void make() {
		parents = new int[V];
		for(int i = 0; i < V; i ++) {
			parents[i] = -1;
		}	// Arrays.fill(parents, -1);
	}
	
	private int findSet(int a) {
		if(parents[a] < 0) {
			return a;
		}
		
		return parents[a] = findSet(parents[a]);	// 집합의 대표자를 자신의 부모로 변경 : 패스 압축
	}
	
	private boolean union(int a, int b) {
		int aRoot = findSet(a);
		int bRoot = findSet(b);
		
		if(aRoot == bRoot) {
			return false;
		}
		
		// 편의상 a 집합에 b 집합을 붙임 (집합의 크기에 따라 붙이도록 처리도 가능)
		parents[aRoot] += parents[bRoot];	// 집합 크기 관리 (절대값을 사용하면 집합의 크기가 됨)
		parents[bRoot] = aRoot;
		return true;
	}
}

/*
input : 

7 11
0 1 32
0 2 31
0 5 60
0 6 51
1 2 21
2 4 46
2 6 25
3 4 34
3 5 18
4 5 40
4 6 51

output : 

175
*/
```

<br>

### PRIM 알고리즘
- 하나의 정점에서 연결된 간선들 중에 하나씩 선택하면서 MST를 만들어 가는 방식

1. 임의 정점을 하나 선택해서 시작
2. 선택한 정점과 인접하는 정점들 중의 최소 비용이 간선이 존재하는 정점을 선택
3. 모든 정점이 선택될 때 까지 2 과정을 반복

<br>

### 서로소인 2개의 집합(2 disjoint-sets) 정보를 유지
- 트리 정점들(tree vertices)
    - MST를 만들기 위해 선택된 정점들
- 비트리 정점들(non-tree vertices)
    - 선택 되지 않은 정점들

<br>

### 구현 방법
- 자바에서는 주로 우선순위 큐를 사용하여 Prim 알고리즘을 구현함
- 우선순위 큐는 다음과 같음
	- [우선순위 큐 정리](./priority-queue.md)

<br>

```java
import java.util.Arrays;
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
		
		Arrays.fill(minEdge,Integer.MAX_VALUE);
		minEdge[0] = 0;
		int cost = 0;
		int i = 0;
		
		for(i = 0; i < V; i ++) {
			// step 1 : 트리 구성에 포함될 가장 유리한 정점 선택
			int min = Integer.MAX_VALUE;
			int minVertex = -1;
			
			for (int j = 0; j < V; j++) {
				if(visited[j]) {
					continue;
				}
				
				if(min > minEdge[j]) {
					minVertex = j;
					min = minEdge[j];
				}
			}
			
			if(minVertex == -1) {
				break;
			}
			visited[minVertex] = true;
			cost += min;
			// step2 : 선택된 정점과 다른 정점들 간선 비용 비교하기
			for(int j = 0; j < V; j++) {
				if(!visited[j] && adjMatrix[minVertex][j] > 0 && minEdge[j] > adjMatrix[minVertex][j]) {
					minEdge[j] = adjMatrix[minVertex][j];
				}
			} 
		}
		
		System.out.println(i==V ? cost : -1);
		
	}
}

```