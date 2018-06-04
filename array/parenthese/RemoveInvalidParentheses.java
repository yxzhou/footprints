package fgafa.array.parenthese;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fgafa.util.Misc;

/**
 * 
 * Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.

    Note: The input string may contain letters other than the parentheses ( and ).
    
    Examples:
    "()())()" -> ["()()()", "(())()"]
    "(a)())()" -> ["(a)()()", "(a())()"]
    ")(" -> [""]
 *
 */

public class RemoveInvalidParentheses {

    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();

        if(null == s || 0 == s.length()){
            result.add("");
            return result;
        }

        Set<String> set = new HashSet<String>();

        removeInvalidParentheses(s, s.toCharArray(), 0, set);
        result.addAll(set);

        return result;
    }

    private void removeInvalidParentheses(String original, char[] s, int start, Set<String> set){

        int count = 0;

        for(int i = start; i < s.length; i++){
            if(s[i] == '('){
                count++;
            }else if(s[i] == ')'){
                count--;
            }

            if(count == -1){

                for(int j = 0; j <= i; j++){
                    if(s[j] == ')' && (j == 0 || s[j - 1] != ')')){
                        s[j] = '#';

                        removeInvalidParentheses(original, s, i + 1, set);

                        s[j] = ')';
                    }
                }
                
                return;
            }
        }
        
        if(count > 0){
            removeInvalidLeftParentheses(original, s, start, set, count);
        }else{//count == 0
            set.add(build(original, s));
        }
    }
    
    private void removeInvalidLeftParentheses(String original, char[] s, int start, Set<String> set, int count){
        if(count == 0){
            if(isValidParentheses(s, 0)){
                set.add(build(original, s));
            }
        }
            
        for(int j = start; j < s.length; j++){
            if(s[j] == '('){
                s[j] = '#';
                
                removeInvalidLeftParentheses(original, s, j + 1, set, count - 1);
                            
                s[j] = '(';
            }
        }
    }
    
    private boolean isValidParentheses(char[] s, int start){
        int count = 0;
        
        for(int i = start; i < s.length; i++){
            if(s[i] == '('){
                count++;
            }else if(s[i] == ')'){
                count--;
            }
            
            if(count < 0){
                return false;
            }
        }
        
        return count == 0;
    }

    private String build(String original, char[] s){
        StringBuilder tmp = new StringBuilder();
        for(int i = 0; i < s.length; i++){
            if(s[i] != '#' || original.charAt(i) == '#'){
                tmp.append(s[i]);
            }
        }

        return tmp.toString();
    }

    public static void main(String[] args){
        RemoveInvalidParentheses sv = new RemoveInvalidParentheses();
        
        String[] input = {
                    null,       //""
                    "",         //""
                    "(",        //""
                    ")()(",     //"()"
                    "()())()",  // ["()()()", "(())()"]
                    "(a)())()", // ["(a)()()", "(a())()"]
                    ")(",       //[""]
                    "())())",   // "()()", "(())"
                    "()())())", // "()()()","()(())","(()())","(())()
                    "())(()"    // "()()"
        };
        
        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("\nInput: %s, Output: ", input[i]));
            
            Misc.printArrayList(sv.removeInvalidParentheses(input[i]));
        }
    }
}
