package fgafa.easy;

import java.util.ArrayList;
import java.util.List;

public class TournamentSheduling
{

  /*
   * 
   * Where numberOfPlayers [assume this as 8] is the total number of players in the tournament with ranks 1 – N 
   * [every one has a unique rank], you need print
   * 
   * input : n = 7  [1, 2, 3, 4, 5, 6, 7, #]
   * Output: [1, 5, 3, 6, 2, 7, 4, #]
   * 
   */
  public List<String> printTournamentParanthesis(int numberOfPlayers) {
    List<String> list = new ArrayList<String>();
    
    //check
   if(numberOfPlayers == 0){
     return list;
   }
    
   int playerId = 1;
   list.add(0, String.valueOf(playerId));

   while(playerId < numberOfPlayers){
     int length = list.size();
     for(int i=length, k=1; i > 0; i--, k +=2){
       ++playerId;
       
       if(playerId <= numberOfPlayers)
         list.add(k, String.valueOf(playerId));
       else
         list.add(k, String.valueOf("#"));
       
     }
   }
   
   return list; 
  }

  /*
   * 
   * input : n = 7  [1, 2, 3, 4, 5, 6, 7, #]
   * Output: [1, 5, 3, 6, 2, 7, 4, #]
   * ==> (((1,5),(3,6)),((2,7),(4,#)))
   *
   */
  public String printTournamentParanthesis(List<String> list, int start, int end) {
    if(list == null || list.size() ==0 || end - start <2 )
      return "";
    
    String leftPart = ""; 
    String rightPart = "";
        
    if( end - start == 2){
      leftPart = (String)list.get(start); 
      rightPart = (String)list.get(end-1);
    }else{
      int mid = start + (end - start)/2;
      leftPart = printTournamentParanthesis(list, start, mid);
      rightPart = printTournamentParanthesis(list, mid, end);      
    }
    
    return "("+leftPart+","+rightPart+")";
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    TournamentSheduling sv = new TournamentSheduling();
    
    
    for(int n=1; n< 8; n++){
      
      List<String> list = sv.printTournamentParanthesis(n);
      
      System.out.println("\n=printTournamentParanthesis(" + n +") " + list.toString());

      System.out.println("=printTournamentParanthesis(" + n +") " + sv.printTournamentParanthesis(list, 0, list.size()));

    }
    
  }

}
