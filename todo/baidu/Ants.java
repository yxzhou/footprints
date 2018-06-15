package fgafa.todo.baidu;
/**
 * 
 * Given some ants that in a line, they can only move one the line, they will turn back if 2 ants meet up with. 
 * 
 * example:
 *   Give a line, length 27 cm,  
 *   There are 5 ants, in (3cm, 7cm, 11)
 *  
 * Question, 

 * 
 */
public class Ants {

    public int[] module(int[] ants, int length){
        
        int[] result = new int[2]; // min and max
        result[0] = Integer.MAX_VALUE; //min
        result[1] = Integer.MIN_VALUE; //max
        int n = ants.length;
        
        int[] directions = new int[n];
        int[] positions = new int[n];
        int activeAnts;
        int count;
        for(int i = (1 << n) - 1; i >= 0; i--){
            initDirection(i, directions);
            System.arraycopy(ants, 0, positions, 0, n);
            activeAnts = n;
            count = 0;
            
            while(activeAnts > 0){
                for(int j = 1; j < n; j++){
                    if(directions[j - 1] == 0){
                        continue;
                    }
                    
                    if(positions[j - 1] + 1 >= positions[j] && directions[j - 1] - directions[j] == 2){
                        directions[j - 1] = -1;
                        directions[j] = 1;
                    }
                    
                    if(positions[j - 1] + 1 != positions[j]){
                        positions[j - 1] += directions[j - 1];
                    }
                    
                    if(positions[j - 1] == 0 || positions[j - 1] == length){
                        activeAnts--;
                    }
                }
                
                if(directions[n - 1] != 0){
                    positions[n - 1] += directions[n - 1];
                    if(positions[n - 1] == 0 || positions[n - 1] == length){
                        activeAnts--;
                    }
                }
                
                count++;
            }
            
            result[0] = Math.min(count, result[0]);
            result[1] = Math.max(count, result[1]);
        }
        
        return result;
    }
    
    private void initDirection(int direction, int[] directions ){
        for(int i = 0; i < directions.length; i++){
            if((direction & (1 << i)) > 0){
                directions[i] = 1;
            }else{
                directions[i] = -1;
            }
        }
    }
    
    public static void main(String[] args) {
        int length = 27;
        int[] ants = {3, 7, 11, 17, 23};

        Ants sv = new Ants();
        int[] result = sv.module(ants, length);
        
        System.out.println(String.format("min=%d, max=%d", result[0], result[1]));
        
    }

}
