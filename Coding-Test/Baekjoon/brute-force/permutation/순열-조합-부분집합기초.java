import java.util.Arrays;

public class Main {
	static char[] chars = "World".toCharArray();
	static int r = 3;
	
	public static void main(String[] args) {
//		makePermutation(0, new char[r], new boolean[chars.length]);
		makeCombination(0, 0, new char[r]);
	}
	
	private static void makeCombination(int cnt, int start, char[] selected) {	// 조합
		if(cnt == r) {
			System.out.println(Arrays.toString(selected));
			
			return;
		}
		
		for(int i = start; i < chars.length; i++) {
			selected[cnt] = chars[i];
			makeCombination(cnt +1, start +1, selected);
		}
	}

		private static void makePermutation(final int nthChoice, char[] choosed, boolean[] visited) {	// 순열
		// 기저 상황
		if(nthChoice >= r) {
			System.out.println(Arrays.toString(choosed));
			return ;
		}
		
		// 재귀 상황
		for(int i = 0; i < chars.length; i++) {
			if(!visited[i]) {
				visited[i] = true;
				choosed[nthChoice] = chars[i];
				makePermutation(nthChoice + 1, choosed, visited);
				visited[i] = false;
			}
		}
		
	}

	private static void makePermutation2(final int nthChoice, char[] choosed) {	// 중복 순열
		// 기저 상황
		if (nthChoice >= r) {
			System.out.println(Arrays.toString(choosed));
			return;
		}
		
		// 재귀 상황
		for (int i = 0; i < chars.length; i++) {
			choosed[nthChoice] = chars[i];
			makePermutation(nthChoice + 1, choosed);
		}
	}
	
	private static void makeCombination2(int cnt, int start, char[] selected) {	// 중복 조합
			if(cnt == r) {
				System.out.println(Arrays.toString(selected));
				
				return;
			}
			
			for(int i = start; i < chars.length; i++) {
				selected[cnt] = chars[i];
				makeCombination(cnt +1, start, selected);
			}
	}

	private static void subset(int cnt, boolean[] selected) {	// 부분 집합
		if(cnt == chars.length) {
			for(int i = 0; i < chars.length; i ++) {
				if(selected[i]) {
					System.out.print(chars[i] + " ");					
				}
			}
			
			System.out.println();
			return;
		}
		
		selected[cnt] = true;
		subset(cnt + 1, selected);
		selected[cnt] = false;
		subset(cnt + 1, selected);
	}
