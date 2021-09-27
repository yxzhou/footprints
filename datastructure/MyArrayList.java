package datastructure;

public class MyArrayList<E>
{
  

  private static final long serialVersionUID = 8683452581122892189L;
  
  private transient Object[] elementData;
  private int size;
  
  
  //construction
  public MyArrayList(int initialCapacity) {
  super();
      if (initialCapacity < 0)
          throw new IllegalArgumentException("Illegal Capacity: "+
                                             initialCapacity);
  this.elementData = new Object[initialCapacity];
  }


  public MyArrayList() {
    this(10);
  }
  
  
  public int size() {
    return size;
  }
  public boolean isEmpty() {
    return size == 0;
  }
  
  

  public int indexOf(Object o) {
    if (o == null) {
      for (int i = 0; i < size; i++)
        if (elementData[i] == null)
          return i;
    }
    else {
      for (int i = 0; i < size; i++)
        if (o.equals(elementData[i]))
          return i;
    }
    return -1;
  }



  public int lastIndexOf(Object o) {
    if (o == null) {
      for (int i = size - 1; i >= 0; i--)
        if (elementData[i] == null)
          return i;
    }
    else {
      for (int i = size - 1; i >= 0; i--)
        if (o.equals(elementData[i]))
          return i;
    }
    return -1;
  }
  
  private void RangeCheck(int index) {
    if (index >= size)
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
  }

  public E get(int index) {
    RangeCheck(index);

    return (E) elementData[index];
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
