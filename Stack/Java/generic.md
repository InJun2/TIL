# Generic

### Generic 이란?
- 사전상으로 '포괄적'의 의미를 가지고, 다양한 타입의 객체를 다루는 메서드, 컬렉션 클래스에서 컴파일 시에 타입 체크
    - 미리 사용할 타입을 명시해서 형 변환을 하지 않아도 되게 함
    - 객체의 타입에 대한 안전성 향상 및 형 변환의 번거로움 감소

<br>

### 클래스에 선언된 Generic
- 클래스 또는 인터페이스 선언 시 <>에 타입 파라미터 표시
- 컴파일러가 코드가 작성된 시점에 어떤 타입이 들어갈 지 파악
    - 오류 검출을 빨리 할수록 좋음

```java
public class GenericBox<T> {
	private T some;
}

public UseBoxTest {
     private static void useGenericBox() {
    	GenericBox<String> box = new GenericBox();  // String 으로 제네릭 타입 명시
    	box.setSome("Hello");	// 이미 타입에 대한 체크 완료
    	
    	String some = box.getSome();	// 원하는 타입으로 바로 사용가능
    }
}
```

<br>

### 형인자 (Type Parameter)
- 단순히 임의의 참조형 타입을 말하며 성격에 따라 선언
    - T : reference
    - E : Element
    - K : Key
    - V : Value

```java
public interface InterfaceName<T> {}

public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable {...}
public class HashMap<K,V> extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable {...}

```

<br>

### Generic 객체 생성
- '어떤 타입'을 속성으로 사용을 의미, 또한 타입 선언 시 결정됨
- 변수 쪽과 생성 쪽의 타입은 반드시 같아야 함
    - 상속 관계 등도 포함되지 않음, 반드시 같아야함
- 제네릭을 사용할 때 <> 없이 사용하는 raw 타입은 사용하지 않는 것이 일반적

```java
public UseBoxTest {
     private static void useGenericBox() {
    	GenericBox box = new GenericBox();  // raw 타입 객체 생성은 지양
    }
}

public UseBoxTest {
    sprivate static void useGenericBox() {
    	GenericBox<String> box = new GenericBox();  // String 으로 제네릭 타입 명시
    }
}
```

<br>

### 헷갈리는 사용법
```java
public void confusing() {
    GenericBox<Animal> pbox = new GenericBox<>();
    pbox.setSome(new Animal());
    pbox.setSome(new Dog());    // Dog는 Animal을 상속받으므로 문제 없음

    GenericBox<Dog> dbox = new GenericBox<>();
    pbox = dbox;    // GenericBox 자체에는 상속관계가 없음. 사용하지 못함
}

// 예시 코드
// 만약 아래 코드가 Collection<? extends E>가 아니라 <E> 라면 상속 관계 없이 들어간 값만 사용할 수 있게 됨
public interface<E> extends Collection<E> {
    boolean addAll(int index, Collection<? extends E> c);
}
```

<br>

### 주의사항
- 타입 파라미터는 객체를 생성하면서 전달됨
    - 타입의 결정 시점은 객체 생성 시점이므로 static 멤버에서는 사용 불가
- Generic은 컴파일 타임에 지정한 타입으로 존재. 컴파일러가 이미 타입을 체크했기 때문에 런타임에는 자유롭게 사용
    - 런타임에는 타입 정보 삭제(단순 Object로 관리)
    - 즉, 런타임에는 Generic 타입이 존재하지 않고 컴파일 타임에 지정된 타입으로만 존재
    - 그렇기 때문에 런타임에 타입 안정성을 확보하기 위해 사용하는 타입을 지정해야하고 raw 타입을 사용하면 안됨
- 런타임에 동작하는 new, instanceof 키워드 사용 불가
- Generic을 이용한 배열 생성 불가
    - 배열은 runtime에 객체의 정보를 유지하고 동일한 타입의 객체만 처리함
    - 만약 T[]이 된다고 가정했을 때는 runtime에 Object[]로 변경됨


