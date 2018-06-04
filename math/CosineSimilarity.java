package fgafa.math;

/**
 * 
 * Cosine similarity is a measure of similarity between two vectors of an inner product space 
 * that measures the cosine of the angle between them. 
 * The cosine of 0° is 1, and it is less than 1 for any other angle.

See wiki: Cosine Similarity

Here is the formula:

/media/problem/cosine-similarity.png

Given two vectors A and B with the same size, calculate the cosine similarity.

Return 2.0000 if cosine similarity is invalid (for example A = [0] and B = [0]).


Example
Given A = [1, 2, 3], B = [2, 3 ,4].

Return 0.9926.

Given A = [0], B = [0].

Return 2.0000
 *
 */

public class CosineSimilarity {

    /**
     * @param A: An integer array.
     * @param B: An integer array.
     * @return: Cosine similarity.
     */
    public double cosineSimilarity(int[] A, int[] B) {
        //check
        if(null == A || 0 == A.length || null == B || A.length != B.length){
            return 2;
        }
        
        double aSquare = 0;
        double bSquare = 0;
        double ab = 0;
        for(int i = 0; i < A.length; i++){
            aSquare += A[i] * A[i];
            bSquare += B[i] * B[i];
            
            ab += A[i] * B[i];
        }
        
        if(0 == aSquare || 0 == bSquare){
            return 2;
        }
        
        return ab / (Math.sqrt(aSquare) * Math.sqrt(bSquare));
        
    }

	
}
