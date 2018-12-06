package fgafa.dailyCoding;

/**
 *
 * Given a list of integers, return the largest product that can be made by multiplying any three integers.

 For example, if the list is [-10, -10, 5, 2], we should return 500, since that's -10 * -10 * 5.

 You can assume the list has at least three integers.
 *
 * Tags: facebook
 *
 * Thoughts:
 *  analyse the test cases
 *  1)  all negative,  output multiple of the 3 in the right end.
 *  2)  only 1 negative, output multiple of the 3 in the right end.
 *  3)  only 2 or more negative, output multiple of the 2 in the left end and 1 in the right end,  or multiple of the 3 in the right end
 *  4)  all not negative, output multiple of the 3 in the right end.
 *
 *  so the point is to collect the 2 in the left end and the 3 in the right end
 *
 */

public class LargestTripleMultiple {

    /**
     * Time O(n)
     */
    public long largestTripleMultiple(int[] nums){
        if(nums.length < 3){
            throw new IllegalArgumentException("the list should has at least three integers.");
        }

        int[] twoEnds = {1, 1, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE}; //

        for(int i : nums){
            if(i > twoEnds[4]){
                twoEnds[2] = twoEnds[3];
                twoEnds[3] = twoEnds[4];
                twoEnds[4] = i;
            }else if(i > twoEnds[3]){
                twoEnds[2] = twoEnds[3];
                twoEnds[3] = i;
            }else if(i > twoEnds[2]){
                twoEnds[2] = i;
            }

            if(i < 0){
                if(i < twoEnds[0]){
                    twoEnds[1] = twoEnds[0];
                    twoEnds[0] = i;
                }else if(i < twoEnds[1]){
                    twoEnds[1] = i;
                }
            }
        }

        if(twoEnds[0] == 1){ // case 4, all not negative
            return twoEnds[2]*twoEnds[3]*twoEnds[4];
        }else if(twoEnds[1] == 1) {//case 2, only one negative
            return twoEnds[2]*twoEnds[3]*twoEnds[4];
        }else if(twoEnds[4] < 0){ //case 1 all are negative
            return twoEnds[2]*twoEnds[3]*twoEnds[4];
        }else{// case 3, there are 2 or more negative
            return Math.max(twoEnds[0]*twoEnds[1], twoEnds[2]*twoEnds[3])*twoEnds[4];
        }
    }


}
