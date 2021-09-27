package dfsbfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class SplitSentence
{

//text is a string without spaces, you need to insert spaces into text, so each word seperated by the space in the resulting string exists in the dictionary, return the resulting string

//running time has to be at least as good as O(n)    ????

//getSentence("iamastudentfromwaterloo", {"from, "waterloo", "hi", "am", "yes", "i", "a", "student"}) -> "i am a student from waterloo"
  public String getSentence(String text, Set<String> dictionary){
    //check
    
    //init
    Stack<Integer> stack = new Stack<Integer>(); 
    int i=0, j=1;
    stack.add(i);
    String tmp = "";
    outer: while( !stack.empty()){
      i = stack.pop();
      
      for( ; j<= text.length();  j++){
        tmp = text.substring(i, j);
        if(dictionary.contains(tmp)){
          stack.push(i);
          i = j;
          
          if(j == text.length()){
            break outer;
          }
        } 
      }      
      
      j = i+1;      
      
    }

    //output
    ArrayList<String> output = new ArrayList<String>();
    //j = text.length();
    while(!stack.empty()){
      i = stack.pop();
      output.add(0, text.substring(i, j));
      
      j = i;
    }
      
    StringBuilder sb = new StringBuilder();
    for(String word : output){
      sb.append(word);
      sb.append(" ");
    }
    
    return sb.substring(0, sb.length() - 1);
    

  }


  
  /**
   * @param args
   */
  public static void main(String[] args) {

    String text = "iamastudentfromwaterloo";
    String[] dic = {"from", "waterloo", "hi", "am", "yes", "i", "a", "student"};
    Set<String> dictionary = new HashSet<String>();
    for(int i=0; i< dic.length; i++)
      dictionary.add(dic[i]);
        
    SplitSentence sv = new SplitSentence();
    System.out.println(sv.getSentence(text, dictionary));
    
  }

}
