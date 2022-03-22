package math.expression;

import java.util.*;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/1289
 * 
 * Given a chemical formula (given as a string), return the count of each atom.
 * 
 * An atomic element always starts with an uppercase character, then zero or more lowercase letters, representing the
 * name.
 *
 * 1 or more digits representing the count of that element may follow if the count is greater than 1. If the count is 1,
 * no digits will follow. For example, H2O and H2O2 are possible, but H1O2 is impossible.
 *
 * Two formulas concatenated together produce another formula. For example, H2O2He3Mg4 is also a formula.
 *
 * A formula placed in parentheses, and a count (optionally added) is also a formula. For example, (H2O2) and (H2O2)3
 * are formulas.
 *
 * Given a formula, output the count of all elements as a string in the following form: the first name (in sorted
 * order), followed by its count (if that count is more than 1), followed by the second name (in sorted order), followed
 * by its count (if that count is more than 1), and so on.
 *
 * Notes:
 * All atom names consist of lowercase letters, except for the first character which is uppercase. 
 * The length of formula
 * will be in the range [1, 1000]. 
 * Formula will only consist of letters, digits, and round parentheses, and is a valid formula as defined in the problem.
 *
 * Example 1:
 * Input: formula = "H2O" 
 * Output: "H2O" 
 * Explanation: The count of elements are {'H': 2, 'O': 1}. 
 * 
 * Example 2:
 * Input: formula = "Mg(OH)2" 
 * Output: "H2MgO2" 
 * Explanation: The count of elements are {'H': 2, 'Mg': 1, 'O': 2}. 
 * 
 * Example 3:
 * Input: formula = "K4(ON(SO3)2)2" 
 * Output: "K4N2O14S4" 
 * Explanation: The count of elements are {'K': 4, 'N': 2, 'O': 14, 'S': 4}.
 * 
 * Thoughts:
 *   atom_expression ->  [A-Z][a-z]*           the first character is uppercase, following with zero or more lowercase character
 *   number_expresson -> [2-9]?|[1-9][0-9]+    none, or 1 digit[2 - 9], or multiple digit that first digit is [1-9]
 *   optional_number -> [2-9]|[1-9][0-9]+      1 digit[2 - 9], or multiple digit that first digit is [1-9]
 *   atom_number -> (atom_expression number_expresson)+
 *
 *   optional_parentheses -> atom_number|\\(optional_parentheses\\)optional_number
 * 
 */

public class CountOfAtoms {

    /**
     * Define n as the length of formula
     *  Time Complexity O(n), Space O(n)
     * 
     * @param formula: a string
     * @return the count of each atom.
     */
    public String countOfAtoms(String formula) {
        if(formula == null){
            return "";
        }

        formula += ")"; //terminal character

        Stack<TreeMap<String, Integer>> stack = new Stack<>(); 
        TreeMap<String, Integer> curr = new TreeMap<>();

        char c;
        for(int i = 0, n = formula.length(); i < n; ){
            c = formula.charAt(i);

            if(c == '('){          
                stack.add(curr);
                curr = new TreeMap<>();     
                
                i++;
            }else if(c == ')'){
                
                //the pattern is '*)number', get the number
                int num = 0;
                for(i++; i < n && Character.isDigit((c = formula.charAt(i))); i++ ){
                    num = num * 10 + (c - '0');
                }
            
                if(num == 0){ //it's the last ')'
                    break;
                }
                
                //merge with stack.top
                TreeMap<String, Integer> top = stack.pop();
                for(String key : curr.keySet()){                    
                    top.put(key, top.getOrDefault(key, 0) + curr.get(key) * num );
                }
                curr = top;
            }else{// if(Character.isUpperCase(c)

                //the patter is [A-Z][a-z]*([2-9]?|[1-9][0-9]+)                
                String atom = "" + c;
                for( i++; i < n && Character.isLowerCase( (c = formula.charAt(i))); i++ ){
                    atom += c;
                }
                
                int num = 0;
                for( ; i < n && Character.isDigit( (c = formula.charAt(i))); i++ ){
                    num = num * 10 + (c - '0');
                }

                curr.put(atom, curr.getOrDefault(atom, 0) + Math.max(num, 1));

            } // else throw IllegalArgumentException
        }

        StringBuilder result = new StringBuilder();
        for(String key : curr.keySet()){
            result.append(key);
            if(curr.get(key) > 1){
                result.append(curr.get(key));
            }
        }

        return result.toString();
    }

    public static void main(String[] args){

        CountOfAtoms sv = new CountOfAtoms();

        String[][] inputs = {
            //{formula, expect}
            {"H2O","H2O"},
            {"Mg(OH)2","H2MgO2"},
            {
                "A(B(C5)6)7", 
                "AB7C210"
            },
            {
                "A(B(C(D4)5)6)7", 
                "AB7C42D840"
            },
            {
                "A(B(C(D(E(F2)3)4)5)6)7", 
                "AB7C42D210E840F5040"
            },
            {
                "AB(CD(EF2)3)5", 
                "ABC5D5E15F30"
            },
            {
                "K4(ON(SO3)2)2", 
                "K4N2O14S4"
            },
            {
                "((N42)24(OB40Li30CHe3O48LiNN26)33(C12Li48N30H13HBe31)21(BHN30Li26BCBe47N40)15(H5)16)14", 
                "B18900Be18984C4200H5446He1386Li33894N50106O22638"
            }
        };

        for(String[] input : inputs){
            System.out.println("\n Input: " + input[0]);
            
            Assert.assertEquals(input[1], sv.countOfAtoms(input[0]));
        }

    }
}
