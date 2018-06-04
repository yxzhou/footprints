package fgafa.easy;


/**
 * 
 * The count-and-say sequence is the sequence of integers beginning as follows:
 * 1, 11, 21, 1211, 111221, ...
 * 
 * 1 is read off as "one 1" or 11.
 * 11 is read off as "two 1s" or 21.
 * 21 is read off as "one 2, then one 1" or 1211.
 * Given an integer n, generate the nth sequence.
 * 
 * Note: The sequence of integers will be represented as a string.
 * 
 *
 */
public class CountAndSay
{


  public String countAndSay(int n) {
    String result = "";
    
    //check
    if(n<1) return result;
    
    StringBuilder sb = new StringBuilder();
    sb.append(1); 
    sb.append('$');  // terminate character 
    
    for(int i=1; i<n; i++){
      
      sb = countAndSay(sb);
      
      //System.out.println("===" + sb);
    }
    
    return sb.substring(0, sb.length() - 1);
  }
  
  /* count from listOdd and store the say in listEven */
  private StringBuilder countAndSay(StringBuilder sb){
    
    char curr, pre = sb.charAt(0);
    int count = 1;   //default it's 1.
    StringBuilder result = new StringBuilder();
    
    for(int i=1; i< sb.length(); i++){
      curr = sb.charAt(i);
      
      if(curr != pre){
        result.append(count);
        result.append(pre);
        
        pre = curr;
        count = 1;
      }else{
        count ++;
      }
    }
    
    return result.append("$");
  }
  
  
  public String countAndSay_x(int n) {
	  //check
	  if( n < 1){
		  return "";
	  }
		  
      StringBuilder curr = new StringBuilder();
      curr.append(1);
      
      char first;
      for(int i=1; i<n; i++){
    	  StringBuilder next = new StringBuilder();

    	  for(int j=0, k=1; j<curr.length(); j=j+k){
    		  first = curr.charAt(j);
    		  k = 1;
    		  while( k < curr.length() - j && first == curr.charAt(k+j)){
    			  k++;
    		  }
    		  
    		  next.append(k);
    		  next.append(first);
    	  }
    	  
    	  curr = next;
      }
      
      return curr.toString();
  }
  
  
  /**
   * @param n the nth
   * @return the nth sequence
   */
  public String countAndSay_n(int n) {
      //check
      if(n < 1){
          return "";
      }
      
      StringBuilder curr = new StringBuilder("1");
      StringBuilder next = new StringBuilder();
      int count;
      for( ; n > 1; n--){
          next = new StringBuilder(); 
          
          for(int i = 0, end = curr.length(); i < end; i += count){
              count = 1;
              while(i + count < end && curr.charAt(i) == curr.charAt(i + count)){
            	  count++;
              }
              
              next.append(count);
              next.append(curr.charAt(i));
          }
          
          curr = next;
      }
      
      return curr.toString();
  }
  
  public String countAndSay_n2(int n) {
      //check
      if(n < 1){
          return "";
      }
      
      StringBuilder curr = new StringBuilder("1");
      StringBuilder next = new StringBuilder();
      int j;
      for( ; n > 1; n--){
          next = new StringBuilder(); 
          
          for(int i = 0, end = curr.length(); i < end; i = j){
              
              for(j = i + 1; j < end && curr.charAt(i) == curr.charAt(j); j++);
              
              next.append(j - i);
              next.append(curr.charAt(i));
          }
          
          curr = next;
      }
      
      return curr.toString();
  }
  
  public static void main(String[] args) {
    CountAndSay sv = new CountAndSay();
    
    int[] n = {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    String[] ans = {"", "", "1", "11", "21", "1211", "111221", "312211", "13112221"
        ,"1113213211", "31131211131221","13211311123113112211"};
    
    for(int i=0; i<n.length; i++){
      System.out.println("Count and Say nth: " + n[i]);
      
      System.out.println("Result:\t" + sv.countAndSay(n[i]) );
      System.out.println("Result:\t" + sv.countAndSay_x(n[i]) );
      System.out.println("Result:\t" + sv.countAndSay_n(n[i]) );
      System.out.println("Result:\t" + sv.countAndSay_n2(n[i]) );
      
      System.out.println("Expect:\t" + ans[i] );
      
    }

  }

}
