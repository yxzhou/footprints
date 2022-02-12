package basic.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * _https://www.lintcode.com/problem/541
 * 
 * Follow up ZigzagIterator: What if you are given k 1d vectors? How well can your code be extended to such cases? The
 * "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases. If "Zigzag" does not look right to you,
 * replace "Zigzag" with "Cyclic".
 *
 * Example1
 * Input: k = 3 vecs = [ [1,2,3], [4,5,6,7], [8,9], ] 
 * Output: [1,4,8,2,5,9,3,6,7]
 *
 * Example2 
 * Input: k = 3 vecs = [ [1,1,1] [2,2,2] [3,3,3] ] 
 * Output: [1,2,3,1,2,3,1,2,3]
 *
 */

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
