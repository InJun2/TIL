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
* 👉 사용: **문자열 숫자 합**

```java
int sum = 0;
for (char c : s.toCharArray()) {
    sum += c - '0';
}
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

## 한 줄 간단 클래스 정리

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

### ✔️ 리스트 계열 Collections

```java
// Collections.sort(List<T> list)
// ArrayList, LinkedList, Stack, Vector 등
Collections.sort(list)      // 정렬
Collections.reverse(list)   // 역정렬
```

### ✔️ Collection 인터페이스 구현체

```java
Collections.max(list)       // 최대값
Collections.min(list)       // 최소값
```

## 🔥 1. ArrayList (동적 리스트)

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

## 🔥 2. LinkedList (연결 리스트)

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
// 레거시 클래스
Stack<Integer> s = new Stack<>();

// 더 빠름
Deque<Integer> stack = new ArrayDeque<>();

stack.push(1);  // 앞에 넣음
stack.push(3);  // 앞에 넣음
stack.peek();   // 앞쪽값 : 3
stack.peekFirst(); // 앞쪽값 : 3
stack.peekLast();  // 뒤쪽값 : 1
stack.pop();       // 앞쪽값 제거 : 3 반환, 비었을때 예외 발생
```

* 👉 사용: **괄호 문제 / DFS / 역순 처리**

---

## 🔥 4. Queue (FIFO)

```java
// 노드 기반, 메모리 비효율
Queue<Integer> q = new LinkedList<>();

Queue<Integer> queue = new ArrayDeque<>();

queue.offer(1);  // 뒤에 넣음
queue.offer(3);  // 뒤에 넣음
queue.peek();   // 앞쪽값 : 1
queue.peekFirst(); // 앞쪽값 : 1
queue.peekLast();  // 뒤쪽값 : 3
queue.poll();       // 앞쪽값 제거 : 1 반환, 비었을때 null 반환
```

* 👉 사용: **BFS / 순서 처리**

---

## 🔥 5. PriorityQueue (우선순위 큐)

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();

pq.offer(3);
pq.offer(1);
pq.poll(); // 가장 작은 값 : 1
```

### ✔️ 최대 힙

```java
PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

pq.offer(3);
pq.offer(1);
pq.poll(); // 가장 큰 값 : 3
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

## 🔥 자주 쓰는 패턴

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

## 🚀 상황별 선택 기준

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

## 🔥 8. Deque (양방향 큐)

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
* 👉 추천: **Stack, Queue 대신 Deque 사용 추천**

---

## 🔥 9. TreeSet (정렬된 Set)

```java
TreeSet<Integer> set = new TreeSet<>();

set.add(3);
set.add(2);
set.add(1);

set.first();   // 최소값 : 1
set.last();    // 최대값 : 3

set.higher(2); // 2보다 큰 가장 가까운 값 : 3
set.lower(2);  // 2보다 작은 가장 가까운 값 : 1

set.tailSet(2);   // 2 이상 모든 값 : 2, 3
set.headSet(2);   // 2 미만 모든 값 : 1
```

### ✔️ 특징

* 자동 정렬 (오름차순)

* 범위 탐색 가능

* 👉 사용: **정렬 유지 + 가까운 값 찾기**

---

## 🔥 10. TreeMap (정렬된 Map)

```java
TreeMap<Integer, String> map = new TreeMap<>();

map.put(3, "c");
map.put(2, "b");
map.put(1, "a");

map.get(1);        // "a"
map.firstKey();    // 최소키 : 1
map.lastKey();     // 최대키 : 3

map.higherKey(2);  // 2보다 큰 가장 가까운 값 : 3
map.lowerKey(2);   // 2보다 작은 가장 가까운 값 : 1

map.tailMap(2);    // 2이상 모든 값 : 2, 3
map.headMap(2);    // 2미만 모든 값 : 1
```

### ✔️ 특징

* Key 기준 자동 정렬

* 범위 탐색 가능

* 👉 사용: **정렬된 상태에서 Key-Value 관리**

---

## 🔥 11. Comparator (정렬 커스터마이징)

```java
// int[]의 경우 comparator 못쓰므로 Integer[]로 사용할 것
Arrays.sort(arr);   // 오름차순
Arrays.sort(arr, (a, b) -> b - a);  // 내림차순

Collections.sort(list);     // 오름차순
Collections.sort(list, (a, b) -> b - a);    // 내림차순
Collections.reverse(list);     // 뒤집기 ( List만 가능 )


list.sort(null);    // 오름차순
list.sort((a, b) -> a[1] - b[1]);    // 특정 인덱스([1]) 값 기준 오름차순
list.sort((a, b) -> b[1] - a[1]);    // 특정 인덱스([1]) 값 기준 내림차순

PriorityQueue<Integer> minPq =
    new PriorityQueue<>();  // 최소 힙
PriorityQueue<Integer> maxPq =
    new PriorityQueue<>((a, b) -> b - a); // 최대 힙

TreeSet<Integer> ascSet =
    new TreeSet<>();    // 오름차순
TreeSet<Integer> descSet =
    new TreeSet<>((a, b) -> b - a); // 내림차순

TreeMap<Integer> ascMap=
    new TreeMap<>();    // 오름차순
TreeMap<Integer> descMap =
    new TreeMap<>((a, b) -> b - a); // 내림차순

```

### ✔️ 특징

* 정렬 기준 직접 정의 가능
* 리스트에서 한번만 뒤집는 용도로는 Collections.reverse(list) 사용
    - 리스트 인터페이스 상속받은 자료구조만 가능
* '(a,b) -> b - a' 방식 보다는 (a,b) -> Integer.compare(b,a) 같은 방식이 더 권장됨
* 👉 사용: **정렬 문제 필수**

---

<br><br>

# 📌 주요 알고리즘

---

## 🔥 1. 그래프 탐색 (BFS/DFS)

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

---

## 🔥 2. 투 포인터 (Two Pointer)

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

## 🔥 3. 이분 탐색 (Binary Search)

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



## 🔥 4. 문자열 뒤집기

```java
String reversed = new StringBuilder(s).reverse().toString();
```

---

## 🔥 5. Prefix Sum (누적합)

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

## 🔥 6. Sliding Window

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

## 🔥 7. Greedy (탐욕법)

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
* 매번 사용해야 하는 알고리즘이 다름
* 👉 사용: **정렬 + 선택 문제**

---

## 🔥 8. Backtracking

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

## 🔥 9. Union-Find (Disjoint Set)

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

## 🔥 10. Topological Sort (위상 정렬)

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

## 🔥 11. Dijkstra (최단 경로)

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

## 🔥 12. DP (동적 계획법)

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

## 🔥 13. BitMask

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

## 🔥 14. LIS (최장 증가 부분 수열)

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

## 🔥 15. Permutation (순열)

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

## 🔥 16. Combination (조합)

```java
void comb(int start, int depth) {
    if (depth == r) return;

    for (int i = start; i < n; i++) {
        comb(i + 1, depth + 1);
    }
}
```
