package bitwise;

/*
 * Given an integer, print the next smallest and next largest number that have the same number of 1 bits
 * in their binary representation.
 * 
 * refer to careerCup bit 37 
 * 
 */

public class Permutation
{

  /*
   * Find the next smallest that have the same number of 1 bits in their binary representation
   * 1 From the right to left, find the first "01", convert the first "01" to "10"
   * 2 To to right of "01", make the number as small as possible 
   * 
   * example ****011100 => ****101100 => ****100011 
   *            ****011100 
   * step#1: => ****101100,  "01" -> 10" 
   *         => ****100011,  "1100" -> "0011"
   * @ return: -1 means can't deal with it. 
   */
  public static int getNextSmallest(int n) {
    //check
    if(n < 1 || n >= Integer.MAX_VALUE -1)
      return -1;
      //throw new IllegalArgumentException("can't deal with such number: " + n);

    //init
    int index = 0;
    int countOne = 1;
    
    //1. From the right to left, find the first "01", convert the first "01" to "10"
    //From the right to left, find the first "1"
    while(!getBit(n, index++)){}
    //countOne ++;
    //find the next zero 
    while(getBit(n, index++)){
      countOne ++;
    }
    
    //convert "01" to "10"
    n = setBit(n, --index, true);
    n = setBit(n, --index, false);
    
    //2. To to right of "01", Make the number as small as possible by shifting all the ones as far to the right as possible.
    //To make it as small as possible: e.g.  "1100" -> "0011"
    //Method#1, there are 4 digits and 2 digits are 1.  so the higher (4-2) digits are 0
    while(index>=countOne){
      n = setBit(n, --index, false);
    }
    while(index>0){
      n = setBit(n, --index, true);
    }
    //method#2, it must be "1000", "1100", "1110" or "1111", we can just swap 
//    for(int j=0, k=index; j < k; j++, k-- )
//      swap(n, j, k);
    
    

    return n;
  }


  /*
   * Find the next smallest that have the same number of 1 bits in their binary representation
   * 
   * @ return: -1 means can't deal with it. 
   * 
   */
  public static int getNextSmallest_add(int n) {
    //check
    if(n < 1 || n >= Integer.MAX_VALUE -1)
      return -1;
      //throw new IllegalArgumentException("can't deal with such number: " + n);

    //init
    int countOne =  bitCount_and(n);
    
    while(n < Integer.MAX_VALUE){
      if(bitCount_and(++n) == countOne){
        return n;
      }
    }
    
    return -1;
  }
  

  /*
   * count how many 1 in a 32 bit int
   * 
   * e.g. 
   * 0110 & 0101 => 0100 & 0010 = > 0000
   * 
   */
  private static int bitCount_and(int input) {
    int tmp = input;

    int cnt = 0;
    while (tmp > 0) {
      tmp &= tmp - 1;
      cnt++;
    }

    return cnt;
  }


/*
 * Find the next largest that have the same number of 1 bits in their binary representation
 *   
 * 1 From right to left, find the first "10", convert "10" to "01"
 * 2 To the right numbers of "10", make the number as big as possible
 * 
 * example ****100011 => ****010011 => ****011100
 * 
 * @return: -1 means can't deal with it. 
 */
  public static int getNextLargest(int n) {
    //check input
    if(n <= 1 || n > Integer.MAX_VALUE -1)
      return -1;
      //throw new IllegalArgumentException("can't deal with such number: " + n);
      
    //init
    int index = 0;
    int countZero = 1;
    
    //1 From right to left, find the first "10", convert "10" to "01"
    //find the first zero
    while(getBit(n, index++)){}
    //countZero ++;
    
    //find the next one
    while(!getBit(n, index++)){
      countZero ++;
    }
     
    //convert "10" to "01"
    n = setBit(n, --index, false);
    n = setBit(n, --index, true);
      
    //2 To the right numbers of "10", make the number as big as possible
    //To the right numbers of "10", there are index digits and countZero 0.
    //To make it as big as possible, we turn off in [0,countZero) and turn on in [countZero,index), 
    while(index >= countZero){
      n = setBit(n, --index, true);
    }
    while(index > 0){
      n = setBit(n, --index, false);
    }
    
    return n;
  }
  
  public static int getNextLargest_substract(int n) {
    //check 
    if(n <= 1 || n > Integer.MAX_VALUE -1)
      return -1;
      //throw new IllegalArgumentException("can't deal with such number: " + n);
      
    //init
    int countOne = bitCount_and(n);
    
    while( n>=0 ){
      if(countOne == bitCount_and(--n)){
        return n;
      }
        
    }

    return -1;
  }


  /*
   * get the bit in the index position of n
   * 
   * @return: false means 0; true means 1  
   * 
   */
  private static boolean getBit(int n, int index) {
    return (n & (1 << index)) > 0;

  }
  /*
   * set the bit in the index position of n
   * 
   * @boolean b, true means to set 1, false means to set 0
   * @return: the new int  
   * 
   */
  private static int setBit(int n, int index, boolean b) {
    if(b)
      return (n | (1 << index));
    else
      return (n & ~(1 << index));
    
  }
  

  public static void main(String[] args) {
    System.out.println("------------Start-------------- " );
    
    /* test getNextSmallest and getNextLargest   
    //init
    int[] is = {Integer.MAX_VALUE - 1, 1, 257, };
    
    
    System.out.println("NOTE: when it can't deal with the input,");
    System.out.println("       return :" + Integer.toBinaryString(-1));
    
    //for (int i = 1; i < Integer.MAX_VALUE; i ++ ) {
    for(int i : is){
      System.out.println("---    To int :" + Integer.toBinaryString(i));

      System.out.println("Next Smallest :"
          + Integer.toBinaryString(getNextSmallest(i)));
      System.out.println("Next Smallest2:"
          + Integer.toBinaryString(getNextSmallest_add(i)));      
      System.out.println(" Next Largest :"
          + Integer.toBinaryString(getNextLargest(i)));
      System.out.println(" Next Largest2:"
          + Integer.toBinaryString(getNextLargest_substract(i)));
    }
   */
       
    /* test | 
    System.out.println("Test: |" + (3|4|5));
    */
   
    
    /* test num reverse  
//    StringBuffer sb = new StringBuffer();
//    sb.append(234);
//    System.out.println(sb.toString()); 
    
    int[] n = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 22, 33, 44, 55, 313, 1881};
    for(int i=0; i<n.length; i++){
      System.out.println(" BinaryString("+ n[i]+") "+ Integer.toBinaryString(n[i]));
      System.out.println(" BinaryString("+ n[i]+") "+ new StringBuilder(Integer.toBinaryString(n[i])).reverse());
      System.out.println(" = "+ n[i]+" =="+ (new StringBuilder().append(n[i])).reverse());

      //System.out.println((new StringBuffer().append(n[i])).toString()); 
    }
    */
    System.out.println("------------End-------------- " );

  }

}
