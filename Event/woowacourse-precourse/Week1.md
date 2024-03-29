# 우아한 테크코스 프리코스 Week1

### 지원 동기
- 지인과 스터디를 진행하고 있던 중 우아한 테크코스 채용을 확인하게 되었고 지인과 같이 우아한 테크코스 프리코스를 신청하게 되었는데 공부를 진행하는 방법에 대해 아직 고민을 하기도 하며 지금까지 정착시킨 객체지향 개념에 대해 직접 구현해보고 피드백을 받아 볼 수 있을 것이라는 생각으로 지원하게 되었음
    - 물론 되면 좋겠지만 경쟁률이 너무 세..
- 해당 지인과 구조를 설계하고 구현하는 방법에 대해 배울 수 있는 좋은 기회라고 생각하게 되어 신청하였음!

<br>

### 우아한 테크코스 프리코스 1주차 과제 진행
- 1주차 프리코스 과제는 숫자 야구 게임으로 룰과 내가 구현한 코드는 다음과 같음
    - [BaseBall Game](https://github.com/woowacourse-precourse/java-baseball-6)
- 기존 알고있던 객체지향 개념을 적용하는 것은 또 다른 문제로 구조 설계부터 난항을 겪기 시작..
- 우선 MVC 패턴을 적용해보려 했으나 매번 요청이 들어가서 서비스를 연결해주는 Controller나 영속성 컨텍스트에 접근하기 위한 Repository가 불필요하다고 생각되어 애플리케이션을 실행할 필요가 없다고 생각하게 되었음 그래서 Run -> View, Run -> Service로 접근하여 설계를 진행하였음
- 입력에 대해 유효성 검사를 진행하거나 객체 내부에서 생성자를 통해 InputNumbers 유효성 검사를 진행
- 문자열을 받아오거나 출력하는 View 로 구현
- 테스트를 짜본 경험이 거의 없다보니 짜본 방법도 모르고 내가 직접 짜도 되는지 모르겠어서 따로 구현하지 못했음

<br>

### 스터디를 통한 코드 리뷰 피드백
- 해당 코드를 구현하고 구성했던 스터디를 통해 코드리뷰를 받았는데 인지하지 못했던 부분이나 더 나은 해결 방법 등 다양하게 배울 수 있던 효과적인 방법인것 같음
    - [받은 코드 리뷰](https://github.com/woowacourse-precourse/java-baseball-6/pull/2369)
- 그 외에도 다른 사람의 코드를 리뷰하면서 오히려 내가 배우는 경우도 많았고 내가 아는 지식을 다시 한번 확인해보는 시간을 가지는 등 좋은 영향이 많았음
- 이후 2주차 이후 과제에서는 해당 코드리뷰나 다른 사람의 구현 방법을 바탕으로 더 객체지향적인 코드를 구현하는 것과 테스트 코드를 하나하나 작성하는 것이 목표가 되는 것 같음

<br>

### 스터디 개설
- 1주차 프리코스 과제를 마치고나서 우아한 테크코스 수코타를 시청하고나서 다른 사람들의 구현 방법이나 의견이 궁금했고 같이 공부해서 공유할 수 있으면 좋겠다고 생각해서 우아한 테크코스 프리코스의 디스코드 방을 통해 개념 발표 및 코드리뷰를 진행하는 스터디를 개설하여 진행하려고 함
- 아무래도 발표는 준비를 진행하고 매주 목요일에 하기로하여 다음부터 진행을 하게되었고 각자 발표를 통해 지식을 공유하는 시간이 기대!
- 또한 생각보다 코드리뷰가 가지는 가치가 크다고 느껴지는 시간이었음. 다른 사람들의 구조나 구현 방법은 내가 생각하지 못했던 부분을 사용하시기도 했고 코드에서 개선해야하는 사항을 인지하는 등 객체지향에 대해 다시 한번 생각해보는 시간을 가질 수 있었는데 진행하면서도 많이 배웠다고 생각했는데 코드리뷰를 통해 인지하고 다시 배우는 기회를 얻었다고 생각함
- 아직 해당 스터디로 진행한 것은 코드리뷰 정도 뿐이지만 벌써 좋은 성과가 있기도 하고 기대도 되다보니 스터디를 진행하는 것은 좋은 생각이었던 것 같음