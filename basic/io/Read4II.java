package fgafa.basic.io;

/**
 * 
 * Read N Characters Given Read4 II - Call multiple times @LeetCode

    The API: int read4(char *buf) reads 4 characters at a time from a file.
    The return value is the actual number of characters read. For example, it returns 3 if there is only 3 characters left in the file.
    By using the read4 API, implement the function int read(char *buf, int n) that reads n characters from the file.
    
    Note:
    The read function may be called multiple times.
 *
 */

public class Read4II {

    final int BLOCKSIZE = 4;
    char[] buffer = new char[BLOCKSIZE];
    
    private int offset = 0;
    
    public Read4II(){
        
    }
    

    /**
     * The read function may be called multiple times.
     * The previous one we can't use it for multiple times since read4 actually read more and break continuity.
     * This one will solve that problem. read the buffer used last time.(through offset)
     */
    public int read(char[] buf, int n) {

        int total = 0;

        while (total < n) {
            int curr = 0;
            if(offset > 0){
                curr = buffer.length;
            }else{
                curr = read4(buffer);
            }
            
            int bytes = Math.min(n - total, curr - offset);
            
            System.arraycopy(buffer /* src */, offset /* srcPos */, buf /* dest */, total /* destPos */, bytes /* length */);
            total += bytes;
            offset = (offset + bytes) % BLOCKSIZE;
            
            if (curr < BLOCKSIZE){ // for case, n >= filesize
                break;
            }
        }
        
        return total;
    }
    
    // API funciton
    int read4(char[] buf) {
        // this is a fake
        return Math.random() > 0.5 ? 2 : 4;
    }
    
}
