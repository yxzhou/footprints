package fgafa.math;

import java.util.HashMap;
import java.util.Map;
import java.lang.Math;

/**
 * Given two integers representing the numerator and denominator of a fraction,
 * return the fraction in string format.
 * 
 * If the fractional part is repeating, enclose the repeating part in
 * parentheses.
 * 
 * For example,
 * 
 * Given numerator = 1, denominator = 2, return "0.5". 
 * Given numerator = 2, denominator = 1, return "2". 
 * Given numerator = 2, denominator = 3, return "0.(6)".
 */
public class Fraction2Decimal {

	public String fractionToDecimal(int numerator, int denominator) {
		// check
		if (0 == denominator)
			return null;
		
		String sign = "";
		if((numerator > 0 && denominator < 0) || (numerator < 0 && denominator > 0))
			sign = "-";
		
		return sign + fractionToDecimal(Math.abs((long)numerator), Math.abs((long)denominator));
	}
	
	private String fractionToDecimal(long numerator, long denominator) {

		long result = numerator / denominator;
		long remain = numerator % denominator;
		if (0 == remain)
			return String.valueOf(result);

		StringBuilder sb = new StringBuilder();
		sb.append(result);
		sb.append('.');

		Map<Long, Integer> tracer = new HashMap<>();

		//for (int index = sb.length(); index < 50 && 0 != remain; index++) {
		for (int index = sb.length(); 0 != remain; index++) {
			if (tracer.containsKey(remain)) {
				sb.insert((int) tracer.get(remain), '(');
				sb.append(')');
				break;
			}

			tracer.put(remain, index);

			numerator = remain * 10;
			result = numerator / denominator;
			remain = numerator % denominator;

			sb.append(result);
		}

		return sb.toString();
	}

    
	public static void main(String[] args) {
		int[][] input = {
				{1,0},
				{0,1},
				{1,2},
				{2,1},
				{2,3},
				{10,6},
				{Integer.MAX_VALUE, 123},
				{Integer.MIN_VALUE, 123},
				{-1, 2},
				{-1, -2},
				{-2, 3}
		};

		Fraction2Decimal sv = new Fraction2Decimal();
		
		for(int i = 0; i< input.length; i++){
			System.out.println(String.format("%d input: %d / %d, \t output: %s", i, input[i][0], input[i][1], sv.fractionToDecimal(input[i][0],input[i][1])));
		}

	}

}
