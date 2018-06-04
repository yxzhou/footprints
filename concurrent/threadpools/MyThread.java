package fgafa.concurrent.threadpools;

/**
 * Created by joeyz on 3/14/17.
 */
class MyThread implements Runnable {
  private final long countUntil;

  MyThread(long countUntil) {
    this.countUntil = countUntil;
  }

  @Override
  public void run() {
    long sum = 0;
    for (long i = 1; i < countUntil; i++) {
      sum += i;
    }
    System.out.println(sum);
  }
}
