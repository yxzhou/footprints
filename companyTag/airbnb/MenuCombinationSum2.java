package companyTag.airbnb;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 */

public class MenuCombinationSum2 {

    /**
     * DP, backpack
     * 
     * 
     * @param prices
     * @param target
     * @return the combination of items that sum to the money
     */
    public List<List<Double>> menuOrder_Array(double[] prices, double money) {
        
        if (prices == null) {
            return Collections.EMPTY_LIST;
        }
        
        int m = (int)(money * 100);
        List<List<Integer>>[] dp = new ArrayList[m + 1];
        dp[0] = new ArrayList<>();
        dp[0].add(new ArrayList<>());

        int p;
        List<Integer> intList;
        for(double price : prices){
            p = (int)(price * 100);
            
            for(int l = m - p, r; l >= 0; l--){
                if(dp[l] == null){
                    continue;
                }
                
                r = l + p;
                if(dp[r] == null){
                    dp[r] = new ArrayList<>();
                }
                
                for(List<Integer> sub : dp[l]){
                    intList = new ArrayList<>(sub);
                    intList.add(p);
                    
                    dp[r].add(intList);
                }
            }
        }
        
        if (dp[m] == null) {
            return Collections.EMPTY_LIST;
        }
        
        List<List<Double>> result = new ArrayList<>();
        List<Double> doubleList;
        for(List<Integer> sub : dp[m]){
            doubleList = new ArrayList<>(sub.size());
            
            for(int x : sub){
                doubleList.add((double)x / 100 );
            }
            
            result.add(doubleList);
        }
        
        return result;
    }

    public List<List<Double>> menuOrder_Map(double[] prices, double money) {
        
        if (prices == null) {
            return Collections.EMPTY_LIST;
        }
        
        int m = (int)(money * 100);
        Map<Integer, List<List<Integer>>> dp = new HashMap<>();//<key, value>, the value is combinations that sum are same, key is the sum 
        dp.computeIfAbsent(0, a -> new ArrayList<>()).add(new ArrayList<>());

        int p;
        List<Integer> intList;
        List<List<Integer>> lists;
        for(double price : prices){
            p = (int)(price * 100);
            
            for(int l = m - p, r; l >= 0; l--){
                if(!dp.containsKey(l)){
                    continue;
                }
                
                r = l + p;

                lists = dp.computeIfAbsent(r, a -> new ArrayList<>());
                
                for(List<Integer> sub : dp.get(l)){
                    intList = new ArrayList<>(sub);
                    intList.add(p);
                    
                    lists.add(intList);
                }
            }
        }
        
        if (!dp.containsKey(m)) {
            return Collections.EMPTY_LIST;
        }
        
        List<List<Double>> result = new ArrayList<>();
        List<Double> doubleList;
        for(List<Integer> sub : dp.get(m)){
            doubleList = new ArrayList<>(sub.size());
            
            for(int x : sub){
                doubleList.add((double)x / 100 );
            }
            
            result.add(doubleList);
        }
        
        return result;
    }
    

    public static void main(String[] args){
        
        //test ArrayList
        List<Integer> list = new ArrayList<>(4);
        //list.set(1, 1); // java.lang.IndexOutOfBoundsException
        //System.out.println( list.get(2) ); //java.lang.IndexOutOfBoundsException
        
        List<Integer>[] dp = new ArrayList[4];
        dp[1] = new ArrayList<>();
        System.out.println( dp[2] == null );
        
        
        
    }

}
