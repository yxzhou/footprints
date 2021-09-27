package easy;

import java.util.ArrayList;
import java.util.List;

import util.Misc;

public class SummaryRange {

	/**
	 * 
	 * Given a sorted integer array without duplicates, return the summary of
	 * its ranges.
	 * 
	 * For example, given [0,1,2,4,5,7], return ["0->2","4->5","7"].
	 */
    public List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<>();

        if(null == nums || 0 == nums.length){
            return result;
        }
        
        //main
        int start = nums[0];
        int end = start;
        for(int i = 1; i< nums.length; i++){
            if( nums[i] == end + 1){
                //end++;
				end = nums[i];
            }else{
                result.add(getRange(start, end));
                
                start = nums[i];
                end = start;
            }
        }
        
        result.add(getRange(start, end));
        
        return result;
    }
    
    private String getRange(int start, int end){
    	if(start == end){
    		return String.valueOf(start);
    	}else{
    		return start + "->" + end;
    	}
    }
	
	public static void main(String[] args) {
		int[][] input = {
				null, 
				{0},
				{0,1,2},
				{0,1,2,4,5,7}};

		SummaryRange sv = new SummaryRange();
		
		for(int[] nums : input){
			System.out.println(" Input: " + Misc.array2String(nums));
			System.out.println("Output: " );
			Misc.printArrayList(sv.summaryRanges(nums));
		}
	}

}
