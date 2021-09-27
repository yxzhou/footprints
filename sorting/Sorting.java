/**
 * 
 * nondecreasing order
 * 
 */
package sorting;

import java.util.Comparator;
import java.util.Random;

/**
 * @author yxzhou
 *
 */
public class Sorting
{
  
  /*
   * 
   * list the array to a String with comma .
   * @param seqNum, a array
   *  
   * @return "a,b,"
   * 
   *  
   
  public static int[] cloneArray(int[] seqNum ){
    //initial
    int[] returnValue = null;
    
    //check
    if( seqNum == null  ) 
      return returnValue;
    else 
      returnValue = new int[seqNum.length];
    
    for(int i=0; i< seqNum.length; i++){
      returnValue[i] = seqNum[i];
    }
    
    return returnValue;
  }
 */ 
  
  /*
   * 
   * compare if the content is same in 2 arrays.
   * @param a1, a array A
   * @param a2, a array B
   *  
   * @return true, contain the same value, or return false. 
   * To 2 NULL array, it will return false. 
   *  
   */ 
  public static boolean compareArray(int[] a1, int[] a2){
    //initial
    boolean returnValue = false;
    
    //check
    if( a1 == null || a2 == null || a1.length != a2.length ) 
      return returnValue;
    
    for(int i=0; i< a1.length; i++){
      if(a1[i] != a2[i]){
        return returnValue;
      }
    }
    
    returnValue = true;
    return returnValue;
  }

  
  /*
   * 
   * list the array to a String with comma .
   * @param seqNum, a array
   *  
   * @return "a,b,"
   * 
   *  
   */ 
  public static StringBuffer toString(int[] seqNum ){
    //initial
    StringBuffer sb = new StringBuffer();
    
    //check
    if( seqNum == null  ) 
      return sb;
    
    for(int i=0; i< seqNum.length; i++){
      
      sb.append(seqNum[i]);
      sb.append(",");
      
    }
    
    return sb;
  }
  
  /*
   * 
   * There is a array and 2 members,  swap the 2 member position.
   * @param seqNum
   * @param seq1
   * @param seq2
   * 
   * @return -1, means not change successfully.  1 means change successfully.  
   * 
   *  
   */ 
  public static int swap(int[] seqNum, int seq1, int seq2, long[] operateCount ){
    //check
    if( seqNum == null || seqNum.length < seq1 + 1 || seqNum.length < seq2 + 1 ) 
      return -1;

    operateCount[0] = operateCount[0] + 1; 
    //System.out.println("------------swap------------operateCount:"+operateCount[0]+"--seq1:"+seq1+ " --seq2:"+seq2+"--seqNum:"+ toString(seqNum));
    
    int iTmp = seqNum[seq1];
    seqNum[seq1] = seqNum[seq2];
    seqNum[seq2] = iTmp;
    
    return 1;
  }
  
  /*
   * 
   * There is a array and 2 members,  
   * change (a1, a2, Dest, a3, a4, a5, Mover, a6) to (a1, a2, Mover, Dest, a3, a4, a5, a6) Or
   * change (a1, a2, Mover, a3, a4, a5, Dest, a6) to (a1, a2, a3, a4, a5, Mover, Dest, a6) 
   * 
   * @param seqNum
   * @param seq1
   * @param seq2
   * 
   * @return -1, means not change successfully.  1 means change successfully.  
   * 
   *  
   */ 
  private static int insert(int[] seqNum, int destination, int mover, long[] operateCount ){
    //check
    if( seqNum == null || seqNum.length < destination + 1 || seqNum.length < mover + 1 || destination == mover) 
      return -1;

    //System.out.println("------------insert------------operateCount:"+operateCount[0]+"--des:"+destination+ " --mover:"+mover+"--seqNum:"+ toString(seqNum));
    if ( destination < mover ) {
      //change (a1, a2, Dest, a3, a4, a5, Mover, a6) to (a1, a2, Mover, Dest, a3, a4, a5, a6) 
      //move back from destination to mover - 1
//      for(int i=mover; i>destination; i-- )
//        swap(seqNum, i, i--, operateCount);
      
      int tmp = seqNum[mover];
      for(int i=mover; i>destination; i-- ){
        seqNum[i] = seqNum[i-1]; 
        operateCount[0] = operateCount[0] + 1;
      }
      
      seqNum[destination] = tmp;
    }
    else {
      //change (a1, a2, Mover, a3, a4, a5, Dest, a6) to (a1, a2, a3, a4, a5, Mover, Dest, a6) 
      //move forward from destination + 1 to mover - 1
//      for(int i=mover; i<destination-1; i++ )
//        swap(seqNum, i, i++, operateCount);        
      
      int tmp = seqNum[mover];
      for(int i=mover; i<destination-1; i++ ){
        seqNum[i] = seqNum[i+1];
        operateCount[0] = operateCount[0] + 1;
      }
      
      seqNum[destination-1] = tmp;
    }
         
    return 1;
  }
  
