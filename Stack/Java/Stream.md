# Stream

### Stream API?
- Java는 객체지향적언어로 기본적으로는 함수형 프로그래밍이 불가능함
- jdk8 부터 Stream API, 람다식, 함수형 인터페이스 등 함수형 프로그래밍할 수 있게 해주는 API 들을 제공
- Stream API는 데이터를 추상화하고 처리하는데 자주 사용되는 함수들을 정의 해둠 -> 추상화 해두어 데이터 종류에 상관없이 데이터를 처리 가능
```
함수형 프로그래밍?
- 등장 배경은 명령형 프로그래밍은 소프트웨어의 크기가 커짐에 따라 복잡하게 엉켜있어 유지보수가 매우 힘듬 -> 함수형 프로그래밍이라는 패러다임에 관심을 가지게 됨
- 함수형 프로그래밍은 거의 모든 것을 순수 함수로 나누어 문제를 해결하는 기법으로 작은 문제를 해결하기 위한 함수를 작성하여 가독성을 높이고 유지보수를 용이하게 해줌
- 클린코드에서 함수형 프로그래밍을 대입문이 없는 프로그래밍이라고 정의하였음
- 기존의 for문과 다르게 지역변수가 필요하지 않음. 해당 지역 변수 자체를 없애 부수 효과를 제거하여 의도하지 않은 문제 해결이 가능

명령형 프로그래밍 : 무엇을 (What) 할것인지 나타내기보다 어떻게 (How) 할건지를 설명하는 방식
    - 절차지향 프로그래밍
    - 객체지향 프로그래밍
선언형 프로그래밍 : 어떻게 할건지보다 무엇을 할건지를 설명하는 방식
    - 함수형 프로그래밍 : 순수 함수를 조합하고 소프트웨어를 만드는 방식
```
<br>

#### Stream API의 특징
1. 원본의 데이터를 변경하지 않음
    - Stream API는 원본의 데이터를 조회하여 원본의 데이터가 아닌 별도의 요소들로 Stream을 생성함
    - 원본의 데이터로부터 읽기만 할 뿐이며, 정렬이나 필터링 등의 작업은 별도의 Stream 요소에서 처리가 됨
2. 일회용으로 사용 
    - Stream API는 일회용이기 때문에 한번 사용이 끝나면 재사용이 불가능
    - Stream이 또 필요한 경우에는 Stream을 다시 생성해주어야 함. 만약 닫힌 Stream을 다시 사용한다면 IllegalStateException이 발생
3. 내부 반복으로 작업을 처리
    - Stream을 이용하면 코드가 간결해지는 이유는 내부 반복
    - 기존에는 반복문을 사용하기 위해 for이나 while 같은 문법을 사용해야 했지만 stream에서는 그러한 반복 문법을 메소드 내에 숨기고 있어 보다 간결한 코드 작성이 가능

<br>

### Optional?
- null을 대신하기 위해 만들어진 코어 라이브러리 데이터 타입
- Optional 클래스는 null이나 null이 아닌 값을 담을 수 있는 클래스
- java8 부터 지원
- Optional 객체는 3가지 메소드로 생성
    - Optional.empty() : 빈 Optional 객체 생성
    - Optional.of(value) : value 값이 null이 아닌 경우 사용
    - Optional.ofNullable(value) : value 값이 null인지 아닌지 확실하지 않은 경우 사용
- Optional 객체 접근 및 사용 방법
    - ifPresent(함수) : Optional 객체가 non-null이 경우에 인자로 넘긴 함수를 실행하는 메서드. Optional 객체가 null이면 인자로 넘긴 함수는 실행되지 않음
    - orElse() : Optional에 담고 있는 객체가 null인 경우 대신할 수 있는 값을 반환하는 메서드
    - orElseGet() : orElse()와 유사하지만 다른 점은 메서드를 인자로 받고 함수에서 반환한 값을 반환하게 되어 있음
    - orElseThrow() : null인 경우 기본 값을 반환하는 대신 예외를 던짐
    - or() : Optional이 empty인 경우 다른 Optional을 반환. 
    - ifPresentOrElse() : Optional에 값이 존재하면 action 수행하고 아닌 경우에는 else 부분을 수행
    - stream() : Optional 객체를 Stream 객체로 변환

