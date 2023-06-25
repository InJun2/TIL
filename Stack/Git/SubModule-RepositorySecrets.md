# Submodule & Repository Secrets

### Submodule, Repository Secrets 사용이유
- github를 이용하는데 있어 민감한 데이터를 사용/관리해야 하는 경우 사용
- 코드 내부적으로 민감한 데이터를 사용하는 경우(yml에 등록하여 사용) Submodule 사용
- git action에서 사용하는데 있어 환경변수에서 민감한 데이터를 사용하는 경우 Repository Secrets 사용

<br>

### SubModule
- git의 repository 하위에 다른 repository 관리하기 위한 도구
- 상위 저장소를 슈퍼 프로젝트, 하위 프로젝트를 서브 모듈이라고 함
- 서브 모듈을 통해 특정한 git repository를 다른 repository의 하위 디렉토리로 사용할 수 있음

<br>

### Repository Secrets
- GitHub Actions 에서 보안이 중요한 환경변수들을 환경 변수를 암호화해서 저장할 수 있는 기능
- ${{ secrets.등록한 Key의 이름 }} 방식으로 사용

<br>

### 참조링크
- https://hudi.blog/git-submodule/
- https://ji5485.github.io/post/2021-06-26/create-env-with-github-actions-secrets/