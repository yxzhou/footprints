/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.map.treeMap;

import java.util.LinkedList;
import java.util.NavigableMap;
import java.util.Queue;
import java.util.TreeMap;


/**
 * _https://www.lintcode.com/problem/1064
 * 
 * Implement a MyCalendarTwo class to store your events. A new event can be added if adding the event will not cause a
 * triple booking.
 *
 * Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open
 * interval [start, end), the range of real numbers x such that start <= x < end.
 *
 * A triple booking happens when three events have some non-empty intersection (ie., there is some time that is common
 * to all 3 events.)
 *
 * For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully
 * without causing a triple booking. Otherwise, return false and do not add the event to the calendar.
 *
 * Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 * The number of calls to MyCalendar.book per test case will be at most 1000. 
 * In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
 * 
 * Example
 * MyCalendar();
 * MyCalendar.book(10, 20); // returns true
 * MyCalendar.book(50, 60); // returns true
 * MyCalendar.book(10, 40); // returns true
 * MyCalendar.book(5, 15); // returns false
 * MyCalendar.book(5, 10); // returns true
 * MyCalendar.book(25, 55); // returns true
 * Explanation: 
 *   The first two events can be booked.  The third event can be double booked.
 *   The fourth event (5, 15) can't be booked, because it would result in a triple booking.
 *   The fifth event (5, 10) can be booked, as it does not use time 10 which is already double booked.
 *   The sixth event (25, 55) can be booked, as the time in [25, 40) will be double booked with the third event;
 *   the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.
 * 
 * 
 * Thoughts:
 *    define n as the number of calls to MyCalendar.book.
 * 
 *  M1) sweep line, 
 *  store <start, +1> and <end, -1> in TreeMap<>, scan all elements in TreeMap to calculate the max 
 *  Time O(n), Space O(n)
 *
 *  M2) TreeMap<start, <end, count>>
 * 
 */
public class MyCalendarII {
    class Node{
        int start;
        int end;
        int count;

        Node(int start, int end, int count){
            this.start = start;
            this.end = end;
            this.count = count;
        }
    }

    TreeMap<Integer, Node> map = new TreeMap<>();

    /**
     * @param start: 
     * @param end: 
     * @return nothing
     */
    public boolean book(int start, int end) {
        
        Integer floor = map.floorKey(start);
        Integer ceil = map.ceilingKey(end);
        
        NavigableMap<Integer, Node> subMap = map.subMap( floor == null? start : floor, true, ceil == null? end : ceil, true );

        Queue<Node> queue = new LinkedList<>();
        for(Node event : subMap.values() ){

            if( start < end && ((event.start <= start && event.end > start) || ( event.start >= start && event.start < end) ) ){
                if(event.count == 2){
                    return false;
                }

                if(event.start <= start){
                    if(event.start < start){
                        queue.add(new Node(event.start, start, event.count));
                    }
                    
                    if(event.end <= end){
                        queue.add(new Node(start, event.end, event.count + 1));
                    }else{
                        queue.add(new Node(start, end, event.count + 1));
                        queue.add(new Node(end, event.end, event.count));
                    }
                }else if(event.start > start){
                    queue.add(new Node(start, event.start, 1));

                    if(event.end <= end){
                        queue.add(new Node(event.start, event.end, event.count + 1));
                    }else{
                        queue.add(new Node(event.start, end, event.count + 1));
                        queue.add(new Node(end, event.end, event.count));
                    }
                }

                start = Math.min(end, event.end);
            }
        }

        if(start < end){   
            queue.add(new Node(start, end, 1));
        }

        Node event;
        while( !queue.isEmpty() ){
            event = queue.poll();
            map.put(event.start, event);
        }

        return true;
    }
    

}
