package basic.regex;

import org.junit.Test;

import java.util.regex.Pattern;

public class RegexPatternTest {

    @Test
    public void testPattern(){
        String content = "I am noob from runoob.com.";

        String pattern = ".*runoob.*";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println(String.format("Text: %s \nRegex: %s, \nisMatch: %b", content, pattern, isMatch));
    }

}
