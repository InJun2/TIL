# CHAPTER12. 다형성

![Polymorphism](./img/polymorphism.png)

## 1. 다형성 (Polymorphism)
- 다형성(Polymorphism)이라는 단어는 그리스어에서 '많은'을 의미하는 'poly'와 '형태'를 의미하는 'morph'의 합성어로 '많은 형태를 가질 수 있는 능력'을 의미
- 상속을 사용하려는 이유가 단지 코드를 재사용하기 위해서라면 좋은 방법이 아님
    - 상속이 객체지향 패러다임 초기에는 타입 계층과 다형성을 구현할 수 있는 거의 유일한 방법이었으나 현재는 아님
- 클라이언트 관점에서 인스턴스들을 동일하게 행동하는 그룹으로 묶기 위해서 상속을 사용하는 것이 좋음
    - 런타임 시점에서 메시지를 처리하기 적합한 메서드를 동적으로 탐색하기 위함
- 객체지향 프로그래밍에서 사용되는 다형성은 다음과 같이 유니버설(Universal) 다형성과 임시(Ad Hoc) 다형성으로 분류할 수 있음
    - 유니버설 다형성 : 여러 타입에 대해 동일한 방식으로 동작할 수 있는 다형성으로 범용적으로 사용이 가능
    - 임시 다형성 : 특정 타입에만 국한되어 동작하는 다형성으로 일반적으로 구체적인 구현이 필요
    - 즉, 정리하면 컴파일 시점의 코드를 고정적으로 실행하는 것이 임시 다형성, 런타임 시점에 유연하게 동적으로 실행하는 것이 유니버설 다형성으로 동작하는 시점과 유연성에서 차이가 있음
- 유니버설 다형성은 매개변수 다형성과 포함 다형성으로 분류됨
    - 매개변수 다형성 : 제네릭 프로그래밍과 관련이 높은데 클래스의 인스턴스 변수나 메서드의 매개변수 타입을 임의의 타입으로 선언한 후 사용하는 시점에 구체적인 타칩으로 지정하는 방식
    - 포함 다형성 : 메시지가 동일하더라도 수신한 객체의 타입에 따라 실제로 수행되는 행동이 달라지는 능력을 의미하며 서브타입(Subtype) 다형성이라고도 부름. 가장 널리 알려진 형태의 다형성으로 특별한 언급이 없으면 다형성은 포함 다형성을 의미하는 것이 일반적
- 임시 다형성은 오버로딩 다형성과 강제 다형성으로 분류됨
    - 오버로딩 다형성 : 일반적으로 하나의 클래스 안에 동일한 이름의 메서드가 존재하는 경우
    - 강제 다형성 : 언어가 지원하는 자동적인 타입 변환이나 사용자가 직접 구현한 타입 변환을 이용해 동일한 연산자를 다양한 타입에 사용할 수 있는 방식
- 오버로딩 다형성과 강제 다형성을 함께 사용하면 실제로 어떤 메서드가 호출도리지 판단하기 어려워 모호해질 수 있음
- 포함 다형성을 구현하는 가장 일반적인 방법은 상속을 사용하는 것으로 두 클래스를 상속 관꼐로 연결하고 자식 클래스에서 부모 클래스의 메서드를 오버라이딩 한 후 클라이언트는 부모 클래스만 참조하면 포함 다형성을 구현할 수 있음
    - 그렇기에 서브타입 다형성이라고 부르며 상속 외에도 서브타입 관계를 만들 수 있는 다양한 방법이 존재
    - 또한 책에서는 이러한 포함 다형성 중심적으로 다룬다고 함

<br>

### 다형성 별 예시 코드
- 아래 코드는 생성자와 미사용 변수 등의 코드를 대부분 줄이고 할인 정책과 금액으로만 구현한 기본코드임

```java
// 할인 정책 인터페이스
interface DiscountPolicy {
    double calculateDiscountAmount(Movie movie);
}

// 금액 할인 정책
class AmountDiscountPolicy implements DiscountPolicy {
    private double discountAmount;

    @Override
    public double calculateDiscountAmount(Movie movie) {
        return discountAmount;
    }
}

// 퍼센트 할인 정책
class PercentDiscountPolicy implements DiscountPolicy {
    private double percent;

    @Override
    public double calculateDiscountAmount(Movie movie) {
        return movie.getFee() * percent;
    }
}

// 영화 클래스
class Movie {
    private double fee;
    private DiscountPolicy discountPolicy;

    public Movie(double fee, DiscountPolicy discountPolicy) {
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public double calculateFinalFee() {
        return fee - discountPolicy.calculateDiscountAmount(this);
    }
}
```

<br>

### 1. 매개변수 다형성 예시
- 제네릭을 사용하여 다양한 타입으로 동작하는 다형성

