package basic.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Vector2DII {

    List<Iterator<Integer>> its;
    int curr = 0;
    
    public Vector2DII(List<List<Integer>> vec2d) {
        this.its = new ArrayList<>();
        for(List<Integer> l : vec2d){
            // 只将非空的迭代器加入数组
            if(l.size() > 0){
               this.its.add(l.iterator()); 
            }
        }
    }

    public int next() {
        Integer res = its.get(curr).next();
        // 如果该迭代器用完了，换到下一个
        if(!its.get(curr).hasNext()){
            curr++;
        }
        return res;
    }

    public boolean hasNext() {
        return curr < its.size() && its.get(curr).hasNext();
    }
    
}
