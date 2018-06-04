package fgafa.basic.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * Some validations for how the {@link java.util.concurrent.CompletableFuture} behaves with the
 * *async methods.

 Method	        Async method	    Arguments	                    Returns
 thenAccept()	thenAcceptAsync()	Result of previous stage	    Nothing
 thenApply()	thenApplyAsync()	Result of previous stage	    Result of current stage

 thenRun()	    thenRunAsync()	    None	                        Nothing

 thenCompose()	thenComposeAsync()	Result of previous stage	    Future result of current stage
 thenCombine()	thenCombineAsync()	Result of two previous stages	Result of current stage
 whenComplete()	whenCompleteAsync()	Result or exception from previous stage	Nothing
 *
 */
public class CompletableFutureTest {


    @Test
    public void testThenCompose() {
        CompletableFuture<String> future11 = new CompletableFuture<>();
        CompletableFuture<String> future12 = new CompletableFuture<>();

        future11.exceptionally(e -> {
            System.out.println("f11 exception: " + e);
            return "f11 exception";
            //throw e;
        })
                .thenCompose(x -> future12.exceptionally(e -> {
                    System.out.println("f12 exception: " + e);
                    return "f12 exception";
                }))
                .whenComplete((r, e) -> {
                    System.out.println(String.format("final result #1: r=%s, e=%s\n", r, e));
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
                    System.out.println(String.format("final result #2: r=%s, e=%s\n", r, e));
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
                .exceptionally(e -> {
                    System.out.println("final exception: " + e);
                    return "final exception";
                })
                .whenComplete((r, e) -> {
                    System.out.println(String.format("final result #3: r=%s, e=%s\n", r, e));
                });

        future31.complete("f31 complete");
        future32.completeExceptionally(new RuntimeException("f32 RuntimeException"));

        /*  */
        CompletableFuture<String> future41 = new CompletableFuture<>();
        CompletableFuture<String> future42 = new CompletableFuture<>();

        future41.thenCompose(x -> future42)
                .whenComplete((r, e) -> {
                    System.out.println(String.format("final result #4: r=%s, e=%s\n", r, e));
                });

        future41.completeExceptionally(new RuntimeException("f41 RuntimeException"));
        future42.complete("f42 complete");
    }

}
