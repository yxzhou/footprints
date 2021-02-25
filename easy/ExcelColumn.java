package fgafa.easy;

/*
 * In an Excel sheet, the column title is A, B, ---, Z, AA, AB, --- 
 * 
 * For example:
 * 
 *     1 -> A
 *     2 -> B
 *     3 -> C
 *     ...
 *     26 -> Z
 *     27 -> AA
 *     28 -> AB 
 *     
 *  Q1, Given a positive integer, return its corresponding column title
 *  base 10 -> base 26 
 *  
 *  Q2, Given a column title, return its corresponding positive integer
 *  base 26 -> base 10
 *  
 */

public class ExcelColumn {


    public int titleToNumber(String s) {
    	if(null == s){
    		return 0;
    	}
    	
        int ret = 0;
        
        try{
	        for(char c : s.toCharArray()){
	        	ret = ret * 26 + (c - 64);  //'A' is 65
	        }
        }catch(NumberFormatException nfe){
        	ret = Integer.MAX_VALUE;
        }
        
        return ret;
    }
    

	public String convertToTitle(int n) {
//		if(n < 1 ){
//			return null;
//		}
		
		StringBuilder ret = new StringBuilder();
		int remain = 0;

		while(n > 0){
			remain = n % 26;
			n = n / 26;
			
			if( 0 == remain){
				remain = 26;
				n--;
			}
			
			ret.append((char)(remain + 64)); //'A' is 65
		}

		return ret.reverse().toString();
	}
    

	public String convertToTitle2(int n) {
	    final int base = 'A';
		StringBuffer sb = new StringBuffer();
		while (n > 0) {
			--n;
			sb.append((char) (n % 26 + base));
			n /= 26;
		}
		return sb.reverse().toString();
	}

	public static void main(String[] args) {
		ExcelColumn sv = new ExcelColumn();
		
		int[] input = {
				0, //null,
				1, //"A",
				26, //"Z",
				27, //"AA",
				52, //"AZ",
				53, //"BA",
				78, //"BZ",
				1405, //"BBA",
				18278, //"ZZZ",
				2147483647 //Integer.MAX_VALUE
				
		};
		
		for(int n : input){
			String s = sv.convertToTitle2(n);
			
			System.out.println(String.format("input: %d \t numberToTitle: %s \t titleToNumber: %d", n, s, sv.titleToNumber(s)));
		}

	}

}
