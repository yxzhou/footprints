package fgafa;

import java.util.*;

public class Test
{

  String test = "11";
  
  public void setTest(String test) {
    test = test;
  }

  public void setTest2(String test) {
    this.test = test;
  }
  
  public void setTest3(final String test) {
    this.test = test;
  }
  
  static int i = 5;
  
  public static void main(String[] args) throws Exception {

    Set<Integer> primes = new HashSet<>(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29));

    System.out.println( Integer.toBinaryString(250));
    System.out.println( -1 % 16);


    int x =  -1 & 1;
    System.out.println( -1 & 1 );
    System.out.println( -2 & 1 );
    System.out.println( 1 & 1 );
    System.out.println( 2 & 1 );

    System.out.println(" == " + (int) 8682522807148012L);

    String.valueOf(1);
    Integer.toString(1);


    float a = 12;
    double d = 18;
    
    Test test = null;
    System.out.println(" == "+test.i);
    System.out.println(" == "+Test.i);
    
    System.out.println("String ".trim() == "String");
    System.out.println(" String".trim() == "String");
    System.out.println("String".trim() == "String");
    
    Test tb = new Test();
    
    System.out.println(System.currentTimeMillis());
    
    //test setTest
    System.out.println(tb.test);
    tb.setTest("settest with this and final");
    System.out.println(tb.test);
    tb.setTest2("settest with this");
    System.out.println(tb.test);
    tb.setTest3("settest with final");
    System.out.println(tb.test);
    
    
    // test regex 
    //testRegex();
    
    // test the mod in 2 sign int 
    //testMOD(); 
    
    // check how many 1 in a 32 bit long int.
//    long tmp1 = System.currentTimeMillis();
//    long tmp2 = tmp1 - 12345678;
//    
//    System.out.println( "--- " + Long.toBinaryString(tmp1));.2
//    System.out.println( "--- " + Long.toBinaryString(tmp2));
//    System.out.println( "--- " + Long.toBinaryString(tmp1 ^ tmp2));
//    count1inLong(tmp1 ^ tmp2);
    
    
    //count1inN(1111111110L);  //1111111110L
    
//    test t = new test();
//    t.foundTheNum();
    
    //test & and | and xor
//    int a = 11;
//    int b = 15;
//    //int c = 1011b;
//    //int i = 101110101b;
//    int d = 012;
//    int f = 0x12;
//    
//    
//    System.out.println( "--- " + (a & b));
//    System.out.println( "--- " + (a | b));
//    System.out.println( "--- " + (a ^ b));
//    System.out.println( "--- " + ((a & b) << 1));
    
    //
//    int m = 355, n = 35;
//    int k = 0x80000000;
//    
//    System.out.println( "--- " + (((n-m) ) >> 31) );
//    System.out.println( "--- " + (((n-m) ) >>> 31) );  
//    
//    System.out.println( "--- " + (((n-m) & k) >>> 31) );  
//    int i = 7;
//    //if(i)
//    if(((double)(3 + 1) / 3 * 6 ) == 8)
//    System.out.println( "--- "  );
    
    if(1==1){
      System.out.println( "-111-- "  );
    }else if(2==2){
      System.out.println( "-22-- "  );
    }else{
      System.out.println( "-333-- "  );
    }

    int y = 1;
    if( y == 1 ){
      System.out.println( "-111-- "  );
      y = 2;
    } else if(y == 2){
      System.out.println( "-22-- "  );
      y = 3;
    } else{
      System.out.println( "-333-- "  );
    }


//    tb.testIterator();

  //test lambda
    Runnable r = () -> System.out.println("Hello Lambda");

    new Thread(r).start();

  }
  
  public static void maxtest()
  {
      int res = 0;
      for( int idx = 0; --idx != 0; )
          // res = ( res > idx ) ? res : idx;
          res = Math.max( res, idx );
      
      for(int i = 100000; i > 0; i--) {}
      for(int i = 1; i < 100001; i++) {}

      System.out.println( "res: " + res );
  }
  
  public final static ArrayList<String> readtest(){
    return null;
  }
  
  /*
   * check how many 1 from 0 to N, N is a UNSIGN. 
   * 
   * example:  N = 321; the 1 appears in (1, 11, 12, ..., 19, 21, ..., 100,101, 110, ..., 199, 210, ...  )  
   * 
   * 
   */
  
  public static long count1inN(long input){
    long returnValue = 0; 
    
    //example: to a 5 digit number, abcde. 
    // "c" is in the hundred, "ab" is the higherNums, "de" is the lowerNums.
    long currNum; //the current digit
    long higherNums;
    long lowerNums;
    
    long factor = 1;
    long tmp;
    while((tmp = input / factor) != 0  ){
      
      higherNums = tmp / 10;
      currNum = tmp % 10 ; 
      lowerNums = input - tmp * factor ;
      
      switch(Integer.parseInt(String.valueOf(currNum))){
        case 0:
          returnValue += higherNums * factor;
          break;
        case 1:
          returnValue += higherNums * factor + lowerNums + 1;
          break;
        default:
          returnValue += (higherNums + 1) * factor;
          break;
        
      }

      factor *= 10;
    }
      
    System.out.println( "Fount " + returnValue+ " 1 from 0 to " + input );
    return returnValue;
  }
  
/*
 * check how many 1 in a 32 bit long int
 */
  public static void count1inLong(long input){
    
    if(input < 0)
      input = System.currentTimeMillis();
    
    long tmp = input; 
    
    long cnt = 0;
    while(tmp > 0){
      tmp &= tmp -1;
      cnt ++;
    }
    System.out.println( "Fount the binary of " + Long.toBinaryString(input)+ " have " + cnt + " 1. ");
    
  }
  

  /*
   * test regex
   */
  public static void testRegex() {

    String strTmp = "Jobs (7months) ";
    //strTmp = strTmp.replaceAll(" ", "");
    //strTmp = strTmp.replaceAll("(", "");
    //strTmp = strTmp.replaceAll(")", "");
    strTmp = strTmp.replaceAll("[ ()]", "");

    System.out.println(strTmp);

  }



  public void testIterator(){
    
    ArrayList<String> someList = new ArrayList<String>();
    someList.add("11");
    someList.add("22");
    someList.add("33");

    for(Iterator<String> i = someList.iterator(); i.hasNext(); ) {
      i.remove();
      
      String item = i.next();
      
      System.out.println(item);
      
    }
    
    for(String item : someList ) {

      System.out.println(item);
      
    }
  }


  
  public void foundTheNum(){
    //init
    int[] reg = new int[30];
    int index = 0;
    for(int i=2; i < 32; i ++){
      reg[index ++] = i;
    }

    System.out.println("--Time--"+System.currentTimeMillis());
    
    for(long i =1; i< 100000000; i++){
      int hit = 0;
      int hit1 = -1;
      int hit2 = -1;
      
      for(int j=0; (j < reg.length)&(hit <= 2); j++){
        if ( (i % reg[j]) != 0 ){
          
          hit ++;
          if(hit == 1){
            hit1 = j;
          }else if(hit == 2){
            hit2 = j;
          }else
            break;
          
        }
        
        //System.out.println(" --test " + i + " j=" + j);
        
      }
      
      if((hit == 2) && (hit1 + 1 == hit2 ) ){
        System.out.println("Found " + i + " " + reg[hit1] +" " + reg[hit2]);
        System.out.println("--Time--"+System.currentTimeMillis());
      }
        
      
    }
    
    System.out.println("--Time--"+System.currentTimeMillis());

      List<Integer>[] neighbors = new ArrayList[2];

  }


  
}
