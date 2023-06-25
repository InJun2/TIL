# Code Deploy

### AWS Code Deploy란?
- SourceCode를 운영환경에 자동 배포하는 역할을 수행하는 AWS Service로 CD 지속적 배포 서비스
- code deploy agent를 ec2에 설치하여 code deploy에서 해당 ec2를 사용할 수 있게 해줌
- code deploy는 우리가 직접 운영환경에 전송하여 기존 프로그램을 종료하고 새로운 버전의 프로그램을 실행하는 과정을 자동화하여 대신 진행
- 프로젝트에서 따로 코드구현을 통해 명령어를 사용하겠지만 code deploy에서 배포할 사전 작업이 필요함

<br>

### Code Deploy 배포 사전작업
1. EC2 인스턴스에서 사용할 IAM ROLE(역할) 생성
    - [IAM](https://us-east-1.console.aws.amazon.com/iamv2/home?region=ap-northeast-2#/home)에서 역할 만들기
    - 이름 지정, AWS 서비스체크, EC2체크 후 다음
    - 해당 역할에 밑의 예시에서 사용한 프로젝트에서는 AmazonEC2FullAccess, AmazonS3FullAccess, AWSCodeDeployFullAccess, AWSCodeDeployRole Access 권한을 부여하였음
2. CodeDeploy 에서 사용할 IAM ROLE(역할) 생성
    - 마찬가지로 [IAM](https://us-east-1.console.aws.amazon.com/iamv2/home?region=ap-northeast-2#/home)에서 역할 만들기
    - 이름 지정, AWS 서비스체크, 다른 AWS 서비스의 사용 사례를 눌러 CodeDeploy 검색으로 통해 체크 후 다음
    - AWSCodeDeployRole 권한 부여
3. IAM 사용자 그룹/사용자 생성(Identity and Access Management)
    - Code Deploy를 실행할 사용자를 만드려고 함
    - 기존 사용자를 사용한다면 AWSCodeDeployFullAccess와 AWSCodeDeployRole 권한을 추가. 새로 생성한다면 바로 밑부터 진행
    - 새로 만든다면 [IAM](https://us-east-1.console.aws.amazon.com/iamv2/home?region=ap-northeast-2#/groups) 에서 그룹 생성
    - 이후 [IAM](https://us-east-1.console.aws.amazon.com/iamv2/home?region=ap-northeast-2#/users) 에서 사용자를 생성
    - 이름을 지정 후 엑세스 키-프로그래밍 방식 엑세스 체크, AWSCodeDeployFullAccess와 AWSCodeDeployRole 권한을 추가
4. EC2 인스턴스에 IAM 역할 지정
    - ec2 인스턴스를 새로 생성한다면 IAM 인스턴스 프로파일에 1번에서 만들었던 IAM 지정
    - 기존 ec2에서 IAM을 수정한다면 ec2 인스턴스 체크 후 작업 -> 보안 -> IAM 역할 수정을 통해 해당 IAM 지정
5. EC2에 CodeDeploy Agent 설치
    - ec2에서 codedeploy를 실행하기 위한 agent를 설치하기 위해 아래 명령어를 순차대로 실행한다
    - aws s3 cp s3://aws-codedeploy-ap-northeast-2/latest/install . --region ap-northeast-2
    - chmod +x install
    - yum install -y ruby
    - ./install auto
    - service codedeploy-agent status 명령어를 통한 agent 프로세스 상태 확인
6. AWS CodeDeploy 애플리케이션/배포그룹 생성
    - [CodeDeploy](https://ap-northeast-2.console.aws.amazon.com/codesuite/codedeploy/applications) 에서 애플리케이션 생성
    - 애플리케이션 이름 지정 후 컴퓨팅 플랫폼 EC2/온프래미스 지정후 생성
    - 해당 애플리케이션을 클릭하여 배포그룹 -> 배포그룹생성
    - 배포 이름 지정 후 서비스역할에는 2번에서 생성하였던 IAM 등록
    - 배포 유형은 현재 위치, 환경 구성은 온프레미스 인스턴스 지정 후 키에 Name, 값에는 ec2 인스턴스 이름 등록
    - 배포 구성은 현재 인스턴스 하나만 배포하고 있기때문에 CodeDeployDefault.AllAtOnce를 지정하였음
        - 다른 설정으로 사용했다가 Error 발생하여 이걸로 지정하였음..
        - OneAtTime은 한번에 하나씩 배포, HalfAtTime은 절반씩 배포, AllAtOnce는 한번에 다 배포를 의미
7. AWS CodeDeploy 배포 생성
    - [CodeDeploy](https://ap-northeast-2.console.aws.amazon.com/codesuite/codedeploy/applications)에서 배포 만들기
    - 6에서 만든 배포 그룹 지정, 애플리케이션을 Amazon S3에 저장 체크
    - 개정 위치는 S3에서 만들었던 버킷을 등록하고 파일 형식은 zip으로 사용하였음
        - S3 만드는 방법은 [해당 링크](https://github.com/InJun2/TIL/blob/main/Stack/AWS/S3.md)에 간단히 기재
    - 이후 추가 배포 동작 설정은 체크하지 않고 진행하였음

<br>

### CodeDeploy 실행
- CodeDeploy를 git action을 통해 실행하였음. 명령어는 다음과 같음. {} 안의 내용을 자신이 생성한 이름/설정을 대신 입력하면 됨
    - aws deploy create-deployment --application-name {codedeploy application 이름} --deployment-config-name {배포 그룹 배포 구성} --deployment-group-name {배포 그룹 명} --s3-location bucket={S3 버킷명},bundleType=zip,key={S3에 저장된 zip 파일 명}
    - 사용한 코드는 [GitHub Action](https://github.com/InJun2/TIL/blob/main/Stack/Git/Git-Action.md)에 정리
- CodeDeploy 설정 등록
    - 프로젝트의 바로 하위인 위치에 appspec.yml 생성. 이름을 해당 이름을 그대로 생성해야함
    - 운영환경과 ec2사용자/프로젝트명 디렉토리 지정하여 권한부여 및 프로젝트 바로 하위의 scripts 디렉토리 안에 sh 파일을 생성하고 해당 파일을 통해 codeDeploy가 실행됨
    - 사용한 코드는 다음과 같음
    ```yml
    version: 0.0
    os: linux

    files:
    - source: /
        destination: /home/ec2-user/glass-bottle
    permissions:
    - object: /home/ec2-user/glass-bottle
        mode: 755

    hooks:
    AfterInstall:
        - location: scripts/deploy.sh
        timeout: 240
        runas: root
    ```
- 이후 실행한 셸 파일은 다음과 같음
```sh
# git action에서 빌드한 자바 파일을 찾아서 BUILD_JAR에 저장하고, 해당 파일 이름만을 JAR_NAME에 저장
# code deploy 실행 중 발생하는 로그를 저장할 DEPLOY_LOG 지정
BUILD_JAR=$(ls /home/ec2-user/glass-bottle/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
DEPLOY_LOG=/home/ec2-user/action/deploy.log
echo "> build 파일명: $JAR_NAME" >> $DEPLOY_LOG

# 해당 DEPLOY_PATH에 위에서 찾았던 jar파일 특정 위치에 복사
echo "> build 파일 복사" >> $DEPLOY_LOG
DEPLOY_PATH=/home/ec2-user/action/
cp $BUILD_JAR $DEPLOY_PATH

# JAR_NAME인 프로세스 확인하여 저장
echo "> 현재 실행중인 애플리케이션 pid 확인" >> $DEPLOY_LOG
CURRENT_PID=$(pgrep -f $JAR_NAME)

# 만약 구동중이라면 프로세스 종료, 구동중이 아니라면 해당 문자열 로그에 출력
if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $DEPLOY_LOG
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

# git submodule을 가져오기 위한 명령어 추가
git submodule update --remote --recursive
git add .
git commit -m "update submodules"
git pull origin main


# 권한 추가
echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

# 위에서 복사했던 jar 파일 background 실행 및 로그를 $DEPLOY_LOG 위치에 저장, 에러 로그도 디렉토리를 따로 지정하여 저장
DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 실행 : $JAR_NAME"    >> $DEPLOY_LOG
nohup java -jar $DEPLOY_JAR >> $DEPLOY_LOG 2>/home/ec2-user/action/deploy_err.log &
```

<br>

### 적용 후기
- 처음 적용하다보니 생각보다 사전작업이 너무 이해가 되지 않았음.. AWS에서 생각 보다 지원해주는 기능이 많구나 느꼈고 설정, 명령어를 사용하는 부분에서 잘못된 부분이 많아 꾀나 헤매면서 진행하였음.. git action을 통한 자동배포가 신기했고 이후에는 zip파일이 아닌 이미지를 통해 저장할 예정임

<br>

### 참조링크
- https://galid1.tistory.com/745
- https://sangchul.kr/entry/aws-AWS-CodeDeploy-사용법
- https://velog.io/@leeeeeyeon/Github-Actions-CodeDeploy-S3로-CICD-구축