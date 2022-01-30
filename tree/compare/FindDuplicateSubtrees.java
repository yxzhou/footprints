/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tree.compare;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tree.TreeNode;

/**
 * https://www.lintcode.com/problem/1108
 *
 * Given a binary tree, return all duplicate subtrees. For each kind of duplicate subtrees, you only need to return the
 * root node of any one of them.
 *
 * Two trees are duplicate if they have the same structure with same node values.
 * 
 * Example 1:
        1
       / \
      2   3
     /   / \
    4   2   4
       /
      4
     * The following are two duplicate subtrees:

      2
     /
    4
    and
    4
 * Therefore, you need to return above trees' root in the form of a list.
 * 
 */
public class FindDuplicateSubtrees {
    Map<String, Integer> visited;
    List<TreeNode> result;

    /**
     * @paramn n: An integer
     * @return: A list of root
     */
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        if(root == null){
            return Collections.EMPTY_LIST;
        }

        result = new LinkedList<>();
        visited = new HashMap<>();

        dfs(root);

        return result;
    }

    private String dfs(TreeNode node){
        if(node == null){
            return "#";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(node.val). append(",").append(dfs(node.left)).append(",").append(dfs(node.right));
        String code = sb.toString();

        visited.put(code, visited.getOrDefault(code, 0) + 1);

        if(visited.get(code) == 2){
            result.add(node);
        }

        return code;
    }
}
