# 우아한 테크코스 프리코스 Week4

### 우아한 테크코스 프리코스 4주차 진행
- 이번 4주차 프리코스 과제는 크리스마스 프로모션이다. 기존 과제와 다르게 private 저장소로 제출해야 하기에 이후 링크를 달아둘 예정
- 이번에도 3주차 코드리뷰를 통해 [장혁수님](https://github.com/woowacourse-precourse/java-lotto-6/pull/111)과 [최윤혁님](https://github.com/woowacourse-precourse/java-lotto-6/pull/733), 그리고 [서재님](https://github.com/woowacourse-precourse/java-lotto-6/pull/1153)의 코드를 통해 코드 구현에 영향을 받았음
    - 기존 처럼 Mock 객체를 통해 다른 클래스에 영향을 받지 않게 테스트 코드 작성 목표
- 기존 과제와 다르게 많은 요구사항이 존재했고 해당 요구사항을 날짜와 주문 메뉴를 입력 받아 이벤트를 적용하는 방향으로 진행
- 진행 전 리드미를 작성하여 설계를 진행하였으나 이후 변경사항이나 추가사항을 통해 기존 설계보다 많은 클래스들을 생성하여 진행
- 이번 코드 리팩토링도 ChatGPT 과 Intellij warnning code를 이용하여 코드 리팩토링 진행

<br>

### 이번에 고민했던 부분
- 해당 기능을 구현하기 위한 구조 설계
  - 이후 필요한 클래스들을 추가하며 설계 변경

- 동일한 입력과 출력을 하는 뷰는 싱글톤으로 구현
```java
// Application.class 에서만 생성하게 하기위해 Default Class로 생성
class SingletonView {
    private static InputView inputView;
    private static OutputView outputView;

    private SingletonView() {
    }

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

- 입력에 대한 유효성 검사와 객체 생성에 대한 유효성 검사 구분에 대한 고민
    - 바로 Map으로 반환하지 않거나 해당 부분을 메서드로 따로 분리하는 것도 나쁘지 않았을 것 같음
```java
public class TextProcessor {
    private final static String MENU_SPLIT = ",";
    private final static String ORDER_NUMBER_SPLIT = "-";
    private final static String ORDER_MENU_INPUT_REGEX = "^[가-힣]+-\\d{1,2}$";

    public Map<String, Integer> processOrder(String orderMenus) {
        List<String> menuArray = extractMenus(orderMenus);

        return createOrderMap(menuArray);
    }

    // ","로 문자열을 분리하고 입력 포맷을 확인. 분리된 문자열을 List로  반환
    private List<String> extractMenus(String inputOrderMenus) {
        return Arrays.stream(inputOrderMenus.split(MENU_SPLIT))
                .map(String::trim)
                .peek(this::validateOrderMenuInput)
                .toList();
    }

    // 분리한 문자열을 리스트로 담아와 "-"로 한번 더 분리. 이후 Map으로 바로 반환하기 때문에 입력에서 중복 확인을 진행하였음
    private Map<String, Integer> createOrderMap(List<String> orderMenus) {
        return orderMenus.stream()
                .map(menuItemInfo -> menuItemInfo.split(ORDER_NUMBER_SPLIT))
                .collect(Collectors.toUnmodifiableMap(
                        menuItemSplit -> menuItemSplit[0].trim(),
                        menuItemSplit -> parseInput(menuItemSplit[1].trim(), ExceptionMessage.INVALID_INPUT_ORDER),
                        (existing, replacement) -> {
                            throw new PromotionException(ExceptionMessage.INVALID_INPUT_ORDER);
                        }
                ));
    }
```

- 이벤트 적용 시 이벤트 내역과 주문 내역을 사용해 값을 저장하는 방법을 Service 에서 진행할지 객체 내부에서 진행할지 고민
```java
    // 기존 입력을 통해 생성한 도메인의 DTO 값을 받아와 이벤트 적용. 해당 Service는 객체를 생성하고 DTO로 반환해주는 역할만을 수행
    public EventResultDTO applyEvents(ReservationDateEventDTO dateEventDTO, OrderMenusDTO orderMenusDto) {
        EventResult eventResult = new EventResult(dateEventDTO
                , converter.convertToMenuItem(orderMenusDto));

        return converter.toDto(new EventResultTextFactory(eventResult));
    }
```

- 출력을 위한 DTO 생성에 대한 고민
  - DTO 룰 출력하고 해당 DTO 통해 다시 이벤트 내역 확인하는데 있어 DTO 값을 사용하는 방법
  - DTO 에서 출력 문자열을 생성하는 메서드를 넣었으나 비즈니스 로직으로 판단하여 EventResultTextFactory에 로직 위임. DTO 객체 생성은 Builder 사용
```java
    // Service
    public EventResultDTO applyEvents(ReservationDateEventDTO dateEventDTO, OrderMenusDTO orderMenusDto) {
        EventResult eventResult = new EventResult(dateEventDTO
                , converter.convertToMenuItem(orderMenusDto));

        return converter.toDto(new EventResultTextFactory(eventResult));
    }

    // Converter
    public EventResultDTO toDto(EventResultTextFactory resultTextProcessor) {
        return EventResultDTO.builder()
                .orderMenus(resultTextProcessor.initOrderMenus())
                .totalPriceBeforeDiscount(resultTextProcessor.initTotalPriceBeforeDiscount())
                .giftMenu(resultTextProcessor.initGiftMenu())
                .benefitHistory(resultTextProcessor.initBenefitHistory())
                .totalBenefit(resultTextProcessor.initTotalBenefit())
                .totalPriceAfterDiscount(resultTextProcessor.initPriceAfterDiscount())
                .rewardBadge(resultTextProcessor.initRewardBadge())
                .build();
    }

// EventResult 도메인을 저장하여 출력 문자열들을 반환하는 각각의 메서드 구현
public class EventResultTextFactory {
    private final EventResult result;

    public EventResultTextFactory(EventResult result) {
        this.result = result;
    }

    public String initBenefitHistory() {
        StringBuilder builder = new StringBuilder();
        builder.append(displayChristmasBenefit());
        builder.append(displayWeekBenefit());
        builder.append(displaySpecialBenefit());
        builder.append(displayGiftEventBenefit());

        if (builder.isEmpty()) {
            return EventResultText.NONE_BENEFIT.getText() + EventResultText.NEW_LINE.getText();
        }

        return builder.toString();
    }

    ...
```

- 잘못된 입력에 대한 메서드 재실행 로직은 지난주 [장혁수님](https://github.com/zangsu) 코드리뷰로 배운 Supplier 반복 사용
```java
// Controller 내부 메서드
    private ReservationDateEventDTO inputCorrectReservationDate() {
        outputView.displayStartPromotion();

        return getCorrectResult(this::generateDateEvent);
    }

    private OrderMenusDTO inputCorrectOrderMenus() {
        return getCorrectResult(this::receiveMenus);
    }

    private <T> T getCorrectResult(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (PromotionException e) {
                outputView.displayException(e);
            }
        }
    }
```

- @MethodSource 입력값인 Stream<Arguments> 메서드 사용
```java
    @ParameterizedTest
    @MethodSource("createNormalOrderMenusData")
    @DisplayName("특별 할인 이벤트 적용 결과 객체 생성")
    void createEventResultBySpecialDay(Map<MenuItem, Integer> testMap) {
        orderMenus = testMap;

        ...
    }

    private static Stream<Arguments> createNormalOrderMenusData() {
        return Stream.of(
                Arguments.of(
                        Map.of(MenuItem.APPETIZER_MUSHROOM_SOUP, 4, MenuItem.MAIN_SEAFOOD_PASTA, 4,
                                MenuItem.BEVERAGE_RED_WINE, 2)
                )
        );
    }
```

- 테스트 코드 mock 객체에 대한 사용방법에 대한 고민. when도 하나의 메서드에서 사용되는 단위라는 것을 알게 되어 해당 방식처럼 진행
```java
public class EventResultTextFactoryTest {
    private EventResultTextFactory factory;
    private EventResult result;
    private DiscountDetail detail;

    // 특정 객체안의 객체 반환 경우 반환 값 지정
    @BeforeEach
    void setUp() {
        result = mock(EventResult.class);
        detail = mock(DiscountDetail.class);
        when(result.getDiscountDetail()).thenReturn(detail);
        factory = new EventResultTextFactory(result);
    }

    ...

    // 특정 경우에만 반환값을 모아서 메서드로 재사용
    private void allEventApply() {
        when(detail.getChristmasDiscount()).thenReturn(2000);
        when(detail.getWeekTypeDiscount()).thenReturn(2000);
        when(detail.getSpecialDiscount()).thenReturn(2000);
        when(detail.getTotalDiscount()).thenReturn(6000);
        when(detail.getWeekDiscountType()).thenReturn(WeekDiscountType.WEEKDAY);
        when(result.getGiftMenu()).thenReturn(GiftMenu.CHAMPAGNE);
    }
```


<br>

### 코드 피드백
- 제출 이후 스터디 및 우아한테크코스 프리코스 디스코드를 통해 코드피드백을 받아 해당 내용을 이어 정리할 예정

<br>

### 느낀점
- 지난번 코드리뷰를 통해 개선한 사항이 많이 존재. 이번에도 개선할 사항이 많지 않을까 싶은데 이후 코드 리뷰를 통해 정리하고자 함
- 이번에는 설계에 대해서도 기존 코드리뷰 피드백의 내용을 많이 개선하였음. 기존에는 간단한 기능을 구현하는 모듈을 구현하고자 하여 간단하게 표현했는데 이번에는 요구사항이 많다보니 Controller와 DTO를 구현하여 진행
- DTO 에서 문자열을 DTO에 담아 반환하였는데 DTO에는 기본 타입 값만 담아서 뷰에서 처리하는 방법도 나쁘지않았을 것 같음. 문자열로 변환하는 과정도 비즈니스 로직이라 생각하여 따로 처리했는데 이 부분은 뭐가 좋을지 잘 모르겠음
- 이번 주문 메뉴 중복 처리는 입력에서 유효성 검사를 진행하였는데 String을 Map으로 바로 바꿔주다보니 이렇게 처리할 수 밖에 없었다고 생각됨. 코드를 구현하고 나니 List로 반환해서 해당 List에 대해 주문 메뉴 생성 객체에서 중복 검사를 하고 Map으로 바꿔주는 방법은 어땠을까 생각하게 됨..
- 지난번 장혁수님께서 사용하신 Supplier 를 통해 함수형 입력 반복이 좋다고 생각되어 Controller 에서 직접 사용하였는데 해당 사용이 좋은지 모르겠음.. OuputView 와도 연결되어 있어야 한다고 생각하는데 Controller에서 있어야 하는 기능인가 하면 또 애매한 부분이 많은 것 같음..
- 이번에도 테스트코드를 작성하면서 다른 사람들이 보기에는 아직 많이 모자른 테스트 코드겠지만 테스트 코드쪽으로는 많이 배웠다고 느껴짐.. mock을 사용해서 특정 상황에 when을 메서드로 빼도 동작한다거나 객체에서 다른 객체를 꺼낼 때 when으로 특정 객체를 받아온다거나, 중복되는 부분을 @BeforeEach로 빼거나, @MethodSource를 통해 Stream<Arguments>를 사용하는 방법 등 다양한 방법 들을 통해 테스트 코드를 작성해보면서 많이 배웠음
- 다른 개발자와 의견을 주고받고 자신의 견해를 이야기하는 시간은 정말 다방면으로 성장하고 좋은 경험이었다고 생각이 드는데 앞으로도 스터디나, 동아리를 통해 다양한 활동을 해보고 싶음.. 일단 꾸준히 지원해봐야 겠다..