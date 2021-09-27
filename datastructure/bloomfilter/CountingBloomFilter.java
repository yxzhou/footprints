/**
 * BloomSet.java A simple implementation of a Bloom Filter
 * (http://en.wikipedia.org/wiki/Bloom_filter). </br> This implementation using
 * the Java MD5 hashing algorithm to determine which bits to flip in the filter.
 */

package datastructure.bloomfilter;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;

public class CountingBloomFilter extends AbstractSet<Object> implements Serializable {

  private byte[] byteset;

  private int totalElementSize; // M, total space for number of elements 
  private int currentElementSize; // N, number of elements actually added 

  private static int K;  //number of hash functions
  private static final ArrayList<MessageDigest> hashAlgorithms = new ArrayList<MessageDigest>();;

  private int bitsPerElement = 8;  // bits Per Element, default it's a byte

  /**
   * @param size
   *          the size(m) of the byte array backing the BloomSet
   * @param keySize
   *          the number of bits(k) sit for each object in the set.
   */
  public CountingBloomFilter(int size) {
    totalElementSize = size;
    byteset = new byte[size];
    currentElementSize = 0;

    try {
      hashAlgorithms.add(MessageDigest.getInstance("MD5"));
      hashAlgorithms.add(MessageDigest.getInstance("SHA-1"));
      hashAlgorithms.add(MessageDigest.getInstance("SHA-256"));
    }
    catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException("Hash function loading exception");
    }

    K = hashAlgorithms.size();
  }


  @Override
  public boolean add(Object obj) {

    for (int index : getIndexes(obj))
      byteset[index] ++;

    currentElementSize++;
    return true;
  }



  @Override
  public boolean contains(Object obj) {
    for (int index : getIndexes(obj)) 
      if (byteset[index] == 0)
        return false;

    return true;
  }

  @Override
  public boolean containsAll(Collection c) {
    Iterator i = c.iterator();

    while (i.hasNext())
      if (!contains(i.next()))
        return false;

    return true;
  }

  private int[] getIndexes(Object obj) {
    int[] hashIndexes = new int[K];

    for (int i = 0; i < K; i++)
      hashIndexes[i] = getHash(obj.hashCode(), hashAlgorithms.get(i));

    return hashIndexes;
  }



  private int getHash(int i, MessageDigest hashAlgorithm) {
    hashAlgorithm.reset();
    byte[] bytes = ByteBuffer.allocate(4).putInt(i).array();
    hashAlgorithm.update(bytes, 0, bytes.length);
    return Math.abs(new BigInteger(1, hashAlgorithm.digest()).intValue()) % totalElementSize;
  }



  @Override
  public int size() {
    return totalElementSize;
  }

  public int count() {
    return currentElementSize;
  }


  @Override
  public boolean isEmpty() {
    return currentElementSize < 1;
  }



  /**
   * Since the BloomSet does not actually store each element, it cannot return a
   * array of the elements themselves.
   */
  @Override
  public Object[] toArray() {
    return null;
  }



  /**
   * Since the BloomSet does not actually store each element, it cannot return a
   * array of the elements themselves.
   */
  @Override
  public Object[] toArray(Object[] a) {
    return null;
  }



  /**
   * remove elements from a Bloom Filter without potentially removing
   * other elements.
   */
  @Override
  public boolean remove(Object obj) {
    if(!contains(obj))
      return false; 
          
    for (int index : getIndexes(obj))     
      byteset[index] --;      
    
    currentElementSize--;
    return true;
  }


  @Override
  public boolean addAll(Collection c) {
    Iterator i = c.iterator();

    while (i.hasNext())
      if (!add(i.next()))
        return false;
    return true;
  }



  /**
   * Cannot remove elements from a Bloom Filter without potentially removing
   * other elements.
   */
  @Override
  public boolean retainAll(Collection c) {
    return false;
  }



  /**
   * Cannot remove elements from a Bloom Filter without potentially removing
   * other elements.
   */
  @Override
  public boolean removeAll(Collection c) {
    return false;
  }



  @Override
  public void clear() {
    this.byteset = new byte[totalElementSize];
    currentElementSize = 0;
  }



  @Override
  public Iterator<Object> iterator() {
    return null;
  }
  
  public double getFalsePositiveRate() {
    return getFalsePositiveRate(currentElementSize);
  }

  public double getFalsePositiveRate(double numberOfElements) {
    // ( 1 - e^(-k*n/m) ) ^ k
    return (double) Math.pow(1.0 - (Math.exp(-K * (double)numberOfElements / (double)totalElementSize )), K);
  }
}
