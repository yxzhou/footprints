package dp.backpack;

/**
 *_https://www.lintcode.com/problem/92
 * 
 * Given n items with size A[i], an integer m denotes the size of a backpack. How full you can fill this backpack?
 *
 * Note
 * You cannot divide item into small pieces
 * 
 * Example 1
 * Input: A = [3, 4, 8, 5], m = 10.
 * Output: 9
 * Explanation: fill with 4 and 5.
 * 
 * Example 2
 * Input: A = [2, 3, 5, 7], m = 12.
 * Output: 12
 * Explanation: fill with 5 and 7.
 *
 * Challenge O(n x m) time and O(m) memory.
 * O(n x m) memory is also acceptable if you do not know how to optimize memory.
 *
 */
public class Backpack {

    /* Time O(m * n)  Space O(m)*/
    public int backPack(int m, int[] A) {
        if (m < 1 || null == A || 0 == A.length) {
            return 0;
        }

        boolean[] visited = new boolean[m + 1]; //default all are false
        for (int a : A) {
            //a just can be used once
            for (int i = m - a; i >= 0; i--) {
                if (visited[i]) {
                    visited[i + a] = true;
                }
            }

            visited[a] = true;
        }

        for (; m > 0; m--) {
            if (visited[m]) {
                return m;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] A = {12, 3, 7, 4, 5, 13, 2, 8, 4, 7, 6, 5, 7};
        int m = 90;

        Backpack sv = new Backpack();
        System.out.println(sv.backPack(m, A));

    }
}
