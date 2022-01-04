package todo.goo;

import java.util.Stack;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/643
 * Suppose we represent our file system by a string in the following manner:

 The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:

 dir
    subdir1
    subdir2
       file.ext

 The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.

 The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:

 dir
    subdir1
       file1.ext
       subsubdir1
    subdir2
       subsubdir2
       file2.ext

 * The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext and an empty
 * second-level sub-directory subsubdir1. subdir2 contains a second-level sub-directory subsubdir2 containing a file
 * file2.ext.
 *
 * We are interested in finding the longest (number of characters) absolute path to a file within our file system. For
 * example, in the second example above, the longest absolute path is "dir/subdir2/subsubdir2/file2.ext", and its length
 * is 32 (not including the double quotes).
 *
 * Given a string representing the file system in the above format, return the length of the longest absolute path to a
 * file in the abstracted file system. If there is no file in the system, return 0.
 *
 * Note:
 *   The name of a file contains at least a period and an extension.
 *   The name of a directory or sub-directory will not contain a period.
 *
 *
 * Tags: google, stack
 *
 */

public class LongestPath {

    /**
     * @param input: an abstract file system
     * @return: return the length of the longest absolute path to file
     */
    public int lengthLongestPath(String input) {
        if(null == input || input.isEmpty()){
            return 0;
        }
        
        //String[] tokens = input.split("\\r?\\n|\\r");  split("\\R") for java8+
        String[] tokens = input.split("\n");

        int max = 0;
        int sum = 0;

        Stack<Integer> stack = new Stack<>();
        
        int depth;
        int width;
        //char tab = '\t';
        for(String token : tokens){
            depth = token.lastIndexOf("\t") + 1;
            while(stack.size() > depth){
                sum -= stack.pop();
            }

            //token = token.replaceAll("[\\n\\t ]", ""); // remove \n and \t and space
            width = token.length() - depth;
      
            if(token.contains(".")){ //file
                max = Math.max(max, sum + width);
            }else{ //directory
                sum += width + 1;
                stack.add(width + 1);
            }
        }

        return max;
    }

    /**
     * wrong to case "dir\n file.txt"
     * 
     * @param input
     * @return 
     */
    public int longestPath(String input){
        int longestLength = 0;

        if(null == input || input.isEmpty()){
            return longestLength;
        }

        Stack<Integer> stack = new Stack<>();
        String[] tokens = input.split("\t");

        int currLength = 0;
        int level = 1;
        for(String token : tokens){
            if(token.isEmpty()){
                level++;
            }else{
                while(stack.size() > level){
                    currLength -= stack.pop();
                }

                //token = token.replaceAll("[\\n\\t ]", ""); // remove \n and \t and space
                int len = token.length() - (token.contains("\n") ? 1 : 0);

                if(token.contains(".")){ //file
                    longestLength = Math.max(longestLength, currLength + len + level);
                }else{
                    currLength += len;

                    stack.push(len);
                }

                level = 1;
            }
        }

        return longestLength;
    }    
    

    public static void  main(String[] args){
        String[] input = {
                "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext",
                "dir\n\tsubdir1\n\tsubdir2\n\t\t  file.ext",
                "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext\n\t\t\tfile33.ext",
                "dir\n file.txt"
        };

        LongestPath sv = new LongestPath();
        for(String s : input){
            System.out.println(Misc.array2String(s.split("\t")));
            System.out.println(sv.longestPath(s) + "\t" + sv.lengthLongestPath(s) + "\n");
        }

    }
}
