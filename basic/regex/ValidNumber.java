package basic.regex;

import java.util.StringTokenizer;

/**
 * _https://www.lintcode.com/problem/417/
 * Validate if a given string is numeric.
 * 
 * Some examples:
 * "0" => true
 * " 0.1 " => true
 * "abc" => false
 * "1 a" => false
 * "2e10" => true
 * 
 * see <ValidNumber.doc>, a valid number is (from left to right):
 * 
 *   space ->  zero or more times 
 *   sign(+/-) ->  zero or once
 *   digits(0-9) ->  0 or 1234 or 1,234,  1234,123 not ok.   
 *   optional_fraction ->  . digits | epsilon
 *   optional_exponent ->  ((E | e) (+ | - | epsilon) digits) | epsilon
 *   
 * sign digits optional_fraction optional_exponent
 * [ ]*[+-]?([0-9]+|[0-9]{1,3}(\\,[0-9]{3})*)(\\.[0-9]+)?([Ee][+-]?[0-9]+)?[ ]*
 * [ ]*[+-]?((0|[1-9]{1}[0-9]*|[1-9]{1}[0-9]{0,2}(\\,[0-9]{3})*)(\\.[0-9]*)?|\\.[0-9]+)([Ee][+-]?[1-9]{1}[0-9]*)*[ ]*
 * 
 * 
 */

public class ValidNumber {

    private static final int ASCII_VALUE_OF_ZERO = 48; // '0' is 48, '1' is 49,
    private static final int ASCII_VALUE_OF_NINE = 57; // '9' is 57

    private boolean isValidDigit(char digit){
        return digit >= ASCII_VALUE_OF_ZERO && digit <= ASCII_VALUE_OF_NINE ; // '0' is 48, '1' is 49,'9' is 57
    }

    /*
     * Validate if a given string is numeric.
     *
     * Some examples:
     * "0" => true
     * " 0.1 " => true
     * "abc" => false
     * "1 a" => false
     * "2e10" => true
     *
     * It is intended for the problem statement to be ambiguous.
     * 1) check e
     * 2) check + and -
     * 3) check .
     * 4) check ,
     *
     */
    public boolean isNumber(String s) {

        s = s.trim();  // 1, omit the space

        int len = s.length();
        if(len == 0)
            return false;
      
    /* check 'e' */
        int iE = s.indexOf('e');
        if(iE == -1)   // there is no 'e'
            return isNumberWithoutE(s);

        return  isNumberWithoutE(s.substring(0, iE)) && isNumberWithoutE(s.substring(iE+1, len));
    }

    private boolean isNumberWithoutE(String s){

        int len = s.length();
        if(len == 0)
            return false;

        char firstDigit = s.charAt(0);
        if(firstDigit == '-' || firstDigit == '+'){
            if(len == 1)
                return false;
            else{
                s = s.substring(1);
                len = s.length();
                firstDigit = s.charAt(0);
            }
        }

        if(len == 1 && firstDigit =='.' )
            return false;
    
    /* check '.' */
        int iStop = s.indexOf('.');

        if(iStop == -1 ) // iStop == -1, there is no '.'
            return isNumberWithoutStop(s);
        //else if(iStop >= len -1) //'.' can't be the last one
        //  return false;
        //else if(iStop < len - 1)
        return isNumberWithoutStop(s.substring(0, iStop)) && isNumberWithoutStopAndComma(s.substring(iStop + 1, len));

        //return false;

    }


    /*
     * decimal can be 0, 00, 010
     */
    private boolean isNumberWithoutStopAndComma(String s){
        //if(s == null || s.length() == 0)
        //  return false;

        for(int i=0; i< s.length(); i++){
            if(!isValidDigit(s.charAt(i)))
                return false;
        }

        return true;
    }

