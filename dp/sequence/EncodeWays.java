package dp.sequence;

/**
 * 
 * A message containing letters from A-Z is being encoded to numbers using the following mapping:
 * 
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * Given an encoded message containing digits, determine the total number of ways to decode it.
 * 
 * For example,
 * Given encoded message "AB", it could be encoded as "12".
 *
 */
public class EncodeWays {
     
    
    public String encode(String chars){
        if(null == chars || 0 == chars.length()){
            return "";
        }
        
        StringBuilder result = new StringBuilder();
        for(char c : chars.toCharArray()){
            if(c > 64 && c < 91){
                result.append(encode(c));                
            }
        }
            
        return result.toString();
    }
    
    private int encode(char c){
        return c - 64; // 'A'  is 65
    }
    
    public static void main(String[] args){
        
        EncodeWays sv = new EncodeWays();
        
        System.out.println(sv.encode("ABZC"));
    }
    
}
