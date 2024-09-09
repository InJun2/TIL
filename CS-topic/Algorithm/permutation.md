# 순열 (Permutation), 조합 (Combination), 부분집합(Subset)

### 완전 검색(Exhaustive Search)
- 완전 검색 방법은 문제의 해법으로 생각할 수 있는 모든 경우의 수를 나열해보고 확인하는 기법
- Brute-force 혹은 generate-and-test 기법이라고도 부름
    - force의 의미는 사람(지능)보다는 컴퓨터의 force를 의미
    - 탐욕 알고리즘적인 접근은 해답을 찾아내지 못하는 경우도 있으니 유의
- 모든 경우의 수를 테스트한 후 최종 해법을 도출
- 상대적으로 빠른 시간에 문제해결(알고리즘 설계)을 할 수 있음
- 일반적으로 경우의 수가 상대적으로 작을 때 유용

#### 완전 검색으로 시작하라
- 모든 경우의 수를 생성하고 테스트하기 때문에 수행속도는 느리지만 해답을 찾아내지 못할 확률이 작음
    - 가능한 경우를 모두 나열하고 이후 선별
    - 메모이제이션, 가지치기 등을 통해 불필요한 경우 제거
- 검정 등에서 주어진 문제를 풀 때 우선 완전 검색으로 접근하여 해답을 도출한 후 성능 개선을 위해 다른 알고리즘을 사용하고 해답을 확인하는 것이 바람직

<br>

### 순열 (Permutation)
- 서로 다른 것들 중 몇개를 뽑아서 한 줄로 나열하는 것
- 대표적인 알고리즘으로 순서화된 요소들의 집합에서 최선의 방법을 찾는 것과 관련이 있음
    - TSP(Traveling Saleman Problem)
- 서로 다른 n개 중 r개를 택하는 순열은 다음과 같이 표현
    - nPr = n * (n - 1) * (n - 2) ... * (n - r + 1)
    = n! = n + (n - 1) * (n - 2) ... * 1
- n > 12 인 경우 시간 복잡도가 폭발적으로 증가
    - 12! = 479,001,600

```java
public class Permutation {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        boolean[] used = new boolean[arr.length];
        List<Integer> current = new ArrayList<>();
        
        // 순열: 특정 개수의 원소를 선택하여 순서를 고려해 나열.
        generatePermutation(arr, used, current);
    }
    
    public static void generatePermutation(int[] arr, boolean[] used, List<Integer> current) {
        if (current.size() == arr.length) {
            System.out.println(current);
            return;
        }
        
        for (int i = 0; i < arr.length; i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(arr[i]);
                generatePermutation(arr, used, current);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }
}
```

<br>

#### 부분 순열

```java
import java.util.ArrayList;
import java.util.List;

public class DuplicatePermutation {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        List<Integer> current = new ArrayList<>();
        
        // 중복 순열: 같은 원소를 여러 번 선택할 수 있음.
        generateDuplicatePermutation(arr, current, 3); // 중복 순열의 길이 (r = 3)
    }
    
    public static void generateDuplicatePermutation(int[] arr, List<Integer> current, int r) {
        if (current.size() == r) {
            System.out.println(current);
            return;
        }
        
        for (int i = 0; i < arr.length; i++) {
            current.add(arr[i]);
            generateDuplicatePermutation(arr, current, r); // 중복 허용
            current.remove(current.size() - 1);
        }
    }
}
```

<br>

### 조합 (Combination)
- 순서를 고려하지 않고, 서로 다른 것들 중 몇 개를 선택하는 것
- n개의 서로 다른 원소에서 r개를 선택하는 조합은 다음과 같이 표현함
    - nCr = n! / (r! * (n - r)!)
    - 예: 3C2 = 3! / (2! * 1!) = 3
- 순서에 상관없이 선택하므로, 순열보다 경우의 수가 적음

```java
public class Combination {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        int r = 2;
        
        // 조합: 특정 개수의 원소를 선택하여 순서와 상관없이 나열.
        generateCombination(arr, r, 0, new ArrayList<>());
    }
    
    public static void generateCombination(int[] arr, int r, int start, List<Integer> current) {
        if (current.size() == r) {
            System.out.println(current);
            return;
        }
        
        for (int i = start; i < arr.length; i++) {
            current.add(arr[i]);
            generateCombination(arr, r, i + 1, current);
            current.remove(current.size() - 1);
        }
    }
}
```

