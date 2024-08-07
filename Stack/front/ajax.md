# AJAX

### Ajax (Asynchronous Javascript And XML)
- Ajax 는 언어나 프레임워크가 아닌 구현하는 방식을 의미
- Ajax는 웹에서 화면을 갱신하지 않고 데이터를 서버로부터 가져와 처리하는 방법을 의미
    - 서버로부터 받은 데이터를 CSR 방식을 통해 페이지에 동적으로 적용 
- JavaScript의 XMLHttpRequest 객체로 데이터를 전달하고 비동기 방식으로 결과를 조회
- 화면 갱신이 없으므로 사용자 입장에서는 편리하지만, 동적으로 DOM을 구성해야 하므로 구현이 복잡

<br>

### Ajax 응답
#### 일반 요청에 대한 응답
- data 입력 후 event 발생
- Ajax를 적용하지 않은 요청은 서버에서 data를 이용하여 logic 처리
- 로직 처리에 대한 결과에 따라 응답 page를 생성하고 client에 전송
    - 화면 전환 발생

#### Ajax 요청에 대한 응답
- data 입력 후 event 발생
- Ajax를 적용하면 event 발생시 서버에서 요청을 처리한 후 Text, XML 또는 JSON으로 응답
- 클라이언트에서는 이 응답 data를 이용하여 화면 전환 없이 현재 페이지에서 동적으로 화면을 재구성

<br>

### SSR, CSR
- 웹 화면을 구성하는 방식은 서버 중심의 상호작용 방식과 클라이언트 중심의 상호작용 방식으로 구분

#### SSR (Server Side Rendering)
- 서버 중심의 개발 방식은 화면 구성이 서버에서 이루어짐
    - 프레젠테이션 영역의 JSP나 PHP, ASP 등

#### CSR (Client Side Rendering)
- 클라잉언트 중심의 개발방식은 클라이언트에서 화면을 구성
    - 주로 JavaScript
- Ajax는 클라이언트 중심의 개발 방식이며 비동기 요청보다는 동적 화면구성이 관건임

<br>

### Ajax 사용방식
- XMLHttpRequest 이용 방식 (Browser)
- fetch() 이용 방식 (Browser)
- 외부라이브러리 이용 방식 - jQuery, axios

<br>

### JavaScript AJAX
- XMLHttpRequest 는 자바스크립트가 Ajax 방식으로 통신할 때 사용하는 객체
- XMLHttpRequest 객체는 Ajax 통신 시 전송방식, 경로, 서버로 전송할 data 등 전송 정보를 담는 역할
- 실제 서버와의 통신은 브라우저의 Ajax 엔진에서 수행
- 직접 자바스크립트로 Ajax를 프로그래밍할 경우 브라우저 별로 통신 방식이 달라 코드가 복잡해짐

<br>

### Ajax httpRequest 속성값
#### 1. readyState
| 값 | 상태          | 설명                                      |
|----|---------------|-------------------------------------------|
| 0  | Uninitialized | 객체만 생성 (open 메소드 호출 전)         |
| 1  | Loading       | open 메소드 호출                          |
| 2  | Loaded        | send 메소드 호출, status의 헤더가 아직 도착되지 않음 |
| 3  | Interactive   | 데이터 일부를 받은 상태                   |
| 4  | Completed     | 데이터 전부를 받은 상태                   |

<br>

#### 2. status
| 코드 | 상태                  | 설명          |
|------|-----------------------|---------------|
| 200  | OK                    | 요청 성공     |
| 403  | Forbidden             | 접근 거부     |
| 404  | Not Found             | 페이지 없음   |
| 500  | Internal Server Error | 서버 오류 발생|

<br>

### fetch()
- 브라우저에서 fetch() 함수를 지원하기 이전에는 XMLHttpRequest를 이용하여 직접 HTTP 요청하고 응답을 직접 구현
- 복잡한 구현때문에 jQuery와 axios 등과 같은 라이브러리 사용
- 브라우저가 fetch() 함수를 지원하면서 위와 같은 라이브러리를 쓰지 않아도 간단히 구현 가능

#### 사용법
- fetch() 함수는 첫번째 인자로 URL, 두번째 인자로 options 객체를 받음
- options에 아무것도 넘기지 않으면 요청은 GET 방식으로 진행되며 url로 부터 content가 다운로드 됨
- 실행 결과 Promise 타입의 객체를 반환
- 반환된 Promise 객체는 API 호출이 성공했을 경우 응답 객체를 resolve하고 실패했을 경우 예외 객체를 reject함

<br>

#### 응답 본문(data) 받는 방법
- response.text() : 응답을 읽고 text를 반환
- response.json() : 응답을 JSON 형식으로 파싱함
- response.formData() : 응답을 FormData 객체 형태로 반환
- response.blob() : 응답을 Blob 형태로 반환

<br>

