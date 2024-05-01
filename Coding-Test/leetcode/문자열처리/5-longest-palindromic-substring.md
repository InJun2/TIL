# 5. Longest Palindromic Substring
- 문자열이 주어지면 가장 긴 팰린드롬 문자열을 반환
    - 팰린드롬이란 뒤집어도 값이 비슷한 문자열
    - https://leetcode.com/problems/longest-palindromic-substring/description/

<br>

### 풀이 방법
- 단어를 쪼개서 회문인지 확인? 해당 경우는 문자열의 개수가 1000개 까지 되므로 분리할 수 없음
- 기존 문자열에서 회문인 문자를 확인해야 할 것 같은데 방법이 떠오르지 않아 책 참조
- 책에서는 투 포인터로 팰린드롬을 발견하면 확장하는 방식을 사용했고 이러한 투 포인터는 슬라이딩 윈도우 처럼 이동한다고 함

<br>

### 코드
```java
class Solution {
    private int left, maxLen;

    public String longestPalindrome(String s) {
        // 문자 길이 저장
        int len = s.length();

        // 길이가 1인 경우 예외 처리
        if (len < 2) {
            return s;
        }

        // 우측으로 한 칸씩 이동하며 투 포인터 조사
        for (int i = 0; i < len - 1; i++) {
            extendPalindrome(s, i, i + 1);  // 두칸짜리 투 포인터
            extendPalindrome(s, i, i + 2);  // 세칸짜리 투 포인터
        }

        //  왼쪽과 최대 길이만큼을 더한 오른쪽 만큼의 문자를 정답으로 리턴
        return s.substring(left, left + maxLen);
    }

    public void extendPalindrome(String s, int j, int k) {
        // 투 포인터가 유효한 범위 내에 있고 양쪽 끝 문자가 일치하는 팰린드롬인 경우 범위 확장
        while(j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
            j--;
            k++;
        }

        // 기존 최대 길이보다 큰 경우 값 교체
        if (maxLen < k - j -1) {
            left = j + 1;
            maxLen = k - j - 1;
        }
    }
}
```

- runtime : 13ms
- memory : 41.97mb

<br>

### 스터디 중 나온 이야기
- 중앙 확장법이라고도 부르는 것 같음. 이러한 기법을 알고 해당 기법으로 푸는 것이 유효한 문제라고 생각
- 나는 풀이를 이해를 못해서 바로 책을 봤는데 접근 방법을 gpt에 물어보고 접근 방법으로 코드를 구현해보고 나서 책을 볼것. 이후 책으로 최적화할 수 있는 부분 적용하는 방법이 좋을 것