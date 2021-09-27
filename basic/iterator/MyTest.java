package basic.iterator;

import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyTest {

    @Test
    public void testIteratorAndRemove(){

        Set<Integer> set = new HashSet<>();

        set.add(11);
        set.add(22);

        for(Iterator<Integer> iterator = set.iterator(); iterator.hasNext(); ){

            int curr = iterator.next();
            System.out.println(curr);

            iterator.remove();  // this is ok

            //java.util.ConcurrentModificationException,
            //set.add(iterator.next() +  100); // only remove is ok during iteratoring
        }

        System.out.println( set.size() );

    }

}
