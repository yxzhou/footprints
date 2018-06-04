package fgafa.basic.memory;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
/**
 * 
 * verify with PhantomReference: 
 * if class  
 * and they don't have any other live reference,  
 * then both Objects A and B will be eligible for Garbage collection
 *
 */

public class TestDeadObject
{

  public static ReferenceQueue<Object> deadQ = new ReferenceQueue<Object>();
  
  private static void garbageCollect() throws InterruptedException{
    System.out.println("Suggesting collection");
    System.gc();
    System.out.println("Sleeping start :"+System.currentTimeMillis());
    Thread.sleep(5000);
    System.out.println("Sleeping end   :"+System.currentTimeMillis());
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) throws InterruptedException {
    System.out.println("---test start---");
    TestCyclicDependence t = new TestCyclicDependence();
    //ReferenceQueue<Object> deadQ = t.test();
    t.test();
    
    PhantomReference<Object> phantomRef = new PhantomReference<Object>(t, deadQ);
    
    //assert(deadQ.poll() != null):"PhantomReference are enqueued 021" + deadQ.poll();
    Object o = deadQ.poll();
    if(o != null)
      System.out.println("PhantomReference are enqueued 021" + o.toString());
    else
      System.out.println("PhantomReference are not enqueued 021" + deadQ.poll());
    
    t = null;  //the object TestCyclicDependence is eligible for GC  
    
    garbageCollect();
    
    //assert(deadQ.poll() != null):"PhantomReference are enqueued 022" + deadQ.poll();
    if(deadQ != null)
      System.out.println("deadQ != null, deaQ is:" + deadQ.toString());    
    
    o = deadQ.poll();
    if(o != null)
      System.out.println("PhantomReference are enqueued 022" + o.toString());
    else
      System.out.println("PhantomReference are not enqueued 022" + deadQ.poll());
    
    System.out.println("---test end---");

  }

}
