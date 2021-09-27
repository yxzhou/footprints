package uva.datastructure.AnagramCheckerN148;

import java.io.*;
import java.util.*;

class Main
{
   
  class Word{
    String origin = "";
    HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
    
    private Word(){};
    
    public Word(String word){
      this.origin = word;
      dumpString2HM(word, hm);
    }
    
  }

  private void dumpString2HM(String phrase, HashMap<Character, Integer> hm){
    Character cTmp;
    for(int i= 0; i< phrase.length() ; i++ ){
      cTmp = phrase.charAt(i);
      
      if(cTmp == ' ')  //ignore the space
        continue;
      
      if(hm.containsKey(cTmp))
        hm.put(cTmp, hm.get(cTmp) + 1);
      else
        hm.put(cTmp, 1);
      
    }
  }
  
  class Phrase extends Word{
    
    private Phrase(){};
    
    public Phrase(String word){
      super(word);
    }
    
    public Phrase clone(){
      Phrase newObject = new Phrase();
      
      for(Character c : hm.keySet())
        newObject.hm.put(c, hm.get(c));
      
      return newObject;
    }
   
    public boolean isInclude(Word p2){
      if(p2.hm.size() > hm.size())
        return false; 
            
      for(Character c : p2.hm.keySet()){
        if(!hm.containsKey(c) || p2.hm.get(c) > hm.get(c))
          return false;
      }
      
      return true;
    }
    
    public void remove(Word p2){
      int diff = 0;
      for(Character c : p2.hm.keySet()){
        diff = hm.get(c) - p2.hm.get(c);
        if(diff == 0)
          hm.remove(c);
        else
          hm.put(c, diff);
      }
      
    }
    
    public boolean isEmpty(){
      return hm.size() == 0;
    }
    
  }
  
  
  
  public void anagramChecking(ArrayList<String> results, String curr, Phrase p, ArrayList<Word> dict, int index){
    
    //System.out.println(curr + " -- " + index);
    
    if(p.isEmpty()){
      results.add(curr);
      return;
    }
      
    Word word;
    for(int i = index; i< dict.size(); i++){
      word = dict.get(i);
      if(p.isInclude(word)){
        Phrase newP = p.clone();
        newP.remove(word);
        anagramChecking(results, curr + " " + word.origin, newP, dict, i + 1);
      }
      
    }
  }
  
  
  public String reOrder(String s){
    ArrayList<String> list = new ArrayList<String> ();
    
    StringTokenizer st = new StringTokenizer(s, " ");
    while(st.hasMoreTokens()){
      list.add(st.nextToken());
    }
    
    Collections.sort(list);
    
    StringBuilder sb = new StringBuilder();
    for(String tmp : list){
      sb.append(" ");
      sb.append(tmp);
      
    }  
    
    return sb.toString();
  }
  
  public static void main(String[] args) throws Exception {

    //init
    Main sv = new Main();
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    //long start = System.currentTimeMillis();
    
    String line;
    int flag = 0;   // 0 means words for dictionary, 1 means phrases, 2 means exit 
    
    ArrayList<Word> dict = new ArrayList<Word> ();
    ArrayList<String> output;
    String newOrder;
    try {
      while (in.hasNext()) {        
        //read
        line = in.nextLine();  // in.nextLine().trim()

        if("#".equals(line) ){
          if(flag == 1)
            return;  // exit when it's the second #
          
          flag ++;
          continue;
        }
        
        if(flag == 0){  //words in dictionary
          dict.add(sv.new Word(line));          
        }else{  // phrase
          output = new ArrayList<String> ();
          sv.anagramChecking(output, "", sv.new Phrase(line), dict, 0);
          
          newOrder = sv.reOrder(line);
          
          for(String s : output){
            if( newOrder.length() != s.length() || !newOrder.equals(s) ) //Do not include the set consisting of the original words.
              System.out.println(line + " =" + s);
          }  
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