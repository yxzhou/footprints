package fgafa.concurrent.guardedblocks;

import java.util.Random;

public class Producer implements Runnable 
{

  private SharedObject box;
  private int id;


  public Producer(SharedObject drop, int id) {
    this.box = drop;
    this.id = id;
  }



  public void run() {
    String importantInfo[] = {"message #001 from " + id, "message #002 from " + id, "message #003 from " + id,
        "message #004 from " + id};
    Random random = new Random();

    for (int i = 0; i < importantInfo.length; i++) {
      box.put(importantInfo[i]);
      
      try {
        Thread.sleep(random.nextInt(50));
      }
      catch (InterruptedException e) {
      }
    }
    
    //box.put("DONE");
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
