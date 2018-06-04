package fgafa.tree;

import java.util.ArrayList;
import java.util.List;

import fgafa.basic.serialize.BinaryTreeSerialize;

public class BinaryTree {

  protected TreeNode root;



  public BinaryTree(TreeNode root) {
    this.root = root;
  }

  public BinaryTree() {

  }

  public TreeNode getRoot() {
    return root;
  }

  public void setRoot(TreeNode root) {
    this.root = root;
  }

  
  /**
   * count the leaf number of the binary tree
   * @param p
   */
  public int countLeaf (TreeNode p) {
    int cnt = 1;
    
    if ( p != null ) {
      cnt = countLeaf(p.left) + countLeaf(p.right); 
    }
    
    return cnt;
  }
   

  /**
   * print a Node
   * @param p
   */
  static void visit(TreeNode p) {
    System.out.print(p.val + " ");
  }


  /** 
   * @param args 
   */
	public static void main(String[] args) {
        System.out.println("This is Bianry Tree");
	}
  

	/**
	 * 
	 * Given preorder and inorder traversal of a tree, construct the binary
	 * tree.
	 * 
	 * Note: You may assume that duplicates do not exist in the tree.
	 */
	public TreeNode buildTree_preorderInorder(int[] preorder, int[] inorder) {
		if (null == preorder || null == inorder
				|| preorder.length != inorder.length || 0 == preorder.length) {
			return null;
		}

		return buildTree_preorderInorder(preorder, 0, preorder.length - 1, inorder, 0,
				inorder.length - 1);
	}

	private TreeNode buildTree_preorderInorder(int[] preorder, int s1, int e1, int[] inorder,
			int s2, int e2) {
		if (s2 > e2) {
			return null;
		} else if (s2 == e2) {
			return new TreeNode(inorder[s2]);
		}

		int index = getIndex(inorder, s2, e2, preorder[s1]);

		if (-1 == index) {
			throw new IllegalArgumentException(
					"the input is not right, inorder-" + inorder);
		}

		TreeNode root = new TreeNode(preorder[s1]);
		root.left = buildTree_preorderInorder(preorder, s1 + 1, s1 + index - s2, inorder, s2, index - 1);
		root.right = buildTree_preorderInorder(preorder, e1 + index + 1 - e2, e1, inorder, index + 1, e2);

		return root;
	}

	private int getIndex(int[] arr, int start, int end, int target) {
		for (int i = start; i <= end; i++) {
			if (target == arr[i]) {
				return i;
			}
		}
		return -1;
	}
	
    public TreeNode buildTree_inorderPostorder(int[] inorder, int[] postorder) {
        if(null == inorder || null == postorder || inorder.length != postorder.length || 0 == inorder.length){
        	return null;
        }
        
        return buildTree_inorderPostorder(inorder, 0, inorder.length -1, postorder, 0, postorder.length -1);
    }
    
    private TreeNode buildTree_inorderPostorder(int[] inorder, int s1, int e1, int[] postorder, int s2, int e2){
    	if(s1 > e1){
    		return null;
    	}else if(s1 == e1){
    		return new TreeNode(inorder[s1]);
    	}
    	
    	int index = getIndex(inorder, s1, e1, postorder[e2]);
    	if(-1 == index){
    		throw new IllegalArgumentException("");
    	}
    	
    	TreeNode root = new TreeNode(postorder[e2]);
    	root.left = buildTree_inorderPostorder(inorder, s1, index -1, postorder, s2, index - 1 - s1 + s2);
    	root.right = buildTree_inorderPostorder(inorder, index + 1, e1, postorder, e2 - e1 + index, e2 - 1);
    	
    	return root;
    }
    
    /**
     * init a binary tree
     * @return the root Node of the tree
     *         8
     *      /      \  
     *     4        7
     *    /  \       \
     *   2    3       6
     *    \          / 
     *     1        5
     */
    public static TreeNode initBT() {
      
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(2, null, a);
        TreeNode c = new TreeNode(3);
        TreeNode d = new TreeNode(4, b, c);
        TreeNode e = new TreeNode(5);
        TreeNode f = new TreeNode(6, e, null);
        TreeNode g = new TreeNode(7, null, f);
        TreeNode h = new TreeNode(8, d, g);
        return h;// root  
    }
    
