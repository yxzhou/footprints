package basic.regex;

public class ValidPhoneNumber {

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
     * the regex would be:
     * 1234567890  -  \d10}
     * 123-456-7890 and 123.456.7890 and 123 456 7890  -  \d{3}[-\.\s]\d{3}[-\.\s]\d{4}
     * (123)456-7890 or (123)4567890   --   \(\d{3}\)\d{3}-?\d{4}
     * 123-456-7890 x1234 and 123-456-7890 ext1234  --  \d{3}-\d{3}-\d{4}\s(x|(ext))\d{3,5}
     */

    //String pattern = "(\\d{10})|(\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4})|(\\(\\d{3}\\)\\d{3}-?\\d{4})|(\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5})";
    String pattern = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})(\\s(x|(ext))\\d{3,5})?$";

    public boolean isValidPhoneNumber(String phoneNumber){
        return phoneNumber.matches(pattern);
    }

    /**
     *  format the valid phone number to "(123) 456-7890"
     *
     */
    String replacement = "($1) $2-$3";
    public String formatPhoneNumber(String phoneNumber){
        return phoneNumber.replaceFirst(pattern, replacement);
    }

    public static void main(String[] args){
        ValidPhoneNumber sv = new ValidPhoneNumber();

        String[] input = {
                /* Following are valid phone numbers */
                "1234567890",
                "123-456-7890",
                "123-456-7890 x1234",
                "123-456-7890 ext1234",
                "(123)-456-7890",
                "123.456.7890",
                "123 456 7890",
                "(123)456-7890",
                "(123)4567890",
                /* Following are invalid phone numbers */
                "12345678",
                "12-12-123"
        };

        for(String phoneNumber : input){
            System.out.println(String.format("%s \t\t isValid: %b \t\t formarted:%s", phoneNumber, sv.isValidPhoneNumber(phoneNumber), sv.formatPhoneNumber(phoneNumber)));
        }

    }

}