```java
public void cantUseGeneric() {
    //I i = new I();//Cannot instantiate the type I

    GenericBox<SpiderMan> obj = new GenericBox<>();

    // compile error : Type Object cannot be safely cast to GenericBox<String>
    //if(obj instanceof GenericBox<String>) {  }

    if (obj instanceof GenericBox gb) { // raw 타입 사용
        gb.setSome("Hello"); // 타입에 안전하지 않음
        System.out.println("맞지만 타입에 안전하지 않음: " + gb.getSome());
    }

    if (obj instanceof GenericBox<?> gb) {  // 와일드카드를 썼을 때는 무언가를 세팅할 수 없음
        // gb.setSome("Hello"); // compile error
        System.out.println("이것이 최선: 뭐든 담기는 GenericBox");
    }
}

// Generic 타입으로는 배열 생성 불가 
public void genericArray() {
    // GenericBox<String> [] boxes2 = new GenericBox<>[3]; // compile error

    GenericBox<Person>[] boxes3 = (GenericBox<Person>[]) new GenericBox[3];
    boxes3[0] = new GenericBox<Person>();
    boxes3[1] = (GenericBox) new GenericBox<String>("Hello");
    // 위의 경우 값이 담기는 문제가 발생함 -> 배열에서는 제네릭을 사용하면 안됨
}
```

<br>

### Generic Method
- 파라미터와 리턴타입으로 type parameter를 갖는 메서드
    - 메서드 리턴 타입 앞에 타입 파라미터 변수 선언
- 메서드 호출 시점에 타입이 결정됨

```java
public class TypeParameterMethodTest<T> {
    T some; // 객제 생성 시점에 T 결정
    public TypeParameterMethodTest(T some) {
        this.some = some;
    }

    public <P> void method1(P p) {  // 메서드 호출 시 타입 결정
        System.out.printf("클래스 레벨의 T: %s%n", some.getClass().getSimpleName());
        System.out.printf("파라미터 레벨의 P: %s%n", p.getClass().getSimpleName());
    }

    public <P> P method2(P p) {
        System.out.printf("클래스 레벨의 T: %s%n", some.getClass().getSimpleName());
        System.out.printf("파라미터 레벨의 P: %s%n", p.getClass().getSimpleName());
        return p;
    }

    public static void main(String[] args) {
        // 객체 생성 시점 - 클래스에 선언된 타입 파라미터 T의 타입 결정
        TypeParameterMethodTest<String> tpmt = new TypeParameterMethodTest<>("Hello");
        // 메서드 호출 시점 - 메서드에 선언된 타입 파라미터 P의 타입 결정
        tpmt.<Long>method1(20L); // 명시적으로 타입 결정
        tpmt.method2(10);        // 묵시적으로 타입 결정 
    }
}
```

<br>

### Bounded Type Parameter (한정형 형인자)
- 필요에 따라 구체적인 타입 제한 필요
- 계산기 프로그램 시 Number 이하의 타입(Byte, Short, Integer...) 로만 제한
    - type parameter 선언 뒤 extends 와 함께 상위 타입 명시

```java
class NumberBox<T extends Number> {
    public void addAll(T... ts) {	// T... 은 내부적으로 배열이므로 해당 코드 작성 X
		double sum = 0;
		for(T t : ts) {
			sum += t.doubleValue();
		}
	}
}

    private static void useNumberBox() {
    	NumberBox<Integer> box = new NumberBox<>();
        // 아래 코드의 경우 값이 들어가기 때문에 문제가 발생할 수 있음
//    	box.addAll((Integer[]) new Object[] {11, 2, "Hello"});
    }
```

<br>

### Wildcard Type
- 실제 type 파라미터가 무엇인지 모르거나 신경쓰지 않을 경우
    - 와일드 카드도 제네릭의 일종으로 유연성을 위한 키워드. 와일드 카드도 마찬가지로 런타임시 타입이 고정됨
- 비 한정형 와일드 카드 자료형 (unbounded wildcard type)
    - Generic type<?> : 타입에 제한이 없음
- 한정형 와일드 카드 자료형 (bounded wildcard type)
    - Generic type<? extends T> : T 또는 T를 상속받은 타입들만 사용 가능
    - Generic type<? super T> : T 또는 T의 조상 타입만 사용 가능

```java
// boxPerson만 사용 가능
void notUseWildCardType(GenericBox<Person> boxPerson) {}
```

<br>

### PECS
- 와일드 카드를 사용한 방법을 PECS 로 부름
    - 와일드 카드 자료형을 파라미터로 하면 위와 같이 자료형을 가져올 수 있으나 블록 안에서 값을 지정하는 것은 다른 문제임
- PE (Producer Extends) : 제네릭 타입이 데이터를 생산하여 외부로 제공하는 역할
    - Generic이 제공하니 코드에서 Person으로 사용 가능
- CS (Consumer Super) : 제네릭 타입이 데이터를 쓰는 역할(추가, 수정)
    - Generic이 소비하니 코드에서 Person 자식은 저장 가능

