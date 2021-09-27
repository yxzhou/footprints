package dailyCoding.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a tree where each edge has a weight, compute the length of the longest path in the tree.

 For example, given the following tree:

   a
  /| \
 b c d
    / \
   e   f
  / \
 g   h
 and the weights: a-b: 3, a-c: 5, a-d: 8, d-e: 2, d-f: 4, e-g: 1, e-h: 1, the longest path would be c -> a -> d -> f, with a length of 17.

 The path does not have to pass through the root, and each node can have any amount of children.
 *
 *
 * Tags: Uber
 */

public class LargestPath {

    class Node{
        String name;
        //V value;

        int weight;
        List<Node> next = new ArrayList<>();

        int largestPathEnd = 0;
        int largestPathIn = 0;

        Node(String name, int weight){
            this.name = name;
            this.weight = weight;
        }
    }

    public int getLargestPath(Node root){
        if(null == root || root.next.isEmpty()){
            return 0;
        }

        helper(root);
        return root.largestPathIn;
    }

    private void helper(Node node){
        if(node.next.isEmpty()){
            node.largestPathEnd = node.weight;
            node.largestPathIn = node.weight;
            return;
        }

        int[] largestPathEndAtChild = new int[2]; // default both are 0
        for(Node child : node.next){
            if(null == child){
                continue;
            }

            helper(child);

            if(child.largestPathEnd > largestPathEndAtChild[0]){
                largestPathEndAtChild[1] = largestPathEndAtChild[0];
                largestPathEndAtChild[0] = child.largestPathEnd;
            }else if(child.largestPathEnd > largestPathEndAtChild[1]){
                largestPathEndAtChild[1] = child.largestPathEnd;
            }
            node.largestPathIn = Math.max(node.largestPathIn, child.largestPathIn);
        }
        node.largestPathEnd = largestPathEndAtChild[0] + node.weight;
        node.largestPathIn = Math.max(node.largestPathIn, largestPathEndAtChild[0] + node.weight + largestPathEndAtChild[0]);
    }

}
