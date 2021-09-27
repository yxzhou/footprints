package leetcode.facebook;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Find the total area covered by two rectilinear rectangles in a 2D plane.
 *
 * Example:
     Input: A = -3, B = 0, C = 3, D = 4, E = 0, F = -1, G = 9, H = 2
     Output: 45
 *
 *
 */

public class RectangleArea {
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        return computeRectilinear(A, B, C, D) +
                computeRectilinear(E, F, G, H) -
                computeRectilinear(Math.max(A, E), Math.max(B, F), Math.min(C, G), Math.min(D, H));
    }

    //input a Rectilinear, bottomLeft(x=A, y=B) and topRight(x=C, y=D)
    private int computeRectilinear(int A, int B, int C, int D){
        if(C < A || D < B){
            return 0;
        }
        return (C-A) * (D-B);
    }

    @Test public void test(){
        Assert.assertEquals(45, computeArea(-3, 0, 3, 4, 0, -1, 9, 2));
    }

}
