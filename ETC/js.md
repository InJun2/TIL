# JavaScript

### Java Script
- 프로토타입 기반의 스크립트 프로그래밍 언어로 객체지향 개념을 지원
- 웹 브라우저가 JavaScript를 HTML과 함께 다운로드 하여 실행함
- 웹 브라우저가 HTML 문서를 읽어 들이는 시점에 JavaScript Engine이 실행됨
- 대부분의 JavaScript Engine은 ECMAScript 표준을 지원

<br>

### JavaScript 특징
- 개발자가 별도의 컴파일 작업을 수행하지 않는 인터프리터 언어
- 각 브라우저별 JavaScript 엔진은 인터프리터와 컴파일러의 장점을 결합하여 비교적 처리 속도가 느린 인터프리터의 단점을 해결
- 명령형, 함수형, 프로토타입 기반 객체지향 프로그래밍을 지원하는 멀티 패러다임 프로그래밍 언어
- HTML에서 JavaScript를 사용하려면 \<script> 태그를 사용
    - script 태그는 'src'와 'type' 속성을 사용하여 js를 선언 (HTML5 부터는 type 속성 생략 가능)
    - 'src' 속성은 외부의 JavaScript 파일을 HTML 문서에 포함될 때 사용하며 생략될 수 있음
    - 'type' 속성은 미디어 타입을 지정할 때 사용되며 JavaScript 코드는 'text/javascript'로 지정
- JavaScript script 태그는 \<head> 나 \<body>  안 어느 곳에서나 선언이 가능하지만 \<body> 안의 끝부분에 태그를 두는 것을 권장함
    - haed 안에 위치하면 브라우저의 각종 입/출력 발생 이전에 초기화 되므로 브라우저가 먼저 점검함
    - body 안에 위치하면 브라우저가 HTML부터 해석하여 화면에 그리기 때문에 사용자가 빠르다고 느낄 수 있음

<br>

### 변수 (variable)
- JS는 변수를 선언할 때 타입을 명시하지 않고 var keyword를 사용하여 선언
- JS는 동적 타입 언어로 변수의 타입 언어. 지정없이 값이 할당되는 과정에서 자동으로 변수의 타입이 결정 -> 같은 변수에 여러 타입의 값을 할당 가능
- 변수의 이름은 함수 이름과 혼동되지 않도록 유일한 이름을 사용하고 ECMA Script 표준에 따라 카멜 케이스를 사용
   - 키워드, 공백 문자 포함, 숫자로 시작 X
   - 특수문자는 _와 $ 허용 
- 백틱(` `)을 사용하여 JS 변수를 출력이 가능

```js
var str = "안효인";
console.log(`당신의 이름은 ${str}입니다.`); // ${variable}를 사용해서 JS 변수 표기 가능
```

<br>

### 자료형 (data type)
- 프로그램에서 다루는 데이터 값의 종류들을 자료 형(data)이라 표현
- 프로그램은 정적인 데이터 값을 동적으로 변환해 가면서 정보를 얻음
- JS는 자료형을 원시 타입(primitive)와 객체 타입(object) 으로 분류
- 원시 타입에는 숫자, 문자열, boolean, null, undefined 와 같이 5가지가 있으며 이를 제외한 모든 값은 객체 타입

|자료형|typeof 출력 값|설명|
|:---:|:---:|:------:|
|숫자형|number|정수 또는 실수형|
|문자열형|string|문자, single or double quotation으로 표기|
|boolean형|boolean|참 or 거짓|
|undefined|undefined|변수가 선언되었지만 초기화가 되지 않은 경우|
|null|obeject|값이 존재하지 않을 경우|

<br>

### JS 숫자 자료형
- JS는 숫자를 정수와 실수로 나누어 구분하지 않음
    - 모든 숫자를 8Byte의 실수 형태로 처리 (정수만을 표현하기 위한 데이터 타입은 없음)
    - 편의성을 위해 정수 리터럴과 실수 리터럴을 제공 (리터럴이란 순수한 값으로 프로그램 소스 코드에서 값을 표현하는 고정된 방법)
    - 숫자의 연산 처리 시 실수 형태로 처리하기 때문에 특정 소수점을 정확하게 표현하지 못함
