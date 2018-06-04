package fgafa.easy;

/**
 * 
 * Compare two strings A and B, determine whether A contains all of the characters in B.
	
	The characters in string A and B are all Upper Case letters.
	
	Example
	For A = "ABCD", B = "ACD", return true.
	
	For A = "ABCD", B = "AABC", return false.
	
	Note
	The characters of B in A are not necessary continuous or ordered.
 *
 */

public class CompareString {

    /**
     * @param A : A string includes Upper Case letters
     * @param B : A string includes Upper Case letter
     * @return :  if string A contains all of the characters in B return true else return false
     */
    public boolean compareStrings(String A, String B) {
        //check
        if(null == A){
            return null == B;
        }else if(null == B){
            return true;
        }
        
        int[] counts = new int[26]; // default all are 0
        int i;
        for(char c : A.toCharArray()){
            i = c - 'A';
            counts[i]++;
        }
        
        for(char c : B.toCharArray()){
            i = c - 'A';
            if(0 == counts[i]){
                return false;
            }
            counts[i]--;
        }
        
        return true;
    }
	
}
