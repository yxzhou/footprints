package fgafa.game.nimGames;



public class CoinInLine
{
    
    /**
     * There are n coins in a line. (Assume n is even). 
     * Two players take turns to take a coin from one of the ends of the line until there are no more coins left. 
     * The player with the larger amount of money wins.
     * 
     * Would you rather go first or second? Does it matter?
     * Assume that you go first, describe an algorithm to compute the maximum amount of money you can win.
     */    
    /*  Time O(n*n), Space O(n*n)*/
    public boolean firstWillWinII(int[] values) {
 
        //check
        if(null == values || 0 == values.length){
            return false; // or throw exception
        }
        
        int size = values.length;
        
        if(size < 3){
            return true;
        }
        
        long[][] totalSum = new long[size][size]; // totalSum[i][j] is the sum of from i to i+j
        long[][] max = new long[size][size]; // max[i][j] is the max take from i to i+j if you are the first player
        
        for(int i = 0; i < size; i++){
            totalSum[i][0] = values[i];
            max[i][0] = values[i];
        }
        
        for(int width = 1; width < size; width++){
            for(int index = size - 1 - width; index >= 0; index--){
                totalSum[index][width] = totalSum[index + 1][width - 1] + totalSum[index][0];
                max[index][width] = totalSum[index][width] - Math.min( max[index + 1][width], max[index][width - 1] );
            }
        }
        
        return max[0][size - 1] * 2 > totalSum[0][size - 1];
    }

    /*  Time O(n*n), Space O(n)*/
    public boolean firstWillWinII_n(int[] values) {
 
        //check
        if(null == values || 0 == values.length){
            return false; // or throw exception
        }
        
        int size = values.length;
        
        if(size < 3){
            return true;
        }
        
        long[] totalSum = new long[size]; // totalSum[i][j] is the sum of from i to i+j
        long[] max = new long[size]; // max[i][j] is the max take from i to i+j if you are the first player
        
        for(int i = 0; i < size; i++){
            totalSum[i] = values[i];
            max[i] = values[i];
        }
        
        for(int width = 1; width < size; width++){
            for(int index = size - 1 - width; index >= 0; index--){
                totalSum[index] = totalSum[index + 1] + values[index];
                max[index] = totalSum[index] - Math.min( max[index + 1], max[index] );
            }
        }
        
        return max[0] * 2 > totalSum[0];
    }
    

    /**
     * There are n coins with different value in a line. Two players take turns
     *  to take one or two coins from left side until there are no more coins left.
     *   The player who take the coins with the most value wins.
    
        Could you please decide the first player will win or lose?
        
        Example
        Given values array A = [1,2,2], return true.
        Given A = [1,2,4], return false.

     * Solution
     *  define n as the size of coins;
     *  define TotalSum(i, n - 1) as the total sum from i to n - 1,  Max(i, n - 1) as the coins with the most value when the first player face the coins from i to n - 1. 
     *  so TotalSum(n - 1, n - 1) = values[n - 1], TotalSum(n - 2, n - 1) = TotalSum(n - 1, n - 1) + values[n - 2], ---
     *  max[n - 1, n - 1] = TotalSum(n - 1, n - 1), max[n - 2, n] = TotalSum(n - 2, n - 1),
     *  when i < n - 1,  max[i, n] = max( values[i] + TotalSum[i + 1, n] - max[i + 1, n], values[i] + values[i + 1] + TotalSum[i + 2, n] - max[i + 2, n])
     *  ( if the first player take one coin, it's values[i], the second player is SMART, it will get max[i + 1, n],  so the first player get values[i] + TotalSum[i + 1, n] - max[i + 1, n] ;
     *    if the first player take two coins, it's values[i] + values[i + 1], ---
     *  )
     *  
     *  Time O(n), Space O(n)*/
    public boolean firstWillWin(int[] values) {
        //check
        if(null == values || 0 == values.length){
            return false;
        }
        
        int size = values.length;
        
        if(size < 3){
            return true;
        }
        
        long[] totalSum = new long[size + 1]; // totolSum[i] = sum (v[i] + --- + v[n])
        long[] max = new long[size + 1];
        
        for(int i = size - 1; i > size - 3; i--){
            totalSum[i] = totalSum[i + 1] + values[i];
            max[i] = totalSum[i];
        }
        
        for(int i = size - 3; i >= 0; i--){
            totalSum[i] = totalSum[i + 1] + values[i];
            max[i] = Math.max(values[i] + totalSum[i + 1] - max[i + 1], values[i] + values[i + 1] + totalSum[i + 2] - max[i + 2]);
        }
        
        return max[0] * 2 > totalSum[0];
    }
    
    /*  Time O(n), Space O(1)*/
    public boolean firstWillWin_n(int[] values) {
        //check
        if(null == values || 0 == values.length){
            return false;
        }
        
        int size = values.length;
        
        if(size < 3){
            return true;
        }
        
        long[] totalSum = new long[4]; 
        long[] max = new long[4];
        
        for(int i = 1; i < 3 ; i++){
            totalSum[i] = totalSum[i - 1] + values[size - i];
            max[i] = totalSum[i];
        }
        
        for(int i = size - 3; i >= 0; i--){
            totalSum[3] = totalSum[2] + values[i];
            max[3] = Math.max(values[i] + totalSum[2] - max[2], values[i] + values[i + 1] + totalSum[1] - max[1]);
            
            for(int j = 1; j < 3; j++){
                totalSum[j] = totalSum[j + 1];
                max[j] = max[j + 1];         
            }
        }
        
        return max[2] * 2 > totalSum[2];
    }
    
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
