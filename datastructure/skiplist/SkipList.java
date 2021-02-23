package fgafa.datastructure.skiplist;

public interface SkipList {

    boolean search(int target);
    void add(int num);
    boolean erase(int num);
}
