package concurrent.guardedblocks;

import java.util.Random;

public class Consumer implements Runnable 
{

  private SharedObject box;
  private int id;


  public Consumer(SharedObject box, int id) {
    this.box = box;
    this.id = id;
  }



  public void run() {
    Random random = new Random();
    for (String message = box.take(); !message.equals("DONE"); message = box.take()) {
      
      System.out.format( id + " RECEIVED: %s%n", message);
      
      try {
        Thread.sleep(random.nextInt(50));
      }
      catch (InterruptedException e) {
      }
    }
  }



  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
