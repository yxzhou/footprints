package fgafa.datastructure.stack;
import java.util.Stack;

/**
 * 
 * Suppose that an intermixed sequence of (stack) push and pop operations are performed. The pushes push the integers 0 through 9 in order; the pops print out the return value. Which of the following sequence(s) could not occur?
    (a)  4 3 2 1 0 9 8 7 6 5
    
    (b)  4 6 8 7 5 3 2 9 0 1
    
    (c)  2 5 6 7 4 8 9 3 1 0
    
    (d)  4 3 2 1 0 5 6 7 8 9
    
    (e)  1 2 3 4 5 6 9 8 7 0
    
    (f)  0 4 6 5 3 8 1 7 2 9
    
    (g)  1 4 7 9 8 6 5 3 0 2
    
    (h)  2 1 4 3 6 5 8 7 9 0
    Answer: (b), (f), and (g).
 *
 */

public class validStackOutput {
    
    public boolean valid(String output, int n){
        //check
        if( null == output || n < 0 || output.length() != n){
            return false;
        }
        
        Stack<Integer> stack = new Stack<Integer>();
        int index = 0; // the index of output
        int i; //the int on the index of output
        int count = 0;
        
        while(index < n && !stack.isEmpty()){
            i = output.charAt(index) - '0';

            while((stack.isEmpty() || stack.peek() < i) && count < n){
                stack.push(count);
                count++;
            }

            if(stack.peek() == i){
                stack.pop();
                index++;
            }else{
                return false;
            }

        }

        return true;
    }
    
    public static void main(String[] args){
        validStackOutput sv = new validStackOutput();
        int n = 10;
        String[] ss = {
                    "4 3 2 1 0 9 8 7 6 5",
                    "4 6 8 7 5 3 2 9 0 1",
                    "2 5 6 7 4 8 9 3 1 0",
                    "4 3 2 1 0 5 6 7 8 9",
                    "1 2 3 4 5 6 9 8 7 0",
                    "0 4 6 5 3 8 1 7 2 9",
                    "1 4 7 9 8 6 5 3 0 2",
                    "2 1 4 3 6 5 8 7 9 0",
        };
        
        for(String s : ss){
            System.out.println(String.format("%s,  %b", s, sv.valid(s, n)));
        }
    }
}
