package datastructure.segmentTree.inversions;


import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/532
 *
 * We can determine how "out of order" an array A is by counting the number of inversions it has. Two elements A[i] and
 * A[j] form an inversion if A[i] > A[j] but i < j. That is, a smaller element appears after a larger element.
 *
 * Given an array, count the number of inversions it has. Do this faster than O(N^2) time.
 *
 *
 * Example1 
 * Input: A = [2, 4, 1, 3, 5] 
 * Output: 3 
 * Explanation: (2, 1), (4, 1), (4, 3) are reverse pairs
 *
 * Example2
 * Input: A = [1, 2, 3, 4] 
 * Output: 0 
 * Explanation: No reverse pair
 * 
 * Example3
 * Input: A = [5, 4, 3, 2, 1] 
 * Output: 10 
 * Explanation: every distinct pair forms an inversion.
 *
 * Thoughts:
 *   m1) TreeMap<value, count>
 * 
 */

public class Inversions {
    
    /**
     * @param nums: an array
     * @return total of reverse pairs
     */
    public long reversePairs(int[] nums) {
        if(nums == null || nums.length < 2){
            return 0;
        }

        int n = nums.length;

        int[] sorted = Arrays.copyOf(nums, n);
        Arrays.sort(sorted);

        int[] biggers = new int[n];
        int[] equals = new int[n];

        long sum = 0;

        int left, right, mid;
        int delta;
        for(int a : nums){
            
            left = 0; 
            right = n - 1;
            while(left <= right){
                mid = left + (right - left) / 2;

                delta = sorted[mid] - a;
                if(delta == 0){
                    sum += biggers[mid];
                    equals[mid]++;

                    break;
                }else if(delta < 0){
                    biggers[mid]++;

                    left = mid + 1;
                }else{
                    sum += biggers[mid] + equals[mid];

                    right = mid - 1;
                }
            }
        }

        return sum;
    }
    
    /**
     * @param nums: an array
     * @return total of reverse pairs
     */
    public long reversePairs_TreeSet(int[] nums) {
        if(nums == null || nums.length < 2){
            return 0;
        }

        long sum = 0;

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        SortedMap<Integer, Integer> greaters;
        for(int a : nums){
            greaters = treeMap.subMap(a, false, Integer.MAX_VALUE, true);

            if(greaters == null){
                continue;
            }

            for(int x : greaters.values()){
                sum += x;
            }
            
            treeMap.put(a, treeMap.getOrDefault(a, 0) + 1);
        }

        return sum;
    }
    
    /**
     * @param A: an array
     * @return total of reverse pairs
     */
    public long reversePairs_mergeSort(int[] A) {
        if(A == null || A.length < 2){
            return 0;
        }

        return mergeSortAndCountInversion(A, 0, A.length - 1);
    }

    private long mergeSortAndCountInversion(int[] nums, int l, int r){
        if(l >= r){
            return 0;
        }

        int mid = l + (r - l) / 2;

        long result = mergeSortAndCountInversion(nums, l, mid) + mergeSortAndCountInversion(nums, mid + 1, r);
        for( int i = l, j = mid + 1; i <= mid && j <= r;  ){
            if(nums[i] <= nums[j]){
                i++;
            }else{
                result += mid - i + 1;
                j++;
            }
        }

        merge(nums, l, mid, r);
        return result;
    } 

    private void merge(int[] nums, int l, int m, int r){
        int[] lefts = Arrays.copyOfRange(nums, l, m + 1);
        int[] rights = Arrays.copyOfRange(nums, m + 1, r + 1);

        int i = 0;
        int j = 0;
        while(i < lefts.length && j < rights.length ){
            if(lefts[i] < rights[j]){
                nums[l++] = lefts[i++];
            }else{
                nums[l++] = rights[j++];
            }
        }

        if(i < lefts.length){ 
            System.arraycopy(lefts, i, nums, l, lefts.length - i);
        }else{
            System.arraycopy(rights, j, nums, l, rights.length - j);
        }
    }

    
    public static void main(String[] args){
        int[][][] cases = {
            //{ testcase, expect }
            {{2, 4, 1, 3, 5},{3}},
            {{5, 4, 3, 2, 1},{10}},
            {{1, 2, 3, 4},{0}},
            {{5, 4, 3, 2},{6}},
            {{5, 4, 3},{3}},
            {{5, 4},{1}},
            {{792,-300,229,-403,802,-156,819,107,924,-548,716,980,-391,47,714,-273,-267,-738,20,38,858,-511,714,533,974,677,107,241,714,-574}, {217}}
        };

        Inversions sv = new Inversions();

        for(int[][] input : cases){
            System.out.println(String.format("\n%s", Arrays.toString(input[0])));
            
            Assert.assertEquals(input[1][0], sv.reversePairs_TreeSet(input[0]));
            Assert.assertEquals(input[1][0], sv.reversePairs(input[0]));
            Assert.assertEquals(input[1][0], sv.reversePairs_mergeSort(input[0]));
        }

    }

}
