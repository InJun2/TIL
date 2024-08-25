import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author	황인준
 * @since 	2024. 8. 25.
 * @link	https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRDL1aeugDFAUo
 * @performance 해당 문제는 두명의 인원이 있고 해당 인원이 계속 효율이 높은 베터리를 사용하여 충전하는 문제로 이동하는 동안 배터리가 사용가능 한지,
 *              확인하기 위해 클래스로 생성하여 사용. 이후 배터리를 어떤 배터리를 선택하는 방법을 구현하지 못해서 블로그 풀이 참조. 해당 배터리 충전
 *              진행 중 처음의 상태를 확인하지 않았어서 맨 처음의 충전 경우도 추가하여 코드 구현
 * @category #시뮬레이션 #완전탐색
 * @note
 */
public class SW_5644_무선충전 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        new SW_5644_무선충전().solution();
    }

    public class BC {
        int row, col, range;
        int power;

        public BC(int row, int col, int range, int power) {
            this.row = row;
            this.col = col;
            this.range = range;
            this.power = power;
        }

        public boolean isInRange(int[] location) {
            int distance = Math.abs(this.row - location[0]) + Math.abs(this.col - location[1]);
            return distance <= this.range;
        }
    }

    static final int N = 10;
    int M, A;
    BC[] bcs;
    int[] peopleMoveA, peopleMoveB;
    int[] locationA, locationB;
    int totalCharge;

    int[] moveRow = {0, -1, 0, 1, 0};
    int[] moveCol = {0, 0, 1, 0, -1};

    private void solution() throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());
            totalCharge = 0;

            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());
            bcs = new BC[A];
            peopleMoveA = new int[M];
            peopleMoveB = new int[M];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                peopleMoveA[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                peopleMoveB[i] = Integer.parseInt(st.nextToken());
            }

            locationA = new int[] {1, 1};
            locationB = new int[] {10, 10};

            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int range = Integer.parseInt(st.nextToken());
                int power = Integer.parseInt(st.nextToken());

                bcs[i] = new BC(y, x, range, power);
            }

            totalCharge += calculateMaxCharge();

            for (int i = 0; i < M; i++) {
                locationA[0] += moveRow[peopleMoveA[i]];
                locationA[1] += moveCol[peopleMoveA[i]];
                locationB[0] += moveRow[peopleMoveB[i]];
                locationB[1] += moveCol[peopleMoveB[i]];

                totalCharge += calculateMaxCharge();
            }

            sb.append("#").append(t).append(" ").append(totalCharge).append("\n");
        }

        System.out.println(sb);
    }

    private int calculateMaxCharge() {
        int maxCharge = 0;

        for (int i = 0; i < A; i++) {
            for (int j = 0; j < A; j++) {
                int chargeA = 0;
                int chargeB = 0;

                if (bcs[i].isInRange(locationA)) {
                    chargeA = bcs[i].power;
                }

                if (bcs[j].isInRange(locationB)) {
                    chargeB = bcs[j].power;
                }

                if (i == j && chargeA > 0 && chargeB > 0) {
                    int sharedCharge = chargeA;
                    maxCharge = Math.max(maxCharge, sharedCharge);
                } else {
                    maxCharge = Math.max(maxCharge, chargeA + chargeB);
                }
            }
        }

        return maxCharge;
    }
}