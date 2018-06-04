package fgafa.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/*
 * Given two words of equal length that are in a dictionary, write a method to transform one word into another
 * word by changing only one letter at a time. The new word you get in each step must be in the dictionary. 
 * 
 *  Example:
 *  input:  DAMP, LIKE
 *  output: DAMP -> LAMP -> LIMP -> LIME -> LIKE
 *  
 *  careercup 219.
 * 
 */
public class WordLadder
{

  private static Hashtable<String, Boolean> htDictionary = new Hashtable<String, Boolean>();
  private static HashSet<String> dict = new HashSet<String>();
  static { 
    String[] st = {"LAMP", "LIMP", "LAKP", "LAME", "LIKP", "LIME", "LAKE",
        "LIKE", // change the first one, 2*2*2
        "DIMP", "DIKP", "DIME", "DIKE", // keep the first one, change the second one, 2*2
        "DAKP", "DAKE", //keep the first two, change the third one, 2
        "DAME" //keep the first three, change the last one
    };

    boolean[] b = {true, true, false, true, false, true, true, true, false,
        false, true, true, false, false, true};

    for (int i = 0; i < st.length; i++) {
      htDictionary.put(st[i], Boolean.valueOf(b[i]));
      
      if(b[i])
        dict.add(st[i]);
    }
    
  }



  private String getNewStr(String str, char c, int i, int len) {
    StringBuffer sb = new StringBuffer();

    sb.append(str.substring(0, i));  // include 0, not i
    sb.append(c);
    sb.append(str.substring(i+1, len));

    return sb.toString(); 
  }


  /* wrong !! */
  private boolean convert(String str1, String str2) {
    boolean returnValue = false;
    //default check
    if (str1 == null || str2 == null || str1.length() != str2.length())
      return returnValue;

    //init
    int len = str1.length();
    String tmp;
    int i = 0;
    int iTmp = 0;
    char[] chars1 = str1.toCharArray();
    char[] chars2 = str2.toCharArray();

    Stack<Integer> st1 = new Stack<Integer>(); //store the change steps
    Stack<String> st2 = new Stack<String>(); // store the temporary STRING according to the change steps
    st2.add(str1);
    Stack<Integer> st3 = new Stack<Integer>(); //store the not changed index
    for (int j = len - 1; j >= 0; j--) {
      st3.push(j);
    }

    while (st1.size() < len) {
      i = st3.pop();
      tmp = st2.peek();
      tmp = tmp.replaceFirst(String.valueOf(chars1[i]), String
          .valueOf(chars2[i]));

      if (htDictionary.get(tmp)) {
        st1.push(i);
        st2.push(tmp);
      }
      else {
        iTmp = st3.pop();
        st3.push(i);
        st3.push(iTmp);
      }

    }

    //
    return returnValue;
  }

  /*
   * wrong !!  
   * Given:
   * start = "hit"
   * end = "cog"
   * dict = ["hot","dot","dog","lot","log"] 
   * 
   * recurr 
   * 
   */
  private static int SUM = 0;
  private static List<Stack> list = new ArrayList<Stack>();
  public void convertR(String str1, String str2, Stack<String> st) {
    //init
    String tmp;
    int len = str1.length();
    char[] chars2 = str2.toCharArray();

    if(!list.isEmpty())
      return;
    
    if(str1.equals(str2)){
      SUM++;
      list.add(st);      

    }else{
      for (int i = 0; i < str1.length(); i++) {
        char[] chars1 = str1.toCharArray();

        if (chars1[i] != chars2[i]) {
          tmp = getNewStr(str1, chars2[i], i, len);

          if (htDictionary.get(tmp)) {
            Stack<String> stTmp = (Stack<String>) st.clone();
            stTmp.push(tmp);

            convertR(tmp, str2, stTmp);
          }
        }
      }

      st = null;
    }

  }

/*
 * Given two words (start and end), and a dictionary, find the length of shortest transformation sequence from start to end, such that:
 * 
 * Only one letter can be changed at a time
 * Each intermediate word must exist in the dictionary
 * For example,
 * 
 * Given:
 * start = "hit"
 * end = "cog"
 * dict = ["hot","dot","dog","lot","log"]
 * As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * return its length 5.
 * 
 * Note:
 * Return 0 if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.  
 */
  public int ladderLength(String start, String end, HashSet<String> dict) {
    if((start == null && end == null) || start.length() != end.length() )
      return 0;

    //HashMap<String, Integer> cache = new HashMap<String, Integer>(); 
    //int ladderLength = ladderLength_recursion(start, end, dict, cache);   
    //if (ladderLength < Integer.MAX_VALUE)
    //  return ladderLength + 1;
    //return 0;
    
    return ladderLength_iterance(start, end, dict); 
    
  }
  