- JS는 언더플로우, 오버플로우, 0으로 나누는 연산에 대해 예외를 발생시키지 않음
- JS는 숫자와 관련된 특별한 상수 존재
    - infinity : 무한대를 나타내는 상수. 어떠한 수를 0으로 나누거나 infinity를 어떠한 수로 사칙연산한 결과
    - NaN(Not a Number) : 계산식의 결과가 숫자가 아님을 나타내는 상수 (NaN 자체도 number type)

<br>

### JS 문자열 자료형
- JS에서 문자열은 16비트의 Unicode 문자를 사용
- 문자 하나를 표현하는 char 같은 문자열은 제공하지 않음
    - 'a'와 같은 한 글자도 문자열으로 표현
- 작은 따옴표', 또는 큰 따옴표" 둘 다 사용가능하며 혼용은 불가
- 이스케이프 시퀀스(\\)도 사용 가능

<br>

### JS boolean, null, undefined 자료형
- 비교 연산의 결과값으로 true 또는 false 중 하나의 값을 가짐
- 비어있는 문자열, null, undefined, 숫자 0은 false로 간주됨
- null은 값이 없거나 비어 있음을 뜻하고, undefined는 값이 초기화 되지 않았음
    - 의미가 비슷하지만 값이 할당되지 않은 변수는 undefined 할당(시스템 레벨), 코드에 명시적으로 값이 없음을 나타낼 때(프로그램 레벨)는 null을 사용

<br>

### JS 자동 형 변환 (Dynamic Typing)
- JS는 Java나 C++ 같은 언어와는 달리 자료 형에 대해 매우 느슨한 규칙이 적용
- 어떤 자료형이든 전달할 수 있고, 그 값을 필요에 따라 변환 가능
- 서로 다른 자료형의 연산이 가능
- 모든 자료형을 var로 선언하기 때문에 변수 선언은 쉽지만 이런 느슨한 규칙때문에 혼란을 야기

<br>

### 변수 호이스팅(Variable Hoisting)
- var 키워드를 사용한 변수는 중복해서 선언이 가능
- 호이스팅이란 var 변수나 function 선언문 등 모든 선언문이 해당 Scope의 처음으로 옮겨진 것 처럼 동작하는 특성
    - 즉 JS는 모든 선언문이 선언되기 이전에 참조가 가능
- 변수의 생성은 선언 단계, 초기화 단계가 한번에 이르어지고 이후 할당이 이루어짐
    - 선언 단계 : 변수 객체에 변수를 등록
    - 초기화 단계 : 변수 객체에 등록된 변수를 메모리에 할당. undefined로 초기화 됨
    - 할당 단계 : undefined로 초기화도니 변수에 실제 값을 할당


```js
// num 이 선언되지 않아 error가 발생할 것 같지만 undefined가 출력
// var num = 123;이 hoisting 되어 맨위로 옮겨진 것 처럼 동작(var num에서 var 키워드를 빼면 안됨)
console.log(num);   // undefined
var num = 123;      
{
    var num = 456;
}
// JS는 블록 레벨 스코프를 가지지 않고 함수 레벨 스코프만 갖음. num은 전역 변수이므로 전역에 선언
// num은 전역 변수이므로 전역에 선언한 변수 num에 두번째 num이 재 할당 되기 때문에 456을 가짐 --> 이를 해결하기 위한 const, let (ES6)
console.log(num);
```

<br>

### var, let, const
- var 키워드는 전역 스코프에서 사용이 가능하여 해당 키워드에 접근할 시 계속 재할당됨
    - var로 선언된 변수는 호이스팅되어 변수 선언이 스코프의 최상위로 끌어올려지면 변수의 값이 초기화되기 전에 사용될 수 있으며, 이는 예기치 않은 결과를 초래
    - var 키워드로 선언된 변수는 동일한 스코프 내에서 다시 선언되어 사용되는 스코프를 알기 어려움

