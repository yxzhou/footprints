package fgafa.basic.regex;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * refer to: https://www.freeformatter.com/java-regex-tester.html#ad-output
 *
 */

public class Regex {

    /**
     *  \w, match any alphanumeric character including underscore. Equivalent to [A-Za-z0-9]
     */
    @Test
    public void alphanumeric(){
        String regex = "^[a-zA-z0-9]+$";
    }

    /**
     * We can consider a sentence valid if it conforms to the following rules:
     *
     * The sentence must start with a capital letter, followed by a lowercase letter or a space.
     * All other characters must be lowercase letters, separators (,,;,:) or terminal marks (.,?,!,‽).
     * There must be a single space between each word.
     * The sentence must end with a terminal mark immediately following a word.
     */
    @Test
    public void sentence(){
        String regex = "^[A-Z](([a-z]+)\\s)((([a-z,;:]+)\\s)*)([a-z]+)[\\.\\?\\!]$";

        Assert.assertTrue(Pattern.matches(regex,"This is a apple!"));
        Assert.assertTrue(Pattern.matches(regex,"An apple say: this is a apple."));

        Assert.assertFalse(Pattern.matches(regex,"this is a apple!"));
        Assert.assertFalse(Pattern.matches(regex,"This is a Apple!"));
        Assert.assertFalse(Pattern.matches(regex,"This  is a apple!"));
        Assert.assertFalse(Pattern.matches(regex,"This is a apple"));
        Assert.assertFalse(Pattern.matches(regex,"This apple say 'this is a apple'."));

        Assert.assertFalse(Pattern.matches(regex,"A apple!"));
        Assert.assertFalse(Pattern.matches(regex,"A1 is a apple!"));
    }

    /**
     * Validate if a given string is numeric.
     *
     * Some examples:
     * the following is valid:
     * "0"
     * "+12"
     * "0.1"
     * ".2"  ??
     * "2e10"
     * "1,000"
     * "1,000.00"
     *
     * the following is invalid:
     * "abc"
     * "1 a"
     *
     */
    @Test
    public void number(){
        // only includes: sign, digits, decimal point. example +12.34
        String regex = "^[\\+\\-]?(\\d+)(\\.\\d+)?$";

        Assert.assertTrue(Pattern.matches(regex, "1"));
        Assert.assertTrue(Pattern.matches(regex, "255"));
        Assert.assertTrue(Pattern.matches(regex, "+2"));
        Assert.assertTrue(Pattern.matches(regex, "2.0"));

        Assert.assertFalse(Pattern.matches(regex, "2.0.1"));

        // only includes: sign, digits, decimal point, comma. example -12,345.67
        regex = "^[\\+\\-]?(\\d+|\\d{1,3}(\\,\\d{3})+)(\\.\\d+)?$";
        Assert.assertTrue(Pattern.matches(regex, "1"));
        Assert.assertTrue(Pattern.matches(regex, "255"));
        Assert.assertTrue(Pattern.matches(regex, "+2"));
        Assert.assertTrue(Pattern.matches(regex, "2.0"));

        Assert.assertFalse(Pattern.matches(regex, "2.0.1"));


        Assert.assertTrue(Pattern.matches(regex, "1,234.56"));
        Assert.assertTrue(Pattern.matches(regex, "-12,345.678"));
        Assert.assertTrue(Pattern.matches(regex, "+123,456,789.00"));

        Assert.assertFalse(Pattern.matches(regex, "123,45.6"));
        Assert.assertFalse(Pattern.matches(regex, "1,2345.6"));

        // only includes: sign, digits, decimal point, comma, 'e'. example -12,345.67, +12e5
        regex = "^[\\+\\-]?((\\d+|\\d{1,3}(\\,\\d{3})+)(\\.\\d+)?|\\d+e\\d+)$";
        Assert.assertTrue(Pattern.matches(regex, "12e5"));
        Assert.assertTrue(Pattern.matches(regex, "+12e5"));

        Assert.assertFalse(Pattern.matches(regex, "1.2e5"));
        Assert.assertFalse(Pattern.matches(regex, "+2e"));
        Assert.assertFalse(Pattern.matches(regex, "e5"));
        Assert.assertFalse(Pattern.matches(regex, "2e2.1"));
    }


