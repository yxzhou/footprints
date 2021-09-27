package basic;

public abstract class ControlFlow
{

  /**
   * @param args
   */
  public static void main(String[] args) {

    int s1 = 5, s2 = 3, i = 1, j=2, k=3, p=4; 
    
//    if(s == i + p){
//      System.out.printf("%d=%d + %d%n", s, i, p);
//    }else{
//      System.out.println("Others");
//    }
    
    
    if(s1 == j + k){
      System.out.printf("%d = %d + %d%n", s1, j, k);
    }else if( s2 == i + j){
      System.out.printf("%d = %d + %d%n", s2, i, k);
    }else if( s1 == i + p){
      System.out.printf("%d = %d + %d%n", s1, i, p);
    }else{
      System.out.println("Others");
    }

  }

}
