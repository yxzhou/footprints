package companyTag.airbnb;

import java.util.*;

/**
 *
 * You’re given an array of CSV strings representing search results. Results are sorted by a score initially. 
 * A given host may have several listings that show up in these results. 
 *   1) Suppose we want to show 12 results per page, 
 *   2) but we don’t want the same host to dominate the results. Write a function that will reorder the list so that a 
 *      host shows up at most once on a page if possible, but otherwise
 *   3) preserves the ordering. 
 * 
 * Your program should return the new array and print out the results in blocks representing the pages.
 *
 * Example #1
 "host_id, listing_id, score, city",  show 6 results per page

 "1,28,300.1,San Francisco",
 "4,5,209.1,San Francisco",
 "20,7,208.1,San Francisco",
 "23,8,207.1,San Francisco",
 "16,10,206.1,Oakland",
 "1,16,205.1,San Francisco",
 "1,31,204.6,San Francisco",
 "6,29,204.1,San Francisco",
 "7,20,203.1,San Francisco",

 set<host_id>

 page1
 "1,28,300.1,San Francisco",
 "4,5,209.1,San Francisco",
 "20,7,208.1,San Francisco",
 "23,8,207.1,San Francisco",
 "16,10,206.1,Oakland",
 "6,29,204.1,San Francisco",

 page2
 "1,16,205.1,San Francisco",
 "7,20,203.1,San Francisco",

 page3
 "1,31,204.6,San Francisco",

*  
* Example #2
 "1,31,204.6,San Francisco",
 "1,31,204.6,San Francisco",
 "1,31,204.6,San Francisco",
 "1,31,204.6,San Francisco",

 one set per page
 page1
 "1,31,204.6,San Francisco",
 page2
 "1,31,204.6,San Francisco",
 page3
 "1,31,204.6,San Francisco",

* 
* 
 "host_id,listing_id,score,city",
 Test Data

 [
 "1,28,300.1,San Francisco",
 "4,5,209.1,San Francisco",
 "20,7,208.1,San Francisco",
 "23,8,207.1,San Francisco",
 "16,10,206.1,Oakland",
 "1,16,205.1,San Francisco",
 "1,31,204.6,San Francisco",
 "6,29,204.1,San Francisco",
 "7,20,203.1,San Francisco",
 "8,21,202.1,San Francisco",
 "2,18,201.1,San Francisco",
 "2,30,200.1,San Francisco",
 "15,27,109.1,Oakland",
 "10,13,108.1,Oakland",
 "11,26,107.1,Oakland",
 "12,9,106.1,Oakland",
 "13,1,105.1,Oakland",
 "22,17,104.1,Oakland",
 "1,2,103.1,Oakland",
 "28,24,102.1,Oakland",
 "18,14,11.1,San Jose",
 "6,25,10.1,Oakland",
 "19,15,9.1,San Jose",
 "3,19,8.1,San Jose",
 "3,11,7.1,Oakland",
 "27,12,6.1,Oakland",
 "1,3,5.1,Oakland",
 "25,4,4.1,San Jose",
 "5,6,3.1,San Jose",
 "29,22,2.1,San Jose",
 "30,23,1.1,San Jose"
 ]
 *
 * Thoughts:
 *   To every string s,  String[] columns = s.split(","); it's {host_id,listing_id,score,city}
 *   if it's existing a pageId x for the host_id, the new pageId is x + 1.
 *     or if it's no existing a pageId x for the host_id, the new pageId is the current pageId, 
 *
 * 
 */

public class PageList {


    public List<List<String> > paging(List<String> items, int size) {
        List<List<String> > ans = new ArrayList<>();

        int n = items.size();

        //用一个set来保存是否出现，加入以后删除原有元素，不够的话顺序补充
        Set<String> st = new HashSet<>();

        for (int i = 0, limit = n / size + 1, it = 0; i < limit; i++ ) {

            List<String> tmp = new ArrayList<>();


            for ( ; it < items.size() && tmp.size() < size; ) {
                if (st.contains(items.get(it))) {
                    it++;
                    continue;
                }

                st.add(items.get(it));
                tmp.add(items.get(it));

            }

//            for ( ; it != items.end() && tmp.size() < size;) {
//                tmp.push_back(*it);
//                items.erase(it);
//            }

            ans.add(tmp);

        }
        return ans;
    }


