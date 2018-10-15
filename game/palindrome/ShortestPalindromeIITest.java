package fgafa.game.palindrome;

import org.junit.Assert;
import org.junit.Test;

public class ShortestPalindromeIITest {


    @Test
    public void test0() {

        String[][] inputs = {
                {"RACE", "ECARACE"},
                {"ZAZZ", "ZAZAZ"},
                {"TOPCODER", "REDTOCPCOTDER"},
                {"Q", "Q"},
                {"MADAMIMADAM", "MADAMIMADAM"},
                {"ALRCAGOEUAOEURGCOEUOOIGFA", "AFLRCAGIOEOUAEOCEGRURGECOEAUOEOIGACRLFA"}
        };

        ShortestPalindromeII sv = new ShortestPalindromeII();

        for(int i = 0; i < inputs.length; i++){
            Assert.assertTrue( sv.shortest(inputs[i][0]).compareTo(inputs[i][1]) == 0) ;
        }
    }
}
