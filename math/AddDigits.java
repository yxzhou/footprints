package math;

/**
 * 
 * Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

    For example:
    
    Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.
    
    Follow up:
    Could you do it without any loop/recursion in O(1) runtime?
 *
 */

public class AddDigits {
    
    public int addDigits(int num) {
        int tmp = 0;
        while(num > 9){
            tmp = num;
            num = 0;
            
            while(tmp > 0){
                num += tmp % 10;
                tmp /= 10;
            }
        }
        
        return num;
    }
    
    //Digit Root, https://en.wikipedia.org/wiki/Digital_root
    public int addDigits_x(int num) {
        return num - ((num - 1) / 9) * 9;
    }
    
    public static void main(String[] args){
        AddDigits sv = new AddDigits();
        
        for(int i = 0; i <= 111; i++){
            System.out.println(String.format(" Input: %d,  Output: %d,  %d",  i, sv.addDigits(i), sv.addDigits_x(i)));
        }
    }
}
