package fgafa;

public class MyObject
{

    private static int[] MyArray = {-2, 0, 3, 5};
  
    // returns null if the index is out of bounds, or returns the integer in that index
    public Integer get(int index){ 
      
      try{
        return MyArray[index];
      }catch(Exception e){
        return null;
      }
      
      
    };
    
    public Integer getIndex(int x){

    //1 get the minmum with MyObject.get(0),  if x<minmum, return null. 
    int minmum = get(0);
    if(x<minmum)
      return null; //out of bounds
    else if(x==minmum)
      return 0;
      
    //2 define left = 0, right = x-minmum,  pilot = get(right) 
    int left = 0, right = x-minmum, middle;
    Integer pilot;
    while(left < right){
      middle = left + ((right-left) >>  1);
      pilot = get(middle);
      
      if(pilot == null || pilot > x )
        right = middle -1;
      else if(pilot.equals(x) )
        return middle;
      else
         left = middle + 1;
      
    }

    return null;  // out of bounds.
    
  }

  
  /**
   * @param args
   */
  public static void main(String[] args) {
    
    int r = (1 << 31) - 1;
    System.out.println( Integer.toBinaryString(r) + " " + r);
    System.out.println( Integer.toBinaryString(2147483645) );
    System.out.println( Integer.toBinaryString(r ^ 2147483645) );
    
    r = (1 << 32) - 1;
    System.out.println( Integer.toBinaryString(r) + " " + r);
    System.out.println( Integer.toBinaryString(2147483645) );
    System.out.println( Integer.toBinaryString(r ^ 2147483645) );
    
    int t = -2;
    System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
    System.out.println(Integer.toBinaryString(2));
    System.out.println(Integer.toBinaryString(1));
    System.out.println(Integer.toBinaryString(0));
    System.out.println(Integer.toBinaryString(-1));
    System.out.println(Integer.toBinaryString(-2));
    System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
    
    System.out.println(t >> 32 - 1);
    System.out.println(t >> 31);
    System.out.println(t >> 32);
    
    int[] x = {-5, 0, 1, 7};
    
    MyObject sv = new MyObject();
    for(int i=0; i< x.length; i++)
      System.out.println(sv.getIndex(x[i]));
    
  }

}
