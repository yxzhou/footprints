package datastructure.LFU;

public interface LFUCache<K, V> {

    void set(K key, V value);

    V get(K key);

}