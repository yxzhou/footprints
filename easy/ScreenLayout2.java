package easy;


/**
 * Google Onsite
 * 
 * Given a string "Oh my God they kill kenny", fill it in a screen. 
 *  1 the word can not be broken 
 *  
 * Question, 
 *   1 return the layout.
 *   2 how many times the string can be filled in? 
 *     If the string is pretty long, and the screen is pretty high, how to speed up?
 * 
 */

public class ScreenLayout2 {

    public char[][] layout(int width, int height, String repeatable) {
        if (width <= 0 || height <= 0 || null == repeatable || 0 == repeatable.length()) {
            return new char[0][0];
        }
        
        char[][] result = new char[height][width];

        int length = repeatable.length();
        if(repeatable.charAt(length - 1) != ' '){
            length++;
            repeatable += ' ';
        }
        
        int n = (width / length + 2) * length;
        char[] wordsBase = new char[n * length];
        for( int i = 0; i < n * length; i += length ){
            System.arraycopy(repeatable.toCharArray(), 0, wordsBase, i, length);
        }

        int carry = 0;
        for(int row = 0; row < height; row++){
            System.arraycopy(wordsBase, carry, result[row], 0, width);
              
            carry = (width + carry) % length;
            
            if(carry > 0 && repeatable.charAt(carry - 1) != ' ' && repeatable.charAt(carry) != ' '){
                carry--;
                int cursor = width - 1;
                while(carry >= 0 && repeatable.charAt(carry) != ' ' ){
                    result[row][cursor] = ' ';
                    cursor--;
                    carry--;
                }
                carry++;
            }
            
            if(repeatable.charAt(carry) == ' '){
                carry++;
            }
            
        }

        return result;
    }
    
    public int times(int width, int height, String repeatable) {
        if (width <= 0 || height <= 0 || null == repeatable || 0 == repeatable.length()) {
            return 0;
        }

        int length = repeatable.length();
        if(repeatable.charAt(length - 1) != ' '){
            length++;
            repeatable += ' ';
        }
        
        int n = (width / length + 2) * length;
        char[] wordsBase = new char[n * length];
        for( int i = 0; i < n * length; i += length ){
            System.arraycopy(repeatable.toCharArray(), 0, wordsBase, i, length);
        }

        int count = 0;
        int[] nums = new int[height];
        int carry = 0;
        int row = 0;
        for(  ; row < height; row++){
            count += (width + carry) / length;
            carry = (width + carry) % length;
            
            if(carry > 0 && repeatable.charAt(carry - 1) != ' ' && repeatable.charAt(carry) != ' '){
                carry--;
                while(carry >= 0 && repeatable.charAt(carry) != ' ' ){
                    carry--;
                }
                carry++;
            }
            
            nums[row] = count;
            
            if(carry == 0){
                break;
            }
        }
        
        int result = 0;
        
        if(row < height){
            result += (height / row) * nums[row - 1];
            
            result += nums[height % row - 1];
        }else{
            result += count;
        }

        return result;
    }
    
    public static void main(String[] args) {
        int width = 100; //80
        int height = 60;
        String repeatable = "Oh my God they kill kenny";

        ScreenLayout2 sv = new ScreenLayout2();
        
        System.out.println(sv.times(width, height, repeatable));
        
        char[][] metrix = sv.layout(width, height, repeatable);
        
        print(metrix);
    }

    public static void print(char[][] metrix) {
        if (null == metrix || 0 == metrix.length || 0 == metrix[0].length) {
            System.out.println("Null");
            return;
        }

        for(int j = 0; j < metrix[0].length; j++){
            System.out.print('-');
        }
        System.out.println();
        
        for (int i=0; i< metrix.length; i++) {
            for(int j = 0; j < metrix[0].length; j++){
                System.out.print(metrix[i][j]);
            }
            System.out.println();
        }

        System.out.println();
    }
    
}
