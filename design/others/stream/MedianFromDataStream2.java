package design.others.stream;

import design.others.stream.MedianFromDataStream;
import junit.framework.Assert;
import util.Misc;

/**
 * 
 * Another solution for MedianFromDataStream: segment tree
 * 
 */
public class MedianFromDataStream2 {

    class Node{
        int value;
        int count;
        boolean isLeaf;
        
        Node left;
        Node right;
    }
    
    Node header = new Node();
    
    public void add(int num){
        Node curr = header;
        
        long l = Integer.MIN_VALUE;
        long r = Integer.MAX_VALUE;
        long m;  //middle
        while(l < r){
           curr.count++;
           
           m = l + (r - l) / 2;
//System.out.println(String.format("l=%d, \t r=%d, \t, m=%d", l, r, m));
           if(num <= m){
               if(curr.left == null){
                   curr.left = new Node();
               }
               
               curr = curr.left;
               r = m;
           }else{
               if(curr.right == null){
                   curr.right = new Node();
               }
               
               curr = curr.right;
               l = m + 1;
           }
        }

//System.out.println(String.format("l=%d, \t r=%d, \t, --num=%d", l, r, num));
        curr.value = num;
        curr.isLeaf = true;
        curr.count++;
       
    }
    
    public int getMedian(){
        Node curr = header;
        
        int half = (curr.count + 1) / 2;
        while(!curr.isLeaf){
            
            if(curr.left == null || curr.left.count < half){
                half = half - ( null == curr.left? 0 : curr.left.count );
                
                curr = curr.right;
            }else{
                curr = curr.left;
            }
        }
        
        return curr.value;
    }
    
            

    public static void main(String[] args) {
        /* test */
        int l = Integer.MIN_VALUE;
        int r = Integer.MAX_VALUE;
        int m = l + (r - l) / 2;
        
        System.out.println( (l + (r - l) / 2) + "\t" + (r + l) / 2);
        
        long l2 = Integer.MIN_VALUE;
        long r2 = Integer.MAX_VALUE;
        long m2 = l2 + (r2 - l2) / 2;
        
        System.out.println( (l + (r - l) / 2) + "\t" + (r + l) / 2);
        

        int[][] inputs = {
            {1, 2, 3, 4, 5},
            {4, 5, 1, 3, 2, 6, 0},
            {2, 20, 100},
            {5,-10,4}
        };
        
        int[][] expects = {
            {1,1,2,2,3},
            {4,4,4,3,3,3,3},
            {2,2,20},
            {5, -10, 4}
        };

        int[] nums;
        int num;
        for (int i = 0; i < inputs.length; i++) {
            nums = inputs[i];
            
            System.out.println(String.format("Input: %s", Misc.array2String(nums)));
            
            MedianFromDataStream sv1 = new MedianFromDataStream();
            MedianFromDataStream2 sv2 = new MedianFromDataStream2();
            
            
            for(int j = 0; j < nums.length; j++){
                num = nums[j];
                
                sv1.add(num);
                Assert.assertEquals(expects[i][j] , sv1.getMedian());
                
                sv2.add(num);
                //System.out.println(String.format("add:%d, getMedian:%d", num, sv2.getMedian()));
                Assert.assertEquals(expects[i][j] , sv2.getMedian());
            }
            
        }
    }
}
