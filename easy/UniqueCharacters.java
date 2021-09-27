package easy;

public class UniqueCharacters {

	/**
	 * From lintcode
	 * Implement an algorithm to determine if a string has all unique
	 * characters.
	 * 
	 * Given "abc", return true.
	 * 
	 * Given "aab", return false.
	 */
    /**
     * @param str: a string
     * @return: a boolean
     */
    public boolean isUnique(String str) {
        // check
        if(null == str || 0 == str.length()){
            return true;
        }
        
        boolean[] isVisited = new boolean[256];
        for(int c : str.toCharArray()){
            if(isVisited[c]){
                return false;
            }else{
                isVisited[c] = true;
            }
        }
        
        return true;
    }
	
	
	public static void main(String[] args) {
		UniqueCharacters sv = new UniqueCharacters();
		
		String[] input = {"abc", "aab"};
		
		for(String s : input){
			System.out.println("Input: \t" + s + "\t Outpu:" + sv.isUnique(s));

		}
		
	}

}
