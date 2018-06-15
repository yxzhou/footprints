package fgafa.basic.regex;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Problem:
 *  以字符串形式给出一个化学分子式，返回每个原子的计数。
 *  原子元素始终以大写字母开始，然后是零个或多个小写字母，代表名称。
 *  如果个数大于1，则可以跟随1个或多个代表该元素的个数。如果个数为1，则不会有数字。 例如，存在H2O和H2O2，但是不存在H1O2。
 *  两个式子连接在一起产生另一个式子。 例如，H2O2He3Mg4也是一个式子。
 *  式子可以放在括号内，并可为整个带括号的式子添加个数。 例如，(H2O2)和(H2O2)3是合理的。
 *  给定一个分子式，将所有元素的个数输出为以下形式的字符串：第一个原子名称（按排序顺序），后面是计数（如果该计数大于1）；
 *  然后是第二个原子名称（按排序顺序 ），然后计数（如果该计数大于1），依此类推……
 *
 * Example:
 *  input "H2O"
 *  Output "H2O"
 *
 *  input "Mg(OH)2"
 *  output "H2MgO2"
 *
 *  Input "K4(ON(SO3)2)2"
 *  output "K4N2O14S4"
 */

public class CountOfAtoms {

    /**
     *  Time O(n * n),
     */
    public String countOfAtoms_scan(String formula){
        StringBuilder result = new StringBuilder();

        Map<String, Integer> counts = countOfAtoms_scan(formula, 0, formula.length());

        SortedSet<String> sortedNames = new TreeSet<>(counts.keySet());
        for(String name : sortedNames){
            result.append(name);
            if(1 != counts.get(name)){
                result.append(counts.get(name));
            }
        }

        return result.toString();
    }

    private Map<String, Integer> countOfAtoms_scan(String formula, int start, int end){
        Map<String, Integer> result = new HashMap<>();

        String name;
        int num;
        for(int i = start, j = start; i < end; ){
            char c = formula.charAt(i);
            if(c == '('){
                //j = formula.indexOf(')', i);  //this is wrong
                j = locateParenthesis(formula, i);
                Map<String, Integer> subResult = countOfAtoms_scan(formula, i + 1, j);

                i = j + 1;
                j = locateNumber(formula, i);
                if(i == j){
                    num = 1;
                }else{
                    num = Integer.valueOf(formula.substring(i, j));
                }

                for(Map.Entry<String, Integer> entry : subResult.entrySet()){
                    if(result.containsKey(entry.getKey())){
                        result.put(entry.getKey(), entry.getValue() * num + result.get(entry.getKey()));
                    }else{
                        result.put(entry.getKey(), entry.getValue() * num );
                    }
                }

            }else{
                j = locateName(formula, i);
                name = formula.substring(i, j);

                i = j;
                j = locateNumber(formula, i);
                if(i == j){
                    num = 1;
                }else{
                    num = Integer.valueOf(formula.substring(i, j));
                }

                if(result.containsKey(name)){
                    result.put(name, num + result.get(name));
                }else{
                    result.put(name, num);
                }
            }

            i = j;
        }

        return result;
    }

    /**
     *  Time O(n ),
     */
    public String countOfAtoms_scan_n(String formula){
        StringBuilder result = new StringBuilder();

        Map<String, Integer> counts = new HashMap<>();
        int end = countOfAtoms(formula, 0, counts);

        if(end != formula.length()){
            throw new IllegalArgumentException("");
        }

        SortedSet<String> sortedNames = new TreeSet<>(counts.keySet());
        for(String name : sortedNames){
            result.append(name);
            if(1 != counts.get(name)){
                result.append(counts.get(name));
            }
        }

        return result.toString();
    }

