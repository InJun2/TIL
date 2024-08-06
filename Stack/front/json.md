# JSON

### JSON (JavaScript Object Notation)
- JSON은 속성-값 쌍, 배열 자료형 또는 기타 모든 시리얼화 가능한 값(serializable value) 또는 "키-값 쌍"으로 이루어진 데이터 오브젝트를 전달하기 위해 인간이 읽을 수 있는 텍스트를 사용하는 개방형 표준 포맷
    - JSON은 사람이 읽을 수 있는 텍스트 기반의 데이터 교환 표준
    - MIME 타입은 'application/json'
- 비동기 브라우저/서버 통신 (AJAX)을 위해 넓게는 XML(AJAX가 사용)을 대체하는 주요 데이터 포맷
    - XML 사용시 파싱과 같은 복잡한 문제 해결
    - 하지만 JSON은 전달받은 데이터의 무결성을 직접 검증해야 함(XML은 스키마를 이용)
- 주로 인터넷에서 자료를 주고 받을 때 그 자료를 표현하는 방법으로 알려져 있는데 자료의 종류에 큰 제한은 없으며 특히 컴퓨터 프로그램의 변수값을 표현하는데 적합
    - 텍스트 기반이므로 어떠한 프로그래밍언어와 플랫폼에서도 사용가능

<br>

### JSON vs XML
- JSON은 종료 태그가 없음
- XML에 비해 상대적으로 구문이 짧음
- JSON 데이터가 XML 데이터보다 빨리 읽고 쓸 수 있음
    - XML은 문서의 DOM을 이용하여 접근
    - JSON은 문자열로 전송 받은 후 바로 파싱하여 속도가 빠름
- XML은 배열 사용이 불가능
- XML은 XML 파서가 필요하지만 JSON은 자바스크립트 함수로 변환하여 사용 가능

<br>

### JSON 구조
- JSON data는 name - value 형태의 쌍으로 collection 타입
- data는 쉼표(,)로 나열됨
- 객체는 중괄호 ({})로 표현
- 배열은 대괄화 ([])로 표현

#### JSON 자료형
- 수 (Number)
    - 정수, 실수 (고정, 부동 소수점)
- 문자열 (String)
    - 유니코드 문자들
    - 큰 따옴(")로 구분
    - 역슬래시(\) 이스케이프 문법을 지원
- 참 / 거짓 (Boolean)
    - true or false
- 배열 (Array)
- 객체 (Object)
- null

```js
let user = 
{
    userid : "user",
    username : "홍길동",
    age : 30,
    otherobject : {"id": "id", "name" : "이승길"},
    student : ["김김김", "박박박", "홍홍홍"] 
}
```

<br>

### JS에서 JSON 사용
- JSON.stringify(JSON 객체)
    - 직렬화 (serialize)
    - argument로 전달받은 자바스크립트의 객체를 문자열로 변환
- JSON.parse(JSON 형식의 문자열)
    - 역직렬화 (deserialize)
    - JSON.stringify(JSON 객체)와 반대로 arugment로 전달받은 문자열을 자바스크립트의 객체로 변환
    - argument로 받은 text는 반드시 JSON 형식에 일치해야 함
- toJSON()
    - 자바스크립트의 Date 객체의 데이터를 JSON 형식의 문자열로 변환
    - Date.prototype 객체에서만 사용 가능

```js
// 자바스크립트 객체
const person = {
    name: "John Doe",
    age: 30,
    address: {
        street: "123 Main St",
        city: "Anytown",
        state: "CA"
    }
};

// 1. JSON.stringify - 객체를 JSON 문자열로 직렬화
const jsonString = JSON.stringify(person);
console.log("JSON.stringify(person):", jsonString);

// 2. JSON.parse - JSON 문자열을 객체로 역직렬화
const parsedObject = JSON.parse(jsonString);
console.log("JSON.parse(jsonString):", parsedObject);

// 3. toJSON - Date 객체를 JSON 형식의 문자열로 변환
const currentDate = new Date();
const jsonDate = currentDate.toJSON();
console.log("currentDate.toJSON():", jsonDate);

// JSON.stringify를 이용한 Date 객체 직렬화
const dateString = JSON.stringify({ currentDate });
console.log("JSON.stringify({ currentDate }):", dateString);

// JSON.parse를 이용한 Date 객체 역직렬화
const parsedDateObject = JSON.parse(dateString);
console.log("JSON.parse(dateString):", parsedDateObject);
```

<br>