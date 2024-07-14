import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n, m, result, temp;
        int[] a, b;

        int T;
        T = Integer.parseInt(br.readLine().trim());

        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            a = new int[n];
            b = new int[m];
            result = Integer.MIN_VALUE;

            StringTokenizer st1 = new StringTokenizer(br.readLine().trim());
            for(int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st1.nextToken());
            }

            StringTokenizer st2 = new StringTokenizer(br.readLine().trim());
            for(int i = 0; i < m; i++) {
                b[i] = Integer.parseInt(st2.nextToken());
            }

            if (n > m) {
                int[] tempArr = a;
                a = b;
                b = tempArr;
                int tempSize = n;
                n = m;
                m = tempSize;
            }

            for (int i = 0; i <= m - n; i++) {
                temp = 0;
                for (int j = 0; j < n; j++) {
                    temp += a[j] * b[i + j];
                }
                if (temp > result) {
                    result = temp;
                }
            }

            System.out.printf("#%d %d\n", test_case, result);
        }
    }
}