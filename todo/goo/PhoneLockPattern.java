package todo.goo;

/**
 * Google Onsite
 * 
 * A smart phone lock screen is a grid[3][3] dots. It's a valid pattern when 
 *  1 each pattern must have at least 4 dots, max 9 dots
 *  2 the dots in a pattern must all be distinct.
 *  3 If the line segment connecting any two consecutive dots in the pattern passes through any other dots, 
 *  the other dots must have previously been in the pattern
 * 
 * Question, how many distinct patterns?  389112. 
 * 
 * 
 */

public class PhoneLockPattern {

     /*   The grid is:
      *   1  2  3
      *   4  5  6
      *   7  8  9
      *                 
      *   Found #1, 
      *     the dots 1 and 3 and 9 and 7 are similar, the pattern that from them are same. 
      *     the dots 2 and 6 and 8 and 4 are same.
      *   
      *   Found #2, 
      *     from 7, it can go to 2, 5, 3 if 5 has been visited, 6, 8 and 9 if 8 has been visited 
      *     from 1, it can go to 6, 5, 9 if 5 has been visited, 8, 4 and 7 if 4 has been visited
      *     from 3, it can go to 8, 5, 7 if 5 has been visited, 4, 2 and 1 if 2 has been visited
      *     from 9, it can go to 4, 5, 1 if 5 has been visited, 2, 6 and 3 if 6 has been visited
      *   it's a 360 degree scan. 
      * 
      */
    public int distinctPatterns(){
        int total = 0;
        
        total += dfs(0, 0, new boolean[3][3], 0) * 4;
        
        total += dfs(0, 1, new boolean[3][3], 0) * 4;
        
        total += dfs(1, 1, new boolean[3][3], 0);

//        for(int x = 0; x < 3; x++){
//            for(int y = 0; y < 3; y++){
//                total += dfs(x, y, new boolean[3][3], 0);
//            }
//        }
        
        return total;
    }
    
    private int dfs(int x, int y, boolean[][] isVisited, int count){
        if(x < 0 || x > 2 || y < 0 || y > 2 || isVisited[x][y]){
            return 0;
        }
        
        if(count == 9){
            return 1;
        }
        
        int total = 0;
        isVisited[x][y] = true;
                    
        if(count > 3){
            total += 1;
        }
        
        count++;
        //clockwise, from 0 to 3, (0, 3]
        total += dfs(x - 2, y + 1, isVisited, count);
        total += dfs(x - 1, y + 1, isVisited, count);
        
        if(x == 2 && y == 0 && isVisited[x-1][y+1]){
            total += dfs(x - 2, y + 2, isVisited, count);
        }
        
        total += dfs(x - 1, y + 2, isVisited, count);
        total += dfs(x, y + 1, isVisited, count);
        
        if(y == 0 && isVisited[x][y+1]){
            total += dfs(x, y + 2, isVisited, count);
        }
        
        //clockwise, from 3 to 6, (3, 6]
        total += dfs(x + 1, y + 2, isVisited, count);
        total += dfs(x + 1, y + 1, isVisited, count);
        
        if(x == 0 && y == 0 && isVisited[x+1][y+1]){
            total += dfs(x + 2, y + 2, isVisited, count);
        }
        
        total += dfs(x + 2, y + 1, isVisited, count);
        total += dfs(x + 1, y, isVisited, count);
        
        if(x == 0 && isVisited[x+1][y]){
            total += dfs(x + 2, y, isVisited, count);
        }
        
        //clockwise, from 6 to 9, (6, 9]
        total += dfs(x + 2, y - 1, isVisited, count);
        total += dfs(x + 1, y - 1, isVisited, count);
        
        if(x == 0 && y == 2 && isVisited[x+1][y-1]){
            total += dfs(x + 2, y - 2, isVisited, count);
        }
        total += dfs(x + 1, y - 2, isVisited, count);
        total += dfs(x, y - 1, isVisited, count);
        
        if(y == 2 && isVisited[x][y-1]){
            total += dfs(x, y - 2, isVisited, count);
        }
        
        //clockwise, from 9 to 12, (9, 12]
        total += dfs(x - 1, y - 2, isVisited, count);
        total += dfs(x - 1, y - 1, isVisited, count);
        
        if(x == 2 && y == 2 && isVisited[x-1][y-1]){
            total += dfs(x - 2, y - 2, isVisited, count);
        }
        
        total += dfs(x - 2, y - 1, isVisited, count);
        total += dfs(x - 1, y, isVisited, count);
        
        if(x == 2 && isVisited[x-1][y]){
            total += dfs(x - 2, y, isVisited, count);
        }
        
        isVisited[x][y] = false;
        
        return total;
    }
    
    public static void main(String[] args) {
       int n = 1;
       for(int i = 1; i < 10; i++){
           n *= i;
           System.out.println(String.format("%d, %d", i, n));

       }
       
       n = 9 * 8* 7 * 6;
       int sum = 0;
       for( int k = 5; k >= 1; k-- ){
           sum += n;
           
           n *= k;
       }
       
       System.out.println(String.format("Sum, %d", sum));

       PhoneLockPattern sv = new PhoneLockPattern();
       System.out.println(String.format("PhoneLockPattern, %d", sv.distinctPatterns()));
    }

}
