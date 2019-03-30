package fgafa.array.parenthese;

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

public class RemoveInvalidParenthesesII {

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


    /**
     * return all valid
     *
     * best solution, please see RemoveInvalidParentheseII.
     *
     */
    public List<String> removeInvalidParentheses_n(String s) {

        if (null == s || s.isEmpty()) {
            return new ArrayList<>();
        }

        Set<String> result = new HashSet<>();
        helper(s.toCharArray(), 0, new char[]{'(',')'}, result);


        return new ArrayList<>(result);
    }

    private void helper(char[] chars, int l, char[] pair, Set<String> result){

        int count = 0;
        for(int i = l; i < chars.length; i++){
            char c = chars[i];

            if(c == pair[1] && count == 0){
                char[] newChars = new char[chars.length - 1];

                for(int j = 0; j <= i; j++){
                    //if(chars[j] == pair[1] && ( j == 0 || (j > 0 && chars[j] != chars[j - 1]))){
                    if(chars[j] == pair[1] && ( j == 0 || chars[j] != chars[j - 1])){
                        System.arraycopy(chars, 0, newChars, 0, j);
                        System.arraycopy(chars, j + 1, newChars, j, chars.length - j - 1);

                        helper(newChars, i, pair, result);
                    }
                }
                return;
            }

            if( c == pair[0]){
                count++;
            }else if( c == pair[1]){
                count--;
            }
        }

        //if(count >= 0){
        reverse(chars);
        if(pair[0] == '('){
            helper(chars, 0, new char[]{')','('}, result);
        }else{
            result.add(String.valueOf(chars));
        }
        //}

    }

    private void reverse(char[] chars){
        char tmp;
        for(int l = 0, r = chars.length - 1; l < r; l++, r--){
            tmp = chars[l];
            chars[l] = chars[r];
            chars[r] = tmp;
        }
    }

    public List<String> removeInvalidParentheses_x(String s) {
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
        RemoveInvalidParenthesesII sv = new RemoveInvalidParenthesesII();
        
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
            
            System.out.println(sv.removeInvalidParentheses(input[i]));
            System.out.println(sv.removeInvalidParentheses_n(input[i]));
            System.out.println(sv.removeInvalidParentheses_x(input[i]));
        }
    }
}
