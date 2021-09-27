package easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * from lintcode
 * Given number n. Print number from 1 to n. But:

	when number is divided by 3, print "fizz".
	when number is divided by 5, print "buzz".
	when number is divided by both 3 and 5, print "fizz buzz".
	
	Example
	If n = 15, you should return:
	
	["1", "2", "fizz", "4", "buzz", "fizz", "7", "8", "fizz", "buzz", "11", "fizz" ---
 *
 */

public class FizzBuzz {

    String fizz = "fizz";
    String buzz = "buzz";
    String fb = "fizz buzz";

    /**
     * param n: As description.
     * return: A list of strings.
     */
    public List<String> fizzBuzz(int n) {
        List<String> results = new ArrayList<String>();
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                results.add(fb);
            } else if (i % 5 == 0) {
                results.add(buzz);
            } else if (i % 3 == 0) {
                results.add(fizz);
            } else {
                results.add(String.valueOf(i));
            }
        }
        return results;
    }



    public List<String> fizzBuzz_2(int n) {
        if(n < 1){
            return Collections.EMPTY_LIST;
        }

        List<String> result = new ArrayList<>(n);
        for(int i = 1; i <= n; i++){
            result.add(String.valueOf(i));
        }
        for(int i = 3; i <= n; i += 3){
            result.set(i - 1, fizz);
        }
        for(int i = 5; i <= n; i += 5){
            result.set(i - 1, buzz);
        }
        for(int i = 15; i <= n; i += 15){
            result.set(i -1, fb);
        }

        return result;
    }
}
