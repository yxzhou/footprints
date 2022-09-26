package sweepLine.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1045
 * 
 *
 * A string S of lowercase letters is given. We want to partition this string into as many parts as possible so that
 * each letter appears in at most one part, and return a list of integers representing the size of these parts.
 *
 * Notes:
 *   1.S will have length in range [1, 500].
 *   2.S will consist of lowercase letters ('a' to 'z') only.
 *
 * Example 1:
 * 	Input:  S = "ababcbacadefegdehijhklij"
 * 	Output:  [9,7,8]
 * 	Explanation:
 * 	The partitions are "ababcbaca", "defegde", "hijhklij".
 * 	A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
 *
 * Example 2:
 * 	Input: S = "abcab"
 * 	Output:  [5]
 * 	Explanation:
 * 	We can not split it.
 * 
 * Thoughts:
 *   each letter appears in at most one part.
 *   Define n as the length of s. m <= 26 as the number of lowercase letters.
 * 
 *   s1, each letter has a interval {start, end}, merge all intervals. 
 *   Time complexity O(n + 26 * lg26)  space O(26)
 * 
 *   s2, two point, greedy, similar with JumpGame
 *   Time complexity O(2n)  space O(26)
 *  
 *
 */

public class PartitionLabels {

    /**
     * @param s: a string
     * @return a list of integers representing the size of these parts
     */
    public List<Integer> partitionLabels_IntervalMerge(String s) {
        List<Integer> result = new LinkedList<>();

        if(s == null || s.length() == 0){
            return result;
        }

        int[][] intervals = new int[26][2];// lowercase character to {start, end}
        for(int[] pair : intervals){
            pair[0] = -1;
            pair[1] = -1;
        }

        int c;
        for(int i = 0, n = s.length(); i < n; i++){
            c = s.charAt(i) - 'a';
            
            if(intervals[c][0] == -1){
                intervals[c][0] = i;
                intervals[c][1] = i;
            }else{
                intervals[c][1] = i;
            }
        }

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]) );

        //merge interval, OR
        int[] pre = null;
        for(int[] curr : intervals){
            if(curr[0] == -1){
                continue;
            }
            
            if(pre == null){
                pre = curr;
            }else if(pre[1] < curr[0]){
                result.add(pre[1] - pre[0] + 1);
                pre = curr;
            }else{
                pre[1] = Math.max(pre[1], curr[1]);
            }
        }

        result.add(pre[1] - pre[0] + 1);

        return result;
    }
    
   /**
     * @param s: a string
     * @return a list of integers representing the size of these parts
     */
    public List<Integer> partitionLabels_IntervalMerge_n(String s) {
        if(s == null || s.isEmpty()){
            return Collections.EMPTY_LIST;
        }

        Interval[] intervals = new Interval[26]; //lowercase letter to {start, end}

        int c;
        for(int i = 0, n = s.length(); i < n; i++){
            c = s.charAt(i) - 'a';

            if(intervals[c] == null){
                intervals[c] = new Interval(i, i);
            }else{
                intervals[c].end = i;
            }
        }

        Arrays.sort(intervals, (a, b) -> Integer.compare( a == null? 0 : a.start, b == null? 0 : b.start) );

        //merge interval, OR
        List<Integer> result = new ArrayList<>();

        Interval pre = null;
        for(Interval curr : intervals){
            if(curr == null){
                continue;
            }
        
            if(pre == null ){
                pre = curr;
            }else if(pre.end < curr.start){
                result.add(pre.end - pre.start + 1);
                pre = curr;
            }else{
                pre.end = Math.max(pre.end, curr.end);
            }
        }

        if(pre != null){
            result.add(pre.end - pre.start + 1);
        }

        return result;
    }
    
   /**
     * @param s: a string
     * @return: a list of integers representing the size of these parts
     */
    public List<Integer> partitionLabels_TwoPoints(String s) {
        if(s == null || s.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        
        int[] ends = new int[26];
        Arrays.fill(ends, -1);
        
        int n = s.length();
        int c;
        int count = 0;
        for(int i = n - 1; i >= 0; i--){
            c = s.charAt(i) - 'a';
            
            if( ends[c] == -1){
                ends[c] = i;
                count++;
                
                if(count == 26){
                    break;
                }
            }
        }
        
        List<Integer> result = new ArrayList<>();
                
        //greedy
        for(int start = -1, left = 0, right = -1; left < n; left++){
            right = Math.max(right, ends[s.charAt(left) - 'a']);
            
            if(left == right){
                result.add(right - start);
                
                start = right;
            }
        }
        
        return result;
    }

    
    public static void main(String[] args){
        PartitionLabels sv = new PartitionLabels();
        
        String[][] inputs = {
            //{ s, expect }
            {
                "ababcbacadefegdehijhklij",
                "9, 7, 8"
            },
            {
                "abcab",
                "5"
            }
        };
        
        for(String[] input : inputs){
            Assert.assertEquals(input[1], Misc.array2String(sv.partitionLabels_IntervalMerge(input[0]), true).toString() );
            
            Assert.assertEquals(input[1], Misc.array2String(sv.partitionLabels_IntervalMerge_n(input[0]), true).toString() );
            
            
            Assert.assertEquals(input[1], Misc.array2String(sv.partitionLabels_TwoPoints(input[0]), true).toString() );
        }
    }
}
