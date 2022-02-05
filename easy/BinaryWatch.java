package easy;

import util.Misc;

import java.util.*;

/**
 * _https://www.lintcode.com/problem/706
 *
 * A binary watch has 4 LEDs on the top which represent the hours (0-11), and the 6 LEDs on the bottom represent the
 * minutes (0-59).
 *
 * For example, the above binary watch reads "3:25".
 *
 * Given a non-negative integer n which represents the number of LEDs that are currently on, return all possible times
 * the watch could represent.
 *
 * Example:
 * Input: n = 1 
 * Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"] 
 * 
 * 
 * Note: 
 *   The order of output does not matter. 
 *   The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00". 
 *   The minute must be consist of two digits and may contain a leading zero, for example "10:02".
 *   hour < 12, minute < 60
 *
 */

public class BinaryWatch {

    /**
     * @param num: the number of "1"s on a given timetable
     * @return all possible time
     */
    public List<String> binaryTime(int num) {
        if(num > 8){
            return Collections.EMPTY_LIST;
        }
        
        List<String> result = new LinkedList<>();

        List<Integer> minutes;
        for(int h = 0, m ; h <= Math.min(num, 3); h++){
            m = num - h;
            if(m > 5){
                continue;
            }
            minutes = cacheMinutes[m];

            for(int hr : cacheHours[h] ){
                for(int min : minutes ){
                    result.add(String.format("%d:%02d", hr, min));
                }
            }
            
        }

        return result;
    }

    final static int[][] cacheHours = {{0}, {1, 2, 4, 8}, {3, 5, 6, 9, 10}, {7, 11}};
    final static List<Integer>[] cacheMinutes = new ArrayList[6];

    static{
        for(int i = 0; i < 6; i++){
            cacheMinutes[i] = new ArrayList<>();
        }
        
        for(int i = 0; i < 60; i++){
            cacheMinutes[count(i)].add(i);
        }
    }

    private static int count(int x){
        int count = 0;

        while(x > 0){
            x &= (x - 1);
            count++;
        }

        return count;
    }
    
    public static void main(String[] args) {
        BinaryWatch sv = new BinaryWatch();
        
        
        for(int numberOfOne = 0; numberOfOne < 10; numberOfOne++){
            System.out.println("\nInput numberOfOne=" + numberOfOne);
            
            Misc.printList(sv.binaryTime(numberOfOne));

        }

    }

}
