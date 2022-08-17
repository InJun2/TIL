# Overloading vs Overriding

### Overloading
- 같은 이름의 메서드 여러개를 가지면서 <b>매개변수의 유형과 개수</b>가 다르도록 하는 기술
    - 메서드의 이름은 같고 매개변수의 갯수나 타입이 다른 함수를 정의
    - 여러개 정의 가능 -> 같은 기능을 하는 메소드를 하나의 이름으로 사용 가능, 메소드의 이름 절약
- 오버로딩은 메서드 오버로딩과 생성자 오버로딩이 존재하지만 개념은 같음
    - 같은 이름의 메서드를 여러개 정의하고 매개변수의 유형과 개수를 다르게하여 다양한 유형의 호출에 응답
- 리턴값만 다르게하는 오버로딩은 작성할 수 없음
- 접근 제어자도 자유롭게 지정 가능

```java
class OverloadingTest{
    //이름이 cat인 메서드
    void cat(){
        System.out.println("매개변수 없음");
    }
    
    //매개변수 int형이 2개인 cat 메서드
    void cat(int a, int b){
        System.out.println("매개변수 :"+a+", "+b);
    }
    
    //매개변수 String형이 한 개인 cat 메서드
    void cat(String c){
        System.out.println("매개변수 : "+ c);
    }
    
}
```

<br>

### Overriding
- 상위 클래스가 가지고 있는 메서드를 하위 클래스가 <b>재정의</b>해서 사용
- 상위 클래스를 상속받으면 상위 클래스가 가지고 있는 하위 클래스로 상속되어 하위클래스에서 사용이 가능하고 또한 메서드를 재정의(Overriding)이 가능
- 오버라이딩하고자하는 메소드의 이름, 매개변수, 리턴 값이 모두 같아야 함
- 메서드의 이름이 서로 같고, 매개변수가 같고, 반환형이 같을 경우에 상속받은 메서드를 덮어쓰는 개념이 이해하기 쉽다고 함 -> 부모 클래스의 메소드는 무시하고 자식 클래스에서 메서드를 구현하고 해당 기능을 사용
- 오버라이딩에서는 접근 제한자를 다르게 설정하는데 오버라이딩에서 접근 제한자를 설정하는 규칙이 존재
    - 자식 클래스에서 오버라이딩하는 메소드의 접근 제한자는 부모 클래스보다 더 좁게 설정할 수 없음 -> 부모 클래스가 default 일 경우 자식 클래스는 default, protected, public 의 접근 제한자 사용 가능
    - 예외(Exception)은 부모 클래스의 메소드 보다 많이 선언할 수 없음 -> 자식 클래스에서는 부모 클래스 예외보다 더 큰 예외를 throws 할 수 없음
    - static 메소드를 인스턴스의 메소드 또는 그 반대로 바꿀 수 없음 -> 부모 클래스의 static 메소드를 자식에서 같은 이름으로 정의할 수 있지만, 이것은 다시 정의하는 것이 아니라 같은 이름의 static 메소드를 새로 정의하는 것
```java
class Woman{    //부모클래스
    public String name;
    public int age;
    
    //info 메서드
    public void info(){
        System.out.println("여자의 이름은 "+name+", 나이는 "+age+"살입니다.");
    }
    
}
 
class Job extends Woman{    //Woman클래스(부모클래스)를 상속받음
 
    String job;
    
    public void info() {    //부모(Woman)클래스에 있는 info()메서드를 재정의
        super.info();       // super 예약어를 이용하여 부모클래스의 내용도 그대로 가져옴
        System.out.println("여자의 직업은 "+job+"입니다.");
    }
}
 
public class OverTest {
 
    public static void main(String[] args) {
        
        //Job 객체 생성
        Job job = new Job();
        
        //변수 설정
        job.name = "유리";
        job.age = 30;
        job.job = "프로그래머";
        
        //호출
        job.info();
    }
}
// 실행결과는 "여자의 직업은 프로그래머입니다"
```
<br>

```
super 키워드
- 부모 클래스로부터 상속받은 필드나 메소드를 자식 클래스에서 참조하는데 사용하는 참조 변수
- 인스턴스 변수의 이름과 지역변수의 이름이 같을 경우 인스턴스 변수 앞의 this를 사용했다면, 이와 마찬가지로 부모 클래스의 멤버와 자식 클래스의 멤버 이름이 같을 경우 super 키워드를 사용하여 구별
- 자바에서는 super 참조 변수를 사용하여 부모 클래스의 멤버에 접근이 가능
- super를 사용하여 부모 클래스의 멤버에 접근할 경우 해당 매개변수에 맞게 입력해야함
```
```java
public class ThisSuper {
	public static void main(String[] args) {
		// 자식 호출
		Child child = new Child();
		
		// 자식에서 메서드 호출
		child.CrazyKim();
	}
}

// 부모 클래스 선언 
class Mother {
	public String blog = "MoCrazyKim";	
	public int Period = 6;

	public void CrazyKim() {
		System.out.println(blog + "의 블로그입니다. 블로그는 만들어진지 " + Period + "년이 됐습니다.");
	}
}

// 자식 클래스 선언
class Child extends Mother {
	String blog = "ChCrazyKim";	
	int Period = 10;	

	public void CrazyKim() {
		super.CrazyKim();
		System.out.println(blog + "의 블로그입니다. 블로그는 만들어진지 " + Period + "년이 됐습니다.");
		System.out.println(super.blog + "의 블로그입니다. 블로그는 만들어진지 " + super.Period + "년이 됐습니다.");
	}
}
// 결과
/*
MoCrazyKim의 블로그입니다. 블로그는 만들어 진지 6년이 됐습니다.
ChCrazyKim의 블로그입니다. 블로그는 만들어 진지 10년이 됐습니다.
MoCrazyKim의 블로그입니다. 블로그는 만들어 진지 6년이 됐습니다.
*/
```


<br>

#### Overloading-Overriding 성립조건
|구분|오버로딩|오버라이딩|
|:---:|:---:|:---:|
|메서드 이름|동일|동일|
|매개변수, 타입|다름|동일|
|리턴 타입|상관없음|동일|

<br>

```
오버로딩 
- 기존에 없는 새로운 메소드를 추가하는 것

오버라이딩 
- 상속받은 메소드를 재정의 하는 것
```


<br>

<div style="text-align: right">22-08-17</div>

-------

## Reference
- https://private.tistory.com/25
- https://hyoje420.tistory.com/14
- https://crazykim2.tistory.com/551
