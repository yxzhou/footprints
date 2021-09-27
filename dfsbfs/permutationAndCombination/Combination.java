package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.List;

public class Combination {

    private ArrayList<String> list = new ArrayList<String>();

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> permutes) {
        this.list = permutes;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        char[] arr = {'a', 'b', 'c', 'd'};
        int iStop = 3;
        int iStart = 0;
        int iLen = arr.length;
        int iEnd = iLen;

        Combination sv = new Combination();

        // get combination 
        sv.setList(new ArrayList<String>());

        iEnd = iLen - iStop + 1;
        sv.getCombination(arr, iStop);
        System.out.println("--getCombination--     " + sv.getList().toString());

        sv.setList(new ArrayList<String>());
        sv.getAllCombination(arr);
        System.out.println("--getAllCombination--  " + sv.getList().toString());

        System.out.println("--getCombination--     " + sv.calCombination_DP(iLen, iStop));

    }

    /*
    * get all Combination, ( list all instead of just amount ) 
    * e.g. input: [1,2,3,4] and 2 (iStop), output  
    * Step 1, select one from 0 to iEnd==3==(4- iStop + 1) :  [1], [2], [3]
    * Step 2, select one from i++ to 4:  [2,3,4], [3,4], [4] 
    * 
    *  Step 1 ---> Step 2
    *  1 --------> 12, 13, 14
    *  2 --------> 23, 24
    *  3 --------> 34
    *  
    *  output [12], [13], [14], [23], [24], [34]
     */
    public void getCombination(char[] arr, int iStop) {
        getCombination(arr, iStop, 0, arr.length, "");
    }

    private void getCombination(char[] arr, int iStop, int iStart, int iEnd,
            String str) {
        if (iStop == str.length()) {
            this.list.add(str);
            return;
        }

        for (int i = iStart; i < iEnd; i++) {
            getCombination(arr, iStop, i + 1, iEnd, str + arr[i]);
        }

    }

    /**
     * Given two integers n and k, return all possible combinations of k numbers
     * out of 1 ... n.
     *
     * Example For example, If n = 4 and k = 2, a solution is:
     * [[2,4],[3,4],[2,3],[1,2],[1,3],[1,4]]
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();

        if (k > 0 && n >= k) {
            combine(1, n, k, new ArrayList<Integer>(), result);
        }

        return result;
    }

    private void combine(int start, int end, int k, List<Integer> list, List<List<Integer>> result) {
        if (k == 0) {
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = start; i <= end - k + 1; i++) {
            list.add(i);

            combine(i + 1, end, k - 1, list, result);

            list.remove(list.size() - 1);
        }
    }

    public void getAllCombination(char[] arr) {
        getAllCombination(arr, 0, arr.length, "");
    }

    private void getAllCombination(char[] arr, int iStart, int iEnd, String str) {
        if (str != "") {
            this.list.add(str);
        }

        for (int i = iStart; i < iEnd; i++) {
            getAllCombination(arr, i + 1, iEnd, str + arr[i]);
        }

    }

    /**
    * calculate the combination, just the amount.
    * 
    * C(n, m) = ( n*(n-1)* ... *(n-m+1) ) / ( m * (m-1) * ... * 1) 
    * e.g. C(8, 2) = 8! / (2! * (8-2)!) = (8 * 7) / (2 * 1) = 28
    * 
    * input: n = 8, m = 2,  C(8, 2)
    * output: 28
    */
    public int calCombination(int n, int m) {
        //TODO
        return -1;
    }

    /**
     * C(n, m) = ( n*(n-1)* ... *(n-m+1) ) / ( m * (m-1) * ... * 1) 
     * e.g. C(8, 2) = 8! / (2! * (8-2)!) = (8 * 7) / (2 * 1) = 28
     *
     * refer to: http://www.leetcode.com/2010/11/unique-paths.html
     * DP solution:
     * define C(m+n, m) = P(i, j) = possible unique paths from (0, 0) to (i, j)
     * P(i, j) = 1 if i == 0 or j == 0 
     *         = P(i, j-1) + P(i-1, j) if i > 0 and j > 0
     *
     */
    public int calCombination_DP(int n, int m) {
        n = n - m;
        int[][] p = new int[m + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            p[0][i] = 1;
        }
        for (int j = 1; j <= m; j++) {
            p[j][0] = 1;
        }

        for (int col = 1; col <= n; col++) {
            for (int row = 1; row <= m; row++) {
                p[row][col] = p[row - 1][col] + p[row][col - 1];
            }
        }

        return p[m][n];
    }

}
