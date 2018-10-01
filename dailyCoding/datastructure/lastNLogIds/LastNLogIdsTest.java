package fgafa.dailyCoding.datastructure.lastNLogIds;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LastNLogIdsTest {

    public static void main(String[] args){
        LastNLogIds pool = new LastNLogIds(10);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable[] runnables = new Runnable[]{
                new Reader(pool),
                new Writer(pool),
                new Writer(pool)
        };

        try {
            Random random = new Random();
            for (int i = 0; i < 50; i++) {
                executor.execute(runnables[random.nextInt(runnables.length)]);
            }
        }finally {
            executor.shutdown();
        }
    }

}


class Writer implements Runnable{
    LastNLogIds pool;

    Writer(LastNLogIds list){
        this.pool = list;
    }

    @Override public void run(){
        Random random = new Random();
        pool.record(random.nextInt(100));
    }
}

class Reader implements Runnable{
    LastNLogIds pool;

    Reader(LastNLogIds list){
        this.pool = list;
    }

    @Override public void run(){
        pool.getLast(6);
    }
}