<br>

### Stream
- 자바의 스트림은 Java8 부터 지원되기 시작한 기능
- 컬렉션에 저장되어 있는 엘리먼트들을 하나씩 순회하면서 처리할 수 있는 코드 패턴 -> 데이터 컬렉션 반복을 멋지게 처리하는 방법
- 자료구조가 포함하는 모든 값을 메소드에 포함하는 컬렉션과 다르게 스트림은 게으른 생성을 통해 요청할 때만 요소를 계산하여 고정된 자료구조를 가짐 
- 스트림은 특정 연산자를 사용할 때 여러개의 조건이 중첩된 상황에서 값이 결정나면 불필요한 연산을 진행하지 않고 조건문을 빠져나와 실행 속도를 높임
- 스트림은 스트림생성 -> 중간 연산 -> 최종연산으로 사용함
- 람다식과 함께 사용되어 컬렉션에 들어있는 데이터에 대한 처리를 매우 간결한 표현으로 작성 가능
- 내부 반복자를 사용하기 때문에 병렬처리가 쉬움
- 코드를 단순하고 가독성있게 작성 가능

<br>

### 스트림 사용 순서 및 사용법
#### 1. 스트림 생성 -> 2. 중간 연산 -> 3. 최종 연산
- Stream 연산이 연결되어 있는 해당 모양이 파이프 같아 보여 파이프라이닝이라고 부르기도 함 
- 스트림은 최종 연산이 들어오고 나서 실행 시작함 -> 그 결과 요소를 중간 연산으로 붙이는데 이런식의 데이터 플로우를 루프 퓨전이라고 함
- 스트림 파이프 라인 개념은 빌더 패턴과 비슷. 빌더 패턴에서는 호출을 연결해서 설정을 만듬. 그리고 준비된 설정에 build 메서드를 호출함
```
스트림 생성
- Stream 객체를 생성하는 단계
- Stream 은 재사용이 불가능하므로 닫히면 다시 생성해주어야 함 -> 연산이 끝나면 Stream이 닫힘
- Stream 객체는 배열, 컬렉션, 임의의 수, 파일 등 거의 모든 것을 가지고 스트림을 생성할 수 있음

중간 연산
- 원본의 데이터를 별도의 데이터로 가공하기 위한 중간 연산 -> 데이터를 가공하는 역할
- 연산 결과를 Stream으로 다시 반환하기 때문에 연속해서 중간 연산을 이어갈 수 있음
- 중간 연산은 중간 연산을 합친 다음에 최종 연산으로 한번에 처리하기 때문에 단말 연산을 스트림 파이프라인에 실행하기 전까지 아무 연산도 수행하지 않음
- filter, map, list, sorted, distinct

최종 연산
-  가공된 데이터로부터 원하는 결과를 만들기 위한 최종 연산으로 Stream의 요소들을 소모하면서 연산이 수행하기 때문에 1번만 처리 가능
- 스트림 파이프라인에서 결과를 도출
- 보통 최종 연산에 의해 List, Integer, void 등 스트림 이외의 결과가 반환됨
- forEach, count, collect

빌더 패턴
- 필요한 데이터만 설정해 객체 생성이 용이하고 불필요한 코드양을 줄임
- 새로운 변수가 추가되는 상황이 생겨도 유연성 확보 가능
- 가독성이 높아짐
- Setter와 다르게 불필요한 변경 가능성을 최소화할 수 있음
ex) User user = User.builder()
             .name("망나니 개발자")
             .height(180)
             .iq(150).build();
```

<br>

