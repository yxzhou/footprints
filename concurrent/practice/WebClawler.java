package fgafa.concurrent.practice;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class WebClawler {

    @Test
    public void singleThread(){
        long startTime = System.currentTimeMillis();

        Set<String> visited = new HashSet<>();
        Queue<String> urls = new LinkedList<>();
        visited.add(startPoint);
        urls.add(startPoint);

        while(!urls.isEmpty()){
            String curr = urls.poll();

            try {
                List<String> nexts = webClawl(curr);

                for (String next : nexts) {
                    if (!visited.contains(next)) {
                        visited.add(next);
                        urls.add(next);
                    }
                }
            } catch (TimeoutException te){
                System.out.println( curr + " Time Out!");
                urls.add(curr);
            }

        }

        //visited.forEach(System.out :: println);
        //Assert.assertEquals(MAX, visited.size());

        System.out.println("Total: "+ visited.size() +" \ttime cost== " + (System.currentTimeMillis() - startTime));
    }


    volatile ConcurrentHashMap<String, Integer> status = new ConcurrentHashMap<>();
    volatile BlockingQueue<String> urls = new LinkedBlockingQueue<>();

    @Test
    public void multipleThread(){
        long startTime = System.currentTimeMillis();

        AtomicInteger counter = new AtomicInteger(1);

        ExecutorService pool = Executors.newFixedThreadPool(10);

        status.put(startPoint, 0);
        urls.add(startPoint);

        try{
            while(counter.get() != 0){
                if (urls.isEmpty()) {
                    continue;
                }

                String curr = urls.poll();

                Future future = pool.submit(()->{
                    System.out.println(Thread.currentThread().getName() + " " + curr);

                    try{
                        List<String> nexts = webClawl(curr);

                        for (String next : nexts) {
                            if (!status.containsKey(next)) {
                                status.put(next, 0);
                                urls.add(next);

                                counter.getAndIncrement();
                            }
                        }

                        //status.put(curr, 1);
                        counter.decrementAndGet();
                    } catch (TimeoutException te){

                        System.out.println(Thread.currentThread().getName() + " " + curr + " Time Out!");
                        urls.add(curr);
                    }
                });

            }

            System.out.println("Total: "+ status.size() +" \ttime cost== " + (System.currentTimeMillis() - startTime));

        }finally {
            pool.shutdown();
        }
    }

    //provided
    final static int MAX = 1000;
    final static int N = 10;
    final static String prefix = "url-";
    final static String startPoint = prefix + "0";
    AtomicBoolean firstTime = new AtomicBoolean(true);
    public List<String> webClawl(String url) throws TimeoutException{
        //fake a list
        List<String> result = new ArrayList<>(N);
        Random random = new Random();

        for(int i = 0; i < N; i++){
            int x = random.nextInt(MAX);

            if(x == 911 && (firstTime.get() || (random.nextInt(2) == 0))){

                firstTime.compareAndSet(true, false);
                throw new TimeoutException("timeout");
            }

            result.add(prefix + x);
        }

        sleep(200);

        return result;
    }

    public static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
