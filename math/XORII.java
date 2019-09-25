package fgafa.math;

import fgafa.bitwise.ReverseBits3;
import org.junit.Test;

/**
 *
 * This problem was asked by Jane Street.
 *
 * Given integers M and N, write a program that counts how many positive integer pairs (a, b) satisfy the following conditions:
 *
 * a + b = M
 * a XOR b = N
 *
 */

public class XORII {

    public int count(int m, int n){
        if(m < 0 || n < 0){
            return 0;
        }

        int count = 0;
        for(int a = 1, end = m / 2 + 1, b; a < end ; a++){
            b = m - a;
            if(  (a ^ b) == n ) {
                if(a == b){
                    count++;
                }else{
                    count += 2;
                }
            }
        }

        return count;
    }

    public int count_2(int m, int n){
        if(m < 0 || n < 0){
            return 0;
        }

        int count = 0;
        for(int a = 1, end = m / 2 + 1, b; a < end ; a++){
            b = n ^ a;
            if(  b == m - a ) {
                if(a == b){
                    count++;
                }else{
                    count += 2;
                }
            }
        }

        return count;
    }

    /**
     *
     *  wrong !!
     *
     */
    public int count_n(int m, int n){
        if(n == 0 && (m & 1) == 0 ){
            return 1;
        }

        if(m < 0 || n < 0 || m < n || ((m - n) & 1) == 1 ){
            return 0;
        }

        int n2 = ReverseBits3.bitsReverse(n);
        if( m - n - n2 * 2 > 0){
            return 0;
        }

        int count = 0;
        while( n  > 0 ){
            count++;
            n &= (n - 1);
        }

        return (count << 1);
    }

    @Test
    public void test(){
        for(int m = 1; m < 10000; m ++){
            for(int n = 0; n < 10000; n++){

                int r = count(m, n);
                int r2 = count_2(m, n);

                //if(r > 0){
                if(r != r2){
                    System.out.println(String.format("%d, %d - %d, %d", m, n, r, r2) );
                }

            }
        }

    }
}
