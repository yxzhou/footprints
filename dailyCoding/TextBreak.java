package fgafa.dailyCoding;

import java.util.ArrayList;
import java.util.List;

import fgafa.util.Misc;

/**
 *
 * Given a string s and an integer k, break up the string into multiple texts such that each text has a length of k or less. You must break it up so that words don't break across lines. If there's no way to break the text up, then return null.
 *
 * You can assume that there are no spaces at the ends of the string and that there is exactly one space between each word.
 *
 * For example, given the string "the quick brown fox jumps over the lazy dog" and k = 10, you should return: ["the quick", "brown fox", "jumps over", "the lazy", "dog"]. No string in the list has a length of more than 10.
 *
 * Tag: amazon
 */
public class TextBreak {

    public List<String> textBreak_wrong(String text, int k){
        if(null == text || text.trim().isEmpty()){
            return null;
        }

        List<String> result = new ArrayList<>();

        String[] words = text.split(" ");

        for(int i = 0, j = 0; i < words.length; ){
            int size = 0;
            while( size <= k && j < words.length){
                size += words[j].length();
                size += 1;
                j++;
            }

            if(size > k){
                j--;
            }

            if( i == j ){
                return null;
            }

            StringBuilder sb = new StringBuilder();
            while(i < j){
                sb.append(words[i]);
                sb.append(" ");
                i++;
            }

            result.add(sb.substring(0, sb.length() - 1));
        }

        return result;
    }

    public List<String> textBreak_2(String text, int k){
        if(null == text || text.trim().isEmpty()){
            return null;
        }

        List<String> result = new ArrayList<>();

        int length = text.length();
        int left = 0;
        for(int lastSpace = 0, right = 0, size = 0; right < length; ){
            if(size <= k + 1){
                if(text.charAt(right) == ' '){
                    lastSpace = right;
                }

                size++;
                right++;
            }else {
                if(left == lastSpace){
                    return null;
                }

                result.add(text.substring(left, lastSpace));
                left = lastSpace + 1;
                size = right - lastSpace;
            }
        }

        if(left < length){
            result.add(text.substring(left, length));
        }

        return result;
    }

    public List<String> textBreak(String text, int k){
        if(null == text || text.trim().isEmpty()){
            return null;
        }

        List<String> result = new ArrayList<>();

        int length = text.length();
        int left = 0;
        while(left < length){
            int right = left + k; //when k = 10, left = 0, check the 10th instead of 9th

            if(right < length){
                while(text.charAt(right) != ' ' && right > left){
                    right--;
                }

                if(right == left){
                    return null;
                }
            }else{
                right = length;
            }

            result.add(text.substring(left, right));
            left = right + 1;
        }

        return result;
    }


    public static void main(String[] args){
        String[] cases = {
            "the quick brown fox jumps over the lazy dog"
        };

        TextBreak sv = new TextBreak();

        Misc.printList(sv.textBreak(cases[0], 10));
        Misc.printList(sv.textBreak_2(cases[0], 10));
    }
}
