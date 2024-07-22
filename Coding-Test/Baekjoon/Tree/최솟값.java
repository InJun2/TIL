import java.io.*;
import java.util.StringTokenizer;

public class Minimum_10868 {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, NumberFormatException {
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];

        for(int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        SegmentTree segmentTree = new SegmentTree(arr);

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            if(a > b) {
                int temp = a;
                a = b;
                b = temp;
            }

            int min = segmentTree.getMinValueByRange(0, arr.length - 1, a, b, 0);
            sb.append(min).append("\n");
        }

        System.out.println(sb);
        br.close();
    }
}

class SegmentTree {
    int[] tree;
    int[] arr;

    SegmentTree(int[] arr) {
        this.tree = new int[arr.length * 4];
        this.arr = arr;
        initTree(0, arr.length - 1, 0);
    }

    public void initTree(int start, int end, int node) {
        if(start == end) {
            tree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;
            initTree(start, mid, node * 2 + 1);
            initTree(mid + 1, end, node * 2 + 2);
            tree[node] = Math.min(tree[node * 2 + 1], tree[node * 2 + 2]);
        }
    }

    public int getMinValueByRange(int start, int end, int qs, int qe, int node) {
        if(qs > end || qe < start) {
            return Integer.MAX_VALUE;
        }

        if(qs <= start && qe >= end) {
            return tree[node];
        }

        int mid = (start + end) / 2;
        int leftMin = getMinValueByRange(start, mid, qs, qe, node * 2 + 1);
        int rightMin = getMinValueByRange(mid + 1, end, qs, qe, node * 2 + 2);

        return Math.min(leftMin, rightMin);
    }
}