#### 스트림 생성
- 우선 자바 스트림을 사용하려면 스트림 객체를 생성하여야 함
- 자바 코드에서 자주 사용하는 컬렉션 객체들은 stream 메소드를 지원함. 컬렉션 객체에서 stream() 메소드를 호출하면 스트림 객체를 만들 수 있음
- 배열도 마찬가지로 스트림을 생성할 수 있음
- 스트림객체는 빌더 형태로 생성 가능함
- generate 메소드를 이용하여 데이터를 생성하는 람다식을 이용해 스트림 생성 가능.
- Iterate 메소드를 이용하여 수열 형태의 데이터 생성 가능
- Empty 스트림 생성 가능
- 기본 타입을 이용하여 스트림 생성 시 기본 타입 (Primary Type)에 대해서 오토박싱과 언박싱이 존재. int 변수를 다룰 때 Integer 클래스로 오토박싱해서 처리하는 경우 오버헤드 발생하여 성능저하 있을 수 있음
- 문자열에 대해서도 스트림 생성 가능. 특정 구분자를 이용하여 문자열을 스플릿 한 후 각각을 스트림으로 뽑아낼 수도 있음
- 텍스트 파일을 읽어 라인단위로 처리하는 코드는 흔한데 해당 코드 역시 스트림으로 작성 가능
- 두개의 스트림을 연결하여 하나의 스트림으로 만들기도 가능
```java
// 배열
IntStream intStream = Arrays.stream({1,2,3,4,5});

// 컬렉션
List<String> list = Arrays.asList("test1","test2","test3");
Stream<String> stream = list.stream();

// 빌더
Stream<String> stream = Stream<String>builder()
                            .add("Aplle")
                            .add("Banana")
                            .add("Melon")
                            .build();

// 람다식, 무한대로 생성을 막기위해 limit를 둠. Supplier<T> 인터페이스로 람다식이 데이터를 생성
Stream<String> stream = Stream.generate(() -> "Hello").limit(5);

// Supplier에 해당하는 람다식이 데이터를 생성하는 람다식임
public static<T> Stream<T> generate(Supplier<T> s){ ... }

// iterate를 이용한 스트림 생성, 값은 (100,110,120,130,140)
Stream<String> stream = Stream.iterate(100, n -> n + 10).limit(5);

// Empty Stream
Stream<String> stream = Stream.empty();

// 기본타입 boxing 하여 처리
IntStream intStream = IntStream.range(1,10); // 1~9
LongStream longStream = LngStream.range(1,10000); // 1~9999

// 제네릭을 이용한 클래스로 사용하려면 박싱을해서 사용
Stream<Integer> stream = IntStream.range(1,10),boxed();

// 정해진 값이 아닌 랜덤 값을 스트림으로 뽑아내려면 Random() 클래스 사용
DoubleStream stream = new Random().double(3);   // double형 랜덤 숫자 3개 생성

// 문자열에 대해 스트림 생성 , ASCII 코드 값을 스트림 형태
IntStream stream = "Hello, World".chars();      // (72, 101, 108, 108, 111, 44, 87, 111, 114, 108, 100)

// 특정 구분자를 이용하여 문자열을 스플릿 후 스트림
Stream<String> stream = Pattern.compile(",").splitAsStream("Apple,Banana,Melon");

// 텍스트 파일을 읽어서 라인단위로 처리하는 코드에서 스트림
Stream<String> stream = Files.lines(Paths.get("test.txt"), Charset.forName("UTF-8"));

// 두 개의 스트림 연결
Stream<String> stream1 = Stream.of("Apple", "Banana", "Melon");
Stream<String> stream2 = Stream.of("Kim", "Lee", "Park");
Stream<String> stream3 = Stream.concat(stream1, stream2);
```

<br>

### 중간 연산 (스트림 데이터 가공)
- 스트림 객체가 뽑아내는 데이터들에 대해 작업을 하기위한 방법
- 특정 데이터들만 걸러내거나 데이터에 대해 가공이 가능
- 데이터를 가공해주는 메소드들은 가공된 결과를 생성해주는 스트림 객체를 리턴
- 스트림 데이터 가공방식은 Filter, Map, flatMap, Sorted, Peek 등등이 존재

<br>

