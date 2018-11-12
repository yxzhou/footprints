package fgafa.dailyCoding;

/**
 *
 * Implement integer exponentiation. That is, implement the pow(x, y) function, where x and y are integers and returns x^y.

 Do this faster than the naive method of repeated multiplication.

 For example, pow(2, 10) should return 1024.
 *
 * tags:  google
 *
 */

public class PowXY {

    public int pow(int x, int y){
        int result = 1;
        int factor = x;

        while(y > 0){
            if( 1 == (y & 1)){
                result *= factor;
            }

            factor *= factor;
            y >>= 1;
        }

        return  result;
    }

    public static void main(String[] args){

        PowXY sv = new PowXY();

        int[][] cases = {
                {2, 5},
                {2, 6},
                {2, 7},
                {2, 8},
                {2, 9},
                {2, 10}
        };

        for(int x = 2, y = 1; y < 11; y++){
            System.out.println(String.format("pow(%d, %d) = %d", x, y, sv.pow(x, y)));
        }

    }

}