    /*
     * It can be "1", "1,000"
     * It cann't be "00",
     *
     */
    private boolean isNumberWithoutStop(String s){
        int len = s.length();
        if(len == 0)
            return true;

        //char firstDigit = s.charAt(0);
        //if(firstDigit == '0'){ // it can be '0', it cann't be '00'
        //  if(len == 1)
        //    return true;
        //  else
        //    return false;
        //}

        StringTokenizer st = new StringTokenizer(s, ",");
        if(!isNumberWithoutStopAndComma(st.nextToken()))
            return false;

        while (st.hasMoreTokens()) {
            String tmp = st.nextToken();

            //System.out.println( "-1-" + (tmp.length() != 3) );
            //System.out.println( "-2-" + !isNumberWithoutStopAndComma(tmp) );

            if(tmp.length() != 3 || !isNumberWithoutStopAndComma(tmp))
                return false;
        }

        return true;
    }


    enum InputType {
        INVALID(0), // 0
        SPACE(1), // 1
        SIGN(2), // 2
        DIGIT(3), // 3
        DOT(4), // 4
        EXPONENT(5), // 5
        NUM_INPUTS(6); // 6

        int value;

        private InputType(int value) {
            this.value = value;
        }

    };

    /**
     * refer: ValidNumber.docx
     */
    public boolean isNumber_FiniteAutomata(String s) {

        int transitionTable[][] = {
                {-1, 0, 3, 1, 2, -1} // next states for state 0
                , {-1, 8, -1, 1, 4, 5} // next states for state 1
                , {-1, -1, -1, 4, -1, -1} // next states for state 2
                , {-1, -1, -1, 1, 2, -1} // next states for state 3
                , {-1, 8, -1, 4, -1, 5} // next states for state 4
                , {-1, -1, 6, 7, -1, -1} // next states for state 5
                , {-1, -1, -1, 7, -1, -1} // next states for state 6
                , {-1, 8, -1, 7, -1, -1} // next states for state 7
                , {-1, 8, -1, -1, -1, -1} // next states for state 8
        };

        int state = 0;
        int i = 0;
        char ch;
        while (i < s.length()) {
            InputType input = InputType.INVALID;  //0
            ch = s.charAt(i);

            if (ch == ' ') {
                input = InputType.SPACE;    //1
            }else if (ch == '+' || ch == '-') {
                input = InputType.SIGN;    //2
            }else if (isValidDigit(ch)) {
                input = InputType.DIGIT;   //3
            }else if (ch == '.') {
                input = InputType.DOT;    //4
            }else if (ch == 'e' || ch == 'E') {
                input = InputType.EXPONENT;   //5
            }

            // Get next state from current state and input symbol
            state = transitionTable[state][input.value];

            // Invalid input
            if (state == -1) {
                return false;
            }

            ++i;
        }
        // If the current state belongs to one of the accepting (final) states, then the number is valid
        return state == 1 || state == 4 || state == 7 || state == 8;

    }

    /**
     * 
     * @param s
     * @return 
     */
    public boolean isNumber_Regex(String s) {

        String pattern = "[ ]*[+-]?((0|[1-9]{1}[0-9]*|[1-9]{1}[0-9]{0,2}(\\,[0-9]{3})*)(\\.[0-9]*)?|\\.[0-9]+)([Ee][+-]?[1-9]{1}[0-9]*)*[ ]*";

        return s.matches(pattern);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        ValidNumber sv = new ValidNumber();

        String[] inputs = {"0", "+0", "00", "1,000.0", "1,000.00", "1000.0", "123,000.0", "1234,000.0", "10e5.4", "2e10", " 0.1 ", "0.", ".2", "."};
        boolean[] expects = {true, true, false, true, true, true, true, false, false, true, true, true, true, false};

        for(int i=0; i<inputs.length; i++){
            System.out.print(String.format("\nInput: %s,  expect: %b", inputs[i], expects[i] ));

            System.out.println("\nOutput :"+ sv.isNumber(inputs[i]) + " \t" + sv.isNumber_FiniteAutomata(inputs[i]) + " \t" + sv.isNumber_Regex(inputs[i]) );
        }
    }

}