#### 1. Filter (필터링)
- 스트림에서 뽑아져 나오는 데이터에서 특정 데이터들만 골라내는 역할
- 조건에 맞는 데이터만을 정제하여 더 작은 컬렉션을 만들어내는 연산. 
- filter함수의 인자로 함수형 인터페이스 Predicate를 받아 boolean을 반환하는 람다식을 작성하여 구현. 해당 데이터에 대해 람다식을 적용해서 true가 리턴되는 데이터만 선별
- 사용 예시
```java
Stream<Integer> stream = IntStream.range(1,10).boxed();
stream.filter(v -> ((v % 2) == 0))
            .forEach(System.out::println);  // 2,4,6,8
```

<br>

#### 2. Map (데이터변환)
- 스트림에서 뽑아져 나오는 데이터에 변경을 가해줌
- map 메소드는 값을 변환해주는 람다식을 인자로 받음
- 스트림에서 생성된 데이터에 map 메소드의 인자로 받은 람다식을 적용해 새로운 데이터를 만들어 냄 -> 
```java
Stream<Integer> stream = IntStream.range(1,10).boxed();
stream.filter(v -> ((v % 2) == 0))
            .map(v -> v * 10)
            .forEach(System.out::println); // 20, 40, 60, 80

Stream<String> stream = 
            names.stream()
            map(s -> s.toUpperCase());

//Stream<File> --> Stream<String> 변환도 가능
Stream<File> fileStream = Stream.of(new File("Test1.java"), new File("Test2.java"), new File("Test3.java"));
Stream<String> fileNameStream = fileStream.map(File::getName);
```

<br>

#### 3. FlatMap (중첩 구조 제거)
- map 메소드와 비슷한 역할로 flatMap 메소드의 인자로 받는 랍다는 리턴 타입이 Stream
- 즉 새로운 스트림을 생성해서 리턴하는 람다를 인자로 받음
- flatMap은 중첩된 스트림 구조를 한단계 적은 단일 컬렉션에 대한 스트림으로 만들어 주는 역할을 함 -> 프로그래밍에서는 플랫트닝(Flattening)이라고 함
- 람다는 (e) -> Collection.steam(e)이며 축약하면 Collection::stream으로 사용
```java
List<List<String>> list = Arrays.asLists(Arrays.asList("A","B","C")), Arrays.asList("a","b","c");
List<String> flatList = list.stream()
            .flatMap(Collection::stream)
            .collect(Collection.toList());  // ["A","B","C","a","b","c"]
```

<br>

#### 4. Sorted (정렬)
- 스트림 데이터들을 정렬하고자 할 때, sorted 메소드를 이용
- 인자없이 sorted 메소드를 호출하면 오름차순으로 정렬. 만약 정렬시 두 값을 비교하는 별도의 로직이 있다면 comparator를 sorted 메소드의 인자로 넘겨줄 수 있음
- 내림차순으로 정렬하기 위해서는 Comparator의 reverseOrder를 이용하면 됨
```java
Stream<T> sorted();
Stream<T> sorted(Comparator<? super T> comparator);

List<String> list = Arrays.asList("Java", "Scala", "Groovy", "Python", "Go", "Swift");

// [Go, Groovy, Java, Python, Scala, Swift]
Stream<String> stream = list.stream()
            .sorted()

// [Swift, Scala, Python, Java, Groovy, Go]
Stream<String> stream = list.stream()
            .sorted(Comparator.reverseOrder())
```

<br>

#### 5. Distinct (중복제거)
- Stream의 요소들에 중복된 데이터가 존재하는 경우, 중복을 제거하기 위해 distinct를 사용
- distinct는 중복된 데이터를 검사하기위해 Object의 equals() 메소드를 사용
```java
List<String> list = Arrays.asList("Java", "Scala", "Groovy", "Python", "Go", "Swift", "Java");

// [Java, Scala, Groovy, Python, Go, Swift]
Stream<String> stream = list.stream()
            .distinct()
```

<br>

