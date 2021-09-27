package basic;

import org.junit.Test;

public class DefaultValue
{
  
  private static int x;
  
  public void incV(){
    x++;
  }

  public static void decV(){
    x--;
  }
  
  public void arrayTest(){
      int[][] arrays = new int[2][2];
      
      for(int i = 0; i < arrays.length; i++){
          System.out.println(arrays[i]);
          
          for(int j = 0; j < arrays[i].length; j++){
              System.out.println(arrays[i][j]);
          }
      }
      
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
      DefaultValue sv = new DefaultValue();
      
      
      sv.arrayTest();

  }

}
