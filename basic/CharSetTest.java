package basic;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class CharSetTest
{

  public static void main(String[] args) {
    System.out.println("Default Charset=" + Charset.defaultCharset());

    System.out.println("Charset.availableCharsets="
        + Charset.availableCharsets());

    System.setProperty("file.encoding", "Latin-1");
    System.out.println("file.encoding=" + System.getProperty("file.encoding"));
    System.out.println("Default Charset=" + Charset.defaultCharset());
    System.out.println("Default Charset in Use=" + getDefaultCharSet());
    
    testString();
  }



  private static String getDefaultCharSet() {
    OutputStreamWriter writer = new OutputStreamWriter(
        new ByteArrayOutputStream());
    String enc = writer.getEncoding();
    return enc;
  }

  
  private static void testString(){
    String str= "abcd";
    
    for(int i=0; i< str.length(); i++){
      
      System.out.print("\n"+ str.charAt(i));
      System.out.print("\t"+ str.codePointAt(i));
    
    }
  }
  
}
