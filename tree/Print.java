package tree;

import tree.path.Height;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Print {

    public void printTreeShape(TreeNode p) {
        if (p == null) {
            return;
        }

        //get the position in horizontal
        Height hgt = new Height();
        int height = hgt.getHeight_R(p);

        //output by level
        levelorder_leftright(p, height);

    }

    private void levelorder_leftright(TreeNode p, int height) {
        if (p == null) {
            return;
        }

        //init
        int[] hgt = new int[height];
        int m = 1;
        for (int i = height - 1; i >= 0; i--) {
            hgt[i] = m;
            m <<= 1;
        }

        Queue<TreeNode> currLevel = new LinkedList<>();
        Queue<TreeNode> nextLevel = new LinkedList<>();
        currLevel.add(p);
        TreeNode node;
        //String unit

        int length = m + 1;
        String[] line4Node = null;
        String[] line4Slash = null;
        Hashtable<TreeNode, Integer> ht = new Hashtable<>();
        ht.put(p, hgt[0]);
        int row = 0;

        while (!currLevel.isEmpty()) {
            drawLine(line4Slash);

            line4Node = new String[length];
            line4Slash = new String[length];

            while (!currLevel.isEmpty()) {
                node = currLevel.poll();
//        if(!ht.containsKey(node))
//          ht.put(node, hgt[row] );

                if (node.left != null) {
                    nextLevel.add(node.left);

                    ht.put(node.left, ht.get(node) - hgt[row + 1]);
                }

                if (node.right != null) {
                    nextLevel.add(node.right);

                    ht.put(node.right, ht.get(node) + hgt[row + 1]);
                }

                appendLine(line4Node, line4Slash, node, ht);
            }

            Queue<TreeNode> tmp = currLevel;
            currLevel = nextLevel;
            nextLevel = tmp;

            //draw the line
            drawLine(line4Node);

            row++;
        }
    }

    private int appendLine(String[] line4Node, String[] line4Slash, TreeNode p, Hashtable<TreeNode, Integer> ht) {
        if (p == null) {
            return 0;
        }

        int parent = ht.get(p);
        int leftChild = 0;
        int rightChild = 0;
        if (p.left != null) {
            leftChild = ht.get(p.left);

            //to the first line
            //append underline,  ht.get(p) - ht.get(p.leftchild)
            appendUnderline(line4Node, leftChild + 1, parent);

            //to the second line     
            //append /,
            appendNode(line4Slash, " /", leftChild);
        }

        //append p itself
        appendNode(line4Node, String.valueOf(p.val), parent);

        if (p.right != null) {
            rightChild = ht.get(p.right);

            //to the first line
            //append underline, ht.get(p.rightchild) - ht.get(p)
            appendUnderline(line4Node, parent + 1, rightChild);

            //to the second line    
            //append \, 
            appendNode(line4Slash, "\\ ", rightChild);
        }

        return rightChild;
    }

    private void appendUnderline(String[] sb, int start, int end) {
        appendUnit(sb, "__", start, end);
    }

    private void appendUnit(String[] sb, String unit, int start, int end) {
        for (int i = start; i < end; i++) {
            sb[i] = unit;
        }
    }

    private void appendNode(String[] sb, String node, int start) {
        if (node.length() == 1) {
            node = " " + node;
        } else if (node.length() > 2) {
            node = node.substring(node.length() - 2);
        }

        assert (node.length() != 2) : "node=" + node;
        sb[start] = node;
    }

    private void drawLine(String[] line) {
        StringBuffer sb = new StringBuffer();
        if (line != null) {

            for (int i = 1; i < line.length; i++) {
                if (line[i] == null) {
                    sb.append("  ");
                } else {
                    sb.append(line[i]);
                }
            }
        }

        System.out.println(sb);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        List<TreeNode> list = BinaryTree.initBTs();

        //traverse
        Print pnt = new Print();

        for (int i = 0; i < list.size(); i++) {
            TreeNode root = (TreeNode) list.get(i);
            System.out.print("\n" + i + " ===  The tree === \n");

            pnt.printTreeShape(root);

        }

    }

}
