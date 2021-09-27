package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Find all possible combinations of k numbers that add up to a number n, 
 * given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.
 *
 * Ensure that numbers within the set are sorted in ascending order.
 *
 * Example 1: Input: k = 3, n = 7 Output: [[1,2,4]]
 * Example 2: Input: k = 3, n = 9 Output: [[1,2,6], [1,3,5], [2,3,4]]
 *
 */
public class CombinationSumIII {

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();

        if (k < 1 || k > 9 || n < k || n >= k * 9) {
            return result;
        }

        helper(k, n, 1, new ArrayList<Integer>(k), 0, result);
        return result;
    }

    private void helper(int k, int n, int last, List<Integer> list, int p, List<List<Integer>> result) {

        if (0 == k && 0 == n) {
            result.add(new ArrayList<>(list));
            return;
        } else if (k <= 0) {
            return;
        }

        for (int i = last; i < 10; i++) {
            if (n < i * k || n > k*9 || i > n) { // all the rest of elements are too great or too small
                break;
            }


            list.set(p, i);

            helper(k - 1, n - i, i + 1, list, p + 1, result);
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
