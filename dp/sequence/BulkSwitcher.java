package dp.sequence;

/**
 * 
 * There are n bulbs that are initially off. 
 * You first turn on all the bulbs. 
 * Then, you turn off every second bulb. 
 * On the third round, you toggle every third bulb (turning on if it's off or turning off if it's on). 
 * For the nth round, you only toggle the last bulb. 
 * Find how many bulbs are on after n rounds.
 * 
 * Example:
 *     
 *  Given n = 3. 
 *  
 * At first, the three bulbs are [off, off, off].
 * After first round, the three bulbs are [on, on, on].
 * After second round, the three bulbs are [on, off, on].
 * After third round, the three bulbs are [on, off, off]. 
 * 
 * So you should return 1, because there is only one bulb is on.
 *
 */

public class BulkSwitcher {

    public int bulbSwitch(int n) {
        if(n < 1){
            return 0;
        }
        
        boolean[] bulbs = new boolean[n + 1]; //default all are false;
        
        for(int i = 1; i <= n; i++){
            
            for(int j = i; j <= n; j +=i ){
                bulbs[j] = !bulbs[j];
            }
            
        }
        
        int count = 0;
        for(int i = 1; i <=n; i++){
            if(bulbs[i]){
                count++;
            }
        }

        return count;
    }
    
    
    public int bulbSwitch_n(int n) {
        if(n < 1){
            return 0;
        }

        return (int)Math.sqrt(n);
    }
}
