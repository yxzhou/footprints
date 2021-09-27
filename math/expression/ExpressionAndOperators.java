package math.expression;

import util.Misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

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

    //dfs
    public List<String> addOperators_dfs(String num, int target) {
        List<String> result = new LinkedList<>();

        if(null == num || num.length() == 0){
            return result;
        }

        dfs(num, 0, target, 0, 0, "", result);

        return result;
    }

    private void dfs(String num, int i, int target, long curr, long last, String path, List<String> result) {

        if(i == num.length() && target == curr){
            result.add(path);
        }

        String s;
        long n;
        for(int j = i + 1; j <= num.length(); j++){
            s = num.substring(i, j);
            n = Long.parseLong(s);

            if(s.length() > 1 && s.charAt(0) == '0' ){
                break;
            }

            if(path.length() == 0){
                dfs(num, j, target, n, n, s, result);
            }else{
                dfs(num, j, target, curr + n, n, path + "+" + s, result );
                dfs(num, j, target, curr - n, -n, path + "-" + s, result );

                dfs(num, j, target, curr - last + last * n, last * n, path + "*" + s, result );
            }
        }
    }
    
    public List<String> addOperators_dfs_x(String num, int target) {
        if(num == null){
            return Collections.EMPTY_LIST;
        }

        List<String> result = new LinkedList<>();

        dfs(num.toCharArray(), 0, new char[num.length()*2], 0, 0, 0, target, result);

        return result;
    }

    private void dfs(char[] num, int i, char[] path, int p, long curr, long last, int target, List<String> result){
        if(i == num.length){
            if(curr == target){
                result.add(String.valueOf(path, 0, p));
            }

            return;
        }

        long n;
        for(int j = i + 1, end = (num[i] == '0'? i + 1 : num.length); j <= end; j++ ){
            n = Long.parseLong(String.valueOf(num, i, j - i));

            if(i == 0){
                System.arraycopy(num, i, path, p, j - i);
                dfs(num, j, path, p + j - i, n, n, target, result);
            }else{
                System.arraycopy(num, i, path, p + 1, j - i);

                path[p] = '+'; //+
                dfs(num, j, path, p + 1 + j - i, curr + n, n, target, result);

                path[p] = '-'; //-
                dfs(num, j, path, p + 1 + j - i, curr - n, -n, target, result);
 
                path[p] = '*'; //-
                dfs(num, j, path, p + 1 + j - i, curr - last + last * n, last * n, target, result);
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
            System.out.println(String.format("\nInput: %s, %d", input[i], targets[i]));

            long startTime = System.currentTimeMillis();
//            Misc.printArrayList(sv.addOperators(input[i], targets[i]));
//            System.out.println(System.currentTimeMillis() - startTime);
            
            startTime = System.currentTimeMillis();
            Misc.printArrayList(sv.addOperators_dfs(input[i], targets[i]));
            System.out.println(System.currentTimeMillis() - startTime);

            startTime = System.currentTimeMillis();
            Misc.printArrayList(sv.addOperators_dfs_x(input[i], targets[i]));
            System.out.println(System.currentTimeMillis() - startTime);
            
        }
    }
    
}
