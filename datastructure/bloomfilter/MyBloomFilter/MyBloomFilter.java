package datastructure.bloomfilter.MyBloomFilter;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.BitSet;


public class MyBloomFilter<E> implements Serializable {
    
    private BitSet bitset;
    
    private int bitsetSize;
    private int bitsPerElement;
    private int k; // how many hash functions
    
    public MyBloomFilter(){
        bitset = new BitSet();
    }
    
    public void add(E element){
        int[] hashcodes = hashcode(element.toString(), k);
        
        for(int hashcode : hashcodes){
            bitset.set(hashcode, true);
        }
    }
    
    public boolean contains(E element){
        int[] hashcodes = hashcode(element.toString(), k);
        
        for(int hashcode : hashcodes){
            if(!bitset.get(hashcode)){
                return false;
            }
        }
        
        return true;
    }
    
    static final Charset charset = Charset.forName("UTF-8"); // encoding used for storing hash values as strings
    
    private int[] hashcode(String str, int hashes){
        byte[] bytes = str.getBytes(this.charset);
                    
        //TODO
        return new int[hashes];
    }
    

}
