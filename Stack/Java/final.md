# final

### final
- 자바에서는 불변성을 확보할 수 있도록 final 키워드를 제공함
- final 키워드는 어떤 것을 제한하는 의미를 가지는 공통적인 성격을 가지고 있음
- final은 변수(variable), 메서드(method), 또는 클래스(class)에 사용될 수 있고 어느곳에서 사용되냐에 따라 다른 의미를 가짐
    - 클래스나 변수에 final을 붙이면 처음 정의된 상태가 변하지 않는 것을 의미

<br>

### 클래스(class)에서의 final
- final 키워드를 클래스에 붙이면 상속 불가능한 클래스가 됨
- 다른 클래스에서 상속하여 재정의를 할 수 없는 것
- 대표적인 클래스로 Integer와 같은 랩퍼(Wrapper) 클래스가 있음. 클래스 설계시 재 정의 여부를 생각해서 재정의 불가능하게 사용하고 싶다면 유지보수 측면에서 final을 사용하는 것이 좋음

<br>

### 인자(arguments)에서의 final
- final 키워드를 인자에 붙이면 메소드 내에서 변경이 불가능함
- final int number를 인자로 선언하면 해당 number를 읽을 수는 있지만 number 값을 변경하려고 하면 컴파일에러가 발생

<br>

### 메서드(method)에서의 final
- final 키워드를 메서드에 붙이면 override를 제한
- 클래스를 상속하게 되면 해당 클래스의 protected, public 접근 제한자를 가진 메서드를 상속해서 재구현을 할 수 있는데 상속 받은 클래스에서 해당 메서드를 수정해서 사용하지 못하도록 함

<br>

### 변수(variable)에서의 final
- final 키워드를 변수에 붙이면 이 변수는 수정할 수 없다는 의미를 가짐
- 수정될 수 없기 때문에 초기화 값을 필수적이고 만약 객체안의 변수라면 생성자, static 블럭을 통한 초기화까지는 허용됨
- 수정 할 수 없다는 범위는 그 변수의 값에 한정하여 다른 객체를 참조하거나 참조하는 객체의 내부의 값은 변경될 수 있음
    - 기본형 변수라면 값을 변경하지 못하지만 참조형 변수라면 가르키는 객체를 변경하지 못하는 것이지 객체 내부의 값은 변경이 가능함

<br>

### 주의할 점
- final 변수는 초기화 이후 변경이 발생하지 않도록 만드는데 List에 final을 선언하면 List 변수의 변경은 불가능하나, List 내부에 있는 변수들은 변경이 가능하여 문자열을 계속 추가가 가능함
- Effective final은 Java8에서 추가된 기능으로 final이 붙지 않은 변수의 값이 변경되지 않는다면 그 변수를 Effectively final이라고 함. 컴파일러가 final로 취급함

<br>

<div style="text-align: right">22-11-29</div>

-------

## Reference
- https://sabarada.tistory.com/148
- https://sudo-minz.tistory.com/135