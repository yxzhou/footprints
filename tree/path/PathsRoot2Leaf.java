package fgafa.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 
 * Given a binary tree, return all root-to-leaf paths.

Example
Given the following binary tree:

   1
 /   \
2     3
 \
  5
All root-to-leaf paths are:

[
  "1->2->5",
  "1->3"
]
 *
 */


public class PathsRoot2Leaf {

        /**
         * @param root the root of the binary tree
         * @return all root-to-leaf paths
         */
        public List<String> binaryTreePaths(TreeNode root) {
            List<String> result = new ArrayList<String>();
            
            //check
            if(null == root){
                return result;
            }
            
            binaryTreePaths(new StringBuilder(), root, result);
            
            return result;
        }
        
        private void binaryTreePaths(StringBuilder s, TreeNode node, List<String> result){
            int start = s.length();
            s.append("->");
            s.append(node.val);
            
            if(null == node.left && null == node.right){
                result.add(s.toString().substring(2));
            }else{
                if(null != node.left){
                    binaryTreePaths(s, node.left, result);
                }
                if(null != node.right){
                    binaryTreePaths(s, node.right, result);
                }
            }
            
            s.delete(start, s.length());
        }
        
        
        public List<String> binaryTreePaths_iterator(TreeNode root) {
            List<String> result = new ArrayList<String>();
            
            //check
            if(null == root){
                return result;
            }
            
            Path path = new Path(root);
            Queue<Path> paths = new LinkedList<Path>();
            paths.add(path);
            
            String tmp;
            TreeNode node;
            while(!paths.isEmpty()){                
                path = paths.poll();
                node = path.node;
                tmp = path.str + "->" + node.val;
                
                if(null == node.left && null == node.right){
                    result.add(tmp.substring(2));
                    continue;
                }
                
                if(null != node.left){
                    paths.add(new Path(tmp, node.left));
                }
                
                if(null != node.right){
                    paths.add(new Path(tmp, node.right));
                }
                    
            }
            
            return result;
        }
        
        class Path{
            String str = "";
            TreeNode node = null;
            
            Path(String str, TreeNode node){
                this.str = str;
                this.node = node;
            }
            
            Path(TreeNode node){
                this.node = node;
            }
        }
        
    /**
     * Definition of TreeNode:
     */
    public class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }
         
}
