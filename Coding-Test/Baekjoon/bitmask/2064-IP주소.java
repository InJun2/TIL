//        int subnetMask = ips[0];
//        for(int i = 1 ; i < N ; i++) {
//            int check = ips[i];
//
//            for(int j = 31; j>= 0; j--) {
//                if(checked[j]) {
//                    continue;
//                }
//
//                if(subnetMask >> 1 != check >> 1) {  // 해당 경우 특정 위치의 비트가 같은걸 확인하는게 아니라 한칸씩 밀린 값을 비교함
//                    checked[j] = true;
//                }
//            }
//        }

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    final static int IP_MAX = 0xffffffff;
    final static int IP_NUM_MAX = 0xff;

    int N;
    int[] ips;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        ips = new int[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), ".");

            int ip = 0;
            for (int j = 0; j < 4; j++) {
                ip = (ip << 8) | Integer.parseInt(st.nextToken());  // 8비트를 밀고 받아온 값을 or 연산
            }

            ips[i] = ip;
        }

        int network = ips[0];
        for (int i = 1; i < N; i++) {
            network &= ips[i];
        }

        int subnetMask = IP_MAX;
        for (int i = 0; i < 32; i++) {
            int bit = 1 << (31 - i);

            boolean mismatch = false;
            for (int j = 1; j < N; j++) {
                if ((ips[0] & bit) != (ips[j] & bit)) {
                    mismatch = true;
                    break;
                }
            }

            if (mismatch) {
                subnetMask &= ~(bit | (bit - 1));
                break;
            }
        }

        network &= subnetMask;  // 이것때문에 엄청 많이 틀림

        System.out.println(changeIp(network));
        System.out.println(changeIp(subnetMask));
    }

    private String changeIp(int ip) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int num = (ip >> (24 - i * 8)) & IP_NUM_MAX;
            sb.append(num).append(".");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}