package fgafa.easy;

public class CharAt {

    public char charAt(int index){
        
        int num = 1;
        while(index >= 0 ){
            String curr = String.valueOf(num);
            
            if(index < curr.length()){
                return curr.charAt(index);
            }else{
                index -= curr.length();
                num++;   
            }
        }
        
        return 'N';
    }
    
    public char charAt_n(int index) {
        if(index < 0){
            return 'N';
        }
        
        int i = 1;
        long n = 9;
        while (index > n) {
            index -= n;

            //n *= (i + 1) * 10 /i; //wrong
            n = n * (i + 1) * 10 / i;
            i++;
        }

        String curr = String.valueOf(index / i + Math.pow(10, i - 1));
        
        return curr.charAt(index % i);
    }
    
    public static void main(String[] args) {
        CharAt sv = new CharAt();
        
        int[] indexs = { -1, 0, 9, 10, 189, 190, 2989, 2990, 12345, 234567, 3456789, 45678901};
        
        for(int index : indexs){
        //for(int index = 234560; index < 234570; index++){  
            System.out.println(String.format("Input: %d, \tOutput: %c, %c", index, sv.charAt(index), sv.charAt_n(index)));
        }

    }

}
