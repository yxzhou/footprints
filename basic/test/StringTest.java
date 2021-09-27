package basic.test;

import org.junit.Test;

public class StringTest {

    @Test
    public void testSplit(){
        String s = "1/2//3/";
        String[] words = s.split("/");

        System.out.println(words.length);


        String s2 = "1/2//3//";
        String[] words2 = s.split("/");

        System.out.println(words2.length);
    }
}
