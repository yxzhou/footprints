package fgafa.basic.io;

/**
 * Read N Characters Given Read4
 * 
 * The API: int read4(char *buf) reads 4 characters at a time from a file.
    The return value is the actual number of characters read. For example, it returns 3 if there is only 3 characters left in the file.
    By using the read4 API, implement the function int read(char *buf, int n) that reads n characters from the file.
    
    Note:
    The read function will only be called once for each test case.
    
    
 *   case 1:  n < filesize
 *   case 2:  n >= filesize
 */

public class Read4 {

   
    /**
     * @param buf Destination buffer
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    public int read(char[] buf, int n) {
        final int BLOCKSIZE = 4;
        char[] buffer = new char[BLOCKSIZE];
        int total = 0;

        while (total < n) {
            int curr = read4(buffer);
            
            int bytes = Math.min(n - total, curr);
            
            System.arraycopy(buffer /* src */, 0 /* srcPos */, buf /* dest */, total /* destPos */, bytes /* length */);
            total += bytes;
            
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
