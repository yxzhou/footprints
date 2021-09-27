package datastructure.segmentTree.rangeSumQuery;

/**
 * 
 * Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).

    Example:
    Given matrix = [
      [3, 0, 1, 4, 2],
      [5, 6, 3, 2, 1],
      [1, 2, 0, 1, 5],
      [4, 1, 0, 1, 7],
      [1, 0, 3, 0, 5]
    ]
    
    sumRegion(2, 1, 4, 3) -> 8
    update(3, 2, 2)
    sumRegion(2, 1, 4, 3) -> 10
    
    Note:
    The matrix is only modifiable by the update function.
    You may assume the number of calls to update and sumRegion function is distributed evenly.
    You may assume that row1 ≤ row2 and col1 ≤ col2.
 *
 */

public class RangeSum2D_mutable {
    
    Node root = null;
    int[][] matrix;
    
    public RangeSum2D_mutable(int[][] matrix){
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            return;
        }
        
        this.matrix = matrix;
        root = build(matrix, 0, 0, matrix.length, matrix[0].length);
    }
    
    private Node build(int[][] matrix, int x1, int y1, int x2, int y2){
        Node result = new Node(x1, y1, x2, y2);
        
        if(x1 >= x2 || y1 >= y2){
            return result ;
        }else if(x1 == x2 - 1 && y1 == y2 - 1){
            result.sum = matrix[x1][y1];
            return result;
        }
        
        int xMid = x1 + ((x2 - x1) >> 1);
        int yMid = y1 + ((y2 - y1) >> 1);
        
        result.next[0] = build(matrix, x1, y1, xMid, yMid);
        result.next[1] = build(matrix, x1, yMid, xMid, y2);
        result.next[2] = build(matrix, xMid, y1, x2, yMid);
        result.next[3] = build(matrix, xMid, yMid, x2, y2);
        
        for(int i = 0; i < result.next.length; i++){
            result.sum += result.next[i].sum;
        }
        
        return result;
    }
    
    public void update(int row, int col, int newValue) {
        
        if(0 <= row && 0 <= col && row < matrix.length && col < matrix[0].length){
            update(root, row, col, newValue - matrix[row][col]);
        }

    }
    
    private void update(Node node, int x, int y, int diff){
        if(node.leftTopRow <= x && node.leftTopCol <= y && x < node.rightBottomRow && y < node.rightBottomCol){
            node.sum += diff;
            
            for(int i = 0; i < node.next.length; i++){
                update(node.next[i], x, y, diff);
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sumRegion(root, row1, col1, row2, col2);
    }
    
    private int sumRegion(Node node, int x1, int y1, int x2, int y2){
        if(x1 <= node.leftTopRow && y1 <= node.leftTopCol && node.rightBottomRow <= x2 && node.rightBottomCol <= y2){
            return node.sum;
        }else if( (x2 < node.leftTopRow && y2 < node.leftTopCol) || (node.rightBottomRow <= x1 && node.rightBottomCol <= y1) ){
            return 0;
        }
        
        int result = 0;
        for(int i = 0; i < node.next.length; i++){
            result += sumRegion(node.next[i], x1, y1, x2, y2);
        }
        
        return result;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    class Node{
        int sum = 0;
        
        int leftTopRow;
        int leftTopCol;
        int rightBottomRow;
        int rightBottomCol;
        
        Node[] next = new Node[4]; // 0-NW, 1-NE, 2-SW, 3-SE
        
        Node(int leftTopRow,
             int leftTopCol,
             int rightBottomRow,
             int rightBottomCol) {
            this.leftTopRow = leftTopRow;
            this.leftTopCol = leftTopCol;
            this.rightBottomRow = rightBottomRow;
            this.rightBottomCol = rightBottomCol;
        }
    }
}
