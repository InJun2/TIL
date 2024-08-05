# Ssafy HTML 정리

### HTML
- Hypertext Markup Language의 약자
- 1990년 이후 웹(Web, World Wide Web)에서 사용하는 문서 양석
- 문서에 하이퍼텍스트, 표, 목록, 비디오 등을 포함할 수 있는 tag를 사용하여 문서를 Web Browser에 표현할 때 tag를 사용
- <!DOCTYPE html> 태그를 통해 현재 문서가 HTML 문서임을 정의
- 시작 tag와 종료 tag가 있으며 tag 사이에 문서 내용을 정의
    - HTML 문서의 전체 구성은 html, head, body tag로 구성

<br>

### 웹표준
- 모든 브라우저에서 웹 서비스가 정상적으로 보여질 수 있도록 하는 것
- W3C는 월드 와이드 웹 창시자인 팀 버너스 리를 중심으로 창립된 월드와이드웹 컨소시엄
    - W3C에서 HTML5를 웹 표준으로 권고하고 웹 브라우저는 이를 따름
- 웹 문서의 3가지 요소는 HTML, CSS, JS
    - HTML : 웹 페이지 문서 담당(구조)
    - CSS : 웹 페이지 다자인 담당(표현)
    - JS : 웹 페이지 이벤트 담당(동작)

<br>

### HTML5의 특징
- 멀티미디어 요소 재생
    - 과거 브라우저는 멀티미디어를 재생하기 위해 별도의 외부 플러그인을 사용해야 헀으나 별도의 플러그인 없이 재생 가능
- 서버와 통신
    - 서버와 클라이언트 사이에 소켓 통신이 가능
- Semantic tag 추가
    - 웹사이트를 검색엔진이 좀 더 빠르게 검색할 수 있도록 하기 위해 특정 tag에 의미를 부여하는 방식
    - 예를 들면 <header> tag는 문서의 주제를 나타냄. 해당 태그가 사용되면 웹 문서의 검색엔진의 경우 웹 문서의 모든 내용을 검색하는 것이 아닌 <header> tag 내용만을 검색하여 더 빠르게 검색 진행

<br>

### Web & HTML 작동 원리
- 서버는 클라이언트 요청 내용을 분석하여 결과값을 HTML로 전송
- 서버는 결과값을 전송한 후 클라이언트와 연결 종료
- 클라이언트는 서버로부터 전달받은 HTML을 Web Browser에 표시
- 각 Web Browser는 브라우저 엔진이 내장되어 있고, 이 엔진이 tag를 해석하여 화면에 표현

<br>

### global attribute tag
- HTML tag에는 어느 tag에서나 넣어서 사용할 수 있는 글로벌 속성이 있음
- class : tag에 적용할 스타일의 이름을 지정
- id : tag에 유일한 ID를 지정함. 자바스크립트에서 주로 사용
- dir : 내용의 텍스트 방향을 지정
- style : 인라인 스타일을 적용하기 위해 사용
- title : tag에 추가 정보를 지정. tag에 마우스 포인터를 위치시킬 경우 title의 값 표시

```
id : 문서 내에서 유일한 tag
class : 여러 tag에 공통적인 특성 부여
```

<br>

### HTML Head 요소
- title
    - 문서의 제목을 의미. 브라우저의 제목 표시줄에 tag 내용이 나타남
- meta
    - 문서의 작성자, 날짜 키워드 등 브라우저 본문에 나타나지 않는 일반 정보를 나타냄 (데이터의 정보를 설명하는 메타데이터)
    - name, content 속성을 이용하여 다양한 정보를 나타냄
    - http-equiv 속성을 이용하여 문서 이동 및 새로 고침이 가능
    - charset 속성을 이용하여 문서의 인코딩 정보를 설정

<br>

### HTML 특수문자
- \&nbsp; : 공백
- \&lt; : '<' 출력
- \&gt; : '>' 출력
- \&amp; : '&' 출력
- \&quot; : '"' 출력
- \&copy; : '&copy;' 출력
- \&reg; : '&reg;' 출력

<br>

### HTML5 기본 태그

