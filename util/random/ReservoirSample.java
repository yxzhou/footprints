package fgafa.util.random;



public class ReservoirSample
{

  /*
   * randomly choosing k samples from a list of n items, where n is either a very large or unknown number. 
   * Typically n is large enough that the list doesn't fit into main memory. 
   * 
   * We are given a big array (or stream) of numbers (to simplify), and we need to write an efficient function to randomly select k numbers where 1 <= k <= n.
   * 
   * A simple solution is to create an array reservoir[] of maximum size k. One by one randomly select an item from stream[0..n-1]. 
   * If the selected item is not previously selected, then put it in reservoir[]. 
   * To check if an item is previously selected or not, we need to search the item in reservoir[]. The time complexity of this algorithm will be O(k^2). 
   * This can be costly if k is big. Also, this is not efficient if the input is in the form of a stream.
   * It can be solved in O(n) time. The solution also suits well for input in the form of stream. 
   * 
   * Following are the steps.
   *   1) Create an array reservoir[0..k-1] and copy first k items of stream[] to it.
   *   2) Now one by one consider all items from (k+1)th item to nth item.
   *     a) Generate a random number from 0 to i where i is index of current item in stream[]. Let the generated random number is j.
   *     b) If j is in range 0 to k-1, replace reservoir[j] with arr[i]
   * 
   */
//A function to randomly select k items from stream[0..n-1].
  public final static int K = 5;
  public static int[] RESERVOIR = new int[K];



  public static void selectKItems(int stream[], int n, int k) {
    int i; // index for elements in stream[]

    // reservoir[] is the output array. Initialize it with
    // first k elements from stream[]
    for (i = 0; i < k; i++)
      RESERVOIR[i] = stream[i];


    // Iterate from the (k+1)th element to nth element
    for (; i < n; i++) {
      // Pick a random index from 0 to i.
      java.util.Random random = new java.util.Random();
      int j = random.nextInt(i + 1);

      // If the randomly picked index is smaller than k, then replace
      // the element present at the index with new element from stream
      if (j < k)
        RESERVOIR[j] = stream[i];
    }

    System.out.println("Following are k randomly selected items");
    printArray(RESERVOIR, k);
  }



  // Driver program to test above function.
  public static void main(String[] args) {
    int stream[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    int n = stream.length;


    selectKItems(stream, n, K);

  }



  // A utility function to print an array
  private static void printArray(int stream[], int n) {
    for (int i = 0; i < n; i++)
      System.out.printf("%d ", stream[i]);
    System.out.printf("%n");
  }



}
