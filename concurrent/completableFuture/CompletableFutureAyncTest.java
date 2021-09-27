package concurrent.completableFuture;

import java.util.concurrent.*;

import org.junit.Before;
import org.junit.Test;

public class CompletableFutureAyncTest {
    private ExecutorService threadPool;

    @Before
    public void setup() {
        this.threadPool = Executors.newSingleThreadExecutor();
    }

    public void tearDown() {
        this.threadPool.shutdownNow();
    }

    /**
     * Make sure that the stages don't go in dead-lock situation when the number of threads are
     * limited.
     */
    /*
    @Test
    public void testSingleThread() {
        TestContext testContext = TestContext.create(1, TimeUnit.SECONDS.toMicros(30));
        CountDownLatch latch = new CountDownLatch(1);
        CompletableFuture
                .runAsync(() -> {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, this.threadPool)
                .thenRunAsync(testContext::complete, this.threadPool);
        latch.countDown();
        testContext.await();
    }
    */

    @Test
    public void testThenCompose(){
        CompletableFuture<String> future11 = new CompletableFuture<>();
        CompletableFuture<String> future12 = new CompletableFuture<>();

        future11.exceptionally(e -> {
            System.out.println("f11 exception: " + e);
            return "f11 exception";
        })
                .thenCompose(x -> future12.exceptionally(e -> {
                    System.out.println("f12 exception: " + e);
                    return "f12 exception";
                }))
                .whenComplete((r, e) -> {
                    System.out.println(String.format("final result #1: r=%s, e=%s\n" ,r,e));
                });

        future11.completeExceptionally(new RuntimeException("f11 RuntimeException"));
        future12.complete("f12 complete");

        /*   */
        CompletableFuture<String> future21 = new CompletableFuture<>();
        CompletableFuture<String> future22 = new CompletableFuture<>();

        future21.exceptionally(e -> {
            System.out.println("f21 exception: " + e);
            return "f21 exception";
        })
                .thenCompose(x -> future22.exceptionally(e -> {
                    System.out.println("f22 exception: " + e);
                    return "f22 exception";
                }))
                .whenComplete((r, e) -> {
                    System.out.println(String.format("final result #2: r=%s, e=%s\n" ,r,e));
                });

        future21.complete("f21 complete");
        future22.completeExceptionally(new RuntimeException("f22 RuntimeException"));

        /*   */
        CompletableFuture<String> future31 = new CompletableFuture<>();
        CompletableFuture<String> future32 = new CompletableFuture<>();

        future31.exceptionally(e -> {
            System.out.println("f31 exception: " + e);
            return "f31 exception";
        })
                .thenCompose(x -> future32.exceptionally(e -> {
                    System.out.println("f32 exception: " + e);
                    throw new RuntimeException(e);
                }))
                .exceptionally( e -> {
                    System.out.println("final exception: " + e);
                    return  "final exception";
                })
                .whenComplete((r, e) -> {
                    System.out.println(String.format("final result #3: r=%s, e=%s\n" ,r,e));
                });

        future31.complete("f31 complete");
        future32.completeExceptionally(new RuntimeException("f32 RuntimeException"));

    }
}
