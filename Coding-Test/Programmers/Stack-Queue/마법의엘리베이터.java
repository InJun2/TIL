import java.util.Stack;

class Solution {
    public int solution(int storey) {
        Stack<Integer> stack = new Stack<>();
        String numStr = Integer.toString(storey);
        int answer = 0;

        for (char digit : numStr.toCharArray()) {
            int currentDigit = digit - '0';
            stack.push(currentDigit);
        }
        
        while(stack.size() != 1) {
            int digit = stack.pop();
            
            if(digit > 5) {
                answer += 10 - digit;
                stack.push(stack.pop() + 1);
            }else if (digit == 5 && stack.peek() >= 5) {
                answer += 5;
                stack.push(stack.pop() + 1);
            }else {
                answer += digit;
            }
        }
        
        int lastNumber = stack.pop();
        
        if(lastNumber > 5) {
            answer += 10 - lastNumber + 1;
        } else {
            answer += lastNumber;
        }
        
        return answer;
    }
}