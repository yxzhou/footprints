package fgafa.dailyCoding;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

/**
 *
 * Using a read7() method that returns 7 characters from a file, implement readN(n) which reads n characters.

 For example, given a file with the content “Hello world”, three read7() returns “Hello w”, “orld” and then “”.
 *
 * Tags: Ms
 *
 */

public class ReadText {

    /**
     *  case 1, request n is more than the text.  call read7() until it can NOT read anything.
     *  case 2, request n is smaller than the text.
     *
     *
     */
    public String read(int n){
        StringBuilder result = new StringBuilder();

        while(n > 0){
            String read = read7();

            if(read.length() == 0){ //case 1
                break;
            }

            int min = Math.min(n, read.length());
            result.append(read.substring(0, min));
            n -= min;
        }

        return result.toString();
    }

    /* provided: start*/
    String input;
    int index;

    ReadText(String input){
        this.input = input;
        this.index = 0;
    }

    private String read7(){
        int from = index;
        this.index = Math.max(from + 7, input.length());

        return input.substring(from, this.index);
    }
    /* provided: end*/
}
