/*
    기존 코드 : 재귀를 어떻게 반복할 지 생각이 나지 않음

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {
	static final int ALL_RESULT = 362880;
	static final int MAX_SCORE = 171;
	static int[] kyuYoungCards;
	static int[] inYoungCards;
	static boolean[] checked;
	static int score;
	static int result;
	
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		
		for(int t = 0; t < T; t++) {
			st = new StringTokenizer(br.readLine());
			
			kyuYoungCards = new int[9];
			inYoungCards = new int[9];
			checked = new boolean[9];
			result = 0;
			score = 0;
			for(int cnt = 0; cnt < kyuYoungCards.length; cnt++) {
				kyuYoungCards[cnt] = Integer.parseInt(st.nextToken());
			}
			
			initCards();
			
			for(int i = 0; i < kyuYoungCards.length; i++) {
				playGame(i);
			}
			
			sb.append("#")
				.append(t)
				.append(" ")
				.append(String.valueOf(result))
				.append(" ")
				.append(String.valueOf(ALL_RESULT - result))
				.append("\n");
		}

		System.out.println(sb);
	}
	
	private static void playGame(int gameCount) {
		// 기저 상황
		if(gameCount == 9) {
			if(score < MAX_SCORE - score) {
				result ++;
			}
			return;
		}
		
		// 재귀 상황
		int cardNum = kyuYoungCards[gameCount];
		
		for(int i = 0; i < inYoungCards.length; i++) {
			if(!checked[i]) {
				int cardNum2 = inYoungCards[i];
				int prevScore = score;
				checked[i] = true;
				if(cardNum < cardNum2) {
					score += cardNum + cardNum2;
				}
				
				playGame(gameCount + 1);
				score = prevScore;
				checked[i] = false;
			}
			
		}
	}
	
	private static void initCards() {
		Set<Integer> set = new HashSet<>();

		for(int i = 1; i < 19; i++) {
			set.add(i);
		}
		
		for(int i = 0; i < kyuYoungCards.length; i++) {
			if(set.contains(kyuYoungCards[i])) {
				set.remove(kyuYoungCards[i]);
			}
		}
		
		int index = 0;
		for(int card : set) {
			inYoungCards[index++] = card;
		}
	}
}

*/

// 이후 GPT 개선 코드

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author 	황인준
 * @since 	2024. 8. 13.
 * @link	https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWgv9va6HnkDFAW0
 * @performance	규영 카드 목록을 저장하고 중복된 카드는 존재하지 않으므로 1~18까지의 수를 Set에 넣어두고 해당 규영 카드 목록을 제거하여 인영 카드 목록 저장
 * 				이후 해당 정보를 바탕으로 순열 재귀 반복을 통해 규영 승리 횟수, 인영 승리 횟수 기록 후 출력
 * @category #순열 #재귀
 * @note	우선 필요하다고 생각된 부분은 규영이의 카드 목록, 인영이의 카드 목록, 인영이가 이미 사용한 카드 여부, 총 승리 횟수, 스코어 점수 였음
 * 				그러나 해당 재귀를 어디서 무엇을 넣어야 하는지 모르겠고 해당 부분에서 시간을 지속적으로 소비하다 GPT를 통해 코드 개선
 * 				개선된 코드는 규영카드, 인영카드, 인영 카드 사용 여부, 규영 승리 횟수, 인영 승리 횟수를 사용하여 구현
 * 				중요했던 부분은 규영의 카드는 고정되어 있고 인영의 카드는 경우의 수에 따라 변경되므로 결정한 인영의 카드를 파라미터로 전송했어야 했음
*/
public class Solution {
    static int[] kyuYoungCards;
    static int[] inYoungCards;
    static boolean[] checked;
    static int kyuWins, inWins;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());
            kyuYoungCards = new int[9];
            inYoungCards = new int[9];
            checked = new boolean[9];
            kyuWins = 0;
            inWins = 0;

            Set<Integer> kyuSet = new HashSet<>();
            for (int i = 0; i < 9; i++) {
                kyuYoungCards[i] = Integer.parseInt(st.nextToken());
                kyuSet.add(kyuYoungCards[i]);
            }

            // 인영이의 카드 설정
            int index = 0;
            for (int i = 1; i <= 18; i++) {
                if (!kyuSet.contains(i)) {
                    inYoungCards[index++] = i;
                }
            }

            // 인영이 카드의 모든 순열을 체크
            permute(0, new int[9]);

            sb.append("#").append(t).append(" ").append(kyuWins).append(" ").append(inWins).append("\n");
        }

        System.out.println(sb);
    }

    // 인영이 카드의 순열 생성 및 게임 결과 계산
    private static void permute(int count, int[] selected) {
    	// 기저 조건
        if (count == 9) {
            int kyuScore = 0, inScore = 0;
            for (int i = 0; i < 9; i++) {
                if (kyuYoungCards[i] > selected[i]) {
                    kyuScore += kyuYoungCards[i] + selected[i];
                } else {
                    inScore += kyuYoungCards[i] + selected[i];
                }
            }
            if (kyuScore > inScore) {
                kyuWins++;
            } else if (kyuScore < inScore) {
                inWins++;
            }
            return;
        }

        // 재귀 조건
        for (int i = 0; i < 9; i++) {
            if (!checked[i]) {
                checked[i] = true;
                selected[count] = inYoungCards[i];
                permute(count + 1, selected);
                checked[i] = false;
            }
        }
    }
}

/*
메모리 : 20,440 kb
실행시간 : 2,855 ms
코드길이 : 2,495
*/