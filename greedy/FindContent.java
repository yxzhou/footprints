package fgafa.greedy;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * There are N kids and some biscuits with different size, you are giving biscuits to these kids. 
 * 
 * To make a kid happy, it need give him/her no smaller in size than what he/she want.
 * 
 * How to make max kids happy? 
 * 
 * Sample:
 * #1, kids' expect is {1,2,3}, you have biscuits {1,1}
 *     the max you can make 1 kid happy, you can give all biscuits to the 1st kid or 2nd kid.
 * #1, kids' expect is {1,2}, you have biscuits {1,2,3}
 *     the max you can make 2 kid happy, you can give the 1st kid the 1st piece biscuit, 
 *     and give the 2nd kid the 2nd piece biscuit.
 *     
 * Note: 
 *     you can give one biscuit to one kid
 *     you can NOT break one piece biscuit 
 *     
 *  Follow up:
 *     How to do if you can give 2 or more biscuits to one kid
 */


public class FindContent {
    
    public int findContent(int[] wants, int[] biscuits){
        if(null == wants || 0 == wants.length || null == biscuits){
            return 0;
        }
        
        Arrays.sort(wants);
        Arrays.sort(biscuits);
        
        int count = 0;
        for(int j = 0; count < wants.length && j < biscuits.length; j++){
            if(wants[count] <= biscuits[j]){
                count++;
            }
        }
        
        return count;
    }
    
    public int findContentII(int[] wants, int[] biscuits){
        if(null == wants || 0 == wants.length || null == biscuits){
            return 0;
        }
        
        Arrays.sort(wants);
        Arrays.sort(biscuits);
        
        //TODO
        return -1;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
