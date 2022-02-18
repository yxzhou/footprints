/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tree;

import java.util.Scanner;

/**
 *
 * 
 */
public class TreeUtils {
    
    public TreeNode readBinaryTree(Scanner in) {

        String token;

        // read header
        TreeNode root;
        token = in.next();
        if ("#".equals(token)) {
            root = null;
            return root;
        } else {
            root = new TreeNode(Integer.parseInt(token));
            readBinaryTreeHelper(root, true, in);
            readBinaryTreeHelper(root, false, in);
            return root;
        }
    }

    private void readBinaryTreeHelper(TreeNode root, boolean isLeft, Scanner in) {
        String token = in.next();
        if ("#".equals(token)) {
            return;
        } else {
            TreeNode newNode = new TreeNode(Integer.parseInt(token));
            if (isLeft) {
                root.left = newNode;
            } else {
                root.right = newNode;
            }

            readBinaryTreeHelper(newNode, true, in);
            readBinaryTreeHelper(newNode, false, in);
        }
    }
    
}
