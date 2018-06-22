package fgafa.concurrent.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class ChainingTest {

    /**
     * stage 1, 2 and 3 are in a chain
     */
    @Test
    public void testChain1(){
        CompletableFuture<String> future = CompletableFuture.completedFuture("11");
        future = future.thenApply(result -> {
           System.out.println("Stage 1:" + result);
           return "22";
        });
        future = future.thenApply(result -> {
            System.out.println("Stage 2:" + result);
            throw new RuntimeException();
        });
        future = future.thenApply(result -> {
            System.out.println("Stage 3:" + result);
            return "33";
        });

        future.exceptionally(e -> {
            System.out.println(e.getMessage());
            return null;
        });
    }

    /**
     * case 2: stage 1, 2, and 3 are not in a chain
     */
    @Test
    public void testChain2() {
        CompletableFuture<String> future = CompletableFuture.completedFuture("11");
        future.thenApply(result -> {
            System.out.println("Stage 1:" + result);
            return "22";
        });
        future.thenApply(result -> {
            System.out.println("Stage 2:" + result);
            throw new RuntimeException();
        });
        future.thenApply(result -> {
            System.out.println("Stage 3:" + result);
            return "33";
        });

        future.exceptionally(e -> {
            System.out.println(e.getMessage());
            return null;
        });
    }

    /**
     * case 3, the future is not immediately completed
     */
    @Test
    public void testChain3() {
        CompletableFuture<String> future = new CompletableFuture();
        future.thenApply(result -> {
            System.out.println("Stage 1:" + result);
            return "22";
        });
        future.thenApply(result -> {
            System.out.println("Stage 2:" + result);
            throw new RuntimeException();
        });
        future.thenApply(result -> {
            System.out.println("Stage 3:" + result);
            return "33";
        });

        future.exceptionally(e -> {
            System.out.println(e.getMessage());
            return null;
        });

        future.complete("11");
    }
}


