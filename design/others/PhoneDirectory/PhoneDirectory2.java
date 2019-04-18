package fgafa.design.others.PhoneDirectory;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class PhoneDirectory2 {
    int max;
    boolean[] status; // default is false, mean it's free; true means it's occupied
    Queue<Integer> frees;

    /** Initialize your data structure here
     @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
    public PhoneDirectory2(int maxNumbers) {
        this.max = maxNumbers;
        status = new boolean[maxNumbers];
        frees = new LinkedList<>();

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

        int x = frees.poll();
        status[x] = true;

        return x;
    }

    /** Check if a number is available or not. */
    public boolean check(int number) {
        if(valid(number) && !status[number]){
            return true;
        }

        return false;
    }

    /** Recycle or release a number. */
    public void release(int number) {
        if(valid(number) && status[number]){
            status[number] = false;
            frees.add(number);
        }
    }

    private boolean valid(int number){
        return number > -1 && number < max;
    }


    @Test public void test(){

//        ["PhoneDirectory","release","get","release","release","get","get","check","get","release","get","release","release","get","check","release"]
//        [[3],               [2],     [],    [2],      [0],      [],  [],    [1],    [],  [0],      [],   [0],[0],[],[1],[1]]
//
//        Output:
//        [null,              null,     0,    null,     null,     1,   2,     false,  2,   null,     2,    null,null,0,false,null]
//        Expected:
//        [null,              null,     0,    null,     null,     1,   0,false,2,null,0,null,null,0,false,null]
    }
}