```java
// 할인 정책 관리자 (제네릭 사용)
class DiscountManager<T extends DiscountPolicy> {
    private T policy;

    public double applyDiscount(Movie movie) {
        return policy.calculateDiscountAmount(movie);
    }
}

// 사용 예시
public static void main(String[] args) {
    DiscountManager<AmountDiscountPolicy> amountManager =
        new DiscountManager<>(new AmountDiscountPolicy(5000));
}
```

<br>

### 2. 포함 다형성 예시
- 인터페이스와 상속을 활용해 런타임 시점에 다른 정책을 적용하는 다형성
- 포함 다형성을 위해 상속을 사용하는 가장 큰 이유는 상속이 클래스들을 계층으로 쌓아올리고 상황에 따라 적절한 메서드를 선택할 수 있는 매커니즘을 제공하기 때문

```java
public static void main(String[] args) {
    // PercentDiscountPolicy를 적용
    Movie movie = new Movie(15000, new PercentDiscountPolicy(0.2));
    System.out.println("Movie Final Fee: " + movie.calculateFinalFee());
}
```

<br>

### 3. 오버로딩 다형성
- 같은 이름의 메서드로 다양한 매개변수를 처리하는 다형성

```java
class DiscountCalculator {
    // 금액 할인 계산
    public double calculate(Movie movie, AmountDiscountPolicy policy) {
        return movie.getFee() - policy.calculateDiscountAmount(movie);
    }

    // 퍼센트 할인 계산
    public double calculate(Movie movie, PercentDiscountPolicy policy) {
        return movie.getFee() - policy.calculateDiscountAmount(movie);
    }
}

// 사용 예시
public static void main(String[] args) {
    Movie movie = new Movie("Batman", 15000, null);

    DiscountCalculator calculator = new DiscountCalculator();
    System.out.println("Amount Discount: " + calculator.calculate(movie, new AmountDiscountPolicy(3000)));
    System.out.println("Percent Discount: " + calculator.calculate(movie, new PercentDiscountPolicy(0.15)));
}
```

<br>

### 4. 강제 다형성
- 타입 변환을 활용해 동일한 연산자나 메서드를 다른 타입에 적용

```java
// 금액 할인 정책
class AmountDiscountPolicy implements DiscountPolicy {
    private double discountAmount;

    // int 값을 받아 내부에서 double로 강제 변환
    public AmountDiscountPolicy(int discountAmount) {
        this.discountAmount = (double) discountAmount;
    }

    public AmountDiscountPolicy(double discountAmount) {    // 오버로딩 다형성
        this.discountAmount = discountAmount;
    }

    // ...
}

// 퍼센트 할인 정책
class PercentDiscountPolicy implements DiscountPolicy {
    private double percent;

    // int 값을 받아 내부에서 double로 강제 변환
    public PercentDiscountPolicy(int percent) {
        this.percent = (double) percent / 100; // 퍼센트 값을 0~1 사이로 변환
    }

    public PercentDiscountPolicy(double percent) {  // 오버로딩 다형성
        this.percent = percent / 100;
    }

    // ...
}

// 영화 클래스
class Movie {
    private double fee;
    private DiscountPolicy discountPolicy;

    // ...

    // int 값을 받아 내부에서 double로 강제 변환
    public Movie(String title, int fee, DiscountPolicy discountPolicy) {
        this.fee = (double) fee; // int -> double 변환
        this.discountPolicy = discountPolicy;
    }
}
```

<br>

## 2. 상속의 양면성
- 객체지향 패러다임의 근간을 이루는 아이디어는 데이터와 행동을 객체라고 불리는 하나의 실행단위 안으로 통합하는 것
    - 즉, 객체지향 프로그래밍을 작성하기 위해서는 항상 데이터와 행동이라는 두 가지 관점을 함께 고려해야 함
- 상속 역시 부모 클래스에서 정의한 모든 데이터를 자식 클래스의 인스턴스에 자동으로 포함시킬 수 있는데 이것은 데이터 관점의 상속임
- 또한 데이터 뿐만 아니라 부모 클래스에서 정의한 일부 메서드 역시 자동으로 자식 클래스에 포함시킬 수 있는데 이것은 행동 관점의 상속임
- 이러한 상속에서 단순히 부모 클래스에서 정의한 데이터와 행동을 자식에게 자동으로 공유할 수 있는 재사용 매커니즘으로 보일 수 있지만 이 관점은 상속을 오해한 것
- 상속의 목적은 재사용이 아니라 프로그램을 구성하는 개념들을 기반으로 다형성을 가능하게 하는 타입 계층을 구축하기 위한 것
- 고민 없이 재사용하기 위해 상속을 사용하면 이해하기 어렵고 유지보수 하기 어려운 코드가 만들어질 확률이 높음
- 이번 장에서 상속의 매커니즘을 이해하는데 필요한 개념은 다음과 같다고 함
    - 업캐스팅
    - 동적 메서드 탐색
    - 동적 바인딩
    - self 참조
    - super 참조
- 