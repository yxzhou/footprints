package fgafa.matrix;

//LongestIncreasingSequenceInMetrix
public class LISInMetrix {

    
    public int longest_inc(int[][] matric) {
        if(null == matric || 0 == matric.length || 0 == matric[0].length){
            return 0;
        }
        
        int m = matric.length;
        int n = matric[0].length;
        
        int[][] mem = new int[m][n];//default all are 0,  store the LIS that starts and bigger 

        int max_path = 0;
        for (int i = 0; i < m; i++)
            for(int j = 0; j < n; j++) {
                int path = dfs_1(matric, mem, i,j);
                
                max_path = Math.max(max_path, path);
            }
        
        return max_path;
    }
    
    private int dfs_1(int[][] mat, int[][] mem, int i, int j) {
        if(mem[i][j] != 0){
            return mem[i][j];
        }
        
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        int newi;
        int newj;
        int m = mem.length;
        int n = mem[0].length;
        for(int[] d : dirs) {
            newi = i + d[0];
            newj = j + d[1];
            if(newi < 0 || newj < 0 || newi >= m || newj >= n){
                continue;
            }
            
            if(mat[newi][newj] == mat[i][j] + 1){
                mem[i][j] = Math.max(mem[i][j], dfs_1(mat, mem, newi, newj));
            }
                
        }
        
        return ++mem[i][j];
    };
    
    
    
    public int longestIncreasingPath(int[][] matrix) {
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            return 0;
        }   
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        int max = 0;
        int[][] pathLenghs = new int[m][n]; //default all are 0
        
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                 max = Math.max(max, dfs(matrix, pathLenghs, i, j));
            }
        }
        
        return max;
     }
     
     private int dfs(int[][] matrix, int[][] pathLenghs, int i, int j){
         
         if(pathLenghs[i][j] > 0){
             return pathLenghs[i][j];
         }
         
         int m = matrix.length;
         int n = matrix[0].length;
         
         
         pathLenghs[i][j] = 1;
         //upper
         if(i > 0 && matrix[i - 1][j] < matrix[i][j]){
             pathLenghs[i][j] = Math.max(pathLenghs[i][j], dfs(matrix, pathLenghs, i - 1, j) + 1);
         }
         //down
         if(i < m - 1 && matrix[i + 1][j] < matrix[i][j]){
             pathLenghs[i][j] = Math.max(pathLenghs[i][j], dfs(matrix, pathLenghs, i + 1, j) + 1);
         }
         //left
         if(j > 0 && matrix[i][j - 1] < matrix[i][j]){
             pathLenghs[i][j] = Math.max(pathLenghs[i][j], dfs(matrix, pathLenghs, i, j - 1) + 1);
         }
         //right
         if(j < n - 1 && matrix[i][j + 1] < matrix[i][j]){
             pathLenghs[i][j] = Math.max(pathLenghs[i][j], dfs(matrix, pathLenghs, i, j + 1) + 1);
         }
         
         return pathLenghs[i][j];
     }
}