### 1. 포맷팅 요소
- \<addr> : 생략된 약어 표시. title 속성을 함께 사용
- \<address> : 명락처 정보 표시
- \<blockquote> : 긴 인용문구 표시. 좌우로 들여쓰기 됨
- \<q> : 짧은 이용문구 표시, 좌우로 따옴표가 붙음
- \<cite> : 웹 문서나 포스트에서 참고 내용 표시
- \<pre> : 공백, 줄 바꿈 등 입력된 그대로 화면에 표시
- \<code> : 컴퓨터 인식을 위한 소스 코드
- \<mark> : 특정 문자열을 강조, 화면에는 하이라이팅됨
- \<hr> : 구분선
- \<b>, \<string> : 굵은 표시로 표시. 특정 문자열을 강조
- \<i>, \<em> : 이텔릭 표시, 특정 문자열을 강종
- \<big>, \<small> : 큰 글자, 작은 글자로 표시
- \<sup>, \<sub> : 위 첨자, 아래 첨자로 표시
- \<s>, \<u> : 취소선, 밑줄

<br>

### 2. 목록형 요소
- 목록 태그는 하나 이상의 하위 tag를 포함
- 목록 tag는 각 항목을 들여쓰기로표현
- \<ul> : 번호 없는 목록을 표시 (unordered list)
- \<ol> : 순서에 따른 목록을 표시 (ordered list)
    - ol type 속성 값으로는 1 (default), a, A, i, l 이 있음
    - start 속성으로 시작 번호를 지정하고 reversed 속성으로 역순 표시가 가능
- \<li> : 목록의 항목 태그 (list item)
- \<dl> : 용어 정의와 설명에 대한 내용을 목록화 해서 표시 (description list)
- \<dt> : 용어 목록의 정의 부분을 나타냄 (description term)
- \<dd> : 용어 목록의 설명 부분을 나타냄 (description details/data)

```html
    <ul>
        <li>Item 1</li>
        <li>Item 2</li>
        <li>Item 3</li>
    </ul>

    <ol type="A" start="3">
        <li>First item</li>
        <li>Second item</li>
        <li>Third item</li>
    </ol>

    <ol reversed>
        <li>First item</li>
        <li>Second item</li>
        <li>Third item</li>
    </ol>

    <dl>
        <dt>HTML</dt>
        <dd>HyperText Markup Language</dd>
    </dl>
```

<br>

### table 모델
- HTML table 모델은 데이터를 행(Row)과 열(Column)의 셀(Cell)에 표시
- 행 그룹 요소인 \<thead>, \<tbody>, \<tfoot> 요소를 사용하여 행동을 그룹화
    - table 행 그룹 요소는 table의 행들을 머리글, 바닥글, 하나 이상의 본체 항목으로 그룹핑
- 각 행 그룹은 최소 하나 이상의 \<tr>을 가져야 함
- \<colgroup>과 \<col> 요소는 열 그룹을 위한 추가적인 구조 정보를 제공
    - colgroup 요소 : 명시적인 열 그룹을 만들며 col 요소는 열을 묶어 그룹핑 함
    - col 요소 : 열의 스타일을 정의하며 'span' 속성으로 여러 열을 그룹화 할 수 있음
- table의 셀은 머리글(\<th>이나 데이터\<td>를 가질 수 있음)
- HTML table \<td> 요소에는 셀을 병합하기 위한 두개의 속성이 존재
    - colspan : 두 개 이상의 열을 하나로 합치기 위해 사용
    - rowspan : 두 개 이상의 행을 하나로 합치기 위해 사용

```html
<table>
    <caption>
        Caption
    </caption>
    <colgroup>
        <col style="background-color: #f2f2f2">
        <col span="2" style="background-color: #e6f7ff">
    </colgroup>
    <thead>
        <tr>
            <th colspan="2">Header 1</th>
            <th>Header 2</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Data 1</td>
            <td rowspan="2">Data 2</td>
            <td>Data 3</td>
        </tr>
    </tbody>
    <tfoot>
        <tr>
            <td>Footer 1</td>
            <td>Footer 3</td>
        </tr>
    </tfoot>
</table>
```

<br>

### Image 요소
- img 태그를 사용하여 이미지를 삽입하기 위해 사용
- src 속성은 이미지 경로를 지정하기 위해 사용 (상대경로, 절대경로, URL 등으로 지정)
- alt 속성은 이미지를 표시할 수 없을 때 화면에 대신하여 보여질 텍스트를 지정
- figure 태그는 설명 글을 붙여야할 대상을 지정
    - figcatpion 태그는 대상에 대한 설명 글을 묶음

```html
<figure>
    <img src="../Java/img/JVM.png" title="JVM" width="300" height="200" alt="JVM 이미지 입니다.">
    <figcaption>JVM은 너무 어려워요</figcaption>
</figure>
```

<br>

