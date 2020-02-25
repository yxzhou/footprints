package fgafa.design.others;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PhoneDirectory2 {

    int max;

    Set<Integer> free;

    /** Initialize your data structure here
     @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
    public PhoneDirectory2(int maxNumbers) {
        this.max = maxNumbers;
        this.free = new HashSet<>();

        for(int i = 0; i < maxNumbers; i++){
            free.add(i);
        }

    }

    /** Provide a number which is not assigned to anyone.
     @return - Return an available number. Return -1 if none is available. */
    public int get() {
        if(free.size() == 0){
            return -1;
        }

        Iterator<Integer> iterator = free.iterator();
        int result = iterator.next();
        iterator.remove();

        return result;
    }

    /** Check if a number is available or not. */
    public boolean check(int number) {
        return free.contains(number);
    }

    /** Recycle or release a number. */
    public void release(int number) {
        if(number > -1 && number < max){
            free.add(number);
        }
    }
    
}
