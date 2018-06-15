package fgafa.array.strobogrammatic;

/**
 * 
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
    Write a function to count the total strobogrammatic numbers that exist in the range of low <= num <= high.
    For example,
    Given low = "50", high = "100", return 3. Because 69, 88, and 96 are three strobogrammatic numbers.
    
    Note:
    Because the range might be a large number, the low and high numbers are represented as string.
 *
 */

public class StrobogrammaticIII {

    int[] firstDigits = {0, 0, 1, 1, 1, 1, 1, 2, 2, 3};//{1, 6, 8, 9}; 
    int[] validDigits = {0, 1, 2, 2, 2, 2, 2, 3, 3, 4};//{0, 1, 6, 8, 9}; 
    int[] middleDigits = {0, 1, 2, 2, 2, 2, 2, 2, 2, 3};//{0, 1, 8}; 
    
    public long strobogrammaticInRange(String low, String high) {
        
        int lowSize = low.length();
        int highSize = high.length();
        
        long[] p = new long[highSize + 2]; // p[i] is the amount of strobogrammatic number with i-digits
        p[1] = 5; // it's {0, 1, 6, 8, 9}
        p[2] = 4; // it's {11, 69, 88, 96}
        long[] t = new long[highSize + 2]; // t[i] is the total amount of strobogrammatic number not bigger than i-digits
        t[1] = p[1];
        t[2] = t[1] + p[2];
        
        for(int i = 3; i <= highSize; i++){
            if( (i & 1) == 1){ //odd
                p[i] = p[i - 1] * 3;  //the valid digit (in middle) is {0, 1, 8}   
            }else{ //even
                p[i] = p[i - 2] * 5;  // the valid digit (not just in middle) is {0, 1, 6, 8, 9} 
            }
            
            t[i] = t[i - 1] + p[i];
        }
        
        long result = t[highSize - 1] - t[lowSize - 1] + helper(high, p) - helper(low, p);
//        if(isStrobogrammatic_2(low)){
//            result++;
//        }
        if(isStrobogrammatic_2(high)){
            result++;
        }
        
        return result;
    }
    
    private long helper(String num, long[] p){
        
        int size = num.length();
        
        if(size == 1){
            return validDigits[num.charAt(0) - '0'];
        }
        
        long factor = p[size] / 4; // the valid digit in the first position is {1, 6, 8, 9}
        long s = firstDigits[num.charAt(0) - '0'] * factor;
        
        for(int i = 1; i < size / 2; i++){
            factor /= 5;
            s += validDigits[num.charAt(i) - '0'] * factor;
        }
        
        if(size > 1 && (size & 1) == 1){
            s += middleDigits[num.charAt(size / 2) - '0'];
        }
        
        return s;
    }
    
    private boolean isStrobogrammatic_2(String num){
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
        
        String[][] input = {
                    {"0", "8"},      //4
                    {"0", "181"},    //12
                    {"50", "100"},   //3
                    {"69", "96"},    //3
                    {"0", "49"},     //6
                    {"0", "100"},    //9
                    {"1", "96"},     //8
                    {"1", "101"},    //9
                    {"11", "100"},   //4
                    {"11", "101"},   //5 
                    {"0", "111111111111111110"},
                    {"111111111111111111", "222222222222222222"},
                    {"0", "222222222222222222"},
                    {"0", "2222222222222222222222222222222"},
                    
        };
        
        StrobogrammaticIII sv = new StrobogrammaticIII();
        
        for(String[] pair : input){
            System.out.println(String.format("Input: %s to %s, Output: %d", pair[0], pair[1], sv.strobogrammaticInRange(pair[0], pair[1])));
        }
        
    }
}
