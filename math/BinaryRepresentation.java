package math;

/**
 * 
 * Given a (decimal - e.g. 3.72) number that is passed in as a string, return the binary representation that is passed in as a string. If the fractional part of the number can not be represented accurately in binary with at most 32 characters, return ERROR.

Example
For n = "3.72", return "ERROR".

For n = "3.5", return "11.1".
 *
 */

public class BinaryRepresentation {

    /**
     *@param n: Given a decimal number that is passed in as a string
     *@return: A string
     */
    final String ERROR = "ERROR";
    public String binaryRepresentation(String n) {
        //check
        if(null == n || 0 == n.length() || n.equals("0")){
            return n;
        }
        
        if(-1 == n.indexOf('.')){
            return parseInteger(n);
        }
        
        String[] strs = n.split("\\.");
        
        if(strs.length > 2 ){
            return ERROR;
        }
        String decimal = parseDecimal(strs[1]);
        
        if(decimal.equals(ERROR)){
            return ERROR;
        }else if(0 == decimal.length() || decimal.equals("0")){
            return parseInteger(strs[0]);
        }else{
            return parseInteger(strs[0]) + '.' + decimal;
        }
        
    }
    
    
    private String parseInteger(String s){
        if(0 == s.length() || s.equals("0")){
            return s;
        }
        
        int num = Integer.parseInt(s);
        StringBuilder result = new StringBuilder();
        
        while(num > 0){
            result.append(num % 2);
            num /= 2;
        }
        
//        int mask = 1;
//        while(num > 0){
//            if((num & 1) == 1){
//                result.append("1");
//            }else{
//                result.append("0");
//            }
//            
//            num >>= 1;
//        }
        
        return result.reverse().toString();
    }
    
    private String parseDecimal(String s){
        if(0 == s.length() || s.equals("0")){
            return s;
        }
        
        double num = Double.parseDouble("0." + s);
        
        StringBuilder result = new StringBuilder();
        while(num > 0){
            if(result.length() >= 32){
                return this.ERROR;
            }
            
            num *= 2;
            if(num >= 1){
                result.append("1");
                num -= 1;
            }else{
                result.append("0");
            }
        }
        
        return result.toString();
    }
    
}
