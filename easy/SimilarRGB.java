/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy;

/**
 *
 * In the following, every capital letter represents some hexadecimal digit from 0 to f.
 *
 * The red-green-blue color "#AABBCC" can be written as "#ABC" in shorthand. For example, "#15c" is shorthand for the
 * color "#1155cc".
 *
 * Now, say the similarity between two colors "#ABCDEF" and "#UVWXYZ" is abs((AB - UV)^2 + (CD - WX)^2 + (EF - YZ)^2).
 *
 * Given the color "#ABCDEF", return a 7 character color that is most similar to #ABCDEF, and has a shorthand (that is,
 * it can be represented as some "#XYZ")
 * 
 * Notes:
 *   color is a string of length 7. 
 *   color is a valid RGB color: for i > 0, color[i] is a hexadecimal digit from 0 to f 
 *   Any answer which has the same (highest) similarity as the best answer will be accepted. 
 *   All inputs and outputs should use lowercase letters, and the output is 7 characters.
 * 
 * Example 1:
 * Input:  "#09f166"
 * Output: "#11ee66"
 * Explanation: The similarity is -(0x09 - 0x11)^2 -(0xf1 - 0xee)^2 - (0x66 - 0x66)^2 = -64 -9 -0 = -73.
 * This is the highest among any shorthand color.
 * 
 * Example 2:
 * Input:  "#010000"
 * Output: "#000000"
 * Explanation: The similarity is -(0x01 - 0x00)^2 -(0x00 - 0x00)^2 - (0x00 - 0x00)^2 = -1 -0 -0 = -1.
 * This is the highest among any shorthand color.
 * 
 * Thoughts:
 *  0x09 - 011
 *  0x13 - 0x11
 *  0xf1 - 0xee
 *  0xfe - 0xff
 *  0x08 - 0x00  instead of  0x11
 * 
 */
public class SimilarRGB {
    /**
     * @param color: the given color
     * @return: a 7 character color that is most similar to the given color
     */
    public String similarRGB(String color) {
        return "#" 
            + helper(color.charAt(1), color.charAt(2)) 
            + helper(color.charAt(3), color.charAt(4))
            + helper(color.charAt(5), color.charAt(6));
    }

    private String helper(char a, char b){
        int diff =  (a >= 'a' ? a - 'a' + 10 : a - '0') - (b >= 'a' ? b - 'a' + 10 : b - '0');

        char c = a;

        if(Math.abs(diff) > 8 ){
            if(diff < 0){
                c = (char)(a + 1);
            }else {
                c = (char)(a - 1);
            }
        }
        
        return "" + c + c;
    }
}
