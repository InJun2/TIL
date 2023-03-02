# String

## 문자열 유형 함수

<br>

### 1. equals
- 두 String이 같은지 판단하는 함수
```java
String str = "String";

// true return
str.equals("String")

// false return
str.equals("STR")
```

<br>

### 2. length
- String 길이를 반환하는 함수
```java
String str = "String";

// 6 return
str.length()
```

<br>

### 3. toUpperCase
- 대문자로 변환하는 함수
```java
String str = "String";

// "STRING" return
str.toUpperCase
```

<br>

### 4. toLowerCase
- 소문자로 변환하는 함수
```java
String str = "String";

// "string" return
str.toLowerCase
```

<br>

### 5. indexOf
- 문자열 내에 다른 문자열이 존재하는지 판단할 때 자주사용하는 함수
- 문자열 중 특정 문자열이 존재한다면 해당 문자열이 존재하는 첫번째 인덱스의 값을 리턴하고 없을 경우 -1을 리턴
```java
String str = "String";

// 1 return
str.indexOf("t");

// -1 return
str.indexOf("s");
```

<br>

### 6. substring
- 문자열의 일부를 추출하기 위해 사용하는 함수
- 특정 인덱스의 번호부터 특정 인덱스의 전번 인덱스 까지 추출
- 인덱스 하나만을 입력값으로 두었을 경우 해당 인덱스 번호부터 마지막 인덱스까지 추출

```java
String str = "String";

// "tr" return
str.substring(1,3)

// "ring" return
str.substring(2)
```

<br>

### 7. replace
- 문자열의 일부를 대체하는 함수
```java
String str = "String";

// "S ring" return
str.replace("t", " ")
```

<br>

### 8. trim
- 앞뒤 공백을 제거하는 함수
```java
String str = "St ri ng";

// "String" return
str.trim()
```

<br>

### 9. compareTo
- 두 문자열의 ASCII 순서 비교하는 함수
- 앞의 문자열이 뒤의 문자열보다 아스키 코드가 낮을 경우 "-" return
- 앞의 문자열의 뒤의 문자열보다 아스키 코드와 같을 경우 0 return
- 앞의 문자열이 뒤의 문자열보다 아스키 코드가 높을 경우 "+" return
- 대문자와 소문자의 아스키코드는 다르므로 비교를 할 경우에는 toUpperCase나 toLowerCase를 이용하여 통일 시켜두고 사용
```java
String str1 = "a";
String str2 = "b";

// "-" return
str1.compareTo(str2)

// 0 return
str1.compareTo("a")

// "+" return
str2.compareTo(str1)
```

<br>

### 10. split
- 입력받은 정규표현식이나 특정 문자를 기준으로 문자열을 나누어 배열에 저장하여 리턴하는 함수
- 인자값을 두개로 하면 두번째 인자값은 배열의 크기를 결정함 
```java
String str = "010-1234-5678";

// splitStr[0] = "010"
// splitStr[1] = "1234"
// splitStr[2] = "5678"
String[] splitStr = str.split("-");

// splitStr2[0] = "010"
// splitStr2[1] = "1234-5678"
String[] splitStr2 = str.split("-", 2);
```

<br>

### 11. contains
- 대상 문자열에 특정 문자열이 포함되어 있는지 확인하는 함수
- 리턴값은 boolean
- 마찬가지로 대/소문자를 구분
```java
String str = "Stirng";

// return true
str.contains("ti");

// return false
str.contains("Ti");
```

<br>

### 12. charAt
- 특정 인덱스 번호의 문자를 리턴하는 함수
- charAt은 substring으로도 구현이 가능
```java
String str = "String";

// "r" return
str.charAt(2)
```

<br>

### 13. startsWith
- 비교 대상 문자열이 입력된 문자열로 시작되는지(prefix) 확인하는 함수
```java
String str = "Stirng Test";

// true return
str.startsWith("String");

// false return
str.startsWith(" Str");
```

<br>

### 14. endsWith
- 비교 대상 문자열이 입력된 문자열로 끝나는지(suffix) 확인하는 함수
```java
String str = "Stirng Test";

// true return
str.endsWith("Test");

// false return
str.endsWith("est ");
```

<br>