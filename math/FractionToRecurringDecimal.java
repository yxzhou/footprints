package math;

import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/1351
 * 
 * Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.
 *
 * If the fractional part is repeating, enclose the repeating part in parentheses.
 * 
 * For example,
 * Given numerator = 1, denominator = 2, return "0.5". 
 * Given numerator = 2, denominator = 1, return "2". 
 * Given numerator = 2, denominator = 3, return "0.(6)".
 * 
 */
public class FractionToRecurringDecimal {

    public String fractionToDecimal(int numerator, int denominator) {
        if (denominator == 0) {
            return "INF";
        }

        if (numerator == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        if ((numerator > 0 && denominator < 0) || (numerator < 0 && denominator > 0)) {
            sb.append("-");
        }
        
        long a = Math.abs((long) numerator);
        long b = Math.abs((long) denominator);
        
        sb.append(a / b);
        a = a % b;
        
        if (a == 0) {
            return sb.toString();
        }
        
        sb.append('.');

        Map<Long, Integer> tracer = new HashMap<>();
        while (a > 0) {
            if (tracer.containsKey(a)) {
                sb.insert((int) tracer.get(a), '(');
                sb.append(')');
                break;
            }

            tracer.put(a, sb.length());

            a *= 10;
            sb.append(a / b);
            a = a % b;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        
        String[][] inputs = {
            {"1", "0", "INF"},
            {"0", "1", "0"},
            {"1", "2", "0.5"},
            {"4", "2", "2"},
            {"2", "3", "0.(6)"},
            {"10", "6", "1.(6)"},
            {"1", "13", "0.(076923)"},
            {"100", "13", "7.(692307)"},
            {"30", "13", "2.(307692)"},
            {"2147483647", "123", "17459216.(64227)"},
            {"-2147483648", "123", "-17459216.(65040)"},
            {"-1", "2", "-0.5"},
            {"-1", "-2", "0.5"},
            {"-2", "3", "-0.(6)"}
        };

        FractionToRecurringDecimal sv = new FractionToRecurringDecimal();

        for (String[] input : inputs) {
            System.out.println(String.format("input: %s / %s, \t\texpect:%s", input[0], input[1], input[2] ));
            
            Assert.assertEquals(input[2], sv.fractionToDecimal(Integer.valueOf(input[0]), Integer.valueOf(input[1])) );
        }

    }

}
