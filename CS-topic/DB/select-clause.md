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

### 집계 함수 (Aggregate Functions)
- SQL 에서 여러 행의 값을 하나의 값으로 요약하는데 사용
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
   OR code LIKE 'w%'
   OR code LIKE 'y%'
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