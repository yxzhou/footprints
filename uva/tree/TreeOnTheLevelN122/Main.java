package fgafa.uva.tree.TreeOnTheLevelN122;

import java.io.*;
import java.util.*;

class Main
{

  
  private static final String NOTCOMPLETE= "not complete";
  
  public String levelOrder(HashMap<String, String> tree, boolean duplicate) {
    //check
    if(!tree.containsKey("") || duplicate)
      return NOTCOMPLETE;
    
    //init
    LinkedList<String> list = new LinkedList<String>(); 
    list.add(""); //add the root
    
    //
    String key, tmp;
    StringBuilder sb = new StringBuilder();
    while(!list.isEmpty()){
      key = list.pop();
            
      sb.append(tree.get(key));
      sb.append(" ");
      tree.remove(key);
      
      tmp = key+"L";
      if(tree.containsKey(tmp))
        list.add(tmp);
        
      tmp = key+"R";
      if(tree.containsKey(tmp))
        list.add(tmp);      
    }

    //output
    if(tree.size() == 0)
      return sb.substring(0, sb.length() - 1);
    else
      return NOTCOMPLETE;
  }
 
  public static void main(String[] args) throws Exception {

    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");

    HashMap<String, String> tree = new HashMap<String, String>();
    
    StringTokenizer st;
    String val, posit, pair;
    boolean duplicate = false; 
    int commaIndex = -1;
    try {
      while (in.hasNext()) {        
        st = new StringTokenizer(in.nextLine(), " ");
        
        while(st.hasMoreTokens()){
          pair = st.nextToken();
          if("()".equals(pair)){
            System.out.println(sv.levelOrder(tree, duplicate));
            
            //re-init
            tree = new HashMap<String, String>();
            duplicate = false;
            break;
            
          }else{
            //parse
            commaIndex = pair.indexOf(",");
            
            val = pair.substring(1, commaIndex);
            posit = pair.substring(commaIndex+1, pair.length() -1);
            
            if(tree.containsKey(posit))
              duplicate = true;
            
            tree.put(posit, val);
          }          
        }
      }
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    finally {
      in.close();
    }

  }

}
