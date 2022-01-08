/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.io;

import org.junit.Assert;

/**
 *
 * @author yuanxi
 */
public class ReaderN {
    
    Reader4 reader4Imp;
    
    /**
     * @param buf destination buffer
     * @param n maximum number of characters to read
     * @return the number of characters read
     */
    char[] buffer = new char[4]; 
    int left = 0; // offset
    int right = 0;

    public int read(char[] buf, int n) {
        
        int total = 0;
        int length;
        
        if(right == -1){ //the stream is end  
            return 0;
        }

        while(total < n){
            if(left == right){ // no cache in buffer
                left = 0;
                right = reader4Imp.read4(buffer);
            }
            
            if(right == 0){ //the stream is end  
                right = -1;
                break;
            }

            length = Math.min(right - left, n - total); //valid length

            System.arraycopy(buffer, left, buf, total, length);
            total += length;
            left += length;
        }

        return total;
    }
    
    public static void main(String[] args){
        
        String[][] inputs = {
            {"ab", "1,2"},
            {"filetestbuffer", "6,5,4,3,2,1,10"},
            {"abcdef", "1,5"},
        };
        
        String[][][] expects = {
            {
                {"1", "a"},
                {"1", "b"}
            },
            {
                {"6", "filete"},
                {"5", "stbuf"},
                {"3", "fer"},
                {"0", ""},
                {"0", ""},
                {"0", ""},
                {"0", ""}
            },
            {
                {"1", "a"},
                {"5", "bcdef"}
            },
        };
        
        int n;
        int x;
        String[] tokens;
        for(int i = 0; i < inputs.length; i++){
            ReaderN sv = new ReaderN();
            sv.reader4Imp = new Reader4Imp(inputs[i][0]);
                            
            tokens = inputs[i][1].split(",");
            for(int j = 0; j < tokens.length; j++){
                n = Integer.parseInt(tokens[j]);
                char[] buf = new char[n];
                x = sv.read(buf, n);
                
                Assert.assertEquals( Integer.parseInt(expects[i][j][0]), x );
                Assert.assertEquals(expects[i][j][1], String.valueOf(buf, 0, x));
                
            }
        }                
                
    }
}
