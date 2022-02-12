package basic.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Vector2DII2 implements Iterator<Integer>{

    List<List<Integer>> vec2d;
    int r;
    int c;

    public Vector2DII2(List<List<Integer>> vec2d) {

        if(vec2d == null){
            return;
        }

        this.vec2d = vec2d;
        this.r = 0;
        this.c = 0;
    }

    @Override
    public Integer next() {
        if(!hasNext()){
            throw new NoSuchElementException();
        }
        
        Integer result = vec2d.get(r).get(c);
        c++;
        return result;
    }

    @Override
    public boolean hasNext() {
        
        while(r < vec2d.size() && (vec2d.get(r) == null || c >= vec2d.get(r).size())){
            r++;
            c = 0;
        }

        return r < vec2d.size() && c < vec2d.get(r).size();
    }

    @Override
    public void remove() {
        if(!hasNext()){
            throw new IllegalStateException();
        }
        
        vec2d.get(r).remove(c);
    }
    
}
