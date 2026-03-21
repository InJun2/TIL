class Solution {
    int n = 0;
    int zero = 0;
    
    public int[] solution(String s) {
        int[] answer = new int[2];
        
        find(s);
        
        answer[0] = n;
        answer[1] = zero;
        
        return answer;
    }
    
    void find(String s) {
        if(s.equals("1")) {
            return;
        }
        
        for(int i=0; i < s.length(); i++) {
            if(s.charAt(i)=='0') {
                zero++;
            }
        }
        
        s = s.replaceAll("0", "");
        s = Integer.toBinaryString(s.length());
        n ++;
        
        find(s);
    }
}