    @Test
    public void ipv4(){

        //[0-255]
        String regex = "^[0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5]$";
        Assert.assertTrue(Pattern.matches(regex, "25"));
        Assert.assertTrue(Pattern.matches(regex, "255"));
        Assert.assertTrue(Pattern.matches(regex, "2"));
        Assert.assertTrue(Pattern.matches(regex, "24"));
        Assert.assertTrue(Pattern.matches(regex, "26"));
        Assert.assertTrue(Pattern.matches(regex, "248"));
        Assert.assertTrue(Pattern.matches(regex, "1"));
        Assert.assertTrue(Pattern.matches(regex, "18"));
        Assert.assertTrue(Pattern.matches(regex, "160"));
        Assert.assertTrue(Pattern.matches(regex, "0"));
        Assert.assertTrue(Pattern.matches(regex, "5"));
        Assert.assertTrue(Pattern.matches(regex, "58"));

        Assert.assertFalse(Pattern.matches(regex, "256"));
        Assert.assertFalse(Pattern.matches(regex, "2551"));
        Assert.assertFalse(Pattern.matches(regex, "2481"));
        Assert.assertFalse(Pattern.matches(regex, "1601"));
        Assert.assertFalse(Pattern.matches(regex, "01"));

        //ipv4 looks like ***.***.***.***
        regex = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$";
        Assert.assertTrue(Pattern.matches(regex, "25.13.244.12"));
        Assert.assertTrue(Pattern.matches(regex, "25.13.244.0"));
        Assert.assertTrue(Pattern.matches(regex, "25.2.244.0"));
        Assert.assertTrue(Pattern.matches(regex, "25.2.5.2"));
        Assert.assertTrue(Pattern.matches(regex, "25.2.58.3"));

        //^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$
    }

    /**
     * In US, the valid phone number would be:
     * 1234567890
     * 123-456-7890
     * 123-456-7890 x1234
     * 123-456-7890 ext1234
     * (123)-456-7890
     * 123.456.7890
     * 123 456 7890
     *
     */
    @Test
    public void phoneNumber(){
        /**
         * the regex would be:
         * 1) 1234567890  -  \d{10}
         * 2) 123-456-7890 and 123.456.7890 and 123 456 7890  -  (\d{3}[-\.\s]){2}\d{4}
         * 3) (123)456-7890 or (123)4567890 or (123)456 789 --   \(\d{3}\)\d{3}[-\s]?\d{4}
         * 4) 123-456-7890 x1234 and 123-456-7890 ext1234, -(case1|case2|case3)[\s(x|ext)\d{3,5}]?
         *
         */
        //String regex = "^(\\d{10}|(([\\(]?([0-9]{3})[\\)]?)?[\\.\\-\\s]?([0-9]{3})[\\.\\-\\s]?([0-9]{4})))(\\s(x|ext)\\d{3,5})?$";
        String regex = "^(\\d{10}|((\\d{3}[-\\.\\s]){2}|\\(\\d{3}\\)\\d{3}[\\-\\s]?)\\d{4})(\\s(x|ext)\\d{3,5})?$";

        Assert.assertTrue(Pattern.matches(regex, "1234567890"));
        Assert.assertTrue(Pattern.matches(regex, "123-456-7890"));
        Assert.assertTrue(Pattern.matches(regex, "123.456.7890"));
        Assert.assertTrue(Pattern.matches(regex, "123 456 7890"));
        Assert.assertTrue(Pattern.matches(regex, "(123)4567890"));
        Assert.assertTrue(Pattern.matches(regex, "(123)456 7890"));
        Assert.assertTrue(Pattern.matches(regex, "(123)456-7890"));
        Assert.assertTrue(Pattern.matches(regex, "1234567890 x123"));
        Assert.assertTrue(Pattern.matches(regex, "123-456-7890 ext123"));
        Assert.assertTrue(Pattern.matches(regex, "123.456.7890 x1234"));
        Assert.assertTrue(Pattern.matches(regex, "123 456 7890 ext1234"));
        Assert.assertTrue(Pattern.matches(regex, "(123)4567890 x12345"));
        Assert.assertTrue(Pattern.matches(regex, "(123)456 7890 ext12345"));
        Assert.assertTrue(Pattern.matches(regex, "(123)456-7890 x123"));

        Assert.assertTrue(Pattern.matches(regex, "123-456.7890"));

        Assert.assertFalse(Pattern.matches(regex, "12345678901"));
        Assert.assertFalse(Pattern.matches(regex, "1234 567 890"));
        Assert.assertFalse(Pattern.matches(regex, "123 4567 890"));
        Assert.assertFalse(Pattern.matches(regex, "12 345 6789"));

        Assert.assertFalse(Pattern.matches(regex, "(123 456.7890"));
        Assert.assertFalse(Pattern.matches(regex, "(123)456.7890"));
    }

