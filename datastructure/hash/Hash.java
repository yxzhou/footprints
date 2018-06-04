package fgafa.datastructure.hash;

import java.util.Hashtable;

/*
 * 
 * refer to  http://www.byvoid.com/blog/string-hash-compare/
 * 
 * Hash         test1   test2   test3   test4   testR1 testR2   testR3  testR4 average  
 *BKDRHash      2       0       4774    481     96.55   100     90.95   82.05   92.64  
 *APHash        2       3       4754    493     96.55   88.46   100     51.28   86.28  
 *DJBHash       2       2       4975    474     96.55   92.31   0       100     83.43  
 *JSHash        1       4       4761    506     100     84.62   96.83   17.95   81.94  
 *RSHash        1       0       4861    505     100     100     51.58   20.51   75.96  
 *SDBMHash      3       2       4849    504     93.1    92.31   57.01   23.08   72.41  
 *PJWHash       30      26      4878    513     0       0       43.89   0       21.95  
 *ELFHash       30      26      4878    513     0       0       43.89   0       21.95 
 *
 *test1: hash collision number when input 100000 random characters that include numbers and letters
 *test2: hash collision number when input 100000 valid English sentences
 *test3: hash collision number when input (hash result from test1) mod 1000003(a big prime)
 *test4: hash collision number when input (hash result from test1) mod 10000019(a big prime)
 *
 *testR and average is a roughly score
 * 
 * 
 */
public class Hash
{

  // BKDR Hash Function
  // It's from Brian Kernighan and Dennis Ritchie, detail see <<The C
  // Programming Language>>. It's used by Java SDK with the factor 31.
  // DJBX33A
  // collision: "rQ" and "qp" has the same hash result when the R is 31. So are
  // "rQrQ", "rQqp", "qprQ", "qpqp",
  // and so you can get 2 to the n-th power of strings with the same hash result
  // (the length is 2*n )

  /*
   * From <<Effective Java>> The value 31 was chosen because it is an odd prime.
   * If it were even and the multiplication overflowed, information would be
   * lost, as multiplication by 2 is equivalent to shifting. The advantage of
   * using a prime is less clear, but it is traditional. A nice property of 31
   * is that the multiplication can be replaced by a shift and a subtraction for
   * better performance: 31 * i == (i << 5) - i. Modern VMs do this sort of
   * optimization automatically. 
   * 
   * Plus: As Goodrich and Tamassia point out, If you take
   * over 50,000 English words (formed as the union of the word lists provided
   * in two variants of Unix), using the constants 31,33,37,39, and 41 will
   * produce less than 7 collisions in each case. Knowing this, it should come
   * as no surprise that many Java implementations choose one of these
   * constants.
   * 
   */
  
  //double BKDRHash(char[] chars) {
  int BKDRHash(char[] chars) {
    int hash = 0;
    for (char ch : chars) {
      hash = hash * 31 + ch; // the R can be 31, 131, 1313, 13131, 131313.
      // hash = hash << 7 + hash << 1 + hash + c.
      // while I found there are no big difference between them with Intel
      // platform and CPU.
      // I did 10 billion test, the time cost is about 0 (If it's Debug version,
      // the latter cost will more than 1/3)

      // In ARM (RISC instead of CISC), the Multiplication performance is decide
      // by the R:

    }
    return hash;
  }


  /**
   * @param key: A String you should hash
   * @param HASH_SIZE: An integer
   * @return an integer
   */
  public int hashCode(char[] key,int HASH_SIZE) {
      //check
      if(null == key || 0 == key.length || 0 == HASH_SIZE){
          return 0; //error return
      }
      
      long result = 0;     // note, it's long instead of int
      for(char c : key){
          result = result * 33 + c;
          result %= HASH_SIZE;  // (a * b) % m = ( a % m ) * ( b % m )
      }
      
      //return result & 0x7FFFFFFF;
      return (int)result;
  }

  // SDBM Hash Function
  // It's from the open source project SDBM (a simpler DB engine ), it's as same
  // as BKDRHash.
  double SDBMHash(char[] chars) {
    double hash = 0;
    for (char ch : chars) {
      hash = 65599 * hash + ch;
      // hash = (size_t)ch + (hash << 6) + (hash << 16) - hash;
    }
    return hash;
  }



  // RS Hash Fuction
  // it's from Robert Sedgwicks, detail see <<Algorithms in C>>
  double RSHash(char[] chars) {
    double hash = 0;
    int magic = 63689;
    for (char ch : chars) {
      hash = hash * magic + ch;
      magic *= 378551;
    }
    return hash;
  }



