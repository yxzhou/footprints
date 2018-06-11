package fgafa.todo.others;

/**
 *
 */

public class MaxDecimal {

    public String maxDecimal(String num1, String num2, int k){
        int totalLength = num1.length() + num2.length();
        int resultSize = totalLength >= k ? k : totalLength;

        char[] result = new char[resultSize];
        maxDecimal(num1.toCharArray(), 0, num2.toCharArray(), 0,  result, 0);
        return new String(result);
    }

    private void maxDecimal(char[] num1, int i, char[] num2, int j, char[] result, int k){
        int width = (num1.length - i) + (num2.length - j) - (result.length - 0) + 1;

        int maxDigitPosition1 = maxDigitPosition(num1, i, width);
        int maxDigitPosition2 = maxDigitPosition(num2, j, width);

        if(num1[maxDigitPosition1] > num2[maxDigitPosition2]){
            result[k] = num1[maxDigitPosition1];
            maxDecimal(num1, maxDigitPosition1+1, num2, j, result, k + 1);
        }else if(num1[maxDigitPosition1] < num2[maxDigitPosition2]){
            result[k] = num2[maxDigitPosition2];
            maxDecimal(num1, i, num2, maxDigitPosition2+1, result, k + 1);
        }else{
            result[k] = num1[maxDigitPosition1];

            char[] resultClone = result.clone();
            maxDecimal(num1, maxDigitPosition1+1, num2, j, result, k + 1);
            maxDecimal(num1, i, num2, maxDigitPosition2+1, resultClone, k + 1);

            for(int p = k + 1; p < result.length; p++){
                if(result[p] < resultClone[p]){
                    result = resultClone;
                    break;
                }
            }
        }

    }

    private int maxDigitPosition(char[] num, int i, int width){
        int maxDigitPosition = i;

        for(int p = i + 1; p < i + width; p++ ){
            if(num[p] > num[maxDigitPosition]){
                maxDigitPosition = p;
            }
        }

        return maxDigitPosition;
    }


}


