package dailyCoding.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Given a set of distinct positive integers, find the largest subset such that every pair of elements in the subset (i, j) satisfies either i % j = 0 or j % i = 0.
 *
 * For example,
 * Given the set [3, 5, 10, 20, 21], you should return [5, 10, 20].
 * Given [1, 3, 6, 24], return [1, 3, 6, 24].
 *
 */

public class PrimeGroup {

    public List<Integer> largestPrimeGroup(List<Integer> nums){
        if(null == nums || 0 == nums.size()){
            return Collections.emptyList();
        }

        Collections.sort(nums);

        if(nums.get(0) < 1){
            return Collections.emptyList();
        }
        if(nums.get(0) == 1){
            return nums;
        }

        int length = nums.size();
        int[] indexes = new int[length];
        List<Integer>[] groups = new ArrayList[length];

        int i = 0;
        boolean flag;
        for(int j = 0; j < length; j++){
            flag = false;
            for(int p = 0; p < i; p++){
                if(nums.get(j) % indexes[p] == 0){
                    flag = true;
                    groups[p].add(nums.get(j));
                }
            }

            if(!flag){
                indexes[i] = nums.get(j);
                groups[i] = new ArrayList<>();
                groups[i].add(nums.get(j));
                i++;
            }
        }

        int max = 0;
        List<Integer> result = groups[0];
        for(int p = 0; p < i; p++){
            if(groups[p].size() > max){
                result = groups[p];
                max = result.size();
            }
        }
        return result;
    }

    @Test
    public void test(){

//        System.out.println(largestPrimeGroup(new ArrayList<>(List.of(3, 5, 10, 20, 21))));
//
//        List<Integer> r = Arrays.asList(5, 10, 20);
//
//        System.out.println(r);

        Assert.assertEquals(Arrays.asList(5, 10, 20), largestPrimeGroup(Arrays.asList(3, 5, 10, 20, 21)));
        Assert.assertEquals(Arrays.asList(5, 10, 20), largestPrimeGroup(Arrays.asList(3, 5, 10, 20, 21)));
    }

}
