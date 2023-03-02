# Hash

## Hash
- Key:Value 를 가지는 자료구조
- 전화번호부와 같이 특정 키에 특정 값이 존재
    - Key : 무엇가를 찾기위한 검색어
    - Value : 해당 검색어로 나온 결과
- Hash는 모든 데이터 타입으로 접근이 가능함

<br>

### HashMap 선언
- capacity는 용량으로 해시테이블의 버킷수이고 load factors는 해시 테이블의 버킷이 얼마나 가득 찼는지 보여주는 수치
    - 이값을 설정하지 않을 경우 initial capcity는 16, load factor는 0.75로 설정
    - HashMap은 load factor가 임계치에 다다르면 해시테이블을 rehash 하게되고 burcket 수를 2배 증가시킴
```java
//HashMap생성
HashMap<String,String> map1 = new HashMap<String,String>();

//new에서 타입 파라미터 생략가능
HashMap<String,String> map2 = new HashMap<>();

//map1의 모든 값을 가진 HashMap생성
HashMap<String,String> map3 = new HashMap<>(map1);

//초기 용량(capacity)지정
HashMap<String,String> map4 = new HashMap<>(10);

//초기 capacity,load factor지정
HashMap<String,String> map5 = new HashMap<>(10, 0.7f);

//초기값 지정
HashMap<String,String> map6 = new HashMap<String,String>(){{
    put("a","b");
}};
```

<br>

### HashMap.get
- 해당 키를 가지고 값을 가져옴
- 값은 키를 대상으로 접근할 경우 value가 변경됨
- 해당 키가 없을 경우 error 발생
```java
HashMap<String, String> map = new HashMap<>();

// 값 추가
map.put("key1", "value1");

// "value1" return
map.get("key1");

// "value2" return
map.put("key1", "value2");
map.get("key1");
```

<br>

### HashMap.getOrDefault
- HashMap.get과 유사하게 해당 키를 가지고 값을 가져오는 것을 동일하나 해당 키가 없을 경우 값을 지정
```java
HashMap<String, String> map = new HashMap<>();

// 값 추가
map.put("key1", "value1");

// "value1" return
map.getOrDefault("key1", "false");

// "false" return
map.getOrDefault("noKey", "false");
```

<br>

### HashMap 모든 요소 조회
- HashMap의 모든 요소 조회
- entrySet() : key 값과 value 값을 가져올 때 사용
- keySet() : key 값을 가져올 때 사용
- key값을 이용해서 value를 찾는 과정에서 시간이 많이 소모되므로 많은 양의 데이터를 가져와야 한다면 entrySet()이 좋음
```java
HashMap<String, String> map = new HashMap<>();

// 값 추가
map.put("key1", "value1");
map.put("key2", "value2");
map.put("key3", "value3");

//entrySet() 활용
for (Entry<Integer, String> entry : map.entrySet()) {
    System.out.println("[Key]:" + entry.getKey() + " [Value]:" + entry.getValue());
}

//KeySet() 활용
for(Integer i : map.keySet()){ //저장된 key값 확인
    System.out.println("[Key]:" + i + " [Value]:" + map.get(i));
}

//iterator() 사용. iterator는 자료구조를 순회할 수 있게 해주는 객체
Iterator<Entry<Integer, String>> entries = map.entrySet().iterator();
while(entries.hasNext()){
    Map.Entry<Integer, String> entry = entries.next();
    System.out.println("[Key]:" + entry.getKey() + " [Value]:" +  entry.getValue());
}

Iterator<Integer> keys = map.keySet().iterator();
while(keys.hasNext()){
    int key = keys.next();
    System.out.println("[Key]:" + key + " [Value]:" +  map.get(key));
}
```

<br>


### HashMap.remove
- 해당 키를 가진 요소 제거
```java
HashMap<String, String> map = new HashMap<>();

// 값 추가
map.put("key1", "value1");

// "key1" 요소 제거
map.remove("key1");
```

<br>

### HashMap.clear
- 모든 요소 제거
``` java
<String, String> map = new HashMap<>();

// 값 추가
map.put("key1", "value1");
map.put("key2", "value2");

// 모든 요소 제거
map.clear();
```