    private int countOfAtoms(String formula, int start, Map<String, Integer> result){
        int i = start;

        String name;
        int num;
        for(int j = i; i < formula.length(); i = j){
            char c = formula.charAt(i);
            if(c == '('){
                Map<String, Integer> subResult = new HashMap<>();
                j = countOfAtoms(formula, i + 1, subResult);

                i = j + 1;
                j = locateNumber(formula, i);
                num = i == j ? 1 : Integer.valueOf(formula.substring(i, j));

                for(String key : subResult.keySet()){
                    result.put(key, subResult.get(key) * num + (result.containsKey(key) ? result.get(key) : 0) );
                }

            }else if (c == ')'){
                break;
            }else {
                j = locateName(formula, i);
                name = formula.substring(i, j);

                i = j;
                j = locateNumber(formula, i);
                num = i == j ? 1 : Integer.valueOf(formula.substring(i, j));

                result.put(name, num + (result.containsKey(name) ? result.get(name) : 0));
            }
        }

        return i;
    }

    private int locateParenthesis(String formula, int start) {
        int i = start;

        int count = 0;
        for( ; i < formula.length(); i++){
            char c = formula.charAt(i);
            if(c == '('){
                count++;
            }else if(c == ')'){
                if(count == 1){
                    break;
                }
                count --;
            }
        }

        return i;
    }

    private int locateName(String formula, int start){
        int i = start;

        if(i < formula.length() && Character.isUpperCase(formula.charAt(i))){
            for( i++; i < formula.length(); i++){
                if(!Character.isLowerCase(formula.charAt(i))){
                    break;
                }
            }
        }

        return i;
    }

    private int locateNumber(String formula, int start){
        int i = start;

        for( ; i < formula.length(); i++){
            if(!Character.isDigit(formula.charAt(i))){
                break;
            }
        }

        return i;
    }

    /**
     *  Time O(n * n),
     */
    public String countOfAtoms_regex(String formula) {
        Matcher matcher = Pattern.compile("([A-Z][a-z]*)(\\d*)|(\\()|(\\))(\\d*)").matcher(formula);
        Stack<Map<String, Integer>> stack = new Stack();
        // TreeMap内部实现红黑树，HashMap内部实现哈希表
        stack.push(new TreeMap());

        while (matcher.find()) {
            String match = matcher.group();
            if (match.equals("(")) {
                stack.push(new TreeMap());
            } else if (match.startsWith(")")) {
                Map<String, Integer> top = stack.pop();
                int multiplicity = match.length() > 1 ? Integer.parseInt(match.substring(1, match.length())) : 1;
                for (String name: top.keySet()) {
                    stack.peek().put(name, stack.peek().getOrDefault(name, 0) + top.get(name) * multiplicity);
                }
            } else {
                int i = 1;
                while (i < match.length() && Character.isLowerCase(match.charAt(i))) {
                    i++;
                }
                String name = match.substring(0, i);
                int multiplicity = i < match.length() ? Integer.parseInt(match.substring(i, match.length())) : 1;
                stack.peek().put(name, stack.peek().getOrDefault(name, 0) + multiplicity);
            }
        }

        StringBuilder ans = new StringBuilder();
        for (String name: stack.peek().keySet()) {
            ans.append(name);
            final int count = stack.peek().get(name);
            if (count > 1) ans.append(String.valueOf(count));
        }
        return ans.toString();
    }

    /**
     *  Character.isUpperCase(char)
     */
    private boolean isUppercaseCharacter(char c){
        return c >= 'A' && c <= 'Z';
    }
    /**
     *  Character.isLowerCase(char)
     */
    private boolean isLowercaseCharacter(char c){
        return c >= 'a' && c <= 'z';
    }

    /**
     * Character.isDigit(char)
     */
    private boolean isNumber(char c){
        return c >= '0' && c <= '9';
    }

    public static void main(String[] args){

        CountOfAtoms sv = new CountOfAtoms();

        String[] formulas = {
                "H2O",
                "Mg(OH)2",
                "K4(ON(SO3)2)2",
                "A(B(C(D(E(F3)4)5)6)7)"
        };

        for(String formula : formulas){
            System.out.println("\n Input: " + formula);
            System.out.println("Output: \n\t" + sv.countOfAtoms_scan(formula) + " \n\t" + sv.countOfAtoms_scan_n(formula) + " \n\t" + sv.countOfAtoms_regex(formula));
        }

    }
}
