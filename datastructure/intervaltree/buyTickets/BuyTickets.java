package fgafa.datastructure.intervaltree.buyTickets;

import java.util.Arrays;

/**
 *  * Sample Input
     4
     0 77
     1 51
     1 33
     2 69
     4
     0 20523
     1 19243
     1 3890
     0 31492

     * Sample Output
     77 33 69 51
     31492 20523 3890 19243
 */
public class BuyTickets {

    public int[] test(int[] indexes, int[] values){

        int length = indexes.length;

        int[] spaces = new int[length * 2 - 1]; //interval tree
        initSpaces(spaces, 0, 0, length - 1);

        int[] result = new int[length];
        for(int i = length - 1; i >= 0; i--){
            assignSpace(spaces, 0, 0, length - 1, indexes[i], result, values[i]);
        }

        return result;
    }

    private void initSpaces(int[] spaces, int position, int left, int right){

        spaces[position] = right - left + 1;

        if(left < right){
            int middle = left + (right - left) / 2;
            int rightSon = position * 2 + 1;
            initSpaces(spaces, rightSon, left, middle);
            initSpaces(spaces, rightSon + 1, middle + 1, right);
        }
    }

    private void assignSpace(int[] spaces, int position, int left, int right, int expectedSpaceIndex, int[] result, int value){

        //
        spaces[position]--;

        if(left == right){
            result[left] = value;
            return;
        }

        int middle = left + (right - left) / 2;
        int leftSon = position * 2 + 1;

        if(spaces[leftSon] > expectedSpaceIndex){
            assignSpace(spaces, leftSon, left, middle, expectedSpaceIndex, result, value);
        }else{
            assignSpace(spaces, leftSon + 1, middle + 1, right, expectedSpaceIndex - spaces[leftSon], result, value);
        }

    }


    public static void main(String[] args){

        int[][][] input = {
            {
                { 0, 1, 1, 2 },
                { 77, 51, 33, 69 }
            },
            {   {0, 1, 1, 0},
                {20523, 19243, 3890, 31492}
            }
        };

        BuyTickets sv = new BuyTickets();

        for(int i = 0; i < input.length; i++){
            Arrays.stream(sv.test(input[i][0], input[i][1])).mapToObj(x -> x + "\t").forEach(System.out::print);

            System.out.println();
        }

    }
}
