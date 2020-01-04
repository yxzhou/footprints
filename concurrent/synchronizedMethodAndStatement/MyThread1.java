package fgafa.concurrent.synchronizedMethodAndStatement;


public class MyThread1 extends Thread {

  private MyClass test;
  
  public MyThread1(MyClass test){
    this.test = test;
  }
  
  public void run() {

    test.synchronizedMethod1(this.getName());
    
    test.synchronizedMethod2(this.getName());
    
    test.synchronizedMethod1(this.getName());

    test.staticSynchronizedMethod(this.getName());

    test.synchronizedStatementOnObjectLevel(this.getName());

    test.synchronizedStatementOnClassLevel(this.getName());
    
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
