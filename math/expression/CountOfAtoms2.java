package math.expression;

import java.util.*;
import junit.framework.Assert;

public class CountOfAtoms2 {

    class Entity{
        String atom;
        int num;

        Entity(String atom, int num){
            this.atom = atom;
            this.num = num;
        }
    }

    /**
     * Define n as the length of formula
     *  Time Complexity O(n), Space O(n)
     * 
     * @param formula: a string
     * @return: return a string
     */
    public String countOfAtoms(String formula) {
        if(formula == null){
            return "";
        }

        formula += "#"; //terminal character

        String atom = "";
        int num = 0;

        Stack<Integer> stack = new Stack<>(); // the index of parentheses
        List<Entity> list = new ArrayList<>();

        char c;
        for(int i = 0; i < formula.length(); i++){
            c = formula.charAt(i);

            if(Character.isDigit(c)){
                num = num * 10 + (c - '0');
            }else if(Character.isLowerCase(c)){
                atom += c;
            }else{// if(Character.isUpperCase(c) || c == '#' || c == ')' || c == ){
                if(!atom.isEmpty()){
                    list.add(new Entity(atom, Math.max(num, 1)));

                    atom = "";
                }else if( num > 1 && !stack.isEmpty() ){ // case  " formula)10 "
                    for(int j = stack.pop(); j < list.size(); j++){
                        list.get(j).num *= num;
                    }
                }

                if(Character.isUpperCase(c)){
                    atom += c;
                }else if(c == '('){
                    stack.add(list.size());
                }

                num = 0;

            }// else throw IllegalArgumentException
        }

        Collections.sort(list, (a, b) -> a.atom.compareTo(b.atom) );

        StringBuilder result = new StringBuilder();
        Entity curr;
        Entity next;
        for(int i = 0, n = list.size(); i < n; i++){
            curr = list.get(i);

            if(i + 1 < n && (next = list.get(i + 1)).atom.equals(curr.atom) ){
                next.num += curr.num;
            }else{
                result.append(curr.atom);
                if(curr.num > 1){
                    result.append(curr.num);
                }
            }
        }

        return result.toString();
    }

    public static void main(String[] args){

        CountOfAtoms2 sv = new CountOfAtoms2();

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
