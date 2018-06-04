package fgafa.basic.memory;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * verify with PhantomReference: 
 * if Object A has reference of object B and object B has reference of Object A, 
 * and they don't have any other live reference,  
 * then both Objects A and B will be eligible for Garbage collection
 *
 * 
 *
 */

public class TestCyclicDependence
{

 //public static ReferenceQueue<Object> test() throws InterruptedException{
 public ReferenceQueue<Object> test() throws InterruptedException{
   //Company c = new Company("company_aa");
   Employee e1 = new Employee("employee_John");
   //Employee e2 = new Employee("employee_Emma");
   
   //e1.setCompany(c);
   //e2.setCompany(c);
   
   //c.getEmployees().add(e1);
   //c.getEmployees().add(e2);
   e1.setCompany(new Company("company_aa"));
   
   //ReferenceQueue<Object> deadQ = new ReferenceQueue<Object>();
   ReferenceQueue<Object> deadQ = TestDeadObject.deadQ;
   PhantomReference<Object> phantomRef = new PhantomReference<Object>(e1, deadQ);
   
   //assert(phantomRef.get() == null):"PhantomReference.get() always return null"; 
   if(phantomRef.get() == null)
     System.out.println("PhantomReference.get() always return null" + phantomRef.get());
   else
     System.out.println("PhantomReference.get() not always return null" + phantomRef.get());
   
   e1 = null;   // remove this live refence, the object Employee with cyclicDependence is eligible for GC
   //e2 = null;
   //c = null;
   
   //assert(deadQ.poll() != null):"PhantomReference are enqueued 001" + deadQ.poll();
   Object o = deadQ.poll();
   if(o != null){
     System.out.println("PhantomReference are enqueued 001" + o.toString());
   }else
     System.out.println("PhantomReference are not enqueued 001" + deadQ.poll());
   
   garbageCollect();
   
   //assert(deadQ.poll() != null):"PhantomReference are enqueued 002" + deadQ.poll();
   if(deadQ != null)
     System.out.println("deadQ != null, deaQ is:" + deadQ.toString());    
   
   o = deadQ.poll();
   if(o != null)
     System.out.println("PhantomReference are enqueued 002" + o.toString());
   else
     System.out.println("PhantomReference are not enqueued 002" + deadQ.poll());
   
   return deadQ;
 }
  
  private void garbageCollect() throws InterruptedException{
    System.out.println("Suggesting collection");
    System.gc();
    System.out.println("Sleeping start :"+System.currentTimeMillis());
    Thread.sleep(5000);
    System.out.println("Sleeping end   :"+System.currentTimeMillis());
  }
 
  protected void finalize() {
    // Note that if there is a finalize() method PhantomReference's don't get appended to a ReferenceQueue
    }

  
  
  /**
   * @param args
   */
  public static void main(String[] args) throws InterruptedException {

    System.out.println("---test start---");
    TestCyclicDependence t = new TestCyclicDependence();
    ReferenceQueue<Object> deadQ = t.test();
    
    //assert(deadQ.poll() != null):"PhantomReference are enqueued 011" + deadQ.poll();
    Object o = deadQ.poll();
    if(o != null)
      System.out.println("PhantomReference are enqueued 011" + o.toString());
    else
      System.out.println("PhantomReference are not enqueued 011" + deadQ.poll());
    
    t.garbageCollect();
    
    //assert(deadQ.poll() != null):"PhantomReference are enqueued 012" + deadQ.poll();
    o = deadQ.poll();
    if(o != null)
      System.out.println("PhantomReference are enqueued 012" + o.toString());
    else
      System.out.println("PhantomReference are not enqueued 012" + deadQ.poll());
    
    System.out.println("---test end---");
  }

}
