# 접근제한자 (Access Modifier)

### 접근 제한자란?
>- 멤버들은 객체 자신들만의 속성이자 특징이므로 대외적으로 공개되는 것이 결코 좋은 것은 아님. 해당 부분 해소를 위해 프로그래머가 객체의 멤버들에게 접근 제한을 걸 수가 있는데 자바에서는 접근 제한자를 이용하여 접근을 제한함
>- 접근 제한자는 보안 문제와 클래스간의 결합도를 낮춤
>- 캡슐화를 위해 사용 ( 상수가 아닌 필드에서 접근제한을 private가 아닌 다른 접근 제한은 지양해야함. 메소드는 역할에 맞는 접근지정자 지정이 필요 )

<br>

### 접근 제한자 종류
>- public : 모든 접근을 허용
>- protected : 같은 패키지에 있는 객체와 상속관계의 객체들만 허용
>- default : 같은 패키지에 있는 객체들만 허용
>- private : 현재 객체 내에서만 허용

<br>

### 접근 제한자 사용 위치
>- 클래스 : public, default
>- 생성자 : public, protected, default, private
>- 멤버변수 : public, protected, default, private
>- 멤버메소드 : public, protected, default, private
>- 지역변수 : 접근제한자 사용 불허

<br>

<div style="text-align: right">22-06-20</div>

-------

## Reference
- https://gyrfalcon.tistory.com/entry/JAVA-접근-제한자
