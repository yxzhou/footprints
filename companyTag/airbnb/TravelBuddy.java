/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * I have a wish list of cities that I want to visit to, my friends also have city wish lists that they want to visit
 * to. If one of my friends share more than 50% (over his city count in his wish list), he is my buddy.
 *
 * Given a list of city wish list, output buddy list sorting by similarity.
 *
 */
public class TravelBuddy {
    public List<String> getTravelBuddies(Set<String> myWishList, Map<String, Set<String>> friendsWishList){
        
        if(myWishList == null || friendsWishList == null){
            return Collections.EMPTY_LIST;
        }
        
        int n = myWishList.size();
        TreeMap<Integer, Set<String>> treeMap = new TreeMap<>(); //map<similarity, Set<friends>>
              
        for(Map.Entry<String, Set<String>> entry : friendsWishList.entrySet()){
            int count = 0;
            
            for(String wish : entry.getValue()){
                if(myWishList.contains(wish)){
                    count++;
                }
            }
            
            if(count * 2 >= n){
                treeMap.computeIfAbsent(count, o -> new HashSet<>()).add(entry.getKey());
            }
        }
        
        List<String> buddies = new ArrayList<>();
        
        for(Map.Entry<Integer, Set<String>> entry : treeMap.descendingMap().entrySet()) {
            buddies.addAll(entry.getValue());
        }
        
        return buddies;
    }
    
    
    public List<String> recommendCities(Set<String> myWishList, Map<String, Set<String>> friendsWishList) {
        
        List<String> buddies =  getTravelBuddies(myWishList, friendsWishList);
        
        Map<String, Integer> cities = new HashMap<>();
        
        for(String buddy : buddies){
            for( String wish : friendsWishList.get(buddy)){
                if(!myWishList.contains(wish)){
                    cities.put(wish, cities.getOrDefault(wish, 0) + 1);
                }
            }
        }
        
        List<String> result = new ArrayList<>(cities.keySet());
        
        Collections.sort(result, (a, b) -> Integer.compare(cities.get(b), cities.get(a)));
        
        return result;
    }
    
    
    public static void main(String[] args){
        
        String[][][] inputs = {
            {
                {"a", "b", "c", "d"},
                {"f1","a", "b", "e", "f"},
                {"f2","a", "c", "d", "g"},
                {"f3","c", "f", "e", "g"},
                    
            }
        };

        TravelBuddy sv = new TravelBuddy();
        
        for(String[][] input : inputs){
            Set<String> myWishList = new HashSet<>(Arrays.asList(input[0]));
            
            Map<String, Set<String>> friendsWishList = new HashMap<>();
            for(int i = 1; i < input.length; i++){
                friendsWishList.put(input[i][0], new HashSet<>(Arrays.asList(Arrays.copyOfRange(input[i], 1, input[i].length))) );
            }
            
            System.out.println( sv.getTravelBuddies(myWishList, friendsWishList) );
            
            System.out.println( sv.recommendCities(myWishList, friendsWishList) );
        }
    }
    
}
