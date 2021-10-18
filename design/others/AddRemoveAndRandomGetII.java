package design.others;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 
 * Continue AddARemoveAndRandomGet
 * 
 * follow up, the data maybe duplicated 
 *
 */

public class AddRemoveAndRandomGetII {
    Map<Integer, Set<Integer>> positions ; //Map< value, Set<position> >
    //final int ENOUGH = Integer.MAX_VALUE;
    //int[] values = new int[ENOUGH];
    List<Integer> values ; // List< value >
    
    Random random;
    
    public AddRemoveAndRandomGetII(){
        positions = new HashMap<>();
        values = new ArrayList<>();
        
        random = new Random();
    }
    
    public boolean add(int val){
        //support the data maybe duplicated 
//        if(positions.containsKey(val) && !positions.get(val).isEmpty()){
//            return false;
//        }
        
        positions.putIfAbsent(val, new HashSet<>());
        positions.get(val).add(values.size());
        
        return values.add(val);
    }
    
    public boolean remove(int val){
        if(!positions.containsKey(val) || positions.get(val).isEmpty()){
            return false;
        }
        
        int p = positions.get(val).iterator().next();
        int tail = values.size() - 1;
        int tailValue = values.get(tail);
        
        values.remove(tail);
        positions.get(val).remove(p);
        positions.get(tailValue).remove(tail);
        
        if(p != tail){ //if the p is the tail, needn't add it back. Note it's p != tail instead of val != tailValue
            values.set(p, tailValue);
            positions.get(tailValue).add(p); 
        }
        
        return true;
    }
    
    public int getRandom(){
        //how to do if array is empty ?
        assert !values.isEmpty();
                
        int p = random.nextInt(values.size());
        
        return values.get(p);
    }
}