  /*
   * 
   * There is a array and a number, do binary select between left and right in the array .
   * @param seqNum, a array with nondecreasing order
   * @param left, start point in the array
   * @param right, end point in the array
   * @param num, a number.
   *  
   * @return true, the position where the num is in the array. 
   * -1 means not found.
   *  
   */ 
  public static int binarySelection(int[] seqNum, int left, int right, int num){
    //initial
    int returnValue = -1;
    
    //check
    if( seqNum == null || seqNum.length <= left || seqNum.length <= right || left > right ) 
      return returnValue;
    
    int middle = 0; 
    while( right >= left)
    {
      middle = ( left + right )/2;

      if( num > seqNum[middle] )
        right = middle-1;
      else
        left = middle+1;    
    }
        
    returnValue = left;
    return returnValue;
  }
  
  /*
   * implement bubble sort into nondecreasing order 
   * 
   */
  private void bubbleSort( int[] seqNum ){
    long timeCount1 = System.currentTimeMillis();
    long[] operateCount = new long[1]; 
    //System.out.println("------------start--------------" );
    
    //check
    if (seqNum == null || seqNum.length < 2 )
      return;
    
    int j=0;

    //always put the min in the end
//    for(int i=seqNum.length; i > 1; i --){
//      for(j=0; j < i - 1; j ++ ){
//        if ( seqNum[j] < seqNum[j+1] ) 
//          swap(seqNum, j, j+1, operateCount);
//      }
//    }
        
    //always put the max in the header
    for(int i=1; i < seqNum.length; i ++){
      for(j=seqNum.length -1; j >= i; j --){
        if ( seqNum[j-1] < seqNum[j] ){
          swap(seqNum, j-1, j, operateCount);
        }
        //operateCount[0] = operateCount[0] + 1; 
      }
    }
    
    long timeCount2 = System.currentTimeMillis();
    System.out.println("--------bubbleSort---------Time:" + (timeCount2 - timeCount1) + " --Operate:- " + operateCount[0] );
  }
  
  
  
  /*
   * implement insert sort into nondecreasing order 
   * 
   */
  public void insertSort( int[] seqNum ){
    long timeCount1 = System.currentTimeMillis();
    long[] operateCount = new long[1]; 
    //System.out.println("------------start--------------" );
    
    //check
    if (seqNum == null || seqNum.length < 2 )
      return;
    
    int j=0;

    for(int i=1; i < seqNum.length; i ++){
      
      //System.out.println("------------start--------------i:"+i +"--seqNum:"+ toString(seqNum));
      j=i-1;
      
      //one by one
//      while(j>=0 && seqNum[i] > seqNum[j]){
//        j--;        
//      }
//      insert(seqNum, j+1, i, operateCount );
      
      
      //binary selection
      j = binarySelection(seqNum, 0, j, seqNum[i]);
      insert(seqNum, j, i, operateCount );
      
    }
    
    long timeCount2 = System.currentTimeMillis();
    System.out.println("-----------insertSort------Time:" + (timeCount2 - timeCount1) + " --Operate:- " + operateCount[0] );
    
  }
  
  
  /*
   * 
   */
  
  private static int partition(int[] array, int begin, int end, long[] operateCount) {
    //Random RND = new Random()
    //int index = begin + RND.nextInt(end - begin + 1);
    int index = begin + (int)(Math.random() * ((end - begin) + 1)) ;  // get a random in [begin, end-1] 
    
    int pivot = array[index];
    swap(array, index, end, operateCount);     
    for (int i = index = begin; i < end; ++ i) {
        if (array[i] > pivot) {
            swap(array, index++, i, operateCount);
        } 
    }
    swap(array, index, end, operateCount);     
    return (index);
  }
  
  
  private void quickSort(int[] array, int begin, int end, long[] operateCount) {
    if (end > begin) {
        int index = partition(array, begin, end, operateCount);
        quickSort(array, begin, index - 1, operateCount);
        quickSort(array, index + 1,  end,  operateCount);
    }
  }
  
