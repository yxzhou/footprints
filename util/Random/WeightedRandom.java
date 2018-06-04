package fgafa.util.Random;

public class WeightedRandom
{

  /*
   * Weighted random sampling with a reservoir
   * 
   * given a big table (query - occurrence), and we need to write an efficient function to randomly select 1 query where probability is based on the occurrence.
   * 
   * Example:
   *  input (q1 - 20)(q2 - 10 )( q3 - 30)
   *  the probability of q1 is 20/(20+10+30) = 20/60 
   * 
   */
  
  static int res;    // The resultant random number
  static int sum = 0;  //Count of numbers visited so far in stream
  
  public static int selectRandom(int query, int weight){   
      int precount = sum;
      sum+= weight;  // increment count of numbers seen so far
      
      // If this is the first element from stream, return it
      if (sum == 1)
          res = query;
      else
      {
          // Generate a random number from 0 to count - 1
          java.util.Random random = new java.util.Random();
          int i = random.nextInt(sum);
   
          // Replace the prev random number with new number with weight/(count+weight) probability
          if (i > precount)
              res  = query;
      }
      
      return res;
  }
   
  // Driver program to test above function.
  public static void main(String[] args) 
  {
      int[] query = {1, 3, 5};
      int[] occurrence = {20, 10, 30};
          
      int n = query.length;

      for (int i = 0; i < n; ++i)
          System.out.printf("Random number from first %d numbers is %d \n",
                                  i+1, selectRandom(query[i], occurrence[i]));
      
  }

  class Query{
    int id;
    int occurrence;
  }
  
}
