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

    final static String fizz = "fizz";
    final static String buzz = "buzz";
    final static String fb = "fizz buzz";

    /**
     * @param n: As description.
     * @return A list of strings.
     * 
     */
    public List<String> fizzBuzz(int n) {
        if(n < 1){
            return Collections.EMPTY_LIST;
        }
                
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                result.add(fb);
            } else if (i % 5 == 0) {
                result.add(buzz);
            } else if (i % 3 == 0) {
                result.add(fizz);
            } else {
                result.add(String.valueOf(i));
            }
        }
        return result;
    }

    public List<String> fizzBuzz_1(int n) {
        if(n < 1){
            return Collections.EMPTY_LIST;
        }
                
        List<String> result = new ArrayList<>();
        
        boolean isDivided3;
        boolean isDivided5;
        for (int i = 1; i <= n; i++) {
            isDivided3 = ( 0 == i % 3 );
            isDivided5 = ( 0 == i % 5 );
            
            if(isDivided3){
                if(isDivided5){
                    result.add(fb);
                }else{
                    result.add(fizz);
                }

            }else if(isDivided5){
                result.add(buzz);
            }else{
                result.add(String.valueOf(i));
            }
        }
        return result;
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
            result.set(i - 1, fb);
        }

        return result;
    }
}
