package fgafa.todo.goo;

/**
 * _http://www.jiuzhang.com/solution/input-stream
 */
public class BusStation {

//    public int getMinTransferNumber(int N, int[][] routes, int A, int B){
//
//    }

    private String getClassName(){
        return this.getClass().getName();
    }

    public static void main(String[] args){
        BusStation sv = new BusStation();

        System.out.println(sv.getClassName());
        System.out.println(BusStation.class.getName());
    }
}
