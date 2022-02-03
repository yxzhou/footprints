/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package geometry;

/**
 * _https://www.lintcode.com/problem/886
 *
 * Given a list of points that form a polygon when joined sequentially, it is found that the polygon is convex (Convex
 * polygon definition).
 *
 *
 * There are at least 3 and at most 10,000 points. 
 * Coordinates are in the range -10,000 to 10,000. 
 * You may assume the polygon formed by given points is always a simple polygon (Simple polygon definition). In other 
 * words, we ensure that exactly two edges intersect at each vertex, and that edges otherwise don't intersect each other.
 * 
 * Example 1:
    Input: points = [[0, 0], [0, 1], [1, 1], [1, 0]]
    Output:  true


 * Example 2:
    Input:  points = [[0, 0], [0, 10], [10, 10], [10, 0], [5, 5]]
    Output:  false
 * 
 * Thoughts:
 *   How to check if the points form a polygon when joined sequentially?
 *   vector multiplication, 
 *  
 *   Define 2 vectors, va and vb.
 *   Define m = va * vb.  the direction of m is right-hand rule, va and vb is on a plane. 
 *   if m == 0, means va and vb are on a line. 
 *   assume m < 0, the direction is outside of the plane. m > 0, the direction is inside of the plane.
 * 
 *   convex polygon means all direction of vector multiplication are same. 
 */
public class ConvexPolygon {
    
    //vector
    /**
     * @param point: a list of two-tuples
     * @return a boolean, denote whether the polygon is convex
     */
    public boolean isConvex(int[][] point) {
        
        Vector[] vectors = new Vector[point.length];
        int n = point.length;
        vectors[0] = new Vector( point[0][0] - point[n-1][0], point[0][1] - point[n-1][1]);
        for(int i = 1; i < n; i++ ){
             vectors[i] = new Vector( point[i][0] - point[i-1][0], point[i][1] - point[i-1][1]);
        }
        
        //vector cross multiplication
        int pre = vectors[0].x * vectors[n - 1].y - vectors[n - 1].x * vectors[0].y;
        int curr;
        for(int i = 1; i < n; i++){
            curr = vectors[i].x * vectors[i - 1].y - vectors[i - 1].x * vectors[i].y;
            
            if(curr != 0){ 
                if(curr * pre < 0){ //diferent direction
                    return false;
                }
                
                pre = curr;
            }
        }
       
        return true;
    }
    /**
     *  cross multiplication
     * 
     * @param v1
     * @param v2
     * @return 
     */
    private int multiply(Vector v1, Vector v2){
        return v1.x * v2.y - v1.y * v2.x;
    }
    
    private Vector build(int[] point1, int[] point2){
        return new Vector( point1[0] - point2[0], point1[1] - point2[1]);
    }
    
    
    class Vector{
        int x;
        int y;
        
        Vector(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    
}
