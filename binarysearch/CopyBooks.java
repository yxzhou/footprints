package binarysearch;

/**
 * _https://www.lintcode.com/problem/437
 * 
 * Given an array A of integer with size of n( means n books and number of pages of each book) and k people to copy the
 * book. You must distribute the continuous id books to one people to copy. (You can give book A[1],A[2] to one people,
 * but you cannot give book A[1], A[3] to one people, because book A[1] and A[3] is not continuous.) Each person have
 * can copy one page per minute. Return the number of smallest minutes need to copy all the books.
 * 
 * Notes: The sum of book pages is less than or equal to 2147483647
 * 
 * Example 1
 * Input pages = [3,2,4], k = 2.
 * Return 5
 * Explanation: First person spends 5 minutes to copy book 1 and book 2, second person spends 4 minutes to copy book 3. 
 * 
 * Example 2
 * Input: pages = [3, 2, 4], k = 3
 * Output: 4
 * Explanation: Each person copies one of the books.
 * 
 * Challenge
 *   Could you do this in O(n*k) time ?
 *
 */

public class CopyBooks {

    /**
     * Time (n * n * k)  Space (n * k)
     * 
     * @param pages: an array of integers
     * @param k: an integer
     * @return an integer
     */
    public int copyBooks_DP(int[] pages, int k) {
        //assert(null != pages && k > 0 && pages.length > 0);
        if(pages == null || pages.length == 0){
            return 0;
        }
        
        int n = pages.length;
        k = Math.min(k, n); //**
        
        int[] sums = new int[n + 1]; //prefix sum 
        for(int i = 0; i < n; i++){
            sums[i + 1] = sums[i] + pages[i];
        }
        
        int[][] f = new int[k + 1][n + 1]; //define f[x][i] as the result for x people copying i books.
        f[0][0] = 0;
        
        int diff;
        for(int p = 1; p <= k; p++){ // p peoples 
            for(int i = 1; i <= n; i++){ // i books
                
                if(p == 1){ // 1 person copy i books, it takes sums[i] minutes
                    f[p][i] = sums[i]; 
                }else{
                    f[p][i] = Integer.MAX_VALUE;
                    
                    for(int j = i - 1; j >= 0; j-- ){ // the last person copy books (j, i]
                        diff = sums[i] - sums[j];
                        if(diff > f[p][i]){
                            break;
                        }
                        
                        f[p][i] = Math.min(f[p][i], Math.max(f[p - 1][j], diff));
                    }
                }
            }
        }
        
        return f[k][n];
    }
    
    public int copyBooks_DP1(int[] pages, int k) {
        //assert(null != pages && k > 0 && pages.length > 0);
        if(pages == null || pages.length == 0){
            return 0;
        }
        
        int n = pages.length;
        k = Math.min(k, n); //**
        
        int[][] result = new int[n][k]; //define f[i][x] as the result for x people copying i books.
        result[0][0] = pages[0]; 
        for(int i = 1; i < n; i++){
            result[i][0] = result[i - 1][0] +  pages[i];
        }
        
        int diff;
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
    
    /**
     * define n as the number of books, m as the number of total pages
     * 
     * Time O( n * log( sum(pages) - max(pages) ) ) = O(n * logm)
     * Space O(1) 
     *
     * @param pages: an array of integers
     * @param k: An integer
     * @return 
     */
    public int copyBooks_BinarySearch(int[] pages, int k) {
        if(pages == null ){
            return 0;
        }

        int max = 0;
        int sum = 0;
        for(int page : pages){
            max = Math.max(max, page);
            sum += page;
        }

        if( k == 1){
            return sum;
        }else if( pages.length <= k){
            return max;
        }

        int low = max;
        int high = sum;
        int mid;
        int x;
        while(low < high){
            mid = low + (high - low) / 2;

            x = assign(pages, mid);
            if(x <= k){
                high = mid;
            }else{
                low = mid + 1;
            }
        }

        return low;
    }

    /**
     * 
     * @param pages
     * @param limit, the max capacity to read the pages
     * @return how many people it need to finish pages when each person has a limitation 
     */
    private int assign(int[] pages, int limit){
        int k = 1;
        int m = limit;
        for(int page : pages){
            if(page <= m){
                m -= page;
            }else{
                k++;
                m = limit - page;
            }
        }

        return k;
    }
}
