package fgafa.uva.datastructure.AnanagramsN156;

import java.io.*;
import java.util.*;

class Main
{
  //Note that words that contain the same letters but of differing case are considered to be anagrams of each other
  private String reOrder(String word){
    int[] chars = new int[26]; // 'a' to 'z' and 'A' to 'Z'  'z' - 'A' = 122 - 65 = 57 
    int cTmp; // 'A'=65, 'Z'=90, 'a'=97, 'z'=122
    for(int i= 0; i< word.length() ; i++ ){
      cTmp = (int)word.charAt(i) - 65;
      if(cTmp > 25 )
        cTmp -= 32; 
      
      chars[cTmp]++;
    }
    StringBuilder sb = new StringBuilder();
    for(int i=0; i<chars.length; i++)
      for(int j=0; j<chars[i]; j++)
        sb.append((char)(i+65));
    
    return sb.toString();
  }
  

  private String reOrder2(String word){
    word = word.toLowerCase();
    char[] chars = word.toCharArray();
    Arrays.sort(chars);
    
    return new String(chars);
  }
  
  
  public static void main(String[] args) throws Exception {

    //init
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    //long start = System.currentTimeMillis();
    
    String line, originalWord, key;
    HashMap<String, String> input = new HashMap<String, String>();
    HashMap<String, Integer> summary = new HashMap<String, Integer>();
    StringTokenizer words;
    try {
      while (in.hasNext()) {        
        //read
        line = in.nextLine().trim();  // in.nextLine().trim()

        //exit when '#'
        if("#".equals(line) ){
          
          //output
          ArrayList<String> output = new ArrayList<String>();
          for(String s : input.values())
            output.add(s);
          
          Collections.sort(output);
          for(String s : output)
            System.out.println(s);
          
          return;
        }
        
        //main
        words = new StringTokenizer(line, " ");
        while(words.hasMoreTokens()){
          originalWord = words.nextToken();
          key = sv.reOrder2(originalWord);
          
          if(!summary.containsKey(key))
            input.put(key, originalWord);
          else
            input.remove(key);
            
          summary.put( key, summary.containsKey(key)? summary.get(key) + 1 : 1);            
        }
        
       }
      
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    finally {
      in.close();
      //System.out.println(System.currentTimeMillis() - start);
    }
    
    
  }
    
}