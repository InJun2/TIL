# SELECT CLAUSE

### SELECT 절 QUERY
- SELECT 절(clause)은 SQL 에서 데이터베이스에서 데이터를 조회하는 명령
- SELECT 절은 SQL에서 가장 기본적인 데이터 조회 명령
- SELECT 시 NULL 데이터 관리에 주의가 필요
    - 1 AND NULL 시 NULL
    - 0 AND NULL 시 FALSE
    - 1 OR NULL 시 TRUE
    - NULL AND NULL 시 NULL
    - NULL OR NULL 시 NULL
    - NULL은 '알 수 없음'이라고 생각하는 것이 편함

| 주요 기능                      | 설명                                                                 |
|--------------------------------|----------------------------------------------------------------------|
| **열 선택**                     | 특정 열을 선택하여 데이터를 조회. 예: `SELECT column1, column2`      |
| **중복 제거**                   | `DISTINCT`를 사용하여 중복된 데이터를 제거. 예: `SELECT DISTINCT column` |
| **조건 필터링**                 | `WHERE` 절을 사용하여 조건에 맞는 행을 필터링. 예: `WHERE column = value` |
| **정렬**                        | `ORDER BY` 절을 사용하여 특정 열을 기준으로 정렬. 예: `ORDER BY column DESC` |
| **결과 제한**                   | `LIMIT`를 사용하여 조회 결과의 개수를 제한. 예: `LIMIT 10`           |
| **CASE 문**                     | `CASE` 문을 사용하여 조건에 따라 다른 값을 반환. 예: `CASE WHEN ... THEN ... END` |
| **BETWEEN**                     | 특정 범위 내의 값을 조회. 예: `WHERE column BETWEEN value1 AND value2` |
| **IN**                          | 여러 값 중 하나와 일치하는 행을 조회. 예: `WHERE column IN (value1, value2)` |
| **NULL 값 필터링**              | `IS NULL` 또는 `IS NOT NULL`을 사용하여 NULL 값이 있는 행을 필터링.    |
| **와일드카드 검색**             | `LIKE`와 `%`, `_`를 사용하여 패턴 매칭. 예: `WHERE column LIKE '%pattern%'` |

<br>

### 내장 함수 (Built-in Functions)
- 내장 함수란 데이터베이스에서 자주 사용되는 연산이나 쉽게 처리할 수 있도록 미리 정의된 함수
    - 기본적으로 제공되어 다양한 유형의 데이터를 처리하고 조작
- 주로 특정 열을 처리하거나 변환하는데 사용
- SELECT, WHERE, HAVING, ORDER BY 등 다양한 SQL 절에서 활용할 수 있음
- MySQL 내장 함수는 단일행 함수와 다중행 함수가 존재


<br>

### 단일행 함수 (Single-Row Function)
- 각 행의 값을 하나의 결과 값으로 변환하는 함수, 예시는 ABS(-5)
- 입력으로 하나의 행을 받고 해당 행의 특정 값을 추출하거나 변환하여 단일 결과 값을 반환
- 숫자, 문자, 날짜 관련 함수, 변환형 함수, NULL 관련 함수

<br>

#### 숫자 관련 함수
- ABS(숫자) : 절대값
- CEILING(숫자), CEIL(숫자) : 올림
- FLOOR(숫자) : 내림
- ROUND(숫자, 자릿수) : 숫자를 자릿수까지 반올림 (자릿수가 음수라면 소수점이 아닌 정수 부분 반올림)
- TRUNCATE(숫자, 자릿수) : 숫자를 자릿수까지 버림
- POW(X, Y), POWER(X, Y) : X의 Y승
- MOD(분자, 분모) : 분자를 분모로 나눈 나머지
- GREATEST(숫자...) : 주어진 수에서 가장 큰 수를 반환
- LEAST(숫자...) : 주어진 수에서 가장 작은수를 반환
- RAND() : 난수 발생

<br>

