# SQL (Structrued Query Language)

### SQL 이란?
- 구조적 질의 언어로 해당 질의 언어를 통해 데이터 베이스를 제어하고 관리
- 관계형 데이터베이스 시스템(RDBMS)에서 자료를 관리 및 처리하기 위해 설계된 언어 -> 관계형 모델 이론에서 파생됨
- 현재 SQL 표준으로 ANSI SQL이 정립됨

<br>

### SQL 언어적 특성
1. SQL은 대소문자를 가리지 않음
2. SQL 명령은 반드시 세미콜론(;)으로 끝나야 함
3. 고유의 값은 따옴표로 감싸줌('')
4. 객체를 나타낼 때는 백틱(``)으로 감싸 줌
5. 한줄 주석은 -- 를 사용하고, 여러 줄 주석은 /* */ 으로 감싸주면 됨

<br>

### SQL 문의 종류
- DDL (Data Definition Language, 데이터 정의어) : 데이터를 정의하는 언어. 데이터를 생성, 수정, 삭제하는 등의 데이터의 골격을 결정하는 역할
    - 릴레이션 정의
- DML (Data Manipulation Language, 데이터 조작어) : 정의된 데이터베이스에 입력된 레코드를 조회하거나 수정하거나 삭제하는 등의 역할
    - 데이터 관리
- DCL (Data Control Language, 데이터 제어어) : 데이터를 제어하는 언어. 데이터베이스에 접근하거나 권한을 주는 등의 역할을 하는 언어
    - 관리하고 접근하는 권한

<br>

### DDL (Data Definition Language, 데이터 정의어) 언어 종류
- CREATE : 데이터베이스, 테이블 등을 생성하는 역할 
- ALTER : 테이블을 수정하는 역할
- DROP : 데이터베이스, 테이블을 삭제하는 역할
- TRUNCATE : 테이블을 초기화 시키는 역할 -> 로그를 기록하지 않음

<br>

```sql
-- 데이터베이스/ 테이블 생성
CREATE DATABASE 데이터베이스이름;
CREATE TABLE 테이블이름;

CREATE TABLE 테이블이름 ( 
	필드이름1 필드타입, 
	필드이름2 필드타입, …
 )

-- 테이블 이름 수정
ALTER TABLE 테이블이름 RENAME TO 새로운 테이블이름;
-- 테이블 컬럼 추가
ALTER TABLE 테이블이름 ADD 컬럼명 컬럼타입;
-- 테이블 컬럼 변경
ALTER TABLE 테이블이름 MODIFY COLUMN 컬럼명 NEW 컬럼명 NEW 컬럼타입;
-- 테이블 컬럼 삭제
ALTER TABLE 테이블이름 DROP 컬럼명;

-- 테이블/데이터베이스 제거
DROP DATABASE [IF EXISTS] 데이터베이스 이름;
DROP TABLE [IF EXISTS] 테이블 이름;
```

<br>

### DML (Data Manipulation Language, 데이터 조작어) 언어 종류
- SELECT : 데이터를 조회하는 역할
- INSERT : 데이터를 삽입하는 역할
- UPDATE : 데이터를 수정하는 역할
- DELETE : 데이터를 삭제하는 역할

<br>

```sql
-- 데이터 조회
SELECT 조회컬럼명[집계함수] FROM 테이블이름 [WHERE 조건] [GROUP BY 속성 [having 그룹조건]] [ORDER BY 속성[정렬기준]];

-- 데이터 삽입
INSERT INTO 테이블이름 VALUES (데이터값1, 데이터값2 …);
INSERT INTO 테이블이름 [(컬럼이름1, 컬럼이름2 …)] VALUES (데이터값1, 데이터값2 …);

-- 데이터 변경
UPDATE 테이블이름 SET 컬럼이름1=데이터값1, 컬럼이름2=데이터값2 [WHERE 조건];

-- 데이터 삭제
DELETE FROM 테이블이름 [WHERE 컬럼이름=데이터값];
```

<br>

