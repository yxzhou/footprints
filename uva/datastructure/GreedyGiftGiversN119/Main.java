package uva.datastructure.GreedyGiftGiversN119;

import java.io.*;
import java.util.*;

class Main
{
  

  public static void main(String[] args) throws Exception {

    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");

    HashMap<String, Integer> output ; //<people, money>
    ArrayList<String> input ;  //store the people in the input order 
    
    String people;
    int money, num, result;
    boolean flag = false;
    try {
      while (in.hasNext()) { 
        //init
        output = new HashMap<String, Integer>(); //<people, money>
        input = new ArrayList<String>();  //store the people in the input order
        
        num = Integer.valueOf(in.nextLine().trim());  //how many people in this group 
        StringTokenizer st = new StringTokenizer(in.nextLine(), " ");  
        while(st.hasMoreTokens()){
          people = st.nextToken();
          input.add(people);
          output.put(people, 0);  
        }
        
        for (int i=num; i>0; i-- ) {              
            st = new StringTokenizer(in.nextLine(), " "); 
            people = st.nextToken();
            money = Integer.valueOf(st.nextToken());
            num = Integer.valueOf(st.nextToken());
            
            if(money == 0 || num == 0)
              continue;
            
            result = money / num;
            output.put(people, output.get(people) - num * result);
             
            for(int j=num; j>0; j-- ){
              people = st.nextToken();
              output.put(people, output.get(people) + result);
            }  
        }
        
        //output a group
        if(flag)
          System.out.println(); //The output for each group should be separated from other groups by a blank line
        
        flag = true;
        for(String elem : input)
          System.out.println(elem + " " + output.get(elem));
                  
      }
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    finally {
      in.close();
    }
    
  }

}
