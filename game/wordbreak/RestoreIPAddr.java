package fgafa.game.wordbreak;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import fgafa.util.Misc;


/**
 * 
 * Restore IP Addresses
 * Given a string containing only digits, restore it by returning all possible valid IP address combinations.
 * 
 * For example:
 * Given "25525511135",
 * 
 * return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
 * 
 * @author yxzhou
 *
 */

public class RestoreIPAddr
{

  /**
   * 
   * example:
   * input: "010010"
   * output: "0.100.1.0" , "0.10.0.10" 
   * 
   * solution:
   *    <"","010010"> 
   * => <"0.","10010">
   * => <"0.1.","0010">, <"0.10.","010">, <"0.101.","10">
   * => <"0.10.0.","10">, <"0.101.1.","0">
   * => <"0.10.0.10","">, <"0.101.1.0","">
   * 
   * @param s
   * @return
   */
  public List<String> restoreIpAddresses(String s) {
    List<String> result = new ArrayList<String>(); 
    Hashtable<String, String> ht = new Hashtable<String, String>();  // example:  <"0.10.","010"> <"0.100.","10">
    
    if(s == null || s.length() < 4 || s.length() > 12 )
      return result;
    
    ht.put("", s);   //start point, example <"","010010">
    String key, tmp, tmpKey;
    int size, tmpLen;

    for(int i=0; i<4; i++){
      result = getKeys(ht);
      size = result.size();
      for(int j=0; j<size; j++){
        key = result.get(j);
        tmp = ht.get(key);
        ht.remove(key);
           
        if(i==3){
          tmpKey = tmp;  
          if(isValidIP(tmpKey))
            ht.put(key + tmpKey , "");
        }else{  
          tmpLen = Math.min(4, tmp.length());
          for(int k=1; k< tmpLen; k++){
            tmpKey = tmp.substring(0, k);
            
            if(isValidIP(tmpKey))
              ht.put(key + tmpKey + ".", tmp.substring(k));
          }
        }
      }
    }
    
    return getKeys(ht);
  }

    private boolean isValidIP(String digit) {
        if (digit == null || digit.equals(""))
            return false;

        if(digit.startsWith("0")){
            if(1 == digit.length())
               return true;
            else
               return false;
        }
        
        try {
            int i = Integer.valueOf(digit);
            return i >= 0 && i <= 255;
        } catch (NumberFormatException nfe) {
            return false;
        }

    }
  
  private  ArrayList<String> getKeys(Hashtable<String, String> ht){
    ArrayList<String> result = new ArrayList<String>();

    for (Enumeration<String> e = ht.keys(); e.hasMoreElements(); ) {
      result.add(e.nextElement());
    }
    
    return result;
  }

  public List<String> restoreIpAddresses_n(String s) {
      List<String> ret = new ArrayList<>();
      if(null == s || 4 > s.length() || 12 < s.length()){
          return ret;
      }

      restoreIpAddresses_recursive(s, 0, new ArrayList<String>(4), ret);
      return ret;
  }
  
  private void restoreIpAddresses_recursive(String s, int i, List<String> ip, List<String> result){
        if (3 == ip.size()) {
            String fourthStr = s.substring(i);
            if (isValidIP(fourthStr)) {
                StringBuilder sb = new StringBuilder();
                for (String tmp : ip) {
                    sb.append(tmp);
                    sb.append(".");
                }
                sb.append(fourthStr);
                result.add(sb.toString());
            }
        } else {
            for (int j = i + 1; j < s.length(); j++) {
                if (isValidIP(s.substring(i, j))) {
                    ip.add(s.substring(i, j));
                    restoreIpAddresses_recursive(s, j, ip, result);
                    ip.remove(ip.size() - 1);
                } else {
                    break;
                }
            }
        }
  }

  
  /**
   * @param s the IP string
   * @return All possible valid IP addresses
   */
  public ArrayList<String> restoreIpAddresses_n2(String s) {
      ArrayList<String> result = new ArrayList<String>();
      
      //check
      if(null == s || 0 == s.length()){
          return result;
      }
      
      restoreIPAddress(s, 0, new StringBuilder(), 0, result);
      
      return result;
  }
  
  private void restoreIPAddress(String s, int index, StringBuilder ip, int count, ArrayList<String> list){
      String tmp;
      
      if(3 == count){
          tmp = s.substring(index);
          
          if(isValidIP(tmp)){
              list.add(ip.substring(1).toString() + "." + tmp);
          }
          
          return;
      }
      
      for(int end = index + 1; end < s.length(); end++){
          tmp = s.substring(index, end);
          
          if(!isValidIP(tmp)){
              break;
          }
          
          int size = ip.length();
          ip.append('.');
          ip.append(tmp);
          
          restoreIPAddress(s, end, ip, count + 1, list);
          
          ip.delete(size, ip.length());
      }
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    
    /* fundmental test */
    Hashtable<String, String> ht = new Hashtable<String, String>();
    
    ht.put("11", "aa");
    ht.put("22", "bb");
    
    /* travel the hashtable #1*/
    for (Enumeration<String> e = ht.keys(); e.hasMoreElements(); ) {
      String key = e.nextElement();
      System.out.println(key);
      
      if(key.length() == 2)
        ht.put(key+"test", "test");
    }
    System.out.println("===ht.size()===" + ht.size());  
    
    /* travel the hashtable #2 */
    Iterator<String> iterator = ht.keySet().iterator();  

    while (iterator.hasNext()) {  
      String key = (String)iterator.next();  
      
      System.out.println(key);
      
      if(key.length() == 2)
        ht.put(key+"test", "test");  // it will trigger java.util.ConcurrentModificationException
   }
    System.out.println("===ht.size()===" + ht.size());  
    /* travel the hashset #3 */
		Set<String> set = new HashSet<String>();
		set.add("11");
		set.add("22");

		Iterator<String> iterator2 = set.iterator();

		while (iterator2.hasNext()) {
			String key = (String) iterator2.next();

			System.out.println(key);

			if("22".equals(key)){
			    set.remove(key);
			}
//			if (key.length() == 2){
//				set.add(key + "test"); // it will trigger java.util.ConcurrentModificationException
//			}
		}
		System.out.println("===ht.size()===" + ht.size());  
    
    
    String[] s = {"010010"};
    RestoreIPAddr sv = new RestoreIPAddr();

    System.out.println("==isValid=="+ sv.isValidIP(null) );
    System.out.println("==isValid=="+ sv.isValidIP("0") );
    System.out.println("==isValid=="+ sv.isValidIP("01") );
    System.out.println("==isValid=="+ sv.isValidIP("260") );
    System.out.println("==isValid=="+ sv.isValidIP("-3") );
    
    for(int i=0; i<s.length; i++){
      
      Misc.printArrayList(sv.restoreIpAddresses(s[i]));
      Misc.printArrayList(sv.restoreIpAddresses_n(s[i]));
    }
    
  }

}
