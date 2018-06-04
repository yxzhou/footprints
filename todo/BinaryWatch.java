package fgafa.todo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fgafa.util.Misc;

public class BinaryWatch {

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
    public List<String> readBinaryWatch(int numberOfOne){
       List<String> result = new LinkedList<>();
        
        if(numberOfOne < 0 || numberOfOne > 8){
            return result;
        }
        
        List<List<String>> hours = readAllHour12();
        List<List<String>> minutes = readAllMinute();
        for(int h = 0, min = Math.min(3, numberOfOne); h <= min; h++ ){
//            if(h >= hours.size() || (numberOfOne - h) >= minutes.size()){
//                continue;
//            }
            
            for(String hour : hours.get(h)){
                for(String minute : minutes.get(numberOfOne - h)){
                    result.add(hour + ":" + minute);
                }
            }
        }
        
        return result;
    }
    
    private List<List<String>> readAllHour12(){
        List<List<String>> result = new ArrayList<>();
        
        for(int numberOfOne = 0; numberOfOne < 4; numberOfOne++){
            result.add(new ArrayList<String>());
        }
        
        for(int value = 0; value < 12; value++){  //??  == 12? 
            int numberOfOne = countNumberOfOne(value);
            
            result.get(numberOfOne).add(String.valueOf(value));
        }
        
        return result;
    }
    
    private List<List<String>> readAllMinute(){
        List<List<String>> result = new ArrayList<>();
        
        for(int numberOfOne = 0; numberOfOne < 6; numberOfOne++){
            result.add(new ArrayList<String>());
        }
        
        for(int value = 0; value < 60; value++){
            int numberOfOne = countNumberOfOne(value);
            
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
        }

    }

}
