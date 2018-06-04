package fgafa.sorting;

import java.util.Random;

import fgafa.util.Misc;

/**
 * 
 * Given a set of n nuts of different sizes and n bolts of different sizes. There is a one-one mapping between nuts and bolts. Comparison of a nut to another nut or a bolt to another bolt is not allowed. It means nut can only be compared with bolt and bolt can only be compared with nut to see which one is bigger/smaller.

	We will give you a compare function to compare nut with bolt.
	
	Example
	Given nuts = ['ab','bc','dd','gg'], bolts = ['AB','GG', 'DD', 'BC'].
	
	Your code should find the matching bolts and nuts.
	
	one of the possible return:
	
	nuts = ['ab','bc','dd','gg'], bolts = ['AB','BC','DD','GG'].
	
	we will tell you the match compare function. If we give you another compare function.
	
	the possible return is the following:
	
	nuts = ['ab','bc','dd','gg'], bolts = ['BC','AA','DD','GG'].
	
	So you must use the compare function that we give to do the sorting.
	
	The order of the nuts or bolts does not matter. You just need to find the matching bolt for each nut.
 *
 */

public class NutsAndBolts {

	/**
	 * @param nuts
	 *            : an array of integers
	 * @param bolts
	 *            : an array of integers
	 * @param compare
	 *            : a instance of Comparator
	 * @return: nothing
	 */
	/* Time O(n * logn) */
	public void sortNutsAndBolts(String[] nuts, String[] bolts, NBComparator compare) {
		//check
		if(null == nuts || null == bolts || 0 == nuts.length || nuts.length != bolts.length){
			return;
		}
		
		quicksort(nuts, bolts, 0, nuts.length - 1, compare);
		
	}

	private void quicksort(String[] nuts, String[] bolts, int start, int end, NBComparator compare){
		if(start >= end){
			return;
		}
		
		Random random = new Random(); 
		int pivot = start + random.nextInt(end - start + 1);
		
		pivot = partition(nuts, start, end, bolts[pivot], compare);
		partition(bolts, start, end, nuts[pivot], compare);
		
		quicksort(nuts, bolts, start, pivot - 1, compare);
		quicksort(nuts, bolts, pivot + 1, end, compare);
	}
	
	private int partition(String[] strs, int start, int end, String str, NBComparator compare){
		int diff1;
		int diff2;
		for(int i = start; i <= end; ){
			diff1 = compare.cmp(strs[i], str);
			diff2 = compare.cmp(str, strs[i]);
			if(diff1 == 1 || diff2 == -1){
				swap(strs, i, end);
				end--;
			}else if(diff1 == 0 || diff2 == 0){
				swap(strs, i, start);
				i++;
			}else{
			    i++;
			}
		}
		swap(strs, start, end);
		
		return end;
	}
	
	private void swap(String[] strs, int i, int j){
		String tmp = strs[i];
		strs[i] = strs[j];
		strs[j] = tmp;
	}
	
	public static void main(String[] args){
		String[] nuts = {"ab","bc","dd","gg", "ef"};
		String[] bolts = {"AB","GG","DD","BC", "EF"};
		
		NutsAndBolts sv = new NutsAndBolts();
		
		sv.sortNutsAndBolts(nuts, bolts, sv.new NBCompare());
		
		System.out.println(Misc.array2String(nuts));
		System.out.println(Misc.array2String(bolts));
	}
	
	/**
	 * You can use compare.cmp(a, b) to compare nuts "a" and bolts "b", if "a"
	 * is bigger than "b", it will return 1, else if they are equal, it will
	 * return 0, else if "a" is smaller than "b", it will return -1. When "a" is
	 * not a nut or "b" is not a bolt, it will return 2, which is not valid.
	 */
	interface NBComparator{
		int cmp(String a, String b);
	}
	
	public class NBCompare implements NBComparator{

		public NBCompare(){
			
		}
		
		@Override
		public int cmp(String a, String b){
			return a.compareToIgnoreCase(b);
		};

	}
}
