package fgafa.math;

/*
 * 
 * You are given two linked lists representing two non-negative numbers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
 *   
 * Example
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * 
 *  Given 7->1->6 + 5->9->2. That is, 617 + 295.
 *  Return 2->1->9. That is 912.
 * 
 *  Given 3->1->5 and 5->9->2, return 8->0->8.
 */
/*
 * case 1: l1 and l2 both are null, return null;
 * case 2: l1 and l2, either is null, return the other
 * case 3: create all new node, no change on the old ones.
 *     (2 4 3) + (5 6 4) => (7 0 8)
 *         (1) + (2) => (3)
 *         (6) + (7) => (3 1)
 *         (0) + (0 1) => (0 1)
 *         (1) + (9 9) => (0 0 1)
 *         (9) + (9 9) => (8 0 1)
 *         
 */
public class Add2Number
{
  

    /**
     * @param l1: the first list
     * @param l2: the second list
     * @return: the sum list of l1 and l2 
     */
    public ListNode addTwoNumbers_n(ListNode l1, ListNode l2) {
        //check
        if(null == l1){
            return l2;
        }else if(null == l2){
            return l1;
        }
        
        int result = 0;
        int carry = 0;
        ListNode head = new ListNode(result);
        ListNode curr = head;
        for( ; null != l1 && null != l2; l1 = l1.next, l2 = l2.next){
            result = l1.val + l2.val + carry;
            if(result > 9){
                result -= 10;
                carry = 1;
            }else{
                carry = 0;
            }
            
            curr.next = new ListNode(result);
            curr = curr.next;
        }
    
        if(null != l2){
            l1 = l2;
        }
    
        for( ; null != l1 ; l1 = l1.next){
            result = l1.val + carry;
            if(result > 9){
                result -= 10;
                carry = 1;
            }else{
                carry = 0;
            }
            
            curr.next = new ListNode(result);
            curr = curr.next;
        }
        
        if(carry > 0){
        	curr.next = new ListNode(carry);
        }
        
        return head.next;
    }
	
  public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
     //check
     if(l1 == null) return l2;
     if(l2 == null) return l1;
     
     //
     ListNode header = new ListNode(-1), currNode = header;
     int carry = 0, currSum = 0;
     while(l1 != null && l2 != null){
       currSum = l1.val + l2.val + carry;
       carry = 0;
       
       if(currSum > 9){
         carry = 1;
         currSum -= 10;
       }
         
       currNode.next = new ListNode(currSum);
       
       currNode = currNode.next;
       l1 = l1.next;
       l2 = l2.next;
     }
     
     ListNode restNode = (l1 != null)? l1 : l2;          
     while(restNode != null){
       currSum = restNode.val + carry;
       carry = 0;
       
       if(currSum > 9){
         carry = 1;
         currSum -= 10;
       }
         
       currNode.next = new ListNode(currSum);
       
       currNode = currNode.next;
       restNode = restNode.next;
     }
     
     if(carry > 0)
       currNode.next = new ListNode(carry);
     
     return header.next;
  }

  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

    int result = 0;
    int carry = 0;
    
    ListNode currN = null;
    ListNode preNode = null;   
    ListNode head = null;
        
    while(l1 != null && l2 != null){
      result  = carry + l1.val + l2.val ;
      carry = 0;     
//      if (result > 9) {
        while (result > 9) {
          carry++;
          result -= 10;
        }
//      }else
//        carry = 0;
      
      currN = new ListNode(result);
      if(preNode== null)
        head = currN;
      else
        preNode.next = currN;
      
      preNode = currN;
      
      l1 = l1.next;
      l2 = l2.next;
    }
    
    ListNode restList = null;
    if(l1 != null)
      restList = l1;
    else if(l2 != null)
      restList = l2;
    
    while(restList != null ){
      result = carry + restList.val ;
      carry = 0;
//      if (result > 9) {
        while (result > 9) {
          carry++;
          result -= 10;
        }
//      }else
//        carry = 0;
      
      currN = new ListNode(result);
      if(preNode== null)
        head = currN;
      else
        preNode.next = currN;
      
      preNode = currN;
      
      restList = restList.next;
    }
    
    if(carry != 0){
      currN = new ListNode(carry);
      preNode.next = currN;
    }
    
    return head;
 }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Add2Number sv = new Add2Number();
    
    int[] a = {243, 578};
    int[] b = {564, 632};
    
    for(int i=0; i< a.length; i++){
      ListNode aLN = sv.initNodeList(a[i]);
      ListNode bLN = sv.initNodeList(b[i]);
      
      System.out.print("\nInput a: "+ a[i] + "\t");
      sv.printNodeList(aLN);
      System.out.print("\nInput b: "+ b[i] + "\t");
      sv.printNodeList(bLN);
      
      System.out.print("\nOutput : \t" );
      ListNode result = sv.addTwoNumbers(aLN, bLN);
      sv.printNodeList(result);
      System.out.print("\nOutput : \t" );
      result = sv.addTwoNumbers2(aLN, bLN);
      sv.printNodeList(result);      
    }

  }

  
  class ListNode{
    
    public ListNode(int x){
      this(x, null);
    }
    
    public ListNode(int x, ListNode node){
      val = x;
      next = node;
    }
    
    int val;  // value
    ListNode next;
       
    public String toString(){
      return String.valueOf(this.val);
    }
  }
  
  /*
   * int 243 ==> list nodes 3->4->2->null 
   * 
   */
  private ListNode initNodeList(int num){
    ListNode preN = null;
    ListNode curN = null;
    ListNode head = null;
    
    int digit = 0;
    int factor = 10;
    while(num > 0){
      digit = num % factor;
      num = num / factor;
            
      curN = this.new ListNode(digit);
      
      if(preN == null)
        head = curN;
      else
        preN.next = curN;
        
      preN = curN;
      
    }
    
    return head;
  }
  
  private void printNodeList(ListNode head){
    while(head != null){
      System.out.print(head.val);
      System.out.print("->");
      head = head.next;
    }
  }
  
}
