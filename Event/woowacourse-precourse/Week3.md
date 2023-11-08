# 우아한 테크코스 프리코스 Week3

### 우아한 테크코스 프리코스 3주차 진행
- 이번 2주차 프리코스 과제는 로또 이다. 자세한 설명은 다음과 같음
    - [Fork Git](https://github.com/InJun2/java-lotto-6)
- 이번에도 테스트를 단위테스트로 진행하고 이전 코드리뷰를 통해 제안 받은 어노테이션을 사용하여 테스트 코드를 구현할 예정

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