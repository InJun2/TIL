## M1 Netty Error

### 에러 메세지
- SEVERE: Unable to load io.netty.resolver.dns.macos.MacOSDnsServerAddressStreamProvider, fallback to system defaults. This may result in incorrect DNS resolutions on MacOS. java.lang.reflect.InvocationTargetException at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method) at ...

<br>

### 해당 문제 사항
- Netty를 쓰는 환경에서는 해당 오류가 M1 Apple chip 부터 발생한다. 해당 문제는 M1 에서만 현재 발생. 현재 에러메세지는 출력되지만 프로젝트는 실행됨.
- native library를 로드하지 못해 발생한 오류로 netty-resolver-dns-native-macos라이브러리 문제

<br>

### 해당 해결 방법
- io.netty:netty-resolver-dns-native-macos 모듈을 runtimeOnly시 추가 해주는 방식을 사용.
- 해당 모듈을 그냥 추가해준다면 m1이 아닌 OS('osx-aarch_64'가 아닌 OS)에서는 문제가 발생하기 때문에 application.yml 파일에 runtimeOnly('io.netty:netty-resolver-dns-native-macos:4.1.93.Final:osx-aarch_64') 추가

<br>

### 참조링크
- https://junho85.pe.kr/2054
- https://github.com/netty/netty/issues/11020#issuecomment-1334134901
