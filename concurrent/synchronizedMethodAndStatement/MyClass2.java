package fgafa.concurrent.synchronizedMethodAndStatement;

public class MyClass2 extends MyClass
{

  //singleton
  public static final MyClass2 uniqueInstance = new MyClass2();
  private MyClass2(){
    
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {

    System.out.println("\n2 thread call non-static and static method of two objects");
    MyClass2 test21 = new MyClass2();
    MyThread1 consumer = new MyThread1(test21);
    consumer.start();  // 3 non-static method + 1 static method

    MyClass2 test22 = new MyClass2();
    MyThread1 consumer2 = new MyThread1(test22);
    consumer2.start();
    
//    Consumer consumer = new Consumer();
//    consumer.start();


  }

}