### Anchor(링크) 요소
- \<a> 태그를 사용하여 하나의 문서에서 다른 문서로 연결하기 위해 (하이퍼링크) 사용
- tag를 서로 중첩해서 사용할 수 없음
- href 속성은 하이퍼링크를 클릭했을 때 이동할 문서의 URL이나 문서의 책갈피를 지정
- target 속성은 하이퍼링크를 클릭했을 때 현재 윈도우 또는 새로운 윈도우에서 이동할지를 지정
    - _blank : 링크의 내용이 새 창이나 새탭에서 열림
    - _self : target 속성의 기본 값으로 링크가 있는 화면에서 열림
    - _parent : 프레임을 사용헀을 때 링크 내용을 부모 프레임에 표시
    - _top : 프레임을 사용했을 때 프레임에서 벗어나 링크 내용을 전체 화면으로 표시
- map 링크 요소는 하나의 영역에서 여러 개의 링크 영역으로 표시
    - 이미지에 영역을 표시할 때는 \<area> 태그를 사용하여 href(링크 경로)
, target(링크표시대상), shape(링크로 사용할 영역 형태) 등을 지정
```html
<tag id="anchor_name"> test text </tag>
<a href="#anchor_name"> link test </a>
```

<br>

### link(링크) 요소
- link 태그를 사용하여 문서와 외부 자원을 연결하기 위해 사용
- \<head> 위치에 정의하여 여러 자원을 연결할 수 있음
- rel 속성은 현재 문서와 연결된 문서 사이의 연관관계를 지정하기 위해 사용
- href 속성은 연결된 문서의 위치를 지정하기 위해 사용

```html
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/main.css">
</head>
```

<br>

### iframe(프레임) 요소
- 화면의 일부분에 다른 문서를 포함
- src 속성은 포함시킬 외부 문서의 경로 지정
- name 속성은 프레임의 이름을 지정

<br>

### form control 요소
- 사용자로부터 데이터를 입력받아 서버에서 처리하기 위한 용도로 사용
- 사용자의 요청에 따라 서버는 HTML form 을 전달
- 사용자는 HTML form에 적절하 데이터를 입력한 후 서버로 전송 (submit)
- 서버는 사용자의 요청을 분석한 후 데이터를 등록하거나 원하는 데이터를 조죄하여 결과를 다시 반환
- 사용되는 태그는 다음과 같음
    - \<form> : 사용자에게 입력받을 항목을 정의. form 태그 내부에 여러 개의 control 요소를 포함
    - \<input> : 텍스트 box, 체크 box, 라디오 버튼 등 사용자가 데이터를 입력할 수 있도록 함
    - \<textarea> : 여러 줄의 문자를 입력할 수 있도록 함
    - \<button> : 버튼 표시
    - \<select> : select box(drop down, combo box)를 표시
    - \<optgroup> : select box의 각 항목들을 그룹화 함
    - \<option> : select box의 각 항목들을 정의
    - \<label> : 마우스를 이용하여 input 항목을 연결
    - \<field> : 입력 항목들을 그룹화
    - \<legent> : fieldset의 제목을 지정
- form의 속성은 다음과 같음
    - method : 사용자가 입력한 내용을 서버쪽 프로그램으로 어떻게 넘겨줄지 지정
    - name : form의 이름을 지정. 한 문서 안에 여러개의 form 태그가 있을 경우 구분자로 사용
    - action : form 태그 안의 내용들을 처리해 줄 서버상의 프로그램 지정 (URL)
    - target : action 태그에서 지정한 스크립트 파일을 현재 창이 아닌 다른 위치에 열도록 지정
    - autocomplete : 자동 완성 기능. 기본값 on

<br>

### form method
- 사용자가 입력한 내용을 서버 쪽 프로그램으로 어떻게 넘겨줄지 지정
- 속성 값은 GET, POST 존재
- GET : 주소 표시줄에 사용자가 입력한 내용 표시
- POST : HTTP 메세지의 Body에 담아서 전송. 사용자가 입력한 내용이 표시되지 않음

<br>

### form id & name
- 각 'id'는 특정 HTML 요소를 고유하게 식별하기 위해 사용
    - 주로 CSS나 스크립틍서 특정 요소를 참조할 때 주로 사용
- 'name'은 폼 요소 내에서 데이터를 서버로 전송할 때, 또는 폼 데이터를 식별하기 위해 사용됨
    - 같은 이름을 가진 폼 요소가 있을 수 있음
    - 서버로 전송된 폼 데이터의 키로 사용됨