#### 문자 관련 함수 (String Functions)
- ASCII(문자): 주어진 문자의 ASCII 값을 반환
- CHAR(숫자): 숫자에 해당하는 ASCII 문자를 반환
- CONCAT(문자...): 주어진 문자열들을 하나로 결합
- CONCAT_WS(구분자, 문자...): 주어진 문자열들을 구분자로 결합
- INSERT(문자열, 시작위치, 길이, 대치문자): 문자열의 특정 위치에서 지정된 길이만큼 대치
- REPLACE(문자열, 찾을문자열, 바꿀문자열): 문자열에서 특정 부분을 다른 문자열로 대체
- INSTR(문자열, 찾을문자열): 찾고자 하는 문자열의 첫 번째 위치 반환
- SUBSTRING(문자열, 시작위치, 길이): 문자열의 일부를 추출
- REVERSE(문자열): 문자열을 뒤집어 반환
- UPPER(문자열), UCASE(문자열): 문자열을 대문자로 변환
- LOWER(문자열), LCASE(문자열): 문자열을 소문자로 변환
- FORMAT(숫자, 소수점자리수): 숫자를 지정된 소수점 자릿수까지 포맷하고, 3자리마다 콤마 추가
- LTRIM(문자열), RTRIM(문자열), TRIM(문자열): 문자열의 좌우 공백을 제거
- LENGTH(문자열): 문자열의 바이트 수를 반환
- CHAR_LENGTH(문자열): 문자열의 문자 수를 반환
- BIT_LENGTH(문자열): 문자열을 비트로 변환한 후 그 길이를 반환

<br>

#### 날짜 관련 함수 (Date Functions)
- NOW(), SYSDATE(), CURRENT_TIMESTAMP(): 현재 날짜와 시간을 반환
- CURDATE(), CURRENT_DATE(): 현재 날짜만 반환
- CURTIME(), CURRENT_TIME(): 현재 시간만 반환
- ADDDATE(날짜, INTERVAL): 주어진 날짜에 특정 시간 또는 날짜를 더함
- SUBDATE(날짜, INTERVAL): 주어진 날짜에서 특정 시간 또는 날짜를 뺌
- DATEDIFF(날짜1, 날짜2): 두 날짜 사이의 일수 차이 반환
- TIMESTAMPDIFF(단위, 시간1, 시간2): 두 시간 사이의 차이를 단위별로 반환
- LAST_DAY(날짜): 주어진 날짜가 속한 달의 마지막 날을 반환
- DATE_FORMAT(날짜, 형식): 날짜를 지정한 형식으로 변환

<br>

#### 논리 관련 함수 (Logical Functions)
- IF(조건, 참일경우, 거짓일경우): 조건이 참일 경우 첫 번째 값을, 거짓일 경우 두 번째 값을 반환
- IFNULL(값1, 값2): 첫 번째 값이 NULL이면 두 번째 값을 반환
- NULLIF(값1, 값2): 두 값이 같으면 NULL, 다르면 첫 번째 값을 반환

<br>

### 다중행 함수 (Multi-Row Function)
- 여러 행이 입력되어 하나의 행으로 결과 값을 반환, 예시는 COUNT(*)
- 여러 행을 그룹화하고 그룹 단위로 계산하여 Group Function 이라고도 함
- 집계, 그룹, 윈도우 함수

<br>

#### 집계 함수 (Aggregate Functions)
- 내장 함수 중 집계 함수로 SQL 에서 여러 행의 값을 하나의 값으로 요약하는데 사용
- 주로 데이터의 통계 정보나 요약을 추출할 때 사용
    - COUNT() : 행의 개수를 반환
    - SUM() : 숫자 열의 합계를 계산
    - AVG() : 숫자 열의 평균값을 반환
    - MAX() : 최대값을 반환
    - MIN() : 최소값을 반환


```sql
SELECT COUNT(*), AVG(salary), MAX(salary), MIN(salary)
FROM employees;
```

<br>

### 서브 쿼리 (Subquery)
- 하나의 SQL 쿼리 안에 또 다른 쿼리를 중첩하여 사용하는 방식
- 주로 다른 쿼리의 결과를 참조하거나 필터링 조건으로 사용됨
    - SELECT 절, WHERE 절, FROM 절, HAVING 절 등에 사용 가능
- 서브 쿼리의 유형은 다음과 같음
    - 스칼라 서브쿼리 : 하나의 값을 반환
    - 다중 행 서브쿼리 : 여러 행을 반환
    - 상관 서브쿼리 : 외부 쿼리와 연관된 서브쿼리

```sql
-- SELECT 절
SELECT name, (SELECT AVG(salary) FROM employees) AS avg_salary FROM employees;

-- WHERE 절
SELECT name FROM employees WHERE salary > (SELECT AVG(salary) FROM employees);

-- FROM 절
SELECT * FROM (SELECT name, salary FROM employees) AS emp;

-- HAVING 절
SELECT department, AVG(salary)
FROM employees
GROUP BY department
HAVING AVG(salary) > (SELECT AVG(salary) FROM employees);
```

