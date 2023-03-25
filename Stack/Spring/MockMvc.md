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
    	
        String jjson = "{\"name\": \"블로그 참조\"}";
        
        //mockMvc에게 컨트롤러에 대한 정보를 입력하자.
        
        mockMvc.perform(
            get("test?query=food") //해당 url로 요청을 한다.
            .contentType(MediaType.APPLICATION_JSON) // Json 타입으로 지정
            .content(jjson) // jjson으로 내용 등록
            .andExpect(status().isOk()) // 응답 status를 ok로 테스트
            .andDo(print()); // 응답값 print
        )
        }
}
```

<br>

### MockMvc 설정 어노테이션
1. ContextHierychy
- 테스트용 DI 컨테이너 만들 때 Bean 파일을 지정
```java
@ContextHierarchy({
	@ContextConfiguration(classes = AppConfig.class),
    @ContextConfiguration(classes = WebMvcConfig.class)
})
```

<br>

2. WebAppConfiguration
- Controller 및 web 환경에 사용되는 빈을 자동으로 생성하여 등록

<div style="text-align: right">23-03-24</div>

-------

## Reference
- https://velog.io/@jkijki12/Spring-MockMvc
- https://velog.io/@boo105/MockMvc-넌-또-뭐하는-애야