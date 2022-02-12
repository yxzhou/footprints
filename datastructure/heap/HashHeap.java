package datastructure.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class HashHeap {
    
    ArrayList<Integer> heap; //ArrayList,  id -> value 
    //String mode;
    int size;
    HashMap<Integer, Node> hash; // Map<value, id + count>
    
    private final Comparator<Integer> comparator;

    class Node {
        public Integer id;
        public Integer num;

        Node(Node now) {
            id = now.id;
            num = now.num;
        }

        Node(Integer first, Integer second) {

            this.id = first;
            this.num = second;
        }
    }

    public HashHeap(Comparator<Integer> comparator) { 

        heap = new ArrayList<Integer>();
        hash = new HashMap<Integer, Node>();
        size = 0;
        
        this.comparator = comparator;
    }

    public int peak() {
        return heap.get(0);
    }

    public int size() {
        return size;
    }

    public Boolean isEmpty() {
        return (heap.size() == 0);
    }

    private int parent(int id) {
        if (id == 0) {
            return -1;
        }
        return (id - 1) / 2;
    }

    private int lson(int id) {
        return id * 2 + 1;
    }

    private int rson(int id) {
        return id * 2 + 2;
    }

    private boolean comparesmall(int a, int b) {
        if(this.comparator == null ){
            return Integer.compare(a, b) < 0;
        }else{
            return this.comparator.compare(a, b) < 0;
        }
        
    }

    private void swap(int idA, int idB) {
        int valA = heap.get(idA);
        int valB = heap.get(idB);

        int numA = hash.get(valA).num;
        int numB = hash.get(valB).num;
        hash.put(valB, new Node(idA, numB));
        hash.put(valA, new Node(idB, numA));
        heap.set(idA, valB);
        heap.set(idB, valA);
    }

    public Integer poll() {
        size--;
        Integer now = heap.get(0);
        Node hashnow = hash.get(now);
        if (hashnow.num == 1) {
            swap(0, heap.size() - 1);
            hash.remove(now);
            heap.remove(heap.size() - 1);
            if (heap.size() > 0) {
                siftdown(0);
            }
        } else {
            hash.put(now, new Node(0, hashnow.num - 1));
        }
        return now;
    }

    public void add(int now) {
        size++;
        if (hash.containsKey(now)) { //replace
            Node hashnow = hash.get(now);
            hash.put(now, new Node(hashnow.id, hashnow.num + 1));

        } else {
            heap.add(now);
            hash.put(now, new Node(heap.size() - 1, 1));
        }

        siftup(heap.size() - 1);
    }

    public boolean containsKey(int now){
        return hash.containsKey(now);
    }
    
    public void delete(int now) {
        if(!hash.containsKey(now)){
            return;
        }
        
        size--;
        
        Node hashnow = hash.get(now);
        int id = hashnow.id;
        int num = hashnow.num;
        if (hashnow.num == 1) {

            swap(id, heap.size() - 1);
            hash.remove(now);
            heap.remove(heap.size() - 1);
            if (heap.size() > id) {
                siftup(id);
                siftdown(id);
            }
        } else {
            hash.put(now, new Node(id, num - 1));
        }
    }

    private void siftup(int id) {
        while (parent(id) > -1) {
            int parentId = parent(id);
            if (comparesmall(heap.get(parentId), heap.get(id)) == true) {
                break;
            } else {
                swap(id, parentId);
            }
            id = parentId;
        }
    }

    private void siftdown(int id) {
        while (lson(id) < heap.size()) {
            int leftId = lson(id);
            int rightId = rson(id);
            int son;
            if (rightId >= heap.size() || (comparesmall(heap.get(leftId), heap.get(rightId)) == true)) {
                son = leftId;
            } else {
                son = rightId;
            }
            if (comparesmall(heap.get(id), heap.get(son)) == true) {
                break;
            } else {
                swap(id, son);
            }
            id = son;
        }
    }
    
}