<br>

### 내장함수와 서브쿼리 비교

| 구분         | 내장 함수 (Built-in Function)                                  | 서브 쿼리 (Subquery)                                         |
|--------------|----------------------------------------------------------------|--------------------------------------------------------------|
| 정의         | MySQL에 미리 정의된 함수로, 데이터를 변환하거나 처리함         | 쿼리 내에 또 다른 쿼리를 포함하여 동적 값 또는 조건을 반환  |
| 사용 목적    | 특정 데이터 값을 변환, 계산, 처리                              | 동적인 값을 반환하거나 복잡한 조건을 처리                    |
| 사용 위치    | SELECT, WHERE, HAVING, ORDER BY 등 다양한 절에서 사용 가능     | SELECT, WHERE, HAVING, ORDER BY 등 다양한 절에서 사용 가능   |
| 속도         | 빠르고 최적화된 연산                                           | 비교적 느릴 수 있음 (복잡도에 따라 다름)                     |
| 복잡도       | 단순한 연산에 적합                                             | 복잡한 조건이나 다중 단계의 데이터를 처리하는 데 유용        |
| 예시         | `SELECT ROUND(급여, 2) FROM 직원;`                             | `SELECT * FROM 직원 WHERE 급여 > (SELECT AVG(급여) FROM 직원);` |


<br>

