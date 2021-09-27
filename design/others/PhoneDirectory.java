package design.others;

import java.util.BitSet;

public class PhoneDirectory {

    int max;
    BitSet directory;

    /** Initialize your data structure here
     @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
    public PhoneDirectory(int maxNumbers) {
        this.max = maxNumbers;
        this.directory = new BitSet();
    }

    /** Provide a number which is not assigned to anyone.
     @return - Return an available number. Return -1 if none is available. */
    public int get() {
        int next = directory.nextClearBit(0);

        if(next >= max){
            return -1;
        }

        directory.set(next);
        return next;
    }

    /** Check if a number is available or not. */
    public boolean check(int number) {
        return !directory.get(number);
    }

    /** Recycle or release a number. */
    public void release(int number) {
        directory.clear(number);
    }

}
