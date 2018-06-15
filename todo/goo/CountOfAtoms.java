package fgafa.todo.goo;

import java.util.*;

public class CountOfAtoms {

    /**
     *  Time O(n * n),
     */
    public String countOfAtoms(String formula){
        StringBuilder result = new StringBuilder();

        Map<String, Integer> counts = countOfAtoms(formula, 0, formula.length());

        SortedSet<String> sortedNames = new TreeSet<>(counts.keySet());
        for(String name : sortedNames){
            result.append(name);
            if(1 != counts.get(name)){
                result.append(counts.get(name));
            }
        }

        return result.toString();
    }

    private Map<String, Integer> countOfAtoms(String formula, int start, int end){
        Map<String, Integer> result = new HashMap<>();

        String name;
        int num;
        for(int i = start, j = start; i < end; ){
            char c = formula.charAt(i);
            if(c == '('){
                //j = formula.indexOf(')', i);  //this is wrong
                j = locateParenthesis(formula, i);
                Map<String, Integer> subResult = countOfAtoms(formula, i + 1, j);

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
    public String countOfAtoms_2(String formula){
        StringBuilder result = new StringBuilder();

        Map<String, Integer> counts = countOfAtoms(formula, 0);

        SortedSet<String> sortedNames = new TreeSet<>(counts.keySet());
        for(String name : sortedNames){
            result.append(name);
            if(1 != counts.get(name)){
                result.append(counts.get(name));
            }
        }

        return result.toString();
    }

    private Map<String, Integer> countOfAtoms(String formula, int start){
        Map<String, Integer> result = new HashMap<>();

        String name;
        int num;
        for(int i = start, j = start; i < formula.length(); ){
            char c = formula.charAt(i);
            if(c == '('){
                //j = formula.indexOf(')', i);  //this is wrong
                j = locateParenthesis(formula, i);
                Map<String, Integer> subResult = countOfAtoms(formula, i + 1, j);

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

            }else if (c == ')'){
                break;
            }else {
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

        if(i < formula.length() && isUppercaseCharacter(formula.charAt(i))){
            for( i++; i < formula.length(); i++){
                if(!isLowercaseCharacter(formula.charAt(i))){
                    break;
                }
            }
        }

        return i;
    }

    private int locateNumber(String formula, int start){
        int i = start;

        for( ; i < formula.length(); i++){
            if(!isNumber(formula.charAt(i))){
                break;
            }
        }

        return i;
    }


    private boolean isUppercaseCharacter(char c){
        return c >= 'A' && c <= 'Z';
    }
    private boolean isLowercaseCharacter(char c){
        return c >= 'a' && c <= 'z';
    }
    private boolean isNumber(char c){
        return c >= '0' && c <= '9';
    }

    public static void main(String[] args){

        CountOfAtoms sv = new CountOfAtoms();

        String[] formulas = {
                "H2O",
                "Mg(OH)2",
                "A(B(C(D(E(F3)4)5)6)7)"
        };

        for(String formula : formulas){
            System.out.println("\n Input: " + formula);
            System.out.println("Output: " + sv.countOfAtoms(formula) + " \t" + sv.countOfAtoms_2(formula));
        }

    }
}
