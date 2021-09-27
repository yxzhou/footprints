package mianjing.uber;

import org.junit.Test;

import java.util.*;

/**
 * 给一个un sorted array，里面数unique，找出所有两两和相等的pairs，
 * 比如[9, 4, 3, 1, 7, 12]
 * 返回：[1, 12] & [4, 9],    [3, 7]. & [1, 9],   [4, 12] & [7, 9]
 *
 */

public class Sum2Group {

    public List<List<int[]>> sum2Group(int[] nums){

        if(null == nums || nums.length < 3){
            return Collections.emptyList();
        }

        Arrays.sort(nums);

        Map<Integer, List<int[]>> map = new HashMap<>();

        final int length = nums.length;
        int sum;
        for(int i = 0; i < length; i++){
            for(int j = i + 1; j < length; j++){
                sum = nums[i] + nums[j];

                map.putIfAbsent(sum, new ArrayList<>());
                map.get(sum).add(new int[]{nums[i], nums[j]});
            }
        }

        List<List<int[]>> result = new ArrayList<>();

        for(List<int[]> list : map.values()){
            if(list.size() > 1){
                result.add(list);
            }
        }

        return result;
    }

    @Test
    public void test(){
        List<List<int[]>> result = sum2Group(new int[]{9, 4, 3, 1, 7, 12});

        for(List<int[]> list : result){
            for(int[] pair : list){
                System.out.print( String.format("[%d, %d] & ", pair[0], pair[1]));
            }

            System.out.println();
        }
    }

}
