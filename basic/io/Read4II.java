package basic.io;

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
        int read = 0;
        int need = 0;

        while (total < n) {
            if(offset > 0){
                read = buffer.length;
            }else{
                read = read4(buffer);
            }
            
            need = Math.min(n - total, read - offset);
            
            System.arraycopy(buffer /* src */, offset /* srcPos */, buf /* dest */, total /* destPos */, need/* length */);
            total += need;
            offset = (offset + need) % BLOCKSIZE;
            
            if (read < BLOCKSIZE){ // for case, n >= filesize
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


/////////// practice ////////////
class Read4II_practice{
    final static int BLOCK_SIZE = 4;
    char[] block = new char[BLOCK_SIZE];
    int offset = 0;

    public int read(char[] buf, int n){
        int size = 0;

        while( size < n){

            if(offset > 0){
                if(n <= offset){

                }
            }

            int read = read4(block);


        }

        return size;
    }


    // API funciton
    int read4(char[] buf) {
        // this is a fake
        return Math.random() > 0.5 ? 2 : 4;
    }
}