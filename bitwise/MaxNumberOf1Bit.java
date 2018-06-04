package fgafa.bitwise;

/**
 * From Uber
 * 
 * filp 1 or 0.
    You are given a binary array with N elements: d[0], d[1], ... d[N -1].
    You can perform AT MOST one move on the array: choose any two integers [L, R],
    and flip all the elements between (and including) the L-th and R-th bits. L and
    R represent the left-most and right-most index of the bits marking the 
    boundaries of the segment which you have decided to flip.

    What is the maximum number of '1'-bits (indicated by S) which you can obtain in the final bit-string?
    'Flipping' a bit means, that a 0 is transformed to a 1 and a 1 is transformed to a 0
    Input Format
    An integer N
    Next line contains the N bits, separated by spaces: d[0] d[1] ... d[N-1].
    
    Output:
    S
    
    Sample Input:
    8
    1 0 0 1 0 0 1 0
    Sample Output:
    6
 *
 */

public class MaxNumberOf1Bit {

    public int flip(int num){
        
        String binary = Integer.toBinaryString(num);
        int total1 = 0;
        int subSum = 0;
        int minSum = 0;
        int curr;
        for( char c :  binary.toCharArray()){
            if(c == '1'){
                total1++;
                curr = 1;
            }else{ // c == '0'
                curr = -1;
            }
            
            subSum = Math.min(subSum + curr, curr);
            minSum = Math.min(minSum, subSum);
        }
        
        return total1 - minSum;
    }
    
    
    public static void main(String[] args){
        
    }
}