<br>

#### 부분 조합

```java
import java.util.ArrayList;
import java.util.List;

public class DuplicateCombination {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        int r = 2;
        
        // 중복 조합: 같은 원소를 여러 번 선택할 수 있음.
        generateDuplicateCombination(arr, r, 0, new ArrayList<>());
    }
    
    public static void generateDuplicateCombination(int[] arr, int r, int start, List<Integer> current) {
        if (current.size() == r) {
            System.out.println(current);
            return;
        }
        
        for (int i = start; i < arr.length; i++) {
            current.add(arr[i]);
            generateDuplicateCombination(arr, r, i, current);  // i를 그대로 전달하여 중복 허용
            current.remove(current.size() - 1);
        }
    }
}
```

<br>

### 부분집합 (Subset)
- 주어진 집합에서 부분집합을 선택하는 것
- 부분집합의 개수는 2^n으로, 원소를 포함하거나 포함하지 않는 두 가지 선택지가 있기 때문
    - 예: 집합 {a, b}의 부분집합은 { }, {a}, {b}, {a, b}
- 모든 원소가 포함되지 않거나, 공집합이 포함될 수 있음

```java
public class Subset {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};

        // 부분집합: 원소들의 부분집합을 나열, 모든 경우의 수를 포함.
        generateSubset(arr, 0, new ArrayList<>());
    }
    
    public static void generateSubset(int[] arr, int index, List<Integer> current) {
        if (index == arr.length) {
            System.out.println(current);
            return;
        }
        
        // 원소를 포함하지 않는 경우
        generateSubset(arr, index + 1, current);
        
        // 원소를 포함하는 경우
        current.add(arr[index]);
        generateSubset(arr, index + 1, current);
        current.remove(current.size() - 1);
    }
}
```

<br>

| **특징**            | **순열 (Permutation)**                                   | **조합 (Combination)**                                | **부분집합 (Subset)**                                 |
|---------------------|----------------------------------------------------------|-------------------------------------------------------|-------------------------------------------------------|
| **정의**            | n개 중 r개를 뽑아 순서대로 나열                           | n개 중 r개를 뽑아 순서를 고려하지 않음                | n개의 원소에서 부분집합을 선택                         |
| **순서 고려**       | O (순서 중요)                                            | X (순서 상관 없음)                                   | X (순서 상관 없음)                                    |
| **공식**            | nPr = n! / (n-r)!                                        | nCr = n! / (r! * (n-r)!)                              | 2^n                                                   |
| **예시**            | 3P2 = 3 * 2 = 6                                          | 3C2 = 3! / (2! * 1!) = 3                              | 집합 {a, b}의 부분집합: { }, {a}, {b}, {a, b}          |
| **사용 예**         | 최적화 문제 (TSP 등)                                     | 그룹 선정, 조합 문제                                  | 집합 내에서 특정 조건을 만족하는 경우 찾기             |

<br>

### 예시 상황

#### 순열
- 주어진 n개의 요소 중 r개의 요소를 순서를 고려하여 나열하는 방법
- 예시: 세 개의 숫자 1, 2, 3 중 두 개의 숫자를 순서를 고려하여 나열
- 가능한 순열: (1, 2), (1, 3), (2, 1), (2, 3), (3, 1), (3, 2) 총 6가지
- 대표적 예시: 여행자가 여러 도시를 방문하는 최적의 경로를 찾는 문제(TSP)

<br>

#### 조합
- 주어진 n개의 요소 중 r개의 요소를 순서를 고려하지 않고 선택하는 방법
- 예시: 세 개의 숫자 1, 2, 3 중 두 개의 숫자를 순서에 상관없이 선택
- 가능한 조합: {1, 2}, {1, 3}, {2, 3} 총 3가지
- 대표적 예시: 팀 구성이나 상품 선택

<br>

#### 부분집합
- 주어진 n개의 요소로 만들 수 있는 모든 집합을 의미
- 예시: 세 개의 요소 a, b, c로 만들 수 있는 모든 부분집합
- 가능한 부분집합: {}, {a}, {b}, {c}, {a, b}, {a, c}, {b, c}, {a, b, c} 총 8가지
- 대표적 예시: 집합의 모든 가능한 조합을 고려하는 문제





