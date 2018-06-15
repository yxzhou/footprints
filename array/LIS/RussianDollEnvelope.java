package fgafa.array.LIS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import fgafa.util.Misc;

/**
 * 
 * You have a number of envelopes with widths and heights given as a pair of integers (w, h). 
 * One envelope can fit into another if and only if both the width and height of one envelope is greater than 
 * the width and height of the other envelope.

    What is the maximum number of envelopes can you Russian doll? (put one inside other)
    
    Example:
    Given envelopes = [[5,4],[6,4],[6,7],[2,3]], 
    the maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
 *
 */

public class RussianDollEnvelope {
    
    /*Time Complexity O(n^2) Space O(1)*/
    public int maxEnvelopes_memorycache(int[][] envelopes) {
        if(null == envelopes){
            return 0;
        }
        
        //TODO
        return -1;
        
    }
    
    
    
    /*Time Complexity O(n^2) Space O(n)*/
    public int maxEnvelopes_dp(int[][] envelopes) {
        if(null == envelopes || 0 == envelopes.length){
            return 0;
        }
        
        List<Envelope> all = new ArrayList<>();
        
        for(int[] pair : envelopes){
            all.add( new Envelope(pair[0], pair[1]) );
        }
        
        Collections.sort(all);

        int max = 1;
        int[] dp = new int[all.size()]; //default all are 0
        for(int i = 0; i < all.size(); i++){
            dp[i] = 1;
        }
        
        for(int i = 0; i < all.size(); i++){
            Envelope curr = all.get(i);
            
            for(int j = 0; j < i; j++){
                if(all.get(j).width < curr.width && all.get(j).height < curr.height){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            
            max = Math.max(max, dp[i]);
        }
        
        return max;
    }
    
    /*Time Complexity O(nlogn) Space O(n)*/
    public int maxEnvelopes_Greedy(int[][] envelopes) {
        if(null == envelopes || 0 == envelopes.length){
            return 0;
        }
        
        List<Envelope> all = new ArrayList<>();
        
        for(int[] pair : envelopes){
            all.add( new Envelope(pair[0], pair[1]) );
        }
        
        Collections.sort(all);
        
        //Misc.printList(all);
     
        int max = 1;
        Envelope[] heights = new Envelope[envelopes.length];
        heights[0] = all.get(0);
        int top = 0;
        
        for(int i = 1; i < all.size(); i++){
            Envelope entry = all.get(i);
            
            //if(entry.width > heights[top].width){
                top = binarySearhAndUpdate(heights, top, entry);
                
                //System.out.println(Misc.array2String(heights));
            //}
            
            max = Math.max(max, top + 1);
        }
        
        return max;
    }
    
    private int binarySearhAndUpdate(Envelope[] lis, int top, Envelope target){
        
        int low = 0, high = top;
        
        while (low <= high) {
          int mid =  low + ((high - low)  >> 1);    //(low + high) / 2; 
          
          if (lis[mid].height == target.height){
              return mid;
          } else if (lis[mid].height < target.height){
              low = mid + 1;
          }else{
              high = mid - 1;
          }
        }
        
        //if(low > top){
            lis[low] = target;
//        }else{ // lis[low].height < target.height
//            lis[low].height = target.height;
//        }
        
        return low > top ? top + 1 : top;
    }
    
    class Envelope implements Comparable<Envelope>{
        int width;
        int height;
        
        int index = 0;
        
        Envelope(int width, int length){
            this.width = width;
            this.height = length;
        }

        @Override
        public int compareTo(Envelope other) {
            if(this.width == other.width){
                return Integer.compare(other.height, this.height); // key point
               // return this.height - other.height;             
            }else{
                return Integer.compare(this.width, other.width);
                //return this.width - other.width;                
            }
        }
        
//        @Override
//        public String toString(){
//            return this.width + "-" + this.height + " ";
//        }
    }

    /*Time Complexity O(nlogn) Space O(n)*/
    public int maxEnvelopes_Greedy_x(int[][] envelopes) {
        if (null == envelopes || 0 == envelopes.length) {
            return 0;
        }

        Arrays.sort(envelopes, new Comparator<int[]>(){
            @Override
            public int compare(int[] pair1,
                               int[] pair2) {
                if(pair1[0] == pair2[0]){
                    return pair2[1] - pair1[1];  // key point
                }else{
                    return pair1[0] - pair2[0];
                }
            }
        });

        int max = 0;
        int[] dp = new int[envelopes.length];
        int end = 0;
        for(int[] pair : envelopes){
            int top = Arrays.binarySearch(dp, 0, end, pair[1]);
            if(top < 0){
                top = 0 - top - 1;
            }
            
            max = Math.max(max, top + 1);
            dp[top] = pair[1];
            if(top == end){
                end++;
            }
        }

        return max;
    }

    public static void main(String[] args) {
        RussianDollEnvelope sv = new RussianDollEnvelope();
        
        //int[][] input = {{5,4},{6,4},{6,7},{2,3}};
        int[][][] input = {
                    {},
                    {{1, 1}},
                    {{5,4},{5,9},{5,7},{2,3}},
                    {{5,4},{6,4},{6,7},{2,3}},
                    {{4,5},{4,6},{6,7},{2,3},{1,1}},
                    {{46,89},{50,53},{52,68},{72,45},{77,81}},
                    {{1,3},{3,5},{6,7},{6,8},{8,4},{9,5}},
                    {{30,50},{12,2},{3,4},{12,15}},
                    {{2,100},{3,200},{4,300},{5,500},{5,400},{5,250},{6,370},{6,360},{7,380}}
        };
        
        
        for(int[][] envelopes : input ){
            System.out.println(String.format("Input: %s", Misc.array2String(envelopes)));
            System.out.println(String.format("Output: %d, %d, %d", sv.maxEnvelopes_dp(envelopes), sv.maxEnvelopes_Greedy(envelopes), sv.maxEnvelopes_Greedy_x(envelopes)));
        }
    }

}
