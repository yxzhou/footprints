package fgafa.todo;

/**
 * 
 * Now you are given a string S, which represents a software license key which we would like to format. The string S is composed of alphanumerical characters and dashes. The dashes split the alphanumerical characters within the string into groups. (i.e. if there are m dashes, the string is split into m+1 groups). The dashes in the given string are possibly misplaced.

We want each group of characters to be of length K (except for possibly the first group, which could be shorter, but still must contain at least one character). To satisfy this requirement, we will reinsert dashes. Additionally, all the lower case letters in the string must be converted to upper case.

So, you are given a non-empty string S, representing a license key to format, and an integer K. And you need to return the license key formatted according to the description above.

Example 1:
Input: S = "2-4A0r7-4k", K = 4

Output: "24A0-R74K"

Explanation: The string S has been split into two parts, each part has 4 characters.
Example 2:
Input: S = "2-4A0r7-4k", K = 3

Output: "24-A0R-74K"

Explanation: The string S has been split into three parts, each part has 3 characters except the first part as it could be shorter as said above.
Note:
The length of string S will not exceed 12,000, and K is a positive integer.
String S consists only of alphanumerical characters (a-z and/or A-Z and/or 0-9) and dashes(-).
String S is non-empty.
 *
 */

public class LicenseKey {
    public String licenseKeyFormatting(String S, int K) {
        int length = S.length();
        int end = length + length / K + 1;
        int start = end - 1;
        char[] buffer = new char[end];
        
        char c;
        int count = K;
        for(int i = length - 1; i > -1; i--){
            c = S.charAt(i);
            if(c == '-'){
                continue;
            }
            
            if(c >= 'a'){//'0' is 48, 'A' is 65, 'a' is 97
                buffer[start--] = (char)('A' + c - 'a'); 
            }else{
                buffer[start--] = c;
            }
            
            count--;
            if(count == 0){
                count = K;
                buffer[start--] = '-';
            }
        }
        
        start++;
        if(start >= end){
            return new String();
        }
        
        if(buffer[start] == '-'){
            start++;
        }
        
        char[] result = new char[end - start];
        System.arraycopy(buffer, start, result, 0, end - start);
        return new String(result);
    }
    

    public static void main(String[] args) {
        
        String[] Ss = {"2-4A0r7-4k", "2-4A0r7-4k", "---", "---", "---"};
        int[] Ks = {4, 3, 3, 2, 1};
        
        String[] expects = {"24A0-R74K", "24-A0R-74K", "", "", ""};

        LicenseKey sv = new LicenseKey();
        
        for(int i = 0; i < Ss.length; i++){
            System.out.println(String.format("S=%s, K=%d, Output:%s == %s", Ss[i], Ks[i], sv.licenseKeyFormatting(Ss[i], Ks[i]), expects[i]));
        }

    }

}
