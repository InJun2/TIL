# 옵티마이저와 힌트

### 1주차 생각해볼만한 부분
- InnoDB 스토리지 엔진의 백그라운드 스레드가 연속된 페이지를 읽는 리드 어헤드 (Read ahead) 작업
  - 어떤 영역의 데이터가 앞으로 필요해지리라는 것을 예측해서 요청이 오기 전에 미리 디스크에서 읽어 InnoDB의 버퍼 풀에 가져다 두는 것
  - innodb_read_ahead_threshold 시스템 변수를 이용해 InnoDB 스토리지 엔진이 언제 어헤드를 시작할지 임계값을 설정할 수 있음 ( 시스템 변수에 설정된 개수만큼 연속된 페이지가 읽히면 버퍼 풀에 적재 )
- InnoDB는 테이블 풀 스캔을 진행할 때 디스크로부터 페이지를 여러개 읽어온다
  - 테이블 풀 스캔이 실행되면 처음 몇 개의 데이터 페이지는 포그라운드 스레드가 페이지 읽기를 실행하지만 특정 시점부터는 읽기 작업을 백그라운드 스레드로 넘김
  - 하지만 몇개를 읽을지 설정하는 시스템 변수는 없고 백그라운드 스레드가 읽기를 넘겨받는 시점부터 한 번에 4개 또는 8개씩 읽으면서 계속 그 수를 증가시켜 최대 64개의 데이터 페이지까지 읽어 버퍼 풀에 저장함
  - 포그라운드 스레드는 해당 버퍼 풀에 준비된 데이터를 가져다 사용하기만 하면되므로 상당히 빨리 처리됨
- select count(*) from employees; 처럼 테이블의 레코드 건수를 조회하면 풀 테이블 스캔 대신 풀 인덱스 스캔을 하게될 가능성이 높음
- 원 패스, 투 패스 정렬 방식이란?
- 네스티드-루프 조인 방식이란?
  - 한 테이블의 각 행을 다른 테이블의 각 행과 비교하여 조인 조건을 만족하는지 확인하는 방식
- 정렬 처리 방식
  - 스트리밍 방식 : 쿼리를 처리하는 동안 실시간으로 레코드를 사용자에게 전송
  - 버퍼링 방식 : ORDER BY, GROUP BY 를 사용한 쿼리는 조건에 일치하는 모든 레코드를 가져온 후 정렬하거나 그루핑해서 차례대로 보내야하므로 결과를 모았다가 사용자에게 전송
- 드라이빙 테이블과 드리븐 테이블이란 및 임시 테이블이란?

<br>

### 1주차 생각해볼만한 부분
- 세미 조인이란?



<br>

### 세미조인
세미 조인이란 다른 테이블에서 조건이 일치하는 레코드가 있는지 없는지만 체크하는 형태의 쿼리
- 세미 쿼리 최적화 방법은 세미 조인 형태의 쿼리와 안티 세미 조인 형태의 쿼리에 차이가 존재
  - 해당 책에서는 최근 도입된 세미 조인 최적화에서만 살펴보았음
- 세미 조인 쿼리 최적화 방법
  - 세미 조인 최적화
  - IN-to-EXISTS 최적화
  - MATERIALIZATION 최적화
- 안티 세미 조인 쿼리 최적화
  - IN-to-EXISTS 최적화
  - MATERIALIZATION 최적화
- 이 중 세미 조인 최적화는 아래 최적화 전략들을 모아 세미 조인 최적화라고 부름
  - Table Pull-out
  - Duplicate Weed-out : materialization 옵티마이저 옵션으로 사용 여부 결정
  - First Match : firstmatch 옵티마이저 옵션으로 사용 여부 결정
  - Loose Scan : loose scan 옵티마이저 옵션으로 사용 여부 결정
  - Materialization : materialization 옵티마이저 옵션으로 사용 여부 결정

```sql
-- 세미 조인 최적화 차이
SELECT *
FROM employees e
WHERE e.emp_no IN
  (SELECT de.emp_no FROM dept_emp de WHERE de.from_date='1995-01-01');
-- 해당 경우 세미조인이 최적화 되어있지 않다면 mysql에서는 employ를 풀 테이블 스캔하면서 서브쿼리가 일치하는 경우를 비교하므로 매우 낮은 성능
-- 세미 조인 최적화의 경우 서브쿼리를 먼저 가져오고 가져온 데이터에서 쿼리 처리 진행
```

<br>

### 테이블 풀-아웃 (Table Pull-out)
- 세미 조인의 서브쿼리에 사용된 테이블을 아우터 쿼리로 끄집어낸 후에 쿼리 를 조인 쿼리로 재작성하는 형태
- 사용 가능하면 항상 세미 조인보다 좋은 성능을 내어 별도 제어하는 옵티 마이저 옵션을 제공하지 않음

