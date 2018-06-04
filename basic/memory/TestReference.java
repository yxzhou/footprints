package fgafa.basic.memory;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 
 * test weakReference, SoftReference
 *
 */
public class TestReference
{

  public static class Referred {
    // Note that if there is a finalize() method PhantomReference's don't get appended to a ReferenceQueue
    protected void finalize() {
        System.out.println("==Good bye cruel world");
    }
  }

  public static class Referred2 {
    // Note that if there is a finalize() method PhantomReference's don't get appended to a ReferenceQueue
  }
  
  private static void garbageCollect() throws InterruptedException{
    System.out.println("Suggesting collection");
    System.gc();
    System.out.println("Sleeping start :"+System.currentTimeMillis());
    Thread.sleep(5000);
    System.out.println("Sleeping end   :"+System.currentTimeMillis());
  }
  
  /*
   * Strong references never get collected until it's not existed
   * 
   */
  
  public void testStrongRef() throws InterruptedException{
    System.out.println("\nCreating strong references");    
    // This is now a strong reference.
    // The object will only be collected if all references to it disappear.
    Referred strong = new Referred();
    
    this.garbageCollect(); // Attempt to claim a suggested reference.

    System.out.println("Removing the strong reference");   
    strong = null;  // The object may now be collected.
    
    this.garbageCollect();

    System.out.println("Done");
  }
  


  /*
   * Weak references only get collected if no other object references it except
   * the weak references. This makes them perfect for keeping meta data about a
   * particular object for the life time of the object.
   */
  
  public void testWeakRef() throws InterruptedException{
    System.out.println("\nCreating weak references");
    // This is now a weak reference.
    // The object will be collected only if no strong references.
    Referred strong = new Referred();
    WeakReference<Referred> weak = new WeakReference<Referred>(strong);
    
    this.garbageCollect(); // Attempt to claim a suggested reference.

    System.out.println("Removing the strong reference");
    strong = null;  // The object may now be collected.
    
    this.garbageCollect();

    System.out.println("Done");
    
  }
  


  /*
   * Soft references only get collected if the JVM absolutely needs the memory.
   * This makes them excellent for implementing object cache's.
   * 
   */
  
  public void testSoftRef() throws InterruptedException{
    System.out.println("\nCreating soft references");
    
    // This is now a soft reference.
    // The object will be collected only if no strong references exist and the JVM really needs the memory.
    Referred strong = new Referred();
    SoftReference<Referred> soft = new SoftReference<Referred>(strong);

    this.garbageCollect(); // Attempt to claim a suggested reference.

    System.out.println("Removing the strong reference");
    strong = null; // The object Referred may but highly likely wont be collected.
    this.garbageCollect();

    System.out.println("Consuming heap");  
    try
    {
        // Create lots of objects on the heap
        List<Referred2> heap = new ArrayList<Referred2>(100000);
        while(true) {
            heap.add(new Referred2());
        }
    }
    catch (OutOfMemoryError e) {
        // The soft object should have been collected before this
        System.out.println("Out of memory error raised");
    }

    System.out.println("Done");
  }
  


  /*
   * Phantom references are objects that can be collected whenever the collector
   * likes. The object reference is appended to a ReferenceQueue and you can use
   * this to clean up after a collection. This is an alternative to the
   * finalize() method and is slightly safer because the finalize() method may
   * resurrect the object by creating new strong references. The
   * PhantomReference however cleans up the object and enqueues the reference
   * object to a ReferenceQueue that a class can use for clean up.
   * 
   * PhantomReferences are enqueued only when the object is physically removed from memory
   * it allow you to determine exactly when an object was removed from memory
   * 
   */
  

