package stringmatching;

/**
 * 
 * refer to java.lang.String
 *
 */
public class String2
{

	/*
	 * Write a function to find the longest common prefix string amongst an array of strings.
	 */
	public String longestCommonPrefix(String[] strs) {
		if (null == strs || 0 == strs.length) {
			return "";
		}

		StringBuilder ret = new StringBuilder();
		try {
			for (int i = 0; i < strs[0].length(); i++) {
				for (int j = 1; j < strs.length; j++) {
					if (strs[0].charAt(i) != strs[j].charAt(i)) {
						return ret.toString();
					}
				}
				ret.append(strs[0].charAt(i));
			}
		} catch (IndexOutOfBoundsException e) {
			// ignore
		}

		return ret.toString();
	}
    
	public String longestCommonPrefix_2(String[] strs) {
		if (null == strs || 0 == strs.length) {
			return "";
		}

		StringBuilder ret = new StringBuilder();
		char c;
		for (int i = 0; i < strs[0].length(); i++) {
			c = strs[0].charAt(i);
			for (int j = 1; j < strs.length; j++) {
				if (i == strs[j].length() || c != strs[j].charAt(i)) {
					return ret.toString();
				}
			}
			ret.append(strs[0].charAt(i));
		}

		return ret.toString();
	}
	
