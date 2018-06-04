package fgafa.binarysearch;

/**
 * 
 * Given an array A of integer with size of n( means n books and number of pages of each book) and k people to copy the book. 
 * You must distribute the continuous id books to one people to copy. 
 * (You can give book A[1],A[2] to one people, but you cannot give book A[1], A[3] to one people, because book A[1] and A[3] is not continuous.) 
 * Each person have can copy one page per minute. Return the number of smallest minutes need to copy all the books.

    Example
    Given array A = [3,2,4], k = 2.
    
    Return 5( First person spends 5 minutes to copy book 1 and book 2 and second person spends 4 minutes to copy book 3. )
    
    Challenge
    Could you do this in O(n*k) time ?
 *
 */

public class CopyBooks {

    /**
     * @param pages: an array of integers
     * @param k: an integer
     * @return: an integer
     * 
     * 
     * ?? pages is an array of positive integers ? 
     */
    /* Time (n * n * k)  Space (n * k) */
    public int copyBooks_dp1(int[] pages, int k) {
        assert(null != pages && k > 0 && pages.length > 0);
        
        int n = pages.length;
        
        if(n < k){  // **
            return copyBooks_dp1(pages, n);
        }
        
        // define result[i][x] as the number of smallest minutes from 0 to i for x people copying. 
        int[][] result = new int[n][k];
        result[0][0] = pages[0];
        for(int i = 1; i < n; i++){
            result[i][0] = result[i - 1][0] +  pages[i];
        }
        
        int diff = 0;
        for(int p = 1; p < k; p++){ // p + 1 peoples 
            for(int i = p; i < n; i++){    
                result[i][p] = Math.max(result[i - 1][p - 1], pages[i]);
                for(int j = i - 2; j > p - 2 ; j--){
                    diff = result[i][0] - result[j][0];
                    if(diff >= result[i][p]){
                        break;
                    }
                    result[i][p] = Math.min(result[i][p], Math.max(result[j][p - 1], diff));
                }
            }
        }
        
        return result[n - 1][k - 1];
    }
    
    
    /* Time O( log( sum(pages) - max(pages) ) * n )  Space O(1) */
    public int copyBooks_n(int[] pages, int k) {
        assert(null != pages && k > 0 && pages.length >= k);
        
       long low = -1;
       long high = 0;
       for(int num : pages){
           low = Math.max(low, num);
           high += num;
       }
       
       long mid;
       while(low < high){
           mid = low + ((high - low) >> 1);
           
           if(helper(pages, mid) <= k){  //**
               high = mid;
           }else{
               low = mid + 1;
           }
       }
       
       return (int)low;
   }
   
   private int helper(int[] pages, long limit){
       int count = 0;
       int sum = 0;
       int tmp = 0;
       for(int i = 0; i < pages.length; i++){
           tmp = sum + pages[i];
           if( tmp <= limit){
               sum = tmp;
           }else{
               count++;
               sum = 0;
               i--;
           }
       }
       
       return tmp <= limit ? count + 1 : count;
   }
}
