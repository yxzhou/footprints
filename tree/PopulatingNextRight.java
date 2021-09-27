package tree;


public class PopulatingNextRight
{

  /*
   * 
   * Given a binary tree
   * 
   *     struct TreeLinkNode {
   *       TreeLinkNode *left;
   *       TreeLinkNode *right;
   *       TreeLinkNode *next;
   *     }
   * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
   * 
   * Initially, all next pointers are set to NULL.
   * 
   * Note:
   * 
   * You may only use constant extra space.
   * You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two children).
   * For example,
   * Given the following perfect binary tree,
   *          1
   *        /  \
   *       2    3
   *      / \  / \
   *     4  5  6  7
   * After calling your function, the tree should look like:
   *          1 -> NULL
   *        /  \
   *       2 -> 3 -> NULL
   *      / \  / \
   *     4->5->6->7 -> NULL
   *    
   * 
   */
  public void connect(TreeLinkNode root) {
    if(root == null)
      return;
    
    root.next = null;    
        
    connect(root.left, root.right);
    connect(root.right, null);
  }
  
  private void connect(TreeLinkNode node, TreeLinkNode next){
    if(node == null)
      return;
    
    node.next = next;
    connect(node.left, node.right);
    
    if(next == null)
      connect(node.right, null);
    else
      connect(node.right, next.left);
  }
  
	public void connect_n(TreeLinkNode root) {
		if (null == root) {
			return;
		}

		TreeLinkNode currLevelHead = root;
		TreeLinkNode currNode;
		while (null != currLevelHead.left) {
			currNode = currLevelHead;

			while (null != currNode.next) {
				currNode.left.next = currNode.right;
				currNode.right.next = currNode.next.left;

				currNode = currNode.next;
			}
			currNode.left.next = currNode.right;

			currLevelHead = currLevelHead.left;
		}
	}
  
  /*
   * 
   * Follow up for problem "Populating Next Right Pointers in Each Node".
   * 
   * What if the given tree could be any binary tree? Would your previous solution still work?
   * 
   * Note:
   * 
   * You may only use constant extra space.
   * For example,
   * Given the following binary tree,
   *          1
   *        /  \
   *       2    3
   *      / \    \
   *     4   5    7
   * After calling your function, the tree should look like:
   *          1 -> NULL
   *        /  \
   *       2 -> 3 -> NULL
   *      / \    \
   *     4-> 5 -> 7 -> NULL
   * 
   */
  
  public void connectII(TreeLinkNode node) {
    if(node == null)
      return;
    
    TreeLinkNode thisRowHead = node;
    
    while(thisRowHead != null){

      TreeLinkNode nextRowHead = null;
      TreeLinkNode thisRowCurr = thisRowHead;
      TreeLinkNode nextRowCurr = null;
      
      for( ; thisRowCurr != null; thisRowCurr = thisRowCurr.next){
        if(thisRowCurr.left != null){
          
          if(nextRowHead == null)
            nextRowHead = thisRowCurr.left;
          
          if(nextRowCurr != null)
            nextRowCurr.next = thisRowCurr.left;
          
          nextRowCurr = thisRowCurr.left;
        }
        
        if(thisRowCurr.right != null){
          
          if(nextRowHead == null)
            nextRowHead = thisRowCurr.right;
          
          if(nextRowCurr != null)
            nextRowCurr.next = thisRowCurr.right;
          
          nextRowCurr = thisRowCurr.right;
        }
        
        
      }

      thisRowHead = nextRowHead;
    }
  }
    
    //constant space
    public void connectII_x(TreeLinkNode root){
        if(null == root){
            return;
        }
        
        TreeLinkNode curr;
        TreeLinkNode next;
        TreeLinkNode currHead = root;
        TreeLinkNode nextHead = null;
        while( null != currHead ){

            //locate nextHead
            for(curr = currHead; null != curr; curr = curr.next){
                if(null != curr.left){
                    nextHead = curr.left;
                } else if(null != curr.right){
                    nextHead = curr.right;
                }
            }
                
            if(null == nextHead){
                break;
            }
            
            //connect the next pointers
            for( next = nextHead ; null != curr; curr = curr.next){
                if(null != curr.left){
                    next.next = curr.left;
                    next = next.next;
                }
                
                if(null != next.right){
                    next.next = curr.right;
                    next = next.next;
                }
            }     
            if(next == next.next){
                next.next = null;
            }
            
            currHead = nextHead;
        }
    }
  
  
  
  public void connectII_n(TreeLinkNode root) {
      if(null == root){
    	  return;
      }
      
      TreeLinkNode currLevelNode = null;
      TreeLinkNode nextLevelHead = new TreeLinkNode(-1);
      nextLevelHead.next = root;
      TreeLinkNode nextLevelNode = null;
      
      while(null != currLevelNode || null != nextLevelHead.next){
    	  if(null == currLevelNode){ //move to the next level
    		  currLevelNode = nextLevelHead.next;
    		  nextLevelHead.next = null;
        	  nextLevelNode = nextLevelHead;
    	  }
    	  
    	  if(null != currLevelNode.left){
    		  nextLevelNode.next = currLevelNode.left;
    		  nextLevelNode = nextLevelNode.next;
    	  }
    	  
    	  if(null != currLevelNode.right){
    		  nextLevelNode.next = currLevelNode.right;
    		  nextLevelNode = nextLevelNode.next;
    	  }
    	  
    	  currLevelNode = currLevelNode.next;
      }
      
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  /* Definition for binary tree with next pointer. */
  public class TreeLinkNode {
      int val;
      TreeLinkNode left, right, next;
      TreeLinkNode(int x) { val = x; }
  }
  
}
