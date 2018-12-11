package fgafa.dailyCoding;

public class Temp {

    //problem #74
    public int count(int n, int x){
        if(n <= 0 || x <= 0){
            return 0;
        }

        int count = 0;

        for(int i = 1; i <= n; i++){
            if( x % i == 0 && x / i <= n){
                count++;
            }
        }

        return count;
    }


    public static void main(String[] args){
        Temp sv = new Temp();

        System.out.println(sv.count(6,12));
    }

}
