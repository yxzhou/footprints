package datastructure.hash;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyHashTable<K, V> implements Map<K, V>, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    
    private transient Entry<K, V>[] table;
    private int loadfactor;
    private int threshold;
    private int count;
    
    public MyHashTable(int capacity, int loadfactor){
        table = new Entry[capacity];
        
        this.loadfactor = loadfactor;
        this.count = 0;
    }
    
    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        if(null == key){
            return false;
        }
        
        int hashcode = key.hashCode();
        int index = (hashcode & 0x7FFFFFFF) % table.length;
        
        for(Entry<K, V> entry = table[index]; null != entry; entry = entry.next){
            if(hashcode == entry.hashcode && key.equals(entry.key)){
                return true;
            }
        }
        
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if(null == value){
            return false;
        }
        
        for(Entry<K, V> entry : table){
            for( ; null != entry; entry = entry.next){
                if(value.equals(entry.value)){
                    return true;
                }
            }
        }
        
        return false;
    }

    @Override
    public V get(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public V put(K key,
                 V value) {
        int hashcode = hashcode(key);
        int index = (hashcode & 0x7FFFFFFF) % table.length;
        
        for(Entry<K, V> entry = table[index]; null != entry; entry = entry.next){
            if(entry.hashcode == hashcode && entry.key.equals(key)){
                V old = entry.value;
                //replace
                entry.value = value;
                
                return old;
            }
        }
        
        //insert
        addEntry(hashcode, key, value);
        
        return null;
    }

    private void addEntry(int hashcode, K key, V value){
        
        if(count >= threshold){
            rehash();
        }
        
        int index = (hashcode & 0x7FFFFFFF) % table.length;
        Entry<K, V> newEntry = new Entry<K, V>(hashcode, key, value, table[index]);
        table[index] = newEntry;
        count++;
    }
    
    private void rehash(){
        int oldCapacity = table.length;
        int newCapacity = (oldCapacity << 1) + 1;
        
        @SuppressWarnings("unchecked")
        Entry<K, V>[] newTable = new Entry[newCapacity];
        
        int index;
        Entry<K, V> next;
        for(Entry<K, V> entry : table){
            while(null != entry){
                next = entry.next;
                
                index = (entry.hashcode & 0x7FFFFFFF) % newTable.length;
                entry.next = newTable[index];
                newTable[index] = entry;
                
                entry = next;
            }
        }
        
        this.table = newTable;
        this.threshold = newCapacity * loadfactor;
        //count, no change
    }
    
    @Override
    public V remove(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Set<K> keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<V> values() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    synchronized int hashcode(K key){
        //TODO
        
        return -1;
    }
    
    class Entry<K, V>{
        int hashcode;
        K key;
        V value;
        Entry<K, V> next;
        
        public Entry(int hashcode, K key, V value, Entry<K, V> next){
            this.hashcode = hashcode;
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        
        public boolean equal(Entry<K, V> o) {
            if(null == o){
                return false;
            }
            
            return this.hashcode == o.hashcode && this.key.equals(o.key) && this.value.equals(o.value);
        }
    }
}