  /* stack over flow O(26^n) */
  private int ladderLength_recursion(String start, String end, HashSet<String> dict, HashMap<String, Integer> cache) {
    if(start.equals(end))
      return 0;
    
    int minLadderLength = Integer.MAX_VALUE, ladderLength;
    String middle;
    for(int i=0; i<start.length(); i++){
      //if(start.charAt(i) != end.charAt(i)){   //wrong!!
      for(char c = 'A'; c<='Z'; c++){  
        middle = getNewStr(start, c, i, start.length());
        if(dict.contains(middle)){
          if(cache.containsKey(middle))
            ladderLength = cache.get(middle); 
          else {  
            ladderLength = ladderLength_recursion(middle, end, dict, cache);
            cache.put(middle, ladderLength);
          }
          if(ladderLength < Integer.MAX_VALUE)
            ladderLength++;
            
          minLadderLength = Math.min( minLadderLength, ladderLength);
        }  
      }
    }
    
    return  minLadderLength; 
  }
  
  private int ladderLength_iterance(String start, String end, HashSet<String> dict) {
    if(start.equals(end))
      return 1;  // 2 ??
    
    HashSet<String> allWords = new HashSet<String>(); 
    LinkedList<String> currWords= new LinkedList<String>();
    allWords.add(start);
    currWords.add(start);
    int count = 1, wordLength = start.length();
    
    String thisWord, tmpWord, tmpWordRight, tmpWordLeft;
    while(!currWords.isEmpty()){
      count ++;

      for(int i=currWords.size(); i>0; i--){
        thisWord = currWords.pop(); 
        
        for(int j=0; j<wordLength; j++){
          tmpWordRight = thisWord.substring(0, j);
          tmpWordLeft = thisWord.substring(j+1, wordLength);
          
          for(char c='A'; c<='Z'; c++){
            //tmpWord = getNewStr(thisWord, c, j, wordLength);
            tmpWord = tmpWordRight + c + tmpWordLeft;
            
            if(!allWords.contains(tmpWord) && dict.contains(tmpWord)){
              if(tmpWord.equals(end))
                return count;              
              
              allWords.add(tmpWord);
              currWords.addLast(tmpWord);
              
            }              
          }          
        }

      }
    }
    
    return 0;
  }  


	public int ladderLength_n(String start, String end, Set<String> dict) {
		if (null == start || null == end || start.length() != end.length()
				|| null == dict ) {
			return 0;
		}

		Set<String> visited = new HashSet<>();
		Queue<String> queue = new LinkedList<>();
		visited.add(start);
		queue.add(start);

		String currWord, tmpWord;
		char[] currChars;
		char originChar;
		int count = 1;
		while (!queue.isEmpty()) {
			count++;

			for (int i = queue.size(); i > 0; i--) {
				currWord = queue.poll();
				currChars = currWord.toCharArray();

				for (int j = 0; j < currChars.length; j++) {
					originChar = currChars[j];
					for (char c = 'a'; c <= 'z'; c++) {
						if(c == originChar){
							continue;
						}
						currChars[j] = c;
						tmpWord = new String(currChars);

						if (tmpWord.equals(end)) {
							return count;
						}

						if (!visited.contains(tmpWord) && dict.contains(tmpWord)) {
							visited.add(tmpWord);
							queue.offer(tmpWord);
						}
					}
					currChars[j] = originChar;
				}

			}
		}

		return 0;
	}
  
	/**
	 * Solution: Graph BFS 这道题想明白后非常简单，其实就是求最短路径问题，自然是BFS方法，其实问题可以用Graph来很好的解释。
	 * 顶点是每个字符串，如果相差一个字符，我们就可以连一条边， 一个字符串的边的数量最大值可能是 25 * L. 然后连线，形成Graph,
	 * 这样就是start - end的最短路径问题. 每次我们可以从start 出发，找adjacent string, 然后 enqueue,
	 * 下次再遍历下一层，这样第一次到end的时候，shortest = length + 1.
	 * 
	 * 这题目的特点: 1. dict来代替BFS 中 visited 标记，直接remove from dict 就代表遍历过了 或者 不存在 2.
	 * 都小写字母, 字符串长度固定. 问题简单化(如果不固定，就不是只换一个char这种简单情形了，会复杂的多，跟这题也会大不相同)
	 * 
	 * Time Complexity. 有点不太确定 最差情况: 对于每一个词, 查询应该是26*wordLength.
	 * 然后一直遍历完所有dict才找到答案. O(dict.size * 26*wordLength) Space 只需要一个Queue
	 * 存储邻接点，最大是dict的size, 因为dict不会是规模的，所以算是O(1)
	 * 
	 */

