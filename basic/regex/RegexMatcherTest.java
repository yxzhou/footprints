package basic.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java.util.regex
 *
 */
public class RegexMatcherTest {
    /**
     *
     * Groups and capturing
     * Capturing groups are numbered by counting their opening parentheses from left to right.
     *
     * In the expression ((A)(B(C))), for example, there are four such groups:
     * 1    	((A)(B(C)))
     * 2    	(A)
     * 3    	(B(C))
     * 4    	(C)
     * Group zero always stands for the entire expression.
     *
     */

    @Test
    public void testMatcherGroup(){
        String content = "This order was placed for QT3000! OK?";
        String regex = "(\\D*)(\\d+)(.*)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(content);

        while(matcher.find()){
            for(int i = 0; i <= matcher.groupCount(); i++){ // note, it's <= matcher.groupCount()
                System.out.println(matcher.group(i));
            }
        }

        //see while(matcher.find())  UrlParse.parseUrls
    }

    @Test
    public void testMatcherStartAndEnd(){
        final String REGEX = "\\bcat\\b";
        final String INPUT = "cat cat cat cattie cat";

        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT);
        int count = 0;

        while (m.find()) {
            count++;
            System.out.println("Match number " + count);
            System.out.println("start(): " + m.start());
            System.out.println("end(): " + m.end());
        }
    }

    /**
     * The matches method attempts to match the entire input sequence against the pattern.
     * The lookingAt method attempts to match the input sequence, starting at the beginning, against the pattern.
     * The find method scans the input sequence looking for the next subsequence that matches the pattern.
     */
    @Test
    public void compareWithFindAndMathesAndLookingAt() {
        final String regex = "foo";
        final String text1 = "fooooooooooooooooo";
        final String text2 = "ooooofoooooooooooo";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher1 = pattern.matcher(text1);
        Matcher matcher2 = pattern.matcher(text2);

        System.out.println(String.format("Contest is: %s, \tRegex is: %s", text1, regex));
        System.out.println(String.format("matches(): %b, \tlookingAt(): %b" ,matcher1.matches(), matcher1.lookingAt()));

        System.out.println(String.format("\nContest is: %s, \tRegex is: %s", text2, regex));
        System.out.println(String.format("matches(): %b, \tlookingAt(): %b" ,matcher2.matches(), matcher2.lookingAt()));
    }
}
