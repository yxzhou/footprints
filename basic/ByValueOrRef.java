package fgafa.basic;

public class ByValueOrRef
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    ByValueOrRef s = new ByValueOrRef();
    
    int pilot = 0;
    
    
    //test basic type
    // int
    
    int i = pilot;
    s.passInt(i);
    if(pilot == i)
      System.out.println("-int--NOT changed by reset !-");
    else
      System.out.println("-int--changed by reset !-");
      
    
    //test int Array
    int[] intArr = new int[1];
    intArr[0] = pilot;
    if(pilot == intArr[0])
      System.out.println("-int array--NOT changed by reset !-");
    else
      System.out.println("-int array--changed by reset !-");    
      
    
    //test struct or class
    
    //Integer
    Integer ig = new Integer(pilot);
    s.passInteger(ig);
    if(pilot == ig.intValue())
      System.out.println("-Integer--NOT changed by reset !-");
    else
      System.out.println("-int--changed by reset !-");
    
    //String
    String str = "-String--NOT changed in pass !-";
    s.passString(str);
    System.out.println(str);
    
    //StringBuffer 
    StringBuffer sb = new StringBuffer("-StringBuffer--NOT changed in pass !-");
    
    s.passStringBuffer(sb);
    System.out.println(sb);
    
  }

  private void passInt(int i){
    i = 1;
  }

  private void passInteger(Integer i){
    i = 1;
    i = Integer.valueOf(2);  
  }

  private void passIntArray(int[] i){
    for(int j = 0; j< i.length; j++){
      i[j] += 1; 
    }
  
  }  
  
  private void passString(String str){
    str = "-String--changed by reset !-";
  }
  
  private void passStringBuffer(StringBuffer sb){
    sb.append("-StringBuffer--changed by append !-");
    sb = new StringBuffer("-StringBuffer--changed by reset !-");
  }
  
  
  
}
