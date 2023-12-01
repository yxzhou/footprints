package companyTag.google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/1361/ 
 * Text Justification
 * 
 * Given an array of words and a length L, format the text such that each line has exactly L characters and is fully
 * (left and right) justified. You should pack your words in a greedy approach; that is, pack as many words as you can
 * in each line. Pad extra spaces ' ' when necessary so that each line has exactly L characters. Extra spaces between
 * words should be distributed as evenly as possible. If the number of spaces on a line do not divide evenly between
 * words, the empty slots on the left will be assigned more spaces than the slots on the right.
 * 
 * For the last line of text, it should be left justified and no extra space is inserted between words.
 * 
 * For example,
 * words: ["This", "is", "an", "example", "of", "text", "justification."]   L: 16
 * Return the formatted lines as: 
 * [
 *   "This    is    an"
 *   "example  of text"
 *   "justification.  "
 * ]
 *
 * For example, 
 * words ["the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"] and k = 16,
 * you should return the following:
 * [
 *   "the  quick brown"  # 1 extra space on the left
 *   "fox  jumps  over"  # 2 extra spaces distributed evenly
 *   "the lazy dog    "  # no extra space is inserted between words
 * ]
 *
 * Note: 
 *   A word is defined as a character sequence consisting of non-space characters only.
 *   Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
 *   The input array words contains at least one word.
 * 
 */

public class ScreenFittingII{


    public List<String> fullJustify_1(String[] words, int L) {
        List<String> lines = new ArrayList<>();

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
                    builder.append(words[i]).append(" ");
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

    /**
     * @param words: an array of string
     * @param maxWidth: a integer
     * @return: format the text such that each line has exactly maxWidth characters and is fully
     */
    public List<String> fullJustify_n(String[] words, int maxWidth) {
        
        List<String> result = new ArrayList<>();
        int width = maxWidth + 1;
        
        StringBuilder line = new StringBuilder();
        StringBuilder extra = new StringBuilder();  //extra space in middle
        int remain; // 
        int end; // space in the end

        for(int i = 0, n = words.length, j, k, w; i < n; i++ ){
            for( j = i, w = width; j < n && w > words[j].length(); w -= words[j].length() + 1, j++ ){};

            if(j == n || j == i + 1){ // it's the last line, Or there is only one word in the line
                extra.append(" ");
                remain = 0;
                end = w;
            }else{
                for(k = w / (j - i - 1) + 1; k > 0; k--){
                    extra.append(" ");
                }

                remain = w % (j - i - 1);
                end = 0;
            }

            for(int min = Math.min(j - 1, n - 1) ; i < min; i++){
                line.append(words[i]);

                line.append(extra);

                if(remain > 0){
                    line.append(" ");
                    remain--;
                }
            }
            
            line.append(words[i]);
            for( k = end ; k > 0; k--){
                line.append(" ");
            }

            result.add(line.toString());
            line.setLength(0);
            extra.setLength(0);
        }

        return result;
    }


    
    /**
     * @param args
     */
    public static void main(String[] args) {
        String[][][] inputs = {
            //{ words, {L}, expect }
            {
                {"a"},
                {"1"},
                {"a"}
            },
            {
                {"a"},
                {"2"},
                {"a "}
            },
            {
                {"a", "b", "c"},
                {"1"},
                {
                    "a",
                    "b",
                    "c"
                }
            },
            {
                {"a", "b", "c"},
                {"3"},
                {
                    "a b",
                    "c  "
                }
            },
            {
                {"This", "is", "an", "example", "of", "text", "justification."},
                {"16"},
                {
                    "This    is    an",
                    "example  of text",
                    "justification.  "
                }
            },
            {
                {"What","must","be","acknowledgment","shall","be"},
                {"16"},
                {
                    "What   must   be",
                    "acknowledgment  ",
                    "shall be        "
                }
            },
            {
                {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"},
                {"20"},
                {
                    "Science  is  what we",
                    "understand      well",
                    "enough to explain to",
                    "a  computer.  Art is",
                    "everything  else  we",
                    "do                  "

                }
            }
            
        };


        ScreenFittingII sv = new ScreenFittingII();

        for (String[][] input : inputs) {
            
            Assert.assertArrayEquals(String.format("words: %s, length= %s", Arrays.toString(input[0]), input[1][0]), 
                    input[2], sv.fullJustify_1(input[0], Integer.parseInt(input[1][0])).toArray() );

            
            Assert.assertArrayEquals(String.format("words: %s, length= %s", Arrays.toString(input[0]), input[1][0]), 
                    input[2], sv.fullJustify_n(input[0], Integer.parseInt(input[1][0])).toArray() );
        }

    }

}
