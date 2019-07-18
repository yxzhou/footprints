package fgafa.basic.Iterator;

import java.util.*;

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

    /**
     * // This is the interface that allows for creating nested lists.
     * // You should not implement it, or speculate about its implementation
     *
     */
     public interface NestedInteger {

         // @return true if this NestedInteger holds a single integer, rather than a nested list.
         public boolean isInteger();

         // @return the single integer that this NestedInteger holds, if it holds a single integer
         // Return null if this NestedInteger holds a nested list
         public Integer getInteger();

         // @return the nested list that this NestedInteger holds, if it holds a nested list
         // Return null if this NestedInteger holds a single integer
         public List<NestedInteger> getList();
     }


    Stack<Iterator<NestedInteger>> stack;
    NestedInteger top = null;

    public NestedIterator(List<NestedInteger> nestedList) {
        stack = new Stack<>();
        stack.add(nestedList.iterator());
    }

    @Override
    public Integer next() {
        if(hasNext()){
            Integer result = top.getInteger();
            top = null;
            return result;
        }

        return null;
    }

    @Override
    public boolean hasNext() {

        if(top != null){
            return true;
        }

        while(!stack.isEmpty()){
            if(stack.peek().hasNext()){
                NestedInteger curr = stack.peek().next();

                if(curr.isInteger()){
                    top = curr;
                    return true;
                }else { //
                    stack.add(curr.getList().iterator());
                }

            }else{
                stack.pop();
            }
        }

        return false;
    }

    public static void main(String[] args) {
        NestedIterator sv;

       //[[1,1],2,[1,1]]
        List<List> nestedList = new ArrayList<>();
        nestedList.add(Arrays.asList(1, 1));
        //nestedList.add(2);
        nestedList.add(Arrays.asList(1,1));

        //sv = new NestedIterator(nestedList);


        //[1,[4,[6]]]

//        while(sv.hasNext()){
//
//        }

    }

}
