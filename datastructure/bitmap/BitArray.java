package datastructure.bitmap;

/**
 * Implement a bit array.
 *
 * A bit array is a space efficient array that holds a value of 1 or 0 at each index.
 *
 * init(size): initialize the array with size
 * set(i, val): updates index at i with val where val is either 1 or 0.
 * get(i): gets the value at index i.
 *
 * Tags: Amazon
 */

public class BitArray {
    int size;
    long[] words;

    public void init(int size){
        if(size < 0){
            throw new NegativeArraySizeException("size < 0: " + size);
        }

        this.size = size;
        words = new long[(size >> 6) + 1];
    }

    public void set(int index, boolean value){
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException("index: " + index);
        }

        int i = index >> 6;
        int j = index % 6;
        words[i] = setBit(words[i], j, value);
    }

    public boolean get(int index){
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException("index: " + index);
        }

        int i = index >> 6;
        int j = index % 6;
        return getBit(words[i], j);
    }

    /*
     * get the bit in the index position of n
     *
     * @return: false means 0; true means 1
     *
     */
    private static boolean getBit(long n, int index) {
        return (n & (1 << index)) > 0;

    }
    /*
     * set the bit in the index position of n
     *
     * @boolean b, true means to set 1, false means to set 0
     * @return: the new int
     *
     */
    private static long setBit(long n, int index, boolean b) {
        if(b) {
            return (n | (1 << index));
        }else {
            return (n & ~(1 << index));
        }
    }

}
