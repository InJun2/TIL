## 코테 주요 메서드

# 📌 Java 코딩테스트 필수 함수 정리 

* 코딩테스트에서 자주 사용하는 Java 기본 클래스

---

## 🔥 1. String (문자열 처리 핵심)

### ✔️ 길이 / 접근

```java
s.length()        // 문자열 길이
s.charAt(i)       // i번째 문자 (char)
```

### ✔️ 문자열 자르기

```java
s.substring(0, 3) // 0 ~ 2 인덱스
```

### ✔️ 포함 여부

```java
s.contains("abc")
s.startsWith("ab")
s.endsWith("bc")
```

### ✔️ 치환

```java
s.replace("a", "b")     // 일반 치환
s.replaceAll("0", "")   // 정규식 기반 (주의)
```

### ✔️ 분리

```java
String[] arr = s.split(" ");
```

### ✔️ 특정 인덱스의 문자

```java
if (s.charAt(i) == '0') { }
```

---

## 🔥 2. Integer (숫자 & 변환)

### ✔️ 기본 변환

```java
Integer.parseInt("123")   // 문자열 → int
Integer.toString(123)     // int → 문자열
```

### ✔️ 진법 변환

```java
Integer.toBinaryString(10)   // "1010"
Integer.toOctalString(10)    // "12"
Integer.toHexString(10)      // "a"

Integer.parseInt("1010", 2)  // 10
```

### ✔️ 비트 연산 관련

```java
Integer.bitCount(10)   // 1의 개수 (1010 → 2)
```

---

## 🔥 3. Math (계산 필수)

### ✔️ 기본 연산

```java
Math.max(a, b)
Math.min(a, b)
Math.abs(-5)
```

### ✔️ 제곱 / 루트

```java
Math.pow(2, 3)   // 8
Math.sqrt(16)    // 4
```

### ✔️ 올림 / 내림 / 반올림

```java
Math.ceil(2.3)    // 3.0
Math.floor(2.7)   // 2.0
Math.round(2.5)   // 3
```

---

## 🔥 4. Arrays

```java
Arrays.sort(arr)          // 정렬
Arrays.fill(arr, 0)       // 초기화
Arrays.toString(arr)      // 출력용
```

### ✔️ 부분 정렬

```java
Arrays.sort(arr, 1, 4); // 1~3 인덱스 정렬
```

---

## 🔥 5. StringBuilder (🔥 성능 핵심)

### ✔️ 문자열 누적

```java
StringBuilder sb = new StringBuilder();

sb.append("hello");
sb.append("world");

String result = sb.toString();
```

## 🔥 6. Character (문자 판별)

```java
Character.isDigit('1')
Character.isLetter('a')
Character.isUpperCase('A')
Character.isLowerCase('a')
```

---

## 🔥 7. 코테 주요 패턴

### ✔️ 문자열 숫자 합

```java
int sum = 0;
for (char c : s.toCharArray()) {
    sum += c - '0';
}
```

### ✔️ 2진수 활용

```java
Integer.toBinaryString(n)
Integer.bitCount(n)
```

### ✔️ 정렬

```java
Arrays.sort(arr);
```

---

## 🔥 8. 진짜 핵심 TOP 5

1. `s.charAt(i)`
2. `s.equals()`
3. `Integer.parseInt()`
4. `Integer.toBinaryString()`
5. `Arrays.sort()`

---

## 🔥 9. 상황별 사용법

### ✔️ 문자열 문제

* `charAt`
* `substring`
* `replace`

### ✔️ 숫자 문제

* `Math`
* `Integer`

### ✔️ 성능 문제

* `StringBuilder`
* `bitCount`

---

## 한 줄 핵심 정리

👉 문자열 → `String`
👉 숫자 변환 → `Integer`
👉 계산 → `Math`
👉 정렬 → `Arrays` / `Collections`
👉 성능 → `StringBuilder`

---

<br><br>

# 📌 Java 코딩테스트 자료구조 컬렉션 정리

---

## 🔥 0. Collections

```java
Collections.sort(list)
Collections.reverse(list)
Collections.max(list)
Collections.min(list)
```

## 🔥 1. ArrayList (동적 배열)

```java
List<Integer> list = new ArrayList<>();

list.add(1);
list.get(0);
list.remove(0);
list.size();
```

### ✔️ 특징

* 인덱스 접근 빠름 (O(1))
* 중간 삽입/삭제 느림

* 👉 사용: **순서 있는 데이터 + 인덱스 접근 필요할 때**

---

## 🔥 2. LinkedList

```java
LinkedList<Integer> list = new LinkedList<>();

list.add(1);
list.removeFirst();
list.removeLast();
```

### ✔️ 특징

* 삽입/삭제 빠름
* 인덱스 접근 느림

* 👉 사용: **삽입/삭제가 많은 경우**

---

## 🔥 3. Stack (LIFO)

```java
Stack<Integer> stack = new Stack<>();

stack.push(1);
stack.pop();
stack.peek();
```

* 👉 사용: **괄호 문제 / DFS / 역순 처리**

---

## 🔥 4. Queue (FIFO)

