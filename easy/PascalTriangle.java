package easy;

import util.Misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * _https://www.lintcode.com/problem/768/?_from=ladder&fromId=190
 * Yang Hui Triangle also called Pascal's triangle
 *
 * Problem #1: Given an integer n (0 <= n <= 20), generate the first n-line Yang Hui (Pascal) triangle.
 * 
 * For example, given n = 4,
 * Return
 * [
 *      [1]
 *     [1,1]
 *    [1,2,1]
 *   [1,3,3,1]
 * ]
 *
 *
 * Problem #2: Given an index k, return the kth row of the Yang Hui (Pascal) triangle.
 *
 * For example, given k = 3,
 * Return [1,3,3,1].
 *
 * Note:
 * Could you optimize your algorithm to use only O(k) extra space?
 *
 */

public class PascalTriangle {

    /**
     * 
     * 
     * @param n
     * @return the first n-line Yang Hui (Pascal) triangle
     */
    public List<List<Integer>> generate(int n) {
        if(n < 1 || n > 20){
            return Collections.EMPTY_LIST;
        }
        
        List<List<Integer>> result = new ArrayList<>(n);

        if (n < 1) {
            return result;
        }

        List<Integer> pre = new ArrayList<>();        
        List<Integer> curr;
        int l;
        while (n-- > 0) {
            l = 0;
            curr = new ArrayList<>(pre.size() + 1);
      
            for (int x : pre) {
                curr.add(l + x);
                l = x;
            }
            curr.add(1);

            result.add(curr);
            pre = curr;
        }

        return result;
    }

    /**
     * 
     * 
     * @param rowIndex
     * @return the kth row of the Yang Hui (Pascal) triangle
     */
    public List<Integer> getRow_x(int rowIndex) {
        if (rowIndex < 0) {
            return Collections.EMPTY_LIST;
        }
        
        List<Integer> result = new ArrayList<>(rowIndex + 1);

        for (int i = 0; i <= rowIndex; i++) {
            result.add(1);
            for (int j = i - 1; j > 0; j--) {
                result.set(j, result.get(j) + result.get(j - 1));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        PascalTriangle sv = new PascalTriangle();

        for (int rowIndex = -1; rowIndex < 6; rowIndex++) {

            System.out.println(String.format("\n generate Yong Hui Triangle: n=%d " , rowIndex + 1) );
            Misc.printListList(sv.generate(rowIndex + 1));
            
            System.out.println(String.format("\n getRow: rowIndex = %d \n %s", rowIndex, Misc.array2String(sv.getRow_x(rowIndex))) );

        }

    }

}
