package basic.regex;

import java.util.StringTokenizer;

public class ValidNumber
{

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

    public boolean isNumber_Regex(String s) {
        //"\\d{6,10}|"
        //^\d+(\.\d+)?
        String pattern = "^([\\+\\-])+\\d+(\\.\\d+)?";

        return s.matches(pattern);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
    /* test isNumber */
        ValidNumber sv = new ValidNumber();

        String[] s = {"0", "+0", "00", "1,000.0", "1,000.00"};

        for(int i=0; i<s.length; i++){
            System.out.print("\nInput  :" + s[i]);

            System.out.println("\tOutput :"+ sv.isNumber(s[i]) + " \t" + sv.isNumber_FiniteAutomata(s[i]) + " \t" + sv.isNumber_Regex(s[i]) );
        }
    }

}