### DCL (Data Control Language, 데이터 조작어) 언어 종류
- GRANT : 특정 데이터베이스 사용자에게 특정 작업에 대한 수행 권한 부여
- REVOCK : 특정 데이터베이스 사용자에게 특정 작업에 대한 수행 권한을 박탈, 회수
- COMMIT : 트랜잭션의 작업이 정상적으로 완료되었음을 관리자에게 알려줌
- ROLLBACK : 트랜잭션의 작업을 취소 및 원래대로 복구하는 역할을 함

<br>

```sql
-- 시스템 권한 부여
GRANT 권한 TO 사용자명;
-- 객체 권한 부여
GRANT 권한 ON 객체명 TO 사용자명;

-- 시스템 권한 회수
REVOKE 권한 FROM 사용자명;
-- 객체 권한 회수
REVOKE 권한 ON 객체 FROM 사용자명;

-- 커밋
COMMIT;

-- 롤백
ROLLBACK;
```

<br>

#### DCL 권한 유형
권한 ROLE
- DBA: 모든 DB 권한 가짐 -> DB 관리자에게만 부여
- RESOURCE: 객체(CREATE, ALTER, DROP), 데이터(SELECT, INSERT, UPDATE, DELETE) 권한
- CONNECT: 단순 접속 권한

<br>

- 시스템 권한
    - CREATE USER : 계정 생성 권한
    - DROP USER : 계정 삭제 권한
    - DROP ANY TABLE : 테이블 삭제 권한
    - CREATE SESSION : 데이터베이스 접속 권한
    - CREATE TABLE : 테이블 생성 권한
    - CREATE VIEW : 뷰 생성 권한
    - CREATE SEQUENCE : 시퀀스 생성 권한
    - CREATE PROCEDURE : 함수 생성 권한
- 객체 권한
    - ALTER : 테이블 변경 권한
    - INSERT : 데이터 조작 권한
    - DELETE : 데이터 조작 권한
    - SELECT : 데이터 조작 권한
    - UPDATE : 데이터 조작 권한
    - EXECUTE : PROCEDURE 실행 권한

<br>

#### DCL 접근 통제
- 데이터베이스의 보안을 구현하는 방법으로 접근 통제 방법을 사용 -> 데이터 베이스 관리 시스템(DBMS)에서 DAC 채택
- DCL은 데이터베이스 관리, 특히 접근 통제 용도로 SQL에서 사용하는 명령어
- 보안 정책에 따라 접근 객체에 대한 접근주체의 접근 권한 확인 및 이를 기반으로한 접근 제어를 통해 자원에 대한 비인가된 사용을 방지하는 정보보호 기능
#### 접근 통제 유형
- 임의 접근 통제(DAC : Discretionary Access Control)
    - 시스템 객체에 대한 접근을 개인 또는 그룹의 식벽자를 기반으로 제한하는 방법
    - 여기서 임의적이라는 말은 어떤 종류의 접근 권한을 갖는 사용자는 다른 사용자에게 자신의 판단에 의해서 권한을 줄 수 있다는 것
    - 주체와 객체의 신분 및 임의적 접근 통제 규칙에 기초하여 객체에 대한 주체의 접근을 통제하는 기능
    - 통제 권한이 주체에 있음
    - 주체가 임의적으로 접근 통제 권한을 배분하여 제어할 수 있음
- 강제 접근 통제(MAC : Mandatory Access Control)
    - 정보시스템 내에서 어떤 주체가 특정 객체에 접근하려 할 때 양쪽의 보안레이블에 기초하여 높은 보안 수준을 요구하는 정보(객체)가 낮은 보안 수준의 주체에게 노출 되지 않도록 제한하는 통제 방법
    - 통제 권한이 제 3자에게 있음
    - 주제는 접근 통제 권한과 무관

<br>

<div style="text-align: right">22-08-10</div>

-------

## Reference
- https://velog.io/@developerjun0615/SQL-DDL-DML-DCL-이란
- https://edu.goorm.io/learn/lecture/15413/한-눈에-끝내는-sql/lesson/767683/sql이란