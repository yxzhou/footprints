
package basic.regex;

/**
 * _https://www.lintcode.com/problem/968
 *
 * Description
 * Implement a function that will determine if a string represents a valid currency amount. 
 * 
 * Such strings will have the following properties:
 *   The amount must consist of base-10 digits. 
 *   The amount may optionally contain thousands separators using the ',' character, such as 10,000,000. 
 *   If thousands separators are present, they must be present at each thousands increment.
 *   The amount must be prefixed by the currency symbol. We support US Dollars (D),Euros (E), and Japanese Yen (Y) only.
 *   Negative amounts may be indicated either by a negative sign before the currency symbol or by enclosing the amount (including currency symbol) in parentheses, such as (D450). 
 *   Dollar and Euro amounts may contain an amount of cents, represented to exactly two digits of precision. 
 *   If a decimal point is present, the cents must be specified. 
 *   Yen amounts may not contain decimal points. 
 *   Amounts may not contain a leading zero, unless it is zero Dollars or Euros and cents are specified. 
 *   Any other characters,including leading or trailing whitespace, is invalid. 
 *
 * Example 
 * Input1: "D450"  Output1: true 
 * Input2: "-E23"  Output2: true
 * 
 * Thoughts:
 *   sign   -> - | enclosing in parentheses 
 *   symbol -> D | E | Y
 *   digits(0-9) -> 1234 or 12,345,  1234,567 not ok 
 *   optional_fraction -> .[0-9]{2}  only for D and E
 * 
 * Currency_without_sign -> 
 *   [DE](0|[1-9]{1}([0-9]+|[0-9]{0,2}(\\,[0-9]{3})*))(\\.[0-9]{2})?
 *   Y(0|[1-9]{1}([0-9]+|[0-9]{0,2}(\\,[0-9]{3})*))
 *  
 */
public class ValidCurrency {
    
    /**
     * @param currency: a string represents a valid or invalid currency amount
     * @return: return whether the given string is a valid currency
     */
    public boolean validCurrencyAmount(String currency) {
        String DE_without_sign = "[DE](0|[1-9]{1}([0-9]+|[0-9]{0,2}(\\,[0-9]{3})*))(\\.[0-9]{2})?";
        String Y_without_sign = "Y(0|[1-9]{1}([0-9]+|[0-9]{0,2}(\\,[0-9]{3})*))";

        StringBuilder currency_without_sign = new StringBuilder();
        currency_without_sign.append("\\-?").append(DE_without_sign).append("|\\-?").append(Y_without_sign);
        currency_without_sign.append("|\\(").append(DE_without_sign).append("\\)|\\(").append(Y_without_sign).append("\\)");   

        return currency.matches(currency_without_sign.toString());
    }
        
}