```java
Queue<Integer> q = new LinkedList<>();

q.offer(1);
q.poll();
q.peek();
```

* 👉 사용: **BFS / 순서 처리**

---

## 🔥 5. PriorityQueue (우선순위 큐)

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();

pq.offer(3);
pq.offer(1);
pq.poll(); // 가장 작은 값
```

### ✔️ 최대 힙

```java
PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
```

### ✔️ 특징

* 👉 사용: **최솟값 / 최댓값 빠르게 뽑을 때**

---

## 🔥 6. HashSet (중복 제거)

```java
Set<Integer> set = new HashSet<>();

set.add(1);
set.contains(1);
set.remove(1);
```

### ✔️ 특징

* 👉 사용: **중복 제거 / 존재 여부 확인**

---

## 🔥 7. HashMap (Key-Value)

```java
Map<String, Integer> map = new HashMap<>();

map.put("a", 1);
map.get("a");
map.containsKey("a");
map.remove("a");
```

### ✔️ 특징

* Key → Value 구조
* 탐색 빠름 O(1)

---

## 🔥 8. 자주 쓰는 패턴 (🔥 중요)

### ✔️ 빈도수 카운팅

```java
Map<String, Integer> map = new HashMap<>();

map.put(s, map.getOrDefault(s, 0) + 1);
```

---

### ✔️ 중복 제거

```java
Set<Integer> set = new HashSet<>(list);
```

---

## 🔥 9. 상황별 선택 기준

* **순서 + 인덱스 필요** → `ArrayList`
* **삽입/삭제 많음** → `LinkedList`
* **DFS / 역순** → `Stack`
* **BFS** → `Queue`
* **최소/최대값** → `PriorityQueue`
* **중복 제거** → `HashSet`
* **카운팅 / 매핑** → `HashMap`

---

## 🚀 한 줄 정리

👉 리스트 → `ArrayList`
👉 큐 → `Queue`
👉 스택 → `Stack`
👉 중복 제거 → `Set`
👉 키-값 → `Map`

---


````markdown
---

## 🔥 10. Deque (양방향 큐)

```java
Deque<Integer> dq = new ArrayDeque<>();

dq.addFirst(1);
dq.addLast(2);

dq.pollFirst();
dq.pollLast();

dq.peekFirst();
dq.peekLast();
````

### ✔️ 특징

* Stack + Queue 기능 모두 가능

* 양쪽에서 삽입/삭제 가능

* 👉 사용: **슬라이딩 윈도우 / 양방향 처리 / BFS 변형**
* 👉 팁: **Stack 대신 Deque 사용 추천**

---

## 🔥 11. TreeSet (정렬된 Set)

```java
TreeSet<Integer> set = new TreeSet<>();

set.add(3);
set.add(1);

set.first();   // 최소값
set.last();    // 최대값

set.higher(1); // 1보다 큰 값
set.lower(3);  // 3보다 작은 값
```

### ✔️ 특징

* 자동 정렬 (오름차순)

* 범위 탐색 가능

* 👉 사용: **정렬 유지 + 가까운 값 찾기**

---

## 🔥 12. TreeMap (정렬된 Map)

```java
TreeMap<Integer, String> map = new TreeMap<>();

map.put(3, "c");
map.put(1, "a");

map.firstKey();  // 최소 키
map.lastKey();   // 최대 키

map.higherKey(1);
map.lowerKey(3);
```

### ✔️ 특징

* Key 기준 자동 정렬

* 범위 탐색 가능

* 👉 사용: **정렬된 상태에서 Key-Value 관리**

---

## 🔥 13. Comparator (정렬 커스터마이징)

```java
Arrays.sort(arr, (a, b) -> b - a); // 내림차순
```

```java
list.sort((a, b) -> a[1] - b[1]);
```

### ✔️ 특징

* 정렬 기준 직접 정의 가능

* 👉 사용: **정렬 문제 필수**

---

## 🔥 14. 투 포인터 (Two Pointer)

```java
int left = 0;
int right = 0;

while (right < n) {
    // 조건 처리
    right++;
}
```

### ✔️ 특징

* 두 개의 포인터로 범위 탐색

* 👉 사용: **부분합 / 구간 탐색 / 정렬된 배열 문제**

---

## 🔥 15. 이분 탐색 (Binary Search)

```java
int left = 0;
int right = n - 1;

while (left <= right) {
    int mid = (left + right) / 2;

    if (arr[mid] == target) {
        // 찾음
    } else if (arr[mid] < target) {
        left = mid + 1;
    } else {
        right = mid - 1;
    }
}
```

### ✔️ 특징

* 탐색 시간 O(log N)

* 👉 사용: **탐색 범위 줄이기 / 최적값 찾기**

---

## 🔥 16. BFS / DFS 패턴

### ✔️ BFS (너비 우선 탐색)

```java
Queue<int[]> q = new LinkedList<>();
boolean[][] visited = new boolean[n][m];

q.offer(new int[]{0, 0});
visited[0][0] = true;

while (!q.isEmpty()) {
    int[] cur = q.poll();
}
```

---

### ✔️ DFS (깊이 우선 탐색)

