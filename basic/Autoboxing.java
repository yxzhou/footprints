package fgafa.basic;

/*
 Autoboxing. Type parameters have to be instantiated as reference types, 
 so Java automatically converts between a primitive type and its corresponding wrapper type in assignments, 
 method arguments, and arithmetic/logic expressions. 
 This conversion enables us to use generics with primitive types, as in the following code:
    Stack stack = new Stack();
    stack.push(17);        // auto-boxing (int -> Integer)
    int i = stack.pop();   // auto-unboxing (Integer -> int)
 Automatically casting a primitive type to a wrapper type is known as autoboxing, 
 and automatically casting a wrapper type to a primitive type is known as auto-unboxing.
 * 
 */

public class Autoboxing
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    //Q. How does auto-boxing handle the following code fragment?
        Integer a = 4;
        int b = a;    
        //Integer aa = null;
        //int bb = aa;
    //A. It results in a run-time error.  (java.lang.NullPointerException)
    //    Primitive type can store every value of their corresponding wrapper type except null.
        
    //--------------------------    
    //Q. Why does the first group of statements print true, but the second false?    
    Integer a1 = 100, a2 = 100;
    System.out.println(a1 == a2);   // true

    Integer b1 = new Integer(100);
    Integer b2 = new Integer(100);
    System.out.println(b1 == b2);   // false

    Integer c1 = 150;
    Integer c2 = 150;
    System.out.println(c1 == c2);   // false,  the different reference
    System.out.println("Integer.MAX_VALUE: "+ Integer.MAX_VALUE);
    
//    A. The second prints false because b1 and b2 are references to different Integer objects. 
//    The first and third code fragments rely on autoboxing. 
//    Surprisingly the first prints true because values between -128 and 127 appear to refer to the same immutable Integer objects
//      (Java implementation of valueOf() retrieves a cached values if the integer is between -128 and 127), 
//    while Java constructs new objects for each integer outside this range

    
    //------------------------------
    cmp(new Integer(42), 43);
    cmp(new Integer(42), new Integer(42));
    cmp(43, 43);
    cmp(142, 142);

    //double and Double
    double x1 = 0.0, y1 = -0.0;
    Double d1 = x1, dd1 = y1;
    System.out.println(x1 == y1);            //true
    System.out.println(d1.equals(dd1));      //false 
    System.out.println(d1 == dd1);      //false 
    
    double x2 = 0.0/0.0, y2 = 0.0/0.0;
    Double d2 = x2, dd2 = y2;
    System.out.println(x2 != y2);             //true
    System.out.println(!d2.equals(dd2));      //false
    System.out.println(d2 == dd2);      //false     
    
    //String
    String s1 = "Hellow";
    String s2 = "Hellow";
    System.out.println(s1 == s2);    // true,  there is a String pool.
    
    
      
    System.out.println(" for debug ");
  }

  public static void cmp(Integer first, Integer second) {
    if (first < second)
      System.out.printf("%d < %d\n", first, second);
    else if (first == second)
      System.out.printf("%d == %d\n", first, second);
    else if (first > second)
      System.out.printf("%d > %d\n", first, second);
    else
      System.out.printf("%d and %d are incomparable\n", first, second);
}  
  
  public void cmp(final int[] arr){
    
  }
  
}
