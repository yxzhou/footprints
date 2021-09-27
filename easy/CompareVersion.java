package easy;

import util.Misc;

/**
 * 
 * Compare two version numbers version1 and version1.
 * If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.
 * 
 * You may assume that the version strings are non-empty and contain only digits and the . character.
 * The . character does not represent a decimal point and is used to separate number sequences.
 * For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level revision of the second first-level revision.
 * 
 * Here is an example of version numbers ordering:
 * 
 * 0.1 < 1.1 < 1.2 < 13.37
 *
 */
public class CompareVersion {
    public int compareVersion(String version1, String version2) {    	

		String[] v1 = split(version1);
		String[] v2 = split(version2);
		
        int diff = compare(v1[0], v2[0]);
        if(diff == 0){
    		if(v1[1] == null && v2[1] == null)
    			return 0;
    		
    		if(v1[1] == null)
    			v1[1] = "0";
    		else if(v2[1] == null)
    			v2[1] = "0";
    		
        	diff = compareVersion(v1[1], v2[1]);
        }
   	
       	return diff;
    }
    
    private String[] split(String version){
        int indexOfDot = version.indexOf('.');
        
        String[] ret = new String[2];
        if( -1 == indexOfDot){
        	ret[0] = version;
            ret[1] = null;
        }else{
        	ret[0] = version.substring(0, indexOfDot);
            ret[1] = version.substring(indexOfDot + 1);
        }
        return ret;
    }
    private int compare(String v1, String v2){
		
    	int i = Integer.parseInt(v1);
    	int j = Integer.parseInt(v2);
    	if(i == j)
    		return 0;
    	else if(i < j)
    		return -1;
    	else 
    		return 1;
    }
    
    public int compareVersion_x(String version1, String version2) {
        String[] levels1 = version1.split("\\.");
        String[] levels2 = version2.split("\\.");

        int length = Math.max(levels1.length, levels2.length);
        for (int i=0; i<length; i++) {
            Integer v1 = i < levels1.length ? Integer.parseInt(levels1[i]) : 0;
            Integer v2 = i < levels2.length ? Integer.parseInt(levels2[i]) : 0;
            int compare = v1.compareTo(v2);
            if (compare != 0) {
                return compare;
            }
        }

        return 0;
    }
    
	public static void main(String[] args) {
		String[][] versions = {
				{"0.1", "1.1"},
				{"1.1", "1.2"},
				{"1.2", "13.37"},
				{"1.2", "1.11"},
				{"1.11", "1.2"},
				{"1.0", "1.0"},
				{"1.0", "1"},
				{"1.0.0.0", "1"},
				{"0.1","0"},
				{"0.1","0.0.1"},
				
		};
		
		CompareVersion sv = new CompareVersion();
		
		for(String[] pair : versions){
			System.out.println("Input: " + Misc.array2String(pair));
			System.out.println("Output: " + sv.compareVersion(pair[0], pair[1]));
		}

	}

}
