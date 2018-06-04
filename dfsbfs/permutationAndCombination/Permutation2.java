package fgafa.dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutation2 {

	/**
	 * Given a collection of numbers, return all possible permutations.
	 * 
	 * For example, [1,2,3] have the following permutations: [1,2,3], [1,3,2],
	 * [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
	 */

	public List<List<Integer>> permute_swap(int[] num) {
		List<List<Integer>> result = new ArrayList<>();
		if (null == num || 0 == num.length) {
			return result;
		}

		permute_swap(num, 0, result);

		return result;
	}

	private void permute_swap(int[] num, int i, List<List<Integer>> result) {
		if (i + 1 == num.length) {
			List<Integer> tmp = new ArrayList<>(num.length);
			for (int j : num)
				tmp.add(j);

			result.add(tmp);
			return;
		}

		for (int curr = i; i < num.length; i++) {
			swap(num, curr, i);

			permute_swap(num, curr + 1, result);

			swap(num, i, curr);
		}
	}

	private void swap(int[] num, int i, int j) {
		int tmp = num[i];
		num[i] = num[j];
		num[j] = tmp;
	}

	public List<List<Integer>> permute_insert(int[] num) {
		List<List<Integer>> result = new ArrayList<>();
		if (null == num || 0 == num.length) {
			return result;
		}

		// start from an empty list
		result.add(new ArrayList<Integer>());

		for (int i = 0; i < num.length; i++) {
			List<List<Integer>> current = new ArrayList<>();

			for (List<Integer> l : result) {
				for (int j = 0; j < l.size() + 1; j++) {
					l.add(j, num[i]);

					ArrayList<Integer> temp = new ArrayList<Integer>(l);
					current.add(temp);

					l.remove(j);
				}
			}

			result = new ArrayList<>(current);
		}

		return result;
	}

	/**
	 * Given a collection of numbers that might contain duplicates, return all
	 * possible unique permutations.
	 * 
	 * For example, [1,1,2] have the following unique permutations: [1,1,2],
	 * [1,2,1], and [2,1,1].
	 */
	public List<List<Integer>> permuteUnique_swap(int[] num) {
		List<List<Integer>> result = new ArrayList<>();
		if (null == num || 0 == num.length) {
			return result;
		}

		permuteUnique_swap(num, 0, result);

		return result;
	}

	private void permuteUnique_swap(int[] num, int i, List<List<Integer>> set) {
		if (i + 1 == num.length) {
			List<Integer> tmp = new ArrayList<>(num.length);
			for (int j : num)
				tmp.add(j);

			set.add(tmp);
			return;
		}

		Set<Integer> unique = new HashSet<>(); // for removing the duplicates
		for (int curr = i; i < num.length; i++) {
			if (!unique.contains(num[i])) {
				unique.add(num[i]);
				swap(num, curr, i);

				permuteUnique_swap(num, curr + 1, set);

				swap(num, i, curr);
			}
		}
	}

	public List<List<Integer>> permuteUnique_insert(int[] num) {
		List<List<Integer>> result = new ArrayList<>();
		if (null == num || 0 == num.length) {
			return result;
		}
		result.add(new ArrayList<Integer>());

		for (int i = 0; i < num.length; i++) {
			Set<ArrayList<Integer>> currentSet = new HashSet<>();
			for (List<Integer> l : result) {
				for (int j = 0; j < l.size() + 1; j++) {
					l.add(j, num[i]);
					ArrayList<Integer> T = new ArrayList<Integer>(l);
					l.remove(j);
					currentSet.add(T);
				}
			}
			result = new ArrayList<>(currentSet);
		}

		return result;
	}

	/**
	 * 
	 * The set [1,2,3,…,n] contains a total of n! unique permutations.
	 * 
	 * By listing and labeling all of the permutations in order, We get the
	 * following sequence (ie, for n = 3):
	 * 
	 * "123" "132" "213" "231" "312" "321" Given n and k, return the kth
	 * permutation sequence.
	 * 
	 * Note: Given n will be between 1 and 9 inclusive.
	 */
	public String getPermutation(int n, int k) {
		if (1 > n || 1 > k)
			return "";

		int[] factorial = new int[n + 1];
		factorial[0] = 1;
		List<Integer> pool = new ArrayList<>(n);
		for (int i = 1; i <= n; i++) {
			pool.add(i);

			factorial[i] = factorial[i - 1] * i;
		}

		k--;// to transfer it as begin from 0 rather than 1
		k = k % factorial[n];// to narrow it down into [0, n-1]

		StringBuilder ret = new StringBuilder();
		int result;
		for (int i = n - 1; i >= 0; i--) {
			result = k / factorial[i];
			k = k % factorial[i];

			ret.append(pool.get(result));
			pool.remove(result);
		}

		return ret.toString();
	}

	
	

	public static void main(String[] args) {
		Permutation2 sv = new Permutation2();

		int[][] input = { { 1 }, { 1, 2 }, { 1, 2, 3 }, { 1, 2, 2 },
				{ 1, 2, 2, 2 }, { 1, 2, 2, 2, 3 }, { 0, 1, 0, 0, 9 },
				{ 3, 3, 0, 0, 2, 3, 2 } };

		for (int[] num : input) {
			// System.out.println("\nInput:" + Misc.array2String(num));

			// Misc.printIntArrayListArrayList(sv.permute_swap(num));
			// Misc.printIntArrayListArrayList(sv.permute_insert(num));

			// Misc.printIntArrayListArrayList(sv.permuteUnique_swap(num));
			// Misc.printIntArrayListArrayList(sv.permuteUnique_insert(num));
		}

		for (int n = 4, k = 0; k < 30; k++) {
			//System.out.println(sv.getPermutation(n, k));
		}
	}

}
