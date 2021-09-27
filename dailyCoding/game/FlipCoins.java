package dailyCoding.game;

import java.util.Random;

import org.junit.Test;

/**
 * You have 100 fair coins and you flip them all at the same time. Any that come up tails you set aside. The ones that come up heads you flip again.
 * How many rounds do you expect to play before only one coin remains?
 *
 * Write a function that, given n, returns the number of rounds you'd expect to play until one coin remains.
 *
 * Tags: Ms
 */

public class FlipCoins {

    public int flipCoins(int coinsNumber){
        int roundCounter = 0;
        while(coinsNumber > 0){
            roundCounter++;

            int headerCounter = 0;
            for(int i = 0; i < coinsNumber; i++){
                if(isHeader()){
                    headerCounter++;
                }
            }
            coinsNumber = headerCounter;
        }

        return roundCounter;
    }



    Random random = new Random();

    private boolean isHeader(){
        return random.nextInt(2) == 0;
    }

    @Test public void test(){
//        System.out.println(isHeader());
//        System.out.println(isHeader());
//        System.out.println(isHeader());

        System.out.println(flipCoins(100));
    }
}
