package math.expression;

/**
 * Solve a given equation and return the value of x in the form of string "x=#value".
 * The equation contains only '+', '-' operation, the variable x and its coefficient.
 *
 * If there is no solution for the equation, return "No solution".
 * If there are infinite solutions for the equation, return "Infinite solutions".
 * If there is exactly one solution for the equation, we ensure that the value of x is an integer.
 *
 * Example 1:
 * Input: "x+5-3+x=6+x-2"
 * Output: "x=2"
 *
 * Example 2:
 * Input: "x=x"
 * Output: "Infinite solutions"
 *
 * Example 3:
 * Input: "2x=x"
 * Output: "x=0"
 *
 * Example 4:
 * Input: "2x+3x-6x=x+2"
 * Output: "x=-1"
 *
 * Example 5:
 * Input: "x=x+2"
 * Output: "No solution"
 *
 */

public class SolveTheEquation {

    public String solveEquation(String equation) {
        if(null == equation || equation.isEmpty()){
            return "Infinite solutions";
        }

        // scan equation, get "nx + m = 0"
        int n = 0;
        int m = 0;
        boolean flag = true; // true, when left side of =; false, when right side of =

        int num = 0;
        boolean sign = true;

        int length = equation.length();
        char[] chars = equation.toCharArray();
        char c;
        for(int i = 0; i < length; i++){
            c = chars[i];

            if(c >= '0' && c <= '9'){
                num = num * 10 + (c - '0');
            }else if(c == 'x'){
                if(i > 0 && chars[i - 1] == '0' && num == 0){ // 1+0x

                }else{
                    n += Math.max(num, 1) * (flag? 1 : -1) * (sign? 1 : -1);
                }

                sign = true;
                num = 0;
            }else{ // + - =
                m += num * (flag? 1 : -1) * (sign? 1 : -1);

                sign = true;
                if(c == '-'){
                    sign = false;
                } else if(c == '='){
                    flag = false;
                }

                num = 0;
            }
        }

        //
        m += num * (flag? 1 : -1) * (sign? 1 : -1);

        if(n == 0){
            if(m == 0){               //0x + 0 = 0
                return "Infinite solutions";
            }else{                    //0x + 2 = 0
                return "No solution";
            }
        }else{
            if(m == 0){               //2x + 0 = 0
                return "x=0";
            }else if(n == 1){         // x + 2 = 0
                return "x=" + (-m);
            }else if(m % n == 0){     // 2x + 4 = 0
                return "x=" + (-m/n);
            }else{                    // 2x + 3 = 0
                return "x=" + (-(double)m / n) ;
            }
        }
    }

}
