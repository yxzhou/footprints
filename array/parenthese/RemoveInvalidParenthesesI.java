package fgafa.array.parenthese;

/**
 *
 * Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.

 Note: The input string may contain letters other than the parentheses ( and ).

 Examples:
 "()())()" -> anyone of ["()()()", "(())()"]
 "(a)())()" -> anyone of ["(a)()()", "(a())()"]
 ")(" -> [""]
 *
 */

public class RemoveInvalidParenthesesI {

    /**
     *  return any one valid
     */
    public String removeInvalidParentheses_anyone(String s) {
        if(null == s || s.isEmpty()){
            return s;
        }

        char[] chars = s.toCharArray();

        int length = s.length();
        char[] mid = new char[length];
        int count = 0;

        //check from left to right
        int r = 0;
        for(int i = 0; i < length; i++){
            char c = chars[i];
            if( c == ')'){
                if(count > 0){
                    count--;

                    mid[r++] = c;
                }
            }else{
                if( c == '('){
                    count++;
                }

                mid[r++] = c;
            }
        }

        //check from right to left
        char[] result = new char[r];
        int l = r - 1;
        count = 0;
        for( int i = l ; i >= 0; i--){
            char c = mid[i];
            if( c == '('){
                if(count > 0){
                    count--;

                    result[l--] = c;
                }
            }else {
                if( c == ')'){
                    count++;
                }

                result[l--] = c;
            }
        }

        return String.valueOf(result, l + 1, r - l - 1);
    }


    public String removeInvalidParentheses_anyone_n(String s) {
        if (null == s || s.isEmpty()) {
            return s;
        }

        StringBuilder mid = helper(s, new char[]{'(',')'});
        StringBuilder result = helper(mid.reverse().toString(), new char[]{')','('});

        return result.reverse().toString();
    }

    private StringBuilder helper(String s, char[] pair){
        StringBuilder sb = new StringBuilder(s.length());

        int count = 0;
        char[] chars = s.toCharArray();
        for(int i = 0; i < s.length(); i++){
            char c = chars[i];

            if(c == pair[1] && count == 0){
                continue;
            }

            if( c == pair[0]){
                count++;
            }else if( c == pair[1]){
                count--;
            }

            sb.append(c);
        }

        return sb;
    }


    public String removeInvalidParentheses_anyone_n2(String s) {
        if (null == s || s.isEmpty()) {
            return s;
        }

        char[] mid = helper(s.toCharArray(), s.length(), new char[]{'(',')'});
        reverse(mid);
        char[] result = helper(mid, mid.length, new char[]{')','('});
        reverse(result);

        return String.valueOf(result);
    }

    private char[] helper(char[] chars, int size, char[] pair){
        char[] mid = new char[size];

        int count = 0;
        int k = 0;
        for(int i = 0; i < size; i++){
            char c = chars[i];

            if(c == pair[1] && count == 0){
                continue;
            }

            if( c == pair[0]){
                count++;
            }else if( c == pair[1]){
                count--;
            }

            mid[k++] = c;
        }

        char[] result = new char[k];
        System.arraycopy(mid, 0, result, 0, k);
        return result;
    }

    private void reverse(char[] chars){
        char tmp;
        for(int l = 0, r = chars.length - 1; l < r; l++, r--){
            tmp = chars[l];
            chars[l] = chars[r];
            chars[r] = tmp;
        }
    }


    public static void main(String[] args){
        RemoveInvalidParenthesesI sv = new RemoveInvalidParenthesesI();

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

//        List<String> tList = Arrays.asList(new String[]{"t1", "t2"});
//        System.out.println(tList);

        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("\nInput: %s, Output: ", input[i]));

            System.out.println(sv.removeInvalidParentheses_anyone(input[i]));

            System.out.println(sv.removeInvalidParentheses_anyone_n(input[i]));

            System.out.println(sv.removeInvalidParentheses_anyone_n2(input[i]));

        }
    }
}