```html
<!-- 
{   해당 데이터에서의 key가 form에서의 name
  "username": "myname", 
  "password": "securepassword123"
}
 -->

<form>
    <input type="text" name="username" placeholder="Enter your username">
    <input type="password" name="password" placeholder="Enter your password">
    <button type="submit">Submit</button>
</form>
```

<br>

### HTML input form type 속성
- text : 한 줄의 텍스트를 입력할 수 있는 텍스트 상자
- password : 비밀번호를 입력할 수 있는 필드
- search : 검색 상자
- tel : 전화번호를 입력할 수 있는 필드
- url : URL 주소를 입력할 수 있는 필드
- email : 메일 주소를 입력할 수 있는 필드
- datetime : 국제 표준시로 설정된 날짜와 시간
- datetime_local : 사용자 지역을 기준으로 날짜와 시간
- date : 사용자 지역을 기준으로 날짜
- month : 사용자 지역을 기준으로 날짜
- week : 사용자 지역을 기준으로 날짜
- time : 사용자 지역을 기준으로 시간
- number : 숫자를 조절할 수 있는 화살표
- color : 숫자를 조절할 수 있는 슬라이드 막대
- checkbox : 주어진 항목에서 2개 이상 선택가능한 체크 박스 (다중 선택)
- radio : 주어진 항목에서 1개만 선택할 수 있는 라디오 버튼
- file : 파일을 첨부할 수 있는 버튼
- submit : 서버 전송 버튼
- image : submit + img
- reset : 리셋 버튼
- button : 기능이 없는 버튼
- hidden : 사용자에게는 보이지 않지만 서버로 넘겨지는 값을 설정

<br>

### input 속성
- placeholder : 텍스트를 입력할 때 도움이 되도록 입력란에 적당한 힌트 내용을 표시
- readOnly : 입력란에 텍스트를 사용자가 직접 입력하지 못하게 읽기 전용으로 지정
- autofocus : 페이지 로딩 후 폼의 요소 중에서 해당 요소에 마우스 커서를 표시
- required : submit 클릭 시 data를 서버로 전송하기 전 필수 입력 항목을 체크
- list : \<datalist>에 미리 정의해 놓은 옵션 값을 \<input> 안에 나열해서 보여줌
- multiple : 두 개 이상의 값을 입력

<br>

### 사용자 입력을 위한 textfield input 속성
- name 속성 : 필드를 구별할 수 있는 이름
- size 속성 : textfield 길이를 지정. 화면에 몇 글자가 보이도록 할 것인지를 지정 (글자수 제한 X)
- value 속성 : textfield가 화면에 보일 때 textfield 부분에 표시될 내용
- maxlength 속성 : textfield에 입력할 수 있는 최대 문자수를 지정
    - size는 화면에 몇 글자가 보이는지 조절, maxlength는 텍스트 필드에 입력할 수 있는 최대 문자수를 지정

<br>

### 사용자 입력을 위한 number, range 속성
- min 속성 : 필드에 입력할 수 있는 최소값을 지정
    - range 기본 최소값은 0
- max : 필드에 입력할 수 있는 최대값을 지정
    - range 기본 최대값은 100
- step : 짝수나 홀수 등 특정 숫자로 제한하려 할 때 숫자 간격을 지정. 기본값은 1
- value : 페이지 로딩 시 필드에 표시할 초기값

<br>

### 사용자 입력을 위한 checkbox, radio 속성
- checked 속성은 여러 개의 항목 중 선택된 항목을 표시
    - checked="checked"
- radio 버튼은 name 속성 값이 모두 일치해야함
- check box는 name 속성과 상관없이 다중 선택이 가능

<br>

### input button
- type="reset"은 input 요소에 입력한 모든 정보를 초기화
- type="submit"은 사용자가 입력한 form의 정보를 서버로 전송
- type="button"은 자체 기능은 없고 스크립트 함수와 연결해 사용

```
input button type vs button tag
- input button 은 기본 동작이 정의 되어 있지 않음
- button tag는 기본은 type="submit"으로 동작
- 해당 태그 모두 type 속성을 통해 'reset', 'submit', 'button' 으로 지정 가능
- input button과 button의 차이로 button 태그는 contents를 포함할 수 있어 아이콘을 추가할 수 있고 CSS를 이용하여 원하는 형태로 꾸밀 수 있음
```

<br>

