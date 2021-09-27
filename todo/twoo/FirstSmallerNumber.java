package todo.twoo;

/**
 *  _https://www.jiuzhang.com/solution/the-previous-number/
 *
 *  For an array, for each element, find the value of the first smaller element before it. If not, then output it itself.
 *
 *  S:  similar with FirstHigherStudent, Monotonous stack
 */

public class FirstSmallerNumber {

    public int[] getPreviousNumber(int[] nums){
        int[] result = new int[nums.length];
        int[] stack = new int[nums.length];

        int top = -1;
        for (int i = 0; i < nums.length; i++) {
            while (top != -1 && nums[i] <= stack[top]) {
                top--;
            }

            stack[++top] = nums[i];

            if(top == 0){
                result[i] = stack[top];
            }else{
                result[i] = stack[top - 1];
            }
        }

        return result;
    }
}
