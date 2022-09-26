/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sweepLine;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import util.Misc;


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
    

    public static void main(String[] args){
        String[] classes = {"MyCalendarII", "MyCalendarII2"};
        
        String[][] inputs = {
            {
                "book(10,20) book(50,60) book(10,40) book(5,15) book(5,10) book(25,55)",
                "true,true,true,false,true,true"
            },
            {
                "book(24,40) book(43,50) book(27,43) book(5,21) book(30,40) book(14,29) book(3,19) book(3,14) book(25,39) book(6,19)",
                "true,true,true,true,false,false,true,false,false,false"
            },
            {
                "book(44,50) book(47,50) book(9,15) book(4,10) book(2,7) book(28,37) book(26,33) book(22,28) book(43,50) book(18,25)",
                "true,true,true,true,true,true,true,true,false,true"
            },
            {
                "book(8,23) book(35,48) book(24,39) book(10,22) book(10,23) book(8,22) book(1,14) book(36,50) book(42,50) book(42,50)",
                "true,true,true,true,false,false,false,false,true,false"
            }
        };
                
        // int parameter
        Class[] paramInt2 = new Class[2];
        paramInt2[0] = Integer.TYPE;
        paramInt2[1] = Integer.TYPE;

        Class cls;
        List<Boolean> result;
        for(String sv : classes){
            System.out.println(String.format("\n call: %s" , sv ));
            
            for (String[] usercase : inputs) {
                System.out.println(String.format(" testcase: %s" , usercase[0]));
                result = new ArrayList<>();
                
                try {

                    cls = Class.forName("sweepLine." + sv);

                    Object obj = cls.getConstructor().newInstance();

                    Method method;
                    StringTokenizer st = new StringTokenizer(usercase[0], " ");
                    while (st.hasMoreTokens()) {
                        String action = st.nextToken();
                        int index = action.indexOf('(');
    //System.out.println(" " + action);

                        if ("book".equals(action.substring(0, index))) {
                            method = cls.getDeclaredMethod("book", paramInt2);

                            int seconfComma = action.lastIndexOf(',');
                            Object ret = method.invoke(obj, Integer.valueOf(action.substring(index + 1, seconfComma).trim()),
                                Integer.valueOf(action.substring(seconfComma + 1, action.length() - 1).trim()));

                            if (ret instanceof Boolean) {
                                result.add((Boolean) ret);
                            }
                        }

                    }

                    Assert.assertEquals(usercase[1], Misc.array2String(result, false).toString() );
                
                }catch (Exception ex) {
                    Logger.getLogger(MyCalendarII.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
   }
    
}