	/* BFS is much better than DFS*/
	public int ladderLength_x(String start, String end, Set<String> dict) {
		if (null == start || null == end || start.length() != end.length()
				|| null == dict ) {
			return 0;
		}

		Queue<String> queue = new LinkedList<String>();
		queue.offer(start);
		dict.remove(start);
		
		String tmpWord;
		char[] chars;
		char currChar;
		int count = 1;
		while (!queue.isEmpty()) {
			count++;

			for (int i = queue.size(); i > 0; i--) {
			    chars = queue.poll().toCharArray();

				for (int j = 0; j < chars.length; j++) {
					currChar = chars[j];
					
					for (char c = 'a'; c <= 'z'; c++) {
						chars[j] = c;
						tmpWord = new String(chars);
						
						if (tmpWord.equals(end)) {
							return count;
						}

						if (dict.contains(tmpWord)) {
							queue.offer(tmpWord);
							dict.remove(tmpWord);
						}
					}
					
					chars[j] = currChar;
				}
			}
		}
		return 0;
	}


  /*
   * TODO??
   *  Given two words (start and end), and a dictionary, find all shortest transformation sequence(s) from start to end, such that:
   *  
   *  Only one letter can be changed at a time
   *  Each intermediate word must exist in the dictionary
   *  For example,
   *  
   *  Given:
   *  start = "hit"
   *  end = "cog"
   *  dict = ["hot","dot","dog","lot","log"]
   *  
   *  Return
   *  
   *  [
   *    ["hit","hot","dot","dog","cog"],
   *    ["hit","hot","lot","log","cog"]
   *  ]
   *  Note:
   *  
   *  All words have the same length.
   *  All words contain only lowercase alphabetic characters.
   */
	public List<List<String>> findLadders_all(String start, String end, Set<String> dict) {
		//init return value
		List<List<String>> result = new ArrayList<>();
		
		//check
		if (null == start || null == end || start.length() != end.length() || null == dict) {
			return result;
		}
		
		if (start.equals(end)) {
			ArrayList<String> list = new ArrayList<String>();
			list.add(start);
			list.add(end);

			result.add(list);
			return result;
		}

		//BFS, find out the whole graph
		Map<String, Set<String>> preNodes = new HashMap<>();// word and his parents
		dict.add(end); //for case, end is not in dict
		dict.remove(start); //ignore the pre Nodes before the start
		
		Set<String> visited = new HashSet<>();
		Queue<String> queue = new LinkedList<>();
		visited.add(start); //
		visited.add(end); //ignore the next Nodes after the end
		queue.add(start);

		String currWord, tmpWord;
		char[] currChars;
		char originChar;
		while (!queue.isEmpty()) {
			for (int i = queue.size(); i > 0; i--) {
				currWord = queue.poll();
				currChars = currWord.toCharArray();

				for (int j = 0; j < currChars.length; j++) {
					originChar = currChars[j];
					for (char c = 'a'; c <= 'z'; c++) {
						if(c == originChar){
							continue;
						}
						currChars[j] = c;
						tmpWord = new String(currChars);
							
						if(dict.contains(tmpWord)){
							if(!preNodes.containsKey(tmpWord)){
								preNodes.put(tmpWord, new HashSet<>());
							}
							preNodes.get(tmpWord).add(currWord);
							
							if (!visited.contains(tmpWord)) {
								visited.add(tmpWord);
								queue.offer(tmpWord);
							}
						}
					}
					currChars[j] = originChar;
				}
			}
		}

		if(preNodes.containsKey(end)){
			//DFS, find all the path
			Map<String, List<String>> preNodes2 = new HashMap<>();
			Map<String, Integer> status = new HashMap<>();
			Stack<String> stack = new Stack<>();
			stack.add(end);
			status.put(end, 0);
			
			while(!stack.isEmpty()){
				currWord = stack.peek();
				
				if(!preNodes2.containsKey(currWord)){
					preNodes2.put(currWord, new ArrayList<String>(preNodes.get(currWord)));
				}
				
				if(status.get(currWord) < preNodes2.get(currWord).size()){
					tmpWord = preNodes2.get(currWord).get(status.get(currWord));
					
					if( tmpWord.equals(start)){
					    List<String> paths = new ArrayList<>();
					    paths.add(start);
					    
					    for(int i = stack.size() - 1; i>=0; i--){
						    paths.add(stack.get(i));
					    }

						result.add(paths);
					}else{
						if(!status.containsKey(tmpWord)){ 
							stack.add(tmpWord);
							status.put(tmpWord, 0);
						} //else  //found loop
					}
					
					status.put(currWord, status.get(currWord) + 1);
				}else{
					stack.pop();
					status.remove(currWord);
				}
			}
		}
		
		return result;
	}
  
