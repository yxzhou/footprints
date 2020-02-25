package fgafa.leetcode.facebook;

import java.util.*;

/**
 * Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters
 * represent different tasks. Tasks could be done without original order. Each task could be done in one interval.
 * For each interval, CPU could finish one task or just be idle.
 *
 * However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n
 * intervals that CPU are doing different tasks or just be idle.
 *
 * You need to return the least number of intervals the CPU will take to finish all the given tasks.
 *
 *
 *
 * Example:
 * Input: tasks = ["A","A","A","B","B","B"], n = 2
 * Output: 8
 * Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
 *
 *
 * Note:
 * The number of tasks is in the range [1, 10000].
 * The integer n is in the range [0, 100].
 *
 *
 * Thoughts:
 *   The point of scheduler is to make sure it's at least n intervals that CPU are doing different tasks or just be idle.
 *   Let's say input tasks,  3 A, 3 B, 2 C, 1 D
 *   If the tasks are scheduled as the below
 *      A B C D
 *      A B C
 *      A B
 *   It would be ok when n is 0, 1, 2
 *   when n is 3, it need add 1 space in the second line
 *   when n is 4, it need add 2 space in the second line and 1 space in the first line
 *   - - -
 *
 *   So the total intervals of CPU is:
 *     when n is small,  it's the tasks' length
 *     when n is bigger, it's (A's number - 1) * (n + 1) + the task number who has same number of A
 */

public class CPUTaskScheduler {
    /** todo */
    public int leastInterval_math(String[] tasks, int k) {
        Map<String, Integer> map = new HashMap<>();
        for(String task : tasks){
            map.put(task, map.getOrDefault(task, 0) + 1);
        }

        List<Integer> list = new LinkedList<>(map.values());
        //Collections.sort(list, Collections.reverseOrder());

        Queue<Integer> queue = new LinkedList<>(list);

        int result = 0;
        while(!queue.isEmpty()){

        }

        return result;
    }

    public int leastInterval_math(char[] tasks, int n) {
        int[] c = new int[26]; //default all are 0
        for(char t : tasks){
            c[t - 'A']++;
        }

        Arrays.sort(c);

        int i = 1;
        for(int j = 24; j >=0 && c[j] == c[25]; j--){
            i++;
        }

        return Math.max(tasks.length, (c[25] - 1) * (n + 1) + i);
    }


}
