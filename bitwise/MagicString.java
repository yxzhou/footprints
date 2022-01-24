package bitwise;


import java.util.BitSet;
import java.util.LinkedList;
import java.util.Queue;
import junit.framework.Assert;
import util.TimeCost;

/**
 * _https://www.lintcode.com/problem/1215
 *
 * A magical string S consists of only '1' and '2' and obeys the following rules:
 *
 * The string S is magical because concatenating the number of contiguous occurrences of characters '1' and '2'
 * generates the string S itself.
 *
 * The first few elements of string S is the following: S = "1221121221221121122……"
 *
 * If we group the consecutive '1's and '2's in S, it will be:
 * 1 22 11 2 1 22 1 22 11 2 11 22 ......
 *
 * and the occurrences of '1's or '2's in each group are:
 * 1 2	2 1 1 2 1 2 2 1 2 2 ......
 *
 * You can see that the occurrence sequence above is the S itself.

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
 *  #   Magic String            S[left]       Tail    NewC        append the NewC S[Index] times
 *  1   1,2,2                   S[2] = 2        2       !2 -> 1     append 1 twice
 *  2   1,2,2,1,1               S[3] = 1        1       !1 -> 2     append 2 once
 *  3   1,2,2,1,1,2             S[4] = 1        2       !2 -> 1     append 1 once
 *  4   1,2,2,1,1,2,1           S[5] = 2        1       !1 -> 2     append 2 twice
 *  5   1,2,2,1,1,2,1,2,2
 * 
 *  
 *  so, it need two index, S[left] decide how many to append to S, S[right]^3 decide what to append 
 *  To store the S, it can be
 *     m0) Queue, 
 *     m1) StringBuilder
 *     m2) int array
 *     m3) BitSet
 *  
 * 
 * 
 */
public class MagicString {

    public int magicalString_Queue(int n){
        if(n < 1){
            return 0;
        }
        if(n < 4){
            return 1;
        }
        
        int result = 1;
        
        Queue<Integer> queue = new LinkedList();
        queue.add(2);
        
        int tail = 2;
        int top;
        int total = 3;
        while(total < n){
            top = queue.poll();

            if(tail == 2){
                tail = 1;
                result += top;
            }else{
                tail = 2;
            }

            total += top;
            for( ; top > 0; top-- ){
                queue.add(tail);
            }
        }

        if(total > n && tail == 1){
            result--;
        }

        return result;
    }

   public int magicalString_Array(int n){
        if(n < 1){
            return 0;
        }
        if(n < 4){
            return 1;
        }

       int result = 1;

       int[] arr = new int[n + 2];
       arr[1] = 1;
       arr[2]= arr[3] = 2;

       int r = 3;
       int tail;
       for(int l = 3; r < n; l++){
           //tail = arr[r] ^ 3;
           tail = ( arr[r] == 1? 2 : 1);
           
           if( arr[l] == 1){
               arr[++r] = tail;
           }else{
               arr[++r] = tail;
               arr[++r] = tail;
           }
                   
           if(tail == 1){
               result += arr[l];
           }
       }
       
       while(r > n){
           if(arr[r] == 1){
               result--;
           }
           
           r--;
       }

       return result;
   }
   
    /**
     * @param n: an integer 
     * @return the number of '1's in the first N number in the magical string S
     */
    public int magicalString_StringBuilder(int n) {
        if(n < 1){
            return 0;
        }

        StringBuilder s = new StringBuilder("122");

        int sum = 1;
        int tail;
        int count;
        for(int l = 2, r = 2, k = 3; k < n; k++, l++){
            count = s.charAt(l) - '0';
            tail = ( s.charAt(r) == '1' ?  2 : 1);
 
            r += count;

            if(count == 1){
                s.append(tail);
            }else{
                s.append(tail).append(tail);
            }

            sum += (s.charAt(k) == '1'? 1 : 0);
        }

        return sum;
    }
    
    /**
     * @param n: an integer 
     * @return the number of '1's in the first N number in the magical string S
     */
    public int magicalString_BitSet(int n) {
       if(n < 1){
            return 0;
        }

        BitSet s = new BitSet();
        s.set(0);

        boolean count; //  true - 1, false - 2
        int r = 2;
        for(int l = 2; r < n; l++){ // r < n instead of k < n
            count = s.get(l);

            if(s.get(r)){ 
                if(count){
                    r++;
                }else{
                    r += 2;
                }
            }else{
                if(count){
                    s.set(++r);
                }else{
                    s.set(++r);
                    s.set(++r);
                }
            }
        }
        
        int sum = s.cardinality();
        while( r >= n ){
            sum -= s.get(r)? 1 : 0; 
            r--;
        }

        return sum;
    }

   public static void main(String[] args){
       MagicString sv = new MagicString();

       int[][] inputs = {
           //{k, magicalString(k)}
           {0, 0},
           {1, 1},
           {2, 1}, 
           {3, 1},
           {4, 2},
           {5, 3},
           {6, 3},
           {7, 4},
           {8, 4},
           {9, 4},
           {10, 5},
           {100, 49},
           {1000, 502},
           {10_000, 4996},
           {100_000, 49972},
           {1000_000, 499986}
       };
       
       for(int[] p : inputs){
           System.out.println(String.format("k = %d,  magicString(k): %d", p[0], p[1]));
           
           Assert.assertEquals(p[1], sv.magicalString_Queue(p[0]));
           Assert.assertEquals(p[1], sv.magicalString_Array(p[0]));
           Assert.assertEquals(p[1], sv.magicalString_StringBuilder(p[0]));
           Assert.assertEquals(p[1], sv.magicalString_BitSet(p[0]));
       }
       
       /** performance test */
       int[] cases = { 1_000, 10_000, 100_000, 1000_000, 1_000_000, 10_000_000, 100_000_000};
        
       TimeCost tc = TimeCost.getInstance();
       tc.init();
        
       for(int k : cases){           
           sv.magicalString_Queue(k);
           System.out.println("\nThe magicalString_Queue       of " + k + " timeCost:" + tc.getTimeCost());
           
           sv.magicalString_Array(k);
           System.out.println("The magicalString_Array       of " + k + " timeCost:" + tc.getTimeCost());
           
           sv.magicalString_StringBuilder(k);
           System.out.println("The magicalString_StringBuilder       of " + k + " timeCost:" + tc.getTimeCost());
           
           sv.magicalString_BitSet(k);
           System.out.println("The magicalString_BitSet       of " + k + " timeCost:" + tc.getTimeCost());
           
       }

   }

}
