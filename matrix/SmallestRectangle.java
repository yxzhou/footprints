package matrix;

import java.util.LinkedList;
import java.util.Queue;
import junit.framework.Assert;
import util.Misc;


/**
 * _https://www.lintcode.com/problem/600
 * 
 * An image is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel. The black pixels are
 * connected, i.e., there is only one black region. Pixels are connected horizontally and vertically. Given the location
 * (x, y) of one of the black pixels, return the area of the smallest (axis-aligned) rectangle that encloses all black
 * pixels.
 *  
 * Example 1, given the following image:
    [
      "0010",
      "0110",
      "0100"
    ]
    and x = 0, y = 2,
    Return 6.
 *
 * Example 2, Input：
 *  [
 *    "1110",
 *    "1100",
 *    "0000",
 *    "0000"
 *  ], x = 0, y = 1
 *  Output：6
 * 
 * 
 * Thoughts: 
 *  define n as row number of the matrix, m as the column number of the matrix, k as the number of the black pixels 
 * 
 *                             Time Complexity            
 *  1) DFS                       O(k)                   maybe stack over flow
 *  2) BFS                       O(k)   
 *  3) Binary Search             O(m*logn + n*logm)     works only when there is only one black region
 * 
 *  If there are not many black pixels, example k < m * logn,  BFD is better.
 *  If there are lots of black pixels, the worst case is k = n * m, BFS is O(4nm), The full scan is O(nm), 
 *     Binary Search is best
 * 
 */

public class SmallestRectangle {
    
    /**
     * 
     * @param image: a binary matrix with '0' and '1'
     * @param x: the location of one of the black pixels
     * @param y: the location of one of the black pixels
     * @return: an integer
     */

    int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int minArea_BFS(char[][] image, int x, int y) {
        if(image == null || x < 0 || x >= image.length || y < 0 || y >= image[0].length || image[x][y] == '0' ){
            return 0;
        }

        int n = image.length;
        int m = image[0].length;

        int[] result = new int[]{Integer.MAX_VALUE, -1, Integer.MAX_VALUE, -1}; // {minX, maxX, minY, maxY}

        //bfs
        Queue<Integer> queue = new LinkedList<>(); // store the x*m + y
        queue.add(x*m + y);

        int top;
        int nx;
        int ny;
        while(!queue.isEmpty()){
            top = queue.poll();
            x = top / m;
            y = top % m;

            image[x][y] = '2';

            result[0] = Math.min(result[0], x);
            result[1] = Math.max(result[1], x);
            result[2] = Math.min(result[2], y);
            result[3] = Math.max(result[3], y);

            for(int[] diff : diffs){
                nx = x + diff[0];
                ny = y + diff[1];

                if(nx >= 0  && nx < n && ny >= 0 && ny < m && image[nx][ny] == '1' ){
                    queue.add(nx * m + ny);
                }
            }
        }

        return (result[1] - result[0] + 1) * (result[3] - result[2] + 1);
    }
    
    public int minArea_DFS(char[][] image, int x, int y) {
        if (null == image || 0 == image.length || 0 == image[0].length) {
            return 0;
        }
        
        int[] extremums = {Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0}; // {minx, maxx, miny, maxy}
        
        dfs(image, x, y, extremums);
        
        return (extremums[1] - extremums[0] + 1) * (extremums[3] - extremums[2] + 1);
    }
    
    private void dfs(char[][] image, int x, int y, int[] extremums){
        if(x < 0 || x >= image.length || y < 0 || y >= image[0].length || image[x][y] == '0' ){
            return;
        }
        
        image[x][y] = '0';
                
        extremums[0] = Math.min(extremums[0], x);
        extremums[1] = Math.max(extremums[1], x);
        extremums[2] = Math.min(extremums[2], y);
        extremums[3] = Math.max(extremums[3], y);


        dfs(image, x+1, y, extremums);
        dfs(image, x-1, y, extremums);
        dfs(image, x, y+1, extremums);
        dfs(image, x, y-1, extremums);
            
    }
    
