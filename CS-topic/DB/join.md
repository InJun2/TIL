# JOIN

### JOIN
- 둘 이상의 테이블에서 데이터가 필요한 경우 테이블 조인이 필요
- 일반적으로 조인 조건을 포함하는 WHERE 절을 작성해야함
- 조인 조건은 일반적으로 각 테이블의 PK 및 FK로 구성됨

<br>

### JOIN의 종류
- INNER JOIN
- OUTTER JOIN
    - LEFT OUTER JOIN
    - RIGHT OUTER JOIN

### 조건 명시에 따른 구분
- NATURAL JOIN
- CROSS JOIN(FULL JOIN, CARTESIAN JOIN)

<br>

### JOIN 주의 사항
- 조인의 처리는 어느 테이블을 먼저 읽을지를 결정하는 것이 중요 (처리할 작업량이 상당히 달라짐)
- INNER JOIN : 어느 테이블을 먼저 읽어도 결과가 달라지지 않아 MySQL 옵티마이저가 조인의 순서를 조절해서 다양한 방법으로 최적화를 수행할 수 있음
- OUTER JOIN : 반드시 OUTER가 되는 테이블을 먼저 읽어야 하므로 옵티마이저가 조인 순서를 선택할 수 없음

<br>

### INNER JOIN
- 가장 일반적인 JOIN의 종류로 교집합임
- 동등 조인(Equi-Join)이라고도 하며, N개의 테이블 조인 시 N-1개의 조인 조건이 필요
- 