```sql
use ssafydb;

-- 모든 사원의 모든 정보 검색.
SELECT *
FROM EMPLOYEES;

-- 사원이 근무하는 부서의 부서번호 검색.
SELECT DEPARTMENT_ID
FROM EMPLOYEES;

-- 사원이 근무하는 부서의 부서번호 검색.(중복제거)
SELECT DISTINCT DEPARTMENT_ID
FROM EMPLOYEES;
-- WHERE DEPARTMENT_ID IS NOT NULL;

-- 회사에 존재하는 모든 부서.
SELECT DEPARTMENT_ID
FROM DEPARTMENTS;

-- 모든 사원의 사번, 이름, 급여 검색.
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY
FROM EMPLOYEES;

-- 모든 사원의 사번, 이름, 급여, 급여 * 12 (연봉) 검색.
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY
FROM EMPLOYEES;

-- 테이블 정보 보기 
DESC EMPLOYEES;

-- 모든 사원의 사번, 이름, 급여, 급여 * 12 (연봉), 커미션, 커미션포함 연봉 검색.
-- 만약 커미션이 없다면 0 리턴
SELECT EMPLOYEE_ID AS 사번, FIRST_NAME "이름", SALARY 급여, SALARY * 12 "연  봉",
	   COMMISSION_PCT, (SALARY + SALARY * IFNULL(COMMISSION_PCT, 0)) * 12 "커미션 포함 연봉"
FROM EMPLOYEES;

-- 모든 사원의 사번, 이름, 급여, 급여에 따른 등급표시 검색.
-- 급여에 따른 등급
--   15000 이상 “고액연봉“      
--   8000 이상 “평균연봉”      
--   8000 미만 “저액연봉＂
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY,
	   CASE
			WHEN SALARY >= 15000 THEN "고액연봉"
            WHEN SALARY >= 8000 THEN "평균연봉"
            ELSE "저액연봉"
	   END "연봉등급"
FROM EMPLOYEES;

-- 부서번호가 50인 사원중 급여가 7000이상인 사원의
-- 사번, 이름, 급여, 부서번호
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY, DEPARTMENT_ID
FROM EMPLOYEES
WHERE DEPARTMENT_ID = 50
AND SALARY >= 7000;

-- 근무 부서번호가 50, 60, 70에 근무하는 사원의 사번, 이름, 부서번호
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY, DEPARTMENT_ID
FROM EMPLOYEES
WHERE DEPARTMENT_ID = 50
OR DEPARTMENT_ID = 60
OR DEPARTMENT_ID = 70;

-- 근무 부서번호가 50, 60, 70이 아닌 사원의 사번, 이름, 부서번호
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY, DEPARTMENT_ID
FROM EMPLOYEES
WHERE DEPARTMENT_ID != 50
AND DEPARTMENT_ID <> 60
AND NOT (DEPARTMENT_ID = 70);

-- 근무 부서번호가 50, 60, 70에 근무하는 사원의 사번, 이름, 부서번호
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY, DEPARTMENT_ID
FROM EMPLOYEES
WHERE DEPARTMENT_ID IN (50, 60, 70, 80, 90, 100);

-- 근무 부서번호가 50, 60, 70이 아닌 사원의 사번, 이름, 부서번호 
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY, DEPARTMENT_ID
FROM EMPLOYEES
WHERE DEPARTMENT_ID NOT IN (50, 60, 70, 80, 90, 100);

-- 급여가 6000이상 10000이하인 사원의 사번, 이름, 급여
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY
FROM EMPLOYEES
WHERE SALARY BETWEEN 6000 AND 10000;

-- 근무 부서가 지정되지 않은(알 수 없는) 사원의 사번, 이름, 부서번호 검색.
SELECT EMPLOYEE_ID, FIRST_NAME, DEPARTMENT_ID
FROM EMPLOYEES
WHERE DEPARTMENT_ID IS NULL;

-- 근무 부서가 지정된 사원의 사번, 이름, 부서번호 검색.
SELECT EMPLOYEE_ID, FIRST_NAME, DEPARTMENT_ID
FROM EMPLOYEES
WHERE DEPARTMENT_ID IS NOT NULL;

-- 커미션을 받는 사원의 사번, 이름, 급여, 커미션
SELECT EMPLOYEE_ID, FIRST_NAME, DEPARTMENT_ID, COMMISSION_PCT
FROM EMPLOYEES
WHERE COMMISSION_PCT IS NOT NULL;

-- 이름에 'x'가 들어간 사원의 사번, 이름
SELECT EMPLOYEE_ID, FIRST_NAME
FROM EMPLOYEES
WHERE FIRST_NAME LIKE "%x%";

-- 이름의 끝에서 3번째 자리에 'x'가 들어간 사원의 사번, 이름
SELECT EMPLOYEE_ID, FIRST_NAME
FROM EMPLOYEES
WHERE FIRST_NAME LIKE "%x__";

-- 모든 사원의 사번, 이름, 급여
-- 단 급여순 정렬(내림차순)
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY
FROM EMPLOYEES
ORDER BY SALARY DESC;

-- ORDER BY 시작 인덱스는 1
-- ORDERING INDEX는 하지만 변경 가능성이 있으므로 사용하기 위험한 방법
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY
FROM EMPLOYEES
ORDER BY 3 DESC;

-- alias (별칭)으로 지정해둔 컬럼을 정렬하려면 백틱(``)을 사용하여 정렬 가능
SELECT EMPLOYEE_ID AS 사번, FIRST_NAME "이름", SALARY 급여, SALARY * 12 "연  봉",
	   COMMISSION_PCT, (SALARY + SALARY * IFNULL(COMMISSION_PCT, 0)) * 12 "커미션 포함 연봉"
FROM EMPLOYEES
ORDER BY `연  봉` DESC
LIMIT 10;

-- 50, 60, 70에 근무하는 사원의 사번, 이름, 부서번호, 급여
-- 단, 부서별 정렬(오름차순) 후 급여순(내림차순) 검색
-- 정렬한 데이터들에서 다시 정렬
SELECT EMPLOYEE_ID, FIRST_NAME, DEPARTMENT_ID, SALARY
FROM EMPLOYEES
ORDER BY DEPARTMENT_ID, SALARY DESC;

-- 급여 순 정렬 후 5번째로 높은 급여를 받는 사원의 사번, 이름, 급여
-- 오프셋 인덱스는 0부터 시작
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY
FROM EMPLOYEES
ORDER BY SALARY DESC
LIMIT 1 OFFSET 4;

SELECT EMPLOYEE_ID, FIRST_NAME, SALARY
FROM EMPLOYEES
ORDER BY SALARY DESC
LIMIT 4, 1;

-- 급여 순 정렬 후 1 ~ 5번째로 급여를 많이 받는 사원의 사번, 이름, 급여
SELECT EMPLOYEE_ID, FIRST_NAME, SALARY
FROM EMPLOYEES
ORDER BY SALARY DESC
LIMIT 0, 5;

-- SELECT 시 NULL 데이터 관리에 주의가 필요
SELECT 1=1, 1=0, 1=NULL, 0=NULL, NULL=NULL
FROM DUAL;
-- 출력 : 1, 0, NULL, NULL, NULL
```