```java
// 들어오는 값은 Person을 상속받은 Person 하위의 클래스가 들어올 수 있음 -> 들어왔을 경우 런타임 시 해당 타입으로 지정이됨 -> 그런데 Peson 하위인 SpiderMan이 들어왔다면 Person을 넣을 수 없음 -> 마찬가지로 그 하위에 클래스가 계속 생길 수 있으므로 어떤 값도 넣을 수 없음
// 들어올 수 있는 최상위의 객체는 Person 이므로 getSome()시 Person 타입으로 반환됨
public void useWildCardType2(GenericBox<? extends Person> boxExtendsPerson) {   // GenericBox<?>도 마찬가지로 어떤 값도 박싱해서 들어오지만 값을 넣을 수는 없음
    // null을 제외한 어떤 값도 넣을 수 없음
   	// boxExtendsPerson.setSome(new Person("사람"));
   	// boxExtendsPerson.setSome(new SpiderMan("피터파커"));
    
    Person person = boxExtendsPerson.getSome();
}

// 들어오는 값은 Person 상위의 클래스만 들어올 수 있음 -> 들어왔을 경우 가장 좁은 객체는 Person 까지의 객체가 들어 올 수 있음 -> 런 타임 시 최대 Object일 수 이며 최소 Person 이므로 Person과 Person을 상속받은 객체를 값에 넣을 수 있음
// Person 최대 상위 객체는 Object 이므로 getSome() 메서드 호출 시 Object 타입으로 반환됨
public void useWildCardType3(GenericBox<? super Person> boxSuperPerson) {
    // 파라미터로 Person의 보다 상위 값만 들어오고 넣을 수 있는 값은 이를 상속 받는 객체만 넣을 수 있음
    boxSuperPerson.setSome(new Person("사람"));
    boxSuperPerson.setSome(new SpiderMan());
    boxSuperPerson.getSome();
}

// 사용 예제
public class Collections {
    // src는 공급자, dest는 소비자로 동작
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {...}
}
```

### 와일드 카드 사용 예시 코드2
```java
// Number의 하위 타입을 가질 수 있는 두 개의 GenericBox를 파라미터로 받고 세번째 파라미터는 원하는 결과의 타입으로 I or D이다.
// 파라미터에 따라 intValue, doubleValue의 합을 반환하는데 이외의 타입이 오면 '타입오류'라고 반환한다.
public GenericBox<? super Number> addAll(GenericBox<? extends Number> gb1,
        GenericBox<? extends Number> gb2, char type) {
    Number num1 = gb1.getSome();
    Number num2 = gb2.getSome();

    return switch (type) {
    case 'I' -> new GenericBox<Number>(num1.intValue() + num2.intValue());
    case 'D' -> new GenericBox<Number>(num1.doubleValue() + num2.doubleValue());
    default -> new GenericBox<Object>("타입 오류");
    };
}
```

#### 추가 질문 : 위의 코드에서 <? super Number>라면 최상위 객체는 Object이고 반환 시 해당 객체를 받는 인스턴스는 Number 일지 Object인지 모르는데 해당 제네릭을 사용하는 이유는?

-  제네릭 타입을 사용할 때는 타입의 안전성과 유연성을 모두 고려해야하므로
1. 타입 안전성 (Type Safety)
    - 타입 안전성은 코드가 컴파일 타임에 타입 오류를 방지하도록 하는 것을 의미
    - <? super Number>는 Number의 상위 타입을 나타내므로 Number를 상속 받는 모든 타입을 안전하게 추가할 수 있음
    - Object를 사용하면 Number 타입만이 아닌 모든 타입의 객체를 추가할 수 있어 타입 안전성이 떨어짐
    - 원하지 않는 타입의 객체가 추가되는 것을 막기 위한 장치
2. 유연성 (Flexibility)
    - 유연성은 코드가 다양한 타입을 처리할 수 있는 능력을 의미. 특정한 타입에 고정되지 않고 여러 타입을 다룰 수 있게 하여 재사용성과 범용성을 높임
    - 제네릭 타입을 사용하면 특정 타입의 객체들만 다루는 메서드로 제한할 수 있음
    - addAll() 메서드가 Number 또는 그 하위 타입만을 다루도록 제한하고 싶을 때 사용
    - Object를 사용하면 타입의 유연성이 없어지고 모든 타입의 객체를 허용하게 되어 예상치 못한 결과를 초래할 수 있음
3. 의도 명확화 (Intent Clarification)
    - Number 타입 또는 그 상위 타입을 다룬다는 것을 명시적으로 표현할 수 있음
    - 이는 코드의 의도를 명확히 하여 코드의 가독성을 높이고 유지보수를 쉽게함

<br>