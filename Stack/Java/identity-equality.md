# 자바 동일성과 동등성

### 동일성 (Identity)
- 두 객체의 메모리 주소가 같음을 의미
    - 즉 같은 객체를 가리키고 있음
- 자바에서 '==' 연산자로 경우 동일성을 판별

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
// 대부분의 자료구조(String, arraylist, queue, stack ..)에서는 equals는 내부적으로 containsAll로 확인하므로 동등성이 true가 된다



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
public boolean equals(Object obj) {
    if (obj instanceof Integer) {
        return value == ((Integer)obj).intValue();
    }
    return false;
}
```