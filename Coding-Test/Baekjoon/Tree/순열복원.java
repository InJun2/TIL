import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 주요 아이디어

/**
 * 1. Inversion Sequence는 각 위치에서 뒤쪽에 있는 자신보다 작은 수의 개수를 나타냄
 * 2. 이 값을 기반으로 올바른 순열을 생성하려면 각 위치에 대해 가능한 위치를 찾아야함
 * 3. 세그먼트 트리를 사용하여 각 위치에 가능한 위치를 빠르게 찾고, 사용된 위치를 업데이트 함
 */
class SegmentTree {
    private final int[] tree;
    private final int size;

    public SegmentTree(int size) {  // 세그먼트 트리의 크기 지정
        this.size = size;
        this.tree = new int[4 * size];  // 세그먼트 트리가 완전 이진 트리로 구현되어 필요
        initTree();
    }

    private void initTree() {
        // tree 배열의 후반부를 1로 채움. 각 위치는 처음에는 사용 가능하므로 1로 설정
        Arrays.fill(tree, size, 4 * size, 1);
        for (int i = size - 1; i > 0; i--) {    // 리프 노드의 값을 이용하여 부모 노드의 값을 설정
            tree[i] = tree[2 * i] + tree[2 * i + 1];
        }
    }

    public void update(int pos) {
        pos += size;    // 리프 노드의 인덱스를 계산
        tree[pos] = 0;  // 해당 위치를 0으로 설정하여 사용했음을 표시
        while (pos > 1) {   // 트리의 상위 노드로 이동하며 각 노드의 값을 갱신
            pos /= 2;
            tree[pos] = tree[2 * pos] + tree[2 * pos + 1];
        }
    }

    public int query(int k) {   // k번째 위치 찾기
        int pos = 1;    // 트리의 루트에서 시작
        while (pos < size) {    // 리프 노드에 도달할 때까지 트리 탐색
            if (tree[2 * pos] >= k) {   // 왼쪽 자식 노드에 k번째 노드가 있는지 확인
                pos = 2 * pos;
            } else {    // 오른쪽 자식 노드에 k번째 노드가 있는지 확인
                k -= tree[2 * pos];
                pos = 2 * pos + 1;
            }
        }

        return pos - size;  // 리프 노드의 실제 인덱스 반환
    }
}


public class Test {
    public static int[] findPermutation(int n, int[] inversionSeq) {
        SegmentTree segTree = new SegmentTree(n);   // 배열의 개수로 세그먼트 트리 생성
        int[] result = new int[n];  // 배열 초기화

        // 역순으로 Inversion Sequence 처리 (뒤에서부터 차례로 순열 복원)
        for (int i = n - 1; i >= 0; i--) {  
            // 현재 숫자가 들어갈 위치를 찾음, 
            int position = segTree.query(inversionSeq[i] + 1);
            result[position] = i + 1;   // 해당 위치에 i + 1을 배치하여 1부터 시작하는 순열을 만들기 위함
            segTree.update(position);   // 해당 위치를 사용했음을 세그머트 트리에 업데이트
        }

        int[] reversedResult = new int[n];
        for (int i = 0; i < n; i++) {
            reversedResult[i] = result[n - i - 1];
        }

        return reversedResult;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] array = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(Arrays.toString(findPermutation(N, array)));
    }
}