#### 6. Peek (특정연산수행)
- 스트림 내 엘리먼트들을 대상으로 map() 메소드처럼 연산을 수행. 그러나 map과 다른점은 Stream의 요소들을 대상으로 Stream에 영향을 주지 않고 특정 연산만을 수행하기 위한 함수. peek은 확인해본다 라는 뜻을 지님
- Stream 각각의 요소들에 대해 특정 작업을 수행할 뿐 결과에 영향을 주지 않음. peek 함수는 파라미터로 함수형 인터페이스 Consumer를 인자로 받음
- 하지만 새로운 스트림을 생성하지는 않고 인자로 받은 람다를 적용하기만 함
- 생성된 데이터들에 변형을 가하지 않고 인자로 받은 람다식만 한번 실행해보기만 함
```java
Stream<T> peek(Consumer<? super T> action);

int sum = IntStream.range(1,10)
            .peek(System.out::println)
            .sum();
```

<br>

#### 7. mapToObject, mapToInteger.. (원시 Stream <-> Stream)
- 일반적인 Stream 객체를 원시 Stream 으로 바꾸거나 그 반대로 하는 작업이 필요한 경우가 있음
- 이러한 경우를 위해서 일반적인 Stream 객체는 mapToInt(), mapToLong(), mapToDouble() 이라는 특수한 Mapping 연산을 지원
- 반대로 원시객체는 mapToObejct를 통해 일반적인 Stream 객체로 바꿀 수 있음
```java
// IntStream -> Stream<Integer>
IntStream.range(1, 4)
    .mapToObj(i -> "a" + i)

// Stream<Double> -> IntStream -> Stream<String>
Stream.of(1.0, 2.0, 3.0)
    .mapToInt(Double::intValue)
    .mapToObj(i -> "a" + i)
```

<br>

### 최종 연산 (Stream 결과)
- 중간 연산을 통해 생성된 Stream을 바탕으로 이제 결과를 만들기 위한 연산
- 최종 연산은 결과를 특정짓기 때문에 하나만 존재 가능하다.
- 최종연산은 Max/Min/Sum/Average/Count, collect, Match, forEach 등이 존재

<br>

#### 1. Max/Min/Sum/Average/Count (최댓값/최솟값/총합/평균/갯수)
- Stream 의 요소들을 대상으로 최댓값/최솟값/총합/평균/갯수를 구하기 위한 최종 연산
- min이나 max또는 average는 Stream이 비어있는 경우 값을 특정할 수 없음 -> 그렇기 때문에 Optional로 값이 반환됨
- sum 메소드나 count 메소드는 값이 비어있을 경우 0으로 값을 특정해 Optional이 아닌 원시 값을 반환함. Stream이 비어있을 경우 0 반환
```java
OptionalInt min = IntStream.of(1, 3, 5, 7, 9).min();
int max = IntStream.of().max().orElse(0);
IntStream.of(1, 3, 5, 7, 9).average().ifPresent(System.out::println);

long count = IntStream.of(1, 3, 5, 7, 9).count();
long sum = LongStream.of(1, 3, 5, 7, 9).sum();
```

<br>

#### 2. collect (데이터 수집)
- Stream의 요소들을 List나 Set, Map 등 다른 종류의 결과로 수집하고 싶은 경우 collect 함수를 이용 가능
- collect 함수는 어떻게 Stream의 요소들을 수집할 것인가를 정의한 Collector 타입을 인자로 받아서 처리
- List로 Stream의 요소들을 수집하는 경우가 많은데 이렇게 자주 사용되는 작업은 Collector 객체에서 static 메소드로 제공 -> 원하는 것이 없으면 Collector 인터페이스를 직접 구현하여 사용 가능
    - collect() : 스트림의 최종연산, 매개변수로 Collector를 필요로 한다.
    - Collector : 인터페이스, collect의 파라미터는 이 인터페이스를 구현해야한다.
    - Collectors : 클래스, static메소드로 미리 작성된 컬렉터를 제공한다.
```java
// collect의 파라미터로 Collector의 구현체가 와야 한다.
Object collect(Collector collector)

List<Product> productList = Arrays.asList(
	new Product(23, "potatoes"),
	new Product(14, "orange"),
	new Product(13, "lemon"),
	new Product(23, "bread"),
	new Product(13, "sugar"));
```