### fetch() 사용 예
- method option : POST 설정
- headers option : Content-Type에 JSON 여부 설정
- body option : 요청 data 값을 JSON 형식으로 직렬화 하여 설정
- PUT 방식의 경우 method option 만 PUT으로 수정하고 나머지는 거의 비슷함
    - PATCH 메소드는 리소스의 부분적인 업데이트를 수행. 변경하고자 하는 데이터만 서버로 보냄
    - PUT 메소드는 지정된 리소스를 대체하거나, 리소스가 존재하지 않으면 새로 만듬

<br>

### Data 전송 형식
- Server와 Client는 주고 받을 data의 형식을 맞춰야함
- 대표적인 data의 형식은 CSV, XML, JSON 등이 있음

#### 1. SCV
- 각 항목을 쉼표(,)로 구분해 데이터를 표현하는 방법
- 다른 두 형식에 비해 굉장히 짧음. 많은 양의 데이터 전송 시 유리
- 단, 각각의 데이터가 어떤 내용인지 파악하기 어려움 (서버와 클라이언트가 완벽히 데이터 구조를 공유할 경우 가능)

```
20201111, 홍길동, A, 90,
20201111, 홍길동2, B, 85,
```

<br>

#### 2. XML
- XML은 태그로 data를 표현함
- tag를 보면 각 data가 무엇을 의미하는지 파악 가능
- tag에 사용자 정의 속성을 넣을 수 있으므로 복잡한 data 전달 가능

```xml
<students>
    <student>
        <id>2022111</id>
        <name>홍길동</name>
        <class>A</class>
        <grade>90</grade>
    </student>
    <student>
        <id>2022111</id>
        <name>홍길동2</name>
        <class>B</class>
        <grade>85</grade>
    </student>
</students>
```

<br>

#### 3. JSON
- CSV와 XML의 단점을 극복한 형식
- JS 에서 사용하는 객체의 형식으로 data를 표현
- Ajax 사용시 거의 표준으로 사용되는 data 표현 방식
- JSON의 특징
    - 데이터를 주고 받을 때 쓸 수 있는 가장 간단한 파일 포맷
    - 텍스트 기반으로 상당히 가벼움
    - 읽기 편함
    - Key와 Value의 쌍으로 되어 있음
    - 서버와 데이터를 주고 받을 때 직렬화, 역직렬화를 사용
    - 프로그램 언어나 플랫폼에 상관없이 사용 가능

<br>

### 동기/비동기 작업
#### 1. 동기(Synchronous)
- 동기 작업은 코드를 한줄 한줄 실행하면서 이전 코드의 동작 결과를 받은 후 다음 코드가 실행되는 구조

#### 2. 비동기(Asynchronous)
- 비동기 작업은 코드를 실행하다가 이전 코드의 동작 결과를 받지 않고 바로 다음 코드를 실행하는 구조

```js
// 간단한 setTimeout 비동기 작업 
let num = 0;

setTimeout(() => {
  console.log(`작업 진행: ${num++} 번째 작업 진행 ..`);
}, 1000);

console.log(`작업 결과 num: ${num}`);
//  setTimeout은 1초 후에 처리될 작업(callback)을 비 동기로 만들어서 던져버리고 바로 "작업 결과 .."가 콘솔에 출력
```

<br>

### Callback Hell
- 문제는 1초 후에 또 1초 후에.. 이런식으로 계속 callback 내에서 다른 callback 내에서 다른 callback 이 불러야 하는 경우 발생
- 동작은 잘 되지만 추적이 매우 힘들어지는 상태를 callback hell 이라고 함
- 해당 Callback Hell을 해결하기 위해 Promise 객체가 등장

<br>

### Promise
- Promise 란 비동기 작업의 완료 또는 실패를 나타내는 객체
    - 비동기 작업을 관리하고 그 결과를 처리하는데 사용됨. 주로 비동기 코드를 작성할 때 콜백 함수 대신 사용되어 가독성과 유지보수성을 향상시킴
- Promise는 총 3개의 상태를 가짐
    - Pending(대기) : 실제 작업을 위한 준비 단계로 Promise 객체 생성 및 fulfilled, rejected 상황에서 호출할 handler 함수 바인딩함
    - Fulfilled(이행) : 동작이 성공적으로 완료된 상태
    - Rejected(거부) : 동작이 실패한 상태

<br>

### Promise 객체의 생성과 호출
- Promise 함수는 생성자에 executor라는 함수를 파라미터로 받는데 executor는 즉시 실행되는 함수
    - new Promise(executor)
- executor는 resolver와 rejecter 라는 2개의 콜백 함수를 인자로 받는데 Promise는 executor 내부에서 상황에 따라 작업 성공 시 resolver를, 실패 시 rejecter를 호출하는 것을 약속함 (확실히 호출)
    - Promise가 정상동작해서 호출되는 resolver는 handler에서 사용할 값 하나를 인자로 받음
    - Promise가 실패해서 동작하는 rejecter는 실패 이유 하나를 인자로 받음
