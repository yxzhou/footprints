package todo;

import java.util.Arrays;

import util.Misc;

/**
 * 
 * Winter is coming! Your first job during the contest is to design a standard heater with fixed warm radius to warm all the houses.

Now, you are given positions of houses and heaters on a horizontal line, find out minimum radius of heaters so that all houses could be covered by those heaters.

So, your input will be the positions of houses and heaters seperately, and your expected output will be the minimum radius standard of heaters.

Note:
Numbers of houses and heaters you are given are non-negative and will not exceed 25000.
Positions of houses and heaters you are given are non-negative and will not exceed 10^9.
As long as a house is in the heaters' warm radius range, it can be warmed.
All the heaters follow your radius standard and the warm radius will the same.

Example 1:
Input: [1,2,3],[2]
Output: 1
Explanation: The only heater was placed in the position 2, and if we use the radius 1 standard, then all the houses can be warmed.
Example 2:
Input: [1,2,3,4],[1,4]
Output: 1
Explanation: The two heater was placed in the position 1 and 4. We need to use radius 1 standard, then all the houses can be warmed.
 *
 */

public class Heaters {

    public int findRadius(int[] houses, int[] heaters) {
        
        if(null == houses || 0 == houses.length || null == heaters || 0 == heaters.length){
            return -1;
        }
        
        Arrays.sort(houses);
        Arrays.sort(heaters);
        
        int result = 0;
        int houseId = 0;
        int heaterId = 0;
        int maxRadius = 0;
        
        for( int currRadius, leftHeater = 0 - heaters[0]; houseId < houses.length && heaterId < heaters.length; houseId++){
            if(houses[houseId] < heaters[heaterId]){
                currRadius = Math.min(houses[houseId] - leftHeater, heaters[heaterId] - houses[houseId]);
                maxRadius = Math.max(maxRadius, currRadius);     
            }else if(houses[houseId] > heaters[heaterId]){              
                result = Math.max(result, maxRadius);
                
                maxRadius = 0;
                leftHeater = heaters[heaterId];
                
                heaterId++;
                houseId--;
            }
        }
        
        if(houseId >= houses.length && heaterId < heaters.length){
            result = Math.max(result, maxRadius);
        }else if(houseId < houses.length && heaterId >= heaters.length){
            result = Math.max(result, houses[houses.length - 1] - heaters[heaters.length - 1]);
        }
        
        return result;
    }


    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE); 
        System.out.println(Math.pow(10, 9)); 
        
        int[][] houses = {{1,2,3}, {1,2,3}, {1,2,3}, {1,2,3}, {1,2,3}, {1,2,3,4}, {1,2,3,4}, {1,2,3,4}, {1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,5,15}, {1,2,3,5,15}, {282475249,622650073,984943658,144108930,470211272,101027544,457850878,458777923} };
        int[][] heaters = {{0}, {1}, {2}, {3}, {4}, {1,4}, {2, 3}, {3, 4}, {2, 4, 6, 8}, {4, 6, 8}, {2, 30}, {2, 15}, {823564440,115438165,784484492,74243042,114807987,137522503,441282327,16531729,823378840,143542612}};
        
        Heaters sv = new Heaters();
        
        for(int i = 0; i < houses.length; i++){
            System.out.println(String.format("\thouses [%s], \n\theaters [%s], \n\toutput: %d\n", Misc.array2String(houses[i]), Misc.array2String(heaters[i]), sv.findRadius(houses[i], heaters[i]) ));
        }

    }

}
