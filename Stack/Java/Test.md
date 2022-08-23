# Test

### Java 단위 테스트(Unit Test)
- 순수 Java 기반의 애플리케이션에 대해 테스트 코드를 작성해보고자 함
- 필요한 라이브러리에는 크게 JUnit5, AssertJ 2가지 라이브러리가 사용됨
    - JUnit5 : 자바 단위 테스트를 톱기 위한 테스팅 프레임워크
    - AssertJ : 자바 테스트를 돕기 위해 다양한 문법을 지원하는 라이브러리
    - JUnit 만으로도 단위테스트를 충분히 작성할 수 있으나 JUnit에서 제공하는 assertEquals()같은 메소드는 AssertJ가 주는 메소드에 비해 가독성이 떨어져 주로 순수 Java 애플리케이션 단위테스트를 위해 JUnit5와 AssertJ 조합이 많이 사용됨

```
테스트 코드
- 테스트 코드란 내가 작성한 메서드가 실제로 제대로 동작하는지 테스트 하는 코드
TDD
- 테스트가 주도하는 개발
- 실패하는 테스트코드 작성 -> 테스트가 성공하는 프로덕션 코드 작성 -> 테스트가 성공하면 프로덕션 코드를 리팩토링 하는식으로 개발
```

<br>

#### 단위 테스트 패턴 given-when-then
- 최근 단위테스트는 거의 given-when-then 패턴으로 작성하는 추세
- 1개의 단위테스트를 3가지 단계로 나누어 처리하는 패턴임
    - given(준비) : 어떠한 데이터가 준비되었을 때
    - when(실행) : 어떠한 함수를 실행하면
    - then(검증) : 어떠한 결과가 나와야 한다
    - 추가적으로 어떤 메소드가 몇번 호출되었는지 검사하기 위한 verity 단계도 사용하는 경우가 있는데 메소드의 호출 횟수가 중요한 테스트에서만 선택적으로 사용하면 됨

```java
@DisplayName("로또 번호 갯수 테스트")       // 가독성이 좋은 다른 이름으로 부여 가능
@Test       // 단위 테스트임을 명시하는 어노테이션
void lottoNumberSizeTest() {    // 테스트 이름은 한글로 써도 무방
// given
    final LottoNumberGenerator lottoNumberGenerator = new LottoNumberGenerator();
    final int price = 1000;

    // when
    final List<Integer> lottoNumber = lottoNumberGenerator.generate(price);

    // then
    assertThat(lotto.size()).isEqualTo(6);
}

@DisplayName("잘못된 로또 금액 테스트")
@Test
void lottoNumberInvalidMoneyTest() {
    // given
    final LottoNumberGenerator lottoNumberGenerator = new LottoNumberGenerator();
    final int price = 2000;

    // when
    final RuntimeException exception = assertThrows(RuntimeException.class, () -> lottoNumberGenerator.generate(price));

    // then
    assertThat(exception.getMessage()).isEqualTo("올바른 금액이 아닙니다.");
}
```

<br>

<div style="text-align: right">22-08-18</div>

-------

## Reference
- https://mangkyu.tistory.com/144
- https://velog.io/@sms8377/Testing-테스트의-종류-및-Jest
