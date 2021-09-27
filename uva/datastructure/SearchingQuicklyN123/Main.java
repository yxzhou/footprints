package uva.datastructure.SearchingQuicklyN123;

import java.io.*;
import java.util.*;

class Main
{
  
  private static final String FLAG_INPUT = "::";
  
  private static String combineTitle(String[] words, int index, int length) {
    StringBuilder sb = new StringBuilder();
    
    for( int i=0; i<length; i++){
      if(i == index)
        sb.append(words[i].toUpperCase());
      else
        sb.append(words[i]);
      
      sb.append(" ");
    }
    
    return sb.substring(0, sb.length() - 1);
  }
 
  public static void main(String[] args) throws Exception {

    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");

    HashMap<String, Integer> ignore = new HashMap<String, Integer>(); //store the words to ignore from the titles
    HashMap<String, ArrayList<String>> output = new HashMap<String, ArrayList<String>>(); // <keyword, title>
    ArrayList<String> tmpList;  //title list for a keyword 
    HashSet<String> keyWord = new HashSet<String>(); // store all keywords 
    
    StringTokenizer st;
    String line;
    String[] word = new String[15];
    boolean isTitle = false;

    try {
      while (in.hasNext()) {        
          line = in.nextLine().trim();
          if(isTitle){          
            st = new StringTokenizer(line.toLowerCase(), " "); 
            //count = st.countTokens();
            
            int count = 0;
            while(st.hasMoreTokens())
              word[count++] = st.nextToken();
            
            for(int i=0; i< count; i++){
              if(!ignore.containsKey(word[i])){
                keyWord.add(word[i]);
                
                if(output.containsKey(word[i]))
                  tmpList = output.get(word[i]);
                else{
                  tmpList = new ArrayList<String>();
                  output.put(word[i], tmpList);
                }
                
                tmpList.add(combineTitle(word, i, count));
              }
            }
            
          }else{          
            if(FLAG_INPUT.equals(line))
              isTitle = true;
            else
              ignore.put(line, 1);
          }
      }
      
      //output
      String[] keyWordsArray = new String[keyWord.size()];
      
      int i = 0;
      for(String key : keyWord)
        keyWordsArray[i++] = key;

      Arrays.sort(keyWordsArray);
      
      for(i=0; i<keyWordsArray.length; i++){
        tmpList = output.get(keyWordsArray[i]);
        for(int j=0; j<tmpList.size(); j++ )
          System.out.println(tmpList.get(j));
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
