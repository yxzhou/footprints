package sorting;

import java.util.Arrays;
import junit.framework.Assert;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/1219
 * 
 * Winter is coming! Your first job during the contest is to design a standard heater with fixed warm radius to warm all
 * the houses.
 *
 * Now, you are given positions of houses and heaters on a horizontal line, find out minimum radius of heaters so that
 * all houses could be covered by those heaters.
 *
 * So, your input will be the positions of houses and heaters separately, and your expected output will be the minimum
 * radius standard of heaters.
 * 
 * Note:
 *   Numbers of houses and heaters you are given are non-negative and will not exceed 25000.
 *   Positions of houses and heaters you are given are non-negative and will not exceed 10^9.
 *   As long as a house is in the heaters' warm radius range, it can be warmed.
 *   All the heaters follow your radius standard and the warm radius will the same.
 * 
 * Example 1:
 * Input: [1,2,3],[2]
 * Output: 1
 * Explanation: The only heater was placed in the position 2, and if we use the radius 1 standard, then all the houses can be warmed.
 * 
 * Example 2:
 * Input: [1,2,3,4],[1,4]
 * Output: 1
 * Explanation: The two heater was placed in the position 1 and 4. We need to use radius 1 standard, then all the houses can be warmed.
 *
 */

public class Heaters {

    /**
     * @param houses: positions of houses
     * @param heaters: positions of heaters
     * @return: the minimum radius standard of heaters
     */
    public int findRadius(int[] houses, int[] heaters) {
        if(houses == null || heaters == null){
            return -1;
        }
        
        Arrays.sort(houses);
        Arrays.sort(heaters);

        int result = Integer.MIN_VALUE; //the minimum radius standard of heaters
        int min;
        int diff;
        for(int i = 0, j = 0, m1 = heaters.length - 1, n = houses.length; i < n; i++){
            min = Math.abs(houses[i] - heaters[j]);
            while( j < m1 && min >= ( diff = Math.abs( houses[i] - heaters[j + 1])) ){
                min = diff;

                j = Math.min(j + 1, m1) ;
            }

            result = Math.max(result, min);
        }

        return result;
    }
    
    public int findRadius_n(int[] houses, int[] heaters) {
        if(null == houses || null == heaters ){
            return -1;
        }
        
        Arrays.sort(houses);
        Arrays.sort(heaters);
        
        int result = 0;  ////the minimum radius standard of heaters
        
        int i = 0; //the index of houses
        int j = 0; //the index of heaters
        int max = 0; // the max radius
        for( int curr, leftHeater = 0 - heaters[0]; i < houses.length && j < heaters.length; ){
            if(houses[i] <= heaters[j]){
                curr = Math.min(houses[i] - leftHeater, heaters[j] - houses[i]);
                max = Math.max(max, curr);     
                i++;
                
            }else if(houses[i] > heaters[j]){              
                result = Math.max(result, max);
                
                max = 0;
                leftHeater = heaters[j];
                j++;
            }
        }
        
        if(i >= houses.length && j < heaters.length){
            result = Math.max(result, max);
        }else if(i < houses.length && j >= heaters.length){
            result = Math.max(result, houses[houses.length - 1] - heaters[heaters.length - 1]);
        }
        
        return result;
    }

    
    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE); 
        System.out.println(Math.pow(10, 9)); 
        
        int[][][] inputs = {
            {
                {2,3,3},
                {0},
                {3}
            },
            {
                {1,2,3},
                {1},
                {2}
            },
            {
                {1,2,3},
                {2},
                {1}
            },
            {
                {1,2,3},
                {3},
                {2}
            },
            {
                {1,2,3},
                {4},
                {3}
            },
            {
                {1,2,3,4},
                {1,4},
                {1}
            },
            {
                {1,2,3,4},
                {2,3},
                {1}
            },
            {
                {1,2,3,4},
                {3,4},
                {2}
            },
            {
                {4,4,6,6},
                {1},
                {5}
            },
            {
                {4,4,6,6},
                {1,8},
                {3}
            },
            {
                {1,2,3,4,5},
                {2,4,6,8},
                {1}
            },
            {
                {1,2,3,4,5},
                {4,6,8},
                {3}
            },
            {
                {1,2,3,5,15},
                {2,30},
                {13}
            },
            {
                {1,2,3,5,15},
                {2,15},
                {3}
            },
            {
                {282475249,622650073,984943658,144108930,470211272,101027544,457850878,458777923},
                {823564440,115438165,784484492,74243042,114807987,137522503,441282327,16531729,823378840,143542612},
                {161834419}
            }
                
        };
        
        Heaters sv = new Heaters();
        
        for(int i = 0; i < inputs.length; i++){
            System.out.println(String.format("\n\t%d----\n\thouses [%s], \n\theaters [%s], ", i, Misc.array2String(inputs[i][0]), Misc.array2String(inputs[i][1]) ));
       
            Assert.assertEquals(inputs[i][2][0], sv.findRadius(inputs[i][0], inputs[i][1]));
            Assert.assertEquals(inputs[i][2][0], sv.findRadius_n(inputs[i][0], inputs[i][1]));
        }

    }

}
