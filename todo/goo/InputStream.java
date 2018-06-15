package fgafa.todo.goo;

import java.util.Stack;

/**
 * _http://www.jiuzhang.com/solution/input-stream
 */

public class InputStream {

    public boolean inputStream(String inputA, String inputB){

        Stack<Character> stackA = new Stack<>();
        for(char c : inputA.toCharArray()){
            if(c == '<'){
                stackA.pop();
            }else{
                stackA.push(c);
            }
        }

        Stack<Character> stackB = new Stack<>();
        for(char c : inputB.toCharArray()){
            if(c == '<'){
                stackB.pop();
            }else{
                stackB.push(c);
            }
        }

        if(stackA.size() != stackB.size()){
            return false;
        }

        while(!stackA.isEmpty()){
            if(stackA.pop() != stackB.pop()){
                return false;
            }
        }

        return true;
    }

}