```html
<form>
  <input type="text" name="username" placeholder="Enter your username">
  <input type="submit" value="Submit">
  <input type="reset" value="Reset">
  <input type="button" value="Click Me" onclick="alert('Button clicked!')">
</form>

<form>
  <input type="text" name="username" placeholder="Enter your username">
  <button type="submit">Submit</button>
  <button type="reset">Reset</button>
  <button type="button" onclick="alert('Button clicked!')">
    <img src="icon.png" alt="Icon"> Click Me
  </button>
</form>
```

<br>

### select 데이터 나열
- select 태그는 select box(dropdown)를 표시
    - 사용자가 미리 정의된 옵션 중 하나를 선택할 수 있는 드롭 다운 메뉴를 만듬
- option 태그는 select box에 포함될 항목들을 정의
- selected 속성은 여러 개의 항목 중 선택된 항목을 표시
- value 속성은 각 항목 값을 지정하기 위해 사용

```html
<form>
    <label for="fruits">Choose a fruit:</label>
    <select id="fruits" name="fruits">
        <optgroup label="Citrus Fruits">
            <option value="orange">Orange</option>
            <option value="lemon">Lemon</option>
            <option value="lime">Lime</option>
        </optgroup>
        <optgroup label="Other Fruits">
            <option value="apple">Apple</option>
            <option value="banana" selected>Banana</option>
            <option value="cherry">Cherry</option>
            <option value="date">Date</option>
            <option value="elderberry">Elderberry</option>
        </optgroup>
    </select>
</form>
```

<br>

### datalist 데이터 나열
- datalist 태그는 \<input> 과 함께 사용하며 텍스트 필드에 직접 값을 입력하는 것이 아니라 datalist의 선택 값이 텍스트 필드에 입력됨
    - 사용자가 텍스트 입력 필드에서 선택 가능한 옵션 목록을 제공
- 미리 정의된 옵션을 제안하면서 사용자가 자유롭게 값을 입력할 수 있게 함
    - 텍스트 입력 필드와 함께 자동 완성 기능을 제안
- label은 사용자를 위해 브라우저에 표시할 레이블로 따로 지정하지 않으면 value 값을 레이블로 사용

```html
<form>
  <label for="fruitInput">Choose a fruit:</label>
  <input list="fruits" id="fruitInput" name="fruit">
  <datalist id="fruits">
    <option value="Apple" label="사과">
    <option value="Banana" label="바나나">
    <option value="Cherry" label="체리">
  </datalist>
</form>
```

<br>

### textarea 여러줄 입력
- textarea 태그는 여러 줄을 입력할 수 있는 box를 표시
    - textarea 태그 안의 문자열 표시
- cols와 rows 속성은 text box 크기를 지정
- disabled 속성은 화면에 표시는 하되 데이터를 수정할 수 없도록 비활성화 상태로 표시

<br>

### 공간 분할 태그
- HTML Elements는 크게 block 형식 elements와 inline 형식 element로 나뉨
- block 형식 element는 사용하는 element가 한 줄을 모두 사용
- inline 형식 element는 contents의 크기만큼만 공간을 사용
- 해당 형식의 태그 구분은 다음과 같음

|block 형식|inline 형식|
|:---:|:---:|
|div|span|
|h1~h6|a|
|p|input|
|ol, ul|b, strong, i, em|
|table|img|
|form||

<br>

### Semantic
- '의미론적인', '의미가 통하는' 이라는 뜻의 사전적의미
    - 브라우저, 검색엔진, 개발자 모두에게 콘텐츠의 의미를 명확히 설명하는 역할
    - sementic web이란 웹에 존재하는 수많은 웹 페이지의 메타데이터를 부여하며 
- 검색 로봇 또는 스크린 리더 등의 기계가 쉽게 해석하고 분석할 수 있도록 만들어진 태그
- HTML5 에서 웹문서 레이아웃을 표준화하는 semantic tag 등장

<br>

### Semantic 요소 tag
- haeder : 헤더 지정
- nav : 문서간의 네비게이션 지정
- aside : 본문 이외의 내용 표시
- section : 본문의 여러 내용들(article) 포함
- acrticle : 본문의 주 내용이 포함
- footer : 제작정보와 저작권 표시 등 포함

```html
<!-- 기존 표현 -->
<div id ="header">
    ...
</div>
<div id="nav">
    ...
</div>
<div id="section">
    <div id="articale"></div>
    <div id="articale"></div>
    <div id="articale"></div>
</div>

<!-- 시멘틱 element -->
<header>
    ...
</header>
<nav>
    ...
</nav>
<section>
    <article></article>
    <article></article>
    <article></article>
</section>
```