# Java 와 C 언어의 차이

### C 언어
- 운영체제를 개발할 목적으로 만든 언어로 하드웨어를 제어하는 시스템 프로그래밍이 가능. 주로 시스템 소프트웨어 개발에 사용
- C, C++, Go 등의 언어는 컴파일 언어
- C언어는 이식성을 갖춘 언어로 기종이 다른 컴퓨터에서도 사용할 수 있는 프로그램을 작성할 수 있음 (이식성이 존재 그러나 OS에 따라서는 재사용이 어려움)
- 메모리를 직접 관리하여 속도가 빠르고 메모리 낭비가 적으며, 바이너리 크기가 작음
```
바이너리 코드
- 컴퓨터가 인식할 수 있는 0과 1로 구성된 이진 코드

기계어
- 기계어는 0과 1로 이루어진 바이너리 코드
- 기계어가 이진코드로 이루어진 것이지 모든 이진코드가 기계어인 것은 아님
```

#### 실행 과정
- 컴파일러가 중간단계인 오브젝트 코드를 생성 후에, 링커가 필요한 라이브러리들을 하나로 합쳐 최종 실행 가능한 파일로 생성
- 라이브러리의 형태가 정적 라이브러리일 경우 라이브러리를 실행파일에 포함시키므로 실행파일이 커지고 동적 라이브러리의 경우 런타임에 링크가 일어남

#### 속도
- 속도가 빠르고 바이너리 크기가 작음
- 생산성보다 중요한 속도를 필요로 하는 임베디드 혹은 모바일 계열, 시스템 프로그래밍 등에서 주로 사용됨 (수정사항을 확인하기 위해선 매번 컴파일 해야함)

#### 언어 특징
- 절차지향 프로그래밍 언어
- 위에서 아래로 흐르는 것 처럼 순차적인 처리가 중요시 되며 프로그램 전체가 유기적으로 연결되도록 만드는 프로그래밍
- 흐름을 읽기는 좋지만 부분 수정할때도 전체코드를 컴파일 해야함

#### 메모리 관리
- 메모리 관리를 직접해야 한다. 코드로 하드웨어를 제어 가능
- OS 레벨의 메모리에 직접 접근하기 때문에 free() 라는 메소드를 호출하여 할당받았던 메모리를 명시적으로 해제해 주어야 함
- 위에 메모리를 명시적으로 해제해주지 않으면 Memory Leak(메모리 누수)이 발생하게 되고, 현재 실행중인 프로그램에서 Memory Leak 발생 시 다른 프로그램에도 영향을 끼칠 수 있음

<br>

### Java
- 운영체제에 독립적인 언어로 전 셰계 다양한 분야에서 사용하는 프로그래밍 언어로 주로 응용 프로그래밍을 위해 설계
- Java는 컴파일 언어이기도 하고, 인터프리터 언어이기도 한 혼합한 형태의 하이브리드 언어
- JVM 위에서 실행되므로, 여러 OS에서 실행 가능
- 속도는 C언어에 비해 느림
```
바이트 코드
- CPU가 이해할 수 있는 언어가 바이너리 코드라면 바이트 코드는 가상머신이 이해할 수 있는 언어
- CPU가 아닌 가상 먼신에서 이해할 수 있는 코드를 위핸 이진 표현법
- 가상 머신이 이해할 수 있는 0과 1로 구성된 이진코드를 의미. 
- 특정 하드웨어가 아닌 가상 컴퓨터에서 돌아가는 실행 프로그램을 위한 이진 표현법으로 하드웨어가 아닌 소프트웨어에 의해 처리되어 보통 기계어보다 더 추상적
```

#### 실행 과정
- 컴파일러가 바로 바이트 코드를 생성. 바이트 코드는 완전한 기계어가 아닌 언어로 JVM에 의해서 운영체제가 이해할 수 있도록 번역해줌
- 바이트 코드는 JVM에서만 실행이 가능 (JVM에 의해 어떤 운영체제라도 사용이 가능해짐)
- 런타임에 필요한 클래스들이 자바 가상기계에 링크되며 클래스 로더가 동적으로 필요한 클래스를 로딩 -> 컴파일된 클래스 파일이 JVM에서 실행되어 플랫폼에 종속적인 코드를 갖지 않아 플랫폼이 달라져도 다시 컴파일할 필요가 없음

#### 언어 특징
- 객체지향 프로그래밍 언어
- 프로그램을 잘게 나누어 부품화 한 후 조립하는 형식으로 여러개의 클래스로 나누어 개발이 가능 -> 필요한 클래스만 수정하여 컴파일 할 수 있어 유지보수 측면에서 유리
- 캡슐화, 다형성, 상속 등의 특징이 있음
- 뛰어난 보안성 및 멀티스레드를 지원

#### 메모리 관리
- GC(Gargbage Collection)이 메모리를 관리
- OS의 메모리 영역에 직접적으로 접근하지 않고 JVM이라는 가상머신을 이용해서 간접적으로 접근
- 메모리라는 까다로운 부분을 JVM에 모두 맡겨 편리
- 프로그램 실행 시 JVM 옵션을 주어 OS에 요청한 사이즈 만큼의 메모리를 할당받아서 실행하게 됨
- 할당받은 이상의 메모리를 사용하게 되면 에러가 나면서 자동으로 프로그램이 종료됨. 메모리 누수가 발생하면 현재 실행 중인 것만 죽고, 다른것에는 영향을 주지 않음 -> JVM을 사용하여 OS레벨에서 Memory Leak(메모리 누수)은 불가능한 장점 보유

<br>

<div style="text-align: right">22-09-09</div>

-------

## Reference
- https://okimaru.tistory.com/41
- https://velog.io/@syi9595/c언어와-자바와의-차이