	public List<List<String>> findLadders_allShortest(String start, String end, Set<String> dict) {
		//init return value
		List<List<String>> result = new ArrayList<>();
		
		//check
		if (null == start || null == end || start.length() != end.length() || null == dict) {
			return result;
		}

		if (start.equals(end)) {
			ArrayList<String> list = new ArrayList<String>();
			list.add(start);
			list.add(end);

			result.add(list);
			return result;
		}	
		
		//BFS, find out the whole graph
		Map<String, Set<String>> preNodes = new HashMap<>();// word and his parents
		dict.add(end); //for case, end is not in dict
		dict.remove(start); //ignore the pre Nodes before the start
		
		Set<String> visited = new HashSet<>();
		Set<String> currLayer = new HashSet<>();
		Queue<String> queue = new LinkedList<>();
		visited.add(start); //
		//visited.add(end); //ignore the next Nodes after the end
		queue.add(start);

		String currWord, tmpWord;
		char[] currChars;
		char originChar;
		boolean isBreak = false;
		while (!queue.isEmpty() && !isBreak) {
			for (int i = queue.size(); i > 0; i--) {
				currWord = queue.poll();
				currChars = currWord.toCharArray();

				for (int j = 0; j < currChars.length; j++) {
					originChar = currChars[j];
					for (char c = 'a'; c <= 'z'; c++) {
						if(c == originChar){
							continue;
						}
						currChars[j] = c;
						tmpWord = new String(currChars);

						if (tmpWord.equals(end)) {
							isBreak = true;
						}
							
                        if (dict.contains(tmpWord)
                                    && !visited.contains(tmpWord)) {
                            if (!preNodes.containsKey(tmpWord)) {
                                preNodes.put(tmpWord, new HashSet<>());
                            }
                            preNodes.get(tmpWord).add(currWord);

                            currLayer.add(tmpWord);
                            queue.offer(tmpWord);
                        }
					}
				
					currChars[j] = originChar;	
				}
			}
			
            visited.addAll(currLayer); //needn't re-visit in the latter layer
            currLayer.clear();
		}

		if(preNodes.containsKey(end)){
			//BFS, find all the shortest path, need pay attention to the loop
			queue.clear();
			queue.offer(end);
			HashMap<String, List<List<String>>> paths = new HashMap<>();
			paths.put(end, build(end));
			isBreak = false;
			
			while(!queue.isEmpty() && !isBreak){
				for (int i = queue.size(); i > 0; i--) {
					currWord = queue.poll();

					for (Iterator<String> iter = preNodes.get(currWord).iterator(); iter.hasNext(); ) {
						tmpWord = iter.next();
						
						if(tmpWord.equals(start)){
							isBreak = true;
							
							for(List<String> list : paths.get(currWord)){
							    List<String> path = new ArrayList<>();
							    path.add(start);
							    
							    for(int j = list.size() - 1; j>=0; j--){
								    path.add(list.get(j));
							    }

							    //path.add(end);
								result.add(path);
							}
						}else{
							if(paths.containsKey(tmpWord)){
								if(null == paths.get(currWord) || paths.get(currWord).get(0).size() + 1 ==  paths.get(tmpWord).get(0).size() ){
									paths.get(tmpWord).addAll(build(tmpWord, paths.get(currWord)));
								}
							}else{
								paths.put(tmpWord, build(tmpWord, paths.get(currWord)));
								queue.offer(tmpWord);
							}
						}
					}
				}
			}
		}
		
		return result;
	}
	
