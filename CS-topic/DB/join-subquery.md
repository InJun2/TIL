# JOIN & SUBQUERY

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

```sql
select e.employee_id, d.department_id
from employees e, departments d
where e.department_id = d.department_id
and e.employee_id = 100;

select e.employee_id, d.department_id
from employees e
join departments d
on e.department_id = d.department_id
and e.employee_id = 100;

select e.employee_id, d.department_id
from employees e
join departments d
using (department_id)
and e.employee_id = 100;

-- natural join은 같은 컬럼에 대해 바로 적용되지만 중복 컬럼이 여러개면 문제 발생 하므로 사용 지양
select e.employee_id, d.department_id
from employees e
natural join departments d
and e.employee_id = 100;
```


<br>

### OUTER JOIN
- 어느 한쪽 테이블에 해당하는 데이터가 존재하는데 다른 쪽 테이블에는 데이터가 존재하지 않을 경우 그 데이터가 검색되지 않는 문제점을 해결하기 위해 사용
- LEFT OUTER JOIN, RIGHT OUTER JOIN, FULL OUTER JOIN 으로 구분됨
    - LETF OUTER JOIN : 왼쪽 테이블을 기준으로 JOIN 조건에 일치하지 않는 데이터까지 출력
    - RIGHT OUTER JOIN : 오른쪽 테이블을 기준으로 JOIN 조건에 일치하지 않는 데이터까지 출력
    - FULL OUTER JOIN : 양쪽 테이블을 기준으로 JOIN 조건에 일치하지 않는 데이터까지 출력, MySQL은 지원하지 않음

```sql
-- left outer join
select e.employee_id, e.first_name, d.department_id
from employees e
left outer join departments d
using (department_id);

select e.employee_id, e.first_name, d.department_id
from employees e
left join departments d
using (department_id);

-- right outer join
select e.employee_id, e.first_name, d.department_id
from employees e
right outer join departments d
using (department_id);

select e.employee_id, e.first_name, d.department_id
from employees e
right join departments d
using (department_id);
```

<br>

### SELF JOIN, Non-Equi JOIN
- SELF JOIN은 같은 테이블끼리 JOIN
    - 예시) 모든 사원의 사번, 이름, 매니저사번, 매니저이름
- Non-Equi JOIN은 table의 PK, FK가 아닌 일반 column을 join으로 지정
    - 예시) 모든 사원의 사번, 이름, 급여, 급여등급 

```sql
-- inner join
select e.employee_id, e.first_name, m.employee_id, m.first_name
from employees e
inner join employees m
on e.manager_id = m.employee_id;

-- none-equi join
select e.employee_id, e.first_name, e.salary, s.grade
from employees e join salgrades s
where e.salary between s.losal and s.hisal;
```

<br>

### 서브 쿼리 (Subquery)
- 서브 쿼리란 다른 쿼리 내부에 포함되어 있는 SELECT 문을 의미
- 서브 쿼리를 포함하고 있는 쿼리를 외부 쿼리(outer query) 또는 메인 쿼리라고 부르며, 서브 쿼리는 내부 쿼리(inner query) 라고도 부름
- 서브 쿼리는 비교 연산자의 오른쪽에 기술해야 하고 반드시 괄호'()'로 감싸져 있어야만 함

#### 서브 쿼리 종류
- 중첩 서브 쿼리 (Nested Subquery) : WHERE 문에 작성하는 서브 쿼리
    - 단일 행 : 서브 쿼리의 결과가 단일행을 리턴
    - 복수 (다중) 행 : 서브 쿼리의 결과가 다중행을 리턴 (IN, ANY, ALL)
    - 다중 컬럼 : 서브 쿼리의 결과가 다중 열을 리턴
- 인라인 뷰 (Inline View) : FROM 문에 작성하는 서브 쿼리
- 스칼라 서브 쿼리 (Scalar Subquery) : SELECT 문에 작성하는 서브 쿼리

<br>

### 서브 쿼리 주의 사항
- 서브 쿼리는 반드시 ()로 감싸야 함
- 서브 쿼리는 단일 행 또는 다중 행 비교 연산자와 함께 사용됨
- 서브 쿼리가 사용이 가능한 곳은 다음과 같음
    - SELECT
    - FROM
    - WHERE
    - HAVING
    - ORDER BY
    - INSERT 문의 VALUES
    - UPDATE 문의 SET

```sql
-- subquery
select department_name
from departments
where department_id = (
    select department_id
    from employees
    where employee_id = 100;
)

-- nested subquery
-- 단일 행
select department_id, department_name
from departments
where location_id = (
    select location_id
    from locations
    where binary upper(city) = upper('seattle')
);

select employee_id, first_name, salary
from employees
where salary > (select avg(salary) from employees)
order by salary desc;

-- 다중 행
select employee_id, first_name
from employees
where department_id in (
    select department_id, department_name
    from departments
    where location_id = (
        select location_id
        from locations
        where binary upper(city) = upper('seattle')
    )
);

select employee_id, first_name, salary, department_id
from employees
where salary > any (
    select salary
    from employees
    where department_id = 30;
)
order by salary;

select employee_id, first_name, salary, department_id
from employees
where salary > all (
    select salary
    from employees
    where department_id = 30;
)
order by salary;

select employee_id, first_name
from employees
where (salary, departmnet_id) in (
    select salary, department_id
    from employees
    where commission_pct is not null
    and manager_id = 148
);
```

<br>

### INLINE VIEW
- 서브 쿼리의 종류로 FROM 절에 사용되는 서비 쿼리를 인라인 뷰(Inline View) 라고 함
- 서브 쿼리가 FROM 절에 사용되면 뷰(View) 처럼 결과가 동적으로 생성된 테이블로 사용 가능
- 임시적인 뷰이기 때문에 데이터베이스에는 저장되지 않음
- 동적으로 생서된 테이블이기 때문에 column을 자유롭게 참조 가능

```sql
select e.employee_id, e.first_name, e.salary, e.department_id
from (
    select distinct department_id
    from employees
    where salary < (select avg(salary) from employees)
    ) d join employees e
on d.department_id = e.department_id;
```

<br>

### Scalar Subquery
- 스칼라 서브 쿼리는 SELECT 절에 있는 서브 쿼리로 한 개의 행만 반환

```sql
select e.employee_id, e.first_name, job_id,
    ( 
        select department_name
        from departments d
        where e.department_id = d.department_id
    ) department_name
from employees e
where job_id = 'IT_PROG';
```

<br>

### 서브쿼리를 이용한 CRUD 예시

```sql
-- employees table -> emp_copy 로 복사, 하지만 제약 사항은 복사되지 않음
create table emp_copy
select * from employees;

-- 특정 컬럼을 이름을 변경하여 필요한 데이터만 복사 가능
create table emp50
select employee_id eid, first_name name, salary sal, department_id did
from emloyees
where department_id = 50;

-- employees table 에서 부서번호가 80인 사원의 모든 정보를 emp_blank에 insert
insert into emp_blank
select * from employees
where department_id = 80;

-- employees table의 모든 사원의 평균 급여보다 적게 받는 emp50 table의 사원의 급여를 500 인상
select emp50
set sal = sal + 500
where sal < (select avg(salary) from employees);

-- employees table의 모든 사우너의 평균 급여보다 적게 받는 emp50 table의 사원은 퇴사
delete fro emp50
where sal < (select avg(salary) from employees);
```

<br>