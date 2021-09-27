package uva.graph.ArbitrageN104;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import util.Misc;

class Main
{
  
  public static final String NOTFOUND = "no arbitrage sequence exists";
  //static final double EPS = 1e-6d;
  
  /*
   * Time O( )  Space O(  )
   */ 
  public String arbitrage(double[][] array, int n) {
    //init
    ArrayList<String> list ;
    LinkedList<ArrayList<String>> aPath = new LinkedList<ArrayList<String>>();
    HashMap<String, String> rates = new HashMap<String, String>();
    for(int i=1; i<n; i++){
      for(int j=1; j<n; j++){
        rates.put(getStr(i, j), String.valueOf(array[i][j]));
        
        if(array[i][j] <= 0 || i == j)
          continue;
        
        list = new ArrayList<String>();
        list.add(String.valueOf(array[i][j]));  //rate
        list.add(String.valueOf(i));  //country #x
        list.add(String.valueOf(j));  //country #x
        
        aPath.add(list);         
      }
    }
    
    int start, end;
    double currRate, newRate;
    ArrayList<String> tmp;
    for(int k=n-2; k>0; k-- ) {   // n-2 instead of n-3,  max allowed transaction is n. 
      for(int i=aPath.size(); i>0; i--){
        list = aPath.pop();
        
        start = Integer.valueOf(list.get(1));
        end = Integer.valueOf(list.get(list.size() - 1));
        currRate = Double.valueOf(list.get(0));
        
        for(int j = 1; j < n ;  j ++){         
          newRate = currRate *  array[end][j]; 

          if(newRate <= Double.valueOf(rates.get(getStr(start, j))) )
            continue;
          
          //if( newRate - 1.01 > EPS && start == j )  // comparison for floating point numbers are inaccurate, it's to "=="
          if( newRate > 1.01 && start == j )  // this does work
            return toString(list) + j;
          
          tmp = new ArrayList<String>();
          tmp.add(String.valueOf(newRate));
          for(int key=1; key<list.size(); key++){
            tmp.add(list.get(key));
          }
          tmp.add(String.valueOf(j));
          aPath.addLast(tmp);
          rates.put(getStr(start, j), String.valueOf(newRate));
        }
      }
    }
    
    return NOTFOUND;
  }
  
  static String getStr(int i, int j) {
    return i + " " + j;
  }
  
  private String toString(ArrayList<String> list){
    StringBuilder sb = new StringBuilder();
    
    for(int i=1; i<list.size(); i++){
      sb.append(list.get(i));
      sb.append(" ");
    }
      
    return sb.toString();
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {

    System.out.println("===start====");
    String inputFile = "src/fgafa.uva/graph/ArbitrageN104/Arbitrage.txt";

    Main sv = new Main();
    Main22 sv22 = new Main22();

    try(//Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
            Scanner in = new Scanner(new BufferedInputStream(new FileInputStream(new File(inputFile))), "UTF-8")){

      while(in.hasNext()){
        int n = in.nextInt() + 1;
        double[][] array = new double[n][n]; 
        
        for (int i = 1; i < n; i++) {
          for (int j = 1; j < n; j++) {
            if(i != j){
              array[i][j] = in.nextDouble();
              if(array[i][j]<0 )  array[i][j] = 0;
            }else
              array[i][j] = 1;

          }
        }      
  
        System.out.println(sv.arbitrage(array, n));
      }
    }catch(Exception e){
      e.printStackTrace();
    }

    System.out.println("\n===Ans====");

    Misc.printFile("src/fgafa.uva/graph/ArbitrageN104/Arbitrage_ans.txt");

  }
  
}
