package fgafa.dailyCoding;

import java.util.*;

/**
 *
 * Implement the serialize and deserialize methods:
 *    String serialize(Node node)
 *    Node deserialize(String str)
 *
 * Tags: Google, Tree, Serialize, Escape Character
 */
public class BinaryTreeSeriallization {

    class Node {
        String value;
        Node left;
        Node right;

        Node(String value){
            this.value = value;
        }
    }

    public final String VALUE_NULL = "#";
    public final String SPLIT = ",";
    public final String ESCAPE = "/";

    public String serialize(Node root){
        if( null == root ){
            return VALUE_NULL;
        }

        StringBuilder result = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()){
            Node curr = queue.poll();
            if(curr == null){
                result.append(SPLIT).append(VALUE_NULL);
            }else{
                result.append(SPLIT).append(escape(curr.value));

                queue.add(curr.left);
                queue.add(curr.right);
            }
        }

        return result.deleteCharAt(0).toString();
    }

    public Node deserialize(String tree){
        if(null == tree || tree.isEmpty()){
            return null;
        }

        List<String> nodeValues = split(tree);

        //Misc.printList(nodeValues);

        Node root = new Node(unescape(nodeValues.get(0)));

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while(!queue.isEmpty() && i < nodeValues.size()){
            Node curr = queue.poll();

            if(!nodeValues.get(i).equals(VALUE_NULL)){
                curr.left = new Node(unescape(nodeValues.get(i)));
                queue.add(curr.left);
            }
            i++;

            if(i < nodeValues.size() && !nodeValues.get(i).equals(VALUE_NULL)) {
                curr.right = new Node(unescape(nodeValues.get(i)));
                queue.add(curr.right);
            }
            i++;
        }

        return root;
    }

    private List<String> split(String tree){
        List<String> result = new ArrayList<>();

        int i = 0;
        int j = 0;
        int count = 0;
        for( ; j < tree.length(); j++){
            switch(tree.charAt(j)){
            case '/':
                count++;
                break;
            case ',':
                if(count % 2 == 0){
                    result.add(tree.substring(i, j));
                    i = j + 1;
                }
            default:
                count = 0;
            }

        }

        if(count % 2 == 0){
            result.add(tree.substring(i, j));
        }else{
            throw new IllegalArgumentException(" Something wrong in input String ");
        }

        return result;
    }

    private String escape(String value){
        String doubleEscape = value.replaceAll(ESCAPE, ESCAPE + ESCAPE);
        String escapeSplit = doubleEscape.replaceAll(SPLIT, ESCAPE + SPLIT);
        return escapeSplit;
    }

    private String unescape(String value){
        String escapeSplit = value.replaceAll(ESCAPE + SPLIT, SPLIT);
        String doubleEscape = escapeSplit.replaceAll(ESCAPE + ESCAPE, ESCAPE);
        return doubleEscape;
    }


    public static void main(String[] args){

        String[] cases = {
                "",
                "a",    //
                "a,",
                ",a",
                "a,b",  //
                ",a,b",
                "a,b,",
                "a,b,c",  //
                "a,b,c,d", //
                "#",
                "a,b,#,c,#", //
                "a,#,b,#,c",
                "a/,,b,#",
                "a//,b,#",
                "a///,,#,c",
                "a/,/,,b,#",
                "a////,b,#"

        };

        BinaryTreeSeriallization sv = new BinaryTreeSeriallization();
        for(int i = 12; i < cases.length; i++){
            try {
                System.out.println( i + ": "+ cases[i]);

                System.out.println(" \t" + sv.serialize(sv.deserialize(cases[i])));
            }catch(Exception e){
                System.out.println("\t" + e.getMessage());
            }

            //Arrays.stream(cases[i].split("^(?://)+,$")).forEach(s -> System.out.println("\t" + s));
        }

    }
}
