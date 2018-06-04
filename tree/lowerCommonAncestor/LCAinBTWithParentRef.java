package fgafa.tree.lowerCommonAncestor;


public class LCAinBTWithParentRef {

    /*
     * get the LCD of a tree
     * Node{
     *   int value;
     *   Node[] childs;
     *   
     *   Node parent;
     * }
     *
     * Time O(n)  Space O(n) 
     * 
     * As root->parent is not NULL, we don't need to pass root in.
     * 
     */
    public TreeNode LCAwithParentP(TreeNode p, TreeNode q) {
      //check
      if(p == null || q == null){
        return null;
      }
      
      int h1 = getHeight(p);
      int h2 = getHeight(q);

      if (h1 > h2) {
        return LCAwithParentP(q, p); // to make the constraint,  h1<=h2.
      }    
        
      // invariant: h1 <= h2.
      for (int diff = h2 - h1; diff > 0; diff--){
        q = q.parent;
      }
      
      for ( ; p!= null && q!=null && p!=q; p=p.parent, q=q.parent) ;
          
      return p;  // if p, q are not in a same tree, p==null.
    }
    
    private int getHeight(TreeNode p) {
      int height = 0;
      for ( ; p!=null; p = p.parent ) {
        height++;
      }
      
      return height;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    class TreeNode{
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode parent;
        
        public TreeNode(int x) {
          val = x;
        }
        
        public TreeNode(int key, TreeNode left, TreeNode right) {
            this.val = key;
            this.left = left;
            this.right = right;
        }
        
        public String toString() {
            return String.valueOf(this.val);
        }
    }
}
