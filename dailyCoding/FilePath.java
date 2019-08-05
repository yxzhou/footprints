package fgafa.dailyCoding;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Given an absolute pathname that may have . or .. as part of it, return the shortest standardized path.
 * For example, given "/usr/bin/../bin/./scripts/../", return "/usr/bin/".
 *
 */

public class FilePath {

    public String filePath(String path){

        if(path == null){
            return "";
        }

        char[] chars;
        int length = path.length();
        if(path.charAt(length - 1) != '/'){
            chars = new char[length + 1];

            System.arraycopy(path.toCharArray(), 0, chars, 0, length);
            chars[length] = '/';
        }else{
            chars = path.toCharArray();
        }

        Deque<StringBuilder> deque = new LinkedList<>();
        deque.addLast(new StringBuilder());
        StringBuilder curr;
        for(char c : chars) {
            curr = deque.peekLast();

            if (c == '/') {
                if(curr.length() == 0){
                    continue;
                }

                if (curr.toString().equals("..")) {
                    if (deque.size() < 2) {
                        throw new IllegalArgumentException(" ");
                    }

                    deque.pollLast();
                    deque.pollLast();

                } else if (curr.toString().equals(".")) {
                    deque.pollLast();
                }

                deque.addLast(new StringBuilder());

            }else{
                curr.append(c);
            }
        }

        curr = new StringBuilder();
        StringBuilder top;
        while(!deque.isEmpty()){
            top = deque.pollFirst();

            if(top.length() > 0){
                curr.append("/");
                curr.append(top);
            }
        }

        if(path.charAt(length - 1) == '/' || curr.length() == 0){
            curr.append("/");
        }

        return curr.toString();
    }

    @Test
    public void test(){

        Assert.assertEquals("/usr/bin/", filePath("/usr/bin/../bin/./scripts/../"));

        Assert.assertEquals("/usr/bin", filePath("/usr/bin/../bin/./scripts/.."));

        Assert.assertEquals("/", filePath("."));

        Assert.assertEquals("/", filePath("/."));

        String pre = null;

        String curr = "";

        System.out.println(curr.equals(pre));

    }


}
