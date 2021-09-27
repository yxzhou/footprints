package dp.sequence;

import util.Misc;

/**
 * There are N children standing in a line. Each child is assigned a rating value.
 *
 *You are giving candies to these children subjected to the following requirements:
 *
 *Each child must have at least one candy.
 *Children with a higher rating get more candies than their neighbors.
 *What is the minimum candies you must give?
 *
 */
public class Candies {
    public int candy(int[] ratings) throws Exception {
    	//check
    	if(null == ratings || 1 > ratings.length)
    		return 0;
    	if(1 == ratings.length)
    		return 1;
    		
        int[] candies = new int[ratings.length]; //default all are 0
        
        //find out the valley bottom and set the candy as 1
        if(ratings[0] <= ratings[1]){
        	candies[0] = 1;
        }
        if(ratings[ratings.length - 1] <= ratings[ratings.length - 2]){
        	candies[ratings.length - 1] = 1;
        }
        for(int i = 1; i< ratings.length-1; i++){
        	if((ratings[i - 1] > ratings[i] && ratings[i] < ratings[i + 1]) 
        			|| (ratings[i - 1] == ratings[i] && ratings[i] <= ratings[i + 1]) 
        			|| (ratings[i - 1] > ratings[i] && ratings[i] == ratings[i + 1])){
        		candies[i] = 1;
        	}
        }
        
        //water flooding
        for(int i = 0; i< ratings.length; i++){
        	if(candies[i] == 1){
        		//to left
        		for(int j=i-1, k=2; j>=0; j--, k++){
        			if(ratings[j] > ratings[j+1])
        				candies[j] = Math.max(candies[j], k);
        			else
        				break;
        		}
        		//to right
        		for(int j=i+1, k=2; j<ratings.length; j++, k++){
        			if(ratings[j] > ratings[j-1])
        				candies[j] = Math.max(candies[j], k);
        			else
        				break;

        		}
        	}
        }
        
        //get the sum
        int sum = 0;
        for(int candy : candies){
        	sum += candy;
        }
        return sum;
    }
	
    
    /**
     * @param ratings Children's ratings
     * @return the minimum candies you must give
     */
    public int candy_n(int[] ratings) {
        int result = 0;
        
        //check
        if(null == ratings || 0 == ratings.length){
            return result;
        }
        
        int[] nums = new int[ratings.length]; //nums[i] is the candies at position i
        nums[0] = 1;   //**
        
        //from left to right
        for(int i = 1; i < ratings.length; i++){
            if(ratings[i] > ratings[i - 1]){
                nums[i] = nums[i - 1] + 1;
            }else{
                nums[i] = 1;
            }
        }
        
        //from right to left, 
        for(int i = ratings.length - 2; i >=0; i--){
            if(ratings[i] > ratings[i + 1]){
                nums[i] = Math.max(nums[i], nums[i + 1] + 1);
            }
            
            result += nums[i];
        }
        
        return result + nums[ratings.length - 1];  //**
    }
    
	public static void main(String[] args) {
		int[][] ratings = {
				null,
				{1},
				{1,2},
				{1,2,3},
				{1,2,3,4},
				{2,1},
				{3,2,1},
				{4,3,2,1},
				{0,1,2,3,2,1},
				{0,1,2,3,3,2,1},
				{0,1,2,3,3,2,2,1},
				{3,1,2,3,4,2,1,1},
		};

		Candies sv = new Candies();
		
		try{
			for(int[] rate : ratings){
				System.out.println("Input: " + Misc.array2String(rate));
				System.out.println("Output: " + sv.candy(rate));
				System.out.println("Output: " + sv.candy_n(rate));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
