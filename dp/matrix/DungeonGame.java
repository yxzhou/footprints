package fgafa.dp.matrix;

import fgafa.util.Misc;

/*
 * The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon. 
 * The dungeon consists of m x N rooms laid out in a 2D grid.
 * Our valiant knight (K) was initially positioned in the top-left room and must fight his way through the dungeon to rescue the princess.
 * 
 *  The knight has an initial health point represented by a positive integer. 
 *  If at any point his health point drops to 0 or below, he dies immediately.
 * 
 *  Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering these rooms; 
 *  other rooms are either empty (0's) or contain magic orbs that increase the knight's health (positive integers).
 * 
 *  In order to reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.
 * 
 * 
 *  Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.
 * 
 *  For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows the optimal path RIGHT-> RIGHT -> DOWN -> DOWN.
 * 
 *  -2 (K)	-3	3
 *  -5	-10	1
 *  10	30	-5 (P)
 * 
 *  Notes:
 * 
 *  The knight's health has no upper bound.
 *  Any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.
 */
public class DungeonGame {

	/*
	 * DP
	 * define dp[i][j] as the minimum health when the knight enter the cell[i][j], and the dungeon consists of m rows and n cols. so
	 *   1) the dp[m-1]][n-1] = 1 - dungeon[m-1][n-1]
	 *   2) dp[i][j] = 
	 *        dp[i+1][j] - dungeon[i][j]  when j = n-1;
	 *        dp[i][j+1] - dungeon[i][j]  when i = m-1;
	 *        min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j]  else
	 */
    public int calculateMinimumHP(int[][] dungeon) {
    	//check
    	if(null == dungeon)
    		return 1;
    	
    	//dungeon MUST consists of m x N rooms laid out in a 2D grid
    	int maxRow = dungeon.length -1;
    	int maxCol = dungeon[0].length -1;
    	
        int[][] dp = new int[maxRow+1][maxCol+1];
        dp[maxRow][maxCol] = getMinHealth(1, dungeon[maxRow][maxCol]); // finally the min health is 1
        for(int i = maxRow - 1; i>=0; i--)
        	dp[i][maxCol] = getMinHealth(dp[i+1][maxCol], dungeon[i][maxCol]);
        for(int j = maxCol -1; j>=0; j--)
        	dp[maxRow][j] = getMinHealth(dp[maxRow][j+1], dungeon[maxRow][j]);
        
        for(int i = maxRow - 1; i>=0; i--){
        	for(int j=maxCol - 1; j>=0; j--){
        		dp[i][j] = getMinHealth(Math.min(dp[i+1][j], dp[i][j+1]), dungeon[i][j]);
        	}
        }
        
        return dp[0][0];
    }
    
    private int getMinHealth(int after, int change){
    	//after must bigger than 1
    	
    	if(change < after)
    		return after - change;
    	else // change >= after
    		return 1;
    }
    
	public static void main(String[] args) {
		int[][][] input = {
				null,
				{{10}},
				{{-10}},
				{{1,1},
				 {1,1}},
				{{2,3},
				 {4,5}},
				{{-2,-3},
				 {-5,-10}},
				{{-2, -3, 3},
				 {-5, -10, 1},
				 {10, 30, -5}}
		};

		int[] results = {
				1,
				1,
				11,
				1,
				1,
				16,
				7
		};
		
		System.out.println("===start===");
		DungeonGame sv = new DungeonGame();

		
		for( int i = 3; i< input.length; i++){
			int[][] dungeons = input[i];
			int result = results[i];
			
			System.out.println(i + ". input: " + Misc.array2String(dungeons));
			System.out.println("output: " + sv.calculateMinimumHP(dungeons) + " \t result: " + result);
		}
		
		
		System.out.println("===end===");
	}

}