    /**
     * 
     * @param image: a binary matrix with '0' and '1'
     * @param x: the location of one of the black pixels
     * @param y: the location of one of the black pixels
     * @return: an integer
     */

    public int minArea_BinarySearch(char[][] image, int x, int y) {
        if(image == null || x < 0 || x >= image.length || y < 0 || y >= image[0].length || image[x][y] == '0' ){
            return 0;
        }
    
        int[] result = new int[4]; // {minX, maxX, minY, maxY}
        
        //binary search the left of (x, y), get the minY
        int low = 0;
        int high = y;
        int mid;
        while(low < high){
            mid = low + (high - low) / 2;
            
            if(foundInColumn(image, mid)){
                high = mid;
            }else{
                low = mid + 1;
            }
        }
        result[2] = low;
        
        //binary search the right of (x, y), get the maxY
        low = y;
        high = image[0].length;
        while(low < high){
            mid = low + (high - low) / 2;
            
            if(foundInColumn(image, mid)){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }
        result[3] = foundInColumn(image, low)? low : low - 1 ;
        
        //binary search the up of (x, y), get the minX
        low = 0;
        high = x;
        while(low < high){
            mid = low + (high - low) / 2;
            
            if(foundInRow(image, mid)){
                high = mid;
            }else{
                low = mid + 1;
            }
        }
        result[0] = low;
        
        //binary search the down of (x, y), get the maxX
        low = x;
        high = image.length;
        while(low < high){
            mid = low + (high - low) / 2;
            
            if(foundInRow(image, mid)){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }
        result[1] = foundInRow(image, low)? low : low - 1 ;
        
        return (result[1] - result[0] + 1) * (result[3] - result[2] + 1);
    }
    
    /**
     * 
     * @param image, a binary matrix with '0' and '1'
     * @param y, the column id
     * @return true if found black pixels, or false
     */
    private boolean foundInColumn(char[][] image, int y){
        if(y < 0 || y >= image[0].length){
            return false;
        }
        
        for(int x = 0; x < image.length; x++){
            if(image[x][y] == '1'){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param image, a binary matrix with '0' and '1'
     * @param x, the row id
     * @return true if found black pixels, or false
     */
    private boolean foundInRow(char[][] image, int x){
        if(x < 0 || x >= image.length){
            return false;
        }
        
        for(int y = 0; y < image[0].length; y++){
            if(image[x][y] == '1'){
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args){
        String[][] matrixs = {
            {
                "0010",
                "0110",
                "0100"
            },
            {
                "1110",
                "1100",
                "0000",
                "0000"
            },
            {
                "111110000000",
                "001111100000",
                "000111000000",
                "000000000000",
                "000000000000",
                "000000000000",
                "000000000000",
                "000000000000",
                "000000000000"  
            },
            {
                "000000000000",
                "000000000000",
                "000000000000",
                "000000000000",
                "000000000000",
                "000000000000",
                "000000000001",
                "000000000001",
                "000000000001" 
            }
        };
        
        int[][] xyr = {
            {0, 2, 6},
            {0, 1, 6},
            {0, 1, 21},
            {6, 11,3}
        };
        
        SmallestRectangle sv = new SmallestRectangle();
        
        for(int i = 0; i < matrixs.length; i++){
            char[][] matrix = convert(matrixs[i]);
                    
            Misc.printMetrix(matrix);
            
            Assert.assertEquals(xyr[i][2], sv.minArea_DFS(matrix, xyr[i][0], xyr[i][1]));
            
            matrix = convert(matrixs[i]);
            Assert.assertEquals(xyr[i][2], sv.minArea_BFS(matrix, xyr[i][0], xyr[i][1]));
            
            matrix = convert(matrixs[i]);
            Assert.assertEquals(xyr[i][2], sv.minArea_BinarySearch(matrix, xyr[i][0], xyr[i][1]));
        }
        
    }
    
    private static char[][] convert(String[] matrix){
        char[][] r = new char[matrix.length][];
        
        for(int i = 0; i < matrix.length; i++){
            r[i] = matrix[i].toCharArray();
        }
        
        return r;
    }
}
