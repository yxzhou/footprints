package stringmatching;

import java.util.Stack;

/**
 * _http://www.jiuzhang.com/solution/input-stream
 * _https://www.lintcode.com/problem/823
 * 
 * 
 * Give two input stream inputA and inputB, which have Backspace. If the final result of the two input streams are
 * equal, output YES, otherwise output NO.
 *
 * Input characters include only lowercase letters and '<' The length of input stream does not exceed 10000.
 * 
 * Example1
 * Input:  inputA = "abcde<<" and inputB = "abcd<e<"
 * Output: "YES"
 * Explanation: The final result of inputA and inputB is "abc", so return "YES".
 * 
 * Example2
 * Input:  inputA = "a<<bc" and inputB = "abc<"
 * Output: "NO"
 * Explanation: The final result of inputA is "bc", and the final result of inputB is "ab", so return "NO".
 * 
 */

public class InputStream {

    public boolean inputStream(String inputA, String inputB) {

        Stack<Character> stackA = new Stack<>();
        for (char c : inputA.toCharArray()) {
            if (c == '<') {
                stackA.pop();
            } else {
                stackA.push(c);
            }
        }

        Stack<Character> stackB = new Stack<>();
        for (char c : inputB.toCharArray()) {
            if (c == '<') {
                stackB.pop();
            } else {
                stackB.push(c);
            }
        }

        if (stackA.size() != stackB.size()) {
            return false;
        }

        while (!stackA.isEmpty()) {
            if (stackA.pop() != stackB.pop()) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param inputA: Input stream A
     * @param inputB: Input stream B
     * @return: The answer
     */
    public String inputStream_n(String inputA, String inputB) {
        char[] arrA = new char[inputA.length()];
        int i = build(inputA, arrA); 

        char[] arrB = new char[inputB.length()];
        int j = build(inputB, arrB);

        //return i == j && Arrays.compare(arrA, 0, i, arrB, 0, j);
        if(i != j){
            return "NO";
        }
            
        for(int k = 0; k < i; k++){
            if(arrA[k] != arrB[k]){
                return "NO";
            }
        }

        return "YES";
    }

    private int build(String s, char[] arr){
        int i = 0;
        char c;
        for(int j = 0, n = arr.length; j < n; j++){
            c = s.charAt(j);
            if( c == '<'){
                i = Math.max(i - 1, 0);
            }else{
                arr[i++] = c;
            }
        }

        return i;
    }
    
}
