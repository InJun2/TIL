# Brute Force

### 브루트 포스
- 이 알고리즘의 가장 큰 특징은 모든 영역을 전체 탐색하는 방법으로 완전 탐색 알고리즘이라고도 부름
- Brute(난폭한) + Force(힘). 무식하게 모든 경우의 수를 탐색하면서 요구조건에 충족되는 결과만을 가져오는 것을 의미
- 전체 탐색 방법으로는 다음과 같으며 어떤 방식으로든 문제를 해결한다면 브루트 포스 알고리즘으로 풀이한 것
    - 순차 탐색 : 선형 구조를 전체적으로 탐색
    - 깊이 우선 탐색 (DFS) : 비선형 구조를 전체적으로 탐색
    - 너비 우선 탐색 (BFS) : 비선형 구조를 전체적으로 탐색

<br>

### 1. for / while loop
- for문이나 while 문을 이용하여 모든 요소 탐색
```java
int result = 1982;

// for문
for(int i=0; i < 10000; i++) {
    if(i != result) {
        continue;
    }

    return i;
}

// while 문
int i = 0;

while(result != i) {
    i++;
}

return i;
```

<br>

### 2. 재귀 함수
- 특정 결과를 얻기 전까지 해당 함수를 재귀하며 반복
- for문이나 while loop 문으로 해결할 수 없는 경우 주로 사용
```java
public int fibonachi(int num) {
    if (num == 0) {
        return 0;
    }

    else if(num <= 2) {
        return num - 1;
    }

    else {
        // num이 2 이하가 아닐 경우 해당 함수를 재귀하여 반복
        return fibonachi(num-1) + fibonachi(num-2);
    }
}
```
<br>