```java
void dfs(int x, int y) {
    visited[x][y] = true;

    for (int d = 0; d < 4; d++) {
        // 이동
    }
}
```

### ✔️ 특징

* 그래프 탐색 기본

* 👉 사용: **그래프 / 미로 / 연결 요소 문제**

---

## 🔥 17. 문자열 뒤집기

```java
String reversed = new StringBuilder(s).reverse().toString();
```

---

## 🔥 18. char 배열 변환

```java
char[] arr = s.toCharArray();
```

### ✔️ 특징

* 문자열 수정 시 빠름

* 👉 사용: **문자열 직접 조작**

---

<br><br>

# 📌 주요 알고리즘


````markdown
---

## 🔥 19. Prefix Sum (누적합)

```java
int[] prefix = new int[n + 1];

for (int i = 1; i <= n; i++) {
    prefix[i] = prefix[i - 1] + arr[i - 1];
}

// 구간 합
int sum = prefix[r] - prefix[l - 1];
````

### ✔️ 특징

* 구간 합을 O(1)로 계산 가능

* 👉 사용: **구간 합 / 시간 단축 문제**

---

## 🔥 20. Sliding Window

```java
int sum = 0;
int left = 0;

for (int right = 0; right < n; right++) {
    sum += arr[right];

    while (sum > target) {
        sum -= arr[left++];
    }
}
```

### ✔️ 특징

* 구간을 유지하면서 이동

* 👉 사용: **부분합 / 연속된 구간 문제**

---

## 🔥 21. Greedy (탐욕법)

```java
Arrays.sort(arr);

int count = 0;
for (int i = 0; i < n; i++) {
    if (조건) {
        count++;
    }
}
```

### ✔️ 특징

* 매 순간 최선 선택

* 👉 사용: **정렬 + 선택 문제**

---

## 🔥 22. Backtracking

```java
void dfs(int depth) {
    if (depth == n) {
        return;
    }

    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            visited[i] = true;
            dfs(depth + 1);
            visited[i] = false;
        }
    }
}
```

### ✔️ 특징

* 모든 경우 탐색

* 👉 사용: **순열 / 조합 / 완전탐색**

---

## 🔥 23. Union-Find (Disjoint Set)

```java
int[] parent = new int[n];

int find(int x) {
    if (parent[x] == x) return x;
    return parent[x] = find(parent[x]);
}

void union(int a, int b) {
    a = find(a);
    b = find(b);

    if (a != b) parent[b] = a;
}
```

### ✔️ 특징

* 집합 합치기 / 연결 여부 확인

* 👉 사용: **그래프 / MST / 연결 요소**

---

## 🔥 24. Topological Sort (위상 정렬)

```java
Queue<Integer> q = new LinkedList<>();

while (!q.isEmpty()) {
    int cur = q.poll();

    for (int next : graph[cur]) {
        if (--indegree[next] == 0) {
            q.offer(next);
        }
    }
}
```

### ✔️ 특징

* 순서가 있는 그래프 정렬

* 👉 사용: **선후 관계 문제**

---

## 🔥 25. Dijkstra (최단 경로)

```java
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

pq.offer(new int[]{start, 0});

while (!pq.isEmpty()) {
    int[] cur = pq.poll();
}
```

### ✔️ 특징

* 가중치 있는 최단 경로

* 👉 사용: **그래프 최단 거리 문제**

---

## 🔥 26. DP (동적 계획법)

```java
int[] dp = new int[n + 1];

dp[0] = 0;
dp[1] = 1;

for (int i = 2; i <= n; i++) {
    dp[i] = dp[i - 1] + dp[i - 2];
}
```

### ✔️ 특징

* 이전 결과 재사용

* 👉 사용: **최적값 문제 / 경우의 수**

---

## 🔥 27. BitMask

```java
int bit = 0;

// 추가
bit |= (1 << i);

// 제거
bit &= ~(1 << i);

// 확인
if ((bit & (1 << i)) != 0) { }
```

### ✔️ 특징

* 상태를 비트로 관리

* 👉 사용: **부분집합 / 상태 압축**

---

## 🔥 28. LIS (최장 증가 부분 수열)

```java
int[] dp = new int[n];
Arrays.fill(dp, 1);

for (int i = 0; i < n; i++) {
    for (int j = 0; j < i; j++) {
        if (arr[j] < arr[i]) {
            dp[i] = Math.max(dp[i], dp[j] + 1);
        }
    }
}
```

### ✔️ 특징

* 증가하는 부분 수열

* 👉 사용: **DP + 수열 문제**

---

## 🔥 29. Permutation (순열)

```java
void perm(int depth) {
    if (depth == n) return;

    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            visited[i] = true;
            perm(depth + 1);
            visited[i] = false;
        }
    }
}
```

---

## 🔥 30. Combination (조합)

```java
void comb(int start, int depth) {
    if (depth == r) return;

    for (int i = start; i < n; i++) {
        comb(i + 1, depth + 1);
    }
}
```

---

## 🚀 최종 정리

👉 1~10 : 기본 문법
👉 10~18 : 자료구조 + 탐색
👉 19~30 : 알고리즘 핵심

