package fgafa.basic.regex;

import fgafa.util.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Problem:
 * Parse a html page, extract the Urls in it.
 *
 * Example:
 * Given the following html page:
 * <div>
 *   <html>
 *      <body>
 *          <div>
 *               <a href="http://www.google.com" class="text-lg">Google</a>
 *               <a href="http://www.facebook.com" style="display:none">Facebook</a>
 *          </div>
 *          <div>
 *               <a href="https://www.linkedin.com">Linkedin</a>
 *               <a href = "http://github.io">LintCode</a>
 *          </div>
 *       </body>
 *   </html>
 * </div>
 *
 *  Return:
 *  [
 *   "http://www.google.com",
 *   "http://www.facebook.com",
 *   "https://www.linkedin.com",
 *   "http://github.io"
 *  ]
 *
 */

public class UrlParse {

    // Pattern pattern1 = Pattern.compile("(href\\s*=\\s*\")([^\"]*?)(\")", Pattern.CASE_INSENSITIVE);
    // Pattern pattern2 = Pattern.compile("(href\\s*=\\s*')([^']*?)(')", Pattern.CASE_INSENSITIVE);
    Pattern pattern = Pattern.compile("(href\\s*=\\s*[\"']?)([^\"'\\s>]*)([\"'>\\s])", Pattern.CASE_INSENSITIVE);

    public List<String> parseUrls(String content) {
        List<String> results = new ArrayList<>();

        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String url = matcher.group(2);
            if (url.length() == 0 || url.startsWith("#")){
                continue;
            }
            results.add(url);
        }

        return results;
    }

    public static void main(String[] args){
        UrlParse sv = new UrlParse();

        String content = "<html>\n" +
                "  <body>\n" +
                "    <div>\n" +
                "      <a href=\"http://www.google.com\" class=\"text-lg\">Google</a>\n" +
                "      <a href=\"http://www.facebook.com\" style=\"display:none\">Facebook</a>\n" +
                "    </div>\n" +
                "    <div>\n" +
                "      <a href=\"https://www.linkedin.com\">Linkedin</a>\n" +
                "      <a href = \"http://github.io\">LintCode</a>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";

        Misc.printArrayList(sv.parseUrls(content));
    }

}
