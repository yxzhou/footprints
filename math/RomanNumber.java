package fgafa.math;

/**
 * Roman number
 * 
 *   1 - I 			11 - XI			
 *   2 - II			12 - XII
 *   3 - III		13 - XIII
 *   4 - IV			14 - XIV
 *   5 - V			15 - XV
 *   6 - VI  		16 - XVI
 *   7 - VII		17 - XVII
 *   8 - VIII		18 - XVIII
 *   9 - IX			19 - XIX
 *   10 - X			20 - XX
 *
 *	 30 - XXX							
 *   40 - XL  						90 - XC			100 - C
 *   41 - XLI		51 - LI			91 - XCI		500 - D
 *   42 - XLII		52 - LII		92 - XCII		1000 - M
 *   43 - XLIII
 *   44 - XLIV
 *   45 - XLV
 *   46 - XLVI
 *   47 - XLVII
 *   48 - XLVIII
 *   49 - XLIX
 *   50 - L
 *   
 */
public class RomanNumber {

	/**
	 * Given an integer, convert it to a roman numeral.
	 * 
	 * Input is guaranteed to be within the range from 1 to 3999.
	 */
	public String intToRoman(int num) {
		StringBuilder str = new StringBuilder();
		String[] symbol = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X","IX", "V", "IV", "I" };
		int[] value = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		for (int i = 0; num != 0; i++) {
			while (num >= value[i]) {
				num -= value[i];
				str.append( symbol[i] );
			}
		}
		return str.toString();
	}

    /**
     * @param n The integer
     * @return Roman representation
     */
    public String intToRoman_n(int n) {
        //check
        if(n < 1 || n > 3999){
            return "";
        }
        
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romains = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X","IX", "V", "IV", "I" };
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < nums.length && n > 0; i++){
            while(n >= nums[i]){
                n -= nums[i];
                result.append(romains[i]);
            }
        }

        return result.toString();
    }
	
	/**
	 * Given a roman numeral, convert it to an integer.
	 * 
	 * Input is guaranteed to be within the range from 1 to 3999.
	 */
	public int romanToInt(String s) {
		int ret = 0;
		if(null == s || 0 == s.length()){
			return ret;
		}
		
		ret += romanToInt(s.charAt(0));
		for(int i=1; i<s.length(); i++){
			ret += romanToInt(s.charAt(i));
			if(romanToInt(s.charAt(i)) > romanToInt(s.charAt(i-1))){
				ret -= (romanToInt(s.charAt(i-1)) << 1);
			}
		}
		
		return ret;
	}
	
	
    /**
     * @param s Roman representation
     * @return an integer
     */
    public int romanToInt_n(String s) {
        //check
        if(null == s || 0 == s.length()){
            return 0;
        }
        
        int result = 0;
        int pre = 0;
        int curr;
        for(char c : s.toCharArray()){
            curr = romanToInt(c);
            result += curr;
            
            if(pre < curr){
                result -= (pre << 1);
            }
            
            pre = curr;
        }
        
        return result;
    }
    
	private int romanToInt(char c) {
		switch (c) {
		case 'I':
			return 1;
		case 'V':
			return 5;
		case 'X':
			return 10;
		case 'L':
			return 50;
		case 'C':
			return 100;
		case 'D':
			return 500;
		case 'M':
			return 1000;
		default:
			return 0;
		}
	}
	
	
	public static void main(String[] args) {
		RomanNumber sv = new RomanNumber();
		
		String[] romans = {
				"XCIX",   //99
				"XCVIII", //98
				"XCVII",  //97 
				"XCVI",   //96
				"LXXXIX", //89
				"LXXXIV", //84 
				"LIX", //59
				"XXXIX",   //39
				"I"        //1   
				
		};

		for(String roman : romans){
			System.out.println( sv.romanToInt(roman) + " - " + sv.romanToInt_n(roman) + " - " + roman);
		}
		
		System.out.println();
		
		int[] ints = {99, 98, 97, 96, 89, 84, 59, 39, 1};
		for(int num : ints){
			System.out.println( num + " - " + sv.intToRoman(num));			
		}
	}

}
