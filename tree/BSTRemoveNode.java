package fgafa.tree;

public class BSTRemoveNode {

    /**
     * @param root: The root of the binary search tree.
     * @param value: Remove the node with given value.
     * @return: The root of the binary search tree after removal.
     */
    public TreeNode removeNode(TreeNode root, int value) {
        TreeNode virtual = new TreeNode(0);
        virtual.left = root;
        
        TreeNode parent = findParent(virtual, value);
        if(null == parent){
            return root;
        }

        //
        TreeNode target = null;
        if(null != parent.left && value == parent.left.val){
            target = parent.left;
            if(null != target.left){
                parent.left = target.left;
                
                addToRightLeaf(target.left, target.right);
            }else{
                parent.left = target.right;
            }
        }else{
            target = parent.right;
            
            if(null != target.left){
                parent.right = target.left;
                
                addToRightLeaf(target.left, target.right);
            }else{
                parent.right = target.right;
            }
        }
        
        return virtual.left;
    }

    private void addToRightLeaf(TreeNode n1, TreeNode n2){
        while(null != n1.right){
            n1 = n1.right;
        }
        
        n1.right = n2;
    }
    
    private TreeNode findParent(TreeNode node, int value){
        TreeNode result = null;
        
        if (null != node) {
            if (null != node.left) {
                if(node.left.val == value){
                    return node;
                }
                
                result = findParent(node.left, value);
            }
            
            if(null == result && null != node.right){
                if(node.right.val == value){
                    return node;
                }
                
                result = findParent(node.right, value);
            }
        }
        
        return result;
    }
    
}
