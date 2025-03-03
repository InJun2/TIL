// import java.io.*;
// import java.util.HashMap;
// import java.util.PriorityQueue;
// import java.util.StringTokenizer;

// public class Main {
// 	public static void main(String[] args) throws IOException {
// 		new Main().solution();
// 	}

// 	private void solution() throws IOException {
// 		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
// 		StringBuilder sb = new StringBuilder();
// 		StringTokenizer st;

// 		int n = Integer.parseInt(br.readLine());

// 		for (int i = 0; i < n; i++) {
// 			st = new StringTokenizer(br.readLine());

// 			HashMap<String, Product> map = new HashMap<>();
// 			int part = Integer.parseInt(st.nextToken());
// 			long money = Integer.parseInt(st.nextToken());
// 			long totalPrice = 0;
// 			int minQuantity = Integer.MAX_VALUE;

// 			PriorityQueue<Product> pq = new PriorityQueue<>();	// 가격 순 정렬
// 			PriorityQueue<Product2> pq2 = new PriorityQueue<>(); // 성능 순 정렬

// 			for (int j = 0; j < part; j++) {
// 				String str = br.readLine();
// 				String[] split = str.split(" ");
// 				Product product = new Product(split[0], Integer.parseInt(split[2]), Integer.parseInt(split[3]));

// 				pq.offer(product);
// 			}

// 			while (!pq.isEmpty()) {
// 				Product p = pq.poll();

// 				if(map.containsKey(p.type)) {
// 					pq2.offer(new Product2(p));
// 					continue;
// 				}

// 				map.put(p.type, p);
// 				totalPrice += p.price;
// 			}

// 			while (!pq2.isEmpty()) {
// 				Product2 p = pq2.poll();
// 				Product beforeP = map.get(p.type);

// 				if(beforeP.quality >= p.quality) {
// 					continue;
// 				}

// 				int plusPrice = p.price - beforeP.price;

// 				if(money < totalPrice + plusPrice) {
// 					continue;
// 				}

// 				totalPrice += plusPrice;
// 				map.put(p.type, new Product(p));
// 			}

// 			for(Product p : map.values()) {
// 				minQuantity = Math.min(minQuantity, p.quality);
// 			}

// 			System.out.println(minQuantity);
// 		}
// 	}

// 	class Product implements Comparable<Product> {
// 		String type;
// 		int price;
// 		int quality;

// 		public Product(String type, int price, int quality) {
// 			this.type = type;
// 			this.price = price;
// 			this.quality = quality;
// 		}

// 		public Product(Product2 p) {
// 			this.type = p.type;
// 			this.price = p.price;
// 			this.quality = p.quality;
// 		}

// 		@Override
// 		public int compareTo(Product o) {
// 			return this.price - o.price;
// 		}
// 	}

// 	class Product2 implements Comparable<Product2> {
// 		String type;
// 		int price;
// 		int quality;

// 		public Product2(Product p) {
// 			this.type = p.type;
// 			this.price = p.price;
// 			this.quality = p.quality;
// 		}

// 		@Override
// 		public int compareTo(Product2 o) {
// 			return this.quality - o.quality;
// 		}
// 	}
// }
// 해당 코드는 부품 별 최소 성능인 부품을 변경해야하는데 성능 최소 부품이 아닌 다른 부품을 변경하고 있어 실패

import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		new Main().solution();
	}

	private void solution() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		for (int tc = 0; tc < T; tc++) {
			st = new StringTokenizer(br.readLine());
			int part = Integer.parseInt(st.nextToken());
			long money = Long.parseLong(st.nextToken());
			long totalPrice = 0;

			HashMap<String, Product> selected = new HashMap<>();
			HashMap<String, PriorityQueue<Product>> map = new HashMap<>();

			PriorityQueue<Product> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.price));

			for (int i = 0; i < part; i++) {
				String[] arr = br.readLine().split(" ");
				pq.offer(new Product(arr[0], Integer.parseInt(arr[2]), Integer.parseInt(arr[3])));
			}

			while (!pq.isEmpty()) {
				Product p = pq.poll();
				if (selected.containsKey(p.type)) {
					if(!map.containsKey(p.type)) {
						map.put(p.type, new PriorityQueue<>(Comparator.comparingInt(pd -> pd.price)));
					}

					map.get(p.type).offer(p);
				} else {
					selected.put(p.type, p);
					totalPrice += p.price;
				}
			}

			while (true) {
				String type = null;
				int minQuality = Integer.MAX_VALUE;

				for (String key : selected.keySet()) {
					Product p = selected.get(key);
					if (p.quality < minQuality) {
						minQuality = p.quality;
						type = key;
					}
				}

				if (type == null) {
					break;
				}

				PriorityQueue<Product> upPQ = map.get(type);
				if (upPQ == null || upPQ.isEmpty()) {
					break;
				}

				boolean upgraded = false;
				while (!upPQ.isEmpty()) {
					Product nextPd = upPQ.peek();
					if (nextPd.quality <= minQuality) {
						upPQ.poll();
						continue;
					}

					int plusMoney = nextPd.price - selected.get(type).price;
					if (money >= totalPrice + plusMoney) {
						totalPrice += plusMoney;
						selected.put(type, nextPd);
						upPQ.poll();
						upgraded = true;
						break;
					} else {
						upPQ.poll();
					}
				}

				if (!upgraded) {
					break;
				}
			}

			int answer = Integer.MAX_VALUE;
			for (Product p : selected.values()) {
				answer = Math.min(answer, p.quality);
			}

			sb.append(answer).append("\n");
		}

		System.out.print(sb);
	}

	class Product {
		String type;
		int price;
		int quality;

		public Product(String type, int price, int quality) {
			this.type = type;
			this.price = price;
			this.quality = quality;
		}
	}
}
