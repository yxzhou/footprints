/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.topological;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * There are ·n· different online courses numbered from 1 to n. Each course has some duration(course length) t and
 * closed on dth day. A course should be taken continuously for t days and must be finished before or on the dth day.
 * You will start at the 1st day.
 *
 * Given n online courses represented by pairs (t,d), your task is to find the maximal number of courses that can be
 * taken.
 *
 * The integer 1 <= d, t, n <= 10,000. You can't take two courses simultaneously. 
 * 
 * Example 1
 * Input: [[100, 200], [200, 1300], [1000, 1250], [2000, 3200]] 
 * Output: 3 
 * Explanation: There're totally 4 courses, but you can take 3 courses at most: 
 * First, take the 1st course, it costs 100 days so you will finish it on the 100th day, and ready to take the next 
 * course on the `101st` day. 
 * Second, take the 3rd course, it costs 1000 days so you will finish it on the 1100th day, and ready to take the next 
 * course on the 1101st day. 
 * Third, take the 2nd course, it costs 200 days so you will finish it on the 1300th day. The 4th course cannot be taken
 * now, since you will finish it on the 3300th day, which exceeds the closed date. 
 * 
 * Example 2
 * Input: [[100,50]] Output: 0
 *
 */
public class CourseScheduleIII {
    /**
     * Greedy
     * 1 sort courses array by the deadline
     * 2 scan line the courses array, if the scanLine + costTime exceeds the deadline, replace it with the most costTime one 
     * from selected courses. it means move all selected course forward.  
     * 
     * 
     * @param courses
     * @return 
     */
    public int scheduleCourse(int[][] courses) {
        if (courses == null || courses.length < 1) {
            return 0;
        }

        Arrays.sort(courses, (a, b) -> a[1] == b[1]? a[0] - b[0] : a[1] - b[1]);

        PriorityQueue<Integer> maxHeap = new PriorityQueue(Collections.reverseOrder());
        int scanLine = 0;
        int r;
        for (int[] course : courses) {
            r = scanLine + course[0];
            if (r <= course[1]) {
                scanLine = r;
                maxHeap.add(course[0]);
            } else if (!maxHeap.isEmpty() && maxHeap.peek() > course[0]) {
                scanLine = r - maxHeap.poll(); // end <= course[1] for sure
                maxHeap.add(course[0]);
            }
        }

        return maxHeap.size();
    }
}