<br>

### 내장 함수 사용 예시

```sql
use ssafydb;

-- ------------------- 숫자 관련 함수 ----------------------
-- 절대값 : 5  0  5
select abs(5), abs(-0), abs(-5);

-- 올림(정수) : 13  13  -12  -12
select ceil(12.2), ceiling(12.2), ceil(-12,2), ceiling(-12.2);

-- 버림(정수) : 12  -13
select floor(12.6), floor(-12.2);

-- 반올림 : 1526  1526  1526.2  1526.16  1530  2000
select round(1526.159), round(1526.159,0), round(1526.159, 1), round(1526.159, 2),
 round(1526.159, -1), round(1526.159, -3);

-- 버림 : 1526  1526.1  1526.15  1520  1000
select truncate(1526.159, 0), truncate(1526.159, 1), truncate(1526.159, 2),
truncate(1526.159, -1), truncate(1526.159, -3);

-- 제곱, 제곱근 : 8  8	4
select pow(2, 3), power(2, 3), sqrt(16);

-- 나머지 : 2  2
select 8 % 3, mod(8, 3);

-- 최대, 최소값 : 9  3
select greatest(6, 2, 3, 4, 9, 1, 7), least(6, 2, 3, 4, 9, 1, 7);

-- 난수
select rand(), rand() * 45 + 1, floor(rand() * 45 + 1);

-- ------------------- 문자 관련 함수 ----------------------
-- 문자 :: > 아스키, 아스키 ::> 문자 : 48  65  97  '0'  'A'  'a'  'ABCD'
select ascii('0'), ascii('A'), ascii('a'), cast(char(48) as char), cast(char(65, 66, 67, 68) as char);

-- 문자열 결합 : 100번 사원의 이름 Steven King
select employee_id || '번 사원의 이름 ' || first_name || ' ' || last_name
from employees
where employee_id = 100;

-- 구분자를 이용한 문자열 결합 : 2024-09-10
select concat_ws('.', '2024', '09', '10');

-- 문자열 대치(index) : hello ssafy !!!
select 'ssafyabc!!!', insert('ssafyabc!!!', 6, 3, ' ssafy ');

-- 문자열 대치(문자열) : hello ssafy !!!
select 'ssafyabc!!!', replace('ssafyabc!!!', 'abc', ' ssafy ');

-- 찾을 문자열의 index 반환 : 7
select instr('hello ssafy !!!', 'ssafy');

-- 문자열 추출 : ssafy
select 'hello ssafy !!!', mid('hello ssafy !!!', 7, 5), substring('hello ssafy !!!', 7, 5);
select 'hello ssafy !!!', mid('hello ssafy !!!', 7, 5), substr('hello ssafy !!!', 7, 5);

-- 응용 중첩 함수
select '123-456', substr('123-456', 1, instr('123-456', '-') - 1);
select '123-456', substr('123-456', instr('123-456', '-') + 1);

-- hello ssafy !!!
select reverse('!!! yfass olleh');

-- hello ssafy !!!  hello ssafy !!!
select lower('Hello Ssafy'), lcase('Hello Ssafy');

-- HELLO SSAFY !!!  HELLO SSAFY !!!
select upper('Hello Ssafy'), ucase('Hello Ssafy');

-- hello... ,  fy !!!
select 'hello ssafy !!!', concat(left('hello ssafy !!!', 5), '...'), right('hello ssafy !!!', 6);

-- 3자리마다 콤마(,) & 소수점 2자리까지
select format(12345678.987, 2);

-- 공백제거, 문자기준 trim
select rtrim('    aaaa    '), ltrim('    aaaa    '), trim('    aaaa    ');

-- 길이(byte), 비트수, 문자의 개수
select length('홍길동'), bit_length('홍길동'), char_length('홍길동');

-- ------------------- 날짜 관련 함수 ----------------------
-- 현재 시간 2024-09-10 10:11:52  2024-09-10 10:11:52  2024-09-10 10:11:52
select now(), sysdate(), current_timestamp();

-- 현재시간 (실행시점) : 2024-09-10 10:11:52, 2024-09-10 10:11:57, 2024-09-10 10:11:52, 2024-09-10 10:11:52
select now(), sleep(5), sysdate(), sleep(5), current_timestamp(), now();

-- 날짜 또는 시간만 반환 : 2024-09-10,  2024-09-10,  10:12:10,  10:12:10
select curdate(), current_date(), curtime(), current_time();

-- X일(or 시간) 후, 전
select now(), adddate(now(), interval 5 minute), date_add(now(), interval 5 day), adddate(now(), interval -5 year),
	subdate(now(), interval 5 minute);

-- 날짜 세부 반환 함수 : dayofweek은 일요일부터 인덱스 1
select year(now()), month(now()), month('2024-10-11'), monthname('2024-10-11'), dayofweek(now());

-- 날짜 형식 지정 : 
-- 2024-09-03 14:04:44	
-- 2024 September 3 PM 2 04 44	
-- 24-09-03 14:04:44	
-- 24.09.03 Tuesday	
-- 14시04분44초
select date_format(now(), '%Y-%m-%d %H:%i:%s'), 
	date_format(now(), '%Y.%m.%d %p %H:%i:%s'),
	date_format(now(), '%H시%i분%s초');

-- 시간, 날짜 차
select datediff('2024-12-31', '2024-01-01') as 날짜차이,
 timestampdiff(hour, '2024-09-03 14:04:44', '2024-09-04 16:30:30') as 시간차이;

-- 달의 마지막 날
select last_day('2024-10-11'), last_day('2024-10-11');


-- ------------------- 논리 관련 함수 ----------------------
-- 논리함수 : 크다  작다  3  b  a
select  if(3>2, '크다', '작다'), ifnull(null, 'isnull'), ifnull('b', 'a'), nullif('a', 'a'), nullif('a', 'b');

-- ------------------- 집계 함수 ----------------------
-- employees table에서 사원의 총수, 급여의 합, 급여의 평균, 최고급여, 최저급여
select count(employee_id) 사원수, sum(salary) 급여총합, avg(salary) 급여평균, max(salary) 최고급여, min(salary) 최저급여
from employees;
```

