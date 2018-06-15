package fgafa.array.strobogrammatic;

/**
 * 
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 * Write a function to determine if a number is strobogrammatic. The number is represented as a string. 
 * For example, the numbers "69", "88", and "818" are all strobogrammatic.
 *
 *  cases:
 *    0,  -> 0
 *    1,  -> 1
 *    2,  
 *    3,
 *    4,
 *    5,
 *    6,  -> 9
 *    7,
 *    8,  -> 8
 *    9,  -> 6
 *    10, -> 01
 *    69, -> 
 *    88,
 *    414,
 *    818,
 *    
 */

public class Strobogrammatic {

    public boolean isStrobogrammatic(String num){
        //check
        if(null == num || 0 == num.length() || (!num.equals("0") && num.endsWith("0"))){
            return false;
        }
        
        for(int left = 0, right = num.length() - 1; left <= right; left++, right--){
            if(!isStrobogrammatic(num.charAt(left), num.charAt(right))){
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isStrobogrammatic(char a, char b){
        switch(a){
            case '0':
                return b == '0';
            case '1':
                return b == '1';
            case '6':
                return b == '9';
            case '8':
                return b == '8';
            case '9':
                return b == '6';
            default:
                return false;
        }
    }
    
    public boolean isStrobogrammatic_2(String num){
        //check
        if(null == num || 0 == num.length() || (1 != num.length() && num.endsWith("0"))){
            return false;
        }
        
        char[] map = {'0', '1', '#', '#', '#', '#', '9', '#', '8', '6'};
        int a;
        for(int left = 0, right = num.length() - 1; left <= right; left++, right--){
            a = num.charAt(left) - '0';
            
            if(a < 0 || a > 9 || map[a] == '#' || map[a] != num.charAt(right)){
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args){
        int[] input = {0,1,2,3,4,5,6,7,8,9,10, 11,69,88,414,818};
        
        Strobogrammatic sv = new Strobogrammatic();
        
        for(int i : input){
            System.out.println(String.format("%d --> %b, %b ", i, sv.isStrobogrammatic(String.valueOf(i)), sv.isStrobogrammatic_2(String.valueOf(i))));
        }
    }
}
