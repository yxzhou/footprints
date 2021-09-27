package dailyCoding.datastructure;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Implement a stack API using only a heap. A stack implements the following methods:
*
 * push(item), which adds an element to the stack
 * pop(), which removes and returns the most recently added element (or throws an error if there is nothing on the stack)
 *
 * Recall that a heap has the following operations:
 *
 * push(item), which adds a new key to the heap
 * pop(), which removes and returns the max value of the heap
 *
 */


public class StackWithHeap<V> {

    class Item{

    }

    class Entity implements Comparator<Entity>{
        Item item;
        //int counter = 0;
        long createTime ;

        Entity(Item item){
            this.item = item;
            //counter = 0;
            createTime = System.currentTimeMillis();
        }

        @Override
        public int compare(Entity o1, Entity o2) {
            return Long.compare(o2.createTime, o1.createTime);
        }
    }

    Map<Item, Entity> pool = new HashMap<>();
    PriorityQueue<Entity> heap = new PriorityQueue<>();

    public void push(Item item){
        Entity entity;
        if(pool.containsKey(item)){
            entity = pool.get(item);

            //entity.counter += 1;
            entity.createTime = System.currentTimeMillis();
        }else{
            entity = new Entity(item);
            pool.put(item, entity);

            heap.offer(entity);
        }
    }

    public Item pop(){
        if(heap.isEmpty()){
            throw new IllegalStateException("");
        }

        Entity result = heap.poll();
        pool.remove(result.item);

        return result.item;
    }

}
