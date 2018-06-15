package fgafa.concurrent.synchronizedMethodAndStatement;


public class Thread2 extends Thread
{

  private ThreadTest test;
  
  public Thread2(ThreadTest test){
    this.test = test;
  }
  
  public void run() {

    //ThreadTest test = new ThreadTest();
    test.synchronizedMethod1(this.getName());
  
    
//    test.method2(this.getName());
//    
//    test.method3(this.getName());

//    ThreadTest2 test2 = ThreadTest2.uniqueInstance;
//    test2.synchronizedMethod1(this.getName());
  }
  
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
