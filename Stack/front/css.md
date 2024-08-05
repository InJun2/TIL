# CSS

### CSS
- CSS(Cascading Style Sheets)란 HTML 문서를 화면에 표시하는 방식을 정의한 언어
- W3C에서 공인한 표준
- 기존 웹 문서를 다양하게 설계하고 변경 요구 대응에 따르는 어려움을 보완
- CSS 규칙은 선택자(selector)와 선언(declaration) 두 부분으로 구성
- 선택자는 규칙이 적용되는 엘리먼트
- 선언 부분에서는 선택자에 적용될 스타일을 작성
- 선언은 중괄호로 감싸며 속성(property)과 값(value)로 이루어짐
    - 속성은 바꾸고 싶은 요소
    - 값은 속성에 적용할 값
    - 선언 안에 하나 이상의 속성을 작성할 수 있으며 각 속성은 semi-colon으로 구분

```css
/* .css : 선택자(selector) */
.css {  
    /* 중괄호는 선언 블록 */
    /* 선언(Declaration)은 속성과 값의 키-값으로 구성 */
    margin: 30px;
    color: #000;
}
```

<br>

### CSS 적용방법
- HTML 문서에 스타일을 적용하는 방법은 외부 스타일시트, 내부 스타일시트, 인라인 스타일 3가지로 분류
- 외부 스타일 시트는 *.css 파일을 <link>나 @import로 HTML 문서에 연결하여 사용
- 하나의 CSS 파일만 수정하면 해당 스타일시트를 사용하는 모든 페이지에 변경 내용 적용
- 외부 스타일 시트가 세가지 방법 중 가장 많이 사용되며 사용 방법은 다음과 같음
- CSS 코드 컨밴션은 BEM 방식을 주로 따름

```html
<!-- 외부 스타일 시트 -->
<link rel="stylesheet" type="text/css" href="path/filename.css" />

<style type="test/css">
    @import url("path/filename.css");
</style>

<!-- 내부 스타일 시트 -->
<style type="text/css">
    .embedded-style {
        /* ... */
    }
</style>

<!-- 인라인 스타일 시트 -->
<p style="background-color: cyan; color: magenta">인라인 스타일 시트</p>
```

<br>

### id와 class 의 차이