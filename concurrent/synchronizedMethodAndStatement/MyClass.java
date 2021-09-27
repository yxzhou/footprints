package concurrent.synchronizedMethodAndStatement;


/*
 * 
 * Java provides two ways to achieve synchronization: synchronized method and synchronized
 * statement.
 *  1)Synchronized method: Methods of a class which need to be synchronized are declared with
 * “synchronized” keyword. If one thread is executing a synchronized method, all other threads
 * which want to execute any of the synchronized methods on the same 'objects' get blocked.
 * 
 *  2)Synchronized statement: It provides the synchronization for a group of statements rather
 * than a method as a whole. And also it needs to give the object on which these synchronized
 * statements will be applied, unlike in a synchronize statement.
 *
 *  3)
 *   synchronized(this): here 'this' is the reference to particular object, the instance of class.
 *
 *   synchronized(MyClass.class), or synchronized(this.getClass()),  here 'MyClass.class' is the reference to the class.
 *
 *   they are different
 *
 */
public class MyClass {

  //object level locking,  lock on instance reference
  private final Object lockOnObjectLevel = new Object();
  //class level locking, lock on class reference
  private final static Object lockOnClassLevel = new Object();

  /* construction cann't be synchronized */
  //public synchronized ThreadTest(){
  public MyClass(){
    synchronized(lockOnObjectLevel){ //lock on
      sleep(3000);
    }
  }

  //no locking
  public void unsynchronizedMethod(String caller){

    System.out.println(caller + "---start unsynchronizedMethod---" + clockin());
    sleep(3000);
    System.out.println(caller + "---end unsynchronizedMethod---"+ clockin());

  }

  //object level locking,  it's locked on "this" object
  public synchronized void synchronizedMethod1(String caller){
    System.out.println(caller + "---start synchronizedMethod1---" + clockin());
    sleep(5000);
    System.out.println(caller + "---end synchronizedMethod1---"+ clockin());
  }
  
  //object level locking, it's locked on "this" object
  public synchronized void synchronizedMethod2(String caller){
    System.out.println(caller + "---start synchronizedMethod2---" + clockin());
    sleep(5000);
    System.out.println(caller + "---end synchronizedMethod2---"+ clockin());
  }

  //object level locking,
  //And compare to synchronized method, here the lock is decided by yourself
  public void synchronizedStatementOnObjectLevel(String caller){
    //synchronized(lockOnObjectLevel){
      synchronized (this){
      System.out.println(caller + "---start synchronizedStatementOnObjectLevel---" + clockin());
      sleep(3000);
      System.out.println(caller + "---end synchronizedStatementOnObjectLevel---"+ clockin());
    }

  }

  //class level locking, it's locked on "this" class instead of object
  public static synchronized void staticSynchronizedMethod(String caller){
    System.out.println(caller + "---start staticSynchronizedMethod---" + clockin());
    sleep(6000);
    System.out.println(caller + "---end staticSynchronizedMethod---"+ clockin());
  }

  //class level locking
  public void synchronizedStatementOnClassLevel(String caller){
    synchronized(lockOnClassLevel){
      //synchronized(MyClass.class){
      //synchronized(this.getClass()){
      System.out.println(caller + "---start synchronizedStatementOnClassLevel---" + clockin());
      sleep(3000);
      System.out.println(caller + "---end synchronizedStatementOnClassLevel---"+ clockin());
    }
  }

  public static void sleep(int millsecond){
    try {
      Thread.sleep(millsecond);
    }
    catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }    
  }
  
  public static long clockin(){
    //System.out.println("It's :"+System.currentTimeMillis());
    return System.currentTimeMillis();
  }


    
  public static void main(String[] args) {
    
    //2 thread call non-static and static method of one object  
//    System.out.println("2 thread call non-static and static method of one object");
//    MyClass test = new MyClass();
//
//    MyThread1 consumer = new MyThread1(test);
//    consumer.start();  // 3 non-static method + 1 static method
//
//    MyThread1 consumer2 = new MyThread1(test);
//    consumer2.start();
    
    
    
    //2 thread call non-static and static method of 2 object  
    System.out.println("\n2 thread call non-static and static method of two objects");
    MyClass test1 = new MyClass();
    MyThread1 consumer21 = new MyThread1(test1);
    consumer21.start();  // 3 non-static method + 1 static method

    MyClass test2 = new MyClass();
    MyThread1 consumer22 = new MyThread1(test2);
    consumer22.start();

    
  }

}
