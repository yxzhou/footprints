package fgafa.backtracking;

import fgafa.util.Misc;

import java.util.*;

    /*
     * A binary watch has 4 LEDs on the top which represent the hours (0-11), and the 6 LEDs on the bottom represent the minutes (0-59).
     *
     * For example, the above binary watch reads "3:25".

    Given a non-negative integer n which represents the number of LEDs that are currently on, return all possible times the watch could represent.

    Example:

    Input: n = 1
    Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
    Note:
    The order of output does not matter.
    The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00".
    The minute must be consist of two digits and may contain a leading zero, for example "10:2" is not valid, it should be "10:02".
     *
     *
     */

public class BinaryWatch {

    /**
     *  backtracking
     *
     *  Time O(2^10), Space O(1)
     */
    public List<String> readBinaryWatch_x(int num) {
        if(num < 0 || num > 8){
            return Collections.EMPTY_LIST;
        }

        List<String> result = new LinkedList<>();

        build(result, num, 0, 0);

        return result;
    }


    private void build(List<String> result, int num, int state, int position){
        if(num == 0){
            int min = (state & 0b111111);
            int hh = (state >>> 6);

            if(min < 60 && hh < 12){
                result.add(String.format("%d:%02d", hh, min));
            }

            return;
        }

        num--;
        for(int i = position; i < 10; i++){
            build(result, num, ( state | (1 << i) ), i + 1);
        }
    }

    /**
     *
     * Time O(  ), Space O(12 + 60)
     *
     */
    public List<String> readBinaryWatch(int numberOfOne){
       List<String> result = new LinkedList<>();
        
        if(numberOfOne < 0 || numberOfOne > 8){
            return result;
        }
        
        Map<Integer, List<String>> hours = readAllHour12();
        Map<Integer, List<String>> minutes = readAllMinute();
        for(int h = 0, min = Math.min(3, numberOfOne); h <= min; h++ ){

            int m = numberOfOne - h;
            if( m >= minutes.size() ){
                continue;
            }
            
            for(String hour : hours.get(h)){
                for(String minute : minutes.get(m)){
                    result.add(hour + ":" + minute);
                }
            }
        }
        
        return result;
    }
    
    private Map<Integer, List<String>> readAllHour12(){
        Map<Integer, List<String>> result = new HashMap<>();
        
        for(int value = 0; value < 12; value++){  //??  == 12?
            int numberOfOne = countNumberOfOne(value);

            result.putIfAbsent(numberOfOne, new LinkedList<>());
            result.get(numberOfOne).add(String.valueOf(value));
        }
        
        return result;
    }
    
    private Map<Integer, List<String>> readAllMinute(){
        Map<Integer, List<String>> result = new HashMap<>();
        
        for(int value = 0; value < 60; value++){
            int numberOfOne = countNumberOfOne(value);

            result.putIfAbsent(numberOfOne, new LinkedList<>());
            result.get(numberOfOne).add(value < 10 ? "0" + value : "" + value);
        }
        
        return result;
    }
    
    private int countNumberOfOne(int number){
        int count = 0;
        
        while(number > 0){
            count++;
            
            number &= number -1;
        }
        
        return count;
    }
    



    
    public static void main(String[] args) {
        BinaryWatch sv = new BinaryWatch();
        
        
        for(int numberOfOne = 0; numberOfOne < 10; numberOfOne++){
            System.out.println("\nInput numberOfOne=" + numberOfOne);
            
            Misc.printList(sv.readBinaryWatch(numberOfOne));

            Misc.printList(sv.readBinaryWatch_x(numberOfOne));
        }

    }

}
