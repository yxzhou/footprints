package tree.lowerCommonAncestor;

import java.util.HashSet;
import java.util.Set;
import tree.TreeNode;


/**
 * 
 * Given two nodes in a Binary Tree. Find the lowest common ancestor(LCA) of the two nodes.
 * The nearest common ancestor of two nodes refers to the nearest common node among all the parent nodes of two nodes (including the two nodes).
 *
 * In addition to the left and right son pointers, each node also contains a father pointer, parent, pointing to its own father.
 * 
 */


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
    public ParentTreeNode LCAwithParentP(ParentTreeNode A, ParentTreeNode B) {
        Set<ParentTreeNode> parents = new HashSet<>();
        
        ParentTreeNode p = A;
        while(p != null){
            parents.add(p);
            p = p.parent;
        }

        p = B;
        while(p != null){
            if(parents.contains(p)){
                return p;
            }
            p = p.parent;
        }        

        return null;
    }
    
    /**
     * 
     * refer to linkedlist.Intersection solution2
     * 
     * Notes: Given two nodes in a Binary Tree
     * 
     */
    public ParentTreeNode lowestCommonAncestorII(ParentTreeNode A, ParentTreeNode B) {
//        if (headA == null || headB == null) {
//            return null;
//        }
        
        ParentTreeNode p = A;
        ParentTreeNode q = B;

        while(p != q ){
            p = (p.parent == null ? B : p.parent);
            q = (q.parent == null ? A : q.parent);
        }
        
        return p;
    }    


    class ParentTreeNode{
        public int val;
        public ParentTreeNode left, right, parent;
    }
}
