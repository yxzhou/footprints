package fgafa.game.twentyFour;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * The 24 game is played as follows. You are given a list of four integers, each between 1 and 9, in a fixed order. By placing the operators +, -, *, and / between the numbers, and grouping them with parentheses, determine whether it is possible to reach the value 24.
 * <p>
 * For example, given the input [5, 2, 7, 8], you should return True, since (5 * 2 - 7) * 8 = 24.
 * <p>
 * Write a function that plays the 24 game.
 * <p>
 *
 * Thoughts:
 * The final expression, example (5 * 2 - 7) * 8
 * 1) It includes four integers. that is from the input, new int[]{a, b, c, d}.
 * It can be abcd, abdc or adbc ---, total it's 4*3*2*1.
 *
 * 2) Between every two integers, there is a operator,  {+, -, *, /},
 * It can be +++ or +--, total it's 4*4*4
 *
 * 3) The parentheses is used to change the calculation sequence. With parentheses,
 * It can calculate the second operator at first,  5 * (2 - 7) * 8
 * The calculate the third operator at second,   5 * ( (2 - 7) * 8 )
 * Total, it's 3*2*1
 */

public class TwentyFour2 {

    public boolean get24(int[] inputs) {
        if (null == inputs || inputs.length != 4) {
            throw new IllegalArgumentException("");
        }

        return fillInIntegers(inputs, new int[3], 0);
    }

    private boolean fillInIntegers(int[] inputs, int[] operatorIds, int end) {
        if (end == inputs.length - 1) {
            if (fillInOperatorIds(inputs, operatorIds, 0)) {
                return true;
            }
        }

        for (int i = end; i < inputs.length; i++) {
            swap(inputs, i, end);

            if (fillInIntegers(inputs, operatorIds,end + 1)) {
                return true;
            }

            swap(inputs, i, end);
        }

        return false;
    }

    private boolean fillInOperatorIds(int[] integers, int[] operatorIds, int end) {
        if (end == operatorIds.length) {
            return calculate(integers, operatorIds, 3) == 24;
        }

        // There are 4 operator {'+','-','*','/'};
        for (int id = 0; id < 4; id++) {
            operatorIds[end] = id;

            if (fillInOperatorIds(integers, operatorIds, end + 1)) {
                return true;
            }
        }

        return false;
    }

    private void swap(int[] inputs, int i, int j) {
        int tmp = inputs[i];
        inputs[i] = inputs[j];
        inputs[j] = tmp;
    }


    /**
     * calculate the expression,
     *
     * @param integers,    it includes integers, firstly it's four integers.
     * @param operatorIds, it includes operatorIds, firstly it's three operatorIds.
     * @return
     */
    private int calculate(int[] integers, int[] operatorIds, int end) {
        if (end == 1) {
            int v = calculate(operatorIds[0], integers[0], integers[1]);

            return v;
        }

        for (int i = 0; i < end; i++) {
            int v = calculate(operatorIds[i], integers[i], integers[i + 1]);
            if (v < 0) {
                continue;
            }

            int tmp1 = integers[i];
            int tmp2 = integers[i + 1];
            int tmpOperatorId = operatorIds[i];

            integers[i] = v;
            System.arraycopy(integers, i + 2, integers, i + 1, integers.length - i - 2);
            System.arraycopy(operatorIds, i + 1, operatorIds, i, operatorIds.length - i - 1);

            if ( (v = calculate(integers, operatorIds, end - 1)) == 24) {
                return 24;
            }

            //backtracking
            System.arraycopy(integers, i + 1, integers, i + 2, integers.length - i - 2);
            integers[i] = tmp1;
            integers[i + 1] = tmp2;
            System.arraycopy(operatorIds, i, operatorIds, i + 1, operatorIds.length - i - 1);
            operatorIds[i] = tmpOperatorId;

            //for print the expression
//            if(v == 24){
//                if(end == 3) {
//                    System.out.print(String.format("%s %s ", Arrays.toString(integers), Arrays.toString(operatorIds)));
//                }
//
//                return 24;
//            }
        }

        return -1;
    }


    /**
     * calculate the 2 input integer
     *
     * @param operatorId, from
     * @param m
     * @param n
     * @return
     */
    private int calculate(int operatorId, int m, int n) {
        switch (operatorId) {
            case 0:
                return m + n;
            case 1:
                return m - n;
            case 2:
                return m * n;
            case 3:
                if(n == 0 || m % n > 0){
                    return -1;
                }
                return m / n;
            default:
                return -1;
        }
    }


    @Test
    public void test() {
        Assert.assertTrue(-1 == calculate(3, 4, 3));

        Assert.assertTrue(get24(new int[]{5, 2, 7, 8}));
        Assert.assertTrue(get24(new int[]{3, 2, 5, 4}));


        Assert.assertFalse(get24(new int[]{1, 1, 1, 1}));


        for(int i = 1; i < 10; i++){
            for(int j = i; j < 10; j++){
                for(int p = j; p < 10; p++){
                    for(int q = p; q < 10; q++) {

                        boolean result = get24(new int[]{i, j, p, q});
                        System.out.print(String.format("{%d, %d, %d, %d} \t %b \n", i, j, p, q, result));

                    }
                }
            }
        }
    }
}

  


