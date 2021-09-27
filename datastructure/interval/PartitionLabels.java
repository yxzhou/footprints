package datastructure.interval;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * _https://www.lintcode.com/problem/1045/description
 * Partition Labels
 *
 * Description
 * A string S of lowercase letters is given. We want to partition this string into as many parts as possible so that each letter appears in at most one part, and return a list of integers representing the size of these parts.
 *
 * Notes:
 * 1.S will have length in range [1, 500].
 * 2.S will consist of lowercase letters ('a' to 'z') only.
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
 */

public class PartitionLabels {

    public List<Integer> partitionLabels(String S) {
        List<Integer> res = new LinkedList<>();

        if(S == null || S.length() == 0){
            return res;
        }

        int[][] intervals = new int[26][2];
        boolean[] visited = new boolean[26]; //default all are false
        int p;
        for(int i = 0, n = S.length(); i < n; i++){
            p = S.charAt(i) - 'a';
            if(visited[p]){
                intervals[p][1] = i;
            }else{
                intervals[p][0] = i;
                intervals[p][1] = i;
                visited[p] = true;
            }
        }

        Arrays.sort(intervals, (p1, p2) -> Integer.compare(p1[0], p2[0]) );

        int[] curr = intervals[0];
        int[] next;
        for(int i = 1, n = intervals.length; i < n; i++){
            next = intervals[i];
            if(curr[1] < next[0]){
                res.add(curr[1] - curr[0] + 1);
                curr = next;
            }else{
                curr[1] = Math.max(curr[1], next[1]);
            }
        }

        res.add(curr[1] - curr[0] + 1);

        return res;
    }

}
