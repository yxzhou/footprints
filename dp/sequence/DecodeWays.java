package dp.sequence;


/**
 * Leetcode #91
 *
 * A message containing letters from A-Z is being encoded to numbers using the following mapping:
 * 
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * Given an encoded message containing digits, determine the total number of ways to decode it.
 * 
 * For example,
 * Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).
 * 
 * The number of ways decoding "12" is 2.
 * 
 *
 */
public class DecodeWays
{
  
  public int numDecodings(String s){
        if(s == null || s.isEmpty() || s.charAt(0) == '0' ){
            return 0;
        }

        int f1 = 0;
        int f2 = 1;
        int tmp;

        int pre = 0;
        int curr;

        for(char c : s.toCharArray()){
            curr = c - '0';

            if(curr < 0 || curr > 9 || (curr == 0 && ( pre == 0 || pre > 2) )){
                return 0;
            }

            if(curr == 0){
                tmp = f1;
            }else if( pre == 0 || pre > 2 || (pre == 2 && curr > 6)){
                tmp = f2;
            }else{
                tmp = f1 + f2;
            }

            f1 = f2;
            f2 = tmp;

            pre = curr;
        }

        return f2;
    }
  
  public static void main(String[] args) {

    DecodeWays sv = new DecodeWays();
        
    String s = "012345678";
    System.out.println(s.substring(3));
    
    
    String[] str = {"", "-1", "ab", "01"
        , "1", "10", "11", "21", "28", "1211", "111221"
        , "110", "230", "301", "1090", "10034", "12034", "1200", "1203", "12834", "834", "1280", "12801"
        ,"6065812287883668764831544958683283296479682877898293612168136334983851946827579555449329483852397155"
        ,"4757562545844617494555774581341211511296816786586787755257741178599337186486723247528324612117156948"
        ,"1159314227869675749153973158896359637455398771636981264557866779635662185364345272665484523344457179"};
    int[] n = {0, 0, 0, 0
        , 1, 1, 2, 2, 1, 5, 13
        , 1, 0, 0, 0, 0, 1, 0, 1, 2, 1, 0, 0
        , 0, 589824, 6912};
    
    for(int i=0; i<str.length; i++){
      System.out.println("\nDecodeWayss: " + str[i]);
      
      System.out.println("Result:\t" + sv.numDecodings(str[i]) );
      
    
      System.out.println("Expect:\t" + n[i] );
      
    }

  }

}
