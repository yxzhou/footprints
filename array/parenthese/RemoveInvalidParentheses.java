package fgafa.array.parenthese;

import fgafa.util.Misc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<String> removeInvalidParentheses_2(String s) {
        List<String> result = new ArrayList<>();

        if(null == s || 0 == s.length()){
            result.add("");
            return result;
        }

        helper(s, result, 0, 0, new char[]{'(', ')'});

        return result;
    }

    public void helper(String s, List<String> result, int last_i, int last_j,  char[] par) {
        for (int counter = 0, i = last_i; i < s.length(); ++i) {
            if (s.charAt(i) == par[0]){
                counter++;
            } else if (s.charAt(i) == par[1]){
                counter--;
            }

            if (counter >= 0) continue;

            for (int j = last_j; j <= i; ++j) {
                if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1])) {
                    helper(s.substring(0, j) + s.substring(j + 1, s.length()), result, i, j, par);
                }
            }

            return;
        }

        String reversed = new StringBuilder(s).reverse().toString();
        if (par[0] == '(') {// finished left to right
            helper(reversed, result, 0, 0, new char[]{')', '('});
        } else { // finished right to left
            result.add(reversed);
        }
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

            Misc.printArrayList(sv.removeInvalidParentheses_2(input[i]));
        }
    }
}
