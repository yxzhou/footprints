package basic.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Vector2DII implements Iterator<Integer>{

    Queue<Iterator<Integer>> iterators;

    public Vector2DII(List<List<Integer>> vec2d) {

        iterators = new LinkedList<>();
        if(vec2d == null){
            return;
        }

        for(List<Integer> list : vec2d){
            
            if(list != null && !list.isEmpty()){
                iterators.add(list.iterator());
            }
        }
    }

    @Override
    public Integer next() {
        return iterators.peek().next();
    }

    @Override
    public boolean hasNext() {
        while(!iterators.isEmpty() && !iterators.peek().hasNext()){
            iterators.poll();
        }

        return !iterators.isEmpty() && iterators.peek().hasNext();
    }

    @Override
    public void remove() {
        iterators.peek().remove();
    }
    
}
