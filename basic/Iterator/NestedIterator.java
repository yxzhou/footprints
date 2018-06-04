package fgafa.basic.Iterator;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 
 * Given a nested list of integers, implement an iterator to flatten it.
    
    Each element is either an integer, or a list -- whose elements may also be integers or other lists.
    
    Example 1:
    Given the list [[1,1],2,[1,1]],
    
    By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,1,2,1,1].
    
    Example 2:
    Given the list [1,[4,[6]]],
    
    By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,4,6].
 * 
 */

public class NestedIterator implements Iterator<Integer> {

    Stack<Iterator> stack = new Stack<>();
    
    public NestedIterator(List<List> nestedList) {
        stack.add(nestedList.iterator());
    }
    
    
    @Override
    public boolean hasNext() {
        while(!stack.isEmpty()){
            Iterator it = stack.peek();
            if(it.hasNext()){
                return true;
            }else{
                stack.pop();
            }
        }
        
        return false;
    }

    @Override
    public Integer next() {
        while(!stack.isEmpty()){
            Iterator it = stack.peek();
            if(it.hasNext()){
                Object curr = it.next();
                
                if(curr instanceof List){
                    stack.pop();
                    stack.add(((List) curr).iterator());
                    
                    return next();
                }else{
                    return (Integer)curr;
                }
            }else{
                stack.pop();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
