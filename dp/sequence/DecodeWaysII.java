package dp.sequence;

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
 * 
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
         if(s == null || s.isEmpty()){
            return 0;
        }

        final long MOD = 1_000_000_007;

        long f1 = 0;
        long f2 = 1;
        long tmp;

        char pre = '0';

        for(char c : s.toCharArray()){
            if(c != '*' && (c < '0' || c > '9' || (c == '0' && ( pre == '0' || (pre != '*' && pre > '2'))) )){
                return 0;
            }

            if(c == '*'){
                tmp = 9 * f2;
                if(pre == '*'){
                    tmp += 15 * f1; 
                }else if(pre == '1'){
                    tmp += 9 * f1; 
                }else if(pre == '2'){
                    tmp += 6 * f1; 
                }
            }else if(c == '0'){
                tmp = f1;
                if(pre == '*'){
                    tmp += f1;
                }
            }else{
                tmp = f2;
                if(pre == '*'){
                    tmp += f1 = (c < '7'? f1 : 0);
                }else if(pre == '1' || (pre == '2' && c < '7') ){
                    tmp += f1;
                }
            }

            f1 = f2;
            f2 = tmp % MOD;

            pre = c;
        }

        return (int)f2;
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
