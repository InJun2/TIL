# Maven vs Gradle

### 빌드 도구
- 빌드도구는 소스코드에서 어플리케이션 생성을 자동화 하기 위한 프로그램
- 빌드는 코드를 사용 혹은 실행 가능한 형태로 컴파일링, 링킹, 패키징 하는것을 포함
- 대규모 프로젝트에선 빌드프로세스를 수동으로 호출하는 것은 무엇을 빌드할지, 어떤 순서로 할지, 어떤 의존성이 있는지 모두 추적하기 쉽지 않기 때문에 실용적이지 않음
- 빌드 도구의 빌드 자동화를 통해 일관되게 수행 가능
    - 종속성 다운로드
    - 소스코드를 바이너리코드로 컴파일
    - 바이너리 코드를 패키징
    - 테스트 실행
    - 프로덕션 시스템에 배포
- 빌드도구에는 Ant, Maven, Gradle 등이 존재
```
빌드 자동화
- 소프트웨어 개발자가 반복해서 하는 코딩을 잘 짜여진 프로세스를 통해 자동으로 실행하여, 믿을 수 있는 결과물도 생산해 낼 수 있는 일련의 작업방식 및 방법
- 지속적인 통합(continuous Integration)과도 일맥상통되는 의미 -> 인간적 실수의 가능성을 최소한으로 감소
```

### Maven
- Apache Maven 으로 자바용 프로젝트 관리 도구
- Apache Ant의 대안으로 만들어짐
- 아파치 라이선스로 배포되는 오픈 소스 소프트웨어
- 고정적이고 선형적인 단계의 모델 기반
- POM.XML 파일을 이용하여 빌드 중인 프로젝트, 빌드 순서, 다양한 외부 라이브러리 종속성 관계를 나타내어 사용
- Maven은 외부저장소에서 필요한 라이브러리와 플러그인들을 다운로드 한다음, 로컬시스템의 캐시에 모두 저장
- 네트워크를 통해 연관된 라이브러리까지 같이 업데이트

#### POM(Project Object Model)
- 이름 그대로 Project Object Model의 정보를 담고 있는 파일
- 프로젝트 정보 : 프로젝트의 이름, 라이센스 등
- 빌드 설정 : 소스, 리소스, 라이프사이클별 실행한 플러그인 등 빌드와 관련된 설정
- 빌드 환경 : 사용자 환경 별로 달라질 수 있는 프로파일 정보
- pom 연관 정보 : 의존 프로젝트(모듈), 상위 프로젝트, 포한하고 있는 하위 모듈 등

```XML
<dependencies>
    <!-- spring boot starter web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>2.7.2</version>
    </dependency>

    ...
</dependencies>
```

<br>

### Gradle
- Ant Builder와 Groovy 스크립트 기반으로 만들어져 기존 Ant의 역할과 배포 스크립트의 기능을 모두 사용 가능
- JVM의 스크립트 언어인 Groovy 언어를 사용한 Domain-specific-language를 사용
- 안드로이드 앱의 공식 빌드 시스템
- 작업 의존성 그래프를 기반
- Gradle은 프로젝트의 어느부분이 업데이트되었는지 알기 때문에, 빌드에 점진적으로 추가할 수 있음 -> 업데이트가 이미 반영된 빌드의 부분은 즉 더이상 재실행되지 않아 빌드 시간이 훨씬 단축
- Gradle의 성능
    - Incrementality 중분성 : Gradle은 가능한 경우 변경된 파일만 작업해 중복 작업을 피함 -> 증분 빌드
    - Build cache : 동일한 입력에 대해서 gradle 빌드를 재사용함
    - Gradle Demon : 빌드 정보를 메모리에 유지하는 프로세스를 구동함
- JAVA, C/C++, Python 등을 지원

```
Groovy
- Java 가상 머신에서 실행되는 스크립트 언어
- 자바 가상 버신에서 동작하지만 자바와는 달리 소스 코드를 컴파일 할 필요가 없음 -> 소스코드를 그대로 실행
- Java와 호환되고 Java 클래스 파일을 그대로 Groovy 클래스로 사용 가능
- Java 문법과 유사하여 빌드처리를 관리하기 편리
```

```javascript
dependencies {
    // spring boot starter web
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-web', version: '2.7.2'
}
```

<br>

### Maven vs Gradle
- 기본적으로 스크립트 길이와 가독성 면에서 gradle이 우세
- Maven과 Gradle 모두 다중 모듈 빌드를 병렬로 실행할 수 있지만, Gradle은 어떤 작업이 업데이트 되었고 안되었는 지를 체크하기 때문에 incremental build를 허용 -> 이미 업데이트된 작업에 대해서는 실행되지 않아 빌드 시간이 훨씬 단축됨
- Maven은 고정적이고 선형적인 단계의 모델을 기반하고 Gradle은 작업 의존성 그래프를 기반함
- maven의 경우 정적인 형태의 XML로 동적인 빌드를 적용할 경우 어려움이 많다면 gradle은 groovy를 사용하기 때문에 동적인 빌드는 Groovy 스크립트로 플러그인을 호출하거나 직접 코드를 짜면 됨
- maven의 경우 멀티 프로젝트에서 특정 설정을 다른 모듈에서 실행하려면 상속을 받아야하지만, gradle은 설정 주입 방식을 제공하기 때문에 멀티 프로젝트에 매우 적합
- gradle은 concurrent에 안전한 캐시 허용 -> 2개 이상의 프로젝트에서 동일한 캐시를 사용할 경우, 서로 overwrite 되지 않도록 checksum 기반의 캐시를 사용하고 캐시를 repository와 동기화 시킬 수 있음


<br>

<div style="text-align: right">22-08-19</div>

-------

## Reference
- https://simplehanlab.github.io/mavengradle
- https://dev-coco.tistory.com/65
- https://jisooo.tistory.com/entry/Spring-빌드-관리-도구-Maven과-Gradle-비교하기