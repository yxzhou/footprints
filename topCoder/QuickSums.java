package topCoder;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Problem Statement
 *    http://community.topcoder.com/stat?c=problem_statement&pm=2829&rd=5072
 *
 * Given a string of digits, find the minimum number of additions required for the string to equal some
 * target number. Each addition is the equivalent of inserting a plus sign somewhere into the string of
 * digits. After all plus signs are inserted, evaluate the sum as usual. For example, consider the string "12"
 * (quotes for clarity). With zero additions, we can achieve the number 12. If we insert one plus sign
 * into the string, we get "1+2", which evaluates to 3. So, in that case, given "12", a minimum of 1
 * addition is required to get the number 3. As another example, consider "303" and a target sum of 6.
 * The best strategy is not "3+0+3", but "3+03". You can do this because leading zeros do not change
 * the result.
 *
 * Write a class QuickSums that contains the method minSums, which takes a String numbers and an int sum.
 * The method should calculate and return the minimum number of additions required to create an
 * expression from numbers that evaluates to sum. If this is impossible, return -1.
 *
 * Constraints
 *  -	numbers will contain between 1 and 10 characters, inclusive.
 *  -	Each character in numbers will be a digit.
 *  -	sum will be between 0 and 100, inclusive.
 *
 * Examples
 * 0) input "99999" and 45
 *    Returns: 4
 * In this case, the only way to achieve 45 is to add 9+9+9+9+9. This requires 4 additions.
 *
 * 1) input "1110" and 3
 *    Returns: 3
 *    Be careful with zeros. 1+1+1+0=3 and requires 3 additions.
 *
 * 2) input "0123456789" and 45
 *    Returns: 8
 *
 * 3) input "99999" and 100
 *    Returns: -1
 *
 * 4) input "382834" and 100
 *    Returns: 2
 * There are 3 ways to get 100. They are 38+28+34, 3+8+2+83+4 and 3+82+8+3+4. The minimum required is 2.
 *
 * 5) input "9230560001" and 71
 *    Returns: 4
 *
 *
 */

public class QuickSums {

    public int minSums(String numbers, int sum){
        if(null == numbers || numbers.isEmpty() || sum < 0){
            return -1;
        }

        Map<String, Integer> cache = new HashMap<>();
        dfs(numbers, 0, sum, sum, 0, cache);

        String resultKey = buildKey(numbers, sum);
        return cache.containsKey(resultKey) ? cache.get(resultKey) - 1 : -1;
    }

    private void dfs(String numbers, int i, int totalSum, long currSum, int count, Map<String, Integer> cache){

        if(currSum == 0 && i == numbers.length()){
            String resultKey = buildKey(numbers, totalSum);
            if(cache.containsKey(resultKey)){
                cache.put(resultKey, Math.min(cache.get(resultKey), count));
            }else{
                cache.put(resultKey, count);
            }

            return;
        }else if(currSum < 0 || i >= numbers.length()){
            return;
        }


        long num = 0; // maybe long
        for(int j = i; j < numbers.length(); j++){
            num = num * 10 + (numbers.charAt(j) - '0');

            if(num > currSum){
                break;
            }

            dfs(numbers, j + 1, totalSum, currSum - num, count + 1, cache);
        }

    }

    private String buildKey(String numbers, int sum){
        return numbers + " " + sum;
    }

    public static void main(String[] args){

        String[][] cases = {
                {"99999", "45", "4"},
                {"1110", "3", "3"},
                {"0123456789", "45", "8"},
                {"99999", "100", "-1"},
                {"382834", "100", "2"},
                {"9230560001", "71", "4"}
        };


        QuickSums sv = new QuickSums();

        for(int i = 0; i < cases.length; i++){
            int result = sv.minSums(cases[i][0], Integer.parseInt(cases[i][1]));

            System.out.println(String.format("%s, %s \t %d \t %b", cases[i][0], cases[i][1], result, result == Integer.parseInt(cases[i][2])));
        }

    }


}
