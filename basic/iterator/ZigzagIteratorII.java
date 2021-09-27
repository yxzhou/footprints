package basic.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ZigzagIteratorII {
    int n;

    int state;
    List<Iterator<Integer>> iter;
 
    public ZigzagIteratorII(List<List<Integer>> lists) {
        this.iter = new LinkedList<>();

        if (null != lists ){
            for(List<Integer> list : lists){
                if(null != list && !list.isEmpty()){
                    iter.add(list.iterator());
                }
            }
        }

        this.n = iter.size();

        this.state = 0;
    }
 
    public int next() {
        int x = -1;

        for(int i = 0; i < iter.size(); i++){
            if(iter.get(state).hasNext()){
                x = iter.get((state)).next();
                break;
            }else{
                iter.remove(state);
                continue;
            }
        }

        if (iter.size() != 0){
            state = (state + 1) % iter.size();
        }
        
        return x;
    }
 
    public boolean hasNext() {
        for(int i = 0; i < iter.size(); i++){
            if(iter.get(i).hasNext()){
                return true;
            }
        }

        return false;
    }
}