|키워드|구분|선언위치|재선언|
|:---:|:---:|:---:|:---:|
|var|변수|전역 스코프|가능|
|let|변수|해당 스코프|불가능|
|const|상수|해당 스코프|불가능|

<br>

```js
function varExample() {
    var x = 1;
    if (true) {
        var x = 2; // 같은 변수 x를 재선언
        console.log(x); // 출력: 2
    }
    console.log(x); // 출력: 2 (var는 함수 스코프를 가짐)
}

function letExample() {
    let y = 1;
    if (true) {
        let y = 2; // 블록 스코프 내에서 새로운 변수 y
        console.log(y); // 출력: 2
    }
    console.log(y); // 출력: 1 (let은 블록 스코프를 가짐)
}

function constExample() {
    const z = 1;
    if (true) {
        const z = 2; // 블록 스코프 내에서 새로운 상수 z
        console.log(z); // 출력: 2
    }
    console.log(z); // 출력: 1 (const는 블록 스코프를 가짐)
}

// var: 재선언과 재할당 모두 가능
var a = 1;
var a = 2; // 재선언
a = 3; // 재할당
console.log(a); // 출력: 3

// let: 재선언 불가능, 재할당 가능
let b = 1;
// let b = 2; // SyntaxError: Identifier 'b' has already been declared
b = 2; // 재할당
console.log(b); // 출력: 2

// const: 재선언과 재할당 모두 불가능
const c = 1;
// const c = 2; // SyntaxError: Identifier 'c' has already been declared
// c = 2; // TypeError: Assignment to constant variable.
console.log(c); // 출력: 1
```

<br>

### JS 연산자(operator)
- 기본적으로 약속된 문자의 표현식을 연산자라고 함
- 연산자는 산술 연산자, 비교 연산자, 논리 연산자, 기타 연산자 등을 제공
- 자바와 다른 연산자는 다음이 있음
    - delete : 프로퍼티를 제거
    - typeof : 피연산자 타입을 리턴
    - in : 프로퍼티가 존재하는지 확인
    - === : 값이 일치하는지 확인 (타입 포함)
    - !== : 값이 일치하지 않는지 확인 (타입 포함)
    - == : 값이 일치하는지 확인 (타입 미포함)

```js
var obj ={}
obj.name = "홍길동" // JS는 없는 변수도 동적으로 'name' 프로퍼티 추가 및 값 설정됨
console.log(obj.name); // 출력: "홍길동"
delete obj.name;    // 프로퍼티 제거
console.log(obj.name); // 출력: undefined
```

<br>

### '||' 연산
- '||' 연산은 두개의 피연산자를 평가하여 둘 중 하나라도 참이면 참을 반환
- JS에서 '||' 연산은 단순히 불리언 값으로만 평가하지 않고 피연산자 중 하나를 그대로 반환함

1. 왼쪽 피연산자를 평가
2. 왼쪽 피연산자가 참(Truthy) 값이면 그 값을 반환하고 연산을 종료
3. 왼쪽 피연산자가 거짓(Falsy) 값이면 오른쪽 피연산자를 평가하고 그 값을 반환

```
Falsy 값
- 'false'
- '0'
- 'null'
- 'undefined'
- 'NaN'
```

<br>

```js
console.log(0 || 1); // 출력: 1 (0은 Falsy, 1은 Truthy)
console.log(1 || 0); // 출력: 1 (1은 Truthy)
console.log(null || "Hello"); // 출력: "Hello" (null은 Falsy)
console.log(undefined || "World"); // 출력: "World" (undefined는 Falsy)
console.log("" || "Non-empty string"); // 출력: "Non-empty string" (빈 문자열은 Falsy)
console.log("Non-empty string" || "Another string"); // 출력: "Non-empty string" (첫 번째 피연산자가 Truthy)
```

<br>

### JS if, for, while

