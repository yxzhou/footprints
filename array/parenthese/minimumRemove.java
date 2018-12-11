package fgafa.array.parenthese;

import java.util.Stack;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Given a string of parentheses, write a function to compute the minimum number of parentheses to be removed to make the string valid (i.e. each open parenthesis is eventually closed).
 *
 * For example,
 *   Given the string "()())()", you should return 1.
 *   Given the string ")(", you should return 2, since we must remove all of them.
 *
 * Tags: goo
 *
 */

public class minimumRemove {

    public int minimumRemove(String input){
        if(null == input || input.length() < 1){
            return 0;
        }

        //Stack<Integer> stack = new Stack<>();
        int remove = 0;
        int count = 0;
        for(char c : input.toCharArray()){
            if(c == '('){
                count++;
            }else if(c == ')'){
                if(count > 0){
                    count--;
                }else{
                    remove++;
                }

            }else{
                throw new IllegalArgumentException("Found special character:" + c);
            }
        }

        return remove + count;
    }

    @Test public void test(){

        Assert.assertEquals(1, minimumRemove("()())()"));
        Assert.assertEquals(2, minimumRemove(")("));
        Assert.assertEquals(2, minimumRemove(")())()"));
        Assert.assertEquals(2, minimumRemove("()())()(()"));

    }

}
