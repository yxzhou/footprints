package basic;

public class DataTransfer
{
   
  public static void main(String[] args) {
    byte b = 10;
    //b = b + 10;  //compile error: cannot convert byte to int
    b += 10;     // b=20; 
    
    char c= 3;
    int a = 65;
    //char d = a;     //compile error: cannot convert int to char
    
    char e = 65;
    
    //float f = 1.3;  //compile error: cannot convert double to float
    float f2 = 1.3f;
    float f3 = 6/2; //3.0
    //float f4 = 6.0/2.0; //compile error: cannot convert double to float
    
    byte b1;
    int a1 = 65;
    //b1 = a1;  //compile error: cannot convert int to byte
    
    final int a2 = 65;
    b1 = a2;  //please pay attention to the above final 
    
    byte b2;
    final int a3 = 10;
    final int z2 = a3;
    b2 = z2;   //b2 = 10
    
    byte b3;
    final int a4;
    //final int z3 = a4;//error,  the local variable a3 may not be initialized
    //b3 = z3;   //compile error: cannot convert int to byte    

  }

}
