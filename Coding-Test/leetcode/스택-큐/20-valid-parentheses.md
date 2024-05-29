# 20. Valid Parentheses
- String 문자열으로 '()', '[]', '{}' 의 괄호가 주어지는데 열린 괄호는 동일한 유형의 괄호로 닫혀야하며 해당 여부를 판별하여 반환
    - https://leetcode.com/problems/valid-parentheses/description/

<br>

### 풀이 방법
- 우선 구현한 방법은 String.charAt()으로 하나씩 문자를 확인하고 열린 괄호는 스택에 넣고 닫힌 괄호는 스택에서 빼는 방법
    - 간단하게 int변수나 Map을 써서 열린 괄호를 기록하는 방법은 '({)}' 처럼 올바르지 않은 문자열을 올바르다고 판단할 수도 있음
- charAt()으로 쓸 필요없이 향상된 for문을 사용하여 String.toCharArray()로 Character 배열으로 변경
- 책에서의 풀이는 Map을 사용하여 해당 코드를 구현하였는데 개인적으로는 containsKey를 사용할 필요도 없는 문제라고 생각하여 풀이를 진행하지는 않았음 
- 책에서는 Stack 구현체로 ArrayDequeue를 사용하여 멀티코어에서 성능에 문제가 있는 Stack() 구현체를 사용하지 않는다고하며 코딩테스트는 싱글 스레드에 알맞은 자료형을 사용하되 멀티 환경에서는 스레드 안전한 자료형을 사용하는 것이 올바르다고함

<br>

### 첫번째 코드
```java
import java.util.Stack;

class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for(char ch : s.toCharArray()) {
            if(ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
                continue;
            }

            if (stack.isEmpty()) {
                return false;
            }

            char top = stack.pop();
            if(top == '(' && ch != ')'
            || top == '{' && ch != '}'
            || top == '(' && ch != ')') {
                return false;
            }
        }

        if(stack.isEmpty()) {
            return true;
        }

        return false;
    }
}
```

- runtime : 1ms
- memory : 42.72mb

<br>

### 다른 사람 코드

```java
import java.util.Stack;

class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>(); 
        for (char ch : s.toCharArray()) { 
            if (ch == '(')
                stack.push(')'); 
            else if (ch == '{')
                stack.push('}'); 
            else if (ch == '[') 
                stack.push(']'); 
            else if (stack.isEmpty() || stack.pop() != ch) 
                return false;
        }

        return stack.isEmpty();
    }
}
```

- runtime : 1ms
- memory : 41.44mb