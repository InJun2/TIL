import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N;
    String[] array;
    int result = Integer.MIN_VALUE;
    int[] numbers;
    boolean[] check;
    List<Character> list;
    int size;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        array = new String[N];
        Set<Character> set = new HashSet<>();

        for (int i = 0; i < N; i++) {
            array[i] = br.readLine();

            for(int j = 0; j < array[i].length(); j++) {
                set.add(array[i].charAt(j));
            }
        }

        list = new ArrayList<>(set);
        size = set.size();

        numbers = new int[size];
        check = new boolean[10];

        permute(0, new int[size]);
        System.out.println(result);
    }

    private void permute(int depth, int[] numbers) {
        if(depth == size) {
            calculate(numbers);
            return;
        }

        for (int i = 9; i >= 0; i--) {
            if (!check[i]) {
                check[i] = true;
                numbers[depth] = i;
                permute(depth + 1, numbers);
                check[i] = false;
            }
        }
    }

    private void calculate(int[] numbers) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < size; i++) {
            map.put(list.get(i), numbers[i]);
        }

        int sum = 0;

        for (String word : array) {
            int num = 0;
            for (int j = 0; j < word.length(); j++) {
                num = num * 10 + map.get(word.charAt(j));
            }

            sum += num;
        }

        result = Math.max(result, sum);
    }
}


// 메모리 : 300424kb, 시간 3108ms