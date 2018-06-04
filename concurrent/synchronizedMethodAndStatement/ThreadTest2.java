package fgafa.basic.thread.synchronizedMethodAndStatement;


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
    
    Thread2 creater = new Thread2();
    creater.start();
   
    Thread2 creater2 = new Thread2();
    creater2.start();
    
//    Consumer consumer = new Consumer();
//    consumer.start();



  }

}
