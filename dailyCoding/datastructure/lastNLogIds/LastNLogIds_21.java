package dailyCoding.datastructure.lastNLogIds;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import util.Misc;

public class LastNLogIds_21 {
    final int N;
    int[] forReading;
    volatile int[] forWriting;
    volatile AtomicInteger lastIndex;

    final transient ReentrantLock lock = new ReentrantLock();

    LastNLogIds_21(int capacity){
        N = capacity;
        forReading = new int[N];
        forWriting = new int[N];
        lastIndex = new AtomicInteger(0);
    }

    public boolean record(int logId){
        int lastIndex = this.lastIndex.incrementAndGet();

        if(lastIndex >= N ){
            lock.lock();

            try{
                if(this.lastIndex.equals(lastIndex)){
                    forReading = forWriting;
                    forWriting = new int[N];

                    int tmp = lastIndex % N;
                    this.lastIndex.compareAndSet(lastIndex, tmp);
                    lastIndex = tmp;
                }
            }finally {
                lock.unlock();
            }
        }

        System.out.println(Thread.currentThread() + " -record-1- "+  forWriting.toString());

        forWriting[lastIndex] = logId;

        System.out.println(Thread.currentThread() + " -record-2- "+  forWriting.toString());
        return true;
    }


    public int[] getLast(int n){
        return getList(this.forReading, n);
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