```sql
EXPLAIN
SELECT * FROM employees e
WHERE e.emp_no IN (SELECT de.emp_no FROM dept_emp de WHERE de.dept_no='d009');

```

<br>

### 중복 제거

<br>

### 퍼스트 매치

<br>

### 루스 스캔

<br>

### 구체화

<br>

## 연습문제

### 1. 드라이빙 테이블과 드리븐 테이블이란?

<details>
<summary>정답</summary>

- 드라이빙 테이블과 드리븐 테이블이란 용어는 조인 연산의 실행 계획과 관련이 있는데 주로 쿼리 최적화와 실행 계획을 이해할 때 사용됨
- 드라이빙 테이블과 드리븐 테이블을 결정하는 조건은 데이터베이스 옵티마이저가 쿼리 실행계획을 세우는 과정에서 사용되며 기준은 다음과 같음
  - 테이블 크기 : 일반적으로 작은 테이블이 드라이빙 테이블로 선택됨. 이후에 조인 연산에서 더 작은 데이터 세트를 기반으로 하여 처리하여 성능이 향상
  - 인덱스 사용 가능성 : 해당 쿼리의 조건에 적절한 인덱스를 가지고 있는 테이블로 선택될 가능성이 높으며 인덱스를 사용하여 특정 행을 빠르게 찾을 수 있어 성능이 향상
  - 조건 필터링 : 필터링 조건이 많은 행을 제외할 수 있는 테이블이 드라이빙 테이블로 선택됨. 이는 드라이빙 테이블이 작은 결과 세트를 생성하여 드리븐 테이블과의 조인 작업을 최적화에 도움을 줌
  - 그 외에는 조인의 유형과 순서, 통계 정보를 통해 드라이빙 테이블을 선택할지 결정함. 해당 조건은 위의 조건들을 포함하였을 때 조인의 유형과 순서, 통계에 따라 추가적으로 드라이빙 테이블을 선택하는 요인이 됨

#### 드라이빙 테이블 (Driving Table)
- 조인 연산에서 먼저 접근하는 테이블
- 주로 더 작은 테이블이나 조건에 맞는 인덱스를 사용하는 테이블이 선택됨
- 쿼리 실행 시 가장 먼저 처리되며 다른 테이블과의 조인 시 기준이 됨

<br>

#### 드리븐 테이블(Driven Table)
- 드라이빙 테이블과 조인되는 테이블
- 드라이빙 테이블의 결과와 결합되어야 하며, 더 큰 테이블이 선택되는 경우가 많음
- 드라이빙 테이블에서 검색된 각 행에 대해 반복적으로 접근됨

</details>

<br>

### 2. 임시 테이블이란?

<details>
<summary>정답</summary>

- 데이터베이스에서 쿼리 실행 중에 일시적으로 데이터를 저장하고 조작하기 위해 사용되는 테이블
- 드라이빙 테이블과 드리븐 테이블의 조인 과정에서도 임시 테이블이 생성될 수 있음

#### 임시 테이블의 역할
- 중간 결과 저장
  - 복잡한 쿼리의 중간 결과를 저장하기 위해 임시 테이블을 사용하여 여러번의 동일한 계산 방지
- 성능 최적화
  - 복잡한 조인이나 서브쿼리의 결과를 임시 테이블에 저장한 후, 이를 기반으로 추가 연산을 수행하면 성능이 향상될 수 있음
- 스코프 제한
  - 임시 테이블은 세션 단위로 생성되며, 세션이 종료되면 자동으로 삭제되므로 다른 세션이나 사용자에게 영향을 미치지 않음

<br>

#### 임시 테이블 사용 예시
- 큰 테이블의 데이터를 부분적으로 조작할 때
- 서브쿼리 결과를 저장하고 이를 재사용할 때
- 집계 함수 결과를 임시 저장하고 후속 처리할 때

<br>

#### 임시 테이블이 필요한 쿼리
- ORDER BY와 GROUP BY에 명시된 칼럼이 다른 쿼리
- ORDER BY나 GROUP BY에 명시된 칼럼이 조인의 순서상 첫 번째 테이블이 아닌 쿼리
- DISTINCT와 ORDER BY가 동시에 쿼리에 존재하는 경우 또는 DISTINCT가 인덱스로 처리되지 못하는 쿼리
- UNION이나 UNION DISTINCT가 사용된 쿼리
- 쿼리의 실행 계획에서 select_type이 DERIVED인 쿼리

<br>

#### 메모리 임시테이블과 디스크 임시 테이블
- 메모리 임시테이블은 메모리에 저장되어 작동하므로 매우 빠른 읽기/쓰기 성능을 제공
- 디스크 임시테이블은 시스템의 디스크에 저장되어 디스크 I/O에 의존하므로 느리지만 대용량의 데이터 세트를 처리할 때 사용됨

</details>