# DB Index

![DB INDEX](./img/index.png)

<br>

### DB Index?
- 인덱스(Index)란 추가적인 쓰기 작업과 저장공간을 활용하여 데이터베이스 테이블의 검색 속도를 향상시키기 위한 자료구조.
- DB는 데이터 양(row)에 따라 실행 결과 속도가 차이가 나는데 데이터 양이 늘어날 수록 실행속도는 느려지고 JOIN, sub query 사용에 데이터 양이 증가하는데 이에 Index를 적재적소에 사용하면 쿼리의 성능을 높일 수 있음
- 예시로는 우리가 책에서 원하는 내용을 찾을 때 책을 전부 찾는 것이 아닌 책의 색인을 보고 해당 페이지를 찾을 수 있는데 index는 여기서 색인과 같다고 함
- Index는 생성 시 데이터를 오름차순으로 정렬하기 때문에 정렬된 주소체계
- 옵티마이저가 가장 효율적인 방법으로 SQL을 수행할 최적의 처리 경로를 설정해주는 DBMS의 핵심 엔진으로 컴퓨터의 두뇌가 CPU가 한다면 DBMS의 두뇌는 옵티마이저라고 함

<br>

### Index 사용 예시
```sql
-- CREATE
CREATE INDEX [인덱스명] ON [테이블명](컬럼1, 컬럼2, 컬럼3.......)
CREATE INDEX EX_INDEX ON CUSTOMERS(NAME,ADDRESS); 
CREATE[UNIQUE] INDEX EX_INDEX ON CUSTOMERS(NAME,ADDRESS); -- 컬럼 중복 X

-- SELECT
SELECT * FROM USER_INDEXES WHERE TABLE_NAME = 'CUSTOMERS';
SELECT * FROM ALL_IND_COLUMNS WHERE TABLE_NAME = 'CUSTOMERS';

-- DELETE
DROP INDEX [인덱스 명]
DROP INDEX EX_INDEX;

-- UPDATE
DROP INDEX [기존 인덱스 명] TO [바뀔 인덱스 명]
ALTER INDEX EX_INDEX RENAME TO EX_INDEX_NEW

-- REBUILD
ALTER INDEX [인덱스명] REBUILD;
ALTER INDEX EX_INDEX REBUILD;
```

<br>

### Index 장점
- 위에도 적어놨듯이 가장 큰 특징은 데이터들이 정렬되어 있다는 것. Index의 장점은 모두 데이터들이 정렬되었다는 장점을 관통하는 내용임
- 전반적인 시스템의 부하를 줄이고 테이블 조회 속도와 그에 따른 성능 향상
#### 1. 조건 검색 WHERE 절의 효율성 
        - 테이블을 만들고 데이터가 쌓이게 되면 테이블의 row는 내부적으로 순서가 없이 뒤죽박죽으로 저장되게 됨. 
        - 위의 상태에서 WHERE 절은 특정 조건에 맞는 데이터를 찾아낼 때도 처음부터 끝까지 검색조건과 맞는지 비교하는데 이것을 풀 테이블 스캔(Full Table Scan) 줄여서 풀 스캔(Full Scan)이라고함
        - 그러나 인덱스 테이블 스캔(Index Table Scan) 시 인덱스 테이블은 데이터들이 정렬되어 저장되어 있기 때문에 해당 조건(WHERE)에 맞는 데이터들을 빠르게 찾아낼 수 있음. 이것이 인덱스를 사용하는 가장 큰 이유
#### 2. 정렬 ORDER BY 절의 효율성
        - 인덱스를 사용하면 ORDER BY에 의한 정렬(Sort)과정을 피힐 수 있음. ORDER BY는 굉장히 부하가 많이 걸리는 작업
        - 정렬과 동시에 1차적으로 메모리에서 정렬이 이루어지고 메모리보다 큰 작업이 필요하다면 디스크I/O도 추가적으로 발생하기 때문
        - 인덱스를 사용하면 위의 자원소모 없이 정렬이 되어 있기 때문에 가져오기만 하면 됨
#### 3. MIN, MAX의 효율적 처리
        - 마찬가지로 데이터 정렬으로 얻는 장점으로 MIN값과 MAX값을 레코드의 시작 값과 끝 값 한건 씩만 가져오면 되기 때문에 Full Table Scan으로 테이블을 모두 뒤져서 작업하는 것보다 훨씬 효율적으로 찾을 수 있음

<br>

