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
    public int numWays(int n,
                       int k) {

        if (n == 0 || k == 0) {
            return 0;
        }
        if (n == 1) {
            return k;
        }

        int sameColorCount = k;
        int diffColorCount = k * (k - 1);

        for (int i = 3; i <= n; i++) {
            int swap = diffColorCount;
            diffColorCount = diffColorCount * (k - 1) + sameColorCount * (k - 1);
            sameColorCount = swap;
        }

        return sameColorCount + diffColorCount;
    }
    
    
    //no adjacent fence posts have the same color
    public int numWays1(int n, int k) {
        if (n == 0 || k == 0) {
            return 0;
        }
        
        int result = k;

        for(int i = 2; i <= n; i++){
            result = result * (k -1);
        }
        
        return result;
    }

    //no more than two adjacent fence posts have the same color
    public int numWays2(int n, int k) {
        if (n == 0 || k == 0) {
            return 0;
        }
        
        int result = k;

        for(int i = 2; i <= n; i++){
            result = result * k;
        }
        
        return result;
    }
    
    //no more than three adjacent fence posts have the same color
    public int numWays3(int n, int k) {
        if (n == 0 || k == 0) {
            return 0;
        }
        
        int f1 = k;
        int f2 = k * k;

        int result = 1;
        for(int i = 3; i <= n; i++){
            result = result * (k -1);
        }
        
        return result;
    }
}
