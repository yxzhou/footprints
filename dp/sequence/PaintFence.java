package dp.sequence;

/**
 * There is a fence with n posts, each post can be painted with one of the k colors. You have to paint all the posts
 * such that no more than two adjacent fence posts have the same color. 
 * Return the total number of ways you can paint the fence.
 *
 * Note: n and k are non-negative integers.
 */

public class PaintFence {
    
    //no more than two adjacent fence posts have the same color
    public int numWays(int n, int k) {
        if (n == 0 || k == 0) {
            return 0;
        }

        int same = k; // the total number when the last fence is same as the previous one
        int diff = 0; // the total number when the last fence is different with the previous one
        
        int swap;
        for (int i = 1; i < n; i++) {
            swap = diff;
            diff = (diff + same) * (k - 1);
            same = swap;
        }

        return same + diff;
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

    //no requirement on two adjacent fence color
    public int numWays2(int n, int k) {
        if (n == 0 || k == 0) {
            return 0;
        }
        
        int result = k;

        for(int i = 1; i < n; i++){
            result = result * k;
        }
        
        return result;
    }
    
    //no more than three adjacent fence posts have the same color
    public int numWays3(int n, int k) {
        if (n == 0 || k == 0) {
            return 0;
        }
        
        //TODO
        int result = -1;
        
        return result;
    }
}
