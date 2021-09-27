package basic.serialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
 *
 * Design an algorithm to serialize and deserialize an N-ary tree. An N-ary tree is a rooted tree in which each node has no more than N children. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that an N-ary tree can be serialized to a string and this string can be deserialized to the original tree structure.
 *
 * For example, you may serialize the following 3-ary tree
 *             1
 *        /    |   \
 *      3      2   4
 *     / \
 *   5    6
 *
 *
 * as [1 [3[5 6] 2 4]]. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
 *
 *
 *
 * Note:
 *
 * N is in the range of [1, 1000]
 * Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.
 *
 */

public class NaryTreeSerialize {
    // Definition for a Node.
    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val,List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    // Encodes a tree to a single string.
    // example. return 1[3[5][6]][2][4]]
    public String serialize(Node root) {
        if(null == root){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        dfs(root, sb);

        return sb.toString();
    }

    private void dfs(Node node, StringBuilder sb) {
        sb.append('[');
        sb.append(node.val);
        //sb.append(' ');

        if(node.children != null){
            for(Node child : node.children){
                sb.append(serialize(child));
                //sb.append(' ');
            }
        }

        sb.append(']');
    }

    // Decodes your encoded data to tree.
    // example. input 1[3[5][6]][2][4]]
    public Node deserialize(String data) {
        if(0 == data.length()){
            return null;
        }

        Stack<Node> stack = new Stack<>();
        int n = 0;
        Node top;
        boolean flag = false;
        for(char c : data.toCharArray()){
            if(c == '['){
                if(flag){
                    stack.push(new Node(n, new ArrayList<>()));
                    n = 0;
                    flag = false;
                }

            }else if(c == ']'){
                if(flag){
                    if(stack.isEmpty()){
                        stack.push(new Node(n, new ArrayList<>()));
                    }else{
                        stack.peek().children.add(new Node(n, new ArrayList<>()));
                    }

                    n = 0;
                    flag = false;
                }else{
                    if(stack.size() > 1){
                        top = stack.pop();
                        stack.peek().children.add(top);
                    }
                }

            }else {
                n = n * 10 + (c - '0');
                flag = true;
            }
        }

        return stack.pop();
    }


    public static void main(String[] args){

        System.out.println("===start==");

        NaryTreeSerialize sv = new NaryTreeSerialize();

        String[] inputs = {
                "[1[3[5][6]][2][4]]",
                "[44]",
                "[1[2[3][4]][5[6]]]",
                "[0[2[3][4]][5[6]]]"
        };

        for(String input : inputs){
            Node root = sv.deserialize(input);
            String result = sv.serialize(root);

            if(!input.equals(result)){
                System.out.println(input + "\n" + result);
            }
        }

        System.out.println("===end==");

    }

}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