### Index 단점
- 인덱스의 가장 큰 문제도 정렬이다. 정렬된 상태를 계속 유지시켜줘야 한다는 점에서 레코드 내에 데이터 값이 바뀌는 부분이라면 악영향을 끼침
- 인덱스를 관리하기 위해서는 추가 적업이 필요함. 인덱스를 잘못 사용할 경우 오히려 성능이 저하되는 역효과 발생
#### 1. 인덱스는 DML에 취약
        - INSERT, UPDATE, DELETE를 통해 데이터가 추가되거나 값이 바뀐다면 인덱스 테이블 내에 있는 값들을 다시 정렬 해야함
        - 인덱스 테이블, 원본 테이블 두 군데의 데이터 수정 작업을 진행해야 함
        - 그렇기 때문에 DML이 빈번한 테이블보다 검색을 위주로 하는 테이블에 인덱스를 생성하는 것이 좋음
#### 2. 인덱스 스캔이 무조건 옳은 것은 아님
        - 인덱스를 사용하느 경우는 인덱스가 테이블 전체 데이터 중에서 10~15% 이하의 데이터를 처리하는 경우에만 효율적이고 그 이상의 데이터를 처리할 땐 사용하지 않는 것이 좋다고 함
        - 직관적인 예시로는 1개의 데이터가 들어있는 테이블과 100만개의 데이터가 들어있는 테이블이 있다고 한다면 100만개의 데이터가 있는 테이블은 인덱스 스캔이 1개의 데이터가 있는 테이블은 풀스캔이 빠름
#### 3. 속도 향상 목적으로 지나친 인덱스 생성은 좋지 않음
        - 인덱스를 관리하기 위해서는 데이터베이스의 약 10%에 해당하는 저장공간이 추가로 필요
        - 속도 향상에 비해 단점들의 COST를 비교해서 인덱스를 만들지를 정해야 함

>#### Index 남발하지 않아야 하는 이유
>- 데이터베이스 서버에 성능 문제 발생시 가장 빨리 생각하는 해결책이 인덱스 추가 생성
>- 문제가 발생 시 마다 인덱스를 생성하면 인덱스가 쌓여가기 때문에 하나의 쿼리문을 빠르게 만들수는 있으나 전체적인 데이터베이스의 성능 부하를 초래
>- 많은 인덱스가 쌓일 경우 DML 부하가 발생하여 전체적인 데이터베이스 성능 저하
>- 인덱스 생성은 그렇기 때문에 좀 더 효율적으로 짜는 방향으로 가야하고 인덱스 생성은 마지막 수단으로 강구해야 한다

<br>

### Index 관리
- 인덱스를 사용하는 경우에 항상 최신의 데이터를 정렬된 상태로 유지해야 하므로 인덱스가 적용된 컬럼에 INSERT, UPDATE, DELETE가 수행될 때 마다 계속 정렬해야하고 그에 따른 부하 발생
- 이를 최소화하기 위해 인덱스는 '데이터 삭제'라는 개념에서 '인덱스를 사용하지 않는다'라는 작업으로 이를 대신한다고 함
- 인덱스 파일은 생성 후 DML이 반복되면 성능이 저하되는데 생성된 인덱스는 트리구조를 가지고 오랫동안 반복 시 한쪽이 무거워져 전체적으로 트리의 깊이가 깊어지기 때문에 인덱스의 검색속도가 떨어지므로 주기적으로 리빌딜 작업을 거치는 것이 좋음
>- INSERT : 새로운 데이터에 대한 인덱스를 추가
>- DELETE : 삭제하는 데이터의 인덱스를 사용하지 않는다는 작업을 진행
>- UPDATE : 기존의 인덱스를 사용하지 않음 처리하고 갱신된 데이터에 대해 인덱스를 추가

<br>

### Index 생성 전략
- 생성된 인덱스를 가장 효율적으로 사용하는 방법은 데이터의 분포도는 최대한으로 그리고 조건절에 호출 빈도는 자주 사용하는 컬럼을 인덱스로 생성하는 것이 좋음
- 인덱스는 특정 컬럼을 기준으로 생성하고 기준이 된 컬럼으로 정렬이된 인덱스 테이블이 생성되는데 이 기준 컬럼은 최대한 중복이 되지않는 값이 좋음 -> 가장 최선은 PK로 인덱스를 거는 것
- 중복값이 없는 인덱스 테이블이 최적의 효율을 발생시키고, 반대로 모든 값이 같은 컬럼의 인덱스 컬럼이 된다면 인덱스로써의 가치가 없다고 함
>1. 조건절에 자주 등장하는 컬럼
>2. 항상 = 으로 비교되는 컬럼
>3. 중복되는 데이터가 최소한인 컬럼 (분포도가 좋은 컬럼 )
>4. ORDER BY 절에서 자주 사용되는 컬럼
>5. JOIN 조건으로 자주 사용되는 컬럼
>6. DML이 자주 발생하지 않는 컬럼

