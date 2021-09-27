package design.mypatterns.singleton;


import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 *  Singleton means only one instance, so the construction method should be private.
 *
 *  1 early load, with final
 *  public class Singleton{
 *      public final static Singleton INSTANCE = new Singleton();
 *
 *      private Singleton(){ }
 *  }
 *
 *  2 early load, with static factory method, getSingleton()
 *  public class Singleton{
 *      private static Singleton uniqueInstance = new Singleton();
 *
 *      private Singleton(){ }
 *
 *      public static Singleton getInstance(){
 *          return uniqueInstance;
 *      }
 *  }
 *
 *  3 lazy load,
 *  public class Singleton{
 *      private Singleton(){ }
 *
 *      private static class LazyHolder{
 *          static final Singleton INSTANCE = new Singleton();
 *      }
 *
 *      public static Singleton getInstance(){
 *          return LazyHolder.INSTANCE;
 *      }
 *  }
 *
 *  LazyHolder is private static class, The JVM guarantees LazyHolder is initialized until the static method getInstance is invoked.
 *
 *  3  ENUM
 *  public enum Singleton{
 *      INSTANCE;
 *      //public final static Singleton INSTANCE = new Singleton();
 *
 *      //something other method
 *  }
 *
 *  4 lazy load,  double-checked locking, see below
 *
 *  Another problem with conventional singletons are that once you implement serializable interface, the default
 *  readObject() method always return a new instance just like constructor in Java. So it need - -
 *  #3-ENUM based singleton provides the serialization machinery for free.
 *
 */

public class Singleton implements Serializable{
    private static final long serialVersinoUID = 5765648836796281035L;
    private volatile static Singleton instance = null;
    
    private Singleton(){
        
    }
    /**
     * double-checked locking
     *
     * @return: The same instance of this class every time
     */
    public static Singleton getInstance() {
        if(null == instance){
            synchronized(Singleton.class){
                if(null == instance){
                        instance = new Singleton();
                }
            }
        }
        
        return instance;
    }


    /**
     *     readResolve 方法维持了Singleton的单例属性
     */
    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

}
