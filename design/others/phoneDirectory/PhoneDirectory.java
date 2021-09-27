package design.others.phoneDirectory;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Design a Phone Directory which supports the following operations:
 *
 * get: Provide a number which is not assigned to anyone.
 * check: Check if a number is available or not.
 * release: Recycle or release a number.
 * Example:
 *
 * // Init a phone directory containing a total of 3 numbers: 0, 1, and 2.
 * PhoneDirectory directory = new PhoneDirectory(3);
 *
 * // It can return any available phone number. Here we assume it returns 0.
 * directory.get();
 *
 * // Assume it returns 1.
 * directory.get();
 *
 * // The number 2 is available, so return true.
 * directory.check(2);
 *
 * // It returns 2, the only number that is left.
 * directory.get();
 *
 * // The number 2 is no longer available, so return false.
 * directory.check(2);
 *
 * // Release number 2 back to the pool.
 * directory.release(2);
 *
 * // Number 2 is available again, return true.
 * directory.check(2);
 *
 *
 * Solutions:                           Time                                            Space
 * set<Integer> frees                   get()-O(n) check() and release()-O(1)           O(n)
 * boolean[] status + Queue<> frees     get(), check() and release()-O(1)               O(2n)
 *
 */


public class PhoneDirectory {
    Set<Integer> frees;
    int max;

    /** Initialize your data structure here
     @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
    public PhoneDirectory(int maxNumbers) {
        this.max = maxNumbers;
        frees = new HashSet<>();

        for(int i = 0; i < maxNumbers; i++){
            frees.add(i);
        }

    }

    /** Provide a number which is not assigned to anyone.
     @return - Return an available number. Return -1 if none is available. */
    public int get() {
        if(frees.isEmpty()){
            return -1;
        }

        int x = frees.iterator().next();  //note:  this maybe O(n)
        frees.remove(x);
        return x;
    }

    /** Check if a number is available or not. */
    public boolean check(int number) {
        return frees.contains(number);
    }

    /** Recycle or release a number. */
    public void release(int number) {
        if(valid(number)){
            frees.add(number);
        }
    }

    private boolean valid(int number){
        return number > -1 && number < max;
    }
}

/**
 * Your PhoneDirectory object will be instantiated and called as such:
 * PhoneDirectory obj = new PhoneDirectory(maxNumbers);
 * int param_1 = obj.get();
 * boolean param_2 = obj.check(number);
 * obj.release(number);
 */
