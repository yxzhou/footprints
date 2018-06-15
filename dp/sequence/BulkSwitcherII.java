package fgafa.dp.sequence;

/**
 * 
 * There is a room with n lights which are turned on initially and 4 buttons on the wall. 
 * After performing exactly m unknown operations towards buttons, 
 * you need to return how many different kinds of status of the n lights could be.
 * 
 * Suppose n lights are labeled as number [1, 2, 3 ..., n], function of these 4 buttons are given below:
 *    Flip all the lights. 
 *    Flip lights with even numbers. 
 *    Flip lights with odd numbers. 
 *    Flip lights with (3k + 1) numbers, k = 0, 1, 2, ... 
 * 
 * Example 1: 
 *    Input: n = 1, m = 1. Output: 2 
 *    Explanation: Status can be: [on], [off] 
 *    
 * Example 2: 
 *    Input: n = 2, m = 1. Output: 3 
 *    Explanation: Status can be: [on, off], [off, on], [off, off] 
 *    
 * Example 3: 
 *    Input: n = 3, m = 1. Output: 4 
 *    Explanation: Status can be: [off, on, off], [on, off, on], [off, off, off], [off, on, on]. 
 *    
 * Note: n and m both fit in range [0, 1000].
 *
 * Solution:
 *   Found #1, to every light or n lights, the result after operations of (r1+r2) would be same of (r2+r1), 
 *     It means the sequence of operation does not matter. 
 *     
 *   Found #2, operation of (r1+ r1) == no change,  so is (r2 + r2), (r3 + r3) and (r4 + r4) 
 *   
 *   Found #3
 *   lights:          1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
 *   r1(flip all)     r1 r1 r1 r1 r1 r1 r1 r1 r1 r1  r1
 *   r2(flip even)       r2    r2    r2    r2    r2
 *   r3(flip odd)     r3    r3    r3    r3    r3     r3
 *   r4(flip 3k + 1)  r4       r4       r4           r4
 *
 *   the result of (7,8,9,10,11) is same of (1,2,3,4,5,6),  L6 is same as L2, and L5 is same as #3
 *   It means the result of first 4 lights is the result of n lights. (especially when n is bigger.)
 *   
 *   Found #4
 *   when m = 1, the operation can be r1, r2, r3 or r4.
 *      
 *        lights: 1, 2, 3, 4
 *          init: 1, 1, 1, 1    (1 means the light is on)      
 *      after r1: 0, 0, 0, 0    (In init, all are flip)
 *      after r2: 1, 0, 1, 0    (In init, even are flip)
 *      after r3: 0, 1, 0, 1    (In init, odd are flip)
 *      after r4, 0, 1, 1, 0  
 *      
 *      when n = 1, output 2,  ( the possible status is 1 or 0)
 *      when n = 2, output 3,  ( the possible status is 00, 10 or 01)
 *      when n = 3, output 4,  ( the possible status is 000, 101, 010 or 011)
 *      when n = 4, output 4,  ( the possible status is 0000, 1010, 0101 or 0110)
 *      when n > 4, output 4, same as when n == 4
 *      
 *   when m = 2, the operation can be:
 *      r1 + r1 or r2 + r2 or r3 + r3 or r4 + r4,  it means no change, 
 *                     result is 1111
 *      r1 + r2 = r3,  result is 0101
 *      r1 + r3 = r2   result is 1010
 *      r1 + r4        result is 1001 
 *      r2 + r3 = r1   result is 0000
 *      r2 + r4        result is 0011 
 *      r3 + r4        result is 1100
 *     
 *      when n = 1, output 2,  ( the possible status is 1 or 0)
 *      when n = 2, output 4,  ( the possible status is 11, 01, 10 or 00)
 *      when n = 3, output 7,  ( the possible status is 111, 010, 101, 100, 000, 001 or 110)
 *      when n = 4, output 7,  ( the possible status is 1111, 0101, 1010, 1001, 0000, 0011 or 1100)
 *      when n > 4, output 7, same as when n == 4
 *   
 *   when m = 3, the operation can be:
 *      3r1 = r1, ( 3r1 = r1 + r1 + r1 = r1) 
 *                                result is 0000
 *      3r2 = r2                  result is 1010   
 *      3r3 = r3                  result is 0101  
 *      3r4 = r4                  result is 1001
 *      2r1 + r2 = r2 ( same as 1010)
 *      2r1 + r3 = r3
 *      2r4 + r4 = r4             
 *      ---
 *      r1 + r2 + r3 = no change, result is 1111
 *      r1 + r2 + r4 = r3 + r4    result is 1100 
 *      r1 + r3 + r4 = r2 + r4    result is 0011
 *      r2 + r3 + r4 = r1 + r4    result is 1001
 *      
 *      when n = 1, output 2,  ( the possible status is 1 or 0)
 *      when n = 2, output 4,  ( the possible status is 00, 10, 01 or 11)
 *      when n = 3, output 7,  ( the possible status is 000, 101, 010, 100, 111, 110 or 001)
 *      when n = 4, output 8,  ( the possible status is 0000, 1010, 0101, 1001, 1111, 1100, 0011 or 1001)
 *      when n > 4, output 8, same as when n == 4
 *      
 *   when m = 4, the operation can be:
 *      no change, ( such as 4r1 or 4r2 or 2r1 + 2r2)
 *      r1         ( such as 32r4 + r2 + r3)
 *      r2         ( such as 3r1 + r3, r1 + r3 + 2r4)
 *      r3         ( such as 3r1 + r2, r1 + r2 + 2r4)
 *      r4         ( such as r1 + r2 + r3 + r4)  
 *      r1 + r4    ( such as 3r1 + r4)
 *      r2 + r4    
 *      r3 + r4 
 *      
 *   so the result as same as when m == 3   
 *      
 */

public class BulkSwitcherII {

}
