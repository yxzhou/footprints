package fgafa.sorting;

public class SortLetterByCase {

	/**
	 * Given a string which contains only letters. Sort it by lower case first and upper case second.
	 * 
	 * Example
	 * For "abAcD", a reasonable answer is "acbAD"
	 * 
	 * Note
	 * It's not necessary to keep the original order of lower-case letters and upper case letters.
	 * 
	 * Challenge
	 * Do it in one-pass and in-place.
	 */
	
    /** 
     *@param chars: The letter array you should sort by Case
     *@return: void
     */
    public void sortLetters(char[] chars) {
        //check
        if(null == chars){
            return;
        }
        
        char tmp;
        for(int left = 0, right = chars.length - 1; left < right; ){
            while(left < right && chars[left] > 'Z'){
                left++;
            }
            while(left < right && chars[right] < 'a'){
                right--;
            }
            
            if(left < right){
                tmp = chars[left];
                chars[left] = chars[right];
                chars[right] = tmp;
                
                left++;
                right--;
            }
        }
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
