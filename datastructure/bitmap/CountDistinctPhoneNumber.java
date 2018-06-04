package fgafa.datastructure.bitmap;

import java.util.BitSet;

public class CountDistinctPhoneNumber {

    public int countDistinctPhoneNumber(int[] phoneNumbers){
        BitSet bitset = new BitSet();
        
        int count = 0;
        int hashcode;
        for(int phoneNumber : phoneNumbers){
            
            hashcode = hashcode(phoneNumber);
            if(!bitset.get(hashcode)){
                bitset.set(hashcode, true);
                count++;
            }
        }
        
        return count;
    }
    
    private int hashcode(int phoneNumber) {
        //return (key.hashCode() & 0x7fffffff) % m;
        return phoneNumber;
    }
}
