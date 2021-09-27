package array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import util.Misc;

/**
 * 
 Given a list of non negative integers, arrange them such that they form the
 * largest number.
 * 
 * For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.
 * 
 * Note: The result may be very large, so you need to return a string instead of
 * an integer.
 *
 */
public class LargestNumber {

	/**
	 * 2 cases need to be noted:
	 *   1) {0, 0}
	 *   2) {21, 22, 23, 2}
	 */
	public String largestNumber(int[] num) {
		// check
		if (null == num || 0 == num.length)
			return "";

		// order by
		int sum = 0;
		List<String> list = new ArrayList<>(num.length);
		for (int n : num){
			list.add(String.valueOf(n));
			sum += n;  
		}

		if(0 == sum){  //note, for case {0, 0}
			return "0";
		}
			
		Collections.sort(list, new NumberComparator());

		StringBuilder sb = new StringBuilder();
		for (int i = list.size() - 1; i >= 0; i--){
			sb.append(list.get(i));
		}

		return sb.toString();
	}

	class NumberComparator implements Comparator<String> {
		public int compare(String a, String b) {
			if (a.length() != b.length()) {  //note, for case {21, 22, 23, 2}
				String tmp = a;
				a = a + b;
				b = b + tmp;
			}

			for (int i = 0; i < a.length(); i++) {
				if (a.charAt(i) > b.charAt(i))
					return 1;
				else if (a.charAt(i) < b.charAt(i))
					return -1;
			}

			return 0;
		}
	}

    /**
     *@param num: A list of non negative integers
     *@return: A string
     */
    public String largestNumber_n(int[] num) {
        //check
        if(null == num || 0 == num.length){
            return "";
        }
        
        List<String> input = new ArrayList<>();
        for(int n : num){
        	input.add(String.valueOf(n));
        }
        
        Collections.sort(input, new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				if(o1.length() != o2.length()){
					String tmp = o1;
					o1 = o1 + o2;
					o2 = o2 + tmp;
				}
				
				for(int i = 0 ; i < o1.length(); i++ ){
					if(o1.charAt(i) < o2.charAt(i)){
						return 1;
					}else if(o1.charAt(i) > o2.charAt(i)){
						return -1;
					}
				}
				
				return 0;
			}
        });
        
        if(input.get(0).equals("0")){
        	return "0";
        }
        
        StringBuilder result = new StringBuilder();
        for(String str : input){
        	result.append(str);
        }
        
        return result.toString();
    }
	
	public static void main(String[] args) {
		LargestNumber service = new LargestNumber();
		int[][] arr = { { 1 }, { 3, 30, 34, 5, 9 }, { 96, 969, 966, 965}, {0, 0} };

		for (int i = 0; i < arr.length; i++) {
			System.out.println("\n -" + i
					+ "-The non-negative integers array is: "
					+ Misc.array2String(arr[i]));

			System.out
					.println("The minimum number of jumps to reach the last index is "
							+ service.largestNumber(arr[i]));

		}

	}

}
