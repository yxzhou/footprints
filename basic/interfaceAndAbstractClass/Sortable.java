package fgafa.basic.interfaceAndAbstractClass;

public interface Sortable
{
  /* only public, static and final,  may not have been initialized */
  public static final int size = -1;
   
  /*All method default are abstract method, cannot have a body, may not be implemented*/
  public int getSize();
  
  public void sort();
  
  /* All method default are abstract method, cannot have a body, may not be implemented */
  //public static void main(String[] args){
  //  
  //}
}
