package fgafa.dailyCoding.datastructure.lastNLogIds;

import java.util.concurrent.atomic.AtomicInteger;

import fgafa.util.Misc;

/**
 * You run an e-commerce website and want to record the last N order ids in a log. Implement a data structure to accomplish this, with the following API:

 record(order_id): adds the order_id to the log
 get_last(i): gets the ith last element from the log. i is guaranteed to be smaller than or equal to N.

 You should be as efficient with time and space as possible.
 *
 * Tags: twitter
 *
 *  Solution 1:
 *    CopyOnWriterArrayList,  time complexity of writing is O(N), time complexity of reading is O(n), space complexity is O(N)
 *
 *  Solution 2:
 *    If the RPS is very high,
 *
 */

public class LastNLogIds_2 {
    final int N;
    int[][] lists;
    volatile AtomicInteger listIndex;
    volatile AtomicInteger lastIndex;

    LastNLogIds_2(int capacity){
        N = capacity;
        lists = new int[2][N];
        listIndex = new AtomicInteger(0);
        lastIndex = new AtomicInteger(0);
    }

    public boolean record(int logId){
        int listIndex = this.listIndex.get();
        int lastIndex = this.lastIndex.incrementAndGet();

        if(lastIndex >= N ){
            int tmp = (lastIndex / N) & 1;
            this.listIndex.weakCompareAndSet(listIndex,tmp);
            listIndex = tmp;

            tmp = lastIndex % N;
            this.lastIndex.compareAndSet(lastIndex, tmp);
            lastIndex = tmp;
        }

        System.out.println(Thread.currentThread() + " -record-1- "+  lists[listIndex].toString());

        lists[listIndex][lastIndex] = logId;

        System.out.println(Thread.currentThread() + " -record-2- "+  lists[listIndex].toString());
        return true;
    }

    public int[] getLast(int n){

        int preListIndex = (listIndex.get() + 1) & 1;
        return getList(this.lists[preListIndex], n);
    }

    private int[] getList(int[] list, int n){

        System.out.println(Thread.currentThread() + " -1- "+  list.toString() + "  " + Misc.array2String(list) );
        LastNLogIds.sleep(1000);
        System.out.println(Thread.currentThread() + " -2- "+ list.toString() + "  " + Misc.array2String(list) );

        int[] result = new int[n];
        System.arraycopy(list, N - n, result, 0, n);
        return  result;
    }

}
