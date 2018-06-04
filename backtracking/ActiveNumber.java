package fgafa.backtracking;

/**
 * 
 * Additive number is a string whose digits can form additive sequence.

    A valid additive sequence should contain at least three numbers. Except for the first two numbers, each subsequent number in the sequence must be the sum of the preceding two.
    
    For example:
    "112358" is an additive number because the digits can form an additive sequence: 1, 1, 2, 3, 5, 8.
    
    1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
    "199100199" is also an additive number, the additive sequence is: 1, 99, 100, 199.
    1 + 99 = 100, 99 + 100 = 199
    
    Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.
    
    Given a string containing only digits '0'-'9', write a function to determine if it's an additive number.
    
    Follow up:
    How would you handle overflow for very large input integers?
 *
 */

public class ActiveNumber {
    
    
    public boolean isAdditiveNumber(String num) {
        //check
        if(null == num || num.length() < 3){
            return false;
        }
        
        char[] chars = num.toCharArray();
        for(int i = 0, limit = num.length() / 2; i <= limit; i++ ){
            for(int j = i+1; j <= limit + 1; j++ ){
                if(isAdditiveNumber(chars, i, j)){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean isAdditiveNumber(char[] num, int i, int j){
        
        int s1 = 0;
        int e1 = i;
        int s2 = e1 + 1;
        int e2 = j;
        int s3 = e2 + 1;
        int e3 = s3 + Math.max(e1 - s1, e2 - s2);
                    
        do{
            if(isAdditiveNumber(num, s1, e1, s2, e2, s3, e3)){
                s1=s2;
                e1=e2;
                s2=s3;
                e2=e3;
            }else if(isAdditiveNumber(num, s1, e1, s2, e2, s3, e3  + 1)){
                s1=s2;
                e1=e2;
                s2=s3;
                e2=e3 + 1;
            }else{
                return false;
            }
            
            s3 = e2+1;
            e3 = s3 + Math.max(e1 - s1, e2 - s2);
        }while(e3 < num.length);
        
        return e2 == num.length - 1 ? true : false;
    }
    
    private boolean isAdditiveNumber(char[] num, int s1, int e1, int s2, int e2, int s3, int e3){
        if(e3 >= num.length || s3 > e3 || num[s3] == '0'){
            return false;
        }
        
        int carry = 0;
        int diff = 0;
        while(e1>=s1 && e2>=s2){
            diff = num[e1] - '0' + num[e2] + carry - num[e3];
            
            if(diff != 10 && diff != 0){
                return false;
            }
            
            carry = diff / 10;
            
            e1--;
            e2--;
            e3--;
        }
        
        while(e1>=s1){
            diff = num[e1] + carry - num[e3];
            
            if(diff != 10 && diff != 0){
                return false;
            }
            
            carry = diff / 10;
            
            e1--;
            e3--;
        }
        
        while(e2>=s2){
            diff = num[e2] + carry - num[e3];
            
            if(diff != 10 && diff != 0){
                return false;
            }
            
            carry = diff / 10;
            
            e2--;
            e3--;
        }
        
        if(e3 == s3){
            diff = carry - (num[e3] - '0');
            
            if(diff != 0){
                return false;
            }
            
            carry = 0;
            
            e3--;
        }
        
        return e3 == s3 - 1 && carry == 0? true : false;
    }

    public static void main(String[] args) {
        ActiveNumber sv = new ActiveNumber();

        String[] input = {
                    //"112358",
                    //"199100199",
                    //"111",
                    "1203"
        };
        
        for(String num : input){
            System.out.println(String.format("%s, isAdditiveNumber: %b", num, sv.isAdditiveNumber(num)));
        }
        
    }

}
