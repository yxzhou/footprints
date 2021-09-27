/**
 * 
 */
package basic;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author yxzhou
 *
 */
public class TestOperatorAndAssignment
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    
//    try{
//      String arrayObj = "10 286";
//
//      Double.parseDouble((String)arrayObj);
//              
//    }catch (Exception e){
//      e.printStackTrace();
//    }

    TestOperatorAndAssignment tester = new TestOperatorAndAssignment();
    
    //tester.testEncode();
      
    //tester.testBitwise();
    //tester.testShift();
    //tester.testAssignment();
    tester.testComparison();
    
    int j = 0;
    for (int k=0; j+k != 10; j++, k++){
      System.out.print("");
    }
    
  }

  private static void test(){

    
    
  }
  
  private void testEncode(){
    try {
      //http://spaces.telenav.com:8080/display/svr/1102-TNTWEB#1102-TNTWEB-Milestones 
      
      System.out.println(URLEncoder.encode("This string has spaces", "UTF-8"));
      System.out.println(URLEncoder.encode("This*string*has*asterisks", "UTF-8"));
      System.out.println(URLEncoder.encode("This%string%has%percent%signs", "UTF-8"));
      System.out.println(URLEncoder.encode("This+string+has+pluses", "UTF-8"));
      System.out.println(URLEncoder.encode("This/string/has/slashes", "UTF-8"));
      System.out.println(URLEncoder.encode("This\"string\"has\"quote\"marks", "UTF-8"));
      System.out.println(URLEncoder.encode("This:string:has:colons", "UTF-8"));
      System.out.println(URLEncoder.encode("This~string~has~tildes", "UTF-8"));
      System.out.println(URLEncoder.encode("This(string)has(parentheses)", "UTF-8"));
      System.out.println(URLEncoder.encode("This.string.has.periods", "UTF-8"));
      System.out.println(URLEncoder.encode("This=string=has=equals=signs", "UTF-8"));
      System.out.println(URLEncoder.encode("This&string&has&ersands", "UTF-8"));
      System.out.println(URLEncoder.encode("This?string?has?non-ASCII characters", "UTF-8"));
      }
      catch (UnsupportedEncodingException ex) {
        throw new RuntimeException("Broken VM does not support UTF-8");
      }

  }
  
  private void testBitwise(){
    int[] intA = {0, 1, 2, 3, 4, 5, 6, 7};
    
    for(int i = 0; i< intA.length; i ++ ){
      
      System.out.println("~" +  intA[i] + " is " + ( ~intA[i] ));
      
//      for(int j = 1; j< 3; j ++ ){
//        
//        System.out.println("a=" + intA[i] + ", b=" + intA[j] + ", a&b=" + (intA[i]& intA[j]) );
//        if ( (intA[i]& intA[j]) == intA[j] ) System.out.println("   ture");
//      }
      
    }
    
  }
  
  
  
  private void testShift(){

    byte x = -1; 
    int y = x >>> 5;
    
    System.out.println("x >> 5: " + (x >> 5));
    System.out.println("x >>> 5: " + (x >>> 5));
    
  }
  
  private void testComparison(){

    //-----------------------------------
    Integer ob1 = new Integer(1);
    Integer ob2 = new Integer(1);
    
    System.out.println("(ob1 == ob2) ? " + ((ob1 == ob2) ? "true" : "false" ));        // false
    System.out.println("(ob1.equals(ob2)) ?  " + ((ob1.equals(ob2)) ? "true" : "false" ));   // true 
    
    //-----------------------------------
    StringBuffer sb1 = new StringBuffer("11");
    StringBuffer sb2 = new StringBuffer("11");
    System.out.println("(sb1 == sb2) ?  " + ((sb1 == sb2) ? "true" : "false" ));        // false
    System.out.println("(sb1.equals(sb2)) ?  " + ((sb1.equals(sb2)) ? "true" : "false" ));   // false 
    
    sb2 = sb1;
    System.out.println("(sb1 == sb2) ?  " + ((sb1 == sb2) ? "true" : "false" ));        // true
    System.out.println("(sb1.equals(sb2)) ?  " + ((sb1.equals(sb2)) ? "true" : "false" ));   // true 
    
    StringBuffer sb3 = new StringBuffer("11");
    sb3.append(sb1);
    System.out.println("(sb1 == sb3) ?  " + ((sb1 == sb2) ? "true" : "false" ));        // true
    System.out.println("(sb1.equals(sb3)) ?  " + ((sb1.equals(sb2)) ? "true" : "false" ));   // true   
    System.out.println("(sb3.equals(sb1)) ?  " + ((sb1.equals(sb2)) ? "true" : "false" ));   // true     
    //-----------------------------------
    int p = 9;
    int q = 65;
    int r=-12;
    float f = 9.0f;
    char c = 'A';  // the Unicode value 65 
    
    System.out.println("  " + ((p < q) ? "true" : "false" ));   // true 
    System.out.println("  " + ((f < q) ? "true" : "false" ));   // true 
    System.out.println("  " + ((f <= c) ? "true" : "false" ));   // true 
    System.out.println("  " + ((c > r) ? "true" : "false" ));   // true 
    System.out.println("  " + ((c >= q) ? "true" : "false" ));   // true 
    
    
  }

  private void changeValue(Integer obj){
    
    //obj.
    
  }
  
  
  private void testAssignment(){

    int x = 4; 
    
    // 
    System.out.println(" value is " + ((x > 4)? "true": 9 ));
    System.out.println(" value is " + ((x > 4)? 99.99 : 9 ));
    System.out.println(" value is " + ((x > 4)? 10 : 9 ));
    System.out.println(" value is " + ((x > 3)? 10 : 9.0 ));
  }
  
  private void testSort(){
    String[] strA = {"1","11","12","2","21","22","3"};
    //int[] intA = {1,11,12,2,21,22,3};
    
    ArrayList ls1 = new ArrayList();
    for(int i = 0; i< strA.length; i ++ ){
      ls1.add(strA[i]);
    }
    
    Collections.sort(ls1);

      System.out.println("-------------string-------------" + ls1.toString());
        
      ArrayList ls2 = new ArrayList();
        for(int i = 0; i< strA.length; i ++ ){
          ls2.add(Integer.valueOf(strA[i]));
        }
     
      
    Collections.sort(ls2);
    
    System.out.println("-------------int-------------" + ls2.toString());
  }
  
  private void testByteSize(){
    String s = "0123456789ABCDEFFEDCBA987654321089ABCDEF01234567";
    try {
      System.out.println("=== " + s.getBytes("utf8").length);
      System.out.println("=== " + s.getBytes("unicode").length);
      System.out.println("=== " + s.getBytes("US-ASCII").length);
    }
    catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
  
}