   private List<List<String>> build(String word) {
       List<String> path = new ArrayList<>();
       path.add(word);
       
        List<List<String>> paths = new ArrayList<>();

        paths.add(path);

        return paths;
    }

    private List<List<String>> build(String word, List<List<String>> paths) {
        if(null == paths){
            return build(word);
        }
        
        List<List<String>> newPaths = new ArrayList<>();

        for (List<String> path : paths) {
            List<String> newPath = new ArrayList<>(path);
            newPath.add(word);
            newPaths.add(newPath);
        }

        return newPaths;
    }
	    
    
    public List<List<String>> findLadders_allShortest_x(String start,
                                                        String end,
                                                        Set<String> dict) {
        // init return value
        List<List<String>> result = new ArrayList<>();

        // check
        if (null == start || null == end || start.length() != end.length() || null == dict) {
            return result;
        }

        if (start.equals(end)) {
            ArrayList<String> list = new ArrayList<String>();
            list.add(start);
            list.add(end);

            result.add(list);
            return result;
        }

        // BFS, find out the whole graph
        Map<String, Set<String>> preNodes = new HashMap<>();// word and his parents
        dict.add(end); // for case, end is not in dict
        dict.remove(start); // ignore the pre Nodes before the start

        Set<String> visited = new HashSet<>();
        Set<String> currLayer = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        visited.add(start); //
        queue.add(start);

        String currWord, tmpWord;
        char[] currChars;
        char originChar;
        boolean isBreak = false;
        while (!queue.isEmpty() && !isBreak) {
            for (int i = queue.size(); i > 0; i--) {
                currWord = queue.poll();
                currChars = currWord.toCharArray();

                for (int j = 0; j < currChars.length; j++) {
                    originChar = currChars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originChar) {
                            continue;
                        }
                        currChars[j] = c;
                        tmpWord = new String(currChars);

                        if (tmpWord.equals(end)) {
                            isBreak = true;
                        }

                        if (dict.contains(tmpWord)
                                    && !visited.contains(tmpWord)) {
                            if (!preNodes.containsKey(tmpWord)) {
                                preNodes.put(tmpWord, new HashSet<>());
                            }
                            preNodes.get(tmpWord).add(currWord);

                            currLayer.add(tmpWord);
                            queue.offer(tmpWord);
                        }
                    }
                    currChars[j] = originChar;
                }
            }

