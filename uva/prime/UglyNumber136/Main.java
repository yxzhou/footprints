package fgafa.uva.prime.UglyNumber136;

import java.util.LinkedList;


public class Main
{
    private static void addIndex(int index, LinkedList<Integer> nextIndex, LinkedList<Integer>[] q){
        if(q[index].peek() > q[nextIndex.get(1)].peek()){
            nextIndex.add(2, index);
            return;
        }
        
        if(q[index].peek() > q[nextIndex.get(0)].peek()){
            nextIndex.add(1, index);
            return;  
        }
            
        nextIndex.add(0, index);
        
    }
    
    private static int getUglyNum(int nth){
        //init
        int[] prime = {2, 3, 5};
        LinkedList<Integer>[] q = new LinkedList[3];
        LinkedList<Integer> nextIndexs = new LinkedList<Integer>();  // next index,  initial it's 0, 1, 2. 
        
        for(int i=0; i<3; i++){
            q[i] = new LinkedList<Integer> ();
            q[i].add(prime[i]);
            
            nextIndexs.add(i);
        }
        
        
        //main
        int index = 0, result = 1;
        for( ; nth>1 ; nth--){
            //compare
            index = nextIndexs.pop();
            result = q[index].pop();
            
            //
            for(int i=index; i<3; i++){
                q[i].add(result * prime[i]);
            }
            
            addIndex(index, nextIndexs, q);
            
        }
        
        return result;
    }
    
    public static void main(String[] arg){
        int n = 1500;
        
        //for( ; n < 20; n++)
            System.out.printf("The %d'th ugly number is %d.%n", n, getUglyNum(n));
    }
}
