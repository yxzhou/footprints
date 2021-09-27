package easy;

import util.Misc;

import java.util.ArrayList;
import java.util.List;

/*
 * Given an array of words and a length L, format the text such that each line 
 * has exactly L characters and is fully (left and right) justified. You should
 * pack your words in a greedy approach; that is, pack as many words as you can
 * in each line. 
 * Pad extra spaces ' ' when necessary so that each line has exactly L characters.
 * Extra spaces between words should be distributed as evenly as possible. 
 * If the number of spaces on a line do not divide evenly between words, 
 * the empty slots on the left will be assigned more spaces than the slots on the right.
 * 
 * For the last line of text, it should be left justified and no extra space is inserted between words.
 * 
 * For example,<br>
 * words: ["This", "is", "an", "example", "of", "text", "justification."]<br>
 * L: 16.<br>
 *
 * Return the formatted lines as: <br>
 * [<br>
 *    "This    is    an",<br>
 *    "example  of text",<br>
 *    "justification.  "<br>
 * ]<br>
 *
 * For example, given the list of words ["the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"] and k = 16,
 * you should return the following:

    ["the  quick brown", # 1 extra space on the left
     "fox  jumps  over", # 2 extra spaces distributed evenly
     "the   lazy   dog"] # 4 extra spaces distributed evenly

 *
 * Note: Each word is guaranteed not to exceed L in length.<br>
 * 
 */

public class TextJustification
{

  public ArrayList<String> fullJustify_1(String[] words, int L) {
    ArrayList<String> returnvalue = new ArrayList<String>();
    
    if(words == null || words.length == 0){
      return returnvalue;
    }
    
    int currLineLenRest = L;   //default value
    int currLineStart = 0; 
    for(int i=0; i<words.length; i++){
      int currWordLen = words[i].length();
      
      if( currLineLenRest > currWordLen){  // currLineLenRest > currWordLen 
        currLineLenRest -= 1 + currWordLen;  
        continue;            
      }
      
      StringBuffer sb = new StringBuffer();      
      if( currLineLenRest == currWordLen){  //  currLineLenRest == currWordLen 
        sb.append(words[currLineStart++]); 
        
        while (currLineStart <= i) {
          sb.append(" ");
          sb.append(words[currLineStart++]);
        }  
        
        currLineLenRest = L;
      }else{                               // currLineLenRest < currWordLen 
        sb.append(words[currLineStart++]); 
        
        if( currLineStart == i ){ //A line other than the last line might contain only one word, add the space after the word  
            sb.append(buildSpace(currLineLenRest+1));
        }else{  //
          int result = (currLineLenRest + 1) / (i - currLineStart);
          int rest = (currLineLenRest + 1) % (i - currLineStart);
          String spaces = buildSpace(result + 1);

          while (currLineStart < i) {
            if (rest-- > 0){
              sb.append(" ");
            }
              
            sb.append(spaces);
            sb.append(words[currLineStart++]);
          }        
        }
        
        currLineLenRest = L - 1 - currWordLen ;
      }
      
      returnvalue.add(sb.toString());
    }
    
    if(currLineStart < words.length){
      StringBuffer sb = new StringBuffer();
      sb.append(words[currLineStart++]);
      
      while (currLineStart < words.length){
        sb.append(" ");    
        sb.append(words[currLineStart++]);   
      }
      
      sb.append(buildSpace(currLineLenRest+1));
      returnvalue.add(sb.toString());      
    }
    
    return returnvalue;
  }
  
  private String buildSpace(int num){
    StringBuffer sb = new StringBuffer();
    
    for(int i=0; i<num; i++)
      sb.append(" ");
    
    return sb.toString();
  }

