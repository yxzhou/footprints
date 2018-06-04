package fgafa.game.nimGames;

/**
 * You are playing the following Nim Game with your friend: There is a heap of stones on the table, 
 * each time one of you take turns to remove 1 to 3 stones. The one who removes the last stone will be the winner. 
 * You will take the first turn to remove the stones. 
 * 
 * Both of you are very clever and have optimal strategies for the game. 
 * Write a function to determine whether you can win the game given the number of stones in the heap. 
 * 
 * For example, if there are 4 stones in the heap, then you will never win the game: 
 * no matter 1, 2, or 3 stones you remove, the last stone will always be removed by your friend.
 */


public class NimGame {

    public boolean canWinNim(int n) {
        if(n < 4){
            return true;
        }
        
        boolean[] ret = new boolean[3];
        ret[0] = true;
        ret[1] = true;
        ret[2] = true;
        
        for(int i = 3; i< n; i++){
            ret[i%3] = !( ret[i%3] && ret[(i+1)%3] && ret[(i+2)%3] );
        }
        
        return ret[(n-1) % 3];
        
    }
    
    public boolean canWinNim_x(int n) {
        if(n < 4){
            return true;
        }
        
        return n % 4 != 0;
    }
    
    /**
     * There are n coins in a line. Two players take turns to take one or two 
     * coins from right side until there are no more coins left. The player who take the last coin wins.

        Have you met this question in a real interview? Yes
        Example
        n = 1, return true.
        n = 2, return true.
        n = 3, return false.
        n = 4, return true.
        n = 5, return true.
     */
    public boolean firstWillWin(int n) {
        if(n < 3){
            return true;
        }
        
        boolean[] ret = new boolean[2];
        ret[0] = true;
        ret[1] = true;
        
        for(int i = 2; i< n; i++){
            ret[i & 1] = !( ret[0] && ret[1] );
        }
        
        return ret[(n-1) & 1];
    }
    
    /*  Time O(1), Space O(1)*/
    public boolean firstWillWin_x(int n) {
        //check
        if(n < 1){
            return false;
        }
        
        return n % 3 != 0;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
       
        NimGame sv = new NimGame();
        
        for(int n = 1 ; n < 33; n++){
            System.out.println(String.format("Input: %d, are you will win? %b", n, sv.canWinNim(n)));
        }

    }
}
