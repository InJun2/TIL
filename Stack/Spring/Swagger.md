# Swagger

### Swagger 란?
- 백엔드 개발에 있어 Web API를 문서화하기 위한 도구
- 간단한 설정으로 프로젝트의 API 목록을 웹에서 확인 및 테스트 가능하게 하는 라이브러리이며, 특히 RESTful API를 문서화시키고 관리 가능
- SpringBoot에서는 Swagger를 사용하면 문서 수정을 자동화가 가능
- Spring-fox, Spring-Doc 2가지가 존재하며 현재는 spring fox를 다루지만 spring-doc이 꾸준히 업데이트 되고 있어 사용한다면 spring-doc을 추천하고 싶음

### 사용 방법
- 다음 방식은 action 프로젝트에서 사용한 방식으로 설정해둔 어노테이션을 사용하여 직접 추가.
- 의존성 추가
    - implementation 'io.springfox:springfox-boot-starter:3.0.0'
- Swagger Config class 생성. 
```java
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {   // Docket: Swagger 설정의 핵심이 되는 Bean
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)  // useDefaultResponseMessages: Swagger 에서 제공해주는 기본 응답 코드 (200, 401, 403, 404). false 로 설정하면 기본 응답 코드를 노출하지 않음
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) // apis: api 스펙이 작성되어 있는 패키지 (Controller) 를 지정
                .paths(PathSelectors.any())         // paths: apis 에 있는 API 중 특정 path 를 선택
                .build()
                .apiInfo(apiInfo());                // apiInfo:Swagger UI 로 노출할 정보
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Selab Auction Api")     // 서비스명
                .description("회원이 상품을 등록하고 해당 상품에 대한 경매 진행")       // API 설명
                .version("1.0")             // API 버전
                .build();
    }
}
```
- 사용하는 Controller에서 메소드에 @ApiOperation 어노테이션을 통해 지정
```java
@PostMapping
@ApiOperation(value = "경매 참여", notes = "해당 상품 번호와 멤버 번호, 경매 참여 가격 입력받아 경매 참여 진행하기")
public ResponseEntity<AuctionResponseDto> participateAuction(@RequestBody @Valid CreateAuctionDto createDto) {
    var response = auctionService.participateAuction(createDto);

    return ResponseDto.created(response);
}

@PostMapping("/immediate-purchase")
@ApiOperation(value = "상품 즉시 구매", notes = "해당 상품 번호와 멤버 번호를 입력받아, 즉시 구매 가격으로 구매 진행하기")
public ResponseEntity<AuctionResponseDto> immediatePurchaseItem(@RequestBody @Valid CreateImmediatePurchaseDto createDto) {
    var response = auctionService.immediatePurchaseItem(createDto);

    return ResponseDto.created(response);
}
```
- 이후 확인은 자신의 url에서 /swagger-ui/index.html을 추가하여 확인 가능
    - ex) localhost:8080/swagger-ui.html/

<br>

### 사용방법2
- 다음 방식은 glass-bottle에서 지인의 자동화 설정을 통한 구현방식
- 의존성 추가 동일
    - implementation 'io.springfox:springfox-boot-starter:3.0.0'
- 설정 자동화. 해당 방식은 [DongGeon0908](https://github.com/DongGeon0908)님의 방식으로 사용하면서도 작동방식이 신기하기만 하다..
```java
@Configuration
@EnableWebMvc
public class SwaggerConfig {
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList("application/json", "application/xml")
    );

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    private ResolvedType typeResolver(Class<?> clazz) {
        return new TypeResolver().resolve(clazz);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .useDefaultResponseMessages(false)
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver(Pageable.class), typeResolver(SwaggerPageable.class)))
                .ignoredParameterTypes(
                        WebSession.class,
                        ServerHttpRequest.class,
                        ServerHttpResponse.class,
                        ServerWebExchange.class
                )
                .apiInfo(new ApiInfo(
                        "Glass Bottle API",
                        "Management REST API SERVICE",
                        "1.0",
                        "urn:tos",
                        new Contact(
                                "Glass Bottle API",
                                "https://github.com/selab-hs/glass-bottle",
                                "wrjssmjdhappy@gmail.com"
                        ),
                        "Apache 2.0",
                        "http://www.apache.org/licenses/LICENSE-2.0",
                        new ArrayList<>()
                ))
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .securitySchemes(List.of(new ApiKey("JWT", "Authorization", "header")))
                .select()
                .paths(PathSelectors.regex("/api/.*"))
                .build();
    }

    public String[] whiteListInSwagger() {
        return new String[]{
                "/swagger",
                "/swagger-ui/springfox.css",
                "/swagger-ui/swagger-ui-bundle.js",
                "/swagger-ui/springfox.js",
                "/swagger-ui/swagger-ui-standalone-preset.js",
                "/swagger-ui/swagger-ui.css",
                "/swagger-resources/configuration/ui",
                "/swagger-ui/favicon-32x32.png",
                "/swagger-resources/configuration/security",
                "/swagger-resources",
                "/v2/api-docs",
                "/swagger-ui/index.html",
                "/favicon.ico"
        };
    }

    @Data
    public static class SwaggerPageable {
        private Integer page;
        private Integer size;
    }
}
```
- 이후 확인은 자신의 url에서 /swagger-ui/index.html을 추가하여 확인 가능

<br>

### 후기
- swagger 3.0을 사용하였는데 spring-fox에 대해 문제가 많이 발생한다. 업데이트가 이후 거의 진행되지 않아 actuator 사용시에 endpoint 접근 방식에서 문제, 스프링 버전과도 문제 등등..
- 지인의 자동화 설정을 보고 신기하고 이해가 잘 되지 않았음.. 이후 따로 다시 확인해서 내용을 추가해보고자 함
- 문서화를 간단히 진행하기 위해 사용했는데 테스트코드 위주의 진행을 한다면 RestDocs가 좋을 것 같음. 문서화로 사용하기 매우 편했지만 이후 RestDocs를 통한 문서화도 진행해 보고싶음

<br>

### 참조링크
- https://lucas-owner.tistory.com/28