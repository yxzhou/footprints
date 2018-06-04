package fgafa.basic.interfaceAndAbstractClass;

public abstract class AbstractList
{
  /*abstact class can have non-static  */
  protected static int size = 1;
  
  /* abstract class can have implemented method for subclass inherit */
  public int getSize(){
    return size;
  }
  
  /*  */
  public abstract void addFirst(Object item);
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