<br>

### 실습 진행

```sql
-- 1. world 데이터베이스 사용 설정
use world;

-- 3. country table 에서 code 가 KOR인 자료 조회
select *
from country
where code = 'KOR';

-- 4. country에서 gnp 변동량이 양수인 국가에 대해 gnp 변동량의 오름차순으로 정렬
select *
from country
where (gnp - GNPOld) >= 0
order by gnp;

-- 5. country 에서 continent를 중복 없이 조회. continent 길이로 정렬
select distinct continent
from country
order by length(continent);

-- 6. country asia 대륙에 속하는 국가들의 정보를 아래와 같이 출력
select concat(name, '은 ', region, '에 속하며 인구는 ', population, '명이다') "정보"
from country
order by name;

-- 7. country에서 독립 년도에 대한 기록이 없고 인구가 10000 이상인 국가의 정보를 인구의 오름차순으로 출력하시오
select name, continent, gnp, population
from country
where IndepYear is Null
and population >= 10000
order by population;

-- 8. country에서 인구가 1억 <= x <= 2억 인 나라를 인구 기준으로 내림차순 정렬해서 상위 3개만 출력
select code, name, population
from country
where population >= 100000000
and Population <= 200000000
order by Population desc
limit 3;

-- 9. country에서 800, 1810, 1811, 1901, 1901에 독립한 나라를 독립년 기준으로 오름차순 출력. 독립년이 같다면 code를 기준으로 정렬
select code, name, indepyear
from country
where indepyear in (800, 1810, 1811, 1901)
order by IndepYear, code;

-- 10. country 에서 region에 asia가 들어가고 name의 두 번째 글자가 'o'인 정보를 출력하시오
select code, name, region
from country
where region like '%asia%'
and name like '_o%';

-- 11. '홍길동'과 'hong'의 글자 길이를 각각 출력하시오
select char_length('홍길동') "한글", length('hong') "영문"
from dual;

-- 12. country에서 governmentform 에 republic이 들어있고 name의 길이가 10 이상인 자료를 name 길이의 내림차순으로 상위 10개만 출력
select code, name, governmentform
from country
where GovernmentForm like '%republic%'
and length(name) >= 10
order by length(name) desc
limit 10;

-- 13. country에서 code가 모음으로 시작하는 나라의 정보를 출력하시오. 이떄 name의 오름차순으로 정렬
SELECT code, name
FROM country
WHERE code LIKE 'a%'
   OR code LIKE 'e%'
   OR code LIKE 'i%'
   OR code LIKE 'o%'
   OR code LIKE 'u%'
ORDER BY name
limit 2, 3;


-- 14. country에서 name을 맨 앞과 맨 뒤에 2글자를 제외하고는 나머지는 *로 처리해서 출력하시오
select name, (select concat(left(name, 2), repeat('*', length(name) - 4), right(name, 2))) as "guess"
from country;


-- 15. country 에서 region을 중복 없이 가져오는데 공백을 _로 대체하시오 region의 길이로 정렬
select distinct replace(region, ' ', '_') as "지역들"
from country
order by length(`지역들`) desc;

-- 16. country에서 인구가 1억 이상인 국가들의 1인당 점유면적을 반올림해서 소숫점 3자리로 표현하시오. 1인당 점유 면적의 오름차순으로 정렬
select name, surfacearea, population, round(surfacearea/population, 3) "1인당 점유 면적"
from country
where population >= 100000000
order by `1인당 점유 면적`;
```

