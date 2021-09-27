package basic.test;

import java.util.Set;
import java.util.TreeSet;

public class Main {
   public static final void main(StringTest[] args) {
    
    System.out.println(Float.MAX_VALUE);    //3.4028235E38 , 2128 - 1
    System.out.println(Float.MIN_VALUE);    //1.4E-45,       2-149
    
    System.out.println(Double.MAX_VALUE);   //1.7976931348623157E308 , 21024 - 1
    System.out.println(Double.MIN_VALUE);   //4.9E-324,                2-1074

    
    Drivable v = new Car();
    v.drive();
    
    
    Set<String> treeSet = new TreeSet<String>();
    //treeSet.add("0");
    treeSet.add(null);
    treeSet.add("1");
    
  }
   


   
}