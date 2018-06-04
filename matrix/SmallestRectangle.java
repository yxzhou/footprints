package fgafa.matrix;


/**
 * 
 * An image is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel. 
 * The black pixels are connected, i.e., there is only one black region. Pixels are connected horizontally and vertically. 
 * Given the location (x, y) of one of the black pixels, return the area of the smallest (axis-aligned) rectangle that encloses all black pixels.
  
    For example, given the following image:
    [
      "0010",
      "0110",
      "0100"
    ]
    and x = 0, y = 2,
    Return 6.
 *
 */

public class SmallestRectangle {
    public int minArea(boolean[][] image, int x, int y) {
        if (null == image || 0 == image.length || 0 == image[0].length) {
            return 0;
        }
        
        int[] extremums = new int[4]; // 0-min x, 1-max x, 2- min y, 3- max y
        extremums[0] = Integer.MAX_VALUE;
        //extremums[1] = Integer.MIN_VALUE;
        extremums[2] = Integer.MAX_VALUE;
        //extremums[3] = Integer.MIN_VALUE;
        
        dfs(image, x, y, extremums);
        
        return (extremums[1] - extremums[0] + 1) * (extremums[3] - extremums[2] + 1);
    }
    
    private void dfs(boolean[][] image, int x, int y, int[] extremums){
        if(x < 0 || x >= image.length || y < 0 || y >= image[0].length){
            return;
        }
        
        if(image[x][y]){
            extremums[0] = Math.min(extremums[0], x);
            extremums[1] = Math.max(extremums[1], x);
            extremums[2] = Math.min(extremums[2], y);
            extremums[3] = Math.max(extremums[3], y);
            
            image[x][y] = false;
            
            dfs(image, x+1, y, extremums);
            dfs(image, x-1, y, extremums);
            dfs(image, x, y+1, extremums);
            dfs(image, x, y-1, extremums);
            
            image[x][y] = true;
        }
    }
    
    public static void main(String[] args){
        SmallestRectangle sv = new SmallestRectangle();
        
        boolean[][] image = {
                    {false, false, true, false},
                    {false, true,  true, false},
                    {false, true, false, false}
        };
        
        System.out.println(sv.minArea(image, 0, 2));
    }
}