<br>

### 추가 실습 Select 문제
- sakila 데이터베이스를 통한 실습 문제 진행
- [해당 블로그 문제 실습](https://goodteacher.tistory.com/738)

```sql
-- 1. staff의 first_name과 글자수를 출력하고 마지막에 홍길동의 글자수를 출력하시오
select first_name, length(first_name) "first name 글자수", char_length('홍길동') "홍길동 글자수"
from staff;

-- 2. staff 테이블에서 홍길동: 비밀번호 형태로 출력하시오. 단 아직 비밀번호가 없는 경우 등록후 사용이라고 출력하시오.
SELECT IFNULL(CONCAT(first_name, ': ', password), CONCAT(first_name, ': ', "등록 후 사용")) AS "staff"
FROM staff;

-- 3. city에서 도시명의 3번째 글자가 모음인 경우를 조회하시오. (city_id 오름차순 정렬)
select city_id, city, country_id, last_update
from city
where substr(city, 3, 1) in ('a', 'e', 'i', 'o', 'u')
order by city_id;

-- 4. city의 도시명을 아래와 같이 출력하시오.(city의 길이로 정렬한다.)
-- [ A Corua(La Corua)] (도시명의 총 글자 수는 [] 를 포함 22자이다.)
select city, concat('[', left(city, 20) , ']')"도시명"
from city
order by length(city) desc;

-- 5. address의 phone을 1234XXXXX1234의 형태로 출력하시오.(단 phone은 길이가 0이상이어야 한다.)
select phone, concat(substr(phone, 1, 4), repeat('*', char_length(phone)-8),substr(phone, -4, 4)) "당첨자 번호"
from address
where length(phone) > 0;

-- 6. customer에서 email의 아이디를 출력하시오. 
select email, left(email, instr(email, '@') - 1) "email id"
from customer;

-- 7. customer에서 email의 host 부분을 추출하시오. 
select distinct substring(email, instr(email, '@') + 1) "host"
from customer;

-- 8. rental에서 반납된 자료를 반납 요일 기준으로 정렬하시오. 단 월요일이 맨 앞이다. 
-- weekday (월 ->), dayofweek (일 ->)
select rental_id, return_date, weekday(return_date) "요일"
from rental
where return_date is not null
order by `요일`;

-- 9. rental에서 렌탈일이 7월인 자료를 조회하시오.
select rental_id, rental_date
from rental
where month(rental_date) = 7;

-- 10. customer에서 100번 고객의 가입일과 100일 기념일은?
select customer_id, create_date, adddate(create_date, interval 100 day) "100일 기념일"
from customer
where customer_id = 100;

-- 11.  rental에서 rental_id가 15000~16000인 자료의 rental_id와 각 rental_id별 렌탈 기간을 대여기간으로 정렬하시오.
-- 정렬은 대여기간의 내림차순으로 한다.
select rental_id, return_date, rental_date, datediff(return_date, rental_date) "대여기간"
from rental
where rental_id between 15000 and 16000
order by `대여기간` desc;

-- 12. customer MARY의 create_date를 2016/02/14(화) 형태로 출력하시오.
select first_name, 
	concat(date_format(create_date, "%y/%m/%d(%a)"))  "가입일"
from customer
where first_name = 'MARY';

-- 13. staff의 사진이 아직 없으면 미제출, 있으면 제출완료라고 표현하시오.
select first_name, picture, if(picture is null, "미제출", "제출완료") "사진제출여부"
from staff;

-- 14. rental에서 rental_id가 15862 ~ 15864 사이인 자료의 rental_id, rental_date, return_date를 출력하시오.
-- 단 아직 반납 안된 자료를 return_date에 미반납이라고 출력한다
select rental_id, rental_date, ifnull(return_date, '미반납') "반납일"
from rental
where rental_id between 15862 and 15864;

-- 15. 위 조건에서 렌탈 기간을 출력하시오. 단 아직 미반납인 경우 오늘 까지로 계산한다. 단 렌탈 기간은 오늘의 날짜에 따라 달라진다.
-- 아래는 2024.03.13일 기준
select rental_id, rental_date, if(return_date is null, '미반납', return_date) as '반납일'
	, if(rental_date is null, datediff(current_date(), rental_date)
	, datediff(return_date, rental_date)) as '렌탈 기간'
from rental where rental_id >= 15862 && rental_id <= 15864;

-- 16. film에서 대여료를 12.3% 인상한 가격을 소숫점 두째 자리로 반올림해서 제목과 함께 출력하시오. 
select title, rental_rate, round(rental_rate*1.123,2) as '인상예정'
from film;

-- 17. customer에서 active가 1이면 활동중, 0이면 탈퇴로 표시/출력하고 이것으로 정렬하시오. 
SELECT 
    last_name, 
    active,
    CASE 
        WHEN active = 1 THEN '활동중' 
        WHEN active = 0 THEN '탈퇴'
    END AS status
FROM customer
WHERE last_name LIKE '%N'
ORDER BY status
LIMIT 4, 2;

-- 18. rental에서 반납 완료된 자료들을 반납 요일 순으로 정렬(일 -> 토)했을 때 반납 요일은? 
select rental_id, left(dayname(return_date), 3) '반납 요일'
from rental
where return_date is not null
order by weekday(return_date) desc;


-- 1. 대여 기간(return_date, rental_date의 차이)이 7일에서 8일인 rental_id 조회 (3583건)
select rental_id
from rental
where datediff(return_date, rental_date) between 7 and 8;
  
-- 2. film 테이블에서 2006년에 발행된 영화 중 가장 긴 영화 순서대로 3개의 제목(title)과 그 길이(length) 조회
select title, length(title)
from film
where release_year = 2006
order by length(title) desc
limit 3;

-- 3. rental 테이블에서 customer_id가 5인 유저가 가장 마지막에 빌린 하나의 rental_id와 rental_date 조회
select rental_id, rental_date
from rental
where customer_id = 5
order by rental_date desc
limit 1;

-- 4. rental 테이블에서 2005년 1월 1일부터 2005년 1월 31일까지 발생한 총 대여 건수(rental_date) 조회 (993건)
select count(*) "총 대여 건수"
from rental
where rental_date between '2005-05-01' and '2005-05-31';

-- 5. rental 테이블에서 2005년 7월 1일부터 2006년 7월 31일까지 return_date가 NULL인, 즉 아직 반납하지 않은 총 기록 개수 조회 (183개)
select count(*) "미 반납 기록"
from rental
where return_date is null
  and rental_date between '2005-07-01' and '2006-07-01';
```

<br>

### 직접 내는 연습 문제

```sql
-- 1. 대여 기간(return_date, rental_date의 차이)이 7일에서 8일인 rental_id 조회 (3583건)
select rental_id
from rental
where datediff(return_date, rental_date) between 7 and 8;
  
-- 2. film 테이블에서 2006년에 발행된 영화 중 가장 긴 영화 순서대로 3개의 제목(title)과 그 길이(length) 조회
select title, length(title)
from film
where release_year = 2006
order by length(title) desc
limit 3;

-- 3. rental 테이블에서 customer_id가 5인 유저가 가장 마지막에 빌린 하나의 rental_id와 rental_date 조회
select rental_id, rental_date
from rental
where customer_id = 5
order by rental_date desc
limit 1;

-- 4. rental 테이블에서 2005년 1월 1일부터 2005년 1월 31일까지 발생한 총 대여 건수(rental_date) 조회 (993건)
select count(*) "총 대여 건수"
from rental
where rental_date between '2005-05-01' and '2005-05-31';

-- 5. rental 테이블에서 2005년 7월 1일부터 2006년 7월 31일까지 return_date가 NULL인, 즉 아직 반납하지 않은 총 기록 개수 조회 (183개)
select count(*) "미 반납 기록"
from rental
where return_date is null
  and rental_date between '2005-07-01' and '2006-07-01';
```