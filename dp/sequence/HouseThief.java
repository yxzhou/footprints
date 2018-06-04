package fgafa.dp.sequence;


/**
 * There are n houses, today a theft hide in one house, tomorrow he will move to the neighbor house, 
 * ( if today he is in 0th, he will move to 1st only. if today he is in (n-1)th, he will move to (n-2)th only. )
 * 
 * Suppose you are working with police, how to catch the theft? How to decide if a strategy is valid to catch a theft?
 * 
 */
public class HouseThief {

    /**
     * 
     * @param n
     * @param strategy
     * @return
     */
    /* */
    public boolean canCatchTheft(int n, int strategy[]) {
        //check
        if(n < 1 || strategy.length < 1){
            return false;
        }
        if(1 == n){
            return 0 == strategy[0];
        }

        //init
        boolean[] safeHouse = new boolean[n+2];//default all are false
        boolean[] nextSafeHouse;
        //safeHouse[0] = false;
        //safeHouse[n+1] = false;
        for(int i = 1; i <= n; i++){
            safeHouse[i] = true;
        }
        safeHouse[strategy[0] + 1] = false;
        int safeHouseNum = n - 1;
        
        //main
        for(int i = 1; i < strategy.length && safeHouseNum > 0; i++){
            safeHouseNum = 0;
            nextSafeHouse = new boolean[n+2];//default all are false
            
            for(int j = 1; j <= n; j++){
                if(safeHouse[j - 1] || safeHouse[j + 1]){
                    nextSafeHouse[j] = true;
                    safeHouseNum++;
                }
            }
            if(nextSafeHouse[strategy[i] + 1]){
                nextSafeHouse[strategy[i] + 1] = false;
                safeHouseNum--;
            }

            safeHouse = nextSafeHouse;
        }
        
        //return
        return safeHouseNum == 0;
    }
    
    public static void main(String[] args) {
        int[] n = { 4, 4, 4, 7, 7};
        int[][] strategy = {
                    {1,1,2,2,1}, 
                    {1,1,2,2,2},
                    {2, 1, 1, 2},
                    {1,2,3,0,3,4,5,5,4,3,6,3,2,1},
                    {1,2,3,3,4,5,5,4,6,4,3,2,1},
        };
        boolean[] expect = {true, false, true, true, false};
        
        
        HouseThief sv = new HouseThief();
        for(int i = 0; i < n.length; i++){
            System.out.println(String.format("Input: n=%d, strategy is %s", n[i], strategy[i].toString()));
            System.out.println(String.format("Output-expect, %b-%b", sv.canCatchTheft(n[i], strategy[i]), expect[i]));
        }
    }

}
