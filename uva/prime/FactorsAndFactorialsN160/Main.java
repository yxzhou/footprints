package fgafa.uva.prime.FactorsAndFactorialsN160;

import java.io.*;
import java.util.*;

class Main
{

  class Factor{
    int n;
    List<Integer> primes = new ArrayList<Integer>();
    
    public Factor(int n){
      this.n = n;     
    }
    
    public HashMap<Integer, HashMap<Integer, Integer>> getFactors(){
      HashMap<Integer, HashMap<Integer, Integer>> factors = new HashMap<Integer, HashMap<Integer, Integer>>();
      
      int limit = (int)Math.sqrt(n);
      HashMap<Integer, Integer> factor;
      for (int i = 2; i <= limit; i++) {
        if (!factors.containsKey(i)) {
          for (int j = i * i; j <= n; j += i){
            if(factors.containsKey(j))
              factor = factors.get(j);
            else{
              factor = new HashMap<Integer, Integer>();
              factors.put(j, factor);
            }  
            factor.put(i, 1);
          }  
        }
      }
      
      //add the prime 
      for(int i=2; i<=n; i++){
        if(!factors.containsKey(i)){
          primes.add(i);
          
          factor = new HashMap<Integer, Integer>();
          factors.put(i, factor);
          factor.put(i, 1);
        }  
      }
      
      //get the factors for factorial
      int mul;
      for(int i=2; i<=n; i++){
        factor = factors.get(i);
        
        mul = 1;
        for(Integer it : factor.keySet())
          mul *= it;
          
        mul = i / mul;
        
        if(mul > 1)
          mergeFactor(factor, factors.get(mul));   
      }
      
      return factors;
    }
    
    public void mergeFactor(HashMap<Integer, Integer> f1, HashMap<Integer, Integer> f2){
      for(Integer it : f2.keySet()){
        if(f1.containsKey(it))
          f1.put(it, f1.get(it) + f2.get(it));
        else
          f1.put(it, f2.get(it));
      }
    }
  }
  
  final static int N = 100;
  
  private HashMap<Integer, Integer> copyValueOf(HashMap<Integer, Integer> curr){
    HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
    
    for(Integer it : curr.keySet())
        result.put(it, curr.get(it));
    
    return result;
  }
  
  public static void main(String[] args) {
    Main sv = new Main();

    Factor fsv = sv.new Factor(N);
    HashMap<Integer, HashMap<Integer, Integer>> factors = fsv.getFactors(); // example  <2,<2, 1>>, <3,<3, 1>>, <4,<2, 2>>, <5,<5, 1>>, <6,<<2, 1>, <3, 1>>>
    
    //
    HashMap<Integer, HashMap<Integer, Integer>> factorials = new HashMap<Integer, HashMap<Integer, Integer>>(); // example  <2!,<<2, 1>>>, <3!,<<2, 1>, <3, 1>>>, <4!,<<2, 2>>>
    HashMap<Integer, Integer> curr = new HashMap<Integer, Integer>();
    
    for(int i=2; i<=N; i++){
      fsv.mergeFactor(curr, factors.get(i));
      factorials.put(i, sv.copyValueOf(curr));      
    }
    
    List<Integer> primes = fsv.primes;
    
    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    int n;
    
    //long start = System.currentTimeMillis();
    try {
      while (in.hasNext()) {        
        //read
        n = in.nextInt();
        
        //exit when it's 0
        if(n==0)
          return;
        
        //output    
        System.out.printf("%3d! =", n);
        
        curr = factorials.get(n); 
        int prime;
        for(int i=0; i<primes.size(); i++){
          prime = primes.get(i);
          if(prime > n)
            break;
          
          if(i% 15 == 0 && i> 0 )
            System.out.printf("%n      ");

          System.out.printf("%3d", curr.get(prime));           
        }
 
        System.out.println();
      }
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    finally {
      in.close();
      //System.out.printf("%n%10d%n",System.currentTimeMillis() - start);
    }
  }
}
