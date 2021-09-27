package dailyCoding;

/**
 *
 * A builder is looking to build a row of N houses that can be of K different colors. He has a goal of minimizing cost while ensuring that no two neighboring houses are of the same color.

 Given an N by K matrix where the nth row and kth column represents the cost to build the nth house with kth color, return the minimum cost which achieves this goal.
 *
 * Tags: facebook
 *
 */

public class HouseColorCost {

    public int minCost(int[][] costs){

        if(null == costs || 0 == costs.length || 0 == costs[0].length){
            return -1;
        }

        int houseNumber = costs.length;
        int colorNumber = costs[0].length;

        //define minCosts[i] is the min cost when the last is ith color
        int[] minCosts = new int[colorNumber]; //default all are 0
        int minIndex = 0;
        int secondMinIndex = 0;

        for(int houseIndex = 0; houseIndex < houseNumber; houseIndex++){
            int newMinIndex = 0;
            int newSecondMinIndex = 0;

            for(int colorIndex = 0; colorIndex < colorNumber; colorIndex++){
                if(colorIndex == minIndex){
                    minCosts[colorIndex] = minCosts[secondMinIndex] + costs[houseIndex][colorIndex];
                }else{
                    minCosts[colorIndex] = minCosts[minIndex] + costs[houseIndex][colorIndex];
                }

                if(minCosts[newMinIndex] > minCosts[colorIndex]){
                    newSecondMinIndex = newMinIndex;
                    newMinIndex = colorIndex;
                }else if(minCosts[newSecondMinIndex] > minCosts[colorIndex]){
                    newSecondMinIndex = colorIndex;
                }
            }

            minIndex = newMinIndex;
            secondMinIndex = newSecondMinIndex;
        }

        int result = minCosts[0];
        for(int i = 1; i < colorNumber; i++){
            result = Math.min(result, minCosts[i]);
        }

        return result;
    }

}