```js
// if
if(조건식1) {
}
else if(조건식2) {
    ...
}

// switch
// 주의 해야 하는 부분은 78 / 10 의 경우 7.8이 나오므로 주의 필요
switch(값) {
    case 값1 :
        // 실행구문
        break;
    default :
        // 실행구문
}

// while
while(조건식) {
    // 실행구문
}

do {
    // 실행구문
} while(조건식);

// for
for(초기화; 조건식; 증강표현식) {
    // 실행 구문
}

// 객체의 속성을 검사할 때 사용
for(let 변수 in 배열 or 객체) {
    // 실행 구문
}

let arr = [2, 3, 4];
arr.sum = 9;
for(let key in arr) {
    console.log(key, arr[key]);
}
// 0 2, 1 3, 2 4, sum 9 출력됨 (증감 반복문과 용도가 다름) 
```

<br>

### JS 객체
- 객체는 이름과 값으로 구성된 프로퍼티 집합
- 문자열, 숫자, boolean, null, undefined를 제외한 모든 값은 객체
- 키(Key)와 값(Value)으로 구성된 프로퍼티(Property) 들의 집합
- 전역 객체를 제외한 JS 객체는 프로퍼티를 동적으로 추가하거나 삭제 가능
- JS의 함수도 일급 객체(first-class)이므로 값으로 사용이 가능
    - 함수를 변수, 객체, 배열 등에 저장할 수 있고 다른 함수에 전달하는 전달 인자(콜백함수) 또는 리턴값으로 사용 가능
    - 함수는 프로그램 실행 중에 동적으로 생성 가능
    - 함수 정의 방법은 함수 선언문, 함수 표현식(리터럴), Function 생성자 함수 세가지 방식 제공
- JS 객체는 프로토타입(prototype)이라는 특별한 프로퍼티를 포함
- JS 객체 생성 방법은 3가지가 존재
    - 객체 리터럴 : 가장 일반적인 방법으로 { }를 사용하여 객체를 생성. 해당 중괄호 안에 1개 이상의 프로퍼티를 추가하여 객체를 생성
    - Object 생성자 함수 : new 연산자와 Object 생성자 함수를 호출하여 빈 객체를 생성. 이후 프로퍼티 또는 메서드를 추가하여 객체를 완성
    - 생성자 함수 : 동일한 프로퍼티를 갖는 객체 생성 시 위 두 방법은 코드를 반복적으로 작성. 생성자 함수를 이용하면 템플릿(클래스)처럼 사용하여 프로퍼티가 동일한 객체 여러 개를 간단히 생성 가능
- 생성자 함수
    - 함수 이름의 첫 문자를 대문자
    - 반드시 'new' 연산자를 붙여 실행
- 생성자 함수 호출 시 동작 순서
    - 빈 객체를 만들어 this에 할당
    - 함수 본문 실행
    - this에 새로운 프로퍼티를 추가해 this를 수정
    - this를 반환(default)

```js
// 객체 리터럴
var obj = {}; // empty obejct
console.log(typeof obj);

function Student(name, area, classNum) {
    this.name = name;
    this.area = area;
    this.classNum = classNum;
}

// Object 생성자 함수
var obj = new Object();
student.name = "김싸피";
student.area = "서울";
student['classNum'] = 10;   // Object 객체 프로퍼티는 '.' 혹은'[]'로 접근 가능, 객체에 없는 속성에 접근하면 undefined 반환
```

<br>

```js
// 함수 선언문
fucntion 함수이름(매개변수1, 매개변수2, 매개변수3) {
    // 함수 내용
}

// 함수 표현식
var 함수이름 = function(매개변수1, 매개변수2, 매개변수3) {
    // 함수내용
}

// Funciton 생성자 함수
var 함수이름 = new Function("매개변수1", "매개변수2", "매개변수3");

// 함수 호출
함수이름(매개변수1, 매개변수2, 매개변수3);
```

<br>

### JS 객체 참조
- 객체는 복사되지 않고 참조됨
- JS에서 원시 데이터 타입이 아닌 모든 값은 참조 타입
- 참조 타입은 Object, Array, Date, Error를 포함
    - 타입 확인 방법으로는 typeof 연산자가 존재
