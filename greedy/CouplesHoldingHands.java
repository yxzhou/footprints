package fgafa.greedy;

/**
 * Leetcode #765
 *
 * N couples sit in 2N seats arranged in a row and want to hold hands. We want to know the minimum number of swaps so that every couple is sitting side by side. A swap consists of choosing any two people, then they stand up and switch seats.
 *
 * The people and seats are represented by an integer from 0 to 2N-1, the couples are numbered in order, the first couple being (0, 1), the second couple being (2, 3), and so on with the last couple being (2N-2, 2N-1).
 *
 * The couples' initial seating is given by row[i] being the value of the person who is initially sitting in the i-th seat.
 *
 * Example 1:
 *
 * Input: row = [0, 2, 1, 3]
 * Output: 1
 * Explanation: We only need to swap the second (row[1]) and third (row[2]) person.
 * Example 2:
 *
 * Input: row = [3, 2, 0, 1]
 * Output: 0
 * Explanation: All couples are already seated side by side.
 * Note:
 *
 * len(row) is even and in the range of [4, 60].
 * row is guaranteed to be a permutation of 0...len(row)-1.
 *
 */

public class CouplesHoldingHands {



    //greedy
    public int minSwapsCouples(int[] row) {
        int len = row.length;

        int[] v2p = new int[len];
        for(int i = 0; i < len; i++){
            v2p[row[i]] = i;
        }

        int count = 0;
        int x;
        int y;
        for(int i = 0, j = 1; j < len; i +=2, j +=2 ){
            x = row[i];
            y = (x ^ 1);

            if(row[j] == y){
                continue;
            }

            count++;

            row[v2p[y]] = row[j];
            v2p[row[j]] = v2p[y];

            //row[j] = y;
            //v2p[y] = j;
        }

        return count;
    }

}
