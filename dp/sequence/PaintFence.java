package fgafa.dp.sequence;

/**
 * There is a fence with n posts, each post can be painted with one of the k
 * colors. You have to paint all the posts such that no more than two adjacent
 * fence posts have the same color. Return the total number of ways you can
 * paint the fence. 
 * 
 * Note: n and k are non-negative integers.
 */

public class PaintFence {
    public int numWays(int n, int k) {

        if (n == 0 || k == 0) {
            return 0;
        }
        if (n == 1) {
            return k;
        }

        int same_color_count = k;
        int diff_color_count = k * (k - 1);

        for (int i = 3; i <= n; i++) {
            int swap = diff_color_count;
            diff_color_count = diff_color_count * (k - 1) + same_color_count
                        * (k - 1);
            same_color_count = swap;
        }

        return same_color_count + diff_color_count;

    }
}
