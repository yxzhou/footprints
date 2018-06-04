package fgafa.todo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.lang.Integer;
import java.util.Queue;

/**
 *
 *  A magical string S consists of only '1' and '2' and obeys the following rules:

 The string S is magical because concatenating the number of contiguous occurrences of characters '1' and '2' generates the string S itself.

 The first few elements of string S is the following: S = "1221121221221121122……"

 If we group the consecutive '1's and '2's in S, it will be:

 1 22 11 2 1 22 1 22 11 2 11 22 ......

 and the occurrences of '1's or '2's in each group are:

 1 2	2 1 1 2 1 2 2 1 2 2 ......

 You can see that the occurrence sequence above is the S itself.

 Given an integer N as input, return the number of '1's in the first N number in the magical string S.

 Note: N will not exceed 100,000.

 Example 1:
 Input: 6
 Output: 3
 Explanation: The first 6 elements of magical string S is "12211" and it contains three 1's, so return 3.
 *
 *
 *  Ans:  how to magic number grow?
 *
 *  the initial value is  1, 2, 2,  the index is S[2] = 2, the tail is 2, the new tail is 1
 *
 *  #   Magic String            S[Index]       Tail    NewC        append the NewC S[Index] times
 *  1   1,2,2                   S[2] = 2        2       !2 -> 1     append 1 twice
 *  2   1,2,2,1,1               S[3] = 1        1       !1 -> 2     append 2 once
 *  3   1,2,2,1,1,2             S[4] = 1        2       !2 -> 1     append 1 once
 *  4   1,2,2,1,1,2,1           S[5] = 2        1       !1 -> 2     append 2 twice
 *  5   1,2,2,1,1,2,1,2,2
 */
public class MagicString {

    public int magicalString(int k){
        int result = 1;
        Integer int_1 = Integer.valueOf(1);
        Integer int_2 = Integer.valueOf(2);
        Queue<Integer> queue = new LinkedList<>(Arrays.asList(int_2));

        Integer tail = int_2;
        int top;
        int total = 3;
        while(total < k){
            top = queue.poll().intValue();

            if(tail.equals(int_2)){
                tail = int_1;
                result += top;
            }else{
                tail = int_2;
            }

            total += top;
            for( ; top > 0; top-- ){
                queue.add(tail);
            }
        }

        if(total > k && tail.intValue() == 1){
            result--;
        }

        return result;
    }

   public int magicalString_2(int k){
       int result = 1;
       Integer int_1 = Integer.valueOf(1);
       Integer int_2 = Integer.valueOf(2);
       List<Integer> lst = new LinkedList<>();
       lst.add(int_1);
       lst.add(int_2);
       lst.add(int_2);

       Integer num;
       for(int index = 2; lst.size() < k; index++){
            num = int_2;
            if(lst.get(lst.size() - 1).equals(int_2)){
                num = int_1;
                result += lst.get(index).intValue();
            }

            for(int i = lst.get(index); i > 0; i-- ){
                lst.add(num);
            }
       }

       for(int end = lst.size() - 1; end >= k; end--){
           if(lst.get(end) == 1){
               result--;
           }
       }

       return result;
   }

   public static void main(String[] args){
       MagicString sv = new MagicString();

       for(int k = 1; k < 50; k++){
           System.out.println(String.format("k=%d, output:%d - %d", k, sv.magicalString(k), sv.magicalString_2(k)));
       }

   }

}
