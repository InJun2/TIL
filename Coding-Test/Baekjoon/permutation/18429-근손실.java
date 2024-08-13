package d0813;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author 황인준
 * @since 2024. 8. 13.
 * @link https://www.acmicpc.net/problem/18429
 * @performance	이전 N과 M 문제 풀이와 마찬가지로 기초 코드를 기반으로 문제풀이 진행,
 * 		필요한 사항은 최대 일자, 현재 일자, 매일 감량 중량, 운동 기구 배열, 운동 기구 사용 여부 배열, 결과를 리턴할 result
 * 		진행 하다가 처음 입력값을 받았을 때 최대 일자, 감량 중량, 운동 기구 배열, result는 전역으로 처리가 가능하고 현재 운동 기구 사용 여부 배열을 사용하므로 
 * 		따로 파라미터로 현재 사용한 운동 기구를 기록할 필요가 없음
 * 		기존과 다른 부분은 지속적으로 총 중량이 500 이상 여부만 파악
 * @category #순열 # 재귀
 * @note	이전 N과 M을 set으로 푼 사람이 존재해 set으로 풀랬으나 같은 중량의 운동기구가 중복될 수 있으므로 set 풀이는 불가능
 */
public class BJ_S3_18429_근손실 {
	static final int START = 500;
	static int MUSCLE_LOSS_RATE;
	static int MAX_DAY;
	static int[] GYM_EQUIPMENT;
	static int result = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		MAX_DAY = Integer.parseInt(st.nextToken());
		MUSCLE_LOSS_RATE = Integer.parseInt(st.nextToken());
		GYM_EQUIPMENT = new int[MAX_DAY];
		boolean[] checked = new boolean[MAX_DAY];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < GYM_EQUIPMENT.length; i++) {
			GYM_EQUIPMENT[i] = Integer.parseInt(st.nextToken());
		}

		permutate(0, START, checked);
		
		System.out.println(result);
	}

	private static void permutate(int day, int weight, boolean[] checked) {
		if (day == MAX_DAY - 1) {
			result++;
			return;
		}

		
		for (int i = 0; i < GYM_EQUIPMENT.length; i++) {
			if(!checked[i]) {
				int newWeight = weight - MUSCLE_LOSS_RATE + GYM_EQUIPMENT[i];
				if(newWeight < START) {
					continue;
				}
				
				checked[i] = true;
				permutate(day + 1, newWeight, checked);
				checked[i] = false;
			}
		}
	}

}