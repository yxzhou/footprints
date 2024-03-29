package geometry;

import org.junit.Assert;

/**
 *
 * Find the total area covered by two rectilinear rectangles in a 2D plane.
 *
 * Each rectangle is defined by its bottom left corner and top right corner as shown in the figure.
 *
 * Assume that the total area is never beyond the maximum possible value of int.
 * 
 * Example:
 *   Input: A = -3, B = 0, C = 3, D = 4, E = 0, F = -1, G = 9, H = 2
 *   Output: 45
 * 
 */
public class RectangleArea {

    /**
     * input two Rectilinear Rectangles (bottom_left and top_right, bottom_left and top_right), return the total area size
     * @param A  bottom_left_x of Rectangle 1
     * @param B  bottom_left_y of Rectangle 1
     * @param C  top_right_x of Rectangle 1
     * @param D  top_right_y of Rectangle 1
     * @param E  bottom_left_x of Rectangle 2
     * @param F  bottom_left_y of Rectangle 2
     * @param G  top_right_x of Rectangle 2
     * @param H  top_right_y of Rectangle 2
     * @return the total area size
     */
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        // rectangle 1 + rectangle 2 - overlap
        return computeRectilinear(A, B, C, D)
                + computeRectilinear(E, F, G, H)
                - computeRectilinear(Math.max(A, E), Math.max(B, F), Math.min(C, G), Math.min(D, H));
    }
    
    /**input a Rectilinear(bottom_left and top_right), return the area size
     * 
     * @param A  bottom_left_x
     * @param B  bottom_left_y
     * @param C  top_right_x
     * @param D  top_right_y
     * @return  the recilinear area size
     */
    private int computeRectilinear(int A, int B, int C, int D) {
        if (C < A || D < B) {
            return 0;
        }
        return (C - A) * (D - B);
    }

    public static void main(String[] args) {
        RectangleArea sv = new RectangleArea();
        
        Assert.assertEquals(45, sv.computeArea(-3, 0, 3, 4, 0, -1, 9, 2));

    }

}