#### 2-1. Collectors.toList()
- Stream 에서 작업한 결과를 List로 반환받을 수 있음
```java
List<String> nameList = productList.stream()
    .map(Product::getName)
    .collect(Collectors.toList());
```
#### 2-2. Collectors.joining()
- Stream에서 작업한 결과를 1개의 String으로 이어붙이기를 원하는 경우에 이용
- Collectors.joining()은 총 3개의 인자를 받을 수 있는데 이를 이용해 간단하게 String을 조합 가능
    - delimiter : 각 요소 중간에 들어가 요소를 구분시켜주는 구분자
    - prefix : 결과 맨 앞에 붙는 문자
    - suffix : 결과 맨 뒤에 붙는 문자
```java
// potatoesorangelemonbreadsugar
String listToString = productList.stream()
	.map(Product::getName)
	.collect(Collectors.joining());

// potatoes orange lemon bread sugar
String listToString = productList.stream()
	.map(Product::getName)
	.collect(Collectors.joining(" "));

// <potatoes, orange, lemon, bread, sugar>
String listToString = productList.stream()
  	.map(Product::getName)
  	.collect(Collectors.joining(", ", "<", ">"));
```
#### 2-3. Collectors.averagingInt(), Collectors.summingInt(), Collectors.summarizingInt()
- Stream에서 작업한 결과의 평균이나 총합을 구하기 위해서 Collectors.averagingInt(), Collectors.summingInt() 이용 가능 -> 총합의 경우 구현하는 방법 다수 존재
- 하지만 만약 1개의 Stream으로 부터 갯수, 합계, 평균, 최댓값을 한번에 얻고 싶은경우 Collectors.summarizingInt()를 이용하면 되는데 이를 이용하면 IntSummaryStatistics 객체가 반환되고 필요한 값에 대해 get 메소드를 이용하여 원하는 값을 꺼내면 됨
    - getCount() : 개수
    - getSum() : 합계
    - getAverage() : 평균
    - getMin() : 최소
    - getMax() : 최대
```java
Double averageAmount = productList.stream()
	.collect(Collectors.averagingInt(Product::getAmount));

// 86
Integer summingAmount = productList.stream()
	.collect(Collectors.summingInt(Product::getAmount));

// 86
Integer summingAmount = productList.stream()
    .mapToInt(Product::getAmount)
    .sum();

//IntSummaryStatistics {count=5, sum=86, min=13, average=17.200000, max=23}
IntSummaryStatistics statistics = productList.stream()
    .collect(Collectors.summarizingInt(Product::getAmount));

//86
statistics.getSum();
// 17.200000
statistics.getAverage();
```
#### 2-4. Collectors.groupingBy()
- Stream에서 작업한 결과를 특정 그룹으로 묶기를 원할 경우 사용
- 결과는 Map으로 반환 받게 됨. groupingBy 매개변수로 함수형 인터페이스 Function을 필요로 함
```java
/*
{23=[Product{amount=23, name='potatoes'}, Product{amount=23, name='bread'}], 
 13=[Product{amount=13, name='lemon'}, Product{amount=13, name='sugar'}], 
 14=[Product{amount=14, name='orange'}]}
 */
Map<Integer, List<Product>> collectorMapOfLists = productList.stream()
  .collect(Collectors.groupingBy(Product::getAmount));
```
#### 2-5. Collectors.partitioningBy()
- 함수형 인터페이스 Function을 사용해서 특정 값을 기준으로 Stream 내의 요소들을 그룹핑했다면 해당 메소드는 함수형 인터페이스 Predicate를 받아 Boolean을 키값으로 partitioning 함
- 예시코드는 제품의 갯수가 15보다 큰 경우와 그렇지 않은 경우를 나눠놓음
```java
/*
{false=[Product{amount=14, name='orange'}, Product{amount=13, name='lemon'}, Product{amount=13, name='sugar'}], 
 true=[Product{amount=23, name='potatoes'}, Product{amount=23, name='bread'}]}
 */
Map<Boolean, List<Product>> mapPartitioned = productList.stream()
	.collect(Collectors.partitioningBy(p -> p.getAmount() > 15));
```

