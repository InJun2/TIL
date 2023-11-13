# 우아한 테크코스 프리코스 Week2

### 우아한 테크코스 프리코스 2주차 진행
- 이번 2주차 프리코스 과제는 자동차 경주 게임이다. 자세한 설명은 다음과 같음
    - [Fork Git](https://github.com/InJun2/java-racingcar-6)
- 이번 2주차 과제는 1주차 때 피드백을 바탕으로 좀더 기능적으로 메소드를 구분하고 매직넘버를 사용하는 것을 지양하며 사용한 Console을 닫아주고 테스트 코드를 작성해보는 등 다양하게 고민하며 구현해볼 수 있었음!
- 이번 구조를 설계하거나 추가된 요구사항을 적용하며 좀더 객체지향적으로 바라보고 더 나은 코드를 작성할 수 있었던 것 같음

<br>

### 이번에 고민했던 코드
- 필요한 요청에 따른 반환값을 일관성있게 변환해서 뷰로 출력하는 것이 좋을 것이라 생각했고 문자열을 변환/조작하는 서비스를 따로 구현하기로 결정. 해당 방식을 Static으로 정적으로 유틸처럼 쓸까 했으나 서비스에서 받아온 값을 문자열로 변환시키는 서비스 역할로만 하자 싶어서 해당 방식처럼만 사용하게 되었음
```java
// 자동차 경주 실행 클래스
public class RacingCarRun {
    private final RacingCarService service;
    private final TextProcessor textProcessor;
    private final RacingCarView view;

    public RacingCarRun() {
        this.service = new RacingCarService();
        this.textProcessor = new TextProcessor();
        this.view = new RacingCarView();
    }
    ...

// 공백을 제거한 문자열 분리, int 형으로 변환, 이동 과정을 문자열로 변환, 뷰로 출력할 문자열을 통합 등등..
public class TextProcessor {
    private final static String SPLIT = ",";
    private final static String SEPARATOR = " : ";
    private final static char CAR_DISTANCE = '-';
    private final static String DELIMITER = ", ";
    private final static String NEW_LINE = "\n";

    public String[] splitCarNames(String str) {
        var splitNames = str.split(SPLIT);

        return trimWhitespace(splitNames);
    }

    private String[] trimWhitespace(String[] strArr) {
        return Arrays.stream(strArr)
                .map(String::trim)
                .toArray(String[]::new);
    }

    public int parseTryNumber(String str) {
        try {
            return validateInputTryCount(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_INTEGER_FORMAT
                    .getErrorMessage());
        }
    }

    public String carsDistanceAsString(Cars cars) {
        StringBuilder builder = new StringBuilder();
        for (Car car : cars.getCars()) {
            builder.append(car.getName())
                    .append(SEPARATOR)
                    .append(displayDistance(car.getDistance()))
                    .append(NEW_LINE);
        }

        return builder.toString();
    }

    public String joinText(List<String> winnerNames) {
        return String.join(DELIMITER, winnerNames);
    }

    private String displayDistance(int distance) {
        return String.valueOf(CAR_DISTANCE)
                .repeat(Math.max(0, distance));
    }

    private int validateInputTryCount(String str) {
        validateTryCountEmpty(str);
        return validTryCountNegativeValue(str);
    }

    private void validateTryCountEmpty(String str) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessage.EMPTY_TRY_COUNT
                    .getErrorMessage());
        }
    }

    private int validTryCountNegativeValue(String str) {
        int count = Integer.parseInt(str);
        if (count < 0) {
            throw new IllegalArgumentException(ExceptionMessage.NEGATIVE_VALUE_TRY_COUNT
                    .getErrorMessage());
        }

        return count;
    }
}
```

- Car라는 객체를 생성하는 방법에 대해 고민했음. 메소드를 통해 Car를 찍어내는 방식으로 할까 아니면 객체를 통해 사용을 할까 했는데 Car를 가지고있는 Cars 클래스를 만들어 사용하였음
    - 생성은 서비스에서 실행
```java
// 생성은 서비스 클래스에서
public class RacingCarService {
    public Cars createCars(String[] strArr) {
        return new Cars(strArr);
    }

// 여러 Car 객체를 보관할 Cars
public class Cars {
    private final List<Car> cars;

    public Cars(String[] carNames) {
        this.cars = createCars(carNames);
    }

    private List<Car> createCars(String[] carNames) {
        return Arrays.stream(carNames)
                .map(Car::new)
                .collect(Collectors.toList());
    }

    public List<Car> getCars() {
        return cars;
    }
}

// 유효성 검사와 move, 값을 저장할 Car 객체
public class Car {
    private final static int MAX_NAME_LENGTH = 5;
    private final static int NO_MOVE_CAR_NUMBER = 3;
    private final String name;
    private int distance;

    protected Car(String name) {
        validateCarName(name);
        this.name = name;
        distance = 0;
    }

    private void validateCarName(String name) {
        validateEmpty(name);
        validateExceeded(name);
    }

    private void validateEmpty(String name) {
        if(name.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessage.EMPTY_NAME
                    .getErrorMessage());
        }
    }

    private void validateExceeded(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(ExceptionMessage.CAR_NAME_MAX_LENGTH_EXCEEDED
                    .getErrorMessage());
        }
    }

    public void move() {
        if (Randoms.pickNumberInRange(0, 9) > NO_MOVE_CAR_NUMBER) {
            this.distance++;
        }
    }

    public String getName() {
        return name;
    }

    public int getDistance() {
        return distance;
    }
}
```

- 예외 메시지 Enum 분리. 해당 메시지는 통합 관리하는 것이 나을 것이라고 판단
```java
public enum ExceptionMessage {
    EMPTY_NAME("이름이 존재하지 않는 레이싱카가 존재합니다"),
    EMPTY_TRY_COUNT("시도 횟수가 입력되지 않았습니다"),
    NEGATIVE_VALUE_TRY_COUNT("시도 횟수가 음수로 잘못되었습니다"),
    CAR_NAME_MAX_LENGTH_EXCEEDED("레이싱카의 이름 입력의 최대 길이를 초과하셨습니다"),
    INVALID_INTEGER_FORMAT("입력된 문자가 정수로 변환되지 않습니다");

    private final String errorMessage;

    ExceptionMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

```

- 테스트 코드 작성에 가장 많은 시간을 소모했는데 어떻게 써야하는지도 잘모르다 보니 조금 찾아보기도 하고 지난번 다른 스터디 분들의 코드를 참조하면서 작성하게 되었음
- 또한 테스트하는데 있어 단위테스트로 진행하는 것이 좋고 다른 객체 생성이 서비스에 영향을 주면 좋지 않을 것이라고 생각해서 찾아봤던 Mokito 라이브러리를 사용하게 되었음

```java
private final static int MOVE_NUM = 4;
private final static int SUCCESS_MOVE_DISTANCE = 1;

// 차 이동 테스트
@Test
@DisplayName("Car_이동")
void moveCar() {
    Car car = new Car("test");

    assertRandomNumberInRangeTest(
            () -> {
                car.move();
                assertThat(car.getDistance())
                        .isEqualTo(SUCCESS_MOVE_DISTANCE);
            },
            MOVE_NUM
    );
}

// 프리코스 제공 라이브러리
/*
    해당 코드를 확인하여 사용하였는데 이해가 어렵다.. 어차피 car 객체 하나만 동작을 하기때문에 Integer... 은 필수가 아니라는 것을 확인하고 위와 같이 사용하게 되었음. 이후로도 테스트 코드를 작성해봐야겠음
*/
public static void assertRandomNumberInRangeTest(
    final Executable executable,
    final Integer value,
    final Integer... values
) {
    assertRandomTest(
        () -> Randoms.pickNumberInRange(anyInt(), anyInt()),
        executable,
        value,
        values
    );
}

// 이동 진행사항 출력 문자열 테스트
@Test
@DisplayName("차_진행사항_출력_문자열")
void carsDistanceAsString() {
    Cars cars = Mockito.mock(Cars.class);

    Car car1 = Mockito.mock(Car.class);
    Car car2 = Mockito.mock(Car.class);

    Mockito.when(car1.getName())
            .thenReturn("test1");
    Mockito.when(car2.getName())
            .thenReturn("test2");
    Mockito.when(car1.getDistance())
            .thenReturn(2);
    Mockito.when(car2.getDistance())
            .thenReturn(1);

    Mockito.when(cars.getCars())
            .thenReturn(Arrays.asList(car1, car2));

    String result = """
            test1 : --
            test2 : -
            """;

    assertThat(textProcessor.carsDistanceAsString(cars))
            .isEqualTo(result);
}
```

<br>

### 이번 코드 리뷰 피드백
- 이번에도 주로 스터디를 통해 코드리뷰를 받았음! 이번에도 역시 많은 도움이 되었고 받은 코드 리뷰는 다음과 같음
    - [받은 코드 리뷰](https://github.com/woowacourse-precourse/java-racingcar-6/pull/1304)

- 해당 클래스는 Cars 에서 getCars 를 불러와 모든 로직을 수행하는데, 이 로직들을 Cars 에 할당하는건 어떻게 생각하는지 궁금하네요. 또 getter 함수의 경우에 만약 Cars 에서 collection 의 타입을 변경한다면 Cars 뿐만 아니라 이 클래스까지 수정해야 하는 상황이 발생해 최대한 사용을 하지 않는게 좋다고 생각합니다!

```java
public class Cars {
    private final List<Car> cars;

    public Cars(String[] carNames) {
        this.cars = createCars(carNames);
    }

    private List<Car> createCars(String[] carNames) {
        return Arrays.stream(carNames)
                .map(Car::new)
                .collect(Collectors.toList());
    }

    public List<Car> getCars() {
        return cars;
    }
}
```

- cars는 var 을 사용하고 count 는 int 를 사용한 이유가 궁금합니다.
```java
    var cars = createCarsByInput(view.getCarNameInput());
    int count = getTryCountByInput(view.getTryCountInput());
```

- 생성자에서 distance를 초기화한 이유가 궁금합니다.
```java
protected Car(String name) {
        validateCarName(name);
        this.name = name;
        distance = 0;
```

- Stream 내부에서 maxCarDistance() 메서드를 호출한다면 최대 거리를 구하는 로직이 매번 호출되지 않을까요?
해당 메서드를 한번만 호출해서 지역 변수에 저장해 둔 뒤, 해당 값과 비교하면 더 효율적으로 어플리케이션을 구성할 수 있을 것 같습니다.
```java
public List<String> getWinnerCarNames(Cars cars) {
        return cars.getCars()
                .stream()
                .filter(car ->
                        car.getDistance() == maxCarsDistance(cars))
                .map(Car::getName)
                .collect(Collectors.toList());
    }

    private int maxCarsDistance(Cars cars) {
        return cars.getCars()
                .stream()
                .mapToInt(Car::getDistance)
                .max()
                .orElse(0);
    }
```

- 위와 같이 동일한 흐름의 테스트를 다른 파라미터를 사용해서 수행하고 싶을때는 @ParameterizedTest가 도움이 될 것 같습니다!!
```java
@Test
    @DisplayName("이동횟수_정수변환_예외")
    void tryCountInputParseException() {
        String input = "12t";

        assertThatThrownBy(
                () -> textProcessor.parseTryNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.INVALID_INTEGER_FORMAT
                        .getErrorMessage());
    }
```

<br>

### 이후 다른 사람 코드 확인
- Input이나 Output 등 한번만 인스턴스를 생성해서 사용할 것들은 싱글톤으로 관리
```java
private static final OutputView instance = new OutputView();

    public static OutputView getInstance() {
        return instance;
    }
```

- Consumer 인터페이스를 통한 람다식 enum 관리도 있었음

```java
public enum Validator {
    NUMBER(input -> {
        if (!input.matches("^[0-9]+$")) {
            throw new IllegalArgumentException(ExceptionType.NUMBER.message());
        }
    }),
    NAME(input -> {
        if (!input.matches("^\\S{1,5}$")) {
            throw new IllegalArgumentException(ExceptionType.NAME.message());
        }
    });

    private final Consumer<String> expression;

    Validator(Consumer<String> expression) {
        this.expression = expression;
    }

    public void validate(String input) {
        expression.accept(input);
    }
``` 

- 테스트 코드 람다식으로 테스트하는 것도 있었음
```java
 @Test
    void 올바른_입력() {
        assertAll(
                () -> assertThatCode(() -> Validator.NUMBER.validate("12")).doesNotThrowAnyException(),
                () -> assertThatCode(() -> Validator.NAME.validate("pobi")).doesNotThrowAnyException()
        );
    }
```

- 정규 표현식은 생성 비용이 커서 바로 사용하는 것 보다 재사용 가능하게 사용하는 것이 좋다고 함
```java
Pattern pattern = Pattern.compile("^[0-9]+$");
Matcher matcher = pattern.matcher(input);
```

<br>

### 프리코스 개인 스터디 발표 진행
- 스터디 발표는 각자 질문을 포함하여 15분씩 진행을 하였고 총 7명의 인원으로 2시간 가까이 진행 하였음
    - 손장미 님은 개인 사정으로 프리코스와 스터디를 이번까지만 진행..
- 저는 스프링에서 사용되는 주요 디자인 패턴에 대해 정리하고 발표를 진행하였는데 발표를 너무 부족하게 진행했던 것 같음. 정리를 한 내용을 조금 더 다른 사람들이 이해하기 쉽게 예시에 대해 확실한 설명을 진행해야 할 것 같다고 느낌..
    - [스프링 주요 디자인 패턴 정리](https://github.com/InJun2/TIL/blob/main/CS-topic/Design%20Pattern/Design-Pattern.md)
- 그래도 다양한 주제 발표로 다양하게 특정 토픽에 접근해보는 시간은 좋은 시간이라고 느껴졌으며 다음번은 좀 더 발표하는 것에도 집중해야겠음

>- 손장미님 크리티컬 섹션 발표
>   - https://unequaled-peach-7e5.notion.site/Critical-Section-473ffc00b23d46c385a3de874d253951
>- 임규리님 SOLID 발표
>   - https://newbie-in-softengineering.tistory.com/119
>- 최윤혁 MVC 패턴 발표
>   -  https://equinox-laborer-a10.notion.site/MVC-Pattern-960605ce199f4873b7230f2a08ee1460?pvs=4
>- 황하림님 예외처리 발표
>   - 올바른 예외 처리 / 관리
>- 문서영님 OS Overview 발표
>   - 인터럽트, CPU 스케줄링 일부
>- 장혁수님 동작파라미터화 발표 
>   - 직접 구현하여 동작 시연

<br>

### 이후 고민중인 사항
- view는 한번만 생성하면 되는 코드라면 싱글톤으로 관리해보는 것은 어떨까?
- 그런데 this.view = new RacingCarView(); 로 애초에 한번만 객체를 생성해서 해당 객체만으로 사용을 하고 있음.. 근데 어차피 프로그램 내에서 한번만 실행하는 메소드를 굳이 싱글톤으로 쓸 필요가 있나..? (서버에서 한번만 생성되고 해당 인스턴스를 많은 요청이 온다면 효과적이라고 생각) 고민을 조금 더 해봐야겠음
```java
public class InputView {
    private static RacingCarView view;

    private View() {
    }

    public static void close() {
        if (view != null) {
            view.close();
            view = null;
        }
    }

    private static RacingCarView getInstance() {
        if (view == null) {
            view = new RacingCarView();
        }

        return view;
    }
}

public class RacingCarView {
    private final static String START_INPUT = "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)";
    private final static String TRY_COUNT_INPUT = "시도할 회수는 몇회인가요?";
    private final static String RACING_RESULT_OUTPUT = "실행 결과";
    private final static String RACING_WINNER_OUTPUT = "최종 우승자 : ";
    private final static String NEW_LINE = "\n";

    public String getCarNameInput() {
        System.out.println(START_INPUT);
        return Console.readLine();
    }

    public String getTryCountInput() {
        System.out.println(TRY_COUNT_INPUT);
        return Console.readLine();
    }

    public void racingResultOutput() {
        System.out.println(NEW_LINE + RACING_RESULT_OUTPUT);
    }

    public void racingCarsDistanceOutput(String str) {
        System.out.println(str);
    }

    public void racingWinnerOutput(String str) {
        System.out.print(RACING_WINNER_OUTPUT + str);
    }
}

```

- 자바카페 독서 스터디에서 해당 코드를 리뷰해주셨음! 해당 내용은 다음 코드 리뷰 이후 인지하고 적용하는 과정을 가질 예정
    - 객체가 가지고 있을 행동(메서드)을 객체에서 구현하고 서비스에서 실행하기
    - static final 순서가 관습에 알맞음
    - 객체가 가지고 있는 상태를 String으로 반환하는 것은 toString()을 오버라이딩하여 사용하는 것도 좋은 방법
    - 우테코 프리코스에서 제공해주는 Randoms 라이브러리의 랜덤값을 구하는 단위 테스트가 미흡. 해당 기능은 mock 객체 없이 테스트하도록 설계하는 것이 맞는 방향이며 이후 추상화와 상속을 통한 문제 해결 예정
    - 테스트 파라미터를 @CsvSource, @ValueSource, @MethodSource, @NullAndEmptySource 등의 어노테이션 사용이 편리

- 객체를 관리하는 DTO를 생성해서 사용하는 것은 어떨지 ..? 영속성 컨텍스트가 없다보니 굳이 데이터를 저장하는 과정이 없다보니 굳이 DTO를 생성할 필요가 없다고 생각했었는데 Cars 컬렉션이 변경된다면 모든 서비스 코드도 수정된다라는 부분 등 좀더 객체를 사용하는 방법에 대해 고민해봐야할 것 같음

- 현재 var 키워드를 사용하였으나 사용하기 편리하고 이후 타입이 바뀌어도 코드를 바꿀필요 없어 유지보수성이 존재하고 import 코드를 줄일 수 있으나 내가 원하지 않는 타입대로 추론할 수도 있다고 하며 코드리뷰 진행시 다른 사람이 코드를 분석할 때 타입을 아는 것이 어려운 것 같아 이후 프리코스 진행에서는 사용하지 않을 것 같음

<br>

### 이후 코수타를 통해 느낀점
- 스파게티 코드를 조심해라
- 깃허브에 디스커션이라는 게 있음. 주로 주제에 대해 이야기하거나 리뷰를 통해 많이 성장했음
- 함수형 프로그래밍과 테스트에 대해 좀더 공부하는 것이 좋을 것 같음
- 기능을 잘게잘게 분리하는 것이 가독성에 있어서 좋은 코드라는 것을 코드리뷰를 통해 알게 되는 것 같음