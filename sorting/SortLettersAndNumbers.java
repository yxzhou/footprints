package fgafa.sorting;

import java.util.Arrays;
import java.util.Comparator;

import fgafa.util.Misc;

public class SortLettersAndNumbers {

	/**
	 * Given a string which contains letters and numbers. Sort it.
	 * 
	 * Example
	 *   a2b < a10b, because 2 < 10
	 *   a2b < a2d, because b < d
	 *   a2b < ad, because 2 < d,  number < letter
	 *   a02b < a2c, because 02 == 2 and b < c
	 * 
	 * Challenge
	 *   .
	 */
	
    /** 
     *@param chars: The letter array you should sort by Case
     *@return: void
     */
    public void sortLettersAndNumbers(String[] strs) {
        // check
        if (null == strs || 0 == strs.length) {
            return;
        }

        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String x, String y) {
                int startX = 0;
                int startY = 0;
                int endX;
                int endY;

                int diff;
                while (startX < x.length() && startY < y.length()) {
                    char a = x.charAt(startX);
                    char b = y.charAt(startY);

                    if (isDigit(a) && isDigit(b)) {
                        endX = getNumberEnd(x, startX);
                        endY = getNumberEnd(y, startY);

                        diff = compareNumber(x, startX, endX, y, startY, endY);
                        if (0 == diff) {
                            startX = endX;
                            startY = endY;
                        } else {
                            return diff;
                        }
                    } else {
                        diff = Character.compare(a, b);
                        if (0 == diff) {
                            startX++;
                            startY++;
                        } else {
                            return diff;
                        }
                    }
                }

                if(startX == x.length() && startY == y.length()){
                    return 0;
                }else{
                    return startX == x.length() ? -1 : 1;  
                }
            }
        });

    }
    
    private static int compareNumber(String x, int startX, int endX, String y, int startY, int endY){
        
        while(startX < endX && x.charAt(startX) == '0'){
            startX++;
        }
        
        while(startY < endY && y.charAt(startY) == '0'){
            startY++;
        }
        
        int lengthX = endX - startX;
        int lengthY = endY - startY;
        if(lengthX == lengthY){
            while(startX < endX && startY < endY){
                int diff = Character.compare(x.charAt(startX), y.charAt(startY));
                if (0 == diff) {
                    startX++;
                    startY++;
                } else {
                    return diff;
                }
            }

            return 0;
        }else{
            return lengthX - lengthY > 0 ? 1 : -1;
        }
    }
    
    private static int getNumberEnd(String str, int start){
        int end = start;
        while(end < str.length() && isDigit(str.charAt(end))){
            end++;
        }
        
        return end;
    }
    
    private static boolean isDigit(char c){
        return c >= '0' && c <= '9';
    }
    
	public static void main(String[] args) {
	    SortLettersAndNumbers sv = new SortLettersAndNumbers();
	    
		String[] input = {
		            "a2b", "a10b", "a2d", "ad", "a02b", "a2c"
		            //"a2b", "a02b"
		};

		
		System.out.println(String.format(" Input: %s", Misc.array2String(input)));
		sv.sortLettersAndNumbers(input);
		System.out.println(String.format("Output: %s", Misc.array2String(input)));
	}

}