<br>

#### 3. Match (조건 검사)
- Stream의 모든 요소들이 특정한 조건을 충족하는지 검사하고 싶은 경우 사용
- match 함수는 Predicate를 받아서 해당 조건이 만족하는 지 검사하고 검사결과를 boolean으로 반환
- match 함수는 크게 3가지가 존재
    - anyMatch : 1개의 요소라도 해당 조건을 만족
    - allMatch : 모든 요소가 해당 조건을 만족
    - nonMatch : 모든 요소가 해당조건을 만족하지 않음
```java
List<String> names = Arrays.asList("Eric", "Elena", "Java");

// true
boolean anyMatch = names.stream()
    .anyMatch(name -> name.contains("a"));

// true
boolean allMatch = names.stream()
    .allMatch(name -> name.length() > 3);

// true
boolean noneMatch = names.stream()
    .noneMatch(name -> name.endsWith("s"));
```

<br>

#### 4. find (찾기)
- 스트림에서 찾은 요소를 리턴하는데 해당 조건에 맞는 요소를 리턴함
- findFirst()와 findAny() 존재

```java
List<String> elements =
        Arrays.asList("a", "a1", "b", "b1", "c", "c1");

// findFirst는 스트림 순서상 가장 첫번째 있는 것을 리턴
Optional<String> firstElement = elements.stream()
        .filter(s -> s.startsWith("b")).findFirst();

// findAny는 순서와 관계없이 먼저 찾아지는 객체를 리턴
Optional<String> anyElement = elements.stream()
        .filter(s -> s.startsWith("b")).findAny();
```

<br>

#### 5. forEach (특정 연산 수행)
- Stream의 요소들을 대상으로 어떤 특정한 연산을 수행하고 싶은 경우
- peek과 유사. peek은 중간 연산으로 실제 요소들에 영향을 주지 않은 채로 작업 진행하고 Stream을 반환하는 함수. 하지만 forEach는 최종 연산으로 실제 요소들에 영향을 줄 수 있으며 반환값이 존재하지 않음
- 예를 들어 요소들을 출력하기 원할 때 사용
```java
names.stream()
    .forEach(System.out::println);
```

<br>

#### 6. Reduce (작은 단위의 결과 생성)
- Stream의 데이터를 변환하지 않고 더하거나 빼는 등의 연산을 수행하여 하나의 값을 만듬
- 누산기(Accumulator)와 연산(Operation)으로 컬렉션에 있는 값을 처리하여 더 작은 컬렉션이나 단일 값을 만드는 작업
- reduce 함수는 여러 요소들을 통해 새로운 결과를 만들어 내는데 reduce 함수는 최대 3가지의 매개변수를 받을 수 있음
    - Accuumulator : 각 요소를 계산한 중간 결과를 생성하기 위해 사용
    - Identity : 계산을 처리하기 위한 초기값
    - Combiner : Pralallel Stream에서 나누어 계산된 결과를 하나로 합치기 위한 로직
```java
Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
Optional<Integer> sum = numbers.reduce((x, y) -> x + y);
// 1 + 2 + 3 .... + 10 -> sum: 55
sum.ifPresent(s -> System.out.println("sum: " + s));

// 초기값이 있는 reduce
Stream<Integer> numbers3 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
Integer sum3 = numbers3.reduce(10, (total, n) -> total + n);
// 10 + 1 + 2 ... + 10 -> sum3: 65
System.out.println("sum3: " + sum3);

// 병렬처리
Stream<Integer> numbers4 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
Integer sum4 = numbers4.parallel().reduce(0, (total, n) -> total + n);
// (1 + 2) + (3 + 4) ... + (9 + 10) -> sum4: 55
System.out.println("sum4: " + sum4);
```

<br>