  /**
   * @param args
   */
  public static void main(String[] args) {

    String[]    src = {"abc", "abc","abc", "abc", "abc", "abcd", "abc", "mississippi"};
    String[] target = {   "", "a",  "ab",    "c",   "b",   "cd", "bcd", "issip"};
    int r ;
    for(int i =0; i< src.length; i++){
        r = indexOf(src[i].toCharArray(), target[i].toCharArray());
        System.out.print("\n--indexOf(\""+src[i]+"\",\""+target[i]+"\") is:"+ r);
        System.out.println("  "+ (r == src[i].indexOf(target[i])));

        r = strStr(src[i], target[i]);
        System.out.print("--strStr(\""+src[i]+"\",\""+target[i]+"\") is:"+ r);
        System.out.println("  "+ (r == src[i].indexOf(target[i])));

        r = strStr_n(src[i], target[i]);
        System.out.print("--strStr_n(\""+src[i]+"\",\""+target[i]+"\") is:"+ r);
        System.out.println("  "+ (r == src[i].indexOf(target[i])));
    }
    
    
    String[] str ={   
      "a, a = X",
      "aa, aa = X",
      "aa, a = X",
      "aa, aaa = aa",
      "abc, abc = X",
      "abcabc, abc = X",
      "abcabcabc, abc = X",
      "abcaabcaabc, abc = XaXaX",
      "abcaaabcaaabca, abc = XaaXaaXa",
      "abcabcabababcabc, abc = XababX",
      "abcabcabababcabcab, abc = XababXab",
      "aabbaabbaaabbbaabb, aabb = XaXbX",
      "aabbaabbaaabbbaabb, aaabb = aabbaabbXbaabb",
      "aabbaabbaaabbbaaabb, aaabb = aabbaabbXbX",
      "aabbaabbaaabbbaaabc, aaabb = aabbaabbXbaaabc",
      "abcdeffdfegabcabc, abc = XdeffdfegX",
      "abcdeffdfegabcabc, ab = XcdeffdfegXcXc",
      "abcdeffdfegabcabc, a = XbcdeffdfegXbcXbc",
      "abcdeffdfegabcab, abc = XdeffdfegXab",
      "abcdeffdfegabcabcab, abc = XdeffdfegXab",
      "abcdeffdfegabcaabcab, abc = XdeffdfegXaXab",
      "abcdeffdfegabcaaaabcab, abc = XdeffdfegXaaaXab",
      "aaaaaa, a = X",
      "aaaaaa, aa = X",
      "aaaaaa, aaaaaa = X",
      "aaaaaa, aaaaaaa = aaaaaa",
      "aabaababaaab, a = XbXbXbXb",
      "aabaababaaa, a = XbXbXbX",
      "aaaab, a = Xb",
      "baaa, a = bX",
      "aabaaabaab, aaa = aabXbaab",
      "aabaaabaab, aa = XbXabXb",
      "aabaaabaa, aa = XbXabX"
    };
    
    
    System.out.println("");
    
    
    int commaId, equalId;
    String sc, pattern, replacement, result;
    for(int i=0; i< str.length; i++){
      commaId = str[i].indexOf(',');
      equalId = str[i].indexOf('=');
      
      sc = str[i].substring(0, commaId);
      pattern = str[i].substring(commaId+2, equalId -1);
      replacement = str[i].substring(equalId + 2);

      System.out.println("\n\""+str[i] + "\", ==>" +"src="+sc+", pattern="+pattern+", replacement="+replacement);
      result = replaceAll(sc, pattern, replacement);
      System.out.print("==>" + result);
      System.out.println("   " + result.equals(sc.replaceAll(pattern, replacement)));
      
    }

    String s1 = "1";
    String s2 = "";

    s2 += s1.charAt(0);
    
  }


  
  /**
   * Returns the index within this string of the first occurrence of the specified substring
   * 
   * @param source
   *            the characters being searched.
   * @param sourceOffset
   *            offset of the source string.
   * @param sourceCount
   *            count of the source string.
   * @param target
   *            the characters being searched for.
   * @param targetOffset
   *            offset of the target string.
   * @param targetCount
   *            count of the target string.
   * @param fromIndex
   *            the index to begin searching from.
   */
  static int indexOf(char[] source, int sourceOffset, int sourceCount,
      char[] target, int targetOffset, int targetCount, int fromIndex) {
    if (fromIndex >= sourceCount) {
      return (targetCount == 0 ? sourceCount : -1);
    }
    if (fromIndex < 0) {
      fromIndex = 0;
    }
    if (targetCount == 0) {
      return fromIndex;
    }

    char first = target[targetOffset];
    int max = sourceOffset + (sourceCount - targetCount);

    for (int i = sourceOffset + fromIndex; i <= max; i++) {
      /* Look for first character. */
      if (source[i] != first) {
        while (++i <= max && source[i] != first);
      }

      /* Found first character, now look at the rest of v2 */
      if (i <= max) {
        int j = i + 1;
        int end = j + targetCount - 1;
        for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++);

        if (j == end) {
          /* Found whole string. */
          return i - sourceOffset;
        }
      }
    }
    return -1;
  }
  
  
  /*Time O(n^2)*/
  public static int indexOf(char[] src, char[] target){
    //check
    if(src == null || target == null)
      return -1;
    
    int sLen = src.length;
    int tLen = target.length;
    if(sLen < tLen || sLen == 0)
      return -1;
    if(tLen == 0)
      return 0;
    
    //main
    int i = 0, j = 0;
    
    while(i < sLen - tLen){
      while(i<sLen-tLen && src[i] != target[0]) 
        i++;
        
      if( src[i] == target[0] ){
        while(i<sLen && j<tLen && src[i+j] == target[j]) j++;
        
        if( j == tLen)
          return i;      
        
        j=0;
      }
      i++;
      
    } 
    return -1;
  }
  
  /**
   * Returns a index to the first occurrence of target in source,
   * or -1  if target is not part of source.
   * @param source string to be scanned.
   * @param target string containing the sequence of characters to match.
   */
  public static int strStr(String source, String target) {
      //check
      if(null == source || null == target){
          return -1;
      }
      if(0 == target.length()){
    	  return 0;
      }
      
      for( int i = 0; i <= source.length() - target.length(); i++){
          
          for(int j = 0; j < target.length(); j++){
              if( source.charAt(i+j) == target.charAt(j) ){
                  
                  if( j == target.length() - 1){
                      return i;
                  }
              }else{
                  break;
              }
          }
      }
      
      return -1;
  }
  
  
  public static int strStr_2(String source, String target) {
      //check
      if(null == source || null == target){
          return -1;
      }
      if(0 == target.length()){
          return 0;
      }
      
      int j = 0;
      for( int i = 0; i <= source.length() - target.length(); i++){
          
          for(j = 0; j < target.length(); j++){
              if( source.charAt(i + j) != target.charAt(j) ){
                  break;
              }
          }
          
          if( j == target.length()){
              return i;
          }
      }
      
      return -1;
  }

    public static int strStr_n(String haystack, String needle) {
        if(null == haystack || null == needle || haystack.length() < needle.length()){
            return -1;
        }

        int n = needle.length();
        long target = hash(needle);

        long curr = hash(haystack.substring(0, n));

        for(int i = 0, end = haystack.length() - n ; i <= end; i++){
            if(curr == target && haystack.substring(i, i + n).equals(needle)){
                return i;
            }

            if(i < end){
                curr = rehash(curr, n, haystack.charAt(i), haystack.charAt(i + n));
            }
        }

        return -1;
    }

    private static long hash(String s){
        long hash = 0;

        for(char c : s.toCharArray()){
            hash += (hash << 5) + c;
        }

        return hash;
    }

    private static long rehash(long hash, int length, char removeChar, char addChar){
        long remove = removeChar;
        while(length > 1){
            remove += (remove << 5) ;
            length--;
        }

        hash -= remove;
        hash += (hash << 5) + addChar;

        return hash;
    }

  /**
   * Replace all occurrence of the given pattern to ‘X’. For example, given that
   * the pattern=”abc”, replace “abcdeffdfegabcabc” with “XdeffdfegX”. Note that
   * multiple occurrences of abc’s that are contiguous will be replaced with
   * only one ‘X'
   * 
   * 
   */
  public static String replaceAll(String src, String pattern, String replacement){
    //check
    if(src == null || pattern == null || replacement == null)
      return null; 
    
    //main
    StringBuffer sb = new StringBuffer();
    char[] s = src.toCharArray();
    char[] p = pattern.toCharArray();
    int sLen = s.length;
    int pLen = p.length;
    int i = 0, j = 0;
    
    while(i<sLen){
      j = 0;
      
      while(i<sLen && s[i] != p[0]) {
        sb.append(s[i]);
        i++;
      }
      
      if(i<sLen && s[i] == p[0]){
        while(i<sLen && j<pLen && s[i] == p[j]){
          i++;
          j++;
        }
        
        if(j == pLen)
          sb.append(replacement);
        else{
          sb.append(s[i - j]);
          i = i - j + 1;
        }
      }      
    }
    
    while(i<sLen)
      sb.append(s[i++]);
    
    //return   
    return sb.toString();
  }

  //private 
  
}
