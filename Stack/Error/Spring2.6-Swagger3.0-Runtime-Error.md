## Spring 2.6 Swagger 3.0 Runtime Error

### 에러 메세지
- org.springframework.context.ApplicationContextException: Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException: Cannot invoke "org.springframework.web.servlet.mvc.condition.PatternsRequestCondition.getPatterns()" because "this.condition" is null ...

<br>

### 해당 문제 사항
- mvc 설정을 하던 중에 this.condition 부분에 NullPointerException을 발생했고, 해당 문제는 스프링 부트 2.6.0 버전부터 적용된 변경 사항이 springfox 의 기존 작동에 문제를 일으키고 있음. 
- Spring 2.6.X 버전에서 swagger 3.0 버전을 사용시 문제가 발생

<br>

### 해당 해결 방법
- 해당 문제는 Springfox의 버그로 Spring MVC 설정 방법에 대해 MVC 경로 일치를 PathPattern 기반 일치자가 아닌 Ant 기반 경로 일치자를 사용하여 수정. Spring Boot 2.6부터 PathPattern이 기본값으로 존재하므로 application.yml 파일에서 spring.mvc.pathmatch.matching-strategy = ant_path_matcher 설정을 추가하였음
- 해당 문제는 WebConfig 클래스에서 @EnableWebMvc를 추가하면 스프링 MVC 자동구성은 WebMvcAutoConfiguration이 담당한다. 이 구성이 활성화되는 조건 중에 WebMvcConfigurationSupport 타입의 빈을 찾을 수 없을 때 라는 조건이 있고 바로 이 조건(@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)) 때문에 이미 빈이 생성되었기 때문에 기본 전략을 참고하지못하여 해당 어노테이션을 사용해도 해결이 됬지만 위의 방법을 사용하기로 하였음

<br>

### 참조링크
- https://stackoverflow.com/questions/70036953/spring-boot-2-6-0-spring-fox-3-failed-to-start-bean-documentationpluginsboo
- https://incheol-jung.gitbook.io/docs/q-and-a/spring/enablewebmvc
