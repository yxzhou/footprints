package fgafa.greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;


/*
 * Given a string of lowercase characters, reorder them such that the same characters are at least distance d from each other.
 *
 * Input: { a, b, b }, distance = 2
 * Output: { b, a, b }
 * 
 */


public class StringReorderDistanceApart
{

  /**
   * TODO
   * 
   * @param old,  a characters list
   * @param d , distance
   * @return a new characters list with new order.
   */
  
  public char[] reOrder(char[] old, int d){
    if(old == null || old.length ==0 )
      return null;
    
    //get duplicate
    Hashtable<String, Integer> count = new Hashtable<>(); // <char, count>
    String tmp; 
    for(int i=0; i< old.length; i++){
      tmp = String.valueOf(old[i]);
      if(count.containsKey(tmp))
        count.put(tmp, count.get(tmp) + 1 );
      else
        count.put(tmp, 1);
    }

    //order by the count
    ArrayList list = new ArrayList();
    Hashtable ht = new Hashtable();
    Integer iTmp;
    String sTmp;
    for (Enumeration e = count.keys() ; e.hasMoreElements() ;) {
      sTmp = (String)e.nextElement();
      iTmp = (Integer) count.get(sTmp);
      
      if( !ht.contains(iTmp)){
        ht.put(iTmp, new ArrayList<String>());
      }
       
      ((ArrayList)ht.get(iTmp)).add(sTmp);
      
      list.add(iTmp);
    }
    Collections.sort(list);
    
    //Greedy,  put the high duplicate first 
    int len = old.length;
    char[] ans = new char[len];
    int m;   // how many duplicate 'A', such as, there is 2 'A' in the old, m=2. 
    int k;   // how many character with the same duplicate num. such as there are 2 'A','B','C', k=3 
    for(int i=0; i< list.size(); i++){
      iTmp = (Integer)list.get(i);
      m = iTmp;
      k = ((ArrayList)ht.get(iTmp)).size();
      
      
      
    }
    
    
    
    return ans;
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {

//    StringReorderDistanceApart s = new StringReorderDistanceApart();
//    
//    char[][] old = {null, {}, {'a', 'b', 'b'},{'a', 'b', 'b','a', 'e'}}; 
//    
//    int[] distance = {1,1,2,2};   
//
//    for(int i=0; i<old.length; i++){
//      
//      System.out.println("Reorder " + Misc.array2String(old[i]) + " by distance:" + distance[i]);
//      
//      System.out.println("To " + Misc.array2String(s.reOrder(old[i], distance[i])));
//      
//    }
    

    for(int factor = 1; factor < 11; factor ++ ){
      System.out.println();
      for(int i = 1; i< 11; i++){
        
       System.out.print(factor * i);
       System.out.print("\t");
        
      }
      
    }
    
  }

}
