package fgafa.easy;

import java.util.ArrayList;
import java.util.List;

import fgafa.util.Misc;

public class PascalTriangle
{

  /*
   * Given numRows, generate the first numRows of Pascal's triangle.
   * 
   * For example, given numRows = 5,
   * Return
   * 
   * [
   *      [1],
   *     [1,1],
   *    [1,2,1],
   *   [1,3,3,1],
   *  [1,4,6,4,1]
   * ]
   * 
   */
  
  public ArrayList<ArrayList<Integer>> generate(int numRows) {
    ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
    
    //check
    if(numRows <=0 ) return result;
    
    ArrayList<Integer> nextLayer;
    int num = 0;
    ArrayList<Integer> currLayer = new ArrayList<Integer>() ;
    currLayer.add(1);
    while(num < numRows){
      result.add(currLayer);
      
      int currInt = 1;
      nextLayer = new ArrayList<Integer>() ;
      nextLayer.add(1);
 
      for(int i=1; i< currLayer.size(); i++){
        nextLayer.add(currInt + currLayer.get(i));
        currInt = currLayer.get(i);
      }
      
      nextLayer.add(1);
      
      currLayer = nextLayer;
      num ++;
    }
    
    return result;
  }
  
    public List<List<Integer>> generate_x(int numRows) {
        List<List<Integer>> ret = new ArrayList<>();
        if (1 > numRows) {
            return ret;
        }

        List<Integer> currLayer = new ArrayList<>();
        currLayer.add(1);
        List<Integer> nextLayer;
        while (numRows > 0) {
            ret.add(currLayer);

            nextLayer = new ArrayList<>();
            nextLayer.add(1);

            for (int i = 1; i < currLayer.size(); i++) {
                nextLayer.add(currLayer.get(i - 1) + currLayer.get(i));
            }

            nextLayer.add(1);
            currLayer = nextLayer;
            numRows--;
        }

        return ret;
    }
  
  public ArrayList<ArrayList<Integer>> generate2(int numRows) {

    ArrayList<ArrayList<Integer>> tri=new ArrayList<ArrayList<Integer>>();
    ArrayList<Integer> row;
    for(int i=0;i<numRows;i++) {
        row=new ArrayList<Integer>(i+1);
        System.out.println(row.size() );
        
        row.add(0,1);
        
        try{
          if(i!=0)row.add(i,1);
        }
        catch(Exception e){
          System.out.println(row.size() + " "+ i);
          e.printStackTrace();
        }
        
        
        for(int j=1;j<=i-1;j++) {
            int val=tri.get(i-1).get(j-1)+tri.get(i-1).get(j);
            row.add(j,val);
        }
        //if(i!=0) row.add(i,1);
        tri.add(row);
    }
    return tri;
   }
  
  /*
   * Given an index k, return the kth row of the Pascal's triangle.
   * 
   * For example, given k = 3,
   * Return [1,3,3,1].
   * 
   * Note:
   * Could you optimize your algorithm to use only O(k) extra space?
   * 
   */
  public ArrayList<Integer> getRow(int rowIndex) {
    ArrayList<Integer> currLayer = new ArrayList<Integer>() ;
    
    //check
    if(rowIndex <0 ) return currLayer;
    
    ArrayList<Integer> nextLayer;
    int num = 0;
    currLayer.add(1);  // rowIndex == 1
    
    while(num < rowIndex){
      
      int currInt = 1;
      nextLayer = new ArrayList<Integer>() ;
      nextLayer.add(1);
 
      for(int i=1; i< currLayer.size(); i++){
        nextLayer.add(currInt + currLayer.get(i));
        currInt = currLayer.get(i);
      }
      
      nextLayer.add(1);
      
      currLayer = nextLayer;
      num ++;
    }
    
    return currLayer;
  }
  
  public List<Integer> getRow_x(int rowIndex) {
	  List<Integer> ret = new ArrayList<>();
      if( 0 > rowIndex){
    	  return ret;
      }
      
      ret.add(1);

	  for(int i = 1; i <= rowIndex; i++){
    	  ret.add(1);
    	  for( int j = i - 1; j>0 ; j--){
    		  ret.set(j, ret.get(j) + ret.get(j-1));
    	  }
	  }
	  
	  return ret;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    PascalTriangle sv = new PascalTriangle();
    
    for(int rowIndex = -1; rowIndex < 6; rowIndex ++){
    	System.out.println("\n Input: " + rowIndex);
    	
    	Misc.printListList(sv.generate_x(rowIndex));
        Misc.printArrayList_Integer(sv.getRow_x(rowIndex));
    }

    
  }

}
