package fgafa.topCoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * Problem Statement
 *   http://community.topcoder.com/stat?c=problem_statement&pm=1166&rd=4705
 *
 * You have been given a list of jewelry items that must be split amongst two people: Frank and Bob.
 * Frank likes very expensive jewelry. Bob doesn't care how expensive the jewelry is, as long as he
 * gets a lot of jewelry. Based on these criteria you have devised the following policy:
 *
 *
 * 1) Each piece of jewelry given to Frank must be valued greater than or equal to each piece of
 *    jewelry given to Bob. In other words, Frank's least expensive piece of jewelry must be valued
 *    greater than or equal to Bob's most expensive piece of jewelry.
 *
 * 2) The total value of the jewelry given to Frank must exactly equal the total value of the
 *    jewelry given to Bob.
 *
 * 3) There can be pieces of jewelry given to neither Bob nor Frank.
 *
 * 4) Frank and Bob must each get at least 1 piece of jewelry.
 *
 * Given the value of each piece, you will determine the number of different ways you can allocate
 * the jewelry to Bob and Frank following the above policy. For example:
 *
 *  values = {1,2,5,3,4,5}
 *  Valid allocations are:
 *  Bob       		Frank
 *  1,2		         3
 *  1,3        		 4
 *  1,4		         5  (first 5)
 *  1,4              5  (second 5)
 *  2,3 		     5  (first 5)
 *  2,3              5  (second 5)
 *  5  (first 5)     5  (second 5)
 *  5  (second 5)	 5  (first 5)
 *  1,2,3,4        5,5
 *
 *  Note that each '5' is a different piece of jewelry and needs to be accounted for separately.
 *  There are 9 legal ways of allocating the jewelry to Bob and Frank given the policy, so your
 *  method would return 9.
 *
 *
 *  Constraints
 *  -	values will contain between 2 and 30 elements inclusive.
 *  -	Each element of values will be between 1 and 1000 inclusive.
 *
 */

public class Jewelry {

    public long howMany(int[] values){
        if(null == values || values.length < 2){
            return 0;
        }

        Arrays.sort(values);

        final int length = values.length;

        int[] sums = new int[length];
        sums[0] = values[0];
        for(int i = 1; i < length; i++){
            sums[i] = sums[i - 1] + values[i];
        }

        //define bobStates[i][s] as the combinations in values [0, i] whose sum is s.
        int[][] bobStates = new int[values.length][sums[length - 1] + 1];
        bobStates[0][values[0]] = 1;
        for(int i = 1; i < values.length; i++){
            bobStates[i][values[0]] = 1;
            System.arraycopy(bobStates[i - 1], 0, bobStates[i], 0, values[i]);
            for(int s = values[i]; s <= sums[i]; s++){
                bobStates[i][s] = bobStates[i - 1][s] +  bobStates[i - 1][s - values[i]];
            }
        }

        //define frankStates[i][s] as the combinations in values [i, values.length) whose sum is s.
        int[][] frankStates = new int[values.length][sums[length - 1] + 1];
        frankStates[length - 1][0] = 1;
        frankStates[length - 1][values[length - 1]] = 1;
        for(int i = length - 2; i >= 0; i--){
            frankStates[i][0] = 1;
            for(int s = values[i]; s <= sums[length - 1] - (i == 0 ? 0 : sums[i - 1]); s++){
                frankStates[i][s] = frankStates[i + 1][s] +  frankStates[i + 1][s - values[i]];
            }
        }

        Map<Integer, Integer> counts = new HashMap<>();
        int count = 0;
        int curr = values[0];
        for(int value : values){
            if(value == curr){
                count++;
            }else{
                counts.put(curr, count);
                count = 0;
            }
        }
        counts.put(curr, count);

        int result = 0;
        for(int i = 0; i < length; i++){
            if(counts.containsKey(values[i])){
                //todo
            }else{
                for(int s = values[i]; s <= sums[i]; s++ ){
                    result += bobStates[i][s] * frankStates[i][s];
                }
            }
        }

        return result;
    }

}
