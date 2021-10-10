/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basic;

import java.util.TreeSet;

/**
 *
 * @author yuanxi
 */
public class TreeSetTest {
   public static void main(String[] args)
   {
      TreeSet<String> ts = new TreeSet<String>();
      
      //add("yes")
//add("lint")
//add("code")
//add("yes")
//add("code")
//topk()
//add("baby")
//add("you")
//add("baby")

      ts.add("lint");
      ts.add("code");
      ts.add("yes");
      
      System.out.println("before remove code " + ts);
      ts.remove("code");
       System.out.println("after remove code " + ts); 
      ts.add("code");
      System.out.println("before remove yes " + ts); 
      ts.remove("yes");
       System.out.println("after remove yes " + ts); 
      ts.add("yes");
       System.out.println("Given TreeSet: " + ts); 
      
      ts.add("baby");
      ts.add("yellow");
      ts.add("yes");
      System.out.println("Given TreeSet: " + ts);
      // remove elements using remove() method
      ts.remove("blue");
      ts.remove("violet");
      ts.remove("red");
      ts.remove("yes");
      // print TreeSet after removal
      System.out.println("New TreeSet: " + ts);
   }
   
}
