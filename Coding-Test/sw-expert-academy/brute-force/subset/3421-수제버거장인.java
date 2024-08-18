import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**

 @author 황인준
 @since 2024. 8. 18.
 @link https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWErcQmKy6kDFAXi
 @performance   해당 조합이 좋지 않은 메뉴는 2개씩 들어오므로 경우의 수 조합을 list에 넣고 각 조합을 조합이 좋지 않은 블랙리스트 메뉴 2개
                모두 포함하고 있는지 유효성 검사 후 없다면 총 조합의 수를 ++ 하는 방식으로 진행, 성능이 터질줄 알았는데 다행히 패스됨
 @category #완전탐색 #부분집합 #백트래킹 #유효성검사
 @note
 */
public class SW_D5_수제버거장인 {
    int N, M;
    int[][] blackList;
    int result;

    public static void main(String[] args) throws NumberFormatException, IOException {
        new SW_D5_수제버거장인().solution();
    }

    public void solution() throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            blackList = new int[M][2];
            result = 0;

            for(int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                blackList[i][0] = Integer.parseInt(st.nextToken());
                blackList[i][1] = Integer.parseInt(st.nextToken());
            }

            List<Integer> list = new ArrayList<>();
            addList(1, list);

            sb.append("#")
                    .append(t + 1)
                    .append(" ")
                    .append(result)
                    .append("\n");
        }

        System.out.println(sb);
        br.close();
    }

    private void addList(int start, List<Integer> list) {
        if (check(list)) {
            result++;
        }

        for (int i = start; i <= N; i++) {
            list.add(i);
            addList(i + 1, list);
            list.remove(list.size() - 1);
        }
    }

    private boolean check(List<Integer> list) {
        for (int[] pair : blackList) {
            if (list.contains(pair[0]) && list.contains(pair[1])) {
                return false;
            }
        }
        return true;
    }
}