            visited.addAll(currLayer); // needn't re-visit in the latter layer
            currLayer.clear();
        }

        if (preNodes.containsKey(end)) {
            // DFS, find all the shortest path
            backTrace(result, new LinkedList<>(), end, start, preNodes);
        }

        return result;
    }

    private void backTrace(List<List<String>> result,
                           List<String> path,
                           String word,
                           String start,
                           Map<String, Set<String>> preNodes) {
        if (word.equals(start)) {
            path.add(0, start);
            result.add(new ArrayList<String>(path));
            path.remove(0);
            return;
        }

        path.add(0, word);
        if (preNodes.get(word) != null) {
            for (String s : preNodes.get(word)) {
                backTrace(result, path, s, start, preNodes);
            }
        }
        path.remove(0);
    }
	
	
    /**
     * @param start, a string
     * @param end, a string
     * @param dict, a set of string
     * @return a list of lists of string
     */
	/* compare to findLadders_allShortest(), it takes more memory space */
   public List<List<String>> findLadders_allShortest_2(String start, String end, Set<String> dict) {
       //init
       List<List<String>> ret = new ArrayList<>();
       
       //check
       if(null == start || 0 == start.length() || null == end || start.length() != end.length()){
           return ret;
       }
       
       //main,  bfs
       Queue<List<String>> queue = new LinkedList<List<String>>();
       queue.offer(Arrays.asList(new String[]{start}));
       dict.remove(start);
       
       List<String> list;
       List<String> tmpList;
       String word;
       char[] chars;
       char currChar;
       
       boolean isCompleted = false;
       while(!queue.isEmpty() && !isCompleted){
           for(int i=queue.size(); i>0; i--){
               list = queue.poll();
               chars = list.get(list.size() - 1).toCharArray();
               
               for(int j=0; j<chars.length; j++){
                   currChar = chars[j];
                   
                   for(char c = 'a'; c <= 'z'; c++){
                	   if(c == currChar){
                		   continue;
                	   }
                	   
                       chars[j] = c;
                       word = new String(chars);
                       
                       tmpList = new ArrayList<String>(list);
                       if(word.equals(end)){
                           isCompleted = true;
                           
                           tmpList.add(end);
                           ret.add(tmpList);
                       } else if(dict.contains(word)){
                           tmpList.add(word);
                           
                           queue.offer(tmpList);
                       }
                   }
                   
                   chars[j] = currChar;
               }
           }
       }
       
       //return
       return ret;
   }
  /**
   * @param args
   */
  public static void main(String[] args) {
    //init
	String[] starts	= {"a","hot", "hit"};
	String[] ends	= {"c","dog", "cog"};
	String[][] dicts = {{"a", "b", "c"},{"hot","dog","dot"}, {"hot","dot","dog","lot","log"}};
	
    WordLadder sv = new WordLadder();

    for(int i = 0; i < starts.length; i++){
        System.out.println("\nconvert " + starts[i] +" to "+ ends[i] +": ");
        
        System.out.println(sv.ladderLength_n(starts[i], ends[i], sv.convert(dicts[i])));
        System.out.println(sv.ladderLength_x(starts[i], ends[i], sv.convert(dicts[i])));
        
        System.out.println(sv.findLadders_all(starts[i], ends[i], sv.convert(dicts[i])));
        
        System.out.println(sv.findLadders_allShortest(starts[i], ends[i], sv.convert(dicts[i])));
        
        System.out.println(sv.findLadders_allShortest_x(starts[i], ends[i], sv.convert(dicts[i])));
        
        System.out.println(sv.findLadders_allShortest_2(starts[i], ends[i], sv.convert(dicts[i])));
    }

//    *    ["hit","hot","dot","dog","cog"],
//    *    ["hit","hot","lot","log","cog"]
//    		
//    [hit, hot, dot, dog, cog]
//    [hit, hot, dot, dog, log, cog], 
//    [hit, hot, dot, lot, log, dog, cog], 
//    [hit, hot, dot, lot, log, cog], 
//    
//    [hit, hot, lot, log, cog], 
//    [hit, hot, lot, log, dog, cog], 
//    [hit, hot, lot, dot, dog, cog], 
//    [hit, hot, lot, dot, dog, log, cog], 
    		
    /*basic testing*/
    System.out.println();
    
    int tmp = Integer.MAX_VALUE;
    System.out.println(tmp);
    tmp --;
    System.out.println(Math.min(tmp ,Integer.MAX_VALUE));
    tmp = tmp + 1;
    System.out.println(tmp);
    System.out.println(tmp < Integer.MAX_VALUE);
    
    //stack
    Stack<String> stack = new Stack<>();
    stack.add("111");
    stack.add("222");
    stack.add("333");
    System.out.println(stack.toString());
    List<String> list = new ArrayList<>();
    list.add("000");
    list.addAll(stack);
    System.out.println(list.toString());
    
    for(int i = stack.size() - 1; i>= 0; i--){
	    list.add(stack.get(i));
    }
    
    System.out.println(list.toString());
    
    //queue
    List<String> queue = new LinkedList<>();
    queue.add("111");
    queue.add("222");
    queue.add("333");
    System.out.println(queue.toString());
    List<String> list2 = new ArrayList<>();
    list2.add("000");
    list2.addAll(queue);
    System.out.println(list.toString());
    
    for(int i = queue.size() - 1; i>= 0; i--){
	    list.add(queue.get(i));
    }
    
    System.out.println(list.toString());
    
    //new ArrayList<>(paths)
    List<String> paths = new ArrayList<>();
    paths.add("000");
    paths.add("001");
    
	List<String> newPaths = new ArrayList<>(paths);
	newPaths.add("111");
	
	System.out.println(paths);
	System.out.println(newPaths);

	List<List<String>> newPaths1 = new ArrayList<>();
	newPaths1.add(paths);
	newPaths1.add(newPaths);
	List<List<String>> newPaths2 = new ArrayList<>(newPaths1);
	for (List<String> path : newPaths2) {
		path.add("newPaths2");
	}

	List<List<String>> newPaths3 = new ArrayList<>();
	for (List<String> path : newPaths1) {
		List<String> newPath = new ArrayList<>(path);
		newPath.add("newPaths3");
		newPaths3.add(newPath);
	}
	
	System.out.println(paths);
	System.out.println(newPaths);
  }
  
  private Set<String> convert(String[] dict){
	  Set<String> ret = new HashSet<>();
	  for(String word : dict){
		  ret.add(word);
	  }
	  
	  return ret;
  }

}