    public static List<TreeNode> initBTs(){
        //init
        ArrayList<TreeNode> list = new ArrayList<TreeNode>();
        BinaryTree tree = new BinaryTree();
        BinaryTreeSerialize seri = new BinaryTreeSerialize();
        
        //case 0
        list.add(null);
        
        //case 1
        {
            TreeNode r = new TreeNode(1);
            list.add(r);
        }
        
        //case 2
        /*
         *      2
         *     /
         *    1
         */
        {
            TreeNode a = new TreeNode(1);
            TreeNode r = new TreeNode(2, a, null);
            list.add(r);      
        }
        
        //case 3
        /*
         *      2
         *       \
         *        1
         */
        {
            TreeNode a = new TreeNode(1);
            TreeNode r = new TreeNode(2, null, a);
            list.add(r);      
        }
        
        //case 4
        /*
         *    3
         *   / \
         *  1   2
         * 
         */
        {
            TreeNode a = new TreeNode(1);
            TreeNode b = new TreeNode(2);
            TreeNode r = new TreeNode(3, a, b);
            list.add(r);      
        }

        //case 5
        /*
         *     4
         *    / \
         *   2   3
         *  /
         * 1
         *  
         */
        {
            TreeNode a = new TreeNode(1);
            TreeNode b = new TreeNode(2, a, null);
            TreeNode c = new TreeNode(3);
            TreeNode r = new TreeNode(4, b, c);
            list.add(r);      
        }
        
        //case 6
        /*
         *     4
         *    / \
         *   2   3
         *        \
         *         1
         *  
         */
        
        {
            TreeNode a = new TreeNode(1);
            TreeNode b = new TreeNode(2);
            TreeNode c = new TreeNode(3, null, a);
            TreeNode r = new TreeNode(4, b, c);
            list.add(r);      
        }

        //case 7
        /*     
         *     6
         *    / \
         *   2   5
         *  /   /
         * 1   4
         *      \
         *       3
         */
        {
            TreeNode a = new TreeNode(1);
            TreeNode b = new TreeNode(2, a, null);
            TreeNode c = new TreeNode(3);
            TreeNode d = new TreeNode(4, null, c);
            TreeNode e = new TreeNode(5, d, null);
            TreeNode r = new TreeNode(6, b, e);
            list.add(r);      
        }
        
        //case 8
        /*     
         *       8
         *      / \
         *     3   7
         *    /   /
         *   2   6
         *  /    \
         * 1      5
         *       /
         *       4
         */
        {
            TreeNode a = new TreeNode(1);
            TreeNode b = new TreeNode(2, a, null);
            TreeNode c = new TreeNode(3, b, null);
            TreeNode d = new TreeNode(4);
            TreeNode e = new TreeNode(5, d, null);
            TreeNode f = new TreeNode(6, null, e);
            TreeNode g = new TreeNode(7, f, null);
          
            TreeNode r = new TreeNode(8, c, g);
            list.add(r);      
        }
        
        //case 9
        /*
         *        3
         *       / \
         *      9  20
         *        /  \
         *       15   7
         */
        {
            TreeNode r = tree.buildTree_preorderInorder(new int[]{3, 9, 20, 15, 7}/*preorder*/, new int[]{9,3,15,20,7}/*inorder*/);
            list.add(r);
        }
        
        //case 10
        /*
         *        1
         *       / \
         *      2   3
         *     / \
         *    4   5
         */
        {
            TreeNode r = seri.deserialize("1,2,4,#,#,5,#,#,3,#,#");
            list.add(r);
        }
        
        //case 11
        /*
         *        _3_
         *       /   \
         *      9    20
         *     / \   / \
         *    4   5 2   7
         */
        {

            TreeNode r = seri.deserialize("3,9,4,#,#,5,#,#,20,2,#,#,7,#,#"); //preorder
            list.add(r);
        }
        
        System.out.print("\n init tree done");
        return list;
    }
    
  }

