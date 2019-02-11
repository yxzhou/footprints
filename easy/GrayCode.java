package fgafa.easy;

import java.util.ArrayList;
import java.util.List;

import fgafa.util.Misc;

/**
 * The gray code is a binary numeral system where two successive values differ in only one bit.<br>
 * Given a non-negative integer n representing the total number of bits in the code, 
 * print the sequence of gray code. A gray code sequence must begin with 0.<br>
 * 
 * For example, given n = 2, return [0,1,3,2]. Its gray code sequence is: <br>
 * 00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2
 * 
 * Note:
 * For a given n, a gray code sequence is not uniquely defined.
 * 
 * For example, [0,2,3,1] is also a valid gray code sequence according to the above definition.
 * 
 * For now, the judge is able to judge based on one instance of gray code sequence. Sorry about that.
 * 
 *
 */

public class GrayCode
{

  /*
   * n = 1, f(1) = (0,1).
   * n = 2, f(2) = f(1) * f(1) =
   *   00, 01
   *   11, 10
   * n = 3, f(3) = f(1) * f(2) = f(1) * (f(1) * f(1))
   * n = 4, f(4) = f(2) * f(2) = (f(1) * f(1)) * (f(1) * f(1))   
   * 
   * Time O(2^n + 2^(n/2) + --- + 1) ~ O(2^(n+1))
   */
  public List<Integer> grayCode(int n) {
    List<Integer> result = new ArrayList<Integer>();
    
    //check
    if(n < 1){
      result.add(0);
      return result;
    }
    
    List<Integer> factor = new ArrayList<Integer>();
    factor.add(0);
    factor.add(1);
    
    for(;n > 0; n >>= 1){
      if((n & 1) == 1)
        result = multiple(result, factor);
      
      factor = multiple(factor, factor);
    }
      
    
    return result;
  }
  
  private List<Integer> multiple(List<Integer> r1, List<Integer> r2){
    List<Integer> result = new ArrayList<Integer>();
    
    //check
    if(r1 == null && r2 == null)
      return result;
    else if(r1 == null || r1.size() == 0)
      return r2;
    else if(r2 == null || r2.size() == 0)
      return r1;
    
    int end2 = r2.size() -1;
    int tmp1, n2 = Integer.toBinaryString(r2.get(end2)).length();
    for(int i=0; i< r1.size(); i++){
      tmp1 = r1.get(i) << n2;
                
      if(i%2 == 0){
        for(int j=0; j<= end2; j++)
          result.add(tmp1 + r2.get(j));
        
      }else{
        for(int j=end2; j>=0; j--)
          result.add(tmp1 + r2.get(j));
        
      }
    }
    
    return result;
  }
  
  /*
   * n = 1,  0,  1
   * n = 2,  00, 01, 11, 10,
   * n = 3,  000, 001, 011, 010, 110, 111, 101, 100
   *
   * Time O(2^n)   
   */
	public List<Integer> grayCode2(int n) {
		List<Integer> result = new ArrayList<>();
		result.add(0);
		for (int i = 0; i < n; i++) {
			int highestBit = 1 << i;
			int len = result.size();
			for (int j = len - 1;j >= 0; j--) {
				result.add(highestBit + result.get(j));
			}
		}
		return result;
	}
  
    public List<Integer> grayCode22(int n) {
        List<Integer> result = new ArrayList<Integer>();
        if(n < 0){
            return result;
        }
        
        result.add(0);
        result.add(1);
        for(int i = 1; i < n ; i++){
            int high = 1 << i;
            for(int j = result.size() - 1; j >= 0; j--){
                result.add( high + result.get(j) );
            }
        }
        
        return result;
    }
	
  /*
   * n = 2, 
   *   i=0, 00^00 = 00 (0)
   *     1, 01^00 = 01 (1)
   *     2, 10^01 = 11 (3)
   *     3, 11^01 = 10 (2)
   * n = 3,
   *   i=0, 000^000 = 000 (0)
   *     1, 001^000 = 001 (1)
   *     2, 010^001 = 011 (3)
   *     3, 011^001 = 010 (2)
   *     4, 100^010 = 110 (6)
   *     5, 101^010 = 111 (7)
   *     6, 110^011 = 101 (5)
   *     7, 111^011 = 100 (4)  
   * ---
   *     
   * Time O(2^n)    
   */
  public List<Integer> grayCodeX(int n) {
    List<Integer> ret = new ArrayList<Integer>();
    int count = 0x01 << n;
    for(int i = 0 ; i < count; ++i) {
        ret.add(i ^ (i>>1));
    }
    return ret;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    
    /* basic testing 
    int n = 22;
    System.out.println("" + Integer.toBinaryString(n).length());
    System.out.println("" + Integer.bitCount(n) );
    */
    
    /* Graycode testing */
    GrayCode sv = new GrayCode();
    
//    ArrayList<Integer> factor = new ArrayList<Integer>();
//    factor.add(0);
//    factor.add(1);

    System.out.println("-1-");
    for(int i=2; i<5; i++){     
      System.out.println("-i- " + i);
      Misc.printArrayList_Integer(sv.grayCode(i));      
    }

    System.out.println("-2-");
    for(int i=2; i<5; i++){
      System.out.println("-i- " + i);
      Misc.printArrayList_Integer(sv.grayCode2(i));
    }
    
    System.out.println("-3-");
    for(int i=2; i<5; i++){
      System.out.println("-i- " + i);
      Misc.printArrayList_Integer(sv.grayCodeX(i));
    }
  }

}
