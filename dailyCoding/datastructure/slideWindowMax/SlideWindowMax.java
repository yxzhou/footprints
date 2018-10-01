package fgafa.dailyCoding.datastructure.slideWindowMax;

import java.util.LinkedList;
import java.util.Deque;

import fgafa.util.Misc;

/**
 *
 * Given an array of integers and a number k, where 1 <= k <= length of the array, compute the maximum values of each subarray of length k.

 For example, given array = [10, 5, 2, 7, 8, 7] and k = 3, we should get: [10, 7, 8, 8], since:

 10 = max(10, 5, 2)
 7 = max(5, 2, 7)
 8 = max(2, 7, 8)
 8 = max(7, 8, 7)

 Do this in O(n) time and O(k) space. You can modify the input array in-place and you do not need to store the results. You can simply print them out as you compute them.
 *
 * Tags: Google,
 *
 *
 *
 */


public class SlideWindowMax {

    private class Wrapper {
        int value;
        int index;

        Wrapper(int value, int index){
            this.value = value;
            this.index = index;
        }
    }

    public int[] slideWindowMax(int[] nums, int k){
        if(null == nums || 0 == nums.length || k < 1){
            return new int[0];
        }

        int[] result = new int[nums.length - k + 1];

        Deque<Wrapper> deque = new LinkedList<>();

        int j = 0;
        int kminus1 = k - 1;
        for(int i = 0; i < nums.length; i++){
            while(!deque.isEmpty() && ((Wrapper)deque.getLast()).value < nums[i]){
                deque.removeLast();
            }

            deque.addLast(new Wrapper(nums[i], i));

            int slideLeft = i - kminus1;
            if(slideLeft >= 0){
                result[j++] = deque.getFirst().value;
            }

            if(deque.getFirst().index <= slideLeft){
                deque.removeFirst();
            }
        }

        return result;
    }

    public static void main(String[] args){
        int[] input = {10, 5, 2, 7, 8, 7};

        SlideWindowMax sv = new SlideWindowMax();
        System.out.println(Misc.array2String(sv.slideWindowMax(input, 3)));
    }

}



