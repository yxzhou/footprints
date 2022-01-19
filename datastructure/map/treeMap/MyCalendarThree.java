/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.map.treeMap;

import java.util.Map;
import java.util.TreeMap;

/**
 * _https://www.lintcode.com/problem/1063/
 *
 * Implement a MyCalendarThree class to store your events. A new event can always be added.
 *
 * Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open
 * interval [start, end), the range of real numbers x such that start <= x < end.
 *
 * A K-booking happens when K events have some non-empty intersection (ie., there is some time that is common to all K
 * events.)
 *
 * For each call to the method MyCalendar.book, return the largest integer such that there exists a K-booking in the
 * calendar.
 *
 * Your class will be called like this: MyCalendarThree cal = new MyCalendarThree(); MyCalendarThree.book(start, end)
 * The number of calls to MyCalendarThree.book per test case will be at most 400.
 * In calls to MyCalendarThree.book(start, end), start and end are integers in the range [0, 10^9].
 * 
 * Example 1
 * Input:
 * MyCalendarThree()
 * book(10,20)
 * book(50,60)
 * book(10,40)
 * book(5,15)
 * book(5,10)
 * book(25,55)
 * Output: [1,1,2,3,3,3]
 * 
 * Example 2
 * Input:
 * MyCalendarThree()
 * book(1,2)
 * book(1,2)
 * book(2,3)
 * Output: [1,2,2]
 * 
 */
public class MyCalendarThree {
    TreeMap<Integer, Integer> map;

    public MyCalendarThree() {
        map = new TreeMap<>();
    }
    
    public int book(int start, int end) {
        map.put(start, map.getOrDefault(start, 0) + 1);
        map.put(end, map.getOrDefault(end, 0) - 1);

        int max = 0;
        int sum = 0;

        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            sum += entry.getValue();

            //if(entry.getKey() >= start && entry.getKey() < end){
                max = Math.max(max, sum);
            //}
        }

        return max;
    }
}
