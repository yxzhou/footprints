package fgafa.basic.test;

public class Test2
{

  public class superclass
  {
    int i;



    superclass() {
      i = 1;
    }



    void f() {
      System.out.println("i=" + i);
    }
  }

  public class subclass extends superclass
  {
    int i;



    subclass() {
      i = 2;
    }



    void f() {
      super.f();
      // System.out.println("i="+i);
    }

  }



  public static void main(String[] args) {
    Test2 sv = new Test2();
    subclass t = sv.new subclass();
    t.f();
  }

}
