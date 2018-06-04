package fgafa.greedy;

/*
 * A pile of nuts is in an oasis, across a desert from a town. 
 * The pile contains ‘N’ kg of nuts, and the town is ‘D’ kilometers away from the pile.
 * 
 * The goal of this problem is to write a program that will compute ‘X’, the maximum amount of nuts that can be transported to the town.
 * 
 * The nuts are transported by a horse drawn cart that is initially next to the pile of nuts. 
 * The cart can carry at most ‘C’ kilograms of nuts at any one time. The horse uses the nuts that it is carrying as fuel. 
 * It consumes ‘F’ kilograms of nuts per kilometer traveled regardless of how much weight it is carrying in the cart. 
 * The horse can load and unload the cart without using up any nuts.
 * 
 * Your program should have a function that takes as input 4 real numbers D,N,F,C and returns one real number: ‘X’ 
 * 
 */

public class CarryNuts
{

  public double getMaxNuts_wrong(double N, double D, double C, double F) {
     //System.out.println(" N："+N+"\t D:"+D+"\t C:"+C + "\t F" + F);
      
    //case1: N is smaller than one-way trip consumption
    if(N<=D*F)
      return 0.0;
    
    //case2: If We have the capacity to carry all nuts, one-way trip will be best
    if (N <= C) {
      return N - D * F; 
    }

    //case3: let's try to find a transit point. 
    // # trips you would travel back and forth, multiple two-way plus the last one-way
    int numTrips = 2 * (int) (N / C) - 1; // 2*(ceil(N/C) - 1) + 1; 

    // remaining weight of nuts after consumption, 
    //the best transit point appears when we try to carry all N and consume C. ???
    double remainingNuts = C * (N / C - 1.0); 

    // how many nuts you consume per km
    double costPerKm = numTrips * F;
    
    // define traveled as the distance you are able to travel before you reach ONE LESS round trip fetching nuts
    // derived from eq: N - costPerKm * traveled = remainingNuts
    double distanceToTravel = (N - remainingNuts) / costPerKm;

    //System.out.println("\n numTrips："+numTrips+"\t remainingNuts:"+remainingNuts+"\t traveled:"+distanceToTravel);
    
    // we are able to travel greater (or equal) than the remaining
    // distance, so fetch the nuts right to the destination
    if (distanceToTravel >= D)
      return N - D * costPerKm;

    // calculate recursively as we travel ONE less round trip now.
    return getMaxNuts_wrong(remainingNuts, D - distanceToTravel, C, F);
  }
  
  /**
   * case 1: when N is smaller than one-way trip consumption, no nuts will be left in the final.
   * case 2: when N is bigger than one-way trip consumption,  and smaller than one way trip capacity, it only need one trip. 
   * case 3: when N is bigger tan one-way trip capacity, it need multiple transit points. 
   * To get the max remain in final, it need move the all nuts in every transit points. 
   *   if N % C == 0, it need (N/C) * 2 - 1 trips, the best best transit point appears when we try to carry all N and consume C. 
   *   if N % C != 0, it need (N/C) * 2 + 1 trips, the best best transit point appears when we try to carry all N and consume N % C. 
   */
  public double getMaxNuts_n(double N, double D, double C, double F) {
    //System.out.println(" N："+N+"\t D:"+D+"\t C:"+C + "\t F" + F);
      
    //case1: N is smaller than one-way trip consumption
    if(N<=D*F)
      return 0.0;
    
    //case2: If We have the capacity to carry all nuts, one-way trip will be best
    if (N <= C) {
      return N - D * F; 
    }
    
    //case3 start --
    //the consume in the best strategy, 
    double consume = N % C;
    if( 0 == consume ){
        consume = C;
    }
    
    //how many trips need
    int numTrips = 2 * (int) (N / C) + 1;
    if(C == consume){
        numTrips -= 2;
    }
    
    //the distance to the best transit point
    double distance = consume / numTrips;;
    
    // we are able to travel greater (or equal) than the remaining distance, so fetch the nuts right to the destination
    if (distance >= D){
      return N - D * F * numTrips;
    }
    
    // calculate recursively as we travel ONE less round trip now.
    return getMaxNuts_wrong(N - consume, D - distance, C, F);
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    CarryNuts service = new CarryNuts(); 
    
    int[] N = {100, 140, 150, 200, 250, 300, 100};
    int[] D = {20, 20, 20, 20, 20, 20, 20};
    int[] C = {50, 50, 50, 50, 50, 50, 50};
    int[] F = {1, 1, 1, 1, 1, 1, 5};
    
    for(int i=0; i< N.length; i++){
      System.out.println("\nN:"+N[i]+"\tD:"+D[i]+"\tC:"+C[i]+"\tF:"+F[i]);
      
      System.out.println(service.getMaxNuts_wrong(N[i], D[i], C[i], F[i]));
      System.out.println(service.getMaxNuts_n(N[i], D[i], C[i], F[i]));
    }

  }

}
