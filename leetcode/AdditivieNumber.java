package leetcode;


/**
 * Additive number is a string whose digits can form additive sequence.

 A valid additive sequence should contain at least three numbers. Except for the first two numbers, each subsequent number in the sequence must be the sum of the preceding two.

 For example:
 "112358" is an additive number because the digits can form an additive sequence: 1, 1, 2, 3, 5, 8.

 1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
 "199100199" is also an additive number, the additive sequence is: 1, 99, 100, 199.
 1 + 99 = 100, 99 + 100 = 199
 Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.

 Given a string containing only digits '0'-'9', write a function to determine if it's an additive number.

 Follow up:
 How would you handle overflow for very large input integers?
 */


public class AdditivieNumber {

    public boolean isAdditiveNumber(String num) {
        if(null == num ){
            return false;
        }

        int mid = (num.length() - 1) / 2;
        for(int firstEnd = 0; firstEnd < mid; firstEnd++ ){
            if(num.charAt(0) == '0' && firstEnd > 0){
                continue;
            }

            for(int secondEnd = firstEnd + 1; secondEnd <= mid; secondEnd++){
                if(num.charAt(firstEnd) == '0' && secondEnd > firstEnd){
                    continue;
                }

                if(dfs(num, 0,firstEnd + 1, secondEnd + 1)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(String num, int firstStart, int secondStart, int secondEnd){
        long first = Long.valueOf(num.substring(firstStart, secondStart));
        long second = Long.valueOf(num.substring(secondStart, secondEnd));

        String sum = String.valueOf(first + second);


        if(!num.substring(secondEnd).startsWith(sum)){
            return false;
        }
        if (secondEnd + sum.length() == num.length()){
            return true;
        }

        return dfs(num, secondStart, secondEnd + 1, secondEnd + num.length() );
    }

}
