package basic.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {

  /** serialVersionUID */
  private static final long serialVersionUID = -5933045562735378538L;

  /** 最大数据存储容量 */
  private static final int  LRU_MAX_CAPACITY     = 1024;

  /** 存储数据容量  */
  private int               capacity;

  /**
   * 默认构造方法
   */
  public LRULinkedHashMap() {
      super();
  }

  /**
   * 带参数构造方法
   * @param initialCapacity   容量
   * @param loadFactor        装载因子
   * @param isLRU             是否使用lru算法，true：使用（按方案顺序排序）;false：不使用（按存储顺序排序）
   */
  public LRULinkedHashMap(int initialCapacity, float loadFactor, boolean isLRU) {
      super(initialCapacity, loadFactor, true);
      capacity = LRU_MAX_CAPACITY;
  }

  /**
   * 带参数构造方法
   * @param initialCapacity   容量
   * @param loadFactor        装载因子
   * @param isLRU             是否使用lru算法，true：使用（按方案顺序排序）;false：不使用（按存储顺序排序）
   * @param lruCapacity       lru存储数据容量       
   */
  public LRULinkedHashMap(int initialCapacity, float loadFactor, boolean isLRU, int lruCapacity) {
      super(initialCapacity, loadFactor, true);
      this.capacity = lruCapacity;
  }

  /** 
   * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
   */
  @Override
  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
      System.out.println(eldest.getKey() + "=" + eldest.getValue());
      
      if(size() > capacity) {
          return true;
      }
      return false;
  }
}

