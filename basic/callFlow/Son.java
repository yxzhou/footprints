package fgafa.basic.callFlow;

/*
 * It's a exampel to understand Inheritance and Polymorphism and Override and call flow.
 * 
 * Son says, I am null
 * Son's name is FATHER
 * Son says, I am SON
 * Son's name is SON
 * 
 * Reason:
 * 1.load the FATHER's construction first, load the SON's construction second
 * 2.In FATHER's construction, there is whoAmI() and tellName(string); they has been overrided by SON. 
 * 3.这一条最复杂，由于JAVA是动态运行/加载的，只要当执行只才分配值，此时只是FATHER被new入内存，SON还没有，所以此时的状态是FATHER.name=FATHER,SON.name=null.其它的都简单了
 * 
 */

public class Son extends Father
{
  
  private String name = "SON";
  private static String staticName = "static_SON";

  public Son() {
    System.out.println("Son construction loading!");

    whoAmI();
    tellName(name);
  }



  public void whoAmI() {
    System.out.println("Son says, I am " + name + " and " + staticName);
  }



  public void tellName(String name) {
    System.out.println("Son's name is " + name);
  }



  /**
   * @param args
   */
  public static void main(String[] args) {

    new Son();

  }

}
