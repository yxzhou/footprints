/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package math.expression;

import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/725/
 * 
 * Given a boolean expression with following symbols.
 *
 * Symbols:
 *   'T' ---> true 
 *   'F' ---> false 
 * 
 * And following operators filled between symbols
 * Operators 
 *   & ---> boolean AND 
 *   | ---> boolean OR 
 *   ^ ---> boolean XOR 
 * 
 * Count the number of ways we can parenthesize the expression so that the value of expression evaluates to true.
 *
 * Let the input be in form of two arrays one contains the symbols (T and F) in order and other contains operators (&, |
 * and ^}
 *
 * Example 1:
 * Input： ['T', 'F', 'T']，['^', '&'] 
 * Output：2 
 * Explanation: The given expression is "T ^ F & T", it evaluates true, in two ways "((T ^ F) & T)" and "(T ^ (F & T))" 
 * 
 * Example 2:
 * Input：['T', 'F', 'F']，['^', '|'] 
 * Output：2 
 * Explanation: The given expression is "T ^ F | F", it evaluates true, in two ways "( (T ^ F) | F )" and "( T ^ (F | F) )".
 * 
 * Thoughts:
 * symb = {s_0, s_1 ... s_(m-1) } oper = {p_0, p_1 .. p_(n-1) }  n = m - 1
 *
 * Define f(s, 0, m, p) as all possible Expression, 
 * Define t(s, 0, m, p) as all possible Expression that is tree,  
 * combination with parenthesize the expression:
 *   [s_0 ... s_k] pk [ s_(k+1) ... s_m ] k is [0, m)
 *
 *  
 * 
 */
public class BooleanParenthesization {
    /**
     * @param symb: the array of symbols
     * @param oper: the array of operators
     * @return: the number of ways
     */
    public int countParenth(String symb, String oper) {
        
        int m = symb.length();
        
        int[][] f = new int[m][m]; //all possible Expression
        int[][] t = new int[m][m]; //all possible Expression that is tree

        for(int i = 0; i < m; i++){
            f[i][i] = 1;

            t[i][i] = (symb.charAt(i) == 'T'? 1 : 0);
        }

        for(int w = 1; w < m; w++ ){
            for(int start = 0, end = start + w; end < m; start++, end++){
                for(int k = start; k < end; k++){

                    f[start][end] += f[start][k] * f[k + 1][end];

                    switch(oper.charAt(k)){
                        case '&':
                            t[start][end] += t[start][k] * t[k + 1][end];
                            break;
                        case '|':
                            t[start][end] += t[start][k] * f[k + 1][end] + f[start][k] * t[k + 1][end] - t[start][k] * t[k + 1][end];
                            break;
                        default: // ^
                            t[start][end] += t[start][k] * (f[k + 1][end] - t[k + 1][end]) + (f[start][k] - t[start][k]) * t[k + 1][end];
                            break;
                    }
            
                }
            }
        }

        return t[0][m - 1];
    }
    
    public static void main(String[] args){
        String[][] inputs = {
            {"TFT", "^&", "2"},
            {"TFF", "^|", "2"},
            {"FTTFFFTTTT", "&|^&|&^^^", "1857"}
        };
        
        BooleanParenthesization sv = new BooleanParenthesization();
        
        for(String[] input : inputs){
            System.out.println(String.format("\nSymb: %s, Oper: %s", input[0], input[1]));
            
            Assert.assertEquals(Integer.parseInt(input[2]), sv.countParenth(input[0], input[1]));
        }
    }
}
