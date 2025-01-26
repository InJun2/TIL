import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    Map<Integer, Integer> map = new HashMap<>();
    int N;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        for(int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");

            int t1 = Integer.parseInt(st.nextToken());
            int t2 = Integer.parseInt(st.nextToken());

            map.put(t1, map.getOrDefault(t1, 0) + 1);
            map.put(t2, map.getOrDefault(t2, 0) - 1);
        }

        List<Integer> keyList = new ArrayList<>(map.keySet());
        Collections.sort(keyList);

        int sum = 0;
        int max = 0;
        int ans_start = 0, ans_end = 0;

        boolean opened = false;

        for(int key : keyList) {
            sum += map.get(key);

            if(sum > max) {
                max = sum;
                ans_start = key;
                opened = true;
            } else if(sum < max && opened) {
                ans_end = key;
                opened = false;
            }
        }

        System.out.println(max);
        System.out.println(ans_start + " " + ans_end);
    }
}
