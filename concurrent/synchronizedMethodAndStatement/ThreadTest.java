package fgafa.concurrent.synchronizedMethodAndStatement;


/*
 * 
 * Java provides two ways to achieve synchronization: synchronized method and synchronized
 * statement.
 *  1)Synchronized method: Methods of a class which need to be synchronized are declared with
 * “synchronized” keyword. If one thread is executing a synchronized method, all other threads
 * which want to execute any of the synchronized methods on the same objects get blocked.
 * 
 *  2)Synchronized statement: It provides the synchronization for a group of statements rather
 * than a method as a whole. And also it needs to give the object on which these synchronized
 * statements will be applied, unlike in a synchronize statement.
 * 
 * 
 */
public class ThreadTest //extends Thread
{
  /* construction cann't be synchronized */
  //public synchronized ThreadTest(){
  public ThreadTest(){
    synchronized(locker){
      sleep(3000);
    }
  }
  
  public static Object locker = new Object();

  //default it's locked on "this" object
  public synchronized void synchronizedMethod1(String caller){
    System.out.println(caller + "---start synchronizedMethod1---" + clockin());
    sleep(5000);
    System.out.println(caller + "---end synchronizedMethod1---"+ clockin());
  }
  
  //default it's locked on "this" object
  public synchronized void synchronizedMethod2(String caller){
    System.out.println(caller + "---start synchronizedMethod2---" + clockin());
    sleep(5000);
    System.out.println(caller + "---end synchronizedMethod2---"+ clockin());
  }

  //default it's locked on "this" class instead of object
  public static synchronized void staticSynchronizedMethod(String caller){
    System.out.println(caller + "---start staticSynchronizedMethod---" + clockin());
    sleep(6000);
    System.out.println(caller + "---end staticSynchronizedMethod---"+ clockin());
  }
  
  //no locker
  public void unsynchronizedMethod(String caller){
    
    System.out.println(caller + "---start unsynchronizedMethod---" + clockin());
    sleep(3000);
    System.out.println(caller + "---end unsynchronizedMethod---"+ clockin());
    
  }

  //compare to synchronized method, here the locker is decided by yourself
  public void synchronizedStatement(String caller){
    synchronized(locker){
      System.out.println(caller + "---start method10---" + clockin());
      sleep(3000);
      System.out.println(caller + "---end method10---"+ clockin());      
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
  
  public void run() {

    synchronizedMethod1("ThreadTest");
    
//    test.method2(this.getName());
//    
//    test.method3(this.getName());
    
  }
    
  public static void main(String[] args) {
    
    //2 thread call non-static and static method of one object  
//    System.out.println("2 thread call non-static and static method of one object");
//    ThreadTest test = new ThreadTest(); 
//    
//    Thread1 consumer = new Thread1(test);
//    consumer.start();  // 3 non-static method + 1 static method 
//
//    Thread1 consumer2 = new Thread1(test);
//    consumer2.start();
    
    
    
    //2 thread call non-static and static method of 2 object  
    System.out.println("\n2 thread call non-static and static method of two objects");
    ThreadTest test1 = new ThreadTest();
    Thread1 consumer = new Thread1(test1);
    consumer.start();  // 3 non-static method + 1 static method 

    ThreadTest test2 = new ThreadTest();
    Thread1 consumer2 = new Thread1(test2);
    consumer2.start();
    
    
    //3 
//    ThreadTest test2 = test;
//    test.start();
//    
//    //ThreadTest test2 = new ThreadTest();
//    test2.start();
    
  }

}
