package fgafa.dp.sequence;

/**
 *  Leetcode #639
 *
 * A message containing letters from A-Z is being encoded to numbers using the following mapping way:
 *
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * Beyond that, now the encoded string can also contain the character '*', which can be treated as one of the numbers from 1 to 9.
 *
 * Given the encoded message containing digits and the character '*', return the total number of ways to decode it.
 *
 * Also, since the answer may be very large, you should return the output mod 109 + 7.
 *
 * Example 1:
 * Input: "*"
 * Output: 9
 * Explanation: The encoded message can be decoded to the string: "A", "B", "C", "D", "E", "F", "G", "H", "I".
 * Example 2:
 * Input: "1*"
 * Output: 9 + 9 = 18
 * Note:
 * The length of the input string will fit in range [1, 105].
 * The input string will only contain the character '*' and digits '0' - '9'.
 *
 */

public class DecodeWaysII {

    /**
     *  case1,  "0"
     *  case2,  "100"
     *
     *
     */

    public int numDecodings(String s) {
        if(null == s  || 0 == s.length()){
            return 0;
        }

        final int MOD = 1_000_000_007;
        final int ZERO = '0';
        final int STAR = '*' - ZERO;

        int p = s.charAt(0) - ZERO;
        int c;

        // if(p == star){
        //     return 9;
        // }
        if(p == 0 || (p != STAR && !isValid(p))){
            return 0;
        }

        long f1 = 1; // f[i - 1], the decode way by i - 1
        long f2 = 1; // f[i], the decode way by i

        if(p == STAR){
            f2 = 9;
        }

        for(int k = 1; k < s.length(); k++){
            c = s.charAt(k) - ZERO;

            if(c != STAR && c != 0 && !isValid(c) && !isValid(p, c)){
                return 0;
            }

            long tmp = 0;
            if(c == STAR){
                tmp = f2 * 9;

                if(p == 1){
                    tmp += f1 * 9;
                }else if(p == 2){
                    tmp += f1 * 6;
                }else if(p == STAR){
                    tmp += f1 * 15;
                }
            }else{
                if(isValid(c)){
                    tmp = f2;
                }

                if(p == STAR){
                    tmp += f1 + (c <= 6 ? f1 : 0);
                }else if(isValid(p, c)){
                    tmp += f1;
                }
            }

            f1 = f2;
            f2 = tmp % MOD;

            p = c;
        }

        return (int)f2;
    }

    private boolean isValid(int digit){
        return digit >= 1 && digit <= 9;
    }

    private boolean isValid(int digit1, int digit2){
        return (digit1 == 1  && (digit2 >= 0 && digit2 <= 9)) || (digit1 == 2 && (digit2 >= 0 && digit2 <= 6));
    }


    public static void main(String[] args) {

        DecodeWaysII sv = new DecodeWaysII();

        String[] str = {"", "-1", "ab", "01"
                , "1", "10", "11", "21", "28", "1211", "111221",
                "*", "1*", "1*1*0", "1*72*",
                };
        int[] n = {0, 0, 0, 0
                , 1, 1, 2, 2, 1, 5, 13
                , 9, 18, 40, 285
                };

        for(int i=0; i<str.length; i++){
            System.out.println("\nDecodeWayss: " + str[i]);

            System.out.println("Result:\t" + sv.numDecodings(str[i]) );


            System.out.println("Expect:\t" + n[i] );

        }

    }

}
