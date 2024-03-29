# Denormalization

### 반정규화 (Denormalization)
- 정규화에 충실하여 모델링을 수행하면 종속성, 활용성은 향상되나 수행속도가 증가 하는 경우가 발생하여 이를 극복하기 위해 성능에 중점을 두어 정규화하는 방법
- 데이터 모델링 규칙에 얽매이지 않고 수행
- 시스템이 물리적으로 구현되었을 때 성능향상을 목적으로 함

<br>

### 반정규화 사용시기
- 정규화에 충실하였으나 수행속도에 문제가 있는 경우
- 다량의 범위를 자주 처리해야 하는 경우
- 특정범위의 데이터만 자주 처리하는 경우
- 처리범위를 줄이지 않고는 수행속도를 개선할 수 없는 경우
- 요약 자료만 주로 요구되는 경우
- 추가된 테이블의 처리를 위한 오버헤드를 고려하여 결정
- 인덱스의 조정이나 부분범위처리로 유도하고, 클러스터링을 이용하여 해결할 수 있는 지를 철저히 검토 후 결정

<br>

<div style="text-align: right">22-12-31</div>

-------

## Reference
- https://iworldt.tistory.com/100