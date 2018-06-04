package fgafa.basic.inner;

import java.util.Iterator;

public class Outer {
  String name = "outer";
  static String name2;
   
  private static class StaticInner {
    int k;
    static int m;
    
    void test(){
      System.out.println("Static inner class test();" );
    }

    static void testStatic(){
      System.out.println("Static inner class testStatic();");
    }
    
  }

  private class MemberInner {
    public void test(){
      System.out.println("Member inner class test();");
    }

  }  

  int i;
  static int j;

  void test() {
      System.out.println("Ouetr test();");
      class LocalClass {
          int y;
          void test() {
              System.out.println("Inside local class test();");
          }
      }
  }
  


  public static void AnonymousInnerTest() {
    Runnable run = new Runnable() {
      public void run() {
        System.out.println("Inside the Anonymous inner class");
      }
    };
  }
  
  public Iterator<Integer> createIntegerIterator(final int from, final int to)
  {
      return new Iterator<Integer>(){
          int index = from;
          public Integer next()
          {
              return index++;
          }
          public boolean hasNext()
          {
              return index <= to;
          }
          
          @Override
          public void remove() {
            index--; 
            
          }
      };
  }

  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}

class OuterBrother{
  
}

