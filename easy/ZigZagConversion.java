package fgafa.easy;

/*
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: 
 * (you may want to display this pattern in a fixed font for better legibility)
 * 
 * Write the code that will take a string and make this conversion given a number of rows:
 * string convert(string text, int nRows);
 *
 * convert("PAYPALISHIRING", 3) 
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * should return "PAHNAPLSIIGYIR".
 * 
 * convert("PAYPALISHIRING", 4) 
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I 
 * should return "PINALSIGYAHRPI".
 * 
 */
public class ZigZagConversion
{

  /*
   * Time O(n), Space O(n)
   */
  public String convert(String s, int nRows) {
    //check
    if(s == null || s.length() == 0 || nRows < 2)
      return s;
    
    int len = s.length();
    int nCols = len / (nRows + nRows - 2) ; 
    nCols = (nCols + 1) * (nRows - 1) ;
    
    int[][] index = new int[nRows][nCols];  // default it's 0
    int i=1;
    int row = 0, col = 0;
    while(i <= len ){
      while(row < nRows && i <= len)
        index[row++][col] = i++;
      
      row --;
      col ++;
      
      while(row > 1 && i <= len)
        index[--row][col++] = i++;
      
      row = 0;
    }   
    
    StringBuffer sb = new StringBuffer();
    int tmp;
    for(row = 0; row < nRows; row ++ ){
      for(col = 0; col <nCols; col++ ){
        tmp = index[row][col];
        if( tmp != 0)  // 0 is the default value
          sb.append(s.charAt(tmp - 1));
      }
      
    }
    
    return sb.toString();
  }
  
  
  /*
   * Case1: s is null, return null;
   * Case2: nRows <=0, return null;
   * Case3: s.length is 0 or 1, or nRows = 1, return s;
   * Case3: s.length <= nRows, return s;
   * Case4: ("PAYPALISHIRING", 3)  or ("PAYPALISHIRING", 4);  
   * 
   * Time O(nRows * s.length), Space O(nRows * s.length)
   * 
   */
  public String convert2(String s, int nRows) {

    if(null == s || nRows < 1)
      return null;
    //if s.length<=nRows, return s
    int length = s.length();
    if(length <= nRows || nRows == 1)
      return s;
    
    /*example: ("PAYPALISHIRING", 3), return "PAHNAPLSIIGYIR" 
     ("PAYPALISHIRING", 4) return "PINALSIGYAHRPI"*/
    int[][] outputIndex = new int[nRows][s.length()]; //default it's 0 
    int index = 1;  // start from 1
    
    int row = 0, col = 0;
    while(index <= length ){
      while(index <= length && row < nRows - 1) //vertical, 0 to nRow - 2
        outputIndex[row++][col] = index++;
      
      while(index <= length && row > 0)      //diagnol, nRow-1 to 1 
        outputIndex[row--][col++] = index++;
      
    }
    
    //output
    StringBuilder sb = new StringBuilder();
    for(int i=0; i<nRows; i++){
      for(int j=0; j<=col; j++){
        if(outputIndex[i][j] > 0) 
          sb.append(s.charAt(outputIndex[i][j] - 1));
      }
    }
      
    return sb.toString();
  }
  
	public String convert_n(String s, int nRows) {

		if (null == s || nRows < 1)
			return null;
	    if(s.length() <= nRows || nRows == 1)
	        return s;

		StringBuilder result = new StringBuilder();
		int step = 2 * nRows - 2;
		int tmp;
		for (int i = 0; i < nRows; i++) {
			for (int j = i; j < s.length(); j += step) {
				result.append(s.charAt(j)); // vertical
				if (i != 0 && i != nRows - 1) {// ignore the first row and last row

					tmp = j + step - i * 2;
					if (tmp < s.length()) {
						result.append(s.charAt(tmp)); // diagnol
					}

				}
			}
		}
		return result.toString();
	}
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    ZigZagConversion sv = new ZigZagConversion();
    
    String[] s = {"PAYPALISHIRING", "PAYPALISHIRING", "PAYPALISHIRING", "PAYPALISHIRING", "PAYPALISHIRING"};
    int[] nRows = {1, 2, 3, 4, 5};
    String[] ans = {"PAYPALISHIRING", "PYAIHRNAPLSIIG", "PAHNAPLSIIGYIR", "PINALSIGYAHRPI", "PHASIYIRPLIGAN"};
                                       
    
    for(int i=0; i< s.length; i++){
      System.out.println("\nInput : \t"+ s[i] + " " + nRows[i]);
      System.out.println("Output: \t"+ sv.convert(s[i], nRows[i]));
      System.out.println("Output: \t"+ sv.convert2(s[i], nRows[i]));
      System.out.println("Output: \t"+ sv.convert_n(s[i], nRows[i]));
      System.out.println("Expectation:\t"+ ans[i]);
    }

  }

}
