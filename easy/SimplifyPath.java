package easy;

import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

/*
 * 
 * Given an absolute path for a file (Unix-style), simplify it.
 * 
 * For example,
 * path = "/home/", => "/home"
 * path = "/a/./b/../../c/", => "/c"
 * 
 * Corner Cases:
 * Did you consider the case where path = "/../"?
 * In this case, you should return "/".
 * Another corner case is the path might contain multiple slashes '/' together, such as "/home//foo/".
 * In this case, you should ignore redundant slashes and return "/home/foo".
 * 
 */

public class SimplifyPath {

    public String simplifyPath_n(String path) {
        if (path == null) {
            return "/";
        }

        String[] tokens = path.split("/");
        int l = 0;
        for (int r = 0; r < tokens.length; r++) {
            if (l == r || tokens[r] == null || tokens[r].isEmpty() || tokens[r].equals(".")) {
                continue;
            }

            if (tokens[r].equals("..")) {
                l = Math.max(l - 1, 0);
            } else {
                tokens[l] = tokens[r];
                l++;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < l; i++) {
            result.append("/").append(tokens[i]);
        }

        return result.length() == 0 ? "/" : result.toString();
    }
        
    public String simplifyPath_2(String path) {
        StringBuffer result = new StringBuffer("/");
        if (path == null || path.length() == 0) {
            return result.toString();
        }

        LinkedList<String> deque = new LinkedList<String>();
        StringTokenizer st = new StringTokenizer(path, "/");
        String tmp;
        while (st.hasMoreTokens()) {
            tmp = st.nextToken();
            if (tmp.length() > 0) {
                if (tmp.equals(".")) {
                    continue;
                } else if (tmp.equals("..")) {
                    if (deque.isEmpty()) //return result.toString();
                    {
                        continue;
                    } else {
                        deque.removeLast();
                    }
                } else {
                    deque.addLast(tmp);
                }

            }
        }

        if (!deque.isEmpty()) {
            result = new StringBuffer("");

//      while(!deque.isEmpty()){
//        result.append("/");
//        result.append(deque.pop());
//      }
            for (String s : deque) {
                result.append("/").append(s);
            }
        }

        return result.toString();
    }

    public String simplifyPath_1(String path) {
        final String delimiter = "/";
        if (null == path) {
            return delimiter;
        }

        LinkedList<String> deque = new LinkedList<String>();
        String tmp;
        for (int i = 0, j = 0; i < path.length() - 1;) {
            i = path.indexOf(delimiter, i);
            j = path.indexOf(delimiter, i + 1);

            if (-1 == j) {
                tmp = path.substring(i);
                i = path.length();
            } else {
                tmp = path.substring(i, j);
                i = j;
            }

            if (!tmp.equals("/") && !tmp.equals("/.")) {
                if (tmp.equals("/..")) {
                    if (deque.isEmpty()) {
                        //return "/";
                        continue; // for case, /../ return /
                    } else {
                        deque.removeLast();
                    }
                } else {
                    deque.addLast(tmp);
                }
            }
        }

        StringBuilder ret = new StringBuilder();
        for (String s : deque) {
            //ret.append("/");
            ret.append(s);
        }

        return 0 == ret.length() ? delimiter : ret.toString();
    }

    /**
     * @param path the original path
     * @return the simplified path
     */
    public String simplifyPath_3(String path) {
        final String delimiter = "/";
        // check
        if (null == path) {
            return delimiter;
        }

        LinkedList<String> deque = new LinkedList<String>();
        String s;
        for (int i = 0, j = 0; i < path.length() - 1; j++) {
            i = path.indexOf(delimiter, i);
            j = path.indexOf(delimiter, i + 1);

            if (-1 == j) {
                s = path.substring(i);
                i = path.length();
            } else {
                s = path.substring(i, j);
                i = j;
            }

            // s maybe "/", "/.", "/.." or "/home"
            if (!s.equals("/") && !s.equals("/.")) {
                if (s.equals("/..")) {
                    if (deque.isEmpty()) {
                        // return "/";
                        continue; // for case, /../ return /
                    } else {
                        deque.removeLast();
                    }
                } else {
                    deque.addLast(s);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!deque.isEmpty()) {
            sb.append(deque.pop());
        }

        return 0 == sb.length() ? delimiter : sb.toString();
    }
  


  
  /**
   * @param args
   */
  public static void main(String[] args) {
    String[] input = {null,
                "",
    		".",
    		"/",
    		"/.",
    		"/./",
    		"/../",
    		"/abc/...",
                "/a/./b///../c/../././../d/..//../e/./f/./g/././//.//h///././/..///"
    };

    SimplifyPath sv = new SimplifyPath();
    
    for(int i=0; i<input.length; i++){
    	System.out.println("\n Input: " + input[i]);
    	
        System.out.println("simplifyPath_n:\t " + sv.simplifyPath_n(input[i]));
        
        System.out.println("simplifyPath_1:\t " + sv.simplifyPath_1(input[i]));
        
        System.out.println("simplifyPath_3:\t " + sv.simplifyPath_3(input[i]));
    }

  }

}