  // AP Hash Function
  // It's from Arash Partow
  double APHash(char[] chars) {
    long hash = 0;
    char ch;
    for (int i = 0; i < chars.length; i++) {
      ch = chars[i];

      if ((i & 1) == 0) {
        hash ^= ((hash << 7) ^ ch ^ (hash >> 3));
      }
      else {
        hash ^= (~((hash << 11) ^ ch ^ (hash >> 5)));
      }
    }
    return hash;
  }



  // JS Hash Function
  // It's from Justin Sobel
  double JSHash(char[] chars) {
    if (null == chars || 0 == chars.length) // return 0 if the input is null
      return 0;
    long hash = 1315423911;
    for (char ch : chars) {
      hash ^= ((hash << 5) + ch + (hash >> 2));
    }
    return hash;
  }



  // DEK Function
  // It's from Donald E. Knuth, detail see <<Art of Computer Programming Volume
  // 3>>
  double DEKHash(char[] chars) {
    if (null == chars || 0 == chars.length) // return 0 if the input is null
      return 0;
    long hash = 1315423911;
    for (char ch : chars) {
      hash = ((hash << 5) ^ (hash >> 27)) ^ ch;
    }
    return hash;
  }



  // FNV Hash Function
  // It's used in Unix System, and in Microsoft's hash_map
  double FNVHash(char[] chars) {
    if (null == chars || 0 == chars.length) // return 0 if the input is null
      return 0;
    long hash = 2166136261L;
    for (char ch : chars) {
      hash *= 16777619;
      hash ^= ch;
    }
    return hash;
  }

  // DJB hash Function
  // it's from Daniel J. Bernstein
  double DJBHash(char[] chars)
  {
    if (null == chars || 0 == chars.length) // return 0 if the input is null
      return 0;
      long hash = 5381;
      for (char ch : chars) {
      
          hash += (hash << 5) + ch;
      }
      return hash;
  }

  //PJW Hash Function
  //It's from a paper of Peter J. Weinberger,  in AT&T Bell  
//  size_t PJWHash(const T *str)
//  {
//      static const size_t TotalBits       = sizeof(size_t) * 8;
//      static const size_t ThreeQuarters   = (TotalBits  * 3) / 4;
//      static const size_t OneEighth       = TotalBits / 8;
//      static const size_t HighBits        = ((size_t)-1) << (TotalBits - OneEighth);  
//      
//      register size_t hash = 0;
//      size_t magic = 0;   
//      while (size_t ch = (size_t)*str++)
//      {
//          hash = (hash << OneEighth) + ch;
//          if ((magic = hash & HighBits) != 0)
//          {
//              hash = ((hash ^ (magic >> ThreeQuarters)) & (~HighBits));
//          }
//      }
//      return hash;
//  }
  
  //ELF Hash Function
  //It's from PJW, detail see the Extended Library Function of Unix
//  size_t ELFHash(const T *str)
//  {
//      static const size_t TotalBits       = sizeof(size_t) * 8;
//      static const size_t ThreeQuarters   = (TotalBits  * 3) / 4;
//      static const size_t OneEighth       = TotalBits / 8;
//      static const size_t HighBits        = ((size_t)-1) << (TotalBits - OneEighth);  
//      register size_t hash = 0;
//      size_t magic = 0;
//      while (size_t ch = (size_t)*str++)
//      {
//          hash = (hash << OneEighth) + ch;
//          if ((magic = hash & HighBits) != 0)
//          {
//              hash ^= (magic >> ThreeQuarters);
//              hash &= ~magic;
//          }       
//      }
//      return hash;
//  }
  
  
  public void testHashtable(){
    Hashtable<String, String> ht = new Hashtable<String, String>();
    
    ht.put("rQ", "rQ01");
    ht.put("qp", "qp01");
    ht.put("rQ", "rQ02");
    ht.put("qp", "qp02");
    
    System.out.println("\nTest Hashtable " + ht.size());
  }
  
  public static void main(String args[]) {

    System.out.println("-----start------");

    String[] str = {"rQ","qp"};
    
    
    Hash hf = new Hash();

    for(int i = 0; i< str.length; i++){
      double return1 = hf.BKDRHash(str[i].toCharArray());
  
      System.out.println( str[i] + "is hashed to" + return1);
    }
    
    int len = 10;
    StringBuffer sb = new StringBuffer(); 
    for(int i = 0; i< len; i++){

      //for(int j = 0; j< len; i++){
        sb.append("a");
      //}
      
      int return1 = hf.BKDRHash(sb.toString().toCharArray());
      
      System.out.println( sb.toString() + " is hashed to" );
      System.out.println( return1);
      
    }
    
    //test java.util.Hashtable
    hf.testHashtable();
    
    System.out.println("-----end------" );
  }

}
