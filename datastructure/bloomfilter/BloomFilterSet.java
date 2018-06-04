/**
 * BloomFilterSet.java A simple implementation of a Bloom Filter (http://en.wikipedia.org/wiki/Bloom_filter). </br> 
 * This implementation using the Java MD5, SHA-1 and SHA-256 hashing algorithm to determine which bits to flip in the filter.
 */

package fgafa.datastructure.bloomfilter;

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


public class BloomFilterSet extends AbstractSet<Object> implements Serializable {

  private BitSet bitset;

  private int totalSetSize; // m, total space for number of elements
  private int currentSetSize; // N, number of elements actually added 

  private static int K;  //number of hash functions
  private static final ArrayList<MessageDigest> hashAlgorithms = new ArrayList<MessageDigest>();;


  /**
   * @param size
   *          the size(m) of the byte array backing the 
   */
  public BloomFilterSet(int size) {
    totalSetSize = size;
    bitset = new BitSet(size);
    currentSetSize = 0;

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
      bitset.set(index, true);

    currentSetSize++;
    return true;
  }



  @Override
  public boolean contains(Object obj) {
    for (int index : getIndexes(obj)) 
      if (!bitset.get(index))
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
    return Math.abs(new BigInteger(1, hashAlgorithm.digest()).intValue()) % totalSetSize;
  }



  @Override
  public int size() {
    return totalSetSize;
  }

  public int count() {
    return currentSetSize;
  }


  @Override
  public boolean isEmpty() {
    return currentSetSize < 1;
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
   * Cannot remove elements from a Bloom Filter without potentially removing
   * other elements.
   */
  @Override
  public boolean remove(Object o) {
    return false;
  }



  @Override
  public boolean containsAll(Collection c) {
    Iterator i = c.iterator();

    while (i.hasNext())
      if (!contains(i.next()))
        return false;

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
    this.bitset.clear();
    currentSetSize = 0;
  }



  @Override
  public Iterator<Object> iterator() {
    return null;
  }
  
  public double getFalsePositiveRate() {
    return getFalsePositiveRate(currentSetSize);
  }

  public double getFalsePositiveRate(double numberOfElements) {
    // ( 1 - e^(-k*n/m) ) ^ k
    return (double) Math.pow(1.0 - (Math.exp(-K * (double)numberOfElements / (double)totalSetSize )), K);
  }
  
}
