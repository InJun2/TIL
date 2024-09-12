# DB View

### View
- 뷰는 사용자에게 접근이 허용된 자료만을 제한적으로 보여주지 위해 하나 이상의 기본 테이블로부터 유도된, 이름을 가지는 가상 테이블
- 뷰와 테이블의 차이점은 TABLE은 실질적인 데이터를 보관하지만 뷰는 데이터가 없고 SQL만 저장함
- 뷰는 데이터 보정작업, 처리과정 시험 등 임시적인 작업을 위한 용도로 활용
    - View는 단지 다른 테이블이나 View에 있는 데이터를 보여주는 역할만 수행
- 뷰는 조인문의 사용 최소화로 사용상의 편의성을 최대화
- 뷰는 저장장치 내에 물리적으로 존재하지 않지만 사용자에게 있는 것처럼 간주

<br>

### View의 특징
- 뷰는 기본테이블로부터 유도된 테이블이기 때문에 기본 테이블과 같은 형태의 구조를 사용하며, 조작도 기본 테이블과 거의 같음
- 뷰는 가상 테이블이기 때문에 물리적으로 구현되어 있지 않음
- 데이터의 논리적 독립성을 제공할 수 있음
- 필요한 데이터만 뷰로 정의해서 처리할 수 있기 때문에 관리가 용이하고 명령문이 간단해짐
- 뷰를 통해 데이터에 접근하게 되면 뷰에 나타나지 않는 데이터를 안전하게 보호하는 효율적인 기법으로 사용할 수 있음
- 기본 테이블의 기본키를 포함한 속성 집합으로 뷰를 구성해야지만 삽입, 삭제, 연산이 가능
- 뷰가 정의된 기본 테이블이나 뷰를 삭제하면 그 테이블이나 뷰를 기초로 정의된 다른 뷰도 자동으로 삭제

<br>

### View 장점
- 논리적 데이터 독립성을 제공
- 동일 데이터에 대해 동시에 여러 사용자의 상이한 응용이나 요구를 지원
- 사용자의 데이터관리를 간단하게 해줌
- 복잡한 쿼리를 단순화하고 쿼리를 재사용할 수 있음
- 접근 제어를 통한 자동 보안이 제공
    - 특정 사용자에게 테이블 전체가 아닌 필요한 필드만 보여줄 수 있음

<br>

### View 단점
- 독립적인 인덱스를 가질 수 없음
- 뷰로 구성된 내용에 대한 삽입, 삭제, 갱신, 연산에 제약이 따름
    - 함수, UNION, GROUP BY 등을 사용한 복합 뷰인 경우 불가능

<br>

### View Data 변경
- View를 생성한 기존 테이블의 data가 update 되면 View의 내용도 update 됨
- View를 조회하게 되면 옵티마이저에서 View를 생성할 때 저장해 놓은 select 문이 실행되는 것이기 때문에 View에서도 update 된 것 처럼 보임

<br>

### View 종류
#### 단순 뷰 (Simple View)
- 하나의 테이블로 생성
- 그룹 함수의 사용이 불가능
- distinct 사용 불가능
- DML 사용가능

#### 복합 뷰 (Complex View)
- 여러 개의 테이블로 생성 (join)
- 그룹 함수의 사용이 가능
- distinct 사용 가능
- DML 사용 불가능

#### 인라인 뷰 (Inline View)
- 일반적으로 가장 많이 사용
- from 절 안에 SQL 문장이 들어가는 것을 인라인 뷰라고 볼 수 있음

```sql
create view 
```

<br>

### View 생성/삭제 예시
- 매번 여러 테이블을 join한 복잡한 쿼리문이 있다고 가정했을 때 매번 조인쿼리문을 쓰기보단 뷰로 만들어서 뷰를 조회하는 것이 편리
```sql
CREATE VIEW 뷰이름[(속성이름[,속성이름])]AS SELECT문;

CREATE VIEW 서울고객(성명, 전화번호)
AS SELECT 성명 전화번호
FROM 고객
WHERE 주소 = '서울시';

ALTER VIEW 뷰이름 AS SELECT 필드1, 필드2... FROM 테이블이름;

DROP VIEW 뷰이름 RESTRICT or CASCADE

DROP VIEW 서울고객 RESTRICT;

CREATE OR REPLACE VIEW v1 
AS 
select 
stu.name,
d.deptno
from student stu, department d, professor p
where student deptno = d.deptno
and d.pno = p.id;
```

<br>

<div style="text-align: right">22-09-09</div>

-------

## Reference
- https://byul91oh.tistory.com/38
- https://helloworld92.tistory.com/75