package fgafa.basic.Iterator;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * 
 * Implement a Java Iterator for a nested HashMap in which an element can be any
 * of 1) an Integer or, 2) another nested HashMap or, 3) an empty HashMap
 * 
 */
public class NestedMap<K, V> extends HashMap<K,V> implements Map<K,V>{

	private static final long serialVersionUID = 6746286626929712719L;

	public Iterator<V> iterator() {
        return new Itr(this.values().iterator());
    }
    
    private class Itr implements Iterator<V> {
    	Stack<Iterator<V>> stack = new Stack<>();
    	
    	public Itr(Iterator<V> values){
    		stack.add(values);
    	}

        public boolean hasNext() {
            return !stack.isEmpty() && stack.peek().hasNext();
        }

        @SuppressWarnings("unchecked")
		public V next() {
			Iterator<V> curr = stack.pop();
			if (curr.hasNext()) {
				V v;

				try {
					v = (V) curr.next();
				} catch (NoSuchElementException e) {
					throw new ConcurrentModificationException();
				}

				if (v instanceof Map) {
					Map<K, V> map = (Map<K, V>) v;

					if (0 == map.size()) { // empty HashMap
						return null;
					} else { // not empty HashMap
						stack.add(map.values().iterator());
						return next();
					}
				} else { // Integer
					return v;
				}
			} else {
				return next();
			}
		}
        
        public void remove() {
        	//TODO
        }
        
//        final void checkForComodification() {
//            if (modCount != expectedModCount)
//                throw new ConcurrentModificationException();
//        }
    }
	
}


