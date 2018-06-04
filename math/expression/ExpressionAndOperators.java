package fgafa.math.expression;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import fgafa.util.Misc;

/**
 * 
 * Given a string that contains only digits 0-9 and a target value, 
 * return all possibilities to add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.

    Examples: 
    "123", 6 -> ["1+2+3", "1*2*3"] 
    "232", 8 -> ["2*3+2", "2+3*2"]
    "105", 5 -> ["1*0+5","10-5"]
    "00", 0 -> ["0+0", "0-0", "0*0"]
    "3456237490", 9191 -> []
 *
 */

public class ExpressionAndOperators {

    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<String>();
        
        if(null == num || 0 == num.length()){
            return result;
        }

        int size = num.length();
        int total = (int)Math.pow(4, size - 1);
        StringBuilder operators = new StringBuilder();
        String tmp;
        for(int i = 0; i < total; i++){
            operators = new StringBuilder();
            tmp = Integer.toUnsignedString(i, 4);
            
            for(int diff = size - 1 - tmp.length(); diff > 0; diff--){
                operators.append('0');
            }
            operators.append(tmp);
            
            tmp = calculate(num, operators.toString(), target);
            if( null != tmp ){
                result.add(tmp);
            }
        }
        
        return result;
    }
    
    /**
     * 
     * @param num, includes 0 to 9
     * @param operators, includes 0 and 1 and 2 and 3, 0 means +, 1 means -, 2 means *, 3 means union
     * @return
     */
    private String calculate(String num, String operators, long target){
        //System.out.println("==" + num + "--" + operators);
        
        StringBuilder result = new StringBuilder();
        
        while(operators.length() < num.length()){
            operators += '0';
        }
        
        Deque<Long> deque = new LinkedList<>();
        deque.push(0L);
        char preOperator = '0';
        long currValue = 0;
        long top = 0;
        char operator;
        for(int i = 0; i < operators.length(); i++){
            operator = operators.charAt(i);
            currValue = currValue * 10 + num.charAt(i) - '0';
                 
            if(operators.charAt(i) == '3'){ // union
                if(currValue == 0){
                    return null;
                }
            }else{
                result.append(getOperator(preOperator));
                result.append(currValue);
                
                if(operators.charAt(i) == '2'){ // *
                    if(preOperator == '2'){
                        top = cal(deque.pollLast(), preOperator, currValue);
                    }else{
                        top = cal(0, preOperator, currValue);
                    }
                }else{ // + or -
                    top = cal(deque.pollLast(), preOperator, currValue);
                    
                    while(!deque.isEmpty()){
                        top += deque.pop();
                    }    
                }
                
                deque.addLast(top);
                preOperator = operator;

                currValue = 0;
            }
        }
        
        return deque.pop() == target ? result.toString().substring(1) : null;
    }
    
    private char getOperator(char c){
        switch (c){
            case '0':
                return '+';
            case '1':
                return '-';
            case '2':
                return '*';
            default:
                throw new IllegalArgumentException("found incorrect character");
        }
    }
    
    private long cal(long a, char operator, long b){
        switch (getOperator(operator)){
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            default:
                throw new IllegalArgumentException("found incorrect character");
        }
    }
    
    
    public List<String> addOperators_2(String num, int target) {
        List<String> res = new ArrayList<String>();
        dfs(num, target, 0, 0, "", res);
        return res;
    }
    private void dfs(String num, int target, long cur, long diff, String temp, List<String> res) {
        if(cur == target && num.length() == 0) {
            res.add(temp);
        }
        for(int i = 1; i<=num.length(); i++) {
            String str = num.substring(0, i);
            if(str.length()>1 && '0' == str.charAt(0)){
                return;
            }
            
            String next = num.substring(i);
            if(temp.length() >0) {
                dfs(next, target, cur + Long.parseLong(str), Long.parseLong(str), temp + "+" +str, res);
                dfs(next, target, cur - Long.parseLong(str), -Long.parseLong(str), temp + "-" +str, res);
                dfs(next, target, (cur - diff) + diff * Long.parseLong(str), diff * Long.parseLong(str), temp + "*" +str, res);
            } else {
                dfs(next, target, Long.parseLong(str), Long.parseLong(str), str, res);
            }
        }
    }
    
    public static void main(String[] args){
 
        
        /*
        for(int i = 0; i < Math.pow(4, 3); i++){
            String tmp = Integer.toUnsignedString(i, 4);
            
            System.out.println(tmp);
            
            while(tmp.length() < 4){
                tmp = '0' + tmp;
            }
            
            System.out.println(tmp);
        }
        */
        
        ExpressionAndOperators sv = new ExpressionAndOperators();
        
        String[] input = {
                    "123",// 6 -> ["1+2+3", "1*2*3"] 
                    "232",// 8 -> ["2*3+2", "2+3*2"]
                    "105",// 5 -> ["1*0+5","10-5"]
                    "00",// 0 -> ["0+0", "0-0", "0*0"]
                    "3456237490",//, 9191 -> []   
                    "2147483648",// -2147483648 -> []
                    "123412341234", // -1234 -> [1234-1234-1234]
                    "123451234512345", // -12345 -> [12345-12345-12345]
                    "123456123456123456", // -123456 -> [123456-123456-123456]
                    "123456712345671234567", // -1234567 -> [1234567-1234567-1234567]
                    "214748364821474836482147483648" // -2147483648 -> [2147483648 - 2147483648 -2147483648]
        };
        
        int[] targets = {6,8,5,0,9191, -2147483648, -1234, -12345, -123456, -1234567, -2147483648};

        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("Input: %s, %d", input[i], targets[i]));
            
            long startTime = System.currentTimeMillis();
            Misc.printArrayList(sv.addOperators(input[i], targets[i]));
            System.out.println(System.currentTimeMillis() - startTime);
            
            startTime = System.currentTimeMillis();
            Misc.printArrayList(sv.addOperators_2(input[i], targets[i]));
            System.out.println(System.currentTimeMillis() - startTime);
        }
    }
    
}
