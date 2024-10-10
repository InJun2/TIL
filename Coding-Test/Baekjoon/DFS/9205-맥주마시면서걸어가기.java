import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	private static final String HAPPY = "happy";
	private static final String SAD = "sad";
	private static final int MOVE = 50;
	private static final int MAX_BEER = 20;
		
	public static void main(String[] args) throws NumberFormatException, IOException {
		new Main().solution();
	}
	
	boolean[] checked;
	List<int[]> list = new ArrayList<>();
	int locationX, locationY;
	boolean result;
	
	private void solution() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 0; t < T; t++) {
			list.clear();
			result = false;
			int num = Integer.parseInt(br.readLine());
			checked = new boolean[num + 1];
			
			st = new StringTokenizer(br.readLine());
			locationX = Integer.parseInt(st.nextToken());
			locationY = Integer.parseInt(st.nextToken());
			
			for(int i = 0; i < num + 1; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				
				list.add(new int[] {x, y});
			}
			
			dfs(locationX, locationY);
			

			if(result) {
				sb.append(HAPPY);
			} else {
				sb.append(SAD);
			}
			
			sb.append("\n");
		}
		
		System.out.print(sb);
	}
	
	public void dfs(int x, int y) {
		if(result) {
			return;
		}
		
		int[] destination = list.get(checked.length - 1);
	    if(getDistance(destination[0], destination[1], x, y) <= MAX_BEER * MOVE) {
	        result = true;
	        return;
	    }
		
		for(int i = 0; i < checked.length; i++) {
			if(!checked[i]) {
				int[] location = list.get(i);
				
				if(getDistance(location[0], location[1], x, y) <= MAX_BEER * MOVE) {
					checked[i] = true;
					dfs(location[0], location[1]);
				}
			}
		}
	}
	
	public int getDistance(int startX, int startY, int x, int y) {
        return Math.abs(startX - x) + Math.abs(startY - y);
    }

}