    public List<String> fullJustify_1_n(String[] words, int L) {
        List<String> lines = new ArrayList<String>();

        int index = 0;
        while (index < words.length) {
            int count = words[index].length();
            int last = index + 1;
            while (last < words.length) {
                if (words[last].length() + count + 1 > L) break;
                count += words[last].length() + 1;
                last++;
            }

            StringBuilder builder = new StringBuilder();
            int diff = last - index - 1;
            // if last line or number of words in the line is 1, left-justified
            if (last == words.length || diff == 0) {
                for (int i = index; i < last; i++) {
                    builder.append(words[i] + " ");
                }
                builder.deleteCharAt(builder.length() - 1);
                for (int i = builder.length(); i < L; i++) {
                    builder.append(" ");
                }
            } else {
                // middle justified
                int spaces = (L - count) / diff;
                int r = (L - count) % diff;
                for (int i = index; i < last; i++) {
                    builder.append(words[i]);
                    if (i < last - 1) {
                        for (int j = 0; j <= (spaces + ((i - index) < r ? 1 : 0)); j++) {
                            builder.append(" ");
                        }
                    }
                }
            }
            lines.add(builder.toString());
            index = last;
        }


        return lines;
    }


    public List<String> fullJustify_2(String[] words, int length){
        List<String> result = new ArrayList<>();
        if(null == words || words.length == 0){
            return result;
        }

        int left = 0;
        int right = 0;
        int rest = length;
        while(right < words.length){
            if(words[right].length() <= rest){
                rest -= words[right].length() + 1;
                right++;
            }else{
                result.add(buildLine(words, length, left, right, rest));

                left = right;
                rest = length;
            }

        }
        result.add(buildLine(words, length, left, right, rest));

        return result;
    }

    public List<String> fullJustify_2_n(String[] words, int L) {
        List<String> returnvalue = new ArrayList<>();

        if (words == null || words.length == 0) {
            return returnvalue;
        }

        int rest = L;
        int i = 0;
        int j = 0;
        while (j < words.length) {

            if (words[j].length() <= rest) {
                rest -= words[j].length() + 1;
                j++;
            } else {
                returnvalue.add(buildLine(words, L, i, j, rest));

                i = j;
                rest = L;
            }

        }

        returnvalue.add(buildLine(words, L, i, j, rest));

        return returnvalue;
    }

    private String buildLine(String[] words,
            final int length,
            int start,
            int end,
            int rest) {
        if (start == end) {
            throw new IllegalArgumentException(words[start] + "is longer than " + length);
        }

        StringBuilder result = new StringBuilder();

        rest++;
        int wordsNum = end - start;
        int middleSpaceAverage = 0;
        int middleSpaceRest = 0;
        int tailSpaceNum = 0;
        if (wordsNum == 1) {
            tailSpaceNum = length - words[start].length(); // rest
        } else {
            middleSpaceAverage = rest / (wordsNum - 1) + 1;
            middleSpaceRest = rest - (wordsNum - 1) * (middleSpaceAverage - 1);
        }

        while (start < end - 1) {
            result.append(words[start]);
            result.append(buildSpace(middleSpaceAverage));

            if(middleSpaceRest > 0){
                result.append(" ");
                middleSpaceRest--;
            }

            start++;
        }
        result.append(words[start]);
        result.append(buildSpace(tailSpaceNum));

        return result.toString();
    }


  /**
   * @param args
   */
  public static void main(String[] args) {
    String[][] words = {{""}, {""},{"a"},{"a"},{"a","b","c","d","e"}, {"a","b","c","d","e"}
    , {"Listen","to","many,","speak","to","a","few."}, {"What","must","be","shall","be."},
            {"What","must","be","shall","be."},
            {"This", "is", "an", "example", "of", "text", "justification."},
            {"the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"}};
    int[] L = {0, 2, 1, 2, 1, 3, 6, 5, 12, 16, 16};
    
    TextJustification sv = new TextJustification();

    for(int i=0; i< words.length; i++){
      
      System.out.println("Input :" + Misc.array2String(words[i]) + " " + L[i]);
      
      Misc.printArrayList(sv.fullJustify_1(words[i], L[i]));

        Misc.printArrayList(sv.fullJustify_1_n(words[i], L[i]));
      
      Misc.printArrayList(sv.fullJustify_2_n(words[i], L[i]));


      Misc.printArrayList(sv.fullJustify_2(words[i], L[i]));
    }
    
      
  }

}
