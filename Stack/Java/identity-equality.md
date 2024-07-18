# 자바 동일성과 동등성

### 동일성 (Identity)
- 두 객체의 메모리 주소가 같음을 의미
    - 즉 같은 객체를 가리키고 있음
- 자바에서 참조형의 '==' 연산자는 같은 객체를 가리키고 있는지를 판별하므로 동일성을 판별함
    - 기본 자료형의 경우 메모리에 바로 할당되므로 '=='는 값을 비교함

```java
String str1 = "hello";
String str2 = str1;

System.out.println(str1 == str2);  // true, str1과 str2는 같은 객체를 가리킴

String str1 = new String("hello");
String str2 = new String("hello");

System.out.println(str1 == str2);  // false, str1과 str2는 각각 다른 객체를 가리킴

// 기본 자료형의 '=='은 동일성이 아닌 메모리의 값을 비교하여 true 반환 
int num1 = 5;
int num2 = 5;

System.out.println(num1 == num2);  // true
```

<br>

### 동등성 (Equality)
- 두 객체의 값이 같음을 의미
    - 참조하고 있는 객체가 다르지만 각각의 객체의 값이 동등함
- 자바에서 대부분의 자료구조의 경우 'equals' 연산자를 통해 동등성을 판별
    - 가장 기초가 되는 Object.equals()의 경우에는 return (this == obj)로 되어 있음
        - 즉 equals 연산자는 재정의하지 않으면 내부적으로 '==' 연산자와 동일하나 대부분은 재정의를 통해 동등성의 기능을 수행함
    - 대부분의 자료구조 내부적으로 equals는 containsAll로 확인하므로 동등성 판별
    - String, Integer의 equals도 동등성 판별

<br>

```java
// 동일성
Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
Set<Integer> set2 = new HashSet<>(Arrays.asList(3, 2, 1));

System.out.println(set1 == set2); // false

// 동등성
Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
Set<Integer> set2 = new HashSet<>(Arrays.asList(3, 2, 1));

System.out.println(set1.equals(set2)); // true
// 대부분의 자료구조(String, arraylist, queue, stack ..)에서는 equals는 내부적으로 containsAll로 확인하므로 동등성 판별

// 대부분의 Set의 구현체 부모클래스
public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Set))
            return false;
        Collection<?> c = (Collection<?>) o;
        if (c.size() != size())
            return false;
        try {
            return containsAll(c);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }
}

// String equals
public boolean equals(Object anObject) {
    if (this == anObject) {
        return true;
    }
    if (anObject instanceof String) {
        String anotherString = (String)anObject;
        int n = value.length;
        if (n == anotherString.value.length) {
            char v1[] = value;
            char v2[] = anotherString.value;
            int i = 0;
            while (n-- != 0) {
                if (v1[i] != v2[i])
                    return false;
                i++;
            }
            return true;
        }
    }
    return false;
}

// Integer
// Integer 은 내부적으로 '=='의 경우에 내부적으로 같은 객체임을 비교하지 않고 내부적으로 같은 값임을 비교함
public boolean equals(Object obj) {
    if (obj instanceof Integer) {
        return value == ((Integer)obj).intValue();
    }
    return false;
}
```