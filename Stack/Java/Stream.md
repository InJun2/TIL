# Stream

### Stream
- 자바의 스트림은 Java8 부터 지원되기 시작한 기능
- 컬렉션에 저장되어 있는 엘리먼트들을 하나씩 순회하면서 처리할 수 있는 코드 패턴
- 람다식과 함께 사용되어 컬렉션에 들어있는 데이터에 대한 처리를 매우 간결한 표현으로 작성 가능
- 내부 반복자를 사용하기 때문에 병렬처리가 쉬움
- 코드를 단순하고 가독성있게 작성 가능

<br>

### 스트림 사용법
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
Stream<Stirng> stream = list.stream();

// 빌더
String<String> stream = Stream<String>builder()
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

### 스트림 데이터 가공
- 스트림 객체가 뽑아내는 데이터들에 대해 작업을 하기위한 방법
- 특정 데이터들만 걸러내거나 데이터에 대해 가공이 가능
- 데이터를 가공해주는 메소드들은 가공된 결과를 생성해주는 스트림 객체를 리턴
- 스트림 데이터 가공방식은 Filter, Map, flatMap, Sorted, Peek 등등이 존재

<br>

#### 1. Filter
- 스트림에서 뽑아져 나오는 데이터에서 특정 데이터들만 골라내는 역할
- filter 메소드에는 boolean 값을 리턴하는 람다식을 넘겨줌. 해당 데이터에 대해 람다식을 적용해서 true가 리턴되는 데이터만 선별
- 사용 예시
```java
Stream<Integer> stream = IntStream.range(1,10).boxed();
stream.filter(v -> ((v % 2) == 0))
            .forEach(System.out::println);  // 2,4,6,8
```

#### 2. Map
- 스트림에서 뽑아져 나오는 데이터에 변경을 가해줌
- map 메소드는 값을 변환해주는 람다식을 인자로 받음
- 스트림에서 생성된 데이터에 map 메소드의 인자로 받은 람다식을 적용해 새로운 데이터를 만들어 냄
```java
Stream<Integer> stream = IntStream.range(1,10).boxed();
stream.filter(v -> ((v % 2) == 0))
            .map(v -> v * 10)
            .forEach(System.out::println); // 20, 40, 60, 80
```

#### 3. FlatMap
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

#### 4. Sorted
- 스트림 데이터들을 정렬하고자 할 때, sorted 메소드를 이용
- 인자없이 sorted 메소드를 호출하면 오름차순으로 정렬. 만약 정렬시 두 값을 비교하는 별도의 로직이 있다면 comparator를 sorted 메소드의 인자로 넘겨줄 수 있음
```java
Stream<T> sorted();
Stream<T> sorted(Comparator<? super T> comparator);
```

#### 5. Peek
- 스트림 내 엘리먼트들을 대상으로 map() 메소드처럼 연산을 수행
- 하지만 새로운 스트림을 생성하지는 않고 인자로 받은 람다를 적용하기만 함
- 생성된 데이터들에 변형을 가하지 않고 인자로 받은 람다식만 한번 실행해보기만 함
```java
Stream<T> peek(Consumer<? super T> action);

int sum = IntStream.range(1,10)
            .peek(System.out::println)
            .sum();
```

<br>

<div style="text-align: right">22-08-05</div>

-------

## Reference
- https://hbase.tistory.com/171