  public void testPhantomRef() throws InterruptedException{
    System.out.println("\nCreating phantom references");
    
    // The reference itself will be appended to the dead queue for clean up.
    ReferenceQueue dead = new ReferenceQueue(); 

    // This map is just a sample we might use to locate resources we need to clean up.
    Map<Reference,String> cleanUpMap = new HashMap<Reference,String>();

    // This is now a phantom reference.
    // The object will be collected only if no strong references.
    Referred2 strong = new Referred2();

    PhantomReference<Referred2> phantom = new PhantomReference(strong, dead);
    cleanUpMap.put(phantom, "The object is removed from memory. PhantomReference is enqueued, you can clean up me now!");

    if(phantom.get() == null)
      System.out.println("PhantomReference.get() always return null,  so you can't resurrect the object by creating new strong references" );
    
    strong = null;  //The object Referred2 may now be collected. 

    this.garbageCollect(); // Attempt to claim a suggested reference.

    // Check for 
    Reference reference = dead.poll();
    if (reference != null) { //The object Referred2 is physically removed from memory, PhantomReference is enqueued
        System.out.println(cleanUpMap.remove(reference));
    }else
      System.out.println("The object is not removed from memory. PhantomReference is not enqueued");
    
    
    System.out.println("Done");
    
  }
  


  /*
   * You saw me use the reference queue class in the previous example. A
   * ReferenceQueue instance can be supplied as an argument to SoftReference,
   * WeakReference or PhantomReference. When an object is collected the
   * reference instance itself will be enqueued to the supplied ReferenceQueue.
   * This allows you to perform clean up operations on the object. This is
   * useful if you are implementing any container classes that you want to
   * contain a Soft, Weak or Phantom reference and some associated data because
   * you can get notified via the ReferenceQueue which Reference was just
   * collected.
   */
  public void testReferenceQueueWithWeakRef() throws InterruptedException{
    System.out.println("\nCreating weak references");
    
    // The reference itself will be appended to the dead queue for clean up.
    ReferenceQueue dead = new ReferenceQueue(); 

    // This map is just a sample we might use to locate resources we need to clean up.
    Map<Reference,String> cleanUpMap = new HashMap<Reference,String>();

    // This is now a phantom reference.
    // The object will be collected only if no strong references.
    Referred strong = new Referred();

    WeakReference<Referred> weak = new WeakReference(strong, dead);
    cleanUpMap.put(weak, "The object is collected by GC. WeakReference is enqueued, you can clean up me now!");

    if(weak.get() != null)
      System.out.println("weak.get is not null,  so you can resurrect the object by creating new strong references");
    
    strong = null;  //The object Referred2 may now be collected. 

    this.garbageCollect(); // Attempt to claim a suggested reference.

    // Check for 
    Reference reference = dead.poll();
    if (reference != null) { //The object Referred is collected, PhantomReference is enqueued
        System.out.println(cleanUpMap.remove(reference));
    }else
      System.out.println("The object is not removed from memory. WeakReference is not enqueued");
    
    
    System.out.println("Done");
    
    
  }

  
  
  /*
   * There is a convince WeakHashMap that wraps all keys by a weak
   * reference. Allowing you to easily store meta data against an object and
   * have the map entry including the meta data removed and collected when the
   * original object itself is unreachable
   */
  public void testWeakHashMap() throws InterruptedException{
    
    System.out.println("\nCreating weakHashMap");
    
    // This is now a weak reference.
    // The object will be collected only if no strong references.
    Referred strong = new Referred();
    Map<Referred,String> metadata = new WeakHashMap<Referred,String>();
    metadata.put(strong, "WeakHashMap's make my world go around");

    this.garbageCollect(); // Attempt to claim a suggested reference.
    System.out.println("WeakHashMap still has metadata entry? " + (metadata.size() == 1));
    System.out.println("Removing the strong reference, make the object is eligibled for GC ");
    strong = null;  //The object Referred may now be collected. 
    this.garbageCollect();

    System.out.println("WeakHashMap still has metadata entry? " + (metadata.size() == 1));

    System.out.println("Done");
    
  }
  
  
  
  public static void main(String[] args) throws InterruptedException{
    
    TestReference t = new TestReference();
    
//    t.testStrongRef();
//    
//    t.testWeakRef();
//    
//    t.testSoftRef();
    
    t.testPhantomRef();
    
    t.testReferenceQueueWithWeakRef();
    
    t.testWeakHashMap();
    
  }
  
}
