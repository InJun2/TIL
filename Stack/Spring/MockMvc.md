# MockMvc

### MockMvc?
- 웹 어플리케이션을 애플리케이션 서버에 배포하지 않고 테스트용 MVC 환경을 만들어 요청 및 전송, 응답기능을 제공해주는 유틸리티 클래스. Mock(모의) 처리
- 컨트롤러 테스트를 하고싶을 때 실제 서버에 구현한 애플리케이션을 올리지 않고 (서블릿 컨테이너를 사용하지 않고) 테스트용으로 시뮬레이션하여 MVC가 되도록 도와주는 클래스
- MockMvc를 사용하면 컨트롤러를 실행해 보는 것 뿐만 아니라, 컨트롤러에서 반환되는 모델 객체의 내용이나 뷰의 이름 등을 검증할 수 있음
- 과정은 다음과 같음
    - MockMvc를 생성한다
    - MockMvc에게 요청에 대한 정보를 입력한
    - 요청에 대한 응답값을 Expect를 이용하여 테스트
    - Expect가 모두 통과하면 테스트 통과
    - Expect가 1개라도 실패하면 테스트 실패

```java
class ControllerTest{
	
    @Autowired
    private MockMvc mockMvc; // mockMvc 생성
    
    @Test
    public void testController() throws Exception{
    	
        String jjson = "{\"name\": \"Test Name\"}";
        
        //mockMvc에게 컨트롤러에 대한 정보를 입력하자.
        
        mockMvc.perform(get("test?query=food") //해당 url로 요청을 한다.
            .contentType(MediaType.APPLICATION_JSON) // Json 타입으로 지정
            .content(jjson) // jjson으로 내용 등록
            .andExpect(status().isOk()) // 응답 status를 ok로 테스트
            .andDo(print()); // 응답값 print
        )
        }
}
```

<br>

### MockMvc 제공 메소드
#### 1. perform()
- MockMvc가 제공하는 메서드로 브라우저에서 서버에 URL 요청을 하듯 컨트롤러를 실행시킬 수 있음
- perform() 메소드는 RequestBuilder 객체를 인자로 받고, 이는 MockMvcRequestBuilders의 정적 메소드를 이용해서 생성

<br>

#### 2. MockMvcRequestBuilders
- MvckMvcRequestBuilders의 메소드들은 GET, POST, PUT, DELETE 요청 방식과 매핑되는 get(), post(), put(), delete() 메소드를 제공
- 이 메소드들은 MockHttpServletRequestBuilder 객체를 리턴하고, 이를 통해 HTTP 요청 관련 정보(파라미터, 헤더, 쿠키 등)를 설정할 수 있음

<br>

#### 3. contentType()
- 요청 헤더의 content type 설정

<br>

#### 4. content()
- 요청 바디의 content 설정

<br>

#### 5. andExpect()
- perform() 메소드를 이용하여 요청을 전송하면, 그 결과로 ResultActions 객체를 리턴하는데 이 객체는 응답 결과를 검증할 수 있는 andExpect() 메소드를 제공
- andExpect()가 요구하는 ResultMatcher는 MockMvcResultMatchers에 정의된 정적 메소드를 통해 생성할 수 있음
- MockMvcResultMatcher 객체는 컨트롤러가 어떤 결과를 전송했는지, 서버의 응답 결과를 검증

<br>

### MockMvcResultMatcher 객체가 제공하는 메소드
#### 1. 응답 상태 코드 검증
- isOK() : 응답 상태코드가 정상적인 처리(200)인지 확인
- isNotFound() : 응답 상태코드가 404 Fot Found인지 확인
- isMethodNotAllowed() : 응답 상태코드가 메소드 불일치(405)인지 확인
- isInternalServerError() : 응답 상태코드가 예외발생(500)인지 확인
- is(int status) : 특정 응답 상태코드가 설정되어있는지 확인 

<br>

#### 2. 뷰/리다이렉트 검증
- 컨트롤러가 리턴하는 뷰를 검증할 때는 view() 메소드를 사용
- andExpect(view().name("user"))는 컨트롤러가 리턴한 뷰 이름이 맞는지 검증
- 만약 요청 처리 결과가 리다이렉트 응답이면 redirectedUrl() 메소드를 사용한다. andExpect(redirectedUrl("/user")) 코드는 '/user' 화면으로 리다이렉트했는지 검증

<br>

#### 3. 모델 정보 검증
- 컨트롤러에서 저장한 모델의 정보를 검증하고 싶다면 MockMvcResultMatchers.model() 메소드를 사용
- attributeExists(String name) : name에 해당하는 데이터가 Model에 포함되어 있는지 검증
- attribute(String name, Object value) : name에 해당하는 데이터가 value 객체인지 검증

<br>

#### 4. 요청/응답 전체 메시지 확인하기
- 생성된 요청과 응답 메시지를 모두 확인해보고 싶다면 perform 메소드가 리턴하는 ResultAction의 andDo(ResultHandler handler) 메소드를 사용
- MockMvcResultHandlers.print() 메소드는 ResultHandler를 구현한 ConsolerPrinting ResultHandler 객체를 리턴하여, ConsolePrintingResultHandler를 andDo() 메소드 인자로 넘겨주면 콘솔에 요청/응답과 관련된 정보를 모두 출력

<br>

<div style="text-align: right">23-03-24</div>

-------

## Reference
- https://velog.io/@jkijki12/Spring-MockMvc
- https://velog.io/@boo105/MockMvc-넌-또-뭐하는-애야
- https://scshim.tistory.com/321