import java.io.*;
import java.util.StringTokenizer;


class SegmentTree2 {
    long[] tree;
    long[] arr;

    public SegmentTree2(long[] arr) {
        this.arr = arr;
        this.tree = new long[arr.length * 4];
        init(0, arr.length - 1, 0);
    }

    private long init(int start, int end, int node) {
        if (start == end) {
            return tree[node] = arr[start];
        }

        int mid = (start + end) / 2;
        return tree[node] = init(start, mid, node * 2 + 1) + init(mid + 1, end, node * 2 + 2);
    }

    public long search(int start, int end, int qs, int qe, int node) {
        if (qs <= start && qe >= end) {
            return tree[node];
        }

        if (qs > end || qe < start) {
            return 0;
        }

        int mid = (start + end) / 2;
        return search(start, mid, qs, qe, node * 2 + 1) + search(mid + 1, end, qs, qe, node * 2 + 2);
    }

    public void update(int start, int end, int idx, long value, int node) {
        if (start == end) {
            tree[node] = value;
        } else {
            int mid = (start + end) / 2;
            if (idx <= mid) {
                update(start, mid, idx, value, 2 * node + 1);
            } else {
                update(mid + 1, end, idx, value, 2 * node + 2);
            }
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }
}

public class Main { // 백준 입출력을 위한 기본 폼
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException, NumberFormatException {
        StringBuilder builder = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        long[] arr = new long[N];

        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        SegmentTree2 tree = new SegmentTree2(arr);

        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if (a == 1) {
                // 업데이트는 c 값을 그대로 반영해야 합니다.
                tree.update(0, arr.length - 1, b - 1, c, 0);
            } else if (a == 2) {
                // 검색은 범위 내의 합을 구합니다.
                long search = tree.search(0, arr.length - 1, b - 1, (int) c - 1, 0);
                builder.append(search).append("\n");
            }
        }

        System.out.print(builder);

        br.close();
        bw.close();
    }
}