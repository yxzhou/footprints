package math;

public class IntegerHelper {

    /**
     * similar with Integer.parseInt(String s, int radix)
     * toDecimal("101011", 2) -> 43
     * 
     */
    public static int toDecimal(String s, int radix){
        //check null ==s , radix < MIN_RADIX, radix > MAX_RADIX

        int result = 0;
        
        for(char c : s.toCharArray()){
            result += result * radix + (c - '0') ;
        }
        
        return result;
    
    }

    /**
     * toDecimal(101011, 2) -> 43
     */
    public static int toDecimal(int s, int radix){
        //check s < 0 , radix < MIN_RADIX, radix > MAX_RADIX

        int result = 0;
        int factor = 1;
        while(s > 0){
            result += s % 10 * factor;
            
            s /= 10;
            factor *= radix;
        }
        
        return result;
    }

    /**
     * toBinary(43, 2)->101011 
     * toOctal(43, 2)->
     */
    public static int toBinary(int s, int radix){
        //check s < 0, radix < MIN_RADIX, radix > MAX_RADIX

        int result = 0;
        int factor = 1;
        while(s > 0){
            result += s % radix * factor;
            
            s /= radix;
            factor *= 10;
        }
        
        return result;
    }
    
    /**
     * fromDecimal(43, 2)->101011 
     */
    public static String toHex(int s, int radix){
        //check s < 0, radix < MIN_RADIX, radix > MAX_RADIX

        String chars = "0123456789ABCDEFGHIJ";
        String result = new String();
        
        while(s > 0){
            result =  chars.charAt(s % radix) + result;
            
            s /= radix;
        }
        
        return result;
    }
}
