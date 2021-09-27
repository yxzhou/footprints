package tree.traversal;

import tree.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

public class IterativeTraversal {

    class ActionAndNode{
        int action = 0; // 0, visit; 1, print
        TreeNode node;

        ActionAndNode(int action, TreeNode node){
            this.action = action;
            this.node = node;
        }
    }

    public void  inorder_iterative(TreeNode root){
        Deque<ActionAndNode> deque = new LinkedList<>();

        deque.addFirst(new ActionAndNode(0, root));

        while(!deque.isEmpty()){
            ActionAndNode top = deque.pop();

            if(null != top && null != top.node){
                if(top.action == 1){
                    print(top.node);
                }else{
                    deque.addFirst(new ActionAndNode(0,top.node.right));
                    deque.addFirst(new ActionAndNode(1,top.node));
                    deque.addFirst(new ActionAndNode(0,top.node.left));
                }
            }
        }
    }

    public void  preorder_iterative(TreeNode root){
        Deque<ActionAndNode> deque = new LinkedList<>();

        deque.addFirst(new ActionAndNode(0, root));

        while(!deque.isEmpty()){
            ActionAndNode top = deque.pop();

            if(null != top && null != top.node){
                if(top.action == 1){
                    print(top.node);
                }else{
                    deque.addFirst(new ActionAndNode(0,top.node.right));
                    deque.addFirst(new ActionAndNode(0,top.node.left));
                    deque.addFirst(new ActionAndNode(1,top.node));
                }
            }
        }
    }

    public void  postorder_iterative(TreeNode root){
        Deque<ActionAndNode> deque = new LinkedList<>();

        deque.addFirst(new ActionAndNode(0, root));

        while(!deque.isEmpty()){
            ActionAndNode top = deque.pop();

            if(null != top && null != top.node){
                if(top.action == 1){
                    print(top.node);
                }else{
                    deque.addFirst(new ActionAndNode(1,top.node));
                    deque.addFirst(new ActionAndNode(0,top.node.right));
                    deque.addFirst(new ActionAndNode(0,top.node.left));
                }
            }
        }
    }

    private static void print(TreeNode p) {
        System.out.print(p.val + " ");
    }
}
