package fgafa.todo;

/**
 * 
 * In the computer world, use restricted resource you have to generate maximum benefit is what we always want to pursue.

For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand, there is an array with strings consisting of only 0s and 1s.

Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s. Each 0 and 1 can be used at most once.

Note:
The given numbers of 0s and 1s will both not exceed 100
The size of given string array won't exceed 600.
Example 1:
Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
Output: 4
Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”

Example 2:
Input: Array = {"10", "0", "1"}, m = 1, n = 1
Output: 2
Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
 *
 */

public class OnesAndZeros {
    
    public int findMaxForm(String[] strs, int m, int n) {
        if (null == strs || 0 == strs.length || strs.length > 600 || m < 0 || m > 100 || n < 0 || n > 100) {
            return 0;
        }

        int[][] maxs = new int[m + 1][n + 1]; // default all are 0
        maxs[0][0] = 1;

        int numberOf0;
        int numberOf1;
        for (String str : strs) {
            if(null == str || 0 == str.length()){
                continue;
            }
            
            numberOf0 = countZeros(str);
            numberOf1 = str.length() - numberOf0;

            for (int i = m; i >= 0; i--) {
                for (int j = n; j >= 0; j--) {
                    if (i < numberOf0 || j < numberOf1 || maxs[i - numberOf0][j - numberOf1] == 0) {
                        continue;
                    }

                    maxs[i][j] = Math.max(maxs[i][j], maxs[i - numberOf0][j - numberOf1] + 1);
                }
            }
        }

        int max = 0;
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                max = Math.max(max, maxs[i][j]);
            }
        }

        return max - 1;
    }
    
    private int countZeros(String str){
        int result = 0;
        
        for(char c : str.toCharArray()){
            if(c == '0'){
                result++;
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        

    }

}

