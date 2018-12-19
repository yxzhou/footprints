package fgafa.array;

import java.util.Stack;

import fgafa.util.Misc;

/*
 * input a sentence, "best wish to you"
 * 
 * output
 *  Q1 "you to wish best"
 *  Q2 "tseb hsiw ot uoy"
 *  Q3 "best wish to you"
 */
public class ReverseWords
{

  /*
   * "str1 str2 str3" => "str3 str2 str1"
   * 
   */
  public String reverse(String[] ss){
    if(ss == null) return null;
    
    StringBuffer sb = new StringBuffer();
    for(int i=ss.length -1; i>=0; i--){
      sb.append(ss[i]);      
    }    
    
    return sb.toString();
  }
  
  /*
   * input:  "today...is..friday" 
   * output: "friday..is...today"
   * 
   * do it in-place
   * */
  public void reverse(char[] input){
     //pre check input
     if(input == null || input.length == 0)
       return;
     
     //init
     int start = 0, len = input.length, end = len;
     
     //swap the whole string
     swapWholeStr(input, start, end - 1);
     
     //find the word one by one and swap them
     for(start=0, end=0; start < len; ){
       //skip the dot
       for(start=end; start < len && input[start] == '.'; start ++);
         
       //locate the word 
       for( end = start + 1; end < len && input[end] != '.'; end ++);
       
       swapWholeStr(input, start, end-1);
       
     }
  }
  
	/**
	 * 
	 * Given an input string, reverse the string word by word.
	 * 
	 * For example, Given s = "the sky is blue", return "blue is sky the".
	 * 
	 * What constitutes a word? 
	 *   A sequence of non-space characters constitutes a word. 
	 * Could the input string contain leading or trailing spaces? 
	 *   Yes. However, your reversed string should not contain leading or trailing spaces. 
	 * How about multiple spaces between two words? 
	 *   Reduce them to a single space in the reversed string.
	 */

	public String reverseWords(String s) {
		if(null ==s || 1 > s.length())
			return s;
		
		Stack<String> stack = new Stack<>();
		for(int i = 0, j = 0; i < s.length(); ){
			j = s.indexOf(' ', i);
			
			if(-1 == j){
				stack.add(s.substring(i));
				break;
			}else if(i == j){
				i++;
			}else { // i < j
				stack.add(s.substring(i, j));
				i = j + 1;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		while(!stack.isEmpty()){
			sb.append(stack.pop());
			sb.append(" ");
		}
		
		if( sb.length() > 0){
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public String reverseWords_n(String s) {
		if (null == s || 1 > s.length()) {
			return s;
		}

		/*
		  _https://docs.oracle.com/javase/tutorial/essential/regex/quant.html
		  \\s+ will split your string on one or more spaces
		 */
		String[] words = s.trim().split("\\s+");
		String result = "";
		for(int i = words.length - 1; i > 0; i--) {
			result += words[i] + " ";
		}

		return result + words[0];
	}
	
	private void swapWholeStr(char[] input, int start, int end) {
		char tmp;
		for (; start < end; start++, end--) {
			tmp = input[start];
			input[start] = input[end];
			input[end] = tmp;
		}
	}
  
  /*
   * "best" => "tseb"
   */
  private String reverse(String s){
    StringBuffer sb = new StringBuffer();
    
    char[] c = s.toCharArray();
    for(int i=c.length -1; i>=0; i--){
      sb.append(c[i]);
    }
    
    return sb.toString();
  }

  private String reverseX(String s){
    return new StringBuilder(s).reverse().toString();
  }
  
  public static String reverseRecursively(String str) {

    //base case to handle one char string and empty string
    if (str.length() < 2) {
        return str;
    }

    return reverseRecursively(str.substring(1)) + str.charAt(0);

  }

  
  /**
   * @param args
   */
  public static void main(String[] args) {
    String[] input = {"today...is..friday","",".","..","test", ".test", "test."};
    
    ReverseWords sv = new ReverseWords();
    
    for(String s : input){
      System.out.println("Input:  " + s);
      char[] tmp = s.toCharArray();
      sv.reverse(tmp);
      System.out.println("Output: " + Misc.array2String(tmp));
    }

    System.out.println();
    
    String[] input2 = {
    		"  today  is  friday  ",
    		 "",
    		 " ",
    		 "  ",
    		 "test",
    		 "a", 
    		 "  t",
    		 "t "};
    
    for(String s : input2){
      System.out.println("Input: " + s  + "\t==" +s.length());

      String ret = sv.reverseWords(s);
      System.out.println("Output: " + ret + "\t==" +ret.length());
    }
    
  }

}
