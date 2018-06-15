package fgafa.concurrent.guardedblocks;

public class ProducerConsumerExample
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    SharedObject box = new SharedObject();
    
    (new Thread(new Producer(box, 10))).start();
    (new Thread(new Producer(box, 11))).start();
    (new Thread(new Consumer(box, 77))).start();
    (new Thread(new Consumer(box, 78))).start();

  }

}
