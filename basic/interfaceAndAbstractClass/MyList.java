package basic.interfaceAndAbstractClass;

public interface MyList
{
  /* only public, static and final,  may not have been initialized */
   public static final int size = 0;
    
   /*default it's abstract method, may not be implement*/
   public int getSize();
   
   public void add();
}
