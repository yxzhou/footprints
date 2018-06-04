package fgafa.matrix;

/**
 * 
 * Write an efficient algorithm that searches for a value in an m x n matrix.
 * This matrix has the following properties:
 * 
 * Integers in each row are sorted from left to right. The first integer of each
 * row is greater than the last integer of the previous row. For example,
 * 
 * Consider the following matrix:
 * 
 * [ 
 *   [1, 3, 5, 7], 
 *   [10, 11, 16, 20], 
 *   [23, 30, 34, 50] 
 * ] 
 * Given target = 3, return true.
 *
 */
public class Search {

	public boolean searchMatrix(int[][] matrix, int target) {
		if(null == matrix){
			return false;
		}
		
		//binary search in the matix[i][0]
		int i=0;
		int j=matrix.length;
		int mid;
		while(i<j-1){
			mid = i + ((j - i) >> 1);
			
			if( target == matrix[mid][0]){
				return true;
			}else if( target < matrix[mid][0]){
				j = mid;
			}else{
				i = mid;
			}
		}
		
		int k;
		if(target == matrix[i][matrix[i].length - 1]){
			return true;
		}else if(target < matrix[i][matrix[i].length - 1]){
			k = i;  // search in ith row 
		}else{
			if( i + 1 < matrix.length){
				k = i + 1;
			}else{
				return false;
			}
		}
		
		//binary search in matrix[k][--]
		for(i=0, j=matrix[k].length; i<=j; ){
			mid = i + ((j - i) >> 1);
			
			if( target == matrix[k][mid]){
				return true;
			}else if( target < matrix[k][mid]){
				j = mid - 1;
			}else{
				i = mid + 1;
			}
		}
		
		return false;
	}

	/** 2 binary search, O(log(rows) + log(cols))*/
	public boolean searchMatrix_2(int[][] matrix, int target) {
		if(null == matrix){
			return false;
		}
		
		int low = 0;
		int high = matrix.length - 1;
		while (low <= high) {
			int mid = low + ((high - low) >> 1);
			if (matrix[mid][0] == target)
				return true;
			else if (matrix[mid][0] > target)
				high = mid - 1;
			else
				low = mid + 1;
		}

		int row = high; // here, matrix[low][0] > target > matrix[high][0]

		if (row < 0)
			return false;

		low = 0;
		high = matrix[0].length - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			if (matrix[row][mid] == target)
				return true;
			else if (matrix[row][mid] > target)
				high = mid - 1;
			else
				low = mid + 1;
		}
		return false;

	}
	
    /**
     * @param matrix, a list of lists of integers
     * @param target, an integer
     * @return a boolean, indicate whether matrix contains target
     */
    public boolean searchMatrix_3(int[][] matrix, int target) {
        //check
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            return false;
        }
        
        //search the matrix[x][0], x is from 0 to m
        int m = matrix.length;
        int n = matrix[0].length;
        if(target < matrix[0][0] || target > matrix[m - 1][n - 1]){
            return false;
        }
        
        int low = 0;
        int high = m - 1;
        int mid;
        while(low <= high){
            mid = low + ((high - low) >> 1);
            
            if(target == matrix[mid][0]){
                return true;
            }else if(target < matrix[mid][0]){
                high = mid - 1;
            }else{ // >
                low = mid + 1;
            }
        }
        
        int rowNum = Math.min(low, high);
        
        //search the matrix[rowNum][y], y is from 0 to n
        low = 0;
        high = n - 1;
        while(low <= high){
            mid = low + ((high - low) >> 1);
  
            if(target == matrix[rowNum][mid]){
                return true;
            }else if(target < matrix[rowNum][mid]){
                high = mid - 1;
            }else{ // >
                low = mid + 1;
            }
        }
        
        return false;
    }
	
	/**
	 * 1 binary search,  array index is from 0 to rows*clos - 1
	 */
	public boolean searchMatrix_n(int[][] matrix, int target) {
        if(matrix.length==0||matrix[0].length==0||matrix==null)
            return false;
            
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        int low = 0;
        int high = rows*cols-1;
        
        while(low<=high){
            int mid = (low+high)/2;
            int midValue = matrix[mid/cols][mid%cols];
            if(midValue == target)
                return true;
            else if(midValue < target)
                low = mid+1;
            else
                high = mid-1;
        }
        return false;
    }
	
	public static void main(String[] args) {
		Search sv = new Search();
		
		int[][][] input = {
				null,
				{{1}},
				{{1}},
				{{1}},
				{{1,3}},
				{{1,3}},
				{{1,3}},
				{{1,3}},
				{{1,3}},
				{{11,13}, {21, 23}},
				{{11,13}, {21, 23}},
				{{11,13}, {21, 23}},
				{{11,13}, {21, 23}},
				{{11,13}, {21, 23}},
				{{11,13}, {21, 23}},
				{{11,13}, {21, 23}},
				{{11,13}, {21, 23}},
				{{11,13}, {21, 23}},
				{{11,13}, {21, 23}}
		};
		
		int[] targets = {
				0,  //false
				0,  //false
				1,  //true
				2,  //false
				0,  //false
				1,  //true
				2,  //false
				3,  //true
				4,  //false
				10, //false
				11, //true
				12, //false
				13, //true
				14, //false
				20, //false
				21, //true
				22, //false 
				23, //true
				24  //false
		};

		boolean[] expects = {
				false,  
				false,
				true,  //
				false,  //
				false,  //
				true,  //
				false,  //
				true,  //
				false, //
				false, //
				true, //
				false, //
				true, //
				false, //
				false, //
				true, //
				false , //
				true,  //
				false				
		};
		
		int[][] matrix;
		int target;
		for(int i =0 ; i< targets.length; i++){
			matrix = input[i];
			target = targets[i];
			System.out.println(expects[i] + "==" + sv.searchMatrix(matrix, target) + "==" + sv.searchMatrix_2(matrix, target) );
		}
		
	}

}
