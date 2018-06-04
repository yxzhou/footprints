package fgafa.basic.Iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ZigzagIteratorII {
    int count;
    List<Iterator> iter;
 
    public ZigzagIteratorII(List<List<Integer>> lists) {
        this.count = 0;
        
        this.iter = new ArrayList<Iterator>();
        if (null != lists ){
            for(List<Integer> list : lists){
                if(null != list && !list.isEmpty()){
                    iter.add(list.iterator());
                }
            }
        }
    }
 
    public int next() {
        int x = (Integer)iter.get(count).next();
        
        if (iter.get(count).hasNext()){
            count++;
        }else{
            iter.remove(count);
        }

        if (iter.size()!=0){
            count = count % iter.size();  //**
        }
        
        return x;
    }
 
    public boolean hasNext() {
        return iter.size()!=0;
    }
}
