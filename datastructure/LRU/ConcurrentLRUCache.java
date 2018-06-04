package fgafa.datastructure.LRU;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;



public class ConcurrentLRUCache<K, V> {
	private final int maxSize;
	private ConcurrentHashMap<K, V> map;
	private ConcurrentLinkedQueue<K> queue;

	public ConcurrentLRUCache(final int maxSize) {
		this.maxSize = maxSize;
		map = new ConcurrentHashMap<K, V>(maxSize);
		queue = new ConcurrentLinkedQueue<K>();
	}

	/**
	 * @param key
	 *            - may not be null!
	 * @param value
	 *            - may not be null!
	 */
	public void put(final K key, final V value) {
		if (map.containsKey(key)) {
			queue.remove(key); // remove the key from the FIFO queue
		}

		while (queue.size() >= maxSize) {
			K oldestKey = queue.poll();
			if (null != oldestKey) {
				map.remove(oldestKey);
			}
		}
		queue.add(key);
		map.put(key, value);
	}

	/**
	 * @param key
	 *            - may not be null!
	 * @return the value associated to the given key or null
	 */
	public V get(final K key) {
		V value = null;
		if (map.containsKey(key)) {
			value = map.get(key);
			queue.remove(key); // remove the key from the FIFO queue
			
			queue.add(key);
		}
		
		return value;
	}
}
