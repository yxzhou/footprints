package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import util.Misc;

/**
 *
 * Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
 *
 * For example, If n = 4 and k = 2, a solution is:
 *
 * [ [1,2], [1,3], [1,4], [2,3], [2,4], [3,4] ]
 *
 *
 */
public class Combinations {

    public List<List<Integer>> combine_recur(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();

        combine(n, k, new ArrayList<Integer>(k), 1, result);

        return result;
    }

    private void combine(int n, int k, List<Integer> subList, int curr, List<List<Integer>> result) {
        if (k == 0) {
            result.add(subList);
            return;
        }

        List<Integer> list;
        for (int end = n - k + 1; curr <= end; curr++) {
            list = new ArrayList<>(subList);
            list.add(curr);
            combine(n, k - 1, list, curr + 1, result);
        }
    }

    /**
     *
     */
    public List<List<Integer>> combine_recur2(int n, int k) {
        if (n <= 0 || k <= 0) {
            return null;
        }

        List<List<Integer>> result = new ArrayList<>();

        buildResult(n, k, new ArrayList<Integer>(), 1, result);

        return result;
    }

    // DFS classical format
    private void buildResult(int n, int k, List<Integer> subResult, int curr, List<List<Integer>> result) {
        if (k == 0) {
            result.add(new ArrayList<>(subResult));
            return;
        }

        for (int end = n - k + 1; curr <= end; curr++) {
            subResult.add(curr);
            buildResult(n, k - 1, subResult, curr + 1, result);
            subResult.remove(subResult.size() - 1);
        }
    }

    /*
     * 
     */
    public List<List<Integer>> combine_iterative(int n, int k) {        
        Queue<List<Integer>> result = new LinkedList<>();
        result.add(new ArrayList<>(k));

        List<Integer> top;
        List<Integer> list;
        for(; k > 0; k--){
            for(int i = result.size(); i > 0; i-- ){
                top = result.poll();

                for(int j = (top.isEmpty()? 1 : top.get(top.size() - 1) + 1), jEnd = n - k + 1 ; j <= jEnd; j++ ){
                    list = new ArrayList<>(top);
                    list.add(j);
                    result.add(list);
                }
            }
        }

        return new LinkedList<>(result);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("==========start==========" );
        long startTime = System.currentTimeMillis(); 
        int n = 15;
        int k = 5;

        Combinations sv = new Combinations();
        //List<List<Integer>> result = sv.combine_recur(n, k);
        //List<List<Integer>> result = sv.combine_recur2(n, k);
        List<List<Integer>> result = sv.combine_iterative(n, k);

        Misc.printListList(result);

        System.out.println("===========end===========" + (System.currentTimeMillis() - startTime));
    }

}