  public void quickSort( int[] array ){
    long timeCount1 = System.currentTimeMillis();
    long[] operateCount = new long[1]; 
    //System.out.println("------------start-quickSort-10------------" );
    
    //check
    if (array == null || array.length < 2)
      return;
      
    quickSort(array, 0, array.length - 1, operateCount);
    
    long timeCount2 = System.currentTimeMillis();
    System.out.println("-----------quickSort------Time:" + (timeCount2 - timeCount1) + " --Operate:- " + operateCount[0] );
 
  }
  
  
  /*
   * 
   * There is two array in nondecreasing order,  merge them into one array.
   * @param a, the first array 
   * @param b, the second array 
   *  
   * @If a and b both are null, return null ; If one is null, the other is not null, return the one that not null; 
   * Or merge them in nondecreasing order
   *  
   */
  /*
  public static int[] merge(int[] a, int[] b ) {
    //check
    if(a==null && b==null)
      return null;
    else if( a==null)
      return b;
    else if( b==null)
      return a;
           
    //initial
    int[] returnValue = new int[a.length + b.length];
    
    int i=0, j = 0, k =0;
    while(j < a.length && k < b.length){
      if(a[j]>b[k]) 
        returnValue[i++] = a[j++];
      else 
        returnValue[i++] = b[k++];
    }
      
    while( j < a.length ){
      returnValue[i++] = a[j++];
    }  
   
    while( k < a.length ){
      returnValue[i++] = b[k++];
    }  
    
    return returnValue;
  }
  */
  
  /*
   * 
   * There is one array, it includes 2 parts that both are in nondecreasing order, here it's to make the 2 parts in nondecreasing order.
   * @param seqNum, the array 
   * @param s1, the start index of the first part 
   * @param e1, the end index of the first part
   * @param s2, the start index of the second part
   * @param e2, the end index of the second part
   *  
   * @If a and b both are null, return null ; If one is null, the other is not null, return the one that not null; 
   * Or merge them in nondecreasing order
   *  
   */
  
  public static void merge(int[] seqNum, int s1, int e1, int s2, int e2, long[] operateCount) {
    
    //System.out.println("------------start-merge-30------------operateCount:"+ operateCount[0]+ "--s1:"+s1+ "--e1:"+e1+ " --s2:"+s2+"--e2:" +e2+"--seqNum:"+ toString(seqNum));
    
    //check
    //the array should be valid
    if(seqNum ==null)
      return;
    
    //the sequence should be (s1, e1, s2, e2)  
    if(s1 > e1 || e1 >= s2 || s2 > e2 )
      return;
    
    //initial
    int i=0, j=s1, k=s2;
    //System.out.println("------------start-1-000------------i:"+i+ "--j:"+j+ " --k:"+k);

    
    //construct a temp array to store the merge result of [s1, e1], [s2, e2]
    int[] tmpArray = new int[(e1-s1+1)+(e2-s2+1)];
    //System.out.println("------------start-1-------------i:"+i+ "--j:"+j+ " --k:"+k+ "--tmpArray:"+ toString(tmpArray));
    operateCount[0] = operateCount[0] + tmpArray.length; 
    
    while(j <= e1 && k <= e2){
      if(seqNum[j]>seqNum[k]) 
        tmpArray[i++] = seqNum[j++];
      else 
        tmpArray[i++] = seqNum[k++];
    }
    
    //System.out.println("------------start-2-------------i:"+i+ "--j:"+j+ " --k:"+k+"--seqNum:"+ toString(seqNum)+ "--tmpArray:"+ toString(tmpArray));
    
    while( j <= e1 ){
      tmpArray[i++] = seqNum[j++];
    }  
    while( k <= e2 ){
      tmpArray[i++] = seqNum[k++];
    }  

    //System.out.println("------------start-3-------------s1:"+s1+ "--e1:"+e1+ " --s2:"+s2+"--e2:" +e2+"--seqNum:"+ toString(seqNum)+ "--tmpArray:"+ toString(tmpArray));

    //refill the merge result back 
    i=0;
    j=s1;
    k=s2;
    while( j <= e1 ){
      seqNum[j++] = tmpArray[i++] ;
    }  
    while( k <= e2 ){
      seqNum[k++] = tmpArray[i++] ;
    }  
    
    //System.out.println("------------end-merge-30------------s1:"+s1+ "--e1:"+e1+ " --s2:"+s2+"--e2:" +e2+"--seqNum:"+ toString(seqNum));
  }
  
