package fgafa.game.majorityElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Given an array of size n, find the majority element. The majority element
 * is the element that appears more than ⌊ n/2 ⌋ times.
 * 
 * You may assume that the array is non-empty and the majority element
 * always exist in the array.
 */
public class MajorityElement {

	/**
	 * 投票算法，因为符合的大多数总是存在。所以没出现一对不一样的就可以忽视这一对。我们可以用计数表示，来一个数字，
	 * 如果和candidate相同那么count++，否则count--，如果count为零了，那么candidate就是下一个数。最后candidate就是大多数了 
	 * Time: O(n) Space O(1)
	 */
    public int majorityElement_x(int[] num) {
        int candidate = num[0];
        
        for(int count = 1, i=1; i< num.length; i++){
        	if(count == 0){
        		candidate = num[i];
        		count ++;
        	}else{
        		if(candidate == num[i]){
        			count++;
        		}else{
        			count--;
        		}
        	}
        }
        
        return candidate;
    }
    
	/**
	 * 因为给定的是32位数，并且大多数一定存在，那么每一位去考虑的话，对于每一位1或者0多的肯定是属于大多数的。这样就知道大多数了 
	 * Time O(n* 32) Space O(1)
	 */
    /*
     * Time O(n)  Space O(1)
     */
	public int majorityElement_1(int[] num) {
		int[] bitCnt = new int[32]; // default all are 0

		for (int i = 0; i < num.length; i++) {
			for (int j = 0; j < 32; j++) {
				if (0 < (num[i] & (1 << j)))
					bitCnt[j]++;
			}
		}

		int ans = 0;
		for (int i = 0; i < 32; i++) {
			if (bitCnt[i] > num.length / 2)
				ans += (int) (1 << i);
		}
		return ans;
	}
    
    /*
     * Time O(nlogn)  Space O(1)
     */
    public int majorityElement_2(int[] num) {
        Arrays.sort(num);
        return num[num.length / 2];
    }
    
    /*
     * Time O(n)  Space O(n)
     */
	public int majorityElement_3(int[] num) {
		int n = num.length;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int elem : num) {
			if (map.containsKey(elem)) {
				map.put(elem, map.get(elem) + 1);
			} else {
				map.put(elem, 1);
			}
		}
		for (int item : map.keySet()) {
			if (map.get(item) > n / 2) {
				return item;
			}
		}
		return -1;
	}

    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
