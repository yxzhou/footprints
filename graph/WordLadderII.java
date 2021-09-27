package graph;

import java.util.*;

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

public class WordLadderII {

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
	
    WordLadderII sv = new WordLadderII();

    for(int i = 0; i < starts.length; i++){
        
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