  public void mergeSort(int[] seqNum){
    long timeCount1 = System.currentTimeMillis();
    long[] operateCount = new long[1]; 
    //System.out.println("------------start-mergeSort-10------------" );
    
    //check
    if (seqNum == null || seqNum.length < 2 )
      return;
    
    mergeSort(seqNum, 0, seqNum.length -1, operateCount);  
        
    long timeCount2 = System.currentTimeMillis();
    System.out.println("-----------mergeSort------Time:" + (timeCount2 - timeCount1) + " --Operate:- " + operateCount[0] );
    
  }
  
  private void mergeSort(int[] seqNum, int s, int e, long[] operateCount){
    //System.out.println("------------start-mergeSort-20------------s:" + s + " --e:" +e + "--seqNum:"+ toString(seqNum));
        
    //check
    if (seqNum == null )
      return;
    if ( s >= e )  //e - s <= 0
      return;
//    if(operateCount[0] > 3)
//      return;
    
    if((e - s) > 1){
      mergeSort(seqNum, s, (e+s)/2, operateCount);
      
      mergeSort(seqNum, (e+s)/2 + 1, e, operateCount);
      
      merge(seqNum, s, (e+s)/2, (e+s)/2+1, e, operateCount);          
    }
//      else if (seqNum[s] < seqNum[e]){
//      // e - s = 1 and ascent 
//      swap(seqNum, s, e, operateCount);
//    }
            
  }
  
//  private int parent(int i)
//  {
//      return (int)Math.floor(i / 2);
//  }
   
  private int left(int i)
  {
      return 2 * i;
  }
   
  private int right(int i)
  {
      return (2 * i + 1);
  }
   
  /*
   * i is the index in the array
   * 
   */
  private void maxHeapify(int array[], int i, int heap_size, long[] operateCount)
  {
      //System.out.println("-------------i-------------"+ i + " heap_size:"+ heap_size+ " maxHeapify:" + toString(array).toString());
    
      //the first point in Heap is array[0].  the second point in Heap is array[1]
      int l = left(i+1) - 1;
      int r = right(i+1) - 1;
      int largest = i;

      if(l < heap_size && array[l] > array[i])
      {
          largest = l;
      }
      if(r < heap_size && array[r] > array[largest])
      {
          largest = r;
      }
      if(largest != i)
      {
          swap(array, largest, i, operateCount);
          
          maxHeapify(array, largest, heap_size, operateCount);
      }
  }
  
  private void minHeapify(int array[], int i, int heap_size, long[] operateCount)
  {
      //System.out.println("-------------i-------------"+ i + " heap_size:"+ heap_size+ " maxHeapify:" + toString(array).toString());
    
      //the first point in Heap is array[0].  the second point in Heap is array[1]
      int l = left(i+1) - 1;
      int r = right(i+1) - 1;
      int smallest = i;

      if(l < heap_size && array[l] < array[i])
      {
          smallest = l;
      }
      if(r < heap_size && array[r] < array[smallest])
      {
          smallest = r;
      }
      if(smallest != i)
      {
          swap(array, smallest, i, operateCount);
          
          minHeapify(array, smallest, heap_size, operateCount);
      }
  }
   
  private void buildMaxHeap(int array[], long[] operateCount)
  {
      for(int i = array.length/2 -1 ; i >= 0; i--)
      {
        maxHeapify(array, i, array.length, operateCount);
      }
  }

  private void buildMinHeap(int array[], long[] operateCount)
  {
      for(int i = array.length/2 -1 ; i >= 0; i--)
      {
        minHeapify(array, i, array.length, operateCount);
      }
  }
  
  public void heapSort(int[] array)
  {
      long timeCount1 = System.currentTimeMillis();
      long[] operateCount = new long[1]; 
      //System.out.println("------------start-HeapSort-10------------" );
      
      //check
      if (array == null || array.length < 2)
        return;
        
      buildMinHeap(array, operateCount);    
      
      for(int i = array.length - 1; i > 0; i--)
      {         
          swap(array, 0, i, operateCount);

          minHeapify(array, 0, i, operateCount);
      }
     
      long timeCount2 = System.currentTimeMillis();
      System.out.println("-----------HeapSort------Time:" + (timeCount2 - timeCount1) + " --Operate:- " + operateCount[0] );
   
  }

