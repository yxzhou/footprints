package todo.goo;

import java.math.BigDecimal;

/**
 * Google Onsite
 * 
 * Given a bottle of medicine, everyday you can take one piece from the bottle, 
 * if it's a whole piece, break it, put one half back to bottle.  
 * if it's a half piece, take it away. 
 * 
 * Suppose it has n whole piece in the bottle, in k-th day, what's the probability that you take a whole piece? 
 */

public class MedicineProbability {

    /*
     * define S(broken whole piece amount, half piece amount) as the occurrence probability of itself. 
     * at the beginning,  it's S(0, 0) = 1
     * after one day,  it's S(1, 1) = 1;
     * after 2nd day, 
     *        S(2, 2) = (n - 1) / n * S(1, 1)
     *        S(1, 0) = 1 / n * S(1, 1) 
     * 
     * after 3rd day, 
     *        S(3, 3) = (n - 2) / n * S(2, 2)
     *        S(2, 1) = 2 / n * S(2, 2) + S(1, 0)
     *        
     * after 4th day, 
     *        S(4, 4) = (n - 3) / n * S(3, 3)
     *        S(3, 2) = 3 / n * S(3, 3) + (n - 2) / (n - 1) * S(2, 1)
     *        S(2, 0) = 1 / (n - 1) * S(2, 1)
     *        
     *  ---
     *  after k-th day, 
     *        S(k, k) = [n - (k - 1)] / [n - (k - 1) + (k - 1)] * S(k - 1, k - 1)
     *        S(k - 1, k - 2) = (k - 1) / n * S(k - 1, k - 1) + [n - (k - 2)] / [n - (k - 2) + (k - 3)] * S(k - 2, k - 3)
     *        S(k - 2, k - 4) = (k - 3) / [n - (k - 2) + (k - 3)] * S(k - 2, k - 3) + [n - (k - 3)] / [n - (k - 3) + (k - 5)] * S(k - 3, k - 5)
     *        ---
     *  
     *   The general formula of the status changing.
     *        S(broken, half) = S(broken, half + 1) * (half + 1) / [n - broken + (half + 1)] // take away a half piece
     *                        + S(broken - 1, half - 1) * [n - (broken - 1)] / [n - (broken - 1) + (half - 1)] // take a whole piece and back a half piece
     *    
     *    to S(broken, half), the probability of take a whole piece is:
     *        S(broken, half) = (n - broken) / (n - broken + half)
     *  
     *  the probability of get a whole piece from the bottle is:
     *  P(k + 1) = (n - k) / (n - k + k) * S(k, k) + 
     *             [n - (k - 1)] / [ n - (k - 1) + (k - 2)] * S(k - 1, k - 2) +
     *             --- +
     *             k is even ? S(k - k/2, 0) : [n - (k - k/2)] / [n - (k - k/2) + 1] * S(k - k/2, 1)
     *  
     */
    
    public double calculateProbability(int n, int k){
        if(n <= 0 || k <= 0 || k > n * 2){
            return 0d;
        }
        
        if(k == 1){
            return 1d;
        }
        
        //status[1][1] show there is n - 1 whole piece and 1 half piece in the bottle 
        int size = Math.min(n, k);
        BigDecimal[][] status = new BigDecimal[size][size]; 
        
        status[0][0] = new BigDecimal("1");
        //status[1][1] = status[0][0];
        
        for(int step = 1; step < k; step++){
            for(int half = step, broken = step; half >= 0; half -= 2, broken--){
                if(broken >= n){
                    continue;
                }
                
                if(half > 0){
                    status[broken][half] = status[broken - 1][half - 1].multiply(div(n - broken + 1, n - broken + half));
                }else{
                    status[broken][half] = new BigDecimal("0");
                }
                    
                if(half < step){
                    status[broken][half] = status[broken][half].add(status[broken][half + 1].multiply(div(half + 1, n - broken + half + 1)));
                }
            }
        }
                    
        BigDecimal result = new BigDecimal("0");
        for(int half = k - 1, broken = k - 1; half >= 0; half -= 2, broken--){
            if(broken < n){
                result = result.add(status[broken][half].multiply(div(n - broken, n - broken + half)));
            }
        }
        
        return result.doubleValue();
    }
    
    private static final int DEF_DIV_SCALE = 10;
    
    private BigDecimal div(int v1, int v2){
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }
    
    public static void main(String[] args) {
        MedicineProbability sv = new MedicineProbability();
        
        final int N = 10;

        for(int n = 1; n < N; n++){
            for(int k = 0; k <= n * 2; k++){
                System.out.println(String.format("Input: n = %d, k = %d, result = %f", n, k, sv.calculateProbability(n, k)));
            }
            
            System.out.println();
        }
        
    }

}
