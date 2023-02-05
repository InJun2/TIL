### H-Index

###  문제 설명
- H-Index는 과학자의 생산성과 영향력을 나타내는 지표입니다. 어느 과학자의 H-Index를 나타내는 값인 h를 구하려고 합니다. 위키백과1에 따르면, H-Index는 다음과 같이 구합니다.
- 어떤 과학자가 발표한 논문 n편 중, h번 이상 인용된 논문이 h편 이상이고 나머지 논문이 h번 이하 인용되었다면 h의 최댓값이 이 과학자의 H-Index입니다.
- 어떤 과학자가 발표한 논문의 인용 횟수를 담은 배열 citations가 매개변수로 주어질 때, 이 과학자의 H-Index를 return 하도록 solution 함수를 작성해주세요.

<br>

### 제한사항
- 과학자가 발표한 논문의 수는 1편 이상 1,000편 이하입니다.
- 논문별 인용 횟수는 0회 이상 10,000회 이하입니다.

<br>

### 입출력 예
|citations|return|
|:---:|:---:|
|\[3, 0, 6, 1, 5\]|3|

<br>

## 풀이
### 첫 번째 풀이
- 이해가 덜 된 상태로 풀이 진행
- 그냥 정렬해서 중간 값이 h-index라는 소리가 아닌가 판단
- 어림도 없이 안됨

```java
import java.util.Arrays;

class Solution {
    public int solution(int[] citations) {
        Arrays.sort(citations);

        int index = citations.length/2;

        return citations[index];
    }
}
```

<br>

### 두 번째 풀이
- 블로그 그대로 참조
- 그냥 오름차순 정렬해두고 총 인용논문수에서 하나씩 줄여보면서 이것보다 큰지 확인
- 큰지 한번 확인이 된다면 오름차순으로 정렬되어 있기 때문에 뒤를 더 확인하지 않아도 뒤의 인덱스에 들어가 있는 인용횟수는 모두 hIndex번 이상 인용되었을 것

```java
package hello.servlet;

import java.util.Arrays;

class Solution {
    public int solution(int[] citations) {
        int answer = 0;
        int hIndex;

        Arrays.sort(citations);

        for(int i=0; i<citations.length; i++) {
            hIndex = citations.length - i;

            if(citations[i] >= hIndex) {
                answer = hIndex;
                break;
            }
        }

        return answer;
    }
}
```

<br>

### 문제 링크
- https://school.programmers.co.kr/learn/courses/30/lessons/42747

<br>

## Reference
- https://yubh1017.tistory.com/23