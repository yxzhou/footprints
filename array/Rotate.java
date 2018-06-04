package fgafa.array;

import fgafa.util.Misc;

/*
 * Rotate a one-dimensional array of n elements to the right by k steps. 
 * For instance, with n=7 and k=3, 
 * the array {a, b, c, d, e, f, g} is rotated to {e, f, g, a, b, c, d}
 * 
 */
public class Rotate
{
  
/*
 *   {a, b, c, d, e, f, g}   n=6, k=3
 * => {g, f, e, d, c, b, a}  // after reverse(str, 0, n-1);
 * => {e, f, g, d, e, f, g}  // after reverse(str, 0, k-1);
 * => {e, f, g, a, b, c, d}  // after reverse(str, k, n-1);
 * 
 */

    public void rotateString_n(char[] str, int offset) {
        //check
        if(null == str || 0 == str.length || offset < 0){
            return;
        }
        
        offset %= str.length;
        
        swapSubString(str, 0, str.length - 1);
        swapSubString(str, 0, offset - 1);
        swapSubString(str, offset, str.length - 1);
    }
    
    private void swapSubString(char[] str, int i, int j){

        while(i<j){
            swapChars(str, i, j);
            i++;
            j--;
        }
    }
    
    private void swapChars(char[] str, int i, int j){
        char c = str[i];
        str[i] = str[j];
        str[j] = c;
    }
	/**
	 * 
	 * Rotate an array of n elements to the right by k steps.
	 * 
	 * For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated
	 * to [5,6,7,1,2,3,4].
	 * 
	 * Note: Try to come up as many solutions as you can, there are at least 3
	 * different ways to solve this problem.
	 */
	public void rotate(int[] nums, int k) {
		if (null == nums || k < 1)
			return;

		k = k % nums.length;

		reverse(nums, 0, nums.length - 1);
		reverse(nums, 0, k - 1);
		reverse(nums, k, nums.length - 1);
	}

	private void reverse(int[] nums, int start, int end) {
		for (int tmp = 0; start < end; start++, end--) {
			tmp = nums[start];
			nums[start] = nums[end];
			nums[end] = tmp;
		}
	}
	
  public boolean isRotated(String s1, String s2){
    return (s1+s2).contains(s2);
  }
  

  
  public boolean isRotated2(String s1, String s2) {
    int len = s1.length();
    if (s2.length() != len)
      return false;
    for (int i = 0; i < len; ++i) {
      if (s1.substring(i) + s1.substring(0, i) == s2)
        return true;
    }
    return false;
  }


  
  /**
   * @param args
   */
  public static void main(String[] args) {
    
    System.out.println("====start====");
    
    String s = "abcdefg";
    int k = 3;
        
    char[] str = s.toCharArray();
    
    System.out.println("The input String:\t\t"+s);
    
    Rotate service = new Rotate();
    service.rotateString_n(str, k);

    System.out.println("Rotate to the right by "+k+ " steps:\t" + Misc.array2String(str));
    
    System.out.println("====end====");
  }

}
