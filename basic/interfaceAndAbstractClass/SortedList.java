package basic.interfaceAndAbstractClass;


public class SortedList extends AbstractList implements MyList, Sortable
{
   
  
  /*- The type SortedList must implement the inherited abstract method AbstractList.addFirst(Object)*/
  public void addFist(Object item){
 // TODO Auto-generated method stub
  }
  
  /*- The type SortedList must implement the inherited abstract method Sortable.sort()*/
  public void sort(){
 // TODO Auto-generated method stub
  }
  
  /*- The type SortedList must implement the inherited abstract method List.add()*/
  public void add(){
 // TODO Auto-generated method stub
  }
  
  @Override
  /**ø
   * The type SortedList must implement the inherited abstract method List.getSize() and Sortable.getSize()
   * It may not be necessary if there is a getSize implemented method in AbstractList.   
   */
  public int getSize() {
    // TODO Auto-generated method stub
    return 0;
  }
  
  //@Override
  /*- The type SortedList must implement the inherited abstract method AbstractList.addFirst()*/
  public void addFirst(Object item) {
    // TODO Auto-generated method stub
    
  }
  

  
  /**
   * @param args
   */
  public static void main(String[] args) {
    
    /* Abstract class can be clarified, can't be initialized */
    //AbstractList abstractList = new AbstractList();
    /* Interface can be clarified, can't be initialized */
    MyList list ;
    
    /*The field SortedList.size is ambiguous*/
    /*Roughly this is a example/problem in multiple inherit. */
//    switch(SortedList.size){
//      case List.size:
//        System.out.println("SortedList.size is from List.size");
//      case Sortable.size:
//        System.out.println("SortedList.size is from List.size");
//      case AbstractList.size:
//        System.out.println("SortedList.size is from List.size");
//    }
    
    
    
  }





}
