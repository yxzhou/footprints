/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.io;

/**
 *
 * @author yuanxi
 */
public class Reader4Imp implements Reader4 {
    char[] all;
    int state = 0; //offset
    
    Reader4Imp(String str){
        all = str.toCharArray();
        state = 0;
    }

    @Override
    public int read4(char[] buf) {
        int need = Math.min(buf.length, all.length - state);
        
        System.arraycopy(all, state, buf, 0, need);
        
        state += need;
        
        return need;
    }
}
