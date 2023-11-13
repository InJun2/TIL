# 우아한 테크코스 프리코스 Week3

### 우아한 테크코스 프리코스 3주차 진행
- 이번 3주차 프리코스 과제는 로또이다. 자세한 설명은 다음과 같음
    - [Fork Git](https://github.com/InJun2/java-lotto-6)
- 이번에도 테스트를 단위테스트로 진행하고 이전 코드리뷰를 통해 제안 받은 어노테이션을 사용하여 테스트 코드를 구현할 예정
    - 기존 처럼 Mock 객체를 통해 다른 클래스에 영향을 받지 않게 테스트 코드 작성 목표

<br>

### 이번에 고민했던 부분
- InputView와 OutputView를 싱글톤으로 생성하여 해당 객체를 계속 사용하려하였으나 그럴경우 View를 어디에서나 호출할 수 있다는 것이 문제가 되어 해당 방식대로 사용하지 않았음..
```java
public class View {
    private static InputView inputView;
    private static OutputView outputView;

    private View() {}

    public static InputView getInputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    public static OutputView getOutputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }
        return outputView;
    }
}
``` 

- 출력 문자열에서 int값이나 float 값을 반환하기 위한 방법에 대해 고민하다 메서드 오버로딩을 통해 파라미터를 받아서 문자열로 반환받았음
```java
public enum ViewMessage {
    ...
    WINNING_STATISTICS_FIFTH("3개 일치 (5,000원) - %d개\n"),
    WINNING_STATISTICS_FOURTH("4개 일치 (50,000원) - %d개\n"),
    WINNING_STATISTICS_THIRD("5개 일치 (1,500,000원) - %d개\n"),
    WINNING_STATISTICS_SECOND("5개 일치, 보너스 볼 일치 (30,000,000원) - %d개\n"),
    WINNING_STATISTICS_FIRST("6개 일치 (2,000,000,000원) - %d개"),
    TOTAL_PROFIT_PERCENTAGE("총 수익률은 %.1f%%입니다."),
    ;

    private final String message;

    ViewMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(int number) {
        return String.format(message, number);
    }

    public String getMessage(Float percent) {
        return String.format(message, percent);
    }
}
```

- 예외를 입력과 객체 생성, 비즈니스 로직 구현 중 어디에서 발생 시킬까 고민하였는데 입력이 없을 경우 입력 문자열 조작 서비스, 잘못된 입력일 경우 객체 생성 등을 통해 예외를 발생 시켰음

- 이번에 기능이 비슷한 중복로직 제거를 위해 고민을하다가 상속을 위주로 변경하였음
    - 기존 Lotto와 WinningLotto를 record로 사용하려고 변경했다가 상속을 통해 클래스로 다시 변경하고 추상 클래스 상속
    - 기존 record는 다른 예외 발생을 위해 중복 로직을 포함하여 메서드를 사용했었음
    - 이후 생각해보니 추상 메서드가 없는데 왜 굳이 추상 클래스를 썼는지 모르곘음 ㅋㅋ.. 이후 기본 클래스로 변경시키겠음
        - 기존에는 중복로직을 추상 메서드로 하여금 다른부분만 역할을 강제하려했으나 Lotto와 WinningLotto의 파라미터가 달라 그렇게 사용하지 못하였음..
```java
public class Lotto extends ValidateLottoNumbers{
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validateLottoNumbers(numbers);
        this.numbers = sortLottoNumbers(numbers);
    }
    // ... 
}

public class WinningLotto extends ValidateLottoNumbers {
    private final List<Integer> numbers;
    private final int bonusNumber;

    public WinningLotto(List<Integer> numbers, int bonusNumber) {
        validateWinningLottoNumbers(numbers, bonusNumber);
        this.numbers = numbers;
        this.bonusNumber = bonusNumber;
    }
    // ...
}

// 추상 클래스 -> 그런데 추상 메서드가 하나도 없어 괜히 추상 클래스로 구현했다고 생각됨..
public abstract class ValidateLottoNumbers {
    private final static int NUMBERS_SIZE = 6;
    private final static int WINNING_NUMBERS_SIZE = 7;
    private final static int MIN_NUMBER = 1;
    private final static int MAX_NUMBER = 45;

    // Lotto 유효성 검사 메서드
    protected void validateLottoNumbers(List<Integer> numbers) {
        validateSize(numbers, ExceptionMessage.INVALID_LOTTO_SIZE);
        validateRange(numbers, ExceptionMessage.INVALID_NUMBER_RANGE);
        validateDuplicationNumbers(numbers);
    }

    // WinningLotto 유효성 검사 메서드
    // validateDuplicationNumbers, validateBonusNumberRange 로직은 다르며 예외 메시지가 달라 해당 방식으로 해결하였음 :D
    protected void validateWinningLottoNumbers(List<Integer> numbers, int bonusNumber) {
        validateSize(numbers, ExceptionMessage.INVALID_WINNING_NUMBERS_SIZE);
        validateRange(numbers, ExceptionMessage.INVALID_WINNING_NUMBERS_RANGE);
        validateDuplicationNumbers(numbers, bonusNumber);
        validateBonusNumberRange(bonusNumber);
    }

    // 해당 메서드를 통해 추상 메서드로 구현을 강제하려했으나 파라미터가 다름..
    private void validateDuplicationNumbers(List<Integer> numbers) {
        Set<Integer> notDuplicationNumbers = new HashSet<>(numbers);

        if (notDuplicationNumbers.size() != NUMBERS_SIZE) {
            throw new CustomIllegalArgumentException(ExceptionMessage.DUPLICATE_LOTTO_NUMBER);
        }
    }

    private void validateDuplicationNumbers(List<Integer> numbers, int bonusNumber) {
        Set<Integer> notDuplicationNumbers = new HashSet<>(numbers);
        notDuplicationNumbers.add(bonusNumber);

        if (notDuplicationNumbers.size() != WINNING_NUMBERS_SIZE) {
            throw new CustomIllegalArgumentException(ExceptionMessage.DUPLICATE_WINNING_NUMBER);
        }
    }
```

- 예외 메시지 전역처리를 위해 예외를 상속받아 예외 메시지를 파라미터로 받아 예외 메시지의 값을 메시지로 넣어주었음
    - 계속 파라미터로 ExceptionMessage.EMPTY_PURCHASE_AMOUNT.getErrorMessage()로 가져오는 건 너무 열받음;
```java
public class CustomIllegalArgumentException extends IllegalArgumentException {
    public CustomIllegalArgumentException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getErrorMessage());
    }
}
```

- 상수 관리를 정적 필드 vs Enum에 대해 고민하다 하나의 계층에서만 사용한다면 정적필드를 사용하였음
```java
// 필드 정적 인스턴스
public class LottoService {
    private final static int LOTTO_PRICE = 1000;
    private final static int MIN_AMOUNT = 1;
    private final static int BONUS_MATCHING_COUNT = 5;
    private final static int MIN_PRIZE_COUNT = 3;
    private final static int NO_PRIZE_COUNT = 0;

    ...
}

// enum 인스턴스
public enum LottoWinningRank {
    FIRST(6, 2000000000),
    SECOND(5, 30000000),
    THIRD(5, 1500000),
    FOURTH(4, 50000),
    FIFTH(3, 5000),
    NO_WINNER(0, 0),
    ;
    
    private final int matchingNumbers;
    private final int prize;

    ...
}
```

- 하고나서 테스트가 에러 발생해서 요구사항을 다시 살펴보니 예외를 던지기만 하는게 아니라 예외 메시지를 출력하고 다시 입력받아야 했음
    - try-catch로 내가 발생시킨 예외를 잡아 예외 메시지를 출력하고 입력부터 로직을 진행하는 메서드를 재귀를 통해 다시 실행
```java
    // 예외 발생시 메시지를 출력하고 다시 실행
    private LottoTickets createLottoTickets() {
        try {
            int amount = inputPurchaseLotto();

            return lottoService.createLottoTickets(amount);
        } catch (CustomIllegalArgumentException e) {
            lottoView.outputException(e);

            return createLottoTickets();
        }
    }
```

- 로또 당첨 개수를 알아내어 로또 당첨 순위 순회하는 방법에 대해 고민
    - 해당 방법은 ChapGPT 방법을 참조하였음
```java
private LottoWinningRank determinePrizeRank(int prizeCount) {
        if (prizeCount < MIN_PRIZE_COUNT && prizeCount >= NO_PRIZE_COUNT) {
            return LottoWinningRank.NO_WINNER;
        }

        return Arrays.stream(LottoWinningRank.values()) // ArrayStream 으로 enum을 모두 순회할 수 있음!
                .filter(rank ->
                        rank.getMatchingNumbers() == prizeCount)
                .findFirst()
                .orElseThrow(() ->
                        new CustomIllegalArgumentException(ExceptionMessage.INVALID_PRIZE_COUNT));
    }
```

- 당첨 내역을 계속 저장할 객체를 어떻게 저장하고 가져올까 고민하였는데 어차피 matchingNumbers 어디에 저장할지 정해야 하므로 Map이 제격이라고 생각헀음. 이제 Map에 어떻게 넣을까 생각했다가 Enum을 순회해서 값이 같다면 넣는 방식으로 하기로 결정
```java
// 위의 LottoWinningRank Enum 참조

// 당첨 내역 저장 객체
public class LottoTotalPrize {
    private final static int INIT_VALUE = 0;
    private final static int PLUS_COUNT = 1;
    // Key를 LottoWinningRank로 사용한 이유는 2등과 3등의 matchingNumbers가 같기 때문
    private final Map<LottoWinningRank, Integer> prizeCounts = new HashMap<>();

    public LottoTotalPrize() {
        init();
    }

    // 처음 생성시 나올 수있는 키에 0으로 초기화
    private void init() {
        Arrays.asList(LottoWinningRank.values())
                .forEach(winningRank ->
                        prizeCounts.put(winningRank, INIT_VALUE));
    }

    // Enum 인스턴스를 파라미터로 받아 해당 인스턴스를 Key로 사용하고 해당 Key의 밸류 값을 가져와 count에 +1 저장
    public void prizeCountPlus(LottoWinningRank winningRank) {
        prizeCounts.put(winningRank, prizeCounts.get(winningRank) + PLUS_COUNT);
    }

    // getter
```

- 당첨 내역에 대해 총 당첨 금액을 계산하기 위해 Map을 순회하는 방법은 ChapGPT를 참조
```java
private int calculateTotalPrize(LottoTotalPrize lottoResult) {
    return lottoResult.getPrizeCounts()
            .entrySet()         // entrySet으로 Map을 모두 순회
            .stream()
            .map(entry ->
                    entry.getKey().getPrize() * entry.getValue())
            .mapToInt(Integer::intValue)
            .sum();
}
```

<br>

### 코드 피드백
- [코드 피드백](https://github.com/woowacourse-precourse/java-lotto-6/pull/1617/files)은 해당 피드백과 코드 리뷰를 진행했던 [서재님](https://github.com/woowacourse-precourse/java-lotto-6/pull/1153/files)의 코드 구현을 바탕으로 이후 다음 프리코스 과제를 진행할 예정
    - jdk17 이후 .collect(Collectors.toList()); 대신 .toList 사용
    - getter 객체 외부에 값을 전달 시 Collections.unmodifiableList 등을 통해 값을 변경하지 못하도록 제한하거나 DTO 객체를 파라미터로 전달
    - 자리수가 많은 정수형 상수를 사용할 때 "2_000_000_000" 처럼 자리수를 구분 가능
    - try-catch 문 중복을 동작 파라미터화를 통해 제거가 가능
    - enum 접근제한자를 default로 사용하여 사용 패키지 제한
    - 스트림에서 찾는 값이 없을 경우 지정된 값을 반환해주는 getOrDefault 사용

<br>

### 느낀점
- 생각보다 ChatGPT와 인텔리제이 replace 기능이 매우 좋다고 느껴졌음
- 기존에는 거의 명명규칙에만 ChatGPT를 사용했는데 중복 로직 코드를 제거하는데 많은 도움이 되었음
- 인텔리제이 IDE에서 애매한 for문이나 if문을 stream으로 바꿔주거나 Class를 Record로 바꿔주는 등 기능에 만족..
- 또 Commit 시 주의 밑줄이 여러개 뜨는게 있다면 해당 부분을 전부 수정해주니 더 좋은 코드가 되었다고 생각
- 이번에 아쉬웠던 부분은 지난번 피드백인 Random 클래스의 테스트 진행을 못했던 부분.. 이번에는 사용하는 메서드가 private다 보니 테스트가 되지 않았음. 다음번에 테스트 코드를 위해 어떻게 사용해야하는지 다시 숙지해야할 것 같음
    - 이전 피드백인 @ParameterizedTest를 사용하였는데 신기하고 코드가 간단해져서 좋음
    - 로또 Mockito 객체를 사용해 가짜 객체를 생성해 사용하였으나 한 객체는 그렇게 사용하지 못했음.. 어떻게 해야할까
    - 테스트 코드 작성도 시간이 꾀나 걸렸는데 이후 연습만이 살일
- 또 아쉬웠던 부분은 리드미를 먼저 작성하고 점진적으로 체크리스트를 통해 작업하고 작업 단위를 작게 나누어서 커밋하지 못하였음.. 작게 나눠서 점진적으로 커밋하는 방식은 습관을 들이도록 노력해보겠음
- 이번에 생각보다 리팩토링 시간이 오래걸렸는데 가독성 부분은 더 나아지기도 했고 많이 배우는 시간이 되었다고 생각함.. 이제 과제 한번 남았는데 걱정반 기대반 인것 같음