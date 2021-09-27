package dailyCoding;

/**
 *
 * Run-length encoding is a fast and simple method of encoding strings. The basic idea is to represent repeated successive characters as a single count and character.
 * For example, the string "AAAABBBCCDAA" would be encoded as "4A3B2C1D2A".
 * Implement run-length encoding and decoding.
 * You can assume the string to be encoded have no digits and consists solely of alphabetic characters.
 * You can assume the string to be decoded is valid.
 *
 *  Tags: amazon
 */

public class Encoding {

    public String encoding(String input){
        StringBuilder result = new StringBuilder();

        char curr = input.charAt(0);
        int count = 0;
        char[] str = input.toCharArray();
        for(int i = 0; i < str.length;i++){
            if(str[i] == curr){
                count++;
            }else{
                result.append(count);
                result.append(curr);
                count = 1;
                curr = str[i];
            }
        }

        result.append(count);
        result.append(curr);
        return result.toString();
    }

    public static void main(String[] args){
        Encoding sv = new Encoding();

        String[][] inputs = {
                {"AAAABBBCCDAA", "4A3B2C1D2A"}
        };

        for(int i = 0; i < inputs.length; i++){
            System.out.println(String.format("%s -> %s vs %s  ", inputs[i][0], sv.encoding(inputs[i][0]), inputs[i][1]));
        }

    }

}
