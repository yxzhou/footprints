/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.unionFind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * _https://www.lintcode.com/problem/1070/
 * 
 * Description
 * Given a list accounts, each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.
 *
 * Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some email that is common to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.
 * 
 * After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.
 *
 * Notes:
 *   The length of accounts will be in the range [1, 1000].
 *   The length of accounts[i] will be in the range [1, 10].
 *   The length of accounts[i][j] will be in the range [1, 30].
 * 
 * Example 1:
	Input:
	[
		["John", "johnsmith@mail.com", "john00@mail.com"],
		["John", "johnnybravo@mail.com"],
		["John", "johnsmith@mail.com", "john_newyork@mail.com"],
		["Mary", "mary@mail.com"]
	]
	
	Output: 
	[
		["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],
		["John", "johnnybravo@mail.com"],
		["Mary", "mary@mail.com"]
	]
        *
 * 
 */
public class AccountMerge {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        if(accounts == null){
            return Collections.EMPTY_LIST;
        }

        Map<String, String> fathers = new HashMap<>(); //Map<email, parent_email>
        Map<String, Integer> groupSize = new HashMap<>(); //Map<email, group_size>
        String email;
        String parent1;
        String parent2;
        for(List<String> account : accounts){
            parent1 = find(account.get(1), fathers);
            groupSize.put(parent1, groupSize.getOrDefault(parent1, 0) + account.size() - 1);

            for(int i = 1; i < account.size(); i++){
                email = account.get(i);
                parent2 = find(email, fathers);
                if(!parent1.equals(parent2)){
                    parent1 = union(parent1, parent2, fathers, groupSize);
                }            

                fathers.put(email, parent1);
            }
        }

        Map<String, Map<String, TreeSet<String>>> merges = new HashMap<>();
        String name;
        Map<String, TreeSet<String>> map;
        for(List<String> account : accounts){
            name = account.get(0);
            merges.putIfAbsent(name, new HashMap<>());
            map = merges.get(name);
            
            parent1 = find(account.get(1), fathers);
            map.putIfAbsent(parent1, new TreeSet<>());
            map.get(parent1).addAll(account.subList(1, account.size()));
        }

        List<List<String>> result = new ArrayList<>();
        List<String> list;
        for(Map.Entry<String, Map<String, TreeSet<String>>> merge : merges.entrySet() ){
            name = merge.getKey();
            for(TreeSet<String> entry : merge.getValue().values() ){
                list = new ArrayList<>(entry.size() + 1);
                list.add(name);
                list.addAll(entry);
                
                result.add(list);
            }
        }
        return result;
    }

    private String find(String email, Map<String, String> fathers){
        String parent;
        while( email != null ){
            parent = fathers.get(email);
        
            if(parent== null || email.equals(parent)){
                break;
            }

            //path compression
            parent = fathers.get(parent);
            fathers.put(email, parent);
            email = parent;
        }

        return email;
    }

    private String union(String p, String q, Map<String, String> fathers, Map<String, Integer> groupSize){
        int pSize = groupSize.getOrDefault(p, 1);
        int qSize = groupSize.getOrDefault(q, 1);

        if( pSize > qSize){
            fathers.put(q, p);
            groupSize.put(p, pSize + qSize);
            return p;
        }

        fathers.put(p, q);
        groupSize.put(q, pSize + qSize);
        return q;
    }
}
