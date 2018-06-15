package fgafa.array;

import algs4.stdlib.In;

import java.io.IOException;
import java.util.Hashtable;

/*
 * 
 * Since XML is very verbose, you are given a way of encoding it where each tag gets mapped to a pre-defined 
 * integer value. The language/grammar is as follows:
 * 
 * 
 */

public class Encoding
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    //String args0 = "F://footprint//lib//gafa//test data//validation.xml";
    String args0 = "F://footprint//lib//gafa//test data//test.xml";
    
//    In in = new In(args0);
//    
//    String218 s = new String218();
//    
//    System.out.println("Result: \n");
//    try{
//      System.out.println(s.encode(in));  
//    }catch(IOException ioe){
//      ioe.printStackTrace(); 
//    }
    

  }

  public StringBuffer encode(In input) throws IOException{
    //init
    StringBuffer sb = new StringBuffer();
    
    //read line by line
    String in = input.readLine();
    
    //split line by space, 
    
    //check every token: if it's start from '<!', it's not Tag; if it's start from '<', it's TAG;if it's start from '</', it's TAG; 
    
    

    return sb; 
    
  }
  
  public static Hashtable<String,Integer> ht = new Hashtable<String,Integer>(); 
  
  public String getTagCode(String tagName){
    
    
    return "";
  }
  public String createTagCode(int index){
    
    
    
    return "";
  }  
  
}
