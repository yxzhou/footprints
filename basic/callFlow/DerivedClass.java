package fgafa.basic.callFlow;

public class DerivedClass extends BaseClass
{

  {
    System.out.println("DerivedClass class level part ##1 loading!");
  }
  
  DerivedClass()
  {
      System.out.println("DerivedClass construction loading!");
  }

  DerivedClass(int i)
  {
      System.out.println("DerivedClass construction loading!" + i);
  }
  
  static
  {
      System.out.println("DerivedClass static part loading !!");
  }
  
  {
    System.out.println("DerivedClass class level part ##2 loading!");
  }
  
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    //new DerivedClass();
    
    new DerivedClass(3);
  }

  
  class innerDerived{
    {
      System.out.println("innerDerived class level part ##1 loading!");
    }
    
    innerDerived(){
        System.out.println("innerDerived construction loading!");
    }
    
    //Cannot define static initializer in inner type DerivedClass.innerDerived
//    static{
//        System.out.println("innerDerived static part loading !!");
//    }

    {
      System.out.println("innerDerived class level part ##2 loading!");
    }
  }
  
}
