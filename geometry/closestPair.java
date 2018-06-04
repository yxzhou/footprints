package fgafa.geometry;

import java.util.Arrays;

public class closestPair {
    
    /**
     * 
     * @param a integer array
     * @return the closest integer pair
     * 
     * ?? how to do if it need return the closet integer pair's index
     */
    public int[] closestPair1D(int[] X){
        int[] result = new int[2];
        
        if(null == X || X.length < 2){
            return result;
        }
        
        Arrays.sort(X);
        
        int diff;
        int MinDiff = Integer.MAX_VALUE;
        for(int i = 1; i < X.length; i++){
            diff = X[i] - X[i - 1];
            if(diff < MinDiff){
                MinDiff = diff;
                
                result[0] = X[i - 1];
                result[1] = X[i];
            }
        }
        
        return result;
    }

    //TreeSet
    
    /**
     * 
     * @param a array of Points,  the ith point is (points[i][0], points[i][0]) 
     * @return
     */
    public int[][] closestPair2D(int[][] points){
        //TODO
        return null;
    }
    
    /**
     * 
     * @param a array of Points,  the ith point is (points[i][0], points[i][1], points[i][2])
     * @return
     */
    public int[][] closestPair3D(int[][] points){
        //TODO
        return null;
    }
    
}
