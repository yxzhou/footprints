package graph.eulerPathAndCircuit;

import util.Misc;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *  Problem:
 *  If give you 0 and 1, it can construct 00, 01, 10 and 11.
 *  The shortest string to include them all are 00110 or 01100 or 10011
 *
 *  Now give you 0, 1, -, 9, it can construct 4-digits string, 0000, 0001, -, 9999.
 *  Would you build the shortest string to include them all ?
 *
 *  Thourght:
 *  It's a Euler Circuit issue.
 *
 *  To 0000, 0001, - , 9999, totally it's n = 10*10*10*10 vertices. The length of the shortest string is n + 1
 *
 */

public class EulerCircuitII {

    public String getEulerCircuit(char[] arr, int k){
        Map<String, Integer> map = new HashMap<>();

        String[] starts = new String[2];

        StringBuilder tmp = new StringBuilder();
        for(int i = 1; i < k; i++){
            tmp.append('0');
        }

        starts[0] = tmp.toString();
        starts[1] = String.valueOf(arr);

        StringBuilder result = new StringBuilder();

        final int N = (int)Math.pow(arr.length, k);
        final int FULL = ( 1 << arr.length ) - 1;//example 0x11 1111 1111.

        for(int i = 0; i < starts.length && N + 1 != result.length(); i++){
            String curr = starts[i];
            result.append(curr);
            int state;

            for(int j = 0; j <= N; j++){
                if(!map.containsKey(curr)){
                    map.put(curr, 0);
                }

                int last = -1;
                state = map.get(curr);

                if(state == FULL){
                    int length = result.length();

                    last = char2Int(result.charAt(length - 1));
                    result.deleteCharAt(length - 1);

                    curr = result.substring(result.length() - k + 1);

                    state = map.get(curr);
                    state = setBit(state, last, false);
                    map.put(curr, state);
                }

                int p = indexOfZero(state, last);

                state = setBit(state, p, true);
                map.put(curr, state);

                result.append(p);
                curr = result.substring(result.length() - k + 1);

            }

        }


        return result.toString();
    }

    /*
     * get the bit in the index position of n
     *
     * @return: false means 0; true means 1
     *
     */
    private static boolean getBit(int n, int index) {
        return (n & (1 << index)) > 0;

    }
    /*
     * set the bit in the index position of n
     *
     * @boolean b, true means to set 1, false means to set 0
     * @return: the new int
     *
     */
    private static int setBit(int n, int index, boolean b) {
        if(b)
            return (n | (1 << index));
        else
            return (n & ~(1 << index));

    }


    private int indexOfZero(int value, int start){
        int i = start + 1;
        int base = ( 1 << i );

        for( ; ( value & base ) == 1; base <<= 1, i++ );

        return i; // '0' is 48
    }

    private static final int ASCII_VALUE_OF_ZERO = 48; // '0' is 48, '1' is 49,

    public static int char2Int(char c){
        return (int)c - ASCII_VALUE_OF_ZERO;
    }

    public static char int2Char(int digit){
        return   (char) (ASCII_VALUE_OF_ZERO + digit);
    }

    public static void main(String[] args) {
        EulerCircuitII sv = new EulerCircuitII();

        String[] arr = {"10",  "9876543210"};
        int[] kk = {2, 4};

        for(int i = 0; i < arr.length; i++){
            System.out.println(String.format("\nInput: %s, %d\nOutput:%s", Misc.array2String(arr[i].toCharArray()), kk[i], sv.getEulerCircuit(arr[i].toCharArray(), kk[i])));
        }
    }

}
