package d0814;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Solution {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		Stack<Character> stack;
		
		int strLen;
		String str;
		int result;
		
		for(int i = 0; i < 10; i++) {
			stack = new Stack<>();
			strLen = Integer.parseInt(br.readLine());
			str = br.readLine();
			result = 1;
			
			for(int j = 0; j < strLen; j ++) {
				char ch = str.charAt(j);
				
				if(ch == '(' || ch == '{' || ch == '[' || ch == '<') {
					stack.push(ch);
				} else {
					if(stack.empty()) {
						result = 0;
						break;
					}
					
					char peekCh = stack.peek();
					
					if(peekCh == '(' && ch == ')') {
						stack.pop();
					} else if(peekCh == '{' && ch == '}') {
						stack.pop();
					} else if(peekCh == '[' && ch == ']') {
						stack.pop();
					} else if(peekCh == '<' && ch == '>') {
						stack.pop();
					} else {
						result = 0;
						break;
					}
				}
			}
			
			sb.append("#")
			.append(i + 1)
			.append(" ")
			.append(result)
			.append("\n");
		}

		System.out.println(sb);
	}
}