    /**
     * There is no 100% reliable solution since the RFC 5322 is way too complex.
     */
    @Test
    public void emailAddress(){
        //***@***.***,  case insensitive

        String regex = "^[a-z0-9\\-\\_]+@([a-z]+).[a-z]{2,6}$";

        Assert.assertTrue(Pattern.matches(regex, "abc@hotmail.com"));
        Assert.assertTrue(Pattern.matches(regex, "123@gmail.com"));
        Assert.assertTrue(Pattern.matches(regex, "abc-123@xyz.org"));
        Assert.assertTrue(Pattern.matches(regex, "abc_123@xyz.cn"));

        Assert.assertFalse(Pattern.matches(regex, "@gmail.com"));
        Assert.assertFalse(Pattern.matches(regex, "123+a@gmail.com"));
        Assert.assertFalse(Pattern.matches(regex, "123@.com"));
        Assert.assertFalse(Pattern.matches(regex, "123+a@gmail."));

        Assert.assertFalse(Pattern.matches(regex, "abc-123@xy-z.org"));
        Assert.assertFalse(Pattern.matches(regex, "abc+123@xyz.cn"));
    }

    @Test
    public void date(){
        //ISO date format yyyy-mm-dd
        String regex = "^[0-9]{4}-((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|(30|31))|(0[469]|11)-(0[1-9]|[1-2][0-9]|30)|02-(0[1-9]|[1-2][0-9]))$";
        Assert.assertTrue(Pattern.matches(regex, "2019-05-26"));
        Assert.assertTrue(Pattern.matches(regex, "2019-05-31"));
        Assert.assertTrue(Pattern.matches(regex, "2019-04-30"));
        Assert.assertTrue(Pattern.matches(regex, "2019-02-29"));

        Assert.assertFalse(Pattern.matches(regex, "2019-00-31"));
        Assert.assertFalse(Pattern.matches(regex, "2019-05-00"));
        Assert.assertFalse(Pattern.matches(regex, "2019-04-31"));
        Assert.assertFalse(Pattern.matches(regex, "2019-02-30"));

        Assert.assertTrue(Pattern.matches(regex, "2019-05/26"));

        //ISO date format yyyy-mm-dd with separator '-' or '/' or '.' or ''. Forces usage of same separator across date
        regex = "^[0-9]{4}[-.///]((0[13578]|(10|12))/1(0[1-9]|[1-2][0-9]|(30|31))|(0[469]|11)/1(0[1-9]|[1-2][0-9]|30)|02/1(0[1-9]|[1-2][0-9]))$";
        Assert.assertTrue(Pattern.matches(regex, "2019-05-26"));
        Assert.assertTrue(Pattern.matches(regex, "2019/05/26"));
        Assert.assertTrue(Pattern.matches(regex, "2019.05.26"));
        Assert.assertTrue(Pattern.matches(regex, "20190526"));

        Assert.assertFalse(Pattern.matches(regex, "2019-05/26"));

        //Hours and minutes, 24 hours format, HH:mm:ss
        //regex = "^([01][0-9]|20|21|22|23)((:[0-5][0-9]){1,2})$";
        regex = "^([01]/d|[20-23]((:[0-5]/d){1,2})$";
        Assert.assertTrue(Pattern.matches(regex, "00:00"));
        Assert.assertTrue(Pattern.matches(regex, "00:00:00"));
        Assert.assertTrue(Pattern.matches(regex, "23:59:59"));

        Assert.assertFalse(Pattern.matches(regex, "24:59"));
        Assert.assertFalse(Pattern.matches(regex, "00:60"));
        Assert.assertFalse(Pattern.matches(regex, "24:59:59"));
        Assert.assertFalse(Pattern.matches(regex, "00:60:59"));
    }

}
