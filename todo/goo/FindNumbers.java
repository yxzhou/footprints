package todo.goo;

/**
 *  Given a postive number n, how many numbers in [0, n] whose binary string doesn't include "11"
 *
 *  Example
 *  input n = 5,
 *  output 5, because 0,1,2,4,5, these 5 numbers whose binary string doesn't include "11".
 *  0 - 000
 *  1 - 001
 *  2 - 010
 *  3 - 011
 *  4 - 100
 *  5 - 101
 *
 *  Solution
 *  The binary string of n will be 1**--*, it has log(2, n) + 1 digits. Define the number of the digits are f(0, n).
 *  if n is 11*--*, f(0, n) = f(000--0, 100--0) + f(100--0, 110--0) + f(110--0, n)
 *
 *    f(000--0, 100--0) ( log(2, n) + 1 digits ) =
 *    f(100--0, 110--0) ( log(2, n) + 1 digits ) = f(00--0, 10--0) (log(2, n) - 1 digits)
 *    f(110--0, n) =  n - 110--0 + 1. because all integer between 110--0 and 11*--* is the Integer
 *
 */

public class FindNumbers {

    public int findIntegers(int n){
        if(n < 3){
            return n + 1;
        }else if(n < 6){
            return n;
        }

        int x = log(n, 2);
        int[] f = new int[x + 1];
        f[0] = 0;
        f[1] = 0;
        f[2] = 1;

        for(int i = 3, j = 1; i <= x; i++, j <<= 1){
            f[i] = 2 * f[i - 2] + f[i - 3] + 3 * j;
        }

        int result = n + 1;
        int y = 0;

        while(n > 1){
            x = log(n, 2);
            y = 3 << (x - 1);

            if( y == (n & y)){
                result -= n - y + 1;
                n = y - 1;
            }

            result -= f[x];
            n &= (1 << x) - 1 ;
        }

        return result;
    }

    private static int log(int x, int base)
    {
        return (int) (Math.log(x) / Math.log(base));
    }


    public int findIntegers_n(int num){
        if (num < 2) {
            return num + 1;
        }

        StringBuilder str = new StringBuilder(Integer.toBinaryString(num)).reverse();
        int k = str.length();

        int[] f = new int[k];
        f[0] = 1;
        f[1] = 2;
        for (int i = 2; i < k; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }

        int ans = 0;
        for (int i = k - 1; i >= 0; i--) {
            if (str.charAt(i) == '1') {
                ans += f[i];
                if (i < k - 1 && str.charAt(i + 1) == '1') {
                    return ans;
                }
            }
        }
        ans++;
        return ans;
    }


    public static void main(String[] args){
        FindNumbers sv = new FindNumbers();

        for(int i = 1; i <= Math.pow(2, 6); i++){
            System.out.println(String.format("%d - %d - %d", i, sv.findIntegers(i), sv.findIntegers_n(i)));

        }
    }

}
