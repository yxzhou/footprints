package fgafa.uva.tree.ClimbingTreeN115;

import java.io.*;
import java.util.*;

class Main
{

  /* Definition for family tree */
  class TreeNode
  {
    String name;
    TreeNode parent;
    ArrayList<TreeNode> children = new ArrayList<TreeNode>();
    int level;
    int group;

    TreeNode(String x) {
      name = x;
    }
  }

  private static HashMap<String, TreeNode> tree = new HashMap<String, TreeNode>();
  private static String[] CHILD_PARENT = {"child", "parent"};

  private String calRelationship(String x, String y) {
    TreeNode the = tree.get(x);
    TreeNode other = tree.get(y);

    if (the == null || other == null || the.group != other.group)
      return "no relation";

    if(the == other || the.parent == other.parent)
      return "sibling"; 
    
    int swap = 0; // 0, not swapped; 1, swapped;
    if (the.level < other.level) {
      TreeNode tmp = the;
      the = other;
      other = tmp;
      swap = 1;
    }

    int diff = the.level - other.level;
    for (int i = diff; i > 0; i--)
      the = the.parent;

    StringBuilder out = new StringBuilder();
    if (the == other) { // child - parent relationship
      while (diff > 2) {
        out.append("great ");
        diff--;
      }
      if (diff == 2) {
        out.append("grand ");
        diff--;
      }
      if (diff == 1)
        out.append(CHILD_PARENT[swap]);

    }else { // cousin relationship
      int count = -1;
      while (the != other) {
        the = the.parent;
        other = other.parent;
        count++;
      }

      out.append(count);
      out.append(" cousin");

      if (diff != 0) {
        out.append(" removed ");
        out.append(diff);
      }
    }

    return out.toString();
  }



  /* set the group and level */
  private void analyzeTree(TreeNode root, int group, int level) {
    if (root == null)
      return;

    root.group = group;
    root.level = level++;

    for (int i = root.children.size() - 1; i >= 0; i--)
      analyzeTree(root.children.get(i), group, level);

  }



  public void builldFamilyTree() {
    // travel, get the root who has no parent
    int group = 0;
    for (String name : tree.keySet()) {
      if (tree.get(name).parent == null) {
        analyzeTree(tree.get(name), group++, 0);
      }
    }

  }



  public static void main(String[] args) throws Exception {

    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");

    String the, other;
    TreeNode child, parent;
    StringTokenizer st;
    boolean flag = true;
    try {
      while (in.hasNext()) {
        st = new StringTokenizer(in.nextLine(), " ");

        the = st.nextToken();
        other = st.nextToken();

        if (flag) {
          if (the.equals("no.child")) {
            flag = false;

            sv.builldFamilyTree();
            continue;
          }

          if(tree.containsKey(the)){
            child = tree.get(the);
          }else{
            child = sv.new TreeNode(the);
            tree.put(the, child);
          }
          
          if(tree.containsKey(other)){
            parent = tree.get(other);
          }else{
            parent = sv.new TreeNode(other);
            tree.put(other, parent);            
          }

          child.parent = parent;
          parent.children.add(child);
        }
        else {
          System.out.println(sv.calRelationship(the, other));

        }
      }
    }
    catch (Exception e) {
      // e.printStackTrace();
    }
    finally {
      in.close();
    }

  }

}
