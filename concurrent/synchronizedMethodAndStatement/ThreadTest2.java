package fgafa.concurrent.synchronizedMethodAndStatement;

public class ThreadTest2 extends ThreadTest
{

  //singleton
  public static final ThreadTest2 uniqueInstance = new ThreadTest2(); 
  private ThreadTest2(){
    
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {

    System.out.println("\n2 thread call non-static and static method of two objects");
    ThreadTest2 test21 = new ThreadTest2();
    Thread1 consumer = new Thread1(test21);
    consumer.start();  // 3 non-static method + 1 static method

    ThreadTest2 test22 = new ThreadTest2();
    Thread1 consumer2 = new Thread1(test22);
    consumer2.start();
    
//    Consumer consumer = new Consumer();
//    consumer.start();



  }

}
