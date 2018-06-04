package fgafa.easy;

import java.util.Stack;

/**
 * 
 * CSV Parser
    input: ：
    [['John', 'Smith', 'john.smith@gmail.com', 'Los Angeles', '1'],
    ['Jane', 'Roberts', 'janer@msn.com', 'San Francisco, CA', '0'],
    ['Alexandra "Alex"', 'Menendez', 'alex.menendez@gmail.com', 'Miami', '1']]
    
    output:
    John|Smith|john.smith@gmail.com|Los Angeles|1
    Jane|Roberts|janer@msn.com|San Francisco, CA|0
    Alexandra "Alex"|Menendez|alex.menendez@gmail.com|Miami|1. 
 *
 */

public class CSVParser {

    public String toCsv(String str) throws Exception {
        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<Character>();
        for(char c : str.toCharArray()){
            switch(c){
                case '[':
                    stack.push('[');
                    break;
                case ']':
                    if(stack.isEmpty() || stack.peek() != '['){
                        throw new IllegalArgumentException("Found mismatch.");
                    }
                    stack.pop();
                    
                    break;
                case '\'':
                    if(stack.peek() == '\''){
                        stack.pop();
                    }else{
                        stack.push('\'');
                    }
                    break;
                case ',':
                    if(!stack.isEmpty() && stack.peek() != '\''){
                        sb.append('|');
                    }else{
                        sb.append(',');
                    }
                    break;
                case ' ':
                    if(!stack.isEmpty() && stack.peek() == '\''){
                        sb.append(' ');
                    }//else ignore
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        
        if(!stack.isEmpty()){
            throw new IllegalArgumentException("Found mismatch.");
        }
        
        return sb.toString();
    }
    
    
    public static void main(String[] args){
        String input = "[['John', 'Smith', 'john.smith@gmail.com', 'Los Angeles', '1'],\n"
                    + "['Jane', 'Roberts', 'janer@msn.com', 'San Francisco, CA', '0'],\n"
                    + "['Alexandra \"Alex\"', 'Menendez', 'alex.menendez@gmail.com', 'Miami', '1']]\n";
    
        CSVParser sv = new CSVParser();
        
        try {
            System.out.println( input);
            System.out.println( sv.toCsv(input));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    
    }
}
