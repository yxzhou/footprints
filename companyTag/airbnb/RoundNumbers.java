package companyTag.airbnb;

import java.util.Arrays;


import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;

/**
 * 
 * Given an double array, X = {x1, x2, ..., xn}, find a way to round each element in X, Y = {y1, y2, ..., yn}
 * It need to satisfy the condition:
 *   1 y_i is a int, that is round of x_i 
 *   2 |sum(Y) - sum(X)| is minimal
 *   3 sum(|Y_i - X_i|) is minimal 
 *   
 * 
 * Example1:
 * input = 30.3, 2.4, 3.5
 * output = 30 2 4
 * 
 * Example2:
 * input = 30.9, 2.4, 3.9
 * output = 31 2 4
 * Explain: it's {31, 2, 4} instead of {30, 3, 4}, because (0.1 + 0.4 + 0.1) < (0.9 + 0.6 + 0.1)
 *
 * Thoughts:
 *   
 *   Define y_i = floor(x_i), d_i = x_i - y_i, 
 *   Example: X = {x1, x2, x3}, Y = {y1, y2, y3}, {d1, d2, d3} = {x1-y1, x2-y2, x3-y3}
 *   Define D = |sum(Y) - sum(X)|
 *   Define R = sum(|Y_i - X_i|), 
 * 
 *   Define R0 as the sum(|Y_i - X_i|) when all y_i is floor(x_i) 
 *   D0 = |sum(Y) - sum(X)| = d1 + d2 + d3  
 *   R0 = sum(|Y_i - X_i|)  = d1 + d2 + d3    
 * 
 *   if one of Y is changed from floor to ceil
 *   D1 = |D0 - 1|  
 *   if the one is y3, y3 = ceil(x3), R1 = d1 + d2 + (1 - d3) = R0 + 1 - 2 * d3    
 *   if the one is y2, y2 = ceil(x2), R1 = d1 + (1 - d2) + d3 = R0 + 1 - 2 * d2    
 *   ---
 *   if 0 <= d1 <= d2 <= d3 <= 1
 *   min(R1) = R0 + 1 - 2 * max(d) = R0 + 1 - 2 * d3
 *  
 *   if two of Y are changed from floor to ceil
 *   D2 = |D0 - 2|  
 *   if the two is y2 and y3, R2 = d1 + (1 - d2) + (1 - d3) = (d1 + d2 + d3) + (2 - 2*d2 - 2*d3)  
 *   if the two is y1 and y3, R2 = (1 - d1) + d2 + (1 - d3) = (d1 + d2 + d3) + (2 - 2*d1 - 2*d3) 
 *   ---
 *   if 0 <= d1 <= d2 <= d3 <= 1
 *   min(R2) = R0 + 1 - 2 * (d2 + d3)  or  min(R2) = min(R1) + 1 - 2 * d2
 * 
 *   The D_i is down and up, example D0 > D1 > 0 < D2 < D3, the point is to find out the |D1| vs |D2|
 *   Example 1, D0 = 1.2, D1 = 0.2, D2 = 0.8 
 *   Example 2, D0 = 1.6, D1 = 0.6, D2 = 0.4 
 *   Example 3, D0 = 1.5, D1 = 0.5, D2 = 0.5
 * 
 *   when D2 < 0.5 < D3, it means two Y need be changed, the minimal R2 = R0 + 1 - 2 * (d2 + d3)
 *   when D2 == 0.5, compare R2 and R3 
 * 
 *   So program will be:
 *     1) calculate D, {d1, d2, d3}
 *        calculate Y, {floor(x_i)} 
 *        calculate D0
 * 
 */

public class RoundNumbers {

    public int[] roundNumber(double[] prices){
        if(null == prices){
            return new int[0];
        }

        int n = prices.length;
        int[] result = new int[n];
        Node[] nodes = new Node[n];//
        
        double diff;
        double sumDiff = 0; // it's |sum(Y) - sum(X)|
        //double diffSum = 0; // it's sum(|Y_i - X_i|)
        for(int i = 0; i < n; i++){
            result[i] = (int)Math.floor(prices[i]);

            diff = prices[i] - result[i];
            nodes[i] = new Node(diff, i);
            sumDiff += diff;
        }

        Arrays.sort(nodes, (a, b) -> Double.compare(b.diff, a.diff));

        int i = 0;
        //diffSum = sumDiff;
        while( sumDiff > 0.5 ) {
            result[nodes[i].id]++;
            
            sumDiff--;
            //diffSum += 1 - 2 * nodes[i].diff;
            
            i++;
        }
        
        if(sumDiff == 0.5 && 1 - 2 * nodes[i].diff < 0 ){ // diffSum > diffSum + 1 - 2 * d_i
            result[nodes[i].id]++;
        }

        return result;
    }
    
    class Node{
        int id; //the index in the prices
        double diff; // price[i] - floor(price[i])
        
        Node(double diff, int id){
            this.diff = diff;
            this.id = id;
        }
    }
    

    public static void main(String[] args){
        double[][][] inputs = {
            {
                {30.3, 2.4, 3.5},
                {30, 2, 4},
            },
            {
                {30.9, 2.4, 3.9},
                {31, 2, 4} // {30, 3, 4}
            },
            {
                {30.3, 2.4, 3.2},
                {30, 3, 3}
            },
            {
                {1.2, 2.3, 3.4},
                {1, 2, 4}
            },
            {
                {1.1, 2.3, 3.2},
                {1, 3, 3}  
            },
            {
                {1.8, 2.7, 3.9},
                {2, 2, 4} 
            },
            {
                {1.8, 1.9, 3.8},
                {2, 2, 4} 
            },
            {
                {1.2, 3.7, 2.3, 4.8},
                {1, 4, 2, 5} //{1, 3, 3, 5}
            },
            {
                {1.2, 2.5, 3.6, 4.0},
                {1, 2, 4, 4} //{1, 2, 4, 4}
            },
            {
                {-0.4, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3},
                {0, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1}
            }
        };
  
        RoundNumbers sv = new RoundNumbers();
                
        for(double[][] input : inputs){
            System.out.println(String.format("\n prices: %s \n expect: %s ", Arrays.toString(input[0]), Arrays.toString(Arrays.stream(input[1]).mapToInt(d -> (int)d).toArray()) ));
            System.out.println(String.format(" result: \n\t%s", Arrays.toString(sv.roundNumber(input[0])) ));
            //System.out.println(String.format(" result: \n\t%s", Arrays.toString(sv.roundNumber_x(input[0])) ));
            
            Assert.assertArrayEquals("roundNumber ", Arrays.stream(input[1]).mapToInt(d -> (int)d).toArray() , sv.roundNumber(input[0]));
            
            //Assert.assertArrayEquals(Arrays.toString(input[0]), Arrays.stream(input[1]).mapToInt(d -> (int)d).toArray() , sv.roundNumber_x(input[0]));
        }

    }

}
