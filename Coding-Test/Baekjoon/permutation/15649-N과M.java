package d0813;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
@author 황인준
@since 	2024. 8. 13.
@link	https://www.acmicpc.net/problem/15649
@performance	기초 코드와 동일하게 numberIndex로 현재 몇번 째 index를 넣어줘야 하는지 파라미터로 넣고,
                array에 입력 및 해당 숫자 visited boolean 타입으로 관리, 이후 해당 index가 배열의 최대 크기에 도달했을 경우 출력 후 리턴,
                이후 재귀가 끝났다면 방문 여부를 취소하고 다시 순회 진행
@category #순열 #재귀
@note 
*/
public class BJ_S3_15649_N과M {
    static int numberMaxLength;
    static StringBuilder sb = new StringBuilder();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        numberMaxLength = N;	// 출력 가능 숫자 1~N
        int[] numberArray = new int[M];	// 출력 배열은 M개 까지
        boolean[] visited = new boolean[N + 1];	// 출력 가능한 숫자 방문 여부, 1~N 까지 하기 위한 +1
        
        permutate(0, numberArray, visited);
        System.out.println(sb);
    }
    
    private static void permutate(int numberIndex, int[] array, boolean[] visited) {
        if(numberIndex == array.length) {	// array가 꽉차면 반환
            for(int a : array) {
                sb.append(a).append(" ");
            }
            sb.append("\n");
            return;
        }
        
        for(int i = 1; i &lt;= numberMaxLength; i++) {	// 1부터 array에 값 넣기
            if(!visited[i]) {	// 방문하지 않았었다면
                visited[i] = true;	// 방문 여부 체크
                array[numberIndex] = i;	// array에 반복 숫자 값 넣기
                permutate(numberIndex + 1, array, visited);	// 이후 방문 여부가 없는 숫자를 넣도록 재귀 반복
                visited[i] = false;
            }
        }
    }
}