package dailyCoding.datastructure.lastNLogIds;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

import util.Misc;

/**
 *
 * You run an e-commerce website and want to record the last N order ids in a log. Implement a data structure to accomplish this, with the following API:

 record(order_id): adds the order_id to the log
 get_last(i): gets the ith last element from the log. i is guaranteed to be smaller than or equal to N.

 You should be as efficient with time and space as possible.
 *
 * Tags: twitter
 *
 *  Solution 0:
 *    circular array store the last N ids
 *    synchronize the reading and writing
 *
 *    when the reading and writing both are low, and reading result need 100% real and accurate
 *
 *  Solution 1:
 *    when reading result doesn't need 100% real. Here introduce a snapshot concept.
 *
 *    like CopyOnWriterArrayList,  time complexity of writing is O(N), time complexity of reading is O(n), space complexity is O(N)
 *    circular array store the last N ids
 *    synchronize the writing
 *
 *  Solution 2:
 *
 *
 */

public class LastNLogIds {

    final int N;
    int[] list;   //volatile
    volatile int lastIndex = 0;

    LastNLogIds(int capacity){
        this.N = capacity;

        list = new int[N];
    }

    final transient ReentrantLock lock = new ReentrantLock();

    public boolean record(int logId){
        lock.lock();

        System.out.println(Thread.currentThread() + " -record-1- "+  list.toString());

        try{
            int[] newList = Arrays.copyOf(list, list.length);
            newList[lastIndex] = logId;
            lastIndex = (lastIndex + 1) % N;

            list = newList;

            System.out.println(Thread.currentThread() + " -record-2- "+  list.toString());
            return true;
        }finally {
            lock.unlock();
        }

    }


    public int[] getLast(int n){
        return getList(this.list, this.lastIndex, n);
    }

    /**
     *
     * The list and lastIndex will be passed in and will not be interference by mutative operations
     *
     * @param list
     * @param lastIndex
     * @param n
     * @return
     */
    private int[] getList(int[] list, int lastIndex, int n){

        System.out.println(Thread.currentThread() + " -1- "+  list.toString() + "  " + Misc.array2String(list) + " lastIndex=" + lastIndex);
        sleep(1000);
        System.out.println(Thread.currentThread() + " -2- "+ list.toString() + "  " + Misc.array2String(list) + " lastIndex=" + lastIndex);

        int[] result = new int[n];

//        for(int i = n- 1; i >= 0; i-- ){
//            lastIndex = lastIndex == 0? N - 1 : lastIndex - 1;
//            result[i] = list[lastIndex];
//        }
        if(lastIndex >= n){
            System.arraycopy(list, lastIndex - n, result, 0, n);
        }else{
            System.arraycopy(list, 0, result, 0, lastIndex);
            System.arraycopy(list, N + lastIndex - n, result, lastIndex, n - lastIndex);
        }

        return  result;
    }


    public static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        LastNLogIds sv = new LastNLogIds(20);


    }
}
