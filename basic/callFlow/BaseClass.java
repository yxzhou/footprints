package basic.callFlow;

public class BaseClass
{

  {
    System.out.println("BaseClass class level part ##1 loading!");
  }
  
  BaseClass()
  {
      System.out.println("BaseClass construction loading!");
  }
  
  static
  {
      System.out.println("BaseClass static part loading !!");
  }
  
  {
    System.out.println("BaseClass class level part ##2 loading!");
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  class innerBase{
    {
      System.out.println("innerBase class level part ##1 loading!");
    }
    
    innerBase(){
        System.out.println("innerBase construction loading!");
    }
    
    //Cannot define static initializer in inner type DerivedClass.innerDerived
//    static{
//        System.out.println("innerBase static part loading !!");
//    }

    {
      System.out.println("innerBase class level part ##2 loading!");
    }
  }
  
}
