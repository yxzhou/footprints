package fgafa.concurrent.threadpools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample {

    ExecutorService searchSourcesExecutor = Executors.newFixedThreadPool(3);

    public Future<Map<String, String>> searchUser() {

        Future<Map<String, String>> mapping = searchSourcesExecutor.submit(new Callable<Map<String, String>>() {
            @Override
            public Map<String, String> call()
                throws Exception {

                Map<String, String> result = new HashMap<String, String>();

                for (int i = 0; i < 3; i++) {
                    System.out.println(this.getClass() + "-searchUser "
                                + Thread.currentThread().getId() + " "
                                + System.currentTimeMillis());
                    Thread.sleep(2000);
                }
                // TODO

                return result;
            }
        });

        return mapping;
    }

    private void closeExecutor() {
        searchSourcesExecutor.shutdown();

        try {
            searchSourcesExecutor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("All tasks are finished!");
    }

    @Override
    public void finalize() {
        closeExecutor();
    }

    public static void main() {
        ExecutorServiceExample sv = new ExecutorServiceExample();

        for (int i = 0; i < 5; i++) {
            Future<Map<String, String>> mapping = sv.searchUser();

            try {

                Map<String, String> users = mapping.get();

            } catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
