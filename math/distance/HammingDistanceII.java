package math.distance;

import java.lang.Math;

/**
 * 
 * Hamming Distance of two point.
 *   1) to 1-Dimension Point(x1) and Point(x2), it's |x1 - x2|
 *   2) to 2-Dimension Point(x1, y1) and Point(x1, y2), it's |x1 - x2| + |y1 - y2|
 *   
 * Question:
 *   1) get the max Hamming Distance in a array of multiple-Dimension
 *   
 * Solution for Q1:
 *   1, straight-forward proposal, calculate every pair's hamming distance. total there are n^2 pairs, so it's O(n^2)
 *   2, 
 *   To 1-dimension, the hamming distance of 2 Point(x) is |x1 - x2|,  all poins are in one line, so the max hamming distance is max(P.x) - min(P.x) 
 *   To 2 dimension, the hamming distance of 2 Point(x, y) is |x1 - x2| + |y1 - y2|, 
 *   |x1 - x2| + |y1 - y2|
 *   = max{
 *      (x1 - x2) + (y1 - y2), 
 *      (x1 - x2) - (y1 - y2), 
 *      -(x1 - x2) + (y1 - y2), 
 *      -(x1 - x2) - (y1 - y2), 
 *    } 
 *    = max{
 *      (x1 - y1) - (x2 - y2),      (a1)
 *      (x1 + y1) - (x2 + y2),      (b1)
 *      -(x1 - y1) + (x2 - y2),     (a2)
 *      -(x1 + y1) + (x2 + y2),     (b2)
 *    }
 *   
 *   from (a1) and (a2), it's max(P.x - P.y) - min(P.x - P.y) 
 *   from (b1) and (b2), it's max(P.x + P.y) - min(P.x + P.y)
 *   so the hamming distance of 2 Point(x, y) is max{ max(P.x - P.y) - min(P.x - P.y), max(P.x + P.y) - min(P.x + P.y) }, it's O(n)
 *   
 *   To 3 dimension, the hamming distance of 2 Point(x, y, z) is |x1 - x2| + |y1 - y2| + |z1 - z2|, 
 *   there are 2^(3-1) = 4 patterns 
 *    pattern1 = P.x + P.y + P.z,  ( + and +,  11)
 *    pattern2 = P.x + P.y - P.z,  ( + and -,  10)
 *    pattern3 = P.x - P.y + P.z,  ( - and +,  01)
 *    pattern4 = P.x - P.y - P.z   ( - and -,  00)
 *   
 *   the hamming distance of 2 Point(x, y, z) is max{ max(pattern1) - min(pattern1), max(pattern2) - min(pattern2), ---}, it's O(n)
 */

public class HammingDistanceII {

    /**
     * get the max Hamming Distance in a array of multiple-Dimension
     */
    public int hammingDistance(Point[] points, int dimension){
        //check - ignore
        
        int result = Integer.MIN_VALUE;
        int patternNum = (1 << (dimension - 1));
        int max;
        int min;
        int curr;
        String patternBits;
        for(int pattern = 0; pattern < patternNum; pattern++){
            max = Integer.MIN_VALUE;
            min = Integer.MAX_VALUE;
            patternBits = Integer.toBinaryString(pattern);
            for(Point p : points){
                curr = calculate(p, patternBits);
                max = Math.max(max, curr);
                min = Math.min(min, curr);
            }
            
            result = Math.max(result, max - min);
        }
        
        return result;
    }
    
    private int calculate(Point p, String patternBits){
        int i = 0;
        int result = p.coordinates[i++];
        
        for(char c : patternBits.toCharArray()){
            if( c == '1' ){
                result += p.coordinates[i++];
            }else{ // c == '0'
                result -= p.coordinates[i++];
            }
        }
        
        return result;
    }
    
    class Point{
        int dimension;
        int[] coordinates;
        
        public Point(int dimension, int[] coordinates){
            this.dimension = dimension;
            
            if(null == coordinates){
                this.coordinates = new int[dimension];
            }else{
                this.coordinates = coordinates;
            }
            
        }
    }
    
    public static void main(String[] args){
        int[][][] input = {
                    {{2}, {3}, {5}}, // 1-dimension
                    {{1,1},{2,2},{3,3},{4,3},{2,8}}, //2 -dimention
                    {{1,1,1},{2,2,2},{3,3,4},{2,8,5}} //3 - dimention
        };
        
        for(int dimension = 0; dimension < input.length; dimension++){
            
        }
        
    }
}
