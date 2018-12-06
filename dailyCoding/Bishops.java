package fgafa.dailyCoding;

/**
 *
 * On our special chessboard, two bishops attack each other if they share the same diagonal. This includes bishops that have another bishop located between them, i.e. bishops can attack through pieces.

 You are given N bishops, represented as (row, column) tuples on a M by M chessboard. Write a function to count the number of pairs of bishops that attack each other. The ordering of the pair doesn't matter: (1, 2) is considered the same as (2, 1).

 For example, given M = 5 and the list of bishops:

 (0, 0)
 (1, 2)
 (2, 2)
 (4, 0)

 The board would look like this:

 [b 0 0 0 0]
 [0 0 b 0 0]
 [0 0 b 0 0]
 [0 0 0 0 0]
 [b 0 0 0 0]

 You should return 2, since bishops 1 and 3 attack each other, as well as bishops 3 and 4.
 *
 * Tags: google
 *
 */

public class Bishops {

    /**
     *  Time O(m)
     */
    public int bishops(int m, int[][] postions){
        if(m <= 1 || postions == null || postions.length <= 1){
            return 0;
        }

        int size = m * 2 + 1;
        int[] diagonals = new int[size];
        int[] reverseDiagonals = new int [size];

        for(int[] position : postions){
            diagonals[position[0] + position[1]]++;
            reverseDiagonals[m + position[0] - position[1]]++;
        }

        int result = 0;

        for(int count : diagonals){
            if(count >= 2){
                result += count * (count - 1) / 2;
            }
        }

        for(int count : reverseDiagonals){
            if(count >= 2){
                result += count * (count - 1) / 2;
            }
        }

        return result;
    }

}
