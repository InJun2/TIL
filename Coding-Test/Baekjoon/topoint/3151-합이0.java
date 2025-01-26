import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        long answer = 0;

        for (int i = 0; i < N - 2; i++) {
            int left = i + 1;
            int right = N - 1;

            while (left < right) {
                long sum = (long)arr[i] + arr[left] + arr[right];

                if (sum == 0) {
                    if (arr[left] == arr[right]) {
                        long len = right - left + 1;
                        answer += (len * (len - 1)) / 2;
                        break;
                    } else {
                        int leftVal = arr[left];
                        int leftCount = 0;
                        while (left < right && arr[left] == leftVal) {
                            left++;
                            leftCount++;
                        }

                        int rightVal = arr[right];
                        int rightCount = 0;
                        while (right >= left && arr[right] == rightVal) {
                            right--;
                            rightCount++;
                        }

                        answer += (long)leftCount * rightCount;
                    }
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        System.out.println(answer);
    }
}