<br>

### 인덱스 구조
- B TREE 인덱스 중 가장 많이 사용하는 것은 B+Tree와 B*Tree
- 주 인덱스 유형은 해시테이블, B TREE (Balanced Tree)

<br>

![Index Hash Table](./img/index_hashTtable.png)
#### 1. 해시 테이블 ( Hash Table )
- Key-Value 형태로 데이터를 저장하는 자료 구조
- 내부적으로 배열 (버킷)을 이용하여 데이터를 저장하기 때문에 빠른 검색 속도 제공
- 해시 함수(Hash Function)을 이용하여 Key를 사용해 index를 계산하는 계산식으로 적절한 index를 찾아 Key 충돌을 줄여 빠른 속도를 유지할 수 있고 Key 충돌 있는 경우는 부가적인 처리 필요
- 데이터 탐색시 해시 함수를 통해 Key에 해당하는 index값을 구함
- index를 이용하여 배열에 저장된 value에 접근하기 때문에 해시 테이블의 평균 시간복잡도는 O(1)

<br>

![Index B-Tree](./img/index_btree.png)
![Tree Balance](./img/tree_balance.png)

#### 2. B-Tree ( Balanced Tree )
- 트리를 구성하는 아이템을 노드라고 하는데 B-Tree는 자식 노드의 개수가 2개 이상인 트리를 말함
- 가장 상단을 구성하는 루트노드(root node), 중간에 위치한 브랜치 노드(branch node), 마지막에 위치한 리프 노드(leaf node)로 구성
- 트리의 차수에 따라 노드 내 최대 Key-Value 수가 달라짐 -> Key-Value수 2개는 2차 B-Tree, 3개일 경우는 3차 B-Tree...
- 균형 트리(Balanced Tree)로 루트 노드에서 리프 노드까지의 거리가 모두 동일
- DML에 따라 시간이 지나면서 데이터의 균형이 깨지면서 성능이 악화됨


<br>

#### 3. B+Tree 
- B-Tree의 확장 개념으로 B-Tree와 다르게 브랜치 노드는 Value에 대한 정보가 존재하지 않고 단순히 Key 값만 존재
- 리프 노드(leaf node)에서만 Value를 관리
- 리프 노드들은 LinkedList 구조로 서로를 참조하고 있기 때문에 B-Tree보다 노드 순회가 쉬움
- 리프 노드가 아닌 자료는 인덱스 노드(포인터 주소 존재)라고 부르고 리프 노드 자료는 데이터 노드(데이터 존재)라고 부름
- 모든 리프노드는 같은 레벨에 존재
- 브랜치 노드에 Value가 없어서 B-Tree보다 차지하는 메모리가 적지만 Value를 찾기위해 리프 노드 까지 이동해아 함
- 브랜치 노드와 리프 노드에 모두 Key가 존재하므로 Key 중복이 발생
- 리프 노드에 데이터가 없으면 디스크에서 탐색 진행 

<br>

#### 4. B*Tree
- 노드의 추가적인 생성과 추가적인 연산의 최소화를 위해 생성
- B-Tree는 최소 M/2개의 키값을 가졌던 기존 노드의 자식 노드 수 최소 조건이 2M/3개로 늘어남
- 노드가 가득차면 분열 대신 이웃한 형제 노드로 재배치 ( 재배치 불가능 시점에서 분열 )
- 각 노드의 자료는 정렬되어 있으나 자료는 중복되지 않음
- 모든 리프노드는 같은 레벨에 존재
- root node가 아닌 노드들은 적어도 2[(M-2)/3]+1개의 자식 노드를 가지고 있음 (최대 M개)


<br>

추가해야 하는 내용 B트리 내용 + 카디널리티 + 인덱스 탐색 방법 + 인덱스 메타 데이터 (myi) https://solbel.tistory.com/739

<div style="text-align: right">22-07-08</div>

-------

## Reference
- https://coding-factory.tistory.com/746
- https://coding-factory.tistory.com/419
- https://mangkyu.tistory.com/96
- https://junhyunny.github.io/information/data-structure/db-index-data-structure/
- https://ssocoit.tistory.com/217
- https://velog.io/@emplam27/자료구조-그림으로-알아보는-B-Tree (+ 추가예정)
- https://yurimkoo.github.io/db/2020/03/14/db-index.html (+ 추가예정)