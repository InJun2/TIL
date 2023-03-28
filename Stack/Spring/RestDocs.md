# RestDocs

### RestDocs?
- 테스트 코드 기반으로 Restful API 문서를 돕는 도구
- Asciidoctor를 이용해서 HTML 등등 다양한 포맷으로 문서를 자동으로 출력할 수 있음
- RestDocs를 사용하는 이유는 테스트가 성공해야 문서가 작성되기 때문에 TDD 위주의 검증을 강제하는 문서화 도구로 신뢰성있는 문서 제작 가능
- 자바 문서 자동화도구 중 하나로 대표적인 도구에는 Swagger와 Rest Docs가 존재
- Swagger와 다르게 실제코드에 추가되는 코드가 없어 비즈니스 로직의 가독성에 영향을 미치지 않음
- 하지만 테스트 코드를 구현하고 성공해야 문서작성이 되므로 많은 시간이 소요되고 적용하기 어려움

<br>

### Swagger의 단점?
- 로직에 애노테이션을 통해 명세를 작성하게 되는데 지속적으로 사용하게 된다면 명세를 위한 코드들이 많이 붙게되어 전체적으로 가독성이 떨어짐
- 테스트 기반이 아니기에 문서가 100% 정확하다고 확신할 수 없음
- 모든 오류에 대한 여러 가지 응답을 문서화할 수 없음

<br>

### Rest Docs vs Swagger

|Spring Rest Docs|Swagger|
|:---:|:---:|
|Asciidoctor을 이용해서 다양한 포맷으로 문서를 자동 출력|API를 테스트 해볼수 있는 화면을 제공|
|테스트가 성공해야 문서작성이 가능함|제품코드에 어노테이션을 추가해야 함|
|적용하기 어려움|적용하기 쉬움|
|제품코드에 영향이 없음|제품코드와 동기화가 안될 수 있음|

<br>

### Rest Docs 사용방법
- @AutoConfigureRestDocs 어노테이션을 통하여 Rest Docs 자동설정
- 기본적으로 테스트는 MockMvc로 진행한 이후 ResultAction의 andDo() 메서드를 이용하여 문서화를 진행

<br>

```java
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs // rest docs 자동 설정
class MemberControllerTest  {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void member_get() throws Exception {
        mockMvc.perform(
                get("/api/members/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo( // rest docs 문서 작성 시작
                        document("member-get", // 문서 조각 디렉토리 명
                                pathParameters( // path 파라미터 정보 입력
                                        parameterWithName("id").description("Member ID") 
                                ),
                                responseFields( // response 필드 정보 입력
                                        fieldWithPath("id").description("ID"),
                                        fieldWithPath("name").description("name"),
                                        fieldWithPath("email").description("email")
                                )
                        )
                )
        ;
    }
}
```

<br>

- 빌드하고 나면 build/generated-snippets 폴더에 테스트에서 작성한 디렉토리 명의 폴더로 파일이 default로 생성
- AsciiDoc 플러그인 설치 이후 src/docs/asciidoc 디렉토리를 만들고 index.adoc 파일을 생성
- [Asciicod 사용법](https://narusas.github.io/2018/03/21/Asciidoc-basic.html)

```
= REST Docs 문서 만들기 (글의 제목)
backtony.github.io(부제)
:doctype: book
:icons: font
:source-highlighter: highlightjs // 문서에 표기되는 코드들의 하이라이팅을 highlightjs를 사용
:toc: left // toc (Table Of Contents)를 문서의 좌측에 두기
:toclevels: 2
:sectlinks:

[[Member-API]]
== Member API

[[Member-단일-조회]]
=== Member 단일 조회
operation::member-get[snippets='http-request,path-parameters,http-response,response-fields']
```

<br>


<br>

### 테스트 코드 리팩토링 사항
- 테스트 코드에서 andDo(document()) 부분에서 문서명을 항상 지정해줘야 하는 점
- 테스트 코드로 인해 build/generated-snippets에 생성된 파일 내용들을 보면 json 포멧이 한줄로 작성되어 보기 매우 불편한 점
- 관례상 andDo(print()) 를 모두 붙이는데 이 코드가 중복된다는 점

<br>

<div style="text-align: right">23-03-27</div>

-------

## Reference
- https://techblog.woowahan.com/2597/
- https://backtony.github.io/spring/2021-10-15-spring-test-3/