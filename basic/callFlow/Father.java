package basic.callFlow;

public class Father
{

  private String name = "FATHER";
  private static String staticName = "static_FATHER";


  public Father() {
    System.out.println("Father construction loading!" );

    whoAmI();
    tellName(name);
  }



  public void whoAmI() {
    System.out.println("Father says, I am " + name + " and "+ staticName);
  }



  public void tellName(String name) {
    System.out.println("Father's name is " + name);
  }



  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