    public List<List<String>> paging_n(String[] lines, int size) {
        List<List<String>> result = new ArrayList<>();

        if (null == lines || size < 1) {
            return result;
        }

        Map<String, Integer> pageIds = new HashMap<>();
        int currPageId = 0;
        for (String line : lines) {
            while (currPageId < result.size() && result.get(currPageId).size() == size) {
                currPageId++;
            }

            String[] tokens = line.split(",");
            String host = tokens[0];

            int pageId = currPageId;
            if (pageIds.containsKey(host) && pageId <= pageIds.get(host)){
                pageId = pageIds.get(host) + 1;

                while (pageId < result.size() && result.get(pageId).size() == size) {
                    pageId++;
                }
            }
            pageIds.put(host, pageId);

            while (result.size() <= pageId) {
                result.add(new ArrayList<>());
            }
            result.get(pageId).add(line);
        }

        return result;
    }
    
    public List<List<String>> paging_x(String[] lines, int size) {
        if(lines == null || size < 1){
            return Collections.EMPTY_LIST;
        }
        
        List<List<String>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        Map<Integer, Integer> hosts = new HashMap<>();//map<hostId, pageId>
        int pageId;
        
        int basePageId = 0;        
        int host;
        
        for(String line : lines){
            host = Integer.parseInt(line.substring(0, line.indexOf(",")));
            
            if(hosts.containsKey(host) && hosts.get(host) >= basePageId){
                pageId = hosts.get(host) + 1;
            }else{                
                if(result.get(basePageId).size() == size ){
                    basePageId++;
                }
                
                pageId = basePageId;
            }
            
            if(result.size() == pageId){
                result.add(new ArrayList<>());
            }
            
            result.get(pageId).add(line);
            hosts.put(host, pageId);
        }
        
        return result;
    }


    public static void main(String[] args) {
        String[] lines = {
                "1,28,300.1,San Francisco",
                "4,5,209.1,San Francisco",
                "20,7,208.1,San Francisco",
                "23,8,207.1,San Francisco",
                "16,10,206.1,Oakland",
                "1,16,205.1,San Francisco",
                "1,31,204.6,San Francisco",
                "6,29,204.1,San Francisco",
                "7,20,203.1,San Francisco",
                "8,21,202.1,San Francisco",
                "2,18,201.1,San Francisco",
                "2,30,200.1,San Francisco",
                "15,27,109.1,Oakland",
                "10,13,108.1,Oakland",
                "11,26,107.1,Oakland",
                "12,9,106.1,Oakland",
                "13,1,105.1,Oakland",
                "22,17,104.1,Oakland",
                "1,2,103.1,Oakland",
                "28,24,102.1,Oakland",
                "18,14,11.1,San Jose",
                "6,25,10.1,Oakland",
                "19,15,9.1,San Jose",
                "3,19,8.1,San Jose",
                "3,11,7.1,Oakland",
                "27,12,6.1,Oakland",
                "1,3,5.1,Oakland",
                "25,4,4.1,San Jose",
                "5,6,3.1,San Jose",
                "29,22,2.1,San Jose",
                "30,23,1.1,San Jose"
        };

        PageList sv = new PageList();

        System.out.println("\n---paging_n-----");
        sv.print(sv.paging_n(lines, 5));
        
        System.out.println("\n---paging_x-----");
        sv.print(sv.paging_x(lines, 5));

    }

    
    private void print(List<List<String>> result){
        for(List<String> page : result){
            System.out.println("\n-----new page----");
            for(String words : page){
                System.out.println(words);
            }
        }
    }

}
