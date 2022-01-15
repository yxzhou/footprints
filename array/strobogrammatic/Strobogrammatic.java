package array.strobogrammatic;

import junit.framework.Assert;

/**
 * 
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 * Write a function to determine if a number is strobogrammatic. The number is represented as a string. 
 * For example, the numbers "69", "88", and "818" are all strobogrammatic.
 *
 *  cases:
 *    0,  -> 0    true
 *    1,  -> 1    true
 *    2,  -> 5       
 *    3,
 *    4,
 *    5,  -> 2
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

    char[] mirrors = {'0', '1', '5', '#', '#', '2', '9', '#', '8', '6'};

    public boolean isStrobogrammatic(String num) {
        if (num == null) {
            return false;
        }

        for (int l = 0, r = num.length() - 1; l <= r; l++, r--) {
            if (num.charAt(l) != mirrors[num.charAt(r) - '0']) {
                return false;
            }
        }

        return true;
    }
    
    public static void main(String[] args){
        int[] inputs = {0,1,2,3,4,5,6,7,8,9,10, 11,69,88,414,818, 26895};
        boolean[] expects = {true, true, false, false, false, false, false, false, true, false, false, true, true, true, false, true, true };
        
        Strobogrammatic sv = new Strobogrammatic();
        
        for(int i = 0; i < expects.length; i++){
            System.out.println(String.format(" Input %d:  %d ", i, inputs[i]));
            
            Assert.assertEquals(expects[i], sv.isStrobogrammatic(String.valueOf(inputs[i])));
        }
    }
}