### Null-Safe한 Stream 생성
- NPE, 널포인터에러를 방지 하기 위해 Java8부터 Optional 이라는 Wrapper 클래스를 제공하여 Null 관련 코드를 가독성 있게 처리할 수 있도록 도와줌
- Stream API 역시 Optional의 도움을 받아 Null-Safe한 Stream 을 생성할 수 있다고 함
- Optional은 코드 가독성을 높여주지만 Wrapper 클래스를 사용하는 것 뿐으로 Stream을 생성하는 대상이 Null이 발생할 확률이 높을경우 해당 코드를 적용하고 무의미하게 Optional을 남발하는 것은 바람직 하지 않음
```java
List<String> nullList = null;

// NPE 발생
nullList.stream()
  .filter(str -> str.contains("a"))
  .map(String::length)
  .forEach(System.out::println); // NPE!

// 빈 Stream으로 처리
collectionToStream(nullList)
  .filter(str -> str.contains("a"))
  .map(String::length)
  .forEach(System.out::println); // []
```

<br>

### 병렬 스트림 (Parallel Stream)?
- Stream은 아주 많은 양의 데이터를 처리해야 하는 경우 런타임 성능을 높이기 위해 병렬로 실행할 수 있는 기능인 병렬 스트림을 제공
- 내부적으로 fork & join을 사용하며 ForkJoinPool()을 통해 사용가능한 공통의 ForkJoinPool의 갯수를 확인할 수 있음
- 내재되어 있는 ThreadPool의 개수는 최대 5개이며, 사용가능한 물리적인 CPU 코어의 수에 따라 다르게 설정
- JVM 매개변수를 통해 별도로 설정 가능
```java 
// 사용가능한 ForkJoinPoll 확인
ForkJoinPool commonPool = ForkJoinPool.commonPool();
System.out.println(commonPool.getParallelism());

// JVM의 매개변수를 통해 별도로 설정
-Djava.util.concurrent.ForkJoinPool.common.parallelism=5


/*
filter: c [main]
filter: e [ForkJoinPool.commonPool-worker-3]
map: e [ForkJoinPool.commonPool-worker-3]
filter: a [ForkJoinPool.commonPool-worker-2]
map: a [ForkJoinPool.commonPool-worker-2]
filter: b [ForkJoinPool.commonPool-worker-1]
forEach: A [ForkJoinPool.commonPool-worker-2]
forEach: E [ForkJoinPool.commonPool-worker-3]
map: c [main]
filter: d [ForkJoinPool.commonPool-worker-2]
map: d [ForkJoinPool.commonPool-worker-2]
map: b [ForkJoinPool.commonPool-worker-1]
forEach: D [ForkJoinPool.commonPool-worker-2]
forEach: C [main]
forEach: B [ForkJoinPool.commonPool-worker-1]
*/
Arrays.asList("a", "b", "c", "d", "e")
        .parallelStream()
        .filter(s -> {
            System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
            return true;
        })
        .map(s -> {
            System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
            return s.toUpperCase();
        })
        .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));
```
- 솔직히 말하자면 해당 부분은 내가 지금 이해하기는 조금 어려운 부분인 것 같다고 판단 -> 다른 Stream에 대한 이해를 충분히 진행 후 다시 정리를 목표로 하겠음


<br>

### 마지막으로 Stream API 의 실행 순서
- Stream API를 제대로 알고 사용하지 않으면 처리 속도의 지연을 야기할 수 있음
- 실행 순서를 고려해야 하는 이유는 Stream API는 수직적인 구조로 진행되어 실행 순서를 고려하는 것이 상당히 중요 -> 잘못된 실행 속도는 연산의 횟수를 불필요하게 증가 시킴
- 그렇기 때문에 Stream API를 사용할 때는 반드시 연산 순서를 고려하여 코드를 작성

<br>

<div style="text-align: right">22-08-05</div>
<div style="text-align: right"> + 내용추가 : 22-08-06</div>

-------

## Reference
- https://hbase.tistory.com/171
- https://www.youtube.com/watch?v=wsvhgrCGW78
- https://velog.io/@blockjjam/Stream의-최종-연산으로-끝을-맺어보자
- https://velog.io/@kyy00n/JAVA-Stream
- https://mangkyu.tistory.com/112 , https://mangkyu.tistory.com/113 , https://mangkyu.tistory.com/114
- https://advenoh.tistory.com/15