  public void shellSort(int[] array)
  {
    long timeCount1 = System.currentTimeMillis();
    long[] operateCount = new long[1]; 
    //System.out.println("------------start-ShellSort-10------------" );
    
    //check
    if (array == null || array.length < 2)
      return;
    
    int i, j, k, temp, gap;
    int[] gaps = { 1,4,10,23,57,132,301,701,1750 };
    
    for (k=0; gaps[k]<array.length; k++);
    
    while (--k >= 0)
    {
      gap = gaps[k];
      for (i=gap; i<array.length; i++)
      {
          temp = array[i];
          j = i;
          while (j>=gap && array[j-gap]>temp)
          {
              array[j] = array[j-gap];
              j = j-gap;
          }
          array[j] = temp;
          
          System.out.println("------------ShellSort-10------------i:"+ i +" array:" + toString(array).toString());
      }
      
      System.out.println("------------ShellSort-10------------k:"+ k + " gap:" + gap);
    }       
    

    long timeCount2 = System.currentTimeMillis();
    System.out.println("-----------ShellSort------Time:" + (timeCount2 - timeCount1) + " --Operate:- " + operateCount[0] );

  }

  
  public void countingSort(int array[], long[] operateCount)
  {
    int i, min, max;
   
    min = max = array[0];
    //get the max and min in the array
    for(i = 1; i < array.length; i++) {
      if (array[i] < min)
        min = array[i];
      else if (array[i] > max)
        max = array[i];
    }
   
    int range = max - min + 1;
    int[] count = new int[range];
//    for(i = 0; i < range; i++)
//      count[i] = 0;
   
    //counting, how many array[i]
    for(i = 0; i < array.length; i++)
      count[ array[i] - min ]++;
   
    int j, z = 0;
    for(i = min; i <= max; i++)
      for(j = 0; j < count[ i - min ]; j++)
        array[z++] = i;
   
  }

  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    
    System.out.println("---start---");
        
    //initial
    int times = 1;
    int size = 270;
    int min = 0;
    int max = 10000;
    
    int[] seqNum = new int[size];
    Sorting sort = new Sorting();
    for(int i=0; i< times; i++){
//      for(int j=0; j< size; j++){
//        seqNum[j] = min + (int)(Math.random() * ((max - min) + 1)) ; 
//      }      
      seqNum = new int[] {9,90,51,89,51,26,43};
      
      System.out.println("-------------i-------------"+ i + "       before sort:" + toString(seqNum).toString());
      
      
//      int[] seqNum0 = seqNum.clone();
//      sort.bubbleSort(seqNum0);
//      System.out.println("-------------i-------------"+ i + " after bubble sort:" + toString(seqNum0).toString());      
      
//      int[] seqNum1 = seqNum.clone();
//      sort.insertSort(seqNum1);
//      System.out.println("-------------i-------------"+ i + " after insert sort:" + toString(seqNum1).toString());
//      System.out.println(" " +  compareArray(seqNum0, seqNum1));
//      
//      int[] seqNum2 = seqNum.clone(); //cloneArray(seqNum);
//      sort.mergeSort(seqNum2);
//      System.out.println("-------------i-------------"+ i + " after merge  sort:" + toString(seqNum2).toString());
//      System.out.println(" " +  compareArray(seqNum0, seqNum2));
      
      int[] seqNum3 = seqNum.clone();
      sort.quickSort(seqNum3);
      System.out.println("-------------i-------------"+ i + " after quick sort:" + toString(seqNum3).toString());
      //System.out.println(" " +  compareArray(seqNum0, seqNum3));      
      
//      int[] seqNum4 = seqNum.clone();
//      sort.heapSort(seqNum4);
//      System.out.println("-------------i-------------"+ i + " after heap sort:" + toString(seqNum4).toString());
//      System.out.println(" " +  compareArray(seqNum0, seqNum4));   
      
//      int[] seqNum5 = seqNum.clone();
//      sort.shellSort(seqNum5);
//      System.out.println("-------------i-------------"+ i + " after shell sort:" + toString(seqNum5).toString());
      //System.out.println(" " +  compareArray(seqNum0, seqNum5));   
      

    }
    
    try{

      
    }catch (Exception e){
      e.printStackTrace();
    }

    System.out.println("---end---");
  }
  
  
}
